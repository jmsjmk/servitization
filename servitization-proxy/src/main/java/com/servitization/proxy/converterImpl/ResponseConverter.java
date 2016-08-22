package com.servitization.proxy.converterImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.servitization.embedder.immobile.ImmobileResponse;
import com.servitization.proxy.CommonLogger;
import com.servitization.proxy.IResponseConvert;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ResponseConverter implements IResponseConvert {

    @Override
    public void convertResponseContext(ImmobileResponse response, String servicePath) throws IOException {
        String originalContent = new String(response.getContent());
        String newContent = ContextConverter.convert(originalContent, servicePath);
        if (newContent == null || newContent.length() == 0) {
            return;
        } else {
            response.resetContent();
            response.getOutputStream().write(newContent.getBytes());
        }
    }

    static class ContextConverter {

        public static String DEST_CODE = "resultCode";
        public static String DEST_DESCRIPTION = "StateDescription";
        public static String DEST_DATA = "Data";

        public static Map<String, Object> getDataMap(JSONObject jobj) {
            Map<String, Object> dataMap = new HashMap<>();
            if (null == jobj) {
                return dataMap;
            }
            return (Map) initData(jobj, dataMap);
        }

        public static Object initData(JSONObject jsonObject, Object obj) {
            for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
                String key = entry.getKey();
                if (checkProperty(key)) {
                    continue;
                }
                Object value = entry.getValue();
                if (obj instanceof Map) {
                    ((Map) obj).put(key, value);
                } else if (obj instanceof JSONObject) {
                    ((JSONObject) obj).put(key, value);
                }
            }
            return obj;
        }

        public static JSONObject getDataJsonObject(JSONObject jo1, JSONObject jo2) {
            if (jo1 == null || jo2 == null) {
                return new JSONObject();
            }
            return (JSONObject) initData(jo1, jo2);
        }

        public static boolean checkProperty(String property) {
            if (property.equals("ResId") || property.equals("ResMsg")
                    || property.equals("StatusCode") || property.equals("StateDescription")
                    || property.equals("Data")) {
                return true;
            }
            return false;
        }

        /**
         * 转换规则描述:
         * 1. -10010 所有接口都可能返回的错误报文信息   {"resultCode":"-10010", "StateDescription":"", "Data":{}}
         * 2. 报文数据中含有不含Data标记
         * 2.1 不含Data,响应中含有ResId,ResMsg 转换规则将json对象包装一个data数据去掉属性参考getDataMap方法
         * 2.2 不含Data,响应中含有statusCode,  将Data设置成为{}
         * 3. 报文数据中含有Data标记
         *
         * @param content
         * @return
         */
        public static String convert(String content, String servicePath) {
            String jsonStr = "";
            try {
                if (null == content || content.length() == 0) {
                    CommonLogger.getLogger().error("convert json error context is null! Path[" + servicePath + "]");
                    return jsonStr;
                }
                Map<String, Object> resMap = new HashMap<>();
                Map<String, Object> dataMap;
                if (content.equalsIgnoreCase("-10010")) {
                    resMap.put(DEST_CODE, Integer.valueOf("-10010"));
                    resMap.put(DEST_DESCRIPTION, "");
                    resMap.put(DEST_DATA, new Object());
                    jsonStr = JSON.toJSONString(resMap);
                    CommonLogger.getLogger().info("Path[" + servicePath + "] ResponseContent Convert From[" + content + "] To[" + JSON.toJSONString(resMap) + "]");
                    return jsonStr;
                }

                String ResId;
                String ResMsg;
                String StatusCode;
                String StateDescription;
                String resultCode;
                Object obj = JSON.parse(content);
                if (obj == null) {
                    CommonLogger.getLogger().error("Convert json error! context is null Path[" + servicePath + "]");
                    return "";
                }
                JSONObject jobj = (JSONObject) obj;
                Object dataObject = jobj.get("Data");
                ResId = jobj.getString("ResId");
                ResMsg = jobj.getString("ResMsg");
                StatusCode = jobj.getString("StatusCode");
                StateDescription = jobj.getString("StateDescription");
                resultCode = jobj.getString("resultCode");
                if (dataObject == null) {
                    if (ResId == null) {
                        resMap.put(DEST_CODE, Integer.valueOf(StatusCode));
                        resMap.put(DEST_DESCRIPTION, StateDescription);
                        resMap.put(DEST_DATA, new Object());
                    } else {
                        resMap.put(DEST_CODE, Integer.valueOf(ResId) == 0 ? 10000 : Integer.valueOf(ResId));
                        resMap.put(DEST_DESCRIPTION, ResMsg);
                        dataMap = getDataMap(jobj);
                        resMap.put(DEST_DATA, dataMap);
                    }
                    jsonStr = JSON.toJSONString(resMap);
                    CommonLogger.getLogger().info("Path[" + servicePath + "] ResponseContent Convert From[" + content + "] To[" + JSON.toJSONString(resMap) + "]");
                } else {
                    if (null == ResId || null == ResMsg) {
                        if (null == StatusCode) {
                            resMap.put(DEST_CODE, Integer.valueOf(resultCode));
                        } else {
                            resMap.put(DEST_CODE, Integer.valueOf((StatusCode)));
                        }
                        resMap.put(DEST_DESCRIPTION, StateDescription);
                        resMap.put(DEST_DATA, dataObject);
                        jsonStr = JSON.toJSONString(resMap);
                        CommonLogger.getLogger().info("Path[" + servicePath + "] ResponseContent Convert From[" + content + "] To[" + JSON.toJSONString(resMap) + "]");
                    } else {
                        resMap.put(DEST_CODE, Integer.valueOf(ResId) == 0 ? 10000 : Integer.valueOf(ResId));
                        resMap.put(DEST_DESCRIPTION, ResMsg);
                        Object destObj;
                        if (dataObject instanceof JSONObject) {
                            destObj = getDataJsonObject(jobj, (JSONObject) dataObject);
                        } else {
                            destObj = dataObject;
                        }
                        resMap.put(DEST_DATA, destObj);
                        jsonStr = JSON.toJSONString(resMap);
                        CommonLogger.getLogger().info("Path[" + servicePath + "] ResponseContent Convert From[" + content + "] To[" + JSON.toJSONString(resMap) + "]");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                CommonLogger.getLogger().error("ResponseContent Convert error!! Path[" + servicePath + "]", e);
                jsonStr = null;
            }
            return jsonStr;
        }
    }
}
