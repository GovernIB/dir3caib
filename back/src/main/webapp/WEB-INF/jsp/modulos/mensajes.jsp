<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${not empty errors}">
    <div class="box-content">
        <div class="alert alert-error fade in">
            <button type="button" class="close" data-dismiss="alert">&times;</button>
            <c:forEach var="error" items="${errors}">
                <strong>${error}</strong><br>
            </c:forEach>
            <c:remove var="errors" scope="session"/>
        </div>
    </div>
</c:if>

<c:if test="${not empty infos}">
    <div class="box-content">
        <div class="alert alert-success fade in">
            <button type="button" class="close" data-dismiss="alert">&times;</button>
            <c:forEach var="info" items="${infos}">
                <strong>${info}</strong><br>
            </c:forEach>
            <c:remove var="infos" scope="session"/>
        </div>
    </div>
</c:if>
