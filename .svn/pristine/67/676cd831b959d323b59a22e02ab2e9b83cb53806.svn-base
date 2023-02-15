//===== CTI Command ======================================================================================================
function CTI_LinkUp(agtid, extid)
{
    var g_PriCti = document.logoutForm.ctiip1.value;	//TSERVER PRIMARY
    var g_PriCtiPort = document.logoutForm.ctiport1.value;  //TSERVER PRIMARY PORT
    var g_BackCti = document.logoutForm.ctiip2.value; //TSERVER PRIMARY
    var g_BackCtiPort = document.logoutForm.ctiport2.value; //TSERVER PRIMARY PORT
    var g_AgtId = agtid; //AGTID
    var g_AgtExt = extid; //EXT번호
    var g_Prefix = ""; //외부발신번호
    var g_Area = "02"; //지역번호
    var g_isHolding = false; //보류 상태인지 여부

    var logPath = "C:\\TelperLog\\TelperLog";
    var loglevel = "20000";
    var maxFileCnt = "100";
    var maxLogLineCnt = "40000";
    var nRet;

    logslog("[TSPCmd.js] f_JobStart ctiip1 : " + g_PriCti + ", ctiport1 : " + g_PriCtiPort + ", agentid : " + g_AgtId + ", Ext : " + g_AgtExt);

    // 이벤트 연결 (attachEvent 를 여러번하게되면 이벤트 발생이 여러번되므로, 한 번만 실행되도록 해야함)
    fn_AttachEvent();

    // ActiveX 초기화
    UCTlpSP.SetLogInfo(logPath, loglevel, maxFileCnt, maxLogLineCnt, 1, 0);
    logslog("[TSPCmd.js] f_JobStart --> SetLogInfo");
    UCTlpSP.SetSvrInfo(g_PriCti, g_PriCtiPort, 20, 10, 10);
    logslog("[TSPCmd.js] f_JobStart --> SetSvrInfo");
    UCTlpSP.SetAgtInfo(g_AgtExt, g_AgtId, "");
    logslog("[TSPCmd.js] f_JobStart --> SetAgtInfo");
    UCTlpSP.SetPbxKind("6");
    logslog("[TSPCmd.js] f_JobStart --> SetPbxKind");

    // 서버연결
    nRet = UCTlpSP.LinkUp(1);
    if(nRet <= 0){
        fn_DetachEvent();
        logslog("[TSPCmd.js] f_JobStart --> fn_DetachEvent");
        logslog("[TSPCmd.js] f_JobStart --> 서버 연결 실패 (nRet : " + nRet + ")");

        alert("서버 연결 실패");
        return;
    }
    else{
        logslog("[TSPCmd.js] f_JobStart --> 서버 연결 성공");
    }
}

function CTI_LinkDown() {
    var nRet;

    nRet = UCTlpSP.LinkDown(1);	// 서버 연결 해제
    rec_Logout();	//녹취서버 로그아웃

    if (nRet <= 0) {
        logslog("[TSPCmd.js] CTI_CmdLogOut --> 서버 연결 해제 실패............. errno : " + nRet);
        alert("서버 연결 해제 실패");
        return;
    } else {
        // 이벤트 연결 해제
        fn_DetachEvent();
        logslog("[TSPCmd.js] CTI_CmdLogOut --> fn_DetachEvent.............");
        logslog("[TSPCmd.js] CTI_CmdLogOut --> 서버 연결 해제 성공.............rqstId[" + nRet + "]");
        return;
    }
}

function CTI_CmdLogIn()
{
    var nRet;

    nRet = UCTlpSP.CmdLogIn("로그인");
    if(nRet <= 0){
        logslog("[TSPCmd.js] CTI_CmdLogIn --> 로그인 요청 실패............. errno[" + nRet + "]");
        alert("로그인 요청 실패 : errno[" + nRet + "]");
    }
    else{
        f_AfterCallWork();
        logslog("[TSPCmd.js] CTI_CmdLogIn --> 로그인 요청 성공............. rqstId[" + nRet + "]");
    }
}

function CTI_CmdLogOut()
{
    var nRet;

    nRet = UCTlpSP.CmdLogOut("로그아웃");
    if(nRet <= 0){
        logslog("[TSPCmd.js] CTI_CmdLogOut --> 로그아웃 요청 실패............. errno[" + nRet + "]");
        alert("로그아웃 요청 실패 : errno[" + nRet + "]");
    }
    else{
        logslog("[TSPCmd.js] CTI_CmdLogOut --> 로그아웃 요청 성공............. rqstId[" + nRet + "]");
    }
}

function CTI_CmdAvail()
{
    var nRet;

    nRet = UCTlpSP.CmdWorkModeChange(TSP_WORKMODE_AVAIL, 0, "업무상태변경(대기)");
    if(nRet <= 0){
        logslog("[TSPCmd.js] CTI_CmdAvail --> 업무상태변경(대기) 요청 실패............. errno[" + nRet + "]");
        alert("대기 요청 실패 : errno[" + nRet + "]");
    }
    else{
        logslog("[TSPCmd.js] CTI_CmdAvail --> 업무상태변경(대기) 요청 성공............. rqstId[" + nRet + "]");
    }
}

function CTI_CmdAfterCallWork()
{
    var nRet;

    nRet = UCTlpSP.CmdWorkModeChange(TSP_WORKMODE_ACW, VAL_WORKMODE_WORK, "업무상태변경(후처리)");
    if(nRet <= 0){
        logslog("[TSPCmd.js] CTI_CmdAfterCallWork --> 업무상태변경(후처리) 요청 실패............. errno[" + nRet + "]");
        alert("후처리 요청 실패 : errno[" + nRet + "]");
    }
    else{
        logslog("[TSPCmd.js] CTI_CmdAfterCallWork --> 업무상태변경(후처리) 요청 성공............. rqstId[" + nRet + "]");
    }
}

function CTI_CmdUnavail(rescode) {
    var nRet;
    var workmode = "";
    var secworkmode = "";
    var workstate = "";

    switch(rescode) {
        case "1":    // 업무
            workmode = TSP_WORKMODE_ACW;
            secworkmode = VAL_WORKMODE_WORK1;
            workstate = "업무상태변경(업무)";
            break;
        case "2":    // 식사
            workmode = TSP_WORKMODE_UNAVAIL;
            secworkmode = VAL_WORKMODE_UNAVAIL1;
            workstate = "업무상태변경(식사)";
            break;
        case "3":    // 휴식
            workmode = TSP_WORKMODE_UNAVAIL;
            secworkmode = VAL_WORKMODE_UNAVAIL;
            workstate = "업무상태변경(휴식)";
            break;
        case "4":    // 교육
            workmode = TSP_WORKMODE_ACW;
            secworkmode = VAL_WORKMODE_WORK2;
            workstate = "업무상태변경(교육)";
            break;
        case "5":    // 기타
            workmode = TSP_WORKMODE_UNAVAIL;
            secworkmode = VAL_WORKMODE_UNAVAIL2;
            workstate = "업무상태변경(기타)";
            break;
        default :
            workmode = TSP_WORKMODE_ACW;
            secworkmode = VAL_WORKMODE_WORK;
            workstate = "업무상태변경(후처리)";
    }

    nRet = UCTlpSP.CmdWorkModeChange(workmode, secworkmode, workstate);
    logslog("[TSPCmd.js] CTI_CmdUnavail --> CmdWorkModeChange : workmode(" + workmode + "), secworkmode(" + secworkmode + "), workstate(" + workstate + ")");

    if (nRet <= 0) {
        logslog("[TSPCmd.js] CTI_CmdUnavail --> 휴식 요청 실패............. errno[" + nRet + "]");
        alert("휴식 요청 실패 : errno[" + nRet + "]");
    } else {
        logslog("[TSPCmd.js] CTI_CmdUnavail --> 휴식 요청 성공............. rqstId[" + nRet + "]");
    }
}

function CTI_CmdMake(telno)
{
    var nRet;
    nRet = UCTlpSP.CmdMake(telno, "전화걸기");
    if(nRet <= 0){
        logslog("[TSPCmd.js] CTI_CmdMake --> 전화걸기 요청 실패............. errno[" + nRet + "]");
        alert("전화걸기 요청 실패 : errno[" + nRet + "]");
    }
    else{
        logslog("[TSPCmd.js] CTI_CmdMake --> 전화걸기 요청 성공............. rqstId[" + nRet + "]");
    }
}

function CTI_CmdAnswer()
{
    var nRet;

    nRet = UCTlpSP.CmdAnswer("전화받기");
    if(nRet <= 0){
        logslog("[TSPCmd.js] CTI_CmdAnswer --> 전화받기 요청 실패............. errno[" + nRet + "]");
        alert("전화받기 요청 실패 : errno[" + nRet + "]");
    }
    else{
        logslog("[TSPCmd.js] CTI_CmdAnswer --> 전화받기 요청 성공............. rqstId[" + nRet + "]");
    }
}

function CTI_CmdDisconnect()
{
    var nRet;

    nRet = UCTlpSP.CmdDisconnect("전화끊기");
    if(nRet <= 0){
        logslog("[TSPCmd.js] CTI_CmdDisconnect --> 전화끊기 요청 실패............. errno[" + nRet + "]");
        alert("전화끊기 요청 실패 : errno[" + nRet + "]");
    }
    else{
        logslog("[TSPCmd.js] CTI_CmdDisconnect --> 전화끊기 요청 성공............. rqstId[" + nRet + "]");
    }
}

function CTI_CmdHold()
{
    var nRet;

    nRet = UCTlpSP.CmdHold("보류");
    if(nRet <= 0){
        logslog("[TSPCmd.js] CTI_CmdHold --> 보류 요청 실패............. errno[" + nRet + "]");
        alert("보류 요청 실패 : errno[" + nRet + "]");
    }
    else{
        logslog("[TSPCmd.js] CTI_CmdHold --> 보류 요청 성공............. rqstId[" + nRet + "]");
    }
}

function CTI_CmdRetrieve()
{
    var nRet;

    nRet = UCTlpSP.CmdRetrieve("보류해제");
    if(nRet <= 0){
        logslog("[TSPCmd.js] CTI_CmdRetrieve --> 보류해제 요청 실패............. errno[" + nRet + "]");
        alert("보류해제 요청 실패 : errno[" + nRet + "]");
    }
    else{
        logslog("[TSPCmd.js] CTI_CmdRetrieve --> 보류해제 요청 성공............. rqstId[" + nRet + "]");
    }
}

var pop_open = null;
function CTI_CmdConsult(telno)
{
    var nRet = UCTlpSP.CmdConsult(telno, "호협의");
    logslog("[TSPCmd.js] CTI_CmdConsult --> telno : " + telno + ", nRet : "+ nRet + "]");
    if(nRet <  0){
        logslog("[TSPCmd.js] CTI_CmdConsult --> 잘못된 전화번호(통화중이거나 자기자신의 전화번호)............. errno[" + nRet + "]");
        alert("잘못된 전화번호입니다. (통화중이거나 자기자신의 전화번호): errno[" + nRet + "]");
    }
    else if(nRet ==  0){
        logslog("[TSPCmd.js] CTI_CmdConsult --> 전환시도 요청 실패............. errno[" + nRet + "]");
        alert("호협의 요청 실패 : errno[" + nRet + "]");
    }
    else{
        logslog("[TSPCmd.js] CTI_CmdConsult --> 전환시도 요청 성공............. rqstId[" + nRet + "]");
        CTI_GetConsultAcdgInfo();
        logslog("[TSPCmd.js] CTI_CmdConsult --> CTI_GetConsultAcdgInfo");
    }
}

function CTI_CmdReconnect()
{
    var nRet;

    nRet = UCTlpSP.CmdReconnect("호회수");
    if(nRet <= 0){
        logslog("[TSPCmd.js] CTI_CmdReconnect --> 호회수 요청 실패............. errno[" + nRet + "]");
        alert("호회수 요청 실패 : errno[" + nRet + "]");
    }
    else{
        logslog("[TSPCmd.js] CTI_CmdReconnect --> 호회수 요청 성공............. rqstId[" + nRet + "]");
    }
}

function CTI_CmdTransfer(telno)
{
    var nRet = UCTlpSP.CmdTransfer("호전환");
    logslog("[TSPCmd.js] CTI_CmdTransfer --> telno : " + telno + ", nRet : "+ nRet + "]");
    if(nRet <= 0){
        logslog("[TSPCmd.js] CTI_CmdTransfer --> 호전환 요청 실패............. errno[" + nRet + "]");
        alert("호전환 요청 실패 : errno[" + nRet + "]");
    }
    else{
        logslog("[TSPCmd.js] CTI_CmdTransfer --> 호전환 요청 성공............. rqstId[" + nRet + "]");

        if(telno.length < 5 && telno.charAt(0) != "9"){    // 내선호전환인 경우
            CTI_TransmitMessage(telno);
            logslog("[TSPCmd.js] CTI_CmdTransfer --> CTI_TransmitMessage");
        }
    }
}

function CTI_CmdConference()
{
    var nRet;

    nRet = UCTlpSP.CmdConference("회의통화");
    if(nRet <= 0){
        logslog("[TSPCmd.js] CTI_CmdConference --> 회의통화 요청 실패............. errno[" + nRet + "]");
        alert("회의통화 요청 실패 : errno[" + nRet + "]");
    }
    else{
        logslog("[TSPCmd.js] CTI_CmdConference --> 회의통화 요청 성공............. rqstId[" + nRet + "]");
    }
}

function CTI_CmdSingleStelTransfer(telno)
{
    if(telno == ""){
        logslog("[TSPCmd.js] CTI_CmdSingleStelTransfer --> 전화번호 없음 : telno[" + telno + "]");
        alert("SingleStepTransfer 할 전화번호를 입력 해주세요.");
        return;
    }

    var nRet = UCTlpSP.CmdSingleStepTransfer(telno, "SSXfer");
    logslog("[TSPCmd.js] CTI_CmdSingleStelTransfer --> telno : " + telno + ", nRet : "+ nRet + "]");
    if(nRet ==  0){
        logslog("[TSPCmd.js] CTI_CmdSingleStelTransfer --> SingleStepTransfer 요청 실패............. errno[" + nRet + "]");
        alert("SingleStepTransfer 요청 실패 : errno[" + nRet + "]");
    }
    else{
        logslog("[TSPCmd.js] CTI_CmdSingleStelTransfer --> SingleStepTransfer 요청 성공............. rqstId[" + nRet + "]");
    }
}




// 2010.08.30 by JIYONG PARK
var pop_open = null;
function CTI_GetConsultInfo()
{
    if(document.all.div_cti.style.display == "none")
    {
        CTI_GetConsultAcdgInfo();
        document.all.div_cti.style.display = "";
        document.cti.text.value = "";
    }
    else
    {
        document.all.div_cti.style.display = "none";
        document.cti.text.value = "";
    }
}


function CTI_GetConsultAcdgInfo()
{
    var ctiid = getValue("logoutForm", "ctiid");
    var nRet = UCTlpSP.GetConsultAcdgInfo(ctiid , "호협의ACDG");

    if(nRet <= 0){
        logslog("[TSPCmd.js] CTI_GetConsultAcdgInfo --> 전환시도ACDG 요청 실패............. errno[" + nRet + "], ctiid[" + ctiid + "]");
        alert("호협의 ACDG정보 요청 실패 : errno[" + nRet + "]");
    }
    else{
        logslog("[TSPCmd.js] CTI_GetConsultAcdgInfo --> 호협의ACDG 요청 성공............. rqstId[" + nRet + "], ctiid[" + ctiid + "]");
    }

    return "0";
}

function CTI_TransmitMessage(telno)
{
    logslog("[TSPCmd.js] CTI_TransmitMessage telno : " + telno);
    var nRet;
    var msg = "transCselgrpId=" + getValue("counselDetailForm", "cselidgrp");
    msg += "&transCselId=" + getValue("counselDetailForm", "cselid");

    nRet = UCTlpSP.TransmitMessage(telno, msg, "상담정보송신");
    if(nRet <= 0){
        logslog("[TSPCmd.js] CTI_TransmitMessage --> 상담정보송신 요청 실패............. errno[" + nRet + "]");
        alert("그룹 내 상담사 상태 요청 실패 : errno[" + nRet + "]");
        return;
    }
    else{
        logslog("[TSPCmd.js] CTI_TransmitMessage --> 상담정보송신 요청 성공............. rqstId[" + nRet + "]");
        return;
    }
}

function CTI_GetAgtPfmc()
{
    var ctiid = getValue("logoutForm", "ctiid");
    var nRet;

    nRet = UCTlpSP.GetAgtPfmc(ctiid, "통계정보");
    if(nRet <= 0){
        logslog("[TSPCmd.js] CTI_GetAgtPfmc --> 통계정보 요청 실패............. errno[" + nRet + "], ctiid[" + ctiid + "]");
        alert("통계정보 요청 실패 : errno[" + nRet + "]");
        return;
    }
    else{
//        logslog("[TSPCmd.js] CTI_GetAgtPfmc --> 통계정보 요청 성공............. rqstId[" + nRet + "], ctiid[" + ctiid + "]");
    }

    return;
}


// 호협의 정보 팝업 함수 ----------------------------------------------------------------------------------------------------

// 그룹 콤보박스 생성하는 메소드
var gg = new Array();
function creategroup()
{
    var groupid = document.cti.group;
    var i = 0;

    for(i = gg.length-1; i > 0; i--)
    {
        groupid.remove(i);
    }

    //groupid.selectedIndex = 0;
    gg = arrayAgent.split(";");

    for(i = 0; i<gg.length-1; i++)
    {
        var ginfo = gg[i].split(",");

        if(sel == ginfo[0])
        {
            groupid.options[i+1] = new Option(ginfo[1], ginfo[0], true, true);
        }
        else
        {
            groupid.options[i+1] = new Option(ginfo[1], ginfo[0]);
        }
    }
}

// 팀 콤보박스 생성하는 메소드
var tc = new Array();
var tcsel = "";
function createT_info()
{
    var team = document.cti.team;
    tcsel = document.cti.team.value;
    var i = 0;

    for(i = tc.length-1; i > 0; i--)
    {
        team.remove(i);
    }

    tc = tCampg.split(";");

    for(i = 0; i<tc.length-1; i++)
    {
        var tinfo = tc[i].split(",");

        if(tcsel == tinfo[0])
        {
            team.options[i+1] = new Option(tinfo[1], tinfo[0], true, true);
        }
        else
        {
            team.options[i+1] = new Option(tinfo[1], tinfo[0]);
        }
    }
}


// 상담자 목록 콤보박스 생성하는 메소드 ( multiple  )
var arrAcdgNo		= new Array();
var arrAgentId		= new Array();
var arrCampgNo		= new Array();
var arrCampgName	= new Array();
var arrStatus		= new Array();
var arrTelno		= new Array();
function createM_info()
{
    var list_id = document.cti.list_info;
    var list_innerHtml = "";
    var i = 0;
    var x = 0;

    arrAcdgNo		= AcdgNo.split(",");
    arrAgentId		= AgentId.split(",");
    arrCampgNo		= CampgNo.split(",");
    arrCampgName	= CampgName	.split(",");
    arrStatus		= Status.split(",");
    arrTelno		= Telno.split(",");

    list_innerHtml += "<table style=\"margin: 0px; padding: 0px;\" >";
    list_innerHtml += "<tr style=\"font-size : 11px; text-align: center; font-weight: bold; border: 1px;\">";
    list_innerHtml += "<td>AcdgNo</td>";
    list_innerHtml += "<td>AgentId</td>";
    list_innerHtml += "<td>CampgNo</td>";
    list_innerHtml += "<td>CampgName</td>";
    list_innerHtml += "<td>Status</td>";
    list_innerHtml += "<td>Telno</td>";
    list_innerHtml += "</tr>";


    for(i = 0; i<arrAcdgNo.length-1; i++)
    {
        var acno = arrAcdgNo[i];
        var agid = arrAgentId[i];
        var cano = arrCampgNo[i];
        var canm = arrCampgName[i];
        var stat = arrStatus[i];
        var teln = arrTelno[i];
        var statNm = "";

        if(tcsel == "" || tcsel == cano)
        {
            if(stat == 0) statNm = "Avail";
            else if(stat == 1) statNm = "Unavail";
            else if(stat == 2) statNm = "Work";
            else if(stat == 3) statNm = "Reserved";
            else if(stat == 4) statNm = "Ring";
            else if(stat == 5) statNm = "AcdIn";
            else if(stat == 6) statNm = "Nacd";
            else if(stat == 7) statNm = "Outgoing";
            else if(stat == 8) statNm = "Abnomal";

            list_innerHtml += "<tr style=\"cursor:hand; font-size: 13px; text-align: center;\" OnMouseOver=this.style.backgroundColor=\"#EBE9FF\"";
            list_innerHtml += "  OnMouseOut=this.style.backgroundColor=\"#FFFFFF\">";
            list_innerHtml += "<td onclick=\"on_Ichange(" + teln + ")\">" + acno + "</td>";
            list_innerHtml += "<td onclick=\"on_Ichange(" + teln + ")\">" + agid + "</td>";
            list_innerHtml += "<td onclick=\"on_Ichange(" + teln + ")\">" + cano + "</td>";
            list_innerHtml += "<td onclick=\"on_Ichange(" + teln + ")\">" + canm + "</td>";
            list_innerHtml += "<td onclick=\"on_Ichange(" + teln + ")\">" + statNm + "</td>";
            list_innerHtml += "<td onclick=\"on_Ichange(" + teln + ")\">" + teln + "</td>";
            list_innerHtml += "</tr>";
        }
    }

    list_innerHtml += "</table>";
    list_id.innerHTML = list_innerHtml;
}


//  상담사 상태 정보 값
//   Status : 0 - Avail,
//				   1 - Unavail
//				   2 - Work
//  	           3 - Reserved
//				   4 - Ring
//				   5 - AcdIn
//                6 - Nacd
//				   7 - Outgoing
//				   8 - Abnomal

// 호협의 정보 팝업 함수 ----------------------------------------------------------------------------------------------------