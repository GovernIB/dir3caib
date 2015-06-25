<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<c:if test="${not empty  nodo.hijos}">
    <ul>
    <c:forEach var="hijo" items="${nodo.hijos}" varStatus="status">
        <!-- TODO: print the node here -->
        <li><span class="${param.color}" style="display:closed;"><i class=""></i> ${hijo.id} - ${hijo.nombre}</span>
            <c:if test="${not empty  hijo.hijos}">
                <c:set var="nodo" value="${hijo}" scope="request"/>
                <jsp:include page="nodo.jsp" flush="true">
                    <jsp:param name="color" value="badge-arbre btn-info"/>
                </jsp:include>
            </c:if>
        </li>
    </c:forEach>
    </ul>
</c:if>
