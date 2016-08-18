package com.servitization.defence.service.helper.vo;

public class DefenceStrategy {
    private String path;
    private int timeInterval;
    private long maxCount;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    /**
     * unit second
     *
     * @return
     */
    public int getTimeInterval() {
        return timeInterval;
    }

    /**
     * unit second
     *
     * @return
     */
    public void setTimeInterval(int timeInterval) {
        this.timeInterval = timeInterval;
    }

    public long getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(long maxCount) {
        this.maxCount = maxCount;
    }
}
