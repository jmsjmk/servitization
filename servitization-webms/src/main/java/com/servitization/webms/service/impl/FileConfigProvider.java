package com.servitization.webms.service.impl;

import com.servitization.commons.util.PropertiesUtil;
import com.servitization.webms.service.IConfigProvider;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * 文件读取配置文件
 */
@Component
public class FileConfigProvider implements IConfigProvider, InitializingBean {

    @Value("config/config.properties")
    private String _filePath;

    private Properties _properties;

    public FileConfigProvider() {
    }

    public FileConfigProvider(String filePath) {
        _filePath = filePath;
        _properties = PropertiesUtil.load(_filePath);
    }

    @Override
    public String Get(String key) {
        if (_properties != null && _properties.containsKey(key)) {
            return _properties.get(key) == null ? null : _properties.get(key).toString();
        }
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        _properties = PropertiesUtil.load(_filePath);
    }

    public String get_filePath() {
        return _filePath;
    }

    public void set_filePath(String _filePath) {
        this._filePath = _filePath;
    }
}
