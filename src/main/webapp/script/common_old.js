/**
 * @(#)common.js
 *
 * Copyright (c) 2011 by Inwoo tech inc, KOREA. All Rights Reserved.
 * http://www.inwoo.co.kr
 *
 * NOTICE! This software is the confidential and proprietary information of
 * Inwoo Tech Inc. You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement you
 * entered into with Inwoo Tech Inc.
 *
 * PowerDesk JavaScript
 *
 * Copyright 	Copyright (c) 2005
 * Company   	Inwoo Tech Inc.
 *
 * @author
 * @version		1.0
 * @date		2005/09/01
 */

setCheckBox = function( objName, state)
{
    if( objName == null) //??? ???
    {
        return;
    }
    else if( objName.length == null)
    {
        objName.checked = state;
    }
    else
    {
        for ( c=0; c<objName.length; c++)
        {
            if (objName[c].style.display != "none") {
                objName[c].checked = state;
            }
        }
    }
}

checkRequired = function( formName, fieldName, fieldAlias)
{
    if( fieldName.length != fieldAlias.length)
    {
        return false;
    }



    return true;
}

launchCenter = function(url, name, height, width)
{
    var str = "height=" + height + ", innerHeight=" + height;

    str += ", width=" + width + ", innerWidth=" + width;

    if (window.screen)
    {
        var ah = screen.availHeight - 30;
        var aw = screen.availWidth - 10;
        var xc = (aw - width) / 2;
        var yc = (ah - height) / 2;

        str += ", left=" + xc + ", screenX=" + xc;
        str += ", top=" + yc + ", screenY=" + yc;
        str += "toolbar=no,menubar=no,titlebar=no";
    }

    return window.open(url, name, str);
}

launchCenterWithScrollBar = function(url, name, height, width)
{
    var str = "scrollbars=yes, height=" + height + ", innerHeight=" + height;

    str += ", width=" + width + ", innerWidth=" + width;

    if (window.screen)
    {
        var ah = screen.availHeight - 30;
        var aw = screen.availWidth - 10;
        var xc = (aw - width) / 2;
        var yc = (ah - height) / 2;

        str += ", left=" + xc + ", screenX=" + xc;
        str += ", top=" + yc + ", screenY=" + yc;
        str += "toobar=no,menubar=no,title=no";
    }

    return window.open(url, name, str);
}

launchSelfConfig = function(url, name, str)
{
    return window.open(url, name, str);
}

launchCenterModal = function(url, name, height, width)
{
    var str = "dialogWidth:" + width + "px;dialogHeight:" + height+"px;";

    str += " center:yes; help:no; status:no; scroll:no; resizable:no";

    return window.showModalDialog('./jsp/inwoo/common/LoadingPage.jsp', name, str);

}

launchCenterModalSelfConfig = function(url, name, str) {
    return window.showModalDialog(url, name, str);
}

isChecked = function( obj)
{
    if( obj == null)
    {
        return false;
    }
    else if( obj.length == null && obj.checked == false)
    {
        return false;
    }
    else if( obj.length != null)
    {
        var nCount = 0;

        for( i=0; i<obj.length; i++)
        {
            if( obj[i].checked == true)
            {
                nCount++;
            }
        }

        if( nCount == 0)
        {
            return false;
        }
    }

    return true;
}

/**
 *
 * ex)
 * var szUrl = "/powerdeskbss/service.do?process=popContractForm";
 * var szTarget = "popWin";
 * var szOption = "dialogWidth:740px;dialogHeight:540px; status:no; scroll:yes; resizable:yes";
 *
 */
 popPage = function( szUrl, szTarget, szOption)
{
    var objModal = null

    objModal = window.showModalDialog(szUrl, szTarget, szOption);

    return objModal;
}

popUser = function()
{
    launchCenter('/powerdeskbss/user.do?process=popUserSelect&option=oneSelect', 'center', 420, 830);
}

setCurrentObj = function(objId, objName)
{
    currentObjId 	= objId;
    currentObjName	= objName;
}

setUserInfo = function( szUserInfo)
{
    var arrUserInfo = szUserInfo.split('|');

    currentObjId.value 		= arrUserInfo[1];
    currentObjName.value	= arrUserInfo[0];
}

isNotNull = function( form, item)
{
    return ! isNull( form, item);
}

isNull = function( form, item)
{
    var form_item = eval( "document."+ form + "." + item);
    return ( form_item.value == '');
}

isNull = function(item) {
    var form_item = eval( "document." + item);
    return ( form_item.value == '');
}

isNullChkFocus = function(item, msg) {
    var form_item = eval( "document." + item);

    if(isNull(item)) {
        alert(msg);
        form_item.focus();
        return false;
    } else
        return true;
}

setFocus = function()
{
    var args = setFocus.arguments;
    var form_item;

    if( args.length == 2)
    {
        form_item = eval( 'document.' + args[0] + '.' + args[1] );
    }
    else if ( args.length == 3)
    {
        form_item = eval( 'document.'+ args[2] + '.' + args[0] + '.' + args[1] );
    }

    form_item.focus();
}

getComboBoxValue = function( form, item)
{
    var form_item = eval( 'document.' + form + '.' + item);
    var selectedIndex = form_item.selectedIndex;

    return form_item.options[ selectedIndex].value;
}

getComboBoxName = function( form, item)
{
    var form_item = eval( 'document.' + form + '.' + item);
    var selectedIndex = form_item.selectedIndex;

    return form_item.options[ selectedIndex].text;
}

na_restore_img_src = function(name, nsdoc)
{
  var img = eval((navigator.appName.indexOf('Netscape', 0) != -1) ? nsdoc+'.'+name : 'document.all.'+name);
  if (name == '')
    return;
  if (img && img.altsrc) {
    img.src    = img.altsrc;
    img.altsrc = null;
  }
}

na_preload_img = function()
{
  var img_list = na_preload_img.arguments;
  if (document.preloadlist == null)
    document.preloadlist = new Array();
  var top = document.preloadlist.length;
  for (var i=0; i < img_list.length-1; i++) {
    document.preloadlist[top+i] = new Image;
    document.preloadlist[top+i].src = img_list[i+1];
  }
}

na_change_img_src = function(name, nsdoc, rpath, preload)
{
  var img = eval((navigator.appName.indexOf('Netscape', 0) != -1) ? nsdoc+'.'+name : 'document.all.'+name);
  if (name == '')
    return;
  if (img) {
    img.altsrc = img.src;
    img.src    = rpath;
  }
}


// 목록보기에서 선택해서 삭제할 경우
confirmDelete = function( objCheck)
{
    var tempFlag = false;

    if( isChecked( objCheck) == false )
    {
        alert( ' 선택된 데이터가 없습니다.\r\n 삭제할 데이터를 선택해 주십시오.');
        return false;
    }
    else
    {
        tempFlag = showModalDialog(
                    '/powerdeskbss/jsp/inwoo/common/confirmDeleteWindow.jsp',
                    tempFlag,
                    "status:no;dialogWidth:298px;dialogHeight:152px;center:yes;help:no;resizable:no");
    }

    return tempFlag;
}

//상세보기화면에서 삭제할 경우
confirmDeleteOnView = function()
{
    var tempFlag = false;

    tempFlag = showModalDialog(
                '/powerdeskbss/jsp/inwoo/common/confirmDeleteWindow.jsp',
                tempFlag,
                "status:no;dialogWidth:298px;dialogHeight:152px;center:yes;help:no;resizable:no");
    return tempFlag;
}

doClose = function() {
    self.close();
}

// input 에 날짜형식인 000-00-00 으로 넣어줌.
addDash = function(dateCtl){
    if(dateCtl.value.length==4 || dateCtl.value.length==7){
        dateCtl.value +="-";
    }
}

onkeylengthMax = function(formobj, maxlength) {
    var li_byte     = 0;
    var li_len      = 0;

    for (var i = 0; i < formobj.value.length; i++) {
        if (escape(formobj.value.charAt(i)).length > 4) {
            li_byte += 3;
        } else {
            li_byte++;
        }

        if (li_byte <= maxlength) {
            li_len = i + 1;
        }
    }

    if (li_byte > maxlength) {
        alert("최대 (영문:" + maxlength + ", 한글:" + Math.floor(maxlength/3) + ")자를 넘을수 없습니다.");
        formobj.value = formobj.value.substr(0, li_len);
    }
    formobj.focus();
}

checkValue = function(formobj,maxlength) {
    gf_onlyNumeric();
    onkeylengthMax(formobj,maxlength);
}

/*************************************************************************
 *  String.trim()
 *  내용의 좌 우측 공백을 제거해 주는 메소드
 *************************************************************************
 * History  : 최초 작성
 * Desc     : 내용의 좌 우측 공백을 제거해 주는 메소드
 * Param    : none
 * Return   : trim()된 문자열
 *************************************************************************/
 /**
 * 빈공백(캐리지리턴, 탭등을 없애기 위한 trim정규식 패턴
 */
var TRIM_PATTERN = /(^\s*)|(\s*$)/g; // 내용의 값을 빈공백을 trim하기 위한것(앞/뒤)
String.prototype.trim = function() {
    return this.replace(TRIM_PATTERN, "");
}

// ============================================================================
// === 함수를 사용하실 경우 구분선 아래의 함수를 사용하시고 위의 함수를 사용하고자 하실경우에는 ===
// === 위의 함수명 앞에 gf_ 를 붙여서 사용하시기 바랍니다.===============================
// =======================        구   분   선                    ===========================
// ============================================================================


// ============================================================================
// Funciotn Name : gf_onlyNumeric()
// @ param :
// @ return :
// @ 설명 :
// @ 작성일 :
// @ 작성자 :
// @ 수정자(1) :
// @ 수정내용(1) :
// ============================================================================
gf_onlyNumeric = function() {
    if (event.keyCode == 13) {
        return;
    }

    if (event.keyCode != 13 && event.keyCode != 22 && (event.keyCode < 48 || event.keyCode > 57)) {
        event.returnValue = false;
        alert("숫자만 입력 가능합니다.");
    }
}

gf_onlyIp = function() {
    if (event.keyCode == 13) {
        return;
    }

    if (event.keyCode != 13 && event.keyCode != 46 && event.keyCode < 48 || event.keyCode > 57) {
        event.returnValue = false;
        alert("아이피 형식에 맞지 않습니다.");
    }
}

// ============================================================================
// Funciotn Name : gf_onlyNumeric2()
// @ param : obj - 체크할 폼의 input name(필수입력) / minSize : 입력가능한 최저값(선택적 입력) / maxSize : 입력가능한 최대값(선택적 입력)
// @ return :
// @ 설명 : 숫자를 체크하면서 tab, caps Lock, shift, ctrl 등의 제어키는 제외함.(예 : gf_onlyNumeric2('scheduleForm.reservationdt3', '00', '59');)
// @ 작성일 : 2007/12/08
// @ 작성자 : 진영민
// @ 수정자(1) :
// @ 수정내용(1) :
// ============================================================================
gf_onlyNumeric2 = function(obj, minSize, maxSize) {
    var obj = eval(obj);
    var temNum = "";
    keyArray = new Array(8, 9, 13, 16, 17, 18, 20, 21, 25, 229);
    var chk = true;
    for(i = 0; i < keyArray.length; i++) {
        if(keyArray[i] == event.keyCode) {
            chk = false;
            break;
        }
    }

    if(chk) {
        // 숫자인지 아닌지 체크
        for(i = 0; i < obj.value.length; i++) {
            if(obj.value.charAt(i) >= 0 && obj.value.charAt(i) <= 9) {
                temNum += obj.value.charAt(i);
            }
            else {
                obj.value = temNum;
                alert("숫자만 입력 가능합니다.");
                break;
            }
        }

        //제한값 여부에 따라 경고 메시지 출력
        if(minSize != "") {
            if(parseInt(temNum) < parseInt(minSize)) {
                alert(minSize + " 이상으로 입력 하세요.");
                obj.value = temNum.substring(0, 1);
            }
        }
        if(maxSize != "") {
            if(parseInt(temNum) > parseInt(maxSize)) {
                alert(maxSize + " 이하로 입력 하세요.");
                obj.value = temNum.substring(0, 1);
            }
        }
        if(minSize == "" && maxSize == "") {
            obj.value = temNum;
        }
        return;
    }
}


// ============================================================================
// Funciotn Name : checkValue(formobj,maxlength)
// @ param : formobj,maxlength
// @ return :
// @ 설명 :
// @ 작성일 :
// @ 작성자 :
// @ 수정자(1) :
// @ 수정내용(1) :
// ============================================================================
gf_checkValue = function(formobj,maxlength) {
    gf_onlyNumeric();
    gf_onkeylengthMax(formobj,maxlength);
}

// ============================================================================
// Funciotn Name : gf_isChecked(obj)
// @ param : obj(Object)
// @ return : boolean
// @ 설명 : 체크박스에 체크여부 확인
// @ 작성일 :
// @ 작성자 :
// @ 수정자(1) :
// @ 수정내용(1) :
// ============================================================================
gf_isChecked = function(obj)
{
    // 객체가 없는 경우
    if( obj == null){
        return false;
    }
    //
    else if( obj.length == null && obj.checked == false){
        return false;
    }
    //
    else if( obj.length != null){
        var nCount = 0;

        for( i=0; i<obj.length; i++)
        {
            if( obj[i].checked == true)
            {
                nCount++;
            }
        }

        if( nCount == 0)
        {
            return false;
        }
    }
    return true;
}

// ============================================================================
// Funciotn Name : gf_setCheckBox( objName, state)
// @ param : objName(String), state(boolean)
// @ return :
// @ 설명 :
// @ 작성일 :
// @ 작성자 :
// @ 수정자(1) :
// @ 수정내용(1) :
// ============================================================================
gf_setCheckBox = function(objName, state)
{
    if( objName == null) //
    {
        return;
    }
    else if( objName.length == null)
    {
        objName.checked = state;
    }
    else
    {
        for ( c=0; c<objName.length; c++)
        {
            objName[c].checked = state;
        }
    }
}


// ============================================================================
// Funciotn Name : gf_calendar(sChk)
// @ param : sChk
// @ return :
// @ 설명 : 선택된 달력의 일자를 해당 텍스트박스에 삽입한다.
// @ 작성일 :
// @ 작성자 :
// @ 수정자(1) :
// @ 수정내용(1) :
// ============================================================================
gf_calendar = function(sChk) {

    sChkVal = eval(sChk).value;
    var result = "";
    var firstList = new Array();
    //var rtYmdt = "";

    result = window.showModalDialog( '/powerservice/include/calendar.jsp', sChkVal, 'status:no;center:yes;scroll:no;help:no;minimize:no;maximize:no;border:thin;statusbar:no;dialogWidth:174px;dialogHeight:218px;dialogTop:'+window.event.screenY+'; dialogLeft:'+window.event.screenX);

  if (result == -1 || result == null || result == "")   return "";
    firstList = result.split(";");
    eval(sChk).value = gf_getYMDFormat(firstList[0]);
    eval(sChk).focus();
}


// ============================================================================
// Funciotn Name : gf_removeDelim(str, delim)
// @ param : str, delim
// @ return : 문자열에 delim을 모두 삭제한다.
// @ 설명 : 문자열에 delim을 모두 삭제한다.
// @ 작성일 :
// @ 작성자 :
// @ 수정자(1) :
// @ 수정내용(1) :
// ============================================================================
gf_removeDelim = function(str, delim) {
    var tmpvalue = eval(str).value;
    var tmp = "";
    for (var i=0; i<tmpvalue.length; i++) {
        if (tmpvalue.charAt(i) != delim) {
            tmp = tmp + tmpvalue.charAt(i);
        }
    }
    return tmp;
}

// ============================================================================
// Funciotn Name : gf_getYMDFormat(yyyymmdd)
// @ param : yyyymmdd
// @ return : 년 월 일이 붙어있는 문자열을 하이픈을 붙여 리턴함
// @ 설명 : 년 월 일이 붙어있는 문자열을 하이픈을 붙여 리턴함
// @ 작성일 :
// @ 작성자 :
// @ 수정자(1) :
// @ 수정내용(1) :
// ============================================================================
gf_getYMDFormat = function(yyyymmdd){
    var gubun = "/";
    yyyymmdd = removeNoneNum(yyyymmdd);

    if (yyyymmdd.length != 8) return yyyymmdd;
    var SdateY = eval( yyyymmdd.substring(0,4) );
    var SdateM = eval( yyyymmdd.substring(4,6) );
    var SdateD = eval( yyyymmdd.substring(6,8) );

    var now = new Date(SdateY, Number(SdateM)-1, SdateD);

    var yr = now.getYear();
    var mName = now.getMonth() + 1;
    var dName = now.getDate();

    if (yr < 100) year=("19"+yr).toString();
    else year=yr.toString();

    if (mName <10) month=("0"+mName).toString();
    else month=mName.toString();

    if (dName <10) day=("0"+dName).toString();
    else day=dName.toString();

    return year + gubun + month + gubun + day;
}

// ============================================================================
// Funciotn Name : gf_getYMDNoFormat(date)
// @ param : yyyy-mm-dd
// @ return : 년 월 일이 하이픈 있는 경우 하이픈을 삭제하여 리턴함
// @ 설명 : 년 월 일이 하이픈 있는 경우 하이픈을 삭제하여 리턴함
// @ 작성일 :
// @ 작성자 :
// @ 수정자(1) :
// @ 수정내용(1) :
// ============================================================================
gf_getYMDNoFormat = function(date){
    var term = "/";
    var strDate = "";

    for(i=0 ; i<date.length ; i++){
        if(term == date.substring(i,i+1)){
            strDate += " ";
        }
        else{
            strDate += date.substring(i,i+1);
        }
    }
    var rtn = strDate.replace(/ /g,"");
    return rtn;
}


// ============================================================================
// Funciotn Name : gf_getHMSFormat(hhmmss)
// @ param : yyyymmdd
// @ return : 년 월 일이 붙어있는 문자열을 하이픈을 붙여 리턴함
// @ 설명 : 년 월 일이 붙어있는 문자열을 하이픈을 붙여 리턴함
// @ 작성일 :
// @ 작성자 :
// @ 수정자(1) :
// @ 수정내용(1) :
// ============================================================================
gf_getHMSFormat = function(hhmmss){
    var gubun = ":";
    if (hhmmss.length != 6) return "";
    var SdateH = hhmmss.substring(0,2);
    var SdateM = hhmmss.substring(2,4);
    var SdateS = hhmmss.substring(4,6);

    return SdateH + gubun + SdateM + gubun + SdateS;
}

// ============================================================================
// Funciotn Name : gf_getHMSNoFormat(date)
// @ param : yyyy-mm-dd
// @ return : 년 월 일이 하이픈 있는 경우 하이픈을 삭제하여 리턴함
// @ 설명 : 년 월 일이 하이픈 있는 경우 하이픈을 삭제하여 리턴함
// @ 작성일 :
// @ 작성자 :
// @ 수정자(1) :
// @ 수정내용(1) :
// ============================================================================
gf_getHMSNoFormat = function(date){
    var rtn = date.replace(/:/g,"");
    return rtn;
}

// ============================================================================
// Funciotn Name : gf_getYMDHMSFormat(yyyymmddHHMMSS)
// @ param : yyyymmddHHMMSS(년도4자리 월2자리 일2자리 시간2자리 분2자리 초2자리를 붙인 문자열)
// @ return : 년-월-일 시간:분:초 형식으로 리턴함 예) 2007-07-02 10:20:30
// @ 설명 : 붙여져 있는 년월일시분초를  년-월-일 시간:분:초 형식으로 리턴함.
// @ 작성일 : 2007.07.02
// @ 작성자 :
// @ 수정자(1) : 김성준
// @ 수정내용(1) :
// ============================================================================
gf_getYMDHMSFormat = function(yyyymmddHHMMSS){
    var dayGubun  = "/";
    var timeGubun = ":";
    if (yyyymmddHHMMSS.length > 14) return "00";

    var year  = "";
    var month = "";
    var day   = "";

    var hour  = "";
    var min   = "";
    var sec   =" ";

    if (yyyymmddHHMMSS.length == 14) {
        var SdateY = eval( yyyymmddHHMMSS.substring(0,4) );
        var SdateM = eval( yyyymmddHHMMSS.substring(4,6) );
        var SdateD = eval( yyyymmddHHMMSS.substring(6,8) );

        var StimeH = eval( yyyymmddHHMMSS.substring(8,10) );
        var StimeM = eval( yyyymmddHHMMSS.substring(10,12) );
        var StimeS = eval( yyyymmddHHMMSS.substring(12,14) );

        var yr = SdateY + 0;
        var mName = SdateM + 0;
        var dName = SdateD + 0;

        var h = StimeH + 0;
        var m = StimeM + 0;
        var s = StimeS + 0;

        if (yr < 100) year=("19"+yr).toString();
        else year=yr.toString();

        if (mName <10) month=("0"+mName).toString();
        else month=mName.toString();

        if (dName <10) day=("0"+dName).toString();
        else day=dName.toString();

        if (h < 10) hour=("0"+h).toString();
        else hour=h.toString();

        if (m <10) min=("0"+m).toString();
        else min=m.toString();

        if (s <10) sec=("0"+s).toString();
        else sec=s.toString();

        return year + dayGubun + month + dayGubun + day + " " + hour + timeGubun + min + timeGubun + sec ;
    }
    else if (yyyymmddHHMMSS.length == 8) {
        var SdateY = eval( yyyymmddHHMMSS.substring(0,4) );
        var SdateM = eval( yyyymmddHHMMSS.substring(4,6) );
        var SdateD = eval( yyyymmddHHMMSS.substring(6,8) );

        var now = new Date(SdateY, Number(SdateM)-1, SdateD);

        var yr = now.getYear();
        var mName = now.getMonth() + 1;
        var dName = now.getDate();

        if (yr < 100) year=("19"+yr).toString();
        else year=yr.toString();

        if (mName <10) month=("0"+mName).toString();
        else month=mName.toString();

        if (dName <10) day=("0"+dName).toString();
        else day=dName.toString();

        return year + dayGubun + month + dayGubun + day;
    }

    return "";
}

// ============================================================================
// Funciotn Name : gf_getYMDHMSNoFormat(datetime)
// @ param : yyyy-mm-dd hh:mi:ss
// @ return : 년 월 일 시 분 초에 구분자를 표시하는 문자('-', ':')나 공백이 있는 경우 삭제하여 리턴함
// @ 설명 : 년 월 일 시 분 초에 구분자를 표시하는 문자('-', ':')나 공백이 있는 경우 삭제하여 리턴함
// @ 작성일 :
// @ 작성자 :
// @ 수정자(1) :
// @ 수정내용(1) :
// ============================================================================
gf_getYMDHMSNoFormat = function(datetime){
    var term = "/";
    var strDate = "";

    var rtn = datetime.replace(/:/g,"");

    for(i=0 ; i<rtn.length ; i++){
        if(term == rtn.substring(i,i+1)){
            strDate += " ";
        }
        else{
            strDate += rtn.substring(i,i+1);
        }
    }

    rtn = strDate.replace(/ /g,"");
    return rtn;
}

// ============================================================================
// Funciotn Name : gf_today()
// @ param :
// @ return : 오늘일자를 리턴함 (형태:년 월 일을 붙임) 예)20070712
// @ 설명 : 오늘일자를 구해서 붙여서 리턴함.
// @ 작성일 :
// @ 작성자 :
// @ 수정자(1) :
// @ 수정내용(1) :
// ============================================================================
gf_today = function() {
    var gubun = "/";
    var now = new Date();
    var yr = now.getYear();
    var mName = now.getMonth() + 1;
    var dName = now.getDate();

    if (yr < 100)
        year=("19"+yr).toString();
    else
        year=yr.toString();

    if (mName <10)
        month=("0"+mName).toString();
    else
        month=mName.toString();

    if (dName <10)
        day=("0"+dName).toString();
    else
        day=dName.toString();

    return year + month + day;
}

// ============================================================================
// Funciotn Name : gf_to_day()
// @ param :
// @ return : 오늘일자를 리턴함 (형태:년-월-일) 예)2007-07-12
// @ 설명 : 오늘일자를 구해서 형태에 맞게 리턴함.
// @ 작성일 :
// @ 작성자 :
// @ 수정자(1) :
// @ 수정내용(1) :
// ============================================================================
gf_to_day = function() {
    var gubun = "/";
    var now = new Date();
    var yr = now.getYear();
    var mName = now.getMonth() + 1;
    var dName = now.getDate();

    if (yr < 100)
        year=("19"+yr).toString();
    else
        year=yr.toString();

    if (mName <10)
        month=("0"+mName).toString();
    else
        month=mName.toString();

    if (dName <10)
        day=("0"+dName).toString();
    else
        day=dName.toString();

    return year + gubun + month + gubun + day;
}


// ============================================================================
// Funciotn Name : gf_popupModal(path,arg,width,height,iTop,iLeft)
// @ param : path,arg,width,height,iTop,iLeft
// @ return : showModalDialog(path,arg,shape)
// @ 설명 : 모달로 팝업시키기 위한 함수(1280 X 1024 인경우)
// @ 작성일 :
// @ 작성자 :
// @ 수정자(1) :
// @ 수정내용(1) :
// ============================================================================
gf_popupModal = function(path,arg,width,height,iTop,iLeft){
    if(iTop  == null) iTop  = (screen.availHeight-height)/2;
    if(iLeft == null) iLeft = (screen.availWidth-width)/2;
    if(iTop  < 100) iTop  = 100;
    var shape  = "dialogWidth:"     + width +"px";
        shape += ";dialogHeight:"   + height+"px";
        shape += ";dialogTop:"      + iTop+"px";
        shape += ";dialogLeft:"     + iLeft+"px";
        shape += ";help:No;status:no;scroll:no;resizable:yes";
    return showModalDialog(path,arg,shape);
}

// ============================================================================
// Funciotn Name : gf_popup(path,arg,width,height,iTop,iLeft)
// @ param : path,arg,width,height,iTop,iLeft
// @ return : window.open(path,arg,shape)
// @ 설명 : 팝업시키기 위한 함수(1024 X 768 인경우)
// @ 작성일 :
// @ 작성자 :
// @ 수정자(1) :
// @ 수정내용(1) :
// ============================================================================
gf_popup = function(path,arg,width,height,iTop,iLeft){

    if(iTop  == null) iTop  = (screen.availHeight-height)/2;
    if(iLeft == null) iLeft = (screen.availWidth-width)/2;
    if(iTop  < 88   ) iTop  = 88;
    var shape  = "dialogWidth:"     + width +"px";
        shape += ";dialogHeight:"   + height+"px";
        shape += ";dialogTop:"      + iTop+"px";
        shape += ";dialogLeft:"     + iLeft+"px";
        shape += ";help:No;status:no;scroll:no;resizable:yes";
    showModelessDialog(path,arg,shape);
}

// ============================================================================
// Funciotn Name : gf_getHyphenTelNo(sDDR, sFirstNo, sLastNo)
// @ param : sDDR, sFirstNo, sLastNo
// @ return : 하이픈("-")이 붙여진 전화번호를 문자열로 반환
// @ 설명 : 분리된 전화먼호에 '-' 붙이기 ex) "010", "1111", "2345" -> "010-1111-2345"
// @ 작성일 : 2006.12.12
// @ 작성자 : 김성준
// @ 수정자(1) :
// @ 수정내용(1) :
// ============================================================================
gf_getHyphenTelNo = function(sDDR, sFirstNo, sLastNo) {
    var tel1 = "";
    var tel2 = "";
    var tel3 = "";

    if(sDDR != null && sFirstNo != null && sLastNo != null){
        tel1 = gf_getAllTrim(sDDR);
        tel2 = gf_getAllTrim(sFirstNo);
        tel3 = gf_getAllTrim(sLastNo);
    }
    else if(sDDR != null && sFirstNo == null && sLastNo == null){
        tel3 = gf_getAllTrim(sDDR);
    }
    else if(sDDR != null && sFirstNo != null && sLastNo == null){
        tel2 = gf_getAllTrim(sDDR);
        tel3 = gf_getAllTrim(sFirstNo);
    }

    if(tel1 == "" && tel2 != "" && tel3 != ""){
        return gf_getAllTrim(tel2+"-"+tel3);
    }
    else if(tel1 == "" && tel2 == "" && tel3 != ""){
        return gf_getAllTrim(tel3);
    }
    else if(tel1 == "" && tel2 == "" && tel3 == ""){
        return "";
    }

    return gf_getAllTrim(tel1+"-"+tel2+"-"+tel3);
}


// ============================================================================
// Funciotn Name : gf_getDelHyphen(str)
// @ param : 전화번호-하이픈 있는
// @ return : 하이픈을 제거한 전화번호 리턴
// @ 설명 : 전화번호에 하이픈이 있는 경우 하이픈을 제거한다.
// @ 작성일 : 2007.07.02
// @ 작성자 : 김성준
// @ 수정자(1) :
// @ 수정내용(1) :
// ============================================================================
  gf_getDelHyphen = function(str) {
    rtn = str.replace(/-/g,"");
    return rtn;
  }


// ============================================================================
// Funciotn Name : gf_getDivTelNo(str)
// @ param : 전화번호-하이픈없는
// @ return : Array() 사이즈는 3
// @ 설명 : 붙여진 전화번호를 3개로 나누어 Array로 리턴함
// @ 작성일 : 2007.07.02
// @ 작성자 : 김성준
// @ 수정자(1) :
// @ 수정내용(1) :
// ============================================================================
gf_getDivTelNo = function(str) {
    var tel = new Array();
    var bDDD = false;

    var sTelNo = gf_getTrim(str); // 모든 공백제거
    sTelNo = gf_getDelHyphen(sTelNo); // 모든 하이픈(-) 제거

    for (i=0 ; i<sTelNo_DDD.length ;i++){
        if (sTelNo.substr(0,sTelNo_DDD[i].length) == sTelNo_DDD[i]) {
            tel[0] = gf_getTrim(sTelNo.substring(0,sTelNo_DDD[i].length));
            tel[1] = gf_getTrim(sTelNo.substring(tel[0].length,sTelNo.length-4));
            tel[2] = gf_getTrim(sTelNo.substring(sTelNo.length-4, sTelNo.length));
            bDDD = true;
        }
    }

    if(bDDD == false){ // 국번이 없는 경우 또는 국번이 올바르지 않는 경우
        tel[0] = gf_getAllTrim(sTelNo.substring(sTelNo.length-12,sTelNo.length-8));
        tel[1] = gf_getAllTrim(sTelNo.substring(sTelNo.length-8,sTelNo.length-4));
        tel[2] = gf_getAllTrim(sTelNo.substring(sTelNo.length-4, sTelNo.length));
    }

    return tel;
}


// ============================================================================
// Funciotn Name : gf_getTrimLength(str)
// @ param : 문자열
// @ return : 공백이 없는 문자열의 사이즈
// @ 설명 : 문자열의 공백을 없애고 사이즈를 구함
// @ 작성일 : 2007.07.02
// @ 작성자 : 김성준
// @ 수정자(1) :
// @ 수정내용(1) :
// ============================================================================
gf_getTrimLength = function(str) {
  str = str.trim();
  return(str.length+(escape(str)+"%u").match(/%u/g).length-1);
}

// ============================================================================
// Funciotn Name : gf_getTrim(str)
// @ param : 문자열
// @ return : 공백이 없는 문자열
// @ 설명 : 문자열의 공백을 없애기 위한 함수
// @ 작성일 : 2007.07.02
// @ 작성자 : 김성준
// @ 수정자(1) :
// @ 수정내용(1) :
// ============================================================================
  gf_getTrim = function(str) {
    rtn = str.replace(/(\s+$)/g,"");
    return rtn;
  }

// ============================================================================
// Funciotn Name : gf_getLength(str)
// @ param : 문자열
// @ return : 문자열의 사이즈
// @ 설명 : 문자열의 사이즈를 구하기 위한 함수
// @ 작성일 : 2007.07.02
// @ 작성자 : 김성준
// @ 수정자(1) :
// @ 수정내용(1) :
// ============================================================================
gf_getLength = function(str) {
  return(str.length+(escape(str)+"%u").match(/%u/g).length-1);
}

// ============================================================================
// Funciotn Name : gf_chkNumber(obj)
// @ param :
// @ return :
// @ 설명 : 숫자가 입력되었는지 확인하기 위한 함수
// @ 작성일 : 2007.07.02
// @ 작성자 :
// @ 수정자(1) :
// @ 수정내용(1) :
// ============================================================================
gf_chkNumber = function(obj){

    var validArr = new Array("8","9","13","17","18","20","25","35","36","37","38","39","40","45","46","48","49","50","51","52","53","54","55","56","57","91","92","96","97","98","99","100","101","102","103","104","105","144");

    var flag = true;
    for (i=0; i < validArr.length; i++){
        if (event.keyCode == validArr[i]) flag = false;
    }

    if (flag) {
        event.returnValue = false;
        alert("숫자만 입력해 주십시요.");
        obj.value = "";
    }
}


// ============================================================================
// Funciotn Name : gf_chkNumberKeycode(keycode)
// @ param :
// @ return :
// @ 설명 : keycode를 통해 숫자가 입력되었는지 확인하기 위한 함수
// @ 작성일 : 2007.07.02
// @ 작성자 : 김성준
// @ 수정자(1) :
// @ 수정내용(1) :
// ============================================================================
gf_chkNumberKeycode = function(keycode){

    var validArr = new Array("8","9","13","17","18","20","25","35","36","37","38","39","40","45","46","48","49","50","51","52","53","54","55","56","57","91","92","96","97","98","99","100","101","102","103","104","105","144");

    var flag = true;
    for (i=0; i < validArr.length; i++){
        if (keycode == validArr[i]) flag = false;
    }

    if (flag) {
        event.returnValue = false;
        alert("숫자만 입력해 주십시요.");
    }
}



// ============================================================================
// Funciotn Name : gf_getDivString(str, symbol)
// @ param : 문자열, 구분자
// @ return : Array
// @ 설명 : 어떠한 구분자에 의해 문자열 나누기 - 리턴값 Array
// @ 작성일 : 2007.07.02
// @ 작성자 : 김성준
// @ 수정자(1) :
// @ 수정내용(1) :
// ============================================================================
gf_getDivString = function(str, symbol) {

    var strValue = str;
    var arrStrValue = new Array();
    var divCount = 0;
    var temp = "";

    for (i=0 ; i<strValue.length ;i++){
        if (strValue.substr(i,1) == symbol) {
            temp = "";
            divCount++;
        }
        else{
            temp = temp + strValue.substr(i,1);
            arrStrValue[divCount] = temp;
        }
     }

    return arrStrValue;
}

// ============================================================================
// Funciotn Name : gf_addItem(obj, id, type, name, value)
// @ param : obj(생성될오브젝트), id(생성될 오브젝트의 상위오브젝트의 아이디), type(text, hidden등), name(해당오브젝트의 이름), value(값)
// @ return :
// @ 설명 : 동적으로 textbox또는 hidden 생성하기
// @ 작성일 :
// @ 작성자 :
// @ 수정자(1) :
// @ 수정내용(1) :
// ============================================================================
gf_addItem = function(obj, id, type, name, value){
    var id  = obj.getElementById(id);
    var input = obj.createElement("input");
    input.type = type;
    input.name = name;
    input.value = value;
    id.appendChild(input);
    input = null;
}

// ============================================================================
// Funciotn Name : gf_deleteItem(obj, id, name)
// @ param : obj(생성될오브젝트), id(생성될 오브젝트의 상위오브젝트의 아이디), name(삭제할오브젝트의 이름)
// @ return :
// @ 설명 : id에 존재하고 있는 객체중에 name에 해당하는 객체를  모두 삭제한다.(리스트에서 선택된 항목삭제를 취소할 때 사용함)
// @ 작성일 :
// @ 작성자 :
// @ 수정자(1) :
// @ 수정내용(1) :
// ============================================================================
gf_deleteItem = function(obj, id, name){
    var id = obj.getElementById(id);
    if(id.hasChildNodes()){
        var children = id.childNodes;
        for(inx=0 ; inx<children.length ; inx++){
            if(children.item(inx).name == name){
                id.removeChild(children.item(inx));
                inx--;
            }
        }
    }
}

// ============================================================================
// Funciotn Name : gf_autoCheckbox(checkboxObj, obj)
// @ param : checkboxObj(체크박스오브젝트), obj(해당오브잭트)
// @ return :
// @ 설명 : 조회조건의 체크박스 자동 체크하기
// @ 작성일 : 2007.07.02
// @ 작성자 : 김성준
// @ 수정자(1) :
// @ 수정내용(1) :
// ============================================================================
gf_autoCheckbox = function(checkboxObj, obj) {
    if( obj.value == "") {
        checkboxObj.checked = false;
    }
    else {
        checkboxObj.checked = true;
    }
}

// ============================================================================
// Funciotn Name : prototype.replaceAll(searchStr, replaceStr)
// @ param  searchStr - 찾는 문자, replaceStr - 바꿀 문자
// @ return 바뀐 문자
// 작성자 : 조경태
// 작성일자 : 2007.11.12
// 설명 : replaceAll의 기능
// 변경일(1) :
// 변경사유(1) :
// ============================================================================
String.prototype.gf_replaceAll = function(searchStr, replaceStr) {
    var temp = this;

    while(temp.indexOf(searchStr) != -1) {
        temp = temp.replace(searchStr, replaceStr);
    }
    return temp;
}

// ============================================================================
// Funciotn Name : gf_truncate(value, length)
// @ param str :  formobj : 오브젝트, length : 자를길이
// @ return : 연산된 문자열
// 작성자 :
// 작성일자 : 2006.07.01
// 설명 :   value를 주어진 길이의 문자열로 만듬(길이가 길 경우 자름)(TEXTAREA를 사용할때 유용함.)
// 변경일(1) : 2006.12.07
// 변경사유(1) : Naming Rule에 따름 / valueFormat 통합
// ============================================================================
gf_truncate = function(formobj, length){
    var inx,cnt;

    if(!formobj.value){return ;}

    var str = ""+formobj.value;

    if(length <= 0){return ;}

    for(inx = 0, cnt = 0; inx < str.length; inx ++){

        if(str.charCodeAt(inx) < 256)
            cnt ++;
        else
            cnt += 2;

        if(cnt > length)
        {
            alert( '최대 (영문:' + length + ',한글:' + (length/2) + ')자를 넘을수 없습니다.');
            formobj.value = str.substring(0,inx);
            return;
        }
    }
}

// ============================================================================
// Funciotn Name : gf_getCheckboxValue(checkboxobj, returnobj)
// @ param str :  checkboxobj : 체크박스오브젝트, returnobj : 값을 넣고자 하는 오브젝트
// @ return : 0 또는 1 체크가 되어있는경우 1 아닌경우 0을 리턴
// 작성자 :
// 작성일자 :
// 설명 : 체크박스의 체크여부를 알아내서 0또는 1값을 보내고자 하는 오브젝트에 값을 넣어준다.
// 변경일(1) :
// 변경사유(1) :
// ============================================================================
gf_getCheckboxValue = function(checkboxobj, returnobj){
    var returnvalue = "";

    // 객체가 없는 경우
    if( checkboxobj == null){ return ; }

    if(checkboxobj.checked == false){
        returnvalue = "0";
    }
    else{
        returnvalue = "1";
    }

    returnobj.value = returnvalue;

    return returnvalue;
}

// ============================================================================
// Funciotn Name : gf_upperString(lowerStr)
// @ param str :  lowerStr : 소문자
// @ return : 대문자로 변환된 문자
// 작성자 : 진영민
// 작성일자 : 2007/11/15
// 설명 : 소문자를 입력받아 대문자로 변경함.(코드 등록에 사용함)
// 변경일(1) :
// 변경사유(1) :
// ============================================================================
gf_upperString = function(lowerStr){
    var upperStr = "";
    var lowerChar = '';

    for(i = 0; i < lowerStr.length; i++) {
        lowerChar = lowerStr.charAt(i);
        if(lowerChar.charCodeAt(0) >= 97 && lowerChar.charCodeAt(0) <= 122 ){
            upperStr += String.fromCharCode(lowerChar.charCodeAt(0) - 32);
        }
        else{
            upperStr += lowerChar
        }
    }
    return upperStr;
}


// ============================================================================
// Funciotn Name : gf_to_week()
// @ param :
// @ return :
// @ 설명 :
// @ 작성일 :
// @ 작성자 :
// @ 수정자(1) :
// @ 수정내용(1) :
// ============================================================================
gf_to_week = function() {
    var gubun = "/";
    var now = new Date();
    var dName = now.getDate() ;

    now.setDate(dName-7);

    var yr = now.getYear();
    var mName = now.getMonth() + 1;
    dName = now.getDate() ;

    if (yr < 100)
        year=("19"+yr).toString();
    else
        year=yr.toString();

    if (mName <10)
        month=("0"+mName).toString();
    else
        month=mName.toString();

    if (dName <10)
        day=("0"+dName).toString();
    else
        day=dName.toString();

    var days = year + gubun + month + gubun + day;

    return days;

}


// ============================================================================
// Funciotn Name : gf_to_month()
// @ param :
// @ return :
// @ 설명 :
// @ 작성일 :
// @ 작성자 :
// @ 수정자(1) :
// @ 수정내용(1) :
// ============================================================================
gf_to_month = function() {
    var gubun = "/";
    var now = new Date();
    var dName = now.getDate() ;

    now.setDate(dName-30);

    var yr = now.getYear();
    var mName = now.getMonth() + 1;
    dName = now.getDate() ;

    if (yr < 100)
        year=("19"+yr).toString();
    else
        year=yr.toString();

    if (mName <10)
        month=("0"+mName).toString();
    else
        month=mName.toString();

    if (dName <10)
        day=("0"+dName).toString();
    else
        day=dName.toString();

    var days = year + gubun + month + gubun + day;

    return days;

}

//============================================================================
//Funciotn Name : gf_add_day()
//@ param :
//@ return :
//@ 설명 :
//@ 작성일 :
//@ 작성자 :
//@ 수정자(1) :
//@ 수정내용(1) :
//============================================================================
gf_add_day = function(days) {
	var gubun = "/";
	var now = new Date();
	var dName = now.getDate();

	now.setDate(dName + days);

	var yr = now.getYear();
	var mName = now.getMonth() + 1;
	dName = now.getDate() ;

	if (yr < 100) {
		year = ("19" + yr).toString();
	} else {
		year = yr.toString();
	}

	if (mName < 10) {
		month = ("0" + mName).toString();
	} else {
		month = mName.toString();
	}

	if (dName < 10) {
		day = ("0" + dName).toString();
	} else {
		day = dName.toString();
	}
	var days = year + gubun + month + gubun + day;

    return days;
}

// ============================================================================
// Funciotn Name : gf_calDateRange(fromday, today)
// @ param : fromday, today
// @ return : 차이 일수
// @ 설명 :
// @ 작성일 : 2008.2.3
// @ 작성자 : 김경희
// @ 수정자(1) :
// @ 수정내용(1) :
// ============================================================================
 gf_calDateRange = function(fromdate, todate){
     var FORMAT = "/";

    // 년도, 월, 일로 분리
    var start_dt = fromdate.split(FORMAT);
    var end_dt = todate.split(FORMAT);

    // 월 - 1(자바스크립트는 월이 0부터 시작하기 때문에...)
    // Number()를 이용하여 08, 09월을 10진수로 인식하게 함.
    start_dt[1] = (Number(start_dt[1]) - 1) + "";
    end_dt[1] = (Number(end_dt[1]) - 1) + "";

    var from_dt = new Date(start_dt[0], start_dt[1], start_dt[2]);
    var to_dt = new Date(end_dt[0], end_dt[1], end_dt[2]);

    return (to_dt.getTime() - from_dt.getTime()) / 1000 / 60 / 60 / 24;
 }

// ============================================================================
// Funciotn Name : checkPhoneNumb(telno1, telno2, telno3)
// @ param  telno1 - 지역번호 또는 핸드폰 서비스 번호, telno2 - 지역번호, telno3 - 번호
// @ return 전화번호 유효성 체크 결과
// 작성자 : 김경희
// 작성일자 : 2007.11.30
// 설명 : 전화번호 유효성 체크한다.
// 변경일(1) :
// 변경사유(1) :
// ============================================================================
gf_checkPhoneNumb = function(telno1, telno2, telno3) {
    // telno1, telno2, telno3 가 empty string인지는 이 function을 호출하기 전에 체크하도록 필수입력항목일 경우에만 empty string 인지 체크하므로

    if (
               (
                   telno1 == "02"  || telno1 == "031" ||
                   telno1 == "032" || telno1 == "033" ||
                   telno1 == "041" || telno1 == "042" ||
                   telno1 == "043" || telno1 == "051" ||
                   telno1 == "052" || telno1 == "053" ||
                   telno1 == "055" ||
                   telno1 == "054" || telno1 == "061" ||
                   telno1 == "062" || telno1 == "063" ||
                   telno1 == "064" || telno1 == "010" ||
                   telno1 == "011" || telno1 == "016" ||
                   telno1 == "017" || telno1 == "018" ||
                   telno1 == "019" || telno1 == "070" ||
                   telno1 == "080"
               )
               &&
               (
                   (gf_getLength(gf_getTrim(telno2)) >= 3) && // 국번은 3자리 이상
                   (gf_getLength(gf_getTrim(telno3)) == 4) && // 뒷번호는 4자리 이상
                   (telno2.substr(0, 1) != "0")  // 국번의 첫자리가 0 이면 안됨.
               )
       ) {
        return true;
    }
    else {
        alert("전화번호를 잘못입력하였습니다.");
        return false;
    }

    return false;
}

// ============================================================================
// Funciotn Name : checkJuminNumb(jumin1, jumin2)
// @ param  jumin1 - 생년월일, jumin2 - 번호
// @ return 주민등록번호 유효성 체크 결과
// 작성자 : 김경희
// 작성일자 : 2007.11.30
// 설명 : 전화번호 유효성 체크한다.
// 변경일(1) :
// 변경사유(1) :
// ============================================================================
gf_checkJuminNumb = function(jumin1, jumin2) {
    var chk =0;
    var yy = jumin1.substring(0,2);
    var mm = jumin1.substring(2,4);
    var dd = jumin1.substring(4,6);
    var sex = jumin2.substring(0,1);

    if (jumin1.length!=6) {
        alert ('주민등록번호 앞자리를 입력하십시오');
        return false;
    }

    if (jumin2.length != 7 ) {
        alert ('주민등록번호 뒷자리를 입력하십시오.');
        return false;
    }

    if (mm < 1 || mm > 12 || dd < 1){
        alert ('주민등록번호 앞자리가 잘못되었습니다.');
        return false;
    }

    if (sex > 0 && sex < 7 ){
    }
    else {
        alert ('주민등록번호 뒷자리가 잘못되었습니다.');
        return false;
    }

    if (sex == 5 || sex == 6) {
        var fgnno = "";
        fgnno = jumin1 + jumin2;
        var sum=0;
        var odd=0;
        buf = new Array(13);
        for(i=0; i<13; i++) { buf[i]=parseInt(fgnno.charAt(i)); }
        odd = buf[7]*10 + buf[8];
        if(odd%2 != 0) {
            alert ('맞지 않는 주민등록번호입니다.[입력하신 주민번호 : ' + jumin1 + "-" + jumin2 + "]");
            return false;
        }
        if( (buf[11]!=6) && (buf[11]!=7) && (buf[11]!=8) && (buf[11]!=9) ) {
            alert ('맞지 않는 주민등록번호입니다.[입력하신 주민번호 : ' + jumin1 + "-" + jumin2 + "]");
            return false;
        }
        multipliers = [2,3,4,5,6,7,8,9,2,3,4,5];
        for(i=0, sum=0; i<12; i++) { sum += (buf[i] *= multipliers[i]); }
        sum = 11 - (sum%11);
        if(sum >= 10) { sum -= 10; }
        sum += 2;
        if(sum >= 10) { sum -= 10; }
        if(sum != buf[12]) {
            alert ('맞지 않는 주민등록번호입니다.[입력하신 주민번호 : ' + jumin1 + "-" + jumin2 + "]");
            return false;
        }
    }
    else {
        for (var i = 0; i <=5 ; i++) {
            chk = chk + ((i%8+2) * parseInt(jumin1.substring(i,i+1)))
        }
        for (var i = 6; i <=11 ; i++) {
            chk = chk + ((i%8+2) * parseInt(jumin2.substring(i-6,i-5)))
        }

        chk = 11 - (chk %11)
        chk = chk % 10

        if (chk != jumin2.substring(6,7)) {
            alert ('맞지 않는 주민등록번호입니다.[입력하신 주민번호 : ' + jumin1 + "-" + jumin2 + "]");
            return false;
        }
    }

     return true;
}

// ============================================================================
// Funciotn Name : checkEmailAddr(emailid, emailaddr)
// @ param  emailid - 이메일아이디, emailaddr - 이메일주소
// @ return 이메일 주소 유효성 체크 결과
// 작성자 : 김경희
// 작성일자 : 2007.12.03
// 설명 : 이메일주소 유효성 체크한다.
// 변경일(1) :
// 변경사유(1) :
// ============================================================================
gf_checkEmailAddr = function(emailid, emailaddr)
{
    var email = emailid + "@" + emailaddr;
      if (!(email.search(/^\s*[\w\~\-\.]+\@[\w\~\-]+(\.[\w\~\-]+)+\s*$/g)>=0)) {
          alert("이메일 주소를 잘못 입력하셨습니다.");
          return false;
      }

    return true;
}

// ============================================================================
// Funciotn Name : gf_chkMsgLength(intMax,objMsg)
// @ param  intMax - max length, objMsg - msg object
// @ return max length 체크
// 작성자 : 김경희
// 작성일자 : 2007.12.27
// 설명 : max length 체크한다.
// 변경일(1) :
// 변경사유(1) :
// ============================================================================
gf_chkMsgLength = function(intMax,objMsg) {
    var length = gf_getLength(objMsg.value);
    if (length > intMax) {
         alert("최대 " + intMax + "byte이므로 초과된 글자수는 자동으로 삭제됩니다.\n");
         objMsg.value = objMsg.value.replace(/\r\n$/, "");
          objMsg.value = gf_assertMsg(intMax,objMsg.value );
     }
}

// ============================================================================
// Funciotn Name : gf_assertMsg(intMax,objMsg)
// @ param  intMax - max length, objMsg - msg
// @ return max length 초과 글자 삭제
// 작성자 : 김경희
// 작성일자 : 2007.12.27
// 설명 : max length 초과 글자 삭제한다.
// 변경일(1) :
// 변경사유(1) :
// ============================================================================
gf_assertMsg = function(intMax,objMsg ) {
     var inc = 0;
     var nbytes = 0;
     var msg = "";

     var msglen = objMsg.length;
     for (i=0; i<msglen; i++) {
          var ch = objMsg.charAt(i);
          if (escape(ch).length > 4) {
              inc = 2;
          } else if (ch == '\n') {
           if (objMsg.charAt(i-1) != '\r') {
               inc = 1;
           }
          } else if (ch == '<' || ch == '>') {
               inc = 4;
          } else {
               inc = 1;
          }
          if ((nbytes + inc) > intMax) {
               break;
          }
          nbytes += inc;
          msg += ch;
     }
     return msg;
}

//
// ============================================================================
// Funciotn Name : gf_sc_check(val)
// @ param  문자
// @ return
// 작성자 : 한경식
// 작성일자 : 2008.02.04
// 설명 : 특수문자 체크
// 변경일(1) :
// 변경사유(1) :
// ============================================================================
gf_sc_check = function(val) {
        //var mikExp = /[$\\@\\\#%\^\&\*\(\)\<\>\+\_\{\}\`\~\=\'\"\|]/; // 제한할 특수문자
        var mikExp = /[\<\>\']/; // 제한할 특수문자
        // 현재 제한 되어 있는 특수문자 목록입니다.
        // $  @ # % ^ & * ( ) < > + _ { } ` ~ = ' " |

       var strPass = val.value;
       var strLength = strPass.length;
       var lchar = val.value.charAt((strLength) - 1);
       if(lchar.search(mikExp) != -1) {
          var tst = val.value.substring(0, (strLength) - 1);
          alert ('       제한되어 있는 특수 문자 목록 \n'
                 +'====================================\n'
//                 +' $  @ # % ^ & * ( ) < > + _ { } ` ~ = ' + "'" + ' " | \n'
                 + '                        < > ' + "'\n"
                 +'====================================\n\n'
                 +' 보안을 위해 위의 특수문자는 사용할수 없습니다.');
          val.value = tst;

      }
}

//============================================================================
//Funciotn Name : gf_getJSONDataFromForm(form)
//@ param  form
//@ return form 데이터가 변환된 JSONString
//작성자 : 조경태
//작성일자 : 2008.08.14
//설명 : form의 데이터를 JSONString 형태로 변환하여 반환
//변경일(1) :
//변경사유(1) :
//============================================================================
gf_getJSONDataFromForm = function(form) {
    var elements = form.elements;
    var jsonText = "{";
    for(idx=0; idx < elements.length; idx++) {
        if(jsonText == "{") {
                // 첫 데이터면 , 없이 데이터만 입력
            jsonText += "\"" + elements[idx].name + "\" : \"" + escape(encodeURIComponent(elements[idx].value)) + "\"";
        } else {
                jsonText += ", \"" + elements[idx].name + "\" : \"" + escape(encodeURIComponent(elements[idx].value)) + "\"";
            }
    }
    return jsonText += "}";
}

//============================================================================
//Funciotn Name : gf_getCheckedValue(form)
//@ param  form
//@ return 체크된 체크박스의 value값('값1', '값2'...)
//작성자 : 조경태
//작성일자 : 2008.12.26
//설명 : form내에 있는 체크박스중 체크된 값들을 반환한다.
//변경일(1) :
//변경사유(1) :
//============================================================================
gf_getCheckedValue = function(form) {
    var elements = form.elements;
    var returnStirng = "";
    for(idx=0, size = elements.length; idx < size; idx++) {
        if(elements[idx].type == "checkbox" && elements[idx].checked) {
            if(returnStirng == "") {
                returnStirng = "'" + elements[idx].value +"'";
            } else {
                returnStirng += ",'" + elements[idx].value +"'";
            }
        }
    }
    return returnStirng;
}

gf_getCheckedValue2 = function(form) {
    var elements = form.elements;
    var returnStirng = "";
    for(idx=0, size = elements.length; idx < size; idx++) {
        if(elements[idx].type == "checkbox" && elements[idx].checked) {
            if(returnStirng == "") {
                returnStirng = gf_convertForMultiKey(elements[idx].value);
            } else {
                returnStirng += ","+gf_convertForMultiKey(elements[idx].value);
            }
        }
    }
    return returnStirng;
}

gf_convertForMultiKey = function(inputString) {
    var keyArray = inputString.split(",");
    var returnArray = "";
    for(i =0; i<keyArray.length; i++) {
        if (i==0) {
            returnArray = "('"+keyArray[i]+"'";
        }
        else {
            returnArray = returnArray + ",'"+keyArray[i]+"'";
        }

        if(i==keyArray.length-1) {
            returnArray = returnArray + ")";
        }
    }
    //alert(returnArray);
    return returnArray;

}


//============================================================================
//Funciotn Name : gf_getClickedTagName(e)
//@ param  event
//@ return 클릭된 태그 이름을 반환한다.
//작성자 : 조경태
//작성일자 : 2008.12.30
//설명 : 클릭된 태그 이름을 반환한다.
//변경일(1) :
//변경사유(1) :
//============================================================================
gf_getClickedTagName = function(e) {
    var targ;
    if (!e) {
        var e=window.event;
    }
    // 마우스를 클릭하지 않는 경우 호출시 빈 문자열을 반환한다.
    if(e == null) {
        return "";
    }
    if (e.target) {
        targ=e.target;
    } else if (e.srcElement) {
      targ=e.srcElement;
    }

    if (targ.nodeType==3) // defeat Safari bug
    {
      targ = targ.parentNode;
    }

    var tname;
    tname=targ.tagName;
    return tname;
}

/******************************************************
 * 체크박스 전체 선택, 해제
 ******************************************************/
 allSelect = function(checkfg) {
     if(document.all.selectcheck) {
         var len = res.length;

         if(len > 1){
             for(i=0; i < res.length; i++) {
                 document.all.selectcheck[i].checked = checkfg;
             }
         }
         else{
             document.all.selectcheck.checked = checkfg;
         }
     }
 }

 /******************************************************
  * 전화번호의 문자를 제거한다.
  ******************************************************/
  isNumberString = function(InString) {
    var returntelno = "";
       if (InString.length == 0) return returntelno;
       RefString = "1234567890";
       for (Count = 0; Count < InString.length; Count++) {
         TempChar = InString.substring (Count, Count+1);
         if (RefString.indexOf(TempChar, 0) != -1)
           returntelno += TempChar;
       }
       return returntelno;
  }

  /******************************************************
   * 시간을 가져온다
   ******************************************************/
   getDateTime = function() {
       var now = new Date();
       var mm = now.getMonth() + 1;
       var dd = now.getDate();
       var hr = now.getHours();
       var mn = now.getMinutes();
       var sc = now.getSeconds();
       var ampm = '오전';
   if (hr>=12) ampm = '오후';
   if (hr>12) hr = hr-12;
   if (hr==0) hr = 12;

   mm = getDigitString(mm, 2, "0");
   dd = getDigitString(dd, 2, "0");
   hr = getDigitString(hr, 2, "0");
   mn = getDigitString(mn, 2, "0");
   sc = getDigitString(sc, 2, "0");

   return mm + '월' + dd + '일' + ' ' + ampm + ' ' + hr + ':' + mn + ':' + sc;
   }

   /******************************************************
* 시계를 보여준다
******************************************************/
showClockObj = function(obj,time) {
    if (obj.type == "text") {
    obj.value = getDateTime();
} else {
    obj.innerHTML = getDateTime();
}

setTimeout('showClock()',time);
}

/******************************************************
* 숫자 등 문자열을 length 자리수의 문자열로 만들어준다(좌측에 str자리수만큼 더해주기) ex : 01, ****1 등
******************************************************/
getDigitString = function( num, length, str) {
    var ret="";
    for(i=0;i<length;i++) ret = ret + str;
    ret = ret + num;
    ret = ret.substring(ret.length - length);
    return ret;
}


/******************************************************
* 우편번호 검색화면 팝업
******************************************************/
searchZipcd = function(formNm, zipNm1, zipNm2, addrNm, focusNm, focusfg) {
   path = "/powerservice/user/user/user_zipcd_pop.do";
   path += "?formNm=" + formNm;
   path += "&zipNm1=" + zipNm1;
   path += "&zipNm2=" + zipNm2;
   path += "&addrNm=" + addrNm;
   path += "&focusNm=" + focusNm;
   path += "&focusfg=" + focusfg;

   retVal = launchCenter(path, "user_zipcd_pop", "443", "760");
   retVal.focus();
}
/******************************************************
* 라디오 버튼 value 값
******************************************************/
getRadioValue = function(obj){
    var len = obj.length;
    var radioValue = "";
    for (var i = 0, m=obj.length; i<m ; i++){
        if(obj[i].checked){
            radioValue = obj[i].value;
        }
    }
    return radioValue;
}

/******************************************************
 * 문자열 byte 체크(한글 포함)
 ******************************************************/
 getByteLength = function(data){
     var len = 0;
     var str = data.substring(0);
     if( str == null ) return 0;
     for(var i=0; i < str.length; i++) {
         var ch = escape(str.charAt(i));
         if( ch.length == 1 ) len++;
         else if( ch.indexOf("%u") != -1) len += 2;
     else if( ch.indexOf("%") != -1) len += ch.length/3;
     }
     return len;
 }

 getCutString = function(data, strLength) {
    var rtnString = "";
var len = 0;
var str = data.substring(0);
if( str == null ) return 0;

for(var i=0; i < str.length; i++) {
    var ch = escape(str.charAt(i));
    if( ch.length == 1 ) len++;
    else if( ch.indexOf("%u") != -1) len += 2;
    else if( ch.indexOf("%") != -1) len += ch.length/3;

    if(len<strLength-3) rtnString += str.charAt(i);
}
if(len <= strLength) rtnString = data;
else rtnString += "...";

    return rtnString;
}

  /******************************************************
  * 전화번호 끊어서 리턴
  * ex ) 025559999 -> 02 555 9999
  ******************************************************/
  getSeperatedNo = function(data){
      var no1 = "";
  var no2 = "";
  var no3 = "";
  var temp = "";
  var rtnList = new Array();

  if (data != null && data.length >= 9) {
      no3 = data.substring(data.length - 4, data.length);
      temp = data.substring(0, data.length - 4);

      if ("02" == temp.substring(0, 2)) {
          no1 = "02";
          no2 = temp.substring(2, temp.length);
      } else if ("0505" == temp.substring(0, 4)) {
          no1 = "0505";
          no2 = temp.substring(4, temp.length);
      } else {
          leftno = temp.substring(0, 3);

          if (leftno == "031" || leftno == "032" ||
              leftno == "033" || leftno == "041" ||
              leftno == "042" || leftno == "043" ||
              leftno == "051" || leftno == "052" ||
              leftno == "053" || leftno == "055" ||
              leftno == "054" || leftno == "061" ||
              leftno == "062" || leftno == "063" ||
              leftno == "064" || leftno == "010" ||
              leftno == "011" || leftno == "016" ||
              leftno == "017" || leftno == "018" ||
              leftno == "019" || leftno == "070" ||
              leftno == "080") {
                  no1 = leftno;
                  no2 = temp.substring(3, temp.length);
              }
          }
      }
      rtnList[0] = no1;
      rtnList[1] = no2;
      rtnList[2] = no3;

      return rtnList;
  }

  /******************************************************
   * 숫자 -> 이미지
   ******************************************************/
   getNumberImgs = function(no, color){
       var number = no.toString();
       var rtn = "";
   var tmp = "";

   for (var i = 0; i < number.length; i++) {
       tmp = number.charAt(i);
       if (tmp == ":") {
           rtn += "<img src='/powerservice/images/scoreboard/" + color + "_colon.png' align='absmiddle'>";
       } else if (tmp == "."){
           rtn += "<img src='/powerservice/images/scoreboard/" + color + "_dot.png' align='absmiddle'>";
       } else {
           rtn += "<img src='/powerservice/images/scoreboard/no_" + color + "_" + tmp + ".png' align='absmiddle'>";
           }
       }
       return rtn;
   }

/******************************************************
 * Date Diff
 ******************************************************/
 getDateDiff = function(date1, date2){
    var compareDate1;
    var compareDate2;

    if (date1.length == 8) {
        compareDate1 = new Date(date1.substr(0,4), date1.substr(4,2), date1.substr(6,2));
        compareDate2 = new Date(date2.substr(0,4), date2.substr(4,2), date2.substr(6,2));
    } else if (date1.length == 14) {
        compareDate1 = new Date(date1.substr(0,4), date1.substr(4,2), date1.substr(6,2), date1.substr(8,2), date1.substr(10,2), date1.substr(12,2));
        compareDate2 = new Date(date2.substr(0,4), date2.substr(4,2), date2.substr(6,2), date2.substr(8,2), date2.substr(10,2), date2.substr(12,2));
    }
    var day = 1000*60*60*24;
    var interval = "";

    if (date1 > date2) {
        interval = compareDate1 - compareDate2;
    } else {
        interval = compareDate2 - compareDate1;
    }
    var diffdays = interval / day;

    return diffdays;
}

/******************************************************
* Email Addr Change
******************************************************/
changeEmailAddr = function(objName, value){
    if (value == "EM99") {	//직접입력
        $(objName).value = "";
        $(objName).readOnly = false;
        $(objName).focus();
    } else {
        $(objName).value = value;
    }
}

/******************************************************
* Find Correct String
* objName : 이메일 주소 Text Box Name
* divName : 정확한 문자열을 보여줄 Div Name
* selObj  : 이메일 주소 SelectBox Object
* correctList : 정확한 문자열 비교 리스트
******************************************************/
findCorrectString = function(objName, divName, selObj, correctList) {
    var percent = 0;
    var tmpPercent = 0;
    var rtnString = "";
    var value = $(objName).value;

    if (value.length > 7) {
        for (var i = 0; i < correctList.length; i++) {
            var correctString = correctList[i];
            var cnt = 0;

            if (value == correctString) {
                rtnString = "";
                break;
            }

            for (var j = 0; j < value.length; j++) {
                var compStr = value.charAt(j);

                if (correctString.substring(j, correctString.length).indexOf(compStr) > -1) {
                    cnt++;
                }
            }
            tmpPercent = cnt / correctString.length * 100;

            if (tmpPercent > percent) {
                percent = tmpPercent;
                rtnString = correctString
            }
        }

        if (rtnString != "" && percent > 50) {
            $(divName).innerHTML = rtnString + "를 입력하셨습니까?";
            $(divName).style.display = "block";
            $(divName).onclick = function() {
                $(objName).value = rtnString;
                $(selObj).value = rtnString;
                $(divName).style.display = "none";
            };
        }
    }
}

/******************************************************
* toggleClassName
* 리스트 table id
* trVal : 클릭한 tr id
******************************************************/
toggleClassName = function(listName, trVal) {
    $("#" + listName + " .tbl_m_grid_on").toggleClass("tbl_m_grid_on");
    $("#" + trVal).toggleClass("tbl_m_grid_on");
}


/**
* 엑셀 다운로드요청 Script
*
* signature
* excelDownLoad( 'excelid', 'sch_searchOption')
* excelid (Mandatary) 				- excelid 식별자
* sch_searchParameterJSON(Mandatory)- sch_로 시작하는 검색 파라미터 집합체(JSON)
*/
excelDownLoad = function() {
    var args = excelDownLoad.arguments;
    if( args.length < 3) {
        return false;
    }

    var url = "/powerservice/common/excelSelectPop.jsp?excelcd=" + args[0] + "&centercd=" + args[1] + "&token=" + args[3];

    var myform=document.forms[0];
    var myWin = window.open("about:blank","winName",getExcelPopStyle(470,340));
    var tempAction=myform.action;
    var tempTarget=myform.target;
    myform.method="post";
    myform.target="winName";
    myform.action=url;
    myform.appendChild(createHidden("sch_searchParameterJSON",args[2]));
    myform.focus();
    myform.submit();

    myform.removeChild(myform.sch_searchParameterJSON);
}
/**
* 엑셀다운로드용 hidden 파라미터 넣기
*
*/
createHidden = function(n,v) {
   var o=document.createElement("input");
   o.type="hidden";
   o.name=n;
   o.id=n;
   o.value=v;

   try {
       return o;
   } finally {
       o = null;
   }
 }
/**
* 엑셀다운로드용 팝업 스타일 반환
*
*/
getExcelPopStyle = function(height, width) {
    var str = "scrollbars=yes, height=" + height + ", innerHeight=" + height;

    str += ", width=" + width + ", innerWidth=" + width;

    if (window.screen)
    {
        var ah = screen.availHeight - 30;
        var aw = screen.availWidth - 10;
        var xc = (aw - width) / 2;
        var yc = (ah - height) / 2;

        str += ", left=" + xc + ", screenX=" + xc;
        str += ", top=" + yc + ", screenY=" + yc;
        str += "toobar=no,menubar=no,title=no";
    }

    return str;
 }

 /**
 * 해당 Form의 "sch_"로 시작하는 검색 파라미터들을 JSON String 으로 만들어 리턴
 *
 * formname "sch_"로 시작하는 검색 파라미터를 추출할 form 이름
 */
 getSearchParameterJSON = function(formname) {
        var formObject = eval('document.' + formname);
        var jsonText = "{";
        var schPattern = /^[a-zA-Z0-9]+/;

        for(idx=0; idx < formObject.length; idx++) {
            if(formObject[idx].name.match(schPattern)) {
                if(jsonText == "{") {
                    // 첫 데이터면 , 없이 데이터만 입력
                    jsonText += "\'" + formObject[idx].name + "\' : \'" + formObject[idx].value + "\'";
                } else {
                    jsonText += ", \'" + formObject[idx].name + "\' : \'" + formObject[idx].value + "\'";
                }
            }
        }
        jsonText += "}";

        return jsonText;
 }

/******************************************************
 * Document Initalize
 * ID가 숫자인 경우 ;를 입력하라는 에러 메세지가 나와서 앞에 소문자i를 붙여서 실행되도록함.
 ******************************************************/
 onStart = function(msg){
    window.status = "[onStart] " + msg;
}


 /******************************************************
  * 로컬파일의 크기 계산
  ******************************************************/
  getFileSize = function(filePath) {
    var len = 0;
    var kbyteSize = 1024;
    if (navigator.appName.indexOf("Netscape") != -1) {
        try {
            netscape.security.PrivilegeManager.enablePrivilege('UniversalXPConnect');
        } catch(e) {
            alert("signed.applets.codebase_principal_support를 설정해주세요!\n"+e);
            return -1;
        }

        try {
            var file = Components.classes["@mozilla.org/file/local;1"].createInstance(Components.interfaces.nsILocalFile);
            file.initWithPath(filePath);

            len = file.fileSize;
        } catch(e) {
            alert("에러 발생:"+e);
        }
    } else if (navigator.appName.indexOf('Microsoft') != -1) {
        var fso = new ActiveXObject("Scripting.FileSystemObject");
        var f = fso.GetFile(filePath);
        len = f.size;
        f = null;
        fso = null;

//		var img = new Image();
//		img.dynsrc = filePath;
//		len = img.fileSize;
    }

    if (len >= (1000 * 1024 * 1024)) {
        len = (len / (1000 * 1024 * 1024)).toFixed(2) + "GB";
    } else if (len > (kbyteSize * 1024)) {
        intSize = (len) / (kbyteSize * 1024) * 100;
        len = (intSize / 100).toFixed(2) + "MB";
    } else if (len > kbyteSize) {
        len = (len / kbyteSize).toFixed(2) + "KB";
    } else {
        len = len.toFixed(2) + "Byte";
    }
    return len;
}

  /******************************************************
   * 당일/전월/전분기 라디오박스 체크
   * 통계 조회 부분에서만 사용함
   ******************************************************/
   dateSet = function(type, nowdate) {
       if(type == "") {
           for(var i=0 ; i <document.reportForm.dateset.length ; i++ ) {
               document.reportForm.dateset[i].checked = false;
           }
       }
       else {
           sdate = "";
           edate = "";
           now=new Date(); // 현재시간 가져오기
           year=now.getYear(); // 년도 가져오기
           month=now.getMonth() + 1; // 월 가져오기 (+1)


           if (type == "now") {
               sdate = edate = nowdate;
           }
           else if(type == "week"){
               var weekday=now.valueOf()+1000*60*60*24*-7; //7일전, (음수면 전일)의 타임스탬프를 얻는다
                 var week=new Date(weekday); //데이트객체 생성
                 var str = new Array();
                 str[str.length]= week.getYear(); //년
               str[str.length]= week.getMonth()+1 < 10 ? "0"+(week.getMonth()+1) : week.getMonth()+1; //월
               str[str.length]= week.getDate() < 10 ? "0"+week.getDate() : week.getDate(); //일
               sdate = str.join("/");
               edate = nowdate;
           }
           else if (type == "month") {
               month--;
               if(month == 0) {   // 전월이 12월이면  년도도 한해 빼야 함.
                   year--;
                   month = 12;
               }

               if(month < 10) {
                   month = "0" + month;
               }

               sdate = year + "/" + month + "/01";
               edate = monthEndDay(year, month);
           }
           else if (type == "quarter") {
               smonth = 0;
               emonth = 0;
               if(month >= 10) {
                   smonth = 7;
                   emonth = 9;
               }
               else if(month >= 7) {
                   smonth = 4;
                   emonth = 6;
               }
               else if(month >= 4) {
                   smonth = 1;
                   emonth = 3;
               }
               else {			// 전년도 분기이므로
                   year--;
                   smonth = 10;
                   emonth = 12;
               }

               if(smonth < 10) {
                   smonth = "0" + smonth;
               }
               if(emonth < 10) {
                   emonth = "0" + emonth;
               }

               sdate = year + "/" + smonth + "/01";
               edate = monthEndDay(year, emonth);
           }

           document.reportForm.startd.value = sdate;
           document.reportForm.endd.value = edate;
       }
   }

   /******************************************************
    * 월의 말일 찾기
    ******************************************************/
    monthEndDay = function(year, month) {
        var intMonth = month;
        if(month <10){
            intMonth = month.replace("0","");
        }

           for(var i = 31; i > 27; i--) {				// 마지막 날짜 찾기
            edate = year + "/" + month + "/" + i;
            var DateObj=new Date(edate);
            temp = DateObj.toLocaleString().split(" ");
            tempMonth = temp[1].replace("월", "");
            if(parseInt(intMonth) == tempMonth) {
                break;
            }
        }
        return edate;
    }


    /******************************************************
     * 비밀번호 체크
     ******************************************************/
     checkPassword = function(upw) {
        if(!/^[a-zA-Z0-9]{6,12}$/.test(upw)) {
            alert('비밀번호는 숫자와 영문자 조합으로 6~12자리를 사용해야 합니다.');
            return false;
        }

        var chk_num = upw.search(/[0-9]/g);
        var chk_eng = upw.search(/[a-z]/ig);

        if (chk_num < 0 || chk_eng < 0) {
            alert('비밀번호는 숫자와 영문자를 혼용하여야 합니다.');
            return false;
        }

        if(/(\w)\1\1\1/.test(upw)) {
              alert('비밀번호에 같은 문자를 4번 이상 사용하실 수 없습니다.');
              return false;
          }
        return true;
    }

    /******************************************************
     * 문자열 길이 체크하여 포커스 이동
     ******************************************************/
     changeFocus = function(value, length, objFocus){
        if (value.length >= length) {
            objFocus.focus();
        }
    }

    /******************************************************
     * 날짜포맷변경
     ******************************************************/
     dateFormat = function(obj, flag) {
        if (flag == 'on') {
            obj.value = gf_getYMDNoFormat(obj.value);
        } else {
            var rtn = gf_getYMDFormat(obj.value);
            obj.value = rtn;
        }
    }

     /******************************************************
     * 숫자 이외의 문자 제거
     ******************************************************/
     removeNoneNum = function(val) {
        var reg=/[^\d]/;

        while (reg.test(val)) {
            val = val.replace(reg, "");
        }
        return val;
    }

     function getBrowserInfo() {
        var arrRes = new Array();
        var ver = 0;

        if(navigator.appName.charAt(0) == "N") {
            if(navigator.userAgent.indexOf("Firefox") != -1) {
                ver = getBrowserEnginVersion("Firefox");

                arrRes[0] = "Firefox";
                arrRes[1] = ver;
                arrRes[2] = "Firefox"+ver+"입니다.";
            } else if(navigator.userAgent.indexOf("Safari") != -1) {
                ver = getBrowserEnginVersion("Safari");

                arrRes[0] = "Safari";
                arrRes[1] = ver;
                arrRes[2] = "Safari"+ver+"입니다.";
            } else if(navigator.userAgent.indexOf("Chrome") != -1) {
                ver = getBrowserEnginVersion("Chrome");

                arrRes[0] = "Chrome";
                arrRes[1] = ver;
                arrRes[2] = "Chrome"+ver+"입니다.";
            }
        } else if(navigator.appName.charAt(0) == "M") {
            ver = getBrowserEnginVersion("MSIE");

            arrRes[0] = "MSIE";
            arrRes[1] = ver;
            arrRes[2] = "MSIE"+ver+"입니다.";
        } else if(navigator.appName.charAt(0) == "O") {
            ver = getBrowserEnginVersion("Opera");

            arrRes[0] = "Opera";
            arrRes[1] = ver;
            arrRes[2] = "Opera"+ver+"입니다.";
        }

        return arrRes;
    }

     // 이벤트를 가져오는 함수(evt.type, evt.charCode, evt.keyCode)
     function fn_getEvent(e) {
         var evt = e || window.event;
         return evt;
     }

     // table을 그려주는 함수
     function appendTable(resDivName, classnm) {
        var obj = document.createElement("table");
        obj.width = "100%";
        obj.className = typeof(classnm) == "undefined" ? "tbl_m_grid" : classnm;

         $("#" + resDivName).empty().append(obj);

         try {
             return obj;
         } finally {
             obj = null;
         }
     }

     // tr을 그려주는 함수
     function appendTr(objTable, i, classnm) {
        var obj = document.createElement("tr");
        obj.id = "tr_" + i;
        obj.style.cursor = "pointer";

        $(objTable).append(obj);

        try {
            return obj;
        } finally {
            obj = null;
        }
     }

     // td를 그려주는 함수
     function appendTd(objTr, id, value, aglignfg) {
        var obj = document.createElement("td");

        if ($("#"+id).is(":visible")) {
            obj.width = $("#"+id).width() + "px";
        } else {
            obj.width = $("#"+id).attr("width");
        }

        if(aglignfg == 0) {
            obj.style.textAlign = "left";
        } else if(aglignfg == 1) {
            obj.style.textAlign = "center";
        } else if(aglignfg == 2) {
            obj.style.textAlign = "right";
        } else {
            obj.style.textAlign = "";
        }

        obj.innerHTML = value;

        $(objTr).append(obj);

        try {
            return obj;
        } finally {
            obj = null;
        }
     }

     /**
     * 매뉴얼 호출 Script
     *
     * signature
     * manualOpen('manualid')
     */
     var oldManualid = "";

     manualOpen = function() {
         var args = manualOpen.arguments;
         if( args.length < 1) {
             return false;
         }
         oldManualid = args[0];
         var sendData = "process=searchManualAjax&manualid="+args[0];

         sendRequest(ajaxManualOpen, sendData, 'POST', '/powerservice/manualAction.do');
     }

     function ajaxManualOpen(request) {
         if(request.status != 200) { return; }
         var resManual = "";
         resManual = eval('(' + request.responseText + ')');

         if(resManual == null || resManual == "") { alert("["+ oldManualid + "] 등록된 매뉴얼이 없습니다."); return; }

         var typefg = urlDecode(resManual[0].typefg);
         var manualid = urlDecode(resManual[0].manualid);
         var attachmentid = urlDecode(resManual[0].attachmentid);

         var width = 900;
         var height = 590;
         var w = screen.availWidth/2 - width/2;
         var h = screen.availHeight/2 - height/2;
         var shape  = "width="+width+",height="+height+",";
         var name = "매뉴얼팝업";
         var path = "/powerservice/system/manual/manual_main_popup.do?manualid=" + manualid;

         if (typefg == "1") {
             top.emptyFrame.location = "/powerservice/fileAction.do?process=downloadFile&manualid="+manualid+"&attachmentid="+attachmentid;
             return;
         } else if(typefg == "2"){
             shape = shape + "top=" + h + ",";
             shape = shape + "left=" + w + ",";
             shape += "titlebar=no,toolbar=no,location=no,directories=no,status=no,";
             shape += "menubar=no,scrollbars=no,resizable=no";
         } else {
             width = 700;
             height = 570;
             w = screen.availWidth/2 - width/2;
             h = screen.availHeight/2 - height/2;
             shape  = "width="+width+",height="+height+",";
             shape = shape + "top=" + h + ",";
             shape = shape + "left=" + w + ",";
             shape += "titlebar=no,toolbar=no,location=no,directories=no,status=no,";
             shape += "menubar=no,scrollbars=no,resizable=no";
             path   = "/powerservice/manualAction.do?process=searchManualOpenByPrimaryKey&manualid="+manualid;
         }

         popup_window = window.open(path, "moniter", shape);
         top.indexFrame.topFrame.addChildren(popup_window);
         popup_window.focus();
     }


