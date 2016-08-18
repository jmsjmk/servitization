package com.servitization.commons.business.agent.log;

import com.servitization.commons.log.GlobalConstant;
import com.servitization.commons.log.Log;
import com.servitization.commons.log.LogFactory;
import com.servitization.commons.log.Logger;
import com.servitization.commons.log.trace.OSUtil;
import com.servitization.commons.log.trace.Spanner;
import com.servitization.commons.log.trace.Tracer;
import com.servitization.commons.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.MDC;

import java.util.Date;

public class RpcLogUtil {

    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger("URLMonitorLogger");
    private static final String nullChar = "null";

    public static void writeLog(RpcLogObject rpcLogObject) {
        StringBuilder builder = new StringBuilder();
        Object traceId = MDC.get("marking");
        Date date = new Date();

        builder.append("null\t");//id
        builder.append(traceId == null ? nullChar : traceId);//traceid
        builder.append("\tnull\tOnlineWeb\tmobile\t");//\t sessionid \t productline \t appname \t

        builder.append(rpcLogObject.getMethodName());//methodname
        builder.append("\t");
        //系统信息返回码
        builder.append(StringUtils.isNotBlank(rpcLogObject.getSysError()) ? rpcLogObject.getSysError() : 0);//responsecode
        //服务器名称
        builder.append("\t");
        builder.append(OSUtil.linuxLocalName());//servername

        builder.append("\t");
        //服务器IP
        builder.append(OSUtil.linuxLocalIp());//serverip
        builder.append("\t");
        //耗时
        builder.append(date.getTime() - rpcLogObject.getStartTime());//elapsedtime
        builder.append("\t");
        builder.append(DateUtil.formatDate(date));//logtime
        builder.append("\t");
        if (LOG.isDebugEnabled()) {
            builder.append(rpcLogObject.getRequest() == null ? nullChar : rpcLogObject.getRequest());//request
            builder.append("\t");
            builder.append(rpcLogObject.getResult() == null ? nullChar : rpcLogObject.getResult());//response
        } else {
            builder.append(nullChar);
            builder.append("\t");
            builder.append(nullChar);//response
        }
        builder.append("\t");
        builder.append(nullChar);//fromurl
        builder.append("\t");
        builder.append(nullChar);//referurl
        builder.append("\t");
        //bussinesserrorcode
        //业务信息返回码，非0表示异常  extend1
        builder.append(StringUtils.isNotBlank(rpcLogObject.getBizError()) ? rpcLogObject.getBizError() : 0);
        builder.append("\t");
        //服务地址
        builder.append(nullChar);//extend2
        builder.append("\t");
        //错误信息 extend3
        builder.append(nullChar);
        LOG.info(builder.toString());
    }

    private static final Logger CYP_LOGGER = LogFactory.getLogger();

    public static void writeLogBefore(RpcLogObject rpcLogObject) {
        Log log = new Log();
        Date date = new Date();
        Tracer tracer = Tracer.getTracer();
        Spanner span = tracer.startRpcTrace();
        log.setLogTime(DateUtil.getFormatDate(date, DateUtil.FORMAT_ALL_MM));
        log.setTraceId(span.getTraceId());
        log.setSpan(span.toSpanString());
        log.setServerName(OSUtil.linuxLocalName());
        log.setServerIp(OSUtil.linuxLocalIp());
        log.setBusinessLine(GlobalConstant.getBusinessLine());
        log.setLogType("001");
        log.setUserLogType("rpc.request");
        log.setAppName(GlobalConstant.getAppName());
        log.setServiceName(rpcLogObject.getMethodName() + ".rpc");
        if (LOG.isDebugEnabled()) {
            log.setRequestBody(rpcLogObject.getRequest() == null ? nullChar : rpcLogObject.getRequest().toString());
        }
        CYP_LOGGER.info(log);
    }

    public static void writeLogAfter(RpcLogObject rpcLogObject) {
        Spanner span = Tracer.getTracer().endRpcTrace();
        Log log = new Log();
        Date date = new Date();
        log.setLogTime(DateUtil.getFormatDate(date, DateUtil.FORMAT_ALL_MM));
        log.setTraceId(span.getTraceId());
        log.setSpan(span.toSpanString());
        log.setBusinessLine(GlobalConstant.getBusinessLine());
        log.setLogType("011");
        log.setServerName(OSUtil.linuxLocalName());
        log.setServerIp(OSUtil.linuxLocalIp());
        log.setRequestHeader(rpcLogObject.getHeader() == null ? nullChar : rpcLogObject.getHeader());
        log.setUserLogType("rpc.result");
        log.setAppName(GlobalConstant.getAppName());
        log.setServiceName(rpcLogObject.getMethodName() + ".rpc");
        log.setResponseCode(String.valueOf(StringUtils.isNotBlank(rpcLogObject.getSysError()) ? rpcLogObject.getSysError() : 0));
        log.setBusinessErrorCode(String.valueOf(StringUtils.isNotBlank(rpcLogObject.getBizError()) ? rpcLogObject.getBizError() : 0));
        log.setException(rpcLogObject.getThrowable());
        log.setExceptionMsg(rpcLogObject.getErrorMsg());
        log.setElapsedTime(String.valueOf(date.getTime() - rpcLogObject.getStartTime()));

        if (LOG.isDebugEnabled()) {
            log.setRequestBody(rpcLogObject.getRequest() == null ? nullChar : rpcLogObject.getRequest().toString());
            log.setResponseBody(rpcLogObject.getResult() == null ? nullChar : rpcLogObject.getResult().toString());
        }
        CYP_LOGGER.info(log);
    }
}
