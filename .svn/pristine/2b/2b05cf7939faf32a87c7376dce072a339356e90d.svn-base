/*
 * (@)# DlwPayService.java
 *
 * @author 석민
 * @version 1.0
 * @date 2016/02/22
 * Copyright (c) 2015 by Inwoo tech inc, KOREA. All Rights Reserved.
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import powerservice.business.dlw.service.DlwDeductionService;
import powerservice.business.sys.service.XlsService;
import powerservice.common.util.CommonUtils;
import powerservice.core.util.ParamUtils;

import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;
import com.oreilly.servlet.MultipartRequest;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.cmmn.exception.EgovBizException;


/**
 * 산출정보를 관리한다
 */
@Service
public class DlwDeductionServiceImpl extends EgovAbstractServiceImpl implements DlwDeductionService {

    @Resource
    public DlwDeductionDAO DlwDeductionDAO;

    @Resource
    private XlsService xlsService;

    private final Logger log = LoggerFactory.getLogger(DlwDeductionServiceImpl.class);

    /** ================================================================
     * 날짜 : 20190124
     * 이름 : 송준빈
     * 추가내용 : 공제 데이터 전송 기준데이터 조회수 (신규/타사 페이지)
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT
     * ================================================================
     * */
    public int getGongjeDayExtCount(Map<String, ?> pmParam) throws Exception {
        return DlwDeductionDAO.getGongjeDayExtCount(pmParam);
    }

    /** ================================================================
     * 날짜 : 20190124
     * 이름 : 송준빈
     * 추가내용 : 공제 데이터 전송 기준데이터 리스트 (신규/타사 페이지)
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT
     * ================================================================
     * */
    public List<Map<String, Object>> getGongjeDayExtList(Map<String, ?> pmParam) throws Exception {
        return DlwDeductionDAO.getGongjeDayExtList(pmParam);
    }

    /** ================================================================
     * 날짜 : 20190124
     * 이름 : 송준빈
     * 추가내용 : 공제 데이터 전송 기준데이터 산출
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT
     * ================================================================
     * */
    /*public int insertGongjeDayExt(Map<String, ?> pmParam) throws Exception {
        return DlwDeductionDAO.insertGongjeDayExt(pmParam);
    }*/
    public int insertGongjeDayExt() throws Exception {
        return DlwDeductionDAO.insertGongjeDayExt();
    }

    /** ================================================================
     * 날짜 : 20190130
     * 이름 : 정승철
     * 추가내용 : 공제 일반데이터 엑셀생성
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT
     * ================================================================
     * */
    public void createExcel() throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar c1 = Calendar.getInstance();
        String sCurrTime = sdf.format(c1.getTime());
        System.out.println("Today : " + sCurrTime);


        Map<String, Object> hmParam = new HashMap<String, Object>();
        hmParam.put("ext_dt", sCurrTime);

        int chkCnt = DlwDeductionDAO.chkGongjeSched(hmParam); // 공제 산출스케줄 등록여부 조회

        if(chkCnt == 0) {
            System.out.println("====해당 날짜 " +sCurrTime+ " 은 공제 산출스케줄에 등록되어 있지 않습니다.");
            return;
        }

        String sBasePath;
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.indexOf("windows") >= 0)
        {
            sBasePath = EgovProperties.getProperty("gongje.basicData.windows.basePath");
        }
        else
        {
            sBasePath = EgovProperties.getProperty("gongje.basicData.unix.basePath");
        }
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 공제 일반 엑셀생성경로 : " + sBasePath);
        String xlFileNm = sBasePath + "DMLIFE_"+sCurrTime+".xlsx";
        log.debug(">xlFileNm : " + xlFileNm);

        //////////////////////////////////////////////////////////////////////////////////////////////////
        //List<Map<String, Object>> mList  = DlwDeductionDAO.getGongjeDList(hmParam); // 신규/타사
        List<Map<String, Object>> mList2 = DlwDeductionDAO.getGongjeUList(hmParam); // 정보변경
        List<Map<String, Object>> mList3 = DlwDeductionDAO.getGongjeRList(hmParam); // 해약
        List<Map<String, Object>> mList4 = DlwDeductionDAO.getGongjeTList(hmParam); // 양도양수
        List<Map<String, Object>> mList5 = DlwDeductionDAO.getGongjeEList(hmParam); // 행사


        SXSSFWorkbook workbook = new SXSSFWorkbook(100);
        Sheet sheetPay = workbook.createSheet("21_00"); // 공제 납입 데이터 산출 시작
        Sheet sheet1 = workbook.createSheet("01_00"); // 신규/타사 sheet
        Sheet sheet2 = workbook.createSheet("31_00"); // 정보변경 sheet
        Sheet sheet3 = workbook.createSheet("51_00"); // 해약 sheet
        Sheet sheet4 = workbook.createSheet("41_00"); // 양도양수 sheet
        Sheet sheet5 = workbook.createSheet("52_00"); // 행사 sheet
        Sheet sheetCash = workbook.createSheet("24_00"); // 레디캐시 sheet

        /*=================================================================================================================*/
        System.out.println("=====공제 납입 데이터 산출 시작[P]=====");
        createPayExcel(sheetPay);
        /*=================================================================================================================*/

        /*=================================================================================================================*/
        System.out.println("=====공제 신규회원 데이터 산출 시작[M1]=====");
        createNewMemberExcel(sheet1);
        /*=================================================================================================================*/

        /*
        System.out.println("=====공제 신규회원 데이터 산출 시작[M1]=====");
        String[] sArrTitle = {"구분", "회원번호", "회원명", "생년월일", "가입일자", "상품명", "상품가격", "만기회차", "만기일자"
                              , "이체일", "월납입금액", "정산년월", "납입일자", "납입회차", "납입금액", "우편번호", "주소"
                              , "상세주소", "전화번호", "이메일", "결과키"};

        List <String> lstCol = new ArrayList<String>();
        lstCol.add("gubun");
        lstCol.add("accnt_no");
        lstCol.add("mem_nm");
        lstCol.add("brth_mon_day");
        lstCol.add("join_dt");
        lstCol.add("prod_nm");
        lstCol.add("prod_amt");
        lstCol.add("expr_no");
        lstCol.add("mdate");
        lstCol.add("cday");
        lstCol.add("mon_pay_amt");
        lstCol.add("yyyymm");
        lstCol.add("pay_dt");
        lstCol.add("pay_num");
        lstCol.add("pay_amt");
        lstCol.add("post");
        lstCol.add("addr1");
        lstCol.add("addr2");
        lstCol.add("tel");
        lstCol.add("email");
        lstCol.add("result_key");

        int i = 0;
        int j = 0;
        Row row = sheet1.createRow(i++);

        for (String field : sArrTitle) {
            Cell cell = row.createCell(j++);
            cell.setCellValue((String) field);
        }

        // 해당 셀에 데이터를 입력한다.
        Map<String, Object> mRow = null;
        String val = "";
        String colId = "";
        for (i = 0; i < mList.size(); i++) {
            mRow = mList.get(i);
            row = sheet1.createRow(i+1);
            for (j=0; j<lstCol.size(); ++j) {
                colId = lstCol.get(j);
                Cell cell = row.createCell(j);
                val = CommonUtils.nvls(String.valueOf(mRow.get(colId)));

                cell.setCellValue(val);
            }
        }
		*/

        /*=================================================================================================================*/
        System.out.println("=====공제 정보변경 회원 데이터 산출 시작[M2]=====");
        String[] sArrTitle2 = {"구분", "회원번호", "공제번호", "회원명", "생년월일", "변경일자", "우편번호"
                               , "주소", "상세주소", "전화번호", "이메일", "결과키"};

        List <String> lstCol2 = new ArrayList<String>();
        lstCol2.add("gubun");
        lstCol2.add("accnt_no");
        lstCol2.add("ded_no");
        lstCol2.add("mem_nm");
        lstCol2.add("brth_mon_day");
        lstCol2.add("chg_dt");
        lstCol2.add("post");
        lstCol2.add("addr1");
        lstCol2.add("addr2");
        lstCol2.add("tel");
        lstCol2.add("email");
        lstCol2.add("result_key");

        int i2 = 0;
        int j2 = 0;

        Row row2 = sheet2.createRow(i2++);

        for (String field : sArrTitle2) {
            Cell cell = row2.createCell(j2++);
            cell.setCellValue((String) field);
        }

        // 해당 셀에 데이터를 입력한다.
        Map<String, Object> mRow2 = null;
        String val2 = "";
        String colId2 = "";
        for (i2 = 0; i2< mList2.size(); i2++) {
            mRow2 = mList2.get(i2);
            row2 = sheet2.createRow(i2+1);
            for (j2=0; j2<lstCol2.size(); ++j2) {
                colId2 = lstCol2.get(j2);
                Cell cell = row2.createCell(j2);
                val2 = CommonUtils.nvls(String.valueOf(mRow2.get(colId2)));

                cell.setCellValue(val2);
            }
        }
        
        /*=================================================================================================================*/
        System.out.println("=====공제 해약 회원 데이터 산출 시작[M3]=====");
        String[] sArrTitle3 = {"구분", "회원번호", "공제번호", "회원명", "해지일자", "총납입금액", "해약구분", "결과키"};

        List <String> lstCol3 = new ArrayList<String>();
        lstCol3.add("gubun");
        lstCol3.add("accnt_no");
        lstCol3.add("ded_no");
        lstCol3.add("mem_nm");
        lstCol3.add("proc_day");
        lstCol3.add("true_amt");
        lstCol3.add("resn_cl");
        lstCol3.add("result_key");

        int i3 = 0;
        int j3 = 0;

        Row row3 = sheet3.createRow(i3++);

        for (String field : sArrTitle3) {
            Cell cell = row3.createCell(j3++);
            cell.setCellValue((String) field);
        }

        // 해당 셀에 데이터를 입력한다.
        Map<String, Object> mRow3 = null;
        String val3 = "";
        String colId3 = "";
        for (i3 = 0; i3 < mList3.size(); i3++) {
            mRow3 = mList3.get(i3);
            row3 = sheet3.createRow(i3+1);
            for (j3=0; j3<lstCol3.size(); ++j3) {
                colId3 = lstCol3.get(j3);
                Cell cell = row3.createCell(j3);
                val3 = CommonUtils.nvls(String.valueOf(mRow3.get(colId3)));

                cell.setCellValue(val3);
            }
        }


        /*=================================================================================================================*/
        System.out.println("=====공제 양도양수 회원 데이터 산출 시작[M4]=====");

        // 양도양수 sheet용
        String[] sArrTitle4 = {"구분", "회원번호", "공제번호", "회원명", "양도일자", "회원번호변경여부", "양수자회원번호"
                               , "양수자회원명", "양수자생년월일", "상품명", "상품변경여부", "총계약금액", "총계약회차"
                               , "만기일자", "월납입일자", "납입금액", "최종납입회차", "최종납입금액", "정산년월"
                               , "납입일자", "납입회차", "납입금액", "우편번호", "주소", "상세주소", "전화번호", "이메일", "결과키"};

        List <String> lstCol4 = new ArrayList<String>();
        lstCol4.add("gubun");
        lstCol4.add("accnt_no");
        lstCol4.add("ded_no");
        lstCol4.add("mem_nm");
        lstCol4.add("trns_dt");
        lstCol4.add("chg_yn");
        lstCol4.add("ys_accnt_no");
        lstCol4.add("ys_mem_nm");
        lstCol4.add("brth_mon_day");
        lstCol4.add("prod_nm");
        lstCol4.add("yn");
        lstCol4.add("amt");
        lstCol4.add("num");
        lstCol4.add("exp_dt");
        lstCol4.add("pay_dd");
        lstCol4.add("pay_amt");
        lstCol4.add("tot_num");
        lstCol4.add("tot_amt");
        lstCol4.add("yymm");
        lstCol4.add("pay_dt");
        lstCol4.add("pay_num");
        lstCol4.add("pay_amt2");
        lstCol4.add("post");
        lstCol4.add("addr1");
        lstCol4.add("addr2");
        lstCol4.add("tel");
        lstCol4.add("email");
        lstCol4.add("result_key");

        int i4 = 0;
        int j4 = 0;

        Row row4 = sheet4.createRow(i4++);

        for (String field : sArrTitle4) {
            Cell cell = row4.createCell(j4++);
            cell.setCellValue((String) field);
        }

        // 해당 셀에 데이터를 입력한다.
        Map<String, Object> mRow4 = null;
        String val4 = "";
        String colId4 = "";
        for (i4 = 0; i4 < mList4.size(); i4++) {
            mRow4 = mList4.get(i4);
            row4 = sheet4.createRow(i4+1);
            for (j4=0; j4<lstCol4.size(); ++j4) {
                colId4 = lstCol4.get(j4);
                Cell cell = row4.createCell(j4);
                val4 = CommonUtils.nvls(String.valueOf(mRow4.get(colId4)));

                cell.setCellValue(val4);
            }
        }

        /*=================================================================================================================*/
        System.out.println("=====공제 행사 회원 데이터 산출 시작[M5]=====");

        String[] sArrTitle5 = {"구분", "회원코드", "공제번호", "회원명", "행사일자", "총납입금액", "행사구분", "결과키"};

        List <String> lstCol5 = new ArrayList<String>();
        lstCol5.add("gubun");
        lstCol5.add("accnt_no");
        lstCol5.add("ded_no");
        lstCol5.add("mem_nm");
        lstCol5.add("event_proc_day");
        lstCol5.add("pay_amt");
        lstCol5.add("type");
        lstCol5.add("result_key");

        int i5 = 0;
        int j5 = 0;

        Row row5 = sheet5.createRow(i5++);

        for (String field : sArrTitle5) {
            Cell cell = row5.createCell(j5++);
            cell.setCellValue((String) field);
        }

        // 해당 셀에 데이터를 입력한다.
        Map<String, Object> mRow5 = null;
        String val5 = "";
        String colId5 = "";
        for (i5 = 0; i5 < mList5.size(); i5++) {
            mRow5 = mList5.get(i5);
            row5 = sheet5.createRow(i5+1);
            for (j5=0; j5<lstCol5.size(); ++j5) {
                colId5 = lstCol5.get(j5);
                Cell cell = row5.createCell(j5);
                val5 = CommonUtils.nvls(String.valueOf(mRow5.get(colId5)));

                cell.setCellValue(val5);
            }
        }

        /*=================================================================================================================*/
        System.out.println("=====공제 레디캐시 데이터 산출 시작[P]=====");
        createReadyCashExcel(sheetCash);
        /*=================================================================================================================*/

        /*=================================================================================================================*/
        System.out.println("=====공제 회원 정보 산출 종료=====");

        FileOutputStream outputStream = new FileOutputStream(xlFileNm);
        workbook.write(outputStream);
        outputStream.close();
    }

    /** ================================================================
     * 날짜 : 20190130
     * 이름 : 정승철
     * 추가내용 : 공제 선수금데이터 엑셀생성
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT
     * ================================================================
     * */
    public void createPayExcel(Sheet sheetPay) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar c1 = Calendar.getInstance();
        String sCurrTime = sdf.format(c1.getTime());
        System.out.println("Today : " + sCurrTime);

        Map<String, Object> hmParam = new HashMap<String, Object>();
        hmParam.put("ext_dt", sCurrTime);

        /*
        int chkCnt = DlwDeductionDAO.chkGongjeSched(hmParam); // 공제 산출스케줄 등록여부 조회

        if(chkCnt == 0) {
            System.out.println("====해당 날짜 " +sCurrTime+ " 은 공제 산출스케줄에 등록되어 있지 않습니다.");
            return;
        }
        */


        /*
        String sBasePath;
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.indexOf("windows") >= 0)
        {
            sBasePath = EgovProperties.getProperty("gongje.payData.windows.basePath");
        }
        else
        {
            sBasePath = EgovProperties.getProperty("gongje.payData.unix.basePath");
        }
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 공제 선수금 엑셀생성경로 : " + sBasePath);

        String xlFileNm = sBasePath + "DMLIFE_PAY_"+sCurrTime+"_001.xlsx";
        */

        String sAccessClassName = EgovProperties.getProperty("Globals.dmlifeway.DriverClassName");
        String sAccessIp        = EgovProperties.getProperty("Globals.dmlifeway.Url");
        String sAccessUserName  = EgovProperties.getProperty("Globals.dmlifeway.UserName");
        String sAccessPassword  = EgovProperties.getProperty("Globals.dmlifeway.Password");

        //List<Map<String, Object>> mList = DlwDeductionDAO.getPayAccnt(hmParam); // 선수금 조회 (** 매일 입금,취소된 회원 데이터 조회)

        /* PreparedStatement  설정 */
        FileOutputStream outputStream = null;
        Connection connectionTypeA = null;
        PreparedStatement preparedStatementTypeA = null ;
        ResultSet rsTypeA = null;

        /*쿼리 진행 */
        String sqlStatementTypeA =  "";
        sqlStatementTypeA += " SELECT /* DlwDeductionMap.getPayAccnt */  \n" ;
        sqlStatementTypeA += "			'21' GUBUN,  \n";
        sqlStatementTypeA += "          EXT.ACCNT_NO,  \n" ;
        sqlStatementTypeA += "          (SELECT DED_NO FROM MEM_PROD_ACCNT WHERE ACCNT_NO = EXT.ACCNT_NO AND DEL_FG = 'N') AS DED_NO,  \n";
        sqlStatementTypeA += "          SUBSTR(NVL(SI_DATE,TO_CHAR(SYSDATE,'YYYYMMDD')),0,6) AS YYMM,  \n";
        sqlStatementTypeA += "          NVL(SI_DATE,TO_CHAR(SYSDATE,'YYYYMMDD'))  AS PAY_DT,  \n";
        sqlStatementTypeA += "          EXT.PAY_NO AS NO,  \n";
        sqlStatementTypeA += "          TO_CHAR(PND.AMT) AS PAY_AMT,  \n";
        sqlStatementTypeA += "                 '10' AS PAY_TYPE,  \n";
        sqlStatementTypeA += "                        CASE WHEN EXT.EXT_INFO = '01' THEN '수금'  \n";
        sqlStatementTypeA += "                            WHEN EXT.EXT_INFO = '04' THEN '자동이체'  \n";
        sqlStatementTypeA += "                 WHEN EXT.EXT_INFO = '06' THEN '카드'  \n";
        sqlStatementTypeA += "                 ELSE '기타' END TYPE,  \n";
        sqlStatementTypeA += "                 EXT.RESULT_KEY  \n";
        sqlStatementTypeA += "   FROM TB_GONGJE_DAY_EXT EXT INNER JOIN  \n";
        sqlStatementTypeA += "           TB_MEMBER_BASIC_INFO_DAY MBID ON EXT.ACCNT_NO = MBID.ACCNT_NO LEFT OUTER JOIN  \n";
        sqlStatementTypeA += "           PRODUCT_NO_DATA PND ON MBID.PROD_CD = PND.PROD_CD AND NO = EXT.PAY_NO  \n";
        sqlStatementTypeA += "   WHERE 1=1  \n";
        sqlStatementTypeA += "   	AND EXT.CL = 'P'  \n";
        sqlStatementTypeA += "       AND GBIT = '00'  \n";
        sqlStatementTypeA += "   UNION ALL  \n";
        sqlStatementTypeA += "   /* 매일 취소된 회원 데이터 조회 */  \n";
        sqlStatementTypeA += "   SELECT  \n";
        sqlStatementTypeA += "   	'21' GUBUN,  \n";
        sqlStatementTypeA += "       EXT.ACCNT_NO,  \n";
        sqlStatementTypeA += "       (SELECT DED_NO FROM MEM_PROD_ACCNT WHERE ACCNT_NO = EXT.ACCNT_NO AND DEL_FG = 'N') AS DED_NO,  \n";
        sqlStatementTypeA += "       SUBSTR(TO_CHAR(SYSDATE,'YYYYMMDD'),0,6) AS YYMM,  \n";
        sqlStatementTypeA += "       NVL(SI_DATE,TO_CHAR(SYSDATE,'YYYYMMDD'))  AS PAY_DT,  \n";
        sqlStatementTypeA += "       EXT.PAY_NO AS NO,  \n";
        sqlStatementTypeA += "       EXT.EXT_INFO2  AS PAY_AMT,  \n";
        sqlStatementTypeA += "       '21' AS PAY_TYPE,  \n";
        sqlStatementTypeA += "       '기타' TYPE,  \n";
        sqlStatementTypeA += "       EXT.RESULT_KEY  \n";
        sqlStatementTypeA += "   FROM TB_GONGJE_DAY_EXT EXT INNER JOIN  \n";
        sqlStatementTypeA += "           TB_MEMBER_BASIC_INFO_DAY MBID ON EXT.ACCNT_NO = MBID.ACCNT_NO  \n";
        sqlStatementTypeA += "   WHERE 1=1  \n";
        sqlStatementTypeA += "       AND EXT.CL = 'P_C'  \n";
        sqlStatementTypeA += "       AND GBIT = '00'     ";

        try
          {
             Class.forName(sAccessClassName);
             connectionTypeA = DriverManager.getConnection(sAccessIp, sAccessUserName, sAccessPassword);
             preparedStatementTypeA = connectionTypeA.prepareStatement(sqlStatementTypeA) ;
             rsTypeA = preparedStatementTypeA.executeQuery();

             String[] sArrTitle = {"구분", "회원번호", "공제번호", "정산년월", "납입일자", "납입회차", "납입금액", "납입구분"
                     , "납입방법", "결과키"};

             List <String> lstCol = new ArrayList<String>();
             lstCol.add("gubun");
             lstCol.add("accnt_no");
             lstCol.add("ded_no");
             lstCol.add("yymm");
             lstCol.add("pay_dt");
             lstCol.add("no");
             lstCol.add("pay_amt");
             lstCol.add("pay_type");
             lstCol.add("type");
             lstCol.add("result_key");

	         int i = 0;
	         int j = 0;

	         Row row = sheetPay.createRow(i++);

               String val = "";
               String colId = "";

               for (String field : sArrTitle) {
                   Cell cell = row.createCell(j++);
                   cell.setCellValue((String) field);
               }

              while(rsTypeA.next())
               {
                   row = sheetPay.createRow(i);
                   for (j=0; j<lstCol.size(); ++j) {
                       colId = lstCol.get(j);
                       Cell cell = row.createCell(j);
                       val = CommonUtils.nvls(String.valueOf(rsTypeA.getString(colId)));
                     cell.setCellValue(val);
                   }
                   i++;
               }

              //System.out.println("@@@@@ : rs 커서 인덱스 값 : " + rsTypeA.getRow());
              System.out.println("@@@@@ : 선수금(21) createRow 수 : " + i);

             rsTypeA.close();
             preparedStatementTypeA.close();
             connectionTypeA.close();

              System.out.println("====PAY엑셀 리스트 작성 끝=====");

              SimpleDateFormat endPayExcel_sdf = new SimpleDateFormat("HH:mm:ss");
              String endPayExcel_Time = endPayExcel_sdf.format(c1.getTime());
              System.out.println("@@@ PAY엑셀 리스트 작성 [종료시간] : " + endPayExcel_Time);

            //outputStream = new FileOutputStream(xlFileNm);
            //wb.write(outputStream);
            //outputStream.close();
          }
         catch(Exception e) {
               e.printStackTrace();
          }
          finally
          {
               if(outputStream != null)
               {
                   //outputStream.close();
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
    }

    /** ================================================================
     * 날짜 : 20190130
     * 이름 : 정승철
     * 추가내용 : 공제 선수금데이터 엑셀생성
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT
     * ================================================================
     * */
    public void createReadyCashExcel(Sheet sheetCash) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar c1 = Calendar.getInstance();
        String sCurrTime = sdf.format(c1.getTime());
        System.out.println("Today : " + sCurrTime);

        Map<String, Object> hmParam = new HashMap<String, Object>();
        hmParam.put("ext_dt", sCurrTime);

        String sAccessClassName = EgovProperties.getProperty("Globals.dmlifeway.DriverClassName");
        String sAccessIp        = EgovProperties.getProperty("Globals.dmlifeway.Url");
        String sAccessUserName  = EgovProperties.getProperty("Globals.dmlifeway.UserName");
        String sAccessPassword  = EgovProperties.getProperty("Globals.dmlifeway.Password");

        /* PreparedStatement  설정 */
        FileOutputStream outputStream = null;
        Connection connectionTypeA = null;
        PreparedStatement preparedStatementTypeA = null ;
        ResultSet rsTypeA = null;

        /*쿼리 진행 */
        String sqlStatementTypeA =  "";
        sqlStatementTypeA += " SELECT \n" ;
        sqlStatementTypeA += "    '24' AS GUBUN,  \n" ;
        sqlStatementTypeA += "    ACCNT_NO,  \n" ;
        sqlStatementTypeA += "    (SELECT DED_NO FROM MEM_PROD_ACCNT WHERE ACCNT_NO = EXT.ACCNT_NO AND DEL_FG = 'N') AS DED_NO,  \n" ;
        sqlStatementTypeA += " EXT.SI_DATE AS EVENT_PROC_DAY,  \n" ;
        sqlStatementTypeA += " CASE WHEN CL = 'C' THEN '10' ELSE '20' END AS TYPE,  \n" ;
        sqlStatementTypeA += " EXT.EXT_INFO2 AS READY_CASH,  \n" ;
        sqlStatementTypeA += " '' AS ETC ,  \n" ;
        sqlStatementTypeA += " EXT.RESULT_KEY  \n" ;
        sqlStatementTypeA += " FROM TB_GONGJE_DAY_EXT EXT  \n" ;
        sqlStatementTypeA += " WHERE 1=1  \n" ;
        sqlStatementTypeA += " AND EXT.CL IN ('C','C_C')  \n" ;
        sqlStatementTypeA += " AND GBIT = '00'  " ;

        try
          {
             Class.forName(sAccessClassName);
             connectionTypeA = DriverManager.getConnection(sAccessIp, sAccessUserName, sAccessPassword);
             preparedStatementTypeA = connectionTypeA.prepareStatement(sqlStatementTypeA) ;
             rsTypeA = preparedStatementTypeA.executeQuery();

             String[] sArrTitle = {"구분", "소비자번호", "공제번호", "처리일자", "포인트유형", "포인트금액", "기타", "SEQ_NO"};

             List <String> lstCol = new ArrayList<String>();
             lstCol.add("gubun");
             lstCol.add("accnt_no");
             lstCol.add("ded_no");
             lstCol.add("event_proc_day");
             lstCol.add("type");
             lstCol.add("ready_cash");
             lstCol.add("etc");
             lstCol.add("result_key");

             int i = 0;
             int j = 0;

  	         Row row = sheetCash.createRow(i++);

               String val = "";
               String colId = "";

               for (String field : sArrTitle) {
                   Cell cell = row.createCell(j++);
                   cell.setCellValue((String) field);
               }

              while(rsTypeA.next())
               {
                   row = sheetCash.createRow(i);
                   for (j=0; j<lstCol.size(); ++j) {
                       colId = lstCol.get(j);
                       Cell cell = row.createCell(j);
                       val = CommonUtils.nvls(String.valueOf(rsTypeA.getString(colId)));
                     cell.setCellValue(val);
                   }
                   i++;
               }

              System.out.println("@@@@@ : 레디캐시(24) createRow 수 : " + i);
              System.out.println("====레디캐시엑셀 리스트 작성 끝=====");
          }
         catch(Exception e) {
               e.printStackTrace();
          }
          finally
          {
               if(outputStream != null)
               {
                   //outputStream.close();
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
    }

    /** ================================================================
     * 날짜 : 20190528
     * 이름 : 임동진
     * 추가내용 : 공제 신규 회원 엑셀 생성
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT
     * ================================================================
     * */
    public void createNewMemberExcel(Sheet sheet1) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar c1 = Calendar.getInstance();
        String sCurrTime = sdf.format(c1.getTime());
        System.out.println("Today : " + sCurrTime);

        Map<String, Object> hmParam = new HashMap<String, Object>();
        hmParam.put("ext_dt", sCurrTime);

        String sAccessClassName = EgovProperties.getProperty("Globals.dmlifeway.DriverClassName");
        String sAccessIp        = EgovProperties.getProperty("Globals.dmlifeway.Url");
        String sAccessUserName  = EgovProperties.getProperty("Globals.dmlifeway.UserName");
        String sAccessPassword  = EgovProperties.getProperty("Globals.dmlifeway.Password");

        /* PreparedStatement  설정 */
        FileOutputStream outputStream = null;
        Connection connectionTypeA = null;
        PreparedStatement preparedStatementTypeA = null ;
        ResultSet rsTypeA = null;

        /*쿼리 진행 */
        String sqlStatementTypeA =  "";
        sqlStatementTypeA += " SELECT  \n" ;
        sqlStatementTypeA += "    '01' GUBUN, \n" ;
        sqlStatementTypeA += "	        EXT.ACCNT_NO, \n" ;
		sqlStatementTypeA += "	        MB.MEM_NM, \n" ;
		sqlStatementTypeA += " CASE WHEN LENGTH(NVL(MB.IDN_NO,' ')) != 10 AND SUBSTR(MB.BRTH_MON_DAY,1,2) = '19' AND MB.SEX = '0001' THEN SUBSTR(MB.BRTH_MON_DAY,3,6) || '1000000' \n" ;
		sqlStatementTypeA += " WHEN LENGTH(NVL(MB.IDN_NO,' ')) != 10 AND SUBSTR(MB.BRTH_MON_DAY,1,2) = '19' AND MB.SEX = '0002' THEN SUBSTR(MB.BRTH_MON_DAY,3,6) || '2000000' \n" ;
		sqlStatementTypeA += "	            WHEN LENGTH(NVL(MB.IDN_NO,' ')) != 10 AND SUBSTR(MB.BRTH_MON_DAY,1,2) = '20' AND MB.SEX = '0001' THEN SUBSTR(MB.BRTH_MON_DAY,3,6) || '3000000' \n" ;
		sqlStatementTypeA += " WHEN LENGTH(NVL(MB.IDN_NO,' ')) != 10 AND SUBSTR(MB.BRTH_MON_DAY,1,2) = '20' AND MB.SEX = '0002' THEN SUBSTR(MB.BRTH_MON_DAY,3,6) || '4000000' \n" ;
		sqlStatementTypeA += " WHEN LENGTH(NVL(MB.IDN_NO,' ')) = 10 THEN MB.IDN_NO \n" ;
		sqlStatementTypeA += " ELSE '' END BRTH_MON_DAY , \n" ;
		sqlStatementTypeA += " MBID.JOIN_DT, \n" ;
		sqlStatementTypeA += " PD.PROD_NM, \n" ;
		sqlStatementTypeA += " PD.PROD_AMT, \n" ;
		sqlStatementTypeA += " PD.EXPR_NO, \n" ;
		sqlStatementTypeA += " TO_CHAR(ADD_MONTHS(ADD_MONTHS(MBID.JOIN_DT, PD.EXPR_NO - MBID.NEW_CHAN_GUNSU), -1), 'YYYYMMDD') AS MDATE, \n" ;
		sqlStatementTypeA += " NVL(REQ.PAY_MTHD,'01') AS CDAY, \n" ;
		sqlStatementTypeA += " (CASE WHEN MBID.STAT = '23' THEN EXT.EXT_INFO2 ELSE TO_CHAR(PD.MON_PAY_AMT) END) AS MON_PAY_AMT, \n" ;
		sqlStatementTypeA += " TO_CHAR(SYSDATE,'YYYYMM') AS YYYYMM, \n" ;
		sqlStatementTypeA += " JOIN_DT AS PAY_DT, \n" ;
		sqlStatementTypeA += " EXT.PAY_NO AS PAY_NUM,  \n" ;
		sqlStatementTypeA += " (CASE WHEN MBID.STAT = '23' THEN EXT.EXT_INFO2 ELSE TO_CHAR(NVL((SELECT SUM(AMT) FROM PRODUCT_NO_DATA WHERE PROD_CD = MBID.PROD_CD AND NO <=  PAY_NO),0)) END) AS PAY_AMT, \n" ;
		sqlStatementTypeA += " CASE WHEN NVL(MB.HOME_ZIP,'N') != 'N' THEN MB.HOME_ZIP ELSE MB.WRPL_ZIP END POST, \n" ;
		sqlStatementTypeA += " CASE WHEN NVL(MB.HOME_ADDR,'N') != 'N' THEN MB.HOME_ADDR ELSE MB.WRPL_ADDR END ADDR1,  \n" ;
		sqlStatementTypeA += " CASE WHEN NVL(MB.HOME_ADDR2,'N') != 'N' THEN MB.HOME_ADDR2 ELSE MB.WRPL_ADDR2 END ADDR2, \n" ;
		sqlStatementTypeA += " CASE WHEN NVL(MB.CELL,'N') != 'N' THEN MB.CELL ELSE '010-0000-0000' END TEL, \n" ;
		sqlStatementTypeA += " MB.EMAIL, \n" ;
		sqlStatementTypeA += " EXT.RESULT_KEY \n" ;
		sqlStatementTypeA += " FROM TB_GONGJE_DAY_EXT EXT INNER JOIN \n" ;
		sqlStatementTypeA += " LF_DMUSER.TB_MEMBER_BASIC_INFO_DAY MBID ON EXT.ACCNT_NO = MBID.ACCNT_NO INNER JOIN \n" ;
		sqlStatementTypeA += " MEMBER MB ON MBID.MEM_NO = MB.MEM_NO AND MB.DEL_FG = 'N' INNER JOIN \n" ;
		sqlStatementTypeA += " PRODUCT PD ON MBID.PROD_CD = PD.PROD_CD LEFT OUTER JOIN \n" ;
		sqlStatementTypeA += " TB_MEMBER_WDRW_REQ REQ ON MBID.ACCNT_NO = REQ.ACCNT_NO AND REQ.DEL_FG = 'C' AND REQ.REQ_PAY_NO = 1 \n" ;
		sqlStatementTypeA += " WHERE 1=1 \n" ;
		sqlStatementTypeA += " AND EXT.CL = 'D' \n" ;
		sqlStatementTypeA += " AND GBIT = '00' " ;

        try
          {
             Class.forName(sAccessClassName);
             connectionTypeA = DriverManager.getConnection(sAccessIp, sAccessUserName, sAccessPassword);
             preparedStatementTypeA = connectionTypeA.prepareStatement(sqlStatementTypeA) ;
             rsTypeA = preparedStatementTypeA.executeQuery();

             String[] sArrTitle = {"구분", "회원번호", "회원명", "생년월일", "가입일자", "상품명", "상품가격", "만기회차", "만기일자"
                     , "이체일", "월납입금액", "정산년월", "납입일자", "납입회차", "납입금액", "우편번호", "주소"
                     , "상세주소", "전화번호", "이메일", "결과키"};

             List <String> lstCol = new ArrayList<String>();
             lstCol.add("gubun");
             lstCol.add("accnt_no");
             lstCol.add("mem_nm");
             lstCol.add("brth_mon_day");
             lstCol.add("join_dt");
             lstCol.add("prod_nm");
             lstCol.add("prod_amt");
             lstCol.add("expr_no");
             lstCol.add("mdate");
             lstCol.add("cday");
             lstCol.add("mon_pay_amt");
             lstCol.add("yyyymm");
             lstCol.add("pay_dt");
             lstCol.add("pay_num");
             lstCol.add("pay_amt");
             lstCol.add("post");
             lstCol.add("addr1");
             lstCol.add("addr2");
             lstCol.add("tel");
             lstCol.add("email");
             lstCol.add("result_key");

               int i = 0;
               int j = 0;

  	         Row row = sheet1.createRow(i++);

               String val = "";
               String colId = "";

               for (String field : sArrTitle) {
                   Cell cell = row.createCell(j++);
                   cell.setCellValue((String) field);
               }

              while(rsTypeA.next())
               {
                   row = sheet1.createRow(i);
                   for (j=0; j<lstCol.size(); ++j) {
                       colId = lstCol.get(j);
                       Cell cell = row.createCell(j);
                       val = CommonUtils.nvls(String.valueOf(rsTypeA.getString(colId)));
                     cell.setCellValue(val);
                   }
                   i++;
               }

              System.out.println("@@@@@ : 신규등록(24) createRow 수 : " + i);
              System.out.println("====레디캐시엑셀 리스트 작성 끝=====");
          }
         catch(Exception e) {
               e.printStackTrace();
          }
          finally
          {
               if(outputStream != null)
               {
                   //outputStream.close();
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
    }

    /** ================================================================
     * 날짜 : 20190130
     * 이름 : 정승철
     * 추가내용 : 공제 산출 스케줄 조회
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_EXT_SCHED
     * ================================================================
     * */
    public List<Map<String, Object>> getGongjeExtDt(Map<String, ?> pmParam) throws Exception {
        return DlwDeductionDAO.getGongjeExtDt(pmParam);
    }

    /** ================================================================
     * 날짜 : 20190207
     * 이름 : 정승철
     * 추가내용 : 공제 전송여부 카운트조회
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT
     * ================================================================
     * */
    public int getGongjeSendCnt(Map<String, ?> pmParam) throws Exception {
        return DlwDeductionDAO.getGongjeSendCnt(pmParam);
    }

    /** ================================================================
     * 날짜 : 20190208
     * 이름 : 정승철
     * 추가내용 : 공제산출스케줄 입력유효성체크
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_EXT_SCHED
     * ================================================================
     * */
    public int chkGongjeExtSched(Map<String, ?> pmParam) throws Exception {
        return DlwDeductionDAO.chkGongjeExtSched(pmParam);
    }

    /** ================================================================
     * 날짜 : 20190408
     * 이름 : 정승철
     * 추가내용 : 공제산출스케줄 입력유효성체크 (** 공제결과 미처리 조회)
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_EXT_SCHED
     * ================================================================
     * */
    public int chkGongjeExtSched_rsProcYn() throws Exception {
        return DlwDeductionDAO.chkGongjeExtSched_rsProcYn();
    }

    /** ================================================================
     * 날짜 : 20190208
     * 이름 : 정승철
     * 추가내용 : 공제산출스케줄 저장
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_EXT_SCHED
     * ================================================================
     * */
    public void saveGongjeExtSched(Map<String, ?> pmParam) throws Exception {
        DlwDeductionDAO.saveGongjeExtSched(pmParam);
    }

    /** ================================================================
     * 날짜 : 20190208
     * 이름 : 정승철
     * 추가내용 : 공제산출스케줄 삭제
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_EXT_SCHED
     * ================================================================
     * */
    public void delGongjeExtSched(Map<String, ?> pmParam) throws Exception {
        DlwDeductionDAO.delGongjeExtSched(pmParam);
    }

    /** ================================================================
     * 날짜 : 20190208
     * 이름 : 정승철
     * 추가내용 : 진행중인 공제데이터 조회
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT
     * ================================================================
     * */
    public List<Map<String, Object>> getGongjeSendingList(Map<String, ?> pmParam) throws Exception {
        return DlwDeductionDAO.getGongjeSendingList(pmParam);
    }

    /** ================================================================
     * 날짜 : 20190208
     * 이름 : 정승철
     * 추가내용 : 공제구분별 최종결과반영 조회
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT
     * ================================================================
     * */
    public List<Map<String, Object>> getGongjeTypeLastResultList(Map<String, ?> pmParam) throws Exception {
        return DlwDeductionDAO.getGongjeTypeLastResultList(pmParam);
    }

    /** ================================================================
     * 날짜 : 20190207
     * 이름 : 송준빈
     * 추가내용 : 공제 데이터 결과 반영 (DMLIFE_PAY 파일)
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT_RESULT
     * ================================================================
     * */

    public void insertGongjeDmlifePayFileUpload(HttpServletRequest request, HttpServletResponse response, Map<String, Object> mResult) throws Exception {
        String tempDir = System.getProperty("java.io.tmpdir");
        MultipartRequest multipartRequest = new MultipartRequest(request, tempDir, 1024 * 1024 * 50);
        Enumeration enm = multipartRequest.getFileNames();

        BufferedReader br = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null ;

         String sAccessClassName = EgovProperties.getProperty("Globals.dmlifeway.DriverClassName");
         String sAccessIp        = EgovProperties.getProperty("Globals.dmlifeway.Url");
         String sAccessUserName  = EgovProperties.getProperty("Globals.dmlifeway.UserName");
         String sAccessPassword  = EgovProperties.getProperty("Globals.dmlifeway.Password");

         System.out.println("프로퍼티[1] :: " + sAccessClassName + "," + "프로퍼티[2] :: " + sAccessIp + "," +
                            "프로퍼티[3] :: " + sAccessUserName + "," + "프로퍼티[4] :: " + sAccessPassword);

        String sqlStatement = "INSERT INTO LF_DMUSER.TB_GONGJE_DAY_EXT_RESULT(GDATE, FILE_TYPE, RESULT_KEY, ACCNT_NO, GUAR_NO, WORK_RESULT, ERR_MSG, GUBUN, CODE,";
               sqlStatement += "MEMB_NO, LIMIT_VALUE, SEQ_NO, PAY_NO, PAY_TYPE, REG_MAN, REG_DM, UPD_MAN, UPD_DM, DEL_FG)";
               sqlStatement += "VALUES (?, 'P', ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE, '', '', 'N')";

        Class.forName(sAccessClassName);
        connection = DriverManager.getConnection(sAccessIp, sAccessUserName, sAccessPassword);
        connection.setAutoCommit(false);
        preparedStatement = connection.prepareStatement(sqlStatement);
        int iDataIdx = 0;

        try
        {
            while (enm.hasMoreElements())
            {
                String sKey = (String)enm.nextElement();

                File fileUploadElement = multipartRequest.getFile(sKey);

                //입력 버퍼 생성
                br  =  new BufferedReader(new InputStreamReader(new FileInputStream(fileUploadElement),"UTF-8"));

                String bfLine = "";


                int totPramCnt = Integer.parseInt(String.valueOf(mResult.get("totPramCnt"))); // 요청 데이터 건수
                //int totExtResultCount = 0; // 결과 데이터 건수
                //String strReqday = ""; //청구 일자

                //결과 레코드 다음행 처리
                while((bfLine = br.readLine()) != null){
                    if (bfLine.length() != 0) iDataIdx++;
                }

                br.close();

                if ( totPramCnt !=  iDataIdx){
                    throw new EgovBizException("공제산출과 업로드 건수가 맞지 않습니다. " + totPramCnt+"<>" + iDataIdx);
                }

                iDataIdx = 0;
                br  =  new BufferedReader(new InputStreamReader(new FileInputStream(fileUploadElement),"UTF-8"));

                //결과 레코드 다음행 처리
                while((bfLine = br.readLine()) != null)
                {
                    if (bfLine.length() != 0) {
                        String oddLine = bfLine.replaceAll("&RET&", "");
                        oddLine = oddLine.replaceAll("RET&", "");
                        String[] arrLineData = oddLine.split("&");

                        Map<String, Object> mRow = new HashMap<String, Object>();

                        for(int idx = 0; idx < arrLineData.length; idx++)
                        {
                            String[] sKeyValues = arrLineData[idx].split("=");
                            String sKeyString = sKeyValues[0];
                            String sValue = sKeyValues[1];
                            mRow.put(sKeyString, sValue);
                            ParamUtils.addSaveParam(mRow);
                        }

                        String sGdate      = CommonUtils.nvls((String)mRow.get("RC_DT"));
                        String sResultKey  = CommonUtils.nvls((String)mRow.get("SEQ_NO"));
                        String sAccntNo    = CommonUtils.nvls((String)mRow.get("CONS_NO"));
                        String sGuarNo     = CommonUtils.nvls((String)mRow.get("GUAR_NO"));
                        String sWorkResult = CommonUtils.nvls((String)mRow.get("WORK_RESULT"));
                        String sErrMsg     = CommonUtils.nvls((String)mRow.get("ERR_MSG"));
                        String sGubun      = CommonUtils.nvls((String)mRow.get("GUBUN"));
                        String sCode       = CommonUtils.nvls((String)mRow.get("CODE"));
                        String sMembNo     = CommonUtils.nvls((String)mRow.get("MEMB_NO"));
                        String sLimitValue = CommonUtils.nvls((String)mRow.get("LIMIT_VALUE"));
                        String sSeqNo      = CommonUtils.nvls((String)mRow.get("SEQ_NO"));
                        String sPayNo      = CommonUtils.nvls((String)mRow.get("ADV_PAY_NUM"));
                        String sPayType    = CommonUtils.nvls((String)mRow.get("PAY_TYPE"));
                        String sRegMan     = CommonUtils.nvls((String)mRow.get("rgsr_id"));

                        preparedStatement.setString(1, sGdate.trim());
                        preparedStatement.setString(2, sResultKey.trim());
                        preparedStatement.setString(3, sAccntNo.trim());
                        preparedStatement.setString(4, sGuarNo.trim());
                        preparedStatement.setString(5, sWorkResult.trim());
                        preparedStatement.setString(6, sErrMsg.trim());
                        preparedStatement.setString(7, sGubun.trim());
                        preparedStatement.setString(8, sCode.trim());
                        preparedStatement.setString(9, sMembNo.trim());
                        preparedStatement.setString(10, sLimitValue.trim());
                        preparedStatement.setString(11, sSeqNo.trim());
                        preparedStatement.setString(12, sPayNo.trim());
                        preparedStatement.setString(13, sPayType.trim());
                        preparedStatement.setString(14, sRegMan.trim());

                        // addBatch에 담기
                        preparedStatement.addBatch();

                        // 파라미터 Clear
                        preparedStatement.clearParameters() ;


                        // OutOfMemory를 고려하여 만건 단위로 커밋
                        if( (iDataIdx % 10000) == 0)
                        {
                            // Batch 실행
                            preparedStatement.executeBatch();

                            // Batch 초기화
                            preparedStatement.clearBatch();

                            // 커밋
                            connection.commit();
                        }

                        iDataIdx++;
                    }

                }

                preparedStatement.executeBatch() ;
                connection.commit() ;

                br.close();
                preparedStatement.close();
                //System.out.println(totExtResultCount);

            }
        }
        catch (IOException ex) {
            br.close();
            preparedStatement.close();
            ex.printStackTrace();
            throw ex;
        }
    }

    /** ================================================================
     * 날짜 : 20190207
     * 이름 : 송준빈
     * 추가내용 : 공제 데이터 결과 반영 (DMLIFE 파일)
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT_RESULT
     * ================================================================
     * */

    public void insertGongjeDmlifeFileUpload(HttpServletRequest request, HttpServletResponse response, Map<String, Object> mResult) throws Exception {
        String sFileTempMakePath = "";
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");
        StringBuffer resultBuffer = new StringBuffer();

        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.indexOf("windows") >= 0) {
            sFileTempMakePath = EgovProperties.getProperty("gongje.tempfile.windows.basePath");
        } else {
            sFileTempMakePath = EgovProperties.getProperty("gongje.tempfile.unix.basePath");
        }

        String tempDir = System.getProperty("java.io.tmpdir");
        MultipartRequest multipartRequest = new MultipartRequest(request, tempDir, 1024 * 1024 * 50);
        Enumeration enm = multipartRequest.getFileNames();

        BufferedReader br = null;
        int iDataIdx = 0;

        try
        {
            while (enm.hasMoreElements())
            {
                String sKey = (String)enm.nextElement();

                File fileUploadElement = multipartRequest.getFile(sKey);

                String sendFileNm = fileUploadElement.getName().substring(7,15);
                //System.out.println("xxxxxxxxxx" + sendFileNm);

                //sFileTempMakePath = sFileTempMakePath + sdf.format(d) + ".tmp";
                sFileTempMakePath = sFileTempMakePath + sendFileNm + ".tmp";

                if (osName.indexOf("windows") >= 0) {
                    sFileTempMakePath = sFileTempMakePath.replaceAll("/", "\\\\");
                }

                System.out.println("업로드 원본 파일 path ::: " + fileUploadElement.getAbsolutePath());
                System.out.println("만들어진 파일 path ::: " + sFileTempMakePath);
                //CommonUtils.writeTextFile(sFileTempMakePath, resultBuffer.toString(), "MS949"); // 파일 생성 여부

                //입력 버퍼 생성
                br  =  new BufferedReader(new InputStreamReader(new FileInputStream(fileUploadElement),"UTF-8"));

                String bfLine = "";

                int totPramCnt = Integer.parseInt(String.valueOf(mResult.get("totPramCnt"))); // 요청 데이터 건수
                //int totExtResultCount = 0; // 결과 데이터 건수
                //String strReqday = ""; //청구 일자

                //결과 레코드 다음행 처리
                while((bfLine = br.readLine()) != null){
                    if (bfLine.length() != 0){
                        resultBuffer.append(bfLine);
                        iDataIdx++;
                        resultBuffer.append("\r\n"); // 한줄 엔터
                    }
                }

                br.close();

                if ( totPramCnt !=  iDataIdx){
                    throw new EgovBizException("공제산출과 업로드 건수가 맞지 않습니다. " + totPramCnt+"<>" + iDataIdx);
                }

                CommonUtils.writeTextFile(sFileTempMakePath, resultBuffer.toString(), "UTF-8"); // 파일 생성 여부

                iDataIdx = 0;
                br  =  new BufferedReader(new InputStreamReader(new FileInputStream(sFileTempMakePath),"UTF-8"));

                //@@@@preparement start
                String sAccessClassName = EgovProperties.getProperty("Globals.dmlifeway.DriverClassName");
                String sAccessIp        = EgovProperties.getProperty("Globals.dmlifeway.Url");
                String sAccessUserName  = EgovProperties.getProperty("Globals.dmlifeway.UserName");
                String sAccessPassword  = EgovProperties.getProperty("Globals.dmlifeway.Password");

                System.out.println("프로퍼티[1] :: " + sAccessClassName + "," + "프로퍼티[2] :: " + sAccessIp + "," +
                                   "프로퍼티[3] :: " + sAccessUserName + "," + "프로퍼티[4] :: " + sAccessPassword);

                Connection connection = null;
                PreparedStatement preparedStatement = null ;


                String sqlStatement = "INSERT INTO LF_DMUSER.TB_GONGJE_DAY_EXT_RESULT(GDATE, RESULT_KEY, ACCNT_NO, GUAR_NO, WORK_RESULT, ERR_MSG, GUBUN, CODE,";
                       sqlStatement += "MEMB_NO, LIMIT_VALUE, SEQ_NO, PAY_NO, PAY_TYPE, REG_MAN, REG_DM, UPD_MAN, UPD_DM, DEL_FG, FILE_TYPE)";
                       sqlStatement += "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE, '', '', 'N', ?)";

                Class.forName(sAccessClassName);
                connection = DriverManager.getConnection(sAccessIp, sAccessUserName, sAccessPassword);
                connection.setAutoCommit(false);

                preparedStatement = connection.prepareStatement(sqlStatement);
                preparedStatement.setQueryTimeout(1000);

                //결과 레코드 다음행 처리
                while((bfLine = br.readLine()) != null)
                {
                    if (bfLine.length() != 0) {
                        String oddLine = bfLine.replaceAll("&RET&", "");
                        oddLine = oddLine.replaceAll("RET&", "");

                        String[] arrLineData = oddLine.split("&");

                        Map<String, Object> mRow = new HashMap<String, Object>();

                        for(int idx = 0; idx < arrLineData.length; idx++)
                        {
                            String[] sKeyValues = arrLineData[idx].split("=");
                            String sKeyString = sKeyValues[0];
                            String sValue = sKeyValues[1];

                            mRow.put(sKeyString, sValue);
                            ParamUtils.addSaveParam(mRow);
                        }

                        /*
                        DlwDeductionDAO.insertGongjeFileUpload(mRow);
                        totExtResultCount++;
                        */

                        String sGdate      = CommonUtils.nvls((String)mRow.get("RC_DT"));
                        String sResultKey  = CommonUtils.nvls((String)mRow.get("SEQ_NO"));
                        String sAccntNo    = CommonUtils.nvls((String)mRow.get("CONS_NO"));
                        String sGuarNo     = CommonUtils.nvls((String)mRow.get("GUAR_NO"));
                        String sWorkResult = CommonUtils.nvls((String)mRow.get("WORK_RESULT"));
                        String sErrMsg     = CommonUtils.nvls((String)mRow.get("ERR_MSG"));
                        String sGubun      = CommonUtils.nvls((String)mRow.get("GUBUN"));
                        String sCode       = CommonUtils.nvls((String)mRow.get("CODE"));
                        String sMembNo     = CommonUtils.nvls((String)mRow.get("MEMB_NO"));
                        String sLimitValue = CommonUtils.nvls((String)mRow.get("LIMIT_VALUE"));
                        String sSeqNo      = CommonUtils.nvls((String)mRow.get("SEQ_NO"));
                        String sPayNo      = CommonUtils.nvls((String)mRow.get("ADV_PAY_NUM"));
                        String sPayType    = CommonUtils.nvls((String)mRow.get("PAY_TYPE"));
                        String sRegMan     = CommonUtils.nvls((String)mRow.get("rgsr_id"));
                        String sFileType   = "M";

                        //납입일 경우 파일 형식을 P로 설정
                        if (sGubun.equals("21")){
                            sFileType = "P";
                        }

                        preparedStatement.setString(1, sGdate.trim());
                        preparedStatement.setString(2, sResultKey.trim());
                        preparedStatement.setString(3, sAccntNo.trim());
                        preparedStatement.setString(4, sGuarNo.trim());
                        preparedStatement.setString(5, sWorkResult.trim());
                        preparedStatement.setString(6, sErrMsg.trim());
                        preparedStatement.setString(7, sGubun.trim());
                        preparedStatement.setString(8, sCode.trim());
                        preparedStatement.setString(9, sMembNo.trim());
                        preparedStatement.setString(10, sLimitValue.trim());
                        preparedStatement.setString(11, sSeqNo.trim());
                        preparedStatement.setString(12, sPayNo.trim());
                        preparedStatement.setString(13, sPayType.trim());
                        preparedStatement.setString(14, sRegMan.trim());
                        preparedStatement.setString(15, sFileType);

                        // addBatch에 담기
                        preparedStatement.addBatch();

                        // 파라미터 Clear
                        preparedStatement.clearParameters() ;


                        // OutOfMemory를 고려하여 만건 단위로 커밋
                        if( (iDataIdx % 5000) == 0)
                        {
                            // Batch 실행
                            preparedStatement.executeBatch();

                            // Batch 초기화
                            preparedStatement.clearBatch();

                            // 커밋
                            connection.commit();
                        }
                        iDataIdx++;
                    }
                }
                preparedStatement.executeBatch() ;
                connection.commit() ;

                br.close();
                preparedStatement.close();
                connection.close();

                // 작업 처리후 임시 파일은 삭제한다.
                File deleteTargetsFileTempPath = new File(sFileTempMakePath);
                if(deleteTargetsFileTempPath.exists() == true)
                {
                    //deleteTargetsFileTempPath.delete();
                }
                //System.out.println(totExtResultCount);
            }
        }
        catch (IOException ex) {
            br.close();
            ex.printStackTrace();
            throw ex;
        }
    }

    /** ================================================================
     * 날짜 : 20190213
     * 이름 : 임동진
     * 추가내용 : 공제 결과 업데이트
     * ================================================================
     * */
    public int uptGongResultStat(Map<String, ?> pmParam) throws Exception {
        return DlwDeductionDAO.uptGongResultStat(pmParam);
    }

    /** ================================================================
     * 날짜 : 20190214
     * 이름 : 송준빈
     * 추가내용 : 공제 데이터 전송이력 조회수
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT, LF_DMUSER.TB_GONGJE_DAY_EXT_RESULT
     * ================================================================
     * */
    public int getGongjeDayExtSendHistCount(Map<String, ?> pmParam) throws Exception {
        return DlwDeductionDAO.getGongjeDayExtSendHistCount(pmParam);
    }

    /** ================================================================
     * 날짜 : 20190214
     * 이름 : 송준빈
     * 추가내용 : 공제 데이터 전송이력 리스트
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT, LF_DMUSER.TB_GONGJE_DAY_EXT_RESULT
     * ================================================================
     * */
    public List<Map<String, Object>> getGongjeDayExtSendHist(Map<String, ?> pmParam) throws Exception {
        return DlwDeductionDAO.getGongjeDayExtSendHist(pmParam);
    }

    /** ================================================================
     * 날짜 : 20190219
     * 이름 : 송준빈
     * 추가내용 : 공제 신고 현황 데이터 (ASIS) 조회수
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_ASIS_DATA
     * ================================================================
     * */
    public int getGongjeAsisDataCount(Map<String, ?> pmParam) throws Exception {
        return DlwDeductionDAO.getGongjeAsisDataCount(pmParam);
    }

    /** ================================================================
     * 날짜 : 20190219
     * 이름 : 송준빈
     * 추가내용 : 공제 데이터 전송이력 (ASIS) 리스트
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_ASIS_DATA
     * ================================================================
     * */
    public List<Map<String, Object>> getGongjeAsisDataList(Map<String, ?> pmParam) throws Exception {
        return DlwDeductionDAO.getGongjeAsisDataList(pmParam);
    }

    /** ================================================================
     * 날짜 : 20190313
     * 이름 : 송준빈
     * 추가내용 : 공제 데이터 전송이력 (ASIS) 리스트 정상고객 총금액
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_ASIS_DATA
     * ================================================================
     * */
    public List<Map<String, Object>> getGongjeAsisDataListSummary(Map<String, ?> pmParam) throws Exception {
        return DlwDeductionDAO.getGongjeAsisDataListSummary(pmParam);
    }

    /** ================================================================
     * 날짜 : 20190219
     * 이름 : 송준빈
     * 추가내용 :  공제 데이터 전송이력 (ASIS) 추가
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_ASIS_DATA
     * ================================================================
     * */
    public int insertGongjeAsisDataList(Map<String, ?> pmParam) throws Exception {
        return DlwDeductionDAO.insertGongjeAsisDataList(pmParam);
    }

    /** ================================================================
     * 날짜 : 20190219
     * 이름 : 송준빈
     * 추가내용 : 공제 데이터 전송이력 (ASIS) 수정
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_ASIS_DATA
     * ================================================================
     * */
    public int updateGongjeAsisDataList(Map<String, ?> pmParam) throws Exception {
        return DlwDeductionDAO.updateGongjeAsisDataList(pmParam);
    }

    /** ================================================================
     * 날짜 : 20190219
     * 이름 : 송준빈
     * 추가내용 : 공제 데이터 전송이력 (ASIS) 삭제
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_ASIS_DATA
     * ================================================================
     * */
    public int deleteGongjeAsisDataList(Map<String, ?> pmParam) throws Exception {
        return DlwDeductionDAO.deleteGongjeAsisDataList(pmParam);
    }

    /** ================================================================
     * 날짜 : 20190220
     * 이름 : 정승철
     * 추가내용 : 공제 기존 누적 선수금 조회
     * 대상 테이블 : LF_DMUSER.GONGJE_MG
     * ================================================================
     * */
    public String getGongjeBefTotalAmt(Map<String, ?> pmParam) throws Exception {
        return DlwDeductionDAO.getGongjeBefTotalAmt(pmParam);
    }

    /** ================================================================
     * 날짜 : 20190220
     * 이름 : 정승철
     * 추가내용 : 공제 월별 보고서 선수금 및 구분별 조회
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT
     * ================================================================
     * */
    public List<Map<String, Object>> getGongjeMonthReport(Map<String, ?> pmParam) throws Exception {
        return DlwDeductionDAO.getGongjeMonthReport(pmParam);
    }

    /** ================================================================
     * 날짜 : 20190220
     * 이름 : 정승철
     * 추가내용 : 공제 월별 보고서 구분별 카운트조회
     * ================================================================
     * */
    public List<Map<String, Object>> getCntGongjeMonthReport(Map<String, ?> pmParam) throws Exception {
        return DlwDeductionDAO.getCntGongjeMonthReport(pmParam);
    }

    /** ================================================================
     * 날짜 : 20190311
     * 이름 : 정승철
     * 추가내용 : 공제 월별 보고서 마감처리
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_CLOSE
     * ================================================================
     * */
    public int insertGongjeClose(Map<String, ?> pmParam) throws Exception {
        return DlwDeductionDAO.insertGongjeClose(pmParam);
    }

    /** ================================================================
     * 날짜 : 20190312
     * 이름 : 정승철
     * 추가내용 : 공제 마감처리할 정산년월 조회
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_CLOSE
     * ================================================================
     * */
    public String getNextGongjeCloseYYYYMM() throws Exception {
        return DlwDeductionDAO.getNextGongjeCloseYYYYMM();
    }

    /** ================================================================
     * 날짜 : 20190319
     * 이름 : 정승철
     * 추가내용 : 공제데이터산출 및 엑셀생성
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT
     * ================================================================
     * 처리순서)
     * 1. 공제산출일 체크
     *   1-1. 공제데이터산출 (TB_GONEJE_DAY_EXT INSERT)
     *   1-2. 엑셀생성
     *   1-3. 공제 에이전트를 통한 파일전송 (** 2019.05.21 추가)
     * ================================================================
     * */
    public void ProcessGongjeSche() throws Exception {

        // 1. 공제산출일 체크
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar c1 = Calendar.getInstance();
        String sCurrTime = sdf.format(c1.getTime());
        System.out.println("Today : " + sCurrTime);

        Map<String, Object> hmParam = new HashMap<String, Object>();
        hmParam.put("ext_dt", sCurrTime);

        int chkCnt = DlwDeductionDAO.chkGongjeSched(hmParam); // 공제 산출스케줄 등록여부 조회

        if(chkCnt == 0) {
            System.out.println("====해당 날짜 " +sCurrTime+ " 은 공제 산출스케줄에 등록되어 있지 않습니다.");
            return;
        }

        // 1-1. 공제데이터산출 (TB_GONEJE_DAY_EXT INSERT)
        DlwDeductionDAO.insertGongjeDayExt();

        // 1-2. 엑셀생성
        String sBasePath;
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.indexOf("windows") >= 0)
        {
            sBasePath = EgovProperties.getProperty("gongje.basicData.windows.basePath");
        }
        else
        {
            sBasePath = EgovProperties.getProperty("gongje.basicData.unix.basePath");
        }
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 공제 일반 엑셀생성경로 : " + sBasePath);
        String xlFileNm = sBasePath + "DMLIFE_"+sCurrTime+".xlsx";
        log.debug(">xlFileNm : " + xlFileNm);

        //////////////////////////////////////////////////////////////////////////////////////////////////
        List<Map<String, Object>> mList  = DlwDeductionDAO.getGongjeDList(hmParam); // 신규/타사
        List<Map<String, Object>> mList2 = DlwDeductionDAO.getGongjeRList(hmParam); // 해약
        List<Map<String, Object>> mList3 = DlwDeductionDAO.getGongjeUList(hmParam); // 정보변경
        List<Map<String, Object>> mList4 = DlwDeductionDAO.getGongjeTList(hmParam); // 양도양수
        List<Map<String, Object>> mList5 = DlwDeductionDAO.getGongjeEList(hmParam); // 행사


        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheetPay = workbook.createSheet("21_00"); // 공제 납입 데이터 산출 시작
        XSSFSheet sheet1 = workbook.createSheet("01_00"); // 신규/타사 sheet
        XSSFSheet sheet2 = workbook.createSheet("31_00"); // 해약 sheet
        XSSFSheet sheet3 = workbook.createSheet("51_00"); // 정보변경 sheet
        XSSFSheet sheet4 = workbook.createSheet("41_00"); // 양도양수 sheet
        XSSFSheet sheet5 = workbook.createSheet("52_00"); // 행사 sheet
        XSSFSheet sheetCash = workbook.createSheet("24_00"); // 레디캐시 sheet

        /*=================================================================================================================*/

        System.out.println("=====공제 납입 데이터 산출 시작[P]=====");

        SimpleDateFormat startPayExcel_sdf = new SimpleDateFormat("HH:mm:ss");
        String startPayExcel_Time = startPayExcel_sdf.format(c1.getTime());
        System.out.println("@@@ PAY엑셀 리스트 작성 [시작시간] : " + startPayExcel_Time);

        //createPayExcel(sheetPay);

        /*=================================================================================================================*/
        System.out.println("=====공제 신규회원 데이터 산출 시작[M1]=====");
        String[] sArrTitle = {"구분", "회원번호", "회원명", "생년월일", "가입일자", "상품명", "상품가격", "만기회차", "만기일자"
                              , "이체일", "월납입금액", "정산년월", "납입일자", "납입회차", "납입금액", "우편번호", "주소"
                              , "상세주소", "전화번호", "이메일", "결과키"};

        List <String> lstCol = new ArrayList<String>();
        lstCol.add("gubun");
        lstCol.add("accnt_no");
        lstCol.add("mem_nm");
        lstCol.add("brth_mon_day");
        lstCol.add("join_dt");
        lstCol.add("prod_nm");
        lstCol.add("prod_amt");
        lstCol.add("expr_no");
        lstCol.add("mdate");
        lstCol.add("cday");
        lstCol.add("mon_pay_amt");
        lstCol.add("yyyymm");
        lstCol.add("pay_dt");
        lstCol.add("pay_num");
        lstCol.add("pay_amt");
        lstCol.add("post");
        lstCol.add("addr1");
        lstCol.add("addr2");
        lstCol.add("tel");
        lstCol.add("email");
        lstCol.add("result_key");

        int i = 0;
        int j = 0;
        Row row = sheet1.createRow(i++);

        for (String field : sArrTitle) {
            Cell cell = row.createCell(j++);
            cell.setCellValue((String) field);
        }

        // 해당 셀에 데이터를 입력한다.
        Map<String, Object> mRow = null;
        String val = "";
        String colId = "";
        for (i = 0; i < mList.size(); i++) {
            mRow = mList.get(i);
            row = sheet1.createRow(i+1);
            for (j=0; j<lstCol.size(); ++j) {
                colId = lstCol.get(j);
                Cell cell = row.createCell(j);
                val = CommonUtils.nvls(String.valueOf(mRow.get(colId)));

                cell.setCellValue(val);
            }
        }
        
        /*=================================================================================================================*/
        System.out.println("=====공제 정보변경 회원 데이터 산출 시작[M2]=====");
        String[] sArrTitle2 = {"구분", "회원번호", "공제번호", "회원명", "생년월일", "변경일자", "우편번호"
                               , "주소", "상세주소", "전화번호", "이메일", "결과키"};

        List <String> lstCol2 = new ArrayList<String>();
        lstCol2.add("gubun");
        lstCol2.add("accnt_no");
        lstCol2.add("ded_no");
        lstCol2.add("mem_nm");
        lstCol2.add("brth_mon_day");
        lstCol2.add("chg_dt");
        lstCol2.add("post");
        lstCol2.add("addr1");
        lstCol2.add("addr2");
        lstCol2.add("tel");
        lstCol2.add("email");
        lstCol2.add("result_key");

        int i2 = 0;
        int j2 = 0;
        Row row2 = sheet2.createRow(i2++);

        for (String field : sArrTitle2) {
            Cell cell = row2.createCell(j2++);
            cell.setCellValue((String) field);
        }

        // 해당 셀에 데이터를 입력한다.
        Map<String, Object> mRow2 = null;
        String val2 = "";
        String colId2 = "";
        for (i2 = 0; i2 < mList2.size(); i2++) {
            mRow2 = mList2.get(i2);
            row2 = sheet2.createRow(i2+1);
            for (j2=0; j2<lstCol2.size(); ++j2) {
                colId2 = lstCol2.get(j2);
                Cell cell = row2.createCell(j2);
                val2 = CommonUtils.nvls(String.valueOf(mRow2.get(colId2)));

                cell.setCellValue(val2);
            }
        }
        
        /*=================================================================================================================*/
        System.out.println("=====공제 해약 회원 데이터 산출 시작[M3]=====");
        String[] sArrTitle3 = {"구분", "회원번호", "공제번호", "회원명", "해지일자", "총납입금액", "해약구분", "결과키"};

        List <String> lstCol3 = new ArrayList<String>();
        lstCol3.add("gubun");
        lstCol3.add("accnt_no");
        lstCol3.add("ded_no");
        lstCol3.add("mem_nm");
        lstCol3.add("proc_day");
        lstCol3.add("true_amt");
        lstCol3.add("resn_cl");
        lstCol3.add("result_key");

        int i3 = 0;
        int j3 = 0;
        Row row3 = sheet3.createRow(i3++);

        for (String field : sArrTitle3) {
            Cell cell = row3.createCell(j3++);
            cell.setCellValue((String) field);
        }

        // 해당 셀에 데이터를 입력한다.
        Map<String, Object> mRow3 = null;
        String val3 = "";
        String colId3 = "";
        for (i3 = 0; i3 < mList3.size(); i3++) {
            mRow3 = mList3.get(i3);
            row3 = sheet3.createRow(i3+1);
            for (j3=0; j3<lstCol3.size(); ++j3) {
                colId3 = lstCol3.get(j3);
                Cell cell = row3.createCell(j3);
                val3 = CommonUtils.nvls(String.valueOf(mRow3.get(colId3)));

                cell.setCellValue(val3);
            }
        }

        /*=================================================================================================================*/
        System.out.println("=====공제 양도양수 회원 데이터 산출 시작[M4]=====");

        // 양도양수 sheet용
        String[] sArrTitle4 = {"구분", "회원번호", "공제번호", "회원명", "양도일자", "회원번호변경여부", "양수자회원번호"
                               , "양수자회원명", "양수자생년월일", "상품명", "상품변경여부", "총계약금액", "총계약회차"
                               , "만기일자", "월납입일자", "납입금액", "최종납입회차", "최종납입금액", "정산년월"
                               , "납입일자", "납입회차", "납입금액", "우편번호", "주소", "상세주소", "전화번호", "이메일", "결과키"};

        List <String> lstCol4 = new ArrayList<String>();
        lstCol4.add("gubun");
        lstCol4.add("accnt_no");
        lstCol4.add("ded_no");
        lstCol4.add("mem_nm");
        lstCol4.add("trns_dt");
        lstCol4.add("chg_yn");
        lstCol4.add("ys_accnt_no");
        lstCol4.add("ys_mem_nm");
        lstCol4.add("brth_mon_day");
        lstCol4.add("prod_nm");
        lstCol4.add("yn");
        lstCol4.add("amt");
        lstCol4.add("num");
        lstCol4.add("exp_dt");
        lstCol4.add("pay_dd");
        lstCol4.add("pay_amt");
        lstCol4.add("tot_num");
        lstCol4.add("tot_amt");
        lstCol4.add("yymm");
        lstCol4.add("pay_dt");
        lstCol4.add("pay_num");
        lstCol4.add("pay_amt2");
        lstCol4.add("post");
        lstCol4.add("addr1");
        lstCol4.add("addr2");
        lstCol4.add("tel");
        lstCol4.add("email");
        lstCol4.add("result_key");

        int i4 = 0;
        int j4 = 0;
        Row row4 = sheet4.createRow(i4++);

        for (String field : sArrTitle4) {
            Cell cell = row4.createCell(j4++);
            cell.setCellValue((String) field);
        }

        // 해당 셀에 데이터를 입력한다.
        Map<String, Object> mRow4 = null;
        String val4 = "";
        String colId4 = "";
        for (i4 = 0; i4 < mList4.size(); i4++) {
            mRow4 = mList4.get(i4);
            row4 = sheet4.createRow(i4+1);
            for (j4=0; j4<lstCol4.size(); ++j4) {
                colId4 = lstCol4.get(j4);
                Cell cell = row4.createCell(j4);
                val4 = CommonUtils.nvls(String.valueOf(mRow4.get(colId4)));

                cell.setCellValue(val4);
            }
        }

        /*=================================================================================================================*/
        System.out.println("=====공제 행사 회원 데이터 산출 시작[M5]=====");

        String[] sArrTitle5 = {"구분", "회원코드", "공제번호", "회원명", "행사일자", "총납입금액", "행사구분", "결과키"};

        List <String> lstCol5 = new ArrayList<String>();
        lstCol5.add("gubun");
        lstCol5.add("accnt_no");
        lstCol5.add("ded_no");
        lstCol5.add("mem_nm");
        lstCol5.add("event_proc_day");
        lstCol5.add("pay_amt");
        lstCol5.add("type");
        lstCol5.add("result_key");

        int i5 = 0;
        int j5 = 0;
        Row row5 = sheet5.createRow(i5++);

        for (String field : sArrTitle5) {
            Cell cell = row5.createCell(j5++);
            cell.setCellValue((String) field);
        }

        // 해당 셀에 데이터를 입력한다.
        Map<String, Object> mRow5 = null;
        String val5 = "";
        String colId5 = "";
        for (i5 = 0; i5 < mList5.size(); i5++) {
            mRow5 = mList5.get(i5);
            row5 = sheet5.createRow(i5+1);
            for (j5=0; j5<lstCol5.size(); ++j5) {
                colId5 = lstCol5.get(j5);
                Cell cell = row5.createCell(j5);
                val5 = CommonUtils.nvls(String.valueOf(mRow5.get(colId5)));

                cell.setCellValue(val5);
            }
        }

        /*=================================================================================================================*/
        System.out.println("=====공제 레디캐시 데이터 산출 시작[P]=====");
        //createReadyCashExcel(sheetCash);
        /*=================================================================================================================*/

        /*=================================================================================================================*/
        System.out.println("=====공제 회원 정보 산출 종료=====");


        FileOutputStream outputStream = new FileOutputStream(xlFileNm);
        workbook.write(outputStream);
        outputStream.close();

        // 공제 에이전트를 통한 파일전송 추가 - 2019.05.21
        System.out.println("=====공제파일전송 시작=====");
        sendGongjeFile();
        System.out.println("=====공제파일전송 종료=====");
    }

    /** ================================================================
     * 날짜 : 20190321
     * 이름 : 정승철
     * 추가내용 : 정상적인 공제 ASIS-DATA 총 금액 및 건수 조회
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_ASIS_DATA
     * ================================================================
     * */
    public List<Map<String, Object>> getSumGongjeAsisData() throws Exception {
        return DlwDeductionDAO.getSumGongjeAsisData();
    }

    /** ================================================================
     * 날짜 : 20190403
     * 이름 : 송준빈
     * 추가내용 : 공제 마감 처리후 ASIS의 정보를 ORIGIN으로 이관
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_ORIGIN
     * ================================================================
     * */
    public int insertGongjeAsisToOrigin(Map<String, ?> pmParam)throws Exception {
        return DlwDeductionDAO.insertGongjeAsisToOrigin(pmParam);
    }

    /** ================================================================
     * 날짜 : 20190410
     * 이름 : 정승철
     * 추가내용 : 공제결과 삭제
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT_RESULT
     * ================================================================
     * */
    public void deleteGongjeResult() throws Exception {
        DlwDeductionDAO.deleteGongjeResult();
    }

    /** ================================================================
     * 날짜 : 20190513
     * 이름 : 정승철
     * 추가내용 : 공제 에이전트를 통한 파일전송
     * ================================================================
     * */
    public void sendGongjeFile() throws Exception {
        Socket socket = null;
        PrintWriter printWriter = null;

        try {
            //(대명개발서버 아이피)61.39.175.221안될시 127.0.0.1으로 호출
            //socket = new Socket("61.39.175.221", 8000); // 운영연계에이전트 IP , 포트
            //socket = new Socket("61.39.175.227", 8000); // 개발연계에이전트 IP , 포트

            //socket = new Socket("192.168.10.62", 8000); // 개발연계에이전트 IP , 포트
            socket = new Socket("192.168.10.61", 8000); // 운영연계에이전트 IP , 포트

            boolean result = socket.isConnected();
            //System.out.println("isConnected : " + result);

            if(result) System.out.println("서버에 연결됨");
            else System.out.println("서버에 연결실패");

            printWriter = new PrintWriter(socket.getOutputStream());

            // 앞의숫자4자리(상조공제조합회원사코드 :  9111(대명테스트) ) + 공백 + 파일경로
            // 소켓호출 예시 => "9111 /app/gongje/send/singo_excel.xlsx"
            //String str = "9111 /app/gongje/send/singo_excel.xlsx";

            // 개발(DEV) ///////////////////////////////////////////////////////////
            //String str = "9111 /app/gongje/send/1040.xlsx";
            ////////////////////////////////////////////////////////////////////////

            // 운영(REAL) //////////////////////////////////////////////////////////
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Calendar c1 = Calendar.getInstance();
            String sCurrTime = sdf.format(c1.getTime());
            System.out.println("@@@@ Today : " + sCurrTime);

            String str = "1040 /app/gongje/send/DMLIFE_" + sCurrTime + ".xlsx";
            ////////////////////////////////////////////////////////////////////////

            printWriter.print(str);
            printWriter.flush();

            System.out.println("■■완료 !■■");

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("■■최초 연결 실패 !■■");

            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally {
            if (printWriter != null){
                printWriter.close();
            }
            if (socket != null){
                try {
                    System.out.println("■■공제소켓닫힘 Finally ~~ !■■");
                    socket.close();
                } catch (IOException e) {
                    System.out.println("■■연결 실패 !■■");
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    /** ================================================================
     * 날짜 : 20190621
     * 이름 : 송준빈
     * 추가내용 : 공제데이터산출 및 엑셀생성 (배치 수정판 :: Test시 버튼 누르는것과 같음)
     * 대상 테이블 : LF_DMUSER.TB_GONGJE_DAY_EXT
     * ================================================================
     * */
	public void getGongjeExtBatchProcess() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar c1 = Calendar.getInstance();
        String sCurrTime = sdf.format(c1.getTime());

        Map<String, Object> hmParam = new HashMap<String, Object>();
        hmParam.put("ext_dt", sCurrTime);

        int chkCnt = DlwDeductionDAO.chkGongjeSched(hmParam); // 공제 산출스케줄 등록여부 조회

        if(chkCnt > 0) 
        {
        	DlwDeductionDAO.insertGongjeDayExt();
    		createExcel();
    		//sendGongjeFile(); // 운영에 반영시엔 이부분을 오픈 개발시엔 닫기 (20190624 임시주석)
        }
        else
        {
        	System.out.println("====해당 날짜 " +sCurrTime+ " 은 공제 산출스케줄에 등록되어 있지 않습니다.");
        }
        
		return;
	}

}
