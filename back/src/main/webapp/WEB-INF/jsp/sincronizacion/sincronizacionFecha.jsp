<%--
  Created by IntelliJ IDEA.
  User: mgonzalez
  Date: 10/07/2026
  Time: 11:48
--%>
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
          <h2><fmt:message key="sincronizacion.fecha"/></h2>
        </div>

        <c:import url="../modulos/mensajes.jsp"/>

        <div class="box-content">
          <div class="well formbox">

            <form:form modelAttribute="sincroForm" method="post" cssClass="form-horizontal">
              <fieldset>
                  <div class="row-fluid">
                    <div class="span12">
                      <div class="control-group">
                        <label class="control-label" for="fechaLimite" rel="popupAbajo"
                               data-content="<fmt:message key="sincronizacion.fechalimite"/>"
                               data-toggle="popover">
                          <fmt:message key="sincronizacion.fechalimite"/>
                        </label>
                        <div class="controls">
                          <div class="input-group date" id="fechaLimitePicker">
                            <form:input path="fechaLimite" type="text" cssClass="form-control"
                                        maxlength="10" placeholder="dd/mm/yyyy" name="fechaLimite"/>
                            <span class="input-group-addon"><span class="fa fa-calendar"></span></span>
                          </div>
                          <form:errors path="fechaLimite" cssClass="help-block" element="span"/>
                        </div>
                      </div>
                    </div>
                  </div>

                <div class="form-actions">
                  <input type="submit" value="<fmt:message key="sincronizacion.sincronizar"/>" class="btn btn-primary">
                </div>

              </fieldset>

            </form:form>

          </div>
        </div>

      </div><!--/span-->

    </div><!--/row-->


    <hr>
  </div>
</div> <!-- /container -->
<script type="text/javascript">
  $(document).ready(function () {
    $('#fechaLimitePicker input').datepicker({
      dateFormat: 'dd/mm/yy',
      changeMonth: true,
      changeYear: true,
      showButtonPanel: true
    });
  });
</script>

<c:import url="../modulos/pie.jsp"/>


</body>
</html>
