package com.servitization.commons.socket.enums;

import org.apache.commons.lang3.StringUtils;

public enum EmcfSerivceEnum {

    HEARBEAT("emcf/heartbeat");

    EmcfSerivceEnum(String serivce) {
        this.serivce = serivce;
    }

    private final String serivce;

    public String serivce() {
        return serivce;
    }

    public static boolean hasService(String service) {
        for (EmcfSerivceEnum ese : values()) {
            if (StringUtils.equals(ese.serivce(), service)) {
                return true;
            }
        }
        return false;
    }
}
