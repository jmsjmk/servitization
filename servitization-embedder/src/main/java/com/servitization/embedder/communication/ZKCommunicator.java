package com.servitization.embedder.communication;

import com.alibaba.fastjson.JSON;
import com.servitization.metadata.define.XmlSerializer;
import com.servitization.metadata.define.embedder.ServiceDefine;
import com.servitization.metadata.define.embedder.impl.ServiceDefineImpl;
import com.servitization.metadata.zk.*;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ZKCommunicator {

    private static final Logger LOG = LoggerFactory.getLogger(ZKCommunicator.class);

    // -----------------------------------------------BOOT-------------------------------------------------

    private static CountDownLatch bootLatch = new CountDownLatch(1);

    private static volatile ServiceDefine sd = null;

    public static ServiceDefine getServiceDefineFromBoot() {
        try {
            ZKBaseStructureBuilder.buildBaseStructure();
            ZooKeeper zk = ZKConnection.zk();
            if (zk.exists(Constants.boot + "/" + IPProvider.hostname, false) == null)
                zk.create(Constants.boot + "/" + IPProvider.hostname,
                        Constants.NONE, Ids.OPEN_ACL_UNSAFE,
                        CreateMode.EPHEMERAL);
            zk.getData(Constants.boot + "/" + IPProvider.hostname,
                    new BootWatcher(), null);
            bootLatch.await(10, TimeUnit.SECONDS);
            // 删掉自身节点，表示不再接收xml数据
            ZKConnection.zk().delete(Constants.boot + "/" + IPProvider.hostname, -1);
            return sd;
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return null;
        }
    }

    static class BootWatcher implements Watcher {

        @Override
        public void process(WatchedEvent event) {
            try {
                if (event.getType() == EventType.NodeDataChanged) {
                    byte[] data = ZKConnection.zk().getData(event.getPath(),
                            false, null);
                    if (data != null && data.length > 0) {
                        String json = new String(data);
                        LOG.info("Boot watcher has get the json: " + json);
                        if (!Constants.ACK_NOTFOUND.equals(json)) {
                            Map<String, String> m_v = JSON.parseObject(json, Map.class);
                            String xml = m_v.get(Constants.boot_meta);
                            String version = m_v.get(Constants.boot_version);
                            ServiceDefineImpl sd_no_version = (ServiceDefineImpl) (XmlSerializer.deserialize(xml));
                            sd_no_version.setVersion(version);
                            sd = sd_no_version;
                            LOG.info("Finish to load sd from zk:" + sd.getName() + sd.getVersion());
                        }
                    }
                }
            } catch (Exception e) {
                LOG.error(e.getMessage());
            } finally {
                bootLatch.countDown();
            }
        }
    }

    // -----------------------------------------------STATUS-------------------------------------------------

    public static boolean reportStatus(StatusInfo info) {
        try {
            ZooKeeper zk = ZKConnection.zk();
            if (zk == null)
                return false;
            if (zk.exists(Constants.status + "/" + info.getIp(), false) == null)
                zk.create(Constants.status + "/" + info.getIp(), info
                                .toString().getBytes(), Ids.OPEN_ACL_UNSAFE,
                        CreateMode.EPHEMERAL);
            else
                zk.setData(Constants.status + "/" + info.getIp(), info.toString().getBytes(), -1);
            return true;
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return false;
        }
    }

    // -----------------------------------------------PUSH-------------------------------------------------

    public static boolean watchPush(Watcher w) {
        try {
            ZooKeeper zk = ZKConnection.zk();
            if (zk == null)
                return false;
            if (zk.exists(Constants.push + "/" + IPProvider.ip, false) == null) {
                zk.create(Constants.push + "/" + IPProvider.ip,
                        Constants.NONE, Ids.OPEN_ACL_UNSAFE,
                        CreateMode.EPHEMERAL);
            }
            zk.getData(Constants.push + "/" + IPProvider.ip, w, null);
            return true;
        } catch (Exception e) {
            LOG.error("Failed to watch push", e.getMessage());
            return false;
        }
    }

    public static ServiceDefine getServiceDefineFromPush(String path) {
        try {
            ServiceDefineImpl sd = null;
            byte[] data = ZKConnection.zk().getData(path, false, null);
            if (data != null && data.length > 0) {
                String json = new String(data);
                Map<String, String> m_v_ack = JSON.parseObject(json, Map.class);
                sd = (ServiceDefineImpl) (XmlSerializer.deserialize(m_v_ack
                        .get(Constants.push_meta)));
                sd.setVersion(m_v_ack.get(Constants.push_version));
            }
            return sd;
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return null;
        }
    }

    public static void pushState(PushState state) {
        try {
            Map<String, String> ack = new HashMap<String, String>();
            ack.put(Constants.push_ack, state.name());
            String json = JSON.toJSONString(ack);
            ZKConnection.zk().setData(Constants.push + "/" + IPProvider.ip,
                    json.getBytes(), -1);
        } catch (Exception e) {
            LOG.error("Failed to push state", e.getMessage());
        }
    }
}
