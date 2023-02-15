var btn_cti = "btn_cti";
var btn_mcti = "btn_mcti";
var img_path = "/powerservice/common/images/main/cti/";

var cti_images = new Array(12);
var cti_over_images = new Array(12);

// 이미지 경로 초기화
for (btn_cnt=1;btn_cnt<=12;btn_cnt++) {
    cti_images[btn_cnt] = new Array(2);
    cti_over_images[btn_cnt] = new Array(2);

    cti_images[btn_cnt][0] = new Image();
    cti_over_images[btn_cnt][0] = new Image();

    cti_images[btn_cnt][0].src = img_path + btn_cti + btn_cnt+ "_off.gif";
    cti_over_images[btn_cnt][0].src = img_path + btn_cti + btn_cnt+ "_on.gif";
}

// cti 버튼 액션
// 모두사용중지
function disableall() {
    setReady_btn(false);
    setMake_btn(false);
    setRecieve_btn(false);
    setDrop_btn(false);
    setHold_btn(false);
    setRetrieve_btn(false);
    setTransfer_btn(false);
//    setThird_btn(false);
    setCancel_btn(false);
    setNotready_btn(false);
    setLoginout_btn(false);
}

//레디 상태로 변경
function goto_ready() {
    disableall();

    setNotready_btn(true);
    setLoginout_btn(false);

    channelstatus_save("10", "10", "대기중");	// 상태업데이트(CTI, 대기);
}

//낫레디 상태로 변경
function goto_notready(status) {
    disableall();

    setReady_btn(true);
    setMake_btn(true);
    setNotready_btn(true);

    setLoginout_btn(false);

    /* 1: 업무
     * 2: 식사
     * 3: 휴식
     * 4: 교육
     * 5: 기타
     */
    logslog("[buttoncontrol.js] goto_notready status : " + status);

    switch(status) {
        case "1": channelstatus_save("10", "30", "업무중"); break;
        case "2": channelstatus_save("10", "40", "식사중"); break;
        case "3": channelstatus_save("10", "50", "휴식중"); break;
        case "4": channelstatus_save("10", "60", "교육중"); break;
        case "5": channelstatus_save("10", "70", "기타"); break;
    }
}

//발신중 상태로 변경
function goto_dialing() {
    disableall();

    setDrop_btn(true);

    channelstatus_save("10", "20", "통화중"); // 상태업데이트(CTI, 통화중);
}

//전환시도중 상태로 변경
function goto_consulting() {
    //모두불가능
    disableall();

    setCancel_btn(true);

    channelstatus_save("10", "20", "통화중"); // 상태업데이트(CTI, 통화중);
}

//전환시도 상태로 변경
function goto_consultation() {
    //모두불가능
    disableall();

    setTransfer_btn(true);
//    setThird_btn(true);
    setCancel_btn(true);

    channelstatus_save("10", "20", "통화중"); // 상태업데이트(CTI, 통화중);
}

//통화중 상태로 변경
function goto_calling() {
    //모두불가능
    disableall();

    setDrop_btn(true);
    setHold_btn(true);

    channelstatus_save("10", "20", "통화중"); // 상태업데이트(CTI, 통화중);
}

//후처리 상태로 변경
function goto_afterwork() {
    //모두불가능
    disableall();

    setReady_btn(true);
    setNotready_btn(true);
    setLoginout_btn(false);

    channelstatus_save("10", "80", "후처리"); // 상태업데이트(CTI, 후처리);
}

//보류 상태로 변경
function goto_Hold() {
    //모두불가능
    disableall();

    setRetrieve_btn(true);

    channelstatus_save("10", "20", "통화중"); // 상태업데이트(CTI, 통화중);
}

//보류 상태로 변경
function goto_UnHold(){
    goto_calling();
}

//전부 안쓰게
function goto_Logout(){
    disableall();

    setLoginout_btn(true);

    channelstatus_save("10", "90", "로그아웃"); // 상태업데이트(CTI, 로그아웃);
}

//다이얼링 상태로 변경
function goto_incoming(){
    disableall();

    setRecieve_btn(true);
}

// 버튼을 사용가능 상태로 변경
function set_btn(use, num, arr) {
    var btn_obj = document.getElementById(btn_cti + num + "_" + arr);

    if(use) {
        btn_obj.src = cti_over_images[num][arr].src;
        btn_obj.value = "on";
        btn_obj.style.cursor = "pointer";
    } else {
        btn_obj.src = cti_images[num][arr].src;
        btn_obj.value = "off";
        btn_obj.style.cursor = "none";

        if (num == "11") {
            btn_obj.style.cursor = "pointer";
        }
    }
}

//대기 가능여부 설정
function setReady_btn(use) {
  set_btn(use,"1","0");
}

//전화걸기 가능여부 설정
function setMake_btn(use) {
    set_btn(use,"2","0");
}

//전화받기 가능여부 설정
function setRecieve_btn(use) {
    set_btn(use,"3","0");
}

//전화끊기 가능여부 설정
function setDrop_btn(use) {
    set_btn(use,"4","0");
}

//보류 가능여부 설정
function setHold_btn(use) {
    set_btn(use,"5","0");
}

//보류해제 가능여부 설정
function setRetrieve_btn(use) {
    set_btn(use,"6","0");
}

// 전환요청 가능여부 설정
function setTransfer_btn(use) {
    set_btn(use,"7","0");
}

//전환취소 가능여부 설정
function setCancel_btn(use) {
    set_btn(use,"8","0");
}

//회의통화 가능여부 설정(이번엔 안씀)
function setThird_btn(use) {
//    set_btn(use,"12","0");
}

//휴식 가능여부 설정
function setNotready_btn(use) {
    set_btn(use,"9","0");
    set_btn(use,"10","0");

    $("#cti select").attr("disabled", use? "" : "disabled");
}

//로그인아웃 가능여부 설정
function setLoginout_btn(use) {
    set_btn(use,"11","0");
}
