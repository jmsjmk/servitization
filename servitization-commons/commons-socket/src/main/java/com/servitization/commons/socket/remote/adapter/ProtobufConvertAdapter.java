package com.servitization.commons.socket.remote.adapter;

import java.lang.reflect.Method;

public class ProtobufConvertAdapter implements ConvertAdapter {
    private Class<?> clazz;

    public ProtobufConvertAdapter(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Object convert(byte[] result) throws Exception {
        Method m = clazz.getMethod("parseFrom", byte[].class);
        return m.invoke(clazz, result);
    }
}