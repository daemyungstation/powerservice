/**
 * @(#)inwoo.core.js
 *
 * Copyright     Copyright (c) 2011
 * Company       Inwoo Tech Inc.
 *
 * @author
 * @version     1.0
 * @date        2013/04/01
 */
var inwoo = inwoo || {};

/**
 * ie7, 8 에서 indexOf 지원하도록 prototype 생성
 */
if (!Array.prototype.indexOf) {
    Array.prototype.indexOf = function(elt /*, from*/) {
        var len = this.length >>> 0;

        var from = Number(arguments[1]) || 0;

        from = (from < 0) ? Math.ceil(from) : Math.floor(from);

        if (from < 0) {
            from += len;
        }

        for (; from < len; from++) {
            if (from in this && this[from] === elt) {
                return from;
            }
        }
        return -1;
    };
}

/**
 * gf_popup
 * 팝업시키기 위한 함수(1024 X 768 인경우)
 *
 * @param  : path,arg,width,height,iTop,iLeft
 * @return : window.open(path,arg,shape)
 * @author    : 정재동
 * @date     : 2013/04/01
 */
gf_popup = function(path, arg, width, height, iTop, iLeft) {
    if (iTop == null) iTop = (screen.availHeight - height) / 2;
    if (iLeft == null) iLeft = (screen.availWidth - width) / 2;
    if (iTop < 88) iTop = 88;

    var shape  = "dialogWidth:" + width + "px";
    shape += ";dialogHeight:" + height + "px";
    shape += ";dialogTop:" + iTop + "px";
    shape += ";dialogLeft:" + iLeft + "px";
    shape += ";help:No;status:no;scroll:no;resizable:yes";

    showModelessDialog(path, arg, shape);
};


/**
 * replaceAll
 * String 오브젝트의 replaceAll을 구현
 *
 * @param searchStr
 * @param replaceStr
 * @returns {String}
 */
String.prototype.replaceAll = function(searchStr, replaceStr) {
    var temp = this;

    while(temp.indexOf(searchStr) != -1) {
        temp = temp.replace(searchStr, replaceStr);
    }
    return temp;
};



/**
 * onkeylengthMax
 * obj의 내용이 최대길이를 넘는지 체크하는 함수. 영문,숫자 - 1바이트, 한글 3바이트
 * 사용법 : <textarea data-bind="value: memo, valueUpdate:'afterkeyup'" class="k-textbox w97" onkeyup="onkeylengthMax(this, 450)"></textarea>
 *
 * @param  : obj, 최대길이(byte)
 * @return : 없음
 * @author : 한숙향
 * @date   : 2013/04/01
 */
onkeylengthMax = function(obj, maxlength) {
    var li_byte = 0;
    var li_len  = 0;

    for (var i = 0; i < obj.value.length; i++) {
        if (escape(obj.value.charAt(i)).length > 4) {
            li_byte += 3;
        } else {
            li_byte++;
        }

        if (li_byte <= maxlength) {
            li_len = i + 1;
        }
    }

    if (li_byte >= maxlength) {
        obj.value = obj.value.substr(0, li_len);
        alert("최대 (영문:" + maxlength + ", 한글:" + Math.floor(maxlength / 3) + ")자를 넘을수 없습니다.");

        return false;
    }
    obj.focus();
    return true;
};

/**
 * cutByteString
 * 내용의 길이를 Byte로 체크하여 자르는 함수. 영문,숫자 - 1바이트, 한글 3바이트
 *
 * @param  : 문자열, 최대길이
 * @return : 해당 Byte 길이 문자열
 * @author : 한숙향
 * @date   : 2013/04/01
 */
cutByteString = function(str, maxlength) {
    if (str == null) {
        return "";
    }

    var li_byte = 0;
    var li_len  = 0;
    for (var i = 0; i < str.length; i++) {
        if (escape(str.charAt(i)).length > 4) {
            li_byte += 3;
        } else {
            li_byte++;
        }

        if (li_byte > maxlength) {
            str = str.substr(0, li_len);
            break;
        } else {
            li_len++;
        }
    }
    return str;
};

/**
 * getUTF8Length
 * UTF8 Byte 길이를 반환
 *
 * @param  : value
 * @return : length
 * @author : 한숙향
 * @date   : 2013/04/01
 */
getUTF8Length = function(value) {
    return unescape(encodeURIComponent(value)).length;
};

/**
 * getCheckedCnt
 * Form의 선택된 체크박스의 갯수를 반환
 *
 * @param   : 폼 ID
 * @return  : 선택된 체크박스의 갯수
 * @author	: 한숙향
 * @date    : 2013/04/01
 */
getCheckedCnt = function(formId) {
    return $("#" + formId + " :checked").length;
};



/**
 * allSelect
 * 체크박스 전체선택, 해제
 * 사용법 : <input type="checkbox" onclick="allSelect('selectcheck', this.checked);">
 *
 * @param       : obj
 * @return      : 없음
 * @author     : 한숙향
 * @date      : 2013/04/01
 */
allSelect = function(objname, checkfg) {
    // $("input[name=" + objname + "]").attr("checked", checkfg);
    // disabled checkbox는 전체선택시에도 선택 안되도록 처리 2012.03.15
    $("input[name=" + objname + "]").each(function() {
        if(!$(this).attr("disabled")) {
            //$(this).attr("checked", checkfg);
            $(this).get(0).checked = checkfg;
        }
    });
};



/**
 * lpad
 * length바이트공간에 base를 적고 나머지 바이트를 왼쪽으로 str로 채움
 * 사용법 : lpad("5", 2, "0"); -> "05"를 리턴함
 *
 * @param       : obj
 * @return      : 없음
 * @author     : 한숙향
 * @date      : 2013/04/01
 */
lpad = function(base, length, str) {
    var ret = "";

    for (var i = 0; i < length; i++) {
        ret = ret + str;
    }
    ret = ret + base;
    ret = ret.substring(ret.length - length);

    return ret;
};



/**
 * phoneNumStr
 * 숫자를 전화번호 형식으로 변환
 * 사용법 : phoneNumStr('01088889999'); -> 010-8888-9999
 *
 * @param   : 전화번호
 * @return	: 변환된 번호
 * @author  : 한숙향
 * @date    : 2013/04/01
 */
phoneNumStr = function(str) {
    var RegNotNum  = /[^0-9]/g;
    var RegPhonNum = "";
    var DataForm   = "";

    // return blank
    if (str == "" || str == null) return "";

    // delete not number
    str = str.replace(RegNotNum,'');

    if (str.length < 5) {
        return str;
    }

    if (str.length > 11) {
        str = str.substring(0, 11);
    }

    if (str.length > 4 && str.length < 7) {
        DataForm = "$1-$2";
        RegPhonNum = /([0-9]{3})([0-9]+)/;
    } else if (str.length == 7) {
        DataForm = "$1-$2";
        RegPhonNum = /([0-9]{3})([0-9]{4})/;
    } else if (str.length == 8) {
        DataForm = "$1-$2";
        RegPhonNum = /([0-9]{4})([0-9]{4})/;
    } else if (str.length > 8 && str.length < 10) {
        DataForm = "$1-$2-$3";
        RegPhonNum = /([0-9]{2})([0-9]{3})([0-9]+)/;
    } else if (str.length == 10) {
        if (str.substring(0,2) == "02") {
            DataForm = "$1-$2-$3";
            RegPhonNum = /([0-9]{2})([0-9]{4})([0-9]+)/;
        } else {
            DataForm = "$1-$2-$3";
            RegPhonNum = /([0-9]{3})([0-9]{3})([0-9]+)/;
        }
    } else if (str.length > 10) {
        DataForm = "$1-$2-$3";
        RegPhonNum = /([0-9]{3})([0-9]{4})([0-9]+)/;
    }

    while (RegPhonNum.test(str)) {
        str = str.replace(RegPhonNum, DataForm);
    }
    return str;
};

ko.extenders.toLocaleFormat = function(target, option) {
    var result = ko.computed({
        read: function () {
            return target();
        },
        write: function (v) {
            if(option)
                target(localeStr(v));
            else
                target(v);
        }
    });

    return result;
};

ko.extenders.parseCustomTag = function (target, options) {
    var result = ko.computed({
        read: function () {
            return parseCustomTag(target());
        },
        write: function(v) {
            target(v);
        }
    });

    result.raw = target;
    return result;
};

/**
 * maskPhone
 * 전화번호 숫자를 입력할때마다 전화번호 형식으로 변환
 * 사용법 : <input type="text" name="hometelno" class="ipt_m_b" style="width:110px;" onkeyup="maskPhone(this);" />
 *
 * @param   : 전화번호 입력 인풋 obj
 * @return  : 없음
 * @author    : 한숙향
 * @date     : 2013/04/01
 */
maskPhone = function(obj) {
    obj.value = phoneNumStr(obj.value);
};


/**
 * computeAgeStr
 * 생년월일 나이 계산
 *
 * @param   : birthd(YYYYMMDD)
 * @return  : 나이(개월, 세)
 * @author  : 이희철
 * @date    : 2014/10/31
 */
computeAgeStr = function(birthd) {
    var today = moment(moment().format('YYYYMMDD'), 'YYYYMMDD');
    if (birthd == null || !moment(birthd, 'YYYYMMDD').isValid()) {
        return "";
    }
    birthd = moment(birthd, 'YYYYMMDD');
    if (today.isBefore(birthd)) {
        return "";
    }

    var age = today.diff(birthd, "months") + 1;
    if (age < 0) {
        return "";
    } else if (age <= 48) {
        return age + " 개월";
    }

    age = today.diff(birthd, "years") + 1;
    return age + " 세";
};


/**
 * stripTag
 * 태그제거
 *
 * @param   : html
 * @return  : text
 * @author  : 한숙향
 * @date    : 2013/04/01
 */
stripTag = function(val) {
    if (val == null) {
        return "";
    }

    //return val.replace(/<(\/)?([a-zA-Z]*)(\s[a-zA-Z]*=[^>]*)?(\s)*(\/)?>/g, "");
    return val.replace(/<(?:.|\n)*?>/gm, '');
};

/**
 * useHtmlTag
 * HTML 태그 사용체크
 *
 * @param   : 객체/문자열, 에디터 속성명
 * @return  : 사용여부
 * @author  : 이희철
 * @date    : 2015/04/01
 */
useHtmlTag = function(poData, psEditProp) {
    var rHtmlTag = /<[\\s]*(\/)*([a-z]+)[\\s]*([^>]*)[\\s]*>/gi;
    if (typeof(poData) == "object") {
        if (typeof(psEditProp) != "string") {
            psEditProp = "";
        }
        for (var sDataProp in poData) {
            /*
             * 2015.11.23 이주희 - HTML태그 입력여부 체크 시 추가코드명은 제외
             */
            if (typeof(poData[sDataProp]) == "string" && sDataProp != psEditProp && sDataProp != "adtl_cd_nm") {
                if (rHtmlTag.test(poData[sDataProp])) {
                    return true;
                }
            }
        };
    } else if (typeof(poData) == "string") {
        return rHtmlTag.test(poData);
    }
    return false;
};

/**
 * zipCdStr
 * 숫자를 우편번호 형식으로 변환 (6자리 우편번호인 경우에만 '-'를 입력함.)
 * 사용법 : zipCdStr("111222"); -> 111-222
 *
 * @param   : 우편번호
 * @return	: 변환된 우편번호
 * @author  : 한숙향
 * @date    : 2013/04/01
 */
zipCdStr = function(psZipCd) {
    var rNotNum = /[^0-9]/g;
    var rZipNum = /([0-9]{3})([0-9]+)/;
    var rDataForm = "$1-$2";

    // return blank
    if (psZipCd == null || psZipCd == "") {
        return "";
    }

    // delete not number
    psZipCd = psZipCd.replace(rNotNum, "");

    if (psZipCd.length < 6) {
        return psZipCd;
    } else if (psZipCd.length > 6) {
        psZipCd = psZipCd.substring(0, 7);
    }

    while (rZipNum.test(psZipCd)) {
        psZipCd = psZipCd.replace(rZipNum, rDataForm);
    }
    return psZipCd;
};

/**
 * excel_down_popup
 * 엑셀 다운로드 팝업
 *
 * @param   : 엑셀ID, 폼명
 * @return  : 없음.
 * @author  : 한숙향
 * @date    : 2013/04/01
 */
var gsExcelParam = "";
excel_down_popup = function(psXlsId, psFormNm) {
    if (typeof(psFormNm) == "undefined") {
        psFormNm = "searchForm";
    }

    gsExcelParam = ko.toJSON($("form[id=" + psFormNm + "]").serializeObject());

    var url = "/powerservice/common/xls-down-popup.do?xlsid=" + psXlsId;
    var name = "excelDownPopup";
    var width = 355;
    var height = 390;

    inwoo.view.popupCenter(url, name, width, height).focus();
};

/**
 * excel_down_express
 * 엑셀 다운로드 팝업
 *
 * @param	: 엑셀ID, 파라미터 객체, 대용량처리 여부
 * @return	: 없음.
 * @author	: 정용재
 * @date	: 2014/12/09
 */
excel_down_express = function(psXlsId, poTarget, pbBigGrid) {
    var sBigGridYn = "N";
    if (typeof(pbBigGrid) == "boolean") {
        sBigGridYn = pbBigGrid ? "Y" : "N";
    }

    $.inwooAjax(inwoo.config.contextRoot + "/syst/xls/download-check", {}, function(poResult) {
        if (poResult.data == "Y") {
            alert("현재 작업중인 엑셀 다운로드가 있습니다.");
        } else {
            excel_down_express_process(psXlsId, poTarget, sBigGridYn);
        }
    });
};
excel_down_express_process = function(psXlsId, poTarget, psBigGridYn) {
    if (typeof(poTarget) == "undefined") {
        poTarget = "searchForm";
    }

    // 엑셀 파라미터 처리
    var oExcelParam = null;
    if (typeof(poTarget) == "string") { // FORM
        oExcelParam = $("form[id=" + poTarget + "]").serializeObject();
    } else if (typeof(poTarget) == "object") { // KENDO OBJECT
        if (typeof(poTarget.searchParam) == "function") { // ViewModel
            // 뷰모델에 그리드(self.grid)가 포함된 경우 정렬 파라미터 추가
            oExcelParam = ko.toJS(poTarget.searchParam());
            if (typeof(poTarget.grid) == "function") {
                if (typeof(poTarget.grid().options.dataSource["_sort"]) == "object") {
                    oExcelParam.orderBy = poTarget.grid().options.dataSource["_sort"]["0"]["field"];
                    oExcelParam.orderDirection = poTarget.grid().options.dataSource["_sort"]["0"]["dir"];
                }
            }
            // 추가 엑셀 파라미터 처리
            if (typeof(poTarget.procExcelParam) == "function") {
                oExcelParam = poTarget.procExcelParam(oExcelParam);
            }
        } else { // SearchParam
            oExcelParam = poTarget;
        }
    }
    if (oExcelParam == null) {
        alert("엑셀 파라미터가 없습니다.");
        return;
    }
    oExcelParam["big_grid_yn"] = psBigGridYn;
    gsExcelParam = ko.toJSON(oExcelParam);

    // 엑셀 다운로드 프레임 생성
    if ($("iframe[name=excel-download-iframe]").length == 0) {
        $("body").append("<iframe name='excel-download-iframe' class='hide' width='0px' height='0px' />");
    }

    // 엑셀 다운로드 폼 삭제
    $("form[name=excel-download-form]").remove();

    // 엑셀 다운로드 폼 생성
    var $ExcelDownloadForm = $("<form />", {
        "name" : "excel-download-form",
        "action" : inwoo.config.contextRoot + "/syst/xls/download",
        "method" : "post",
        "target" : "excel-download-iframe"
    }).appendTo($("body"));

    // 엑셀 다운로드 파라미터 생성
    $("<input />", {
        "type"	: "hidden",
        "name"	: "xls_id",
        "value" : psXlsId
    }).appendTo($ExcelDownloadForm);

    $("<input />", {
        "type"	: "hidden",
        "name"	: "xls_param",
        "value" : gsExcelParam
    }).appendTo($ExcelDownloadForm);

    kendo.ui.progress($("body"), true);
    setTimeout(excel_down_check, 3000);
    $ExcelDownloadForm.submit();
};


/*
 * 사용자 정의 엑셀다운
 * psAction	: 엑셀다운로드 경로
 * psTempNm	: 엑셀템플릿 명
 * poParam	: 전달 파라미터
 */

excel_down_express_custom = function(psAction, psTempNm, poParam, psBody) {
    if (typeof(psBody)) {
        excel_progress_body = $("#" + psBody);
    } else {
        excel_progress_body = $("body");
    }
    $.inwooAjax(inwoo.config.contextRoot + "/syst/xls/download-check", {}, function(poResult) {
        if (poResult.data == "Y") {
            alert("현재 작업중인 엑셀 다운로드가 있습니다.");
        } else {
            excel_down_express_custom_process(psAction, psTempNm, poParam);
        }
    });
};
excel_down_express_custom_process = function(psAction, psTempNm, poParam) {
    // 엑셀 다운로드 프레임 생성
    if ($("iframe[name=excel-download-iframe]").length == 0) {
        $("body").append("<iframe name='excel-download-iframe' id='excel-download-iframe' class='hide'/>");
    }

    // 엑셀 다운로드 폼 삭제
    $("form[name=excel-download-form]").remove();

    // 엑셀 다운로드 폼 생성
    var $ExcelDownloadForm = $("<form />", {
        "name" : "excel-download-form",
        "action" : inwoo.config.contextRoot + psAction,
        "method" : "post",
        "target" : "excel-download-iframe"
    }).appendTo($("body"));

    // 엑셀 다운로드 파라미터 생성
    $("<input />", {
        "type"	: "hidden",
        "name"	: "xls_template",
        "value" : psTempNm
    }).appendTo($ExcelDownloadForm);

    $("<input />", {
        "type"	: "hidden",
        "name"	: "xls_param",
        "value" : JSON.stringify(poParam)
    }).appendTo($ExcelDownloadForm);

    kendo.ui.progress(excel_progress_body, true);
    // setTimeout(excel_down_check, 3000);
    $ExcelDownloadForm.submit();
};

/**
 * excel_down_check
 * 엑셀 다운로드 체크
 *
 * @param	: 없음.
 * @return	: 없음.
 * @author	: 정용재
 * @date	: 2014/12/09
 */
excel_down_check = function() {
    $.inwooAjax(inwoo.config.contextRoot + "/syst/xls/download-check", {}, function(poResult) {
        if (poResult.data == "Y") {
            setTimeout(excel_down_check, 2000);
        } else {
            kendo.ui.progress($("body"), false);
        }
    });
};

localeStr = function(str) {
    str = Number(str).toLocaleString().split(".")[0];
       return str;
};

/**
 * getHrchCodeObj
 * 계층코드 리스트에서 해당 오브젝트를 찾아서 반환
 *
 * @param   : 계층코드 리스트, 계층코드
 * @return  : 계층코드 오브젝트
 * @author  : 정용재
 * @date    : 2014/10/01
 */
getHrchCodeObj = function(poHrchCodeList, psHrchCd) {
    var oEmpty = {"hrch_cd" : "", "hrch_cd_nm" : "", "hrch_adtl_cd" : "", "hrch_adtl_cd_nm" : ""};
    if (poHrchCodeList == null || psHrchCd == null || psHrchCd == "") {
        return oEmpty;
    }
    var oHrchCodeFilterList = $.grep(poHrchCodeList, function(v, i) {return v.hrch_cd == psHrchCd;});
    return (oHrchCodeFilterList.length > 0) ? oHrchCodeFilterList[0] : oEmpty;
};

/**
 * getHrchCodeLevelObj
 * 계층코드리스트에서 해당오브젝트를 찾아서 반환
 *
 * @param   : 계층코드 리스트, 계층레벨, 계층코드
 * @return  : text
 * @author  : 정용재
 * @date    : 2014/10/01
 */
getHrchCodeLevelObj = function(poHrchCodeList, psHrchCdLvlCnt, psHrchCd) {
    var oEmpty = {"hrch_cd" : "", "hrch_cd_nm" : "", "hrch_adtl_cd" : "", "hrch_adtl_cd_nm" : ""};
    if (poHrchCodeList == null || psHrchCdLvlCnt == null || psHrchCd == null) {
        return oEmpty;
    }
    if (psHrchCdLvlCnt == "" || psHrchCd == "") {
        return oEmpty;
    }
    var oHrchCodeFilterList = $.grep(clscodeList, function(v, i) {return v.hrch_cd_lvl_cnt == psHrchCdLvlCnt && v.hrch_cd == psHrchCd;});
    return (oHrchCodeFilterList.length > 0) ? oHrchCodeFilterList[0] : oEmpty;
};

/**
 * transTag
 * 유니코드 변환
 *
 * @param   : html
 * @return  : text
 * @author  : 정용재
 * @date    : 2015/10/30
 */
transTag = function(v) {
    v = v.replaceAll("&amp;","&");
    v = v.replaceAll("&lt;","<");
    v = v.replaceAll("&gt;",">");
    v = v.replaceAll("&quot;","'");
    v = v.replaceAll("&apos;","'");
    v = v.replaceAll("&nbsp;"," ");
    v = v.replaceAll("&#35;","#");
    v = v.replaceAll("&#37;","%");
    v = v.replaceAll("&#38;","&");
    v = v.replaceAll("&#40;","(");
    v = v.replaceAll("&#41;",")");
    v = v.replaceAll("&#43;","+");
    v = v.replaceAll("&#59;",";");
    return v;
};

/**
 * getCodeObj
 * 코드 리스트에서 해당 오브젝트를 찾아서 반환
 *
 * @param   : 코드 리스트, 코드값
 * @return  : 코드 오브젝트
 * @author  : 정용재
 * @date    : 2014/10/01
 */
getCodeObj = function(poCodeList, psCd) {
    var oEmpty = {"cd" : "", "cd_nm" : "", "adtl_cd" : "", "adtl_cd_nm" : ""};
    if (poCodeList == null || psCd == null || psCd == "") {
        return oEmpty;
    }
    var oCodeFilterList = $.grep(poCodeList, function(v, i) {return v.cd == psCd;});
    return (oCodeFilterList.length > 0) ? oCodeFilterList[0] : oEmpty;
};

/**
 * isHpno
 * 휴대폰번호인지 체크
 *
 * @param   : 휴대폰번호
 * @return  : rtn (0이상이면 휴대폰번호)
 * @author    : 한숙향
 * @date     : 2013/04/01
 */
isHpno = function(hpno) {
    var checkNum = ["010", "011", "016", "018", "019"];
    var rtn = -1;

    if (hpno.length == 10 || hpno.length == 11) {
        rtn = jQuery.inArray(hpno.substring(0, 3), checkNum);
    }
    return rtn;
};

/**
 * logslog
 * 로컬 로그출력 함수
 * 사용법 : logslog(출력할문자열)
 *
 * @param     : logstr
 * @return     : 없음.
 * @author    : 이창희
 * @date     : 2013/04/01
 */
logslog = function(logstr) {
    try {
        if (getValue("logoutForm", "logfg") == "Y")
            logs.Log(logstr);
    } catch(e) {}
};



/**
 * copy_clipboard
 * 클립보드에 복사
 *
 * @param   : 복사할내용
 * @return  : 없음
 * @author    : 한숙향
 * @date     : 2013/04/01
 */
copy_clipboard = function(value) {
     if (!document.all || value == null || value == "") {
         return;
     }
     window.clipboardData.setData("text", value);
     alert("복사되었습니다.");
};



/**
 * addExtensionClass
 * 확장자 클래스 추가
 *
 * @param   : 확장자
 * @return  : 확장자 css 클래스명
 * @author  : 한숙향
 * @date    : 2013/04/01
 */
function addExtensionClass(extension) {
    switch (extension) {
        case '.bmp':
            return "bmp-file";
        case '.doc':
        case '.docx':
            return "doc-file";
        case '.gif':
            return "gif-file";
        case '.html':
            return "html-file";
        case '.jpeg':
            return "jpeg-file";
        case '.jpg':
            return "jpg-file";
        case '.pdf':
            return "pdf-file";
        case '.png':
            return "png-file";
        case '.txt':
            return "txt-file";
        case '.wav':
            return "wav-file";
        case '.wmv':
            return "wmv-file";
        case '.xls':
        case '.xlsx':
            return "xls-file";
        case '.zip':
        case '.rar':
        case '.alz':
            return "zip-file";
        default:
            return "etc-file";
    }
}


/**
 * removeNoneNum
 * 숫자 이외의 문자 제거
 * 사용법 : <input type="text" name="telno" class="ipt_m_sch" onkeyup="removeNoneNum(this);" style="width: 100px;">
 *
 * @param  	 : obj
 * @return 	 : 없음
 * @author	 : 한숙향
 * @date 	 : 2013/04/01
 */
removeNoneNum = function(obj) {
    var reg = /\D/g;
    var val = obj.value;

    val = val.replace(reg, "");

    $(obj).val(val);
};


/**
 * selectGridRow
 * 그리드에 포함된 버튼 클릭 시 그리드 행 선택
 *
 * @param   : Grid 객체
 * @return  : 없음
 * @author  : 이희철
 * @date    : 2015/04/01
 */
selectGridRow = function(poGrid) {
    if (event == null) {
        return false;
    }

    var eTarget = event.currentTarget != null ? event.currentTarget : event.srcElement;
    if (eTarget != null) {
        if (event.preventDefault != null) {
            event.preventDefault();
        } else {
            event.returnValue = false;
        }

        if (typeof(poGrid) == "object") {
            var $GridTr = $(eTarget).closest("tr");
            if ($GridTr.length > 0) {
                poGrid.select($GridTr[0]);
                return true;
            }
        }
    }
    return false;
};

/**
 * addSubmitTokenParam
 * 중복처리방지 TOKEN 파라미터 추가
 *
 * @param   : 폼 객체, 파라미터 객체
 * @return  : 파라미터 객체
 * @author  : 이희철
 * @date    : 2015/04/01
 */
var goSubmitToken = {};
addSubmitTokenParam = function($Form, poParam) {
    if ($Form.find("input[name=" + inwoo.config.submitActionParam + "]").length > 0) {
        var sSubmitAction = $Form.find("input[name=" + inwoo.config.submitActionParam + "]").val();
        var sSubmitToken = goSubmitToken[sSubmitAction];
        if (sSubmitToken == null) {
            // 첫 요청인 경우 폼의 HIDDEN 값 가져옴
            sSubmitToken = $Form.find("input[name=" + inwoo.config.submitTokenParam + "]").val();
        } else {
            // 요청 후 재처리 되지 않도록 TOKEN 초기화
            goSubmitToken[sSubmitAction] = "";
        }

        poParam[inwoo.config.submitActionParam] = sSubmitAction;
        poParam[inwoo.config.submitTokenParam] = sSubmitToken;
        /*
        console.log(inwoo.config.submitActionParam + "-[" + poParam[inwoo.config.submitActionParam] + "] " +
                    inwoo.config.submitTokenParam + "-[" + poParam[inwoo.config.submitTokenParam] + "]");
        */
    }
    return poParam;
};

/**
 * setSubmitTokenParam
 * 중복처리방지 TOKEN 파라미터 저장
 *
 * @param   : 폼 객체, 결과 객체
 * @return  : 없음
 * @author  : 이희철
 * @date    : 2015/04/01
 */
setSubmitTokenParam = function($Form, poResult) {
    if (poResult != null && poResult.token != null) {
        if ($Form.find("input[name=" + inwoo.config.submitActionParam + "]").length > 0) {
            var sSubmitAction = $Form.find("input[name=" + inwoo.config.submitActionParam + "]").val();
            goSubmitToken[sSubmitAction] = poResult.token;
            // console.log("new_" + inwoo.config.submitTokenParam + "-[" + goSubmitToken[sSubmitAction] + "]");
        }
    }
};



$.fn.serializeObject = function() {
    obj = {};

    $.each(this.serializeArray(), function(idx, arr) {
        // obj[arr.name] = arr.value;
        obj[arr.name] = (obj[arr.name] ? obj[arr.name] + "," : "") + arr.value;
    });

    return obj;
};


(function($) {
    /*
     * 파라미터가 있는 경우 : $.inwwLoad(url, parameter, callbackfunc)
     * 파라미터가 없는 경우 : $.inwooLoad(url, callbackFunc)
     * 비동기 방식으로 페이지를 적재한다.
     */
    $.inwooLoad = function() {
        callbackFunc = arguments.length==1 ? function(){} : arguments[arguments.length-1];

        $.ajax({
            // CSRF Protection 추가
            type: 'POST'
          , async: false
          , url: arguments[0]
          , data: arguments.length == 3 ? arguments[1] : ""
          , success: function(data) {
              callbackFunc(data);
          }
        });
    };

    $.inwooAjax = function(url, pars, callback) {
        $.ajax({
              // CSRF Protection 추가
              headers: {
                  "X-CSRF-Token": inwoo.config.csrfToken
              }
            , type: "POST"
            , async: true
            , url: url
            , dataType: "json"
            , cache: false
            , data: pars
            , contentType: "application/x-www-form-urlencoded; charset=UTF-8"
            , success: function(data) {
                callback(data);
            }
            , error: function(response, status, error) {
                var _errMsg = $.trim(response.responseText);
                if (response.status == 500) {
                    if (_errMsg.indexOf("[AccessIPException]") > 0) {
                        top.document.location.replace(inwoo.config.contextRoot + "/error/access-deny.do");
                    } else if (_errMsg.indexOf("[AccessSessionException]") > 0) {
                        top.document.location.replace(inwoo.config.contextRoot + "/error/session.do");
                    } else {
                        alert(_errMsg);
                    }
                } else {
                    callback({result: "ERROR", errMsg: _errMsg});
                }
            }

        });
    };



    $.inwooAjaxJson = function(url, json, callback) {
        $.ajax({
              // CSRF Protection 추가
              headers: {
                  "X-CSRF-Token": inwoo.config.csrfToken
              }
            , type: "POST"
            , async: true
            , url: url
            , dataType: "json"
            , cache: false
            , data: typeof(json) == "string" ? json : ko.toJSON(json)
            , contentType: "application/json; charset=utf-8"
            , success: function(data) {
                callback(data);
            }
            , error: function(response, status, error) {
                var _errMsg = $.trim(response.responseText);
                if (response.status == 500) {
                    if (_errMsg.indexOf("[AccessIPException]") > 0) {
                        top.document.location.replace(inwoo.config.contextRoot + "/error/access-deny.do");
                    } else if (_errMsg.indexOf("[AccessSessionException]") > 0) {
                        top.document.location.replace(inwoo.config.contextRoot + "/error/session.do");
                    } else {
                        alert(_errMsg);
                    }
                } else {
                    callback({result: "ERROR", errMsg: _errMsg});
                }
            }

        });
    };
})(jQuery);

/*
*
* 같은 값이 있는 열을 병합함
*
* 사용법 : $('#테이블 ID').rowspan(0);
*
*/
$.fn.rowspan = function(colIdx, isStats) {
   return this.each(function(){
       var that = null;
       $('tr', this).each(function(row) {
           $('td:eq('+colIdx+')', this).filter(':visible').each(function(col) {

               if ($(this).html() == $(that).html()){
                   var checkPrev = true;

                   if (isStats) {
                       var thistemp = $(this);
                       var thattemp = $(that);
                       while (typeof(thistemp.prev().html()) != "undefined") {
                           if (thistemp.prev().html() != thattemp.prev().html()) {
                               checkPrev = false;
                               break;
                           } else {
                               thistemp = thistemp.prev();
                               thattemp = thattemp.prev();
                           }
                       }
                   }
                   if (checkPrev) {
                       rowspan = $(that).attr("rowspan") || 1;
                       rowspan = Number(rowspan)+1;

                       $(that).attr("rowspan",rowspan);

                       // do your action for the colspan cell here
                       $(this).hide();

                       //$(this).remove();
                       // do your action for the old cell here
                   } else {
                       that = this;
                   }
               } else {
                   that = this;
               }

               // set the that if not already set
               that = (that == null) ? this : that;
           });
       });
   });
};

/*
 * Object 구조를 보여준다
 * showAttr(대상 오브젝트, 오브젝트 내용상세 표시유무, 출력함수:기본은 console)
 * 2015.06.08
 * 정용재
 */
showAttr = function(oTarget, bDetail, fnOutput) {
    fnOutput = (typeof(fnOutput) == "function" ? fnOutput : console.log);

    if (typeof(oTarget) == "object") {
        for(var _element in oTarget) {
            if (bDetail == true) {
                fnOutput([_element, oTarget[_element]]);
            } else {
                fnOutput([_element, typeof(oTarget[_element])]);
            }
        }
    } else {
        fnOutput([oTarget, typeof(oTarget)]);
    }

};

/*
 * 해당 템플릿 파일을 다운로드 한다.
 * downloadExcelTemp(filenm : 다운로드할 템플릿 파일명)
 * 2015.10.13
 * 정용재
 */
downloadExcelTemp = function(filenm) {
    location.href = inwoo.config.contextRoot + "/file/download-template?file_nm=" + filenm;
};

ko.extenders.momentFormat = function(target, option) {
    var result = ko.computed({
        read: function () {
            return target();
        },
        write: function (v) {
            // 기본 date형태는 YYYMMDD
            var mask = "YYYYMMDD";
            if (typeof(option) == "function") {
                switch(option()) {
                case "year" :
                    mask = "YYYY";
                    break;
                case "month" :
                case "quarter" :
                    mask = "YYYYMM";
                    break;
                case "week" :
                case "day" :
                    mask = "YYYYMMDD";
                    break;
                }
            } else if (typeof(option) == "string") {
                mask = option;
            }

            if (v == null || v == "") {
                target(v);
            } else {
                if (typeof(v) != "date" && typeof(v) != "object") {
                    var date = moment(v, mask);
                    if (moment(date).isValid()) {
                        target(v);
                    } else {
                        target("");
                    }
                } else {
                    if (moment(v).isValid()) {
                        target(moment(v).format(mask));
                    }
                }
            }
        }
    });
    return result;
};




ko.extenders.phoneNumberFormat = function (target, options) {
    var result = ko.computed({
        read: function () {
            return phoneNumStr(target());
        },
        write: function(v) {
            target(phoneNumStr(v));
        }
    });

    result.raw = target;
    return result;
};

ko.extenders.zipCdFormat = function (target, options) {
    var result = ko.computed({
        read: function () {
            return zipCdStr(target());
        },
        write: function(v) {
            target(zipCdStr(v));
        }
    });

    result.raw = target;
    return result;
};



ko.extenders.checkbox = function(target, options) {
    var result = ko.computed({
        read: function () {
            return options.boolTarget() == true ? options.checkedVal : options.uncheckedVal;
        },
        write: function (v) {
            if(v == options.checkedVal) options.boolTarget(true);
            else options.boolTarget(false);
        }
    });

    return result;
};



ko.extenders.limitByteLength = function (target, options) {
    var result = ko.computed({
        read: function () {
            return phoneNumStr(target());
        },
        write: function(v) {
            var len = getUTF8Length(v);

            if (len > options.limit)

            target(phoneNumStr(v));
        }
    });

    result.raw = target;
    return result;
};

ko.extenders.round = function (target, options) {
    var result = ko.computed({
        read: function () {
            if (target()== null) {
                return target();
            } else {
                return Number(target()).toFixed(options);
            }
        },
        write: function(v) {
            target(v);
        }
    });

    result.raw = target;
    return result;
};




ko.bindingHandlers.allowBindings = {
    init: function(elem, valueAccessor) {
        // Let bindings proceed as normal *only if* my value is false
        var shouldAllowBindings = ko.unwrap(valueAccessor());
        return { controlsDescendantBindings: !shouldAllowBindings };
    }
};



ko.bindingHandlers.inwooTabStrip = {
    init: function(element, valueAccessor, allBindings, viewModel, bindingContext) {
        var $element = $(element);
        var $header = $(element).children("ul").addClass("tab_con").find("li");
        var $button = $($("#" + ko.unwrap(valueAccessor().buttonTemplate)).html()).closest("div");
        var $body = $(element).children("div").addClass("content_middle");

        $header.last().addClass("last");

        $element.addClass("content_outer");
        $element.children("ul")
            .wrap("<div class=\"content_top\"></div>")
            .before("<div class=\"right_bg\"></div>")
            .after($button);
        $element.append("<div class=\"content_bottom\"><div class=\"right_bg\"></div></div>");

        if (valueAccessor() == null || valueAccessor().index == null) {
            $header.first().addClass("active");
            $body.hide();
            $body.first().show();
            $button.hide();
            $button.first().show();
        } else {
            var state = {header: $header, button: $button, body: $body};
            $element.data("inwooTabStrip", state);
            valueAccessor().index(ko.unwrap(valueAccessor().index));
        }

        $header.click(function(e) {
            var $target = $(e.target);
            // console.log("1-" + $target.prop("nodeName"));
            if ($target.prop("nodeName").toLowerCase() != "li") {
                $target = $target.parent();
                // console.log("2-" + $target.prop("nodeName"));
                if ($target.prop("nodeName").toLowerCase() != "li") {
                    $target = $target.parent();
                    // console.log("3-" + $target.prop("nodeName"));
                    if ($target.prop("nodeName").toLowerCase() != "li") {
                        return;
                    }
                }
            }

            var shareButtonUnwrap = valueAccessor() != null && ko.unwrap(valueAccessor().shareButton) || true;

            if (valueAccessor().index == null) {
                var tabIndex = $header.index($target);
                $header.removeClass("active");
                $target.addClass("active");
                if(shareButtonUnwrap == false) {
                    $button.hide();
                    $button.eq(tabIndex).show();
                }
                $body.hide();
                $body.eq(tabIndex).show();
            } else {
                valueAccessor().index($header.index($target));
            }
        });
    },
    update: function(element, valueAccessor, allBindings, viewModel, bindingContext) {
        // This will be called once when the binding is first applied to an element,
        // and again whenever the associated observable changes value.
        // Update the DOM element based on the supplied values here.

        if(valueAccessor() != null && valueAccessor().index != null) {
            var state = $(element).data("inwooTabStrip");
            var $header = state.header;
            var $body = state.body;
            var $button = state.button;
            var indexUnwrap = ko.unwrap(valueAccessor().index) || 0;
            var shareButtonUnwrap = valueAccessor().shareButton == null ? false : ko.unwrap(valueAccessor().shareButton);

            $header.removeClass("active");
            $header.eq(indexUnwrap).addClass("active");
            if (shareButtonUnwrap == false) {
                $button.hide();
                $button.eq(indexUnwrap).show();
            }
            $body.hide();
            $body.eq(indexUnwrap).show();
        }
    }
};



ko.bindingHandlers.inwooSlideWindow = {
    init: function(element, valueAccessor, allBindings, viewModel, bindingContext) {
        var $element = $(element);
        var $anchor = $("#" + ko.unwrap(valueAccessor().anchor));
        var isOpen = valueAccessor().isOpen;

        $element.hide();
        isOpen(false);

        $element.on("mouseleave", function(e) {
            //isOpen(false);
        });

        $(document).on("click", function(e) {
            if(!$element.is(e.target) && !$element.has(e.target).length &&
                    !$anchor.is(e.target) && !$anchor.has(e.target).length) {
                isOpen(false);
            }
        });
    },
    update: function(element, valueAccessor, allBindings, viewModel, bindingContext) {
        var valueUnwrapped = ko.unwrap(valueAccessor().isOpen);
        if(valueUnwrapped == true) {
            $(element).slideToggle(100);
        } else {
            $(element).slideUp(200);
        }
    }
};

ko.bindingHandlers.inwooSlideStopWindow = {
    init: function(element, valueAccessor, allBindings, viewModel, bindingContext) {
        var $element = $(element);
        var $anchor = $("#" + ko.unwrap(valueAccessor().anchor));
        var isOpen = valueAccessor().isOpen;

        $element.hide();
        isOpen(false);
    },
    update: function(element, valueAccessor, allBindings, viewModel, bindingContext) {
        var valueUnwrapped = ko.unwrap(valueAccessor().isOpen);
        if(valueUnwrapped == true) {
            $(element).slideToggle(100);
        } else {
            $(element).slideUp(200);
        }
    }
};

ko.bindingHandlers.enterKey = {
    init: function(element, valueAccessor, allBindingsAccessor, viewModel) {
        ko.utils.registerEventHandler(element, 'keydown', function(evt) {
            if (evt.keyCode === 13) {
                evt.preventDefault();
                evt.target.blur();
                valueAccessor().call(viewModel);
            }
        });
    }
};


/*
 * 화면이 resize될때 높이값을 맞춰준다.
 * eResizeWindow(이벤트)
 * 2015.10.30.
 * 정용재
 */
eResizeWindow = function(e) {
    var wH = $(window).outerHeight();
    var wW = $(window).width();

    $("[data-height]").each(function() {
        var nVal = $(this).data("height");

        if($(this).is(':visible')) {
            if (typeof(nVal) == "number") {
                $(this).height(nVal);
                wH -= $(this).outerHeight();
            } else if (typeof(nVal) == "string" && nVal.indexOf("%")<0 && (new RegExp(/^[0-9+]*$/)).test(nVal.split("px")[0])) {
                nVal = Number(nVal.split("px")[0]);
                $(this).height(nVal);
                wH -= $(this).outerHeight();
            }
        }
    });

    $("[data-height]").each(function() {
        var nVal = $(this).data("height");
        var nMax = $(this).data("height-max");
        var nMin = $(this).data("height-min");


        if (!(typeof(nVal) == "string" && nVal.indexOf("%")>=0 && (new RegExp(/^[0-9+]*$/)).test(nVal.split("%")[0]))) {
            return;
        }

        nVal = Number(nVal.split("%")[0]);

        var nExpectVal = wH*nVal/100;
        // 최대값 비교하여 최대값보다 클 경우 최대값 입력
        if (typeof(nMax) != "undefined" && nExpectVal > nMax) {
            $(this).height(nMax);

        // 최소값 비교하여 최소값보다 작을 경우 최소값 입력
        } else if (typeof(nMin) != "undefined" && nExpectVal < nMin) {
            $(this).height(nMin);

        // 계산된 퍼센트  수치를 입력
        } else {
            $(this).height(nExpectVal);
        }

    });
};