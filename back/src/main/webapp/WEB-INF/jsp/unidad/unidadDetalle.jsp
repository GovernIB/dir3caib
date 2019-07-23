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
                      <h2><spring:message code="unidad.detalle"/> ${unidad.codigo} - ${unidad.denominacion}</h2>
                  </div>

                  <div class="box-content pad-top0">

                    <!-- Box con Información Detalle de la Oficina  -->
                    <div class="box span3">

                        <div class="box-header well cabeceraDetalle">
                            <h2><fmt:message key="dir3caib.informacion"/></h2>
                        </div>

                        <div class="box-content pad-top0">
                            <div class="pad-bottom0">
                                <dl class="detalle">
                                    <div class="box-header well cabeceraDetalle">
                                        <h5><spring:message code="unidad.datos"/></h5>
                                    </div>
                                    <dt> <spring:message code="unidad.codigo"/>: </dt> <dd> ${unidad.codigo}</dd>
                                    <dt> <spring:message code="unidad.denominacion"/>: </dt> <dd> ${unidad.denominacion}</dd>
                                    <dt> <spring:message code="unidad.estado"/>: </dt>
                                    <c:if test="${unidad.estado.codigoEstadoEntidad == 'V'}"><dd><span class="label label-success">${unidad.estado.descripcionEstadoEntidad}</span></dd></c:if>
                                    <c:if test="${unidad.estado.codigoEstadoEntidad == 'E'}">
                                        <dd><span class="label label-warning">${unidad.estado.descripcionEstadoEntidad}</span></dd>
                                        <dt> <spring:message code="unidad.baja"/>: </dt><dd>${unidad.observBaja}</dd>
                                    </c:if>
                                    <dt> <spring:message code="unidad.edp"/>: </dt>
                                    <c:if test="${unidad.esEdp}">
                                        <dd><p class="centrat ajustarEDP" rel="edp"
                                           data-content="<c:if test="${not empty unidad.codEdpPrincipal}">Edp Principal: <c:out value="${unidad.codEdpPrincipal.codigo} - ${unidad.codEdpPrincipal.denominacion}" escapeXml="true"/></c:if>"
                                           data-toggle="popover"><span class="label label-success">Sí</span></p></dd>
                                    </c:if>
                                    <c:if test="${!unidad.esEdp}"><dd><span class="label label-danger">No</span></dd></c:if>
                                    <c:if test="${unidad.estado.codigoEstadoEntidad == 'A'}"><dd><span class="label label-important">${unidad.estado.descripcionEstadoEntidad}</span></dd></c:if>
                                    <c:if test="${unidad.estado.codigoEstadoEntidad == 'T'}"><dd><span class="label label-info">${unidad.estado.descripcionEstadoEntidad}</span></dd></c:if>
                                    <c:if test="${not empty unidad.nivelJerarquico}"><dt> <spring:message code="unidad.nivel"/>: </dt> <dd> ${unidad.nivelJerarquico}</dd></c:if>
                                    <c:if test="${not empty unidad.nifcif}"><dt> <spring:message code="unidad.nifcif"/>: </dt> <dd> ${unidad.nifcif}</dd></c:if>
                                    <c:if test="${not empty unidad.nivelAdministracion.descripcionNivelAdministracion}"><dt> <spring:message code="unidad.administracion"/>: </dt> <dd> ${unidad.nivelAdministracion.descripcionNivelAdministracion}</dd></c:if>
                                    <c:if test="${not empty unidad.codTipoUnidad.descripcionTipoUnidadOrganica}"><dt> <spring:message code="unidad.tipo"/>: </dt> <dd> ${unidad.codTipoUnidad.descripcionTipoUnidadOrganica}</dd></c:if>
                                    <c:if test="${not empty unidad.codUnidadRaiz.codigo}"><dt> <spring:message code="unidad.unidadraiz"/>: </dt> <dd> <a onclick="goTo('<c:url value="/unidad/${unidad.codUnidadRaiz.codigo}/detalle"/>','<spring:message code="dir3caib.organismo.detalle.generar" javaScriptEscape="true"/>')" onmouseover="this.style.cursor='pointer';">${unidad.codUnidadRaiz.codigo} - ${unidad.codUnidadRaiz.denominacion}</a></dd></c:if>
                                    <c:if test="${not empty unidad.codUnidadSuperior.codigo}"><dt> <spring:message code="unidad.unidadsuperior"/>: </dt> <dd> <a onclick="goTo('<c:url value="/unidad/${unidad.codUnidadSuperior.codigo}/detalle"/>','<spring:message code="dir3caib.organismo.detalle.generar" javaScriptEscape="true"/>')" onmouseover="this.style.cursor='pointer';">${unidad.codUnidadSuperior.codigo} - ${unidad.codUnidadSuperior.denominacion}</a></dd></c:if>

                                    <hr class="divider">
                                    <div class="box-header well cabeceraDetalle">
                                        <h5><spring:message code="unidad.direccion"/></h5>
                                    </div>
                                    <c:if test="${not empty unidad.nombreVia}"><dd> ${unidad.nombreVia}, ${unidad.numVia}</dd></c:if>
                                    <c:if test="${not empty unidad.complemento}"><dd> ${unidad.complemento}</dd></c:if>
                                    <c:if test="${not empty unidad.codLocalidad.descripcionLocalidad}"><dd> ${unidad.codPostal} - ${unidad.codLocalidad.descripcionLocalidad}</dd></c:if>
                                    <c:if test="${not empty unidad.codComunidad.descripcionComunidad}"><dd> ${unidad.codComunidad.descripcionComunidad}</dd></c:if>
                                    <c:if test="${not empty unidad.codPais.descripcionPais}"><dd> ${unidad.codPais.descripcionPais}</dd></c:if>
                                    <c:if test="${empty unidad.nombreVia && empty unidad.complemento && empty unidad.codLocalidad.descripcionLocalidad && empty unidad.codComunidad.descripcionComunidad && empty unidad.codPais.descripcionPais}">
                                        <dt>&nbsp;</dt><dd><spring:message code="oficina.busqueda.vacio"/></dd>
                                    </c:if>

                                    <!-- Muestra contactos de la unidad-->
                                    <c:if test="${fn:length(unidad.contactos)>0}">
                                        <hr class="divider">
                                        <div class="box-header well cabeceraDetalle">
                                            <h5><spring:message code="unidad.contacto"/></h5>
                                        </div>
                                        <c:forEach items="${unidad.contactos}" var="contacto">
                                            <c:if test="${contacto.tipoContacto.codigoTipoContacto == 'E'}"><dt> <spring:message code="unidad.contacto.mail"/>: </dt></c:if>
                                            <c:if test="${contacto.tipoContacto.codigoTipoContacto == 'F' || contacto.tipoContacto.codigoTipoContacto == 'T'}"><dt> <spring:message code="unidad.contacto.telefono"/>: </dt></c:if>
                                            <c:if test="${contacto.tipoContacto.codigoTipoContacto == 'U'}"><dt> <spring:message code="unidad.contacto.url"/>: </dt></c:if>
                                            <dd> ${contacto.valorContacto}</dd>
                                        </c:forEach>
                                    </c:if>

                                    <!-- Muestra Ambíto de la unidad-->
                                    <hr class="divider">
                                    <div class="box-header well cabeceraDetalle">
                                        <h5><spring:message code="unidad.ambito"/></h5>
                                    </div>
                                    <c:if test="${not empty unidad.codAmbitoTerritorial}"><dt> <spring:message code="unidad.ambito.territorial"/>: </dt><dd>${unidad.codAmbitoTerritorial.descripcionAmbito}</dd></c:if>
                                    <c:if test="${not empty unidad.codAmbPais}"><dt> <spring:message code="unidad.ambito.pais"/>: </dt><dd>${unidad.codAmbPais.descripcionPais}</dd></c:if>
                                    <c:if test="${not empty unidad.codAmbComunidad}"><dt> <spring:message code="unidad.ambito.ca"/>: </dt><dd>${unidad.codAmbComunidad.descripcionComunidad}</dd></c:if>
                                    <c:if test="${not empty unidad.codAmbProvincia}"><dt> <spring:message code="unidad.ambito.provincia"/>: </dt><dd>${unidad.codAmbProvincia.descripcionProvincia}</dd></c:if>
                                    <c:if test="${not empty unidad.codAmbIsla}"><dt> <spring:message code="unidad.ambito.isla"/>: </dt><dd>${unidad.codAmbIsla.descripcionIsla}</dd></c:if>

                                    <!-- Muestra Oficinas relacionadas de la unidad-->
                                    <!-- Equivalen a las Oficinas Organizativas Ofi (organizativaOfi de dir3) VIGENTES-->
                                    <hr class="divider">
                                    <div class="box-header well cabeceraDetalle">
                                        <h5><spring:message code="unidad.oficinas"/></h5>
                                    </div>
                                    <c:forEach items="${unidad.organizativaOfi}" var="oficina">
                                        <c:if test="${oficina.estado.codigoEstadoEntidad == 'V'}">
                                            <dt> - </dt><dd>${oficina.oficina.codigo} - ${oficina.oficina.denominacion}</dd>
                                        </c:if>
                                    </c:forEach>
                                    <c:if test="${empty unidad.organizativaOfi}">
                                        <dt>&nbsp;</dt><dd><spring:message code="oficina.busqueda.vacio"/></dd>
                                    </c:if>

                                    <!-- Muestra Oficinas que registran la unidad-->
                                    <hr class="divider">
                                    <div class="box-header well cabeceraDetalle">
                                        <h5><spring:message code="unidad.registren"/></h5>
                                    </div>
                                    <c:set var="sinOficinas" scope="session" value="false"/>
                                    <c:forEach items="${oficinasRegistran}" var="oficinaRegistra">
                                        <c:forEach items="${oficinaRegistra.servicios}" var="servicio">
                                            <c:if test="${servicio.codServicio == 2}">
                                                <c:set var="sinOficinas" value="true"/>
                                                <dt> - </dt><dd>${oficinaRegistra.codigo} - ${oficinaRegistra.denominacion}</dd>
                                            </c:if>
                                        </c:forEach>
                                    </c:forEach>
                                    <c:if test="${empty oficinasRegistran || !sinOficinas}">
                                        <dt>&nbsp;</dt><dd><spring:message code="oficina.busqueda.vacio"/></dd>
                                    </c:if>

                                </dl>
                            </div>
                        </div>

                    </div>

                    <!-- Box con el árbol de la Oficina  -->
                    <div class="box span9 minAlt">
                        <div class="box-header well cabeceraDetalle">
                            <h2><fmt:message key="dir3caib.arbol"/></h2>
                        </div>

                        <!-- LEYENDA -->
                        <div class="button-right">
                            <div class="box llegenda">
                                <div class="box-header well">
                                    <div class="col-xs-12">
                                        <i class="fa fa-comment-o"></i> <strong><spring:message code="dir3caib.leyenda"/></strong>
                                    </div>
                                </div>

                                <div class="box-content">
                                    <div class="pad-bottom5">
                                        <span class="badge-arbre btn-primary llegendaCapsa"><i class="fa fa-globe"></i> <spring:message code="dir3caib.unidadOrganica"/></span>
                                    </div>

                                    <div class="pad-bottom5">
                                        <span class="badge-arbre btn-edp llegendaCapsa"><i class="fa fa-globe"></i> <spring:message code="dir3caib.edp"/></span>
                                    </div>

                                    <c:if test="${not empty oficinasPrincipales}">
                                        <div class="pad-bottom5">
                                            <span class="badge-arbre btn-warning llegendaCapsa"><i class="fa fa-home"></i> <spring:message code="dir3caib.oficina.principal"/></span>
                                        </div>
                                    </c:if>

                                    <c:if test="${not empty oficinasAuxiliares}">
                                        <div class="pad-bottom5">
                                            <span class="badge-arbre btn-ofaux llegendaCapsa"><i class="fa fa-home"></i> <spring:message code="dir3caib.oficina.auxiliar"/></span>
                                        </div>
                                    </c:if>

                                    <c:if test="${not empty relacionesSirOfi}">
                                        <div class="pad-bottom5">
                                            <span class="badge-arbre btn-ofsir llegendaCapsa"><i class="fa fa-exchange"></i> <spring:message code="oficina.oficina"/> <img src="<c:url value="/img/logo-SIR.png"/>" width="20" alt="SIR" title="SIR"/></span>
                                        </div>
                                    </c:if>

                                    <c:if test="${not empty relacionesOrganizativaOfi}">
                                        <div class="pad-bottom5">
                                                <span class="badge-arbre btn-success llegendaCapsa"><i class="fa fa-institution"></i> <spring:message
                                                        code="dir3caib.oficina.organizativa"/></span>
                                        </div>
                                    </c:if>
                                </div>
                            </div>
                        </div>

                        <div class="btn-group infoBranca opcionArbol">
                            <button type="button" class="btn btn-primary infoBranca tamany12" onclick="amaga(${fn:length(unidadesSegundoNivel)},${fn:length(unidadesTercerNivel)},${fn:length(unidadesCuartoNivel)})"><i class="fa fa-sitemap fa-rotate-180"></i> <spring:message code="dir3caib.arbol.cierra"/></button>
                        </div>
                        <div class="btn-group infoBranca opcionArbol">
                            <button type="button" class="btn btn-info infoBranca tamany12" onclick="goTo('<c:url value="/unidad/${unidad.codigo}/detalle"/>')"><i class="fa fa-sitemap"></i> <spring:message code="dir3caib.arbol.abre"/></button>
                        </div>
                        <div class="btn-group infoBranca">
                            <button type="button" id="infoCopy" class="btn infoBranca tamany12" disabled style="cursor:default"><i class="fa fa-info-circle colophon">  <spring:message code="dir3caib.organismo.arbol.copiar"/></i></button>
                        </div>

                        <div class="tree">
                            <ul>
                                <li>
                                        <span class="panel-heading btn-danger vuitanta-percent" id="entidad" onclick="copyToClipboard(this)" style="cursor:copy"><i
                                                class=""></i> ${unidadRaiz.codigo} - ${unidadRaiz.denominacion}</span>
                                    <ul>

                                        <c:set var="contadorPrimer" value="0"></c:set>
                                        <c:set var="contadorSegon" value="0"></c:set>
                                        <c:set var="contadorTercer" value="0"></c:set>
                                        <c:set var="contadorQuart" value="0"></c:set>
                                        <c:set var="contadorCinque" value="0"></c:set>
                                        <c:set var="contadorSise" value="0"></c:set>

                                        <c:forEach var="organismo1" items="${unidadesPrimerNivel}">
                                            <li>
                                                <c:if test="${organismo1.esEdp == false}">
                                                        <span class="panel-heading btn-primary vuitanta-percent" id="zeroNivell"
                                                              style="cursor:copy" onclick="copyToClipboard(this)"><i
                                                                class=""></i> ${organismo1.codigo} - ${organismo1.denominacion}</span>
                                                </c:if>
                                                <c:if test="${organismo1.esEdp == true}">
                                                        <span class="panel-heading btn-edp vuitanta-percent" id="zeroNivell"
                                                              style="cursor:copy" onclick="copyToClipboard(this)"><i
                                                                class=""></i> ${organismo1.codigo} - ${organismo1.denominacion}</span>
                                                </c:if>

                                                <ul>

                                                    <!-- **** Oficinas ***-->
                                                    <c:forEach var="oficinaPrincipal" items="${oficinasPrincipales}">
                                                        <c:if test="${oficinaPrincipal.codUoResponsable.codigo == organismo1.codigo}">
                                                            <li>
                                                                <a href="javascript:void(0);"><span
                                                                        class="panel-heading btn-warning vuitanta-percent"
                                                                        style="cursor:copy" onclick="copyToClipboard(this)"><i
                                                                        class="fa fa-home"></i> ${oficinaPrincipal.codigo} - ${oficinaPrincipal.denominacion}</span></a>

                                                                <c:forEach var="relacionSirOfi" items="${relacionesSirOfi}">
                                                                    <c:if test="${relacionSirOfi.unidad.codigo == organismo1.codigo}">
                                                                        <c:if test="${oficinaPrincipal.codigo == relacionSirOfi.oficina.codigo}">
                                                                            <a href="javascript:void(0);"><span
                                                                                    class="panel-heading btn-ofsir vuitanta-percent"
                                                                                    style="cursor:copy"><img src="<c:url value="/img/logo-SIR.png"/>" width="20" alt="SIR" title="SIR"/></span></a>
                                                                        </c:if>
                                                                    </c:if>
                                                                </c:forEach>

                                                                <ul>
                                                                    <c:forEach var="oficinaAuxiliar" items="${oficinasAuxiliares}">
                                                                        <c:if test="${oficinaAuxiliar.codOfiResponsable.codigo == oficinaPrincipal.codigo}">
                                                                            <li>
                                                                                <a href="javascript:void(0);"><span
                                                                                        class="panel-heading btn-ofaux vuitanta-percent"
                                                                                        style="cursor:copy" onclick="copyToClipboard(this)"><i
                                                                                        class="fa fa-home"></i> ${oficinaAuxiliar.codigo} - ${oficinaAuxiliar.denominacion}</span></a>

                                                                                <c:forEach var="relacionOrganizativaOfi" items="${relacionesOrganizativaOfi}">
                                                                                    <c:if test="${relacionOrganizativaOfi.unidad.codigo == organismo1.codigo}">
                                                                                        <c:if test="${oficinaAuxiliar.codigo == relacionOrganizativaOfi.oficina.codigo}">
                                                                                            <a href="javascript:void(0);"><span
                                                                                                    class="panel-heading btn-success vuitanta-percent"
                                                                                                    style="cursor:copy"><i
                                                                                                    class="fa fa-institution"></i></span></a>
                                                                                        </c:if>
                                                                                    </c:if>
                                                                                </c:forEach>

                                                                                <ul>
                                                                                    <c:forEach var="oficinaAuxiliar2" items="${oficinasAuxiliares}">
                                                                                        <c:if test="${oficinaAuxiliar2.codOfiResponsable.codigo == oficinaAuxiliar.codigo}">
                                                                                            <li>
                                                                                                <a href="javascript:void(0);"><span
                                                                                                        class="panel-heading btn-ofaux vuitanta-percent"
                                                                                                        style="cursor:copy" onclick="copyToClipboard(this)"><i
                                                                                                        class="fa fa-home"></i> ${oficinaAuxiliar2.codigo} - ${oficinaAuxiliar2.denominacion}</span></a>
                                                                                            </li>
                                                                                        </c:if>
                                                                                    </c:forEach>
                                                                                </ul>
                                                                            </li>
                                                                        </c:if>
                                                                    </c:forEach>
                                                                </ul>
                                                            </li>
                                                        </c:if>
                                                    </c:forEach>
                                                    <!-- **** Oficinas Funcionales/Organizativas ***-->
                                                    <c:forEach var="relacionOrganizativaOfi" items="${relacionesOrganizativaOfi}">
                                                        <c:if test="${relacionOrganizativaOfi.unidad.codigo == organismo1.codigo}">
                                                            <c:if test="${relacionOrganizativaOfi.unidad.codUnidadRaiz.codigo == relacionOrganizativaOfi.oficina.codUoResponsable.codigo}">
                                                                <li>
                                                                    <a href="javascript:void(0);"><span
                                                                            class="panel-heading btn-success vuitanta-percent"
                                                                            style="cursor:copy" onclick="copyToClipboard(this)"><i
                                                                            class="fa fa-institution"></i> ${relacionOrganizativaOfi.oficina.codigo} - ${relacionOrganizativaOfi.oficina.denominacion}</span></a>
                                                                </li>
                                                            </c:if>
                                                        </c:if>
                                                    </c:forEach>
                                                    <!-- **** Oficinas Sir ***-->
                                                    <c:forEach var="relacionSirOfi" items="${relacionesSirOfi}">
                                                        <c:if test="${relacionSirOfi.unidad.codigo == organismo1.codigo}">
                                                            <c:if test="${relacionSirOfi.oficina.codUoResponsable.codigo != organismo1.codigo}">
                                                                <li>
                                                                    <a href="javascript:void(0);"><span
                                                                            class="panel-heading btn-ofsir vuitanta-percent"
                                                                            style="cursor:copy" onclick="copyToClipboard(this)"><i
                                                                            class="fa fa-exchange"></i> ${relacionSirOfi.oficina.codigo} - ${relacionSirOfi.oficina.denominacion} <img src="<c:url value="/img/logo-SIR.png"/>" width="20" alt="SIR" title="SIR"/></span></a>
                                                                </li>
                                                            </c:if>
                                                        </c:if>
                                                    </c:forEach>

                                                    <c:forEach var="organismo2" items="${unidadesSegundoNivel}">
                                                        <c:if test="${organismo2.codUnidadSuperior.codigo == organismo1.codigo}">
                                                            <li>

                                                                <c:if test="${organismo2.esEdp  == false}">
                                                                        <span class="panel-heading btn-primary vuitanta-percent"
                                                                              id="primerNivell${contadorPrimer}"
                                                                              style="cursor:copy" onclick="copyToClipboard(this)"><i
                                                                                class=""></i> ${organismo2.codigo} - ${organismo2.denominacion}</span>
                                                                </c:if>
                                                                <c:if test="${organismo2.esEdp == true}">
                                                                        <span class="panel-heading btn-edp vuitanta-percent"
                                                                              id="primerNivell${contadorPrimer}"
                                                                              style="cursor:copy" onclick="copyToClipboard(this)"><i
                                                                                class=""></i> ${organismo2.codigo} - ${organismo2.denominacion}</span>
                                                                </c:if>

                                                                <c:set var="contadorPrimer" value="${contadorPrimer+1}"></c:set>
                                                                <ul>

                                                                    <!-- **** Oficinas ***-->
                                                                    <c:forEach var="oficinaPrincipal" items="${oficinasPrincipales}">
                                                                        <c:if test="${oficinaPrincipal.codUoResponsable.codigo == organismo2.codigo}">
                                                                            <li>
                                                                                <a href="javascript:void(0);"><span
                                                                                        class="panel-heading btn-warning vuitanta-percent"
                                                                                        style="cursor:copy" onclick="copyToClipboard(this)"><i
                                                                                        class="fa fa-home"></i> ${oficinaPrincipal.codigo} - ${oficinaPrincipal.denominacion}</span></a>

                                                                                <c:forEach var="relacionSirOfi" items="${relacionesSirOfi}">
                                                                                    <c:if test="${relacionSirOfi.unidad.codigo == organismo2.codigo}">
                                                                                        <c:if test="${oficinaPrincipal.codigo == relacionSirOfi.oficina.codigo}">
                                                                                            <a href="javascript:void(0);"><span
                                                                                                    class="panel-heading btn-ofsir vuitanta-percent"
                                                                                                    style="cursor:copy"><img src="<c:url value="/img/logo-SIR.png"/>" width="20" alt="SIR" title="SIR"/></span></a>
                                                                                        </c:if>
                                                                                    </c:if>
                                                                                </c:forEach>

                                                                                <ul>
                                                                                    <c:forEach var="oficinaAuxiliar" items="${oficinasAuxiliares}">
                                                                                        <c:if test="${oficinaAuxiliar.codOfiResponsable.codigo == oficinaPrincipal.codigo}">
                                                                                            <li>
                                                                                                <a href="javascript:void(0);"><span
                                                                                                        class="panel-heading btn-ofaux vuitanta-percent"
                                                                                                        style="cursor:copy" onclick="copyToClipboard(this)"><i
                                                                                                        class="fa fa-home"></i> ${oficinaAuxiliar.codigo} - ${oficinaAuxiliar.denominacion}</span></a>

                                                                                                <c:forEach var="relacionOrganizativaOfi" items="${relacionesOrganizativaOfi}">
                                                                                                    <c:if test="${relacionOrganizativaOfi.unidad.codigo == organismo2.codigo}">
                                                                                                        <c:if test="${oficinaAuxiliar.codigo == relacionOrganizativaOfi.oficina.codigo}">
                                                                                                            <a href="javascript:void(0);"><span
                                                                                                                    class="panel-heading btn-success vuitanta-percent"
                                                                                                                    style="cursor:copy"><i
                                                                                                                    class="fa fa-institution"></i></span></a>
                                                                                                        </c:if>
                                                                                                    </c:if>
                                                                                                </c:forEach>

                                                                                                <ul>
                                                                                                    <c:forEach var="oficinaAuxiliar2" items="${oficinasAuxiliares}">
                                                                                                        <c:if test="${oficinaAuxiliar2.codOfiResponsable.codigo == oficinaAuxiliar.codigo}">
                                                                                                            <li>
                                                                                                                <a href="javascript:void(0);"><span
                                                                                                                        class="panel-heading btn-ofaux vuitanta-percent"
                                                                                                                        style="cursor:copy" onclick="copyToClipboard(this)"><i
                                                                                                                        class="fa fa-home"></i> ${oficinaAuxiliar2.codigo} - ${oficinaAuxiliar2.denominacion}</span></a>
                                                                                                            </li>
                                                                                                        </c:if>
                                                                                                    </c:forEach>
                                                                                                </ul>
                                                                                            </li>
                                                                                        </c:if>
                                                                                    </c:forEach>
                                                                                </ul>
                                                                            </li>
                                                                        </c:if>
                                                                    </c:forEach>
                                                                    <!-- **** Oficinas Funcionales/Organizativas ***-->
                                                                    <c:forEach var="relacionOrganizativaOfi" items="${relacionesOrganizativaOfi}">
                                                                        <c:if test="${relacionOrganizativaOfi.unidad.codigo == organismo2.codigo}">
                                                                            <c:if test="${relacionOrganizativaOfi.unidad.codUnidadRaiz.codigo == relacionOrganizativaOfi.oficina.codUoResponsable.codigo}">
                                                                                <li>
                                                                                    <a href="javascript:void(0);"><span
                                                                                            class="panel-heading btn-success vuitanta-percent"
                                                                                            style="cursor:copy" onclick="copyToClipboard(this)"><i
                                                                                            class="fa fa-institution"></i> ${relacionOrganizativaOfi.oficina.codigo} - ${relacionOrganizativaOfi.oficina.denominacion}</span></a>
                                                                                </li>
                                                                            </c:if>
                                                                        </c:if>
                                                                    </c:forEach>
                                                                    <!-- **** Oficinas Sir ***-->
                                                                    <c:forEach var="relacionSirOfi" items="${relacionesSirOfi}">
                                                                        <c:if test="${relacionSirOfi.unidad.codigo == organismo2.codigo}">
                                                                            <c:if test="${relacionSirOfi.oficina.codUoResponsable.codigo != organismo2.codigo}">
                                                                                <li>
                                                                                    <a href="javascript:void(0);"><span
                                                                                            class="panel-heading btn-ofsir vuitanta-percent"
                                                                                            style="cursor:copy" onclick="copyToClipboard(this)"><i
                                                                                            class="fa fa-exchange"></i> ${relacionSirOfi.oficina.codigo} - ${relacionSirOfi.oficina.denominacion} <img src="<c:url value="/img/logo-SIR.png"/>" width="20" alt="SIR" title="SIR"/></span></a>
                                                                                </li>
                                                                            </c:if>
                                                                        </c:if>
                                                                    </c:forEach>

                                                                    <c:forEach var="organismo3" items="${unidadesTercerNivel}">
                                                                        <c:if test="${organismo3.codUnidadSuperior.codigo == organismo2.codigo}">
                                                                            <li>

                                                                                <c:if test="${organismo3.esEdp == false}">
                                                                                        <span class="panel-heading btn-primary vuitanta-percent"
                                                                                              id="segonNivell${contadorSegon}"
                                                                                              style="cursor:copy" onclick="copyToClipboard(this)"><i
                                                                                                class=""></i> ${organismo3.codigo} - ${organismo3.denominacion}</span>
                                                                                </c:if>
                                                                                <c:if test="${organismo3.esEdp == true}">
                                                                                        <span class="panel-heading btn-edp vuitanta-percent"
                                                                                              id="segonNivell${contadorSegon}"
                                                                                              style="cursor:copy" onclick="copyToClipboard(this)"><i
                                                                                                class=""></i> ${organismo3.codigo} - ${organismo3.denominacion}</span>
                                                                                </c:if>

                                                                                <c:set var="contadorSegon" value="${contadorSegon+1}"></c:set>
                                                                                <ul>

                                                                                    <!-- **** Oficinas ***-->
                                                                                    <c:forEach var="oficinaPrincipal" items="${oficinasPrincipales}">
                                                                                        <c:if test="${oficinaPrincipal.codUoResponsable.codigo == organismo3.codigo}">
                                                                                            <li>
                                                                                                <a href="javascript:void(0);"><span
                                                                                                        class="panel-heading btn-warning vuitanta-percent"
                                                                                                        style="cursor:copy" onclick="copyToClipboard(this)"><i
                                                                                                        class="fa fa-home"></i> ${oficinaPrincipal.codigo} - ${oficinaPrincipal.denominacion}</span></a>

                                                                                                <c:forEach var="relacionSirOfi" items="${relacionesSirOfi}">
                                                                                                    <c:if test="${relacionSirOfi.unidad.codigo == organismo3.codigo}">
                                                                                                        <c:if test="${oficinaPrincipal.codigo == relacionSirOfi.oficina.codigo}">
                                                                                                            <a href="javascript:void(0);"><span
                                                                                                                    class="panel-heading btn-ofsir vuitanta-percent"
                                                                                                                    style="cursor:copy"><img src="<c:url value="/img/logo-SIR.png"/>" width="20" alt="SIR" title="SIR"/></span></a>
                                                                                                        </c:if>
                                                                                                    </c:if>
                                                                                                </c:forEach>

                                                                                                <ul>
                                                                                                    <c:forEach var="oficinaAuxiliar" items="${oficinasAuxiliares}">
                                                                                                        <c:if test="${oficinaAuxiliar.codOfiResponsable.codigo == oficinaPrincipal.codigo}">
                                                                                                            <li>
                                                                                                                <a href="javascript:void(0);"><span
                                                                                                                        class="panel-heading btn-ofaux vuitanta-percent"
                                                                                                                        style="cursor:copy" onclick="copyToClipboard(this)"><i
                                                                                                                        class="fa fa-home"></i> ${oficinaAuxiliar.codigo} - ${oficinaAuxiliar.denominacion}</span></a>

                                                                                                                <c:forEach var="relacionOrganizativaOfi" items="${relacionesOrganizativaOfi}">
                                                                                                                    <c:if test="${relacionOrganizativaOfi.unidad.codigo == organismo3.codigo}">
                                                                                                                        <c:if test="${oficinaAuxiliar.codigo == relacionOrganizativaOfi.oficina.codigo}">
                                                                                                                            <a href="javascript:void(0);"><span
                                                                                                                                    class="panel-heading btn-success vuitanta-percent"
                                                                                                                                    style="cursor:copy"><i
                                                                                                                                    class="fa fa-institution"></i></span></a>
                                                                                                                        </c:if>
                                                                                                                    </c:if>
                                                                                                                </c:forEach>

                                                                                                                <ul>
                                                                                                                    <c:forEach var="oficinaAuxiliar2" items="${oficinasAuxiliares}">
                                                                                                                        <c:if test="${oficinaAuxiliar2.codOfiResponsable.codigo == oficinaAuxiliar.codigo}">
                                                                                                                            <li>
                                                                                                                                <a href="javascript:void(0);"><span
                                                                                                                                        class="panel-heading btn-ofaux vuitanta-percent"
                                                                                                                                        style="cursor:copy" onclick="copyToClipboard(this)"><i
                                                                                                                                        class="fa fa-home"></i> ${oficinaAuxiliar2.codigo} - ${oficinaAuxiliar2.denominacion}</span></a>
                                                                                                                            </li>
                                                                                                                        </c:if>
                                                                                                                    </c:forEach>
                                                                                                                </ul>
                                                                                                            </li>
                                                                                                        </c:if>
                                                                                                    </c:forEach>
                                                                                                </ul>
                                                                                            </li>
                                                                                        </c:if>
                                                                                    </c:forEach>
                                                                                    <!-- **** Oficinas Funcionales/Organizativas ***-->
                                                                                    <c:forEach var="relacionOrganizativaOfi" items="${relacionesOrganizativaOfi}">
                                                                                        <c:if test="${relacionOrganizativaOfi.unidad.codigo == organismo3.codigo}">
                                                                                            <c:if test="${relacionOrganizativaOfi.unidad.codUnidadRaiz.codigo == relacionOrganizativaOfi.oficina.codUoResponsable.codigo}">
                                                                                                <li>
                                                                                                    <a href="javascript:void(0);"><span
                                                                                                            class="panel-heading btn-success vuitanta-percent"
                                                                                                            style="cursor:copy" onclick="copyToClipboard(this)"><i
                                                                                                            class="fa fa-institution"></i> ${relacionOrganizativaOfi.oficina.codigo} - ${relacionOrganizativaOfi.oficina.denominacion}</span></a>
                                                                                                </li>
                                                                                            </c:if>
                                                                                        </c:if>
                                                                                    </c:forEach>
                                                                                    <!-- **** Oficinas Sir ***-->
                                                                                    <c:forEach var="relacionSirOfi" items="${relacionesSirOfi}">
                                                                                        <c:if test="${relacionSirOfi.unidad.codigo == organismo3.codigo}">
                                                                                            <c:if test="${relacionSirOfi.oficina.codUoResponsable.codigo != organismo3.codigo}">
                                                                                                <li>
                                                                                                    <a href="javascript:void(0);"><span
                                                                                                            class="panel-heading btn-ofsir vuitanta-percent"
                                                                                                            style="cursor:copy" onclick="copyToClipboard(this)"><i
                                                                                                            class="fa fa-exchange"></i> ${relacionSirOfi.oficina.codigo} - ${relacionSirOfi.oficina.denominacion} <img src="<c:url value="/img/logo-SIR.png"/>" width="20" alt="SIR" title="SIR"/></span></a>
                                                                                                </li>
                                                                                            </c:if>
                                                                                        </c:if>
                                                                                    </c:forEach>

                                                                                    <c:forEach var="organismo4" items="${unidadesCuartoNivel}">
                                                                                        <c:if test="${organismo4.codUnidadSuperior.codigo == organismo3.codigo}">
                                                                                            <li>

                                                                                                <c:if test="${organismo4.esEdp == false}">
                                                                                                        <span class="panel-heading btn-primary vuitanta-percent"
                                                                                                              id="tercerNivell${contadorTercer}"
                                                                                                              style="cursor:copy" onclick="copyToClipboard(this)"><i
                                                                                                                class=""></i> ${organismo4.codigo} - ${organismo4.denominacion}</span>
                                                                                                </c:if>
                                                                                                <c:if test="${organismo4.esEdp == true}">
                                                                                                        <span class="panel-heading btn-edp vuitanta-percent"
                                                                                                              id="tercerNivell${contadorTercer}"
                                                                                                              style="cursor:copy" onclick="copyToClipboard(this)"><i
                                                                                                                class=""></i> ${organismo4.codigo} - ${organismo4.denominacion}</span>
                                                                                                </c:if>

                                                                                                <c:set var="contadorTercer" value="${contadorTercer+1}"></c:set>
                                                                                                <ul>

                                                                                                    <!-- **** Oficinas ***-->
                                                                                                    <c:forEach var="oficinaPrincipal" items="${oficinasPrincipales}">
                                                                                                        <c:if test="${oficinaPrincipal.codUoResponsable.codigo == organismo4.codigo}">
                                                                                                            <li>
                                                                                                                <a href="javascript:void(0);"><span
                                                                                                                        class="panel-heading btn-warning vuitanta-percent"
                                                                                                                        style="cursor:copy" onclick="copyToClipboard(this)"><i
                                                                                                                        class="fa fa-home"></i> ${oficinaPrincipal.codigo} - ${oficinaPrincipal.denominacion}</span></a>

                                                                                                                <c:forEach var="relacionSirOfi" items="${relacionesSirOfi}">
                                                                                                                    <c:if test="${relacionSirOfi.unidad.codigo == organismo4.codigo}">
                                                                                                                        <c:if test="${oficinaPrincipal.codigo == relacionSirOfi.oficina.codigo}">
                                                                                                                            <a href="javascript:void(0);"><span
                                                                                                                                    class="panel-heading btn-ofsir vuitanta-percent"
                                                                                                                                    style="cursor:copy"><img src="<c:url value="/img/logo-SIR.png"/>" width="20" alt="SIR" title="SIR"/></span></a>
                                                                                                                        </c:if>
                                                                                                                    </c:if>
                                                                                                                </c:forEach>

                                                                                                                <ul>
                                                                                                                    <c:forEach var="oficinaAuxiliar" items="${oficinasAuxiliares}">
                                                                                                                        <c:if test="${oficinaAuxiliar.codOfiResponsable.codigo == oficinaPrincipal.codigo}">
                                                                                                                            <li>
                                                                                                                                <a href="javascript:void(0);"><span
                                                                                                                                        class="panel-heading btn-ofaux vuitanta-percent"
                                                                                                                                        style="cursor:copy" onclick="copyToClipboard(this)"><i
                                                                                                                                        class="fa fa-home"></i> ${oficinaAuxiliar.codigo} - ${oficinaAuxiliar.denominacion}</span></a>

                                                                                                                                <c:forEach var="relacionOrganizativaOfi" items="${relacionesOrganizativaOfi}">
                                                                                                                                    <c:if test="${relacionOrganizativaOfi.unidad.codigo == organismo4.codigo}">
                                                                                                                                        <c:if test="${oficinaAuxiliar.codigo == relacionOrganizativaOfi.oficina.codigo}">
                                                                                                                                            <a href="javascript:void(0);"><span
                                                                                                                                                    class="panel-heading btn-success vuitanta-percent"
                                                                                                                                                    style="cursor:copy"><i
                                                                                                                                                    class="fa fa-institution"></i></span></a>
                                                                                                                                        </c:if>
                                                                                                                                    </c:if>
                                                                                                                                </c:forEach>

                                                                                                                                <ul>
                                                                                                                                    <c:forEach var="oficinaAuxiliar2" items="${oficinasAuxiliares}">
                                                                                                                                        <c:if test="${oficinaAuxiliar2.codOfiResponsable.codigo == oficinaAuxiliar.codigo}">
                                                                                                                                            <li>
                                                                                                                                                <a href="javascript:void(0);"><span
                                                                                                                                                        class="panel-heading btn-ofaux vuitanta-percent"
                                                                                                                                                        style="cursor:copy" onclick="copyToClipboard(this)"><i
                                                                                                                                                        class="fa fa-home"></i> ${oficinaAuxiliar2.codigo} - ${oficinaAuxiliar2.denominacion}</span></a>
                                                                                                                                            </li>
                                                                                                                                        </c:if>
                                                                                                                                    </c:forEach>
                                                                                                                                </ul>
                                                                                                                            </li>
                                                                                                                        </c:if>
                                                                                                                    </c:forEach>
                                                                                                                </ul>
                                                                                                            </li>
                                                                                                        </c:if>
                                                                                                    </c:forEach>
                                                                                                    <!-- **** Oficinas Funcionales/Organizativas ***-->
                                                                                                    <c:forEach var="relacionOrganizativaOfi" items="${relacionesOrganizativaOfi}">
                                                                                                        <c:if test="${relacionOrganizativaOfi.unidad.codigo == organismo4.codigo}">
                                                                                                            <c:if test="${relacionOrganizativaOfi.unidad.codUnidadRaiz.codigo == relacionOrganizativaOfi.oficina.codUoResponsable.codigo}">
                                                                                                                <li>
                                                                                                                    <a href="javascript:void(0);"><span
                                                                                                                            class="panel-heading btn-success vuitanta-percent"
                                                                                                                            style="cursor:copy" onclick="copyToClipboard(this)"><i
                                                                                                                            class="fa fa-institution"></i> ${relacionOrganizativaOfi.oficina.codigo} - ${relacionOrganizativaOfi.oficina.denominacion}</span></a>
                                                                                                                </li>
                                                                                                            </c:if>
                                                                                                        </c:if>
                                                                                                    </c:forEach>
                                                                                                    <!-- **** Oficinas Sir ***-->
                                                                                                    <c:forEach var="relacionSirOfi" items="${relacionesSirOfi}">
                                                                                                        <c:if test="${relacionSirOfi.unidad.codigo == organismo4.codigo}">
                                                                                                            <c:if test="${relacionSirOfi.oficina.codUoResponsable.codigo != organismo4.codigo}">
                                                                                                                <li>
                                                                                                                    <a href="javascript:void(0);"><span
                                                                                                                            class="panel-heading btn-ofsir vuitanta-percent"
                                                                                                                            style="cursor:copy" onclick="copyToClipboard(this)"><i
                                                                                                                            class="fa fa-exchange"></i> ${relacionSirOfi.oficina.codigo} - ${relacionSirOfi.oficina.denominacion} <img src="<c:url value="/img/logo-SIR.png"/>" width="20" alt="SIR" title="SIR"/></span></a>
                                                                                                                </li>
                                                                                                            </c:if>
                                                                                                        </c:if>
                                                                                                    </c:forEach>

                                                                                                    <c:forEach var="organismo5" items="${unidadesQuintoNivel}">
                                                                                                        <c:if test="${organismo5.codUnidadSuperior.codigo == organismo4.codigo}">
                                                                                                            <li>

                                                                                                                <c:if test="${organismo5.esEdp == false}">
                                                                                                                        <span class="panel-heading btn-primary vuitanta-percent"
                                                                                                                              id="quartNivell${contadorQuart}"
                                                                                                                              style="cursor:copy" onclick="copyToClipboard(this)"><i
                                                                                                                                class=""></i> ${organismo5.codigo} - ${organismo5.denominacion}</span>
                                                                                                                </c:if>
                                                                                                                <c:if test="${organismo5.esEdp == true}">
                                                                                                                        <span class="panel-heading btn-edp vuitanta-percent"
                                                                                                                              id="quartNivell${contadorQuart}"
                                                                                                                              style="cursor:copy" onclick="copyToClipboard(this)"><i
                                                                                                                                class=""></i> ${organismo5.codigo} - ${organismo5.denominacion}</span>
                                                                                                                </c:if>

                                                                                                                <c:set var="contadorQuart" value="${contadorQuart+1}"></c:set>
                                                                                                                <ul>

                                                                                                                    <!-- **** Oficinas ***-->
                                                                                                                    <c:forEach var="oficinaPrincipal" items="${oficinasPrincipales}">
                                                                                                                        <c:if test="${oficinaPrincipal.codUoResponsable.codigo == organismo5.codigo}">
                                                                                                                            <li>
                                                                                                                                <a href="javascript:void(0);"><span
                                                                                                                                        class="panel-heading btn-warning vuitanta-percent"
                                                                                                                                        style="cursor:copy" onclick="copyToClipboard(this)"><i
                                                                                                                                        class="fa fa-home"></i> ${oficinaPrincipal.codigo} - ${oficinaPrincipal.denominacion}</span></a>

                                                                                                                                <c:forEach var="relacionSirOfi" items="${relacionesSirOfi}">
                                                                                                                                    <c:if test="${relacionSirOfi.unidad.codigo == organismo5.codigo}">
                                                                                                                                        <c:if test="${oficinaPrincipal.codigo == relacionSirOfi.oficina.codigo}">
                                                                                                                                            <a href="javascript:void(0);"><span
                                                                                                                                                    class="panel-heading btn-ofsir vuitanta-percent"
                                                                                                                                                    style="cursor:copy"><img src="<c:url value="/img/logo-SIR.png"/>" width="20" alt="SIR" title="SIR"/></span></a>
                                                                                                                                        </c:if>
                                                                                                                                    </c:if>
                                                                                                                                </c:forEach>

                                                                                                                                <ul>
                                                                                                                                    <c:forEach var="oficinaAuxiliar" items="${oficinasAuxiliares}">
                                                                                                                                        <c:if test="${oficinaAuxiliar.codOfiResponsable.codigo == oficinaPrincipal.codigo}">
                                                                                                                                            <li>
                                                                                                                                                <a href="javascript:void(0);"><span
                                                                                                                                                        class="panel-heading btn-ofaux vuitanta-percent"
                                                                                                                                                        style="cursor:copy" onclick="copyToClipboard(this)"><i
                                                                                                                                                        class="fa fa-home"></i> ${oficinaAuxiliar.codigo} - ${oficinaAuxiliar.denominacion}</span></a>

                                                                                                                                                <c:forEach var="relacionOrganizativaOfi" items="${relacionesOrganizativaOfi}">
                                                                                                                                                    <c:if test="${relacionOrganizativaOfi.unidad.codigo == organismo5.codigo}">
                                                                                                                                                        <c:if test="${oficinaAuxiliar.codigo == relacionOrganizativaOfi.oficina.codigo}">
                                                                                                                                                            <a href="javascript:void(0);"><span
                                                                                                                                                                    class="panel-heading btn-success vuitanta-percent"
                                                                                                                                                                    style="cursor:copy"><i
                                                                                                                                                                    class="fa fa-institution"></i></span></a>
                                                                                                                                                        </c:if>
                                                                                                                                                    </c:if>
                                                                                                                                                </c:forEach>

                                                                                                                                                <ul>
                                                                                                                                                    <c:forEach var="oficinaAuxiliar2" items="${oficinasAuxiliares}">
                                                                                                                                                        <c:if test="${oficinaAuxiliar2.codOfiResponsable.codigo == oficinaAuxiliar.codigo}">
                                                                                                                                                            <li>
                                                                                                                                                                <a href="javascript:void(0);"><span
                                                                                                                                                                        class="panel-heading btn-ofaux vuitanta-percent"
                                                                                                                                                                        style="cursor:copy" onclick="copyToClipboard(this)"><i
                                                                                                                                                                        class="fa fa-home"></i> ${oficinaAuxiliar2.codigo} - ${oficinaAuxiliar2.denominacion}</span></a>
                                                                                                                                                            </li>
                                                                                                                                                        </c:if>
                                                                                                                                                    </c:forEach>
                                                                                                                                                </ul>
                                                                                                                                            </li>
                                                                                                                                        </c:if>
                                                                                                                                    </c:forEach>
                                                                                                                                </ul>
                                                                                                                            </li>
                                                                                                                        </c:if>
                                                                                                                    </c:forEach>
                                                                                                                    <!-- **** Oficinas Funcionales/Organizativas ***-->
                                                                                                                    <c:forEach var="relacionOrganizativaOfi" items="${relacionesOrganizativaOfi}">
                                                                                                                        <c:if test="${relacionOrganizativaOfi.unidad.codigo == organismo5.codigo}">
                                                                                                                            <c:if test="${relacionOrganizativaOfi.unidad.codUnidadRaiz.codigo == relacionOrganizativaOfi.oficina.codUoResponsable.codigo}">
                                                                                                                                <li>
                                                                                                                                    <a href="javascript:void(0);"><span
                                                                                                                                            class="panel-heading btn-success vuitanta-percent"
                                                                                                                                            style="cursor:copy" onclick="copyToClipboard(this)"><i
                                                                                                                                            class="fa fa-institution"></i> ${relacionOrganizativaOfi.oficina.codigo} - ${relacionOrganizativaOfi.oficina.denominacion}</span></a>
                                                                                                                                </li>
                                                                                                                            </c:if>
                                                                                                                        </c:if>
                                                                                                                    </c:forEach>
                                                                                                                    <!-- **** Oficinas Sir ***-->
                                                                                                                    <c:forEach var="relacionSirOfi" items="${relacionesSirOfi}">
                                                                                                                        <c:if test="${relacionSirOfi.unidad.codigo == organismo5.codigo}">
                                                                                                                            <c:if test="${relacionSirOfi.oficina.codUoResponsable.codigo != organismo5.codigo}">
                                                                                                                                <li>
                                                                                                                                    <a href="javascript:void(0);"><span
                                                                                                                                            class="panel-heading btn-ofsir vuitanta-percent"
                                                                                                                                            style="cursor:copy" onclick="copyToClipboard(this)"><i
                                                                                                                                            class="fa fa-exchange"></i> ${relacionSirOfi.oficina.codigo} - ${relacionSirOfi.oficina.denominacion} <img src="<c:url value="/img/logo-SIR.png"/>" width="20" alt="SIR" title="SIR"/></span></a>
                                                                                                                                </li>
                                                                                                                            </c:if>
                                                                                                                        </c:if>
                                                                                                                    </c:forEach>

                                                                                                                    <c:forEach var="organismo6" items="${unidadesSextoNivel}">
                                                                                                                        <c:if test="${organismo6.codUnidadSuperior.codigo == organismo5.codigo}">
                                                                                                                            <li>

                                                                                                                                <c:if test="${organismo6.esEdp == false}">
                                                                                                                                        <span class="panel-heading btn-primary vuitanta-percent"
                                                                                                                                              id="cinqueNivell${contadorCinque}"
                                                                                                                                              style="cursor:copy" onclick="copyToClipboard(this)"><i
                                                                                                                                                class=""></i> ${organismo6.codigo} - ${organismo6.denominacion}</span>
                                                                                                                                </c:if>
                                                                                                                                <c:if test="${organismo6.esEdp == true}">
                                                                                                                                        <span class="panel-heading btn-edp vuitanta-percent"
                                                                                                                                              id="cinqueNivell${contadorCinque}"
                                                                                                                                              style="cursor:copy" onclick="copyToClipboard(this)"><i
                                                                                                                                                class=""></i> ${organismo6.codigo} - ${organismo6.denominacion}</span>
                                                                                                                                </c:if>

                                                                                                                                <c:set var="contadorCinque" value="${contadorCinque+1}"></c:set>
                                                                                                                                <ul>

                                                                                                                                    <!-- **** Oficinas ***-->
                                                                                                                                    <c:forEach var="oficinaPrincipal" items="${oficinasPrincipales}">
                                                                                                                                        <c:if test="${oficinaPrincipal.codUoResponsable.codigo == organismo6.codigo}">
                                                                                                                                            <li>
                                                                                                                                                <a href="javascript:void(0);"><span
                                                                                                                                                        class="panel-heading btn-warning vuitanta-percent"
                                                                                                                                                        style="cursor:copy" onclick="copyToClipboard(this)"><i
                                                                                                                                                        class="fa fa-home"></i> ${oficinaPrincipal.codigo} - ${oficinaPrincipal.denominacion}</span></a>

                                                                                                                                                <c:forEach var="relacionSirOfi" items="${relacionesSirOfi}">
                                                                                                                                                    <c:if test="${relacionSirOfi.unidad.codigo == organismo6.codigo}">
                                                                                                                                                        <c:if test="${oficinaPrincipal.codigo == relacionSirOfi.oficina.codigo}">
                                                                                                                                                            <a href="javascript:void(0);"><span
                                                                                                                                                                    class="panel-heading btn-ofsir vuitanta-percent"
                                                                                                                                                                    style="cursor:copy"><img src="<c:url value="/img/logo-SIR.png"/>" width="20" alt="SIR" title="SIR"/></span></a>
                                                                                                                                                        </c:if>
                                                                                                                                                    </c:if>
                                                                                                                                                </c:forEach>

                                                                                                                                                <ul>
                                                                                                                                                    <c:forEach var="oficinaAuxiliar" items="${oficinasAuxiliares}">
                                                                                                                                                        <c:if test="${oficinaAuxiliar.codOfiResponsable.codigo == oficinaPrincipal.codigo}">
                                                                                                                                                            <li>
                                                                                                                                                                <a href="javascript:void(0);"><span
                                                                                                                                                                        class="panel-heading btn-ofaux vuitanta-percent"
                                                                                                                                                                        style="cursor:copy" onclick="copyToClipboard(this)"><i
                                                                                                                                                                        class="fa fa-home"></i> ${oficinaAuxiliar.codigo} - ${oficinaAuxiliar.denominacion}</span></a>

                                                                                                                                                                <c:forEach var="relacionOrganizativaOfi" items="${relacionesOrganizativaOfi}">
                                                                                                                                                                    <c:if test="${relacionOrganizativaOfi.unidad.codigo == organismo6.codigo}">
                                                                                                                                                                        <c:if test="${oficinaAuxiliar.codigo == relacionOrganizativaOfi.oficina.codigo}">
                                                                                                                                                                            <a href="javascript:void(0);"><span
                                                                                                                                                                                    class="panel-heading btn-success vuitanta-percent"
                                                                                                                                                                                    style="cursor:copy"><i
                                                                                                                                                                                    class="fa fa-institution"></i></span></a>
                                                                                                                                                                        </c:if>
                                                                                                                                                                    </c:if>
                                                                                                                                                                </c:forEach>

                                                                                                                                                                <ul>
                                                                                                                                                                    <c:forEach var="oficinaAuxiliar2" items="${oficinasAuxiliares}">
                                                                                                                                                                        <c:if test="${oficinaAuxiliar2.codOfiResponsable.codigo == oficinaAuxiliar.codigo}">
                                                                                                                                                                            <li>
                                                                                                                                                                                <a href="javascript:void(0);"><span
                                                                                                                                                                                        class="panel-heading btn-ofaux vuitanta-percent"
                                                                                                                                                                                        style="cursor:copy" onclick="copyToClipboard(this)"><i
                                                                                                                                                                                        class="fa fa-home"></i> ${oficinaAuxiliar2.codigo} - ${oficinaAuxiliar2.denominacion}</span></a>
                                                                                                                                                                            </li>
                                                                                                                                                                        </c:if>
                                                                                                                                                                    </c:forEach>
                                                                                                                                                                </ul>
                                                                                                                                                            </li>
                                                                                                                                                        </c:if>
                                                                                                                                                    </c:forEach>
                                                                                                                                                </ul>
                                                                                                                                            </li>
                                                                                                                                        </c:if>
                                                                                                                                    </c:forEach>
                                                                                                                                    <!-- **** Oficinas Funcionales/Organizativas ***-->
                                                                                                                                    <c:forEach var="relacionOrganizativaOfi" items="${relacionesOrganizativaOfi}">
                                                                                                                                        <c:if test="${relacionOrganizativaOfi.unidad.codigo == organismo6.codigo}">
                                                                                                                                            <c:if test="${relacionOrganizativaOfi.unidad.codUnidadRaiz.codigo == relacionOrganizativaOfi.oficina.codUoResponsable.codigo}">
                                                                                                                                                <li>
                                                                                                                                                    <a href="javascript:void(0);"><span
                                                                                                                                                            class="panel-heading btn-success vuitanta-percent"
                                                                                                                                                            style="cursor:copy" onclick="copyToClipboard(this)"><i
                                                                                                                                                            class="fa fa-institution"></i> ${relacionOrganizativaOfi.oficina.codigo} - ${relacionOrganizativaOfi.oficina.denominacion}</span></a>
                                                                                                                                                </li>
                                                                                                                                            </c:if>
                                                                                                                                        </c:if>
                                                                                                                                    </c:forEach>
                                                                                                                                    <!-- **** Oficinas Sir ***-->
                                                                                                                                    <c:forEach var="relacionSirOfi" items="${relacionesSirOfi}">
                                                                                                                                        <c:if test="${relacionSirOfi.unidad.codigo == organismo6.codigo}">
                                                                                                                                            <c:if test="${relacionSirOfi.oficina.codUoResponsable.codigo != organismo6.codigo}">
                                                                                                                                                <li>
                                                                                                                                                    <a href="javascript:void(0);"><span
                                                                                                                                                            class="panel-heading btn-ofsir vuitanta-percent"
                                                                                                                                                            style="cursor:copy" onclick="copyToClipboard(this)"><i
                                                                                                                                                            class="fa fa-exchange"></i> ${relacionSirOfi.oficina.codigo} - ${relacionSirOfi.oficina.denominacion} <img src="<c:url value="/img/logo-SIR.png"/>" width="20" alt="SIR" title="SIR"/></span></a>
                                                                                                                                                </li>
                                                                                                                                            </c:if>
                                                                                                                                        </c:if>
                                                                                                                                    </c:forEach>

                                                                                                                                    <c:forEach var="organismo7" items="${unidadesSeptimoNivel}">
                                                                                                                                        <c:if test="${organismo7.codUnidadSuperior.codigo == organismo6.codigo}">
                                                                                                                                            <li>

                                                                                                                                                <c:if test="${organismo7.esEdp == false}">
                                                                                                                                                        <span class="panel-heading btn-primary vuitanta-percent"
                                                                                                                                                              id="siseNivell${contadorSise}"
                                                                                                                                                              style="cursor:copy" onclick="copyToClipboard(this)"><i
                                                                                                                                                                class=""></i> ${organismo7.codigo} - ${organismo7.denominacion}</span>
                                                                                                                                                </c:if>
                                                                                                                                                <c:if test="${organismo7.esEdp == true}">
                                                                                                                                                        <span class="panel-heading btn-edp vuitanta-percent"
                                                                                                                                                              id="siseNivell${contadorSise}"
                                                                                                                                                              style="cursor:copy" onclick="copyToClipboard(this)"><i
                                                                                                                                                                class=""></i> ${organismo7.codigo} - ${organismo7.denominacion}</span>
                                                                                                                                                </c:if>

                                                                                                                                                <c:set var="contadorSise" value="${contadorSise+1}"></c:set>
                                                                                                                                                <ul>

                                                                                                                                                    <!-- **** Oficinas ***-->
                                                                                                                                                    <c:forEach var="oficinaPrincipal" items="${oficinasPrincipales}">
                                                                                                                                                        <c:if test="${oficinaPrincipal.codUoResponsable.codigo == organismo7.codigo}">
                                                                                                                                                            <li>
                                                                                                                                                                <a href="javascript:void(0);"><span
                                                                                                                                                                        class="panel-heading btn-warning vuitanta-percent"
                                                                                                                                                                        style="cursor:copy" onclick="copyToClipboard(this)"><i
                                                                                                                                                                        class="fa fa-home"></i> ${oficinaPrincipal.codigo} - ${oficinaPrincipal.denominacion}</span></a>

                                                                                                                                                                <c:forEach var="relacionSirOfi" items="${relacionesSirOfi}">
                                                                                                                                                                    <c:if test="${relacionSirOfi.unidad.codigo == organismo7.codigo}">
                                                                                                                                                                        <c:if test="${oficinaPrincipal.codigo == relacionSirOfi.oficina.codigo}">
                                                                                                                                                                            <a href="javascript:void(0);"><span
                                                                                                                                                                                    class="panel-heading btn-ofsir vuitanta-percent"
                                                                                                                                                                                    style="cursor:copy"><img src="<c:url value="/img/logo-SIR.png"/>" width="20" alt="SIR" title="SIR"/></span></a>
                                                                                                                                                                        </c:if>
                                                                                                                                                                    </c:if>
                                                                                                                                                                </c:forEach>

                                                                                                                                                                <ul>
                                                                                                                                                                    <c:forEach var="oficinaAuxiliar" items="${oficinasAuxiliares}">
                                                                                                                                                                        <c:if test="${oficinaAuxiliar.codOfiResponsable.codigo == oficinaPrincipal.codigo}">
                                                                                                                                                                            <li>
                                                                                                                                                                                <a href="javascript:void(0);"><span
                                                                                                                                                                                        class="panel-heading btn-ofaux vuitanta-percent"
                                                                                                                                                                                        style="cursor:copy" onclick="copyToClipboard(this)"><i
                                                                                                                                                                                        class="fa fa-home"></i> ${oficinaAuxiliar.codigo} - ${oficinaAuxiliar.denominacion}</span></a>

                                                                                                                                                                                <c:forEach var="relacionOrganizativaOfi" items="${relacionesOrganizativaOfi}">
                                                                                                                                                                                    <c:if test="${relacionOrganizativaOfi.unidad.codigo == organismo7.codigo}">
                                                                                                                                                                                        <c:if test="${oficinaAuxiliar.codigo == relacionOrganizativaOfi.oficina.codigo}">
                                                                                                                                                                                            <a href="javascript:void(0);"><span
                                                                                                                                                                                                    class="panel-heading btn-success vuitanta-percent"
                                                                                                                                                                                                    style="cursor:copy"><i
                                                                                                                                                                                                    class="fa fa-institution"></i></span></a>
                                                                                                                                                                                        </c:if>
                                                                                                                                                                                    </c:if>
                                                                                                                                                                                </c:forEach>

                                                                                                                                                                                <ul>
                                                                                                                                                                                    <c:forEach var="oficinaAuxiliar2" items="${oficinasAuxiliares}">
                                                                                                                                                                                        <c:if test="${oficinaAuxiliar2.codOfiResponsable.codigo == oficinaAuxiliar.codigo}">
                                                                                                                                                                                            <li>
                                                                                                                                                                                                <a href="javascript:void(0);"><span
                                                                                                                                                                                                        class="panel-heading btn-ofaux vuitanta-percent"
                                                                                                                                                                                                        style="cursor:copy" onclick="copyToClipboard(this)"><i
                                                                                                                                                                                                        class="fa fa-home"></i> ${oficinaAuxiliar2.codigo} - ${oficinaAuxiliar2.denominacion}</span></a>
                                                                                                                                                                                            </li>
                                                                                                                                                                                        </c:if>
                                                                                                                                                                                    </c:forEach>
                                                                                                                                                                                </ul>
                                                                                                                                                                            </li>
                                                                                                                                                                        </c:if>
                                                                                                                                                                    </c:forEach>
                                                                                                                                                                </ul>
                                                                                                                                                            </li>
                                                                                                                                                        </c:if>
                                                                                                                                                    </c:forEach>
                                                                                                                                                    <!-- **** Oficinas Funcionales/Organizativas ***-->
                                                                                                                                                    <c:forEach var="relacionOrganizativaOfi" items="${relacionesOrganizativaOfi}">
                                                                                                                                                        <c:if test="${relacionOrganizativaOfi.unidad.codigo == organismo7.codigo}">
                                                                                                                                                            <c:if test="${relacionOrganizativaOfi.unidad.codUnidadRaiz.codigo == relacionOrganizativaOfi.oficina.codUoResponsable.codigo}">
                                                                                                                                                                <li>
                                                                                                                                                                    <a href="javascript:void(0);"><span
                                                                                                                                                                            class="panel-heading btn-success vuitanta-percent"
                                                                                                                                                                            style="cursor:copy" onclick="copyToClipboard(this)"><i
                                                                                                                                                                            class="fa fa-institution"></i> ${relacionOrganizativaOfi.oficina.codigo} - ${relacionOrganizativaOfi.oficina.denominacion}</span></a>
                                                                                                                                                                </li>
                                                                                                                                                            </c:if>
                                                                                                                                                        </c:if>
                                                                                                                                                    </c:forEach>
                                                                                                                                                    <!-- **** Oficinas Sir ***-->
                                                                                                                                                    <c:forEach var="relacionSirOfi" items="${relacionesSirOfi}">
                                                                                                                                                        <c:if test="${relacionSirOfi.unidad.codigo == organismo7.codigo}">
                                                                                                                                                            <c:if test="${relacionSirOfi.oficina.codUoResponsable.codigo != organismo7.codigo}">
                                                                                                                                                                <li>
                                                                                                                                                                    <a href="javascript:void(0);"><span
                                                                                                                                                                            class="panel-heading btn-ofsir vuitanta-percent"
                                                                                                                                                                            style="cursor:copy" onclick="copyToClipboard(this)"><i
                                                                                                                                                                            class="fa fa-exchange"></i> ${relacionSirOfi.oficina.codigo} - ${relacionSirOfi.oficina.denominacion} <img src="<c:url value="/img/logo-SIR.png"/>" width="20" alt="SIR" title="SIR"/></span></a>
                                                                                                                                                                </li>
                                                                                                                                                            </c:if>
                                                                                                                                                        </c:if>
                                                                                                                                                    </c:forEach>
                                                                                                                                                </ul>
                                                                                                                                            </li>
                                                                                                                                        </c:if>
                                                                                                                                    </c:forEach>
                                                                                                                                </ul>
                                                                                                                            </li>
                                                                                                                        </c:if>
                                                                                                                    </c:forEach>
                                                                                                                </ul>
                                                                                                            </li>
                                                                                                        </c:if>
                                                                                                    </c:forEach>
                                                                                                </ul>
                                                                                            </li>
                                                                                        </c:if>
                                                                                    </c:forEach>
                                                                                </ul>
                                                                            </li>
                                                                        </c:if>
                                                                    </c:forEach>
                                                                </ul>
                                                            </li>
                                                        </c:if>
                                                    </c:forEach>
                                                </ul>
                                            </li>
                                        </c:forEach>

                                    </ul>
                                </li>
                            </ul>
                        </div>
                        </div>
                    </div>

                <!-- Box con la trazabilidad de los sustitutos-->
                <div class="box span9 minAlt">
                    <div class="box-header well cabeceraDetalle">
                        <h2><fmt:message key="dir3caib.sustitutos"/></h2>
                    </div>
                    <%-- Mostramos los históricos de una unidad de manera recursiva--%>
                    <c:if test="${not empty nodo.historicos}">
                        <c:set var="nodo" value="${nodo}" scope="request"/>
                        <jsp:include page="../nodohistorico.jsp" flush="true"/>
                    </c:if>
                </div>
          </div>
        </div>
    </div>
</div>

<c:import url="../modulos/pie.jsp"/>

<script type="text/javascript">
    $(function () {
        $('.tree li:has(ul > li)').addClass('parent_li').find(' > span').attr('title', 'Amaga la branca');
        $('.tree li:has(ul > li)').addClass('parent_li').find(' > span > i').addClass('fa fa-minus');
        $('.tree li.parent_li > span').on('click', function (e) {
            var children = $(this).parent('li.parent_li').find(' > ul > li');
            if (children.is(":visible")) {
                children.hide('fast');
                $(this).find(' > i').removeClass('fa fa-minus');
                $(this).attr('title', 'Mostra la branca').find(' > i').addClass('fa fa-plus');
            } else {
                children.show('fast');
                $(this).find(' > i').removeClass('fa fa-plus');
                $(this).attr('title', 'Amaga la branca').find(' > i').addClass('fa fa-minus');
            }
            e.stopPropagation();
        });
    });
</script>

<script type="text/javascript">
    // Permet copiar la informació d'un span a dins el portapapers
    function copyToClipboard(that){
        var inp =document.createElement('input');
        document.body.appendChild(inp);
        inp.value =that.textContent;
        inp.select();
        document.execCommand('copy',false);
        inp.remove();
    }
</script>

<script type="text/javascript">
    $("[rel='edp']").popover({ trigger: 'hover',placement: 'right',container:"body", html:true});
</script>

</body>
</html>