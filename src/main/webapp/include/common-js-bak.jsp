<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!-- CSS -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/script/kendoui/styles/kendo.common.min.css">
<link rel="stylesheet" type="text/css"  href="${pageContext.request.contextPath}/script/kendoui/styles/kendo.uniform.min.css">
<link rel="stylesheet" type="text/css"  href="${pageContext.request.contextPath}/script/kendoui/plugins/menu.ex/kendo.menu.ex.css">

<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css-bak/style.css">
<!-- //CSS -->

<!-- Script -->

<script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery/jquery-1.10.2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery/jquery-ui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/util/json2.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/script/util/momentjs/moment-with-langs.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/script/kendoui/js/kendo.web.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/kendoui/js/cultures/kendo.culture.ko-KR.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/kendoui/js/kendo.timezones.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/kendoui/plugins/menu.ex/kendo.menu.ex.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/script/knockout/knockout-3.0.0.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/knockout/knockout.mapping.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/knockout/knockout-kendo.min.js"></script>

<!--[if lte IE 8]><script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery/flot/excanvas.min.js"></script><![endif]-->
<script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery/flot/jquery.flot.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery/flot/jquery.flot.resize.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery/flot/jquery.flot.categories.js"></script>


<script type="text/javascript" src="${pageContext.request.contextPath}/script/inwoo/custom/inwoo.config.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/inwoo/custom/inwoo.popup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/inwoo/custom/inwoo.model.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/inwoo/custom/inwoo.softphone.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/script/inwoo/inwoo.core.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/inwoo/inwoo.core.screen-data.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/inwoo/inwoo.kendo.viewmodel.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/inwoo/inwoo.kendo.web.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/inwoo/inwoo.session.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/inwoo/inwoo.view.js"></script>
<!-- //Script -->

<script type="text/javascript">
    // context path
    inwoo.config.contextRoot = "${pageContext.request.contextPath}";

    // upload url
    inwoo.config.fileSaveUrl = inwoo.config.contextRoot + "/file/insert";
    inwoo.config.fileRemoveUrl = inwoo.config.contextRoot + "/file/delete";

    // Initialize kendo Globalization
    kendo.culture("ko-KR");

    moment.lang("ko-KR");

</script>
