var inwoo = inwoo || {};
inwoo.view = inwoo.view || {};

inwoo.view.__popupList = [];

inwoo.view.popupCenter = function(url, name, width, height, scroll) {
    var scrollbar = "";

    if (scroll == undefined) scrollbar = "no";
    else scrollbar = scroll;
    var str = "height=" + height + ",width=" + width;

    if (window.screen) {
        var ah = screen.availHeight - 30;
        var aw = screen.availWidth - 10;
        var xc = (aw - width) / 2;
        var yc = (ah - height) / 2;

        str += ",left=" + xc + ",NSscreenX=" + xc;
        str += ",top=" + yc + ",NSscreenY=" + yc;
        str += ",toolbar=no,location=no,status=no,menubar=no,resizable=yes,scrollbars=" + scrollbar;
    }

    var win = window.open(url, name, str);

    inwoo.view.__popupList.push(win);

    return win;
};

inwoo.view.closePopupAll = function() {
    for (var i = 0; i < inwoo.view.__popupList.length; i++) {
        var win = inwoo.view.__popupList.pop();
        try { win.close(); } catch(e) {}
    }
};


inwoo.view.checkLoadingVM = function(arrVM) {
    for (var idx in arrVM) {
        if (arrVM[idx] == null) return false;
    }

    return true;
};