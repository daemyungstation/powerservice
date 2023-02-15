/**
 * codeset 등의 코드성 데이터 호출
 */

(function($) {
    var _screenData = {
          basicValueList: []
        , codeList: []
        , hrchCodeList: []
        , consTypList: []
        , userList: []
        , teamList: []
        , cntrList: []
        , serverList: []
        , wrkTypList: []
        , authorityList: []
        , evltTypList: []
        , evltPlanList: []
        , campaignList: []
    };

    $.inwooLoadCodeList = function(arrCodeset, callback) {
        $.ajax({
              type : "POST"
            , async : true
            , url : inwoo.config.contextRoot + "/syst/cd/scrn-list"
            , dataType : "json"
            , cache : false
            , data : JSON.stringify(arrCodeset)
            , contentType: "application/json; charset=utf-8"
            , success : function(obj) {
                _screenData.codeList = obj.data;
                callback();
            }
            , error : function(response, status) {
                alert("초기 데이터 적재를 실패하였습니다.\n화면을 다시 불러주십시요.");
                logslog("ajaxEx " + "" + " : " + status + "/" + response.status + "(URL : " + url + " + param : " + pars + ")");
            }

        });
    };

    $.inwooLoadScreenData = function(param, callback) {
        $.ajax({
              type : "POST"
            , async : true
            , url : inwoo.config.contextRoot + "/main/screen-data"
            , dataType : "json"
            , cache : false
            , data : JSON.stringify(param)
            , contentType: "application/json; charset=utf-8"
            , success : function(obj) {
                if (obj.data.basicValueList != null) _screenData.basicValueList = obj.data.basicValueList;
                if (obj.data.codeList != null) _screenData.codeList = obj.data.codeList;
                if (obj.data.hrchCodeList != null) _screenData.hrchCodeList = obj.data.hrchCodeList;
                if (obj.data.consTypList != null) _screenData.consTypList = obj.data.consTypList;
                if (obj.data.userList != null) _screenData.userList = obj.data.userList;
                if (obj.data.teamList != null) _screenData.teamList = obj.data.teamList;
                if (obj.data.cntrList != null) _screenData.cntrList = obj.data.cntrList;
                if (obj.data.serverList != null) _screenData.serverList = obj.data.serverList;
                if (obj.data.wrkTypList != null) _screenData.wrkTypList = obj.data.wrkTypList;
                if (obj.data.authorityList != null) _screenData.authorityList = obj.data.authorityList;
                if (obj.data.evltTypList != null) _screenData.evltTypList = obj.data.evltTypList;
                if (obj.data.evltPlanList != null) _screenData.evltPlanList = obj.data.evltPlanList;
                if (obj.data.campaignList != null) _screenData.campaignList = obj.data.campaignList;

                callback();
            }
            , error : function() {
                alert("초기 데이터 적재를 실패하였습니다.\n화면을 다시 불러주십시요.");
            }

        });
    };

    $.inwooBasicValue = function(psBasVaNm) {
        for (var i = 0; _screenData.basicValueList.length; i++) {
            if (_screenData.basicValueList[i].basicvaluenm == psBasVaNm) {
                return _screenData.basicValueList[i].bas_vl;
            }
        }
        return "";
    };

    $.inwooCodeList = function(psCdSetCd, psAddCd) {
        if (psAddCd != undefined) {
            return $.grep(_screenData.codeList, function (a) { return a.cd_set_cd == psCdSetCd && a.adtl_cd == psAddCd; });
        } else {
            return $.grep(_screenData.codeList, function (a) { return a.cd_set_cd == psCdSetCd; });
        }
    };

    $.inwooHrchCodeList = function(psHrchTypCd, psHrchCdLvlCnt) {
        return $.grep(_screenData.hrchCodeList, function (a) { return a.hrch_typ_cd == psHrchTypCd && a.hrch_cd_lvl_cnt == psHrchCdLvlCnt; });
    };

    $.inwooCodeFilterList = function(psCdSetCd, lCdList) {
        return $.grep(_screenData.codeList, function (a) { return (a.cd_set_cd == psCdSetCd && lCdList.indexOf(a.cd) >= 0); });
    };

    $.inwooUserList = function(psTeamCd) {
        if (psTeamCd == null) {
            return _screenData.userList;
        }
        else {
            return $.grep(_screenData.userList, function (a) { return a.team_cd == psTeamCd; });
        }
    };

    $.inwooTeamList = function(psCntrCd) {
        if (psCntrCd == null) {
            return _screenData.teamList;
        }
        else {
            return $.grep(_screenData.teamList, function (a) { return a.cntr_cd == psCntrCd; });
        }
    };

    $.inwooCntrList = function() {
        return _screenData.cntrList;
    };

    $.inwooServerList = function() {
        return _screenData.serverList;
    };

    $.inwooWrkTypList = function() {
        return _screenData.wrkTypList;
    };

    $.inwooAuthorityList = function() {
        return _screenData.authorityList;
    };

    $.inwooConsTypList = function() {
        return _screenData.consTypList;
    };

    $.inwooEvltTypList = function() {
        return _screenData.evltTypList;
    };

    $.inwooEvltPlanList = function() {
        return _screenData.evltPlanList;
    };

    $.inwooCampaignList = function() {
        return _screenData.campaignList;
    };

})(jQuery);
