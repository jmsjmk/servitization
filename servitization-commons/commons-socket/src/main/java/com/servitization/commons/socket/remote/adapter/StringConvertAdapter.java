package com.servitization.commons.socket.remote.adapter;


public class StringConvertAdapter implements ConvertAdapter {

    @Override
    public Object convert(byte[] result) throws Exception {
        return new String(result);
    }
}