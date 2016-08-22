package com.servitization.metadata.common;

public enum StandardHeaderEnum {

    // 要全部小写，header name从request取出来都只能是小写
    ACCEPT_ENCODING("accept-encoding"),

    // response
    CONTENT_ENCODING("content-encoding");

    StandardHeaderEnum(String headerName) {
        this.headerName = headerName;
    }

    private String headerName;

    public String headerName() {
        return headerName;
    }
}
