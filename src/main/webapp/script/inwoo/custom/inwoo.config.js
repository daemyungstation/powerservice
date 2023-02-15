var inwoo = inwoo || {};

inwoo.config = {
    contextRoot: "/powerservice",
    contextRootMobile: "/m/powerservice",
    refreshTimeOfMiniBoard: 30000,
    submitActionParam: "submit_action",
    submitTokenParam: "submit_token",

   // 파일업로드 사이즈 (Total:파일총합, File : 일반적인 첨부파일, Excel:엑셀파일)
    maxUploadSizeTotal : 104857600,
    maxUploadSizeFile : 15728640,
    maxUploadSizeExcel : 104857600,
    errMsgMaxUploadSizeTotal : "최대 100MB까지 파일을 첨부할 수 있습니다.",
    errMsgMaxUploadSizeFile : "최대 15MB의 파일을 첨부할 수 있습니다.",
    errMsgMaxUploadSizeExcel : "최대 100MB의 파일을 첨부할 수 있습니다.",
};



inwoo.data =inwoo.data || {};
var oEmpty = {"cd" : "", "cd_nm" : "", "adtl_cd" : "", "adtl_cd_nm" : ""};
inwoo.data.periodList = [{cd: "day",        cd_nm: "일",    parseFormats: "yyyyMMdd",   format: "yyyy-MM-dd",   depth: "month"},
                         {cd: "week",       cd_nm: "주",    parseFormats: "yyyyMMdd",   format: "yyyy-MM-dd",   depth: "month"},
                         {cd: "month",      cd_nm: "월",    parseFormats: "yyyyMM",     format: "yyyy-MM",      depth: "year"},
                         {cd: "quarter",    cd_nm: "분기",  parseFormats: "yyyyMM",     format: "yyyy-MM",      depth: "year"},
                         {cd: "year",       cd_nm: "년",    parseFormats: "yyyy",       format: "yyyy",         depth: "decade"},
                        ];
                        /**
                         * 수정일자 : 2015-11-03
                         * 수 정 자 : 정훈
                         * 수정이유 : 날짜 검색 기준변경
                         */
inwoo.data.periodsList = [{cd: "day",		cd_nm: "01",		adtl_cd: [{cd: "0101",	cd_nm: "전체",		adtl_cd: "all"},
                                                                          {cd: "0102",	cd_nm: "금일",		adtl_cd: 0},
                                                                          {cd: "0103",	cd_nm: "전일",		adtl_cd: -1},
                                                                          {cd: "0104",	cd_nm: "1주일",		adtl_cd: -7},
                                                                          {cd: "0105",	cd_nm: "1개월",		adtl_cd: -1,	adtl_cd_nm: "month",	base: true},
                                                                          {cd: "0106",	cd_nm: "3개월",		adtl_cd: -3,	adtl_cd_nm: "month",	base: true},
                                                                          {cd: "0107",	cd_nm: "6개월",		adtl_cd: -6,	adtl_cd_nm: "month",	base: true}
                                                                         ]
                          },
                          {cd: "week",		cd_nm: "02",		adtl_cd: [{cd: "0201",	cd_nm: "전체",		adtl_cd: "all"},
                                                                          {cd: "0202",	cd_nm: "금주",		adtl_cd: 0,		base: true},
                                                                          {cd: "0203",	cd_nm: "전주",		adtl_cd: -1},
                                                                          {cd: "0204",	cd_nm: "4주",		adtl_cd: -3},
                                                                          {cd: "0205",	cd_nm: "8주",		adtl_cd: -7},
                                                                          {cd: "0206",	cd_nm: "12주",		adtl_cd: -11}
                                                                         ]
                          },
                          {cd: "month",		cd_nm: "03",		adtl_cd: [{cd: "0301",	cd_nm: "전체",		adtl_cd: "all"},
                                                                          {cd: "0302",	cd_nm: "금월",		adtl_cd: 0,		base: true},
                                                                          {cd: "0303",	cd_nm: "3개월",		adtl_cd: -2},
                                                                          {cd: "0304",	cd_nm: "6개월",		adtl_cd: -5},
                                                                          {cd: "0305",	cd_nm: "12개월",	adtl_cd: -11}
                                                                         ]
                          },
                          {cd: "quarter",	cd_nm: "04",		adtl_cd: [{cd: "0401",	cd_nm: "전체",		adtl_cd: "all"},
                                                                          {cd: "0402",	cd_nm: "금년",		adtl_cd: 0,		adtl_cd_nm: "year",		base: true},
                                                                          {cd: "0403",	cd_nm: "전년",		adtl_cd: -1,	adtl_cd_nm: "year"},
                                                                          {cd: "0404",	cd_nm: "3년",		adtl_cd: -2,	adtl_cd_nm: "year"},
                                                                         ]
                          },
                          {cd: "year",		cd_nm: "05",		adtl_cd: [{cd: "0501",	cd_nm: "전체",		adtl_cd: "all"},
                                                                          {cd: "0502",	cd_nm: "금년",		adtl_cd: 0,		base: true},
                                                                          {cd: "0503",	cd_nm: "3년",		adtl_cd: -2},
                                                                          {cd: "0504",	cd_nm: "5년",		adtl_cd: -4}
                                                                         ]
                            }
                        ];

/*
 * 1. inwoo.period(startd오브젝트, endd오브젝트, period오브젝트, 전체검색유무, 표출할 period목록 문자열:선택사항, startd픽커 ID:선택, endd픽커 ID:선택)
 *    period목록: day, month, week,  quarter, year
 *    ex) this.term = new inwoo.period(this.startd, this.endd, this.period, false, "day, month, year");
 *
 *    참고사항)
 *    kendoDatePick의 초기 parseFormats: ['yyyyMMdd'], format: 'yyyy-MM-dd'은 첫 조회 조건형태에 맞추어야 함.
 *    ex) month -> parseFormats: ['yyyyMM'], format: 'yyyy-MM'
 *
 *
 * 2. period 범례추가
 *    this.term.addPeriods(period, {cd: 코드값,	cd_nm: 코드명, adtl_cd: 증감일수, adtl_cd_nm: period와 다른 날짜로 계산할경우 입력});
 * 	  ex) this.term.addPeriods("day", {cd: "05", cd_nm: "3개월", adtl_cd: -3, adtl_cd_nm: "month"});
 *
 * 3. period 범례삭제
 * 	  this.term.delPeriods("week", [val1, val2, ..]);  -  val은 코드값 또는 코드명
 *    ex) this.term.delPeriods("week", [0, "전주"]);
 *    	  this.term.delPeriods("month", 0);
          this.term.delPeriods("month", "3개월");
 *
 * 4. period 기본선택값 변경
 *    this.term.changeBase(period", cd 또는 cd_nm);
 *    ex) this.term.changeBase("day", -3);
 *

 */
inwoo.period = function (obj_startd, obj_endd, obj_period, allfg, option, startd_id, endd_id) {
    this.periodList_bak = $.extend(true, [], inwoo.data.periodList);
    this.periodsList_bak = $.extend(true, [], inwoo.data.periodsList);

    this.periodList = ko.observableArray();
    this.periodsList = ko.observableArray();

    this.periods = ko.observable().extend({notify: "always"});

    if (typeof(option) != "undefined") {
        this.periodList($.grep(this.periodList_bak, function(v, i) {
            return option.indexOf(v.cd) >= 0;
        }));
        this.periodsList($.grep(this.periodsList_bak, function(v, i) {
            return option.indexOf(v.cd) >= 0;
        }));
    } else {
        this.periodList(this.periodList_bak);
        this.periodsList(this.periodsList_bak);
    }

    obj_period.subscribe(function(newVal) {
        var startdPicker = $("#" + (typeof(startd_id) == "string" ? startd_id : "search_stt_dt")).data("kendoDatePicker");
        var enddPicker = $("#" + (typeof(endd_id) == "string" ? endd_id : "search_end_dt")).data("kendoDatePicker");

        if (startdPicker != null && enddPicker != null) {
            var codeObj = getCodeObj(this.periodList(), newVal);
            startdPicker.setOptions({
                "parseFormats"	: codeObj.parseFormats,
                "format"		: codeObj.format,
                "start"			: codeObj.depth,
                "depth"			: codeObj.depth
            });
            enddPicker.setOptions({
                "parseFormats"	: codeObj.parseFormats,
                "format"		: codeObj.format,
                "start"			: codeObj.depth,
                "depth"			: codeObj.depth
            });
        }

        // periodsList 원본으로부터 해당 Period의 선택목록을 가져온다.
        var tempList = $.grep(this.periodsList_bak, function(v, i) {
            return v.cd == newVal && v.cd != "input";
        });

        if (tempList.length>0) {
            this.periodsList($.grep(tempList[0].adtl_cd, function(v, i) {
                 return allfg==true || v.cd != "all";
            }));
            //기본선택 : base가 true설정된 값(없을 경우 맨 처음 값)
            var defaultcd = this.periodsList()[0].cd;
            $.each(this.periodsList(), function(i, v) {
                if (true == v.base) {
                    defaultcd = v.cd;
                }
            });
            this.periods(defaultcd);
        }
    }.bind(this));

    this.periods.subscribe(function(newVal) {
        var codeObj = getCodeObj(this.periodsList(), newVal);
        if (codeObj.adtl_cd == "all") { // 전체
            obj_startd("");
            obj_endd("");
            return;
        } else if (newVal == "input") {	// 직접입력을 경우엔 리턴하고 직접입력이 아닐경우엔 목록에서 직접입력을 제거한다.
            return;
        } else {
            this.periodsList($.grep(this.periodsList(), function(v, i) {
                return v.cd != "input";
            }));
        }

        // 추가코드 유무에 따라서 추가코드가 있는 경우 해당값으로 날짜 계산
        var term = obj_period();
        if (typeof(codeObj.adtl_cd_nm) != "undefined") {
            term = codeObj.adtl_cd_nm;
        }

        if (obj_period() == "quarter") {
            term = "year";
            obj_startd(moment().startOf(term).add(term+"s", codeObj.adtl_cd));
            obj_endd(moment());
        } else if(obj_period() == "day") {
            /**
             * 수정일자 : 2015-11-03
             * 수 정 자 : 정훈
             * 수정이유 : 전월 기준 변경
             */
            if(term != "day"){
                obj_startd(moment().startOf(obj_period()).add(term+"s", codeObj.adtl_cd).add("days", 1));
                obj_endd(moment());
            }else{
                obj_startd(moment().startOf(obj_period()).add(term+"s", codeObj.adtl_cd));
                obj_endd(moment());
            }
        } else{
            obj_startd(moment().startOf(obj_period()).add(term+"s", codeObj.adtl_cd));
            obj_endd(moment());
        }
    }.bind(this));

    if (this.periodList().length > 0) {
        obj_period(this.periodList()[0].cd);
    }

    this.addPeriods = function(period, value) {
        var index = -1;
        $.each(this.periodsList_bak, function (i, v) {
            if (v.cd == period) {
                index = i;
            }
        });

        if (index > -1) {
            var pos = null;

            value.cd = this.periodsList_bak[index].cd_nm + value.cd;

            $.grep(this.periodsList_bak[index].adtl_cd, function (v, i) {
                if (v.cd == value.cd) {
                    pos = i;
                    v.cd++;
                } else if (v.cd > value.cd) {
                    v.cd++;
                }
            });

            if (pos == null) {
                this.periodsList_bak[index].adtl_cd.push(value);
            } else {
                this.periodsList_bak[index].adtl_cd.splice(pos, 0, value);
            }

            // periodsList 원본으로부터 해당 Period의 선택목록을 가져온다.
            var tempList = $.grep(this.periodsList_bak, function(v, i) {
                return v.cd == obj_period();
            });

            if (tempList.length>0) {
                this.periodsList($.grep(tempList[0].adtl_cd, function(v, i) {
                     return allfg==true || v.adtl_cd != "all";
                }));
                //기본선택 : base가 true설정된 값(없을 경우 맨 처음 값)
                var defaultcd = this.periodsList()[0].cd;
                $.each(this.periodsList(), function(i, v) {
                    if (true == v.base) {
                        defaultcd = v.cd;
                    }
                });
                this.periods(defaultcd);
            }
        }
    }.bind(this);
    this.delPeriods = function(period, value) {
        var index = -1;
        $.each(this.periodsList_bak, function (i, v) {
            if (v.cd == period) {
                index = i;
            }
        });

        if (index > -1) {
            this.periodsList_bak[index].adtl_cd = $.grep(this.periodsList_bak[index].adtl_cd, function(v, i) {
                if (typeof(value) == "number" || typeof(value) == "string") {
                    return (value != v.cd && value != v.cd_nm);
                } else if (typeof(value) == "object") {
                    return $.grep(value, function(comp) {return comp==v.cd || comp==v.cd_nm;}).length == 0;
                }

            });

            // periodsList 원본으로부터 해당 Period의 선택목록을 가져온다.
            var tempList = $.grep(this.periodsList_bak, function(v, i) {
                return v.cd == obj_period();
            });

            if (tempList.length>0) {
                this.periodsList($.grep(tempList[0].adtl_cd, function(v, i) {
                     return allfg==true || v.cd != "all";
                }));
                //기본선택 : base가 true설정된 값(없을 경우 맨 처음 값)
                var defaultcd = this.periodsList()[0].cd;
                $.each(this.periodsList(), function(i, v) {
                    if (true == v.base) {
                        defaultcd = v.cd;
                    }
                });
                this.periods(defaultcd);
            }
        }
    }.bind(this);
    this.changeBase = function(period, target) {
        var index = -1;
        $.each(this.periodsList_bak, function (i, v) {
            if (v.cd == period) {
                index = i;
            }
        });

        if (index > -1) {
            $.each(this.periodsList_bak[index].adtl_cd, function (i, v) {
                if (v.cd == target || v.cd_nm == target) {
                    v.base = true;
                } else {
                    v.base = false;
                }
            });

            // periodsList 원본으로부터 해당 Period의 선택목록을 가져온다.
            var tempList = $.grep(this.periodsList_bak, function(v, i) {
                return v.cd == obj_period();
            });

            if (tempList.length>0) {
                this.periodsList($.grep(tempList[0].adtl_cd, function(v, i) {
                     return allfg==true || v.cd != "all";
                }));
                //기본선택 : base가 true설정된 값(없을 경우 맨 처음 값)
                var defaultcd = this.periodsList()[0].cd;
                $.each(this.periodsList(), function(i, v) {
                    if (true == v.base) {
                        defaultcd = v.cd;
                    }
                });
                this.periods(defaultcd);
            }
        }
    }.bind(this);

    this.clickPicker = function() {
        if (this.periods() != "input") {
            this.periodsList().push({cd: "input",	cd_nm: "직접입력"});
            this.periodsList(this.periodsList());
            this.periods("input");
        }
    }.bind(this);

};