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
                    <h2>DIR3CAIB</h2>
                </div>

                <c:import url="modulos/mensajes.jsp"/>

                <div class="box-content">

                    <c:if test="${empty sincronizaciones}">
                        No se ha realizado ninguna Sincronización, el directorio
                    </c:if>

                    <c:if test="${not empty sincronizaciones}">

                        <div class="page-header">
                            <h2><spring:message code="sincronizacion.ultimas"/></h2>
                        </div>

                        <table class="table table-bordered">
                            <thead>
                                <tr>
                                    <th><spring:message code="sincronizacion.tipo"/></th>
                                    <th><fmt:message key="dir3caib.intervalo"/> (<fmt:message key="dir3caib.fechainicio"/> - <fmt:message key="dir3caib.fechafin"/>)</th>
                                    <th><fmt:message key="dir3caib.fechaSincronizacion"/></th>
                                    <th><fmt:message key="dir3caib.estado"/></th>
                                    <th><spring:message code="sincronizacion.ficheros"/></th>
                                </tr>
                            </thead>

                            <tbody>
                                <c:forEach var="sincronizacion" items="${sincronizaciones}">
                                    <tr>
                                        <td>
                                            <c:if test="${sincronizacion.tipo == 1}"> <%--Catálogo--%>
                                                <span class="label label-info"><spring:message code="sincronizacion.tipo.${sincronizacion.tipo}"/></span>
                                            </c:if>
                                            <c:if test="${sincronizacion.tipo == 2}"> <%--Directorio--%>
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
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
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