<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:if test="${nodo.codigoEstado == 'E'}">
    <li class="ampleComplet"><span class="panel-heading btn-danger vuitanta-percent" onclick="goTo('<c:url value="/unidad/${nodo.codigo}/detalle"/>','<spring:message code="dir3caib.organismo.detalle.generar" javaScriptEscape="true"/>')" style="cursor:pointer"><i
                class="">&nbsp;</i>${nodo.codigo} - ${nodo.denominacion}</span>
</c:if>
<c:if test="${nodo.codigoEstado == 'V'}">
    <li class="ampleComplet"><span class="panel-heading btn-success vuitanta-percent" onclick="goTo('<c:url value="/unidad/${nodo.codigo}/detalle"/>','<spring:message code="dir3caib.organismo.detalle.generar" javaScriptEscape="true"/>')" style="cursor:pointer"><i
                    class="">&nbsp;</i>${nodo.codigo} - ${nodo.denominacion}</b></span>
</c:if>
<%-- Mostramos los historicos de manera recursiva--%>
<c:forEach items="${nodo.historicos}" var="historico2" varStatus="loop">
    <c:if test="${loop.first}">
        <ul class="ampleComplet">
    </c:if>
    <c:set var="nodo" value="${historico2}" scope="request"/>
    <jsp:include page="nodohistorico.jsp" flush="true"/>
    <c:if test="${loop.last}">
        </li></ul>
    </c:if>
</c:forEach>
