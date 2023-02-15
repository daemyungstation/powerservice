//*-----------------------------------------------------------------------------
//* 화면 ID  : TSPCallControl.JS
//* 화면명   : 소프트폰(AVAYA) 리퀘스트 일반 함수
//* 작성자   : EIT-박준연(jypark@enovationit.com)
//*----------------------------------------------------------------------------
//* 이력사항
//*----------------------------------------------------------------------------
// 2009, 04, 30 EIT-박준연 최초 구성
//*----------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------
// 1. 전역변수 설정
//--------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------
// 2. CTI에 접속한다 업무시작
//--------------------------------------------------------------------------------------------------
//==================================================================================================
//함수 : f_JobStart
//인자 : agtid - AGENTID, extid - 내선번호
//반환 :
//==================================================================================================
function f_JobStart(agtid, extid)
{
    try
    {
        logslog("[TSPCallControl.js] f_JobStart START");

        CTI_LinkUp(agtid, extid);

        logslog("[TSPCallControl.js] f_JobStart END");
    }
    catch(err)
    {
        logslog("[TSPCallControl.js] f_JobStart ERR : " + err.description);
        alert("f_JobStart() :" + err.description);
    }
}

//--------------------------------------------------------------------------------------------------
// 3. CTI에 접속종료한다 업무끝
//--------------------------------------------------------------------------------------------------
//==================================================================================================
//함수 : f_JobStop
//인자 :
//반환 :
//==================================================================================================
function f_JobStop()
{
    try
    {
        logslog("[TSPCallControl.js] f_JobStop START");

        CTI_CmdLogOut();

        logslog("[TSPCallControl.js] f_JobStop END");
    }
    catch(err)
    {
        logslog("[TSPCallControl.js] f_JobStop ERR : " + err.description);
        alert("f_JobStop() :" + err.description);
    }
}

//--------------------------------------------------------------------------------------------------
// 4. 상담대기를 한다.
//--------------------------------------------------------------------------------------------------
//==================================================================================================
//함수 : f_Ready
//인자 :
//반환 :
//==================================================================================================
function f_Ready()
{
    try
    {
        logslog("[TSPCallControl.js] f_Ready START");
        CTI_CmdAvail();
        // 대기시 상담 초기화
        counsel_reg();
        customer_reg();
        logslog("[TSPCallControl.js] f_Ready END");
    }
    catch(err)
    {
        logslog("[TSPCallControl.js] f_Ready ERR : " + err.description);
        alert("f_Ready() :" + err.description);
    }
}

//--------------------------------------------------------------------------------------------------
// 5. 상담 휴식을 한다.
//--------------------------------------------------------------------------------------------------
//==================================================================================================
//함수 : f_NotReady
//인자 : rescode - 이석사유
//반환 :
//==================================================================================================
function f_NotReady(rescode)
{
    try
    {
        logslog("[TSPCallControl.js] f_NotReady START");
        logslog("[TSPCallControl.js] f_NotReady REASONCODE : " + rescode);
        CTI_CmdUnavail(rescode);
        logslog("[TSPCallControl.js] f_NotReady END");
    }
    catch(err)
    {
        logslog("[TSPCallControl.js] f_NotReady ERR : " + err.description);
        alert("f_NotReady() :" + err.description);
    }
}

//--------------------------------------------------------------------------------------------------
// 6. 통화후 작업상태로 간다.
//--------------------------------------------------------------------------------------------------
//==================================================================================================
//함수 : f_AfterCallWork
//인자 :
//반환 :
//==================================================================================================
function f_AfterCallWork()
{
    try
    {
        logslog("[TSPCallControl.js] f_AfterCallWork START");
        CTI_CmdAfterCallWork();
        logslog("[TSPCallControl.js] f_AfterCallWork END");
    }
    catch(err)
    {
        logslog("[TSPCallControl.js] f_AfterCallWork ERR : " + err.description);
        alert("f_AfterCallWork() :" + err.description);
    }
}

//--------------------------------------------------------------------------------------------------
// 7. 상대방에게 전화를 건다.
//--------------------------------------------------------------------------------------------------
//==================================================================================================
//함수 : f_Dial
//인자 : telno - 발신전화번호
//반환 :
//==================================================================================================
function f_Dial(telno, prefix)
{
    if(prefix == null && (telno.charAt(0) == "0" || telno.charAt(0) == "1")) prefix = "9";

    try
    {
        if(telno == "")
        {
            alert("발신 전화번호가 없습니다.");
            return;
        }
        logslog("[TSPCallControl.js] f_Dial START");
        document.logoutForm.custtelno.value = telno;
        telno = telno.telnbChk(prefix);
        logslog("[TSPCallControl.js] f_Dial DIALNO : " + telno);
        CTI_CmdMake(telno);
        logslog("[TSPCallControl.js] f_Dial END");
    }
    catch(err)
    {
        logslog("[TSPCallControl.js] f_Dial : ERR : " + err.description);
        alert("f_Dial() :" + err.description);
    }
}

//--------------------------------------------------------------------------------------------------
// 8. 현재 통화중인 전화를 끊는다.
//--------------------------------------------------------------------------------------------------
//==================================================================================================
//함수 : f_Release
//인자 :
//반환 :
//==================================================================================================
function f_Release()
{
    try
    {
        logslog("[TSPCallControl.js] f_Release START");
        CTI_CmdDisconnect();
        logslog("[TSPCallControl.js] f_Release END");
    }
    catch(err)
    {
        logslog("[TSPCallControl.js] f_Release : ERR : " + err.description);
        alert("f_Release() :" + err.description);
    }
}

//--------------------------------------------------------------------------------------------------
// 9. 현재 벨중인 전화를 받는다.
//--------------------------------------------------------------------------------------------------
//==================================================================================================
//함수 : f_Answer
//인자 :
//반환 :
//==================================================================================================
function f_Answer()
{
    try
    {
        logslog("[TSPCallControl.js] f_Answer START");
        var callCnt = UCTlpSP.GetCallCnt();
        if (callCnt == 0) {
            alert("고객이 이미 전화를 끊었습니다.");
            objCallPopup.close();
        } else {
            CTI_CmdAnswer();
        }
        logslog("[TSPCallControl.js] f_Answer END");
    }
    catch(err)
    {
        logslog("[TSPCallControl.js] f_Answer : ERR : " + err.description);
        alert("f_Answer() :" + err.description);
    }
}

//--------------------------------------------------------------------------------------------------
// 9.1 현재 통화중인 전화를 보류시킨다.
//--------------------------------------------------------------------------------------------------
//==================================================================================================
//함수 : f_Hold
//인자 :
//반환 :
//==================================================================================================
function fncHold_Click() {
    try{
        logslog("[TSPCallControl.js] fncHold_Click START");
        if(!g_isHolding){
            g_isHolding = true;
            f_Hold();
            document.all.btnHold.src = "images/bt_11.jpg";
        }else{
            g_isHolding = false;
            f_UnHold();
            document.all.btnHold.src = "images/bt_04.gif";
        }
        logslog("[TSPCallControl.js] fncHold_Click END");
    }catch(err){
        logslog("[TSPCallControl.js] fncHold_Cick : ERR : " + err.description);
        alert("fncHold_Cick() :" + err.description);
    }
}

//--------------------------------------------------------------------------------------------------
// 10. 현재 통화중인 전화를 보류시킨다.
//--------------------------------------------------------------------------------------------------
//==================================================================================================
//함수 : f_Hold
//인자 :
//반환 :
//==================================================================================================
function f_Hold()
{
    try
    {
        logslog("[TSPCallControl.js] f_Hold START");
        CTI_CmdHold();
        logslog("[TSPCallControl.js] f_Hold END");
    }
    catch(err)
    {
        logslog("[TSPCallControl.js] f_Hold : ERR : " + err.description);
        alert("f_Hold() :" + err.description);
    }
}

//--------------------------------------------------------------------------------------------------
// 11. 보류중인 전화를 해제한다.
//--------------------------------------------------------------------------------------------------
//==================================================================================================
//함수 : f_UnHold
//인자 :
//반환 :
//==================================================================================================
function f_UnHold()
{
    try
    {
        logslog("[TSPCallControl.js] f_UnHold START");
        CTI_CmdRetrieve();
        logslog("[TSPCallControl.js] f_UnHold END");
    }
    catch(err)
    {
        logslog("[TSPCallControl.js] f_UnHold : ERR : " + err.description);
        alert("f_UnHold() :" + err.description);
    }
}

//--------------------------------------------------------------------------------------------------
// 12. 2스텝으로 호전환 한다.
//--------------------------------------------------------------------------------------------------
//==================================================================================================
//함수 : f_TransferInit
//인자 : telno - 발신 전화번호
//반환 :
//==================================================================================================
function f_TransferInit(telno)
{
    try
    {
        logslog("[TSPCallControl.js] f_TransferInit START");
        logslog("[TSPCallControl.js] f_TransferInit TELNO : " + telno);
        if(telno == "")
        {
            alert("전환 할 전화번호가 없습니다.");
            return;
        }

        // 상담정보
        logslog("[TSPCallControl.js] f_TransferInit document.counselDetailForm.cselidgrp.value : " + document.counselDetailForm.cselidgrp.value);
        logslog("[TSPCallControl.js] f_TransferInit document.counselDetailForm.cselid.value : " + document.counselDetailForm.cselid.value);
        if (document.counselDetailForm.cselidgrp.value != document.counselDetailForm.cselid.value) {
            document.counselDetailForm.cselidgrp_old.value = document.counselDetailForm.cselidgrp.value;
            document.counselDetailForm.cselidgrp.value = document.counselDetailForm.cselid.value;
        }

        // 상담을 일단 저장하자고..
        goto_consulting_pop("init");
        logslog("[TSPCallControl.js] f_TransferInit --> goto_consulting_pop(init)");
        logslog("[TSPCallControl.js] f_TransferInit document.counselDetailForm.cselidgrp.value : " + document.counselDetailForm.cselidgrp.value);
        logslog("[TSPCallControl.js] f_TransferInit document.counselDetailForm.cselid.value : " + document.counselDetailForm.cselid.value);
//        counsel_save("");
        logslog("[TSPCallControl.js] f_TransferInit END");

        CTI_CmdConsult(telno);
        logslog("[TSPCallControl.js] f_TransferInit --> CTI_CmdConsult(" + telno + ")");
    }
    catch(err)
    {
        logslog("[TSPCallControl.js] f_TransferInit : ERR : " + err.description);
        alert("f_TransferInit() :" + err.description);
    }
}

//--------------------------------------------------------------------------------------------------
// 13. 1스텝으로 호전환 한다.
//--------------------------------------------------------------------------------------------------
//==================================================================================================
//함수 : f_TransferMute
//인자 : telno - 발신 전화번호
//반환 :
//==================================================================================================
function f_TransferMute(telno, type)
{
    try

    {
        logslog("[TSPCallControl.js] f_TransferMute START");
        logslog("[TSPCallControl.js] f_TransferMute TELNO : " + telno + ", TYPE : " + type);
        if(telno == "")
        {
            alert("전환 할 전화번호가 없습니다.");
            return;
        }
        if (type == "G") {
            document.o_CTI.TAttachUserData("groupFg", "Y");
            document.o_CTI.TAttachUserData("centercd", document.counselDetailForm.centercd.value);
        } else if (type == "A") {
            document.o_CTI.TAttachUserData("CSELID", document.counselDetailForm.cselid.value);
            document.o_CTI.TAttachUserData("SURVEY", "1");
            document.o_CTI.TAttachUserData("AGENTID", document.logoutForm.ctiid.value);
            document.o_CTI.TAttachUserData("ANI", document.counselDetailForm.anino.value);
            document.o_CTI.TAttachUserData("CONNID", document.counselDetailForm.connid.value);
        }
        document.o_CTI.TTransferMute(telno);
        logslog("[TSPCallControl.js] f_TransferMute END");
    }
    catch(err)
    {
        logslog("[TSPCallControl.js] f_TransferMute : ERR : " + err.description);
        alert("f_TransferMute() :" + err.description);
    }
}

//--------------------------------------------------------------------------------------------------
// 14. 회의통화를 시도한다.
//--------------------------------------------------------------------------------------------------
//==================================================================================================
//함수 : f_ConferenceInit
//인자 : telno - 발신 전화번호
//반환 :
//==================================================================================================
function f_ConferenceInit(telno)
{
    try
    {
        logslog("[TSPCallControl.js] f_ConferenceInit START");
        logslog("[TSPCallControl.js] f_ConferenceInit TELNO : " + telno);
        if(telno == "")
        {
            alert("입력하신 전화번호가 없습니다.");
            return;
        }
        goto_consulting_pop("init");
        logslog("[TSPCallControl.js] f_ConferenceInit --> goto_consulting_pop(init)");
        CTI_CmdConsult(telno);
        logslog("[TSPCallControl.js] f_ConferenceInit --> CTI_CmdConsult()");
        logslog("[TSPCallControl.js] f_ConferenceInit END");
    }
    catch(err)
    {
        logslog("[TSPCallControl.js] f_ConferenceInit : ERR : " + err.description);
        alert("f_ConferenceInit() :" + err.description);
    }
}

//--------------------------------------------------------------------------------------------------
// 15. 호전환 요청 및 회의통화 요청에 관련하여 완료시킨다.
//--------------------------------------------------------------------------------------------------
//==================================================================================================
//함수 : f_Complete
//인자 :
//반환 :
//==================================================================================================
function f_Complete(contextPath, loginid, telno)
{
    try
    {
        logslog("[TSPCallControl.js] f_Complete START");
        logslog("[TSPCallControl.js] f_Complete TRANFG : " + logoutForm.transferfg.value);
        logslog("[TSPCallControl.js] f_Complete CONFFG : " + logoutForm.conferencefg.value);
        if (logoutForm.transferfg.value == "Y") {
            document.counselDetailForm.respid.value = loginid;
            counsel_save("");

            CTI_CmdTransfer(telno);
            logslog("[TSPCallControl.js] f_Complete --> CTI_CmdTransfer");

            counsel_reg();
            customer_reg();
            // TO-DO LIST 재조회..
            var todoVal = ifr_Todo.todoVal;
            ifr_Todo.location = contextPath + "/main/todo_sub_list.do?todoVal="+todoVal;
            logoutForm.transferfg.value = "N";
            logslog("[TSPCallControl.js] f_Complete todoVal : " + todoVal);
        } else if (logoutForm.conferencefg.value == "Y") {
            logoutForm.conferencefg.value = "N";

            CTI_CmdConference();
            logslog("[TSPCallControl.js] f_Complete --> CTI_CmdConference");
        }
        goto_consulting_pop("complete");
        logslog("[TSPCallControl.js] f_Complete --> goto_consulting_pop");
        logslog("[TSPCallControl.js] f_Complete END");
    }
    catch(err)
    {
        logslog("[TSPCallControl.js] f_Complete : ERR : " + err.description);
        alert("f_Complete() :" + err.description);
    }
}

//--------------------------------------------------------------------------------------------------
// 16. 호전환 요청 및 회의통화 요청에 관련하여 취소시킨다.
//--------------------------------------------------------------------------------------------------
//==================================================================================================
//함수 : f_Reconnect
//인자 :
//반환 :
//==================================================================================================
function f_Reconnect()
{
    try
    {
        logslog("[TSPCallControl.js] f_Reconnect START");
        logoutForm.transferfg.value = "N";
        logoutForm.conferencefg.value = "N";
        if (document.counselDetailForm.cselidgrp_old.value != "") {
            document.counselDetailForm.cselidgrp.value = document.counselDetailForm.cselidgrp_old.value;
            document.counselDetailForm.cselidgrp_old.value = "";
        }
        goto_consulting_pop("reconnect");
        CTI_CmdReconnect();
        setValue("transInfoForm", "loginid", "");
        setValue("transInfoForm", "extno", "");
        logslog("[TSPCallControl.js] f_Complete --> CTI_CmdReconnect");
        logslog("[TSPCallControl.js] f_Reconnect END");
    }
    catch(err)
    {
        logslog("[TSPCallControl.js] f_Reconnect : ERR : " + err.description);
        alert("f_Reconnect() :" + err.description);
    }
}

//--------------------------------------------------------------------------------------------------
// 17. 버튼제어
//--------------------------------------------------------------------------------------------------
//==================================================================================================
//함수 : f_ButtonStatus
//인자 : btnStatus - 버튼 상태
//반환 :
//==================================================================================================
function f_ButtonStatus(args)
{
    logslog("[TSPCallControl.js] f_ButtonStatus START");
    logslog("[TSPCallControl.js] f_ButtonStatus ARGS : " + args);
    alert("f_ButtonStatus = " + args);
    logslog("[TSPCallControl.js] f_ButtonStatus END");
}

//--------------------------------------------------------------------------------------------------
// 18. 해당 오브젝트를 사용가능 한 상태로 변경
//--------------------------------------------------------------------------------------------------
//==================================================================================================
//함수 : f_StatusEnable
//인자 : args - 오브젝트명
//반환 :
//==================================================================================================
function f_StatusEnable(args)
{
    try{
        logslog("[TSPCallControl.js] f_StatusEnable START");
        logslog("[TSPCallControl.js] f_StatusEnable ARGS : " + args);
        if(args != undefined || args != "") {
            args.disabled = false;
            args.style.filter = "";
        }
        logslog("[TSPCallControl.js] f_StatusEnable END");
    }catch(err){
        logslog("[TSPCallControl.js] f_StatusEnable : ERR : " + err.description);
        alert("f_StatusEnable() :" + err.description);
    }
}

//--------------------------------------------------------------------------------------------------
// 19. 해당 오브젝트를 사용 불가능 한 상태로 변경
//--------------------------------------------------------------------------------------------------
//==================================================================================================
//함수 : f_StatusDisable
//인자 : args - 오브젝트명
//반환 :
//==================================================================================================
function f_StatusDisable(args)
{
    try{
        logslog("[TSPCallControl.js] f_StatusDisable START");
        logslog("[TSPCallControl.js] f_StatusDisable ARGS : " + args);
        if(args != undefined || args != "") {
            args.disabled = true;
            args.style.filter = "gray()";
        }
        logslog("[TSPCallControl.js] f_StatusDisable END");
    }catch(err){
        logslog("[TSPCallControl.js] f_StatusDisable : ERR : " + err.description);
        alert("f_StatusDisable() :" + err.description);
    }
}

String.prototype.telnbChk = function(prefix) {
    var temp_str = this;

    temp_str = temp_str.replaceAll("-","");
    temp_str = temp_str.replaceAll("+","");
    temp_str = temp_str.replaceAll("(","");
    temp_str = temp_str.replaceAll(")","");

    /*if (temp_str.indexOf("032") == 0) {
        temp_str = temp_str.Right(temp_str.length - 3);
    }*/

    temp_str = prefix + temp_str;

    return temp_str;
};