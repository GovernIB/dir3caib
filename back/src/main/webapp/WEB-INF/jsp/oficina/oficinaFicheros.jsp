<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/modulos/includes.jsp" %>
<%@page import="es.caib.dir3caib.persistence.model.Descarga" %>
<%@page import="es.caib.dir3caib.persistence.model.Dir3caibConstantes" %>

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
                            <h2><fmt:message key="oficina.existentes"/></h2>
                        </div>

                        <c:import url="../modulos/mensajes.jsp"/>
                       
                        <div class="box-content">
                           <c:if test="${empty descarga}">
                                <div class="alert fade in">
                                <fmt:message key="oficina.existentes.notfound"/><strong><a href="<c:url value="/oficina/sincronizar"/>"><fmt:message key="dir3caib.obtenerws"/></a></strong>
                            </div>
                            </c:if>
                            <c:if test="${not empty descarga}">
                                <c:if test="${not empty ficheros}">

                                    <table class="table table-bordered">
                                        <colgroup>
                                            <col>
                                        </colgroup>
                                        <thead>
                                            <tr>
                                                <th><fmt:message key="dir3caib.fichero"/></th>
                                                <th><fmt:message key="dir3caib.intervalo"/> (<fmt:message
                                                        key="dir3caib.fechainicio"/> - <fmt:message
                                                        key="dir3caib.fechafin"/>)
                                                </th>
                                                <th><fmt:message key="dir3caib.fechaimportacion"/></th>
                                            </tr>
                                        </thead>

                                        <tbody>
                                        <c:forEach var="fichero" items="${ficheros}">
                                                <tr>
                                                    <td>
                                                        <a href="<c:url value="/archivo/${fichero}/${descarga.codigo}"/>"
                                                           target="_blank">${fichero}</a></td>
                                                    <td>( <c:if
                                                            test="${empty descarga.fechaInicio}"> ******* </c:if><fmt:formatDate
                                                            pattern="dd/MM/yyyy" value="${descarga.fechaInicio}"/> -
                                                        <c:if test="${empty descarga.fechaFin}"> ******* </c:if><fmt:formatDate
                                                                pattern="dd/MM/yyyy" value="${descarga.fechaFin}"/> )
                                                    </td>
                                                    <td><fmt:formatDate pattern="dd/MM/yyyy" value="${descarga.fechaImportacion}" /></td>
                                                </tr>
                                            </c:forEach>
                                          </tbody>
                                      </table>
                                    <div class="form-horizontal">
                                      <div class="form-actions">
                                          <input type="button" value="<fmt:message key="dir3caib.boton.importar"/>" onclick="javascript:confirmDescarga('<c:url value="/oficina/importar"/>','<fmt:message key="dir3caib.confirm.importar"/>');" class="btn btn-primary">
                                       </div>
                                    </div>
                                    <jsp:include page="../modalSincro.jsp" flush="true"/>
                                </c:if>
                                <% Descarga descarga = (Descarga) request.getAttribute("descarga");
                                    if (Dir3caibConstantes.CODIGO_RESPUESTA_VACIO.equals(descarga.getEstado())) {%>
                                <fmt:message key="dir3caib.datos.nohay"/>
                                <%} %>
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