
var customtagList = [];
$.inwooLoadScreenData({
    code: true,
    codeFilter: ["SYS060"],
}, function() {
    customtagList = $.inwooCodeList("SYS060");
});


addCustomTag = function(cd_nm, type, target) {
    if (type == "sms") {
        if (typeof(target()) == "undefined") {
            target(cd_nm);
        } else {
            target(target() + cd_nm);
        }

    } else if (type == "email") {
        var temp = "";
        temp = cd_nm;

        // 타이틀(제목)에 커스텀태그입력
        if (viewModel.selectedData().tagTrgtId() == "emil_titl") {
            $("#emil_titl").focus();

            // <span>태그는 제거
            temp = temp.replace(/<(\/)?span(?:.|\n)*?>/gm, '');

            $("#emil_titl").val($("#emil_titl").val() + temp);
        } else {
        	// 스마트에디터(내용)내에 커스텀태그입력
            sBodyValue = DEXT5.setInsertHTML(temp);
        }
    };
};

parseCustomTag = function(textcnts) {
    var temp = textcnts;
    if (typeof(textcnts) == "string") {
        $.each(customtagList, function(i, v) {
            temp = temp.replaceAll(v.adtl_cd_nm, v.cd_nm);
        });
    }
    return temp;
};

trnsCustomTag = function(target) {
    if (typeof(target()) != "undefined") {
        $.each(customtagList, function(i, v) {
            target(target().replaceAll(v.cd_nm, v.adtl_cd_nm));
        });

    }
};
