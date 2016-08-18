package com.servitization.commons.log;

public class LogFactory {
    public static final Logger logger = new LoggerImpl();

    public static Logger getLogger() {
        return logger;
    }
}
