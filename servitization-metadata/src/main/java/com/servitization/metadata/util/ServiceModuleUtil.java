package com.servitization.metadata.util;

import com.servitization.metadata.define.proxy.ServiceModule;

import java.util.HashSet;
import java.util.Set;

public class ServiceModuleUtil {

    private static final String CONVERT = "yes";

    private static final String NO_CONVERT = "no";

    public static boolean isResConvertServiceModule(ServiceModule sm) {
        if (sm.getConvert() == null || sm.getConvert().equals("") || sm.getConvert().equals(NO_CONVERT)) {
            return false;
        } else if (sm.getConvert().equals(CONVERT)) {
            return true;
        }
        return false;
    }
}
