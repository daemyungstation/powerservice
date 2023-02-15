<%@page pageEncoding="UTF-8"%>
<!--
 Copyright (c) 2015 Company Inwoo Tech Inc.

 @author 차건호
 @version 1.0
 @date 2015/04/15
 @프로그램ID << AP-UI-0603 >>
 @설명 << 상담자료관리 - FAQ관리 - 보기 팝업 >>
-->
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>FAQ 상세정보</title>

<!-- common include -->
<%@ include file="/include/common-js.jsp" %>

<style type="text/css">
body{overflow:hidden}
</style>

</head>

<body>

<!-- popup -->
<div class="popup_outer">
    <c:if test="${empty param.popupfg }">
    <div class="p_top">
        <h2>FAQ 상세정보</h2>
        <div class="pop_close" data-bind="click: function() {window.close();}"></div>
    </div>
    </c:if>

    <div id="read-form" data-bind="with: selectedData" class="popup_inner bd0"
        <c:choose>
            <c:when test="${not empty param.height }">style="max-height:550px; height:${param.height}px"</c:when>
            <c:otherwise>style="max-height:550px;"</c:otherwise>
        </c:choose>>
        <div class="popup_wb">
            <ul class="detail_tit">
                <li class="tit ellipsis" style="width: 290px">
                    <span class="kind"><span data-bind="text: cons_typ1_nm"></span><span data-bind="visible: cons_typ2_nm() != null">&nbsp;&gt;</span><span data-bind="text: cons_typ2_nm"></span><span data-bind="visible: cons_typ3_nm() != null">&nbsp;&gt;</span><span data-bind="text: cons_typ3_nm"></span></span>
                </li>
                <li class="modi">수정자&nbsp;:&nbsp;<span data-bind="text: amnd_nm"></span>&nbsp;&nbsp;&nbsp;&nbsp;조회수&nbsp;:&nbsp;<span data-bind="text: inq_cnt"></span>&nbsp;&nbsp;&nbsp;&nbsp;<span data-bind="text: moment(amnt_dttm(), 'YYYYMMDDHHmmss').isValid() ? moment(amnt_dttm(), 'YYYYMMDDHHmmss').format('YYYY-MM-DD HH:mm') : ''"></span></li>
            </ul>

            <div class="txt_title">
                <pre data-bind="html: faq_qust_cntn"></pre>
            </div>

            <div class="txt_view">
                <pre data-bind="html: faq_ansr_html_cntn"></pre>
            </div>

            <ul class="attch_outter">
                <li data-bind="kendoListView: $parent.faqListviewConfig" class="attch"></li>
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
    var Faq = function() {
        this.page_indx = ko.observable();
        this.faq_id = ko.observable();
        this.cntr_cd = ko.observable();
        this.cons_typ1_cd = ko.observable();
        this.cons_typ2_cd = ko.observable();
        this.cons_typ3_cd = ko.observable();
        this.faq_qust_cntn = ko.observable();
        this.faq_ansr_cntn = ko.observable();
        this.faq_ansr_html_cntn = ko.observable();
        this.open_stat_cd = ko.observable("Y");
        this.inq_cnt = ko.observable();
        this.rgsr_id = ko.observable();
        this.rgsn_dttm = ko.observable();
        this.amnd_id = ko.observable();
        this.amnt_dttm = ko.observable();
        this.open_stat_nm = ko.observable();
        this.cons_typ1_nm = ko.observable();
        this.cons_typ2_nm = ko.observable();
        this.cons_typ3_nm = ko.observable();
        this.rgsr_nm = ko.observable();
        this.amnd_nm = ko.observable();
    };

    var SearchParam = function() {
        this.faq_id = ko.observable("${param.faq_id}");
        this.page_typ = ko.observable("${param.page_typ}");
        this.rltn_tbl_nm = ko.observable("TB_FAQ_DTL");
    };

    var viewModel = new function() {
        var self = this;

        this.selectedData = ko.observable(new Faq());
        this.searchParam = ko.observable(new SearchParam());

        this.items = ko.observableArray();

        this.goSearch = function() {
            var view = $("#read-form");

            kendo.ui.progress(view, true);

            $.inwooAjaxJson(inwoo.config.contextRoot + "/total-sch/faq/view", ko.toJS(self.searchParam()), function(obj) {
                if (obj.result == "OK") {
                    ko.mapping.fromJS(obj.data.viewData, {}, self.selectedData());

                    var sFileList = obj.data.fileList;

                    for (var i = 0; i < sFileList.length; i++) {
                        self.items.push(sFileList[i]);
                    }

                    var sSearchVal = decodeURIComponent("${param.search_val}");

                    var sSearchWord = "${param.search_typ}";

                    var replaceValue = "<span class='bg_y'>" + sSearchVal + "</span>";

                    if (sSearchVal != "") {
                        sSearchVal = new RegExp(sSearchVal, "gi");

                        if("faq_qust_cntn" == sSearchWord) {
                                self.selectedData().faq_qust_cntn(self.selectedData().faq_qust_cntn().replace(sSearchVal, replaceValue));
                        } else if ("faq_ansr_cntn" == sSearchWord) {
                                self.selectedData().faq_ansr_html_cntn(self.selectedData().faq_ansr_html_cntn().replace(sSearchVal, replaceValue));
                        } else {
                                self.selectedData().faq_qust_cntn(self.selectedData().faq_qust_cntn().replace(sSearchVal, replaceValue));
                                self.selectedData().faq_ansr_html_cntn(self.selectedData().faq_ansr_html_cntn().replace(sSearchVal, replaceValue));
                        }
                    }

                /*  if (sSearchVal != "") {
                    sSearchVal = new RegExp(sSearchVal, "gi");

                    if("faq_qust_cntn" == sSearchWord) {
                        if (self.selectedData().faq_qust_cntn() != null) {
                            self.selectedData().faq_qust_cntn(self.selectedData().faq_qust_cntn().replace(sSearchVal, replaceValue));
                        }
                    } else if ("faq_ansr_cntn" == sSearchWord) {
                        if (self.selectedData().faq_ansr_html_cntn() != null) {
                            self.selectedData().faq_ansr_html_cntn(self.selectedData().faq_ansr_html_cntn().replace(sSearchVal, replaceValue));
                        }
                    } else {
                        if (self.selectedData().faq_qust_cntn() != null) {
                            self.selectedData().faq_qust_cntn(self.selectedData().faq_qust_cntn().replace(sSearchVal, replaceValue));
                        }
                        if (self.selectedData().faq_ansr_html_cntn() != null) {
                            self.selectedData().faq_ansr_html_cntn(self.selectedData().faq_ansr_html_cntn().replace(sSearchVal, replaceValue));
                        }
                    }
                }
           */
                } else {
                    alert(obj.errMsg);
                }

                kendo.ui.progress(view, false);
            });
        };

        this.faqListviewConfig = {
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
