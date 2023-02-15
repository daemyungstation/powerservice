<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!--
 Copyright (c) 2015 Company Inwoo Tech Inc.

 @author 이희철
 @version 1.0
 @date 2015/04/01
 @프로그램ID << total-search >>
 @설명 << 통합검색 >>
-->
<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>통합검색</title>

<!-- common include -->
<%@ include file="/include/common-js.jsp" %>
<!-- //common include -->

<style type="text/css">
body{background:none}
body{overflow:hidden}
.bg_y{background-color:yellow}
</style>

</head>

<body>

<div class="popup_outer t_s">

<div class="p_top">
<h2>통합검색</h2>
<div class="pop_close" data-bind="click: function() {window.close();}"></div>
</div>

<div class="popup_inner bg_w bdr0 max_no">

<!-- header -->
<%@include file="total-search-header.jsp" %>
<!-- header -->

<!-- Left -->
<%@include file="total-search-left.jsp" %>
<!-- Left -->

<!-- Right -->
<div class="total_right_con">

<!-- FAQ -->
<div id="faq-list-area" class="ts_con_box" data-bind="with: faqViewModel">
    <!-- title -->
    <ul class="ts_tit_box">
    <li class="tit">FAQ</li>
    <li class="txt">검색결과 <strong data-bind="text: total" class="fc_orange">0</strong>건</li>
    <li class="bt" data-bind="click: $root.selectLeftMenu.bind($data, 'faq'), visible: $root.srchTypView() == ''">더보기 ▼</li>
    </ul>
    <!-- //title -->

    <div id="blank" style="height:30px"></div>

    <div data-bind="kendoListView: faqListviewConfig"></div>
    <div id="faq-list-pager" data-bind="visible: $root.srchTypView() == 'faq'" class="k-search-pager-wrap"></div>

    <!-- 검색내용 -->
    <script id="faq-listview-template" type="text/html">
        <ul class="ts_cont first">
            <li class="tit1" data-bind="html: procSrchCntn(faq_qust_cntn), click: viewModel.openViewPopup.bind($data, 'faq', faq_id, $parent.searchJSON().srch_word)"></li>
            <li class="tit2">|&nbsp;<span data-bind="text: moment(rgsn_dttm, 'YYYYMMDDHHmmss').format('YYYY-MM-DD HH:mm:ss')"></span>&nbsp;&nbsp;&nbsp;조회수:&nbsp;<span data-bind="text: inq_cnt"></span></li>
            <li class="path"><span data-bind="text: cons_typ1_nm"></span><span class="clamp">&gt;</span><span data-bind="text: cons_typ2_nm"></span><span class="clamp">&gt;</span><span data-bind="text: cons_typ3_nm"></span></li>
            <li data-bind="html: procSrchCntn(faq_qust_cntn)" class="txt"></li> <!-- DB에서 글자 수 제한 걸어 주삼-->
            <li data-bind="html: procSrchCntn(faq_ansr_cntn)" class="txt" style="border-top:1px solid #eee"></li> <!-- DB에서 글자 수 제한 걸어 주삼-->
        </ul>
    </script>
    <!-- //검색내용 -->
</div>
<!-- //FAQ -->

<!-- 공지사항 -->
<div id="ancm-mtr-list-area" class="ts_con_box" data-bind="with: ancmMtrViewModel">
    <!-- title -->
    <ul class="ts_tit_box">
    <li class="tit">공지사항</li>
    <li class="txt">검색결과 <strong data-bind="text: total" class="fc_orange">0</strong>건</li>
    <li class="bt" data-bind="click: $root.selectLeftMenu.bind($data, 'ancm_mtr'), visible: $root.srchTypView() == ''">더보기 ▼</li>
    </ul>
    <!-- //title -->

    <div id="blank" style="height:30px"></div>

    <div data-bind="kendoListView: ancmMtrListviewConfig"></div>
    <div id="ancm-mtr-list-pager" data-bind="visible: $root.srchTypView() == 'ancm_mtr'" class="k-search-pager-wrap"></div>

    <!-- 검색내용 -->
    <script id="ancm-mtr-listview-template" type="text/html">
        <ul class="ts_cont first">
            <li class="tit1" data-bind="html: '[' + ancm_typ_nm + '] ' + procSrchCntn(ancm_mtr_titl), click: viewModel.openViewPopup.bind($data, 'ancm_mtr', ancm_mtr_id, $parent.searchJSON().srch_word)"></li>
            <li class="tit2">|&nbsp;<span data-bind="text: moment(rgsn_dttm, 'YYYYMMDDHHmmss').format('YYYY-MM-DD HH:mm:ss')"></span>&nbsp;&nbsp;&nbsp;조회수:&nbsp;<span data-bind="text: inq_cnt"></span></li>
            <li data-bind="html: procSrchCntn(ancm_mtr_cntn)" class="txt"></li> <!-- DB에서 글자 수 제한 걸어 주삼-->
    </ul>
    </script>
    <!-- //검색내용 -->
</div>
<!-- //공지사항 -->

<!-- 게시판 -->
<div id="nobd-list-area" class="ts_con_box" data-bind="with: nobdViewModel">
    <!-- title -->
    <ul class="ts_tit_box">
    <li class="tit">게시판</li>
    <li class="txt">검색결과 <strong data-bind="text: total" class="fc_orange">0</strong>건</li>
    <li class="bt" data-bind="click: $root.selectLeftMenu.bind($data, 'nobd'), visible: $root.srchTypView() == ''">더보기 ▼</li>
    </ul>
    <!-- //title -->

    <div id="blank" style="height:30px"></div>

    <!-- 검색내용 -->
    <div data-bind="kendoListView: nobdListviewConfig"></div>
    <div id="nobd-list-pager" data-bind="visible: $root.srchTypView() == 'nobd'" class="k-search-pager-wrap"></div>

    <script id="nobd-listview-template" type="text/html">
        <ul class="ts_cont first">
            <li class="tit1" data-bind="html: '[' + nobd_bswr_div_nm + '] ' + procSrchCntn(nobd_titl), click: viewModel.openViewPopup.bind($data, 'nobd', nobd_id, $parent.searchJSON().srch_word)"></li>
            <li class="tit2">|&nbsp;<span data-bind="text: moment(rgsn_dttm, 'YYYYMMDDHHmmss').format('YYYY-MM-DD HH:mm:ss')"></span>&nbsp;&nbsp;&nbsp;조회수:&nbsp;<span data-bind="text: inq_cnt"></span></li>
            <li data-bind="html: procSrchCntn(nobd_cntn)" class="txt"></li> <!-- DB에서 글자 수 제한 걸어 주삼-->
        </ul>
    </script>
    <!-- //검색내용 -->
</div>
<!-- //게시판 -->

<!-- 상담스크립트 -->
<div id="cons-scrt-list-area" class="ts_con_box" data-bind="with: consScrtExmpViewModel">
    <!-- title -->
    <ul class="ts_tit_box">
    <li class="tit">상담스크립트</li>
    <li class="txt">검색결과 <strong data-bind="text: total" class="fc_orange">0</strong>건</li>
    <li class="bt" data-bind="click: $root.selectLeftMenu.bind($data, 'cons_scrt'), visible: $root.srchTypView() == ''">더보기 ▼</li>
    </ul>
    <!-- //title -->

    <div id="blank" style="height:30px"></div>

    <!-- 검색내용 -->
    <div data-bind="kendoListView: consScrtExmpListviewConfig"></div>
    <div id="cons-scrt-list-pager" data-bind="visible: $root.srchTypView() == 'cons_scrt'" class="k-search-pager-wrap"></div>

    <script id="cons-scrt-listview-template" type="text/html">
        <ul class="ts_cont first">
            <li class="tit1" data-bind="html: procSrchCntn(cons_scrt_titl), click: viewModel.openViewPopup.bind($data, 'cons_scrt', cons_scrt_id, $parent.searchJSON().srch_word)"></li>
            <li class="tit2">|&nbsp;<span data-bind="text: moment(rgsn_dttm, 'YYYYMMDDHHmmss').format('YYYY-MM-DD HH:mm:ss')"></span></li>
            <li class="path"><span data-bind="text: cons_typ1_nm"></span><span class="clamp">&gt;</span><span data-bind="text: cons_typ2_nm"></span><span class="clamp">&gt;</span><span data-bind="text: cons_typ3_nm"></span></li>
            <li data-bind="html: procSrchCntn(cons_scrt_cntn)" class="txt"></li> <!-- DB에서 글자 수 제한 걸어 주삼-->
        </ul>
    </script>
    <!-- //검색내용 -->
</div>
<!-- //상담스크립트 -->

</div>
<!-- //Right -->

</div>
</div>

<script type="text/javascript">

    /**
     * 전역변수
     */
    var SearchParam = function() {
        var self = this;

        // 검색유형 선택 이벤트 처리
        this.isOpen = ko.observable(false);
        this.srchtypcdList = ko.observableArray(
                [{value: "", text: "전체검색"},
                 {value: "faq", text: "FAQ"},
                 {value: "ancm_mtr", text: "공지사항"},
                 {value: "nobd", text: "게시판"},
                 {value: "cons_scrt", text: "상담스크립트"}]
            );

        this.toggle = function() {
            self.isOpen(!self.isOpen());
        };

        this.selectSrchTyp = function(poData) {
            self.isOpen(false);
            self.srch_typ_cd(poData.value);
            self.srch_typ_nm(poData.text);

            $("#srch_word").select();
        };

        this.goSearch = function() {
            viewModel.goSearch();
        };


        // 검색조건
        this.totl_srch_yn = ko.observable("Y");
        this.nobd_typ_id = ko.observable("all"); // "NBD2015042020150411"
        this.srch_word = ko.observable(decodeURIComponent("${param.srchword}") == "" ? "검색할 키워드를 입력하세요" : decodeURIComponent("${param.srchword}"));
        this.srch_typ_cd = ko.observable("${param.srchtyp}");
        this.srch_typ_nm = ko.computed({
            read: function () {
                var sSrchTypNm = "";
                var oSrchTypCdList = this.srchtypcdList();
                for (var i = 0; i < oSrchTypCdList.length; i++) {
                    if (oSrchTypCdList[i].value == this.srch_typ_cd()) {
                        sSrchTypNm = oSrchTypCdList[i].text;
                        break;
                    }
                }
                return sSrchTypNm;
            },
            write: function (value) {

            },
            owner: this
        });

    };

    var FaqViewModel = function(masterVM) {
        var self = this;
        this.masterVM = ko.observable(masterVM);

        this.searchJSON = ko.observable(ko.toJS(masterVM.searchParam()));
        this.selectedData = ko.observable();

        this.total = ko.observable(0);
        this.inq_cnt = ko.observable();

        this.dataSource = new kendo.data.DataSource(
            inwoo.kendo.listview.dataSource({
                context: self,
                url: inwoo.config.contextRoot + "/total-sch/faq/list/usr",
                pageSize: 3,
                sort: {field: "amnt_dttm", dir: "desc"}
            })
        );

        this.faqListviewConfig = {
            data: {},
            dataSource: self.dataSource,
            template: "faq-listview-template",
            useKOTemplates: true,
            autoBind: false
        };

        this.goSearch = function() {
            self.searchJSON(ko.toJS(masterVM.searchParam()));
            self.dataSource.pageSize(masterVM.srchTypView() == "" ? 3 : 10);
            self.dataSource.page(1);
        };

        this.init = function() {
            $("#faq-list-pager").kendoPager({
                dataSource: self.dataSource,
                autoBind: false
            });
        };

        self.init();
    };

    var ConsScrtExmpViewModel = function(masterVM) {
        var self = this;
        this.masterVM = ko.observable(masterVM);

        this.searchJSON = ko.observable(ko.toJS(masterVM.searchParam()));
        this.selectedData = ko.observable();

        this.total = ko.observable(0);

        this.dataSource = new kendo.data.DataSource(
            inwoo.kendo.listview.dataSource({
                context: self,
                url: inwoo.config.contextRoot + "/total-sch/cons-scrt/list/usr",
                pageSize: 3,
                sort: {field: "amnt_dttm", dir: "desc"}
            })
        );

        this.consScrtExmpListviewConfig = {
            data: {},
            dataSource: self.dataSource,
            template: "cons-scrt-listview-template",
            useKOTemplates: true,
            autoBind: false
        };

        this.goSearch = function() {
            self.searchJSON(ko.toJS(masterVM.searchParam()));
            self.dataSource.pageSize(masterVM.srchTypView() == "" ? 3 : 10);
            self.dataSource.page(1);
        };

        this.init = function() {
            $("#cons-scrt-list-pager").kendoPager({
                dataSource: self.dataSource,
                autoBind: false
            });
        };

        self.init();
    };

    var AncmMtrViewModel = function(masterVM) {
        var self = this;
        this.masterVM = ko.observable(masterVM);

        this.searchJSON = ko.observable(ko.toJS(masterVM.searchParam()));
        this.selectedData = ko.observable();

        this.total = ko.observable(0);
        this.inq_cnt = ko.observable();

        this.dataSource = new kendo.data.DataSource(
            inwoo.kendo.listview.dataSource({
                context: self,
                url: inwoo.config.contextRoot + "/total-sch/ancm-mtr-dtl/list/usr",
                pageSize: 3,
                sort: {field: "amnt_dttm", dir: "desc"}
            })
        );

        this.ancmMtrListviewConfig = {
            data: {},
            dataSource: self.dataSource,
            template: "ancm-mtr-listview-template",
            useKOTemplates: true,
            autoBind: false
        };

        this.goSearch = function() {
            self.searchJSON(ko.toJS(masterVM.searchParam()));
            self.dataSource.pageSize(masterVM.srchTypView() == "" ? 3 : 10);
            self.dataSource.page(1);
        };

        this.init = function() {
            $("#ancm-mtr-list-pager").kendoPager({
                dataSource: self.dataSource,
                autoBind: false
            });
        };

        self.init();
    };

    var NobdViewModel = function(masterVM) {
        var self = this;
        this.masterVM = ko.observable(masterVM);

        this.searchJSON = ko.observable(ko.toJS(masterVM.searchParam()));
        this.selectedData = ko.observable();

        this.total = ko.observable(0);

        this.dataSource = new kendo.data.DataSource(
            inwoo.kendo.listview.dataSource({
                context: self,
                url: inwoo.config.contextRoot + "/total-sch/nobd/list/all",
                pageSize: 3,
                sort: {field: "amnt_dttm", dir: "desc"}
            })
        );

        this.nobdListviewConfig = {
            data: {},
            dataSource: self.dataSource,
            template: "nobd-listview-template",
            useKOTemplates: true,
            autoBind: false
        };

        this.goSearch = function() {
            self.searchJSON(ko.toJS(masterVM.searchParam()));
            self.dataSource.pageSize(masterVM.srchTypView() == "" ? 3 : 10);
            self.dataSource.page(1);
        };

        this.init = function() {
            $("#nobd-list-pager").kendoPager({
                dataSource: self.dataSource,
                autoBind: false
            });
        };

        self.init();
    };


    var SearchUsefulPageParam = function() {
        this.useful_page = ko.observable();

        this.onSelect = function(e) {
            var dataItem = this.dataItem(e.item);
            openLeftPopup(dataItem.bkmk_addr, dataItem.scrn_widt_vl, dataItem.scrn_hght_vl);
        };

    };

    var SearchRelatedPageParam = function() {
        this.related_page = ko.observable();

        this.onSelect = function(e) {
            var dataItem = this.dataItem(e.item);

            openLeftPopup(dataItem.bkmk_addr, dataItem.scrn_widt_vl, dataItem.scrn_hght_vl);
        };
    };

    var FavoritesViewModel = function() {
        var self = this;

        var FavoritesSearchParam = function() {
            this.team2_cd = ko.observable("${param.team_cd}");
            this.user_id = ko.observable("${param.user_id}");
        }

        this.searchParam = ko.observable(new FavoritesSearchParam());
        this.searchJSON = ko.observable(ko.toJS(self.searchParam));
        this.selectedData = ko.observable();

        this.dataSource = new kendo.data.DataSource(
            inwoo.kendo.listview.dataSource({
                context: self,
                url: inwoo.config.contextRoot + "/total-sch/list-user/indi"
            })
        );

        this.favoritesListviewConfig = {
            data: {},
            dataSource: self.dataSource,
            template: "favorites-listview-template",
            useKOTemplates: true,
            autoBind: false
        };

        this.goSearch = function() {
            if (typeof(self.searchParam().user_id()) != "undefined" && self.searchParam().user_id() != "") {
                self.searchJSON(ko.toJS(self.searchParam));
                self.dataSource.page(1);
            }
        };


        /**
         * openFavoritesPopup
         * 즐겨찾기 관리 팝업
         *
         * @param   : 없음.
         * @return  : 없음.
         * @author  : 이희철
         * @date    : 2015/04/01
         */
        this.openFavoritesPopup = function() {
            var sUrl = inwoo.config.contextRoot + "/total-search/bkmk-info-popup.do";
            var sName = "favoritesPopup";
            var nWidth = 640;
            var nHeight = 460;

            sUrl += "?user_id=" + self.searchParam().user_id();
            sUrl += "&team_cd=" + self.searchParam().team2_cd();

            var oPopup = inwoo.view.popupCenter(sUrl, sName, nWidth, nHeight);
            oPopup.focus();
        };
    };


    var viewModel = new function() {
        var self = this;

        // FAQ, 우수상담, 공지사항, 게시판
        this.searchParam = ko.observable(new SearchParam());
        this.srchTypView = ko.observable("");

        this.faqViewModel = new FaqViewModel(self);
        this.consScrtExmpViewModel = new ConsScrtExmpViewModel(self);
        this.ancmMtrViewModel = new AncmMtrViewModel(self);
        this.nobdViewModel = new NobdViewModel(self);

        this.total = ko.computed({
            read: function() {
                var nTotal = 0;
                switch (self.searchParam().srch_typ_cd()) {
                case "faq":
                    nTotal = self.faqViewModel.total();
                    break;
                case "ancm_mtr":
                    nTotal = self.ancmMtrViewModel.total();
                    break;
                case "nobd":
                    nTotal = self.nobdViewModel.total();
                    break;
                case "cons_scrt":
                    nTotal = self.consScrtExmpViewModel.total();
                    break;
                default:
                    nTotal = self.faqViewModel.total() + self.consScrtExmpViewModel.total() + self.ancmMtrViewModel.total() + self.nobdViewModel.total();
                    break;
                }
                return nTotal;
            },
            write: function(psVl) {

            },
            owner: this
        });

        // 유용한페이지, 관련페이지, 즐겨찾기
        this.searchUsefulPageParam = ko.observable(new SearchUsefulPageParam());
        this.searchRelatedPageParam = ko.observable(new SearchRelatedPageParam());
        this.favoritesViewModel = new FavoritesViewModel();

        // FAQ 검색
        this.searchFaq = function() {
            self.faqViewModel.goSearch();
            $("#faq-list-area").show();
        };

        // 상담스크립트 검색
        this.searchConsScrt = function() {
            self.consScrtExmpViewModel.goSearch();
            $("#cons-scrt-list-area").show();
        };

        // 공지사항 검색
        this.searchAncmMtr = function() {
            self.ancmMtrViewModel.goSearch();
            $("#ancm-mtr-list-area").show();
        };

        // 게시판 검색
        this.searchNobd = function() {
            self.nobdViewModel.goSearch();
            $("#nobd-list-area").show();
        };

        // 왼쪽메뉴 선택 이벤트
        this.selectLeftMenu = function(psVl) {
            self.searchParam().srch_typ_cd(psVl);
            self.srchTypView(psVl);

            // 통합 검색
            self.goSearch();
        };

        // 즐겨찾기 검색
        this.searchFavorites = function() {
            self.favoritesViewModel.goSearch();
        };

        // 통합 검색
        this.goSearch = function() {
            var sSrchTypCd = self.searchParam().srch_typ_cd();
            var sSrchWord = self.searchParam().srch_word();
            var sAlertMsg = $("#srch_word").attr("title");

            if (sSrchWord == sAlertMsg) {
                self.searchParam().srch_word("");
            }

            // 검색목록 숨김
            $("#faq-list-area").hide();
            $("#cons-scrt-list-area").hide();
            $("#ancm-mtr-list-area").hide();
            $("#nobd-list-area").hide();

            self.srchTypView(sSrchTypCd);

            // 검색대상에 따라 분기
            switch (sSrchTypCd) {
                case "faq" :
                    self.searchFaq();
                    break;

                case "ancm_mtr" :
                    self.searchAncmMtr();
                    break;

                case "nobd" :
                    self.searchNobd();
                    break;

                case "cons_scrt" :
                    self.searchConsScrt();
                    break;

                default :
                    self.searchFaq();
                    self.searchAncmMtr();
                    self.searchNobd();
                    self.searchConsScrt();
            }
        };

        this.openViewPopup = function(srch_typ, view_id, search_val) {

            var url = "";
            var name = "";
            var width = 0;
            var height = 0;

            switch(srch_typ) {
            case "faq":
                url = inwoo.config.contextRoot + "/total-search/view/faq-view-popup.do?faq_id=" + view_id + "&search_val=" + (search_val == null ? "" : escape(encodeURIComponent(search_val)));
                name = "faqViewPopup";
                width = 650;
                height = 622;
                break;

            case "ancm_mtr":
                url = inwoo.config.contextRoot + "/total-search/view/ancm-mtr-view-popup.do?ancm_mtr_id=" + view_id + "&search_val=" + (search_val == null ? "" : escape(encodeURIComponent(search_val)));
                name = "ancmMtrViewPopup";
                width = 650;
                height = 581;
                break;

            case "nobd":
                url = inwoo.config.contextRoot + "/total-search/view/nobd-view-popup.do?nobd_id=" + view_id + "&search_val=" + (search_val == null ? "" : escape(encodeURIComponent(search_val)));
                name = "nobdViewPopup";
                width = 650;
                height = 582;
                break;

            case "cons_scrt":
                url = inwoo.config.contextRoot + "/total-search/view/cons-scrt-view-popup.do?cons_scrt_id=" + view_id + "&word=" + (search_val == null ? "" : escape(encodeURIComponent(search_val)));
                name = "consScrtViewPopup";
                width = 650;
                height = 622;
                break;

            }
            var obj = inwoo.view.popupCenter(url, name, width, height);
            obj.focus();
        };

        self.searchFavorites();
        self.goSearch();
    };

    /**
     * $(document).ready
     * jquery ready 이벤트를 받아서 실행(onload보다는 조금 더 빨리 실행됨)
     *
     * @param   : 없음.
     * @return  : 없음.
     * @author  : 이희철
     * @date    : 2015/04/01
     */
    $(document).ready(function () {
        ko.applyBindings(viewModel);
    });


    /**
     * procSrchCntn
     * 통합검색 제목 내용 태그 제거 / 사이즈 제한 / 검색어 강조 처리
     */
     procSrchCntn = function(psCntn, pnMaxByte) {
        // 태그 제거
        psCntn = stripTag(psCntn);

        // 사이즈 제한
        var nMaxByte = 500;
        if (getUTF8Length(psCntn) > nMaxByte) {
            var nByte = 0;
            for (var i = 0; i < psCntn.length; i++) {
                if (escape(psCntn.charAt(i)).length > 4) {
                    nByte += 3;
                } else {
                    nByte++;
                }

                if (nByte >= nMaxByte) {
                    psCntn = psCntn.substr(0, i) + "...";
                    break;
                }
            }
        }

        // 검색어 강조
        var sSrchWrd = viewModel.searchParam().srch_word();
        var sSrchTag = "<span class='bg_y'>" + sSrchWrd + "</span>";
        var rSrchWrd = new RegExp(sSrchWrd, "gi");
        if (sSrchWrd != "") {
            psCntn = psCntn.replace(rSrchWrd, sSrchTag);
        }

        return psCntn;
    };


    /**
     * openLeftPopup
     * 유용한페이지, 관련페이지, 즐겨찾기 팝업
     *
     * @param   : URL
     * @return  : 없음.
     * @author  : 이희철
     * @date    : 2015/04/01
     */
    openLeftPopup = function(poUrl, poWidth, poHeight) {

        var sUrl = poUrl;
        var sWidth = poWidth;
        var sHeight = poHeight;

        if (sUrl == null || sUrl == "") {
            return;
        }
        if (sHeight == null || sHeight == "") {
            sHeight = "500";
        }
        if (sWidth == null || sWidth == "") {
            sWidth = "1000";
        }

        if (sUrl.indexOf("http://") < 0) {
            sUrl = "http://" + sUrl;
        }

        if (sUrl.length <= 7 || sUrl.indexOf(" ") >= 0 || sUrl.indexOf(",") >= 0) {
            alert("URL이 올바르지 않습니다.");
            return;
        }

        var shape = "width=" + sWidth + ",height=" + sHeight + ",resizable=yes";
        window.open(sUrl, "_blank", shape);
    };
</script>

</body>

</html>
