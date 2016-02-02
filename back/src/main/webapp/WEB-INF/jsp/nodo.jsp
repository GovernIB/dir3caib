<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<c:if test="${not empty  nodo.hijos}">

    <c:forEach var="hijo" items="${nodo.hijos}" varStatus="status">

        <!-- print the node here -->
        <c:if test="${empty oficinas}">
        <li><span class="badge-arbre btn-primary" style="display:closed;"><i class=""></i> ${hijo.id} - ${hijo.nombre}</span>
        </c:if>
        <c:if test="${not empty oficinas}">
            <li><span class="badge-arbre btn-ofaux" style="display:closed;"><i class=""></i> ${hijo.id} - ${hijo.nombre}</span>
        </c:if>

            <!--Pintamos oficinas responsables -->
            <ul>
                <c:forEach var="ofiDependiente" items="${hijo.oficinasDependientes}">
                    <li><a href="javascript:void(0);"><span class="badge-arbre btn-warning" style="display:closed;"><i class="fa fa-home"></i>  ${ofiDependiente.id} - ${ofiDependiente.nombre}</span></a>
                        <!-- Pintamos las auxiliares -->
                        <c:if test="${not empty ofiDependiente.oficinasAuxiliares }">
                            <ul>
                                <c:forEach var="ofiAuxiliar" items="${ofiDependiente.oficinasAuxiliares}">
                                    <li><a href="javascript:void(0);"><span class="badge-arbre btn-ofaux" style="display:closed;"><i class="fa fa-home"></i> ${ofiAuxiliar.id} - ${ofiAuxiliar.nombre}</span></a>
                                        <!-- Pintamos las auxiliares de todos los niveles -->
                                        <c:set var="oficinaAuxiliar" value="${ofiAuxiliar}" scope="request"/>
                                        <jsp:include page="auxiliares.jsp" flush="true"/>
                                    </li>
                                </c:forEach>
                            </ul>
                        </c:if>
                    </li>
                </c:forEach>
                <!--Pintamos relaciones organizativas -->
                <c:forEach var="ofiFuncional" items="${hijo.oficinasFuncionales}">
                    <li><span class="badge-arbre btn-success"><i class="fa fa-institution"></i> ${ofiFuncional.id} - ${ofiFuncional.nombre}</span></li>
                </c:forEach>


                <c:if test="${not empty  hijo.hijos}">
                    <c:set var="nodo" value="${hijo}" scope="request"/>
                    <jsp:include page="nodo.jsp" flush="true"/>
                </c:if>
            </ul>
        </li>
    </c:forEach>

</c:if>