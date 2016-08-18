package com.servitization.commons.socket.vo;

import com.servitization.commons.socket.enums.ChannelStatusEnum;
import org.jboss.netty.channel.Channel;

public class ChannelState {
    private Channel channel;
    private ChannelStatusEnum status;
    private boolean success;

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public ChannelStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ChannelStatusEnum status) {
        this.status = status;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("ChannelState[channel=,").append(channel != null ? channel.toString() : null)
                .append(",status=").append(status != null ? status.toString() : null).append(",success=").append(success)
                .toString();
    }
}
