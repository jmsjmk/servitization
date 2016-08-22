package com.servitization.embedder.communication;

import java.net.InetAddress;

/**
 * 获取本机名,IP
 */
public class IPProvider {
    static {
        InetAddress address;
        try {
            address = InetAddress.getLocalHost();
            ip = address.getHostAddress();
            hostname = address.getHostName();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String ip;

    public static String hostname;
}
