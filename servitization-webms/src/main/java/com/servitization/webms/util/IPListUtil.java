package com.servitization.webms.util;

import java.util.HashSet;
import java.util.Set;

public class IPListUtil {

    /**
     * IP分割器
     *
     * @param ips
     * @return null：空内容 Set：合法IP集合 exception： 格式不合法
     */
    public static Set<String> ipSpliter(String ips) throws Exception {
        Set<String> rst = new HashSet<>();
        if (ips == null || ips.length() == 0)
            return null;
        String[] ips_array = ips.split("\n");
        int line = 0;
        for (String ip : ips_array) {
            line++;
            ip = ip.trim();
            if (ip.length() > 0) {
                if (!isIp(ip))
                    throw new RuntimeException("第" + line + "行格式不合法");
                rst.add(ip);
            }
        }
        if (rst.size() > 0)
            return rst;
        else
            return null;
    }

    private static boolean isIp(String ip) {
        String[] bits = ip.split("\\.");
        if (bits.length != 4)
            return false;
        for (String bit : bits) {
            if (bit.length() == 0 || bit.length() > 3)
                return false;
            for (int i = 0; i < bit.length(); i++) {
                char c = bit.charAt(i);
                if (c < '0' || c > '9')
                    return false;
            }
        }
        return true;
    }

    public static String format(Set<String> ips) {
        StringBuffer sb = new StringBuffer();
        boolean isFirst = true;
        for (String ip : ips) {
            if (!isFirst) {
                sb.append("\n").append(ip);
            } else {
                sb.append(ip);
                isFirst = false;
            }
        }
        return sb.toString();
    }

    public static Set<String> unformat(String ips) {
        Set<String> ips_set = new HashSet<>();
        String[] ips_array = ips.split("\n");
        for (String ip : ips_array) {
            ips_set.add(ip);
        }
        return ips_set;
    }
}
