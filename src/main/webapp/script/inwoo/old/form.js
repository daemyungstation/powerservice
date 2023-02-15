/**
 * @(#)form.js
 *
 * Copyright     Copyright (c) 2011
 * Company       Inwoo Tech Inc.
 *
 * @author      Lee Changhee
 * @version        1.0
 * @date        2013/04/01
 */

/**
 * $.validator.setDefaults
 * validator defautl 설정
 *
 * @param     : options.
 * @return     : 없음.
 * @author    : 이창희
 * @date     : 2013/04/01
 */
$.validator.setDefaults({
    onsubmit: false,
    onkeyup: false,
    onclick: false,
    onfocusout: false,
    showErrors:function(errorMap, errorList){
        if (errorList.length > 0) {
            alert(errorList[0].message);
            $(errorList[0].element).trigger("focus");
        }
    }
});

jQuery.validator.addMethod("minByteLength", function(value, element,param) {return this.optional(element) || value.replace(/[^\x00-\xff]/g,"**").length >= param;});
jQuery.validator.addMethod("maxByteLength", function(value, element,param) {return this.optional(element) || value.replace(/[^\x00-\xff]/g,"**").length <= param;});

/**
 * appendTable
 * list_create에서 호출하는 table 추가 함수
 *
 * @param     : 출력대상 div명, 클래스명
 * @return     : 생성한 테이블객체.
 * @author    : 이창희
 * @date     : 2013/04/01
 */
appendTable = function(resDivName, classnm) {
   var obj = document.createElement("table");
   obj.width = "100%";
   obj.className = typeof(classnm) == "undefined" ? "tbl_m_grid" : classnm;

    $("#" + resDivName).empty().append(obj);

    try {
        return obj;
    } finally {
        obj = null;
    }
};

/**
 * appendTr
 * list_create에서 호출하는 tr 추가 함수
 *
 * @param     : 테이블객체, tr_id, 클래스명
 * @return     : 생성한 tr객체.
 * @author    : 이창희
 * @date     : 2013/04/01
 */
appendTr = function(objTable, i, classnm) {
   var obj = document.createElement("tr");
   obj.id = "tr_" + i;
   obj.style.cursor = "pointer";

   $(objTable).append(obj);

   try {
       return obj;
   } finally {
       obj = null;
   }
};

/**
 * appendTd
 * list_create에서 호출하는 td 추가 함수
 *
 * @param     : tr_obj, id, 출력값, 정렬플래그, 헤더ID
 * @return     : 생성한 td객체.
 * @author    : 이창희
 * @date     : 2013/04/01
 */
appendTd = function(objTr, id, value, aglignfg, headerId) {
    var obj = document.createElement("td");

    if ($(objTr).parent().children().index($(objTr)) == 0) {
        var thObj = null;
        var headerTrObj = null;

        if (typeof(headerId) != "undefined") {
            thObj = $("#" + headerId).find("#" + id);
            headerTrObj = $("#" + headerId + " tr");
        } else {
            thObj = $("#" + id);
            headerTrObj = $(".tbl_m_grid tr");
        }

        var width = thObj.attr("width");

        if (objTr.cells.length == 0) width = width - 1;

        $(obj).width(width);
   }

   if(aglignfg == 0) {
       obj.style.textAlign = "left";
   } else if(aglignfg == 1) {
       obj.style.textAlign = "center";
   } else if(aglignfg == 2) {
       obj.style.textAlign = "right";
   } else {
       obj.style.textAlign = "";
   }
   obj.innerHTML = value;

   $(objTr).append(obj);

   try {
       return obj;
   } finally {
       obj = null;
   }
};

/**
 * clearTable
 * list_create에서 호출하는 테이블 초기화 함수(스크롤바 없는 테이블만들때)
 *
 * @param     : 테이블객체
 * @return     : 없음.
 * @author    : 이창희
 * @date     : 2013/04/01
 */
clearTable = function(objTable) {
    var trItem = $(objTable).find("tr:gt(0)");
    trItem.remove();
};

/**
 * appendTrNoScr
 * list_create에서 호출하는 tr 추가 함수(스크롤바 없는 테이블만들때)
 *
 * @param     : 테이블객체, tr_id, 클래스명
 * @return     : 생성한 tr객체.
 * @author    : 이창희
 * @date     : 2013/04/01
 */
appendTrNoScr = function(objTable, i, classnm) {
   var obj = document.createElement("tr");
   obj.id = "tr_" + i;
   obj.style.cursor = "pointer";

   $(objTable).append(obj);

   try {
       return obj;
   } finally {
       obj = null;
   }
};

/**
 * appendTdNoScr
 * list_create에서 호출하는 td 추가 함수(스크롤바 없는 테이블만들때)
 *
 * @param     : tr_obj, 출력값, 정렬플래그
 * @return     : 생성한 td객체.
 * @author    : 이창희
 * @date     : 2013/04/01
 */
appendTdNoScr = function(objTr, value, aglignfg) {
   var obj = document.createElement("td");

   if(aglignfg == 0) {
       obj.style.textAlign = "left";
   } else if(aglignfg == 1) {
       obj.style.textAlign = "center";
   } else if(aglignfg == 2) {
       obj.style.textAlign = "right";
   } else {
       obj.style.textAlign = "";
   }

   obj.innerHTML = value;

   $(objTr).append(obj);

   try {
       return obj;
   } finally {
       obj = null;
   }
};

/**
 * toggleClassName
 * list_create에서 list_click시 호출되는 함수
 *
 * @param     : 출력되는 table명, tr id
 * @return     : 생성한 td객체.
 * @author    : 이창희
 * @date     : 2013/04/01
 */
toggleClassName = function(listName, trVal) {
    $("#" + listName + " .tbl_m_grid_on").toggleClass("tbl_m_grid_on");
    $("#" + listName).find("#" + trVal).toggleClass("tbl_m_grid_on");
};

/**
 * configSet
 * 검색조건 입력시 해당하는 체크박스 선택/해제 처리, 엔터키 입력시 검색
 *
 * @param     : 검색항목 obj
 * @return  : 없음.
 * @author    : 한숙향
 * @date     : 2013/04/01
 */
configSet = function(obj) {
    var targetObj = $("input[name=" + obj.name + "Chk]");

    if (obj.value == "") {
        $(targetObj).attr("checked", false);
    } else {
        $(targetObj).attr("checked", true);
    }

    if (event.keyCode == 13) {
        search_go();
    } else {
        return;
    }
};

/**
 * make_param
 * 검색시 ajax에 사용되는 parameter를 serialize함
 *
 * @param   : 폼명, 프로세스명
 * @return  : serialize 된 폼 파라미터
 * @author    : 이창희
 * @date     : 2013/04/01
 */
make_param = function(formName, processName) {
    eval("document." + formName + ".process").value = processName;
    var pars = $("form[name=" + formName + "]").serialize();

    return pars;
};

/**
 * getObject
 * Form 항목 jQuery 오브젝트를 리턴함
 *
 * @param   : 폼명, 오브젝트명
 * @return  : 해당 오브젝트의 값
 * @author    : 이창희
 * @date     : 2013/04/01
 */
getObject = function(formName, objName) {
    var obj = $("form[name=" + formName + "] :input[name=" + objName + "]");
    return obj;
};

/**
 * setValue
 * Form 항목의 값을 설정함
 *
 * @param   : 폼명, 오브젝트명, 값
 * @return  : 없음
 * @author    : 한숙향
 * @date     : 2013/04/01
 */
setValue = function(formName, objName, value) {
    var obj = getObject(formName, objName);
    var objType = obj.prop("type");

    if (objType == "radio" || objType == "checkbox") {
        obj.filter("input[value=" + value + "]").prop("checked", true);
    } else if (objType == "select-one") {
        if (obj.attr("data-role") == "dropdownlist") {
            obj.data("kendoDropDownList").value(value);
        } else {
            obj.find("option[value=" + value + "]").prop("selected", true);

            if (getValue(formName, objName) == "") {
                obj.find("option:first").prop("selected", true);
            }
        }
    } else {
        obj.val(value);
    }
};

/**
 * setFirstValue
 * 첫번째값을 선택(select)
 *
 * @param   : 폼명, 오브젝트명
 * @return  : 없음
 * @author    : 이창희
 * @date     : 2013/04/01
 */
setFirstValue = function(formName, objName) {
     var obj = getObject(formName, objName);
     obj.children("option:first").attr("selected", "selected");
};

/**
 * setLastValue
 * 마지막값을 선택(select)
 *
 * @param   : 폼명, 오브젝트명
 * @return  : 없음
 * @author    : 한숙향
 * @date     : 2013/04/01
 */
setLastValue = function(formName, objName) {
     var obj = getObject(formName, objName);
     obj.children("option:last").attr("selected", "selected");
};

/**
 * setText
 * Form 항목의 Text값을 설정함
 *
 * @param   : 폼명, 오브젝트명, 값
 * @return  : 없음
 * @author    : 한숙향
 * @date     : 2013/04/01
 */
setText = function(formName, objName, value) {
    $("form[name=" + formName + "] :input[name=" + objName + "] option[text='" + value + "']").attr("selected", "selected");
};

/**
 * getValue
 * Form 항목의 값을 리턴함
 *
 * @param   : 폼명, 오브젝트명
 * @return  : 해당 오브젝트의 값
 * @author    : 이창희
 * @date     : 2013/04/01
 */
getValue = function(formName, objName) {
    var obj = getObject(formName, objName);
    var objType = obj.prop("type");
    var value;

    if (objType == "radio") {
        value = obj.filter(":checked").val();
    } else {
        value = obj.val();
    }
    return value;
};

/**
 * getText
 * Form 항목의 text을 리턴함
 *
 * @param   : 폼명, 오브젝트명
 * @return  : 해당 오브젝트의 값
 * @author    : 이창희
 * @date     : 2013/04/01
 */
getText = function(formName, objName) {
    var obj = getObject(formName, objName);
    var objType = obj.attr("type");
    var ret = "";

    if (objType == "select-one") {
        ret = obj.find("option:selected").text();
    } else {
        ret = obj.text();
    }

    return ret;
};

/**
 * addFileTd
 * 파일아이콘을 TD에 입력해줌
 *
 * @param   : TD 오브젝트, 파일아이디, 파일이름
 * @return  : 없음
 * @author    : 한숙향
 * @date     : 2013/04/01
 */
addFileTd = function(objTd, fileid, filenm, param) {
    if (typeof(param) == "undefined") {
        param = "";
    }

    if (fileid == null || fileid == "") {
        return "";
    }

    if (objTd != null) {
        var aObj = document.createElement("a");
        aObj.href = "/powerservice/file/download?fileid=" + fileid + param;
        aObj.target = "downFrame";
        aObj.style.cursor = "pointer";

        aObj.innerHTML = "<img src='/powerservice/common/images/ico/file/" + getFileIcon(filenm) + "' alt='" + filenm + "' align='absmiddle'>";

        $(objTd).append(aObj);
    } else {
        return "<a href='/powerservice/file/download?fileid=" + fileid + param +  "' target='downFrame' style='cursor:pointer;'>"
            + "<img src='/powerservice/common/images/ico/file/" + getFileIcon(filenm) + "' alt='" + filenm + "' align='absmiddle'>";
            + "</a>";
    }
};

/**
 * getFileIcon
 * 파일확장자별 이미지를 리턴함
 *
 * @param   : 파일이름
 * @return  : Image Path
 * @author    : 정용재
 * @date     : 2013/04/01
 */
getFileIcon = function(filenm) {
    // 파일 확장자별 이미지 변경
    var imgIcon = "";
    switch (filenm.split(".")[filenm.split(".").length-1].toLowerCase()) {
    // 그림파일
    case "jpg" :
    case "png" :
    case "gif" :
    case "bmp" :
    case "jpeg" :
        imgIcon = "ico_jpg.gif";
        break;

    // Power Point 파일
    case "ppt" :
    case "pptx" :
        imgIcon = "ico_ppt.gif";
        break;

    // Html 파일
    case "htm" :
    case "html" :
        imgIcon = "ico_htm.gif";
        break;

    // PDF 파일
    case "pdf" :
        imgIcon = "ico_pdf.gif";
        break;

    // 한글과 컴퓨터 파일
    case "hwp" :
        imgIcon = "ico_hwp.gif";
        break;

    // 워드 파일
    case "doc" :
    case "docx" :
    case "txt" :
        imgIcon = "ico_word.gif";
        break;

    // Excel 파일
    case "xls" :
    case "xlsx" :
        imgIcon = "ico_xls.gif";
        break;

    default :
        imgIcon = "ico_etc.gif";
        break;
    }
    return imgIcon;
};

/**
 * configSet_other
 * 검색조건 입력시 해당하는 체크박스 선택/해제 처리, 엔터키 입력시 검색 (name과 Chk의 매핑이 다를때)
 *
 * @param     : 검색항목 obj, 체크박스 선택/해제 text
 * @return  : 없음.
 * @author    : 김장미
 * @date     : 2013/04/01
 */
configSet_other = function(obj, text) {
    var targetObj = $("input[name=" + text + "]");

    if (obj.value == "") {
        $(targetObj).attr("checked", false);
    } else {
        $(targetObj).attr("checked", true);
    }

    if (event.keyCode == 13) {
        search_go();
    } else {
        return;
    }
};

/**
 * noListCheck
 * 리스트 없을 때 표시
 *
 * @param     : 스크롤 여부
 * @return  : 없음.
 * @author    : 김장미
 * @date     : 2013/04/01
 */
function noListCheck(mode) {
    var objTable = "";

    if (mode == undefined) { // 페이징이면
        objTable = $("#" + resTableName).get(0);
        clearTable(objTable);
    } else { // 스크롤이면
        objTable = appendTable(resTableName, "tbl_m_grid_scr");
    }

    var objTr = document.createElement("tr");

    $(objTable).append(objTr);

    var obj = document.createElement("td");
    obj.colSpan = $(".tbl_m_grid th").length;
    obj.style.textAlign = "center";
    obj.innerHTML = "데이터가 존재하지 않습니다.";

    $(objTr).append(obj);
}
