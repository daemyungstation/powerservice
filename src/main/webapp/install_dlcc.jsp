<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%

//String sWinId = "828282";

String sUrl = request.getRequestURL().toString();
String sDomain = sUrl.substring(0, sUrl.indexOf("/", 8));
//String ServerUrl="http://192.168.10.61:8080/powerservice/";
String ServerUrl = sDomain + "powerservice/";


String sKey = "DLCC";

String sTitle = sKey + "Install Page";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<HTML>
<HEAD>
<TITLE>[대명스테이션] 콜센터시스템 프로그램 설치</TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<meta http-equiv="X-UA-Compatible" content="requiresActiveX=true">
<SCRIPT LANGUAGE="JavaScript">

var sWinId = "";

var sKey = "<%=sKey%>";
var Server_Path = "<%=sDomain%>";
var SvcUrl = "<%=ServerUrl%>";

Server_Path = Server_Path+"/powerservice";

var bOnError = false;
var XPLATFORM_CAB_VER = "2015,12,30,1";
var sessionid = "";

function fn_launchX()
{
    XLauncher.version     = "9.2.1";
    XLauncher.xadl = Server_Path +  "/xui/DLCC.xadl";
    XLauncher.onlyone = true;
    XLauncher.loadingimage = Server_Path+"/xui/install/img/loading_img.gif";		//데이터 로딩시 보이는 이미지
    XLauncher.splashimage = Server_Path+"/xui/install/img/lf-splash.jpg";			//XPlatform 실행시 보이는 이미지
    XLauncher.splashmessage = "none";
    XLauncher.componentpath="%USERAPP%\\TOBESOFT\\XPlatform\\9.2.1\\"+sKey;
    XLauncher.makeshortcut(sKey, Server_Path+"/xui/install/img/DLCC.ico", "DLCC");
    //XLauncher.globalvalue = "GV_SYSTEMDIV:g="+sWinId +" ,GV_SVCURL="+ SvcUrl;			//globalvariable에 추가될 변수값 설정

    XLauncher.download();
    XLauncher.launch();
    //fn_close();
}

function fn_objectOnError()
{
    bOnError = true;
}

function fn_load()
{
    if (navigator.userAgent.indexOf("MSIE") > -1 || navigator.userAgent.indexOf("Trident") !=-1) {
        if(bOnError == false){
            fn_launchX();
        }
    }
}

function fn_close()
{
    if(navigator.appVersion.indexOf("MSIE 7.") >= 0 || navigator.appVersion.indexOf("MSIE 8.") >= 0  ||
        navigator.appVersion.indexOf("MSIE 9.") >= 0 || navigator.appVersion.indexOf("MSIE 10.") >=0  ||
        navigator.appVersion.indexOf("Trident") >= 0  ) {
        window.open('about:blank','_self').close();
    } else {
        window.opener = self;
        self.close();
    }
}

function fn_ctmpError() {
    //alert("CTMP OCX LOAD 오류가 발생하였습니다.");
}

function fn_cmsError() {
    //alert("CMS OCX LOAD 오류가 발생하였습니다.");
}

function fn_recError() {
    //alert("REC OCX LOAD 오류가 발생하였습니다.");
}

function fn_ozError() {
    //alert("OZ OCX LOAD 오류가 발생하였습니다.");
}

function fn_genesysError() {
	//alert("Genesys OCX LOAD 오류가 발생하였습니다.");
}
</SCRIPT>
</HEAD>
<style>
    body{background-color:#e5e5e5;}
    #lnstall{ position:relative; width:786px; margin:0 auto;}
</style>
<BODY id="lnstall" onload="fn_load()">
<SCRIPT LANGUAGE="JavaScript">

// XPLATFORM Engine
document.write('<OBJECT ID="XPlatformAX" CLASSID="CLSID:7E0D6AB0-6E1E-441C-969A-CD85DCA58DFB" width="0" height="0" '
        + 'CodeBase="./xui/install/XPLATFORM9.2.1_SetupEngine.cab#VERSION='+XPLATFORM_CAB_VER+'" onError="fn_objectOnError()">'
        + '</OBJECT>');

// XPLATFORM Launcher
document.write('<OBJECT ID="XLauncher" CLASSID="CLSID:A30D5481-7381-4dd9-B0F4-0D1D37449E97" width="0" height="0" '
      + 'CodeBase="./xui/install/XPLATFORM9.2.1_XPLauncher.cab#VERSION='+XPLATFORM_CAB_VER+'" onError="fn_objectOnError()">'
        + '<PARAM NAME="key" VALUE="' + sKey + '">'
        + '</OBJECT>');

// CTMP OCX
// http://localhost:8000/powerservice/common/activex/CAPI3X.cab#version=4,5,9,21
document.write('<OBJECT ID="CAPI3X" CLASSID="CLSID:3598E614-E72D-4EDA-864C-E1547FDEE2CD" width="0" height="0" '
             + 'CodeBase="./common/activex/CAPI3X.cab#version=4,5,9,23" onError="fn_ctmpError()">'
             + '<PARAM NAME="key" VALUE="' + sKey + '">'
             + '</OBJECT>');

// http://localhost:8000/powerservice/common/activex/CAPI3X.cab#version=4,5,9,21
document.write('<OBJECT ID="CAPI3XEvt" CLASSID="CLSID:E51C1DAD-2F38-4AA2-B4DC-3836F31CA80D" width="0" height="0" '
             + 'CodeBase="./common/activex/CAPI3X.cab#version=4,5,9,23" onError="fn_ctmpError()">'
             + '<PARAM NAME="key" VALUE="' + sKey + '">'
             + '</OBJECT>');

// REC
//http://localhost:8000/powerservice/common/activex/VTMOCX.CAB#version=1,0,0,0
document.write('<OBJECT ID="VTMAPI" CLASSID="CLSID:C0A99929-F5A1-4549-8511-A42206826C0D" width="0" height="0" '
             + 'CodeBase="./common/activex/VTMOCX.CAB#version=1,0,0,0" onError="fn_recError()">'
             + '<PARAM NAME="key" VALUE="' + sKey + '">'
             + '</OBJECT>');

document.write('<OBJECT ID="VtmApiCtrl" CLASSID="CLSID:93A3D61B-C992-4A52-91BE-917A38BBEE6B" width="0" height="0" '
             + 'CodeBase="./common/activex/VtmActiveXCtrl.CAB#version=1,1,0,1" onError="fn_recError()">'
             + '<PARAM NAME="key" VALUE="' + sKey + '">'
             + '</OBJECT>');

// CMS
//http://www.cmsedi.or.kr/CMSCommX/CMSCommAX.cab#Version=1,0,0,6
document.write('<OBJECT ID="CMSCommAX" CLASSID="CLSID:60601393-834E-44DA-84BE-99E08C9734FE" width="0" height="0" '
             + 'CodeBase="./common/activex/CMSCommAX.cab#Version=1,0,0,7" onError="fn_cmsError()">'
             + '<PARAM NAME="key" VALUE="' + sKey + '">'
             + '</OBJECT>');

//OZ
document.write('<OBJECT ID="ZTransferX" CLASSID="CLSID:C7C7225A-9476-47AC-B0B0-FF3B79D55E67" width = "0" height = "0" '
             + 'CodeBase="http://192.168.10.61:18088/oz70/ozrviewer/ZTransferX_2,2,5,3.cab#version=2,2,5,3" onError="fn_ozError()">'
             + '<PARAM NAME="download.Server" VALUE="http://192.168.10.61/oz70/ozrviewer">'
             + '<PARAM NAME="download.Port" VALUE="18088">'
             + '<PARAM NAME="download.Instruction" VALUE="ozrviewer.idf">'
             + '<PARAM NAME="install.Base" VALUE="<PROGRAMS>/Forcs">'
             + '<PARAM NAME="install.Namespace" VALUE="' + sKey + '">'
             + '</OBJECT>');
             
//Genesys OCX
//http://localhost:8000/powerservice/common/activex/ARIGCSPx.cab#version=4,5,9,21
document.write('<OBJECT ID="ARIGCSP" CLASSID="CLSID:D0C23493-023C-4B45-939F-02F3D713C18F" width="0" height="0" '
          + 'CodeBase="./common/activex/ARIGCSPx.cab#version=1,0,1,5" onError="fn_genesysError()">'
//          + '<PARAM NAME="key" VALUE="' + sKey + '">'
          + '</OBJECT>');             

</SCRIPT>
<div style="margin-top:150px;">
    <img src="./xui/install/img/background_install.gif" width="786" height="440" />
</div>

DextEditor Plugin => <a href="./dexteditor/plugin/dext5Setup.exe">LINK</a><br>
OZViewer Plugin => <a href="./common/activex/SetupOZViewer.exe">LINK</a><br>
OfficeKeeper => <a href="http://officekeeper.daemyungimready.com/checkout3/install/install.php">LINK</a><br>
OfficeMessenger => <a href="http://download.officemessenger.co.kr/">LINK</a><br>
XPlatform Regedit => <a href="./common/regedit/XPlatformIE.zip">LINK</a>

</BODY>
</HTML>