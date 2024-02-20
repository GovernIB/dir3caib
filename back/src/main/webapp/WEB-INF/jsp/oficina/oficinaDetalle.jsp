<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/modulos/includes.jsp" %>

<!DOCTYPE html>
<html lang="ca">
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
                    <h2><spring:message code="oficina.detalle"/> ${oficina.codigo} - ${oficina.denominacion}</h2>
                </div>

                <div class="box-content pad-top0">

                    <!-- Box con Información de la Oficina  -->
                    <div class="box span3">

                        <div class="box-header well cabeceraDetalle">
                            <h2><fmt:message key="dir3caib.informacion"/></h2>
                        </div>

                        <div class="box-content pad-top0">
                            <div class="pad-bottom0">
                                <dl class="detalle">
                                    <div class="box-header well cabeceraDetalle">
                                        <h5><spring:message code="oficina.datos"/></h5>
                                    </div>
                                    <dt> <spring:message code="oficina.codigo"/>: </dt> <dd> ${oficina.codigo}</dd>
                                    <dt> <spring:message code="oficina.denominacion"/>: </dt> <dd> ${oficina.denominacion}</dd>
                                    <c:if test="${not empty oficina.denomLenguaCooficial}"><dt> <spring:message code="oficina.denominacion.cooficial"/>: </dt> <dd>${oficina.denomLenguaCooficial}</dd></c:if>
                                    <dt> <spring:message code="oficina.estado"/>: </dt>
                                        <c:if test="${oficina.estado.codigoEstadoEntidad == Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE}"><dd><span class="label label-success"><spring:message code="estado.${oficina.estado.codigoEstadoEntidad}"/></span></dd></c:if>
                                        <c:if test="${oficina.estado.codigoEstadoEntidad == Dir3caibConstantes.ESTADO_ENTIDAD_EXTINGUIDO}"><dd><span class="label label-warning"><spring:message code="estado.${oficina.estado.codigoEstadoEntidad}"/></span></dd></c:if>
                                        <c:if test="${oficina.estado.codigoEstadoEntidad == Dir3caibConstantes.ESTADO_ENTIDAD_ANULADO}"><dd><span class="label label-important"><spring:message code="estado.${oficina.estado.codigoEstadoEntidad}"/></span></dd></c:if>
                                        <c:if test="${oficina.estado.codigoEstadoEntidad == Dir3caibConstantes.ESTADO_ENTIDAD_TRANSITORIO}"><dd><span class="label label-info"><spring:message code="estado.${oficina.estado.codigoEstadoEntidad}"/></span></dd></c:if>
                                    <c:if test="${not empty oficina.nivelAdministracion.descripcionNivelAdministracion}"><dt> <spring:message code="oficina.administracion"/>: </dt> <dd> ${oficina.nivelAdministracion.descripcionNivelAdministracion}</dd></c:if>
                                    <c:if test="${not empty oficina.tipoOficina.descripcionJerarquiaOficina}"><dt> <spring:message code="oficina.tipo"/>: </dt> <dd> ${oficina.tipoOficina.descripcionJerarquiaOficina}</dd></c:if>
                                    <c:if test="${not empty oficina.codUoResponsable.codigo}"><dt> <spring:message code="oficina.unidadresponsable"/>: </dt> <dd> <a onclick="goTo('<c:url value="/unidad/${oficina.codUoResponsable.codigo}/${paginaUrl}"/>','<spring:message code="dir3caib.organismo.detalle.generar" javaScriptEscape="true"/>')" onmouseover="this.style.cursor='pointer';">${oficina.codUoResponsable.codigoDir3} v${oficina.codUoResponsable.version} - <c:choose><c:when test="${denominacionCooficial and not empty(oficina.codUoResponsable.denomLenguaCooficial)}">${oficina.codUoResponsable.denomLenguaCooficial}</c:when><c:otherwise>${oficina.codUoResponsable.denominacion}</c:otherwise></c:choose></a></dd></c:if>
                                    <c:if test="${not empty oficina.codOfiResponsable.codigo}"><dt> <spring:message code="oficina.oficinaresponsable"/>: </dt> <dd> <a onclick="goTo('<c:url value="/oficina/${oficina.codOfiResponsable.codigo}/${paginaUrl}"/>','<spring:message code="dir3caib.organismo.detalle.generar" javaScriptEscape="true"/>')" onmouseover="this.style.cursor='pointer';">${oficina.codOfiResponsable.codigo} - <c:choose><c:when test="${denominacionCooficial and not empty(oficina.codOfiResponsable.denomLenguaCooficial)}">${oficina.codOfiResponsable.denomLenguaCooficial}</c:when><c:otherwise>${oficina.codOfiResponsable.denominacion}</c:otherwise></c:choose></a></dd></c:if>
                                    <c:if test="${not empty oficina.fechaAltaOficial}"><dt> <spring:message code="oficina.fechaCreacion"/>: </dt> <dd><fmt:formatDate value="${oficina.fechaAltaOficial}" pattern="dd/MM/yyyy"/></dd></c:if>

                                    <!-- Muestra la dirección de la oficina-->
                                    <hr class="divider">
                                    <div class="box-header well cabeceraDetalle">
                                        <h5><spring:message code="oficina.direccion"/></h5>
                                    </div>
                                    <c:if test="${not empty oficina.nombreVia}"><dd> ${oficina.nombreVia}, ${oficina.numVia}</dd></c:if>
                                    <c:if test="${not empty oficina.direccionObservaciones}"><dd> ${oficina.direccionObservaciones}</dd></c:if>
                                    <c:if test="${not empty oficina.localidad.descripcionLocalidad}"><dd> ${oficina.codPostal} - ${oficina.localidad.descripcionLocalidad}</dd></c:if>
                                    <c:if test="${not empty oficina.codComunidad.descripcionComunidad}"><dd> ${oficina.codComunidad.descripcionComunidad}</dd></c:if>
                                    <c:if test="${not empty oficina.codPais.descripcionPais}"><dd> ${oficina.codPais.descripcionPais}</dd></c:if>

                                    <!-- Muestra contactos de la oficina-->
                                    <c:if test="${fn:length(oficina.contactos)>0}">
                                        <hr class="divider">
                                        <div class="box-header well cabeceraDetalle">
                                            <h5><spring:message code="oficina.contacto"/></h5>
                                        </div>
                                        <c:forEach items="${oficina.contactos}" var="contacto">
                                            <c:if test="${contacto.tipoContacto.codigoTipoContacto == Dir3caibConstantes.TIPO_CONTACTO_EMAIL}"><dt> <spring:message code="oficina.contacto.mail"/>: </dt></c:if>
                                            <c:if test="${contacto.tipoContacto.codigoTipoContacto == Dir3caibConstantes.TIPO_CONTACTO_FAX || contacto.tipoContacto.codigoTipoContacto == Dir3caibConstantes.TIPO_CONTACTO_TELEFONO}"><dt> <spring:message code="oficina.contacto.telefono"/>: </dt></c:if>
                                            <c:if test="${contacto.tipoContacto.codigoTipoContacto == Dir3caibConstantes.TIPO_CONTACTO_URL}"><dt> <spring:message code="oficina.contacto.url"/>: </dt></c:if>
                                            <dd> ${contacto.valorContacto}</dd>
                                        </c:forEach>
                                        <c:if test="${not empty oficina.horarioAtencion}"><dt> <spring:message code="oficina.horario"/>: </dt> <dd> ${oficina.horarioAtencion}</dd></c:if>
                                    </c:if>

                                    <!-- Muestra Clasificación de la oficina-->
                                    <hr class="divider">
                                    <div class="box-header well cabeceraDetalle">
                                        <h5><spring:message code="oficina.clasificacion"/></h5>
                                    </div>
                                    <c:forEach items="${oficina.servicios}" var="servicio">
                                        <c:if test="${servicio.estado.codigoEstadoEntidad == Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE}">
                                            <dt> - </dt><dd>${servicio.servicio.descServicio}</dd>
                                        </c:if>
                                    </c:forEach>

                                </dl>
                            </div>
                        </div>

                    </div>

                    <!-- Organigrama de la Oficina  -->
                    <c:import url="../organigrama/organigrama.jsp">
                        <c:param name="tipo" value="${Dir3caibConstantes.OFICINA}"/>
                    </c:import>

                </div>
            </div>
        </div>
    </div>
</div>

<c:import url="../modulos/pie.jsp"/>

</body>
</html>