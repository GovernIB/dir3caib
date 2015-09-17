<%@page import="org.springframework.context.i18n.LocaleContextHolder"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" 
%><%
   session.setAttribute("lang", LocaleContextHolder.getLocale().getLanguage());
%>
    <fmt:setLocale scope="session" value="${lang}"/> 

    <meta charset="utf-8">
    <title><fmt:message key="dir3caib.titulo"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <link rel="stylesheet" href="<c:url value="/css/bootstrap.css"/>" type="text/css"  />
    <link rel="stylesheet" href="<c:url value="/css/jquery-ui-1.10.1.custom.css"/>" type="text/css"  />
    <link rel="stylesheet" href="<c:url value="/css/bootstrap-responsive.css"/>" type="text/css"  />
    <link rel="stylesheet" href="<c:url value="/css/dir3caib.css"/>" type="text/css"  />

    <!-- Add custom CSS here -->
    <link href="<c:url value="/font-awesome/css/font-awesome.min.css"/>" rel="stylesheet">

    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
    <script type="text/javascript" src="<c:url value="/js/jquery.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/js/jquery-ui.custom.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/js/jquery-ui-timepicker-addon.js"/>"></script>

    <script type="text/javascript" src="<c:url value="/js/bootstrap.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/js/dir3caib.js"/>"></script>
