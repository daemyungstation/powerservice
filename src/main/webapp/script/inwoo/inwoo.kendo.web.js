var inwoo = inwoo || {};
inwoo.kendo = inwoo.kendo || {};
inwoo.kendo.editor = inwoo.kendo.editor || {};
inwoo.kendo.tree = inwoo.kendo.tree || {};
inwoo.kendo.grid = inwoo.kendo.grid || {};
inwoo.kendo.dropdownlist = inwoo.kendo.dropdownlist || {};
inwoo.kendo.combobox = inwoo.kendo.combobox || {};
inwoo.kendo.listview = inwoo.kendo.listview || {};
inwoo.kendo.tooltip = inwoo.kendo.tooltip || {};
inwoo.kendo.upload = inwoo.kendo.upload || {};
inwoo.kendo.scheduler = inwoo.kendo.scheduler || {};

/**
 * Grid Message
 */
inwoo.kendo.grid.messages = {
    PAGEABLE: {
        display: "{0} - {1} / 검색결과 {2}건", //{0} is the index of the first record on the page, {1} - index of the last record on the page, {2} is the total amount of records
        empty: "검색 결과가 없습니다",
        page: "페이지",
        of: "{0}", //{0} is total amount of pages
        itemsPerPage: "페이지당 건수",
        first: "처음",
        previous: "이전",
        next: "다음",
        last: "끝",
        refresh: "새로고침"
    }
};

inwoo.kendo.editor.options = {
    tools: ['bold',
            'italic',
            'underline',
            'strikethrough',
            'justifyLeft',
            'justifyCenter',
            'justifyRight',
            'justifyFull',
            'insertUnorderedList',
            'insertOrderedList',
            'indent',
            'outdent',
            'createLink',
            'unlink',
            {
                name: "custom",
                tooltip: "Insert a horizontal rule",
                exec: function(e) {
                    $("body").prepend('<div id="window"></div>');

                    var window = $("#window");
                    window.kendoWindow({
                        width: "505px",
                        height: "315px",
                        title: "Rams's Ten Principles of Good Design",
                        content: inwoo.config.contextRoot + "/common/editor-image-upload.do",
                        actions: ["Close"],
                        open: function() { this.center(); },
                        modal: true,
                        visible: false
                    });

                    window.data("kendoWindow").open();

                    var editor = $(this).data("kendoEditor");
                    //editor.exec("inserthtml", { value: "<img src='" + inwoo.config.contextRoot + "/file/download?fileid=FLE201403041422590000562'>"});
                }
            },
            'subscript',
            'superscript',
            'createTable',
            'addRowAbove',
            'addRowBelow',
            'addColumnLeft',
            'addColumnRight',
            'deleteRow',
            'deleteColumn',
            'viewHtml',
            'formatting',
            'fontName',
            'fontSize',
            'foreColor',
            'backColor'],
    imageBrowser: {
        messages: {
            dropFilesHere: "Drop files here"
        },
        transport: {
             read: {
                 type: "POST",
                 contentType: "application/json; charset=utf-8",
                 url: inwoo.config.contextRoot + "/file/list",
                 dataType: "json"
             },
             destroy: {
                 // CSRF Protection 추가
                 headers: {
                     "X-CSRF-Token": inwoo.config.csrfToken
                 },
                 url: "/kendo-ui/service/ImageBrowser/Destroy",
                 type: "POST"
             },
             create: {
                 // CSRF Protection 추가
                 headers: {
                     "X-CSRF-Token": inwoo.config.csrfToken
                 },
                 url: "/kendo-ui/service/ImageBrowser/Create",
                 type: "POST"
             },
             thumbnailUrl: inwoo.config.contextRoot + '/file/download?fileid',
             uploadUrl: inwoo.config.contextRoot + "/file/imagebrowser/insert",
             imageUrl: inwoo.config.contextRoot + '/file/download?fileid={0}',
             parameterMap: function (options, operation) {
                 if (operation !== "read" && options.models) {
                     return JSON.stringify(options.models);
                 } else {
                     return JSON.stringify(options);
                 }
             }
        },
        schema: {
            data: function(response) {
                if (response.data != null) {
                    return response.data.list;
                }
            }
        },
        error: function(e) {
            alert(e.xhr.responseText.trim());
        }
    }
};

/**
 * Grid DataSource
 */
inwoo.kendo.grid.dataSource = function(config) {
    var dataSource = {
        transport: {
            read: {
                type: "POST",
                contentType: "application/json; charset=utf-8",
                url: config.url,
                dataType: "json",
                data: function() {
                    return config.context.searchJSON();
                }
            },
            create: {
                // CSRF Protection 추가
                headers: {
                    "X-CSRF-Token": inwoo.config.csrfToken
                },
                type: "POST",
                contentType: "application/json; charset=utf-8",
                url: config.insertUrl,
                dataType: "json"
            },
            update: {
                // CSRF Protection 추가
                headers: {
                    "X-CSRF-Token": inwoo.config.csrfToken
                },
                type: "POST",
                contentType: "application/json; charset=utf-8",
                url: config.updateUrl,
                dataType: "json"
            },
            parameterMap: function (options, operation) {
                if (operation !== "read" && options.models) {
                    return JSON.stringify(options.models);
                } else {
                    return JSON.stringify(options);
                }
            }
        },
        schema: {
            data: function(response) {
                if (response.data != null) {
                    // null값 공백처리 2015.07.07 정용재
                    $.each(response.data.list, function(i, v) {
                        $.each(v, function(key, value){
                            if(value==null)
                                eval("v." + key + "=\"\";");
                        });
                    });
                    return response.data.list;
                }
            },
            total: function(response) {
                if (response.data != null) {
                    return response.data.total;
                }
            },
            model: config.model
        },
        requestEnd: function(e) {
            if (config.context.onRequestEnd != null) {
                config.context.onRequestEnd(e);
            }
        },
        error: function(e) {
            alert(e.xhr.responseText.trim());
        },
        batch: config.batch,
        pageSize: config.pageSize,
        serverPaging: true,
        serverSorting: true,
        sort: config.sort,
        serverFiltering: true,
        aggregate: config.aggregate
    };

    return dataSource;
};

/**
 * Grid Config
 */
inwoo.kendo.grid.gridConfig = function(config) {

    var gridConfig = {
        widget: config.widget,
        data: {},
        dataSource: config.context.dataSource,
        autoBind: config.autoBind == null ? true : config.autoBind,
        scrollable: config.scrollable == null ? true : config.scrollable,
        resizable: config.resizable == null ? true : config.resizable,
        selectable: config.selectable == null ? "row" : config.selectable,
        rowTemplate: config.rowTemplate,
        useKOTemplates: config.useKOTemplates == null ? false : config.useKOTemplates,
        sortable: config.sortable == null ? {
            mode: "single",
            allowUnsort: false
        } : config.sortable,
        pageable: config.pageable != null ? config.pageable : {
            messages: inwoo.kendo.grid.messages.PAGEABLE
        },
        toolbar : config.toolbar,
        columns : config.columns,
        editable: config.editable,
        edit: config.edit,
        change: function(e) {
            config.context.onChange(e);
        },
        dataBound: function(e) {
            config.context.onDataBound(e);
        },
        saveChanges: function(e) {
            config.context.onSaveChanges(e);
        },
        detailTemplate: config.detailTemplate,
        detailInit: config.detailInit
    };

    return $.extend(config, gridConfig);
};

/**
 *  DropDonwList DataSource
 */
inwoo.kendo.dropdownlist.dataSource = function(url, param, callback) {
    var dataSource = {
        type: "odata",
        serverFiltering: true,
        transport: {
            read: {
                type: "POST",
                contentType: "application/json; charset=utf-8",
                url: url,
                dataType: "json",
                data: param
            },
            parameterMap: function (data, operation) {
                return JSON.stringify(data);
            }
        },
        schema: {
            data: function(response) {
                return response.data.list;
            },
            total: function(response) {
                return response.data.total;
            }
        },
        requestEnd: function(e) {
            if (typeof(callback) == "function") {
                setTimeout(function() {
                    callback(e);
                }, 150);
            }
        },
        error: function(e) {
            alert(e.xhr.responseText.trim());
        }
    };


    return dataSource;
};

/**
 *  ComboBox DataSource
 */
inwoo.kendo.combobox.dataSource = function(url, param) {
    var dataSource = {
        type: "odata",
        serverFiltering: true,
        transport: {
            read: {
                type: "POST",
                contentType: "application/json; charset=utf-8",
                url: url,
                dataType: "json"
            },
            parameterMap: function (data, operation) {
                return JSON.stringify(data);
            }
        },
        schema: {
            data: function(response) {
                if (response.data != null) {
                    // console.log(ko.toJSON(response.data));
                    return response.data.list;
                }
            },
            total: function(response) {
                if (response.data != null) {
                    return response.data.total;
                }
            }
        },
        error: function(e) {
            alert(e.xhr.responseText.trim());
        }
    };

    return dataSource;
};

/**
 * ListView DataSource
 */
inwoo.kendo.listview.dataSource = function(config) {
    var dataSource = {
        transport: {
            read: {
                type: "POST",
                contentType: "application/json; charset=utf-8",
                url: config.url,
                dataType: "json",
                data: function() {
                    return config.context.searchJSON();
                }
            },
            parameterMap: function (data, operation) {
                return JSON.stringify(data);
            }
        },
        schema: {
            data: function(response) {
                return response.data.list;
            },
            total: function(response) {
                try{ config.context.total(response.data.total); } catch(e) {};
                return response.data.total;
            }
        },
        error: function(e) {
            alert(e.xhr.responseText.trim());

            if (config.onError != null) {
                config.onError(e);
            }
        },
        pageSize: config.pageSize,
        serverPaging: true,
        serverSorting: true,
        sort: config.sort
    };

    return $.extend(config, dataSource);
};

/**
 *  Tree DataSource
 */
inwoo.kendo.tree.dataSource = function(config) {
    var dataSource = {
        transport: {
            read: {
                type: "POST",
                contentType: "application/json; charset=utf-8",
                url: config.url,
                dataType: "json",
                data: function() {
                    return config.context.searchJSON();
                }
            },
            parameterMap: function (data, operation) {
                return JSON.stringify(data);
            }
        },
        schema: {
            data: function(response) {
                return response.data.list;
            },
            total: function(response) {
                return response.data.total;
            },
            model: {
                id: config.nodeId,
                hasChildren: "haschildren"
            }
        },
        error: function(e) {
            alert(e.xhr.responseText.trim());
        },
        serverSorting: true,
        sort: config.sort
    };

    return $.extend(config, dataSource);
};

/**
 *  Tree Config
 */
inwoo.kendo.tree.treeConfig = function(config) {
    var treeConfig = {
        widget: config.widget,
        dataSource: config.context.dataSource,
        dataTextField: config.dataTextField,
        change: function(e) {
            config.context.onChange(e);
        },
        dataBound: function(e) {
            config.context.onDataBound(e);
        }
    };

    return $.extend(config, treeConfig);
};

/**
 * Upload Config
 */
inwoo.kendo.upload.uploadConfig = function(config) {
    var uploadConfig = {
        async: {
            saveUrl: inwoo.config.fileSaveUrl,
            removeUrl: config.autoUpload == null ? inwoo.config.fileRemoveUrl : inwoo.config.fileRemoveRealUrl,
            autoUpload: config.autoUpload == null ? false : config.autoUpload,
            saveField: config.saveField
        },
        localization: {
            select: "파일 선택",
            dropFilesHere: "마우스로 파일을 끌어오세요.",
            statusUploaded: "파일 업로드 완료",
            statusUploading: "파일 업로드 중"
        },
        files: config.files,
        serverMessage: null,
        template: (config.template == null) ?
                  "<span class='k-progress'></span>" +
                  "<div class='file-wrapper'>" +
                  "    <span class='file-icon #=addExtensionClass(files[0].extension)#-16x16'></span>" +
                  "    <span class='file-heading'>#=name# (#=Math.ceil(size / 1024)# KB)</span>" +
                  "    <button type='button' class='k-upload-action'></button>" +
                  "</div>"
                  : config.template,
        complete: function(e) {
            var $KendoUpload = $(e.sender.element).closest("div.k-upload");

            var nErrorCount = 0;
            if ($KendoUpload.length > 0) {
                nErrorCount = $KendoUpload.find("li.k-file-error").length;
            }

            if (nErrorCount > 0) { // 업로드 오류
                // 진행바 숨기기
                var $SaveForm = $KendoUpload.closest("form");
                if ($SaveForm.length > 0) {
                    var $LoadingMask = $SaveForm.find("div.k-loading-mask");
                    if ($LoadingMask.length > 0) {
                        kendo.ui.progress($LoadingMask.parent(), false);
                    }
                }

                var sErrorMessage = "파일 업로드 오류가 발생했습니다.";
                if (serverMessage != null && serverMessage != "") {
                    sErrorMessage = "파일 업로드 " + serverMessage;

                    serverMessage = null;
                }
                if (!confirm(sErrorMessage + "\r\n확인 후 진행하시겠습니까?")) {
                    config.context.saveData();
                }
            } else { // 업로드 성공
                config.context.saveData();
            }
        },
        select: function(e) {
            var oFileList = e.files;

            var $KendoUpload = $(e.sender.element).closest("div.k-upload");
            if ($KendoUpload.length == 0) {
                e.preventDefault();

                alert("업로드 콤포넌트를 찾을 수 없습니다.");
                return;
            }

            // 첨부파일 여러개 선택하여 등록시 처리
            if (oFileList.length + $KendoUpload.find(".k-file").length > config.uploadCnt) {
                e.preventDefault();

                alert("최대 " + config.uploadCnt + "개의 파일을 첨부할 수 있습니다.");
                return;
            }

            if (config.fileTypes == null) {
                if (config.uploadType != null && config.uploadType == "20") { // 이미지
                    config.fileTypes = [".bmp", ".gif", ".jpeg", ".jpg", ".png"];
                } else { // 첨부파일
                    config.fileTypes = [".bmp", ".gif", ".jpeg", ".jpg", ".png",
                                        ".doc", ".docx", ".ppt", ".pptx", ".xls", ".xlsx",
                                        ".hwp", ".html", ".pdf", ".txt", ".wav", ".wmv",
                                        ".zip", ".rar", ".alz"];
                }
            }

            var nTotalFileSize = 0;
            for (var i = 0; i < oFileList.length; i++) {
                if (config.fileTypes.indexOf(oFileList[i].extension.toLowerCase()) < 0) {
                    e.preventDefault();

                    alert("[" + oFileList[i].extension + "]는 첨부할 수 없는 파일형식입니다.");
                    return;
                }

                // maxUploadFileSize 파라미터가 있을경우 전달된 사이즈로 비교. 없을경우 config에 고정된 값으로 비교
                if (config.maxUploadFileSize) {
                    if (oFileList[i].size > config.maxUploadFileSize) {
                        e.preventDefault();

                        alert(config.maxUploadFileSizeMsg);
                        return;
                    }
                } else {
                    if (oFileList[i].size > inwoo.config.maxUploadSizeFile) {
                        e.preventDefault();

                        alert(inwoo.config.errMsgMaxUploadSizeFile);
                        return;
                    }
                }

                nTotalFileSize += oFileList[i].size;
            }

            if (nTotalFileSize > inwoo.config.maxUploadSizeTotal) {
                e.preventDefault();

                alert(errMsgMaxUploadSizeTotal);
                return;
            }

            if (config.afterSelect != null) {
                config.afterSelect(e);
            }
        },
        remove: function(e) {
            var oFileList = e.files;
            e.data = {"fileid": oFileList[0].id};

            if (config.afterRemove != null) {
                config.afterRemove(e);
            }
        },
        upload: function(e) {
            if (config.uploadId != null) {
                e.data = {"atch_typ_cd": config.uploadType, "rltn_tbl_nm": config.uploadTable, "rltn_item_id": config.uploadId};
            } else {
                e.data = {"atch_typ_cd": config.uploadType, "rltn_tbl_nm": config.uploadTable};
            }
        },
        success: function(e) {
            var oFileIdList = config.context.selectedData().fileIds();

            if (e.operation == "upload") {
                var sFileId = e.response.file_id;

                e.files[0].id = sFileId;
                oFileIdList.push(sFileId);
            } else if (e.operation == "remove") {
                var sFileId = e.response.file_id;
                var nIndex = oFileIdList.indexOf(sFileId);

                oFileIdList.splice(nIndex, 1);
            }
        },
        error: function(e) {
            // 서버 메시지 초기화
            serverMessage = null;

            if (e != null && e.operation != null) {
                if (e.operation == "upload") { // 업로드
                    // 서버 메시지 저장
                    if (e.XMLHttpRequest != null && e.XMLHttpRequest.response != null) {
                        serverMessage = e.XMLHttpRequest.response.trim();
                    }
                } else if (e.operation == "remove") { // 삭제
                    alert("파일 삭제 오류가 발생했습니다.\r\n확인 후 다시 시도해주세요.");
                }
            }
        },
        multiple: config.multiple
    };

    return $.extend(config, uploadConfig);
};

/**
 * Excel Upload Config
 */
inwoo.kendo.upload.excelUploadConfig = function(config) {
    var uploadConfig = {
        async: {
            saveUrl: config.saveUrl,
            removeUrl: inwoo.config.fileRemoveUrl,
            autoUpload: false,
            saveField: config.saveField
        },
        localization: {
            select: "파일 선택",
            dropFilesHere: "마우스로 파일을 끌어오세요.",
            statusUploaded: "파일 업로드 완료",
            statusUploading: "파일 업로드 중"
        },
        files: config.files,
        serverMessage: null,
        template: "<span class='k-progress'></span>" +
                  "<div class='file-wrapper'>" +
                  "    <span class='file-icon #=addExtensionClass(files[0].extension)#-16x16'></span>" +
                  "    <span class='file-heading'>#=name# (#=Math.ceil(size / 1024)# KB)</span>" +
                  "    <button type='button' class='k-upload-action'></button>" +
                  "</div>",
        complete: function(e) {
            // 진행바 숨기기
            var $LoadingMask = $("div.k-loading-mask");
            if ($LoadingMask.length > 0) {
                kendo.ui.progress($LoadingMask.parent(), false);
            }

            // 업로드 오류 확인
            var $KendoUpload = $(e.sender.element).closest("div.k-upload");
            var nErrorCount = 0;
            if ($KendoUpload.length > 0) {
                nErrorCount = $KendoUpload.find("li.k-file-error").length;
            }
            if (nErrorCount > 0) {
                if (serverMessage == null || serverMessage == "") {
                    alert("파일 업로드 오류가 발생했습니다.");
                } else {
                    alert("파일 업로드 " + serverMessage);

                    serverMessage = null;
                }
            }

            if (config.afterUpload != null) {
                config.afterUpload(e, nErrorCount);
            }
        },
        select: function(e) {
            var oFileList = e.files;

            var $KendoUpload = $(e.sender.element).closest("div.k-upload");
            if ($KendoUpload.length == 0) {
                e.preventDefault();

                alert("업로드 콤포넌트를 찾을 수 없습니다.");
                return;
            }

            if (oFileList.length + $KendoUpload.find(".k-file").length > 1) {
                e.preventDefault();

                alert("최대 1개의 파일을 첨부할 수 있습니다.");
                return;
            }

            if (config.fileTypes == null) {
                config.fileTypes = [".xls", ".xlsx"];
            }

            if (config.fileTypes.indexOf(oFileList[0].extension.toLowerCase()) < 0) {
                e.preventDefault();

                alert("첨부할 수 없는 파일형식입니다.");
                return;
            }

            if (oFileList[0].size > inwoo.config.maxUploadSizeExcel) {
                e.preventDefault();

                alert(inwoo.config.errMsgMaxUploadSizeExcel);
                return;
            }

            $KendoUpload.find(".k-dropzone").hide();
            $KendoUpload.find(".k-upload-button").hide();

            if (config.afterSelect != null) {
                config.afterSelect(e);
            }
        },
        remove: function(e) {
            var oFileList = e.files;
            e.data = {"fileid": oFileList[0].id};

            var $KendoUpload = $(e.sender.element).closest("div.k-upload");
            if ($KendoUpload.length > 0) {
                $KendoUpload.find(".k-dropzone").show();
                $KendoUpload.find(".k-upload-button").show();
            }

            if (config.afterRemove != null) {
                config.afterRemove(e);
            }
        },
        upload: function(e) {
            if (config.uploadData != null) {
                e.data = config.uploadData;
            } else {
                e.data = {};
            }

            // 추가 파라미터 전달
            if (config.extraParam != null) {
                $.each(config.extraParam(), function(sKey, oValue) {
                    e.data[sKey] = oValue;
                });
            }
        },
        success: function(e) {
            if (config.afterSuccess != null) {
                config.afterSuccess(e);
            }
        },
        error: function(e) {
            // 서버 메시지 초기화
            serverMessage = null;

            if (e != null && e.operation != null) {
                if (e.operation == "upload") { // 업로드
                    // 서버 메시지 저장
                    if (e.XMLHttpRequest != null && e.XMLHttpRequest.response != null) {
                        serverMessage = e.XMLHttpRequest.response.trim();
                    }
                } else if (e.operation == "remove") { // 삭제
                    alert("파일 삭제 오류가 발생했습니다.\r\n확인 후 다시 시도해주세요.");
                }
            }
        },
        multiple: false
    };

    return $.extend(config, uploadConfig);
};

/**
 * File Config
 */
inwoo.kendo.tooltip.fileConfig = function(config) {
    var tooltipConfig = {
        filter: ".m_ico.attach",
        content: {
            url: inwoo.config.contextRoot + "/common/file-list.do"
        },
        width: config.width,
        height: config.height,
        autoHide: false,
        showOn: "click",
        position: "top",
        requestStart: function(e) {
            e.options.url = kendo.format(inwoo.config.contextRoot + "/common/file-list.do?rltn_item_id={0}&height=" + config.height, e.target.data("id"));
        }
    };

    return $.extend(config, tooltipConfig);
};

/**
 * Scheduler Message
 */
inwoo.kendo.scheduler.messages = {
    allDay: "종일",
    cancel: "취소",
    date: "날짜",
    deleteWindowTitle: "일정삭제",
    destroy: "삭제",
    event: "일정",
    save: "저장",
    time: "시간",
    today: "오늘",
    editor: {
        allDayEvent: "종일",
        description: "설명",
        editorTitle: "일정",
        end: "종료일",
        start: "시작일",
        title: "제목"
    },
    views: {
        day: "일별",
        week: "주별",
        month: "월별",
        agenda: "목록"
    }
};

/**
 * Scheduler DataSource
 */
inwoo.kendo.scheduler.dataSource = function(config) {
    var dataSource = {
        transport: {
            read: {
                type: "POST",
                contentType: "application/json; charset=utf-8",
                url: config.url,
                dataType: "json",
                data: function() {
                    var scheduler = $("#" + config.schedulerId).data("kendoScheduler");
                    var result = {
                        start: scheduler.view().startDate(),
                        end: scheduler.view().endDate()
                    };
                    return result;
                }
            },
            create: {
                // CSRF Protection 추가
                headers: {
                    "X-CSRF-Token": inwoo.config.csrfToken
                },
                type: "POST",
                contentType: "application/json; charset=utf-8",
                url: config.saveUrl,
                dataType: "json"
            },
            update: {
                // CSRF Protection 추가
                headers: {
                    "X-CSRF-Token": inwoo.config.csrfToken
                },
                type: "POST",
                contentType: "application/json; charset=utf-8",
                url: config.saveUrl,
                dataType: "json"
            },
            destroy: {
                // CSRF Protection 추가
                headers: {
                    "X-CSRF-Token": inwoo.config.csrfToken
                },
                type: "POST",
                contentType: "application/json; charset=utf-8",
                url: config.deleteUrl,
                dataType: "json"
            },
            parameterMap: function (options, operation) {
                if (operation !== "read" && options.models) {
                    return JSON.stringify(options.models);
                } else {
                    return JSON.stringify(options);
                }
            }
        },
        schema: {
            data: function(response) {
                return response.data;
            },
            model: config.model
        },
        error: function(e) {
            alert(e.xhr.responseText.trim());
        },
        batch: true,
        serverFiltering: true
    };

    return $.extend(config, dataSource);
};
