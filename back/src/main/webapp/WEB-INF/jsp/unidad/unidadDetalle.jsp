<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/jsp/modulos/includes.jsp"%>

<!DOCTYPE html>
<html lang="ca">
<head>
<c:import url="../modulos/imports.jsp" />
</head>

<body>

	<c:import url="../modulos/menu.jsp" />
	<div class="row-fluid container main">
		<div class="well well-dir3caib">
			<div class="row-fluid">

				<div class="box span12">

					<div class="box-header well">
						<h2>
							<spring:message code="unidad.detalle"/> ${unidad.codigoDir3} v${unidad.version} -
							<c:choose>
								<c:when test="${denominacionCooficial and not empty unidad.denomLenguaCooficial}">
									${unidad.denomLenguaCooficial}
								</c:when>
								<c:otherwise>${unidad.denominacion}</c:otherwise>
							</c:choose>
						</h2>
					</div>

					<div class="box-content pad-top0">

						<!-- Box con Información Detalle de la Oficina  -->
						<div class="box span3">

							<div class="box-header well cabeceraDetalle">
								<h2>
									<fmt:message key="dir3caib.informacion" />
								</h2>
							</div>

							<div class="box-content pad-top0">
								<div class="pad-bottom0">
									<dl class="detalle">
										<div class="box-header well cabeceraDetalle">
											<h5>
												<spring:message code="unidad.datos" />
											</h5>
										</div>
										<dt><spring:message code="unidad.codigo" />:</dt><dd>${unidad.codigoDir3} v${unidad.version}</dd>
										<dt><spring:message code="unidad.denominacion" />:</dt><dd>${unidad.denominacion}</dd>
										<c:if test="${not empty unidad.denomLenguaCooficial}">
											<dt><spring:message code="unidad.denominacion.cooficial" />:</dt><dd>${unidad.denomLenguaCooficial}</dd>
										</c:if>
										<dt><spring:message code="unidad.estado" />:</dt>
										<c:if test="${unidad.estado.codigoEstadoEntidad == Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE}">
											<dd><span class="label label-success"><spring:message code="estado.${unidad.estado.codigoEstadoEntidad}"/></span></dd>
										</c:if>
										<c:if test="${unidad.estado.codigoEstadoEntidad == Dir3caibConstantes.ESTADO_ENTIDAD_EXTINGUIDO}">
											<dd><span class="label btn-danger"><spring:message code="estado.${unidad.estado.codigoEstadoEntidad}"/></span></dd>
										    <c:if test="${not empty unidad.fechaExtincion}"><dt><spring:message code="unidad.fechaExtincion" />:</dt><dd><fmt:formatDate pattern="dd/MM/yyyy" value="${unidad.fechaExtincion}" /></dd></c:if>
											<c:if test="${not empty unidad.observBaja}"><dt><spring:message code="unidad.baja" />:</dt><dd>${unidad.observBaja}</dd> </c:if>
										</c:if>
										<dt><spring:message code="unidad.edp" />:</dt>
										<c:if test="${unidad.esEdp}">
											<dd><p class="centrat ajustarEDP" rel="edp"
												    data-content="<c:if test="${not empty unidad.codEdpPrincipal}">Edp Principal: <c:if test="${denominacionCooficial and not empty unidad.codEdpPrincipal.denomLenguaCooficial}"><c:out value="${unidad.codEdpPrincipal.codigoDir3} v${unidad.codEdpPrincipal.version} - ${unidad.codEdpPrincipal.denomLenguaCooficial}" escapeXml="true"/></c:if><c:if test="${not denominacionCooficial}"><c:out value="${unidad.codEdpPrincipal.codigoDir3} v${unidad.codEdpPrincipal.version} - ${unidad.codEdpPrincipal.denominacion}" escapeXml="true"/></c:if></c:if>"
													data-toggle="popover">
													<span class="label label-success">Sí</span>
												</p>
											</dd>
										</c:if>
										<c:if test="${!unidad.esEdp}">
											<dd><span class="label label-danger">No</span></dd>
										</c:if>
										<c:if test="${unidad.estado.codigoEstadoEntidad == Dir3caibConstantes.ESTADO_ENTIDAD_ANULADO}">
											<dd><span class="label label-important"><spring:message code="estado.${unidad.estado.codigoEstadoEntidad}"/></span></dd>
										</c:if>
										<c:if test="${unidad.estado.codigoEstadoEntidad == Dir3caibConstantes.ESTADO_ENTIDAD_TRANSITORIO}">
											<dd><span class="label label-info"><spring:message code="estado.${unidad.estado.codigoEstadoEntidad}"/></span></dd>
										</c:if>
										<c:if test="${not empty unidad.nivelJerarquico}">
											<dt><spring:message code="unidad.nivel" />:</dt>
											<dd>${unidad.nivelJerarquico}</dd>
										</c:if>
										<c:if test="${not empty unidad.nifcif}">
											<dt><spring:message code="unidad.nifcif" />:</dt>
											<dd>${unidad.nifcif}</dd>
										</c:if>
										<c:if
											test="${not empty unidad.nivelAdministracion.descripcionNivelAdministracion}">
											<dt><spring:message code="unidad.administracion" />:</dt>
											<dd>${unidad.nivelAdministracion.descripcionNivelAdministracion}</dd>
										</c:if>
										<c:if
											test="${not empty unidad.codTipoUnidad.descripcionTipoUnidadOrganica}">
											<dt><spring:message code="unidad.tipo" />:</dt>
											<dd>${unidad.codTipoUnidad.descripcionTipoUnidadOrganica}</dd>
										</c:if>
										<c:if test="${not empty unidad.codUnidadRaiz.codigo}">
											<dt><spring:message code="unidad.unidadraiz" />:</dt>
											<c:choose>
												<c:when
													test="${denominacionCooficial and not empty unidad.codUnidadRaiz.denomLenguaCooficial}">
													<dd>
														<a
															onclick="goTo('<c:url value="/unidad/${unidad.codUnidadRaiz.codigo}/${paginaUrl}"/>','<spring:message code="dir3caib.organismo.detalle.generar" javaScriptEscape="true"/>')"
															onmouseover="this.style.cursor='pointer';">${unidad.codUnidadRaiz.codigoDir3}
															v${unidad.codUnidadRaiz.version} -
															${unidad.codUnidadRaiz.denomLenguaCooficial}</a>
													</dd>
												</c:when>
												<c:otherwise>
													<dd>
														<a
															onclick="goTo('<c:url value="/unidad/${unidad.codUnidadRaiz.codigo}/${paginaUrl}"/>','<spring:message code="dir3caib.organismo.detalle.generar" javaScriptEscape="true"/>')"
															onmouseover="this.style.cursor='pointer';">${unidad.codUnidadRaiz.codigoDir3}
															v${unidad.codUnidadRaiz.version} -
															${unidad.codUnidadRaiz.denominacion}</a>
													</dd>
												</c:otherwise>
											</c:choose>
										</c:if>
										<c:if test="${not empty unidad.codUnidadSuperior.codigo}">
											<dt><spring:message code="unidad.unidadsuperior" />:</dt>
											<c:choose>
												<c:when
													test="${denominacionCooficial and not empty unidad.codUnidadSuperior.denomLenguaCooficial}">
													<dd>
														<a
															onclick="goTo('<c:url value="/unidad/${unidad.codUnidadSuperior.codigo}/${paginaUrl}"/>','<spring:message code="dir3caib.organismo.detalle.generar" javaScriptEscape="true"/>')"
															onmouseover="this.style.cursor='pointer';">${unidad.codUnidadSuperior.codigoDir3}
															v${unidad.codUnidadSuperior.version} -
															${unidad.codUnidadSuperior.denomLenguaCooficial}</a>
													</dd>
												</c:when>
												<c:otherwise>
													<dd>
														<a
															onclick="goTo('<c:url value="/unidad/${unidad.codUnidadSuperior.codigo}/${paginaUrl}"/>','<spring:message code="dir3caib.organismo.detalle.generar" javaScriptEscape="true"/>')"
															onmouseover="this.style.cursor='pointer';">${unidad.codUnidadSuperior.codigoDir3}
															v${unidad.codUnidadSuperior.version} -
															${unidad.codUnidadSuperior.denominacion}</a>
													</dd>
												</c:otherwise>
											</c:choose>
										</c:if>
										<c:if test="${not empty historicosAnteriores}">
											<b><spring:message code="unidad.historicos" />: </b>
											<ul>
												<c:forEach items="${historicosAnteriores}" var="historico">
													<c:choose>
														<c:when test="${denominacionCooficial and not empty historico.denomLenguaCooficial }">
														<li><a
														onclick="goTo('<c:url
                                                        value="/unidad/${historico.codigo}/${paginaUrl}"/>','<spring:message
                                                        code="dir3caib.organismo.detalle.generar"
                                                        javaScriptEscape="true"/>')"
														onmouseover="this.style.cursor='pointer';">${historico.codigoDir3} v${historico.version}
															 - ${historico.denomLenguaCooficial}</a></li>
														</c:when>
														<c:otherwise>
														<li><a
														onclick="goTo('<c:url
                                                        value="/unidad/${historico.codigo}/${paginaUrl}"/>','<spring:message
                                                        code="dir3caib.organismo.detalle.generar"
                                                        javaScriptEscape="true"/>')"
														onmouseover="this.style.cursor='pointer';">${historico.codigoDir3} v${historico.version}
															 - ${historico.denominacion}</a></li>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</ul>
										</c:if>
										<c:if test="${not empty historicosFinales}">
											<b><spring:message code="unidad.sustitutos" />: </b>
											<ul>
												<c:forEach items="${historicosFinales}" var="sustituto">
													<c:choose>
													<c:when test="${denominacionCooficial and not empty sustituto.denomLenguaCooficial} }">
													<li><a
														onclick="goTo('<c:url
                                                        value="/unidad/${sustituto.codigo}/${paginaUrl}"/>','<spring:message
                                                        code="dir3caib.organismo.detalle.generar"
                                                        javaScriptEscape="true"/>')"
														onmouseover="this.style.cursor='pointer';">${sustituto.codigoDir3} v${sustituto.version}
															 - ${sustituto.denomLenguaCooficial}</a></li>
													</c:when>
													<c:otherwise>
													<li><a
														onclick="goTo('<c:url
                                                        value="/unidad/${sustituto.codigo}/${paginaUrl}"/>','<spring:message
                                                        code="dir3caib.organismo.detalle.generar"
                                                        javaScriptEscape="true"/>')"
														onmouseover="this.style.cursor='pointer';">${sustituto.codigoDir3} v${sustituto.version}
															 - ${sustituto.denominacion}</a></li>
													</c:otherwise>
													</c:choose>
												</c:forEach>
											</ul>
										</c:if>

										<hr class="divider">
										<div class="box-header well cabeceraDetalle">
											<h5><spring:message code="unidad.direccion"/></h5>
										</div>
										<c:if test="${not empty unidad.nombreVia}">
											<dd>${unidad.nombreVia}, ${unidad.numVia}</dd>
										</c:if>
										<c:if test="${not empty unidad.complemento}">
											<dd>${unidad.complemento}</dd>
										</c:if>
										<c:if test="${not empty unidad.codLocalidad.descripcionLocalidad}">
											<dd>${unidad.codPostal} - ${unidad.codLocalidad.descripcionLocalidad}</dd>
										</c:if>
										<c:if test="${not empty unidad.codComunidad.descripcionComunidad}">
											<dd>${unidad.codComunidad.descripcionComunidad}</dd>
										</c:if>
										<c:if test="${not empty unidad.codPais.descripcionPais}">
											<dd>${unidad.codPais.descripcionPais}</dd>
										</c:if>
										<c:if
											test="${empty unidad.nombreVia && empty unidad.complemento && empty unidad.codLocalidad.descripcionLocalidad && empty unidad.codComunidad.descripcionComunidad && empty unidad.codPais.descripcionPais}">
											<dt>&nbsp;</dt>
											<dd><spring:message code="oficina.busqueda.vacio" /></dd>
										</c:if>

										<!-- Muestra contactos de la unidad-->
										<c:if test="${fn:length(unidad.contactos)>0}">
											<hr class="divider">
											<div class="box-header well cabeceraDetalle">
												<h5><spring:message code="unidad.contacto" /></h5>
											</div>
											<c:forEach items="${unidad.contactos}" var="contacto">
												<c:if test="${contacto.tipoContacto.codigoTipoContacto == 'E'}">
													<dt><spring:message code="unidad.contacto.mail" />:</dt>
												</c:if>
												<c:if test="${contacto.tipoContacto.codigoTipoContacto == 'F' || contacto.tipoContacto.codigoTipoContacto == 'T'}">
													<dt><spring:message code="unidad.contacto.telefono" />:</dt>
												</c:if>
												<c:if test="${contacto.tipoContacto.codigoTipoContacto == 'U'}">
													<dt><spring:message code="unidad.contacto.url" />:</dt>
												</c:if>
												<dd>${contacto.valorContacto}</dd>
											</c:forEach>
										</c:if>

										<!-- Muestra Ámbito de la unidad-->
										<hr class="divider">
										<div class="box-header well cabeceraDetalle">
											<h5><spring:message code="unidad.ambito" /></h5>
										</div>
										<c:if test="${not empty unidad.codAmbitoTerritorial}">
											<dt><spring:message code="unidad.ambito.territorial" />:</dt>
											<dd>${unidad.codAmbitoTerritorial.descripcionAmbito}</dd>
										</c:if>
										<c:if test="${not empty unidad.codAmbPais}">
											<dt><spring:message code="unidad.ambito.pais" />:</dt>
											<dd>${unidad.codAmbPais.descripcionPais}</dd>
										</c:if>
										<c:if test="${not empty unidad.codAmbComunidad}">
											<dt><spring:message code="unidad.ambito.ca" />:</dt>
											<dd>${unidad.codAmbComunidad.descripcionComunidad}</dd>
										</c:if>
										<c:if test="${not empty unidad.codAmbProvincia}">
											<dt><spring:message code="unidad.ambito.provincia"/>:</dt>
											<dd>${unidad.codAmbProvincia.descripcionProvincia}</dd>
										</c:if>
										<c:if test="${not empty unidad.codAmbIsla}">
											<dt><spring:message code="unidad.ambito.isla" />:</dt>
											<dd>${unidad.codAmbIsla.descripcionIsla}</dd>
										</c:if>

										<!-- Muestra Oficinas relacionadas de la unidad-->
										<!-- Equivalen a las Oficinas Organizativas Ofi (organizativaOfi de dir3) VIGENTES-->
										<hr class="divider">
										<div class="box-header well cabeceraDetalle">
											<h5><spring:message code="unidad.oficinas" /></h5>
										</div>
										<c:set var="sinOficinas" scope="session" value="true" />
										<c:forEach items="${unidad.organizativaOfi}" var="oficina">
											<c:if test="${oficina.estado.codigoEstadoEntidad == Dir3caibConstantes.ESTADO_ENTIDAD_VIGENTE}">
												<c:set var="sinOficinas" value="false" />
												<dt>-</dt>
												<dd>${oficina.oficina.codigo} - ${oficina.oficina.denominacion}</dd>
											</c:if>
										</c:forEach>
										<c:if test="${empty unidad.organizativaOfi || sinOficinas}">
											<dt>&nbsp;</dt>
											<dd><spring:message code="oficina.busqueda.vacio"/></dd>
										</c:if>

										<!-- Muestra Oficinas que registran la unidad-->
										<hr class="divider">
										<div class="box-header well cabeceraDetalle">
											<h5><spring:message code="unidad.registren" /></h5>
										</div>
										<c:set var="sinOficinas" scope="session" value="true" />
										<c:forEach items="${oficinasRegistran}" var="oficinaRegistra">
											<c:if test="${oficinaRegistra.oficinaInformacion}">
												<c:set var="sinOficinas" value="false" />
												<dt>-</dt>
												<dd>${oficinaRegistra.codigo} - ${oficinaRegistra.denominacion}</dd>
											</c:if>
										</c:forEach>
										<c:if test="${empty oficinasRegistran || sinOficinas}">
											<dt>&nbsp;</dt>
											<dd><spring:message code="oficina.busqueda.vacio"/></dd>
										</c:if>

										<!-- Muestra Clasificación de la oficina-->
										<hr class="divider">
										<div class="box-header well cabeceraDetalle">
											<h5><spring:message code="oficina.clasificacion"/></h5>
										</div>
										<c:forEach items="${unidad.servicios}" var="servicio">
											<dt>-</dt>
											<dd>${servicio.servicio.descServicio}</dd>
										</c:forEach>
									</dl>
								</div>
							</div>

						</div>

						<c:set var="extinguida" value="false" />

						<!-- Organigrama de la Unidad  -->
						<c:if test="${!unidadExtinguida}">
							<c:import url="../organigrama/organigrama.jsp">
								<c:param name="tipo" value="${Dir3caibConstantes.UNIDAD}"/>
							</c:import>
						</c:if>

						<!-- Trazabilidad de los sustitutos-->
						<c:if test="${unidadExtinguida}">
							<c:set var="extinguida" value="true" />
							<c:if test="${not empty nodo}">
								<div class="box span9 maxAlt pre-scrollable">
									<div class="box-header well cabeceraDetalle ampleComplet">
										<h2>
											<fmt:message key="dir3caib.sustitutos" />
										</h2>
									</div>

									<!-- LEYENDA -->
									<div class="box llegenda-extingit">
										<div class="box-header well llegendaVert">
											<div class="col-xs-12">
												<i class="fa fa-comment-o"></i> <strong><spring:message
														code="dir3caib.leyenda" /></strong> <span
													class="badge-arbre btn-danger llegendaCapsa"> <spring:message
														code="dir3caib.unidadOrganica.extinguida" /></span> <span
													class="badge-arbre btn-success llegendaCapsa"> <spring:message
														code="dir3caib.unidadOrganica.vigente" /></span>
											</div>
										</div>
									</div>

									<%-- Árbol de trazabilidad de sustitutos --%>
									<div class="tree pad-top20">
										<ul class="ampleComplet">
											<%-- Mostramos los históricos de una unidad de manera recursiva--%>
												<c:set var="nodo" value="${nodo}" scope="request" />
												<jsp:include page="../nodohistorico.jsp" flush="true" />
										</ul>
									</div>
								</div>
							</c:if>
						</c:if>
					</div>
				</div>
			</div>
		</div>
	</div>

	<c:import url="../modulos/pie.jsp" />

</body>
</html>