<!--
 Copyright (c) 2013 Company Inwoo Tech Inc.

 @author 한경식
 @version 1.0
 @date 2013/04/01
 @프로그램ID : 화면 레이아웃
 @설명 <<설명>>
-->
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>인우기술 콜센터 상담관리프로그램 [${pageContext.request.localName}]</title>
<link href="${pageContext.request.contextPath}/common/css/style.css" rel="stylesheet" type="text/css" />

</head>
<frameset rows="38,*" frameborder="no" border="0" framespacing="0">
    <frame src="${pageContext.request.contextPath}/include/top.do" name="topFrame" id="topFrame" scrolling="no"  title="탑메뉴"  noresize />
    <frameset rows="*,25">
        <frameset name="mainFrameset" id="mainFrameset" cols="200,*">
            <frame src="${pageContext.request.contextPath}/include/leftmenu.do" name="leftFrame" id="leftFrame" title="왼쪽메뉴" noresize scrolling="no"/>
            <frame src="${pageContext.request.contextPath}/monitoring/dashboard/dashboard_main.do?registerfg=Y&modifyfg=Y&deletefg=Y" name="mainFrame" id="mainFrame" title="메인컨텐츠" scrolling="no" noresize />
        </frameset>
        <frame src="${pageContext.request.contextPath}/include/bottom.do" name="bottomFrame" id="bottomFrame" title="스크롤메세지바" noresize scrolling="no"/>
    </frameset>
</frameset>
<noframes>
<body>
<div id="ly_help" class="ly_help" style="width:100px; cursor: pointer;" onclick="message_popup();">
    <strong>쪽지알림</strong>
    <p>안읽은 쪽지 : <span id="msgnoticnt">0</span>개</p>
    <div class="edge"></div>
</div>
</body>
</noframes>
</html>
<!--                                                                        -->
<!-- Java script                                                            -->
<!--                                                                        -->
<script type="text/javascript">
var leftMode = "on";

function getLeftMode() {
    return leftMode;
}
</script>