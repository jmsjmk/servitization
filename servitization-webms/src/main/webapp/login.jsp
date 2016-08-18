<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String uname = request.getParameter("uname");
    String upassword = request.getParameter("upassword");
    if (null != uname && upassword != null) {
        if (uname.equalsIgnoreCase("test") && upassword.equals("test")) {
            session.setAttribute("user", "success");
            response.sendRedirect("/");
        }
    }
%>
<html>
<head>
    <title>login</title>
</head>
<body>
<form action="login.jsp" method="post">
    用户名:<input type="text" name="uname"/> <br/>
    密码 : <input type="password" name="upassword"/> <br/>

    <input type="submit" name="tj" value="提交"/>
</form>
</body>
</html>
