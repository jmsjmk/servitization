package com.servitization.commons.socket.client;

import com.servitization.commons.socket.remote.adapter.ConvertAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

public class FollowerFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(FollowerFactory.class);
    private static final Map<String, Record> INVENTORY = new ConcurrentHashMap<>();
    private static final Timer followerFactoryTimer = new Timer("followerFactoryTimer", true);

    public static Follower produceFollower(String rpcId, String traceId, String span, long timeOut, ConvertAdapter adapter) {

        Record record = new Record(rpcId, timeOut, adapter, traceId, span);
        INVENTORY.put(rpcId, record);
        return record.getFollower();
    }

    public static Follower getFollowerInfo(String rpcId) {
        Record record = INVENTORY.get(rpcId);
        return (record != null ? record.getFollower() : null);
    }

    public static void finishProduction(String rpcId, byte[] info) {
        Record record = INVENTORY.remove(rpcId);
        if (record != null) {
            record.setContent(info);
        } else {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("request mark does not exist,marking:" + rpcId);
            }
        }
    }

    static class Record {
        private Follower follower;
        private ConvertAdapter adapter;
        private final CountDownLatch latch;
        private final TimerTask task;

        Record(String rpcId, long timeOut, ConvertAdapter adapter, String traceId, String spanName) {
            latch = new CountDownLatch(1);
            this.adapter = adapter;
            this.follower = new Follower(rpcId, latch, timeOut, traceId, spanName);
            task = new AutomaticClear(rpcId);
            //清理任务发生在正常超时之后
            followerFactoryTimer.schedule(task, timeOut + 50);
        }

        public void setContent(byte[] content) {
            if (content != null && content.length > 0) {
                try {
                    this.follower.setResult(adapter.convert(content));
                } catch (Exception e) {
                    LOGGER.error("convert result exception#marking:" + follower.getFollowerId(), e);
                }
            }
            latch.countDown();
            task.cancel();
        }

        public Follower getFollower() {
            return follower;
        }
    }

    static class AutomaticClear extends TimerTask {
        private String id;

        public AutomaticClear(String id) {
            this.id = id;
        }

        @Override
        public void run() {
            FollowerFactory.finishProduction(this.id, null);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("auto clear follower finished,marking:" + id);
            }
        }
    }
}
