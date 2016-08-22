package com.servitization.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonLogger {
    private static Logger logger = LoggerFactory.getLogger("proxy_logger");

    public static Logger getLogger() {
        return logger;
    }
}
