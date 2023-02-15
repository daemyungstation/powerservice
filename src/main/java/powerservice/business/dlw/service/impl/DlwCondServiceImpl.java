/*
 * (@)# DlwCondServiceImpl.java
 *
 * @author 석민
 * @version 1.0
 * @date 2016/03/14
 * Copyright (c) 2016 by Inwoo tech inc, KOREA. All Rights Reserved.
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

package powerservice.business.dlw.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;

import powerservice.business.dlw.service.DlwCondService;
import powerservice.common.util.CommonUtils;

import com.ibm.icu.util.Calendar;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 대명라이프웨이 현황을 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 석민
 * @version 1.0
 * @date 2016/03/14
 * @프로그램ID DlwCondServiceImpl
 */
@Service
public class DlwCondServiceImpl extends EgovAbstractServiceImpl implements DlwCondService {

    @Resource
    public DlwCondDAO DlwCondDAO;

    /**
    *
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
   public List<Map<String, Object>> getClList(Map<String, ?> pmParam) throws Exception {
       return DlwCondDAO.getClList(pmParam);
   }

   /**
    *
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
   public String getInqCondQry(Map<String, ?> pmParam) throws Exception {
       return DlwCondDAO.getInqCondQry(pmParam);
   }

   /**
    * 종합 회원 현황 건수
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
   public int getCondMemCount(Map<String, ?> pmParam) throws Exception {
       return DlwCondDAO.getCondMemCount(pmParam);
   }

  /**
   * 종합 회원 현황
   *
   * @param pmParam 검색 조건
   * @return
   * @throws Exception
   */
  public List<Map<String, Object>> getCondMemList(Map<String, ?> pmParam) throws Exception {
      return DlwCondDAO.getCondMemList(pmParam);
  }

  /**
   * 종합 입금현황 건수
   *
   * @param pmParam 검색 조건
   * @return
   * @throws Exception
   */
  public int getPayCount(Map<String, ?> pmParam) throws Exception {
      return DlwCondDAO.getPayCount(pmParam);
  }

 /**
  * 종합 입금현황
  *
  * @param pmParam 검색 조건
  * @return
  * @throws Exception
  */
 public List<Map<String, Object>> getPayList(Map<String, ?> pmParam) throws Exception {
     return DlwCondDAO.getPayList(pmParam);
 }

 /**
  * 증서출력 구분
  *
  * @param pmParam 검색 조건
  * @return
  * @throws Exception
  */
 public List<Map<String, Object>> getCertfCond(Map<String, ?> pmParam) throws Exception {
     return DlwCondDAO.getCertfCond(pmParam);
 }

 /**
  * 연체현황 - 상품구분기준
  *
  * @param pmParam 검색 조건
  * @return
  * @throws Exception
  */
 public List<Map<String, Object>> getDelayPayByProd(Map<String, ?> pmParam) throws Exception {
     return DlwCondDAO.getDelayPayByProd(pmParam);
 }

 /**
  * 연체현황 - 전체실적(실납입)
  *
  * @param pmParam 검색 조건
  * @return
  * @throws Exception
  */
 public List<Map<String, Object>> getRealPay(Map<String, ?> pmParam) throws Exception {
     return DlwCondDAO.getRealPay(pmParam);
 }

 /**
  * 연체현황 - 채권구분기준
  *
  * @param pmParam 검색 조건
  * @return
  * @throws Exception
  */
 public List<Map<String, Object>> getDelayPayByBond(Map<String, ?> pmParam) throws Exception {
     return DlwCondDAO.getDelayPayByBond(pmParam);
 }

 /**
  * 실적현황
  *
  * @param pmParam 검색 조건
  * @return
  * @throws Exception
  */
 public List<Map<String, Object>> getAcrsProd(Map<String, ?> pmParam) throws Exception {
     return DlwCondDAO.getAcrsProd(pmParam);
 }

 /**
  * 실적현황 - batch 데이터
  *
  * @param pmParam 검색 조건
  * @return
  * @throws Exception
  */
 public List<Map<String, Object>> getAcrsProdByBatch(Map<String, ?> pmParam) throws Exception {
     return DlwCondDAO.getAcrsProdByBatch(pmParam);
 }

 /**
  * 연체 데이터 삽입
  *
  * @param pmParam 검색 조건
  * @return
  * @throws Exception
  */
 public void insertUserProdInfoMonth() throws Exception {
     Map<String, ?> pmParam = null;
     DlwCondDAO.insertUserProdInfoMonth(pmParam);
 }

 /**
  * 실적 데이터 삽입
  *
  * @param pmParam 검색 조건
  * @return
  * @throws Exception
  */
 public void insertUserProdInfo() throws Exception {
     Map<String, ?> pmParam = null;
     DlwCondDAO.insertUserProdInfo(pmParam);
 }

 /**
  * 연체현황 기준월 데이터
  *
  * @param pmParam 검색 조건
  * @return
  * @throws Exception
  */
 public List<Map<String, Object>> getBasMonth(Map<String, ?> pmParam) throws Exception {
     return DlwCondDAO.getBasMonth(pmParam);
 }
 /**
  * 입금현황(new) - 회차별 건수
  *
  * @param pmParam 검색 조건
  * @return
  * @throws Exception
  */
 public int getPayMonthCount(Map<String, ?> pmParam) throws Exception {
     return DlwCondDAO.getPayMonthCount(pmParam);
 }

 /**
  * 입금현황(new) - 회차별
  *
  * @param pmParam 검색 조건
  * @return
  * @throws Exception
  */
 public List<Map<String, Object>> getPayMonthList(Map<String, ?> pmParam) throws Exception {
     return DlwCondDAO.getPayMonthList(pmParam);
 }
 /**
  * 입금현황(new) - 회원별 건수
  *
  * @param pmParam 검색 조건
  * @return
  * @throws Exception
  */
 public int getPayMonthByMemCount(Map<String, ?> pmParam) throws Exception {
     return DlwCondDAO.getPayMonthByMemCount(pmParam);
 }

 /**
  * 입금현황(new) - 회원별
  *
  * @param pmParam 검색 조건
  * @return
  * @throws Exception
  */
 public List<Map<String, Object>> getPayMonthByMemList(Map<String, ?> pmParam) throws Exception {
     CommonUtils.printLog(pmParam);
     return DlwCondDAO.getPayMonthByMemList(pmParam);
 }

 /*
 public int insertMbidCsvFileMake() throws Exception {
	 Map<String, Object> hmParamTypeProc = new HashMap<String, Object>();
	 Map<String, Object> hmParamTypeA = new HashMap<String, Object>();
     Map<String, Object> hmParamTypeB = new HashMap<String, Object>();
     Map<String, Object> hmParamTypeC = new HashMap<String, Object>();

     // 에러코드및 메시지
     String szErrorCode = "0";
     String szErrorMsg = "OK";

     Connection connectionTypeA = null;
     Connection connectionTypeB = null;
     PreparedStatement preparedStatementTypeA = null ;
     PreparedStatement preparedStatementTypeB = null ;
     ResultSet rsTypeA = null;
     ResultSet rsTypeB = null;
     File sMakeFileDirectoryPath = null;

     try
     {
     	String sMbidFileParentFixPath = "";
     	final String A_TYPE_CSV_NAME_POSTFIX = "_A.csv";
     	final String B_TYPE_CSV_NAME_POSTFIX = "_B.csv";
     	final String C_TYPE_CSV_NAME_POSTFIX = "_C.csv";

     	String sOsName = System.getProperty("os.name").toLowerCase();
         if (sOsName.indexOf("windows") >= 0)
         {
         	sMbidFileParentFixPath = "C:\\media\\MBID\\"; // 이건 예제임. 실제 PATH가 아님. (rootPath : C:/)
         }
         else
         {
         	sMbidFileParentFixPath = "/media/MBID/"; // 이건 예제임. 실제 PATH가 아님. (rootPath : /app/)
         }

         Date d = new Date();
         SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");
         String sYYYYMMDD = sdf.format(d);
         String sYYYYMM   = sYYYYMMDD.substring(0, 6);

         sMakeFileDirectoryPath = new File(sMbidFileParentFixPath + sYYYYMM);
         if(sMakeFileDirectoryPath.exists() == false)
 		{
         	sMakeFileDirectoryPath.mkdirs();
 		}
         System.out.println("디렉토리 생성 완료");
         StringBuffer resultBuffer = new StringBuffer();

     	String sAccessClassName = EgovProperties.getProperty("Globals.Fix.Database.Access.DriverClassName");
     	String sAccessIp        = EgovProperties.getProperty("Globals.Fix.Database.Access.Ip");
     	String sAccessUserName  = EgovProperties.getProperty("Globals.Fix.Database.Access.UserName");
     	String sAccessPassword  = EgovProperties.getProperty("Globals.Fix.Database.Access.Password");

     	System.out.println("프로퍼티[1] :: " + sAccessClassName + "," + "프로퍼티[2] :: " + sAccessIp + "," +
     			           "프로퍼티[3] :: " + sAccessUserName + "," + "프로퍼티[4] :: " + sAccessPassword);


     	// A 타입 파일 작성 start
     	String sqlStatementTypeA =  "";
     	sqlStatementTypeA += "SELECT '회원번호,고유번호,회원명,생년월일,성별,지역,휴대전화번호,E메일,CI정상여부,SDP회원여부,가입년도,가입월,상품명,회원상태,최종납입회차,상조금액,결합금액,추가금액,연체회수,회원몰잔여금액,홈페이지여부,회원몰고객여부,마케팅허용여부,회원몰적립금액,회원몰사용금액' AS CSV_ROW_STRING FROM DUAL \n";
     	sqlStatementTypeA += "UNION ALL                                              \n";
     	sqlStatementTypeA += "SELECT REPLACE(ACCNT_NO    , ',','') || ',' ||         \n";
     	sqlStatementTypeA += "       REPLACE(MEM_NO      , ',','') || ',' ||         \n";
     	sqlStatementTypeA += "       REPLACE(MEM_NM      , ',','') || ',' ||         \n";
     	sqlStatementTypeA += "       REPLACE(BRTH_MON_DAY, ',','') || ',' ||         \n";
     	sqlStatementTypeA += "       REPLACE(SEX         , ',','') || ',' ||         \n";
     	sqlStatementTypeA += "       REPLACE(ADDR        , ',','') || ',' ||         \n";
     	sqlStatementTypeA += "       REPLACE(CELL        , ',','') || ',' ||         \n";
     	sqlStatementTypeA += "       REPLACE(EMAIL       , ',','') || ',' ||         \n";
     	sqlStatementTypeA += "       REPLACE(CI_YN       , ',','') || ',' ||         \n";
     	sqlStatementTypeA += "       REPLACE(SDP_YN      , ',','') || ',' ||         \n";
     	sqlStatementTypeA += "       REPLACE(JOIN_YYYY   , ',','') || ',' ||         \n";
     	sqlStatementTypeA += "       REPLACE(JOIN_MM     , ',','') || ',' ||         \n";
     	sqlStatementTypeA += "       REPLACE(PROD_NM     , ',','') || ',' ||         \n";
     	sqlStatementTypeA += "       REPLACE(STAT        , ',','') || ',' ||         \n";
     	sqlStatementTypeA += "       REPLACE(TRUE_CNT    , ',','') || ',' ||         \n";
     	sqlStatementTypeA += "       REPLACE(PAY_AMT     , ',','') || ',' ||         \n";
     	sqlStatementTypeA += "       REPLACE(REL_AMT     , ',','') || ',' ||         \n";
     	sqlStatementTypeA += "       REPLACE(ADD_AMT     , ',','') || ',' ||         \n";
     	sqlStatementTypeA += "       REPLACE(DIFF_CNT    , ',','') || ',' ||         \n";
     	sqlStatementTypeA += "       REPLACE(DIFF_MALL   , ',','') || ',' ||         \n";
     	sqlStatementTypeA += "       REPLACE(HOMP_YN     , ',','') || ',' ||         \n";
     	sqlStatementTypeA += "       REPLACE(MALL_YN     , ',','') || ',' ||         \n";
     	sqlStatementTypeA += "       REPLACE(MARKT_AGR_YN, ',','') || ',' ||         \n";
     	sqlStatementTypeA += "       REPLACE(ACU_AMT     , ',','') || ',' ||         \n";
     	sqlStatementTypeA += "       REPLACE(USE_AMT     , ',','') AS CSV_ROW_STRING \n";
     	sqlStatementTypeA += "  FROM LF_DMUSER.TB_CSV_MAIN_LIST                      \n";
     	sqlStatementTypeA += " WHERE 1=1                                             \n";

     	System.out.println(sqlStatementTypeA);
     	Class.forName(sAccessClassName);
     	connectionTypeA = DriverManager.getConnection(sAccessIp, sAccessUserName, sAccessPassword);
         preparedStatementTypeA = connectionTypeA.prepareStatement(sqlStatementTypeA) ;
         rsTypeA = preparedStatementTypeA.executeQuery();

         System.out.println("데이터A reading 중");
         while(rsTypeA.next())
         {
         	String rowData = rsTypeA.getString("csv_row_string");
         	resultBuffer.append(rowData);
             resultBuffer.append("\r\n"); // 한줄 엔터
         }
         System.out.println("데이터A reading 종료");

         if (sOsName.indexOf("windows") >= 0)
         {
         	CommonUtils.writeTextFile(sMakeFileDirectoryPath + "\\" + sYYYYMMDD + A_TYPE_CSV_NAME_POSTFIX, resultBuffer.toString(), "MS949"); // 파일만듬
         }
         else
         {
         	CommonUtils.writeTextFile(sMakeFileDirectoryPath + "/" + sYYYYMMDD + A_TYPE_CSV_NAME_POSTFIX, resultBuffer.toString(), "MS949"); // 파일만듬
         }

         System.out.println("A쓰기완료!");

         resultBuffer.delete(0, resultBuffer.length());

         hmParamTypeA.put("ext_dt", sYYYYMMDD);
         hmParamTypeA.put("file_type", "A");
         if (sOsName.indexOf("windows") >= 0)
         {
         	hmParamTypeA.put("file_path", sMakeFileDirectoryPath + "\\" + sYYYYMMDD + A_TYPE_CSV_NAME_POSTFIX);
         }
         else
         {
         	hmParamTypeA.put("file_path", sMakeFileDirectoryPath + "/" + sYYYYMMDD + A_TYPE_CSV_NAME_POSTFIX);
         }
         hmParamTypeA.put("file_name", sYYYYMMDD + A_TYPE_CSV_NAME_POSTFIX);
         System.out.println("A신규데이터 인서트");
         DlwCondDAO.insertMbidCsvFileList(hmParamTypeA);

         rsTypeA.close();
         preparedStatementTypeA.close();
         connectionTypeA.close();
         // A 타입 파일 작성 end

         // B 타입 파일 작성 start
         String sqlStatementTypeB = "";
      	 sqlStatementTypeB += "SELECT '회원번호,고유번호,회원명,생년월일,성별,지역,휴대전화번호,E메일,CI정상여부,SDP회원여부,가입년도,가입월,상품명,회원상태,최종납입회차,상조금액,결합금액,추가금액,연체회수,회원몰잔여금액,홈페이지여부,회원몰고객여부,마케팅허용여부,회원몰적립금액,회원몰사용금액' AS CSV_ROW_STRING FROM DUAL \n";
      	 sqlStatementTypeB += "UNION ALL                                              \n";
         sqlStatementTypeB += "SELECT REPLACE(ACCNT_NO    , ',','') || ',' ||         \n";
         sqlStatementTypeB += "       REPLACE(MEM_NO      , ',','') || ',' ||         \n";
         sqlStatementTypeB += "       REPLACE(MEM_NM      , ',','') || ',' ||         \n";
         sqlStatementTypeB += "       REPLACE(BRTH_MON_DAY, ',','') || ',' ||         \n";
         sqlStatementTypeB += "       REPLACE(SEX         , ',','') || ',' ||         \n";
         sqlStatementTypeB += "       REPLACE(ADDR        , ',','') || ',' ||         \n";
         sqlStatementTypeB += "       REPLACE(CELL        , ',','') || ',' ||         \n";
         sqlStatementTypeB += "       REPLACE(EMAIL       , ',','') || ',' ||         \n";
         sqlStatementTypeB += "       REPLACE(CI_YN       , ',','') || ',' ||         \n";
         sqlStatementTypeB += "       REPLACE(SDP_YN      , ',','') || ',' ||         \n";
         sqlStatementTypeB += "       REPLACE(JOIN_YYYY   , ',','') || ',' ||         \n";
         sqlStatementTypeB += "       REPLACE(JOIN_MM     , ',','') || ',' ||         \n";
         sqlStatementTypeB += "       REPLACE(PROD_NM     , ',','') || ',' ||         \n";
         sqlStatementTypeB += "       REPLACE(STAT        , ',','') || ',' ||         \n";
         sqlStatementTypeB += "       REPLACE(TRUE_CNT    , ',','') || ',' ||         \n";
         sqlStatementTypeB += "       REPLACE(PAY_AMT     , ',','') || ',' ||         \n";
         sqlStatementTypeB += "       REPLACE(REL_AMT     , ',','') || ',' ||         \n";
         sqlStatementTypeB += "       REPLACE(ADD_AMT     , ',','') || ',' ||         \n";
         sqlStatementTypeB += "       REPLACE(DIFF_CNT    , ',','') || ',' ||         \n";
         sqlStatementTypeB += "       REPLACE(DIFF_MALL   , ',','') || ',' ||         \n";
         sqlStatementTypeB += "       REPLACE(HOMP_YN     , ',','') || ',' ||         \n";
         sqlStatementTypeB += "       REPLACE(MALL_YN     , ',','') || ',' ||         \n";
         sqlStatementTypeB += "       REPLACE(MARKT_AGR_YN, ',','') || ',' ||         \n";
         sqlStatementTypeB += "       REPLACE(ACU_AMT     , ',','') || ',' ||         \n";
         sqlStatementTypeB += "       REPLACE(USE_AMT     , ',','') AS CSV_ROW_STRING \n";
         sqlStatementTypeB += "  FROM LF_DMUSER.TB_CSV_MAIN_LIST                      \n";
         sqlStatementTypeB += " WHERE 1=1                                             \n";

         System.out.println(sqlStatementTypeB);
         connectionTypeB = DriverManager.getConnection(sAccessIp, sAccessUserName, sAccessPassword);
         preparedStatementTypeB = connectionTypeB.prepareStatement(sqlStatementTypeB) ;
         rsTypeB = preparedStatementTypeB.executeQuery();

         System.out.println("데이터B reading 중");

         while(rsTypeB.next())
         {
         	String rowData = rsTypeB.getString("csv_row_string");
         	resultBuffer.append(rowData);
         	resultBuffer.append("\r\n"); // 한줄 엔터
         }
         System.out.println("데이터B reading 종료");

         if (sOsName.indexOf("windows") >= 0)
         {
         	CommonUtils.writeTextFile(sMakeFileDirectoryPath + "\\" + sYYYYMMDD + B_TYPE_CSV_NAME_POSTFIX, resultBuffer.toString(), "MS949"); // 파일만듬
         }
         else
         {
         	CommonUtils.writeTextFile(sMakeFileDirectoryPath + "/" + sYYYYMMDD + B_TYPE_CSV_NAME_POSTFIX, resultBuffer.toString(), "MS949"); // 파일만듬
         }

         System.out.println("B 쓰기완료!");

         resultBuffer.delete(0, resultBuffer.length());

         hmParamTypeB.put("ext_dt", sYYYYMMDD);
         hmParamTypeB.put("file_type", "B");
         if (sOsName.indexOf("windows") >= 0)
         {
         	hmParamTypeB.put("file_path", sMakeFileDirectoryPath + "\\" + sYYYYMMDD + B_TYPE_CSV_NAME_POSTFIX);
         }
         else
         {
         	hmParamTypeB.put("file_path", sMakeFileDirectoryPath + "/" + sYYYYMMDD + B_TYPE_CSV_NAME_POSTFIX);
         }
         hmParamTypeB.put("file_name", sYYYYMMDD + B_TYPE_CSV_NAME_POSTFIX);
         System.out.println("B 신규데이터 인서트");
         DlwCondDAO.insertMbidCsvFileList(hmParamTypeB);

         rsTypeB.close();
         preparedStatementTypeB.close();
         connectionTypeB.close();

         // B 타입 파일 작성 end
     }
     catch (Exception e)
     {
         e.printStackTrace();
         szErrorCode = "-1";
         szErrorMsg = e.getMessage();
     }
     finally
     {
     	if(rsTypeA != null)
         {
             rsTypeA.close();
         }
     	if(rsTypeB != null)
         {
             rsTypeB.close();
         }
         if(preparedStatementTypeA != null)
         {
         	preparedStatementTypeA.close();
         }
         if(preparedStatementTypeB != null)
         {
         	preparedStatementTypeB.close();
         }
         if(connectionTypeA != null)
         {
         	connectionTypeA.close();
         }
         if(connectionTypeB != null)
         {
         	connectionTypeB.close();
         }
     }

     return 0;
 }
 */
 public int insertMbidCsvFileMake() throws Exception {
	 Date d = new Date();
     SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
     String sYYYYMMDD = sdf.format(d);
     String sYYYYMM   = sYYYYMMDD.substring(0, 6);

     Map<String, Object> hmParamTypeA = new HashMap<String, Object>();

     hmParamTypeA.put("bat_proc_dt", sYYYYMMDD);

     List<Map<String, Object>> listBatchInfo = DlwCondDAO.getMbidExcelBatchDay(hmParamTypeA);

     if (listBatchInfo.size() <= 0) {
         System.out.println("배치수행 일자가 아닙니다. 배치를 종료합니다.");
         return -1;
     }

     String sMbidFileParentFixPath;
     String sMbidFileFullPath;
     String osName = System.getProperty("os.name").toLowerCase();
     if (osName.indexOf("windows") >= 0)
     {
    	 sMbidFileParentFixPath = "C:\\media\\MBID\\"; // 이건 예제임. 실제 PATH가 아님. (rootPath : C:/)
    	 sMbidFileFullPath = sMbidFileParentFixPath + sYYYYMM + "\\" + sYYYYMMDD + "_A.xlsx";
     }
     else
     {
    	 sMbidFileParentFixPath = "/media/MBID/"; // 이건 예제임. 실제 PATH가 아님. (rootPath : /app/)
    	 sMbidFileFullPath = sMbidFileParentFixPath + sYYYYMM + "/" + sYYYYMMDD + "_A.xlsx";
     }

     File fileMakeFileDirectoryPath = new File(sMbidFileParentFixPath + sYYYYMM);
     if(fileMakeFileDirectoryPath.exists() == false)
	 {
    	 fileMakeFileDirectoryPath.mkdirs();
	 }

     System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 엑셀생성경로 : " + sMbidFileParentFixPath);

     //List<Map<String, Object>> mList = DlwCondDAO.getMbidExcelList(hmParamTypeA); // 선수금 조회 (** 매일 입금,취소된 회원 데이터 조회)
     List<Map<String, Object>> mList = new ArrayList<Map<String,Object>>();

     FileOutputStream outputStream = null;
     Connection connectionTypeA = null;
     PreparedStatement preparedStatementTypeA = null ;
     ResultSet rsTypeA = null;

     String sAccessClassName = EgovProperties.getProperty("Globals.Fix.Database.Access.DriverClassName");
  	 String sAccessIp        = EgovProperties.getProperty("Globals.Fix.Database.Access.Ip");
  	 String sAccessUserName  = EgovProperties.getProperty("Globals.Fix.Database.Access.UserName");
  	 String sAccessPassword  = EgovProperties.getProperty("Globals.Fix.Database.Access.Password");

  	 System.out.println("프로퍼티[1] :: " + sAccessClassName + "," + "프로퍼티[2] :: " + sAccessIp + "," +
  			            "프로퍼티[3] :: " + sAccessUserName + "," + "프로퍼티[4] :: " + sAccessPassword);


  	 // A 타입 파일 작성 start
  	 String sqlStatementTypeA =  "";
     sqlStatementTypeA += "WITH ACCNT_RESN_AMT AS                                                                                                                               \n";
     sqlStatementTypeA += "(                                                                                                                                                    \n";
     sqlStatementTypeA += "    SELECT                                                                                                                                           \n";
     sqlStatementTypeA += "        MPA.ACCNT_NO,                                                                                                                                \n";
     sqlStatementTypeA += "        MPA.PROD_MODEL_KIND,                                                                                                                         \n";
     sqlStatementTypeA += "        RM.SEQ,                                                                                                                                      \n";
     sqlStatementTypeA += "        RD.NO,                                                                                                                                       \n";
     sqlStatementTypeA += "        RD.RESN_AMT                                                                                                                                  \n";
     sqlStatementTypeA += "    FROM LF_DMUSER.MEM_PROD_ACCNT MPA INNER JOIN                                                                                                     \n";
     sqlStatementTypeA += "        LF_DMUSER.RESNSTD_M RM ON MPA.PROD_CD = RM.PROD_CD AND MPA.PROD_MODEL_KIND = RM.MODEL_CL_CD AND MPA.DEL_FG = 'N' INNER JOIN                  \n";
     sqlStatementTypeA += "        LF_DMUSER.RESNSTD_D RD ON RM.SEQ = RD.MST_SEQ                                                                                                \n";
     sqlStatementTypeA += "    WHERE 1=1                                                                                                                                        \n";
     sqlStatementTypeA += "    AND RM.APP_DAY <= MPA.JOIN_DT                                                                                                                    \n";
     sqlStatementTypeA += "    ORDER BY RM.APP_DAY DESC                                                                                                                         \n";
     sqlStatementTypeA += ")                                                                                                                                                    \n";
     sqlStatementTypeA += "                                                                                                                                                     \n";
     sqlStatementTypeA += "SELECT                                                                                                                                               \n";
     sqlStatementTypeA += "    TBL.ACCNT_NO,                                                                                                                                    \n";
     sqlStatementTypeA += "    TBL.MEM_NO,                                                                                                                                      \n";
     sqlStatementTypeA += "    TBL.MEM_NM,                                                                                                                                      \n";
     sqlStatementTypeA += "    TBL.BRTH_MON_DAY,                                                                                                                                \n";
     sqlStatementTypeA += "    DECODE(TBL.SEX,'0001','남성','여성') AS SEX,                                                                                                     \n";
     sqlStatementTypeA += "    SUBSTR(TBL.HOME_ADDR,0,6) AS ADDR,                                                                                                               \n";
     sqlStatementTypeA += "    TBL.CELL,                                                                                                                                        \n";
     sqlStatementTypeA += "    TBL.EMAIL,                                                                                                                                       \n";
     sqlStatementTypeA += "    TBL.CI_YN,                                                                                                                                       \n";
     sqlStatementTypeA += "    TBL.SDP_YN,                                                                                                                                      \n";
     sqlStatementTypeA += "    SUBSTR(TBL.JOIN_DT,0,4) AS JOIN_YYYY,                                                                                                            \n";
     sqlStatementTypeA += "    SUBSTR(TBL.JOIN_DT,5,2) AS JOIN_MM,                                                                                                              \n";
     sqlStatementTypeA += "    TBL.PROD_NM,                                                                                                                                     \n";
     sqlStatementTypeA += "    DECODE(TBL.MAIN_CONTRACT, '01', '상조', '02', '해외여행', '03', '크루즈', '04', '골프', '05', '어학연수', '주계약없음') AS MAIN_CONTRACT,        \n";
     sqlStatementTypeA += "    TBL.STAT,                                                                                                                                        \n";
     sqlStatementTypeA += "    TBL.TRUE_CNT,                                                                                                                                    \n";
     sqlStatementTypeA += "    TBL.PAY_AMT,                                                                                                                                     \n";
     sqlStatementTypeA += "    TBL.REL_AMT,                                                                                                                                     \n";
     sqlStatementTypeA += "    TBL.ADD_AMT,                                                                                                                                     \n";
     sqlStatementTypeA += "    TBL.DIFF_CNT,                                                                                                                                    \n";
     sqlStatementTypeA += "    (TBL.ACU_AMT - TBL.USE_AMT) AS DIFF_MALL,                                                                                                        \n";
     sqlStatementTypeA += "    'X' AS HOMP_YN,                                                                                                                                  \n";
     sqlStatementTypeA += "    TBL.MALL_YN,                                                                                                                                     \n";
     sqlStatementTypeA += "    TBL.MARKT_AGR_YN,                                                                                                                                \n";
     sqlStatementTypeA += "    TBL.ACU_AMT,                                                                                                                                     \n";
     sqlStatementTypeA += "    TBL.USE_AMT,                                                                                                                                     \n";
     sqlStatementTypeA += "    NVL((SELECT RESN_AMT FROM ACCNT_RESN_AMT ARA WHERE 1=1 AND TBL.ACCNT_NO = ARA.ACCNT_NO AND TBL.TRUE_CNT = ARA.NO AND ROWNUM = 1), 0) AS RESN_AMT,\n";
     sqlStatementTypeA += "    TBL.MAN_DAY                                                                                                                                     \n";
     sqlStatementTypeA += "FROM                                                                                                                                                 \n";
     sqlStatementTypeA += "(                                                                                                                                                    \n";
     sqlStatementTypeA += "    SELECT                                                                                                                                           \n";
     sqlStatementTypeA += "        MBID.ACCNT_NO,                                                                                                                               \n";
     sqlStatementTypeA += "        MBID.MEM_NO,                                                                                                                                 \n";
     sqlStatementTypeA += "        MB.MEM_NM,                                                                                                                                   \n";
     sqlStatementTypeA += "        (CASE WHEN LENGTH(MB.CI_VAL) = 88 THEN 'Y' ELSE 'N' END) AS CI_YN,                                                                           \n";
     sqlStatementTypeA += "        MB.BRTH_MON_DAY,                                                                                                                             \n";
     sqlStatementTypeA += "        MB.EMAIL,                                                                                                                                    \n";
     sqlStatementTypeA += "        MBID.TRUE_DAY,                                                                                                                               \n";
     sqlStatementTypeA += "        MBID.JOIN_DT,                                                                                                                                \n";
     sqlStatementTypeA += "        MB.SEX,                                                                                                                                      \n";
     sqlStatementTypeA += "        MB.HOME_ADDR,                                                                                                                                \n";
     sqlStatementTypeA += "        PD.PROD_NM,                                                                                                                                  \n";
     sqlStatementTypeA += "        DECODE(MAIN_CONTRACT, '01', '상조', '02', '해외여행', '03', '크루즈', '04', '골프', '05', '어학연수', '주계약없음') AS MAIN_CONTRACT,        \n";
     sqlStatementTypeA += "        MB.CELL,                                                                                                                                     \n";
     sqlStatementTypeA += "        MB.MARKT_AGR_YN,                                                                                                                             \n";
     sqlStatementTypeA += "        MBID.MONTH_COUNT,                                                                                                                            \n";
     sqlStatementTypeA += "        MBID.REAL_CNT AS TRUE_CNT,                                                                                                                   \n";
     sqlStatementTypeA += "        DECODE(MBID.KSTBIT,'01','대기','02','가입','03','해약','행사') AS STAT,                                                                      \n";
     sqlStatementTypeA += "        NVL                                                                                                                                          \n";
     sqlStatementTypeA += "        (                                                                                                                                            \n";
     sqlStatementTypeA += "            (                                                                                                                                        \n";
     sqlStatementTypeA += "                SELECT                                                                                                                               \n";
     sqlStatementTypeA += "                    DISTINCT 'Y'                                                                                                                     \n";
     sqlStatementTypeA += "                FROM TB_MEMBER_BASIC_INFO_DAY A INNER JOIN                                                                                           \n";
     sqlStatementTypeA += "                    PRODUCT B ON A.PROD_CD = B.PROD_CD                                                                                               \n";
     sqlStatementTypeA += "                WHERE A.MEM_NO = MBID.MEM_NO                                                                                                         \n";
     sqlStatementTypeA += "                AND B.SECTION_THR = '0010'                                                                                                           \n";
     sqlStatementTypeA += "                AND MBID.MEM_NO = A.MEM_NO                                                                                                           \n";
     sqlStatementTypeA += "            ),                                                                                                                                       \n";
     sqlStatementTypeA += "        'N') AS SDP_YN,                                                                                                                              \n";
     sqlStatementTypeA += "        (MBID.MONTH_COUNT - MBID.TRUE_CNT) AS DIFF_CNT,                                                                                              \n";
     sqlStatementTypeA += "        NVL((SELECT SUM(PND.AMT) FROM PRODUCT_NO_DATA PND WHERE PND.PROD_CD = MBID.PROD_CD AND NO <= MBID.TRUE_CNT),0) AS PAY_AMT,                   \n";
     sqlStatementTypeA += "        NVL((SELECT SUM(PND.REL_AMT) FROM PRODUCT_NO_DATA PND WHERE PND.PROD_CD = MBID.PROD_CD AND NO <= MBID.REL_CNT),0) AS REL_AMT,                \n";
     sqlStatementTypeA += "        NVL((SELECT SUM(PND.ADD_AMT) FROM PRODUCT_NO_DATA PND WHERE PND.PROD_CD = MBID.PROD_CD AND NO <= MBID.ADD_CNT),0) AS ADD_AMT,                \n";
     sqlStatementTypeA += "        (CASE WHEN NVL(DLW.GOODS_ID,'N') != 'N' AND MALL_USE_YN = 'Y'  THEN 'Y' ELSE 'N' END) AS MALL_YN,                                            \n";
     sqlStatementTypeA += "        NVL(DLW.ACU_AMT,0) AS ACU_AMT,                                                                                                               \n";
     sqlStatementTypeA += "        NVL(DLW.USE_AMT,0) AS USE_AMT,                                                                                                               \n";
     sqlStatementTypeA += "        TO_CHAR(ADD_MONTHS(MBID.JOIN_DT, PD.EXPR_NO - MBID.NEW_CHAN_GUNSU - 1 +                                                                      \n";
     sqlStatementTypeA += "        		              NVL((SELECT  NVL(MME.EXT_PERIOD, 0)                                                                                       \n";
     sqlStatementTypeA += "        		                   FROM LF_DMUSER.TB_MEMBER_MANGI_EXT MME                                                                               \n";
     sqlStatementTypeA += "        		                   WHERE 1=1                                                                                                            \n";
     sqlStatementTypeA += "        		                   AND DEL_FG = 'N'                                                                                                     \n";
     sqlStatementTypeA += "        		                   AND MME.ACCNT_NO = MBID.ACCNT_NO), 0) * 12 ),'YYYYMMDD') AS MAN_DAY                                                  \n";
     sqlStatementTypeA += "    FROM LF_DMUSER.TB_MEMBER_BASIC_INFO_DAY MBID INNER JOIN                                                                                          \n";
     sqlStatementTypeA += "        LF_DMUSER.MEMBER MB ON MBID.MEM_NO = MB.MEM_NO AND MB.DEL_FG = 'N' INNER JOIN                                                                \n";
     sqlStatementTypeA += "        LF_DMUSER.PRODUCT PD ON MBID.PROD_CD = PD.PROD_CD LEFT OUTER JOIN                                                                            \n";
     sqlStatementTypeA += "        LF_DMUSER.TB_MEMBER_DLWMALL DLW ON MBID.MEM_NO = DLW.MEMBER_ID AND MBID.ACCNT_NO = DLW.GOODS_ID                                              \n";
     sqlStatementTypeA += "    WHERE 1=1                                                                                                                                        \n";
     sqlStatementTypeA += "    AND MEM_NM != 'AAA'                                                                                                                              \n";
     sqlStatementTypeA += "    --AND (HOME_ADDR LIKE '%김포%' OR HOME_ADDR LIKE '%파주%' OR HOME_ADDR LIKE '%고양%')                                                            \n";
     sqlStatementTypeA += ") TBL                                                                                                                                                \n";
     sqlStatementTypeA += "WHERE 1=1                                                                                                                                            \n";
     sqlStatementTypeA += "AND DIFF_CNT < 4                                                                                                                                     \n";

  	 System.out.println(sqlStatementTypeA);

  	 try
  	 {
  		 Class.forName(sAccessClassName);
  	  	 connectionTypeA = DriverManager.getConnection(sAccessIp, sAccessUserName, sAccessPassword);
  	     preparedStatementTypeA = connectionTypeA.prepareStatement(sqlStatementTypeA) ;
  	     rsTypeA = preparedStatementTypeA.executeQuery();

  	     // 대용량 엑셀 만들기
  	     Workbook wb = new SXSSFWorkbook(100);
  	     Sheet sh = wb.createSheet("21_00");

  	     // 선수금 sheet용
  	     String[] sArrTitle = {"회원번호","고유번호","회원명","생년월일","성별",
  	    		               "지역", "휴대전화번호", "E메일", "CI정상여부", "SDP회원여부",
  	    		               "가입년도", "가입월", "상품명", "주계약", "회원상태", "최종납입회차",
  	    		               "상조금액", "결합금액", "추가금액", "연체회수", "회원몰잔여금액",
  	    		               "홈페이지여부", "회원몰고객여부", "마케팅허용여부", "회원몰적립금액", "회원몰사용금액",
  	    		               "해약금", "만기일자"};

  	     List <String> lstCol = new ArrayList<String>();
  	     lstCol.add("accnt_no");
  	     lstCol.add("mem_no");
  	     lstCol.add("mem_nm");
  	     lstCol.add("brth_mon_day");
  	     lstCol.add("sex");
  	     lstCol.add("addr");
  	     lstCol.add("cell");
  	     lstCol.add("email");
  	     lstCol.add("ci_yn");
  	     lstCol.add("sdp_yn");
  	     lstCol.add("join_yyyy");
  	     lstCol.add("join_mm");
  	     lstCol.add("prod_nm");
  	     lstCol.add("main_contract");
  	     lstCol.add("stat");
  	     lstCol.add("true_cnt");
  	     lstCol.add("pay_amt");
  	     lstCol.add("rel_amt");
  	     lstCol.add("add_amt");
  	     lstCol.add("diff_cnt");
  	     lstCol.add("diff_mall");
  	     lstCol.add("homp_yn");
  	     lstCol.add("mall_yn");
  	     lstCol.add("markt_agr_yn");
  	     lstCol.add("acu_amt");
  	     lstCol.add("use_amt");
  	     lstCol.add("resn_amt");
  	     lstCol.add("man_day");

  	     int i = 0;
  	     int j = 0;
  	     Row row = sh.createRow(i++);

  	     for (String field : sArrTitle) {
  	         Cell cell = row.createCell(j++);
  	         cell.setCellValue((String) field);
  	     }

  	     System.out.println("====일일청구기준데이터 엑셀파일 리스트 sheet 시작");

  	     // 해당 셀에 데이터를 입력한다.
  	     Map<String, Object> mRow = null;
  	     String val = "";
  	     String colId = "";
  	     int rowCnt = 0;

  	     for (i = 0; i < mList.size(); i++) {
  	         mRow = mList.get(i);
  	         row = sh.createRow(i+1);
  	         for (j=0; j<lstCol.size(); ++j) {
  	             colId = lstCol.get(j);
  	             Cell cell = row.createCell(j);
  	             val = CommonUtils.nvls(String.valueOf(mRow.get(colId)));

  	             cell.setCellValue(val);
  	         }
  	     }

  	     while(rsTypeA.next())
  	     {
  	         row = sh.createRow(i+1);
  	         for (j=0; j<lstCol.size(); ++j) {
  	             colId = lstCol.get(j);
  	             Cell cell = row.createCell(j);
  	             val = CommonUtils.nvls(String.valueOf(rsTypeA.getString(colId)));

  	             cell.setCellValue(val);
  	         }
  	         i++;
  	     }

  	     System.out.println("====일일청구기준데이터 엑셀파일 리스트 sheet 끝");
  	     //////////////////////////////////////////////////////////////////////////////////////////////////////////

  	     outputStream = new FileOutputStream(sMbidFileFullPath);
  	     wb.write(outputStream);

  	     hmParamTypeA.put("ext_dt", sYYYYMMDD);
  	     hmParamTypeA.put("file_type", "A");
  	     hmParamTypeA.put("file_path", sMbidFileFullPath);
  	     hmParamTypeA.put("file_name", sYYYYMMDD + "_A.xlsx");

  	     DlwCondDAO.insertMbidCsvFileList(hmParamTypeA);

  	     outputStream.close();
  	     rsTypeA.close();
  	     preparedStatementTypeA.close();
  	     connectionTypeA.close();
  	 }
  	 catch(Exception e)
  	 {
  		 e.printStackTrace();
  	 }
  	 finally
  	 {
  		 if(outputStream != null)
  		 {
  		     outputStream.close();
  		 }
  		 if(rsTypeA != null)
 		 {
  			 rsTypeA.close();
 		 }
  		 if(preparedStatementTypeA != null)
 		 {
  			 preparedStatementTypeA.close();
 		 }
  		 if(connectionTypeA != null)
 		 {
  			 connectionTypeA.close();
 		 }
  	 }

	 return 0;
 }

 public int sendMessageBatchAuto() throws Exception {
	 Map<String, Object> hmParam = new HashMap<String, Object>();
	 int iSuccessCode = 0;

     try
     {
    	 System.out.println("메시지 전송을 시작합니다.");
    	 int iSmsCode     = DlwCondDAO.insertMbidBatchSmsSend(hmParam);
    	 int iMmsCode     = DlwCondDAO.insertMbidBatchMmsSend(hmParam);
    	 int iMsgHistCode = DlwCondDAO.insertMbidBatchMsgSendHist(hmParam);
    	 System.out.println("메시지 전송을 성공하였습니다.");
    	 iSuccessCode = 1;
     }
     catch(Exception e)
     {
    	 iSuccessCode = -1;
    	 e.printStackTrace();
    	 System.out.println("메세지 전송 처리 오류 입니다.");
     }
     finally
     {

     }

     return iSuccessCode;
 }

 public int insertMemberYencheHstr() throws Exception {
	 Map<String, Object> hmParam = new HashMap<String, Object>();
	 int iSuccessCode = 0;
	 try
     {
    	 System.out.println("고객연체 히스토리 데이터 적재를 시작합니다.");
    	 int iMsg = DlwCondDAO.insertMemberYencheHstr(hmParam);
    	 System.out.println("고객연체 히스토리 데이터 적재를 성공하였습니다.");
    	 iSuccessCode = 1;
     }
     catch(Exception e)
     {
    	 iSuccessCode = -1;
    	 e.printStackTrace();
    	 System.out.println("고객연체 히스토리 데이터 적재에 실패하였습니다.");
     }
     finally
     {

     }

     return iSuccessCode;
 }

 public int insertAlowBasicInfo() throws Exception {
	 Map<String, Object> hmParam = new HashMap<String, Object>();
	 int iSuccessCode = 0;
	 try
     {
		 //String sToDay = (String)pmParam.get("calc_day"); // 개발 테스트시
		 /*
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		 String sToDay = (String)pmParam.get("calc_day"); // 개발 테스트시

		 String sPreMonth  = addDate(sToDay, 0, -1, 0);
		 String sPpreMonth = addDate(sToDay, 0, -2, 0);

		 String sPpreMonthFirstDay = sPpreMonth.substring(0, 6) + "" + "01";
		 String sPpreMonthLastDay  = sPpreMonth.substring(0, 6) + "" + getMonthLastDay(sPpreMonth);

		 String sPreMonthYYYYMM    = sPreMonth.substring(0, 6);
		 String sPreMonthFirstDay  = sPreMonth.substring(0, 6) + "" + "01";
		 String sPreMonthLastDay   = sPreMonth.substring(0, 6) + "" + getMonthLastDay(sPreMonth);

		 String sToMonthFirstDay   = sToDay.substring(0, 6) + "" + "01";
		 String sToMonthLastDay    = sToDay.substring(0, 6) + "" + getMonthLastDay(sToDay.substring(0, 6));

		 System.out.println("sPpreMonthFirstDay (전전달의 첫일) :: " + sPpreMonthFirstDay);
		 System.out.println("sPpreMonthLastDay  (전전달의 말일) :: " + sPpreMonthLastDay);
		 System.out.println("sPreMonthYYYYMM    (전달의 YYYYMM) :: " + sPreMonthYYYYMM);
		 System.out.println("sPreMonthFirstDay  (전달의 첫일)   :: " + sPreMonthFirstDay);
		 System.out.println("sPreMonthLastDay   (전달의 말일)   :: " + sPreMonthLastDay);
		 System.out.println("sToMonthFirstDay   (이번달의 첫일) :: " + sToMonthFirstDay);
		 System.out.println("sToMonthLastDay    (이번달의 말일) :: " + sToMonthLastDay);

		 hmParam.put("S_PPRE_MONTH_FIRST_DAY", sPpreMonthFirstDay);
		 //hmParam.put("sPpreMonthLastDay", sPpreMonthLastDay);
		 hmParam.put("S_PRE_MONTH_YYYYMM", sPreMonthYYYYMM);
		 hmParam.put("S_PRE_MONTH_FIRST_DAY", sPreMonthFirstDay);
		 hmParam.put("S_PRE_MONTH_LAST_DAY", sPreMonthLastDay);
		 hmParam.put("S_TO_MONTH_FIRST_DAY", sToMonthFirstDay);
		 //hmParam.put("sToMonthLastDay", sToMonthLastDay);
		  *
		  */
    	 System.out.println("수당/수수료 산출 데이터를 적재합니다.");

    	 int iMsg = DlwCondDAO.insertAlowBasicInfo(hmParam);

    	 System.out.println("수당/수수료 산출 데이터 적재를 성공하였습니다.");

    	 iSuccessCode = 1;
     }
     catch(Exception e)
     {
    	 iSuccessCode = -1;
    	 e.printStackTrace();
    	 System.out.println("수당/수수료 산출 데이터 적재에 실패하였습니다.");
     }
     finally
     {

     }

     return iSuccessCode;
 }
 /**
  * 해당 월 마감 된 데이터 수 조회
  *
  * @param pmParam 검색 조건
  * @return
  * @throws Exception
  */
 public int getCloseDataCount(Map<String, ?> pmParam) throws Exception {
     return DlwCondDAO.getCloseDataCount(pmParam);
 }


 /**
  * 해당 월 수수료 마감
  *
  * @param pmParam 검색 조건
  * @return
  * @throws Exception
  */
 public int insertCloseDataComm(Map<String, ?> pmParam) throws Exception {
	 int iSuccessCode = 0;

	 try {

    	 iSuccessCode = DlwCondDAO.insertCloseDataComm(pmParam);
     }
     catch(Exception e) {
    	 iSuccessCode = -1;
    	 e.printStackTrace();
    	 System.out.println("수당/수수료 산출 마감을 실패하였습니다.");
     }
     return iSuccessCode;
 }

 /**
  * 해당 월 수당 마감
  *
  * @param pmParam 검색 조건
  * @return
  * @throws Exception
  */
 public int insertCloseDataAlow(Map<String, ?> pmParam) throws Exception {
	 int iSuccessCode = 0;

	 try {

    	 iSuccessCode = DlwCondDAO.insertCloseDataAlow(pmParam);
     }
     catch(Exception e) {
    	 iSuccessCode = -1;
    	 e.printStackTrace();
    	 System.out.println("수당/수수료 산출 마감을 실패하였습니다.");
     }
     return iSuccessCode;
 }

 /**
  * 수당, 수수료 산출 여부 확인
  *
  * @param pmParam 검색 조건
  * @return
  * @throws Exception
  */
 public List<Map<String, Object>> getAlowCalcMgmtExtList(Map<String, Object> pmParam) throws Exception {
	 return DlwCondDAO.getAlowCalcMgmtExtList(pmParam);
 }

 /**
  * 수당, 수수료 산출 여부 등록
  *
  * @param pmParam 검색 조건
  * @return
  * @throws Exception
  */
 public int insertAlowCalcMgmtExtList(Map<String, Object> pmParam)throws Exception {
	 return DlwCondDAO.insertAlowCalcMgmtExtList(pmParam);
 }

}
