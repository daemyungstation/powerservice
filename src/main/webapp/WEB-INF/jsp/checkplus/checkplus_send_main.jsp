<%@ page language="java" contentType="text/html;charset=euc-kr" %>

<html>
<head>
<title>NICE�ſ������� - CheckPlus �Ƚɺ������� �׽�Ʈ</title>

<script language='javascript'>
window.name ="Parent_window";

function fnPopup(){
	
	/*
	window.open('', 'popupChk', 'width=500, height=550, top=100, left=100, fullscreen=no, menubar=no, status=no, toolbar=no, titlebar=yes, location=no, scrollbar=no');
	document.form_chk.action = "https://nice.checkplus.co.kr/CheckPlusSafekeyModel/checkplus.cb";
	document.form_chk.target = "popupChk";
	document.form_chk.submit();
	*/
	
	/*
	alert("param_r1 : " + document.form_chk.param_r1.value);
	alert("param_r2 : " + document.form_chk.param_r2.value);
	alert("param_r3 : " + document.form_chk.param_r3.value);
	*/
	
	window.resizeTo(500, 300);
	document.form_chk.action = "https://nice.checkplus.co.kr/CheckPlusSafekeyModel/checkplus.cb";
	document.form_chk.submit();
}

function fn_initPage() {
	fnPopup();
}

</script>
</head>
<body onload="fn_initPage();">
<!-- 
��ü���� ROW ����Ÿ : ${sPlainData}<br>
�α���ID : ${lgnId}<br>
<br>
-->

<!-- �Ƚ�Ű ���� �˾��� ȣ���ϱ� ���ؼ��� ������ ���� form�� �ʿ��մϴ�. -->
<form name="form_chk" method="post">
<input type="hidden" name="m" value="safekeySendService">				<!-- �ʼ� ����Ÿ��, �����Ͻø� �ȵ˴ϴ�. -->
<input type="hidden" name="EncodeData" value="${sEncData}">				<!-- ������ ��ü������ ��ȣȭ �� ����Ÿ�Դϴ�. -->

<!-- ��ü���� ����ޱ� ���ϴ� ����Ÿ�� �����ϱ� ���� ����� �� ������, ������� ����� �ش� ���� �״�� �۽��մϴ�.
	    	 �ش� �Ķ���ʹ� �߰��Ͻ� �� �����ϴ�. -->
<input type="hidden" name="param_r1" value="${sReqDatetime}">
<input type="hidden" name="param_r2" value="${lgnId}">
<input type="hidden" name="param_r3" value="${mem_no}">

<!-- 
<a href="javascript:fnPopup();"> �Ƚ�Ű���� ����</a>
-->
</form>
</body>
</html>
