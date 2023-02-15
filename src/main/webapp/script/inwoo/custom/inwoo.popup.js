﻿var inwoo = inwoo || {};
inwoo.view = inwoo.view || {};
inwoo.view.popup = inwoo.view.popup || {};

/**
 * inwoo.view.popupCenter
 * 팝업을 화면 가운데에 띄운다
 *
 * @param    : url, 팝업명, 높이, 너비, 스크롤바 추가
 * @return  : 팝업obj
 * @author  : 한숙향
 * @date    : 2013/04/01
 */
/**
 * openFaqListPopup
 * FAQ 조회 팝업
 *
 * @param   : 없음.
 * @return  : 없음.
 * @author  : 차건호
 * @date    : 2015/04/24
 */
openFaqListPopup = function(selectionfg, searchValue, cons_typ1_cd, cons_typ2_cd, cons_typ3_cd) {
    var url = inwoo.config.contextRoot + "/knowledge/faq-dtl/faq-list-popup.do?selectionfg=" + (selectionfg == true ? selectionfg : '') + "&searchValue=" + (searchValue == null ? "" : escape(encodeURIComponent(searchValue)))
               +"&cons_typ1_cd=" + (cons_typ1_cd == null ? "" : cons_typ1_cd) + "&cons_typ2_cd=" + (cons_typ2_cd == null ? "" : cons_typ2_cd) + "&cons_typ3_cd=" + (cons_typ3_cd == null ? "" : cons_typ3_cd);
    var name = "faqListPopup";
    var width = 796;
    var height = 525;

    var obj = inwoo.view.popupCenter(url, name, width, height);
    obj.focus();
};

/**
* openFaqListViewPopup
* FAQ 상세조회 팝업
*
* @param   : 없음.
* @return  : 없음.
* @author  : 정훈
* @date    : 2015/10/06
*/
openFaqListViewPopup = function(selectionfg, searchValue, cons_typ1_cd, cons_typ2_cd, cons_typ3_cd) {
   var url = inwoo.config.contextRoot + "/knowledge/faq-dtl/faq-list-view-popup.do?selectionfg=" + (selectionfg == true ? selectionfg : '') + "&searchValue=" + (searchValue == null ? "" : escape(encodeURIComponent(searchValue)))
              +"&cons_typ1_cd=" + (cons_typ1_cd == null ? "" : cons_typ1_cd) + "&cons_typ2_cd=" + (cons_typ2_cd == null ? "" : cons_typ2_cd) + "&cons_typ3_cd=" + (cons_typ3_cd == null ? "" : cons_typ3_cd);
   var name = "faqListViewPopup";
   var width = 686;
   var height = 677;

   var obj = inwoo.view.popupCenter(url, name, width, height);
   obj.focus();
};

/**
 * openFaqViewPopup
 * FAQ 상세정보 팝업
 *
 * @param   : 없음.
 * @return  : 없음.
 * @author  : 차건호
 * @date    : 2015/04/24
 */
openFaqViewPopup = function(faq_id, search_val, page_typ, search_typ) {

    var url = inwoo.config.contextRoot + "/knowledge/faq-dtl/faq-view-popup.do?faq_id=" + faq_id + "&search_val=" + (search_val == null ? "" : escape(encodeURIComponent(search_val))) + "&page_typ=" + page_typ + "&search_typ=" + search_typ;
    var name = "faqViewPopup";
    var width = 650;
    var height = 622;

    var obj = inwoo.view.popupCenter(url, name, width, height);
    obj.focus();
};

/**
 * openFaqDmndViewPopup
 * FAQ 요청 상세정보 팝업
 *
 * @param   : 없음.
 * @return  : 없음.
 * @author  : 차건호
 * @date    : 2015/04/24
 */
openFaqDmndViewPopup = function(faq_dmnd_id) {
    var url = inwoo.config.contextRoot + "/knowledge/faq-dmnd/faq-dmnd-view-popup.do?faq_dmnd_id=" + faq_dmnd_id;
    var name = "faqDmndViewPopup";
    var width = 650;
    var height = 622;

    var obj = inwoo.view.popupCenter(url, name, width, height);
    obj.focus();
};

/**
 * openAncmMtrListPopup
 * 공지사항조회 팝업
 *
 * @param   : 없음.
 * @return  : 없음.
 * @author  : 차건호
 * @date    : 2015/04/24
 */
openAncmMtrListPopup = function(pageType, searchValue) {
    var url = inwoo.config.contextRoot + "/knowledge/ancm-mtr-dtl/ancm-mtr-list-popup.do?pageType=" + pageType + "&searchValue=" + (searchValue == null ? "" : escape(encodeURIComponent(searchValue)));
    var name = "ancmMtrListPopup";
    var width = 796;
    var height = 501;

    var obj = inwoo.view.popupCenter(url, name, width, height);
    obj.focus();
};

/**
 * openAncmMtrViewPopup
 * 공지사항 상세정보 팝업
 *
 * @param   : 없음.
 * @return  : 없음.
 * @author  : 차건호
 * @date    : 2015/04/24
 */
openAncmMtrViewPopup = function(ancm_mtr_id, searchValue, pageType, searchType) {
    var url = inwoo.config.contextRoot + "/knowledge/ancm-mtr-dtl/ancm-mtr-view-popup.do?ancm_mtr_id=" + ancm_mtr_id + "&searchValue=" + (searchValue == null ? "" : escape(encodeURIComponent(searchValue))) + "&pageType=" + pageType + "&searchType=" + searchType;
    var name = "ancmMtrViewPopup";
    var width = 650;
    var height = 581;

    var obj = inwoo.view.popupCenter(url, name, width, height);
    obj.focus();
};

/**
 * openNobdListPopup
 * 게시판 조회 팝업
 *
 * @param   : 없음.
 * @return  : 없음.
 * @author  : 차건호
 * @date    : 2015/04/20
 */
openNobdListPopup = function(nobd_typ_id, searchValue) {
    if (searchValue == null) {
        searchValue = "";
    }

    var url = inwoo.config.contextRoot + "/knowledge/nobd-typ/view/list-popup/" + nobd_typ_id + "?searchValue=" + escape(encodeURIComponent(searchValue));
    var name = "NobdListPopup";
    var width = 796;
    var height = 501;

    var obj = inwoo.view.popupCenter(url, name, width, height);
    obj.focus();
};

/**
 * openNobdViewPopup
 * 게시판 상세정보 팝업
 *
 * @param   : 없음.
 * @return  : 없음.
 * @author  : 차건호
 * @date    : 2015/04/24
 */
openNobdViewPopup = function(nobd_typ_id, nobd_id, searchValue, searchType) {
    var url = inwoo.config.contextRoot + "/knowledge/nobd-typ/view/view-popup/" + nobd_typ_id + "?nobd_id=" + nobd_id + "&searchValue=" + escape(encodeURIComponent(searchValue)) + "&searchType=" + searchType;
    var name = "nobdViewPopup";
    var width = 650;
    var height = 582;

    var obj = inwoo.view.popupCenter(url, name, width, height);
    obj.focus();
};

/**
 * openExccConsExmpListPopup
 * 우수상담사례조회 팝업
 *
 * @param   : 없음.
 * @return  : 없음.
 * @author  : 차건호
 * @date    : 2015/04/16
 */
openExccConsExmpListPopup = function(searchValue) {
    var url = inwoo.config.contextRoot + "/knowledge/excc-cons-exmp/excc-cons-exmp-list-popup.do?searchValue=" + (searchValue == null ? "" : escape(encodeURIComponent(searchValue)));
    var name = "exccConsExmpListPopup";
    var width = 750;
    var height = 500;

    var obj = inwoo.view.popupCenter(url, name, width, height);
    obj.focus();
};

/**
 * openExccConsExmpViewPopup
 * 우수상담사례 상세정보 팝업
 *
 * @param   : 없음.
 * @return  : 없음.
 * @author  : 차건호
 * @date    : 2015/04/24
 */
openExccConsExmpViewPopup = function(excc_cons_id, search_val) {
    var url = inwoo.config.contextRoot + "/knowledge/excc-cons-exmp/excc-cons-exmp-view-popup.do?excc_cons_id=" + excc_cons_id + "&word=" + (search_val == null ? "" : escape(encodeURIComponent(search_val)));
    var name = "exccConsExmpViewPopup";
    var width = 650;
    var height = 581;

    var obj = inwoo.view.popupCenter(url, name, width, height);
    obj.focus();
};

/**
 * openConsScrtListPopup
 * 상담스크립트조회 팝업
 *
 * @param   : 없음.
 * @return  : 없음.
 * @author  : 차건호
 * @date    : 2015/04/22
 */
openConsScrtListPopup = function(cons_typ1_cd, cons_typ2_cd, cons_typ3_cd) {
    var url = inwoo.config.contextRoot + "/knowledge/cons-scrt/cons-scrt-list-popup.do?cons_typ1_cd=" + (cons_typ1_cd == null ? "" : cons_typ1_cd) + "&cons_typ2_cd=" + (cons_typ2_cd == null ? "" : cons_typ2_cd) + "&cons_typ3_cd=" + (cons_typ3_cd == null ? "" : cons_typ3_cd);
    var name = "consScrtListPopup";
    var width = 750;
    var height = 520;

    var obj = inwoo.view.popupCenter(url, name, width, height);
    obj.focus();
};

/**
 * openConsScrtListPopup
 * 상담스크립트상세조회 팝업
 *
 * @param   : 없음.
 * @return  : 없음.
 * @author  : 정훈
 * @date    : 2015/10/07
 */
openConsScrtListViewPopup = function(cons_typ1_cd, cons_typ2_cd, cons_typ3_cd) {
    var url = inwoo.config.contextRoot + "/knowledge/cons-scrt/cons-scrt-list-view-popup.do?cons_typ1_cd=" + (cons_typ1_cd == null ? "" : cons_typ1_cd) + "&cons_typ2_cd=" + (cons_typ2_cd == null ? "" : cons_typ2_cd) + "&cons_typ3_cd=" + (cons_typ3_cd == null ? "" : cons_typ3_cd);
    var name = "consScrtListViewPopup";
    var width = 686;
    var height = 876;

    var obj = inwoo.view.popupCenter(url, name, width, height);
    obj.focus();
};

/**
 * openConsScrtViewPopup
 * 상담스크립트 상세정보
 *
 * @param   : 없음.
 * @return  : 없음.
 * @author  : 차건호
 * @date    : 2015/04/22
 */
openConsScrtViewPopup = function(cons_scrt_id, searchValue, searchType) {
    var url = inwoo.config.contextRoot + "/knowledge/cons-scrt/cons-scrt-view-popup.do?cons_scrt_id=" + cons_scrt_id + "&word=" + (searchValue == null ? "" : escape(encodeURIComponent(searchValue))) + "&searchType=" + searchType;
    var name = "consScrtViewPopup";
    var width = 650;
    var height = 622;

    var obj = inwoo.view.popupCenter(url, name, width, height);
    obj.focus();
};

/**
 * openUserSelectPopup
 * 상담사 선택 팝업을 띄운다(한명선택)
 *
 * @param   : 없음
 * @return  : 없음
 * @author  : 배윤정
 * @date    : 2013/04/01
 */
function openUserSelectPopup() {
    var url = inwoo.config.contextRoot + "/common/user-select-popup.do";
    var name = "userSelectPopup";
    var width = 670;
    var height = 520;

    inwoo.view.popupCenter(url, name, width, height).focus();
}

/**
 * receiverSelectPopup
 * 상담사 조회(다중선택)
 *
 * @param   : 없음
 * @return  : 없음
 * @author  : 이주희
 * @date    : 2014/04/23
 */
openReceiverSelectPopup = function () {
    var url =  inwoo.config.contextRoot + "/common/receiver-select-popup.do";
    var name = "receiverSelectPopup";
    var width = 860;
    var height = 490;

    var obj = inwoo.view.popupCenter(url, name, width, height);
    obj.focus();
};

/**
 * openConsTypSelectPopup
 * 상담유형 조회
 *
 * @param   : 없음
 * @return  : 없음
 * @author  : 차건호
 * @date    : 2015/04/24
 */
function openConsTypSelectPopup() {
    var url = inwoo.config.contextRoot + "/knowledge/cons-typ/cons-typ-select-popup.do";
    var name = "consTypSelectPopup";
    var width = 796;
    var height = 524;

    inwoo.view.popupCenter(url, name, width, height);
}

/**
 * openPasswordChangePopup
 * 비밀번호 변경 팝업
 *
 * @param  : 로그인ID, 센터코드
 * @return : 없음.
 * @author : 배윤정
 * @date   : 2013/10/24
 */
function openPasswordChangePopup(psLgnId, psCntrCd) {
    if (typeof(psLgnId) != "string" || psLgnId == null) {
        psLgnId = "";
    }
    if (typeof(psCntrCd) != "string" || psCntrCd == null) {
        psCntrCd = "";
    }

    var url = inwoo.config.contextRoot + "/common/password-change-popup.do?lgnid=" + psLgnId + "&cntrcd=" + psCntrCd;
    var name = "passwordChangePopup";
    var width = 546;
    var height = 321;

    var obj = inwoo.view.popupCenter(url, name, width, height);
    obj.focus();
}

/**
 * openSuctViewPopup
 * 퀵링크 조회팝업
 *
 * @param   : 없음.
 * @return  : 없음.
 * @author  : 차건호
 * @date    : 2015/04/23
 */
openSuctViewPopup = function(ppup_scrn_nm, suct_ppup_div_cd, suct_set_id, scrn_widt_vl, scrn_hght_vl) {
    var url = inwoo.config.contextRoot + "/knowledge/suct/suct-view-popup.do?ppup_scrn_nm=" +  encodeURI(encodeURIComponent(ppup_scrn_nm))
            + "&suct_ppup_div_cd=" + suct_ppup_div_cd + "&suct_set_id=" + suct_set_id + "&height=" + scrn_hght_vl;
    var name = suct_set_id + "Popup";
    var width = scrn_widt_vl;
    var height = scrn_hght_vl;
/*
    var obj = inwoo.view.popupCenter(url, name, width, height);
    obj.focus();*/

    var top = 0;
    var left = 0;
    var shape = "width=" + width + ", height=" + height + ", top="  + top + ", left=" + left + "location=no, menubar=no, resizable=no, status=no, titlebar=yes, toolbar=no";

    window.open(url, name, shape);
};

/**
 * openPgmAthrPopup
 * 프로그램 추가 팝업
 *
 * @param   : 없음.
 * @return  : 없음.
 * @author  : 한숙향
 * @date    : 2013/10/04
 */
openPgmAthrPopup = function(psLvlCnt, psAthrCd, psHgrnPgmCd, psGdsCd) {
    var sUrl = inwoo.config.contextRoot + "/syst/athr/pgm-athr-popup.do?lvlcnt=" + psLvlCnt + "&athrcd=" + psAthrCd + "&hgrnpgmcd=" + psHgrnPgmCd + "&gdscd=" + psGdsCd;
    var sName = "pgmAthrPopup";
    var nWidth = 796;
    var nHeight = 330;

    var obj = inwoo.view.popupCenter(sUrl, sName, nWidth, nHeight);
    obj.focus();
};

/**
 * openMessagePopup
 * 쪽지함 팝업창 오픈
 *
 * @param    : 없음.
 * @return  : 없음.
 * @author  : 한숙향
 * @date    : 2013/04/01
 */
openMessagePopup = function() {
    var url = inwoo.config.contextRoot + "/chnl/nmsg-dtl/nmsg-dtl-popup.do";
    var name = "nmsgDtlPopup";
    var width = 796;
    var height = 675;

    var obj = inwoo.view.popupCenter(url, name, width, height);
    obj.focus();
};

/**
 * openTotalSearchPopup
 * 통합검색 팝업오픈(DB정보에서만 검색)
 *
 * @param   : 검색유형, 검색어
 * @return  : 없음.
 * @author  : 이희철
 * @date    : 2015/04/01
 */
openTotalSearchPopup = function(psSrchTyp, psSrchWord) {
    if (typeof(psSrchTyp) != "string" || psSrchTyp == null) {
        psSrchTyp = "";
    }
    if (typeof(psSrchWord) != "string" || psSrchWord == null) {
        psSrchWord = "";
    } else {
        psSrchWord = escape(encodeURIComponent(psSrchWord));
    }

    var sUrl = inwoo.config.contextRoot + "/total-search/total-search.do?srchtyp=" + psSrchTyp + "&srchword=" + psSrchWord;
    var sName = "totalSearchPopup";
    var nWidth = 1015;
    var nHeight = 900;

    var oPopup = inwoo.view.popupCenter(sUrl, sName, nWidth, nHeight, "yes");
    oPopup.focus();
};

/* TODO : 아래는 정리필요!!  */

/**
 * center_list_popup
 * 센터 목록 팝업
 *
 * @param   : 없음.
 * @return  : 없음.
 * @author  : 김장미
 * @date    : 2013/04/01
 */
center_list_popup = function() {
    var url = "${pageContext.request.contextPath}/user/center/center_list_popup.do";
    var name = "centerListPopup";
    var width = 700;
    var height = 500;

    var obj = inwoo.view.popupCenter(url, name, width, height);
    obj.focus();
};

/**
 * agencies_view_popup
 * 미리보기로 화면을 팝업한다.
 *
 * @param   : 없음
 * @return  : 없음
 * @author  : 정용재
 * @date    : 2013/07/11
 *
 * 사용하지 않는 듯 (15.04.24 차건호)
 */
function agencies_view_popup() {
    var shape;
    var path = getValue("detailForm", "homepage");

    if (path.indexOf("http://") < 0) {
        alert("http://를 포함한  주소 모두 입력 해 주시기 바랍니다.");
        return;
    }

    shape = "width=1000, height=800, top=0, left=0 ,channelmode=no, directories=yes, location=yes";
    shape += ", menubar=yes, resizable=yes, scrollbars=yes, status=yes, titlebar=yes, toolbar=yes";

    window.open(path, getValue("detailForm","agenciesnm"), shape);
}

/**
 * openZipPopup
 * 우편번호조회
 *
 * @param   : 없음
 * @return  : 없음
 * @author  : 최현식
 * @date    : 2013/10/14
 */
function openZipPopup() {
    var url = inwoo.config.contextRoot + "/common/zipcd-search-popup.do";
    var name = "zipPopup";
    var width = 750;
    var height = 450;
    var top = 0;
    var left = 0;
    var shape = "width=" + width + ", height=" + height + ", top="  + top + ", left=" + left + "location=yes, menubar=no, resizable=yes, status=no, titlebar=yes, toolbar=no";

    window.open(url, "zipcdPopup", shape);
}

/**
 * openZipPopupDaum
 * 우편번호조회
 *
 * @param   : 없음
 * @return  : 없음
 * @author  : 차건호
 * @date    : 2015/05/14
 */
function openZipPopupDaum() {
    var url = inwoo.config.contextRoot + "/common/zipcd-search-popup-daum.do";
    var name = "ZipPopupDaum";
    var width = 700;
    var height = 600;

    inwoo.view.popupCenter(url, name, width, height).focus();
}

/**
 * openExcelItemPopup
 * 엑셀항목보기
 *
 * @param   : 없음
 * @return  : 없음
 * @author  : 배윤정
 * @date    : 2013/10/11
 */
function openExcelItemPopup(psXlsId, psCntrCd) {
    var url = inwoo.config.contextRoot + "/syst/xls/xls-item-popup.do?xlsid="+ psXlsId +"&cntrcd=" + psCntrCd;
    var name = "excelItemPopup";
    var width = 796;
    var height = 376;

    inwoo.view.popupCenter(url, name, width, height);
}

/**
 * openExamPaperSelectPopup
 * 시험지 검색 팝업
 *
 * @param   : 없음.
 * @return  : 없음.
 * @author  : 최현식
 * @date    : 2013/10/04
 */
openEmshSelectPopup = function(openmode, exampaperidexcept) {
    var url = inwoo.config.contextRoot + "/exam-admn/emsh-select-popup.do?openmode=" + openmode + "&emsh_id_except=" + exampaperidexcept;
    var name = "examPaperPopup";
    var width = 860;
    var height = 400;

    var obj = inwoo.view.popupCenter(url, name, width, height);
    obj.focus();
};


/**
 * openChklistPopup
 * 체크리스트조회 팝업
 *
 * @param   : 없음.
 * @return  : 없음.
 * @author  : 유오성
 * @date    : 2014/07/04
 */
openChklistPopup = function(majorcd, middlecd, minorcd) {
    var url = inwoo.config.contextRoot + "/knowledge/csel-checklist/checklist-list-popup.do?majorcd=" + (majorcd == null ? "" : majorcd) + "&middlecd=" + (middlecd == null ? "" : middlecd) + "&minorcd=" + (minorcd == null ? "" : minorcd);
    var name = "chklistPopup";
    var width = 750;
    var height = 520;

    var obj = inwoo.view.popupCenter(url, name, width, height);
    obj.focus();
};

/**
 * openChklistViewPopup
 * 체크리스트 상세정보
 *
 * @param   : 없음.
 * @return  : 없음.
 * @author  : 유오성
 * @date    : 2014/07/01
 */
openChecklistViewPopup = function(chklistid, searchValue, searchType) {
    var url = inwoo.config.contextRoot + "/knowledge/csel-checklist/checklist-view-popup.do?chklistid=" + chklistid + "&word=" + (searchValue == null ? "" : escape(encodeURIComponent(searchValue))) + "&searchType=" + searchType;
    var name = "chklistViewPopup";
    var width = 725;
    var height = 803;

    var obj = inwoo.view.popupCenter(url, name, width, height);
    obj.focus();
};

/**
 * openCoachingViewPopup
 * QA코칭 상세정보 팝업
 *
 * @param   : 없음.
 * @return  : 없음.
 * @author  : 최현식
 * @date    : 2014/05/30
 */
openCoachingViewPopup = function(coachingid) {
    var url = inwoo.config.contextRoot + "/qaeval/coaching-view-popup.do?coachingid=" + coachingid;
    var name = "coachingViewPopup";
    var width = 725;
    var height = 618;

    var obj = inwoo.view.popupCenter(url, name, width, height);
    obj.focus();
};

/**
 * openQaevalPlanResultPopup
 * 평가계획별 결과 팝업
 *
 * @param   : 없음
 * @return  : 없음
 * @author  : 최현식
 * @date    : 2014/06/19
 */
function openEvltReslItemDtl(evlt_plan_id, trpr_id) {
    var url = inwoo.config.contextRoot + "/evlt-admn/evlt-resl-dtl-popup.do?evlt_plan_id=" + evlt_plan_id + "&trpr_id=" + trpr_id;
    var name = "";
    var width = 1000;
    var height = 790;

    inwoo.view.popupCenter(url, name, width, height).focus();
}

/**
 * openCoachingUserSelectPopup
 * 코칭 상담원 선택 팝업을 띄운다(한명선택)
 *
 * @param   : 없음
 * @return  : 없음
 * @author  : 최현식
 * @date    : 2014/06/12
 */
function openCoachingUserSelectPopup() {
    var url = inwoo.config.contextRoot + "/qaeval/coaching-user-select-popup.do";
    var name = "userCoachingSelectPopup";
    var width = 796;
    var height = 497;

    inwoo.view.popupCenter(url, name, width, height).focus();
}

/**
 * 캠페인 실행팝업(설문지)
 *
 * @param   : 캠페인ID, 대상고객리스트ID, 소분류리스트ID, 대상고객ID, 설문지ID, 인덱스
 * @return  : 없음.
 * @author  : 최현식
 * @date    : 2014/07/11
 */
function openSurveyRegPopup (pagingindex, campaignid, targetlistid, subtargetlistid, targetcustid, surveyid) {
    var url = inwoo.config.contextRoot + "/main/div/survey-reg-popup.do?campaignid=" + campaignid + "&targetlistid=" + targetlistid
            + "&subtargetlistid=" + subtargetlistid + "&targetcustid=" + targetcustid + "&surveyid=" + surveyid + "&pagingindex=" + pagingindex;
    var name = "campaignRegPopup";
    var width = 850;
    var height = 800;

    inwoo.view.popupCenter(url, name, width, height).focus();
}

/**
 * 캠페인 미리보기팝업(설문지)
 *
 * @param   : 설문지ID
 * @author  : 정 훈
 * @date    : 2015/9/15
 */
function openSurveyPreviewPopup (surs_id) {
    var url = inwoo.config.contextRoot + "/campaign/surs/surs-view-popup.do?surs_id=" + surs_id;
    var name = "openSurveyPreviewPopup";
    var width = 850;
    var height = 780;

    inwoo.view.popupCenter(url, name, width, height).focus();
}
/**
 * 캠페인 실행팝업(스크립트)
 *
 * @param   :
 * @return  : 없음.
 * @author  : 최현식
 * @date    : 2014/07/11
 */
function openScriptRegPopup (pagingindex, campaignid, targetlistid, subtargetlistid, targetcustid, scriptid) {
    var url = inwoo.config.contextRoot + "/main/div/script-reg-popup.do?campaignid=" + campaignid + "&targetlistid=" + targetlistid
            + "&subtargetlistid=" + subtargetlistid + "&targetcustid=" + targetcustid + "&scriptid=" + scriptid + "&pagingindex=" + pagingindex;
    var name = "campaignRegPopup";
    var width = 800;
    var height = 800;

    inwoo.view.popupCenter(url, name, width, height).focus();
}

/**
 * openExamPaperSelectPopup
 * 시험지 미리보기
 *
 * @param   : 없음.
 * @return  : 없음.
 * @author  : 최현식
 * @date    : 2014/09/04
 */
openEmshPreviewPopup = function(emsh_id) {
    var url = inwoo.config.contextRoot + "/exam-admn/emsh-preview-popup.do?emsh_id=" + emsh_id;
    var name = "examPaperPreviewPopup";
    var width = 800;
    var height = 800;

    var obj = inwoo.view.popupCenter(url, name, width, height);
    obj.focus();
};

/**
 * openSearchExamPopup
 * 시험 조회 팝업
 *
 * @param   : 없음.
 * @return  : 없음.
 * @author  : 최현식
 * @date    : 2015/04/01
 */
openSearchExamPopup = function(psExamid, psExamTrprId) {
    var sUrl = inwoo.config.contextRoot + "/mypage/exam/exam-search-popup.do?examid=" + psExamid + "&examtrprid=" + psExamTrprId;
    var sName = "searchExamPopup";
    var nWidth = 1100;
    var nHight = 800;

    var oPopup = inwoo.view.popupCenter(sUrl, sName, nWidth, nHight);
    oPopup.focus();
};


/**
 * openMyPagePopup
 * Mypage팝업
 *
 * @param   : 없음
 * @return  : 없음
 * @author  : 김은지
 * @date    : 2014/06/09
 */
function openMyPagePopup() {
    var url = inwoo.config.contextRoot + "/mypage/user/user.do?popFg=Y";
    var name = "userPopup";
    var width = 600;
    var height = 620;

    inwoo.view.popupCenter(url, name, width, height).focus();
}

/**
 * openSmsSendPopup
 * 문자메시지 발송 팝업
 *
 * @param   : 없음.
 * @return  : 없음.
 * @author  : 이희철
 * @date    : 2013/10/04
 */
openSmsSendPopup = function(clph_no, consno, cust_id, cust_nm, cntr_rprs_tlno, smssendmsg) {
    clph_no = (clph_no == null) ? "" : clph_no;
    consno = (consno == null) ? "" : consno;
    cust_id = (cust_id == null) ? "" : cust_id;
    cust_nm = (cust_nm == null) ? "" : escape(encodeURIComponent(cust_nm));
    cntr_rprs_tlno = (cntr_rprs_tlno == null) ? "0264753800" : cntr_rprs_tlno;
    smssendmsg = (smssendmsg == null) ? "" : escape(encodeURIComponent(smssendmsg));

    var url = inwoo.config.contextRoot + "/chnl/chat/chat-sndg-popup.do?clph_no=" + clph_no + "&consno=" + consno
            + "&cust_id=" + cust_id + "&cust_nm=" + cust_nm + "&cntr_rprs_tlno="+ cntr_rprs_tlno + "&smssendmsg=" + smssendmsg;
    var name = "smsSendPopup";
    var width = 1100;
    var height = 780;

    var obj = inwoo.view.popupCenter(url, name, width, height);
    obj.focus();
};

/**
 * openEmailSendPopup
 *  Email 발송 팝업
 *
 * @param   : 없음.
 * @return  : 없음.
 * @author  : 이희철
 * @date    : 2013/10/04
 */
openEmailSendPopup = function(email_addr, cust_id, cust_nm) {
    var url = inwoo.config.contextRoot + "/chnl/email/email-sndg-popup.do?email_addr=" + email_addr
            + "&cust_id=" + cust_id + "&cust_nm=" + escape(encodeURIComponent(cust_nm));
    var name = "emailSendPopup";
    var width = 1250;
    var height = 680;

    var obj = inwoo.view.popupCenter(url, name, width, height);
    obj.focus();
};


/**
 * openCmpgDetailViewPopup
 * 캠페인정보 상세보기
 *
 * @param   : 없음.
 * @return  : 없음.
 * @author  : 차건호
 * @date    : 2015/04/30
 */
openCmpgDetailViewPopup = function(cust_id) {
    var url = inwoo.config.contextRoot + "/campaign/cmpg/cmpg-detail-view-popup.do?" + "cmpg_id=" + cust_id;
    var name = "cmpgDetailViewPopup";
    var width = 600;
    var height = 230;

    var obj = inwoo.view.popupCenter(url, name, width, height);
    obj.focus();
};


/**
 * openImgPopup
 * 이미지 팝업
 *
 * @param   : 없음.
 * @return  : 없음.
 * @author  : 차건호
 * @date    : 2015/04/22
 */
openImgPopup = function(img_url_addr) {
    var url = inwoo.config.contextRoot + "/common/img-popup.do?img_url_addr=" + img_url_addr;
    var name = "openImgPopup";
    var width = 750;
    var height = 750;

    var obj = inwoo.view.popupCenter(url, name, width, height);
    obj.focus();
};

/**
 * openEmailTpPopup
 * 이메일 템플릿 선택 팝업을 띄운다(1건)
 *
 * @param   : 없음
 * @return  : 없음
 * @author  : 차건호
 * @date    : 2013/04/01
 */
function openEmailtpSelectPopup() {
    var url = inwoo.config.contextRoot + "/common/emailtp-select-popup.do";
    var name = "emailtpSelectPopup";
    var width = 770;
    var height = 510;

    inwoo.view.popupCenter(url, name, width, height).focus();
}

/**
 * openSmsmsgtpSelectPopup
 * 문자 템플릿 선택 팝업을 띄운다(1건)
 *
 * @param   : 없음
 * @return  : 없음
 * @author  : 차건호
 * @date    : 2013/04/01
 */
function openSmsmsgtpSelectPopup() {
    var url = inwoo.config.contextRoot + "/common/smsmsgtp-select-popup.do";
    var name = "smsmsgtpSelectPopup";
    var width = 770;
    var height = 490;

    inwoo.view.popupCenter(url, name, width, height).focus();
}


/**
 * openFcbkPopup
 * 페이스북 연동 팝업
 *
 * @param   : 없음.
 * @return  : 없음.
 * @author  : 유오성
 * @date    : 2015/06/10
 */
openFcbkPopup = function() {
    var sUrl = inwoo.config.contextRoot + "/chnl/sns/fcbk.do";
    var sName = "fcbkPopup";
    var nWidth = 1100;
    var nHeight = 780;

    inwoo.view.popupCenter(sUrl, sName, nWidth, nHeight).focus();
};

/**
 * openFcbkViewPopup
 * 페이스북 상세정보 팝업
 *
 * @param   : 없음.
 * @return  : 없음.
 * @author  : 유오성
 * @date    : 2015/06/19
 */
openFcbkViewPopup = function(psFcbkId) {
    var sUrl = inwoo.config.contextRoot + "/chnl/sns/fcbk-view-popup.do?fcbkid=" + psFcbkId;
    var sName = "fcbkViewPopup";
    var nWidth = 725;
    var nHeight = 682;

    inwoo.view.popupCenter(sUrl, sName, nWidth, nHeight).focus();
};


/**
 * openCompSelectPopup
 * 고객사/협력사 정보 팝업
 *
 * @param   : 없음
 * @return  : 없음
 * @author  : 정훈
 * @date    : 2015/08/03
 */
function openCompSelectPopup() {
    var url = inwoo.config.contextRoot + "/biz/comp-popup.do";
    var name = "compSelectPopup";
    var width = 770;
    var height = 490;

    inwoo.view.popupCenter(url, name, width, height).focus();
};


/**
 * openCompSearchPopup
 * 고객사/협력사 서치 팝업
 *
 * @param   : 없음
 * @return  : 없음
 * @author  : 정훈
 * @date    : 2015/08/03
 */
function openCompSearchPopup() {
    var url = inwoo.config.contextRoot + "/biz/comp-search-popup.do";
    var name = "compSearchPopup";
    var width = 770;
    var height = 490;

    inwoo.view.popupCenter(url, name, width, height).focus();
};


/**
 * openChprSelectPopup
 * 담당자정보 팝업
 *
 * @param   : 없음
 * @return  : 없음
 * @author  : 정훈
 * @date    : 2015/08/03
 */
function openChprSelectPopup(comp_id, comp_conm_nm) {
    var url = inwoo.config.contextRoot + "/biz/chpr-popup.do?comp_conm_nm=" + escape(encodeURIComponent(comp_conm_nm));
    var name = "chprSelectPopup";
    var width = 770;
    var height = 490;

    inwoo.view.popupCenter(url, name, width, height).focus();
};


/**
 * openChprInfoSelectPopup
 * 담당자 상세정보(1건) 팝업
 *
 * @param   : 담당자 KEY
 * @return  : 없음
 * @author  : 정훈
 * @date    : 2015/08/04
 */
function openChprInfoSelectPopup(chpr_id) {
    var url = inwoo.config.contextRoot + "/biz/chpr-Info-popup.do?"+"chpr_id=" + chpr_id;
    var name = "chprInfoSelectPopup";
    var width = 770;
    var height = 290;

    console.log(url);

    var obj = inwoo.view.popupCenter(url, name, width, height);
    obj.focus();
};


/**
 * openSlopIssHstrPopup
 * 사업 이슈 이력 팝업
 *
 * @param   : 이슈 KEY
 * @return  : 없음
 * @author  : 정훈
 * @date    : 2015/08/04
 */
function openSlopIssHstrPopup(slop_iss_id) {
    var url = inwoo.config.contextRoot + "/biz/slop-iss-hstr-div.do?"+"slop_iss_id=" + slop_iss_id;
    var name = "slopIssHstrPopup";
    var width = 770;
    var height = 620;

    console.log(url);

    var obj = inwoo.view.popupCenter(url, name, width, height);
    obj.focus();
};

/**
 * openConsInfoPopup
 * 상담상세정보팝업
 *
 * @param   : 상담 KEY
 * @return  : 없음
 * @author  : 정훈
 * @date    : 2015/11/03
 */
function openConsInfoPopup(sConsno){
    var url = inwoo.config.contextRoot + "/web/web-cons/web-cons-info-popup.do?consno=" + sConsno;
    var name = "consInfoPopup";
    var width = 850;
    var height = 460;

    console.log(url);

    var obj = inwoo.view.popupCenter(url, name, width, height);
    obj.focus();
}

