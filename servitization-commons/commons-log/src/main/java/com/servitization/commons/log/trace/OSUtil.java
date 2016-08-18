package com.servitization.commons.log.trace;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.InetAddress;
import java.util.Properties;

public class OSUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(OSUtil.class);
    private static String localIp;
    private static String localName;

    static {
        if (isWindows()) {
            InetAddress addr = null;
            try {
                addr = InetAddress.getLocalHost();
                localIp = addr.getHostAddress().toString();//获得本机IP　　
                localName = addr.getHostName().toString();//获得本机名称
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            localIp = getLinuxLocalIp();
            localName = getLinuxName();
        }

    }

    public static String linuxLocalIp() {
        return localIp;
    }

    public static String linuxLocalName() {
        return localName;
    }

    /**
     * 获取linux服务器ip地址
     *
     * @return
     */
    private static String getLinuxLocalIp() {
        Process p = null;
        String line = null;
        try {
            p = Runtime
                    .getRuntime()
                    .exec(new String[]{
                            "/bin/sh",
                            "-c",
                            "/sbin/ifconfig | grep 'inet addr:'| grep -v '127.0.0.1' | cut -d: -f2 | awk '{ print $1}'"});
            InputStream fis = p.getInputStream();
            LineNumberReader input = new LineNumberReader(
                    new InputStreamReader(fis));
            line = input.readLine();

            p.waitFor();
            return line;
        } catch (IOException e) {

            LOGGER.error("get local ip error", e);
            return "";
        } catch (InterruptedException e) {
            LOGGER.error("get local ip error", e);
            return "";
        }


    }

    /**
     * 获取linux机器名名称
     *
     * @return
     */
    private static String getLinuxName() {
        String line = null;
        try {
            Process p = Runtime.getRuntime().exec(
                    new String[]{"/bin/sh", "-c", "uname -n"});

            InputStream fis = p.getInputStream();
            LineNumberReader input = new LineNumberReader(
                    new InputStreamReader(fis));
            line = input.readLine();

            p.waitFor();
            return line;
        } catch (IOException e) {
            LOGGER.error("get linux name error", e);
            return StringUtils.EMPTY;
        } catch (InterruptedException e) {
            LOGGER.error("get linux name error", e);
            return StringUtils.EMPTY;
        }

    }


    private static boolean isWindows() {
        Properties prop = System.getProperties();
        String os = StringUtils.lowerCase(prop.getProperty("os.name"));
        return os.startsWith("win");
    }

}
