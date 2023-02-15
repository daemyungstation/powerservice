// JavaScript Document

/* LNB  */////////////////////////////////////////////////////////
$(document).ready(function() {
// 메뉴 클릭 액션
    $("#nav > li:not(.lnb_sub)").on("click", function(e) {
        var isActive = $(this).hasClass("active");

        if (!isActive) {
        // hide any open menus and remove all other classes
        $(".lnb_sub").slideUp(350);
        $("#nav li").removeClass("active");

        // open our new menu and add the open class
        $(this).next().slideDown(350);
        $(this).addClass("active");

        // 대분류메뉴 클릭시 메뉴펼침
        var splitter = $("#admin-splitter").data("kendoSplitter");
        splitter.expand(".k-pane:first");
    }
});

 // 서브메뉴 클릭 액션
    $(".lnb_sub > ul > li").on("click", function(e) {
    var isActive = $(this).hasClass("active");

        if (!isActive) {
        // remove all other classes
        $(".lnb_sub li").removeClass("active");

        // add the open class
        $(this).addClass("active");
        }
    });
});


/* Sub Tab 순차적 위치 배열 */////////////////////////////////////////////////////////
$(document).ready(function() {
    $(".tab_sub li").each(function(index) {
         $(this).css({left:index * 160 + 1});
    });
    $(".tab_sub2 li").each(function(index) {
         $(this).css({left:index * 300 + 1});
    });
});

/* login input value focus시 내용사라짐  */////////////////////////////////////////////////////////
function clearText(thefield) {
      if (thefield.defaultValue==thefield.value) {
        thefield.value="";
      }
    }

function defaultText(thefield) {
      if (thefield.value=="") {
         thefield.value=thefield.defaultValue;
      }
    }

/* 왼쪽 메뉴 접혔을 시, 툴팁표시 */////////////////////////////////////////////////////////
function setNavMajorTitle() {
    $(".nav-major").each(function(i,e) {
        var sMajorTitle = $(this).text();
        $(this).attr("title",sMajorTitle);
    });
}