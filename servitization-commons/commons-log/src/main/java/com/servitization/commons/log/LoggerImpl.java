package com.servitization.commons.log;


public class LoggerImpl implements Logger {

    private static final org.apache.log4j.Logger BIG_LOG = org.apache.log4j.Logger.getLogger("cyp_commons_log");

    @Override
    public void info(Log message) {
        if (BIG_LOG == null) {
            return;
        }
        BIG_LOG.info(message);
    }
}
