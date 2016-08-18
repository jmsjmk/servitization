package com.servitization.commons.util.compress;

import org.apache.commons.lang3.StringUtils;

public class CompressUtil {

    public static byte[] compress(byte[] data, CompressType compressType) throws Exception {
        if (data == null || data.length < 1 || compressType == null) {
            return data;
        }
        switch (compressType) {
            case LZSS:
                return LzssUtil.compress(data);

            case ZLIB:
                return ZLibUtils.compress(data);

            case GZP:
                return GZipUtil.compress(data);
            default:

                break;
        }
        return data;
    }

    public static byte[] compress(String data, CompressType compressType) throws Exception {
        return compress(data.getBytes(), compressType);
    }

    public static byte[] compress(String data, String compressType) throws Exception {
        return compress(data.getBytes(), CompressType.valueOfEnum(compressType));
    }


    public static enum CompressType {
        LZSS("lzss"), ZLIB("zlib"), GZP("gzip");

        CompressType(String type) {
            this.type = type;
        }

        private String type;

        public String type() {
            return type;
        }

        public static CompressType valueOfEnum(String type) {
            if (StringUtils.isBlank(type)) {
                return null;
            }
            for (CompressType ct : values()) {
                if (StringUtils.equalsIgnoreCase(ct.type(), type)) {
                    return ct;
                }
            }
            return null;
        }
    }
}
