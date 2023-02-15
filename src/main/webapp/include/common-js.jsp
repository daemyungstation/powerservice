<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!-- CSS -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/script/kendoui/styles/kendo.common.min.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/script/kendoui/styles/kendo.silver.min.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/script/kendoui/plugins/menu.ex/kendo.menu.ex.css">

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/theme/css/common.css" />
<!-- //CSS -->

<!-- Script -->

<script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery/jquery-ui.min.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/dexteditor/js/dext5editor.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/script/util/json2/json2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/util/amplifyjs/amplify.min-1.1.2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/util/momentjs/moment-with-langs.min-2.5.1.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/script/kendoui/js/kendo.custom.min.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/kendoui/js/cultures/kendo.culture.ko-KR.min.js"></script>
<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/script/kendoui/js/kendo.timezones.min.js"></script> --%>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/kendoui/plugins/menu.ex/kendo.menu.ex.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/script/knockout/knockout-custom-3.3.0.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/knockout/knockout.mapping.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/knockout/knockout-kendo.min-0.9.3.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/script/inwoo/custom/inwoo.config.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/inwoo/custom/inwoo.popup.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/inwoo/custom/inwoo.model.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/script/inwoo/inwoo.core.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/inwoo/inwoo.core.screen-data.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/inwoo/inwoo.kendo.viewmodel.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/inwoo/inwoo.kendo.web.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/inwoo/inwoo.session.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/inwoo/inwoo.view.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/script/common.js"></script>

<!-- //Script -->
<script type="text/javascript">
    // context path
    inwoo.config.contextRoot = "${pageContext.request.contextPath}";
    inwoo.config.imagePath = "theme";

    // upload url
    inwoo.config.fileSaveUrl = inwoo.config.contextRoot + "/file/insert";
    inwoo.config.fileRemoveUrl = inwoo.config.contextRoot + "/file/delete";
    inwoo.config.fileRemoveRealUrl = inwoo.config.contextRoot + "/file/delete/real";


    // CSRF Protection 추가
    inwoo.config.csrfToken = "${csrf_token}";

    // Initialize kendo Globalization
    kendo.culture("ko-KR");
    moment.lang("ko-KR");

</script>
