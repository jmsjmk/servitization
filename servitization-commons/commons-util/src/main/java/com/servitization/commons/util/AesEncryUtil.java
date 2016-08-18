package com.servitization.commons.util;

import com.servitization.commons.util.crypto.BufferedBlockCipher;
import com.servitization.commons.util.crypto.DataLengthException;
import com.servitization.commons.util.crypto.InvalidCipherTextException;
import com.servitization.commons.util.crypto.engines.AESFastEngine;
import com.servitization.commons.util.crypto.modes.CBCBlockCipher;
import com.servitization.commons.util.crypto.paddings.PaddedBufferedBlockCipher;
import com.servitization.commons.util.crypto.params.KeyParameter;
import com.servitization.commons.util.crypto.params.ParametersWithIV;
import com.servitization.commons.util.util.encoders.Hex;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.math.BigInteger;
import java.net.URLEncoder;

/**
 * AES加密工具
 * 兼容.net版本
 */
public class AesEncryUtil {
    public final static byte[] ENCRYPT_CIPHER = {0x31, 0x32, 0x33, 0x34, 0x35,
            0x36, 0x37, 0x38, 0x39, 0x31, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36};

    public final static byte[] ENCRYPT_IV = {0x0, 0x0, 0x0, 0x0, 0x0, 0x0,
            0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0};

    public final static String encryptAndEncoding(String text, byte[] cipher,
                                                  byte[] IV) {
        try {
            BufferedBlockCipher engine = new PaddedBufferedBlockCipher(
                    new CBCBlockCipher(new AESFastEngine()));
            engine.init(true,
                    new ParametersWithIV(new KeyParameter(cipher), IV));
            byte[] content = text.getBytes("utf-8");
            byte[] enc = new byte[engine.getOutputSize(content.length)];
            int size1 = engine.processBytes(content, 0, content.length, enc, 0);
            int size2 = engine.doFinal(enc, size1);
            byte[] encryptedContent = new byte[size1 + size2];
            System.arraycopy(enc, 0, encryptedContent, 0, encryptedContent.length);
            return Base64.encode(encryptedContent);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * key为秘钥
     *
     * @param text
     * @param key
     * @return
     */
    public final static String encryptByKey(String text, String key) {
        try {
            key = convertTo16Key(key);
            byte[] cipher = key.getBytes();
            BufferedBlockCipher engine = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESFastEngine()));
            engine.init(true, new ParametersWithIV(new KeyParameter(cipher), ENCRYPT_IV));
            byte[] content = text.getBytes("utf-8");
            byte[] enc = new byte[engine.getOutputSize(content.length)];
            int size1 = engine.processBytes(content, 0, content.length, enc, 0);
            int size2 = engine.doFinal(enc, size1);
            byte[] encryptedContent = new byte[size1 + size2];
            System.arraycopy(enc, 0, encryptedContent, 0, encryptedContent.length);
            return Base64.encode(encryptedContent);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public final static String decrypt(String content, byte[] encCipher,
                                       byte[] encIV) {
        try {
            BufferedBlockCipher engine = new PaddedBufferedBlockCipher(
                    new CBCBlockCipher(new AESFastEngine()));
            engine.init(false, new ParametersWithIV(
                    new KeyParameter(encCipher), encIV));
            byte[] encryptedContent = Hex.decode(content.getBytes());
            byte[] dec = new byte[engine.getOutputSize(encryptedContent.length)];
            int size1 = engine.processBytes(encryptedContent, 0,
                    encryptedContent.length, dec, 0);
            int size2 = engine.doFinal(dec, size1);
            byte[] decryptedContent = new byte[size1 + size2];
            System.arraycopy(dec, 0, decryptedContent, 0,
                    decryptedContent.length);
            return new String(decryptedContent, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public final static String decrypt(byte[] content, byte[] encCipher,
                                       byte[] encIV) throws DataLengthException, IllegalStateException, InvalidCipherTextException, UnsupportedEncodingException {

        BufferedBlockCipher engine = new PaddedBufferedBlockCipher(
                new CBCBlockCipher(new AESFastEngine()));
        engine.init(false, new ParametersWithIV(
                new KeyParameter(encCipher), encIV));
        byte[] encryptedContent = content;
        byte[] dec = new byte[engine.getOutputSize(encryptedContent.length)];
        int size1 = engine.processBytes(encryptedContent, 0,
                encryptedContent.length, dec, 0);
        int size2 = engine.doFinal(dec, size1);
        byte[] decryptedContent = new byte[size1 + size2];
        System.arraycopy(dec, 0, decryptedContent, 0,
                decryptedContent.length);
        return new String(decryptedContent, "utf-8");

    }

    public final static String decodingAndDecrypt(String text) {
        try {
            byte[] decoded = Base64.decode(text);
            return decrypt(decoded, ENCRYPT_CIPHER, ENCRYPT_IV);
        } catch (Exception e) {
        }
        return null;
    }

    public final static String decryptByKey(String text, String key) throws Exception {
        byte[] decoded = Base64.decode(text);
        key = convertTo16Key(key);
        return decrypt(decoded, key.getBytes(), ENCRYPT_IV);
    }

    public static String getHexString(byte[] b) throws Exception {
        String result = "";
        for (int i = 0; i < b.length; i++) {
            result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
        }
        return result;
    }

    public static byte[] getByteArray(String hexString) {
        return new BigInteger(hexString, 16).toByteArray();
    }

    private static String convertTo16Key(String entryKey) {
        if (StringUtils.isNotBlank(entryKey)) {
            if (entryKey.length() == 16) {
                return entryKey;
            }
            if (entryKey.length() > 16) {
                return entryKey.subSequence(0, 16).toString();
            }
            if (entryKey.length() < 16) {
                return convet2Substi16Byte(entryKey);
            }
        }
        return null;
    }

    private static String convet2Substi16Byte(String key) {
        StringBuffer keyBuffer = new StringBuffer(key);
        for (int i = 0; i < 16 - key.length(); i++) {
            keyBuffer.append("0");
        }
        return keyBuffer.toString();
    }


    /**
     * 源文件通过加密运算写入到目标文件
     *
     * @param sourcePath
     * @param toPath
     */
    public static void writeSource2DstFileByLines(String sourcePath, String toPath, String reqMethod, String key) {
        if (StringUtils.isBlank(key)) {
            key = "1234567890123456";
        }
        File sourceFile = new File(sourcePath);
        FileWriter fileWriter = null;
        BufferedReader reader = null;
        BufferedWriter writer = null;
        String tempString = null;
        try {
            reader = new BufferedReader(new FileReader(sourceFile));
            fileWriter = new FileWriter(toPath);
            writer = new BufferedWriter(fileWriter);
            /**
             * 一次读入一行，直到读入null为文件结束
             */
            while ((tempString = reader.readLine()) != null) {
                writer.write(convert2AesEncryText(tempString, reqMethod, key));
                writer.newLine();
            }
            writer.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                    writer.close();
                } catch (Exception e) {
                }
            }
        }
    }

    private static String convert2AesEncryText(String sourceStr, String reqMethod, String key) {
        String encryptKeyText = encryptByKey(sourceStr, key);
        if ("GET".equalsIgnoreCase(reqMethod)) {
            try {
                return URLEncoder.encode(encryptKeyText, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return StringUtils.EMPTY;
            }
        }
        return encryptKeyText;
    }

    public static void main(String[] args) throws Exception {
        String text = "mzKcPM5O5CAFg2aVJ8gxUm+LTQJ2ywMtotR463XCx5w=";
//		String key = "ET20RIxGCta3btIg";
        String key = "gxBWKMbAPKLhXh6u";
        String str = AesEncryUtil.decryptByKey(text, key);
        System.out.println("str--------:" + str);
    }
}
