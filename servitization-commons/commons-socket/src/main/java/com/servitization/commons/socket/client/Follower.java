package com.servitization.commons.socket.client;

import com.servitization.commons.log.trace.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


/**
 * 客户端tcp请求过程跟随者
 */
public final class Follower {

    private static final Logger LOGGER = LoggerFactory.getLogger(Follower.class);
    /**
     * 跟踪id
     */
    private final String followerId;
    private final CountDownLatch latch;
    private Object result;
    private long timeOut;
    private final String traceId;
    private final String spanName;

    public Follower(String followerId, CountDownLatch latch, long timeOut, String traceId, String spanName) {

        this.followerId = followerId;
        this.latch = latch;
        this.timeOut = timeOut;
        this.spanName = spanName;
        this.traceId = traceId;
    }

    public String getFollowerId() {
        return followerId;
    }

    public String getTraceId() {
        return traceId;
    }

    public String getSpanName() {
        return spanName;
    }

    public Object getResult() {
//		LOGGER.info("client call Follower.getResult(),maxWait time "+timeOut+"ms.marking:"+followerId);
//		long time = System.currentTimeMillis();
//		InterruptedException ex = null;
        try {
            latch.await(timeOut, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            LOGGER.error("Follower await  InterruptedException#marking:" + followerId, e);
//			ex =e;
        }
//		LOGGER.info("client call Follower.getResult() finished,wait time "+(System.currentTimeMillis()-time)+"ms,marking:"+followerId);

        Tracer tracer = Tracer.getTracer();
        tracer.endTrace();
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
