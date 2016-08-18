package com.servitization.webms.service;

public interface IConfigProvider {
    /**
     * 根据key值读取配置文件，如果配置项未存在，返回null
     */
    String Get(String key);
}
