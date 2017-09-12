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
                            <h2><fmt:message key="unidad.restaurarDirectorio"/></h2>
                        </div>

                        <c:import url="../modulos/mensajes.jsp"/>
                       
                        <div class="box-content">
                            <div class="well formbox">

                                <div class="page-header"><fmt:message key="unidad.restaurarDirectorio"/></div>
                                <div class="row-fluid"><fmt:message key="unidad.restaurarDirectorio.info"/></div>


                                <form:form modelAttribute="unidadForm" method="post" cssClass="form-horizontal" onsubmit="return validateDates($('#fechaInicio'),$('#fechaFin'));">
                                    <fieldset>

                                        <div class="form-actions">

                                            <input type="submit" value="<fmt:message key="unidad.restaurarDirectorio"/>" class="btn btn-primary">

                                        </div>

                                     </fieldset>

                                </form:form>

                        </div>
                    </div>

                    </div>
                </div>

                <hr>

            </div>
        </div>

        <c:import url="../modulos/pie.jsp"/>

    </body>
</html>