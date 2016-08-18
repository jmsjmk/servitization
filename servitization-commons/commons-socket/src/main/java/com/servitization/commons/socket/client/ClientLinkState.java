package com.servitization.commons.socket.client;

import com.google.protobuf.ByteString;
import com.servitization.commons.socket.client.pool.IConnectPool;
import com.servitization.commons.socket.enums.*;
import com.servitization.commons.socket.message.SocketRequest;
import com.servitization.commons.socket.message.SocketResponse;
import com.servitization.commons.socket.vo.DelayItem;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.*;
import org.jboss.netty.handler.timeout.IdleStateAwareChannelHandler;
import org.jboss.netty.handler.timeout.IdleStateEvent;
import org.jboss.netty.handler.timeout.ReadTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

public class ClientLinkState extends IdleStateAwareChannelHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientLinkState.class);
    private IConnectPool connectPool;
    private DelayQueue<DelayItem<String, Channel>> dQueue;
    private ConcurrentMap<String, DelayItem<String, Channel>> markChannelMap;
    private final int timeout;

    /**
     * @param connectPool 连接池
     * @param timeout     心跳超时时间
     */
    public ClientLinkState(IConnectPool connectPool, int timeout) {
        this.connectPool = connectPool;
        this.timeout = timeout;
        dQueue = new DelayQueue<>();
        markChannelMap = new ConcurrentHashMap<>();
        //初始化检查
        CheckIdleQueueThread check = new CheckIdleQueueThread(this.connectPool, dQueue, markChannelMap);
        Thread checkThread = new Thread(check, "check-idle-queue-thread");
        checkThread.setDaemon(true);
        checkThread.start();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
            throws Exception {
        Throwable throwable = e.getCause();
        if (throwable instanceof ReadTimeoutException || throwable instanceof IOException) {
            LOGGER.error(
                    "ClientLinkState socket exception channel will close#channel:"
                            + ctx.getChannel().toString(), throwable);
            try {
                DelayItem<String, Channel> item = this.markChannelMap.remove(ctx.getChannel().getLocalAddress());
                if (item != null) {
                    this.dQueue.remove(item);
                    this.connectPool.discardChannel(ctx.getChannel());
                }
            } catch (Exception e2) {
                LOGGER.error("ClientLinkState exceptionCaught#", e2);
            }
        } else {
            super.exceptionCaught(ctx, e);
        }
    }

    @Override
    public void channelIdle(ChannelHandlerContext ctx, IdleStateEvent e) throws Exception {
        super.channelIdle(ctx, e);
        Channel channel = e.getChannel();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(e.getState() + ",channel:" + channel.toString());
        }
        switch (e.getState()) {
            case READER_IDLE: {// 读取时间超时
                this.connectPool.discardChannel(channel);//废弃网络链接
                break;
            }
            case WRITER_IDLE: {
                Channel rChannel = this.connectPool.changeChannel(channel, timeout);
                if (rChannel != null) {
                    String marking = rChannel.getLocalAddress().toString();
                    SocketRequest.Request srequest = SocketRequest.Request.newBuilder()
                            .setCharset(CharsetEnum.UTF8.code())
                            .setCompress(CompressEnum.NON.code())
                            .setEncrypt(EncryptEnum.NON.code())
                            .setProtocol(ProtocolEnum.STRING.code())
                            .setServiceName(EmcfSerivceEnum.HEARBEAT.serivce())
                            .setServiceVersion("1.0")
                            .setSocketVersion(SocketVersionEnum.INITIAL_VERSION.code())
                            .setMarking(marking).setRequest(ByteString.EMPTY).build();
                    ChannelBuffer rBuffer = ChannelBuffers.copiedBuffer(srequest.toByteArray());
                    e.getChannel().write(rBuffer).addListener(new IdleChannelFutureListener(this.connectPool, dQueue, markChannelMap, timeout, marking));
                }
                break;
            }
            case ALL_IDLE: //开启了全空闲监听
                this.connectPool.discardChannel(channel);//废弃网络链接
                break;
            default:
                //应该不会走到这来
                break;
        }
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
            throws Exception {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(new StringBuilder().append("client channel(").append(ctx.getChannel().getId()).append(") received messages:").append(e.toString()).toString());
        }
        SocketResponse.Response response = (SocketResponse.Response) e.getMessage();
        if (response != null) {
            // 查询是否是emcd系统级的方法，如果是则忽略
            String serviceName = response.getServiceName();
            String marking = response.getMarking();
            //是心跳检查结果去掉记录
            if (EmcfSerivceEnum.hasService(serviceName)) {

                DelayItem<String, Channel> item = this.markChannelMap.remove(marking);
                if (item != null) {
                    this.dQueue.remove(item);
                    this.connectPool.releaseChannel(ctx.getChannel());
                }
            } else {
                //非心跳检查进入其他handler
                super.messageReceived(ctx, e);
            }
        } else {
            LOGGER.error("ClientIdleHandler.messageReceived response is null#channelId:"
                    + ctx.getChannel().getId());
            return;
        }
    }


    static class IdleChannelFutureListener extends ChannelFutureListener {

        private IConnectPool connectPool;
        private int timeout;
        private String marking;
        private DelayQueue<DelayItem<String, Channel>> dQueue;
        private ConcurrentMap<String, DelayItem<String, Channel>> markChannelMap;

        public IdleChannelFutureListener(IConnectPool connectPool, DelayQueue<DelayItem<String, Channel>> dQueue, ConcurrentMap<String, DelayItem<String, Channel>> markChannelMap, int timeout, String marking) {
            super(connectPool);
            this.timeout = timeout;
            this.connectPool = connectPool;
            this.marking = marking;
            this.dQueue = dQueue;
            this.markChannelMap = markChannelMap;
        }

        @Override
        public void operationComplete(ChannelFuture future) throws Exception {
            if (future.isDone() && future.isSuccess()) {
                long nanoTime = TimeUnit.NANOSECONDS.convert(timeout, TimeUnit.MILLISECONDS);
                DelayItem<String, Channel> item = new DelayItem<String, Channel>(marking, future.getChannel(), nanoTime);
                this.markChannelMap.put(future.getChannel().getLocalAddress().toString(), item);
                dQueue.put(item);
                //由于链接还处在检测状态所以不恢复使用
            } else {
                this.connectPool.discardChannel(future.getChannel());
                LOGGER.error("IdleChannelFutureListener channel send failure#channelId:"
                        + future.getChannel().getId(), future.getCause());
            }
        }
    }

    static class CheckIdleQueueThread implements Runnable {
        private DelayQueue<DelayItem<String, Channel>> dqueue;
        private ConcurrentMap<String, DelayItem<String, Channel>> markChannelMap;
        private IConnectPool connectPool;

        public CheckIdleQueueThread(IConnectPool connectPool, DelayQueue<DelayItem<String, Channel>> dqueue, ConcurrentMap<String, DelayItem<String, Channel>> markChannelMap) {
            this.dqueue = dqueue;
            this.markChannelMap = markChannelMap;
            this.connectPool = connectPool;
        }

        @Override
        public void run() {
            LOGGER.info("CheckIdleQueueThread start...");
            for (; ; ) {
                try {
                    DelayItem<String, Channel> item = dqueue.take();
                    if (item != null) {

                        DelayItem<String, Channel> mitem = this.markChannelMap.remove(item.getKey());
                        if (mitem != null) {
                            this.connectPool.discardChannel(mitem.getValue());
                        }
                    }
                } catch (Exception e) {
                    LOGGER.info("CheckIdleQueueThread Exception.", e);
                }
            }
        }
    }
}
