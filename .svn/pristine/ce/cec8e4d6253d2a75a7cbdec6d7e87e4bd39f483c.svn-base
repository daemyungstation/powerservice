var inwoo = inwoo || {};
inwoo.kendo = inwoo.kendo || {};

/**
 * GRID 없이 상세화면에서 사용
 *
 * @param config
 * @returns {inwoo.kendo.SingleRowViewModel}
 */
inwoo.kendo.SingleRowViewModel = function(config) {
    var self = this;

    this.loadedEditor = false;

    this.mode = ko.observable("R");

    this.selectedData = ko.observable(new config.DataVM());

    this.searchParam = ko.observable(new config.SearchVM());
    this.searchJSON = ko.observable(ko.toJS(self.searchParam));

    this.createData = function() {
        self.selectedData(new config.DataVM());
        if (config.editor) {
            if (self.loadedEditor) {
                DEXT5.setBodyValue("");

                // 에디터 사이즈 조정
                resizeDextEditor();
            } else {
                DEXT5.config.EditorHolder = "dext_editor";
                DEXT5.config.userFontFamily = "굴림체";
                // DEXT5.config.IgnoreSameEditorName = "1";
                new Dext5editor("Dexteditor");

                self.loadedEditor = true;
            }
        }
        self.mode("C");
    };

    this.updateData = function() {
        if (config.editor) {
            if (self.loadedEditor) {
                DEXT5.setBodyValue(self.selectedData()[$("#dext_editor").attr("value")]());

                // 에디터 사이즈 조정
                resizeDextEditor();
            } else {
                DEXT5.config.EditorHolder = "dext_editor";
                DEXT5.config.userFontFamily = "굴림체";
                // DEXT5.config.IgnoreSameEditorName = "1";
                new Dext5editor("Dexteditor");

                self.loadedEditor = true;
            }
        }
        self.mode("U");
    };

    this.saveData = function() {
        // HTML 태그 사용체크
        var sEditProp = "";
        if (config.editor) {
            sEditProp = $("#dext_editor").attr("value");
        }
        if (useHtmlTag(ko.toJS(self.selectedData), sEditProp)) {
            alert("HTML 태그는 입력할 수 없습니다.");
            return;
        }

        var saveForm = $("#" + config.saveFormId);
        var validator = saveForm.data("kendoValidator");
        if (validator == null) {
            validator = saveForm.kendoValidator().data("kendoValidator");
        }

        if (config.editor) {
            var sBodyValue = "";
            if (DEXT5.isEmpty() != true) {
                sBodyValue = DEXT5.getBodyValue().replaceAll("\r\n", "");

                // 커스텀태그 처리
                $( "<div></div>", {
                    "id": "packHTML",
                    // "class" : "hide",
                    "html" : sBodyValue
                }).appendTo( "body" );

                $("#packHTML").find("span[name=customtag]").each(function(){
                    sBodyValue = sBodyValue.replaceAll($(this).clone().wrapAll("<div/>").parent().html(), "<span name=\"customtag\">"+ transTag($(this).html()) +"</span>");
                });
                $("#packHTML").remove();
            }

            self.selectedData()[sEditProp](sBodyValue);
        }

        if (validator.validate() && typeof(config.saveUrl) != "undefined") {
            kendo.ui.progress(saveForm, true);

            // 중복처리방지 TOKEN 파라미터 추가
            var oParam = addSubmitTokenParam(saveForm, ko.toJS(self.selectedData()));
            $.inwooAjaxJson(config.saveUrl, oParam, function(obj) {
                kendo.ui.progress(saveForm, false);

                // 중복처리방지 TOKEN 파라미터 저장
                setSubmitTokenParam(saveForm, obj);

                if (obj.result == "OK") {
                    var koObj = new config.DataVM();
                    ko.mapping.fromJS(obj.data, {}, koObj);
                    self.selectedData(koObj);

                    if (config.afterSave != null) {
                        config.afterSave();
                    }

                    self.readData();
                } else {
                    alert(obj.errMsg);
                }
            });
        } else {
            kendo.ui.progress(saveForm, false);
        }
    };

    this.readData = function() {
        self.goSearch();
        self.mode("R");
    };

    this.goSearch = function() {
        var readForm = $("#" + config.readFormId);

        kendo.ui.progress(readForm, true);
        self.searchJSON(ko.toJS(self.searchParam));

        $.inwooAjaxJson(config.readUrl, self.searchJSON(), function(obj) {
            if (obj.result == "OK") {
                var koObj = new config.DataVM();
                ko.mapping.fromJS(obj.data, {}, koObj);
                self.selectedData(koObj);
            } else {
                alert(obj.errMsg);
            }
            kendo.ui.progress(readForm, false);
        });
    };

    this.deleteData = function() {
        if (confirm(config.deleteMsg == null ? "삭제하시겠습니까?" : config.deleteMsg)) {
            var readForm = $("#" + config.readFormId);
            kendo.ui.progress(readForm, true);

            // 중복처리방지 TOKEN 파라미터 추가
            var oParam = addSubmitTokenParam(readForm, ko.toJS(self.selectedData()));
            $.inwooAjaxJson(config.deleteUrl, oParam, function(obj) {
                kendo.ui.progress(readForm, false);

                // 중복처리방지 TOKEN 파라미터 저장
                setSubmitTokenParam(readForm, obj);

                if (obj.result == "OK") {
                    if (config.afterDelete != null) {
                        config.afterDelete();
                    }
                } else {
                    alert(obj.errMsg);
                }
            });
        }
    };

    this.uploadData = function() {
        // HTML 태그 사용체크
        var sEditProp = "";
        if (config.editor) {
            sEditProp = $("#dext_editor").attr("value");
        }
        if (useHtmlTag(ko.toJS(self.selectedData), sEditProp)) {
            alert("HTML 태그는 입력할 수 없습니다.");
            return;
        }

        var saveForm = $("#" + config.saveFormId);
        var validator = saveForm.data("kendoValidator");

        if (validator == null) {
            validator = saveForm.kendoValidator().data("kendoValidator");
        }

        if (saveForm.find("div.k-loading-mask").length == 0 && validator.validate()) {
            var uploadButton = $(".k-upload-selected");

            if (uploadButton.length > 0) {
                kendo.ui.progress(saveForm, true);

                uploadButton.click();
            } else {
                self.saveData();
            }
        }
    };
    //this.goSearch();
};

/**
 * 일반적인 grid (CRUD)에서 사용
 *
 * @param config
 * @returns {inwoo.kendo.BaseViewModel}
 */
inwoo.kendo.BaseViewModel = function(config) {
    var self = this;

    this.loadedEditor = false;

    this.mode = ko.observable("R");

    this.selectedIndex = ko.observable(null);
    this.selectedData = ko.observable(new config.DataVM());

    this.searchParam = ko.observable(new config.SearchVM());
    this.searchJSON = ko.observable(ko.toJS(self.searchParam));

    this.createData = function() {
        if (config.grid() != undefined){
            config.grid().clearSelection();
        }
        self.selectedData(new config.DataVM());

        if (config.editor) {
            if (self.loadedEditor) {
                DEXT5.setBodyValue("");

                // 에디터 사이즈 조정
                resizeDextEditor();
            } else {
                DEXT5.config.EditorHolder = "dext_editor";
                DEXT5.config.userFontFamily = "굴림체";
                // DEXT5.config.IgnoreSameEditorName = "1";
                new Dext5editor("Dexteditor");

                self.loadedEditor = true;
            }
        }

        self.mode("C");
    };

    this.updateData = function() {
        if (config.editor) {
            if (self.loadedEditor) {
                DEXT5.setBodyValue(self.selectedData()[$("#dext_editor").attr("value")]());

                // 에디터 사이즈 조정
                resizeDextEditor();
            } else {
                DEXT5.config.EditorHolder = "dext_editor";
                DEXT5.config.userFontFamily = "굴림체";
                // DEXT5.config.IgnoreSameEditorName = "1";
                new Dext5editor("Dexteditor");

                self.loadedEditor = true;
            }
        }
        self.mode("U");
    };

    this.saveData = function() {
        // HTML 태그 사용체크
        var sEditProp = "";
        if (config.editor) {
            sEditProp = $("#dext_editor").attr("value");
        }
        if (useHtmlTag(ko.toJS(self.selectedData), sEditProp)) {
            alert("HTML 태그는 입력할 수 없습니다.");
            return;
        }

        var saveForm = $("#" + config.saveFormId);
        var validator = saveForm.data("kendoValidator");
        if (validator == null) {
            validator = saveForm.kendoValidator().data("kendoValidator");
        }

        if (config.editor) {
            var sBodyValue = "";
            if (DEXT5.isEmpty() != true) {
                sBodyValue = DEXT5.getBodyValue().replaceAll("\r\n", "");

                // 커스텀태그 처리
                $( "<div></div>", {
                    "id": "packHTML",
                    // "class" : "hide",
                    "html" : sBodyValue
                }).appendTo( "body" );

                $("#packHTML").find("span[name=customtag]").each(function(){
                    sBodyValue = sBodyValue.replaceAll($(this).clone().wrapAll("<div/>").parent().html(), "<span name=\"customtag\">"+ transTag($(this).html()) +"</span>");
                });
                $("#packHTML").remove();
            }

            self.selectedData()[sEditProp](sBodyValue);
        }

        if (!saveForm.is("form") || validator.validate()) {
            kendo.ui.progress(saveForm, true);

            // 중복처리방지 TOKEN 파라미터 추가
            var oParam = addSubmitTokenParam(saveForm, ko.toJS(self.selectedData()));
            $.inwooAjaxJson(config.saveUrl, oParam, function(obj) {
                kendo.ui.progress(saveForm, false);

                // 중복처리방지 TOKEN 파라미터 저장
                setSubmitTokenParam(saveForm, obj);

                if (obj.result == "OK") {
                    if (self.selectedData().page_indx() != null) {
                        /* 속도 문제 개선
                        $.each(obj.data, function(key, value) {
                            if (key != "page_indx") {
                                // config.dataSource.data()[self.selectedIndex()].set(key, value);
                                config.dataSource.data()[self.selectedIndex()].set(key, (value != null) ? value : "");
                            }
                        });
                        */
                        var dataIteam = config.dataSource.data()[self.selectedIndex()];
                        for (var prop in obj.data) {
                            if (prop != "page_indx") {
                                dataIteam[prop] = (obj.data[prop] != null) ? obj.data[prop] : "";
                            }
                        }

                        if (config.afterSave != null) {
                            config.afterSave(obj);
                        }

                        if (config.grid != null) {
                            config.grid().refresh();
                            config.grid().select(config.grid().tbody.find("tr:eq(" + self.selectedIndex() + ")"));
                        }
                    } else if (obj.data != null) {
                        obj.data.page_indx = "New";

                        /**
                         * 수정일시 : 2015.09.23
                         * 수 정 자 : 정용재 선임
                         * 요 청 자 : 우달식 이사
                         * 작업내용 : 신규글 작성시 하단에 표시되는 것을 상단에 표시되게 재변경
                         */
                        /*
                        var insertIndex = config.dataSource.data().length;
                        if (insertIndex == null) {
                            insertIndex = 0;
                        }
                        */
                        var insertIndex = 0;
                        self.selectedIndex(insertIndex);
                        config.dataSource.insert(insertIndex, obj.data);

                        if (config.afterSave != null) {
                            config.afterSave(obj);
                        }
                    }
                } else {
                    alert(obj.errMsg);
                }
            });
        }
    };

    this.deleteData = function() {
        if (confirm(config.deleteMsg == null ? "삭제하시겠습니까?" : config.deleteMsg)) {
            var readForm =$("body");
            kendo.ui.progress(readForm, true);

            // 중복처리방지 TOKEN 파라미터 추가
            var oParam = addSubmitTokenParam(readForm, ko.toJS(self.selectedData()));
            $.inwooAjaxJson(config.deleteUrl, oParam, function(obj) {
                kendo.ui.progress(readForm, false);

                // 중복처리방지 TOKEN 파라미터 저장
                setSubmitTokenParam(readForm, obj);

                if (obj.result == "OK") {
                    var dataItem = config.dataSource.data()[self.selectedIndex()];
                    // 수정 가능한 그리드인 경우 변경 메타 정보까지 삭제
                    if (config.grid != null && config.grid().getOptions().editable) {
                        config.dataSource.pushDestroy(dataItem);
                    } else {
                        config.dataSource.remove(dataItem);
                    }
                    self.selectedData(new config.DataVM());

                    if (config.afterDelete != null) {
                        config.afterDelete();
                    }
                } else {
                    alert(obj.errMsg);
                }
            });
        }
    };

    this.readData = function() {
        if (config.dataSource.data().length == 0) {
            self.selectedIndex(null);
            self.selectedData(new config.DataVM());
        } else {
            if (self.selectedIndex() == null) {
                self.selectedIndex(0);
            }

            /**
             * 수정일시 : 2015.11.03
             * 수 정 자 : 정 훈 연구원
             * 요 청 자 : 우달식 이사
             * 작업내용 : 신규글 작성시 취소버튼시 그리드 상단에 포커싱
             */

            if(self.mode()=='C'){
                self.selectedIndex(0);
            }

            // onChange event fire
            config.grid().select(config.grid().tbody.find("tr:eq(" + self.selectedIndex() + ")"));
        }
        self.mode("R");
    };

    this.goSearch = function() {
        self.selectedIndex(null);
        self.selectedData(new config.DataVM());
        self.searchJSON(ko.toJS(self.searchParam));
        config.dataSource.page(1);
    };

    this.resetSearch = function () {
        self.searchParam(new config.SearchVM());
    };

    this.uploadData = function() {
        // HTML 태그 사용체크
        var sEditProp = "";
        if (config.editor) {
            sEditProp = $("#dext_editor").attr("value");
        }
        if (useHtmlTag(ko.toJS(self.selectedData), sEditProp)) {
            alert("HTML 태그는 입력할 수 없습니다.");
            return;
        }

        var saveForm = $("#" + config.saveFormId);
        var validator = saveForm.data("kendoValidator");

        if (validator == null) {
            validator = saveForm.kendoValidator().data("kendoValidator");
        }

        if (saveForm.find("div.k-loading-mask").length == 0 && validator.validate()) {
            var uploadButton = $(".k-upload-selected");

            if (uploadButton.length > 0) {
                kendo.ui.progress(saveForm, true);

                uploadButton.click();
            } else {
                self.saveData();
            }
        }
    };


    // start of grid event
    this.onChange = function(e) {
        var data = config.grid().dataItem(config.grid().select()[0]);
        var koObj = new config.DataVM();

        ko.mapping.fromJS(data, {}, koObj);

        self.selectedData(koObj);
        self.selectedIndex(config.dataSource.indexOf(data));
        self.mode("R");
    };

    this.onDataBound = function(e) {
        if (config.grid().options.selectable) {
            setTimeout( function() {
                self.readData();
            }, 10);
        }
    };

    this.onSaveChanges = function(e) {
    };
    // end of grid event

    if (config.editor) {
        //ko.bindingHandlers.kendoEditor.options.tools = inwoo.kendo.editor.options.tools;
        //ko.bindingHandlers.kendoEditor.options.imageBrowser = inwoo.kendo.editor.options.imageBrowser;
    }
};


/**
 * 공지사항과 같이 상세보기가 팝업으로 뜨는 뷰에서 사용
 *
 * @param config
 * @returns {inwoo.kendo.DetailPopupViewModel}
 */
inwoo.kendo.DetailPopupViewModel = function(config) {
    var self = this;

    this.isOpen = ko.observable(false);

    var parent = new inwoo.kendo.BaseViewModel({
        DataVM: config.DataVM,
        SearchVM: config.SearchVM,
        dataSource: config.dataSource,
        grid: config.grid,
        saveFormId: config.saveFormId,
        saveUrl: config.saveUrl,
        deleteUrl: config.deleteUrl,
        editor: config.editor,
        afterSave: function() {
            if (config.afterSave != null) {
                config.afterSave();
            }
            self.closeWindow();
        },
        afterDelete: function() {
            if (config.afterDelete != null) {
                config.afterDelete();
            }
            self.closeWindow();
        }
    });

    ko.utils.extend(self, parent);

    this.createData = function() {
        if (config.changeLoadedEditor) {
            parent.loadedEditor = false;
        }
        parent.createData();
        self.openWindow();
    };

    this.updateData = function(e) {
        self.selectGridRow(e);

        parent.updateData();
        self.openWindow();
    };

    this.readData = function(e) {
        self.selectGridRow(e);

        parent.readData();
        self.openWindow();
    };

    this.openWindow = function() {
        self.isOpen(true);
    };

    this.closeWindow = function() {
        self.isOpen(false);
    };

    this.selectData = function(data) {
        ko.mapping.fromJS(data, {}, self.selectedData());
        self.openWindow();
    };

    this.selectGridRow = function(e) {
        if (typeof(e) == "undefined") {
            e = event;
        }
        if (e == null) {
            return false;
        }

        var eTarget = e.currentTarget != null ? e.currentTarget : e.srcElement;
        if (eTarget != null) {
            if (e.preventDefault != null) {
                e.preventDefault();
            } else {
                e.returnValue = false;
            }

            if (config.grid() != null) {
                config.grid().select($(eTarget).closest("tr")[0]);
            }
        }
    };
};

/**
 *  tree가 사용되는 뷰에서 사용
 *
 * @param config
 * @returns {inwoo.kendo.TreeViewModel}
 */
inwoo.kendo.TreeViewModel = function(config) {
    var self = this;

    this.selectedIndex = ko.observable(null);
    this.selectedData = ko.observable(new config.DataVM());

    this.searchParam = ko.observable(new config.SearchVM());
    this.searchJSON = ko.observable(ko.toJS(self.searchParam));

    this.goSearch = function() {
        self.selectedIndex(null);
        self.selectedData(new config.DataVM());
        self.searchJSON(ko.toJS(self.searchParam));

        config.dataSource.read();
    };

    this.reloadTree = function(nodeId) {
        if (nodeId == null) {
            config.dataSource.read();
        } else {
            var dataItem = config.tree().dataSource.get(nodeId);
            var node = config.tree().findByUid(dataItem.uid);

            dataItem.loaded(false);

            config.tree().collapse(node);
            config.tree().select(node);
            config.tree().expand(node);
        }
    };

    this.expandAllNodes = function() {
        config.tree().expand(".k-item");
    };

    this.collapseAllNodes = function() {
        config.tree().collapse(".k-item");
    };

    this.onDataBound = function(e) {

    };

    this.onChange = function(e) {
        var data = config.tree().dataItem(config.tree().select()[0]);
        var koObj = new config.DataVM();
        ko.mapping.fromJS(data, {}, koObj);
        self.selectedData(koObj);
        self.selectedIndex(config.dataSource.indexOf(data));
    };

};


/**
 * 선택팝업(grid) 뷰에서 사용
 *
 * @param config
 * @returns {inwoo.kendo.SelectPopupViewModel}
 */
inwoo.kendo.SelectPopupViewModel = function(config) {
    var self = this;

    this.isOpen = ko.observable(false);

    var parent = new inwoo.kendo.BaseViewModel({
        DataVM: config.DataVM,
        SearchVM: config.SearchVM,
        dataSource: config.dataSource,
        grid: config.grid,
        saveFormId: config.saveFormId,
        saveUrl: config.saveUrl,
        deleteUrl: config.deleteUrl,
        editor: config.editor,
        afterSave: function() {
            if (config.afterSave != null) {
                config.afterSave();
            }
            self.closeWindow();
        },
        afterDelete: function() {
            if (config.afterDelete != null) {
                config.afterDelete();
            }
            self.closeWindow();
        }
    });

    ko.utils.extend(self, parent);

    this.openWindow = function() {
        self.isOpen(true);
    };

    this.closeWindow = function() {
        self.isOpen(false);
    };

    this.goSearch = function() {
        self.openWindow();
        self.searchJSON(ko.toJS(self.searchParam));
        config.dataSource.page(1);
    };

    this.onChange = function() {
    };

    this.onDataBound = function() {
    };

    this.selectData = function(data) {
        var koObj = new config.DataVM();
        ko.mapping.fromJS(data, {}, koObj);
        self.selectedData(koObj);
        self.closeWindow();
    };
};

// 에디터 로드 완료 이벤트
function dext_editor_loaded_event() {
    var $DextEditor = $("#dext_editor");
    if ($DextEditor.length == 0) {
        return;
    }

    // 에디터 사이즈 조정
    resizeDextEditor();

    // 부모창 fileIds에 저장된 이미지 파일의 아이디를 남긴다. 2014.11.04 정용재
    var sTargetViewModel = $DextEditor.attr("target");
    if (typeof(sTargetViewModel) == "undefined" || sTargetViewModel == "") {
        sTargetViewModel = "viewModel";
    }
    var oTargetViewModel = eval(sTargetViewModel);
    DEXT5.setBodyValue(oTargetViewModel.selectedData()[$DextEditor.attr("value")]());
}

// 에디터 사이즈 조정
function resizeDextEditor() {
    var $DextEditor = $("#dext_editor");
    if ($DextEditor.length == 0) {
        return;
    }

    setTimeout(function() {
        var nEditorWidth = $DextEditor.attr("width");
        if (typeof(nEditorWidth) == "undefined") {
            nEditorWidth = $DextEditor.width();
        }
        var nEditorHeight = $DextEditor.attr("height");
        if (typeof(nEditorHeight) == "undefined") {
            nEditorHeight = $DextEditor.height();
        }
        DEXT5.setSize("100%", nEditorHeight);
    }, 250);
}