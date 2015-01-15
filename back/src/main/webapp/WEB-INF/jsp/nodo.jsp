<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<c:if test="${not empty  nodo.hijos}">
    <ul>
    <c:forEach var="hijo" items="${nodo.hijos}" varStatus="status">
        <!-- TODO: print the node here -->
        <li><c:if test="${hijo.estado == 'V'}"><span class="badge-arbre btn-success" style="display:closed;"><i class=""></i> ${hijo.nombre}</span></c:if>
            <c:if test="${hijo.estado != 'V'}"><span class="badge-arbre btn-warning" style="display:closed;"><i class=""></i> ${hijo.nombre}</span></c:if>
            <c:if test="${not empty  hijo.hijos}">
                <c:set var="nodo" value="${hijo}" scope="request"/>
                <jsp:include page="nodo.jsp" flush="true"/>
            </c:if>
        </li>
    </c:forEach>
    </ul>
</c:if>
