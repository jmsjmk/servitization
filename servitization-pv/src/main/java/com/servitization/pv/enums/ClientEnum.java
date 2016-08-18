package com.servitization.pv.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 枚举类按照以下方式写
 */
public enum ClientEnum {

    Wap(0, "Wap"), Iphone(1, "Iphone"), J2me(2, "J2me"), Android(3, "Android"), Ipad(4, "Ipad"),
    AndroidPad(5, "AndroidPad"), WindowsPhone(6, "WindowsPhone"), Html5Wap(7, "Html5Wap"), WindowsPcPad(8, "WindowsPcPad"), Unknown(-1, "Unknown");

    ClientEnum(int clientType, String platform) {
        this.clientType = clientType;
        this.platform = platform;
    }

    public static ClientEnum clientTypeOfEnum(int clientType) {

        for (ClientEnum ce : values()) {
            if (ce.clientType() == clientType) {
                return ce;
            }
        }
        return Unknown;
    }

    public static ClientEnum platformOfEnum(String platform) {
        for (ClientEnum ce : values()) {
            if (StringUtils.equalsIgnoreCase(platform, ce.platform())) {
                return ce;
            }
        }
        return Unknown;
    }

    private int clientType;        //平台vid
    private String platform;    //平台名称

    public int clientType() {
        return clientType;
    }

    public String platform() {
        return platform;
    }
}
