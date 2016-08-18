package com.servitization.webms.entity;

/***
 * 在http请求aos时，返回的数据格式
 */
public class AosReturnDataEntitry {

    private int code;
    private String msg;
    private AosDataEntitry data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public AosDataEntitry getData() {
        return data;
    }

    public void setData(AosDataEntitry data) {
        this.data = data;
    }

}
