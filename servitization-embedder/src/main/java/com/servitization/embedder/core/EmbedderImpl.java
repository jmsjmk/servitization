package com.servitization.embedder.core;

import com.servitization.embedder.Embedder;
import com.servitization.embedder.Worker;
import com.servitization.embedder.communication.IPProvider;
import com.servitization.embedder.communication.ZKCommunicator;
import com.servitization.embedder.context.impl.GlobalContextImpl;
import com.servitization.embedder.immobile.ImmobileRequest;
import com.servitization.embedder.immobile.ImmobileResponse;
import com.servitization.metadata.define.embedder.ServiceDefine;
import com.servitization.metadata.zk.PushState;
import com.servitization.metadata.zk.StatusInfo;
import com.servitization.metadata.zk.ZKConnection;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import javax.servlet.FilterChain;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class EmbedderImpl implements Embedder {

	/**
	 * 单例处理:--------------------begin-------------------------
	 */
	private EmbedderImpl() {
	}

	private static EmbedderImpl self = new EmbedderImpl();

	public static Embedder getInstance() {
		return self;
	}

	/**
	 * 单例处理:--------------------end---------------------------
	 */

	private static final Logger LOG = LoggerFactory.getLogger(EmbedderImpl.class);

	private Worker worker = null;

	private ReadWriteLock runtimeLock = new ReentrantReadWriteLock();

	private volatile boolean initFinishedFlag = false; // 是否初始化完成

	/**
	 * Environment:--------------------begin-------------------------
	 */
	private ApplicationContext emcfContext = null; // EMCF context

	/**
	 * Environment:--------------------end---------------------------
	 */

	public void init() {
		LOG.info("Begin to init the Embedder...");
		initEnvironment(); // 全局一次
		// --------------启动过程开始--------------
		// --------------1.从启动节点读取元数据装载--------------
		boolean updateSuccess = false;
		ServiceDefine sd = ZKCommunicator.getServiceDefineFromBoot();
		if (sd != null) {
			try {
				LOG.info(sd.getName() + " " + sd.getVersion() + " are ready from zk...");
				Worker worker = generateWorker(sd);
				this.worker = worker;
				updateSuccess = true;
			} catch (Exception e) {
				LOG.error(e.getMessage());
			}
		}
		// --------------2.从本地备份读取元数据装载--------------
		if (!updateSuccess) {
			LOG.info("Begin to get the metadata from local...");
			long L1 = System.currentTimeMillis();
			sd = LocalBackupManager.restore();
			long L2 = System.currentTimeMillis();
			LOG.info("Finsh to get the metadata from local...time[" + (L2 - L1) +"]ms");
			if (sd != null) {
				try {
					Worker worker = generateWorker(sd);
					this.worker = worker;
					updateSuccess = true;
				} catch (Exception e) {
					LOG.error(e.getMessage());
				}
			}
		}

		if (!updateSuccess) {
			LOG.error("Can't load ServiceDefine from zk nor local, boot failed!");
			this.destroy();
			System.exit(0);// 启动失败
		}
		// --------------3.建立本地元数据备份--------------
		LOG.info("Begin to backup the sd...");
		LocalBackupManager.backupFormat(sd);
		// --------------4.绑定zk通信--------------
		LOG.info("Begin to bind the zk...");
		bindZK(sd);
		// --------------5.关注zk过期事件--------------
		ZKConnection.registerListenerOnExpired(new RebindExpiredListener(sd));
		// --------------启动过程结束--------------
		initFinishedFlag = true;
		LOG.info("Success to init the embedder...");
	}

	private void initEnvironment() {
		LOG.info("Begin to init the spring environment...");
		emcfContext = new GenericXmlApplicationContext(new String[] { "config/root-context.xml" });
		LOG.info("Finsh to init the spring environment...");
	}

	private Worker generateWorker(ServiceDefine sd) {
		LOG.info("Begin to generate the worker...");
		long L1 = System.currentTimeMillis();
		Worker newWorker = new WorkerImpl();
		newWorker.init(new GlobalContextImpl(sd, emcfContext));
		long L2 = System.currentTimeMillis();
		LOG.info("Finsh to generate the worker...time[" + (L2 - L1) + "]ms");
		return newWorker;
	}

	private void bindZK(ServiceDefine sd) {
		boolean reportSuccess = ZKCommunicator
				.reportStatus(new StatusInfo(sd.getName(), sd.getVersion(), IPProvider.hostname, IPProvider.ip));
		if (!reportSuccess)
			LOG.error("Failed to report the status to the zk!");
		boolean watchSuccess = ZKCommunicator.watchPush(new PushWatcher());
		if (!watchSuccess)
			LOG.error("Failed to watch the push to the zk!");
	}

	public void process(ImmobileRequest request, ImmobileResponse response, FilterChain chain) {
		if (!initFinishedFlag)
			return;
		runtimeLock.readLock().lock();
		try {
			worker.process(request, response, chain);
		} finally {
			runtimeLock.readLock().unlock();
		}
	}

	public void destroy() {
		if (worker != null)
			worker.destory();
		worker = null;
		emcfContext = null;
		LOG.info("Embedder has been destroyed.");
	}

	class PushWatcher implements Watcher {

		@Override
		public void process(WatchedEvent event) {
			if (event.getType() == EventType.NodeDataChanged) {
				try {
					ServiceDefine sd = ZKCommunicator.getServiceDefineFromPush(event.getPath());
					if (sd != null) {
						ZKCommunicator.pushState(PushState.IN_SYNCHRONOUS);
						// --------------1.从PUSH节点读取元数据,准备装载--------------
						LOG.info("Begin to reload the service define...");
						Worker oldWorker = EmbedderImpl.this.worker;
						Worker newWorker = generateWorker(sd);

						// --------------2.新产生TPS timer--------------
						runtimeLock.writeLock().lock();
						try {
							EmbedderImpl.this.worker = newWorker;
						} finally {
							runtimeLock.writeLock().unlock();
						}

						LOG.info("Begin to destory the old worker...");
						oldWorker.destory();
						LOG.info("Finish to reload the worker and timer...");
						// --------------3.建立本地元数据备份--------------
						// LocalBackupManager.backup(sd);
						LocalBackupManager.backupFormat(sd);
						LOG.info("Finish to backup to the local xml...");
						// --------------4.更新zk status节点--------------
						if (!ZKCommunicator.reportStatus(
								new StatusInfo(sd.getName(), sd.getVersion(), IPProvider.hostname, IPProvider.ip)))
							throw new RuntimeException("Failed to report the status to the zk!");
						LOG.info("Finish to report the status to the zk...");
						// --------------5.关注zk过期事件--------------
						ZKConnection.registerListenerOnExpired(new RebindExpiredListener(sd));
						ZKCommunicator.pushState(PushState.SYNCHRONOUS_SUCCESS);
						LOG.info("Finish to reload, well done...");
					}
				} catch (Exception e) {
					ZKCommunicator.pushState(PushState.SYNCHRONOUS_FAILED);
					LOG.error("Failed to update the service define form push!", e);
				} finally {
					// 绑定zk通信
					boolean watchSuccess = ZKCommunicator.watchPush(new PushWatcher());
					if (!watchSuccess)
						LOG.error("Failed to watch the push to the zk!");
					else
						LOG.info("Finish to bind to the zk...");
				}
			}
		}
	}

	class RebindExpiredListener implements ZKConnection.ExpiredListener {

		private ServiceDefine sd = null;

		RebindExpiredListener(ServiceDefine sd) {
			this.sd = sd;
		}
		@Override
		public void reset() {
			bindZK(sd);
		}
	}
}
