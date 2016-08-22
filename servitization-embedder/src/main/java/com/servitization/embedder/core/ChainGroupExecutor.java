package com.servitization.embedder.core;

import com.servitization.embedder.context.GlobalContext;
import com.servitization.embedder.context.RequestContext;
import com.servitization.embedder.context.impl.RequestContextImpl;
import com.servitization.embedder.handler.ChainGroup;
import com.servitization.embedder.handler.HandleResult;
import com.servitization.embedder.immobile.ImmobileRequest;
import com.servitization.embedder.immobile.ImmobileResponse;
import com.servitization.metadata.define.embedder.ChainElementDefine;
import com.servitization.metadata.define.embedder.ChainGroupDefine;
import com.servitization.metadata.define.embedder.GroupPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ChainGroupExecutor implements ChainGroup {

    private static final Logger LOG = LoggerFactory.getLogger(ChainGroupExecutor.class);

    private Chain chain = null;

    private ThreadPoolExecutor service = null;

    private GroupPolicy grpPolicy = null;

    private LinkedBlockingQueue<Runnable> queue = null;

    private int processTimeout;

    public void init(ChainElementDefine eleDefine, GlobalContext context) {
        ChainGroupDefine grpDefine = (ChainGroupDefine) eleDefine;
        List<ChainElementDefine> elements = grpDefine.getChainElementDefineList();
        chain = new Chain();
        chain.init(elements, context);
        grpPolicy = grpDefine.getGroupPolicy();
        LOG.info("Using 'size:" + grpDefine.getGroupSize() + "' created a pool with " + grpPolicy.toString() + " Policy!");
        queue = new LinkedBlockingQueue<>(grpDefine.getGroupSize());
        // default is abort-reject handler
        service = new ThreadPoolExecutor(grpDefine.getGroupSize(),
                grpDefine.getGroupSize(), 1, TimeUnit.MINUTES, queue,
                new ChainGroupThreadFactory());
        processTimeout = grpDefine.getGroupProcessTimeout() <= 0 ? 2 : grpDefine.getGroupProcessTimeout();
    }

    public HandleResult handle(ImmobileRequest request, ImmobileResponse response, RequestContext reqContext) {
        Future<HandleResult> future;
        try {
            future = service.submit(new GroupCallable(request, response, reqContext));
        } catch (RejectedExecutionException rej) {
            if (grpPolicy == GroupPolicy.OPEN) {
                LOG.debug("The OPEN pool is degrading the request. Because the pool is full!");
                return HandleResult.CONTINUE; // 启用降级策略
            } else {
                reqContext.addError();
                return HandleResult.STOP;
            }
        }
        HandleResult result = HandleResult.STOP;
        try {
            result = future.get(processTimeout, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            if (grpPolicy == GroupPolicy.OPEN) {
                LOG.debug("The OPEN pool is degrading the request. Because task timeout!");
                return HandleResult.CONTINUE;
            }
        } catch (Exception e) {
            LOG.error(reqContext.getGlobalContext().getServiceDefine().getName() + "#Error occurs when processing group callable task.", e);
        }
        return result;
    }

    public void destroy(GlobalContext context) {
        service.shutdown();
        chain.destory(context);
        service = null;
        chain = null;
    }

    /**
     * 异步请求对象
     */
    private class GroupCallable implements Callable<HandleResult> {

        private final ImmobileRequest request;
        private final ImmobileResponse response;
        private final RequestContext reqContext;

        public GroupCallable(ImmobileRequest request, ImmobileResponse response, RequestContext reqContext) {
            this.request = request;
            this.response = response;
            this.reqContext = reqContext;
        }

        public HandleResult call() throws Exception {
            RequestContextImpl reqContextWrapper = new RequestContextImpl();
            reqContextWrapper.restoreRequestContext(reqContext);
            return chain.exec(request, response, reqContextWrapper);
        }
    }

    /**
     * 线程创建工厂 与默认工厂的区别仅在于指定的线程名字
     */
    private static class ChainGroupThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        ChainGroupThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            namePrefix = "chain group pool-" + poolNumber.getAndIncrement() + "-thread-";
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }
}
