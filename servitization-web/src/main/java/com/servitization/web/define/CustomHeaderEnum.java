package com.servitization.web.define;

public enum CustomHeaderEnum {

    // 要全部小写，headername从request取出来都只能是小写

    CHANNELID("channelid"), DEVICEID("deviceid"), VERSION("version"), CLIENTTYPE(
            "clienttype"), ISCOMPRESS("iscompress"), SESSIONTOKEN(
            "sessiontoken"), CHECKCODE("checkcode"), CLIENTIP("clientip"), TRACEID(
            "traceid");

    CustomHeaderEnum(String headerName) {
        this.headerName = headerName;
    }

    private String headerName;

    public String headerName() {
        return headerName;
    }

}
