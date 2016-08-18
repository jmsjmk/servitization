package com.servitization.pv.enums;

import org.apache.commons.lang3.math.NumberUtils;

/**
 * 设备的版本号 UnKnown = 0,IPhone = 1,Android = 2, IPad =
 * 3,WindowsPhone = 4, AndroidPad = 5,WindowsPad = 6,PC=7,Robot=8
 */
public enum DeviceType {

    IPhone(1, "IPhone"), Android(2, "Android"), Ipad(3, "Ipad"), WindowsPhone(4, "WindowsPhone"), AndroidPad(5,
            "AndroidPad"), WindowsPad(6, "WindowsPad"), PC(6, "PC"), Robot(8, "Robot"), UnKnown(0, "UnKnown");

    DeviceType(int DeviceType, String platform) {
        this.DeviceType = DeviceType;
        this.platform = platform;
    }

    public static DeviceType getDeviceType(int DeviceType) {

        for (DeviceType ce : values()) {
            if (ce.getDeviceType() == DeviceType) {
                return ce;
            }
        }
        return UnKnown;
    }

    public static DeviceType getDeviceTypeByPlatform(String platform) {
        for (DeviceType ce : values()) {
            if (ce.getPlatform().equalsIgnoreCase(platform)) {
                return ce;
            }
        }
        return UnKnown;
    }

    /**
     * 适配客户端传递的DeviceType 统一成字符串的数字
     *
     * @param name
     * @return
     */
    public static String convert2DeviceType(String name) {
        if (NumberUtils.isDigits(name)) {
            return name;
        }
        return String.valueOf(getDeviceTypeByPlatform(name).getDeviceType());
    }

    private int DeviceType; // 客户端类型
    private String platform; // 客户端名称

    public int getDeviceType() {
        return DeviceType;
    }

    public void setDeviceType(int DeviceType) {
        this.DeviceType = DeviceType;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
}