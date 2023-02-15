<%@page pageEncoding="UTF-8"%>

<!DOCTYPE html>
<!--
 Copyright (c) 2013 Company Inwoo Tech Inc.

 @author 배윤정
 @version 1.0
 @date 2013/07/09
 @프로그램ID << AP-UI-0607 >>
 @설명 << 상담자료관리 - 게시판관리 >>
-->
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang3.StringUtils;" %><%
    String identifier = StringUtils.defaultString((String)request.getParameter("identifier"));
    String height = StringUtils.defaultString((String)request.getParameter("height"));
    String width = StringUtils.defaultString((String)request.getParameter("widht"));
    String contents = java.net.URLDecoder.decode(StringUtils.defaultString((String)request.getParameter("contents")));
%>
<html lang="ko">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <title>DEXT5 Editor Sample Write</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/dexteditor/js/dext5editor.js"></script>
</head>
<body>
    <div id="divEditor" style="width:100%;height:100%;">
    <textarea name="<%=identifier %>" id="<%=identifier %>" style="display:none;"></textarea>
<!--에디터 영역-->
    <script type="text/javascript">
    var dextcnt = 0;
        //DEXT5.config.Width = "";
        //DEXT5.config.SkinName = "";
        //DEXT5.config.InitXml = "";
        // ex)  DEXT5.config.InitXml = "dext_editor_mobile.xml"; //config 폴더 안의 파일 이름만 지정
        DEXT5.config.IgnoreSameEditorName = '1';
        new Dext5editor("Dexteditor");
    </script>
<!--에디터 영역-->
<script type="text/javascript">
    function dext_editor_loaded_event() {
        var width = '<%=width%>' == '' ? $("#divEditor").width():  '<%=width%>';
        var height = '<%=height%>' == '' ? $("#divEditor").height() :  '<%=height%>';

        DEXT5.setSize(width, height);

        var contents = '<%=contents%>';
        if ( contents != "")
            DEXT5.setBodyValue(contents);
    }

    function getEditorContents() {
        var sBodyValue = "";
        if (DEXT5.isEmpty() == true) {
            return;
        } else {
            sBodyValue = DEXT5.getBodyValue('Dexteditor').gf_replaceAll("\r\n", "");
        }

        document.all.<%=identifier%>.value = sBodyValue;
    }
</script>
</div>
</body>
</html>
