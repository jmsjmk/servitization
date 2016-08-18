package com.servitization.metadata.zk;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooKeeper.States;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * 同步化的zk连接
 */
public class ZKConnection {

    private static final Logger LOG = LoggerFactory.getLogger(ZKConnection.class);

    private static ZooKeeper zk = null;

    public static synchronized ZooKeeper zk() {
        if (zk == null || !zk.getState().isConnected()) {
            try {
                LOG.info("Begin to create the ZK connection.");
                createConnection();
                LOG.info("Finish to create the ZK connection.");
            } catch (Exception e) {
                LOG.error("Can't finish creating the connection with ZK.", e);
                return null;
            }
        }
        return zk;
    }

    private static void createConnection() throws IOException,
            InterruptedException {
        CountDownLatch connectedLatch = new CountDownLatch(1);
        Watcher watcher = new ConnectedWatcher(connectedLatch);
        zk = new ZooKeeper(Constants.zk_url, 60000, watcher);
        String auth = Constants.zk_username + ":" + Constants.zk_password;
        zk.addAuthInfo("digest", auth.getBytes());
        if (States.CONNECTING == zk.getState())
            connectedLatch.await();
    }

    static class ConnectedWatcher implements Watcher {

        private CountDownLatch connectedLatch;

        ConnectedWatcher(CountDownLatch connectedLatch) {
            this.connectedLatch = connectedLatch;
        }

        @Override
        public void process(WatchedEvent event) {
            if (event.getState() == KeeperState.SyncConnected) {
                connectedLatch.countDown();
            } else if (event.getState() == KeeperState.Expired) {
                // 处理断线重连问题
                if (zk != null)
                    try {
                        zk.close();
                    } catch (InterruptedException e) {
                        LOG.error("Can't close the expired connection!", e);
                    }
                zk = null;
                if (listener != null)
                    listener.reset();
            }
        }
    }

    public static void registerListenerOnExpired(ExpiredListener l) {
        if (l != null)
            listener = l;
    }

    private static volatile ExpiredListener listener = null;

    public static interface ExpiredListener {

        public void reset();

    }
}
