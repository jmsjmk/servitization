package com.servitization.commons.user.remote.common;

public enum ClientType {

    Wap(0, "Wap"), Iphone(1, "Iphone"), J2me(2, "J2me"), Android(3, "Android"), Ipad(4, "Ipad"), AndroidPad(5, "AndroidPad"),
    WindowsPhone(6, "WindowsPhone"), Html5Wap(7, "Html5Wap"), WindowsPcPad(8, "WindowsPcPad"), Unknown(-1, "Unknown");

    ClientType(int clientType, String platform) {
        this.clientType = clientType;
        this.platform = platform;
    }

    public static ClientType getClientTypeEnum(int clientType) {
        for (ClientType ce : values()) {
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
