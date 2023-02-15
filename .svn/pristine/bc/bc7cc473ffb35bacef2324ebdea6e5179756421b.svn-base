<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="majorList">${USER_SESSION.productcd}${USER_SESSION.authoritycd}MAJORPROGRAM${USER_SESSION.centercd}</c:set>
<c:set var="middleList">${USER_SESSION.productcd}${USER_SESSION.authoritycd}MIDDLEPROGRAM${USER_SESSION.centercd}</c:set>
<c:set var="minorList">${USER_SESSION.productcd}${USER_SESSION.authoritycd}MINORPROGRAM${USER_SESSION.centercd}</c:set>
<style type="text/css">
    /* title bar */
    .titleDiv {background:url('${pageContext.request.contextPath}/theme/images/main/admin/ico_admin_title.gif') no-repeat;margin:0px;padding:10px 0 0 0;height:26px}
    .titleDiv .graySpan {padding-left: 10px;color: #504e4e;}
    .titleDiv .highrightSpan {color: #504e4e;font-weight: bold;}
    /* title bar */
</style>

<c:forEach var="major" items="${applicationScope[majorList] }">
    <c:forEach var="middle" items="${applicationScope[middleList][major.programcd] }">
        <c:if test="${middle.programcd eq param.programcd}">
            <c:set var="step" value="2" />
            <c:set var="majprogramnm" value="${major.programnm}" />
            <c:set var="midprogramnm" value="${middle.programnm}" />
            <c:set var="popupfg" value="${middle.popupfg}" />
            <c:set var="programnm" value="${middle.programnm}" />
        </c:if>
        <c:forEach var="minor" items="${applicationScope[minorList][middle.programcd] }">
            <c:if test="${minor.programcd eq param.programcd}">
                <c:set var="step" value="3" />
                <c:set var="majprogramnm" value="${major.programnm}" />
                <c:set var="midprogramnm" value="${middle.programnm}" />
                <c:set var="minprogramnm" value="${minor.programnm}" />
                <c:set var="popupfg" value="${minor.popupfg}" />
                <c:set var="programnm" value="${minor.programnm}" />
            </c:if>
        </c:forEach>
    </c:forEach>
</c:forEach>

<c:if test="${popupfg eq 'N'}">
<div id="titleDiv" class="titleDiv">
    <c:if test="${step eq '2'}">
        <span class="graySpan">${majprogramnm } > </span><span class="highrightSpan">${midprogramnm }</span>
    </c:if>
    <c:if test="${step eq '3'}">
        <span class="graySpan">${majprogramnm } > ${midprogramnm } > </span><span class="highrightSpan">${minprogramnm }</span>
    </c:if>
</div>
</c:if>

<c:if test="${popupfg eq 'Y'}">
<div id="poptitle">
<table class="tbl_pop_header">
    <tr>
        <td class="txt_pop_title">${programnm}</td>
        <td class="btnRight"><img src="${pageContext.request.contextPath}/theme/images/btn/btn_pop_close.gif" style="cursor:pointer" onclick="window.close();"/></td>
    </tr>
</table>
</div>
</c:if>
