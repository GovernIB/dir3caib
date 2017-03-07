<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/modulos/includes.jsp" %>

<!DOCTYPE html>
<html lang="ca">
<head>
    <c:import url="modulos/imports.jsp"/>
</head>

<body>

<c:import url="modulos/menu.jsp"/>
<div class="row-fluid container main">
    <div class="well well-dir3caib">
        <div class="row-fluid">
            <div class="box span12">
                <div class="box-header well">
                    <h2><fmt:message key="unidad.organigrama"/>&nbsp;<i> ${nodo.codigo} - ${nodo.denominacion}</i></h2>
                </div>

                    <!-- LEYENDA -->
                    <div class="box llegenda">
                        <div class="box-header well">
                            <div class="col-xs-12">
                                <i class="fa fa-comment-o"></i> <strong><spring:message code="dir3caib.leyenda"/></strong>
                            </div>
                        </div>

                        <div class="box-content">
                            <div class="col-xs-6 pad-bottom5">
                                <span class="badge-arbre btn-primary"><i class="fa fa-globe"></i> <spring:message code="dir3caib.unidadOrganica"/></span>
                            </div>

                            <div class="col-xs-6 pad-bottom5">
                                <span class="badge-arbre btn-warning"><i class="fa fa-home"></i> <spring:message code="dir3caib.oficina.principal"/></span>
                            </div>

                            <div class="col-xs-6 pad-bottom5">
                                <span class="badge-arbre btn-ofaux"><i class="fa fa-home"></i> <spring:message code="dir3caib.oficina.auxiliar"/></span>
                            </div>

                            <div class="col-xs-6 pad-bottom5">
                                <span class="badge-arbre btn-success"><i class="fa fa-institution"></i> <spring:message
                                        code="dir3caib.oficina.organizativa"/></span>
                            </div>
                        </div>
                    </div>

                    <div class="tree">
                        <c:set var="nodoactual" value="${nodo.codigo} - ${nodo.denominacion}"/>
                        <c:set var="nodoraiz" value="${nodo.raiz}"/>
                        <c:set var="nodosuperior" value="${nodo.superior}"/>
                        <c:if test="${nodoraiz!=nodoactual}">
                        <ul>
                            <li>
                                <span class="badge-arbre btn-primary"><i class=""></i> ${nodo.raiz}</span>
                                </c:if>
                                <c:if test="${nodoraiz!=nodosuperior}">
                                <ul>
                                    <li>
                                        <span class="badge-arbre btn-primary"><i class=""></i> ${nodo.superior}</span>
                                        </c:if>
                                        <ul>

                            <li>
                                <c:if test="${empty oficinas}">
                                    <span class="badge-arbre btn-primary"><i
                                            class=""></i> ${nodo.codigo} - ${nodo.denominacion}</span>
                                </c:if>
                                <c:if test="${not empty oficinas}">
                                    <span class="badge-arbre btn-warning"><i
                                            class=""></i> ${nodo.codigo} - ${nodo.denominacion}</span>
                                </c:if>

                                <!--Pintamos oficinas responsables -->
                                <ul>
                                    <c:forEach var="ofiDependiente" items="${nodo.oficinasDependientes}">
                                        <li><a href="javascript:void(0);"><span class="badge-arbre btn-warning"
                                                                                style="display:closed;"><i
                                                class="fa fa-home"></i> ${ofiDependiente.codigo} - ${ofiDependiente.denominacion}</span></a>
                                            <!-- Pintamos las auxiliares -->
                                            <c:if test="${not empty ofiDependiente.oficinasAuxiliares }">
                                                <ul>
                                                    <c:forEach var="ofiAuxiliar" items="${ofiDependiente.oficinasAuxiliares}">
                                                        <%-- <li><a href="javascript:void(0);"><span class="badge-arbre btn-ofaux" style="display:closed;"> <i class="fa fa-home"></i> ${ofiAuxiliar.codigo} - ${ofiAuxiliar.denominacion}</span></a></li>--%>
                                                        <li><a href="javascript:void(0);"><span
                                                                class="badge-arbre btn-ofaux" style="display:closed;"><i
                                                                class="fa fa-home"></i> ${ofiAuxiliar.codigo} - ${ofiAuxiliar.denominacion}</span></a>
                                                            <!-- Pintamos las auxiliares de todos los niveles -->
                                                            <c:set var="oficinaAuxiliar" value="${ofiAuxiliar}" scope="request"/>
                                                            <jsp:include page="auxiliares.jsp" flush="true"/>
                                                        </li>
                                                    </c:forEach>
                                                </ul>
                                            </c:if>
                                        </li>
                                    </c:forEach>

                                    <!-- ORGANIZATIVAS -->

                                    <c:forEach var="ofiOrganizativa" items="${nodo.oficinasOrganizativas}">
                                        <li><a href="javascript:void(0);"><span class="badge-arbre btn-success"
                                                                                style="display:closed;"><i
                                                class="fa fa-institution"></i> ${ofiOrganizativa.codigo} - ${ofiOrganizativa.denominacion}</span></a>
                                        </li>
                                    </c:forEach>
                                    <jsp:include page="nodo.jsp"/>
                                </ul>
                            </li>
                        </ul>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                    </div>
            </div>
        </div>
    </div>
</div>

<c:import url="modulos/pie.jsp"/>

<script type="text/javascript">
    $(function () {
        $('.tree li:has(ul > li)').addClass('parent_li').find(' > span').attr('title', 'Amaga la branca');
        $('.tree li:has(ul > li)').addClass('parent_li').find(' > span').addClass('fa fa-minus');
        $('.tree li.parent_li > span').on('click', function (e) {
            var children = $(this).parent('li.parent_li').find(' > ul > li');
            if (children.is(":visible")) {
                children.hide('fast');
                $(this).removeClass('fa fa-minus');
                $(this).attr('title', 'Mostra la branca').addClass('fa fa-plus');
            } else {
                children.show('fast');
                $(this).removeClass('fa fa-plus');
                $(this).attr('title', 'Amaga la branca').addClass('fa fa-minus');
            }
            e.stopPropagation();
        });
    });
</script>

</body>
</html>























































