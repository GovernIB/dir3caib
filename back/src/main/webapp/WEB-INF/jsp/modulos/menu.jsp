<%@page import="es.caib.dir3caib.back.security.LoginInfo"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<% request.getSession().setAttribute("loginInfo", LoginInfo.getInstance());%>

<div class="row-fluid container nav-container">
      <div class="govern-logo pull-left" style="height:63px; width:159px"><img src="<c:url value="/img/govern-logo.png"/>" width="159" height="36" alt="Govern de les Illes Balears" /></div>
      <div class="aplication-logo pull-left"><a href="<c:url value="/"/>"><img src="<c:url value="/img/logoDir.png"/>" width="159" height="36" alt="Directorio Común"/></a></div>

      <div class="pull-right main-menu">
      
      <%
        session.setAttribute("idiomes", new String[]{ "ca" , "es"});
   
       %> 
 
      
          <ul class="user-nav pull-right">
              <li>              
              <c:forEach  var="idioma"  items="${idiomes}" varStatus="status">
              <a href="<c:url value="/inicio/${idioma}"></c:url>"> 
                  <img src="<c:url value="/img/${idioma}_petit_${lang eq idioma? 'on' : 'off'}.gif"/>" 
                    alt="${idioma}" width="17" height="14" border="0" />
               </a> 
              </c:forEach>
              </li>
              <li><i class="icon-user icon-white"></i> ${pageContext.request.remoteUser}</li>
          </ul>
          <div class="clearfix"></div>

          <div class="navbar navbar-static">
              <div class="navbar-inner">
                  <ul class="nav  pull-right" role="navigation">
                      <%--<sec:authorize access="hasRole('DIR_ADMIN')">--%>                          
                          <li class="dropdown">
                              <a id="drop1" href="#" role="button" class="dropdown-toggle" data-toggle="dropdown"><fmt:message key="menu.oficina"/><b class="caret"></b></a>
                              <ul class="dropdown-menu" role="menu" aria-labelledby="drop1">
                                  <li><a href="<c:url value="/oficina/ficheros"/>"><fmt:message key="menu.listado.ficheros"/></a></li>
                                  <li><a href="<c:url value="/oficina/descarga/list"/>"><fmt:message key="menu.listado.descargas"/></a></li>
                                  <li class="divider"></li>
                                  <c:if test="${loginInfo.development== true}">
                                      <li><a href="<c:url value="/oficina/obtener"/>"><fmt:message key="oficina.obtener"/></a></li>
                                      <c:if test="${loginInfo.descargaOficina == true}"><li><a href="<c:url value="/oficina/sincronizar"/>"><fmt:message key="menu.sincronizar.oficina"/></a></li></c:if>
                                  </c:if>
                                  <c:if test="${loginInfo.development== false}">
                                    <c:if test="${loginInfo.descargaOficina == false}"><li><a href="<c:url value="/oficina/obtener"/>"><fmt:message key="oficina.obtener"/></a></li></c:if>
                                    <c:if test="${loginInfo.descargaOficina == true}"><li><a href="<c:url value="/oficina/sincronizar"/>"><fmt:message key="menu.sincronizar.oficina"/></a></li></c:if>
                                  </c:if>
                                  <li><a href="<c:url value="/oficina/list/"/>"><fmt:message key="menu.buscar.oficina"/></a></li>
                              </ul>
                          </li>                          
                          <li class="dropdown">
                              <a id="drop2" href="#" role="button" class="dropdown-toggle" data-toggle="dropdown"><fmt:message key="menu.unidad"/><b class="caret"></b></a>
                              <ul class="dropdown-menu" role="menu" aria-labelledby="drop1">
                                  <li><a href="<c:url value="/unidad/ficheros"/>"><fmt:message key="menu.listado.ficheros"/></a></li>
                                  <li><a href="<c:url value="/unidad/descarga/list"/>"><fmt:message key="menu.listado.descargas"/></a></li>
                                  <li class="divider"></li>
                                  <c:if test="${loginInfo.development== true}">
                                      <li><a href="<c:url value="/unidad/obtener"/>"><fmt:message key="unidad.obtener"/></a></li>
                                      <c:if test="${loginInfo.descargaUnidad == true}"><li><a href="<c:url value="/unidad/sincronizar"/>"><fmt:message key="menu.sincronizar.unidad"/></a></li></c:if>
                                  </c:if>
                                  <c:if test="${loginInfo.development== false}">
                                    <c:if test="${loginInfo.descargaUnidad== false}"><li><a href="<c:url value="/unidad/obtener"/>"><fmt:message key="unidad.obtener"/></a></li></c:if>
                                    <c:if test="${loginInfo.descargaUnidad == true}"><li><a href="<c:url value="/unidad/sincronizar"/>"><fmt:message key="menu.sincronizar.unidad"/></a></li></c:if>
                                  </c:if>
                                  <li><a href="<c:url value="/unidad/list/"/>"><fmt:message key="menu.buscar.unidad"/></a></li>
                              </ul>
                          </li>
                          <li class="dropdown">
                              <a id="drop3" href="#" role="button" class="dropdown-toggle" data-toggle="dropdown"><fmt:message key="menu.catalogo"/><b class="caret"></b></a>
                              <ul class="dropdown-menu" role="menu" aria-labelledby="drop1">
                                  <li><a href="<c:url value="/catalogo/ficheros"/>"><fmt:message key="menu.listado.ficheros"/></a></li>
                                  <li><a href="<c:url value="/catalogo/descarga/list"/>"><fmt:message key="menu.listado.descargas"/></a></li>
                                  <li class="divider"></li>
                                  <li><a href="<c:url value="/catalogo/obtener"/>"><fmt:message key="catalogo.obtener"/></a></li>
                              </ul>
                          </li>                          
                      <%--</sec:authorize>--%>
               </ul>
              </div>
          </div>

      </div>

</div>
