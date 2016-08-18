package com.servitization.commons.permission.service;

public interface IConfigProvider {
    /**
     * 根据key去配置文件中读取转发的配置地址
     */
    String Get(String key);
}
