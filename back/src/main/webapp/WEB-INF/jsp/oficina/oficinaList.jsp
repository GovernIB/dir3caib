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
        <h2><fmt:message key="oficina.listado"/></h2>
    </div>

    <c:import url="../modulos/mensajes.jsp"/>

    <div class="box-content">
        <div class="well formbox">

            <div class="page-header">
                <fmt:message key="oficina.busqueda"/>
            </div>

            <form:form modelAttribute="oficinaBusqueda" method="post" cssClass="form-horizontal">
                <form:hidden path="pageNumber"/>
                <fieldset>

                    <div class="row-fluid">
                        <div class="span6">
                            <div class="control-group">
                                <form:label path="oficina.denominacion" cssClass="control-label"><fmt:message
                                        key="oficina.denominacion"/></form:label>
                                <div class="controls">
                                    <form:input path="oficina.denominacion" cssClass="input-xlarge"/> <form:errors
                                        path="oficina.denominacion" cssClass="help-inline" element="span"/>
                                </div>
                            </div>
                        </div>
                        <div class="span6">
                            <div class="control-group">
                                <form:label path="oficina.codigo" cssClass="control-label"><fmt:message
                                        key="oficina.codigo"/></form:label>
                                <div class="controls">
                                    <form:input path="oficina.codigo" cssClass="input-xlarge"/> <form:errors
                                        path="oficina.codigo" cssClass="help-inline" element="span"/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row-fluid">
                        <div class="span6">
                            <div class="control-group">
                                <form:label path="oficina.nivelAdministracion.codigoNivelAdministracion" cssClass="control-label"><fmt:message key="oficina.administracion"/></form:label>
                                <div class="controls">
                                    <form:select path="oficina.nivelAdministracion.codigoNivelAdministracion" cssClass="input-xlarge">
                                        <form:option value="-1" label="..."/>
                                        <form:options items="${administraciones}" itemValue="codigoNivelAdministracion" itemLabel="descripcionNivelAdministracion"/>
                                    </form:select>
                                    <form:errors path="oficina.nivelAdministracion.codigoNivelAdministracion" cssClass="help-inline" element="span"/>
                                </div>
                            </div>
                        </div>
                        <div class="span6">
                            <div class="control-group">
                                <form:label path="oficina.estado" cssClass="control-label"><fmt:message key="oficina.estado"/></form:label>
                                <div class="controls">
                                    <form:select path="oficina.estado.codigoEstadoEntidad"  cssClass="input-xlarge">
                                        <form:option value="-1" label="..."/>
                                        <form:options items="${estadosEntidad}" itemValue="codigoEstadoEntidad" itemLabel="descripcionEstadoEntidad"/>
                                    </form:select>
                                    <form:errors path="oficina.estado.codigoEstadoEntidad" cssClass="help-inline" element="span"/>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row-fluid">
                        <div class="span6">
                            <div class="control-group">
                                <form:label path="oficina.codComunidad.codigoComunidad" cssClass="control-label"><fmt:message key="oficina.comunidad"/></form:label>
                                <div class="controls">
                                    <form:select path="oficina.codComunidad.codigoComunidad" onchange='actualizarProvincias()' cssClass="input-xlarge">
                                       <form:option value="-1" label="..."/>
                                       <form:options items="${comunidades}" itemValue="codigoComunidad" itemLabel="descripcionComunidad"/>
                                    </form:select>
                                     <form:errors path="oficina.codComunidad.codigoComunidad" cssClass="help-inline" element="span"/>
                                </div>
                            </div>
                        </div>
                        <div class="span6">
                            <div class="control-group">
                                <form:label path="oficina.localidad.provincia.codigoProvincia" cssClass="control-label"><fmt:message key="oficina.provincia"/></form:label>
                                <div class="controls">
                                    <form:select path="oficina.localidad.provincia.codigoProvincia"  cssClass="input-xlarge"/>
                                    <form:errors path="oficina.localidad.provincia.codigoProvincia" cssClass="help-inline" element="span"/>
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
                <fmt:message key="dir3caib.busqueda.vacio"/> <strong><fmt:message key="oficina.oficina"/></strong>
            </div>
        </c:if>

        <c:if test="${not empty paginacion.listado}">
            <div class="well" style="margin-top:8px;padding:4px 4px">
                <c:if test="${paginacion.totalResults > 1}">
                    <fmt:message key="dir3caib.resultados"/> <strong>${paginacion.totalResults}</strong> <fmt:message key="oficina.oficinas"/>
                </c:if>
                <c:if test="${paginacion.totalResults == 1}">
                    <fmt:message key="dir3caib.resultado"/> <strong>${paginacion.totalResults}</strong> <fmt:message key="oficina.oficina"/>
                </c:if>

                <p class="pull-right">Página <strong>${paginacion.currentIndex}</strong> de ${paginacion.totalPages}</p>
            </div>
            <table class="table table-bordered">
                <colgroup>
                    <col>
                    <col>
                    <col>
                </colgroup>
                <thead>
                <tr>
                    <th><fmt:message key="oficina.codigo"/></th>
                    <th><fmt:message key="oficina.denominacion"/></th>
                    <th><fmt:message key="oficina.responsable"/></th>
                    <th><fmt:message key="oficina.unidadresponsable"/></th>
                    <th><fmt:message key="dir3caib.sir"/></th>
                    <th><fmt:message key="oficina.estado"/></th>
                </tr>
                </thead>

                <tbody>
                <c:forEach var="nodo" items="${paginacion.listado}">
                    <tr>

                        <td>${nodo.codigo}</td>
                        <td><a onclick="verArbol('<c:url value="/oficina/${nodo.codigo}/arbol"/>')"
                               onmouseover="this.style.cursor='pointer';">${nodo.denominacion}</a></td>
                            <%--<td><a href="<c:url value="/oficina/${oficina.codigo}/detalle"/>">${oficina.denominacion}</a></td>--%>
                        <td><c:if
                                test="${not empty nodo.raiz}">${nodo.raiz}</c:if><c:if
                                test="${empty nodo.superior}"><fmt:message
                                key="oficina.no.responsable"/></c:if></td>
                        <td>${nodo.superior}</td>
                        <td>
                            <c:if test="${nodo.tieneOficinaSir}">
                                <span class="label label-success">Sí</span>
                            </c:if>
                            <c:if test="${!nodo.tieneOficinaSir}">
                                <span class="label label-danger">No</span>
                            </c:if>
                        </td>
                        <td>${nodo.descripcionEstado}</td>


                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <!-- Paginacion -->
            <c:import url="../modulos/paginacionBusqueda.jsp">
                <c:param name="entidad" value="oficina"/>
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


        <c:url var="provincias" value="/rest/catalogo/provincias/comunidadAutonoma" />

                $(document).ready(function() {
                   // actualizamos las provincias para cuando recargamos la pagina.
                   actualizarProvincias();


                });

        // Cargamos las provincias de la comunidad autonoma seleccionada
        function actualizarProvincias(){
            actualizarSelect('${provincias}', '#oficina\\.localidad\\.provincia\\.codigoProvincia', $('#oficina\\.codComunidad\\.codigoComunidad option:selected').val(), '${oficinaBusqueda.oficina.localidad.provincia.codigoProvincia}', true);
        }

    </script>

    <script type="text/javascript">
        function verArbol(url) {
            $.ajax({
                type:'GET',
                beforeSend: function(objeto){
                    waitingDialog.show('<spring:message code="dir3caib.organismo.arbol.generar" javaScriptEscape='true'/>', {dialogSize: 'm', progressType: 'warning'});
                },
                success:function(respuesta){
                    goTo(url);
                }
            });
        }
    </script>


</body>
</html>