package com.servitization.embedder.immobile;

import java.util.Enumeration;
import java.util.Map;

/**
 * 请求过程中，不变的request对象 可写，改变内容
 */
public interface ImmobileRequest {

    /**
     * 获取请求方法
     *
     * @return
     */
    String getMethod();

    /**
     * 获取指定参数
     *
     * @param name
     * @return
     */
    String getParameter(String name);

    /**
     * 获取所有参数
     *
     * @return
     */
    Map<String, String[]> getParameterMap();

    /**
     * 获取所有参数名
     *
     * @return
     */
    Enumeration<String> getParameterNames();

    /**
     * 设置一个参数
     *
     * @param key
     * @param value
     */
    void setParameter(String key, String value);

    /**
     * 获取一个头字段
     *
     * @param name
     * @return
     */
    String getHeader(String name);

    /**
     * 获取所有头字段名
     *
     * @return
     */
    Enumeration<String> getHeaderNames();

    /**
     * 设置一个头字段
     *
     * @param key
     * @param value
     */
    void setHeader(String key, String value);

    /**
     * 获取客户端IP 1、对于H5请求，获取到的是H5机器IP 2、对于信任的代理，获取到的是xf转发过来的IP
     * 3、其他获取到的是真实的客户IP或其代理IP
     *
     * @return
     */
    String getRemoteIP();

    /**
     * 获取请求的服务名
     *
     * @return
     */
    String getServiceName();
}
