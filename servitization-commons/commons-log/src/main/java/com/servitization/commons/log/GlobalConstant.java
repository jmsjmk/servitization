package com.servitization.commons.log;

public class GlobalConstant {

    private static String businessLine;
    private static String appName;

    public static String getBusinessLine() {
        return businessLine;
    }

    public synchronized static void setBusinessLine(String businessLine) {
        if (GlobalConstant.businessLine == null) {
            GlobalConstant.businessLine = businessLine;
        }
    }

    public static String getAppName() {
        return appName;
    }

    public synchronized static void setAppName(String appName) {
        if (GlobalConstant.appName == null) {
            GlobalConstant.appName = appName;
        }
    }
}
