<%@ page language="java" contentType="text/html;charset=euc-kr" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
    <title>NICE신용평가정보 - 안심키 인증 결과</title>

<script language="javascript">
function fn_close() {
	window.open("about;blank", "_self");
	opener = window;
	window.close();
}
</script>
</head>

<body margin="0">
	<div id="div_title" style="width: 99%; margin: padding: 10px; 0px; text-align: center; background-color:#3366ff; color:#fff;">
    	<strong>안심키 인증 결과</strong>
    </div>
    
    <div id="div_main" style="width: 99%; margin: 10px; text-align: left;">

<c:choose>
    <c:when test="${result.success_yn eq 'Y'}">
        <c:out value="${result.return_msg}"/>  
    </c:when>  
    <c:when test="${result.success_yn eq 'N'}">
        <c:out value="${result.error_msg}"/>
    </c:when>  
    <c:otherwise>  
        <c:out value="${result.return_msg}"/>  
    </c:otherwise>
</c:choose>

    </div>
    
	<center>
	<button type="button" name="btn_close" onclick="fn_close();">확인</button>
	</center>
</body>
</html>