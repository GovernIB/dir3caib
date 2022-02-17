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
        <h2><fmt:message key="unidad.listado"/></h2>
    </div>

    <c:import url="../modulos/mensajes.jsp"/>

    <div class="box-content">
        <div class="well formbox">

            <div class="page-header">
                <fmt:message key="unidad.busqueda"/>
            </div>

            <form:form modelAttribute="unidadBusqueda" method="post" cssClass="form-horizontal">
                <form:hidden path="pageNumber"/>
                <fieldset>

                    <div class="row-fluid">

                        <div class="span6">
                            <div class="control-group">
                                <form:label path="unidad.denominacion" cssClass="control-label"><fmt:message
                                        key="unidad.denominacion"/></form:label>
                                <div class="controls">
                                    <form:input path="unidad.denominacion" cssClass="input-xlarge"/> <form:errors
                                        path="unidad.denominacion" cssClass="help-inline" element="span"/>
                                </div>
                            </div>
                        </div>
                        <div class="span6">
                            <div class="control-group">
                                <form:label path="unidad.codigo" cssClass="control-label"><fmt:message
                                        key="unidad.codigo"/></form:label>
                                <div class="controls">
                                    <form:input path="unidad.codigo" cssClass="input-xlarge" style="width:180px"/>
                                    	<span> - v. </span>
                                        <form:input path="unidad.version" cssClass="input-xlarge" style="width:30px"/> 
                                        <form:errors path="unidad.codigo" cssClass="help-inline" element="span" />
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row-fluid">
                    	<div class="span6">
                    		<div class="control-group">
                    			<form:label path="unidad.nifcif" cssClass="control-label"><fmt:message
                                        key="unidad.nifcif"/></form:label>
                                <div class="controls">
                                    <form:input path="unidad.nifcif" cssClass="input-xlarge"/> 
                                    <form:errors path="unidad.nifcif" cssClass="help-inline" element="span"/>
                                </div>
                    		</div>
                    	</div>
                    	<div class="span6">
                            <div class="control-group">
                                <form:label path="unidad.codAmbitoTerritorial.codigoAmbito" cssClass="control-label"><fmt:message key="unidad.ambitoTerritorial"/></form:label>
                                <div class="controls">
                                    <form:select path="unidad.codAmbitoTerritorial.codigoAmbito"  cssClass="input-xlarge"/>
                                    <form:errors path="unidad.codAmbitoTerritorial.codigoAmbito" cssClass="help-inline" element="span"/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row-fluid">
                        <div class="span6">
                            <div class="control-group">
                                <form:label path="unidad.nivelAdministracion.codigoNivelAdministracion" cssClass="control-label"><fmt:message key="unidad.administracion"/></form:label>
                                <div class="controls">
                                    <form:select path="unidad.nivelAdministracion.codigoNivelAdministracion" onchange='actualizarAmbitos()' cssClass="input-xlarge">
                                        <form:option value="-1" label="..."/>
                                        <form:options items="${administraciones}" itemValue="codigoNivelAdministracion" itemLabel="descripcionNivelAdministracion"/>
                                    </form:select>
                                    <form:errors path="unidad.nivelAdministracion.codigoNivelAdministracion" cssClass="help-inline" element="span"/>
                                </div>
                            </div>
                        </div>
                        <div class="span6">
                            <div class="control-group">
                                <form:label path="unidad.codAmbProvincia.codigoProvincia" cssClass="control-label"><fmt:message key="unidad.provincia"/></form:label>
                                <div class="controls">
                                    <form:select path="unidad.codAmbProvincia.codigoProvincia"  cssClass="input-xlarge"/>
                                    <form:errors path="unidad.codAmbProvincia.codigoProvincia" cssClass="help-inline" element="span"/>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row-fluid">
                        <div class="span6">
                            <div class="control-group">
                                <form:label path="unidad.codComunidad.codigoComunidad" cssClass="control-label"><fmt:message key="unidad.comunidad"/></form:label>
                                <div class="controls">
                                    <form:select path="unidad.codComunidad.codigoComunidad" onchange='actualizarProvincias()' cssClass="input-xlarge">
                                        <form:option value="-1" label="..."/>
                                        <form:options items="${comunidades}" itemValue="codigoComunidad" itemLabel="descripcionComunidad"/>
                                    </form:select>
                                    <form:errors path="unidad.codComunidad.codigoComunidad" cssClass="help-inline" element="span"/>
                                </div>
                            </div>
                        </div>
                        <div class="span6">
                            <div class="control-group">
                                <form:label path="unidad.estado" cssClass="control-label"><fmt:message key="unidad.estado"/></form:label>
                                <div class="controls">
                                    <form:select path="unidad.estado.codigoEstadoEntidad"  cssClass="input-xlarge">
                                        <form:option value="-1" label="..."/>
                                        <form:options items="${estadosEntidad}" itemValue="codigoEstadoEntidad" itemLabel="descripcionEstadoEntidad"/>
                                    </form:select>
                                    <form:errors path="unidad.estado.codigoEstadoEntidad" cssClass="help-inline" element="span"/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row-fluid">
                        <div class="span6">
                            <div class="control-group">
                                <form:label path="unidadRaiz" cssClass="control-label"><fmt:message
                                        key="unidad.unidades.raiz"/></form:label>
                                <div class="controls">
                                    <form:checkbox path="unidadRaiz" value="false" cssClass="input-xlarge"/>
                                </div>
                            </div>
                        </div>
                        <div class="span6">
                            <div class="control-group">
                                <form:label path="denominacionCooficial" cssClass="control-label largeLabel"><fmt:message
                                        key="unidad.denominacion.cooficial"/></form:label>
                                <div class="controls">
                                    <form:checkbox path="denominacionCooficial" value="true" cssClass="input-xlarge"/>
                                </div>
                            </div>
                        </div>
                        
                    </div>


                    <div class="form-actions">
                        <input type="submit" value="<fmt:message key="dir3caib.boton.buscar"/>" class="btn btn-primary">
                        <input type="reset" value="<fmt:message key="dir3caib.boton.restablecer"/>" class="btn btn-primary">
                    </div>

                </fieldset>

            </form:form>

        </div>
    </div>

    <div class="box-content">

        <c:if test="${paginacion != null && empty paginacion.listado}">
            <div class="alert fade in">
                <button type="button" class="close" data-dismiss="alert">&times;</button>
                <fmt:message key="dir3caib.busqueda.vacio"/> <strong><fmt:message key="unidad.unidad"/></strong>
            </div>
        </c:if>

        <c:if test="${not empty paginacion.listado}">
            <div class="well" style="margin-top:8px;padding:4px 4px">
                <c:if test="${paginacion.totalResults > 1}">
                    <fmt:message key="dir3caib.resultados"/> <strong>${paginacion.totalResults}</strong> <fmt:message key="unidad.unidades"/>
                </c:if>
                <c:if test="${paginacion.totalResults == 1}">
                    <fmt:message key="dir3caib.resultado"/> <strong>${paginacion.totalResults}</strong> <fmt:message key="unidad.unidad"/>
                </c:if>

                <p class="pull-right">Página <strong>${paginacion.currentIndex}</strong> de ${paginacion.totalPages}</p>
            </div>
            <table class="table table-bordered">

                <thead>
                    <tr>
                        <th><fmt:message key="unidad.codigo"/></th>
                        <th><fmt:message key="unidad.denominacion"/></th>
                        <th><fmt:message key="unidad.superior"/></th>
                        <th><fmt:message key="unidad.raiz"/></th>
                        <th><fmt:message key="unidad.edp"/></th>
                        <th><fmt:message key="dir3caib.sir"/></th>
                        <th><fmt:message key="unidad.estado"/></th>
                        <th class="center"><spring:message code="dir3caib.acciones"/></th>
                    </tr>
                </thead>

                <tbody>
                <c:forEach var="nodo" items="${paginacion.listado}">
                    <tr>

                        <td>${nodo.codigo}</td>
                        <td>${nodo.denominacion}</td>
                        <td>${nodo.superior}</td>
                        <td>${nodo.raiz}</td>
                        <td>
                            <c:if test="${nodo.esEdp}">
                                <p class="centrat" rel="edp"
                                   data-content="<c:if test="${not empty nodo.edpPrincipal}">Edp Principal: <c:out value="${nodo.edpPrincipal}" escapeXml="true"/></c:if>"
                                   data-toggle="popover"><span class="label label-success">Sí</span></p>
                            </c:if>
                            <c:if test="${!nodo.esEdp}">
                                <span class="label label-danger">No</span>
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${nodo.tieneOficinaSir}">
                                <span class="label label-success">Sí</span>
                            </c:if>
                            <c:if test="${!nodo.tieneOficinaSir}">
                                <span class="label label-danger">No</span>
                            </c:if>
                        </td>
                        <td>${nodo.descripcionEstado}</td>
                        <td class="center">
                        	<c:choose>
                        		<c:when test="${unidadBusqueda.denominacionCooficial}">
                        			<a class="btn btn-primary btn-sm" href="<c:url value="/unidad/${nodo.codigo}/detall"/>" title="<spring:message code="dir3caib.detalle"/>"><span class="fa fa-eye"></span></a>
                        		</c:when>
                        		<c:otherwise>
                        			<a class="btn btn-primary btn-sm" href="<c:url value="/unidad/${nodo.codigo}/detalle"/>" title="<spring:message code="dir3caib.detalle"/>"><span class="fa fa-eye"></span></a>
                        		</c:otherwise>
                        	</c:choose>  
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <!-- Paginacion -->
            <c:import url="../modulos/paginacionBusqueda.jsp">
                <c:param name="entidad" value="unidad"/>
            </c:import>

        </c:if>
    </div>

    </div><!--/span-->

    </div><!--/row-->


    <hr>



    </div>
    </div> <!-- /container -->

    <c:import url="../modulos/pie.jsp"/>


    <script type="text/javascript">

        $("[rel='edp']").popover({ trigger: 'hover',placement: 'top',container:"body", html:true});

        <c:url var="ambitosTerritoriales" value="/rest/catalogo/ambitosTerritoriales" />
        <c:url var="provincias" value="/rest/catalogo/provincias/comunidadAutonoma" />

        $(document).ready(function() {
           // actualizamos los ambitos y provincias para cuando recargamos la pagina.
           actualizarAmbitos();
           actualizarProvincias();


        });

        // Cargamos los ambitos territoriales de la administracion seleccionada
        function actualizarAmbitos(){
            actualizarSelect('${ambitosTerritoriales}', '#unidad\\.codAmbitoTerritorial\\.codigoAmbito', $('#unidad\\.nivelAdministracion\\.codigoNivelAdministracion option:selected').val(), '${unidadBusqueda.unidad.codAmbitoTerritorial.codigoAmbito}', true);
        }

        // Cargamos las provincias de la comunidad autonoma seleccionada
        function actualizarProvincias(){
            actualizarSelect('${provincias}', '#unidad\\.codAmbProvincia\\.codigoProvincia', $('#unidad\\.codComunidad\\.codigoComunidad option:selected').val(), '${unidadBusqueda.unidad.codAmbProvincia.codigoProvincia}', true);
        }

    </script>


</body>
</html>