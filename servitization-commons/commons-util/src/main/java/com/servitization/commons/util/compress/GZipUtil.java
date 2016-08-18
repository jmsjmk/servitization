package com.servitization.commons.util.compress;

import java.io.*;
import java.util.zip.GZIPOutputStream;

public class GZipUtil {
    public static final int BUFFER = 1024;

    /**
     * 数据压缩
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] compress(byte[] data) throws Exception {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // 压缩
        compress(bais, baos);
        byte[] output = baos.toByteArray();
        baos.flush();
        baos.close();
        bais.close();
        return output;
    }

    /**
     * 数据压缩
     *
     * @param is
     * @param os
     * @throws Exception
     * @throws Exception
     */
    public static void compress(InputStream is, OutputStream os) throws Exception {

        GZIPOutputStream gos = null;
        int count;
        byte data[] = new byte[BUFFER];
        try {
            gos = new GZIPOutputStream(os);

            while ((count = is.read(data, 0, BUFFER)) != -1) {
                gos.write(data, 0, count);
            }
            gos.finish();
            gos.flush();
        } catch (IOException e) {
            throw e;
        } finally {
            if (gos != null) {
                gos.close();

            }
        }
    }
}
