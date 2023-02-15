<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!--
 Copyright (c) 2015 Company Inwoo Tech Inc.

 @author 유오성
 @version 1.0
 @date 2015/04/01
 @프로그램ID << login >>
 @설명 <<로그인>>
-->
<c:choose>
<c:when test="${pageContext.request.localName == '114.201.134.8'}">
    <c:set var="cntr_yn" value="N" />
</c:when>
<c:otherwise>
    <c:set var="cntr_yn" value="Y" />
</c:otherwise>
</c:choose>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<c:choose>
<c:when test="${cntr_yn == 'Y'}">
    <title>인우기술 컨택센터 상담프로그램 [${pageContext.request.localName}]</title>
</c:when>
<c:otherwise>
    <title>인우기술 업무포털 시스템 [${pageContext.request.localName}]</title>
</c:otherwise>
</c:choose>

<!-- common include -->
<%@ include file="/include/common-js.jsp" %>

<script type="text/javascript" src="${pageContext.request.contextPath}/script/cookie.js"></script>
<c:choose>
<c:when test="${cntr_yn == 'Y'}">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/theme/images/icon/favicon1.ico"/>
</c:when>
<c:otherwise>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/theme/images/icon/favicon.ico"/>
</c:otherwise>
</c:choose>
</head>

<body class="login">
<c:set var="login_lock_cnt">${empty BASIC['login_lock_cnt'] ? '5' : BASIC['login_lock_cnt'].basicvalue }</c:set>
<c:set var="cntr_cd">${empty param.cntr_cd ? 'CCA' : param.cntr_cd }</c:set>

<form:form name="loginForm" method="post" onsubmit="return false;">
<input type="hidden" id="cntr_cd" name="cntr_cd" value="${cntr_cd}" />
<input type="hidden" id="gds_cd" name="gds_cd" value="1" />
<input type="hidden" id="csrf_token" name="csrf_token" value="" />
<input type="hidden" id="delSessFg" name="delSessFg" value="N" />

<div id="login_wrap">
<div class="login_logo"><img src="${pageContext.request.contextPath}/theme/images/login-logo-inwoo.gif" height="60" /></div>


<c:choose>
<c:when test="${cntr_yn == 'Y'}">
    <ul class="login_copy">
    <li class="txt1">Customer Care Center</li>
    <li class="txt2">PS 시스템에 접속하셨습니다.<br>ID와 Password를 입력해 주세요!</li>
    </ul>
</c:when>
<c:otherwise>
    <ul class="login_copy">
    <li class="txt1">업무 포털 시스템</li>
    <li class="txt2">업무 시스템에 접속하셨습니다.<br>ID와 Password를 입력해 주세요!</li>
    </ul>
</c:otherwise>
</c:choose>

<ul class="login_input">
<li id="login-choice" class="btn1">
<c:choose>
<c:when test="${cntr_yn == 'Y'}">
<button type="button" class="bt_login_choice active" title="상담업무" value="1"><div class="log_img sec1"></div><div class="bt_tit">상담업무</div></button>
</c:when>
<c:otherwise>
<button type="button" class="bt_login_choice active" title="지원업무" value="1"><div class="log_img sec1"></div><div class="bt_tit">지원업무</div></button>
</c:otherwise>
</c:choose>
<button type="button" class="bt_login_choice" title="관리업무" value="2"><div class="log_img sec2"></div><div class="bt_tit">관리업무</div></button>
</li>
<li class="input_box">
    <input id="lgn_id" name="lgn_id" type="text" value="사용자 ID를 입력하세요." title="사용자 ID를 입력하세요." onfocus="if (this.value == this.title) this.value='';" onblur="if (this.value == '') this.value = this.title;" style="text-transform:uppercase">
</li>
<li class="input_box">
    <input type="password" id="pwd" name="pwd" onblur="changeText(this);" onkeypress="if (event.keyCode == 13) login()" style="display:none" autocomplete="off">
    <input type="text" name="pwd_temp" value="사용자 Password를 입력하세요." onfocus="changeText(this);">
</li>

<li class="cho">
<div class="txt1">
    <input id="loginidChk" name="loginidChk" type="checkbox" class="chk1"><label for="loginidChk">ID 저장</label>&nbsp;&nbsp;|&nbsp;&nbsp;<span class="pwcng" onclick="openPasswordChangePopup($('#lgn_id').val(), $('#cntr_cd').val());">비밀번호 변경</span>
</div>
<div class="txt2"><input type="checkbox" class="chk2">ID 저장&nbsp;&nbsp;|&nbsp;&nbsp;비밀번호 변경</div>
</li>
<li><button type="button" onclick="login()" class="bt_login" title="로그인">Log In</button></li>
</ul>

<ul id="login-footer" class="login_footer">
<li class="logo_img"><img src="${pageContext.request.contextPath}/theme/images/common/img_pslogo.png"/></li>
<li class="footer_txt">
Microsoft Internet Explore 9.0 이상 / 1280x1024 해상도에서 최적의 환경으로 보실 수 있습니다.
</li>
</ul>
</div>

</form:form>

<!-- Java script -->
<script type="text/javascript">

/**
 * Footer 하단 고정
 */
fixFooter = function() {
    var $Footer = $("#login-footer");
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
 * @author  : 유오성
 * @date    : 2015/04/01
 */
$(document).ready(function () {

    checkPlatform();
    fixFooter();

    // 업무선택
    var $LoginButtons = $("#login-choice").find("button");
    $LoginButtons.click(function() {
        var $Self = $(this);

        $Self.siblings().removeClass("active");
        $Self.addClass("active");

        $("#gds_cd").val($Self.attr("value"));
    });

    // 쿠키에 있는 아이디 셋팅
    var bCheck = getCookie("INWOO-LOGINID-CHK");
    if (bCheck) {
        $("#lgn_id").val(getCookie("INWOO-LOGINID"));
        $("#loginidChk").prop("checked", true);
    } else {
        $("#loginidChk").prop("checked", false);
    }

    // 마지막으로 로그인했던 업무(상담/관리) 선택
    var sAuthProduct = getCookie("INWOO-AUTH-PRODUCT");
    if (sAuthProduct == null || sAuthProduct == "") {
        sAuthProduct = "1";
    }
    $("#login-choice").find("button[value=" + sAuthProduct + "]").click();
});

/**
 * login
 * 로그인
 *
 * @param  : 없음
 * @return : 없음.
 * @author : 유오성
 * @date   : 2015/04/01
 */
login = function() {
    // Validation check
    var $LoginId = $("#lgn_id");
    var $LoginPw = $("#pwd");

    // 아이디 체크
    if ($LoginId.val() == "" || $LoginId.val() == $LoginId.attr("title")) {
        alert("아이디를 입력하세요.");
        $LoginId.focus();
        return;
    }

    // 비밀번호 체크
    if ($LoginPw.val() == "") {
        alert("비밀번호를 입력하세요.");
        $LoginPw.focus();
        return;
    }

    // 아이디 저장 쿠키처리
    var bCheck = $("#loginidChk").is(":checked");
    if (bCheck) {
        setCookie("INWOO-LOGINID-CHK", bCheck);
        setCookie("INWOO-LOGINID", $("#lgn_id").val());
    } else {
        delCookie("INWOO-LOGINID-CHK");
        delCookie("INWOO-LOGINID");
    }

    // 로그인한 업무(상담업무/관리업무) 쿠키저장
    setCookie("INWOO-AUTH-PRODUCT", $("#gds_cd").val());

    // 로그인
    var oParam = $("form[name=loginForm]").serialize();
    $.inwooAjax("${pageContext.request.contextPath}/login", oParam, callbackLogin);
};

function callbackLogin(poResult) {
    if (poResult.result == "OK") {
        // 귀찮아서 여기서 제품별로 호출함. 이러면 안되...고쳐야해ㅜ_ㅜ
          /**
            *변경일자 : 2015-11-11
            *변 경 자 : 정      훈
            *변경이유 : 제품코드에따른 업무 추가 화면분기
            *추후이슈 : 제품관리와의 연계후 하드코딩 추후 수정
            *요 청 자 : 우달식 이사님
            */
        if (document.loginForm.gds_cd.value == "1") {//상담업무
            window.open("${pageContext.request.contextPath}/main/usr", "main_popup", "toolbar=no, menubar=no, status=no, scrollbars=no, resizable=yes, width=1270px, height=960px");
        } else if(document.loginForm.gds_cd.value == "2"){//관리업무
            window.open("${pageContext.request.contextPath}/main/admin", "main_popup", "toolbar=no, menubar=no, status=no, scrollbars=no, resizable=yes, width=1270px, height=960px");
        } else if(document.loginForm.gds_cd.value == "3"){//2차상담
            window.open("${pageContext.request.contextPath}/main/n2cons", "main_popup", "toolbar=no, menubar=no, status=no, scrollbars=no, resizable=yes, width=1270px, height=960px");
        } else if(document.loginForm.gds_cd.value == "4"){//웹상담
            window.open("${pageContext.request.contextPath}/main/web", "main_popup", "toolbar=no, menubar=no, status=no, scrollbars=no, resizable=yes, width=1270px, height=960px");
        }

        // window.close();
    } else if (poResult.result == "UNKNOWN") {
        // 중목로그인 일때...
        if (confirm(poResult.errMsg)) {
            // 재로그인
            $("#delSessFg").val("Y");
            login();
            $("#delSessFg").val("N");
        }
    } else {
        alert(poResult.errMsg);
    }
}

function changeText(peTarget) {
    var eLoginForm = document.loginForm;
    if (eLoginForm.pwd.value == "") {
        if (peTarget == eLoginForm.pwd) {
            eLoginForm.pwd_temp.style.display = "inline";
            peTarget.style.display = "none";
        } else {
            peTarget.style.display = "none";
            eLoginForm.pwd.style.display = "inline";
            eLoginForm.pwd.focus();
        }
    }
}

function checkPlatform() {
    var pf = navigator.platform;
    var ua = navigator.userAgent;

    // 모바일 접속일 경우
    /*
    if(/iphone/i.test(pf) || /ipad/i.test(pf) || /linux armv7/i.test(pf)) {
        location.replace(inwoo.config.contextRootMobile);
    }
    */
}

</script>
</body>
</html>
