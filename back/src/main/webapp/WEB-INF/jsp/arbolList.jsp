<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/modulos/includes.jsp" %>

<!DOCTYPE html>
<html lang="ca">
<head>
    <c:import url="modulos/imports.jsp"/>
</head>

<body>

    <c:import url="modulos/menu.jsp"/>
    <div class="row-fluid container main">
        <div class="well well-dir3caib">
            <div class="row-fluid">
                <div class="box span12">
                    <div class="box-header well">
                        <h2><fmt:message key="unidad.organigrama"/>&nbsp;<i> ${nodo.id} -${nodo.nombre}</i></h2>
                    </div>

                    <c:if test="${empty nodo.hijos}">
                        <div class="alert alert-warning alert-dismissable">
                            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                             <strong><spring:message code="unidad.organigrama.vacio"/></strong>
                        </div>
                    </c:if>
                    <c:if test="${not empty nodo.hijos}">
                    <div class="tree">
                            <ul>
                                <li>
                                     <span class="badge-arbre btn-success"><i class=""></i> ${nodo.id} -${nodo.nombre}</span>
                                     <jsp:include page="nodo.jsp">
                                         <jsp:param name="color" value="badge-arbre btn-primary"/>
                                     </jsp:include>

                                </li>
                            </ul>
                    </div>
                    </c:if>

                </div>
            </div>
        </div>
    </div>


</body>
</html>























































