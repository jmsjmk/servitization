package com.servitization.commons.permission.util;

import com.servitization.commons.business.agent.entity.CustomizeParameterEntity;
import com.servitization.commons.permission.service.impl.PropertiesReader;
import org.apache.commons.lang3.StringUtils;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class UrlUtil {

    private UrlUtil() {

    }

    /**
     * ---组装url的参数---
     */
    public static String getUrlParam(String key, final Map<String, String> paramMap, final CustomizeParameterEntity entity,
                                     final PropertiesReader propertiesreader) {
        String url = propertiesreader.Get(key);
        if (null == entity || null == paramMap || paramMap.size() <= 0 || StringUtils.isBlank(url)) {
            throw new RuntimeException("url util error");
        }

        StringBuilder param = new StringBuilder();
        Iterator<Entry<String, String>> itera = paramMap.entrySet().iterator();
        int size = paramMap.size();
        while (itera.hasNext()) {
            size--;
            Entry<String, String> entry = itera.next();
            if (0 == size) {
                param.append(entry.getKey()).append("=").append(entry.getValue());
            } else {
                param.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }
        entity.setUrl(url);
        return param.toString();
    }
}
