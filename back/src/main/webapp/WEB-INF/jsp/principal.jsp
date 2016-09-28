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
                        <table class="table table-bordered">
                        <colgroup>
                            <col>
                        </colgroup>
                        <thead>
                        <tr>
                            <th></th>
                            <th><fmt:message key="menu.catalogo"/></th>
                            <th><fmt:message key="menu.unidad"/></th>
                            <th><fmt:message key="menu.oficina"/></th>
                        </tr>
                        </thead>

                        <tbody>
                        <tr>
                            <td><strong><spring:message code="dir3caib.ultima.actualizacion"/></strong></td>
                            <td><fmt:formatDate pattern="dd/MM/yyyy"
                                                value="${ultimaDescargaCatalogo.fechaImportacion}"/></td>
                            <td><fmt:formatDate pattern="dd/MM/yyyy"
                                                value="${ultimaDescargaUnidad.fechaImportacion}"/></td>
                            <td><fmt:formatDate pattern="dd/MM/yyyy"
                                                value="${ultimaDescargaOficina.fechaImportacion}"/></td>
                        </tr>
                        </tbody>
                    </table>
                    </div>
                </div>
            </div>

            <hr>

           <%-- <footer>
                <p><fmt:message key="dir3caib.version"/></p>
            </footer>--%>

        </div>
    </div>

    <c:import url="modulos/pie.jsp"/>

  </body>
</html>