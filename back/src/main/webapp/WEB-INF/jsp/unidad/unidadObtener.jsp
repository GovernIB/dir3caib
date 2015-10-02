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
                            <h2><fmt:message key="unidad.obtener"/></h2>
                        </div>

                        <c:import url="../modulos/mensajes.jsp"/>
                       
                        <div class="box-content">
                            <div class="well formbox">
                            <!-- Solo mostramos las fechas si es desarrollo-->
                            <c:if test="${development}">
                                <div class="page-header"><fmt:message key="dir3caib.fechas"/></div>
                                <div class="row-fluid"><fmt:message key="dir3caib.info.fechas"/></div>
                            </c:if>

                            <form:form modelAttribute="fechasForm" method="post" cssClass="form-horizontal" onsubmit="return validateDates($('#fechaInicio'),$('#fechaFin'));">
                                <fieldset>
                                    <c:if test="${development}">
                                        <div class="row-fluid">
                                            <div class="span6">
                                                <div class="control-group">
                                                        <form:label path="fechaInicio" cssClass="control-label"><fmt:message key="dir3caib.fechainicio"/></form:label>
                                                        <div class="controls">
                                                            <form:input path="fechaInicio" maxlength="10" cssClass="input-xlarge datepicker" type="text" placeholder="dd/mm/yyyy"/> <form:errors path="fechaInicio" cssClass="help-inline" element="span"/>
                                                        </div>
                                                 </div>
                                            </div>
                                            <div class="span6">
                                                <div class="control-group">
                                                        <form:label path="fechaFin" cssClass="control-label"><fmt:message key="dir3caib.fechafin"/></form:label>
                                                        <div class="controls">
                                                            <form:input path="fechaFin" maxlength="10" cssClass="input-xlarge datepicker" type="text" placeholder="dd/mm/yyyy"/> <form:errors path="fechaFin" cssClass="help-inline" element="span"/>
                                                        </div>
                                                 </div>
                                            </div>
                                        </div>
                                    </c:if>
                                                 
                                    <div class="form-actions">
                                        <c:if test="${empty descarga}"><fmt:message key="unidad.obtener.etiqueta"/></c:if><c:if test="${not empty descarga}"><fmt:message key="dir3caib.ultima.actualizacion"/>:&nbsp;<fmt:formatDate pattern="dd/MM/yyyy" value="${descarga.fechaFin}" /></c:if>  <input type="submit" value="<fmt:message key="dir3caib.boton.obtener"/>" class="btn btn-primary">
                                        <%--<input type="submit" value="<fmt:message key="dir3caib.boton.obtener"/>" class="btn btn-primary" >--%>
                                        <input type="reset" value="<fmt:message key="dir3caib.boton.restablecer"/>" class="btn btn-primary">
                                    </div>
                                                            
                                 </fieldset>

                            </form:form>

                        </div>
                    </div>

                    </div>
                </div>

                <hr>

                <%--<footer>
                    <p><fmt:message key="dir3caib.version"/></p>
                </footer>--%>

            </div>
        </div>

        <c:import url="../modulos/pie.jsp"/>

    </body>
</html>