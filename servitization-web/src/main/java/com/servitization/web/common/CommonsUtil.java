package com.servitization.web.common;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CommonsUtil {

    public static Properties loadProperties(String fileName) {
        if (fileName == null || fileName.trim().length() == 0) {
            return null;
        }
        Properties p = new Properties();
        try {
            InputStream is = CommonsUtil.class.getClassLoader().getResourceAsStream(fileName);
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
