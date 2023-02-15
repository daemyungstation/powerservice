/**
 * @(#)common.js
 *
 * Copyright     Copyright (c) 2013
 * Company       Inwoo Tech Inc.
 *
 * @author      정용재
 * @version        1.0
 * @date        2013/07/11
 */

    $.inwooAjax("/powerservice/popup/list", "", callback_test);

    function callback_test(obj) {
        //alert(obj.data[1].name);
        for (var i=0; i<obj.data.length; i++) {
            eval(obj.data[i].name + " = new popupInfo('" + obj.data[i].url + "', '" + obj.data[i].width + "','" + obj.data[i].height + "');");
        }
    };

    popupInfo = function(url, width, height) {
        this.url = url;
        this.width = width;
        this.height = height;
    };

    /**
     * openPopup
     * 센터 목록 팝업
     *
     * @param   : 없음.
     * @return  : 없음.
     * @author  : 김장미
     * @date    : 2013/04/01
     */
    openPopup = function(target, urlname, w, h) {
        var url = null;
        var name = null;
        var opt = null;

        if (urlname == undefined) {
            url = "/powerservice" + target.url;
            name = target.name;
            opt = "height=" + target.height + ", innerHeight=" + target.height + ", width=" + target.width + ", innerWidth=" + target.width;

            if (window.screen) {
                var ah = screen.availHeight - 30;
                var aw = screen.availWidth - 10;
                var xc = (aw - target.width) / 2;
                var yc = (ah - target.height) / 2;

                opt += ", left=" + xc + ", screenX=" + xc;
                opt += ", top=" + yc + ", screenY=" + yc;
                opt += "toolbar=no,menubar=no,titlebar=no,resizable=yes,scrollbars="+scroll;
            }
        } else {
            url = target;
            name = urlname;

            var width = w == undefined ? "1000" : w;
            var height = h == undefined ? "800" : h;
            opt = "width=" + width + ", height=" + height;
            opt += ", top=0, left=0 ,channelmode=no, directories=yes, location=yes";
            opt += ", menubar=yes, resizable=yes, scrollbars=yes, status=yes, titlebar=yes, toolbar=yes";
        }

        var pop = window.open(url, name, opt);
        pop.focus();
    };

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
        var url = "${pageContext.request.contextPath}/syst/center/center_list_popup.do";
        var name = "centerListPopup";
        var width = 700;
        var height = 500;

        var obj = launchCenter(url, name, width, height);
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