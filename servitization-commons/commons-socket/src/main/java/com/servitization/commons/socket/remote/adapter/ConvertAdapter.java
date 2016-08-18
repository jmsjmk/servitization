package com.servitization.commons.socket.remote.adapter;

public interface ConvertAdapter {
    Object convert(byte[] result) throws Exception;
}
