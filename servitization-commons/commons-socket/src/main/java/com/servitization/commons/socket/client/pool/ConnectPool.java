package com.servitization.commons.socket.client.pool;

import com.servitization.commons.socket.client.ClientHandler;
import com.servitization.commons.socket.client.ClientLinkState;
import com.servitization.commons.socket.enums.ChannelStatusEnum;
import com.servitization.commons.socket.message.SocketResponse;
import com.servitization.commons.socket.utils.EmcfDelayQueue;
import com.servitization.commons.socket.vo.ChannelState;
import com.servitization.commons.socket.vo.DelayObjectItem;
import com.servitization.commons.socket.vo.DiscardChannel;
import com.servitization.commons.socket.vo.TimeState;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.protobuf.ProtobufDecoder;
import org.jboss.netty.handler.codec.protobuf.ProtobufEncoder;
import org.jboss.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import org.jboss.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import org.jboss.netty.handler.execution.ExecutionHandler;
import org.jboss.netty.handler.execution.MemoryAwareThreadPoolExecutor;
import org.jboss.netty.handler.timeout.IdleStateHandler;
import org.jboss.netty.util.HashedWheelTimer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * 自定义连接池
 */
public class ConnectPool implements IConnectPool {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectPool.class);

    /**
     * netty客户单
     */
    private final ClientBootstrap bootstrap;
    /**
     * 连接池变量
     */
    private PoolVariable variable;

    private String connectPoolName;

    /**
     * 默认关闭关闭空闲连接监听，开启心跳检查监控读写空闲连接，关闭强制关闭连接功能，61秒检查空闲连接
     *
     * @param poolName
     * @param url
     */
    public ConnectPool(String poolName, String url) {
        this(poolName, url, 10, 60000, 200, 10, 10000, false, 0, 0.5, 1048576,
                1048576, true, 5, 60, 100, 60000, false, 0, 61000);
    }

    /**
     * 默认关闭关闭空闲连接监听，开启心跳检查监控读写空闲连接
     *
     * @param poolName
     * @param url
     * @param coefficient
     * @param forceCloseChannel
     * @param forceCloseTimeMillis
     */
    public ConnectPool(String poolName, String url, double coefficient,
                       boolean forceCloseChannel, long forceCloseTimeMillis) {
        this(poolName, url, 10, 60000, 200, 10, 10000, false, 0, coefficient,
                1048576, 1048576, true, 5, 60, 100, 60000, forceCloseChannel,
                forceCloseTimeMillis, 61000);
    }

    /**
     * @param poolName
     * @param url
     * @param openIdleClose
     * @param minEvictableIdleTimeSeconds
     * @param coefficient
     * @param openHeartbeat
     * @param chennelWriteIdleSeconds
     * @param chennelReadIdleSeconds
     * @param maxHeartbeatWaitMillis
     * @param forceCloseChannel
     * @param forceCloseTimeMillis
     */
    public ConnectPool(String poolName, String url, boolean openIdleClose,
                       int minEvictableIdleTimeSeconds, double coefficient,
                       boolean openHeartbeat, int chennelWriteIdleSeconds,
                       int chennelReadIdleSeconds, int maxHeartbeatWaitMillis,
                       boolean forceCloseChannel, long forceCloseTimeMillis) {
        this(poolName, url, 10, 60000, 200, 10, 10000, openIdleClose,
                minEvictableIdleTimeSeconds, coefficient, 1048576, 1048576,
                openHeartbeat, chennelWriteIdleSeconds, chennelReadIdleSeconds,
                maxHeartbeatWaitMillis, 60000, forceCloseChannel,
                forceCloseTimeMillis, 61000);
    }

    /**
     * 全量连接池构造器
     *
     * @param poolName                    连接池名称
     * @param url                         连接池URL
     * @param initialSize                 初始化连接数
     * @param maxActive                   最大活跃连接数
     * @param maxIdle                     最大空闲连接数
     * @param minIdle                     最小空闲连接数
     * @param maxWait                     最大等待时间，单位毫秒
     * @param openIdleClose               池中的连接空闲指定时间后被回收
     * @param minEvictableIdleTimeSeconds 池中的连接空闲指定时间后被回收
     * @param coefficient                 生成线程系数[0,1)
     * @param maxChannelMemorySize        通道最大内存数
     * @param maxTotalMemorySize          最大内存使用数
     * @param openHeartbeat               是否开启心跳检查（心跳检查会导致空闲关闭检查无效）
     * @param chennelWriteIdleSeconds     通道最大写空闲时间秒
     * @param chennelReadIdleSeconds      通道最大读空闲时间秒
     * @param maxHeartbeatWaitMillis      心跳最大等待时间毫秒
     * @param discardChannelWaitTime      过期通道销毁等待时间毫秒
     * @param forceCloseChannel           是否强制关闭连接(开启强制关闭时，建议把discardChannelWaitTime参数调大)
     * @param forceCloseTimeMillis        是否强制关闭连接最大时间
     * @param checkIdleLinkMillis         检查空闲连接 （这是大于0就是开始空闲连接检查）
     */
    public ConnectPool(String poolName, String url, int initialSize,
                       int maxActive, int maxIdle, int minIdle, int maxWait,
                       boolean openIdleClose, int minEvictableIdleTimeSeconds,
                       double coefficient, long maxChannelMemorySize,
                       long maxTotalMemorySize, boolean openHeartbeat,
                       int chennelWriteIdleSeconds, int chennelReadIdleSeconds,
                       int maxHeartbeatWaitMillis, long discardChannelWaitTime,
                       boolean forceCloseChannel, long forceCloseTimeMillis,
                       long checkIdleLinkMillis) {
        if (maxTotalMemorySize < maxChannelMemorySize) {
            maxTotalMemorySize = maxChannelMemorySize;
        }
        bootstrap = new ClientBootstrap(new NioClientSocketChannelFactory(
                Executors.newCachedThreadPool(),
                Executors.newCachedThreadPool()));
        int numberOfCores = Runtime.getRuntime().availableProcessors();
        int poolSize = (int) (numberOfCores / (1 - coefficient));
        MemoryAwareThreadPoolExecutor executor = new MemoryAwareThreadPoolExecutor(
                poolSize, maxChannelMemorySize, maxTotalMemorySize, 20,
                TimeUnit.SECONDS);
        final ExecutionHandler executionHandler = new ExecutionHandler(executor);
        final ClientHandler handler = new ClientHandler(this);
        if (openHeartbeat) {
            final ClientLinkState idleHander = new ClientLinkState(this,
                    maxHeartbeatWaitMillis);// 检测所有是否可用
            final IdleStateHandler idleStateHandler = new IdleStateHandler(
                    new HashedWheelTimer(), chennelReadIdleSeconds,
                    chennelWriteIdleSeconds, 0);// 检测空闲链接状态
            bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
                public ChannelPipeline getPipeline() throws Exception {
                    return Channels.pipeline(
                            new ProtobufVarint32FrameDecoder(),
                            new ProtobufDecoder(SocketResponse.Response
                                    .getDefaultInstance()),
                            new ProtobufVarint32LengthFieldPrepender(),
                            new ProtobufEncoder(), idleStateHandler,
                            executionHandler, idleHander, handler);
                }
            });
        } else {

            if (openIdleClose) {
                final ClientLinkState idleHander = new ClientLinkState(this, 0);// 检测所有是否可用
                final IdleStateHandler idleStateHandler = new IdleStateHandler(
                        new HashedWheelTimer(), 0, 0,
                        minEvictableIdleTimeSeconds);// 检测空闲链接状态
                bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
                    public ChannelPipeline getPipeline() throws Exception {
                        return Channels.pipeline(
                                new ProtobufVarint32FrameDecoder(),
                                new ProtobufDecoder(SocketResponse.Response
                                        .getDefaultInstance()),
                                new ProtobufVarint32LengthFieldPrepender(),
                                new ProtobufEncoder(), idleStateHandler,
                                executionHandler, idleHander, handler);
                    }
                });
            } else {
                bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
                    public ChannelPipeline getPipeline() throws Exception {
                        return Channels.pipeline(
                                new ProtobufVarint32FrameDecoder(),
                                new ProtobufDecoder(SocketResponse.Response
                                        .getDefaultInstance()),
                                new ProtobufVarint32LengthFieldPrepender(),
                                new ProtobufEncoder(), executionHandler,
                                handler);
                    }
                });
            }
        }

        bootstrap.setOption("child.tcpNoDelay", true);
        bootstrap.setOption("reuserAddress", true);
        bootstrap.setOption("child.keepAlive", true);
        bootstrap.setOption("allIdleTime", "5");
        this.connectPoolName = new StringBuilder("ConnectPool(")
                .append(poolName).append(")").toString();
        variable = new PoolVariable(poolName, url, initialSize, maxActive,
                (maxIdle <= minIdle ? (minIdle * 3) : maxIdle), minIdle,
                maxWait, discardChannelWaitTime, forceCloseChannel,
                forceCloseTimeMillis, checkIdleLinkMillis);
        ChannelItem item;
        for (int i = 0; i < this.variable.getInitiaSize(); i++) {
            item = this.createChannel();
            if (item != null) {
                this.variable.getChannelMap().put(item.getChannel().getId(), item);
                this.variable.getChannelQueue().offer(item.getChannel());
            }
        }
        Thread discardChannelThread = new Thread(new DiscardChannelThread(
                this.variable), "emcf-DiscardChannelThread");
        discardChannelThread.setDaemon(true);
        discardChannelThread.start();
        Thread waitChannelThread = new Thread(new WaitChannelThread(
                this.variable), "emcf-WaitChannelThread");
        waitChannelThread.setDaemon(true);
        waitChannelThread.start();
        Thread idleChannelListener = new Thread(new IdleChannelListener(this,
                this.variable), "emcf-IdleChannelListener");
        idleChannelListener.setDaemon(true);
        idleChannelListener.start();
        if (forceCloseChannel) {
            Thread forceCloseChannelThread = new Thread(
                    new ForceCloseChannelThread(this, this.variable),
                    "emcf-ForceCloseChannelThread");
            forceCloseChannelThread.setDaemon(true);
            forceCloseChannelThread.start();
        }
        if (this.variable.getCheckIdleLinkMillis() >= 0) {
            Thread asyncCreateChannelThread = new Thread(
                    new AsyncCreateChannelThread(this, this.variable),
                    "emcf-AsyncCreateChannelThread");
            asyncCreateChannelThread.setDaemon(true);
            asyncCreateChannelThread.start();
        }
        LOGGER.info(new StringBuilder()
                .append("ConnectPool init finished.poolName:").append(poolName)
                .append(",url:").append(url).append(",initiaSizea:")
                .append(initialSize).append(",maxActive:").append(maxActive)
                .append(",maxIdle:").append(maxIdle).append(",minIdle:")
                .append(minIdle).append(",maxWait:").append(maxWait)
                .append(",minEvictableIdleTimeSeconds:")
                .append(minEvictableIdleTimeSeconds).append(",coefficient:")
                .append(coefficient).append(",maxChannelMemorySize:")
                .append(maxChannelMemorySize).append(",maxTotalMemorySize:")
                .append(maxTotalMemorySize).append(",chennelWriteIdleSeconds:")
                .append(chennelWriteIdleSeconds)
                .append(",chennelReadIdleSeconds:")
                .append(chennelReadIdleSeconds)
                .append(",maxHeartbeatWaitMillis:")
                .append(maxHeartbeatWaitMillis).toString());
    }

    /**
     * 通道管理
     */
    private ChannelState channelManager(Channel channel, ChannelStatusEnum channelStatus, long maxtime) throws Exception {
        ChannelState state = new ChannelState();
        if (channelStatus == null) {
            return state;
        }
        // final Lock lock =this.variable.getLock();
        ChannelItem item;
        switch (channelStatus) {
            case IDLE:
                if (channel == null) {
                    return state;
                }
                item = this.variable.getChannelMap().get(channel.getId());
                if (item == null) {// 说明已经被废弃
                    return state;
                }
                final Lock idleLock = item.getItemLock();
                idleLock.lock();
                try {
                    if (item.getStatus() == ChannelStatusEnum.DISCARD) {
                        state.setSuccess(false);
                        state.setStatus(ChannelStatusEnum.DISCARD);
                    } else {
                        state.setSuccess(true);
                        state.setStatus(ChannelStatusEnum.IDLE);
                        state.setChannel(channel);
                        item.setStatus(ChannelStatusEnum.IDLE);
                        this.variable.getChannelQueue().offer(channel);
                    }
                } finally {
                    idleLock.unlock();
                }
                return state;
            case USE:
                if (channel == null) {
                    Channel newChannel = this.variable.getChannelQueue().poll();
                    if (newChannel != null) {
                        ChannelItem use_item = this.variable.getChannelMap().get(
                                newChannel.getId());
                        if (use_item != null) {
                            final Lock useLock = use_item.getItemLock();
                            if (useLock.tryLock(maxtime, TimeUnit.MILLISECONDS)) {
                                try {
                                    if (!(use_item.getStatus() == ChannelStatusEnum.IDLE)) {
                                        state.setStatus(use_item.getStatus());
                                        return state;
                                    } else {
                                        state.setChannel(newChannel);
                                        state.setStatus(ChannelStatusEnum.USE);
                                        state.setSuccess(true);
                                        use_item.setStatus(ChannelStatusEnum.USE);
                                        use_item.setUseTime(new Date());
                                    }
                                } finally {
                                    useLock.unlock();
                                }
                            }
                        } else {
                            // 这是不可能出现的～～
                            newChannel.unbind();
                            newChannel.close();
                        }
                    } else {
                        // 建立新连接
                        item = this.createChannel();
                        // 如果创建通道成功则增加入通道记录列表中
                        if (item != null) {
                            item.setUseTime(new Date());
                            item.setStatus(ChannelStatusEnum.USE);
                            ChannelItem tmpItem = this.variable.getChannelMap()
                                    .putIfAbsent(item.getChannel().getId(), item);
                            if (tmpItem != null) {
                                item = tmpItem;
                            }
                            state.setSuccess(true);
                            state.setChannel(item.getChannel());
                            state.setStatus(ChannelStatusEnum.USE);
                        } else {
                            state.setSuccess(false);
                            state.setStatus(ChannelStatusEnum.FULL);
                        }
                    }
                } else {
                    boolean rChannel = this.variable.getChannelQueue().remove(channel);
                    if (rChannel) {
                        item = this.variable.getChannelMap().get(channel.getId());
                        if (item == null) {
                            return state;
                        }
                        final Lock useLock = item.getItemLock();
                        if (useLock.tryLock(maxtime, TimeUnit.MILLISECONDS)) {
                            try {
                                if (!(item.getStatus() == ChannelStatusEnum.IDLE)) {
                                    state.setStatus(item.getStatus());
                                } else {
                                    item.setStatus(ChannelStatusEnum.USE);
                                    item.setUseTime(new Date());
                                    state.setStatus(ChannelStatusEnum.USE);
                                    state.setChannel(channel);
                                    state.setSuccess(true);
                                }
                            } finally {
                                useLock.unlock();
                            }
                        }
                    }
                }
                return state;
            case REUSE:
                if (channel != null) {
                    item = this.variable.getChannelMap().get(channel.getId());
                    if (item != null) {
                        final Lock reuseLock = item.getItemLock();
                        reuseLock.lock();
                        try {
                            if (item.getStatus() == ChannelStatusEnum.DISCARD) {
                                state.setStatus(ChannelStatusEnum.DISCARD);
                            } else {
                                item.setStatus(ChannelStatusEnum.USE);
                                item.setUseTime(new Date());
                                state.setStatus(ChannelStatusEnum.USE);
                                state.setChannel(channel);
                                state.setSuccess(true);
                            }
                        } finally {
                            reuseLock.unlock();
                        }
                    }
                }
                return state;
            case DISCARD:
                if (channel == null) {
                    return state;
                }
                this.variable.getChannelQueue().remove(channel);
                item = this.variable.getChannelMap().get(channel.getId());

                if (item == null) {
                    return state;
                }
                state.setChannel(channel);
                state.setSuccess(true);
                state.setStatus(ChannelStatusEnum.DISCARD);
                final Lock discardLock = item.getItemLock();
                discardLock.lock();
                try {
                    if (item.getStatus() != ChannelStatusEnum.DISCARD) {
                        item.setStatus(ChannelStatusEnum.DISCARD);
                        long nanoTime = TimeUnit.NANOSECONDS.convert(
                                this.variable.getDiscardChannelWaitTime(),
                                TimeUnit.MILLISECONDS);
                        // 把无效通道放入过期队列中，定期自动删除
                        this.variable
                                .getDiscardChannelQueue()
                                .put(new DelayObjectItem<>(
                                        new DiscardChannel(new Date(), channel),
                                        nanoTime));
                        int discardSize = this.variable.getDiscardSize()
                                .incrementAndGet();
                        // 通道废弃后检查是否需要创建连接
                        int length = this.variable.getMinIdle()
                                - (this.variable.getMaxActive()
                                - this.variable.getMaxActiveSemaphore()
                                .availablePermits() - discardSize);
                        if (length > 0) {
                            this.variable.getMinIdleQueue().offer(new Object());
                        }
                    }
                } finally {
                    discardLock.unlock();
                }
                return state;
            default:
                break;
        }
        return state;
    }

    public Channel changeChannel(Channel channel, long maxWait)
            throws Exception {
        if (channel == null) {
            return null;
        }
        ChannelState item = this.channelManager(channel, ChannelStatusEnum.USE, maxWait);
        return item.isSuccess() ? item.getChannel() : null;
    }

    public Channel getChannel() throws Exception {
        return this.getChannel(this.variable.getMaxWait());
    }

    /**
     * 获取一个有效通道
     *
     * @return
     */
    public Channel getChannel(long maxWait) throws Exception {
        Channel channel = null;
        ChannelState item;
        long comeInTime = System.currentTimeMillis();
        List<Channel> excludeList = new ArrayList<>();
        boolean isFull;
        TimeState timeState = new TimeState();
        timeState.setMaxWaitTime(maxWait);
        while (true) {
            item = this.channelManager(null, ChannelStatusEnum.USE,
                    timeState.getMaxWaitTime());// 获取链接
            this.getTimeState(timeState, comeInTime, maxWait);// 计算最大等待时间和是否运行超时
            if (!timeState.isTimeOut() && !item.isSuccess()) {
                Thread.sleep(1000);
                continue;
            }
            isFull = (item.getStatus() == ChannelStatusEnum.FULL);
            if (!isFull) {
                channel = item.getChannel();
            }

            // 查询是否是排除的通道
            if (excludeList.contains(channel)) {
                this.channelManager(channel, ChannelStatusEnum.IDLE, 0);
                this.getTimeState(timeState, comeInTime, maxWait);// 计算最大等待时间和是否运行超时
                if (!timeState.isTimeOut()) {
                    continue;
                }
            }
            this.getTimeState(timeState, comeInTime, maxWait);// 计算最大等待时间和是否运行超时
            if (!timeState.isTimeOut() && isFull) {
                LocalLock localLock = new LocalLock();

                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug(this.connectPoolName
                            + ".getChannel() no connection available, the current thread will be waiting for "
                            + timeState.getMaxWaitTime() + " ms.");
                }
                localLock.getLock().lock();
                try {
                    long nanoTime = TimeUnit.NANOSECONDS.convert(
                            timeState.getMaxWaitTime(), TimeUnit.MILLISECONDS);
                    this.variable.getWaitQueue()
                            .put(new DelayObjectItem<>(localLock,
                                    nanoTime));
                    //等待过期队列唤醒
                    localLock.getWaitCondition().await();
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug(this.connectPoolName
                                + ".getChannel() wait completed.");
                    }
                    if (localLock.isPassive()) {
                        // 表明是被动启动
                        channel = localLock.getChannel();
                        if (channel == null) {
                            continue;
                        }
                    } else {
                        // 表明是等待超时
                        timeState.setTimeOut(true);
                        continue;
                    }
                } catch (Exception e) {
                    LOGGER.error(
                            this.connectPoolName
                                    + ".getChannel() localLock wait Exception.",
                            e);
                } finally {
                    localLock.getLock().unlock();
                }
            }
            if (channel != null) {
                if (channel.isConnected() && channel.isOpen()) {
                    if (!channel.isWritable()) {
                        if (LOGGER.isDebugEnabled()) {
                            LOGGER.debug(this.connectPoolName
                                    + ".getChannel()  get channel is non-writeable .channel Id:"
                                    + channel.getId());
                        }
                        excludeList.add(channel);
                        this.channelManager(channel, ChannelStatusEnum.IDLE, 0);// 获取链接
                        this.getTimeState(timeState, comeInTime, maxWait);// 计算最大等待时间和是否运行超时
                        if (!timeState.isTimeOut()) {
                            continue;
                        } else {
                            channel = null;
                        }
                    }
                } else {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug(this.connectPoolName
                                + ".getChannel()  get channel is not connected.channel Id:"
                                + channel.getId());
                    }
                    if (!timeState.isTimeOut()) {
                        Thread.sleep(500);
                        continue;
                    } else {
                        channel = null;
                    }
                }
            }

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(this.connectPoolName
                        + ".getChannel()  return channel .channel Id:"
                        + (channel != null ? channel.getId() : null));
            }
            excludeList.clear();
            return channel;
        }
    }

    private TimeState getTimeState(TimeState timeState, long comeInTime,
                                   long maxWait) {
        long runTime, waitTime;
        runTime = System.currentTimeMillis() - comeInTime;
        waitTime = maxWait - runTime;
        timeState.setTimeOut((waitTime <= 0));
        timeState.setMaxWaitTime(waitTime);
        return timeState;

    }

    /**
     * 回收通道方法
     *
     * @param channel
     * @throws Throwable
     */
    public void releaseChannel(Channel channel) throws Exception {
        if (channel == null) {
            return;
        }
        if (!this.variable.getWaitQueue().isEmpty()) {
            DelayObjectItem<LocalLock> waitObj = this.variable.getWaitQueue().get();
            if (waitObj != null && waitObj.getItem() != null) {
                ChannelState state = this.channelManager(channel,
                        ChannelStatusEnum.REUSE, 0);
                if (state.isSuccess()) {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug(this.connectPoolName
                                + ".releaseChannel(...)  find wait request,notify this request,use channel id:"
                                + channel.getId());
                    }
                    LocalLock localLock = waitObj.getItem();
                    localLock.getLock().lock();
                    try {
                        localLock.setChannel(channel);
                        localLock.setPassive(true);
                        localLock.getWaitCondition().signal();
                    } finally {
                        localLock.getLock().unlock();
                    }
                } else {
                    if (state.getStatus() == null) {
                        if (LOGGER.isDebugEnabled()) {
                            LOGGER.debug(this.connectPoolName
                                    + ".releaseChannel(...) channelMap not found channel,channel id:"
                                    + channel.getId());
                        }
                        if (channel.isConnected() && channel.isOpen()) {
                            channel.close();
                        }
                    }
                }
            }
        }

        ChannelState state = this.channelManager(channel,
                ChannelStatusEnum.IDLE, 0);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(this.connectPoolName
                    + ".releaseChannel(...)  release channel "
                    + (state.isSuccess() ? "success" : "fail")
                    + " ,channel id:" + channel.getId());
        }
    }

    public void discardChannel(Channel channel) throws Exception {
        if (channel == null) {
            return;
        }
        ChannelState discardChannel = this.channelManager(channel,
                ChannelStatusEnum.DISCARD, 0);
        if (!discardChannel.isSuccess()) {
            if (discardChannel.getStatus() == null) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug(this.connectPoolName
                            + ".discardChannel(...) channelMap not found channel,channel id:"
                            + channel.getId());
                }
                if (channel.isConnected() && channel.isOpen()) {
                    channel.unbind();
                    channel.close();
                }
            }
            return;
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(this.connectPoolName
                    + ".recordChannel(...)  discard channel "
                    + (discardChannel.isSuccess() ? "success" : "fail")
                    + ",channel id:" + channel.getId());
        }
    }

    /**
     * 创建远程通道
     *
     * @return
     */
    protected ChannelItem createChannel() {
        if (this.variable.getMaxActiveSemaphore().tryAcquire()) {
            ChannelItem item = null;
            String[] urls = this.variable.getUrl().split(":");
            ChannelFuture future;
            future = bootstrap.connect(new InetSocketAddress(urls[0], Integer
                    .parseInt(urls[1])));
            future.awaitUninterruptibly();
            if (future.getChannel().isConnected()
                    && future.getChannel().isOpen()) {
                item = new ChannelItem();
                item.setChannel(future.getChannel());
                item.setStatus(ChannelStatusEnum.IDLE);
                item.setUseTime(new Date());
                LOGGER.info(this.connectPoolName
                        + ".createChannel() create channel  successed,channel id:"
                        + item.getChannel().getId());
                // 判断是否是强制关闭连接
                if (this.variable.isForceCloseChannel()) {
                    long nanoTime = TimeUnit.NANOSECONDS.convert(
                            this.variable.getForceCloseTimeMillis(),
                            TimeUnit.MILLISECONDS);
                    this.variable.getCloseChannelQueue().put(
                            new DelayObjectItem<ChannelItem>(item, nanoTime));
                }
            } else {
                LOGGER.error(this.connectPoolName
                        + ".createChannel() create channel failed.");
                this.variable.getMaxActiveSemaphore().release();
            }
            return item;
        } else {
            LOGGER.error(this.connectPoolName
                    + ".createChannel() failed, full pool.");
        }
        return null;
    }

    class LocalLock {
        public LocalLock() {
            this.lock = new ReentrantLock();
            this.waitCondition = lock.newCondition();
        }

        private Channel channel;
        private boolean passive;
        private final Lock lock;
        private final Condition waitCondition;

        public Channel getChannel() {
            return channel;
        }

        public void setChannel(Channel channel) {
            this.channel = channel;
        }

        public boolean isPassive() {
            return passive;
        }

        public void setPassive(boolean passive) {
            this.passive = passive;
        }

        public Lock getLock() {
            return lock;
        }

        public Condition getWaitCondition() {
            return waitCondition;
        }

    }

    class ChannelItem {
        public ChannelItem() {
            this.createTime = new Date();
        }

        private final Lock itemLock = new ReentrantLock();
        /**
         * 通道
         */
        private Channel channel;
        /**
         * 是否被使用
         */
        private ChannelStatusEnum status = ChannelStatusEnum.IDLE;

        /**
         * 使用时间
         */
        private Date useTime;
        /**
         * 创建时间
         */
        private final Date createTime;

        public Channel getChannel() {
            return channel;
        }

        public void setChannel(Channel channel) {
            this.channel = channel;
        }

        public Date getUseTime() {
            return useTime;
        }

        public void setUseTime(Date useTime) {
            this.useTime = useTime;
        }

        public ChannelStatusEnum getStatus() {
            return status;
        }

        public void setStatus(ChannelStatusEnum status) {
            this.status = status;
        }

        public Date getCreateTime() {
            return createTime;
        }

        public Lock getItemLock() {
            return itemLock;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("ChannelItem[channel=").append(channel.toString());
            builder.append(",createTime=").append(this.createTime);
            builder.append(",status=").append(this.status);
            builder.append(",useTime=").append(
                    useTime != null ? useTime.getTime() : null);
            builder.append("]");
            return builder.toString();
        }

    }

    class PoolVariable {
        PoolVariable(String poolName, String url, int initiaSize,
                     int maxActive, int maxIdle, int minIdle, int maxWait,
                     long discardChannelWaitTime, boolean forceCloseChannel,
                     long forceCloseTimeMillis, long checkIdleLinkMillis) {
            this.poolName = poolName;
            this.url = url;
            this.initiaSize = initiaSize;
            this.maxActive = maxActive;
            this.maxIdle = maxIdle;
            this.minIdle = minIdle;
            this.maxWait = maxWait;
            this.discardChannelWaitTime = discardChannelWaitTime;
            this.forceCloseChannel = forceCloseChannel;
            this.forceCloseTimeMillis = forceCloseTimeMillis;
            this.checkIdleLinkMillis = checkIdleLinkMillis;
            this.channelMap = new ConcurrentHashMap<>();
            this.channelQueue = new ConcurrentLinkedQueue<>();
            this.discardChannelQueue = new DelayQueue<>();
            this.waitQueue = new EmcfDelayQueue<>();
            this.maxActiveSemaphore = new Semaphore(maxActive);
            this.minIdleQueue = new ArrayBlockingQueue<>(this.minIdle);
            if (this.forceCloseChannel) {
                closeChannelQueue = new DelayQueue<>();
            } else {
                closeChannelQueue = null;
            }
        }

        /**
         * 通道汇总
         */
        private final ConcurrentMap<Integer, ChannelItem> channelMap;

        /**
         * 有效通道列表
         */
        private final Queue<Channel> channelQueue;

        /**
         * 回收通道队列
         */
        private final DelayQueue<DelayObjectItem<DiscardChannel>> discardChannelQueue;
        private final AtomicInteger discardSize = new AtomicInteger(0);
        private final BlockingQueue<Object> minIdleQueue;
        /**
         * 等待可用通道队列
         */
        private final EmcfDelayQueue<DelayObjectItem<LocalLock>> waitQueue;

        /**
         * 过期强制关闭队列
         */
        private final DelayQueue<DelayObjectItem<ChannelItem>> closeChannelQueue;

        /**
         * 初始化数量
         */
        private final int initiaSize;
        /**
         * 最大等待时间
         */
        private final int maxWait;
        /**
         * 最大活跃数量
         */
        private final int maxActive;

        private final Semaphore maxActiveSemaphore;
        /**
         * 最大空闲数量
         */
        private final int maxIdle;
        /**
         * 最小空闲数量
         */
        private final int minIdle;
        /**
         * 连接地址 192.168.0.1:8080
         */
        private final String url;
        /**
         * 连接池名称
         */
        private final String poolName;

        /**
         * 废弃队列最大等待时间
         */
        private final long discardChannelWaitTime;
        /**
         * 强制关闭连接
         */
        private final boolean forceCloseChannel;
        /**
         * 强制关闭连接时间
         */
        private final long forceCloseTimeMillis;
        /**
         * 补充连接线程运行时间毫秒
         *
         * @return
         */
        private final long checkIdleLinkMillis;

        public ConcurrentMap<Integer, ChannelItem> getChannelMap() {
            return channelMap;
        }

        public Queue<Channel> getChannelQueue() {
            return channelQueue;
        }

        public DelayQueue<DelayObjectItem<DiscardChannel>> getDiscardChannelQueue() {
            return discardChannelQueue;
        }

        public EmcfDelayQueue<DelayObjectItem<LocalLock>> getWaitQueue() {
            return waitQueue;
        }

        public int getInitiaSize() {
            return initiaSize;
        }

        public int getMaxWait() {
            return maxWait;
        }

        public int getMaxActive() {
            return maxActive;
        }

        public int getMaxIdle() {
            return maxIdle;
        }

        public int getMinIdle() {
            return minIdle;
        }

        public String getUrl() {
            return url;
        }

        public String getPoolName() {
            return poolName;
        }

        public Semaphore getMaxActiveSemaphore() {
            return maxActiveSemaphore;
        }

        public long getDiscardChannelWaitTime() {
            return discardChannelWaitTime;
        }


        public boolean isForceCloseChannel() {
            return forceCloseChannel;
        }

        public long getForceCloseTimeMillis() {
            return forceCloseTimeMillis;
        }

        public DelayQueue<DelayObjectItem<ChannelItem>> getCloseChannelQueue() {
            return closeChannelQueue;
        }

        public long getCheckIdleLinkMillis() {
            return checkIdleLinkMillis;
        }

        public AtomicInteger getDiscardSize() {
            return discardSize;
        }

        public BlockingQueue<Object> getMinIdleQueue() {
            return minIdleQueue;
        }

    }
}

/**
 * 废弃队列处理线程
 */
class DiscardChannelThread implements Runnable {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(DiscardChannelThread.class);
    private final ConnectPool.PoolVariable variable;

    public DiscardChannelThread(final ConnectPool.PoolVariable variable) {
        this.variable = variable;

    }

    @Override
    public void run() {
        LOGGER.info("DiscardChannelThread start...");
        for (; ; ) {
            try {
                DelayObjectItem<DiscardChannel> item = this.variable
                        .getDiscardChannelQueue().take();
                if (item != null && item.getItem() != null
                        && item.getItem().getChannel() != null) {
                    LOGGER.info(this.variable.getPoolName()
                            + " DiscardChannelThread discard channel,"
                            + item.getItem().toString());
                    Channel channel = item.getItem().getChannel();
                    // 这里没有加锁是因为通道已经废弃，所以不会使用
                    this.variable.getChannelMap().remove(channel.getId());
                    channel.unbind();// 发送解除绑定请求
                    channel.close().addListener(
                            new CloseChannelFutureListener(variable));
                    channel = null;
                }

            } catch (Exception e) {
                LOGGER.info("DiscardChannelThread Exception.", e);

            }
        }
    }
}

class IdleChannelListener implements Runnable {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(IdleChannelListener.class);
    private final ConnectPool.PoolVariable variable;
    private final ConnectPool pool;

    public IdleChannelListener(final ConnectPool pool,
                               final ConnectPool.PoolVariable variable) {
        this.variable = variable;
        this.pool = pool;
    }

    @Override
    public void run() {
        LOGGER.info("IdleChannelListener start...");
        Object needAdd;
        for (; ; ) {
            try {
                needAdd = this.variable.getMinIdleQueue().take();
                if (needAdd == null) {
                    continue;
                }
                ConnectPool.ChannelItem item = this.pool.createChannel();
                if (item != null) {
                    this.variable.getChannelMap().put(item.getChannel().getId(), item);
                    this.variable.getChannelQueue().offer(item.getChannel());
                }

            } catch (Exception e) {
                LOGGER.error("IdleChannelListener Exception.", e);
            }
        }
    }
}

class AsyncCreateChannelThread implements Runnable {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(AsyncCreateChannelThread.class);
    private final ConnectPool.PoolVariable variable;
    private final ConnectPool pool;

    public AsyncCreateChannelThread(final ConnectPool pool,
                                    final ConnectPool.PoolVariable variable) {
        this.variable = variable;
        this.pool = pool;
    }

    @Override
    public void run() {
        LOGGER.info("AsyncCreateChannelThread start...");
        final int maxActive = this.variable.getMaxActive();
        final Semaphore semaphore = this.variable.getMaxActiveSemaphore();
        final int minIdle = this.variable.getMinIdle();
        final long checkIdleLinkMillis = this.variable.getCheckIdleLinkMillis();
        int length;
        if (checkIdleLinkMillis <= 0) {
            return;
        }
        for (; ; ) {
            try {
                Thread.sleep(checkIdleLinkMillis);
                length = minIdle - (maxActive - semaphore.availablePermits());
                if (length > 0) {
                    for (int i = 0; i < length; i++) {
                        ConnectPool.ChannelItem item = this.pool.createChannel();
                        if (item != null) {

                            this.variable.getChannelMap().put(item.getChannel().getId(), item);
                            this.variable.getChannelQueue().offer(item.getChannel());
                        }
                    }
                }
            } catch (Exception e) {
                LOGGER.error("AsyncCreateChannelThread Exception.", e);

            }
        }
    }
}

class ForceCloseChannelThread implements Runnable {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(ForceCloseChannelThread.class);
    private final ConnectPool.PoolVariable variable;
    private final IConnectPool pool;

    public ForceCloseChannelThread(IConnectPool pool,
                                   final ConnectPool.PoolVariable variable) {
        this.variable = variable;
        this.pool = pool;
    }

    @Override
    public void run() {
        LOGGER.info("ForceCloseChannelThread start...");
        DelayObjectItem<ConnectPool.ChannelItem> item;
        for (; ; ) {
            try {
                item = this.variable.getCloseChannelQueue().take();
                if (item != null) {
                    LOGGER.info("ForceCloseChannelThread force channel:"
                            + item.getItem().toString());
                    this.pool.discardChannel(item.getItem().getChannel());
                }
            } catch (Exception e) {
                LOGGER.error("ForceCloseChannelThread Exception.", e);
            }
        }
    }
}

class WaitChannelThread implements Runnable {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(WaitChannelThread.class);
    private final ConnectPool.PoolVariable variable;

    public WaitChannelThread(final ConnectPool.PoolVariable variable) {
        this.variable = variable;
    }

    @Override
    public void run() {
        LOGGER.info("WaitChannelThread start...");
        DelayObjectItem<ConnectPool.LocalLock> item;
        ConnectPool.LocalLock localLock;
        for (; ; ) {
            try {
                item = this.variable.getWaitQueue().take();
                if (item != null) {
                    localLock = item.getItem();
                    if (localLock != null) {
                        localLock.getLock().lock();
                        try {
                            localLock.setPassive(false);
                            localLock.getWaitCondition().signal();
                        } finally {
                            localLock.getLock().unlock();
                        }
                    }
                }
            } catch (Exception e) {
                LOGGER.error("WaitChannelThread Exception.", e);
            }
        }
    }
}

class CloseChannelFutureListener implements ChannelFutureListener {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(CloseChannelFutureListener.class);
    private final ConnectPool.PoolVariable variable;

    public CloseChannelFutureListener(final ConnectPool.PoolVariable variable) {
        this.variable = variable;
    }

    @Override
    public void operationComplete(ChannelFuture future) throws Exception {
        if (future.isSuccess()) {
            this.variable.getMaxActiveSemaphore().release();
            this.variable.getDiscardSize().decrementAndGet();
            LOGGER.info("close channel successfully,channelId:"
                    + future.getChannel().getId());
        } else {
            LOGGER.error("close channel failure#channelId:"
                    + future.getChannel().getId(), future.getCause());
        }
    }
}
