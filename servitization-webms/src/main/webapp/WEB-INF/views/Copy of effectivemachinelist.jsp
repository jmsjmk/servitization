<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<c:choose>
    <c:when test="${not empty machines}">
        <table class="table table-bordered table-hover  table-striped">
            <thead>
            <tr>
                <th style="text-align: center;">#</th>
                <th style="text-align: center;">机器IP</th>
                <th style="text-align: center;">机器名</th>
                <th style="text-align: center;">机器所属节点</th>
            </tr>
            </thead>
            <tbody>
            <c:set value="1" var="num" scope="page"/>
            <c:forEach var="m" items="${machines}">
                <tr align="center">
                    <td scope="row">
                        <c:out value="${num}"/>
                        <c:set value="${num + 1}" var="num"/>
                    </td>
                    <td><c:out value="${m.machineIp}"/></td>
                    <td><c:out value="${m.machineName}"/></td>
                    <td>
                        <c:choose>
                            <c:when test="${m.nodeName != null and m.nodeName != ''}">
                                <c:out value="${m.nodeName}"/>
                            </c:when>
                            <c:otherwise>
                                -
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:when>
    <c:otherwise>
        没有生效机器！
    </c:otherwise>
</c:choose>