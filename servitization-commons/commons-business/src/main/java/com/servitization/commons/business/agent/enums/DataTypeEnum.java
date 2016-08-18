package com.servitization.commons.business.agent.enums;

public enum DataTypeEnum {
    STRING(0, "String"), JSON(1, "JSON"), PROTOTYPE(2, "Prototype"), MAP(3,
            "Map");
    private int code;
    private String name;

    DataTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
