<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/modulos/includes.jsp" %>

<!DOCTYPE html>
<html>
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
                    <h2><fmt:message key="descarga.listado"/></h2>
                </div>

                <c:import url="modulos/mensajes.jsp"/>

                <div class="box-content">
                    <c:if test="${empty descargas}">
                        <div class="alert fade in">
                            <fmt:message key="descarga.existentes.notfound"/>
                        </div>
                    </c:if>
                    <c:if test="${not empty descargas}">
                            <table class="table table-bordered">
                                <colgroup>
                                    <col>
                                </colgroup>
                                <thead>
                                <tr>
                                    <th><fmt:message key="dir3caib.codigo"/></th>
                                    <th><fmt:message key="dir3caib.fechainicio"/></th>
                                    <th><fmt:message key="dir3caib.fechafin"/></th>
                                    <th><fmt:message key="dir3caib.fechaimportacion"/></th>
                                </tr>
                                </thead>

                                <tbody>
                                <c:forEach var="descarga" items="${descargas}">
                                    <tr>
                                        <td>${descarga.codigo}</td>
                                        <td><fmt:formatDate pattern="dd/MM/yyyy" value="${descarga.fechaInicio}" /></td>
                                        <td><fmt:formatDate pattern="dd/MM/yyyy" value="${descarga.fechaFin}" /></td>
                                        <td><fmt:formatDate pattern="dd/MM/yyyy" value="${descarga.fechaImportacion}" /></td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </c:if>
                </div>

            </div>
        </div>

        <hr>

        <%--  <footer>
              <p><fmt:message key="dir3caib.version"/></p>
          </footer>--%>

    </div>
</div>

<c:import url="modulos/pie.jsp"/>

</body>
</html>