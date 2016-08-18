package com.servitization.commons.log;

import com.servitization.commons.log.trace.OSUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;

public class ErrorLogLayout extends PatternLayout {
    static LEThrowableInfoWapper lew = new LEThrowableInfoWapper();
    static final String nullString = "null";
    static String logEnd = null;

    @Override
    public String format(LoggingEvent event) {
        StringBuffer sBuffer = new StringBuffer();
        sBuffer.append(super.format(event)).append('\t');
        //拼接信息异常信息
        if (event.getThrowableInformation() != null && event.getThrowableInformation().getThrowable() != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            event.getThrowableInformation().getThrowable().printStackTrace(pw);
            pw.close();
            String exceptionString = sw.toString().replaceAll("\n", "#");
            sBuffer.append(event.getThrowableInformation().getThrowable().getClass().getName()).append('\t');
            sBuffer.append(replaceTabAndEnter(exceptionString)).append('\t');
            try {
                lew.getThrowableInfoField().set(event, null);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            sBuffer.append(nullString).append('\t');
            sBuffer.append(nullString).append('\t');
        }
        sBuffer.append(logEnd).append('\n');

        return sBuffer.toString();
    }

    private static String replaceTabAndEnter(String str) {
        if (str != null && str.length() > 0) {
            if (str.indexOf("\n") > -1 || str.indexOf("\t") > -1) {
                return str.replaceAll("\n|\t|\r", " ");
            }
        }
        return StringUtils.isBlank(str) ? nullString : str;
    }

    public void setBusinessLine(String businessLine) {
        GlobalConstant.setBusinessLine(businessLine);
    }

    public void setAppName(String appName) {
        GlobalConstant.setAppName(appName);
        logEnd = String.format("%s\t%s\t%s\t%s.%s",
                OSUtil.linuxLocalName(),
                OSUtil.linuxLocalIp(),
                GlobalConstant.getBusinessLine(),
                GlobalConstant.getBusinessLine(), GlobalConstant.getAppName());
        System.out.println(String.format("ErrorLogLayout.logEnd:{%s}", logEnd));
    }

    static class LEThrowableInfoWapper {
        Field f;

        protected LEThrowableInfoWapper() {
            try {
                f = LoggingEvent.class.getDeclaredField("throwableInfo");
                f.setAccessible(true);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        Field getThrowableInfoField() {
            return f;
        }
    }
}
