package com.servitization.commons.socket.vo;

public class TimeState {

    private boolean timeOut;
    private long maxWaitTime;

    public boolean isTimeOut() {
        return timeOut;
    }

    public void setTimeOut(boolean timeOut) {
        this.timeOut = timeOut;
    }

    public long getMaxWaitTime() {
        return maxWaitTime;
    }

    public void setMaxWaitTime(long maxWaitTime) {
        this.maxWaitTime = maxWaitTime;
    }
}
