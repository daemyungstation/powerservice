<%@page pageEncoding="UTF-8"%>
<!--
 Copyright (c) 2015 Company Inwoo Tech Inc.

 @author 차건호
 @version 1.0
 @date 2015/04/22
 @프로그램ID << AP-UI-9910 >>
 @설명 <<상담스크립트 상세정보>>
-->
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>상담스크립트 상세정보</title>

<!-- common include -->
<%@ include file="/include/common-js.jsp" %>

<style type="text/css">
body{overflow:hidden}
</style>

</head>

<body>

<!-- popup -->
<div class="popup_outer">
    <div class="p_top">
        <h2>상담스크립트 상세정보</h2>
<div class="pop_close" data-bind="click: function() {window.close();}"></div>
    </div>

    <div id="readForm" data-bind="with: selectedData" class="popup_inner bd0" style="max-height:550px">
        <div class="popup_wb">
            <ul class="detail_tit">
                <li class="tit ellipsis" style="width: 290px">
                    <span class="kind"><span data-bind="text: cons_typ1_nm"></span><span data-bind="visible: cons_typ2_nm() != null">&gt;</span><span data-bind="text: cons_typ2_nm"></span><span data-bind="visible: cons_typ3_nm() != null">&gt;</span><span data-bind="text: cons_typ3_nm"></span></span>
                </li>
                <li class="modi">수정자&nbsp;:&nbsp;<span data-bind="text: amnd_nm"></span>&nbsp;&nbsp;&nbsp;&nbsp;<span data-bind="text: moment(amnt_dttm(), 'YYYYMMDDHHmmss').isValid() ? moment(amnt_dttm(), 'YYYYMMDDHHmmss').format('YYYY-MM-DD HH:mm') : ''"></span></li>
            </ul>

            <div class="txt_title">
                <pre data-bind="html: cons_scrt_titl"></pre>
            </div>

            <div class="txt_view">
                <pre data-bind="html: cons_scrt_html_cntn"></pre>
            </div>

            <ul class="attch_outter">
                <li data-bind="kendoListView: $parent.faqListviewConfig" class="attch"></li>
                <li class="regi">등록자&nbsp;:&nbsp;<span data-bind="text: rgsr_nm"></span>&nbsp;&nbsp;&nbsp;&nbsp;<span data-bind="text: moment(rgsn_dttm(), 'YYYYMMDDHHmmss').isValid() ? moment(rgsn_dttm(), 'YYYYMMDDHHmmss').format('YYYY-MM-DD HH:mm') : ''"></span></li>
            </ul>

        </div>
    </div>
</div>
<!-- //popup -->


<!-- Java script -->
<script type="text/javascript">

    /**
     * 전역변수
     */
    var CselscriptView = function() {
        this.cons_typ1_nm = ko.observable();
        this.cons_typ2_nm = ko.observable();
        this.cons_typ3_nm = ko.observable();
        this.cons_scrt_titl = ko.observable();
        this.cons_scrt_cntn = ko.observable();
        this.cons_scrt_html_cntn = ko.observable();
        this.amnd_nm = ko.observable();
        this.rgsr_nm = ko.observable();
        this.amnt_dttm = ko.observable();
        this.rgsn_dttm = ko.observable();
    };

    var SearchParam = function() {
        this.cons_scrt_id = ko.observable("${param.cons_scrt_id}");
    };

    var viewModel = new function() {
        var self = this;

        this.selectedData = ko.observable(new CselscriptView());
        this.searchParam = ko.observable(new SearchParam());

        this.items = ko.observableArray();

        this.goSearch = function() {
            var view = $("#readForm");

            kendo.ui.progress(view, true);

                $.inwooAjaxJson(inwoo.config.contextRoot + "/total-sch/cons-scrt/view", ko.toJS(self.searchParam()), function(obj) {
                    if (obj.result == "OK") {
                        ko.mapping.fromJS(obj.data.viewData, {}, self.selectedData());

                        var searchValue = decodeURIComponent("${param.search_val}");

                        var searchWord = "${param.searchType}";

                        var replaceValue = "<span class='bg_y'>" + searchValue + "</span>";

                        if (searchValue != "") {
                            searchValue = new RegExp(searchValue, "gi");

                            if ("cons_scrt_titl" == searchWord) {
                                self.selectedData().cons_scrt_titl(self.selectedData().cons_scrt_titl().replace(searchValue, replaceValue));
                            } else if ("cons_scrt_cntn" == searchWord) {
                                self.selectedData().cons_scrt_html_cntn(self.selectedData().cons_scrt_html_cntn().replace(searchValue, replaceValue));
                            } else {
                                self.selectedData().cons_scrt_titl(self.selectedData().cons_scrt_titl().replace(searchValue, replaceValue));
                                self.selectedData().cons_scrt_html_cntn(self.selectedData().cons_scrt_html_cntn().replace(searchValue, replaceValue));
                            }

                        }
                    } else {
                        alert(obj.errMsg);
                    }
                    kendo.ui.progress(view, false);
            });
        };

        this.goSearch();
    };

    /**
     * $(document).ready
     * jquery ready 이벤트를 받아서 실행(onload보다는 조금 더 빨리 실행됨)
     *
     * @param     : 없음.
     * @return     : 없음.
     * @author    : 한숙향
     * @date     : 2013/04/01
     */
    $(document).ready(function () {
        ko.applyBindings(viewModel);
    });

</script>
</body>
</html>
