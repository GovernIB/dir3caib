<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:if test="${not empty nodo.historicos}">
    <li><span class="panel-heading btn-danger vuitanta-percent" onclick="copyToClipboard(this)" style="cursor:copy"><i
                class="">&nbsp;</i>${nodo.codigo} - ${nodo.denominacion}</span>
</c:if>
<c:if test="${empty nodo.historicos}">
    <li><span class="panel-heading btn-success vuitanta-percent" onclick="copyToClipboard(this)" style="cursor:copy"><i
                    class="">&nbsp;</i>${nodo.codigo} - ${nodo.denominacion}</b></span>
</c:if>
<%-- Mostramos los historicos de manera recursiva--%>
<c:forEach items="${nodo.historicos}" var="historico2" varStatus="loop">
    <c:if test="${loop.first}">
        <ul>
    </c:if>
    <c:set var="nodo" value="${historico2}" scope="request"/>
    <jsp:include page="nodohistorico.jsp" flush="true"/>
    <c:if test="${loop.last}">
        </li></ul>
    </c:if>
</c:forEach>
