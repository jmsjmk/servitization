package com.servitization.metadata.define.proxy;

public enum ThresholdType {
    BYPERCENTAGE("BYPERCENTAGE"),
    BYUSER("BYUSER");

    private ThresholdType(String str) {
        des = str;
    }

    private String des;

    public String typeName() {
        return des;
    }
}
