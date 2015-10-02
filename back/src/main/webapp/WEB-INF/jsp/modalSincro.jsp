<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div id="modalSincro" class="modal fade bs-example-modal-lg" >
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h3><fmt:message key="dir3caib.sincronizando"/></h3>
            </div>
            <div class="modal-body" >
                <div class="col-xs-4 centrat" id="carga">
                    <img src="<c:url value="/img/712.GIF"/>" width="60" height="60"/>
                </div>
            </div>
        </div>
    </div>
</div>