package com.servitization.commons.permission.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 加载读取配置文件
 */
public class ConfigReaderUtil {
    private static Logger logger = LoggerFactory.getLogger(ConfigReaderUtil.class);

    public static Properties load(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            return null;
        }
        Properties properties = new Properties();

        InputStream is = null;
        try {
            is = ConfigReaderUtil.class.getClassLoader().getResourceAsStream(fileName);
            properties.load(is);

        } catch (FileNotFoundException e) {
            logger.error("loader  file " + fileName + " not found");
        } catch (IOException e) {
            logger.error("loader file " + fileName + " ioException");
        } finally {
            if (null != is) {
                try {
                    is.close();
                    is = null;
                } catch (IOException e) {
                    logger.error("close file  " + fileName + " failure");
                }
            }
        }
        return properties;
    }
}
