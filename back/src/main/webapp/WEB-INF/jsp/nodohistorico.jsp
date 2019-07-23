<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<ul>
    <c:if test="${not empty nodo.historicos}">
        <li>${nodo.codigo} - ${nodo.denominacion}</li>
    </c:if>
    <c:if test="${empty nodo.historicos}">
        <li><b>${nodo.codigo} - ${nodo.denominacion}</b></li>
    </c:if>
    <%-- Mostramos los historicos de manera recursiva--%>
    <c:forEach items="${nodo.historicos}" var="historico2">
        <c:set var="nodo" value="${historico2}" scope="request"/>
        <jsp:include page="nodohistorico.jsp" flush="true"/>
    </c:forEach>
</ul>
