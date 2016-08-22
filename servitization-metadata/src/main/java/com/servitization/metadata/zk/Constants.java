package com.servitization.metadata.zk;

import com.servitization.metadata.common.CommonsUtil;

public class Constants {

    /**
     * ZK URL
     */
    public final static String zk_url = CommonsUtil.CONFIG_PROVIDAR.getProperty("zk.url");

    /**
     * 过滤的URL
     */
    public final static String exclude_url = CommonsUtil.CONFIG_PROVIDAR.getProperty("exclude_url");

    public final static String zk_username = CommonsUtil.CONFIG_PROVIDAR.getProperty("zk.username");

    public final static String zk_password = CommonsUtil.CONFIG_PROVIDAR.getProperty("zk.password");

    public static final String root = "/mobileapi";

    public static final String trunk = "/servitization";

    // **********************************启动过程用************************************
    public static final String boot = root + trunk + "/boot";

    public static final String boot_meta = "meta";

    public static final String boot_version = "version";

    public static final String ACK_NOTFOUND = "ACK_NOTFOUND";

    // **********************************报告status过程用************************************
    public static final String status = root + trunk + "/status";

    // **********************************push过程用************************************
    public static final String push = root + trunk + "/push";

    public static final String push_meta = "meta";

    public static final String push_version = "version";

    public static final String push_ack = "ack";

    public static final byte[] NONE = "NONE".getBytes();
}
