package com.servitization.commons.socket.vo;

import org.jboss.netty.channel.Channel;

import java.util.Date;

public class DiscardChannel {

    public DiscardChannel(Date discardTime, Channel channel) {
        this.discardTime = discardTime;
        this.channel = channel;

    }

    private Date discardTime;
    private Channel channel;

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Date getDiscardTime() {
        return discardTime;
    }

    public void setDiscardTime(Date discardTime) {
        this.discardTime = discardTime;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(DiscardChannel.class.getSimpleName());
        builder.append("[discardTime=");
        builder.append(discardTime);
        builder.append(",channel=");
        builder.append(channel.toString());
        builder.append("]");
        return builder.toString();
    }
}
