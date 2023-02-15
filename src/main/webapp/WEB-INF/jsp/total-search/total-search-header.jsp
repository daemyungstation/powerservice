<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!-- Search -->
<form:form data-bind="with: searchParam">
    <ul class="total_schbox">
        <li class="total_select">
            <div id="toggle-search" data-bind="click: toggle, text: srch_typ_nm" class="total_selectcon"></div>
            <ul data-bind="inwooSlideWindow: {isOpen: isOpen, anchor: 'toggle-search'}, foreach: srchtypcdList" class="total_selectlist">
                <li data-bind="value: value, text: text, click: $parent.selectSrchTyp"></li>
            </ul>
        </li>
        <li>
            <input id="srch_word" type="text" data-bind="value: srch_word, enterKey: goSearch" class="bluebox"
                   onfocus="if (this.value == this.title) this.value = '';"
                   onblur="if (this.value == '') this.value = this.title;"
                   title="검색할 키워드를 입력하세요" />
        </li>
        <li><button data-bind="click: goSearch" type="submit" title="검색" class="bt_tseche"><div class="rb"></div></button></li>
    </ul>
</form:form>
<!-- //Search -->

<!-- 검색결과 표시 -->
<ul class="ts_middle">

    <!-- 검색결과가 있는 경우 -->
    <li data-bind="visible: total() > 0" class="txt">
        <span class="fc_blue fb">"<span data-bind="text: searchParam().srch_word()"></span>"</span>에 대한 검색결과는 총&nbsp;<span class="fb" data-bind="text: total"></span>건 입니다.
    </li>
    <!-- //검색결과가 있는 경우 -->

    <!-- 검색결과가 없는 경우 -->
    <li data-bind="visible: total() == 0" class="bu_exclamation">
    </li>
    <li data-bind="visible: total() == 0" class="txt">
        <span class="fc_blue fb">"<span data-bind="text: searchParam().srch_word()"></span>"</span>에 대한 검색결과가 없습니다.
    </li>
    <!-- //검색결과가 없는 경우 -->

</ul>
<!-- //검색결과 표시 -->
