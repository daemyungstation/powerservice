<%@page pageEncoding="UTF-8"%>
<!--
 Copyright (c) 2015 Company Inwoo Tech Inc.

 @author 차건호
 @version 1.0
 @date 2015/04/13
 @프로그램ID << AP-UI-9915 >>
 @설명 << 즐겨찾기 관리 >>
-->
<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>즐겨찾기 관리</title>

<!-- common include -->
<%@ include file="/include/common-js.jsp" %>

<style type="text/css">
body{overflow:hidden}
</style>

</head>

<body>

<div class="popup_outer">
<div class="p_top">
<h2>즐겨찾기 관리</h2>
<div class="pop_close" data-bind="click: function() {window.close();}"></div>
</div>

<div class="popup_inner bg_w bd0">

    <!-- 02. List -->
    <div id="grid" style="height:395px" data-bind="kendoGrid: gridConfig"></div>
    <!-- //02. List -->

</div>
</div>

<script id="templateCommand" type="text/x-kendo-template">
    <button onclick="viewModel.deleteData();" class="btn_gray1" title="삭제"><span class="k-icon k-i-close"></span>삭제<div class="rb"></div></button>
</script>

<!-- Java script -->
<script type="text/javascript">

    /**
     * 전역변수
     */
    var BkmkInfo = function() {
        this.page_indx = ko.observable();
        this.bkmk_id = ko.observable();
        this.cntr_cd = ko.observable();
        this.user_id = ko.observable();
        this.bkmk_nm = ko.observable();
        this.bkmk_addr = ko.observable();
        this.expe_sqnc = ko.observable();
        this.rgsr_id = ko.observable();
        this.rgsn_dttm = ko.observable();
        this.amnd_id = ko.observable();
        this.amnt_dttm = ko.observable();
    };

    var SearchParam = function() {
        this.bkmk_div_cd =  ko.observable("30");
        this.team2_cd = ko.observable("${param.team_cd}");
        this.user_id = ko.observable("${param.user_id}");
    };

    var viewModel = new function() {
        var self = this;

        this.dataSource = new kendo.data.DataSource(
            inwoo.kendo.grid.dataSource({
                context: self,
                url: inwoo.config.contextRoot + "/total-sch/list-user/indi",
                insertUrl: inwoo.config.contextRoot + "/total-sch/save-tot-sch?user_id=${param.user_id}&team_cd=${param.team_cd}",
                updateUrl: inwoo.config.contextRoot + "/total-sch/save-tot-sch?user_id=${param.user_id}&team_cd=${param.team_cd}",
                batch: true,
                model: {
                    id: "bkmk_id",
                    fields: {
                        user_id: {defaultValue: "ALL", editable: false},
                        page_indx: {defaultValue: "New", editable: false},
                        bkmk_nm: {validation: {required: true, validationMessage: "표시이름을 입력하세요.", maxlength: function(input) {
                                                                                                                    if (input.val().length > 33) {
                                                                                                                        input.attr("data-maxlength-msg", "최대 33자까지 가능합니다.");
                                                                                                                        return false;
                                                                                                                    }
                                                                                                                    return true;
                                                                                                                    }
                                                 }
                                 },
                        bkmk_addr: {defaultValue: "http://", validation: {required: true, validationMessage: "URL을 입력하세요.", maxlength: function(input) {
                                                                                                                    if (input.val().length > 33) {
                                                                                                                        input.attr("data-maxlength-msg", "최대 33자까지 가능합니다.");
                                                                                                                        return false;
                                                                                                                    }
                                                                                                                    return true;
                                                                                                                    }
                                                }
                                },
                    }
                },
                sort: {field: "expe_sqnc", dir: "asc"}
            })
        );

        this.grid = ko.observable();
        this.gridConfig = inwoo.kendo.grid.gridConfig({
            context: self,
            widget: self.grid,
            sortable: false,
            pageable: false,
            editable: {destroy: false},
            toolbar: [{name: "create",	text: "신규등록"},
                      {name: "save",	text: "변경사항 저장"},
                      {name: "cancel",	text: "취소"}
                     ],
            columns: [{field:"page_indx",	title: "NO",       	width: 42,    template: "#=page_indx =='New' ?'<font class =fc_red>'+page_indx+'</font>' :page_indx #"},
                      {field:"bkmk_nm", 	title: "표시이름", 	width: 200},
                      {field:"bkmk_addr",	title: "URL"},
                      {width: 90,			template: kendo.template("#= user_id != 'ALL' ? $(\"[id='templateCommand']\").html() : ''  #")}
                     ]
        });

        var parent = new inwoo.kendo.BaseViewModel({
            DataVM: BkmkInfo,
            SearchVM: SearchParam,
            dataSource: self.dataSource,
            grid: self.grid,
            deleteUrl: inwoo.config.contextRoot + "/total-sch/delete",
            afterDelete: function() {
                self.goSearch();
            }
        });

        ko.utils.extend(self, parent);

        this.onSaveChanges = function(e) {
            if(self.dataSource.data().length > 10){
                e.preventDefault();
                alert("최대 10개까지 등록가능합니다.");
            } else {
                if (confirm("변경사항을 저장하시겠습니까?")) {
                    self.dataSource.one("change", function (e) {
                        parent.goSearch();
                    });
                } else {
                    e.preventDefault();
                }
            }
        };

        this.deleteData = function() {
            // 그리드 행 선택
            selectGridRow(self.grid());
            parent.deleteData();
        };

    };

    /**
     * $(document).ready
     * jquery ready 이벤트를 받아서 실행(onload보다는 조금 더 빨리 실행됨)
     *
     * @param	: 없음.
     * @return  : 없음.
     * @author  : 한숙향
     * @date    : 2013/04/01
     */
    $(document).ready(function () {
        ko.applyBindings(viewModel);

        var grid = $("#grid").data("kendoGrid");
        var dataSource = viewModel.dataSource;

        grid.table.kendoDraggable({
            filter: "tbody > tr:not(.k-grid-edit-row)",
            group: "gridGroup",
            axis: "y",
            hint: function(e) {
                return $('<div class="k-grid k-widget"><table><tbody><tr>' + e.html() + '</tr></tbody></table></div>');
            }
        });

        grid.table.kendoDropTarget({
            group: "gridGroup",
            drop: function(e) {
                e.draggable.hint.hide();

                var target = dataSource.getByUid($(e.draggable.currentTarget).data("uid"));
                var dest = $(document.elementFromPoint(e.originalEvent.clientX, e.originalEvent.clientY));

                if (dest.is("th")) {
                    return;
                }
                dest = dataSource.getByUid(dest.parent().data("uid"));

                //not on same item
                if (target.get("id") !== dest.get("id")) {
                    var destPosition = dataSource.indexOf(dest);

                    dataSource.remove(target);
                    dataSource.insert(destPosition, target);

                    var data = dataSource.data();

                    for (var i = 0; i < data.length; i++) {
                        data[i].expe_sqnc = (i + 1);
                    }

                    $.inwooAjaxJson(inwoo.config.contextRoot + "/total-sch/save-tot-sch?user_id=${param.user_id}&team_cd=${param.team_cd}", ko.toJS(data), function(obj) {
                        if (obj.result == "OK") {
                            //dataSource.read();
                        } else {
                            alert(obj.errMsg);
                        }
                    });
                }
            }
        });
    });

</script>

</body>
</html>
