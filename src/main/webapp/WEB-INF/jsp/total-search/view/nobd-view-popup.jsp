<%@page pageEncoding="UTF-8"%>
<!--
 Copyright (c) 2015 Company Inwoo Tech Inc.

 @author 차건호
 @version 1.0
 @date 2015/04/20
 @프로그램ID <<PS_UI_9906>>
 @설명 << 게시판 상세정보 >>
-->
<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>게시판 상세정보</title>

<!-- common include -->
<%@ include file="/include/common-js.jsp" %>

<style type="text/css">
body{overflow:hidden}
</style>


</head>

<body>

<!-- popup -->
<div id="nobdView" data-bind="with: nobdViewModel" class="popup_outer">
    <div class="p_top">
        <h2>게시판 상세정보</h2>
<div class="pop_close" data-bind="click: function() {window.close();}"></div>
    </div>

    <div data-bind="with: selectedData" class="popup_inner bd0" style="max-height:510px">
    <div class="popup_wb">
        <ul class="detail_tit">
        <li class="tit ellipsis" style="width: 290px"><span class="kind"><span data-bind="text: nobd_bswr_div_nm"></span>&nbsp;&gt;</span><span data-bind="html: nobd_titl, attr: {title: nobd_titl}"></span></li>
        <li class="modi">수정자&nbsp;:&nbsp;<span data-bind="text: amnd_nm"></span>&nbsp;&nbsp;&nbsp;&nbsp;조회수&nbsp;:&nbsp;<span data-bind="text: inq_cnt"></span>&nbsp;&nbsp;&nbsp;&nbsp;<span data-bind="text: moment(amnt_dttm(), 'YYYYMMDDHHmmss').isValid() ? moment(amnt_dttm(), 'YYYYMMDDHHmmss').format('YYYY-MM-DD HH:mm') : ''"></span></li>
        </ul>

        <div class="txt_view">
            <pre data-bind="html: nobd_html_cntn"></pre>
        </div>

        <ul class="attch_outter">
        <li data-bind="kendoListView: $parent.fileListViewConfig" class="attch">
        </li>
        <li class="regi">등록자&nbsp;:&nbsp;<span data-bind="html: rgsr_nm"></span>&nbsp;&nbsp;&nbsp;&nbsp;<span data-bind="text: moment(rgsn_dttm(), 'YYYYMMDDHHmmss').isValid() ? moment(rgsn_dttm(), 'YYYYMMDDHHmmss').format('YYYY-MM-DD HH:mm') : ''"></span></li>
        </ul>

        <!-- Comment -->
        <div id="div-reply" class="cmt_comm" data-bind="with: $root.nobdAnsrViewModel, visible: nobd_typ_ansr_fg() == 'Y'">
            <ul class="comment_outter">
            <li class="ft_l"><textarea id="nobd_ansr_cntn" onFocus="if (this.value == this.title) this.value = '';" onblur="if (this.value == '') this.value = this.title;" title="댓글을 달아주세요.">댓글을 달아주세요.</textarea></li>
            <li class="ft_l"><button type="button" data-bind="click: saveData" class="btn_comment_regi" title="등록">등록<div class="rb"></div></button></li>
            </ul>

            <div data-bind="kendoListView: replyListViewConfig"></div>
            <div id="list-pager" class="k-search-pager-wrap pagenavi"></div>
        </div>
        <!-- //Comment -->

    </div>

    </div>
</div>
<!-- //popup -->

<script id="file-listview-template" type="text/html">
<span data-bind="text: file_nm, click: function() {location.href = inwoo.config.contextRoot + '/file/download?file_id=' + file_id}" class="filename"></span>
</script>

<script id="reply-listview-template" type="text/html">
    <div class="comment_view">
        <img data-bind="attr: {src: inwoo.config.contextRoot + '/file/download?file_id=' + file_id }" onerror="this.src=inwoo.config.contextRoot + '/theme/images/xxx.jpg'" />
        <ul class="com_box">
            <li><strong><span data-bind="text: rgsr_nm"></span></strong><span>|&nbsp;<span data-bind="text: moment(rgsn_dttm, 'YYYYMMDDHHmmss').isValid() ? moment(rgsn_dttm, 'YYYYMMDDHHmmss').format('YYYY-MM-DD HH:mm') : ''"></span></span></li>
            <li class="com_txt"><pre data-bind="html: nobd_ansr_cntn"></pre></li>
        </ul>
        <button type="button" data-bind="click: $parent.deleteData, visible: '${USER_SESSION.userid}' == rgsr_id" class="btn_comment_del" title="삭제"><div class="rb"></div></button>
    </div>
</script>

<!-- Java script -->
<script type="text/javascript">

    /**
     * 전역변수
     */
    var Nobd = function() {
        this.page_indx = ko.observable();
        this.nobd_id = ko.observable();
        this.nobd_typ_id = ko.observable("${nobdTyp.nobd_typ_id}");
        this.nobd_typ_file_fg = ko.observable("${nobdTyp.file_atch_yn}");
        this.nobd_typ_ansr_fg = ko.observable("${nobdTyp.ansr_yn}");
        this.nobd_typ_cd = ko.observable();
        this.nobd_typ_nm = ko.observable();
        this.nobd_titl = ko.observable();
        this.nobd_cntn = ko.observable();
        this.nobd_html_cntn = ko.observable();
        this.inq_cnt = ko.observable();

        this.rgsn_dttm = ko.observable();
        this.rgsr_nm = ko.observable();
        this.amnt_dttm = ko.observable();
        this.amnd_nm = ko.observable();
        this.nobd_bswr_div_nm = ko.observable();
    };

    var SearchParam = function() {
        this.nobd_typ_id = ko.observable("${nobdTyp.nobd_typ_id}");
        this.nobd_id = ko.observable("${param.nobd_id}");
        this.rltn_tbl_nm = ko.observable("TB_NOBD_DTL");
    };

    var NobdViewModel = function(nobdAnsrViewModel) {
        var self = this;

        nobdAnsrViewModel.masterVM(self);

        this.selectedData = ko.observable(new Nobd());
        this.searchParam = ko.observable(new SearchParam());

        this.items = ko.observableArray();

        this.goSearch = function() {
            var view = $("#nobdView");

            kendo.ui.progress(view, true);

            $.inwooAjaxJson(inwoo.config.contextRoot + "/total-sch/nobd/view", ko.toJS(self.searchParam()), function(obj) {
                if (obj.result == "OK") {
                    ko.mapping.fromJS(obj.data.viewData, {}, self.selectedData());

                    var fileList = obj.data.fileList;

                    for (var i = 0; i < fileList.length; i++) {
                        self.items.push(fileList[i]);
                    }

                    var searchValue = decodeURIComponent("${param.search_val}");

                    var searchWord = "${param.searchType}";

                    var replaceValue = "<span class='bg_y'>" + searchValue + "</span>";

                    if (searchValue != "") {
                        searchValue = new RegExp(searchValue, "gi");
                        if ("nobd_titl" == searchWord) {
                            self.selectedData().nobd_titl(self.selectedData().nobd_titl().replace(searchValue, replaceValue));
                        } else if ("nobd_cntn" == searchWord) {
                            self.selectedData().nobd_html_cntn(self.selectedData().nobd_html_cntn().replace(searchValue, replaceValue));
                        } else if ("rgsr_nm" == searchWord) {
                            self.selectedData().rgsr_nm(self.selectedData().rgsr_nm().replace(searchValue, replaceValue));
                        } else {
                            self.selectedData().nobd_titl(self.selectedData().nobd_titl().replace(searchValue, replaceValue));
                            self.selectedData().nobd_html_cntn(self.selectedData().nobd_html_cntn().replace(searchValue, replaceValue));
                        }
                    }

                    if (self.selectedData().nobd_typ_ansr_fg() == "Y") {
                        nobdAnsrViewModel.goSearch();
                    }
                } else {
                    alert(obj.errMsg);
                }
                kendo.ui.progress(view, false);
            });
        };

        // 첨부파일
        this.fileListViewConfig = {
            data: self.items,
            template: "file-listview-template",
            useKOTemplates: true
        };

        self.goSearch();
    };

    // 게시판 댓글
    var NobdAnsr = function() {
        this.page_indx = ko.observable();
        this.nobd_ansr_id = ko.observable();
        this.nobd_id = ko.observable("${param.nobd_id}");
        this.nobd_ansr_cntn = ko.observable();
        this.cntr_cd = ko.observable();
        this.rgsr_id = ko.observable();
        this.rgsr_nm = ko.observable();
        this.rgsn_dttm = ko.observable();
        this.amnd_id = ko.observable();
        this.amnd_nm = ko.observable();
        this.amnt_dttm = ko.observable();
        this.file_id = ko.observable();
    };

    var NobdAnsrSearchParam = function() {
        this.nobd_id = ko.observable("${param.nobd_id}");
    };

    var NobdAnsrViewModel = function() {
        var self = this;
        this.masterVM = ko.observable();

        this.selectedIndex = ko.observable(null);
        this.selectedData = ko.observable(new NobdAnsr());

        this.searchParam = ko.observable(new NobdAnsrSearchParam());
        this.searchJSON = ko.observable(ko.toJS(self.searchParam));

        this.dataSource = new kendo.data.DataSource(
            inwoo.kendo.listview.dataSource({
                context: self,
                url: inwoo.config.contextRoot + '/knowledge/nobd-ansr/list',
                pageSize: 3,
                sort: {field: "rgsn_dttm", dir: "desc"}
            })
        );

        this.replyListViewConfig = {
            data: {},
            dataSource: self.dataSource,
            template: "reply-listview-template",
            useKOTemplates: true,
            autoBind: false
        };

        this.goSearch = function() {
            $("#list-pager").kendoPager({
                dataSource: self.dataSource
            });
        };

        this.saveData = function() {
            var reply = $("#div-reply");
            var obj = $("#nobd_ansr_cntn");

            if (obj.val() == "" || obj.val() == obj.attr("title")) {
                alert("댓글내용을 입력해 주세요.");
                return;
            }
            kendo.ui.progress(reply, true);

            self.selectedData(new NobdAnsr());
            self.selectedData().nobd_ansr_cntn($("#nobd_ansr_cntn").val());

            $.inwooAjaxJson(inwoo.config.contextRoot + "/knowledge/nobd-ansr/save", ko.toJS(self.selectedData()), function(obj) {
                if (obj.result == "OK") {
                    self.dataSource.insert(0, obj.data);
                    $("#nobd_ansr_cntn").empty().focus();
                    kendo.ui.progress(reply, false);
                } else {
                    kendo.ui.progress(reply, false);
                    alert(obj.errMsg);
                }
            });
        };

        this.deleteData = function(item) {
            if (confirm("삭제하시겠습니까?")) {
                var reply = $("#div-reply");

                $.inwooAjaxJson(inwoo.config.contextRoot + "/knowledge/nobd-ansr/delete", ko.toJS(item), function(obj) {
                    if (obj.result == "OK") {
                        self.dataSource.read();
                        kendo.ui.progress(reply, false);
                    } else {
                        kendo.ui.progress(reply, false);
                        alert(obj.errMsg);
                    }
                });
            }
        };
    };

    var viewModel = new function() {
        this.nobdAnsrViewModel = new NobdAnsrViewModel();
        this.nobdViewModel = new NobdViewModel(this.nobdAnsrViewModel);
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
