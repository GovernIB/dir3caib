<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/modulos/includes.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <c:import url="../modulos/imports.jsp"/>
</head>

<body>

<c:import url="../modulos/menu.jsp"/>

<div class="row-fluid container main">
    <div class="well well-dir3caib">

        <div class="row-fluid">

            <div class="box span12">

                <div class="box-header well">
                    <h2><fmt:message key="sincronizacion.listado"/></h2>
                </div>

                <c:import url="../modulos/mensajes.jsp"/>

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
                                <thead>
                                <tr>
                                    <th><spring:message code="sincronizacion.tipo"/></th>
                                    <th><fmt:message key="dir3caib.intervalo"/> (<fmt:message key="dir3caib.fechainicio"/> - <fmt:message key="dir3caib.fechafin"/>)</th>
                                    <th><fmt:message key="dir3caib.fechaSincronizacion"/></th>
                                    <th><fmt:message key="dir3caib.estado"/></th>
                                    <th><spring:message code="sincronizacion.ficheros.directorio"/></th>
                                    <th><spring:message code="sincronizacion.ficheros.catalogo"/></th>
                                </tr>
                                </thead>

                                <tbody>
                                <c:forEach var="sincronizacion" items="${listado}">
                                    <tr>
                                        <td>
                                            <c:if test="${sincronizacion.tipo == 1}">
                                                <span class="label label-info"><spring:message code="sincronizacion.tipo.${sincronizacion.tipo}"/></span>
                                            </c:if>
                                            <c:if test="${sincronizacion.tipo == 2}">
                                                <span class="label label-success"><spring:message code="sincronizacion.tipo.${sincronizacion.tipo}"/></span>
                                            </c:if>
                                        </td>
                                        <td>(<c:if test="${empty sincronizacion.fechaInicio}"> ******* </c:if><fmt:formatDate pattern="dd/MM/yyyy" value="${sincronizacion.fechaInicio}" />  -  <c:if test="${empty sincronizacion.fechaFin}"> ******* </c:if><fmt:formatDate pattern="dd/MM/yyyy" value="${sincronizacion.fechaFin}" />)</td>
                                        <td>
                                            <fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${sincronizacion.fechaImportacion}" />
                                        </td>

                                        <td>
                                            <c:choose>
                                                <c:when test="${sincronizacion.estado == 1}">
                                                    <span class="label label-info"><spring:message code="sincronizacion.estado.${sincronizacion.estado}"/></span>
                                                </c:when>
                                                <c:when test="${sincronizacion.estado == 2}">
                                                    <span class="label label-success"><spring:message code="sincronizacion.estado.${sincronizacion.estado}"/></span>
                                                </c:when>
                                                <c:when test="${sincronizacion.estado == 3}">
                                                    <span class="label label-warning"><spring:message code="sincronizacion.estado.${sincronizacion.estado}"/></span>
                                                </c:when>
                                                <c:when test="${sincronizacion.estado == 4}">
                                                    <span class="label label-important"><spring:message code="sincronizacion.estado.${sincronizacion.estado}"/></span>
                                                </c:when>
                                                <c:when test="${sincronizacion.estado == 5}">
                                                    <span class="label label-success"><spring:message code="sincronizacion.estado.${sincronizacion.estado}"/></span>
                                                </c:when>

                                            </c:choose>
                                        </td>

                                        <td class="center">
                                            <div class="btn-group">
                                                <c:if test="${not empty sincronizacion.ficherosDirectorio}">
                                                    <button type="button" class="btn btn-success dropdown-toggle"
                                                            data-toggle="dropdown"><fmt:message
                                                            key="dir3caib.descargar"/> <span class="caret"></span>
                                                    </button>
                                                </c:if>
                                                <c:if test="${empty sincronizacion.ficherosDirectorio}">
                                                    <button type="button" class="btn btn-success dropdown-toggle"
                                                            data-toggle="dropdown" disabled="disabled"><fmt:message
                                                            key="dir3caib.descargar"/> <span class="caret"></span>
                                                    </button>
                                                </c:if>
                                                <ul class="dropdown-menu">
                                                    <c:forEach var="fichero" items="${sincronizacion.ficherosDirectorio}">
                                                        <li class="submenu-complet"><a
                                                                href="<c:url value="/archivo/${fichero}/${sincronizacion.codigo}"/>">${fichero}</a>
                                                        </li>
                                                    </c:forEach>
                                                </ul>
                                            </div>
                                        </td>

                                        <td class="center">
                                            <div class="btn-group">
                                                <c:if test="${not empty sincronizacion.ficherosCatalogo}">
                                                    <button type="button" class="btn btn-success dropdown-toggle"
                                                            data-toggle="dropdown"><fmt:message
                                                            key="dir3caib.descargar"/> <span class="caret"></span>
                                                    </button>
                                                </c:if>
                                                <c:if test="${empty sincronizacion.ficherosCatalogo}">
                                                    <button type="button" class="btn btn-success dropdown-toggle"
                                                            data-toggle="dropdown" disabled="disabled"><fmt:message
                                                            key="dir3caib.descargar"/> <span class="caret"></span>
                                                    </button>
                                                </c:if>
                                                <ul class="dropdown-menu">
                                                    <c:forEach var="fichero" items="${sincronizacion.ficherosCatalogo}">
                                                        <li class="submenu-complet"><a
                                                                href="<c:url value="/archivo/${fichero}/${sincronizacion.codigo}"/>">${fichero}</a>
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
                            <c:import url="../modulos/paginacion.jsp">
                                <c:param name="entidad" value="sincronizacion"/>
                                
                            </c:import>
                        </c:if>
                </div>

            </div>
        </div>

        <hr>

    </div>
</div>

<c:import url="../modulos/pie.jsp"/>

</body>
</html>