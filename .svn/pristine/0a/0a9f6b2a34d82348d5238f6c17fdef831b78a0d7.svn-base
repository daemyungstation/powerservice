<%@page pageEncoding="UTF-8"%>
<!--
 Copyright (c) 2015 Company Inwoo Tech Inc.

 @author 한숙향
 @version 1.0
 @date 2015/04/01
 @프로그램ID default
 @설명 기본 에러페이지
-->
<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>인우기술 컨택센터 상담프로그램</title>

<!-- common include -->
<%@ include file="/include/common-js.jsp" %>
<!-- //common include -->

</head>

<body class="login">
<div id="login_wrap">
<div class="error_img"><img src="${pageContext.request.contextPath}/theme/images/common/img_error.png" width="440" height="200"  alt="error"/></div>

<ul class="error_txt">
<li class="txt1">SORRY,</li>
<li class="txt2">${exception.message}</li>
</ul>

<ul id="error-footer" class="login_footer">
<li class="logo_img"><img src="${pageContext.request.contextPath}/theme/images/common/img_pslogo.png"/></li>
<li class="footer_txt">
Microsoft Internet Explore 9.0 이상 / 1280x1024 해상도에서 최적의 환경으로 보실 수 있습니다.
</li>
</ul>

</div>

<!-- Java script -->
<script type="text/javascript">

/**
 * Footer 하단 고정
 */
fixFooter = function() {
    var $Footer = $("#error-footer");
    var nDocHeight = $(window).height();
    var nFooterHeight = $Footer.height();
    var nFooterTop = $Footer.position().top + nFooterHeight;

    if (nFooterTop < nDocHeight) {
        $Footer.css("margin-top", (nDocHeight - nFooterTop) + "px");
    }
};

$(window).resize(function () {
    fixFooter();
});

/**
 * $(document).ready
 * jquery ready 이벤트를 받아서 실행(onload보다는 조금 더 빨리 실행됨)
 *
 * @param   : 없음.
 * @return  : 없음.
 * @author  : 한숙향
 * @date    : 2015/04/01
 */
$(document).ready(function () {
    fixFooter();
});

</script>

</body>
</html>
