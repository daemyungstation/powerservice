// JavaScript Document

$(document).ready(function(){
/* Header Search Droop */////////////////////////////////////////////////////////
	$(".select_con").slideUp();
//	$(".s_select").on("click",function(){
//		$('.select_con').slideDown(100,function(){
//         });
//	});
//	$(".select_con li").on("click",function(){
//		$(".select_con").slideUp(100);
//	});
	$(".s_select").click(function(){
	$(".select_con").slideToggle(100) 
    })
	$(".select_con").on("mouseleave",function(){
		$(".select_con").slideUp(100);
	});

/* select_seat Droop */////////////////////////////////////////////////////////
	$(".select_seat").hide();
	$(".gnb .g9").click(function(){
	$(".select_seat").slideToggle(100) 
    })
	$("#btn_seat").on("click",function(){
		$(".select_seat").slideUp(200);
	});
	$(".select_seat").on("mouseleave",function(){
		$(".select_seat").slideUp(100);
	});

/* QuickMenu Droop */////////////////////////////////////////////////////////
	$(".quickmenu_con").hide();
	$(".quickmenu").click(function(){
	$(".quickmenu_con").slideToggle(100) 
    })
	$(".quickmenu_con").on("mouseleave",function(){
		$(".quickmenu_con").slideUp(100);
	});
});

/* tab change	*/
$(document).ready(function() {
    var sec_div = $("div[id^='sec_']");
    var tab_li = sec_div.find(".tab_con li");

    sec_div.find(".content_middle, .btn").hide();
    sec_div.find(".content_middle:first, .btn:first").show();

    tab_li.click(function() {
    	var self = $(this);
        var parent = self.parents(".content_outer");

        self.siblings().removeClass("active");
        self.addClass("active");
        parent.find(".content_middle").hide();
        parent.find(".btn").hide();

        var activeTab = $(this).attr("tcon");
        $("#" + activeTab).show();
        var activeBtn = $(this).attr("tbn");
        $("#" + activeBtn).show();
	});
});

/* consult_middle Auto Max-Width  */////////////////////////////////////////////////////////
$(document).ready(function(){
	$(".consult_middle").css("width", $(".consult_con").width() - 40 );
	$(window).resize(function() {
    	$(".consult_middle").css("width", $(".consult_con").width() - 40 );
	});
}); 

/* footer bottom fix  */////////////////////////////////////////////////////////
// #1
//function FooterBottom(){
//    var height = $(window).height();
//    $('#footer').html(height);    // Display the height
//};
//$(document).ready(FooterBottom);    // When the page first loads
//$(window).resize(FooterBottom);     // When the browser changes size

// #2
//$(document).ready(function(){
//	$("#footer").css("top", $(document).height() - 32 );
//	$(window).resize(function() {
//    	$("#footer").css("top", $(document).height() - 32 );
//	});
//});

// #3
//$(function() { 
//function  resizeWindow() { 
//var winH = $(window).height(); 
//$("#footer").css({top:winH}); 
//} 
//$(window).resize(resizeWindow); 
//}) ;
