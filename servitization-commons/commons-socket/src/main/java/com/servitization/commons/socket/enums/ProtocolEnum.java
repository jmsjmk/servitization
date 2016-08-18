package com.servitization.commons.socket.enums;

public enum ProtocolEnum {
    STRING(0, "String"), OBJECT(1, "jsonObject"), GOOGLE(2, "google protocol buffer"), THRIFT(3, "thrift");

    ProtocolEnum(int code, String desc) {
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

    public static ProtocolEnum valueOfEnum(int code) {
        for (ProtocolEnum ce : values()) {
            if (ce.code() == code) {
                return ce;
            }
        }
        return ProtocolEnum.OBJECT;
    }
}
