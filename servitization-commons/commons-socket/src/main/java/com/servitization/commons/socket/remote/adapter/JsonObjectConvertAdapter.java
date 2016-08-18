package com.servitization.commons.socket.remote.adapter;

import com.alibaba.fastjson.JSON;

/**
 * json对象适配器
 */
public class JsonObjectConvertAdapter implements ConvertAdapter {
    private Class<?> clazz;

    public JsonObjectConvertAdapter(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Object convert(byte[] result) throws Exception {
        return JSON.parseObject(result, clazz);
    }
}
