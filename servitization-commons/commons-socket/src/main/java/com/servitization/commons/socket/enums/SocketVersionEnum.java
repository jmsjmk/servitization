package com.servitization.commons.socket.enums;

public enum SocketVersionEnum {
    INITIAL_VERSION(1000, "第一版");

    SocketVersionEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private int code;
    private String desc;

    public int code() {
        return code;
    }

    public String desc() {
        return desc;
    }

    public static SocketVersionEnum valueOfEnum(int code) {
        for (SocketVersionEnum ce : values()) {
            if (ce.code() == code) {
                return ce;
            }
        }
        return SocketVersionEnum.INITIAL_VERSION;
    }
}
