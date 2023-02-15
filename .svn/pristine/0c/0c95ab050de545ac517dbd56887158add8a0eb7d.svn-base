/**
 *
 *
 */
var SoftPhone = (function() {
    var SoftPhoneNew = function( ctiObj ) {
        initSoftPhoneData();
        //initNotReadyReasonMenu();
        initButtonEvent();
    };

    SoftPhoneNew.CTI_STATUS_READY 			= "대기";
    SoftPhoneNew.CTI_STATUS_NOT_READY 		= "이석";
    SoftPhoneNew.CTI_STATUS_RINGING			= "호인입";
    SoftPhoneNew.CTI_STATUS_INITIATED  		= "발신";
    SoftPhoneNew.CTI_STATUS_BUSY 			= "통화중";
    SoftPhoneNew.CTI_STATUS_HELD 			= "보류";
    SoftPhoneNew.CTI_STATUS_ACW 			= "후처리";
    SoftPhoneNew.CTI_STATUS_CONSULTATION_1	= "호전환시도";
    SoftPhoneNew.CTI_STATUS_CONSULTATION_2	= "3자통화시도";
    SoftPhoneNew.CTI_STATUS_CONFERENCE		= "3자통화";
    SoftPhoneNew.CTI_STATUS_LOGOUT			= "로그아웃";

    // 이석사유 리스트
    SoftPhoneNew.NOT_READY_REASON_LIST = [
        {text: "업무",
            data: {code: 1},
            click: function (e) {
                changeCtiStatus(CTI_STATUS_NOT_READY,  this.item.data('data').code);
            }
        },
        {text: "식사",
            data: {code: 2},
            click: function (e) {
                changeCtiStatus(CTI_STATUS_NOT_READY,  this.item.data('data').code);
            }
        },
        {text: "휴식",
            data: {code: 3},
            click: function (e) {
                changeCtiStatus(CTI_STATUS_NOT_READY,  this.item.data('data').code);
            }
        },
        {text: "교육",
            data: {code: 4},
            click: function (e) {
                changeCtiStatus(CTI_STATUS_NOT_READY,  this.item.data('data').code);
            }
        },
        {text: "기타",
            data: {code: 5},
            click: function (e) {
                changeCtiStatus(CTI_STATUS_NOT_READY,  this.item.data('data').code);
            }
        }
    ];

    var arrButtonControlData = {};
    var arrColumn = {};
    var arrButtonClass = {};

    var ctiStatus = SoftPhoneNew.CTI_STATUS_LOGOUT;
    var agentStatus = -1;	// 0 이면 Ready, 0 이상은 NotReady 의 휴식사유코드


    var initSoftPhoneData = function() {
        // 상태에 따른 버튼 제어 데이터
        // 		O : 활성화
        // 		X : 비활성화
        // 		T : 토글
        arrButtonControlData[SoftPhoneNew.CTI_STATUS_READY] =
            ["X", "X", "X", "X", "X",   "X", "X", "X", "O", "X"];
        arrButtonControlData[SoftPhoneNew.CTI_STATUS_NOT_READY] =
            ["O", "O", "X", "X", "X",   "X", "X", "X", "X", "O"];
        arrButtonControlData[SoftPhoneNew.CTI_STATUS_RINGING] =
            ["X", "X", "O", "X", "X",   "X", "X", "X", "X", "X"];
        arrButtonControlData[SoftPhoneNew.CTI_STATUS_INITIATED] =
            ["X", "X", "O", "X", "X",   "X", "X", "X", "X", "X"];
        arrButtonControlData[SoftPhoneNew.CTI_STATUS_BUSY] =
            ["X", "X", "X", "O", "O",   "X", "O", "O", "X", "X"];
        arrButtonControlData[SoftPhoneNew.CTI_STATUS_HELD] =
            ["X", "X", "X", "X", "X",   "O", "X", "X", "X", "X"];
        arrButtonControlData[SoftPhoneNew.CTI_STATUS_ACW] =
            ["O", "X", "X", "X", "X",   "X", "X", "X", "O", "X"];
        arrButtonControlData[SoftPhoneNew.CTI_STATUS_CONSULTATION] =
            ["X", "X", "X", "X", "O",   "O", "X", "X", "O", "X"];
        arrButtonControlData[SoftPhoneNew.CTI_STATUS_CONFERENCE] =
            ["X", "X", "O", "X", "X",   "X", "X", "X", "X", "X"];
        arrButtonControlData[SoftPhoneNew.CTI_STATUS_LOGOUT] =
            ["X", "X", "X", "X", "X",   "X", "X", "X", "X", "T"];


        // 버튼 제어 데이터의 배열 위치
        arrColumn["btnReady"] 				= 0;
        arrColumn["btnMakeCall"] 			= 1;
        arrColumn["btnAnswer"] 				= 2;
        arrColumn["btnClearCall"] 			= 3;
        arrColumn["btnHold"] 				= 4;
        arrColumn["btnRelease"] 			= 5;
        arrColumn["btnTransfer"] 			= 6;
        arrColumn["btnReconnect"] 			= 7;
        arrColumn["btnNotReady"] 			= 8;
        arrColumn["btnLogout"]				= 9;


        // 버튼 제어 상태에 따른 클래스명
        arrButtonClass["btnReady"] = {};
        arrButtonClass["btnReady"]["O"] = "menu g1";
        arrButtonClass["btnReady"]["X"] = "menu g1_off";

        arrButtonClass["btnMakeCall"] = {};
        arrButtonClass["btnMakeCall"]["O"] = "menu g2";
        arrButtonClass["btnMakeCall"]["X"] = "menu g2_off";

        arrButtonClass["btnAnswer"] = {};
        arrButtonClass["btnAnswer"]["O"] = "menu g3";
        arrButtonClass["btnAnswer"]["X"] = "menu g3_off";

        arrButtonClass["btnClearCall"] = {};
        arrButtonClass["btnClearCall"]["O"] = "menu g4";
        arrButtonClass["btnClearCall"]["X"] = "menu g4_off";

        arrButtonClass["btnHold"] = {};
        arrButtonClass["btnHold"]["O"] = "menu g5";
        arrButtonClass["btnHold"]["X"] = "menu g5_off";

        arrButtonClass["btnRelease"] = {};
        arrButtonClass["btnRelease"]["O"] = "menu g6";
        arrButtonClass["btnRelease"]["X"] = "menu g6_off";

        arrButtonClass["btnTransfer"] = {};
        arrButtonClass["btnTransfer"]["O"] = "menu g7";
        arrButtonClass["btnTransfer"]["X"] = "menu g7_off";

        arrButtonClass["btnReconnect"] = {};
        arrButtonClass["btnReconnect"]["O"] = "menu g8";
        arrButtonClass["btnReconnect"]["X"] = "menu g8_off";

        arrButtonClass["btnNotReady"] = {};
        arrButtonClass["btnNotReady"]["O"] = "menu g9";
        arrButtonClass["btnNotReady"]["X"] = "menu g9_off";

        arrButtonClass["btnLogout"] = {};
        arrButtonClass["btnLogout"]["O"] = "turnon";
        arrButtonClass["btnLogout"]["T"] = "turnoff";
        arrButtonClass["btnLogout"]["X"] = "turnon disable";
    };

    var initNotReadyReasonMenu = function() {
        var cm = $("#not-ready-reason-menu").kendoMenuEx({
            dataSource: SoftPhoneNew.NOT_READY_REASON_LIST,
            anchor: "#btnNotReady",
            delay: 10000,
            event: "click"
        });
    };

    var initButtonEvent = function() {
       $("#btnReady").click( function() {
            changeCtiStatus(SoftPhoneNew.CTI_STATUS_READY);
        } );

        $("#btnMakeCall").click( function() {
            //changeCtiStatus(SoftPhoneNew.CTI_STATUS_BUSY);
            //ibCounselDivViewModel.callViewModel.incomingCall();
        } );

        $("#btnAnswer").click( function() {
            changeCtiStatus(SoftPhoneNew.CTI_STATUS_BUSY);
        } );

        $("#btnClearCall").click( function() {
            changeCtiStatus(SoftPhoneNew.CTI_STATUS_READY);
        } );

        $("#btnHold").click( function() {
            changeCtiStatus(SoftPhoneNew.CTI_STATUS_HELD);
        } );

        $("#btnRelease").click( function() {
            changeCtiStatus(SoftPhoneNew.CTI_STATUS_BUSY);
        } );

        $("#btnTransfer").click( function() {

        } );

        $("#btnLogout").click( function() {
            if(ctiStatus == SoftPhoneNew.CTI_STATUS_LOGOUT) {
            	changeCtiStatus(SoftPhoneNew.CTI_STATUS_NOT_READY,  3);
            } else {
                changeCtiStatus(SoftPhoneNew.CTI_STATUS_LOGOUT);
            }
        } );
    };

    // 버튼 활성화 제어
    var changeCtiButtons = function(status) {
        $.each(arrColumn, function(key, val) {
            applyButtonClass(key, status);
        });
    };


    var applyButtonClass = function(buttonId, status) {
        $.each(arrButtonClass[buttonId], function(key, val) {
            $("#" + buttonId).removeClass(val);
        });

        $("#" + buttonId).addClass(arrButtonClass[buttonId][arrButtonControlData[status][arrColumn[buttonId]]]);
        arrButtonControlData[status][arrColumn[buttonId]] == "X" ? $("#" + buttonId).prop("disabled", true) : $("#" + buttonId).prop("disabled", false);
    };

    var showCtiStatus = function(status, reasonCode) {
        if(status == SoftPhoneNew.CTI_STATUS_NOT_READY) {
            $.each(SoftPhoneNew.NOT_READY_REASON_LIST, function(i, val) {
                if(val.data.code == reasonCode) {
                    $("#cti-status").text(val.text);
                }
            });
        } else {
            $("#cti-status").text(status);
        }
    };

    var changeCtiStatus = function(status, reasonCode) {
        ctiStatus = status;
        changeCtiButtons(status);
        showCtiStatus(status, reasonCode);
    };

    SoftPhoneNew.prototype.changeCtiStatus = function(status, reasonCode) {
        changeCtiStatus(status, reasonCode);
    };

    SoftPhoneNew.prototype.getCtiStatus = function() {
        return ctiStatus;
    };

    return SoftPhoneNew ;
}());

var softPhone = new SoftPhone();