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


                    <!-- LEYENDA -->
                  <%--  <div class="col-xs-4 button-right">
                        <div class="box-header well">
                            <div class="panel-heading">
                                <div class="row">
                                    <div class="col-xs-12">
                                        <i class="fa fa-comment-o"></i> <strong><spring:message code="dir3caib.leyenda"/></strong>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-footer">
                                <div class="row">
                                    <div class="col-xs-6 pad-bottom5">
                                        <button type="button" class="btn-primary btn-xs"><i class="fa fa-globe"></i> <spring:message code="dir3caib.unidadOrganica"/></button>
                                    </div>

                                    <div class="col-xs-6 pad-bottom5">
                                        <button type="button" class="btn-warning btn-xs"><i class="fa fa-home"></i> <spring:message code="dir3caib.oficina.principal"/></button>
                                    </div>


                                    <div class="col-xs-6 pad-bottom5">
                                        <button type="button" class="btn-ofaux btn-xs"><i class="fa fa-home"></i> <spring:message code="dir3caib.oficina.auxiliar"/></button>
                                    </div>


                                    <div class="col-xs-6 pad-bottom5">
                                        <button type="button" class="btn-success btn-xs"><i class="fa fa-institution"></i> <spring:message code="dir3caib.oficina.funcional"/></button>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>--%>
                    <c:if test="${not empty nodo.hijos}">
                        <div class="tree">
                            <ul>
                                <li>
                                    <span class="badge-arbre btn-primary"><i class=""></i> ${nodo.id} -${nodo.nombre}</span>
                                    <!--Pintamos oficinas responsables -->
                                    <ul>
                                        <c:forEach var="ofiDependiente" items="${nodo.oficinasDependientes}">
                                            <li><span class="badge-arbre btn-warning"><i class="fa fa-home"></i> ${ofiDependiente.id} -${ofiDependiente.nombre}</span>
                                                <!-- Pintamos las auxiliares -->
                                                <c:if test="${not empty ofiDependiente.oficinasAuxiliares }">
                                                    <ul>
                                                        <c:forEach var="ofiAuxiliar" items="${ofiDependiente.oficinasAuxiliares}">
                                                            <li><span class="badge-arbre btn-ofaux"><i class="fa fa-home"></i> ${ofiAuxiliar.id} -${ofiAuxiliar.nombre}</span></li>
                                                        </c:forEach>
                                                    </ul>
                                                </c:if>
                                            </li>
                                         </c:forEach>
                                         <!-- FUNCIONALES -->
                                         <c:forEach var="ofiFuncional" items="${nodo.oficinasFuncionales}">
                                            <li><span class="badge-arbre btn-success"><i class="fa fa-institution"></i> ${ofiFuncional.id} -${ofiFuncional.nombre}</span></li>
                                         </c:forEach>
                                    </ul>
                                    <jsp:include page="nodo.jsp"/>
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























































