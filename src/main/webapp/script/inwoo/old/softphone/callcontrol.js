    var gs_ctiusefg  = "1";		// CTI 사용여부(0:사용안함/1:전화/2:전화,웹)
    var gs_ocxfg = true; 		// ocx가 설치되었는지 확인하는 전역변수(false: 설치안됨/true:설치됨)

    String.prototype.trim = function() {
        return this.replace(/(^\s*)|(\s*$)/gi, "");
    };

    String.prototype.replaceAll = function(str1, str2) {
        var temp_str = "";

        if (this.trim() != "" && str1 != str2) {
            temp_str = this.trim();
            while (temp_str.indexOf(str1) > -1) {
                temp_str = temp_str.replace(str1, str2);
            }
        }
        return temp_str;
    };

    String.prototype.telnbChk = function(prefix) {
        var temp_str = this;

        temp_str = temp_str.replaceAll("-","");
        temp_str = temp_str.replaceAll("+","");
        temp_str = temp_str.replaceAll("(","");
        temp_str = temp_str.replaceAll(")","");

        if (temp_str.indexOf("032") == 0) {
            temp_str = temp_str.Right(temp_str.length - 3);
        }

        temp_str = prefix + temp_str;

        return temp_str;
    };

    // ==================
    // 콜센터CTI 로그인
    // ==================
    function Login(agentid, dn_num, agentnm) {
        gs_ctiusefg = document.getElementById("ctiusefg").value;
        var cti_ip1 = document.logoutForm.ctiip1.value;
        var cti_ip2 = document.logoutForm.ctiip2.value;
        var cti_port = document.logoutForm.ctiport.value;

        try {
            // InitToolbar = 0을 리턴하면 정상 - 환경셋팅 프로그램
            //isdk1.InitToolbar (cti_ip1, '2020', cti_ip1, '2020', cti_ip1, cti_port, cti_ip1, cti_port, "default","password","SoftPhone",30,60, true);
            isdk1.InitToolbar (cti_ip1, '2020', cti_ip2, '2020', cti_ip1, cti_port, cti_ip2, cti_port, "default","password","SoftPhone",30,60, true);
        } catch(e) {
            gs_ocxfg = false;
            alert("CTI Ocx 설치가 되지 않았습니다.\n\n다시 설치하시고 작업하세요!.");
        }

        if (gs_ocxfg) {
            if (gs_ctiusefg == "Y") {
                var result = "";
                var login_result = "";

                isdk1.RegisterDN(dn_num);

                login_result = isdk1.LoginIF (agentid, "", '');

                if (login_result != "S") {
                    if (login_result == "5") alert("(CTI)다른 상담사이 로그인 중인 상태입니다.");
                    else if (login_result == "6") alert("(CTI)해당 전화가 이미 통화중입니다.");
                    else alert("(CTI) 로그인 실패 : Error Code = " + login_result);
                }
            } else {
                alert("로그인 할 수 없습니다\n\nCTI 사용유무를 확인하세요");
            }
        }
    }

    // ==================
    // 콜센터CTI 로그아웃
    // ==================
    function Logout() {
        if (gs_ocxfg) {
            try {
                isdk1.LogoutIF();
                rec_Logout();
            } catch(e) {}
        }
    }

    // ==============
    // 콜센터CTI 대기
    // ==============
    function Ready() {
        isdk1.ReadyAutoInIF();
        counsel_reg();
        customer_reg();
    }

    // NotReady
    function NotReady(reasonCode) {
        isdk1.NReadyIF(reasonCode);
    }

    // ==================================
    // 콜센터CTI CTI를 통한 전화걸기 시도
    // ==================================
    function Dial(DialNB, prefix) {
        if (prefix == null) prefix = "9";
        DialNB = DialNB.telnbChk(prefix);

        isdk1.DialWithDataIF(DialNB,"", "");
    }

    // ==================
    // 콜센터CTI 전화끊기
    // ==================
    function Drop() {
        isdk1.ReleaseIF();
    }

    // ==================
    // 콜센터CTI 전화받기
    // ==================
    function Receive() {
        isdk1.AnswerIF();
    }

    // ==============
    // 콜센터CTI 보류
    // ==============
    function Hold() {
        isdk1.HoldIF();
    }

    // ==============
    // 콜센터CTI 복귀
    // ==============
    function UnHold() {
        isdk1.RetrieveIF();
    }

    function AfterWork() {
        logslog("AfterWork");
        isdk1.NReadyACWIF();
    }

    //============ 협의통화
    function Consultation(prefix, DialNB) {
        var key = "key";
        var value = "value";

        var DialNB = DialNB.telnbChk(prefix);

        //alert("DialNB = " + DialNB);

        logslog("협의통화 시작 = " + DialNB);

        isdk1.InitConferWithDataIF(DialNB, key, value);
        logslog("협의통화 종료");
    }

    //=== 협의통화 취소
    function ConsultationCancel() {
        isdk1.ReconnectIF();
    }

    //============ 호전환
    function Transfer() {
        isdk1.CompTransferIF();
    }

    //== 회의통화
    function Conference() {
        isdk1.CompConferIF();
    }

    // ==============
    // 콜센터CTI 싱글스텝호전환
    // ==============
    function SingleStepTransfer(prefix, DialNB, key, value) {
        var DialNB = DialNB.telnbChk(prefix);

        if (typeof(key) == "undefined") {
            key = "";
        }

        if (typeof(value) == "undefined") {
            value = "";
        }

        //isdk1.SingleStepTransferIF(DialNB, "", "");
        isdk1.SingleStepTransferIF(DialNB, key, value);
    }

    // 녹취함수
    function getrecordid() {
        logslog("녹취 아이디 가져오는 함수 호출");

        var cselid = getValue("counselDetailForm", "cselid");
        logslog("rec_getrecid() 함수 호출");
        var recordid = rec_getrecid();
        logslog("// cselid = " + cselid + " recordid = " + recordid);

        // 녹취정보저장
        record_save(recordid, cselid);
    }

    // 이벤트 데이터 반환
    function getEventValue(message, key1, key2) {
        // 이벤트는 ";" 로 구분된다
        var data = message.split(";");
        var cnt = data.length;

        for(var i = 0; i < cnt; i++) {
            var values = data[i].split("=");

            if(values[0] == key1) {
                // 값이 더 있는지 확인
                // 내부 값은 "~" 로 구분된다
                if(values[1].indexOf("~") > 0) {
                    var innerData = values[1].split("~");
                    var innerCount = innerData.length;

                    for(var j = 0; j < innerCount; j++) {
                        var innerValues = innerData[j].split("^");

                        if(innerValues[0] == key2) {
                             return innerValues[1];
                        }
                    }
                } else {
                    return values[1];
                }
            }
        }

        return "";
    }

    // 상담사상태를 쿼리하여 버튼을 그대로 맞춤
    function getAgentState() {
        logslog("getAgentState()");
        var agentState = isdk1.getAgentState();
        logslog("getAgentState() = " + agentState);

        if (agentState == "0100") goto_ready();
        else if (agentState == "0201") goto_notready("1");
        else if (agentState == "0202") goto_notready("2");
        else if (agentState == "0203") goto_notready("3");
        else if (agentState == "0204") goto_notready("4");
        else if (agentState == "0205") goto_notready("5");
        else if (agentState == "0206") goto_notready("6");
        else if (agentState == "0300") goto_afterwork();
    }


    // 이벤트
    function CTIEventHandler(lEventType, bszMsg) {
        switch (lEventType)
        {
        //
        case 62 :
            logslog("62 EventNetworkReached");

            var CallType = getEventValue(bszMsg, "CallType", "");

            logslog("CallType = " + CallType);
            if ( CallType == "3" ) {
                goto_calling();
            } else if ( CallType == "4" ) {
                goto_consulting();
            }
            break;
        // 로그인
        case 104 :
            logslog("104 LOGIN");
            rec_Login();
            break;
            // 로그아웃
        case 105 :
            logslog("105 LOGOUT");
            goto_Logout();
            break;
            // 대기, 자동		(EVT_READY_AUTOIN)
        case 107 :
            logslog("107 EVT_READY_AUTOIN");
            goto_ready();
            break;
            // 대기, 수동		(EVT_READY_MANUALIN)
        case 108 :
            logslog("108 EVT_READY_MANUALIN");
            goto_ready();
            break;
            // NotReady		(EVT_NOTREADY_BREAK)
        case 109 :
            logslog("109 NotReady();");
            var reason = getEventValue(bszMsg, "Reasons","Reasons")
            logslog("NotReady("+reason+");");
            if (reason == "") NotReady("2");
            else goto_notready(reason);
            break;
            // 후처리			(EVT_NOTREADY_ACW)
        case 110 :
            logslog("110 후처리");
            goto_afterwork();
            break;
            // 전화왔다!!		(EVT_RINGING)
        case 116 :
            logslog("// 116 EVT_RINGING");
            var anino = getEventValue(bszMsg, "ANI","");

            logslog("변환전 anino = " + anino);

            if (anino == "") anino = getEventValue(bszMsg, "OtherDn","");

            if (anino.length != 4 && anino.substring(0,1) != "0") anino = "0" + anino;

            logslog("변환후 anino = " + anino);

            var callid = getEventValue(bszMsg, "CallID","");
            var scode = getEventValue(bszMsg, "UserData","IVR_MENU");
            var sdata = getEventValue(bszMsg, "UserData","IVR_STAGE");

            logslog("callid="+callid+", anino="+anino+", scode="+scode+", sdata="+sdata);

            //  어디서 인입되나
            //
            // 120 				: 3000
            // 2114 			: 3104
            // 환경신문고		: 3001
            // 종건			 	: 3105
            // 경제자유구역청	: 4444
            var DNIS = getEventValue(bszMsg, "UserData","ORIGIN_DNIS");
            var CallType = getEventValue(bszMsg, "CallType", "");
            var IN_CALL_TYPE = getEventValue(bszMsg, "UserData","IN_CALL_TYPE");
            var IVR_AUTH = getEventValue(bszMsg, "UserData","IVR_AUTH");	// 주민번호데이터

            try {
                document.logoutForm.dnis.value = DNIS;
            } catch(e) {}

            logslog("bszMsg=" + bszMsg);

            logslog("DNIS = " + DNIS + ", CallType = " + CallType + ", IN_CALL_TYPE = " + IN_CALL_TYPE + ", ivr_auth = " + IVR_AUTH);

            if (IN_CALL_TYPE == "CUST_REG_NUM") {	// 주민번호 요청후에는 Auto Answer 처리
                if (IVR_AUTH != null && IVR_AUTH.length == 13) {
                    $("#ssno1").val(IVR_AUTH.substring(0, 6));
                    $("#ssno2").val(IVR_AUTH.substring(6, 13));
                }
                Receive();
            } else {
                if (CallType == 2) {
                    cticallhist_save(callid, anino, scode, sdata, CallType);
                }
                goto_incoming();
            }
            break;
            // Dialing!! 	(EVT_DIALING)
        case 117 :
            logslog("// 117 EVT_DIALING");

            var CallType = getEventValue(bszMsg, "CallType", "");

            logslog("CallType = " + CallType);

            if (CallType == 4) {
                // 전환시도일 경우에 전환시도 상태 표시
                goto_consulting();
            } else {
                goto_dialing();
            }
            break;
            // 통화중 		(EVT_TALKING)
        case 119 :
            logslog("// 119 통화중");

            var CallType = getEventValue(bszMsg, "CallType", "");

            logslog("CallType = " + CallType);

            if (CallType == 2) {
                startCselTime();
                setTimeout("getrecordid()", "5000");
            }

            if (CallType == 4) {
                // 전환시도일 경우에 전환시도 상태 표시
                goto_consultation();
            } else {
                goto_calling();
            }

            break;
        case 121 :
            logslog("// 121 Hold");
            goto_Hold();
            break;

            // 재연결
        case 122 :
            logslog("// 122 Reconnect");
            goto_UnHold();
            break;
        case 127 :
            logslog("// 127 Abandon");

            getAgentState();                 // 상담사상태 조회해서 그상태로 버튼 변경

            break;
            //EVT_ERROR (CTI 호처리 관련 에러 발생)
        case 128 :
            logslog("// 128 ERROR");
            //alert("오류메시지 = " + bszMsg );
            //alert("CTI ErrorMessage="+getEventValue(bszMsg, "invalidCSTADeviceIdentifie",""));
            //alert("CTI ErrorCode="+getEventValue(bszMsg, "ErrorCode",""));
            var errorcode = getEventValue(bszMsg, "ErrorCode","");

            //if (errorcode == "56") alert("해당 전화가 이미 끊어졌습니다");
            //else if (errorcode == "1141") alert("소프트폰 상태와 전화기 상태가 맞지 않습니다\n전화기, 소프트폰 모두 로그아웃 후 재시도 해보세요");
            //else if (errorcode == "1151") alert("교환기, Genesys에 존재하지 않는 로그인ID로 로그인 요청하였습니다");
            //else if (errorcode == "1161"){}
            //else alert("확인되지 않은 오류코드 errorcode = " + errorcode + "가 확인 되었습니다. 개발자(lch75)에게 문의 하세요");
          //else if (errorcode == "1161") alert("소프트폰 동작을 처리할 수 없는 상태(수화기가 들려있거나 통화중인 상태)입니다\n전화기, 소프트폰 모두 로그아웃 후 재시도 해보세요");
            if (errorcode == "1151") alert("로그인중인 아이디입니다.\n전화기를 로그아웃 하세요");
            logslog("오류코드 errorcode = " + errorcode + "가 확인 되었습니다. 개발자에게 문의 하세요");

            break;
            //EVT_CFG_INITFAIL
        case 152 :
            aler("CfgServer 초기화 실패(상담사ID 쿼리 오류");
            break;
            //EVT_CFG_CTIIDFAIL
        case 153 :
            aler("CfgServer 초기화 실패(상담사ID 쿼리 실패");
            break;
            //EVT_SERVER_DISCONNECTED
        case 165 :
            alert("CTI 서버와 연결이 끊겼습니다 관리자에게 문의하세요");
            break;
            //EVT_LINK_DISCONNECTED
        case 166 :
            alert("교환기 서버와 연결이 끊겼습니다 관리자에게 문의하세요");
            break;
            // onHook
        case 167 :
            logslog("// 167 onHook");

            stopCselTime();

            // 이벤트 값중에 CallType이 2,3인 경우 후처리 요청한다
            var CallType = getEventValue(bszMsg, "CallType", "");

            logslog("CallType = " + CallType);

            if (CallType == "2") {
                AfterWork();
            } else {
                getAgentState();                 // 상담사상태 조회해서 그상태로 버튼 변경
            }

            break;
        }
        logslog("lEventType = " + lEventType);
    }