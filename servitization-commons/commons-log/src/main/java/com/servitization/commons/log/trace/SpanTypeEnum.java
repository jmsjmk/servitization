package com.servitization.commons.log.trace;

public enum SpanTypeEnum {
    RPC_CLIENT_SEND("cs"),
    RPC_CLIENT_RECEIVED("cr"),
    RPC_SERVER_RECEIVED("sr"),
    RPC_SERVER_SEND("ss"),
    ASYN_START("as"),
    ASYN_END("ae");

    SpanTypeEnum(String type) {
        this.type = type;
    }

    private String type;

    public String type() {
        return type;
    }
}
