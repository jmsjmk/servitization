<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.alibaba.fastjson.*" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%
    Map<String, String> smap = new HashMap<String, String>();

    smap.put("data","data");
    smap.put("name","name");
    smap.put("age","age");

    String s = JSONObject.toJSONString(smap);
    System.out.println(s);
    out.println(s);
%>