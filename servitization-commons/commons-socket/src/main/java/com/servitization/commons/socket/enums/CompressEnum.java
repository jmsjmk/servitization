package com.servitization.commons.socket.enums;

public enum CompressEnum {

    NON(0, "不压缩"), ZLIB(1, "zlib压缩");

    CompressEnum(int code, String desc) {
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

    public static CompressEnum valueOfEnum(int code) {
        for (CompressEnum ce : values()) {
            if (ce.code() == code) {
                return ce;
            }
        }
        return CompressEnum.NON;
    }
}
