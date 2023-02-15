/*
 * (@)# DlwWdrwService.java
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.nicepay.module.lite.NicePayWebConnector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import powerservice.business.bean.UserSession;
import powerservice.business.dlw.service.DlwWdrwService;
import powerservice.business.sys.service.BasVlService;
import powerservice.common.util.CommonUtils;
import powerservice.common.util.NicePayProcess;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;

import com.oreilly.servlet.MultipartRequest;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.cmmn.exception.EgovBizException;


/**
 * 산출정보를 관리한다
 */
@Service
public class DlwWdrwServiceImpl extends EgovAbstractServiceImpl implements DlwWdrwService {

    @Resource
    public DlwWdrwDAO DlwWdrwDAO;

    @Resource
    public BasVlService basVlService;


    private final Logger log = LoggerFactory.getLogger(DlwWdrwServiceImpl.class);

    /* (상세구분별) 산출 조회 */
    public List<Map<String, Object>> getDlwWdrwListByReqbit(Map<String, ?> pmParam) throws Exception {
        return DlwWdrwDAO.getDlwWdrwListByReqbit(pmParam);
    }


    /* 임시건 산출 */
    public List<Map<String, Object>> saveTempWdrw(Map<String, ?> pmParam) throws Exception {
        List<Map<String, Object>> result = null;

        //CMS/카드 일괄 산출
        DlwWdrwDAO.saveImsiWdrw(pmParam);
        /*
            if(!"Card".equals(pmParam.get("wdrw_gubun"))) {
                DlwWdrwDAO.saveCmsTempWdrw(pmParam); // CMS 임시건 산출
            } else {
                DlwWdrwDAO.saveCardTempWdrw(pmParam); // 카드 임시건 산출
            }
        */
        return result;
    }


    /* 산출 회원정보 조회 */
    public List<Map<String, Object>> getDlwWdrwAcntInfo(Map<String, ?> pmParam) throws Exception {
        return DlwWdrwDAO.getDlwWdrwAcntInfo(pmParam);
    }


    /* 임의등록 */
    public void addWdrwTemp(Map<String, Object> pmParam) throws Exception {
        DlwWdrwDAO.addWdrwTemp(pmParam);

        //공통정보
        //if(!"Card".equals(pmParam.get("wdrw_gubun"))) {

//              int inv_no = DlwWdrwDAO.getnew_inv_no(pmParam);
//              pmParam.put("inv_no", inv_no);
          //  DlwWdrwDAO.addWdrwTemp(pmParam);
        //} else {
/*
            List<Map<String, Object>> lstInvNo = DlwWdrwDAO.getInvNo(pmParam);

            String sInvNo = "";
            if (null == lstInvNo) {
                sInvNo = (String)pmParam.get("inv_gunsu");
            } else {
                //sInvNo = (String)lstInvNo.get(0).get("inv_no");

                // 원본
                sInvNo = String.valueOf(lstInvNo.get(0).get("inv_no"));

                System.out.println(sInvNo);
            }
            pmParam.put("inv_no", sInvNo);
*/
            //DlwWdrwDAO.addWdrwTemp(pmParam);
        //}

    }


    /* 임의삭제 */
    public void delWdrwTemp(Map<String, Object> pmParam) throws Exception {

        DlwWdrwDAO.delWdrwTemp(pmParam);
    }


    /**
     * 고객정보 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwAccntBaseInfo(Map<String, ?> pmParam) throws Exception {
        //return DlwWdrwDAO.getDlwAccntInfo(pmParam);
        return DlwWdrwDAO.getDlwAccntBaseInfo(pmParam);
    }


    /**
     * 고객정보 카운트조회
     * 정승철
     * 20181113
     */
    public int getCntDlwAccntBaseInfo(Map<String, ?> pmParam) throws Exception {
        return DlwWdrwDAO.getCntDlwAccntBaseInfo(pmParam);
    }

    /**
     * 고객정보 조회(팝업용)
     * 정승철
     * 20181113
     */
    public List<Map<String, Object>> getDlwAccntBaseInfo_popup(Map<String, ?> pmParam) throws Exception {
        return DlwWdrwDAO.getDlwAccntBaseInfo_popup(pmParam);
    }


    /**
     * 대명라이프웨이 CMS 구좌별 최종납입회차 조회
     *
     * @param pmParam 검색 조건
     * @return 최종납입회차
     * @throws Exception
     */
    public int getDlwLastPayNo(Map<String, ?> pmParam) throws Exception {
        return DlwWdrwDAO.getDlwLastPayNo(pmParam);
    }


    /**
     * 대명라이프웨이 납입액 조회
     *
     * @param pmParam 검색 조건
     * @return 납입액
     * @throws Exception
     */
    public int getInvAmt(Map<String, ?> pmParam) throws Exception { // 대명라이프웨이 납입액 조회
        return DlwWdrwDAO.getInvAmt(pmParam);
    }


    /**
     * CARD 파일 작성 기초 리스트
     * 임동진
     * 20180927
    */
    public List<Map<String, Object>> getWdrwReqList(Map<String, ?> pmParam) throws Exception {
        return DlwWdrwDAO.getWdrwReqList(pmParam);
    }
    
    /**
     * CARD 무승인 파일 작성 취소 리스트
     * 송준빈
     * 20191211
    */
    public List<Map<String, Object>> getWdrwReqCnclList(Map<String, ?> pmParam) throws Exception {
        return DlwWdrwDAO.getWdrwReqCnclList(pmParam);
    }

    /**
     * 청구데이터 INSERT
     * 임동진
     * 20181004
     */
    public int insertReqWdrw(Map<String, ?> pmParam) throws Exception {
        return DlwWdrwDAO.insertReqWdrw(pmParam);
    }
    /**
     * CMS기초정보 READ
     * 임동진
     * 20181004
     */
   public List<Map<String, Object>> getWdrwDlwCmsComnInfo(Map<String, ?> pmParam) throws Exception {
       return DlwWdrwDAO.getWdrwDlwCmsComnInfo(pmParam);
   }

   /**
    * 산출 가능 일자 체크
    * 임동진
    * 20181005
    */
   public List<Map<String, Object>> getWdrwReqCheck(Map<String, ?> pmParam) throws Exception {
       return DlwWdrwDAO.getWdrwReqCheck(pmParam);
   }

   /**
    * 산출마감체크 조회
    * 정승철
    * 20181012
   */
   public List<Map<String, Object>> getWdrwExtCheck(Map<String, ?> pmParam) throws Exception {
       return DlwWdrwDAO.getWdrwExtCheck(pmParam);
   }

   /**
    * 산출마감체크 저장
    * 정승철
    * 20181012
   */
   public void saveExtChk(Map<String, ?> pmParam) throws Exception {
       DlwWdrwDAO.saveExtChk(pmParam);
   }

   /**
    * 산출마감체크 삭제
    * 정승철
    * 20181012
   */
   public void delExtChk(Map<String, ?> pmParam) throws Exception {
       DlwWdrwDAO.delExtChk(pmParam);
   }

   /**
    * 산출마감 입력유효성체크
    * 정승철
    * 20181012
   */
   public int chkWdrwExt(Map<String, ?> pmParam) throws Exception {
       return DlwWdrwDAO.chkWdrwExt(pmParam);
   }

   /**
    * 휴일여부 체크
    * 정승철
    * 20181012
   */
   public String getHolidayChk(Map<String, ?> pmParam) throws Exception {
       return DlwWdrwDAO.getHolidayChk(pmParam);
   }

   /**
    * 실시간 결제 대상 회원 정보
    * 임동진
    * 20181012
   */
   public List<Map<String, Object>> getRealTimeReqList(Map<String, ?> pmParam) throws Exception {
       return DlwWdrwDAO.getRealTimeReqList(pmParam);
   }

   /**
    * 실시간 결제 실행 SERVICE
    * 임동진
    * 20181012
   */
   public Map<String, Object> RealTimeReqProc(Map<String, Object> pmParam) throws Exception {
       String result_cd = "";
       String result_msg = "";
       String tid = "";
       String auth_dt = "";
       String auth_cd = "";
       String uip = "";
       String card_cd = "";
       try
       {
           Map<String, Object> rsltParam = RealTimebillPay(pmParam);
           result_cd = String.valueOf(rsltParam.get("result_cd"));
           result_msg = String.valueOf(rsltParam.get("result_msg"));
           tid = String.valueOf(rsltParam.get("tid"));
           auth_dt = String.valueOf(rsltParam.get("auth_dt"));
           auth_cd = String.valueOf(rsltParam.get("auth_cd"));
           uip = String.valueOf(rsltParam.get("uip"));
           card_cd = String.valueOf(rsltParam.get("card_cd"));
           pmParam.put("uip", uip);
           pmParam.put("result_cd", result_cd);
           pmParam.put("result_msg", result_msg);
           pmParam.put("tid", tid);
           pmParam.put("auth_dt", auth_dt);
           pmParam.put("auth_cd", auth_cd);
           pmParam.put("card_cd", card_cd);

       }
       catch(Exception e)
       {
           e.printStackTrace();
       }
       return pmParam;
   }

   /**
    * 실시간 결제 실행 SERVICE_2
    * 임동진
    * 20181012
    */
   public Map<String, Object> RealTimebillPay(Map<String, Object> pmParam) throws Exception {
       InetAddress inet = InetAddress.getLocalHost();
       String uip = inet.getHostAddress();
       String accntNo = (String)pmParam.get("accnt_no");
       String payAmt = (String)pmParam.get("pay_amt");
       String memNm = (String)pmParam.get("mem_nm");
       String email = (String)pmParam.get("email");
       String cell = (String)pmParam.get("cell");
       String prodNm = (String)pmParam.get("prod_nm");
       String bid = (String)pmParam.get("bid");
       String cardQuota = (String)pmParam.get("card_quota");
       String billGubun = (String)pmParam.get("billGubun");

       if (billGubun == null) {
           billGubun = "00";
         }

       System.out.println("pmParam>>>>>>>>>>>>>>>>>>>>>>>>>>>>: " + pmParam);

       NicePayProcess nicepay = new NicePayProcess();

       try {
           nicepay = RealTimeBillSetting(pmParam, "Real_Key");
           nicepay.setAmt(payAmt);
           nicepay.setMoid(accntNo);
           nicepay.setBuyerName(memNm);
           nicepay.setBuyerEmail(email);
           nicepay.setBuyerTel(cell);
           nicepay.setGoodsName(prodNm);
           nicepay.setBillKey(bid);

           //임동진 수정
           nicepay.setMallreserved(billGubun);
           nicepay.setCardQuota(cardQuota);
           NicePayWebConnector result = nicepay.paybill();
           String resultCode = result.getResultData("ResultCode");
           String resultMsg = result.getResultData("ResultMsg");
           String tid = result.getResultData("TID");
           String authDate = result.getResultData("AuthDate");
           String authCode = result.getResultData("AuthCode");
           String cardCode = result.getResultData("CardCode");

           pmParam.put("uip", uip);
           pmParam.put("result_cd", resultCode);
           pmParam.put("result_msg", resultMsg);
           pmParam.put("tid", tid);
           pmParam.put("auth_dt", authDate);
           pmParam.put("auth_cd", authCode);
           pmParam.put("card_cd", cardCode);
       } catch(Exception e) {
           e.printStackTrace();
       }
       return pmParam;
   }

   /**
    * 실시간 결제 실행 SERVICE_3
    * 임동진
    * 20181012
    */
   public NicePayProcess RealTimeBillSetting(Map<String, Object> pmParam, String gbn) throws Exception {
       NicePayProcess nicepay = new NicePayProcess();

       ParamUtils.addCenterParam(pmParam);

       String sPayFilePath = basVlService.getBasVlAsString("pay_file_path", (String) pmParam.get("cntr_cd"));

       nicepay.setNicePayHome(sPayFilePath+"/web_site/nicelog");

       //MID 및 KEY값 DB에서 가져오기
       //String strMidKey = DlwCardDAO.getAccntMid(pmParam);

       String strMidKey = String.valueOf(pmParam.get("bill_key"));

       //System.out.println("pmParam>>>>>>>>>>>>>>>>>>>>>>>>>>>>: " + pmParam);
       System.out.println("strMidKey>>>>>>>>>>>>>>>>>>>>>>>>>>>>: " + strMidKey);

       String mid = strMidKey.substring(0,10);  //mid
       String key = strMidKey.substring(10);	 //key

       nicepay.setMID(mid);
       nicepay.setMerchantKey(key);

       return nicepay;
   }

   /**
    * 카드 / CMS 결과데이터 인서트 (실시간, 파일배치)
    * 임동진
    * 20181015
   */
   public int insertReqWdrwResult(Map<String, ?> pmParam) throws Exception {
       return DlwWdrwDAO.insertReqWdrwResult(pmParam);
   }

   /**
    * 성공한 데이터 회원 입금 데이터 insert
    * 임동진
    * 20181016
   */
   public int insertMemPayData(Map<String, ?> pmParam) throws Exception {
       String strPayKind = String.valueOf(pmParam.get("pay_kind"));

       //가입상태 및 가입일 업데이트 처리
       DlwWdrwDAO.uptMemJoinStatus(pmParam);

       //실시간 입금 반영 or 파일 일괄 입급 반영
       if (strPayKind.equals("02")){
           return DlwWdrwDAO.insertMemPayData(pmParam);
       } else {
           return DlwWdrwDAO.insertFilePayData(pmParam);
       }

       /*
       int intPayNo = 0;
       intPayNo = Integer.parseInt(String.valueOf(pmParam.get("pay_no")));
       if (pmParam.get("pay_kind").equals("02") ){
           if(intPayNo == 1){
               DlwWdrwDAO.uptMemJoinStatus(pmParam);
           }
       } else{
           DlwWdrwDAO.uptMemJoinStatus(pmParam);
       }
       */

       //회원의 실시간 결제 성공한 납이 회차가 1이면 업데이트 (건수가 아님 납입 회차임)


       /*
       if (pmParam.get("pay_kind").equals("02")){
           int intPayNo = 0;
           intPayNo = Integer.parseInt(String.valueOf(pmParam.get("pay_no")));

         //회원의 실시간 결제 성공한 납이 회차가 1이면 업데이트 (건수가 아님 납입 회차임)
           if (intPayNo == 1){
               DlwWdrwDAO.uptMemJoinStatus(pmParam);
           }
       }
       */

   }

   /**
    * CMS 결과반영 데이터 업로드
    * 임동진
    * 20181022
   */
   public void dataFileUpload(HttpServletRequest request, HttpServletResponse response, Map<String, Object> mResuslt) throws Exception {

       String subDir = CommonUtils.nvls(request.getParameter("subDir"));

       int idx = 1;

       String[] fileClToArr	= { "0001", "0002", "0003", "0004", "0005" };

       String successPath 		= "";
       String errorPath 		= "";

       /*
       String osName = System.getProperty("os.name").toLowerCase();

       if (osName.indexOf("windows") >= 0) {
           successPath = EgovProperties.getProperty("cms.file.windows.successPath");
       } else {
           successPath = EgovProperties.getProperty("cms.file.unix.successPath");
       }
       */

       //저장경로
       successPath = "C:/app/test";

       String tempDir = System.getProperty("java.io.tmpdir");

       log.info("---uploadToNas 서비스 - 1");

       MultipartRequest multipartRequest = new MultipartRequest(request, tempDir, 1024*1024*20);
       Enumeration enm = multipartRequest.getFileNames();

       String sKey = "";
       //String[] fileLayout	= null; //회원 결과 로우 데이터
       String[] fileLayout	    = new String[50000];
       String[] fileLayout_T	= null; //회원 결과 로우데이터 하단 종합 결과
       String[] fileLayout_H	= null; //회원 결과 로우데이터 상단 헤더 결과
       int fileCnt			= 0;

       UserSession oUserSession = (UserSession) SessionUtils.getLoginUser();
       String empleNo 		= CommonUtils.nvls(oUserSession.getUserid());

       Map<String, Object> mParam = null;

       try {

               if ("".equals(empleNo)) {
                   throw new EgovBizException("업로드 권한이 없습니다.");
               }

               mParam = new HashMap<>();
               mParam.put("emple_no", empleNo);

               // 실제로는 단건만 처리함
               while (enm.hasMoreElements())
               {
                   fileCnt++;
                   sKey = (String)enm.nextElement();

                   File upfile = multipartRequest.getFile(sKey);

                   //입력 스트림 생성
                   //FileReader fileRd =  new FileReader(upfile);

                   //입력 버퍼 생성
                   BufferedReader br  =  new BufferedReader(new InputStreamReader(new FileInputStream(upfile),"euc-kr"));

                   String bfLine = "";

                   int totCnt_1 = 0; //결과 데이터 하단 종합 건수
                   int totCnt_2 = 0; //결과 데이터 레코드 건수
                   String strReqday = ""; //청구 일자

                   bfLine = br.readLine();

                   //결과 청구 일자 가져오기
                   strReqday = "20" + bfLine.substring(27,33);

                   //결과 레코드 담기
                   bfLine = bfLine.substring(151);

                   //결과 레코드 다음행 처리
                   //fileLayout = bfLine.split("                     R");
                   //fileLayout = bfLine.split("\nR");
                   int iStartDataByteIndex = 0;
                   int iExpressionDataLineLength = 142;
                   int iFileLayoutArrayIndex = 0;
                		   
                   while(true)
                   {
                	   if(bfLine == null || bfLine.equals(""))
                	   {
                		   break;
                	   }
                	   else
                	   {
                		   String sLineData = bfLine.substring(iStartDataByteIndex, iStartDataByteIndex + iExpressionDataLineLength);
                		   //System.out.println("읽어온 라인은 ::: " + sLineData);
                		   if(sLineData.substring(0, 8).equals("99999999"))
                		   {
                			   break;
                		   }
                		   else
                		   {
                			   fileLayout[iFileLayoutArrayIndex] = sLineData;
                    		   iFileLayoutArrayIndex++;
                    		   iStartDataByteIndex += iExpressionDataLineLength;
                		   }
                	   }
                   }
                   
                   System.out.println("데이터 개수 :: " + iFileLayoutArrayIndex);
                   
                   // 결과 레코드 하단 결과 조회
                   fileLayout_T = bfLine.split("T99999999");

                   totCnt_1 = Integer.parseInt(fileLayout_T[1].substring(18,26));
                   //totCnt_2 = fileLayout.length;
                   totCnt_2 = iFileLayoutArrayIndex;

                   if (totCnt_1 != totCnt_2 ){
                       br.close();
                       throw new EgovBizException("결과 데이터의 개수가 맞지 않습니다.(전산팀 전달["+ totCnt_1 + "<>" + totCnt_2 + "])" );
                   }

                   //System.out.println("totCnt_1>>>>>>>" + totCnt_1);
                   //System.out.println("totCnt_2>>>>>>>" + totCnt_2);

                   //for(int i=0; i<fileLayout.length;i++ ){
                   for(int i = 0; i < iFileLayoutArrayIndex; i++){
                       mParam.put("accnt_no"		, fileLayout[i].substring(82,94))	; // 회원번호
                       mParam.put("req_day"			, strReqday)	                    ; // 청구일
                       mParam.put("req_no"			, 999)	                            ; // 청구납입회차
                       mParam.put("pay_kind"		, "01")	                            ; // 01:파일, 02:실시간, 03:자유,04:기타
                       mParam.put("result_key"		, fileLayout[i].substring(0,8))	    ; // CMS일련번호
                       mParam.put("result_cd"		, fileLayout[i].substring(68,72))	; // 결과 코드
                       mParam.put("result_msg"		, "CMS실패")	                    ; // 결과 메세지
                       mParam.put("pay_mthd"		, "04")	                            ; // 납입방법(CMS)
                       mParam.put("auth_dt"		    , "")	                            ; // 승인일자(카드)
                       mParam.put("auth_cd"		    , "")	                            ; // 승인번호(카드)
                       mParam.put("ichae_cd"		, fileLayout[i].substring(18,21))	; // 출금참가기관(은행)점코드(7)
                       mParam.put("ichae_no"		, fileLayout[i].substring(25,41))	; // 출금계좌번호(16)
                       mParam.put("etc"		        , fileLayout[i].substring(72,80))	; // 통장기재내용(16)

                       System.out.println("mParam>>>>>>>" + mParam);

                       //결과 데이터 인서트
                       insertReqWdrwResult(mParam);

                           /************************************************************************************************
                            * 전체 cms결과 파일 내역
                            * 					    //mParam.put("rec_gbn"			, bdr.get(1))	; // Record 구분
                        //mParam.put("rec_gbn"			, rec_gbn)	; // Record 구분
                        mParam.put("data_ser_no"		, fileLayout[i].substring(0,8))	; // Data 일련번호(8)
                        mParam.put("data_ser_no"		, fileLayout[i].substring(0,8))	; // Data 일련번호(8)
                        mParam.put("org_cd"			, fileLayout[i].substring(8,18))		; // 기관코드(10)
                        mParam.put("wd_bank_cd"		, fileLayout[i].substring(18,25))	; // 출금참가기관(은행)점코드(7)
                        mParam.put("wd_accnt_no"		, fileLayout[i].substring(25,41))	; // 출금계좌번호(16)
                        mParam.put("wd_imps_amt"		, fileLayout[i].substring(41,54))	; // 출금불능금액(13)
                        mParam.put("birth_dt"			, fileLayout[i].substring(54,67))	; // 생년월일 또는 사업자등록번호(13)
                        mParam.put("wd_yn"				,fileLayout[i].substring(67,68))	; // 출금여부 (1)
                        mParam.put("wd_imps_cd"		,fileLayout[i].substring(68,72))	; // 출금불능코드(4)
                        mParam.put("accnt_wrt_conts"		,fileLayout[i].substring(72,88))	; // 통장기재내용(16)
                        // mParam.put("accnt_wrt_conts"	, new String(bdr.getByEnc(16, "EUC-KR").getBytes(), "UTF-8")); // 통장기재내용(16)
                        //mParam.put("accnt_wrt_conts"	, bdr.getByEnc(16, "EUC-KR")); // 통장기재내용
                        mParam.put("money_type"		, fileLayout[i].substring(88,90))	; // 자금종류(2)
                        mParam.put("payers_no"			, fileLayout[i].substring(90,110))	; // 납부자번호(20)
                        mParam.put("org_use_section"	, fileLayout[i].substring(110,115))	; // 이용기관 사용영역(5)
                        mParam.put("wd_type"			, fileLayout[i].substring(115,116))	; // 출금형택(1)
                        mParam.put("tel"				, fileLayout[i].substring(116,128))	; // 전화번호(핸드폰)(12)
                        mParam.put("filler"			, fileLayout[i].substring(128,149))	; // 빈값(21)
                        *************************************************************************************************/
                   }

                   br.close();

                  /*****************************************************************************************************
                   *  PLAN -B 파일 저장 로직
                   String sOrigFn = upfile.getName();

                   mResuslt.put("file_nm", sOrigFn);

                   System.out.println(sOrigFn);

                   String p_cms = sOrigFn.substring(0, 4);

                   System.out.println(p_cms);
                   System.out.println(successPath);

                   //폴더 생성
                   File fileClFolder = new File(successPath + p_cms );
                   if (!fileClFolder.isDirectory()) {
                        fileClFolder.mkdirs();
                   }

                        String fileSavePath = successPath + p_cms + "/" + sOrigFn;
                        log.info("파일저장경로> fileSavePath : " + fileSavePath);
                        log.info("upfile.length() : " + upfile.length());
                        log.info("upfile.getAbsolutePath() : " + upfile.getAbsolutePath());

                        FileOutputStream newFileOs = new FileOutputStream(new File(fileSavePath));
                    //    newFileOs.close();
                        FileUtils.copyFile(upfile, newFileOs);
                        upfile.delete();
                        newFileOs.close();

                        log.info("hsFactoringFileBatchUpload> 18 ");

                        HashMap<String,String> obj = new HashMap<>();
                        if ("Y".equals(randomYn)) {
                            log.info("hsFactoringFileBatchUpload> 19 ");
                            obj.put("cl", "임의매치");
                            obj.put("file_nm", sOrigFn);
                        }
                        else {
                            log.info("hsFactoringFileBatchUpload> 20 ");
                            obj.put("cl", "성공");
                            obj.put("file_nm", sOrigFn);
                        }
                        log.info("hsFactoringFileBatchUpload> 21 ");

                ********************************************************************************************************/
               }
               if (fileCnt < 1) {
                   throw new EgovBizException("업로드된 파일이 없습니다.");
               }

       } catch (FileNotFoundException ex) {
           ex.printStackTrace();
           throw ex;
       } catch (IOException ex) {

           log.info("hsFactoringFileBatchUpload> 29 ");
           ex.printStackTrace();

           throw ex;
       }

    }

   /**
    * 청구데이터 INSERT
    * 임동진
    * 20181004
    */
   public int uptReqResultKey(Map<String, ?> pmParam) throws Exception {
       return DlwWdrwDAO.uptReqResultKey(pmParam);
   }

   /**
    * 가상계좌 청구 대상 회원 리스트
    * 임동진
    * 20181029
    * @param pmParam 검색 조건
    * @return 가상계좌 정보 산출정보
    * @throws Exception
    */
   public List<Map<String, Object>> getVirtualReqList(Map<String, ?> pmParam) throws Exception {
       return DlwWdrwDAO.getVirtualReqList(pmParam);
   }

   /**
    * 가상계좌 산출 대상 회원 리스트
    * 임동진
    * 20181029
    * @param pmParam 검색 조건
    * @return 가상계좌 정보 산출정보
    * @throws Exception
    */
   public List<Map<String, Object>> getVirtualExtList(Map<String, ?> pmParam) throws Exception {
       return DlwWdrwDAO.getVirtualExtList(pmParam);
   }

   /**
    * 가상계좌 산출 INSERT
    * 임동진
    * 20181030
   */
   public int insertVirtualMemData(Map<String, ?> pmParam) throws Exception {
       return DlwWdrwDAO.insertVirtualMemData(pmParam);
   }

   /**
    * 가상계좌 산출데이터 청구 등록
    * 임동진ㄴ
    * 20181030
   */
   public int insertVirtualReq(Map<String, ?> pmParam) throws Exception {
       return DlwWdrwDAO.insertVirtualReq(pmParam);
   }

   /**
   * 가상계좌 전송 전  청구 여부 확인
   * 임동진
   * 20181101
   */
   public List<Map<String, Object>> getVirtualReqYn(Map<String, ?> pmParam) throws Exception {
       return DlwWdrwDAO.getVirtualReqYn(pmParam);
   }

   /**
   * 가상계좌 NICE 전송 후 TID업데이트 혹은 실패 처리
   * 임동진
   * 20181101
   */
   public int updateVirtualReqSendtoNice(Map<String, ?> pmParam) throws Exception {
       return DlwWdrwDAO.updateVirtualReqSendtoNice(pmParam);
   }

   /**
    * 가상계좌 산출 현황 조회 LIST
    * 임동진
    * 20181105
    */
   public List<Map<String, Object>> getVirtualReqResultList(Map<String, ?> pmParam) throws Exception {
       return DlwWdrwDAO.getVirtualReqResultList(pmParam);
   }

   /**
    * 가상계좌 산출 현황 조회 COUNT
    * 임동진
    * 20181105
    */
   public int getVirtualReqResultCnt(Map<String, ?> pmParam) throws Exception {
       return DlwWdrwDAO.getVirtualReqResultCnt(pmParam);
   }

   /**
    * 가상계좌 청구 중 강제 취소
    * 임동진
    * 20181105
    */
   public int updateVirtualReqDelete(Map<String, Object> pmParam) throws Exception {

       return DlwWdrwDAO.updateVirtualReqDelete(pmParam);
   }

   /**
    * 가상계좌 산출 대상자 삭제
    * 임동진
    * 20181105
    */
   public int updateVirtualExtDelete(Map<String, ?> pmParam) throws Exception {
       return DlwWdrwDAO.updateVirtualExtDelete(pmParam);
   }

   /** ================================================================
    * 날짜 : 20181211
    * 이름 : 송준빈
    * 추가내용 : 청구강제마감 업데이트
    * 대상 테이블 : TB_MEMBER_WDRW_REQ
    * ================================================================
    * */
   public int updateCompulsionWdrdList(Map<String, Object> pmParam) {
       return DlwWdrwDAO.updateCompulsionWdrdList(pmParam);
   }

   /**
    * 특수 산출
    * 정승철
    * 20181211
    */
   public List<Map<String, Object>> saveSpecialWdrw(Map<String, ?> pmParam) throws Exception {
       List<Map<String, Object>> result = null;

       // 특수 산출
       DlwWdrwDAO.saveSpecialWdrw(pmParam);
       return result;
   }
   
   /** ================================================================
    * 날짜 : 20191202
    * 이름 : 송준빈
    * 추가내용 : 카드 무승인 데이터 resultKey추출 (가맹점 번호 기준)
    * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
    * ================================================================
    */
   public int uptReqNauthResultKey(Map<String, ?> pmParam) throws Exception {
   	   return DlwWdrwDAO.uptReqNauthResultKey(pmParam);
   }
   
   /** ================================================================
    * 날짜 : 20191202
    * 이름 : 송준빈
    * 추가내용 : 카드 무승인 데이터 개수 추출 (가맹점 번호 기준)
    * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
    * ================================================================
    */
   public List<Map<String, Object>> getWdrwNauthReqFranCnt(Map<String, ?> pmParam) throws Exception {
	   return DlwWdrwDAO.getWdrwNauthReqFranCnt(pmParam);
   }
   
   /** ================================================================
    * 날짜 : 20191202
    * 이름 : 송준빈
    * 추가내용 : 카드 무승인 데이터 추출
    * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
    * ================================================================
    */
   public List<Map<String, Object>> getWdrwNauthReqList(Map<String, ?> pmParam) throws Exception {
       return DlwWdrwDAO.getWdrwNauthReqList(pmParam);
   }
   
   /** ================================================================
    * 날짜 : 20191202
    * 이름 : 송준빈
    * 추가내용 : 카드 무승인 데이터 개수 추출 (청구일자기준)
    * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
    * ================================================================
    */
   public List<Map<String, Object>> getWdrwNauthReqTotal(Map<String, ?> pmParam) throws Exception {
	   return DlwWdrwDAO.getWdrwNauthReqTotal(pmParam);
   }
   
   /** ================================================================
    * 날짜 : 20191209
    * 이름 : 송준빈
    * 추가내용 : 카드 무승인 데이터 산출
    * 대상 테이블 : LF_DMUSER.TB_MEMBER_REQ_NAUTH_CNCL
    * ================================================================
    * */
   public int updateNauthCancelCalc(Map<String, ?> pmParam) throws Exception {
       return DlwWdrwDAO.updateNauthCancelCalc(pmParam);
   }
   
   /** ================================================================
    * 날짜 : 20191202
    * 이름 : 송준빈
    * 추가내용 : 카드 무승인 프랜차이즈 개수 추출
    * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
    * ================================================================
    * */
   public int getWdrwNauthReqFranCount(Map<String, ?> pmParam) throws Exception {
       return DlwWdrwDAO.getWdrwNauthReqFranCount(pmParam);
   }

   /** ================================================================
    * 날짜 : 20200226
    * 이름 : 송준빈
    * 추가내용 : 가상계좌 산출데이터 청구 등록 NEW
    * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
    * ================================================================
    * */
   public int insertVirtualReqRefac(Map<String, ?> pmParam) throws Exception {
	   return DlwWdrwDAO.insertVirtualReqRefac(pmParam);
   }
   
   /** ================================================================
    * 날짜 : 20181018
    * 이름 : 송준빈
    * 추가내용 : 출금이체의뢰내역(청구) 조회건수
    * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
    * ================================================================
    * */
   public int getDayCardNauthCount(Map<String, Object> pmParam) {
       return DlwWdrwDAO.getDayCardNauthCount(pmParam);
   }
}
