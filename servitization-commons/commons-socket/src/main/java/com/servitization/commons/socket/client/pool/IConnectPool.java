package com.servitization.commons.socket.client.pool;

import org.jboss.netty.channel.Channel;

public interface IConnectPool {
    Channel getChannel() throws Exception;

    Channel getChannel(long maxWait) throws Exception;

    void releaseChannel(Channel channel) throws Exception;

    void discardChannel(Channel channel) throws Exception;

    Channel changeChannel(Channel channel, long maxWait) throws Exception;
}
