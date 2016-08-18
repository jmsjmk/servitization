package com.servitization.commons.permission.service.impl;

import com.servitization.commons.permission.service.IConfigProvider;
import com.servitization.commons.permission.util.ConfigReaderUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * 文件读取配置文件
 */
@Component
public class PropertiesReader implements IConfigProvider, InitializingBean {

    @Value("config/config.properties")
    private String _filePath;

    private Properties _properties;

    public PropertiesReader() {
    }

    @Override
    public String Get(String key) {

        if (_properties != null && _properties != null && _properties.containsKey(key)) {
            return _properties.get(key) == null ? null : _properties.get(key).toString();
        }
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        _properties = ConfigReaderUtil.load(_filePath);
    }

    public String get_filePath() {
        return _filePath;
    }

    public void set_filePath(String _filePath) {
        this._filePath = _filePath;
    }

}
