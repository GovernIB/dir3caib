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
                            <h2><fmt:message key="catalogo.obtener"/></h2>
                        </div>

                        <c:import url="../modulos/mensajes.jsp"/>
                       
                        <div class="box-content">
                            <div class="well formbox">


                                <div class="page-header"><fmt:message key="catalogo.catalogo"/></div>

                                <form:form modelAttribute="catalogo" method="post" cssClass="form-horizontal">
                                    <fieldset>
                                        <div class="alert fade in">
                                            <c:if test="${empty descarga}"><fmt:message key="catalogo.obtener.etiqueta"/></c:if><c:if test="${not empty descarga}"><fmt:message key="dir3caib.ultima.actualizacion"/>:&nbsp;<fmt:formatDate pattern="dd/MM/yyyy" value="${descarga.fechaFin}" /></c:if>  <input type="submit" value="<fmt:message key="dir3caib.boton.obtener"/>" class="btn btn-primary">
                                        </div>

                                     </fieldset>


                                </form:form>

                        </div>
                    </div>

                    </div>
                </div>

                <hr>

               <%-- <footer>
                    <p><fmt:message key="dir3caib.version"/></p>
                </footer>--%>

            </div>
        </div>

        <c:import url="../modulos/pie.jsp"/>

    </body>
</html>