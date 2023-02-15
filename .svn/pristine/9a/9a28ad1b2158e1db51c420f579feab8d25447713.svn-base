<%@page import="com.dext5.DEXT5Handler"%>
<%
    String _allowFileExt = "gif, jpg, jpeg, png, bmp, wmv, asf, swf, avi, mpg, mpeg, mp4";
    int upload_max_size = 2147483647;

    String idx = request.getSession().getAttribute("ids") == null ? "" : request.getSession().getAttribute("ids").toString();
    DEXT5Handler dext5 = new DEXT5Handler();

    dext5.SetDebugMode(true);

    String result = dext5.DEXTProcess(request, response, application, _allowFileExt, upload_max_size);

    result = result.replace("/dexteditor/img", "/powerservice/dexteditor/img");
    out.print(result);
    System.out.println(result);

    // 파일 저장 경로 (물리적 경로)
    //if(dext5.LastSaveFile().length() > 0) {
    //	System.out.println("save file : [" + dext5.LastSaveFile() + "]");
    //}

    // 파일 저장 경로 (WEB URL)
    //if(dext5.LastSaveUrl().length() > 0) {
    //System.out.println("save url : [" + dext5.LastSaveUrl() + "]");
    //}

    // 에러 Message
    //if(dext5.LastErrorMessage().length() > 0) {
    //	System.out.println("DEXT5 Handler Error : [" + dext5.LastErrorMessage() + "]");
    //}
%>