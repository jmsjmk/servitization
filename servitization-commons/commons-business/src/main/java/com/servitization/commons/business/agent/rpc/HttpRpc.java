package com.servitization.commons.business.agent.rpc;

import com.alibaba.fastjson.JSON;
import com.servitization.commons.business.agent.AgentField;
import com.servitization.commons.business.agent.AgentService;
import com.servitization.commons.business.agent.HttpClientAgentUtils;
import com.servitization.commons.business.agent.entity.CustomizeParameterEntity;
import com.servitization.commons.business.agent.enums.DataTypeEnum;
import com.servitization.commons.business.agent.enums.MethodTypeEnum;
import com.servitization.commons.business.agent.log.RpcLogObject;
import com.servitization.commons.business.agent.log.RpcLogUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpRpc implements IRpc {

    private static final int OK = 200;

    @Override
    public Object execute(RpcObject rpcObject) {
        HttpClientAgentUtils httpClient = new HttpClientAgentUtils();
        Method method = rpcObject.getMethod();
        AgentService agentService = rpcObject.getAgentService();
        Object[] args = rpcObject.getArgs();
        RpcConfig rpcConfig = rpcObject.getRpcConfig();
        Class<?> returnType = method.getReturnType();
        String name = agentService.name();
        String url;
        if (StringUtils.isBlank(name) || (url = rpcConfig.get(name + ".url")) == null) {
            throw new RuntimeException("Agent name and url can not be null");
        }
        //处理超时时间
        int timeOut = handleTimeOut(rpcConfig.get(name + ".timeOut"), agentService.timeOut(), 30000);
        int readTimeOut = handleTimeOut(rpcConfig.get(name + ".readTimeOut"), agentService.readTimeOut(), 200000);
        httpClient.setConnectionTimeOut(timeOut);
        httpClient.setSocketTimeOut(readTimeOut);
        // 使用自定义实体传递参数
        if (agentService.customizeParameter()) {
            if (args.length < 2) {
                throw new RuntimeException("Can not find customize parameter entity");
            } else {
                if (args[1] instanceof CustomizeParameterEntity) {
                    CustomizeParameterEntity cpe = (CustomizeParameterEntity) args[1];
                    if (StringUtils.isNotBlank(cpe.getUrl())) {
                        url = cpe.getUrl();
                    }
                } else {
                    throw new RuntimeException("Parameter can not be converted to CustomizeParameterEntity");
                }
            }
        }
        MethodTypeEnum methodType = agentService.methodType();
        Object arg = args[0];
        DataTypeEnum reqType = agentService.reqType();
        String request = null;
        Map<String, String> mRequest = null;
        switch (reqType) {
            case STRING:
                request = arg instanceof String ? (String) arg : form(arg);
                break;
            case JSON:
                request = JSON.toJSONString(arg);
                break;
            case MAP:
                mRequest = (Map<String, String>) arg;
                break;
            default:
                throw new RuntimeException("HttpRpc Only support JSON and String DataType. MethodName:" + method.getName());
        }
        Object result = null;
        //构造日志对象
        RpcLogObject rpcLogObject = new RpcLogObject();
        long startTime = System.currentTimeMillis();
        rpcLogObject.setMethodName(name);
        rpcLogObject.setStartTime(startTime);
        rpcLogObject.setRequest(result);
        RpcLogUtil.writeLogBefore(rpcLogObject);
        Exception sysException = null;
        //发送前日志
        HttpClientAgentUtils.HttpResult httpResult;
        try {
            switch (methodType) {
                case POST:
                    ContentType contentType = agentService.contentType();
                    switch (contentType) {
                        case POST_JSON:
                            httpResult = httpClient.postJson(url, request);
                            break;
                        case POST_TEXT:
                            httpResult = httpClient.post(url, request);
                            break;
                        case POST_KV:
                            List<NameValuePair> params = new ArrayList<>();
                            String[] kvs = request.split("&");
                            for (String kv : kvs) {
                                String[] sv = kv.split("=", 2);
                                if (sv.length == 2) {
                                    NameValuePair nameValuePair = new BasicNameValuePair(sv[0], sv[1]);
                                    params.add(nameValuePair);
                                }
                            }
                            httpResult = httpClient.post(url, params);
                            break;
                        case POST_MKV:
                            List<NameValuePair> mParams = new ArrayList<>();
                            for (Map.Entry<String, String> entry : mRequest.entrySet()) {
                                NameValuePair nameValuePair = new BasicNameValuePair(entry.getKey(), entry.getValue());
                                mParams.add(nameValuePair);
                            }
                            httpResult = httpClient.post(url, mParams);
                            break;
                        default:
                            throw new RuntimeException("HttpRpc not support ContentType. MethodName:" + method.getName());
                    }
                    break;
                case GET:
                    ContentType get_type = agentService.contentType();
                    switch (get_type) {
                        case GET_DERICET:
                            httpResult = httpClient.get(url + request, "");
                            break;
                        case GET_DEFAULT:
                            httpResult = httpClient.get(url, request);
                            break;
                        default:
                            httpResult = httpClient.get(url, request);
                            break;
                    }
                    break;
                case PUT:
                    httpResult = httpClient.putJson(url, request);
                    break;
                case DELETE:
                    httpResult = httpClient.delete(url, request);
                    break;
                default:
                    throw new RuntimeException("HttpRpc Only support POST and GET MethodType. MethodName:" + method.getName());
            }
            if (httpResult != null) {
                DataTypeEnum resultType = agentService.resultType();
                switch (resultType) {
                    case STRING:
                        result = httpResult.getContent();
                        break;
                    case JSON:
                        result = StringUtils.isNotBlank(httpResult.getContent()) ? JSON.parseObject(httpResult.getContent(), returnType) : null;
                        break;
                    case PROTOTYPE:
                        result = httpResult;
                        break;
                    default:
                        throw new RuntimeException("HttpRpc Only support JSON、 String and PROTOTYPE DataType. MethodName:" + method.getName());
                }
                if (httpResult.getStatusCode() != OK) {
                    rpcLogObject.setSysError(httpResult.getStatusCode() + "");
                }
            }
        } catch (Exception e) {
            sysException = e;
        }
        rpcLogObject.setResult(result);
        rpcLogObject.setThrowable(sysException);
        RpcLogUtil.writeLogAfter(rpcLogObject);
        if (sysException != null) {
            throw new RuntimeException(sysException.getMessage());
        }
        return result;
    }

    /**
     * 超时时间处理
     * 优先级：propertiesTimeOut 》 annotionTimeOut 》defaultTimOut
     *
     * @param propertiesTimeOut 配置文件中的时间
     * @param annotionTimeOut   注解配置的时间
     * @param defaultTimOut     默认时间
     * @return
     */
    private int handleTimeOut(String propertiesTimeOut, int annotionTimeOut, int defaultTimOut) {
        int timeOut = 0;
        if (StringUtils.isNumeric(propertiesTimeOut)) {
            timeOut = Integer.parseInt(propertiesTimeOut);
        }
        timeOut = timeOut > 0 ? timeOut : (annotionTimeOut > 0 ? annotionTimeOut : defaultTimOut);
        return timeOut;
    }

    public static <T> String form(T entity) {
        String result = StringUtils.EMPTY;
        if (entity == null) {
            return result;
        }
        Field[] fields = entity.getClass().getDeclaredFields();
        StringBuilder builder = new StringBuilder();
        AgentField agentField;
        try {
            for (int i = 0; i < fields.length; i++) {
                agentField = fields[i].getAnnotation(AgentField.class);
                String key = agentField != null && StringUtils.isNotBlank(agentField.name()) ? agentField.name() : fields[i].getName();
                builder.append(key);
                builder.append("=");
                fields[i].setAccessible(true);
                Object value = fields[i].get(entity);
                value = value == null ? "" : fields[i].get(entity);
                builder.append(value);
                if (i < fields.length - 1) {
                    builder.append("&");
                }
            }
            result = builder.toString();
        } catch (IllegalAccessException e) {
            result = StringUtils.EMPTY;
        }
        return result;
    }
}
