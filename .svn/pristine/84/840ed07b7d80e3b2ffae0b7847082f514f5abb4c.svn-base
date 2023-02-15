/*
 * (@)# ExcelUtils.java
 *
 * @author 정용재
 * @version 1.0
 * @date 2015. 9. 16
 * Copyright (c) 2013 by Inwoo tech inc, KOREA. All Rights Reserved.
 *
 * http://www.inwoo.co.kr
 *
 * NOTICE! This software is the confidential and proprietary
 * information of
 * Inwoo Tech Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms
 * of the license agreement you
 * entered into with Inwoo Tech Inc.
 *
 */
package powerservice.common.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;

import powerservice.business.bean.UserSession;
import powerservice.core.util.SessionUtils;

/**
 * Excel 관련 공통 유틸 Copyright (c) 2013 Company Inwoo Tech Inc.
 *
 * @author 정용재
 * @version 1.0
 * @date 2015. 9. 16.
 * @프로그램ID ExcelUtils
 */
public class ExcelUtils {
    /**
     *엑셀 다운로드 준비를 한다.
     * @ctx 				ServletContext	서블렛 컨테스트
     * @mFileData			Map				파일정보를 생성하여 채운다.
     * @psTemplateFileName	String			템플릿파일명
     * @return 				String			오류가 있을경우 오류코드명을 리턴, 없을 경우는 null을 리턴
     * @throws				Exception
     */
   public static String prepareExcelDown(ServletContext ctx, Map<String, String> mFileData, String psTemplateFileName) throws Exception {
        return prepareExcelDown(ctx, mFileData, psTemplateFileName, null);
   }
   public static String prepareExcelDown(ServletContext ctx, Map<String, String> mFileData, String psTemplateFileName, Logger LOGGER) throws Exception {
       File oTargetPathFile = null; // 작업 폴더
       File[] oFileList = null; // 생성 파일 리스트

       // 세션 확인
       UserSession oUser = (UserSession) SessionUtils.getLoginUser();
       if (oUser == null) {
           return "error_session";
       }

       String sUserId = oUser.getUserid();
       String sRealPath = ctx.getRealPath("");
       String sTemplateFileName = sRealPath + "/WEB-INF/xls/" + psTemplateFileName + ".xlsx"; // 템플릿 파일
       String sTargetPath = sRealPath + "/WEB-INF/temp/" + sUserId; // 사용자 작업 임시 폴더

       if (LOGGER != null) {
           LOGGER.info("###### EXCEL - " + oUser.getLoginid() + " ###### START");
           LOGGER.info("###### EXCEL - " + oUser.getLoginid() + " ###### user_nm [" + oUser.getUsernm() + "]");
           LOGGER.info("###### EXCEL - " + oUser.getLoginid() + " ###### template [" + sTemplateFileName + "]");
       }

       mFileData.put("real_path", sRealPath);
       mFileData.put("template_file_name", sTemplateFileName);
       mFileData.put("target_path", sTargetPath);
       mFileData.put("target_file_name", psTemplateFileName + "_" + DateUtil.currentTimeToString(DateUtil.SDF_YYYYMMDD) + ".xlsx");

       // 템플릿 파일 확인
       if (!(new File(sTemplateFileName)).exists()) {
           if (LOGGER != null) {
               LOGGER.error("###### EXCEL - " + oUser.getLoginid() + " ###### ERROR [tempfile]");
           }

           return "error_tempfile";
       }

       // 사용자 작업 임시 폴더 생성
       oTargetPathFile = new File(sTargetPath);
       if (!oTargetPathFile.getParentFile().exists()) {
           oTargetPathFile.getParentFile().mkdir();
       }
       if (!oTargetPathFile.exists()) {
           oTargetPathFile.mkdir();
       }

       // 이전 생성 엑셀 파일 삭제
       oFileList = oTargetPathFile.listFiles();
       if (oFileList != null) {
           for (int i = 0; i < oFileList.length; i++) {
               oFileList[i].delete();
           }
       }
       // 이전 생성 압축 파일 삭제
       oTargetPathFile = new File(sTargetPath + "/list");
       if (oTargetPathFile != null && oTargetPathFile.exists()) {
           oFileList = oTargetPathFile.listFiles();
           if (oFileList != null) {
               for (int i = 0; i < oFileList.length; i++) {
                   oFileList[i].delete();
               }
           }
           oTargetPathFile.delete();
       }

       return null;
   }

   /**
    * 엑셀 파일 전송을 한다.
    * @mFileData			Map					파일정보를 생성하여 채운다.
    * @mExcelData			Map					엑셀에 담을 데이터
    * @poResponse			HttpServletResponse
    * @return 				String				오류가 있을경우 오류코드명을 리턴, 없을 경우는 null을 리턴
    * @throws				Exception
    */
   public static boolean transFile(HttpSession poSession, Map<String, String> mFileData, Map<String, Object> mExcelData, HttpServletResponse poResponse) throws Exception {
       boolean bResult = false;

       String psTemplateFileName = mFileData.get("template_file_name");

       String psTargetPath = mFileData.get("target_path");
       String psTargetFileName = mFileData.get("target_file_name");

       // 엑셀 파일 생성
       XLSTransformer oTransformer = new XLSTransformer();
       oTransformer.transformXLS(psTemplateFileName, mExcelData, psTargetPath + "/" + psTargetFileName);

       File oTransFile = null; // 전송 파일
       FileInputStream oFileInputStream = null;
       ServletOutputStream oServletOutputStream = null;

       File oTargetPathFile = null; // 작업 폴더
       File[] oFileList = null; // 생성 파일 리스트

       try {
           // 생성 엑셀 파일 1건이 있는 경우
           oTransFile = new File(psTargetPath + "/" + psTargetFileName);
           if (oTransFile != null && oTransFile.exists()) { // 엑셀 파일 1건
               // 엑셀 파일 헤더 설정
               StringBuffer sContentDisposition = new StringBuffer();
               sContentDisposition.append("attachment;fileName=\"");
               sContentDisposition.append(URLEncoder.encode(psTargetFileName, "UTF-8").replaceAll("\\+", "%20"));
               sContentDisposition.append("\";");
               poResponse.setHeader("Content-Disposition", sContentDisposition.toString());
               poResponse.setContentType("application/x-msexcel");
               poResponse.setContentLength(((Long) oTransFile.length()).intValue());

           // 생성 엑셀 파일 1건이 없는 경우
           } else {
               // 사용자 작업 리스트 폴더 생성 파일 확인
               oTargetPathFile = new File(psTargetPath + "/list");
               if (oTargetPathFile != null && oTargetPathFile.exists()) {
                   oFileList = oTargetPathFile.listFiles();
               }

               if (oFileList != null && oFileList.length > 0) {
                   // 생성 파일명 변경
                   psTargetFileName = psTargetFileName.replace(".xlsx", ".zip").replace(".xls", ".zip");

                   // 압축 파일 생성
                   oTransFile = new File(psTargetPath + "/" + psTargetFileName);
                   OutputStream oFileOutputStream = null;
                   try {
                       oFileOutputStream = new FileOutputStream(oTransFile);
                       (new CompressUtil()).zip(oTargetPathFile, oFileOutputStream, Charset.defaultCharset().name(), false);
                       oFileOutputStream.close();
                       oFileOutputStream = null;
                   } catch (Exception exception) {
                       exception.printStackTrace();
                       poSession.removeAttribute("EXCEL_SESSION");
                       throw exception;
                   } finally {
                       if (oFileOutputStream != null) {
                           oFileOutputStream.close();
                       }
                   }

                   // 압축 파일 헤더 설정
                   StringBuffer sContentDisposition = new StringBuffer();
                   sContentDisposition.append("attachment;fileName=\"");
                   sContentDisposition.append(URLEncoder.encode(psTargetFileName, "UTF-8").replaceAll("\\+", "%20"));
                   sContentDisposition.append("\";");
                   poResponse.setHeader("Content-Disposition", sContentDisposition.toString());
                   poResponse.setContentType("application/octet-stream");
                   poResponse.setContentLength(((Long) oTransFile.length()).intValue());
               }
           }

           if (oTransFile != null && oTransFile.exists()) {
               // 파일 전송
               oFileInputStream = new FileInputStream(oTransFile);
               oServletOutputStream = poResponse.getOutputStream();
               int nReadSize = 0;
               byte[] bytes = new byte[2048];
               while ((nReadSize = oFileInputStream.read(bytes)) != -1) {
                   oServletOutputStream.write(bytes, 0, nReadSize);
               }

               // 자원 해제
               oServletOutputStream.flush();
               oServletOutputStream.close();
               oServletOutputStream = null;
               oFileInputStream.close();
               oFileInputStream = null;

               bResult = true;
           }
       } catch (Exception exception) {
           exception.printStackTrace();
           poSession.removeAttribute("EXCEL_SESSION");
           throw exception;
       } finally {
           if (oServletOutputStream != null) {
               oServletOutputStream.close();
           }
           if (oFileInputStream != null) {
               oFileInputStream.close();
           }

           // 작업 파일 삭제
           if (oTransFile != null && oTransFile.exists()) {
               oTransFile.delete();
           }
           // 이전 생성 엑셀 파일 삭제
           oTargetPathFile = new File(psTargetPath);
           if (oTargetPathFile != null && oTargetPathFile.exists()) {
               oFileList = oTargetPathFile.listFiles();
               if (oFileList != null) {
                   for (int i = 0; i < oFileList.length; i++) {
                       oFileList[i].delete();
                   }
               }

               oTargetPathFile.delete();
           }
           // 이전 생성 압축 파일 삭제
           oTargetPathFile = new File(psTargetPath + "/list");
           if (oTargetPathFile != null && oTargetPathFile.exists()) {
               oFileList = oTargetPathFile.listFiles();
               if (oFileList != null) {
                   for (int i = 0; i < oFileList.length; i++) {
                       oFileList[i].delete();
                   }
               }
               oTargetPathFile.delete();
           }
       }

       return bResult;
   }

   /**
    * 멀티시트 엑셀파일을 전송한다.
    * ex) user.jsp, UserController참조
    * @param poSession
    * @param mFileData		파일 정보
    * @param lsExcelData	시트별 데이터정보
    * @param lsSheetNm		시트별 제목
    * @param poResponse
    * @return
    * @throws Exception
    */
   public static boolean transFileMultiSheet(HttpSession poSession, Map<String, String> mFileData, List<Map<String, Object>> lsExcelData, List<String> lsSheetNm, HttpServletResponse poResponse) throws Exception {
       boolean bResult = false;

       String psTemplateFileName = mFileData.get("template_file_name");

       String psTargetPath = mFileData.get("target_path");
       String psTargetFileName = mFileData.get("target_file_name");

       // 엑셀 파일 생성
       XLSTransformer oTransformer = new XLSTransformer();

       InputStream is = new BufferedInputStream(new FileInputStream(psTemplateFileName));
       Workbook resultWorkbook = oTransformer.transformMultipleSheetsList(is, lsExcelData, lsSheetNm, "data", new HashMap<String, Object>(), 0);

       FileOutputStream os = new FileOutputStream(psTargetPath + "/" + psTargetFileName);
       resultWorkbook.write(os);
       os.close();
       is.close();

       File oTransFile = null; // 전송 파일
       FileInputStream oFileInputStream = null;
       ServletOutputStream oServletOutputStream = null;

       File oTargetPathFile = null; // 작업 폴더
       File[] oFileList = null; // 생성 파일 리스트

       try {
           // 생성 엑셀 파일 1건이 있는 경우
           oTransFile = new File(psTargetPath + "/" + psTargetFileName);
           if (oTransFile != null && oTransFile.exists()) { // 엑셀 파일 1건
               // 엑셀 파일 헤더 설정
               StringBuffer sContentDisposition = new StringBuffer();
               sContentDisposition.append("attachment;fileName=\"");
               sContentDisposition.append(URLEncoder.encode(psTargetFileName, "UTF-8").replaceAll("\\+", "%20"));
               sContentDisposition.append("\";");
               poResponse.setHeader("Content-Disposition", sContentDisposition.toString());
               poResponse.setContentType("application/x-msexcel");
               poResponse.setContentLength(((Long) oTransFile.length()).intValue());

           // 생성 엑셀 파일 1건이 없는 경우
           } else {
               // 사용자 작업 리스트 폴더 생성 파일 확인
               oTargetPathFile = new File(psTargetPath + "/list");
               if (oTargetPathFile != null && oTargetPathFile.exists()) {
                   oFileList = oTargetPathFile.listFiles();
               }

               if (oFileList != null && oFileList.length > 0) {
                   // 생성 파일명 변경
                   psTargetFileName = psTargetFileName.replace(".xlsx", ".zip").replace(".xls", ".zip");

                   // 압축 파일 생성
                   oTransFile = new File(psTargetPath + "/" + psTargetFileName);
                   OutputStream oFileOutputStream = null;
                   try {
                       oFileOutputStream = new FileOutputStream(oTransFile);
                       (new CompressUtil()).zip(oTargetPathFile, oFileOutputStream, Charset.defaultCharset().name(), false);
                       oFileOutputStream.close();
                       oFileOutputStream = null;
                   } catch (Exception exception) {
                       exception.printStackTrace();
                       poSession.removeAttribute("EXCEL_SESSION");
                       throw exception;
                   } finally {
                       if (oFileOutputStream != null) {
                           oFileOutputStream.close();
                       }
                   }

                   // 압축 파일 헤더 설정
                   StringBuffer sContentDisposition = new StringBuffer();
                   sContentDisposition.append("attachment;fileName=\"");
                   sContentDisposition.append(URLEncoder.encode(psTargetFileName, "UTF-8").replaceAll("\\+", "%20"));
                   sContentDisposition.append("\";");
                   poResponse.setHeader("Content-Disposition", sContentDisposition.toString());
                   poResponse.setContentType("application/octet-stream");
                   poResponse.setContentLength(((Long) oTransFile.length()).intValue());
               }
           }

           if (oTransFile != null && oTransFile.exists()) {
               // 파일 전송
               oFileInputStream = new FileInputStream(oTransFile);
               oServletOutputStream = poResponse.getOutputStream();
               int nReadSize = 0;
               byte[] bytes = new byte[2048];
               while ((nReadSize = oFileInputStream.read(bytes)) != -1) {
                   oServletOutputStream.write(bytes, 0, nReadSize);
               }

               // 자원 해제
               oServletOutputStream.flush();
               oServletOutputStream.close();
               oServletOutputStream = null;
               oFileInputStream.close();
               oFileInputStream = null;

               bResult = true;
           }
       } catch (Exception exception) {
           exception.printStackTrace();
           poSession.removeAttribute("EXCEL_SESSION");
           throw exception;
       } finally {
           if (oServletOutputStream != null) {
               oServletOutputStream.close();
           }
           if (oFileInputStream != null) {
               oFileInputStream.close();
           }

           // 작업 파일 삭제
           if (oTransFile != null && oTransFile.exists()) {
               oTransFile.delete();
           }
           // 이전 생성 엑셀 파일 삭제
           oTargetPathFile = new File(psTargetPath);
           if (oTargetPathFile != null && oTargetPathFile.exists()) {
               oFileList = oTargetPathFile.listFiles();
               if (oFileList != null) {
                   for (int i = 0; i < oFileList.length; i++) {
                       oFileList[i].delete();
                   }
               }

               oTargetPathFile.delete();
           }
           // 이전 생성 압축 파일 삭제
           oTargetPathFile = new File(psTargetPath + "/list");
           if (oTargetPathFile != null && oTargetPathFile.exists()) {
               oFileList = oTargetPathFile.listFiles();
               if (oFileList != null) {
                   for (int i = 0; i < oFileList.length; i++) {
                       oFileList[i].delete();
                   }
               }
               oTargetPathFile.delete();
           }
       }

       return bResult;
   }
}
