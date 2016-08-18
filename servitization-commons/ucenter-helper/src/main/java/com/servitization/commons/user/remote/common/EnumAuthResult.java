package com.servitization.commons.user.remote.common;

public enum EnumAuthResult {
    SUCCESS("session_1000", ""),
    TimeOut("session_1001", "会话已过期，请重新登录"),
    NoPermission("session_1003", "没有权限访问该接口，请申请"),
    NotValid("session_1004", "token unvalid");
    private String code;
    private String message;

    EnumAuthResult(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
