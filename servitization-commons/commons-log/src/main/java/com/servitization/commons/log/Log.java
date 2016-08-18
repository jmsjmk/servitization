package com.servitization.commons.log;

import org.apache.commons.lang3.StringUtils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * <p> logversion			  日志版本号（默认版本1.0）	Y
 * <p> logtime				  日志记录时间（格式：yyyy-MM-dd HH:mm:ss SSS）	Y
 * <p> traceId				  跟踪ID（用于跟踪用户的一次请求流程）	Y
 * <p> BusinessLine			  用于区分各业务线，主要日志加工时对不同得业务线进行个性化处理（枚举：mobile,web,）	Y
 * <p> logtype				  日志类型（详情见说明1）	Y
 * <p> servername			  服务器名	Y
 * <p> serverip				  服务器IP	Y
 * <p> UserLogType			 （用户自定义日志使用数字代表，此项可用于对日志的个性化处理的区分）	（建议填写）
 * <p> sessionid			  用户id，无线设备id(可用于分析用户行为使用)	（建议填写）
 * <p> Cookieid	              Client机器id	(记录接口响应的日志必须输出)
 * <p> Appname	              业务线分支名称
 * <p> ServiceName	          调用服务接口的名称（可以使地址，action名称）	(记录接口响应的日志必须输出)
 * <p> elapsedtime	          耗时、毫秒数	(记录接口响应的日志必须输出)
 * <p> Request header	      用户http请求的头信息内容（可自定义输出内容，必须json格式）	(记录接口响应的日志必须输出)
 * <p> Request body	          用户http请求的请求内容（必须json格式）（可以用于记录调用方法的参数）	(记录接口响应的日志必须输出)
 * <p> responseCode	          接口响应结果（0代表成功，非0代表失败 	(记录接口响应的日志必须输出)
 * <p> BusinessErrorCode	  如果有业务异常可以填写	(记录接口响应的日志必须输出)
 * <p> Response body		  返回结果（一次接口请求的结果数据，或者方法调用的放回内容）
 * <p> Hadoop content		  用于记录hadoop补充信息各业务先自定义内容要求使用#分隔，详情见说明2
 * <p> exception			  异常的堆栈信息	系统异常时填写
 * <p> exceptionmsg			  异常的说明	系统异常时填写
 * <p> extend1				  扩展字段1(建议json传送)
 * <p> extend2				  扩展字段2
 * <p>
 * <p> 说明1：
 * <p> 		现在位运算只支持3位（1代表是、0代表否），
 * <p> 右数第一位为代表计算进日志系统（mongodb中保存时间14天）
 * <p> 右数第二位为代表计算进入checklist
 * <p> 右数第三位为代表计算hadoop日志 的pv和uv日志
 * <p>
 * <p> 说明2：
 * <p> 		在请求中不能完全给出hadoop日志需要的信息的时候，就需要填写“Hadoop content”这个字段来补充内容，
 * 我们会通过“BusinessLine”字段来区分各业务系统的日志
 */
public class Log {
    private static final String TAB = "\t";
    private static final String ENTER = "\n";
    private static final String NULL_STR = "null";

    /**
     * 日志版本号（默认版本1.0）
     */
    //	private String logVersion;

    /**
     * 日志记录时间（格式：yyyy-MM-dd HH:mm:ss SSS）
     */
    private String logTime;
    /**
     * 跟踪ID（用于跟踪用户的一次请求流程）
     */
    private String traceId;
    /**
     *
     */
    private String span;
    /**
     * 用于区分各业务线，主要日志加工时对不同得业务线进行个性化处理（枚举：mobile,web,）
     */
    private String businessLine;
    /**
     * 日志类型
     * 现在位运算只支持3位（1代表是、0代表否），
     * 右数第一位为代表计算进日志系统（mongodb中保存时间14天）
     * 右数第二位为代表计算进入checklist
     * 右数第三位为代表计算hadoop日志 的pv和uv日志
     */
    private String logType;
    /**
     * 服务器名
     */
    private String serverName;


    /**
     * 服务器IP
     */
    private String serverIp;
    /**
     * （用户自定义日志使用数字代表，此项可用于对日志的个性化处理的区分）
     */
    private String userLogType;
    /**
     * 用户id，无线设备id(可用于分析用户行为使用)
     */
    private String sessionId;
    /**
     * Client机器id
     */
    private String cookieId;
    /**
     * 业务线分支名称
     */
    private String appName;
    /**
     * 调用服务接口的名称（可以使地址，action名称）
     */
    private String serviceName;
    /**
     * 耗时、毫秒数
     */
    private String elapsedTime;
    /**
     * 用户http请求的头信息内容（可自定义输出内容，必须json格式）
     */
    private String requestHeader;
    /**
     * 用户http请求的请求内容（必须json格式）（可以用于记录调用方法的参数）
     */
    private String requestBody;
    /**
     * 接口响应结果（0代表成功，非0代表失败
     */
    private String responseCode;
    /**
     * 接口响应结果（0代表成功，非0代表失败
     */
    private String businessErrorCode;
    /**
     * 返回结果（一次接口请求的结果数据，或者方法调用的放回内容）
     */
    private String responseBody;
    /**
     * 用于记录hadoop补充信息各业务先自定义内容要求使用#分隔，
     * 在请求中不能完全给出hadoop日志需要的信息的时候，就需要填写“Hadoop content”这个字段来补充内容，我们会通过“BusinessLine”字段来区分各业务系统的日志
     */
    private String hadoopContent;
    /**
     * 异常的堆栈信息
     */
    private Throwable exception;
    /**
     * exceptionmsg	异常的说明
     */
    private String exceptionMsg;
    /**
     * 扩展字段1(建议json传送)
     */
    private String extend1;
    /**
     * 扩展字段2
     */
    private String extend2;
    /**
     * 日志时间戳
     */
    private long logTimeStamp;
    /**
     * 设备唯一标示👌
     */
    private String imei;
    /**
     * 请求方法
     */
    private String method;

    private static String replaceTabAndEnter(String str) {
        if (str != null && str.length() > 0) {
            if (str.indexOf("\n") > -1 || str.indexOf("\t") > -1) {
                return str.replaceAll("\n|\t", " ");
            }
        }
        return StringUtils.isBlank(str) ? NULL_STR : str;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public long getLogTimeStamp() {
        return logTimeStamp;
    }

    public void setLogTimeStamp(long logTimeStamp) {
        this.logTimeStamp = logTimeStamp;
    }

    //	public String getLogVersion() {
//		return logVersion;
//	}
//	public void setLogVersion(String logVersion) {
//		this.logVersion = logVersion;
//	}
    public String getLogTime() {
        return logTime;
    }

    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getBusinessLine() {
        return businessLine;
    }

    public void setBusinessLine(String businessLine) {
        this.businessLine = businessLine;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public String getUserLogType() {
        return userLogType;
    }

    public void setUserLogType(String userLogType) {
        this.userLogType = userLogType;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getCookieId() {
        return cookieId;
    }

    public void setCookieId(String cookieId) {
        this.cookieId = cookieId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(String elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public String getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader(String requestHeader) {
        this.requestHeader = requestHeader;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getBusinessErrorCode() {
        return businessErrorCode;
    }

    public void setBusinessErrorCode(String businessErrorCode) {
        this.businessErrorCode = businessErrorCode;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public String getHadoopContent() {
        return hadoopContent;
    }

    public void setHadoopContent(String hadoopContent) {
        this.hadoopContent = hadoopContent;
    }

    public String getExceptionMsg() {
        return exceptionMsg;
    }

    public void setExceptionMsg(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }

    public String getExtend1() {
        return extend1;
    }

    public void setExtend1(String extend1) {
        this.extend1 = extend1;
    }

    public String getExtend2() {
        return extend2;
    }

    public void setExtend2(String extend2) {
        this.extend2 = extend2;
    }

    public Throwable getException() {
        return exception;
    }

    public void setException(Throwable exception) {
        this.exception = exception;
    }

    public String getSpan() {
        return span;
    }

    public void setSpan(String span) {
        this.span = span;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("1.0").append(TAB);
        builder.append(replaceTabAndEnter(this.logTime)).append(TAB);
        builder.append(replaceTabAndEnter(this.traceId)).append(TAB);
        builder.append(replaceTabAndEnter(this.span)).append(TAB);
        builder.append(replaceTabAndEnter(this.businessLine)).append(TAB);
        builder.append(replaceTabAndEnter(this.logType)).append(TAB);
        builder.append(replaceTabAndEnter(this.serverName)).append(TAB);
        builder.append(replaceTabAndEnter(this.serverIp)).append(TAB);
        builder.append(replaceTabAndEnter(this.method)).append(TAB);
        builder.append(replaceTabAndEnter(this.userLogType)).append(TAB);
        builder.append(replaceTabAndEnter(this.sessionId)).append(TAB);
        builder.append(replaceTabAndEnter(this.cookieId)).append(TAB);
        builder.append(replaceTabAndEnter(this.appName)).append(TAB);
        builder.append(replaceTabAndEnter(this.serviceName)).append(TAB);
        builder.append(replaceTabAndEnter(this.elapsedTime)).append(TAB);
        builder.append(replaceTabAndEnter(this.requestHeader)).append(TAB);
        builder.append(replaceTabAndEnter(this.requestBody)).append(TAB);
        builder.append(replaceTabAndEnter(this.responseCode)).append(TAB);
        builder.append(replaceTabAndEnter(this.businessErrorCode)).append(TAB);
        builder.append(replaceTabAndEnter(this.responseBody)).append(TAB);
        builder.append(replaceTabAndEnter(this.hadoopContent)).append(TAB);
        String exceptionString = null;
        if (exception != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            this.exception.printStackTrace(pw);
            exceptionString = sw.toString();
            pw.close();
            exceptionString = exceptionString.replaceAll("\n", "#");
            sw = null;
            pw = null;
        }
        builder.append(replaceTabAndEnter(exceptionString)).append(TAB);
        builder.append(replaceTabAndEnter(this.exceptionMsg)).append(TAB);
        builder.append(replaceTabAndEnter(this.extend1)).append(TAB);
        builder.append(replaceTabAndEnter(this.extend2)).append(ENTER);

        return builder.toString();
    }
}
