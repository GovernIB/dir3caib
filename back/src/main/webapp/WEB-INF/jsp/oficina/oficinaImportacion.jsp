<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/modulos/includes.jsp" %>

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
                            <h2><fmt:message key="oficina.importacion"/></h2>
                        </div>

                        <c:import url="../modulos/mensajes.jsp"/>
                       
                        <div class="box-content"> 
                        
                            <table class="table table-bordered">
                                <colgroup>
                                    <col>
                                    <col>
                                    <col width="101">
                                </colgroup>
                                <thead>
                                    <tr>
                                        <th><fmt:message key="dir3caib.fichero"/></th>
                                        <th><fmt:message key="dir3caib.existente"/></th>
                                        <th><fmt:message key="dir3caib.importado"/></th> 
                                    </tr>
                                </thead>

                                <tbody>
                                    <c:forEach var="fichero" items="${ficheros}">
                                        <tr>
                                            <td>${fichero}</td>
                                            <td class="center">
                                                <c:if test="${fn:contains(existentes, fichero)}"><span class="badge badge-success">Si</span></c:if>
                                                <c:if test="${not fn:contains(existentes, fichero)}"><span class="badge badge-important">No</span></c:if>
                                            </td>
                                            <td class="center"> 
                                                <c:if test="${fn:contains(procesados, fichero)}"><span class="badge badge-success">Si</span></c:if>
                                                <c:if test="${not fn:contains(procesados, fichero)}"><span class="badge badge-important">No</span></c:if>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                  </tbody>
                              </table>
                        
                        </div>

                    </div>
                </div>

                <hr>

            <%--    <footer>
                    <p><fmt:message key="dir3caib.version"/></p>
                </footer>
--%>
            </div>
        </div>

        <c:import url="../modulos/pie.jsp"/>

    </body>
</html>