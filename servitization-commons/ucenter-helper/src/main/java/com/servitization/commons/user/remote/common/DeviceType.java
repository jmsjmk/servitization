package com.servitization.commons.user.remote.common;

public enum DeviceType {

    Wap(0, "Wap"), Iphone(1, "Iphone"), Android(2, "Android"), IPad(3, "IPad"), WindowsPhone(4, "WindowsPhone"), AndroidPad(5, "AndroidPad"),
    WindowsPad(6, "WindowsPad"), PC(7, "PC"), Robot(8, "Robot"), Unknown(-1, "Unknown");

    DeviceType(int clientType, String platform) {
        this.clientType = clientType;
        this.platform = platform;
    }

    public static DeviceType getClientTypeEnum(int clientType) {
        for (DeviceType ce : values()) {
            if (ce.getClientType() == clientType) {
                return ce;
            }
        }
        return Unknown;
    }

    /**
     * 平台 vid
     */
    private int clientType;

    /**
     * 平台名称
     */
    private String platform;

    public int getClientType() {
        return clientType;
    }

    public void setClientType(int clientType) {
        this.clientType = clientType;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

}
