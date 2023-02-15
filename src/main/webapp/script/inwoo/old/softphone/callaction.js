  var activecallid = 0;
  var heldcallid = 0;
  var ueidata = '';
  var ani;
  var targetdb;
  var ccState = true;
  var trState = false;
  var state = false;
  var ocxfg = true;
  var holdState = false;

  var g_agentId;
  var g_openDevice;
  var g_agentName;

  var cticallid;
  var ctianino;
  var ctiuei;
  var arsinfo = "";

  var gs_CallGateId = "0";		// 전화 gateId 아이디

  function fnLogin() { // 로그인

      var result = "";
      fnChangeAgentStatus(1);
      result = fnChangeAgentStatus(0);

      var loginstatus = document.getElementById("loginstatus").value;
      if (loginstatus == "1") result = fnChangeAgentStatus(2,3); //휴식으로 설정

    return result;
  }

  function fnAgentWait(waitmode) {
      var result = CAPI3X1.ctmpsetagentflag(gs_CallGateId, 0, CAPI3X1.OpenAgentID, waitmode);
      return result;
  }

  function fnChangeAgentStatus(mode, reasonCode) {
    if(reasonCode == null) reasonCode = 0;
    var result;
    result = CAPI3X1.ctmpsetfeatureagentstatus(gs_CallGateId, 0, CAPI3X1.OpenDevice, mode, CAPI3X1.OpenAgentID, CAPI3X1.OpenGroup, CAPI3X1.OpenGroup, reasonCode, 0, "");

    return result;
  }


  // ctmp 서버열기
  function fnOpenServer (agentid, dn_num, agentnm, ctiip, ctiport) {
    var result;

    try{
        CAPI3X1.ServerName = ctiip;
        CAPI3X1.ServerNameII = ctiip;
        CAPI3X1.OpenPort = ctiport;
        CAPI3X1.OpenDevice = dn_num;
        CAPI3X1.OpenAgentId = agentid;
        CAPI3X1.OpenGroup = "1030";
        CAPI3X1.LinkMode = "4";

        if (CAPI3X1.ctmpopenserver()==1) {
            CAPI3XEvt1.opengate(CAPI3X1.GateId);
            result = CAPI3X1.ctmpmonitorstart(CAPI3X1.GateId, 0);

            g_agentId = agentid;
            g_openDevice = dn_num;
            g_agentName = agentnm;

        } else {
            alert("CTI 서버에 연결하지 못했습니다.");
        }

    }catch(e){
        ocxfg = false;
        alert("CTI Ocx 설치가 되지 않았습니다.\n\n다시 설치하시고 작업하세요!.");

    }

    return result;

  }

  // ctmp 서버닫기
  function fnLogout() {
    if (ocxfg) {
        var result;

        fnChangeAgentStatus(1);		// 전화

        result = CAPI3X1.ctmpmonitorstop(gs_CallGateId, 0);
        result = CAPI3X1.ctmpcloseserver(gs_CallGateId, 0);

        return result;
    }
  }

  // DND 모드 설정
  function fnSetDnd(yn) {
      var resutl = CAPI3X1.ctmpsetfeaturednd(gs_CallGateId, 0, CAPI3X1.OpenDevice, yn, "");
  }

  function fnSetDndReady() {
//	  fnSetDnd(1);
//	  sleep(2000)
//	  fnSetReady();
//	  sleep(2000)
//	  fnSetDnd(0);
      //setTimeout("fnSetDnd(0)","2000");
  }

//	function sleep(milliseconds) {
//	var start = new Date().getTime();
//	for (var i = 0; i < 1e7; i++) {
//
//		if ((new Date().getTime() - start) > milliseconds) {
//			break;
//		}
//	}
//}

  // 전화 대기
  function fnSetReady() {
      var result = fnChangeAgentStatus(3);
      return result;
  }

  // NotReady
  function fnSetNotReady(reasonCode) {
      var result = "";
      if(getAgentStatus() != "후처리중" && getAgentStatus() != "대기중") {
          fnSetReady();
      }

      result = fnChangeAgentStatus(2, reasonCode);
      return result;
  }

  // 전화걸기
  function fnMakecall(phoneNum) {
    var result;

    if(phoneNum == "") {
        alert("전화번호를 입력하세요.");
        return;
    }

    result = CAPI3X1.ctmpmakecall(gs_CallGateId, 0, CAPI3X1.OpenDevice, phoneNum, "", "", "", "", "", "");
    return result;
  }

  // 전화끊기
  function fnClearcall() {
    var result;

    result = CAPI3X1.ctmpclearconnection(gs_CallGateId, 0, activecallid, CAPI3X1.OpenDevice, "");

    return result;
  }

  // 전화받기
  function fnAnswercall() {
    var result;

    result = CAPI3X1.ctmpanswercall(gs_CallGateId, 0, activecallid, CAPI3X1.OpenDevice, "");

    if(result==1){
        //setTimeout("do_rec()","5000"); // 전화받기 성공시 녹취.
    }

    return result;
  }

  // 전화보류
  function fnHoldcall() {
    var result;

    holdState = true;

    result = CAPI3X1.ctmpholdcall(gs_CallGateId, 0, activecallid, CAPI3X1.OpenDevice);

    return result;
  }

  // 보류해제
  function fnRetrievecall() {
    var result;

    holdState = false;

    result = CAPI3X1.ctmpretrievecall(gs_CallGateId, 0, heldcallid, CAPI3X1.OpenDevice);

    return result;
  }

  // 전환시도
  function fnConsultationcall(phoneNum, chkfg, arsfg) {
    var result;

    trState = true;
    targetdb = phoneNum;

    if(phoneNum == "") {
        alert("전화번호를 입력하세요.");
        return;
    }
    var obj = top.getCounselFrame();

    var transfer_uei = ctiuei +  obj.inboundForm.inboundid.value + "|";

    if(arsfg == "1") {
        transfer_uei = phoneNum;
    }
    if(chkfg == "Y") {
        transfer_uei = top.centercd;
    }

    result = CAPI3X1.ctmpconsultationcall(gs_CallGateId, 0, activecallid, CAPI3X1.OpenDevice, phoneNum, 0, "2", "", "", transfer_uei, "", "");
    return result;

  }

  // ctmponesteptransfer
  function fnOnesteptransfer(phoneNum, chkfg, arsfg) {
        var result;

        if(phoneNum == "") {
            alert("전화번호를 입력하세요.");
            return;
        }
        var transfer_uei = "";

        if(arsfg == "1") {
            transfer_uei = phoneNum;
        }
        if(chkfg == "Y") {
            transfer_uei = top.centercd;
        }

        result = CAPI3X1.ctmponesteptransfer(gs_CallGateId, 0, activecallid, CAPI3X1.OpenDevice, phoneNum, "", transfer_uei, "");
        return result;
  }

  // 호전환
  function fnTransfercall() {
    var result;

    result = CAPI3X1.ctmptransfercall(gs_CallGateId, 0, heldcallid, CAPI3X1.OpenDevice, activecallid, targetdb, "");
    return result;
  }

  // 회의통화
  function fnConferencecall() {
    var result;

    result = CAPI3X1.ctmpconferencecall(gs_CallGateId, 0, heldcallid, CAPI3X1.OpenDevice, activecallid, targetdb, "");
    return result;
  }

  // 전환취소
  function fnReconnectcall() {
    var result;

    result = CAPI3X1.ctmpreconnectcall(gs_CallGateId, 0, activecallid, CAPI3X1.OpenDevice, heldcallid, CAPI3X1.OpenDevice);
    return result;

  }

  // 후처리
  function fnSetAfterCallWork(reasonCode) {
    var result = fnChangeAgentStatus(5, reasonCode);
    // 후처리 이후에 바로 3초동안 전화 안들어오게
    fnAgentWait("3");
    return result;
  }

  // 호전환 받은 상담 설정.
  function registerInboundTransfer(inboundid){
      var param = "process=registerInboundTransfer&inboundid=" + inboundid;
      top.setCounselFrame("inboundMod", param);
      //url = "/powerservice/inboundAction.do?process=registerInboundTransfer&inboundid="+inboundid;
      //var obj =  top.getCounselFrame();
      //obj.location.href(url);

  }

  /************************************************************
   *
   * 녹취관련 함수들
   *
   * *********************************************************/

    function rec_Login() {
        var recip = document.getElementById("recip").value;
        if(recip!=""){
            var result = APLinkWeb.SendLoginInfo(document.getElementById("recip").value, g_agentId, g_openDevice, g_agentName);
            if(result!=1)alert("녹취 로그인 실패" + ":"+result);
        }

    }

    function rec_start() {
        var obj = top.getCounselFrame();

        if(getAgentStatus() == "통화중") insertRecord();
    }

    function rec_getfile() {
        var file_name = APLinkWeb.GetRecFileName();
        top.localLog("녹취파일명 : " + file_name);
        return file_name;
    }

    function do_rec(){
        var seq_num = APLinkWeb.SendSetup();
        rec_start();
    }

    function insertRecord() {
        var filename = rec_getfile();
        var counselid = top.indexFrame.mainCounselFrame.counselFrame.counselFrame.inboundForm.inboundid.value;
        var centercd = top.indexFrame.mainCounselFrame.counselFrame.counselFrame.inboundForm.regcentercd.value;
        var recordtypecd = "";
        var customerid = "";

        var sendData = "process=registerRecordAjax";
        sendData += "&recordtypecd=" + recordtypecd;
        sendData += "&counselid=" + counselid;
        sendData += "&customerid=" + customerid;
        sendData += "&recordfilenm=" + filename;
        sendData += "&centercd=" + centercd;

        top.localLog("녹취 insertRecord() param : " + sendData);
        sendRequest(ajaxCallBack, sendData, 'POST', '/powerservice/recordAction.do');
    }

    function ajaxCallBack(request) {
        if (request.responseText == "succeed") {
            //alert("성공");
        } else {
            //alert("실패");
        }
    }


  // CTI event function 시작

  function ctiConfernced(){
      activecallid = CAPI3XEvt1.CallRefId;
      state = true;
  }

  function ctiConnectionCleared(){

      if(heldcallid==0 || state || holdState){
          ccState = true;
          state = false;
          holdState = false;
          fnSetAfterCallWork(0);
          goto_afterwork();
          top.getCounselFrame().clearTalkClock();
          clearStatusClock();
      }else{
          heldcallid=0;
      }


  }

  function ctiDelivered(){
      activecallid = CAPI3XEvt1.CallRefId;
      ccState = false;

      if(CAPI3XEvt1.LocalConnState == 2) {           // 전화를 받아서 생긴 이벤트일 때
          document.getElementById("cti_row").style.display = "";
            document.getElementById("menu_row").style.display = "none";

          goto_incoming();

          ueiSplit();
      } else if(CAPI3XEvt1.LocalConnState == 3){    // 전화를 걸어서 생긴 이벤트일 때
          if(trState) goto_consult();

          trState = false;
      }
  }

  function ctiDiverted(){

  }

  function ctiEstablished(){
      if(heldcallid==0 || state || holdState){
          setTimeout("do_rec()","5000");
          goto_calling();
          activecallid = CAPI3XEvt1.CallRefId;

          fnAgentWait("10");

          clearStatusClock();
          statusClock();
      }

  }

  function ctiHeld(){
      heldcallid  = CAPI3XEvt1.CallRefId;
  }

  function ctiNetworkReached(){
      activecallid = CAPI3XEvt1.CallRefId;
  }

  function ctiRetrieved(){
      activecallid = CAPI3XEvt1.CallRefId;
      heldcallid = 0;
  }

  function ctiTransferred(){
      activecallid = CAPI3XEvt1.CallRefId;
      heldcallid = 0;

      var callerData = CAPI3XEvt1.UEI;
      var callDataSplit = callerData.split("|");

      //if(callDataSplit[7] != undefined && callDataSplit[7]!= ""){
      //    registerInboundTransfer(callDataSplit[7]);
      //}
  }

  function ctiNotReady(){
      heldcallid = 0;
  }

  function ctiReady(){
      heldcallid = 0;
  }

  function ctiAfterCallWork(){
      heldcallid = 0;
  }

  function ueiSplit() {
      var action_status = -1;
      var callerData = CAPI3XEvt1.UEI;
      var callDataSplit = callerData.split("|");


      top.localLog("[GetCallerInfo] 데이터가 수신 되었습니다.");
      top.localLog(callerData);

      var residentno = "";
      var ani_no = CAPI3XEvt1.OtherParty;

      if(callDataSplit.length>4) {
          arsinfo = callDataSplit[4];
          residentno = callDataSplit[5];
      } else {
          arsinfo = "";
          residentno = "";
      }

      if(ani_no.length == 9 && ani_no.substring(0,2) == "01") ani_no = "02" + ani_no.substring(1,ani_no.length);

      // CTI callhistory 입력
      cticallid = activecallid;
      ctianino = ani_no;
      ctiuei = callerData;
      saveCticallhistory();

      document.getElementById("anino").value = ani_no;
      document.getElementById("customerno").value = residentno;

      top.localLog("==========================================");
      top.localLog("[GetCallerInfo] 0 : 걸려온 유형 " + gs_CallType);
      top.localLog("[GetCallerInfo] 1 : 걸려온 전화번호 : " + ani_no);
      top.localLog("[GetCallerInfo] 2 : 인입정보 : " + arsinfo);
      top.localLog("[GetCallerInfo] 3 : 주민번호 : " + residentno);
      top.localLog("==========================================");

      var call_type;

      if(callDataSplit[7] != undefined && callDataSplit[7]!= ""){
          // 호전환
          call_type = "2";
      }else if(ani_no.length == 4 && CAPI3XEvt1.UUI != "2"){
          // 내선전화
          call_type = "3";
      }else{
          // 일반전화
          call_type = "0";
      }
      openDailingPopup(call_type);
  }
