<%@ page import="es.caib.dir3caib.persistence.model.Dir3caibConstantes" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/modulos/includes.jsp" %>

<!DOCTYPE html>
<html>
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
                    <h2><fmt:message key="descarga.listado.${elemento}"/></h2>
                </div>

                <c:import url="modulos/mensajes.jsp"/>

                <div class="box-content">
                    <c:if test="${paginacion != null && empty listado}">
                        <div class="alert fade in">
                            <button type="button" class="close" data-dismiss="alert">&times;</button>
                            <fmt:message key="descarga.existentes.notfound"/>
                        </div>
                    </c:if>

                    <c:if test="${not empty listado}">
                           <div class="well" style="margin-top:8px;padding:4px 4px">
                               <c:if test="${paginacion.totalResults > 1}">
                                   <fmt:message key="dir3caib.resultados"/> <strong>${paginacion.totalResults}</strong> <fmt:message key="descarga.descargas"/>
                               </c:if>
                               <c:if test="${paginacion.totalResults == 1}">
                                   <fmt:message key="dir3caib.resultado"/> <strong>${paginacion.totalResults}</strong> <fmt:message key="descarga.descarga"/>
                               </c:if>

                               <p class="pull-right">Página <strong>${paginacion.currentIndex}</strong> de ${paginacion.totalPages}</p>
                           </div>
                            <table class="table table-bordered">
                                <colgroup>
                                    <col>
                                    <col>
                                    <col>
                                    <col>
                                    <col width="130">
                                </colgroup>
                                <thead>
                                <tr>
                                    <th>id</th>
                                    <th><fmt:message key="dir3caib.intervalo"/>    (<fmt:message key="dir3caib.fechainicio"/> - <fmt:message key="dir3caib.fechafin"/>)</th>
                                    <th><fmt:message key="dir3caib.fechaimportacion"/></th>
                                    <th><fmt:message key="dir3caib.estado"/></th>
                                    <th><fmt:message key="dir3caib.ficheros"/></th>
                                </tr>
                                </thead>

                                <tbody>
                                <c:forEach var="descarga" items="${listado}">
                                    <tr>
                                        <td>${descarga.codigo}</td>
                                        <td>( <c:if test="${empty descarga.fechaInicio}"> ******* </c:if><fmt:formatDate pattern="dd/MM/yyyy" value="${descarga.fechaInicio}" />  -  <c:if test="${empty descarga.fechaFin}"> ******* </c:if><fmt:formatDate pattern="dd/MM/yyyy" value="${descarga.fechaFin}" /> )</td>
                                        <td>
                                            <fmt:formatDate pattern="dd/MM/yyyy" value="${descarga.fechaImportacion}" /></td>
                                            <c:set var="codigoVacio" value="<%=Dir3caibConstantes.CODIGO_RESPUESTA_VACIO%>"/>
                                        <td>
                                            <c:if test="${not empty descarga.fechaImportacion}">
                                                <i class="fa fa-check-square fa-lg" style="color:green"></i>
                                                <spring:message code="dir3caib.importado"/>
                                            </c:if>
                                            <c:if test="${empty descarga.fechaImportacion}">
                                                <i class="fa fa-exclamation-triangle fa-lg" style="color:red"></i>
                                                <c:if test="${not empty descarga.estado && descarga.estado==codigoVacio}">
                                                    <fmt:message key="descarga.vacio"/></c:if>
                                                <c:if test="${not empty descarga.estado && descarga.estado!=codigoVacio}">
                                                    <fmt:message key="descarga.error.importacion"/>
                                                </c:if>
                                            </c:if>
                                        </td>
                                        <td class="center">
                                            <div class="btn-group">
                                                <button type="button" class="btn btn-success dropdown-toggle"
                                                        data-toggle="dropdown"><fmt:message
                                                        key="dir3caib.descargar"/><span class="caret"></span>
                                                </button>
                                                <ul class="dropdown-menu">
                                                    <c:forEach var="fichero" items="${descarga.ficheros}">
                                                        <li class="submenu-complet"><a
                                                                href="<c:url value="/archivo/${fichero}/${descarga.codigo}"/>">${fichero}</a>
                                                        </li>
                                                    </c:forEach>
                                                </ul>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>

                            <!-- Paginacion -->
                            <c:import url="modulos/paginacion.jsp">
                                <c:param name="entidad" value="descarga"/>
                                <c:param name="elemento" value="${elemento}"/>
                            </c:import>
                        </c:if>
                </div>

            </div>
        </div>

        <hr>

    </div>
</div>

<c:import url="modulos/pie.jsp"/>

</body>
</html>