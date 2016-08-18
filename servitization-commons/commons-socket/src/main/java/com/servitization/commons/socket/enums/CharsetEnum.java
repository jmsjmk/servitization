package com.servitization.commons.socket.enums;

public enum CharsetEnum {
    UTF8(0, "UTF-8"), GBK(1, "GBK"),
    GB2312(2, "GB2312"), ISO88591(3, "ISO8859-1");

    CharsetEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    private int code;
    private String value;

    public int code() {
        return code;
    }

    public String value() {
        return value;
    }

    public static CharsetEnum valueOfEnum(int code) {
        for (CharsetEnum ce : values()) {
            if (ce.code() == code) {
                return ce;
            }
        }
        return CharsetEnum.UTF8;
    }
}
