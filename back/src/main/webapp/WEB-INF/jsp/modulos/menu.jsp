<%@page import="es.caib.dir3caib.back.security.LoginInfo" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<% request.getSession().setAttribute("loginInfo", LoginInfo.getInstance());%>

<div class="row-fluid container nav-container">
    <div class="govern-logo pull-left"><img src="<c:url value="/img/govern-logo.png"/>"
                                            width="70" height="70"
                                            alt="Govern de les Illes Balears"/></div>
    <div class="aplication-logo pull-left"><a href="<c:url value="/"/>"><img src="<c:url value="/img/logoDir.png"/>"
                                                                             width="159" height="36"
                                                                             alt="Directorio Común"/></a></div>

    <div class="pull-right main-menu">

        <%
            session.setAttribute("idiomes", new String[]{"ca", "es"});

        %>

        <ul class="user-nav pull-right">
            <li>
                <c:forEach var="idioma" items="${idiomes}" varStatus="status">
                    <a href="<c:url value="${requestScope.requestURI}?lang=${idioma}"/>">
                        <img src="<c:url value="/img/${idioma}_petit_${lang eq idioma? 'on' : 'off'}.gif"/>"
                             alt="${idioma}" width="17" height="14" border="0"/>
                    </a>
                </c:forEach>
            </li>
            <li><i class="icon-user icon-white"></i> ${pageContext.request.remoteUser}</li>
        </ul>
        <div class="clearfix"></div>

        <div class="navbar navbar-static">
            <div class="navbar-inner">
                <ul class="nav  pull-right" role="navigation">

                    <%--MENÚ SINCRONIZACIÓN--%>
                    <sec:authorize access="hasRole('DIR_ADMIN')">
                        <li class="dropdown">
                            <a id="drop4" href="#" role="button" class="dropdown-toggle"
                               data-toggle="dropdown"><fmt:message key="menu.sincronizacion"/><b class="caret"></b></a>
                            <ul class="dropdown-menu" role="menu" aria-labelledby="drop4">
                                <li><a href="<c:url value="/sincronizacion/list"/>"><fmt:message key="menu.listado.sincronizacion"/></a></li>
                                <li class="divider"></li>
                                <li><a href="javascript:void(0);" onclick="if (! confirmDescarga('<c:url value="/sincronizacion/sincronizar"/>','<fmt:message key="menu.sincronizar.confirm"/>')) { return false; }"><fmt:message key="menu.sincronizar.directorio"/></a></li>
                                <li><a href="javascript:void(0);" onclick="if (! confirmDescarga('<c:url value="/sincronizacion/catalogo"/>','<fmt:message key="menu.sincronizar.confirm"/>')) { return false; }"><fmt:message key="menu.sincronizar.catalogo"/></a></li>
                                <c:if test="${loginInfo.development == true}">
                                    <li class="divider"></li>
                                    <li><a href="<c:url value="/sincronizacion/restaurarDirectorio"/>"><fmt:message key="sincronizacion.restaurarDirectorio"/></a></li>
                                </c:if>

                            </ul>
                        </li>
                    </sec:authorize>

                    <%--MENÚ UNIDADES--%>
                    <li class="dropdown">
                        <a id="drop2" href="#" role="button" class="dropdown-toggle" data-toggle="dropdown"><fmt:message
                                key="menu.unidad"/><b class="caret"></b></a>
                        <ul class="dropdown-menu" role="menu" aria-labelledby="drop1">

                            <li><a href="<c:url value="/unidad/list"/>"><fmt:message key="menu.buscar.unidad"/></a></li>

                        </ul>
                    </li>

                    <%--Menú Oficinas--%>
                    <li class="dropdown">
                        <a id="drop1" href="#" role="button" class="dropdown-toggle" data-toggle="dropdown">
                            <fmt:message key="menu.oficina"/><b class="caret"></b></a>
                        <ul class="dropdown-menu" role="menu" aria-labelledby="drop1">

                            <li><a href="<c:url value="/oficina/list"/>"><fmt:message key="menu.buscar.oficina"/></a></li>

                        </ul>
                    </li>

                </ul>
            </div>
        </div>

    </div>
    <!-- todo revisar y probar -->
    <jsp:include page="../modalSincro.jsp" flush="true"/>
</div>
