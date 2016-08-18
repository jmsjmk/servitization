package com.servitization.embedder.immobile.impl;

import com.servitization.embedder.immobile.ImmobileResponse;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImmobileResponseHttpImpl implements ImmobileResponse {

    private ByteArrayOutputStream byteArray = new ByteArrayOutputStream(1024);

    private BufferedServletOutputStream outputStream = new BufferedServletOutputStream();

    private HttpServletResponse originalResponse = null;

    public ImmobileResponseHttpImpl(HttpServletResponse response) {
        originalResponse = response;
    }

    public ServletOutputStream getOutputStream() throws IOException {
        return outputStream;
    }

    public byte[] getContent() {
        return byteArray.toByteArray();
    }

    public void resetContent() {
        byteArray.reset();
    }

    public void flushContent() throws IOException {
        // add by jiamingku 响应头默认设置成为json格式
        originalResponse.setContentType("application/json;charset=UTF-8");
        if (originalResponse != null)
            byteArray.writeTo(originalResponse.getOutputStream());
    }

    public void setCharacterEncoding(String charset) {
        if (originalResponse != null)
            originalResponse.setCharacterEncoding(charset);

    }

    @Override
    public void setHeader() {
        if (originalResponse != null) {
            originalResponse.setHeader("Access-Control-Allow-Origin", "*");
            originalResponse.setHeader("Access-Control-Allow-Credentials", "true");
        }
    }

    class BufferedServletOutputStream extends ServletOutputStream {

        @Override
        public void write(int b) throws IOException {
            byteArray.write(b);
        }

        @Override
        public void write(byte b[], int off, int len) {
            byteArray.write(b, off, len);
        }

    }

}
