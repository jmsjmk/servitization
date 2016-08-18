package com.servitization.proxy.log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.servitization.commons.log.Log;
import com.servitization.commons.log.trace.*;
import com.servitization.commons.util.DateUtil;
import com.servitization.commons.util.TraceUtils;
import com.servitization.embedder.immobile.ImmobileRequest;
import com.servitization.embedder.immobile.ImmobileResponse;
import com.servitization.metadata.common.CustomHeaderEnum;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.text.MessageFormat;
import java.util.*;

public class ServitizationLogUtil {

    private static final Logger LOGGER = Logger.getLogger("servitization_log");

    private static final Logger BIGLOG = Logger.getLogger("biglog");

    private static final String BUSINESS_LINE = "servitization";

    private static final String USER_LOG_TYPE = "servitization_filter";

    private static final String LOG_TYPE = "011";

    /**
     * 写日志
     *
     * @param obj
     * @param appName
     * @param response
     */
    public static void writeBigLog(ServitizationLogObj obj, String appName, ImmobileResponse response) {
        Map<String, Object> map = new HashMap<>();
        Log log = new Log();
        Date date = new Date();
        log.setLogTime(String.valueOf(DateUtil.getFormatDate(date, DateUtil.FORMAT_ALL_MM)));
        log.setElapsedTime(String
                .valueOf(System.currentTimeMillis() - obj.getStartTime()));
        log.setTraceId(obj.getSpan().getTraceId());
        log.setSpan(obj.getSpan().toSpanString());
        log.setAppName(appName);
        log.setBusinessLine(BUSINESS_LINE);
        log.setLogType(LOG_TYPE);
        log.setUserLogType(USER_LOG_TYPE);
        log.setServerName(OSUtil.linuxLocalName());
        log.setServerIp(OSUtil.linuxLocalIp());
        log.setRequestHeader(obj.getRequestHeader());
        log.setServiceName(obj.getServiceName());
        log.setRequestBody(obj.getRequestBody());
        log.setLogTimeStamp(System.currentTimeMillis());
        log.setImei(obj.getImei());
        writeBusinesException(log, response);

        map.put("data", log);
        map.put("rowKey", "imei,logTimeStamp");
        map.put("table", "big_log");
        String logInfo = JSON.toJSONString(map);
        BIGLOG.info(logInfo);
    }

    /**
     * 写日志
     *
     * @param obj
     * @param appName
     * @param response
     */
    public static void writeServitizationLog(ServitizationLogObj obj,
                                             String appName, ImmobileResponse response) {
        Log log = new Log();
        Date date = new Date();
        log.setLogTime(String
                .valueOf(DateUtil.getFormatDate(date, DateUtil.FORMAT_ALL_MM)));
        log.setElapsedTime(String
                .valueOf(System.currentTimeMillis() - obj.getStartTime()));
        log.setTraceId(obj.getSpan().getTraceId());
        log.setSpan(obj.getSpan().toSpanString());
        log.setAppName(appName);
        log.setMethod(obj.getMethod());
        log.setBusinessLine(BUSINESS_LINE);
        log.setLogType(LOG_TYPE);
        log.setUserLogType(USER_LOG_TYPE);
        log.setServerName(OSUtil.linuxLocalName());
        log.setServerIp(OSUtil.linuxLocalIp());
        log.setRequestHeader(obj.getRequestHeader());
        log.setServiceName(obj.getServiceName());
        log.setRequestBody(obj.getRequestBody());
        writeBusinesException(log, response);
        LOGGER.info(log);
        stopServitizationLog(obj.getTracer());
    }

    private static void writeBusinesException(Log log,
                                              ImmobileResponse response) {
        try {
            if (response == null)
                return;
            byte[] bytes = response.getContent();
            if (bytes == null || bytes.length == 0)
                return;
            String result = new String(bytes, "utf-8");
            log.setResponseBody(result);
            JSONObject jsonObj = JSON.parseObject(result);
            if (jsonObj.containsKey("isError")
                    && jsonObj.getBooleanValue("isError")) {
                log.setResponseCode("1");
                log.setBusinessErrorCode("1");
            }
        } catch (Exception e) {

        }
    }

    public static void stopServitizationLog(Tracer tracer) {
        TraceUtils.endTrace();
        tracer.endRootTrace();
    }

    /**
     * 获取请求参数
     *
     * @param request
     * @return
     */
    private static String getRequestParam(ImmobileRequest request) {
        StringBuilder param = new StringBuilder();
        Map<String, String[]> params = request.getParameterMap();

        if (params != null && params.size() > 0) {
            String[] reqParams;
            int i = 0;
            for (String key : params.keySet()) {
                reqParams = params.get(key);
                if (i++ != 0) {
                    param.append(",");
                }
                param.append(key);
                param.append("=");
                if (reqParams != null && reqParams.length > 0) {
                    param.append(Arrays.toString(reqParams));
                }
            }
        }
        return param.toString();
    }

    // 得到请求头的信息
    private static String getRequestHeader(ImmobileRequest request) {
        Enumeration<String> headNames = request.getHeaderNames();
        JSONObject jsonHeader = new JSONObject();
        if (headNames != null && headNames.hasMoreElements()) {
            CustomHeaderEnum[] ches = CustomHeaderEnum.values();
            CustomHeaderEnum che;
            String headerValue;
            for (int i = 0; i < ches.length; i++) {
                che = ches[i];
                headerValue = request.getHeader(che.headerName());
                if (StringUtils.isNotBlank(headerValue)) {
                    jsonHeader.put(che.headerName(), headerValue);
                }
            }
        }
        jsonHeader.put("CustomerClientIP",
                request.getHeader(CustomHeaderEnum.CLIENTIP.headerName()));
        jsonHeader.put(CustomHeaderEnum.CLIENTIP.headerName(),
                request.getRemoteIP());

        return jsonHeader.toString();
    }

    /**
     * 创建spanner 和tracer 对象
     *
     * @param request
     * @return
     */
    public static ServitizationLogObj getServitizationLogObj(
            ImmobileRequest request) {
        String traceId = request
                .getHeader(CustomHeaderEnum.TRACEID.headerName());
        if (StringUtils.isBlank(traceId)) {
            traceId = NotifyId.getInstance().getUniqIDHashString();
        } else {
            if (traceId.length() > 64) {
                traceId = new String(traceId.substring(0, 64));
            }
        }
        String imei = request.getHeader(CustomHeaderEnum.IMEI.headerName());
        // 建立请求的跟踪id
        Tracer tracer = Tracer.getTracer();
        Spanner span = tracer.startTrace(traceId, traceId + "-" + "1", "1",
                SpanTypeEnum.RPC_SERVER_RECEIVED, true);
        TraceUtils.beginTrace(span.getTraceId());

        ServitizationLogObj obj = new ServitizationLogObj();
        obj.setMethod(request.getMethod());
        obj.setSpan(span);
        obj.setTracer(tracer);
        obj.setRequestBody(JSON.toJSONString(getRequestParam(request)));
        obj.setRequestHeader(getRequestHeader(request));
        obj.setServerName(OSUtil.linuxLocalName());
        obj.setServerIp(OSUtil.linuxLocalIp());
        obj.setStartTime(System.currentTimeMillis());
        obj.setServiceName(request.getServiceName());
        obj.setImei(imei);
        return obj;
    }

    public static String getServitizationLogMsg(ImmobileRequest request,
                                                long startTime, String code) {
        String msg = "serverIP[{0}] serverUri[{1}] method[{2}] headers[{3}] "
                + "params[{4}] time[{5}]ms code[{6}]";

        msg = MessageFormat.format(msg, OSUtil.linuxLocalIp(),
                request.getServiceName(), request.getMethod(),
                getRequestHeader(request), getRequestParam(request),
                String.valueOf(System.currentTimeMillis() - startTime), code);
        return msg;
    }
}
