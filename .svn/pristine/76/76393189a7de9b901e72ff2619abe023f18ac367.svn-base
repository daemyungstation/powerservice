<%@ page contentType="text/html;charset=euc-kr" %>
<%@ page language="java"%>
<%@ page import="java.io.File"%>
<%@ page import="java.util.Enumeration"%>
<%@ page import="com.oreilly.servlet.MultipartRequest"%>
<%@ page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@ page import="com.tobesoft.xplatform.data.ColumnHeader"%>
<%@ page import="com.tobesoft.xplatform.data.DataSet"%>
<%@ page import="com.tobesoft.xplatform.data.DataTypes"%>
<%@ page import="com.tobesoft.xplatform.data.PlatformData"%>
<%@ page import="com.tobesoft.xplatform.data.VariableList"%>
<%@ page import="com.tobesoft.xplatform.tx.HttpPlatformResponse"%>
<%@ page import="com.tobesoft.xplatform.tx.PlatformException"%>
<%
    String sHeader = request.getHeader("Content-Type");
    if (sHeader == null)
    {
        return;
    }
    request.setCharacterEncoding("utf-8");
    //String sRealPath = request.getSession().getServletContext().getRealPath("/");
    //String sPath     = request.getParameter("PATH");
    //String sUpPath   = sRealPath + sPath;
    //String sUpPath   = "C:/eGovFrame-3.2/workspace/powerservice/src/main/webapp/EMMA/attach_file/mmsmt";
    String sUpPath   = "/app/EMMA/attach_file/mmsmt";
    System.out.println("sUpPath === :"+sUpPath);
    int    nMaxSize  = 500 * 1024 * 1024; // 최대 업로드 파일 크기 500MB(메가)로 제한

    PlatformData resData    = new PlatformData();
    VariableList resVarList = resData.getVariableList();

    String sMsg = " A ";
    try
    {
        MultipartRequest multi = new MultipartRequest(request, sUpPath, nMaxSize, "utf-8", new DefaultFileRenamePolicy());
        Enumeration files      = multi.getFileNames();

        sMsg += "B ";
        DataSet ds = new DataSet("Dataset00");
        ds.addColumn(new ColumnHeader("fileName", DataTypes.STRING));
        ds.addColumn(new ColumnHeader("fileType", DataTypes.STRING));
        ds.addColumn(new ColumnHeader("fileSize", DataTypes.LONG));

        sMsg += "C ";
        String sFName = "";
        String sName  = "";
        String stype  = "";
        File   f      = null;

        while (files.hasMoreElements())
        {
            sMsg += "D ";
            sName  = (String)files.nextElement();
            sFName = multi.getFilesystemName(sName);
            stype  = multi.getContentType(sName);
            int nRow = ds.newRow();
            ds.set(nRow, "fileName", sFName);
            ds.set(nRow, "fileType", stype);

            f = multi.getFile(sName);
            if (f != null)
            {
                ds.set(nRow, "fileSize", f.length());
            }
            sMsg += nRow +" ";
        }

        resData.addDataSet(ds);
        resVarList.add("ErrorCode", 200);
        resVarList.add("ErrorMsg", sUpPath+"/"+sFName);
    }
    catch (Exception e)
    {
        resVarList.add("ErrorCode", 500);
        resVarList.add("ErrorMsg", sMsg + " " + e);
    }

    HttpPlatformResponse res = new HttpPlatformResponse(response);
    res.setData(resData);
    res.sendData();
%>
