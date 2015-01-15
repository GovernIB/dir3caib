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