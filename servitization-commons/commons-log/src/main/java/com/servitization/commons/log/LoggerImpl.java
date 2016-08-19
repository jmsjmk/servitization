package com.servitization.commons.log;


public class LoggerImpl implements Logger {

    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger("commons_log");

    @Override
    public void info(Log message) {
        if (LOGGER == null) {
            return;
        }
        LOGGER.info(message);
    }
}
