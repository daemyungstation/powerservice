<!DOCTYPE html>
<!--
 Copyright (c) 2013 Company Inwoo Tech Inc.

 @author 한숙향
 @version 1.0
 @date 2013/04/01
 @프로그램ID << AP-UI-0703 >>
 @설명 << 사용자 관리 >>
-->
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="ko">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>사용자관리</title>

<!-- common include -->
<%@ include file="/include/common-js.jsp" %>

<style type="text/css">
body{background:none}
</style>
</head>

<body>
<div class="bbs_con">

<div class="head_con">
<h1>버튼</h1>
</div>

<div class="content_outer">

<!-- 텍스트 버튼 -->
<button type="submit" data-bind="" class="k-button" title="검색"><span class="k-icon k-i-search"></span>검색</button>

<button type="reset" data-bind="" class="k-button" title="초기화"><span class="k-icon k-i-refresh"></span>초기화</button>

<button data-bind="" class="k-button" title="신규등록"><span class="k-icon k-i-plus"></span>신규등록</button>

<button data-bind="" class="k-button" title="추가"><span class="k-icon k-i-plus"></span>추가</button>

<button data-bind="" class="k-button" title="수정"><span class="k-icon k-i-pencil"></span>수정</button>

<button data-bind="" class="k-button" title="삭제"><span class="k-icon k-i-close"></span>삭제</button>

<button data-bind="" class="k-button" title="저장"><span class="iw-icon iw-i-save"></span>저장</button>

<button data-bind="" class="k-button" title="선택"><span class="k-icon k-i-tick"></span>선택</button>

<!-- <button data-bind="" class="k-button" title="적용"><span class="iw-icon iw-i-"></span>적용</button>  -->

<button data-bind="" class="k-button" title="뒤로"><span class="k-icon k-i-arrow-w"></span>뒤로</button>

<button data-bind="" class="k-button" title="앞으로"><span class="k-icon k-i-arrow-e"></span>앞으로</button>

<button data-bind="" class="k-button" title="취소">취소</button>

<button data-bind="" class="k-button" title="보기">보기</button>

<button data-bind="" class="k-button" title="미리보기">미리보기</button>
<!-- //텍스트 버튼 -->

<!-- 아이콘 버튼 -->
<button data-bind="" class="k-button" title="전화"><span class="iw-icon iw-i-phone"></span><span class="blind">전화</span></button>

<button data-bind="" class="k-button" title="핸드폰"><span class="iw-icon iw-i-hp"></span><span class="blind">핸드폰</span></button>

<button data-bind="" class="k-button" title="우편번호"><span class="iw-icon iw-i-zip"></span><span class="blind">우편번호</span></button>

<button data-bind="" class="k-button" title="검색"><span class="iw-icon iw-i-search"></span><span class="blind">검색</span></button>

<button data-bind="" class="k-button" title="스크립트"><span class="iw-icon iw-i-script"></span><span class="blind">스크립트</span></button>

<button data-bind="" class="k-button" title="FAQ"><span class="iw-icon iw-i-faq"></span><span class="blind">FAQ</span></button>

<button data-bind="" class="k-button" title="녹취"><span class="iw-icon iw-i-record"></span><span class="blind">녹취</span></button>

<button data-bind="" class="k-button" title="청취"><span class="iw-icon iw-i-listen"></span><span class="blind">청취</span></button>

<button data-bind="" class="k-button" title="펼치기"><span class="k-icon k-si-plus"></span><span class="blind">펼치기</span></button>

<button data-bind="" class="k-button" title="접기"><span class="k-icon k-si-minus"></span><span class="blind">접기</span></button>
<!-- //아이콘 버튼 -->

</div>
</div>
</body>
</html>
