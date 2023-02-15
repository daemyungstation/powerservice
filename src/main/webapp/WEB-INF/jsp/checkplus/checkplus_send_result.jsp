<%@ page language="java" contentType="text/html;charset=utf-8" %>
<html>
<head>
    <title>NICE신용평가정보 - 안심키 발급 SMS 발송결과</title>
<style type="text/css">
body {
	margin: 0px;
}

table {
	width: 100%;
	border-collapse: collapse;
}

td{	
	border: 1px solid #cccccc;
	padding: 4px;
}
</style>
<script language="javascript">
function initPage() {
	// window.resizeTo(500, 550);
}

function fn_close() {
	self.opener = self; 
	window.close();
}
</script>

</head>
<body onload="initPage();">
	
	<table align="center" cellspacing="0" border="0">
        <tr>
            <td style="border: 0px; text-align: center; width: 100%; padding-top: 10px;">안심키 SMS 발송 결과</td>
        </tr>
	</table>    
	
	
    <table align="center" cellspacing="0" style="width: 90%;">
        <!-- 
        <tr>
            <td>복호화한 시간</td>
            <td>${sendResult.sCipherTime} (YYMMDDHHMMSS)</td>
        </tr>
        -->
        <tr>
            <td>요청 번호</td>
            <td>${sendResult.sRequestNumber}</td>
        </tr>
        <!-- 
        <tr>
            <td>결과코드</td>
            <td>${sendResult.sReturnCode}</td>
        </tr>
        --> 
        <tr>
            <td>결과메세지</td>
            <td>${sendResult.sReturnMsg}</td>
        </tr>       

        <!-- 
        <tr>
            <td>RESERVED1</td>
            <td>${sendResult.sReserved1}</td>
        </tr>
        <tr>
            <td>RESERVED2</td>
            <td>${sendResult.sReserved2}</td>
        </tr>
        <tr>
            <td>RESERVED3</td>
            <td>${sendResult.sReserved3}</td>
        </tr>
         -->
    </table>        
    <!-- 
    ${sendResult.sMessage}<br>
     -->
     
<center>
	
	<table align="center" cellspacing="0" border="0">
        <tr>
            <td style="border: 0px; text-align: center; width: 100%;">
            	<input type="button" value="닫기" onclick="fn_close();"/>
            </td>
        </tr>
	</table>
    
</center>
</body>
</html>