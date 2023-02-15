// Agent Work State
TSP_AGENT_STATE_LINKDOWN = 0;
TSP_AGENT_STATE_LINKUP = 1;
TSP_AGENT_STATE_LOGOUT = 2;
TSP_AGENT_STATE_LOGIN = 3;

TSP_RESP_TYPE_SUCCESS = "success";
TSP_RESP_TYPE_FAIL = "fail";

TSP_WORKMODE_AVAIL = "Avail";
TSP_WORKMODE_ACW = "AfterCallWork"; // 업무(후처리, 업무, 교육)
TSP_WORKMODE_UNAVAIL = "Unavail";   // 휴식(휴식, 식사, 기타)

// 2010.09.15 by JIYONG
TSP_WORKMODE_UNAVAIL_BREAKTIME =  "BreakTime";				  	//  1.휴식
TSP_WORKMODE_UNAVAIL_EDUCATION = "Education";					//  2.교육
TSP_WORKMODE_UNAVAIL_CONFERENCE = "Conference";			//  3.회의
TSP_WORKMODE_UNAVAIL_MEALTIME = "MealTime";						//  4.식사
TSP_WORKMODE_UNAVAIL_WORK = "Work";									//  5.작업
TSP_WORKMODE_UNAVAIL_LEAVE = "Leave";								//  6.이석
TSP_WORKMODE_UNAVAIL_GOOUT= "GoOut";								//  7.외출

// 2010.09.15 END

TSP_CALLSTATE_IDLE = 0;               // 호가 없는 상태
TSP_CALLSTATE_INIT = 1;               // 발신, 로그인 등의 기능을 위해 HookOff 된 상태
TSP_CALLSTATE_DIALING = 2;            // 발신 중인 상태
TSP_CALLSTATE_RINGING = 3;            // 착신 중인 상태
TSP_CALLSTATE_ESTABLISHED = 4;        // 호가 연결된 상태
TSP_CALLSTATE_HOLD = 5;               // 보류를 행한 상태
TSP_CALLSTATE_HELD = 6;               // 보류를 당한 상태
TSP_CALLSTATE_DISCONNECTED = 7;				// 통화가 종료되었지만 콜은 남아있는(HookOff) 상태 (뚜뚜뚜~)
TSP_CALLSTATE_CONFERENCED = 8;				// 회의통화

PBX_LINK_LGKPN_TAPI = 11;					// 11 : LG LDK-100 TAPI
PBX_LINK_TCP_IAP = 1;					    // 1 : SAMSUNG IAP
MAX_ARS_SERV   = 8;   								// 한 고객이 ARS Menu를 선택한 최대 Depth

//2010.09.29 by JIYONG
VAL_WORKMODE_UNAVAIL		=	"Unavail";     // 휴식
VAL_WORKMODE_UNAVAIL1		=	"Unavail1";    // 식사
VAL_WORKMODE_UNAVAIL2		=	"Unavail2";    // 기타
VAL_WORKMODE_UNAVAIL3		=	"Unavail3";
VAL_WORKMODE_UNAVAIL4		=	"Unavail4";
VAL_WORKMODE_UNAVAIL5		=	"Unavail5";
VAL_WORKMODE_UNAVAIL6		=	"Unavail6";
VAL_WORKMODE_UNAVAIL7		=	"Unavail7";
VAL_WORKMODE_UNAVAIL8		=	"Unavail8";
VAL_WORKMODE_UNAVAIL9		=	"Unavail9";

//20120731 INSU
VAL_WORKMODE_WORK		= "AfterCallWork";  	// 후처리
VAL_WORKMODE_WORK1		=	"AfterCallWork1";   // 업무
VAL_WORKMODE_WORK2		=	"AfterCallWork2";   // 교육
VAL_WORKMODE_WORK3		=	"AfterCallWork3";
VAL_WORKMODE_WORK4		=	"AfterCallWork4";
VAL_WORKMODE_WORK5		=	"AfterCallWork5";
VAL_WORKMODE_WORK6		=	"AfterCallWork6";
VAL_WORKMODE_WORK7		=	"AfterCallWork7";
VAL_WORKMODE_WORK8		=	"AfterCallWork8";
VAL_WORKMODE_WORK9		=	"AfterCallWork9";

//===== CTI Event ======================================================================================================
var isAttachEvent = false;

function fn_AttachEvent()
{
    if (isAttachEvent == true) return;

    UCTlpSP.attachEvent("EvtLinkUp", CTI_EvtLinkUp);
    UCTlpSP.attachEvent("EvtLinkDown", CTI_EvtLinkDown);
    UCTlpSP.attachEvent("EvtResp", CTI_EvtResp);
    UCTlpSP.attachEvent("EvtRinging", CTI_EvtRinging);
    UCTlpSP.attachEvent("EvtDialing", CTI_EvtDialing);
    UCTlpSP.attachEvent("EvtConnected", CTI_EvtConnected);
    UCTlpSP.attachEvent("EvtRejected", CTI_EvtRejected);
    UCTlpSP.attachEvent("EvtHold", CTI_EvtHold);
    UCTlpSP.attachEvent("EvtHeld", CTI_EvtHeld);
    UCTlpSP.attachEvent("EvtRetrieved", CTI_EvtRetrieved);
    UCTlpSP.attachEvent("EvtDisconnected", CTI_EvtDisconnected);
    UCTlpSP.attachEvent("EvtConnCleared", CTI_EvtConnCleared);
    UCTlpSP.attachEvent("EvtEvtCallInited", CTI_EvtCallInited);
    UCTlpSP.attachEvent("EvtAgentLogIn", CTI_EvtAgentLogIn);
    UCTlpSP.attachEvent("EvtAgentLogOut", CTI_EvtAgentLogOut);
    UCTlpSP.attachEvent("EvtAgentWorkModeChanged", CTI_EvtAgentWorkModeChanged);
    UCTlpSP.attachEvent("EvtXfered", CTI_EvtXfered);
    UCTlpSP.attachEvent("EvtXferCompleted", CTI_EvtXferCompleted);
    UCTlpSP.attachEvent("EvtConferenced", CTI_EvtConferenced);
    UCTlpSP.attachEvent("EvtUpdatedStatus", CTI_EvtUpdatedStatus);

    // 2010.08.30 by JIYONG PARK
    UCTlpSP.attachEvent("EvtConsultAcdgInfo", CTI_EvtConsultAcdgInfo);
    UCTlpSP.attachEvent("EvtConsultAgentInfo", CTI_EvtConsultAgentInfo);
    UCTlpSP.attachEvent("EvtPopup", CTI_EvtPopup);
    UCTlpSP.attachEvent("EvtTransmitMessage", CTI_EvtTransmitMessage);
    UCTlpSP.attachEvent("EvtReceiveMessage", CTI_EvtReceiveMessage);
    UCTlpSP.attachEvent("EvtAgtPfmc", CTI_EvtAgtPfmc);

    isAttachEvent = true;
}

function fn_DetachEvent()
{
    UCTlpSP.detachEvent("EvtLinkUp", CTI_EvtLinkUp);
    UCTlpSP.detachEvent("EvtLinkDown", CTI_EvtLinkDown);
    UCTlpSP.detachEvent("EvtResp", CTI_EvtResp);
    UCTlpSP.detachEvent("EvtRinging", CTI_EvtRinging);
    UCTlpSP.detachEvent("EvtDialing", CTI_EvtDialing);
    UCTlpSP.detachEvent("EvtConnected", CTI_EvtConnected);
    UCTlpSP.detachEvent("EvtRejected", CTI_EvtRejected);
    UCTlpSP.detachEvent("EvtHold", CTI_EvtHold);
    UCTlpSP.detachEvent("EvtHeld", CTI_EvtHeld);
    UCTlpSP.detachEvent("EvtRetrieved", CTI_EvtRetrieved);
    UCTlpSP.detachEvent("EvtDisconnected", CTI_EvtDisconnected);
    UCTlpSP.detachEvent("EvtEvtConnCleared", CTI_EvtConnCleared);
    UCTlpSP.detachEvent("EvtEvtCallInited", CTI_EvtCallInited);
    UCTlpSP.detachEvent("EvtAgentLogIn", CTI_EvtAgentLogIn);
    UCTlpSP.detachEvent("EvtAgentLogOut", CTI_EvtAgentLogOut);
    UCTlpSP.detachEvent("EvtAgentWorkModeChanged", CTI_EvtAgentWorkModeChanged);
    UCTlpSP.detachEvent("EvtXfered", CTI_EvtXfered);
    UCTlpSP.detachEvent("EvtXferCompleted", CTI_EvtXferCompleted);
    UCTlpSP.detachEvent("EvtConferenced", CTI_EvtConferenced);
    UCTlpSP.detachEvent("EvtUpdatedStatus", CTI_EvtUpdatedStatus);

    // 2010.08.30 by JIYONG PARK
    UCTlpSP.detachEvent("EvtConsultAcdgInfo", CTI_EvtConsultAcdgInfo);
    UCTlpSP.detachEvent("EvtConsultAgentInfo", CTI_EvtConsultAgentInfo);
    UCTlpSP.detachEvent("EvtPopup", CTI_EvtPopup);
    UCTlpSP.detachEvent("EvtTransmitMessage", CTI_EvtTransmitMessage);
    UCTlpSP.detachEvent("EvtReceiveMessage", CTI_EvtReceiveMessage);
    UCTlpSP.detachEvent("EvtAgtPfmc", CTI_EvtAgtPfmc);

    isAttachEvent = false;

}

function CTI_EvtLinkUp(reasonCd)
{
    logslog("[TSPEvt.js] CTI_EvtLinkUp reasonCd : " + reasonCd);

    if(reasonCd == 1){    // 사용자 Link Up
        CTI_CmdLogIn();
        logslog("[TSPEvt.js] CTI_EvtLinkUp --> CTI_CmdLogIn");
    }
    else{// 자동 Link Up
    }

    return;
}

function CTI_EvtLinkDown(reasonCd)
{
    logslog("[TSPEvt.js] CTI_EvtLinkDown reasonCd : " + reasonCd);

    if(reasonCd == 1)
        ;			// 사용자 Link Down
    else
        ;			// 자동 Link Down

    return;
}

function CTI_EvtResp(rqstId, respType, reasonCd, desc)
{
    logslog("[TSPEvt.js] CTI_EvtResp rqstId[" + rqstId + "], respType[" + respType + "], reasonCd[" + reasonCd + "], desc[" + desc + "]");

    if(respType == TSP_RESP_TYPE_FAIL){
        logslog("[TSPEvt.js] CTI_EvtResp -- > TSP_RESP_TYPE_FAIL reasonCd[" + reasonCd + " : " + desc + "]");

        if(reasonCd == "15"){    // 이미 로그인 중
            CTI_LinkDown();
            logslog("[TSPEvt.js] CTI_EvtResp --> CTI_LinkDown");
        }
        alert(desc + " 실패 (reason:" + reasonCd + ")");
    }

    return;
}

function CTI_EvtRinging(connId, destTelno)
{
    logslog("[TSPEvt.js] CTI_EvtRinging connId[" + connId + "], destTelno[" + destTelno + "]");

    CONNID = connId;
    return;
}

function CTI_EvtDialing(connId, destTelno)
{
    logslog("[TSPEvt.js] CTI_EvtDialing connId[" + connId + "], destTelno[" + destTelno + "]");

    return;
}

function CTI_EvtAgentLogOut(telno, ctiId, pilotTelno)
{
    logslog("status_msg = 로그아웃되었습니다 : telno = " + telno + ", ctiId = " + ctiId + ", pilotTelno = " + pilotTelno );
    setValue("ctiForm", "ctiloginyn", "N");

    goto_Logout();
    logslog("[TSPEvt.js] CTI_EvtAgentLogOut --> goto_Logout");

    rec_Logout();
    logslog("[TSPEvt.js] CTI_EvtAgentLogOut --> rec_Logout");

    CTI_LinkDown();
    logslog("[TSPEvt.js] CTI_EvtAgentLogOut --> CTI_LinkDown");
    return;
}

function CTI_EvtAgentLogIn(telno, ctiId, pilotTelno)
{
    logslog("status_msg = 로그인되었습니다 : telno = " + telno + ", ctiId = " + ctiId + ", pilotTelno = " + pilotTelno );

    setValue("ctiForm", "ctiloginyn", "Y");
    wait_search();
    logslog("[TSPEvt.js] CTI_EvtAgentLogIn --> wait_search");

    rec_Login();	// 녹취서버 로그인
    logslog("[TSPEvt.js] CTI_EvtAgentLogIn --> rec_Login");

    return;
}

function CTI_EvtAgentWorkModeChanged(workMode)
{
    logslog("[TSPEvt.js] CTI_EvtAgentWorkModeChanged workMode[" + workMode + "]");

    return;
}

function CTI_EvtCallInited(connId)
{
    logslog("[TSPEvt.js] CTI_EvtCallInited connId[" + connId + "]");

    return;
}

function CTI_EvtDisconnected(connId, destTelno)
{
    logslog("[TSPEvt.js] CTI_EvtDisconnected connId[" + connId + "], destTelno[" + destTelno + "]");

    return;
}

function CTI_EvtConnCleared(connId)
{
    logslog("[TSPEvt.js] CTI_EvtConnCleared connId[" + connId + "]");
    stopCselTime();
    f_AfterCallWork();

    return;
}

function CTI_EvtRetrieved(connId, reasonCd)
{
    logslog("[TSPEvt.js] CTI_EvtRetrieved connId[" + connId + "], reasonCd[" + reasonCd + "]");

    goto_UnHold();
    logslog("[TSPEvt.js] CTI_EvtHeld --> goto_calling");
    return;
}

function CTI_EvtHold(connId, reasonCd)
{
    logslog("[TSPEvt.js] CTI_EvtHold connId[" + connId + "], reasonCd[" + reasonCd + "]");

    goto_Hold();
    logslog("[TSPEvt.js] CTI_EvtHold --> goto_Hold");
    return;
}

function CTI_EvtHeld(connId, reasonCd)
{
    logslog("[TSPEvt.js] CTI_EvtHeld connId[" + connId + "], reasonCd[" + reasonCd + "]");

    return;
}

function CTI_EvtRejected(connId, destTelno)
{
    logslog("[TSPEvt.js] CTI_EvtRejected connId[" + connId + "], destTelno[" + destTelno + "]");

    return;
}

function CTI_EvtConnected(connId, destTelno)
{
    logslog("[TSPEvt.js] CTI_EvtConnected connId[" + connId + "], destTelno[" + destTelno + "]");

    startCselTime();
    setTimeout(function() {
        setValue("logoutForm", "recordid", rec_GetRecordID());
    }, "5000");

    goto_calling();
    logslog("[TSPEvt.js] CTI_EvtConnected --> goto_calling");

    return;
}

function CTI_EvtXfered(oldConnId, newConnId, destTelno, reasonCd)
{
    logslog("[TSPEvt.js] CTI_EvtXfered oldConnId[" + oldConnId + "], newConnId[" + newConnId + "], destTelno[" + destTelno + "], reasonCd[" + reasonCd + "]");

    return;
}

function CTI_EvtXferCompleted(heldConnId, activeConnId, reasonCd)
{
    logslog("[TSPEvt.js] CTI_EvtXferCompleted heldConnId[" + heldConnId + "], activeConnId[" + activeConnId + "], reasonCd[" + reasonCd + "]");

    return;
}

function CTI_EvtConferenced(oldConnId, newConnId, groupId, attendCnt, reasonCd)
{
    logslog("[TSPEvt.js] CTI_EvtConferenced oldConnId[" + oldConnId + "], newConnId[" + newConnId + "], groupId[" + groupId + "], attendCnt[" + attendCnt + "], reasonCd[" + reasonCd + "]");

    return;
}



function CTI_EvtUpdatedStatus()
{
    // 서버연결/로그인 상태에 대한 버튼변경
    var agtState = UCTlpSP.GetAgentState();

    if (agtState == TSP_AGENT_STATE_LINKDOWN) {
        logslog("[TSPEvt.js] CTI_EvtUpdatedStatus --> GetAgentState : 상태코드(" + agtState + " : TSP_AGENT_STATE_LINKDOWN)");
        return;
    } else if (agtState == TSP_AGENT_STATE_LINKUP) {
        logslog("[TSPEvt.js] CTI_EvtUpdatedStatus --> GetAgentState : 상태코드(" + agtState + " : TSP_AGENT_STATE_LINKUP)");
        return;
    } else if (agtState == TSP_AGENT_STATE_LOGOUT) {
        logslog("[TSPEvt.js] CTI_EvtUpdatedStatus --> GetAgentState : 상태코드(" + agtState + " : TSP_AGENT_STATE_LOGOUT)");
        return;
    } else if (agtState == TSP_AGENT_STATE_LOGIN) {
        logslog("[TSPEvt.js] CTI_EvtUpdatedStatus --> GetAgentState : 상태코드(" + agtState + " : TSP_AGENT_STATE_LOGIN)");
    } else {
        logslog("[TSPEvt.js] CTI_EvtUpdatedStatus --> GetAgentState : 상태코드(" + agtState + " : 알 수 없음)");
        alert("알 수 없는 상담사 상태입니다 : 상태코드(" + agtState + ")");
        return;
    }

    // 현재 연결된 콜 갯수에 따라 버튼 변경
    var callCnt = UCTlpSP.GetCallCnt();
    logslog("[TSPEvt.js] CTI_EvtUpdatedStatus --> GetCallCnt : 현재 연결된 콜 개수(" + callCnt + ")");

    if (callCnt == 0) {
        // 통화중인 콜이 없음 (상담사 업무모드에 따라 업데이트)
        var workMode = UCTlpSP.GetAgtWorkMode();
        var workModeComment = "";

        if (workMode == TSP_WORKMODE_AVAIL){    // 대기
            workModeComment = "TSP_WORKMODE_AVAIL";

            f_Clear(); //데이터 초기화
            logslog("[TSPEvt.js] CTI_EvtUpdatedStatus --> f_Clear");

            goto_ready();
            logslog("[TSPEvt.js] CTI_EvtUpdatedStatus --> goto_ready");
        } else if (workMode == VAL_WORKMODE_WORK){    // 후처리
            workModeComment = "VAL_WORKMODE_WORK";
            goto_afterwork();

            logslog("[TSPEvt.js] CTI_EvtUpdatedStatus --> goto_afterwork() : 후처리중");
        } else if(workMode == VAL_WORKMODE_WORK1){    // 업무
            workModeComment = "VAL_WORKMODE_WORK1";
            goto_notready("1");

            logslog("[TSPEvt.js] CTI_EvtUpdatedStatus --> goto_notready(1) : 업무중");
        } else if(workMode == VAL_WORKMODE_WORK2){    // 교육
            workModeComment = "VAL_WORKMODE_WORK2";
            goto_notready("4");

            logslog("[TSPEvt.js] CTI_EvtUpdatedStatus --> goto_notready(4) : 교육중");
        } else if(workMode == VAL_WORKMODE_UNAVAIL){    // 휴식
            workModeComment = "VAL_WORKMODE_UNAVAIL";
            goto_notready("3");

            logslog("[TSPEvt.js] CTI_EvtUpdatedStatus --> goto_notready(3) : 휴식중");
        } else if(workMode == VAL_WORKMODE_UNAVAIL1){    // 식사
            workModeComment = "VAL_WORKMODE_UNAVAIL1";
            goto_notready("2");

            logslog("[TSPEvt.js] CTI_EvtUpdatedStatus --> goto_notready(2) : 식사중");
        } else if(workMode == VAL_WORKMODE_UNAVAIL2){    // 기타
            workModeComment = "VAL_WORKMODE_UNAVAIL2";
            goto_notready("5");

            logslog("[TSPEvt.js] CTI_EvtUpdatedStatus --> goto_notready(5) : 기타");
        } else {
            workModeComment = "알 수 없음.";
            alert("알 수 없는 상담사 업무모드입니다 : 업무모드(" + workMode + ")");
        }
        logslog("[TSPEvt.js] CTI_EvtUpdatedStatus --> GetAgtWorkMode : 업무모드(" + workMode + ":" + workModeComment + ")");
    }
    else if(callCnt == 1){
        // 1:1 통화중인 경우 (콜상태에 따라 업데이트)
        var callState = UCTlpSP.GetCallState(0);
        var callStateComment = "";

        if(callState == TSP_CALLSTATE_IDLE){
            callStateComment = "잘못된 콜 상태";
            alert("잘못된 콜의 상태입니다 : 콜파티(" + (callCnt - 1) + "), 콜상태(" + callState + ")");
        }
        else if(callState == TSP_CALLSTATE_INIT){
            callStateComment = "TSP_CALLSTATE_INIT";
        }
        else if(callState == TSP_CALLSTATE_DIALING){
            callStateComment = "TSP_CALLSTATE_DIALING";
        }
        else if(callState == TSP_CALLSTATE_RINGING){
            callStateComment = "TSP_CALLSTATE_RINGING";
        }
        else if(callState == TSP_CALLSTATE_ESTABLISHED){
            callStateComment = "TSP_CALLSTATE_ESTABLISHED";
        }
        else if(callState == TSP_CALLSTATE_HOLD){
            callStateComment = "TSP_CALLSTATE_HOLD";
        }
        else if(callState == TSP_CALLSTATE_HELD){
            callStateComment = "TSP_CALLSTATE_HELD";
        }
        else if(callState == TSP_CALLSTATE_DISCONNECTED){
            callStateComment = "TSP_CALLSTATE_DISCONNECTED";
        }
        else if(callState == TSP_CALLSTATE_CONFERENCED){
            callStateComment = "TSP_CALLSTATE_CONFERENCED";
        }
        else{
            callStateComment = "알 수 없음";
            alert("잘못된 콜의 상태입니다 : 콜파티갯수(" + (callCnt) + "), 콜상태(" + callState + ")");
        }

        logslog("[TSPEvt.js] CTI_EvtUpdatedStatus --> GetCallState : 1:1 통화중인 경우 - 콜파티갯수(" + (callState == TSP_CALLSTATE_IDLE ? callCnt - 1 : callCnt) + "), 콜상태(" + callState + " : " + callStateComment + ")");
    }
    else if(callCnt == 2){
        var callState0 = UCTlpSP.GetCallState(0);
        var callState1 = UCTlpSP.GetCallState(1);
        var callState1Comment = "";

        if((callState0 != TSP_CALLSTATE_HOLD) && (callState0 != TSP_CALLSTATE_DISCONNECTED) && (callState1 <= TSP_CALLSTATE_IDLE) )
        {
            alert("잘못된 콜의 상태입니다 : 콜파티갯수(" + (callCnt) + "), 콜상태(" + callState0 + ", " + callState1 + ")");
        }

        if(callState1 == TSP_CALLSTATE_INIT){
            callState1Comment = "TSP_CALLSTATE_INIT";
        }
        else if(callState1 == TSP_CALLSTATE_DIALING){
            callState1Comment = "TSP_CALLSTATE_DIALING";
        }
        else if(callState1 == TSP_CALLSTATE_ESTABLISHED){
            callState1Comment = "TSP_CALLSTATE_ESTABLISHED";
        }
        else if(callState1 == TSP_CALLSTATE_HELD){
            callState1Comment = "TSP_CALLSTATE_HELD";
        }
        else if(callState1 == TSP_CALLSTATE_DISCONNECTED){
            callState1Comment = "TSP_CALLSTATE_DISCONNECTED";
        }
        else{
            alert("잘못된 콜의 상태입니다 : 콜파티(" + (callCnt - 1) + "), 콜상태(" + callState0 + ", " + callState1 + ")");
        }

        logslog("[TSPEvt.js] CTI_EvtUpdatedStatus --> GetCallState : 콜파티(" + (callCnt - 1) + "), 콜상태(" + callState1 + " : " + callState1Comment + ")");
    }
    else{
        logslog("[TSPEvt.js] 경고 : 잘못된 콜의 상태입니다 : 콜파티(" + (callCnt - 1) + ")");
        alert("경고 : 잘못된 콜의 상태입니다 : 콜파티(" + (callCnt - 1) + ")");
    }
}



// 2010.08.30 by JIYONG PARK

// -- 상담사 정보를 저장하기 위한 멤버변수
var AcdgNo		= "";
var AgentId		= "";
var CampgNo		= "";
var CampgName	= "";
var Status		= "";
var Telno		= "";
var teamcnt		= "";
// -- 상담사 정보를 저장하기 위한 멤버변수 끝.

// -- 팀 정보를 저장하기 위한 멤버변수
var tCampg	= "";
// -- 팀 정보를 저장하기 위한 멤버변수 끝.
function CTI_EvtConsultAgentInfo()
{
    // 팀 정보 -------------------------------------------------------------------------------------------------------
    var	campgCnt = UCTlpSP.GetConsultCampgCnt();
    teamcnt = campgCnt;
    var i;

    tCampg = "";
    for (i=0; i<campgCnt; i++)
    {
        tCampg += UCTlpSP.GetCampgNoOfCampgInfo(i) + "," + UCTlpSP.GetCampgNameOfCampgInfo(i) + ";";
    }

    createT_info();

    // 상담사 정보 -------------------------------------------------------------------------------------------------
    AcdgNo		= "";
    AgentId		= "";
    CampgNo		= "";
    CampgName	= "";
    Status		= "";
    Telno		= "";

    var agentCnt = UCTlpSP.GetConsultAgentCnt();

    var i;
    for (i=0; i<agentCnt; i++)
    {
        AcdgNo	+= UCTlpSP.GetAcdgNoOfAgentInfo(i) + ",";
        AgentId	+= UCTlpSP.GetAgentIdOfAgentInfo(i) + ",";
        CampgNo += UCTlpSP.GetCampgNoOfAgentInfo(i) + ",";
        CampgName += UCTlpSP.GetCampgNameOfAgentInfo(i) + ",";
        Status += UCTlpSP.GetStatusOfAgentInfo(i) + ",";
        Telno += UCTlpSP.GetAgentTelOfAgentInfo(i) + ",";
    }

    createM_info();

    return;
}

var arrayAgent = "";
var myacdgno = "";
var isAcdgInfo =0;
var isMyAcdgNo =0;
var sel = "";
function CTI_EvtConsultAcdgInfo()
{
    // 그룹정보 출력
    var	acdgCnt = UCTlpSP.GetConsultAcdgCnt();

    arrayAgent = "";

    var i;
    for (i=0; i<acdgCnt; i++)
    {
        arrayAgent += UCTlpSP.GetAcdgNoOfAcdgInfo(i) + "," + UCTlpSP.GetAcdgNameOfAcdgInfo(i) + ";";
    }

    // 로그인한 상담사 자신의 그룹번호 가져오기
    myacdgno = "";
    myacdgno = UCTlpSP.GetConsultMyAcdgNo();

    sel = myacdgno;
    creategroup();

    var nRet;
    nRet = UCTlpSP.GetConsultAgentInfo(myacdgno, "호협의myAGENT");

    if(nRet <= 0){
        alert("호협의 myAGENT정보 요청 실패 : errno[" + nRet + "]");
    }
    else{
        fn_Log("호협의정보 myAGENT요청 성공 : rqstId[" + nRet + "]" );
    }

    return;

}

function CTI_EvtPopup()
{
    /* GetPopupKind() - callType
     * 35 : 인바운드(IVR)
     * 36 : 아웃바운드(국선발신)
     * 37 : 내선착신
     * 38 : 호협의착신
     * 이외 : UNKNOWN
     * */

    var callType     = UCTlpSP.GetPopupKind()        ;
    var custTelno    = UCTlpSP.GetPopupCustTelno()   ;
    var custId       = UCTlpSP.GetPopupCustId()      ;
    var custName   	 = UCTlpSP.GetPopupCustName()    ;
    var custInfo0    = UCTlpSP.GetPopupCustInfo0()   ;
    var custInfo1    = UCTlpSP.GetPopupCustInfo1()   ;
    var campJobId    = UCTlpSP.GetPopupCampJobId()   ;
    var campJobSubId = UCTlpSP.GetPopupCampJobSubId();
    var taskId       = UCTlpSP.GetPopupTaskId()      ;
    var preAgtTelno  = UCTlpSP.GetPopupPreAgtTelno() ;
    var queWaitTime  = UCTlpSP.GetPopupQueWaitTime() ;
    var queWaitCnt   = UCTlpSP.GetPopupQueWaitCnt()  ;
    var queWaitStep  = UCTlpSP.GetPopupQueWaitStep() ;
    var queAbndCnt   = UCTlpSP.GetPopupQueAbndCnt()  ;
    var outboundId   = UCTlpSP.GetPopupOutboundId()  ;
    var ticketId     = UCTlpSP.GetPopupTicketId()    ;
    var whereDial    = UCTlpSP.GetPopupWhereDial()   ;
    var dnis         = UCTlpSP.GetPopupDnis()        ;
    GUBUN            = UCTlpSP.GetPopupArsServKind(0);    // IVR서비스구분

    f_CTIRingingHandler(custTelno, callType, "");
    logslog("[TSPEvt.js] CTI_EvtPopup --> f_CTIRingingHandler custTelNo : " + custTelno + ", callType : " + callType);

    if(callType == "36"){
        goto_dialing();
        logslog("[TSPEvt.js] CTI_EvtPopup --> goto_dialing");
    }else {
        goto_incoming();
        logslog("[TSPEvt.js] CTI_EvtPopup --> goto_incoming");
    }

    logslog("[TSPEvt.js] CTI_EvtPopup --> UCTlpSP.GetPopupKind()         : " + callType    );
    logslog("[TSPEvt.js] CTI_EvtPopup --> UCTlpSP.GetPopupCustTelno()    : " + custTelno   );
    logslog("[TSPEvt.js] CTI_EvtPopup --> UCTlpSP.GetPopupCustId()       : " + custId      );
    logslog("[TSPEvt.js] CTI_EvtPopup --> UCTlpSP.GetPopupCustName()     : " + custName    );
    logslog("[TSPEvt.js] CTI_EvtPopup --> UCTlpSP.GetPopupCustInfo0()    : " + custInfo0   );
    logslog("[TSPEvt.js] CTI_EvtPopup --> UCTlpSP.GetPopupCustInfo1()    : " + custInfo1   );
    logslog("[TSPEvt.js] CTI_EvtPopup --> UCTlpSP.GetPopupCampJobId()    : " + campJobId   );
    logslog("[TSPEvt.js] CTI_EvtPopup --> UCTlpSP.GetPopupCampJobSubId() : " + campJobSubId);
    logslog("[TSPEvt.js] CTI_EvtPopup --> UCTlpSP.GetPopupTaskId()       : " + taskId      );
    logslog("[TSPEvt.js] CTI_EvtPopup --> UCTlpSP.GetPopupPreAgtTelno()  : " + preAgtTelno );
    logslog("[TSPEvt.js] CTI_EvtPopup --> UCTlpSP.GetPopupQueWaitTime()  : " + queWaitTime );
    logslog("[TSPEvt.js] CTI_EvtPopup --> UCTlpSP.GetPopupQueWaitCnt()   : " + queWaitCnt  );
    logslog("[TSPEvt.js] CTI_EvtPopup --> UCTlpSP.GetPopupQueWaitStep()  : " + queWaitStep );
    logslog("[TSPEvt.js] CTI_EvtPopup --> UCTlpSP.GetPopupQueAbndCnt()   : " + queAbndCnt  );
    logslog("[TSPEvt.js] CTI_EvtPopup --> UCTlpSP.GetPopupOutboundId()   : " + outboundId  );
    logslog("[TSPEvt.js] CTI_EvtPopup --> UCTlpSP.GetPopupTicketId()     : " + ticketId    );
    logslog("[TSPEvt.js] CTI_EvtPopup --> UCTlpSP.GetPopupWhereDial()    : " + whereDial   );
    logslog("[TSPEvt.js] CTI_EvtPopup --> UCTlpSP.GetPopupDnis()         : " + dnis        );
    logslog("[TSPEvt.js] CTI_EvtPopup --> UCTlpSP.GetPopupArsServKind(0) : " + GUBUN  );

    return;
}
// 2010.09.05 END

function CTI_EvtTransmitMessage(respType, reasonCd)
{
    /*respType : 성공여부
    reasonCd : 사유*/

    logslog("[TSPEvt.js] CTI_EvtTransmitMessage respType(" + respType + "), reasonCd(" + reasonCd + ")");

// 원하는 작업을 하면 됩니다.
}

function CTI_EvtReceiveMessage(srcTelno, destTelno, recvMessage)
{
    /*srcTelno : 쪽지 송신측 전화번호
    destTelno : 쪽지 수신측 전화번호
    recvMessage : 쪽지내용*/

    logslog("[TSPEvt.js] CTI_EvtReceiveMessage srcTelno(" + srcTelno + "), destTelno(" + destTelno + "), recvMessage(" + recvMessage + ")");

    var splitdata = recvMessage.split("&");
    f_UDATAParser(splitdata, "=");

    counsel_view(CSELGRPID, CSELID);
}

function CTI_EvtAgtPfmc()
{
    var waitCallAcdgSvcSts = UCTlpSP.GetWaitCallAcdgSvcSts();    // 대기고객수
//    logslog("[TSPEvt.js] CTI_EvtAgtPfmc --> UCTlpSP.GetWaitCallAcdgSvcSts() 대기고객수   : " + waitCallAcdgSvcSts);
    var availAgtAcdgSvcSts = UCTlpSP.GetAvailAgtAcdgSvcSts();      // 대기상담사수
//    logslog("[TSPEvt.js] CTI_EvtAgtPfmc --> UCTlpSP.GetAvailAgtAcdgSvcSts()  대기상담사수 : " + availAgtAcdgSvcSts);

    $("#waitcustcnt").text(waitCallAcdgSvcSts);
    $("#waitagtcnt").text(availAgtAcdgSvcSts);

    if(waitCallAcdgSvcSts > 0) $("#waitcustcnt").css("color", "red");
    else $("#waitcustcnt").css("color", "");

    if(availAgtAcdgSvcSts == 0) $("#waitagtcnt").css("color", "red");
    else $("#waitagtcnt").css("color", "");
}

/***** 추가 *****/
//*-----------------------------------------------------------------------------
//* 화면 ID  : TSPEvt.JS
//* 화면명   : 소프트폰(AVAYA) 이벤트 핸들링 클래스
//* 작성자   : EIT-박준연(jypark@enovationit.com)
//*----------------------------------------------------------------------------
//* 이력사항
//*----------------------------------------------------------------------------
//2009, 04, 30 EIT-박준연 최초 구성
//*----------------------------------------------------------------------------

var CONNID = "";
var DNIS = "";
var ANI = "";
var GUBUN = "";
var CSELGRPID = "";
var CSELID = "";
var GROUP = "";
var CENTER = "";
//--------------------------------------------------------------------------------------------------
//1. 이벤트 에러 핸들링
//--------------------------------------------------------------------------------------------------
//==================================================================================================
//함수 : f_CTIErrorHandler
//인자 : ERRCD - 에러코드, ERRMSG - 에러메세지
//반환 :
//==================================================================================================
function f_CTIErrorHandler(ERRCD, ERRMSG)
{
  try
  {
      logslog("[TSPEvt.js] f_CTIErrorHandler ERRCD : " + ERRCD + ", ERRMSG : " + ERRMSG);
      //alert("ERRCD : " + ERRCD + ", ERRMSG : " + ERRMSG);//에러메세지 표기
  }
  catch(err)
  {
      //alert("f_CTIErrorHandler() :" + err.description);
  }
}

//--------------------------------------------------------------------------------------------------
//2. 전화 인입시 고객정보 처리
//--------------------------------------------------------------------------------------------------
//==================================================================================================
//함수 : f_CTIRingingHandler
//인자 : ANI - 인입전화번호, UDATA - 유저데이터
//반환 :
//==================================================================================================
function f_CTIRingingHandler(ANI, CALLTYPE, UDATA)
{
  try
  {
      logslog("[TSPEvt.js] f_CTIRingingHandler START");
      logslog("[TSPEvt.js] f_CTIRingingHandler ANI : " + ANI);
      logslog("[TSPEvt.js] f_CTIRingingHandler CALLTYPE : " + CALLTYPE);
      logslog("[TSPEvt.js] f_CTIRingingHandler UDATA : " + UDATA);
      //유저데이터 파싱하기
      f_UDATA(ANI, UDATA);
      f_RingingAction(CALLTYPE);
     // alert (UDATA);
     logslog("[TSPEvt.js] f_CTIRingingHandler END");
  }
  catch(err)
  {
      alert("f_CTIRingingHandler() :" + err.description);
  }
}

function  f_CTIEstablishHandler(ANI, CALLTYPE, UDATA)
{
  try
  {
      logslog("[TSPEvt.js] f_CTIEstablishHandler START");
      logslog("[TSPEvt.js] f_CTIEstablishHandler ANI : " + ANI);
      logslog("[TSPEvt.js] f_CTIEstablishHandler CALLTYPE : " + CALLTYPE);
      logslog("[TSPEvt.js] f_CTIEstablishHandler UDATA : " + UDATA);
      if(CALLTYPE == "36") {
          //유저데이터 파싱하기
          f_UDATA(ANI, UDATA);
          f_RingingAction(CALLTYPE);
      }
      logslog("[TSPEvt.js] f_CTIEstablishHandler END");
     // alert (UDATA);
  }
  catch(err)
  {
      alert("f_CTIEstablishHandler() :" + err.description);
  }
}

//--------------------------------------------------------------------------------------------------
//4. 전화벨이 울렸을때의 액션
//--------------------------------------------------------------------------------------------------
//==================================================================================================
//함수 : f_RingingAction
//인자 : CALLTYPE - 콜타입
//반환 :
//==================================================================================================
function f_RingingAction(CALLTYPE)
{
  try
  {
      logslog("[TSPEvt.js] f_RingingAction START");
      logslog("[TSPEvt.js] f_RingingAction CALLTYPE => 35(인바운드(IVR)), 36(아웃바운드(국선발신)), 37(내선착신), 38(호협의착신), 이외(UNKNOWN)");
      logslog("[TSPEvt.js] f_RingingAction CALLTYPE : " + CALLTYPE);

      v_callType = "";

      if(CALLTYPE == "35") v_callType = "[인바운드(IVR)] 전화가 왔습니다.";
      else if(CALLTYPE == "36") v_callType = "[아웃바운드(국선발신)] 전화가 연결되었습니다.";
      else if(CALLTYPE == "37") v_callType = "[내선] 전화가 왔습니다.";
      else if(CALLTYPE == "38") v_callType = "[전환시도] 전화가 왔습니다.";
      else v_callType = "";

      document.logoutForm.dnis.value = DNIS;
      logslog("[TSPEvt.js] f_RingingAction CONNID : " + CONNID + ", ANI : " + ANI + ", CALLTYPE : " + CALLTYPE + ", CSELID : " + CSELID);
      logslog("[TSPEvt.js] f_RingingAction GROUP : " + GROUP + ", CENTER : " + CENTER + ", GUBUN : " + GUBUN);
      if (CALLTYPE == "3") {
          ANI = document.logoutForm.custtelno.value;
          document.logoutForm.custtelno.value = "";
      }
      cticallhist_save(CONNID, ANI, GUBUN, "", CALLTYPE, CSELID, GROUP, CENTER);
      logslog("[TSPEvt.js] f_RingingAction END");
  }
  catch(err)
  {
      alert("f_RingingAction() :" + err.description);
  }
}

//--------------------------------------------------------------------------------------------------
//4. 전화가 연결되었을 때 액션
//--------------------------------------------------------------------------------------------------
//==================================================================================================
//함수 : f_EstablishAction
//인자 :
//반환 :
//==================================================================================================
function f_EstablishAction()
{
  try
  {
      ;
  }
  catch(err)
  {
      alert("f_EstablishAction() :" + err.description);
  }
}

//--------------------------------------------------------------------------------------------------
//5. 초기화
//--------------------------------------------------------------------------------------------------
//==================================================================================================
//함수 : f_Clear
//인자 :
//반환 :
//==================================================================================================
function f_Clear()
{
  try
  {
      ; //유저데이터 초기화 및 화면기초 정보 초기화
  }
  catch(err)
  {
      alert("f_Clear() :" + err.description);
  }
}


//--------------------------------------------------------------------------------------------------
//6. 이벤트에서 데이터 받아와 파싱시작
//--------------------------------------------------------------------------------------------------
//==================================================================================================
//함수 :f_UDATA
//인자 : v_aninum - 인입번호, v_userdata - 유저데이터
//반환 :
//==================================================================================================
function f_UDATA(v_aninum, v_userdata)
{
  try
  {
      logslog("[TSPEvt.js] f_UDATA START");
      logslog("[TSPEvt.js] f_UDATA v_aninum : " + v_aninum);
      ANI = v_aninum;

      /*UCTlpSP.GetPopupKind();
      UCTlpSP.GetPopupCustTelno();
      UCTlpSP.GetPopupCustId();
      UCTlpSP.GetPopupCustName();
      UCTlpSP.GetPopupCustInfo0();
      UCTlpSP.GetPopupCustInfo1();
      UCTlpSP.GetPopupCampJobId();
      UCTlpSP.GetPopupCampJobSubId();
      UCTlpSP.GetPopupTaskId();
      UCTlpSP.GetPopupPreAgtTelno();
      UCTlpSP.GetPopupQueWaitTime();
      UCTlpSP.GetPopupQueWaitCnt();
      UCTlpSP.GetPopupQueWaitStep();
      UCTlpSP.GetPopupQueAbndCnt();
      UCTlpSP.GetPopupOutboundId();
      UCTlpSP.GetPopupTicketId();
      UCTlpSP.GetPopupWhereDial();*/
      DNIS = UCTlpSP.GetPopupDnis();

      logslog("[TSPEvt.js] f_UDATA END");

/* org
      logslog("[TSPEvt.js] f_UDATA START");
      logslog("[TSPEvt.js] f_UDATA v_aninum : " + v_aninum);
      logslog("[TSPEvt.js] f_UDATA v_userdata : " + v_userdata);
      ANI = v_aninum;

      var splitdata = v_userdata.split("&");
      f_UDATAParser(splitdata, "=");
      logslog("[TSPEvt.js] f_UDATA END");*/
  }
  catch(err)
  {
      alert("f_UDATA() :" + err.description);
  }
}

//--------------------------------------------------------------------------------------------------
//7. USERDATA전문 파싱 시작
//--------------------------------------------------------------------------------------------------
//==================================================================================================
//함수 :f_UDATAParser
//인자 : splitdata - 유저데이터 배열, delimeter = 구분자
//반환 :
//==================================================================================================
function f_UDATAParser(splitdata, delimeter)
{
  try
  {
      var length = splitdata.length;
      var index = "";
      var key = "";
      var values = "";

      for( var i  =0 ; i < length ; i++ )
      {
          index = splitdata[i].indexOf( delimeter );
          key = splitdata[i].substring( 0 , index );
          values = splitdata[i].substr( index+1 );
          //trace( "key=[" + key + "] , value = [" + value + "]");
          f_UDATASet(key, values);
      }
  }
  catch(err)
  {
      alert("f_UDATAParser() :" + err.description);
  }
}

//--------------------------------------------------------------------------------------------------
//8. USERDATA변수 설정
//--------------------------------------------------------------------------------------------------
//==================================================================================================
//함수 :f_UDATASet
//인자 : key - 키 , value = 값
//반환 :
//==================================================================================================
function f_UDATASet(key, values)
{
  if( values == null || values == "UNDEFINED" || values == "undefined" ) values ="";

  try
  {
      logslog("[TSPEvt.js] f_UDATASet START");
      logslog("[TSPEvt.js] f_UDATASet KEY : " + key + ", VALUES : " + values);
      switch(key)
      {
          case "CONNID":
              CONNID = values;
          break;
          case "DNIS":
              DNIS = values;
          break;
          case "ANI":
              ANI = values;
          break;
          case "GUBUN" :
              GUBUN = values;
          break;
          case "transCselgrpId" :
              CSELGRPID = values;
          break;
          case "transCselId" :
              CSELID = values;
          break;
          case "groupFg" :
              GROUP = values;
          break;
          case "centercd" :
              CENTER = values;
          break;
      }
      logslog("[TSPEvt.js] f_UDATASet END");

  }
  catch(err)
  {
      alert("f_UDATASet() :" + err.description);
  }
}


function f_CTISetConId(connid)
{
  logslog("[TSPEvt.js] f_CTISetConId START");
  logslog("[TSPEvt.js] f_CTISetConId connid : " + connid);
  CONNID = connid;
  logslog("[TSPEvt.js] f_CTISetConId END");
}