package com.servitization.commons.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.MDC;

public abstract class TraceUtils {

    public static final String TRACE_ID_KEY = "traceId";

    public static void beginTrace(String traceId) {
        MDC.put(TRACE_ID_KEY, traceId);
    }

    public static String getTrace() {
        Object obj = MDC.get(TRACE_ID_KEY);
        if (obj != null) {
            return obj.toString();
        }
        return StringUtils.EMPTY;
    }

    /**
     * 结束一次Trace, 清除traceId.
     */
    public static void endTrace() {
        MDC.remove(TRACE_ID_KEY);
    }

}
