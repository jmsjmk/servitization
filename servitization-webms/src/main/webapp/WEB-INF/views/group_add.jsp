<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="panel panel-default">
   <div class="panel-heading">
      <h3 class="panel-title">
          <span>添加/修改</span>
      </h3>
   </div>

   <div class="panel-body">
       <form class="form-horizontal">
           <c:choose >
               <c:when test="${metadataGroup != null}">
                   <input type="hidden" id="groupId" value="<c:out value="${metadataGroup.id}"/>">
               </c:when>
               <c:otherwise>
                   <input type="hidden" id="groupId" value="">
               </c:otherwise>
           </c:choose>
            <div class="form-group">
                <label for="name" class="col-xs-2 control-label">名称</label>
                <div class="col-xs-7">
                    <c:choose>
                        <c:when test="${metadataGroup != null}">
                            <input type="text" class="form-control" id="name" placeholder="name" value="<c:out value="${metadataGroup.name}"/>" >
                        </c:when>
                        <c:otherwise>
                            <input type="text" class="form-control" id="name" placeholder="name" >
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="form-group">
                <label for="policy" class="col-xs-2 control-label">策略</label>
                <div class="col-xs-7">
                    <select class="form-control" id="policy">
                        <option value="SECURE"  <c:if test="${metadataGroup != null and metadataGroup.policy == 'SECURE'}">selected</c:if> >SECURE</option>
                        <option value="OPEN" <c:if test="${metadataGroup != null and metadataGroup.policy == 'OPEN'}">selected</c:if> >OPEN</option>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label for="size" class="col-xs-2 control-label">线程池大小</label>
                <div class="col-xs-7">
                    <c:choose>
                        <c:when test="${metadataGroup != null}">
                            <input type="text" class="form-control" id="size" placeholder=size value="<c:out value="${metadataGroup.size}"/>" >
                        </c:when>
                        <c:otherwise>
                            <input type="text" class="form-control" id="size" placeholder="size" value="10" >
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="form-group">
                <label for="processTimeOut" class="col-xs-2 control-label">处理超时时间</label>
                <div class="col-xs-7">
                    <c:choose>
                        <c:when test="${metadataGroup != null}">
                            <input type="text" class="form-control" id="processTimeOut" placeholder="processTimeOut" value="<c:out value="${metadataGroup.processTimeOut}"/>" >
                        </c:when>
                        <c:otherwise>
                            <input type="text" class="form-control" id="processTimeOut" placeholder="processTimeOut" value="1000" >
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="form-group">
                <label for="upModule" class="col-xs-2 control-label">上行模块</label>
                <div>
                    <c:forEach var="m" items="${modules}">
                        <c:if test="${m.chain==0}">
                            <c:choose>
                                <c:when test="${metadataGroup != null}">
                                    <c:set value="false" var ="flag"/>
                                    <c:forTokens items="${metadataGroup.moduleIds}" delims="," var="mods">
                                        <c:set value="m${m.id}" var="mId"/>
                                        <c:choose>
                                            <c:when test="${mId == mods}">
                                                <c:set value="true" var="flag"></c:set>
                                            </c:when>
                                        </c:choose>
                                    </c:forTokens>
                                    <c:choose>
                                        <c:when test="${flag==true }">
                                            <input type="checkbox" name="upModule" value="m${m.id}" checked="checked"/>${m.name }
                                        </c:when>
                                        <c:otherwise>
                                            <input type="checkbox" name="upModule" value="m${m.id}"/>${m.name }
                                        </c:otherwise>
                                    </c:choose>
                                </c:when>
                                <c:otherwise>
                                    <input type="checkbox" name="upModule" value="m${m.id}"/>${m.name }
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                    </c:forEach>
                </div>
            </div>
                        
            <div class="form-group">
                <label for="downModule" class="col-xs-2 control-label">下行模块</label>
                <div>
                    <c:forEach var="m" items="${modules}">
                        <c:if test="${m.chain==1}">
                            <c:choose>
                                <c:when test="${metadataGroup != null}">
                                    <c:set value="false" var ="flag"/>
                                    <c:forTokens items="${metadataGroup.moduleIds}" delims="," var="mods">
                                        <c:set value="m${m.id}" var="mId"/>
                                        <c:choose>
                                            <c:when test="${mId == mods}">
                                                <c:set value="true" var="flag"></c:set>
                                            </c:when>
                                        </c:choose>
                                    </c:forTokens>
                                    <c:choose>
                                        <c:when test="${flag==true }">
                                            <input type="checkbox" name="downModule" value="m${m.id}" checked="checked"/>${m.name }
                                        </c:when>
                                        <c:otherwise>
                                            <input type="checkbox" name="downModule" value="m${m.id}"/>${m.name }
                                        </c:otherwise>
                                    </c:choose>
                                </c:when>
                                <c:otherwise>
                                    <input type="checkbox" name="downModule" value="m${m.id}"/>${m.name }
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                    </c:forEach>
                </div>
            </div>
            <div class="form-group">
                <div class="col-xs-offset-2 col-xs-3">
                    <button type="button" class="btn btn-default btn-primary" onclick="addGroup();">保存</button>
                </div>
                <div class="col-xs-3">
                    <button type="button" class="btn btn-default" onclick="showGroupPage();">取消</button>
                </div>
            </div>
       </form>
   </div>
</div>
<script src="${path}/common/js/alert.js"></script>
<script src="${path}/common/js/group.js"></script>