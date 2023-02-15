<%@page pageEncoding="UTF-8"%>

<div class="total_left_con">

<!-- Left Menu -->
<ul id="total-search-tab" class="ts_left">
    <li data-bind="click: $root.selectLeftMenu.bind($data, ''),				css: {active: srchTypView() == ''}"				class="menu">전체 검색</li>
    <li data-bind="click: $root.selectLeftMenu.bind($data, 'faq'),			css: {active: srchTypView() == 'faq'}"			class="menu">FAQ</li>
    <li data-bind="click: $root.selectLeftMenu.bind($data, 'ancm_mtr'),		css: {active: srchTypView() == 'ancm_mtr'}" 	class="menu">공지사항</li>
    <li data-bind="click: $root.selectLeftMenu.bind($data, 'nobd'),			css: {active: srchTypView() == 'nobd'}"			class="menu">게시판</li>
    <li data-bind="click: $root.selectLeftMenu.bind($data, 'cons_scrt'),	css: {active: srchTypView() == 'cons_scrt'}"	class="menu">상담스크립트</li>
    <li id="blank" style="height:10px;"></li>
    <li class="line"></li>
</ul>
<!-- //Left Menu -->

<!-- Left Option -->
<ul class="ts_select" >
    <li>
        <!-- 유용한페이지 -->
        <div data-bind="with: searchUsefulPageParam">
            <input data-bind="kendoDropDownList: {value: useful_page, dataTextField: 'bkmk_nm', dataValueField: 'bkmk_addr', optionLabel: '유용한페이지', autoBind: false, select: onSelect,
                                                  dataSource: inwoo.kendo.dropdownlist.dataSource(inwoo.config.contextRoot + '/total-sch/list-user/useful', ko.toJS($root.favoritesViewModel.searchParam()))}" class="wp140" />
        </div>
        <!-- //유용한페이지 -->
    </li>
    <li>
        <!-- 관련페이지 -->
        <div data-bind="with: searchRelatedPageParam">
            <input data-bind="kendoDropDownList: {value: related_page, dataTextField: 'bkmk_nm', dataValueField: 'bkmk_addr', optionLabel: '관련페이지', autoBind: false, select: onSelect,
                                                  dataSource: inwoo.kendo.dropdownlist.dataSource(inwoo.config.contextRoot + '/total-sch/list-user/related', ko.toJS($root.favoritesViewModel.searchParam()))}" class="wp140" />
        </div>
        <!-- //관련페이지 -->
    </li>
</ul>
<!-- //Left Option -->

<!-- 즐겨찾기 -->
<div class="fav_con" data-bind="with: favoritesViewModel">
    <div class="search_favorites_hgroup">
        <h4 class="ft_l ">즐겨찾기</h4>

        <span class="ft_r">
            <button data-bind="click: openFavoritesPopup" class="btn_gray1"><span class="k-icon k-i-pencil"></span>관리<div class="rb"></div></button>
        </span>
    </div>
    <div id="blank" style="height:4px;"></div>
    <div data-bind="kendoListView: favoritesListviewConfig" class="fav_con"></div>

    <script id="favorites-listview-template" type="text/html">
        <a href="#" data-bind="click: openLeftPopup.bind($data, bkmk_addr, scrn_widt_vl, scrn_hght_vl)"><span data-bind="text: page_indx + '.  '"></span><span data-bind="text: bkmk_nm"></span></a><br>
    </script>
</div>
<!-- //즐겨찾기 -->

</div>

<script type="text/javascript">

// LeftMenu 탭 클릭
$("#total-search-tab").find("li.menu").click(function() {
    var $Self = $(this);

    $Self.siblings().removeClass("active");
    $Self.addClass("active");
});

/**
 * openLeftPopup
 * 유용한페이지, 관련페이지, 즐겨찾기 팝업
 *
 * @param   : URL
 * @return  : 없음.
 * @author  : 이희철
 * @date    : 2015/04/01
 */
openLeftPopup = function(poUrl, poWidth, poHeight) {

    var sUrl = poUrl;
    var sWidth = poWidth;
    var sHeight = poHeight;

    if (sUrl == null || sUrl == "") {
        return;
    }
    if (sHeight == null || sHeight == "") {
        sHeight = "500";
    }
    if (sWidth == null || sWidth == "") {
        sWidth = "1000";
    }

    if (sUrl.indexOf("http://") < 0) {
        sUrl = "http://" + sUrl;
    }

    if (sUrl.length <= 7 || sUrl.indexOf(" ") >= 0 || sUrl.indexOf(",") >= 0) {
        alert("URL이 올바르지 않습니다.");
        return;
    }

    var shape = "width=" + sWidth + ",height=" + sHeight + ",resizable=yes";
    window.open(sUrl, "_blank", shape);
};


</script>
