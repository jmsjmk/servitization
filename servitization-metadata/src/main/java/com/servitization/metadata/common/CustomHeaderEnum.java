package com.servitization.metadata.common;

public enum CustomHeaderEnum {

    // 要全部小写，headername从request取出来都只能是小写
    CHANNELID("channelid"),
    DEVICEID("deviceid"),
    COMPRESS("compress"),
    SESSIONTOKEN("sessiontoken"),
    CHECKCODE("checkcode"),
    CLIENTIP("clientip"),
    TRACEID("traceid"),
    OSVERSION("osversion"),
    GUID("guid"),
    APPID("appid"),
    SESSIONKEY("sessionkey"),
    ACCEPTENCODING("accept-encoding"),
    ENCRYPTRESP("encryptresp"),
    // -----------------------CYP-------------------
    COOKIE("cookie"),
    APP("app"),
    IMEI("imei"),
    ONLINEID("onlineid"),
    BUSINESSID("businessid"),
    CLIENTID("clientid"),
    CLIENTTYPE("clienttype"),
    OS("os"),
    SV("sv"),
    VERSION("version"),
    S("s"),
    N("n"),
    P("p"),
    TID("tid"),
    MEMBERCODE("membercode"),
    IP("ip"),
    E("e"),
    Z("z"),
    LL("ll"),
    AT("at"),
    OW("ow"),
    SC("sc"),
    AL("al"),
    PM("pm"),
    DN("dn");

    CustomHeaderEnum(String headerName) {
        this.headerName = headerName;
    }

    private String headerName;

    public String headerName() {
        return headerName;
    }

}
