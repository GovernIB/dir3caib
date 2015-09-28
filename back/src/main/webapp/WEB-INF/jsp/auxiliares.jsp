<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<ul>
    <c:forEach var="ofiAux" items="${oficinaAuxiliar.oficinasAuxiliares}">
        <li><a href="javascript:void(0);"><span class="badge-arbre btn-ofaux" style="display:closed;"><i class="fa fa-home"></i> ${ofiAux.id} - ${ofiAux.nombre}</span></a>
            <c:set var="oficinaAuxiliar" value="${ofiAux}" scope="request"/>
            <jsp:include page="auxiliares.jsp" flush="true"/>
        </li>
    </c:forEach>
</ul>