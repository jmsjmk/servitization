package com.servitization.commons.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 资源文件类
 */
public class PropertiesUtil {

    /**
     * 只加载一次文件
     *
     * @param fileName 文件名
     * @return
     */
    public static Properties load(String fileName) {
        if (fileName == null || fileName.trim().length() == 0) {
            return null;
        }
        Properties p = new Properties();
        try {
            InputStream is = PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName);
            if (is != null) {
                p.load(is);
                is.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return p;
    }
}
