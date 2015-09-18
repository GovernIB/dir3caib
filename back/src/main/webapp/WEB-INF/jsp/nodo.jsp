<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<c:if test="${not empty  nodo.hijos}">

    <c:forEach var="hijo" items="${nodo.hijos}" varStatus="status">
        <!-- TODO: print the node here -->
        <li><span class="badge-arbre btn-primary" style="display:closed;"><i class=""></i> ${hijo.id} - ${hijo.nombre}</span>

            <!--Pintamos oficinas responsables -->
            <ul>
            <c:forEach var="ofiDependiente" items="${hijo.oficinasDependientes}">
                <li><span class="badge-arbre btn-warning" style="display:closed;"><i class="fa fa-home"></i>  ${ofiDependiente.id} - ${ofiDependiente.nombre}</span>
                    <!-- Pintamos las auxiliares -->
                    <c:if test="${not empty ofiDependiente.oficinasAuxiliares }">
                        <ul>
                            <c:forEach var="ofiAuxiliar" items="${ofiDependiente.oficinasAuxiliares}">
                                <li><span class="badge-arbre btn-ofaux" style="display:closed;"><i class="fa fa-home"></i> ${ofiAuxiliar.id} - ${ofiAuxiliar.nombre}</span></li>
                            </c:forEach>
                        </ul>
                    </c:if>
                </li>
            </c:forEach>
            <!--Pintamos relaciones organizativas -->
            <c:forEach var="ofiFuncional" items="${hijo.oficinasFuncionales}">
                <li><span class="badge-arbre btn-success"><i class="fa fa-institution"></i> ${ofiFuncional.id} -${ofiFuncional.nombre}</span></li>
            </c:forEach>
            </ul>

            <c:if test="${not empty  hijo.hijos}">
                <c:set var="nodo" value="${hijo}" scope="request"/>
                <jsp:include page="nodo.jsp" flush="true"/>
            </c:if>
        </li>
    </c:forEach>
</c:if>
