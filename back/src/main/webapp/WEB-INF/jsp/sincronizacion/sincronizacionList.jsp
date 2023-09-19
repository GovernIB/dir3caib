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
                                    <th><spring:message code="sincronizacion.ficheros"/></th>
                                    <th><spring:message code="dir3caib.acciones"/></th>
                                </tr>
                            </thead>

                            <tbody>
                                <c:forEach var="sincronizacion" items="${listado}" varStatus="status">
                                    <tr>
                                        <td>
                                            <c:if test="${sincronizacion.tipo == 1}"> <%--Catálogo--%>
                                                <span class="label label-info"><spring:message code="sincronizacion.tipo.${sincronizacion.tipo}"/></span>
                                            </c:if>
                                            <c:if test="${sincronizacion.tipo == 2}"> <%--Directorio actualización--%>
                                                <span class="label label-success"><spring:message code="sincronizacion.tipo.${sincronizacion.tipo}"/></span>
                                            </c:if>
                                            <c:if test="${sincronizacion.tipo == 3}"> <%--Directorio completo--%>
                                                <span class="label label-important"><spring:message code="sincronizacion.tipo.${sincronizacion.tipo}"/></span>
                                            </c:if>
                                        </td>
                                        <td>
                                            <c:if test="${sincronizacion.tipo == 1}"> <%--Catálogo--%>
                                                <fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${sincronizacion.fechaFin}" />
                                            </c:if>
                                            <c:if test="${sincronizacion.tipo == 2 || sincronizacion.tipo == 3}"> <%--Directorio--%>
                                                (<c:if test="${empty sincronizacion.fechaInicio}"> ******* </c:if><fmt:formatDate pattern="dd/MM/yyyy" value="${sincronizacion.fechaInicio}" />  -  <c:if test="${empty sincronizacion.fechaFin}"> ******* </c:if><fmt:formatDate pattern="dd/MM/yyyy" value="${sincronizacion.fechaFin}" />)
                                            </c:if>
                                        </td>
                                        <td>
                                            <fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${sincronizacion.fechaImportacion}" />
                                        </td>

                                        <td>
                                            <c:choose>
                                                <c:when test="${sincronizacion.estado == 1}">
                                                    <span class="label label-info"><spring:message code="sincronizacion.estado.${sincronizacion.estado}"/></span>
                                                </c:when>
                                                <c:when test="${sincronizacion.estado == 2}">
                                                    <span class="label label-warning"><spring:message code="sincronizacion.estado.${sincronizacion.estado}"/></span>
                                                </c:when>
                                                <c:when test="${sincronizacion.estado == 3}">
                                                    <span class="label label-important"><spring:message code="sincronizacion.estado.${sincronizacion.estado}"/></span>
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

                                                <c:if test="${sincronizacion.tipo == 1}"> <%--Catálogo--%>
                                                    <c:if test="${not empty sincronizacion.ficherosCatalogo}">
                                                        <button type="button" class="btn btn-success dropdown-toggle"
                                                                data-toggle="dropdown"><fmt:message key="dir3caib.descargar"/> <span class="caret"></span>
                                                        </button>

                                                        <ul class="dropdown-menu">
                                                            <c:forEach var="fichero" items="${sincronizacion.ficherosCatalogo}">
                                                                <li class="submenu-complet"><a href="<c:url value="/archivo/${fichero}/${sincronizacion.codigo}"/>">${fichero}</a></li>
                                                            </c:forEach>
                                                        </ul>
                                                    </c:if>
                                                </c:if>

                                                <c:if test="${sincronizacion.tipo == 2}">  <%--Directorio actualización--%>
                                                    <c:if test="${not empty sincronizacion.ficherosDirectorio}">
                                                        <button type="button" class="btn btn-success dropdown-toggle"
                                                                data-toggle="dropdown" <c:if test="${empty sincronizacion.ficherosDirectorio}">disabled="disabled"</c:if> ><fmt:message
                                                                key="dir3caib.descargar"/> <span class="caret"></span>
                                                        </button>

                                                        <ul class="dropdown-menu">
                                                            <c:forEach var="fichero" items="${sincronizacion.ficherosDirectorio}">
                                                                <li class="submenu-complet"><a href="<c:url value="/archivo/${fichero}/${sincronizacion.codigo}"/>">${fichero}</a></li>
                                                            </c:forEach>
                                                        </ul>
                                                    </c:if>

                                                </c:if>
                                                <c:if test="${sincronizacion.tipo == 3}">  <%--Directorio completa--%>
                                                    <c:if test="${not empty sincronizacion.ficherosDirectorioCompleto}">
                                                        <button type="button" class="btn btn-success dropdown-toggle"
                                                                data-toggle="dropdown" <c:if test="${empty sincronizacion.ficherosDirectorioCompleto}">disabled="disabled"</c:if> ><fmt:message
                                                                key="dir3caib.descargar"/> <span class="caret"></span>
                                                        </button>

                                                        <ul class="dropdown-menu">
                                                            <c:forEach var="nivel" items="${nivelesAdministracion}">
                                                                <li class="dropdown-submenu"><a tabindex="-1" href="#">${nivel.descripcionNivelAdministracion}</a>
                                                                    <ul class="dropdown-menu">
                                                                        <c:forEach var="ficheros" items="${sincronizacion.ficherosDirectorioCompleto}">
                                                                            <c:if test="${ficheros.key == nivel.codigoNivelAdministracion}">
                                                                                <c:forEach var="fichero" items="${ficheros.value}">
                                                                                    <li class="submenu-complet"><a tabindex="-1" href="<c:url value="/archivo/${fichero}/${nivel.codigoNivelAdministracion}/${sincronizacion.codigo}"/>">${fichero}</a></li>
                                                                                </c:forEach>
                                                                            </c:if>
                                                                        </c:forEach>
                                                                    </ul>
                                                                </li>
                                                            </c:forEach>
                                                        </ul>
                                                    </c:if>

                                                </c:if>
                                            </div>
                                        </td>
                                        <td class="center">
                                            <c:if test="${ultimaSincroDirectorio.codigo == sincronizacion.codigo || ultimaSincroCatalogo.codigo == sincronizacion.codigo}">
                                                <a class="btn btn-danger btn-sm disabled" href="javascript:void(0);" title="<spring:message code="sincronizacion.eliminar.correcta"/>"><span class="fa fa-eraser"></span></a>
                                            </c:if>
                                            <c:if test="${ultimaSincroDirectorio.codigo != sincronizacion.codigo && ultimaSincroCatalogo.codigo != sincronizacion.codigo}">
                                                <a class="btn btn-danger btn-sm" onclick='javascript:confirm("<c:url value="/sincronizacion/${sincronizacion.codigo}/delete"/>","<spring:message code="dir3caib.confirmar.eliminacion" htmlEscape="true"/>")' href="javascript:void(0);" title="<spring:message code="dir3caib.eliminar"/>"><span class="fa fa-eraser"></span></a>
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>

                        <c:if test="${ultimaSincroDirectorio != null && ultimaSincroCatalogo != null}">
                            <c:if test="${sincronizacionesCatalogo > 1 || sincronizacionesDirectorio > 1}">
                                <div class="span2">
                                    <a class="btn btn-danger" onclick='javascript:confirm("<c:url value="/sincronizacion/deleteAll"/>","<spring:message code="dir3caib.confirmar.eliminar.sincronizaciones" htmlEscape="true"/>")' href="javascript:void(0);" title="<spring:message code="sincronizacion.eliminar.todos"/>"><spring:message code="sincronizacion.eliminar.todos"/></a>
                                </div>
                            </c:if>
                        </c:if>

                        <!-- Paginacion -->
                        <div class="span9 dreta">
                            <c:import url="../modulos/paginacion.jsp">
                                <c:param name="entidad" value="sincronizacion"/>
                            </c:import>
                        </div>
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