<%@page pageEncoding="UTF-8"%>
<!--
 Copyright (c) 2015 Company Inwoo Tech Inc.

 @author 차건호
 @version 1.0
 @date 2015/04/21
 @프로그램ID <<PS_UI_9904>>
 @설명 << 공통화면 - 공지사항 상세정보 >>
-->
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>공지사항 상세정보</title>

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
        <h2>공지사항 상세정보</h2>
<div class="pop_close" data-bind="click: function() {window.close();}"></div>
    </div>

    <div id="readForm" data-bind="with: selectedData" class="popup_inner bd0" style="max-height:509px">
    <div class="popup_wb">
        <ul class="detail_tit">
        <li class="tit ellipsis short" style="width: 290px">
        [<span data-bind="text: ancm_typ_nm, class: ancm_typ_cd() == '10' ? 'fc_red' : ''"></span>] <span data-bind="html: ancm_mtr_titl, attr: {title: ancm_mtr_titl}"></span>
        </li>
        <li class="modi">수정자&nbsp;:&nbsp;<span data-bind="text: amnd_nm"></span>&nbsp;&nbsp;&nbsp;&nbsp;조회수&nbsp;:&nbsp;<span data-bind="text: inq_cnt"></span>&nbsp;&nbsp;&nbsp;&nbsp;<span data-bind="text: moment(amnt_dttm(), 'YYYYMMDDHHmmss').isValid() ? moment(amnt_dttm(), 'YYYYMMDDHHmmss').format('YYYY-MM-DD HH:mm') : ''"></span></li>
        </ul>
        <div class="txt_view">
            <pre data-bind="html: ancm_mtr_html_cntn"></pre>
        </div>

        <ul class="attch_outter">
            <li data-bind="kendoListView: $parent.fileListviewConfig" class="attch"></li>
            <li class="regi">등록자&nbsp;:&nbsp;<span data-bind="text: rgsr_nm"></span>&nbsp;&nbsp;&nbsp;&nbsp;<span data-bind="text: moment(rgsn_dttm(), 'YYYYMMDDHHmmss').isValid() ? moment(rgsn_dttm(), 'YYYYMMDDHHmmss').format('YYYY-MM-DD HH:mm') : ''"></span></li>
        </ul>

        </div>
    </div>
</div>
<!-- //popup -->


<script id="file-listview-template" type="text/html">
<span data-bind="text: file_nm, click: function() {location.href = inwoo.config.contextRoot + '/file/download?file_id=' + file_id}" class="filename"></span>
</script>

<!-- Java script -->
<script type="text/javascript">

    /**
     * 전역변수
     */
    var AncmMtr = function() {
        this.page_indx = ko.observable();
        this.ancm_mtr_id = ko.observable();
        this.ancm_mtr_titl = ko.observable();
        this.use_yn = ko.observable("Y");
        this.ancm_chpr_id = ko.observable();
        this.ancm_mtr_cntn = ko.observable();
        this.ancm_mtr_html_cntn = ko.observable();
        this.ancm_stt_dt = ko.observable().extend({momentFormat: "YYYYMMDD"});
        this.ancm_end_dt = ko.observable().extend({momentFormat: "YYYYMMDD"});
        this.inq_cnt = ko.observable();
        this.ancm_typ_cd = ko.observable("10");
        this.cntr_cd = ko.observable();
        this.rgsr_id = ko.observable();
        this.rgsn_dttm = ko.observable();
        this.amnd_id = ko.observable();
        this.amnt_dttm = ko.observable();
        this.ancm_typ_nm = ko.observable();
        this.rgsr_nm = ko.observable();
        this.amnd_nm = ko.observable();
        this.file_flag = ko.observable();
    };

    var SearchParam = function() {
        this.ancm_mtr_id = ko.observable("${param.ancm_mtr_id}");
        this.pageType = ko.observable("${param.pageType}");
        this.rltn_tbl_nm = ko.observable("TB_ANCM_MTR_DTL");
    };

    var viewModel = new function() {
        var self = this;

        this.selectedData = ko.observable(new AncmMtr());
        this.searchParam = ko.observable(new SearchParam());

        this.items = ko.observableArray();

        this.goSearch = function() {
            var view = $("#readForm");

            kendo.ui.progress(view, true);

            $.inwooAjaxJson(inwoo.config.contextRoot + "/total-sch/ancm-mtr-dtl/view", ko.toJS(self.searchParam()), function(obj) {
                if (obj.result == "OK") {
                    ko.mapping.fromJS(obj.data.viewData, {}, self.selectedData());

                    var fileList = obj.data.fileList;

                    for (var i = 0; i < fileList.length; i++) {
                        self.items.push(fileList[i]);
                    }

                    var searchValue = decodeURIComponent("${param.search_val}");

                    var searchWord = "${param.searchType}";

                    var replaceValue = "<span class='bg_y'>" + searchValue + "</span>";

                        console.log(self.selectedData().ancm_mtr_html_cntn());
                    if (searchValue != "") {
                        searchValue = new RegExp(searchValue, "gi");

                        if ("ancm_mtr_titl" == searchWord) {
                            self.selectedData().ancm_mtr_titl(self.selectedData().ancm_mtr_titl().replace(searchValue, replaceValue));
                        } else if ("ancm_mtr_cntn" == searchWord) {
                            self.selectedData().ancm_mtr_html_cntn(self.selectedData().ancm_mtr_html_cntn().replace(searchValue, replaceValue));
                        } else {
                            self.selectedData().ancm_mtr_titl(self.selectedData().ancm_mtr_titl().replace(searchValue, replaceValue));
                            self.selectedData().ancm_mtr_html_cntn(self.selectedData().ancm_mtr_html_cntn().replace(searchValue, replaceValue));
                        }
                    }
                } else {
                    alert(obj.errMsg);
                }
                kendo.ui.progress(view, false);
            });
        };

        this.fileListviewConfig = {
            data: self.items,
            template: "file-listview-template",
            useKOTemplates: true
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
