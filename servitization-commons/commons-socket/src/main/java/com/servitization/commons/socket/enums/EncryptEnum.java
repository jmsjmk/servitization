package com.servitization.commons.socket.enums;

public enum EncryptEnum {
    NON((byte) 0, "不加密");

    EncryptEnum(byte code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private byte code;
    private String desc;

    public byte code() {
        return code;
    }

    public String desc() {
        return desc;
    }

    public static EncryptEnum valueOfEnum(int code) {
        for (EncryptEnum ce : values()) {
            if (ce.code() == code) {
                return ce;
            }
        }
        return EncryptEnum.NON;
    }
}
