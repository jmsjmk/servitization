package com.servitization.commons.socket.client;

import com.servitization.commons.log.GlobalConstant;
import com.servitization.commons.log.Log;
import com.servitization.commons.log.LogFactory;
import com.servitization.commons.log.Logger;
import com.servitization.commons.log.trace.OSUtil;
import com.servitization.commons.log.trace.Spanner;
import com.servitization.commons.log.trace.Tracer;
import com.servitization.commons.socket.client.pool.IConnectPool;
import com.servitization.commons.socket.message.SocketRequest;
import com.servitization.commons.socket.remote.adapter.ConvertAdapter;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.joda.time.DateTime;

import java.util.Map;

public class RemoteClient {
    private static final Logger LOGGER = LogFactory.getLogger();

    private Map<String, IConnectPool> connectPools;

    public RemoteClient() {
    }

    /**
     * 针对操作和请求是异步的，但是获得连接方法还是同步，可能出现阻塞的情况
     *
     * @param request
     * @param timeOut
     * @return
     * @throws Throwable
     */
    public Follower asyncClient(String poolName, SocketRequest.Request request, long timeOut, ConvertAdapter adapter) throws Exception {

        ChannelBuffer rBuffer = ChannelBuffers.copiedBuffer(request.toByteArray());

        IConnectPool connectPool = connectPools.get(poolName);
        Channel channel = connectPool.getChannel(timeOut);
        if (channel == null) {
            return null;
        }
        Log log = new Log();
        log.setLogTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss SSS"));
        log.setBusinessLine(GlobalConstant.getBusinessLine());
        log.setLogType("001");
        log.setServerName(OSUtil.linuxLocalName());
        log.setServerIp(OSUtil.linuxLocalIp());
        log.setUserLogType("emcf-client-request");
        log.setAppName(GlobalConstant.getAppName());
        log.setServiceName(request.getServiceName());
        log.setRequestHeader(channel.toString());
        Spanner span = Tracer.getTracer().getCurrentSpan();
        log.setSpan(span.toSpanString());
        log.setTraceId(request.getTraceId());
        Follower follower = FollowerFactory.produceFollower(request.getMarking(), request.getTraceId(), request.getSpan(), timeOut, adapter);
        LOGGER.info(log);
        channel.write(rBuffer).addListener(new ChannelFutureListener(connectPool));
        return follower;
    }

    public void setConnectPools(Map<String, IConnectPool> connectPools) {
        this.connectPools = connectPools;
    }
}

class ChannelFutureListener implements org.jboss.netty.channel.ChannelFutureListener {
    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ChannelFutureListener.class);
    private final IConnectPool connectPool;

    public ChannelFutureListener(IConnectPool connectPool) {
        this.connectPool = connectPool;
    }

    @Override
    public void operationComplete(ChannelFuture future) throws Exception {
        Channel channel = future.getChannel();
        if (channel.isWritable() && future.isDone() && future.isSuccess()) {
            try {
                this.connectPool.releaseChannel(channel);
            } catch (Exception e) {
                LOGGER.error("ChannelFutureListener releaseChannel Exception,channelId:" + channel.getId(), e);
                return;
            }
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("ChannelFutureListener releaseChannel finished,channelId:" + channel.getId());
            }
        } else {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("ChannelFutureListener releaseChannel channel not done,channelId:" + channel.getId());
            }
        }
    }
}
