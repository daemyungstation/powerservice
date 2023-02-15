
    /**
     *	TELPER-REC API 상수
     */
    var S_OK			= 0;		//성공
    var S_FALSE			= 1;		//실패
    var	S_ERR_CONNECT 	= 2;		// 접속실패
    var S_ERR_SEND 		= 3;		// 전송실패
    var S_ERR_RECV 		= 4;		// 수신실패
    var S_ERR_MSG 		= 5;		// 메세지오류
    var S_ERR_TELNUM 	= 6;		// 내선번호오류
    var S_ERR_AGENT_ID	= 7;		// 상담사ID 오류
    var S_ERR_LOGIN 	= 8;		// 로그인이안된상태
    var S_ERR_CONTROL 	= 9;		// 채널컨트롤방식에러

    var IO_KIND_INBOUND		= 1;	//인바운드
    var IO_KIND_OUTBOUND	= 2;	//아웃바운드

    var CHANNEL_STATE_IDLE = 1;			// 대기
    var CHANNEL_STATE_RECORDING = 2;	// 녹음중
    var CHANNEL_STATE_ERROR = 6;		// 전화기 접속 에러

    var g_Ip1	= "";				//서버 IP
    var g_Ip2	= "";				//서버 IP
    var g_Port1	= "";				//서버 PORT
    var g_Port2	= "";				//서버 PORT

    var g_AgtId	= "";				//상담사 ID
    var	g_AgtNm	= "";				//상담사 명
    var g_TelNo	= "";				//내선번호

    var g_bLogin1	= false;		//로그인 여부
    var g_bLogin2	= false;		//로그인 여부
    var g_IsRecording1	= false;	//녹취 여부
    var g_IsRecording2	= false;	//녹취 여부

    /**
     *	TELPER-REC 초기화
     */
    function initRec() {
        TlpRecAp1.SetLogInfo("D:\\TELPER\\Rec\\AP1", 1);	//로그패스 및 인덱스 설정(최초 1회만 설정)
        TlpRecAp1.SetUseEvent(true);						//이벤트 사용 여부(최초 1회만 설정)
        TlpRecAp1.SetUseChannelError(true);					//채널 에러 사용여부(최초 1회만 설정)
        TlpRecAp1.SetMsgFormat(2);							//메세지 포맷 설정

        TlpRecAp2.SetLogInfo("D:\\TELPER\\Rec\\AP2", 1);	//로그패스 및 인덱스 설정(최초 1회만 설정)
        TlpRecAp2.SetUseEvent(true);						//이벤트 사용 여부(최초 1회만 설정)
        TlpRecAp2.SetUseChannelError(true);					//채널 에러 사용여부(최초 1회만 설정)
        TlpRecAp2.SetMsgFormat(2);							//메세지 포맷 설정
    }

    /**
     *	TELPER-REC 에러 확인
     */
    function CheckError(nErrorCode) {
        var bRet = false;

        switch(nErrorCode) {
        case S_OK: 						//성공
            bRet = true;
            break;
        case S_FALSE:					//실패
            alert("[녹취] 실패");
            break;
        case S_ERR_CONNECT:				// 접속실패
            alert("[녹취] 녹취 서버에 접속 할 수 없습니다");
            break;
        case S_ERR_SEND:				// 전송실패
            alert("[녹취] 메세지 전송 실패");
            break;
        case S_ERR_RECV:				// 수신실패
            alert("[녹취] 응답 메세지 수신 실패");
            break;
        case S_ERR_MSG:					// 메세지오류
            alert("[녹취] 응답 메세지 오류");
            break;
        case S_ERR_TELNUM:				// 내선번호오류
            alert("[녹취] 내선번호를 찾을 수 없습니다");
            break;
        case S_ERR_AGENT_ID:			// 상담사ID 오류
            alert("[녹취] 상담사ID를 찾을 수 없습니다");
            break;
        case S_ERR_LOGIN:				// 로그인이안된상태
            alert("[녹취] 녹취 로그인이 안된 상태입니다");
            break;
        case S_ERR_CONTROL:				// 채널컨트롤방식에러
            alert("[녹취] 녹음 제어를 할 수 없는 채널입니다");
            break;
        default:
            alert("[녹취] 알수없는 에러 코드[" + nErrorCode + "]");
            break;
        }
        return bRet;
    }

    /**
     *	TELPER-REC 서버1 로그인
     */
    function rec_Login() {
        logslog("[녹취] rec_Login start...");

        initRec();

        g_Ip1   = document.logoutForm.recip1.value;		//서버1 IP
        g_Ip2   = document.logoutForm.recip2.value;		//서버2 IP
        g_Port1 = document.logoutForm.recport1.value;	//서버1 통신 포트
        g_Port2 = document.logoutForm.recport2.value;	//서버2 통신 포트

        TlpRecAp1.SetSvrInfo(g_Ip1, g_Port1);		//TELPER-REC 서버1 IP와 통신 포트 설정
        TlpRecAp2.SetSvrInfo(g_Ip2, g_Port2);		//TELPER-REC 서버2 IP와 통신 포트 설정
        logslog("[녹취] TlpRecAp1.SetSvrInfo(\"" + g_Ip1 + "\", \"" + g_Port1 + "\")");
        logslog("[녹취] TlpRecAp2.SetSvrInfo(\"" + g_Ip2 + "\", \"" + g_Port2 + "\")");

        g_AgtId = document.logoutForm.ctiid.value;		//상담사 ID
        g_AgtNm = document.logoutForm.usernm.value;		//상담사 명
        g_TelNo = document.logoutForm.ext.value;		//내선 번호

        TlpRecAp1.SetUserInfo(g_AgtNm, g_TelNo, g_AgtId, "0", "0");	//상담사 정보 설정
        TlpRecAp2.SetUserInfo(g_AgtNm, g_TelNo, g_AgtId, "0", "0");	//상담사 정보 설정
        logslog("[녹취] TlpRecAp1.SetUserInfo(\"" + g_AgtNm + "\", \"" + g_TelNo + "\", \"" + g_AgtId + "\", \"1\", \"1\")");
        logslog("[녹취] TlpRecAp2.SetUserInfo(\"" + g_AgtNm + "\", \"" + g_TelNo + "\", \"" + g_AgtId + "\", \"1\", \"1\")");

        var nRet1 = TlpRecAp1.Login();		//TELPER-REC 서버에 로그인
        var nRet2 = TlpRecAp2.Login();		//TELPER-REC 서버에 로그인
        logslog("[녹취] TlpRecAp1.Login()");
        logslog("[녹취] TlpRecAp2.Login()");

        if (!CheckError(nRet1)) {
            alert("[녹취] TELPER-REC 서버1에 로그인을 실패하였습니다.");
            logslog("[녹취] TlpRecAp1.Login Fail!");
            return;
        }
        logslog("[녹취] TlpRecAp1.Login Success!");

        if (!CheckError(nRet2)) {
            alert("[녹취] TELPER-REC 서버2에 로그인을 실패하였습니다.");
            logslog("[녹취] TlpRecAp2.Login Fail!");
            return;
        }
        logslog("[녹취] TlpRecAp2.Login Success!");

        g_bLogin1 = true;
        g_bLogin2 = true;
        g_IsRecording1 = false;
        g_IsRecording2 = false;

        logslog("[녹취] 상담사ID[" + g_AgtId + "] 상담사명[" + g_AgtNm + "] 내선번호[" + g_TelNo + "]");
    }

    /**
     *	TELPER-REC 서버 로그아웃
     */
    function rec_Logout() {
        logslog("[녹취] rec_Logout()");

        var nRet1 = TlpRecAp1.Logout();
        var nRet2 = TlpRecAp2.Logout();
        logslog("[녹취] TlpRecAp1.Logout()");
        logslog("[녹취] TlpRecAp2.Logout()");

        g_bLogin1 = false;
        g_bLogin2 = false;
        g_IsRecording1 = false;
        g_IsRecording2 = false;

        if (!CheckError(nRet1)) {
            alert("[녹취] TELPER-REC 서버1에 로그아웃을 실패하였습니다.");
            logslog("[녹취] TlpRecAp1.Logout Fail!");
        } else {
            logslog("[녹취] TlpRecAp1.Logout Success!");
        }

        if (!CheckError(nRet2)) {
            alert("[녹취] TELPER-REC 서버2에 로그아웃을 실패하였습니다.");
            logslog("[녹취] TlpRecAp2.Logout Fail!");
        } else {
            logslog("[녹취] TlpRecAp2.Logout Success!");
        }
        return;
    }

    /**
     * 녹음 ID 조회
     */
    function rec_GetRecordID() {
        logslog("[녹취] rec_GetRecordID()");

        var tempId = TlpRecAp1.GetRecordID();

        logslog("[녹취] TlpRecAp1.GetRecordID()");
        logslog("[녹취] RecordID[" + tempId + "]");

        return tempId;
    }

    /**
     * 부가 정보 저장
     */
    function rec_SaveCstInfo(recordId) {
        logslog("[녹취] rec_SaveCstInfo()");

        var cstName		= getValue("customerDetailForm", "customernm");		//고객명
        var cstTelNo	= getValue("customerDetailForm", "anino");	//고객전화번호

        //var recordId	= document.all.TlpRecAp1.GetRecordID();//rec_GetRecordID();
        var InOut;
        InOut = IO_KIND_INBOUND;	//호유형 인아웃 바운드 설정
        //InOut = IO_KIND_OUTBOUND;	//호유형 인아웃 바운드 설정

        TlpRecAp1.SetCstInfo("10001", cstName);		//고객명 설정
        TlpRecAp2.SetCstInfo("10001", cstName);		//고객명 설정
        TlpRecAp1.SetCstInfo("10002", cstTelNo);	//고객 CID 설정
        TlpRecAp2.SetCstInfo("10002", cstTelNo);	//고객 CID 설정

        logslog("[녹취] TlpRecAp1.SetCstInfo(\"10001\", \"" + cstName + "\")");
        logslog("[녹취] TlpRecAp2.SetCstInfo(\"10001\", \"" + cstName + "\")");
        logslog("[녹취] TlpRecAp1.SetCstInfo(\"10002\", \"" + cstTelNo + "\")");
        logslog("[녹취] TlpRecAp2.SetCstInfo(\"10002\", \"" + cstTelNo + "\")");

        //설정한 부가정보 저장
        var nRet1 = TlpRecAp1.SaveCstInfo(InOut, recordId);
        var nRet2 = TlpRecAp2.SaveCstInfo(InOut, recordId);
        logslog("[녹취] TlpRecAp1.SaveCstInfo(\"" + InOut + "\", \"" + recordId + "\")");
        logslog("[녹취] TlpRecAp2.SaveCstInfo(\"" + InOut + "\", \"" + recordId + "\")");

        if (!CheckError(nRet1)) {
            //alert("[녹취] 부가정보 저장에 실패하였습니다.");
            logslog("[녹취] TlpRecAp1.SaveCstInfo Fail!");
            return;
        }
        logslog("[녹취] TlpRecAp1.SaveCstInfo Success!");

        if (!CheckError(nRet2)) {
            //alert("[녹취] 부가정보 저장에 실패하였습니다.");
            logslog("[녹취] TlpRecAp2.SaveCstInfo Fail!");
            return;
        }
        logslog("[녹취] TlpRecAp2.SaveCstInfo Success!");
    }
