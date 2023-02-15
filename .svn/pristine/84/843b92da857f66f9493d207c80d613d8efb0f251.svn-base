/**
 * @(#)common.js
 *
 * Copyright 	Copyright (c) 2011
 * Company   	Inwoo Tech Inc.
 *
 * @author      Lee Changhee
 * @version		1.0
 * @date		2013/04/01
 */

$.fn.serializeObject = function() {
    obj = {};

    $.each(this.serializeArray(), function(idx, arr) {
        obj[arr.name] = arr.value;
    });

    return obj;
};

/**
 * String.prototype.replaceAll
 * 문자열 치환
 *
 * @param 	: 찾는문자, 바꿀문자
 * @return 	: 바뀐문자
 * @author	: 이창희
 * @date 	: 2013/04/01
 */
String.prototype.replaceAll = function(searchStr, replaceStr) {
    var temp = this;

    while(temp.indexOf(searchStr) != -1) {
        temp = temp.replace(searchStr, replaceStr);
    }
    return temp;
};

/**
 * String.prototype.contains
 * 검색 문자열을 포함하고 있는지 여부를 확인
 *
 * @param 	: 찾는문자
 * @return 	: 포함여부(true/false)
 * @author	: 이창희
 * @date 	: 2013/04/01
 */
String.prototype.contains = function(searchStr) {
    if (this.indexOf(searchStr) > 0) return true;
    else return false;
};

/**
 * String.prototype.Right
 * 오른쪽 문자열 n Byte를 리턴
 *
 * @param 	: 변경할문자, 오른쪽바이트수
 * @return 	: 리턴 문자열
 * @author	: 이창희
 * @date 	: 2013/04/01
 */
String.prototype.Right = function(n) {
    var temp = this;

    if (n <= 0) return "";
    else if (n > String(temp).length) return temp;
    else {
        var iLen = String(temp).length;
        return String(temp).substring(iLen, iLen - n);
    }
};

/**
 * launchCenter
 * 팝업을 화면 가운데에 띄운다
 *
 * @param 	: url, 팝업명, 높이, 너비, 스크롤바 추가
 * @return 	: 팝업obj
 * @author	: 한숙향
 * @date 	: 2013/04/01
 */
launchCenter = function(url, name, width, height, scroll) {
    var scrollbar = "";

    if (scroll == undefined) scrollbar = "no";
    else scrollbar = scroll;
    var str = "height=" + height + ", innerHeight=" + height + ", width=" + width + ", innerWidth=" + width;

    if (window.screen) {
        var ah = screen.availHeight - 30;
        var aw = screen.availWidth - 10;
        var xc = (aw - width) / 2;
        var yc = (ah - height) / 2;

        str += ", left=" + xc + ", screenX=" + xc;
        str += ", top=" + yc + ", screenY=" + yc;
        str += "toolbar=no,menubar=no,titlebar=no,resizable=yes,scrollbars="+scroll;
    }

    var pop = window.open(url, name, str);

    var childrenArray = null;
    var childrenArrayStr = "";
    while(!(childrenArray = eval(childrenArrayStr + "top.childrenArray"))) {
        if (eval(childrenArrayStr + "top.opener")) { // MODAL
            childrenArrayStr += "top.opener.";
        } else { // MODALESS
            childrenArrayStr += "dialogArguments.";
        }
    }
    childrenArray[childrenArray.length] = pop;

    return pop;
};

/**
 * gf_popupModal
 * 모달로 팝업시키기 위한 함수(1280 X 1024 인경우)
 *
 * @param  : path,arg,width,height,iTop,iLeft
 * @return : showModalDialog(path,arg,shape)
 * @author : 정재동
 * @date   : 2013/04/01
 */
gf_popupModal = function(path, arg, width, height, iTop, iLeft) {
    if (iTop == null) iTop = (screen.availHeight - height) / 2;
    if (iLeft == null) iLeft = (screen.availWidth - width) / 2;
    if (iTop < 100) iTop = 100;

    var shape = "dialogWidth:" + width + "px";
    shape += ";dialogHeight:" + height + "px";
    shape += ";dialogTop:" + iTop + "px";
    shape += ";dialogLeft:" + iLeft + "px";
    shape += ";help:No;status:no;scroll:no;resizable:yes";

    return showModalDialog(path, arg, shape);
};

/**
 * gf_popup
 * 팝업시키기 위한 함수(1024 X 768 인경우)
 *
 * @param  : path,arg,width,height,iTop,iLeft
 * @return : window.open(path,arg,shape)
 * @author	: 정재동
 * @date 	: 2013/04/01
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
 * cutByteString
 * 내용의 길이를 Byte로 체크하여 자르는 함수. 영문,숫자 - 1바이트, 한글 3바이트
 *
 * @param  : obj, 최대길이
 * @return : 없음
 * @author : 한숙향
 * @date   : 2013/04/01
 */
cutByteString = function(obj, maxlength) {
    var value = $(obj).val();
    var li_byte = 0;
    var li_len  = 0;

    for (var i = 0; i < value.length; i++) {
        if (escape(value.charAt(i)).length > 4) {
            li_byte += 3;
        } else {
            li_byte++;
        }

        if (li_byte > maxlength) {
            value = value.substr(0, li_len);
            break;
        }
    }
    return value;
};

/**
 * onkeylengthMax
 * obj의 내용이 최대길이를 넘는지 체크하는 함수. 영문,숫자 - 1바이트, 한글 3바이트
 * 사용법 : <textarea name="messagecnts" style="height: 96px;" onkeyup="onkeylengthMax(this, 4000);"></textarea>
 *
 * @param  : obj, 최대길이
 * @return : 없음
 * @author : 한숙향
 * @date   : 2013/04/01
 */
onkeylengthMax = function(obj, maxlength) {
    var li_byte = 0;
    var li_len  = 0;

    for (var i = 0; i < obj.value.length; i++) {
        if (escape(obj.value.charAt(i)).length > 4) {
            li_byte += 2;
        } else {
            li_byte++;
        }

        if (li_byte <= maxlength) {
            li_len = i + 1;
        }
    }

    if (li_byte >= maxlength) {
        alert("최대 (영문:" + maxlength + ", 한글:" + Math.floor(maxlength / 2) + ")자를 넘을수 없습니다.");
        obj.value = obj.value.substr(0, li_len);
    }
    obj.focus();
};

/**
 * getCheckedCnt
 * Form의 선택된 체크박스의 갯수를 반환
 *
 * @param   : 폼명
 * @return  : 선택된 체크박스의 갯수
 * @author	: 한숙향
 * @date 	: 2013/04/01
 */
getCheckedCnt = function(formName) {
    return $("form[name=" + formName + "] :checked").length;
};

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
    var reg = /[^\d]/;
    var val = obj.value;

    while (reg.test(val)) {
        val = val.replace(reg, "");
    }
    $(obj).val(val);
};

/**
 * allSelect
 * 체크박스 전체선택, 해제
 * 사용법 : <input type="checkbox" onclick="allSelect('selectcheck', this.checked);">
 *
 * @param  	 : obj
 * @return 	 : 없음
 * @author	 : 한숙향
 * @date 	 : 2013/04/01
 */
allSelect = function(objname, checkfg) {
   // $("input[name=" + objname + "]").attr("checked", checkfg);
   // disabled checkbox는 전체선택시에도 선택 안되도록 처리 2012.03.15
   $("input[name=" + objname + "]").each(function() {
        if(!$(this).attr("disabled")) {
            $(this).attr("checked", checkfg);
        }
    })
};

/**
 * lpad
 * length바이트공간에 base를 적고 나머지 바이트를 왼쪽으로 str로 채움
 * 사용법 : lpad("5", 2, "0"); -> "05"를 리턴함
 *
 * @param  	 : obj
 * @return 	 : 없음
 * @author	 : 한숙향
 * @date 	 : 2013/04/01
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
 * urlDecode
 * 한글파라미터를 디코딩
 *
 * @param   : 디코딩할 문자열, 디코드모드(detail - 엔터처리, list - 빈칸처리)
 * @return  : 디코딩된 문자열.
 * @author	: 이창희
 * @date 	: 2013/04/01
 */
urlDecode = function(utftext, mode) {
    if (utftext == undefined) {
        utftext = "";
    }

    if (mode == "detail") {
        utftext = utftext.replace(new RegExp("%u000a", "gi"), "\n");
        utftext = utftext.replace(new RegExp("&uml", "gi"), "'");
    } else if (mode == "html") {
        //utftext = utftext.replace(new RegExp(" ", "gi"), "&nbsp;");
        utftext = utftext.replace(new RegExp("%u000a", "gi"), "<br/>");
    } else  if (mode == "csel") {
        if (utftext.indexOf("</") != -1) {
            utftext = utftext.replace(new RegExp("%u000a", "gi"), "\n");
            utftext = utftext.replace(new RegExp("&uml", "gi"), "'");
        } else {
            //utftext = utftext.replace(new RegExp(" ", "gi"), "&nbsp;");
            utftext = utftext.replace(new RegExp("%u000a", "gi"), "<br/>");
        }
    } else {
        utftext = utftext.replace(new RegExp("%u000a", "gi"), " ");
        utftext = utftext.replace(new RegExp("&uml", "gi"), "'");
    }
    return utftext;
};

/**
 * convertTime
 * 시간타입으로 변환
 *
 * @param   : object
 * @return  : 변환된 문자
 * @author	: 한숙향
 * @date 	: 2013/04/01
 */
convertTime = function (obj) {
    removeNoneNum(obj);

    var str = obj.value.replaceAll(':','');
    var len = str.length;
    var chk = true;

    if (len == 4) {
        var hh = str.substring(0,2);
        var mm = str.substring(2,4);

        if (hh < 00 || hh > 23 || mm < 00 || mm > 59) {
            chk = false;
        }
    } else if (len < 4) {
        obj.focus();
        return;
    } else {
        chk = false;
    }

    if (chk) {
        str = str.substring(0,2) + ":" + str.substring(2,4);
        obj.value = str;
    } else {
        alert("시간입력이 잘못되었습니다.\r\n시간은 00~23, 분은 00~59 사이로 입력하세요.");
        obj.value = "";
        obj.focus();
        obj.select();
        return;
    }
};

/**
 * phoneNumStr
 * 숫자를 전화번호 형식으로 변환
 * 사용법 : phoneNumStr('01088889999'); -> 010-8888-9999
 *
 * @param   : 전화번호
 * @return  : 변환된 번호
 * @author	: 한숙향
 * @date 	: 2013/04/01
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

/**
 * maskPhone
 * 전화번호 숫자를 입력할때마다 전화번호 형식으로 변환
 * 사용법 : <input type="text" name="hometelno" class="ipt_m_b" style="width:110px;" onkeyup="maskPhone(this);" />
 *
 * @param   : 전화번호 입력 인풋 obj
 * @return  : 없음
 * @author	: 한숙향
 * @date 	: 2013/04/01
 */
maskPhone = function(obj) {
    obj.value = phoneNumStr(obj.value);
};

/**
 * stripTag
 * 태그제거
 *
 * @param   : html
 * @return  : text
 * @author	: 한숙향
 * @date 	: 2013/04/01
 */
stripTag = function(val) {
    return val.replace(/<(\/)?([a-zA-Z]*)(\s[a-zA-Z]*=[^>]*)?(\s)*(\/)?>/g, "");
};

/**
 * 검색 파라미터들을 JSON String 으로 만들어 리턴
 * 엑셀 다운로드 팝업
 *
 * @param 	: 폼명
 * @return 	: 없음.
 * @author	: 한숙향
 * @date 	: 2013/04/01
 */
getSearchParamJSON = function(formnm) {
    if (typeof(formnm) == "undefined") {
        formnm = "searchForm";
    }
    var formObject = $("form[name=" + formnm + "]").get(0);
    var jsonText = "{";
    var schPattern = /^[a-zA-Z0-9]+/;

    for (var i = 0; i < formObject.length; i++) {
        if (formObject[i].name.match(schPattern)) {
            var name = formObject[i].name;
            var value = formObject[i].value;
            var type = formObject[i].type;

            if (type == "checkbox") {
                if (!formObject[i].checked) {
                    value = "";
                }
            }

            if (jsonText != "{") {
                jsonText += ", ";
            }
            jsonText += "\'" + name + "\' : \'" + value + "\'";
        }
    }
    jsonText += "}";

    return jsonText;
};

/**
 * excel_down_popup
 * 엑셀 다운로드 팝업
 *
 * @param 	: 쿼리디비ID
 * @return 	: 없음.
 * @author	: 한숙향
 * @date 	: 2013/04/01
 */
excel_down_popup = function(querydbid, formnm) {
    var url = "/powerservice/common/excel_down_popup.do?querydbid=" + querydbid;
    var name = "excelDownPopup";
    var width = 305;
    var height = 485;

    var myWin = launchCenter(url, name, width, height);

    var o = document.createElement("input");
    o.type = "hidden";
    o.name = "param";
    o.id = "param";
    o.value = getSearchParamJSON(formnm);

    var myform = document.forms[0];
    var tempAction = myform.action;
    var tempTarget = myform.target;

    myform.method = "post";
    myform.target = name;
    myform.action = url;
    myform.appendChild(o);
    myform.focus();
    myform.submit();
    myform.removeChild(myform.param);

    myform.action = tempAction;
    myform.target = tempTarget;
};

/**
 * isHpno
 * 휴대폰번호인지 체크
 *
 * @param   : 휴대폰번호
 * @return  : rtn (0이상이면 휴대폰번호)
 * @author	: 한숙향
 * @date 	: 2013/04/01
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
 * specialStr_check
 * 특수문자 체크
 * 사용법 : <input type="text" name="telno" class="ipt_m_sch" onkeyup="specialStr_check(this);" style="width: 100px;">
 *
 * @param 	: obj
 * @return 	: 없음.
 * @author	: 김장미
 * @date 	: 2013/04/01
 */
specialStr_check = function(obj) {
    //var mikExp = /[$\\@\\\#%\^\&\*\(\)\<\>\+\_\{\}\`\~\=\'\"\|]/; // 제한할 특수문자
    var mikExp = /[\<\>\']/; // 제한할 특수문자
    // 현재 제한 되어 있는 특수문자 목록입니다.
    // $  @ # % ^ & * ( ) < > + _ { } ` ~ = ' " |

    var strPass = obj.value;
    var strLength = strPass.length;
    var lchar = obj.value.charAt((strLength) - 1);
    if(lchar.search(mikExp) != -1) {
       var tst = obj.value.substring(0, (strLength) - 1);
       alert ('       제한되어 있는 특수 문자 목록 \n'
             +'====================================\n'
//             +' $  @ # % ^ & * ( ) < > + _ { } ` ~ = ' + "'" + ' " | \n'
             + '                        < > ' + "'\n"
             +'====================================\n\n'
             +' 위의 특수문자는 사용할수 없습니다.');
       obj.value = tst;
  }
};

/**
 * specialStr_check_cnsl
 * 특수문자 체크(상담등록시)
 * 사용법 : <input type="text" name="telno" class="ipt_m_sch" onkeyup="specialStr_check_cnsl(this);" style="width: 100px;">
 *
 * @param 	: obj
 * @return 	: 없음.
 * @author	: 김장미
 * @date 	: 2013/04/01
 */
specialStr_check_qacnsl = function(obj) {
    var mikExp = /[$\\@\\\#%\^\&\*\(\)\<\>\+\_\{\}\`\~\=\'\"\|]/; // 제한할 특수문자
    var mikExp = /[\\]/; // 제한할 특수문자
    // 현재 제한 되어 있는 특수문자 목록입니다.
    // $  @ # % ^ & * ( ) < > + _ { } ` ~ = ' " |

    var strPass = obj.value;
    var strLength = strPass.length;
    var lchar = obj.value.charAt((strLength) - 1);
    if(lchar.search(mikExp) != -1) {
       var tst = obj.value.substring(0, (strLength) - 1);
       alert ('            제한되어 있는 특수 문자 목록 \n'
             +'==================================\n'
//             +' $  @ # % ^ & * ( ) < > + _ { } ` ~ = ' + "'" + ' " | \n'
             + '                             \\  ' + "\n"
             +'==================================\n'
             +'         위의 특수문자는 사용할수 없습니다.');
       obj.value = tst;
  }
};

/**
 * specialStr_check_cnsl
 * 특수문자 체크(상담등록시)
 * 사용법 : <input type="text" name="telno" class="ipt_m_sch" onkeyup="specialStr_check_cnsl(this);" style="width: 100px;">
 *
 * @param 	: obj
 * @return 	: 없음.
 * @author	: 김장미
 * @date 	: 2013/04/01
 */
specialStr_check_cnsl = function(obj) {
    var mikExp = /[$\\@\\\#%\^\&\*\(\)\<\>\+\_\{\}\`\~\=\'\"\|]/; // 제한할 특수문자
    var mikExp = /[\\\'\"\`\']/; // 제한할 특수문자
    // 현재 제한 되어 있는 특수문자 목록입니다.
    // $  @ # % ^ & * ( ) < > + _ { } ` ~ = ' " |

    var strPass = obj.value;
    var strLength = strPass.length;
    var lchar = obj.value.charAt((strLength) - 1);
    if(lchar.search(mikExp) != -1) {
       var tst = obj.value.substring(0, (strLength) - 1);
       alert ('            제한되어 있는 특수 문자 목록 \n'
             +'==================================\n'
//             +' $  @ # % ^ & * ( ) < > + _ { } ` ~ = ' + "'" + ' " | \n'
             + '                      \\  ' + ' " ' + "  `   '\n"
             +'==================================\n'
             +'         위의 특수문자는 사용할수 없습니다.');
       obj.value = tst;
  }
};

/**
 * logslog
 * 로컬 로그출력 함수
 * 사용법 : logslog(출력할문자열)
 *
 * @param 	: logstr
 * @return 	: 없음.
 * @author	: 이창희
 * @date 	: 2013/04/01
 */
logslog = function(logstr) {
    try {
        if (getValue("logoutForm", "logfg") == "Y")
            logs.Log(logstr);
    } catch(e) {}
};

/**
 * maskBirthd
 * 생년월일 숫자를 입력할때마다 생년월일 형식으로 변환
 * 사용법 : <input type="text" name="birthd" class="ipt_m_b" style="width:110px;" onkeyup="maskBirthd(this);" />
 *
 * @param   : 생년월일 입력 인풋 obj
 * @return  : 없음
 * @author	: 박병목
 * @date 	: 2013/04/01
 */
maskBirthd = function(obj) {
    obj.value = birthday(obj.value);
};

/**
 * birthday
 * 숫자를 생년월일 형식으로 변환
 * 사용법 : birthday('20111010'); -> 2011-10-10
 *
 * @param   : 생년월일
 * @return  : 변환된 번호
 * @author	: 박병목
 * @date 	: 2013/04/01
 */
birthday = function(str) {
    var RegNotNum  = /[^0-9]/g;
    var RegbirthdayNum = "";
    var DataForm   = "";

    // return blank
    if (str == "" || str == null) return "";

    // delete not number
    str = str.replace(RegNotNum,'');

    if (str.length < 5) {
        return str;
    }

    if (str.length > 8) {
        str = str.substring(0, 8);
    }

    if (str.length > 4 && str.length <= 6) {
        DataForm = "$1-$2";
        RegbirthdayNum = /([0-9]{4})([0-9]+)/;
    } else if (str.length > 6) {
        DataForm = "$1-$2-$3";
        RegbirthdayNum = /([0-9]{4})([0-9]{2})([0-9]+)/;
    }

    while (RegbirthdayNum.test(str)) {
        str = str.replace(RegbirthdayNum, DataForm);
    }
    return str;
};

/**
 * copy_clipboard
 * 클립보드에 복사
 *
 * @param   : 복사할내용
 * @return  : 없음
 * @author	: 한숙향
 * @date 	: 2013/04/01
 */
copy_clipboard = function(value) {
     if (!document.all || value == null || value == "") {
         return;
     }
     window.clipboardData.setData("text", value);
     alert("복사되었습니다.");
 };

 /**
  * allChecked
  * disabled 된 체크박스를 validate
  * 사용법 : allChecked(res, 'selectcheck');
  *
  * @param   : obj
  * @return  : 없음
  * @author	 : 김장미
  * @date 	 : 2013/04/01
  */
allChecked = function(obj, objname) {
    if (obj.length == 0) {
        alert("선택된 목록이 없습니다.");
        return false;
       } else {
           if ($("input:checkbox[name=" + objname + "]:checked").length == 0) {
               alert("선택된 목록이 없습니다.");
               return false;
        }
       }
    return true;
 };

/**
 * checkOff
 * 연결된 체크박스 해제
 * 사용법 : checkOff(this, 'searchForm', 'respid');
 *
 * @param   : 해제할 체크박스
 * @return  : 없음
 * @author	 : 김장미
 * @date 	 : 2013/04/01
 */
checkOff = function(obj, formNm, target) {
    if (obj.checked == false) {

        if (getObject(formNm, target + "Chk").attr("disabled") == false) {
            getObject(formNm, target + "Chk").attr("checked", "");
            setValue(formNm, target, "");
        } else {

        }
    }
}

/**
 * roundXL
 * 엑셀 스타일의 반올림 함수 정의
 * 사용법 : roundXL(n, digits);
 *
 * @param   : 숫자, 자리수
 * @return  : 반올림값(Float)
 * @author	 : 김장미
 * @date 	 : 2013/04/01
 */
roundXL = function(n, digits) {
    if (digits >= 0) return parseFloat(n.toFixed(digits)); // 소수부 반올림

    digits = Math.pow(10, digits); // 정수부 반올림
    var t = Math.round(n * digits) / digits;

    return parseFloat(t.toFixed(0));
}

formatNumber = function(num) {
  var str = String(num)
  var re = /(-?[0-9]+)([0-9]{3})/;

  while (re.test(str)) {
   str = str.replace(re, "$1,$2");
  }
  return str;
}

/******************************************************
 * 문자열 byte 체크(한글 포함)
 ******************************************************/
 getByteLength = function(data){
     var len = 0;
     var str = data.substring(0);
     if( str == null ) return 0;
     for(var i=0; i < str.length; i++) {
         var ch = escape(str.charAt(i));
         if( ch.length == 1 ) len++;
         else if( ch.indexOf("%u") != -1) len += 2;
         else if( ch.indexOf("%") != -1) len += ch.length/3;
     }
     return len;
 }

 /******************************************************
 * 숫자 등 문자열을 length 자리수의 문자열로 만들어준다(좌측에 str자리수만큼 더해주기) ex : 01, ****1 등
 ******************************************************/
 getDigitString = function( num, length, str) {
     var ret="";
     for(i=0;i<length;i++) ret = ret + str;
     ret = ret + num;
     ret = ret.substring(ret.length - length);
     return ret;
 }

 getCutString = function(data, strLength) {
     var rtnString = "";
     var len = 0;
     var str = data.substring(0);
     if( str == null ) return 0;

     for(var i=0; i < str.length; i++) {
         var ch = escape(str.charAt(i));
         if( ch.length == 1 ) len++;
         else if( ch.indexOf("%u") != -1) len += 2;
         else if( ch.indexOf("%") != -1) len += ch.length/3;

         if(len<strLength-3) rtnString += str.charAt(i);
     }
     if(len <= strLength) rtnString = data;
     else rtnString += "...";

     return rtnString;
 }


