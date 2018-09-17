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
                    <h2><fmt:message key="unidad.organigrama"/>&nbsp;<i> ${unidadRaiz.codigo}
                        - ${unidadRaiz.denominacion}</i></h2>
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

                    <div class="btn-group infoBranca">
                        <button type="button" id="infoCopy" class="btn infoBranca" disabled style="cursor:default"><i class="fa fa-info-circle colophon">  <spring:message code="dir3caib.organismo.arbol.copiar"/></i></button>
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
                                                <span class="panel-heading btn-primary vuitanta-percent" id="govern"
                                                      style="cursor:copy" onclick="copyToClipboard(this)"><i
                                                        class=""></i> ${organismo1.codigo} - ${organismo1.denominacion}</span>
                                        </c:if>
                                        <c:if test="${organismo1.esEdp == true}">
                                                <span class="panel-heading btn-edp vuitanta-percent" id="govern"
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
    </div>
</div>

<c:import url="modulos/pie.jsp"/>

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

</body>
</html>























































