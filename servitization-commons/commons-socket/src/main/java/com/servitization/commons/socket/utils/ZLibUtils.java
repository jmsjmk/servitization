package com.servitization.commons.socket.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * ZLib压缩工具
 */
public class ZLibUtils {

    /**
     * 压缩
     *
     * @param data 待压缩数据
     * @return byte[] 压缩后的数据
     * @throws Exception
     */
    public static byte[] compress(byte[] data) throws Exception {
        byte[] output = new byte[0];
        Deflater compresser = new Deflater();
        compresser.reset();
        compresser.setInput(data);
        compresser.finish();
        ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);
        try {
            byte[] buf = new byte[1024];
            while (!compresser.finished()) {
                int i = compresser.deflate(buf);
                bos.write(buf, 0, i);
            }
            output = bos.toByteArray();
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                bos.close();
            } catch (IOException e) {
                throw e;
            }
        }
        compresser.end();
        return output;
    }


    /**
     * 解压缩
     *
     * @param data 待压缩的数据
     * @return byte[] 解压缩后的数据
     * @throws Exception
     */
    public static byte[] decompress(byte[] data) throws Exception {
        byte[] output = new byte[0];

        Inflater decompresser = new Inflater();
        decompresser.reset();
        decompresser.setInput(data);

        ByteArrayOutputStream o = new ByteArrayOutputStream(data.length);
        try {
            byte[] buf = new byte[1024];
            while (!decompresser.finished()) {
                int i = decompresser.inflate(buf);
                o.write(buf, 0, i);
            }
            output = o.toByteArray();
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                o.close();
            } catch (IOException e) {
                throw e;
            }
        }
        decompresser.end();
        return output;
    }
}