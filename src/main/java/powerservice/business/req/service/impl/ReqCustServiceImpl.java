package powerservice.business.req.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.nicepay.module.lite.NicePayWebConnector;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import powerservice.business.bean.UserSession;
import powerservice.business.req.service.ReqCustService;
import powerservice.business.sys.service.BasVlService;
import powerservice.common.util.CommonUtils;
import powerservice.common.util.INICISPay;
import powerservice.common.util.NicePayProcess;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;

import com.oreilly.servlet.MultipartRequest;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.cmmn.exception.EgovBizException;

@Service
public class ReqCustServiceImpl extends EgovAbstractServiceImpl implements ReqCustService {

    private final Logger log = LoggerFactory.getLogger(ReqCustServiceImpl.class);

    @Resource
    public ReqCustDAO ReqCustDAO;

    @Resource
    public BasVlService basVlService;

      /**
        * 산출 가능 일자 체크
        * 임동진
        * 20181005
        */
       public List<Map<String, Object>> getWdrwReqCheck(Map<String, ?> pmParam) throws Exception {
           return ReqCustDAO.getWdrwReqCheck(pmParam);
       }

       /* (상세구분별) 산출 조회 */
        public List<Map<String, Object>> getDlwWdrwListByReqbit(Map<String, ?> pmParam) throws Exception {

            String strSearchYy = String.valueOf(pmParam.get("search_yy"));

            //실시간 입금 반영 or 파일 일괄 입급 반영
            if ("1".equals(strSearchYy)){ //2022년
                return ReqCustDAO.getDlwWdrwListByReqbit(pmParam);
            } else { //2021년
                return ReqCustDAO.getDlwWdrwOldListByReqbit(pmParam);
            }
        }

        /**
         * 결과 데이터 건수조회
         * 정승철
         * 20181015
        */
        public List<Map<String, Object>> getReqResultCount(Map<String, ?> pmParam) throws Exception {
            String strSearchYy = String.valueOf(pmParam.get("search_yy"));

            //실시간 입금 반영 or 파일 일괄 입급 반영
            if ("1".equals(strSearchYy)){ //2022년
                return ReqCustDAO.getReqResultCount(pmParam);
            } else { //2021년
                return ReqCustDAO.getReqResultOldCount(pmParam);
            }


        }

        /* 임시건 산출 */
        public List<Map<String, Object>> saveTempWdrw(Map<String, ?> pmParam) throws Exception {

            List<Map<String, Object>> result = null;

            //CMS/카드 일괄 산출
            ReqCustDAO.saveImsiWdrw(pmParam);

            return result;
        }

        /**
         * 특수 산출
         * 정승철
         * 20181211
         */
        public List<Map<String, Object>> saveSpecialWdrw(Map<String, ?> pmParam) throws Exception {
            List<Map<String, Object>> result = null;

            // 특수 산출
            ReqCustDAO.saveSpecialWdrw(pmParam);
            return result;
        }

        /** ================================================================
         * 날짜 : 20181023
         * 이름 : 임동진
         * 추가내용 : 결과 데이터 확인 후 청구 테이블 결과 업데이트 처리
         * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
         * ================================================================
         * */
        public int uptReqResultStat(Map<String, ?> pmParam) throws Exception {
            return ReqCustDAO.uptReqResultStat(pmParam);
        }

        /**
         * 성공한 데이터 회원 입금 데이터 insert
         * 임동진
         * 20181016
        */
        public int insertMemPayData(Map<String, ?> pmParam) throws Exception {
            String strPayKind = String.valueOf(pmParam.get("pay_kind"));

            //실시간 입금 반영 or 파일 일괄 입급 반영
            if (strPayKind.equals("02")){
                //가입상태 및 가입일 업데이트 처리
                ReqCustDAO.uptMemJoinRealtimeStatus(pmParam);
                return ReqCustDAO.insertMemPayData(pmParam);
            } else {
                //가입상태 및 가입일 업데이트 처리
                ReqCustDAO.uptMemJoinStatus(pmParam);
                return ReqCustDAO.insertFilePayData(pmParam);
            }

            /*
            int intPayNo = 0;
            intPayNo = Integer.parseInt(String.valueOf(pmParam.get("pay_no")));
            if (pmParam.get("pay_kind").equals("02") ){
                if(intPayNo == 1){
                    ReqCustDAO.uptMemJoinStatus(pmParam);
                }
            } else{
                ReqCustDAO.uptMemJoinStatus(pmParam);
            }
            */

            //회원의 실시간 결제 성공한 납이 회차가 1이면 업데이트 (건수가 아님 납입 회차임)


            /*
            if (pmParam.get("pay_kind").equals("02")){
                int intPayNo = 0;
                intPayNo = Integer.parseInt(String.valueOf(pmParam.get("pay_no")));

              //회원의 실시간 결제 성공한 납이 회차가 1이면 업데이트 (건수가 아님 납입 회차임)
                if (intPayNo == 1){
                    ReqCustDAO.uptMemJoinStatus(pmParam);
                }
            }
            */

        }

        /** ================================================================
         * 날짜 : 20181018
         * 이름 : 송준빈
         * 추가내용 : 출금이체의뢰내역(청구) 조회건수
         * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
         * ================================================================
         * */
        public int getDayCardNauthCount(Map<String, Object> pmParam) {
            return ReqCustDAO.getDayCardNauthCount(pmParam);
        }

        /** ================================================================
         * 날짜 : 20191209
         * 이름 : 송준빈
         * 추가내용 : 카드 무승인 데이터 산출
         * 대상 테이블 : LF_DMUSER.TB_MEMBER_REQ_NAUTH_CNCL
         * ================================================================
         * */
        public int updateNauthCancelCalc(Map<String, ?> pmParam) throws Exception {
            return ReqCustDAO.updateNauthCancelCalc(pmParam);
        }

        /**
         * 청구데이터 INSERT
         * 임동진
         * 20181004
         */
        public int uptReqResultKey(Map<String, ?> pmParam) throws Exception {
            return ReqCustDAO.uptReqResultKey(pmParam);
        }

        /**
         * CARD 파일 작성 기초 리스트
         * 임동진
         * 20180927
        */
        public List<Map<String, Object>> getWdrwReqList(Map<String, ?> pmParam) throws Exception {
            return ReqCustDAO.getWdrwReqList(pmParam);
        }

        /**
         * 청구데이터 INSERT
         * 임동진
         * 20181004
         */
        public int insertReqWdrw(Map<String, ?> pmParam) throws Exception {
            return ReqCustDAO.insertReqWdrw(pmParam);
        }

        /**
         * CMS기초정보 READ
         * 임동진
         * 20181004
         */
       public List<Map<String, Object>> getWdrwDlwCmsComnInfo(Map<String, ?> pmParam) throws Exception {
           return ReqCustDAO.getWdrwDlwCmsComnInfo(pmParam);
       }

       /** ================================================================
        * 날짜 : 20191202
        * 이름 : 송준빈
        * 추가내용 : 카드 무승인 데이터 resultKey추출 (가맹점 번호 기준)
        * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
        * ================================================================
        */
       public int uptReqNauthResultKey(Map<String, ?> pmParam) throws Exception {
              return ReqCustDAO.uptReqNauthResultKey(pmParam);
       }

       /** ================================================================
        * 날짜 : 20191202
        * 이름 : 송준빈
        * 추가내용 : 카드 무승인 데이터 개수 추출 (가맹점 번호 기준)
        * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
        * ================================================================
        */
       public List<Map<String, Object>> getWdrwNauthReqFranCnt(Map<String, ?> pmParam) throws Exception {
           return ReqCustDAO.getWdrwNauthReqFranCnt(pmParam);
       }

       /**
        * CMS 결과반영 데이터 업로드
        * 임동진
        * 20181022
       */
       public void dataFileUploadOld(HttpServletRequest request, HttpServletResponse response, Map<String, Object> mResuslt) throws Exception {

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
                               System.out.println("읽어온 라인은 ::: " + sLineData);
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
                           mParam.put("result_key"		, fileLayout[i].substring(0,8) + strReqday + "04N")	    ; // CMS일련번호
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
        * CMS 결과반영 데이터 업로드(NEW)
        * 송준빈
        * 20210823
        */
       public void dataFileUpload(HttpServletRequest request, HttpServletResponse response, Map<String, Object> mResuslt) throws Exception {
           String tempDir = System.getProperty("java.io.tmpdir");
           MultipartRequest multipartRequest = new MultipartRequest(request, tempDir, 1024*1024*20);
           Enumeration enm = multipartRequest.getFileNames();

           String sKey = "";
           String[] fileLayout = new String[80000];
           String[] fileLayout_T = null; //회원 결과 로우데이터 하단 종합 결과
           int fileCnt   = 0;

           UserSession oUserSession = (UserSession) SessionUtils.getLoginUser();
           String empleNo = CommonUtils.nvls(oUserSession.getUserid());

           try
           {
               if ("".equals(empleNo))
               {
                   throw new EgovBizException("업로드 권한이 없습니다.");
               }

               while (enm.hasMoreElements())
               {
                   fileCnt++;
                   sKey = (String)enm.nextElement();

                   File upfile = multipartRequest.getFile(sKey);

                   //입력 버퍼 생성
                   BufferedReader br  =  new BufferedReader(new InputStreamReader(new FileInputStream(upfile), "euc-kr"));
                   String bfLine = "";

                   int totCnt_1 = 0; //결과 데이터 하단 종합 건수
                   int totCnt_2 = 0; //결과 데이터 레코드 건수
                   String strReqDay = ""; //청구 일자

                   bfLine = br.readLine();

                   //결과 청구 일자 가져오기
                   strReqDay = "20" + bfLine.substring(27,33);

                   //결과 레코드 담기
                   bfLine = bfLine.substring(151);

                   //결과 레코드 다음행 처리
                   int iStartDataByteIndex = 0;
                   int iExpressionDataLineLength = 142;
                   int iFileLayoutArrayIndex = 0;
                   String sLineData = "";

                   while(bfLine != null && !bfLine.equals(""))
                   {
                       sLineData = bfLine.substring(iStartDataByteIndex, iStartDataByteIndex + iExpressionDataLineLength);
                       if(sLineData.substring(0, 8).equals("99999999")){
                           break;
                       }
                       else {
                           fileLayout[iFileLayoutArrayIndex] = sLineData;
                           iFileLayoutArrayIndex++;
                           iStartDataByteIndex += iExpressionDataLineLength;
                       }
                   }

                   System.out.println("데이터 개수 :: " + iFileLayoutArrayIndex);

                   // 결과 레코드 하단 결과 조회
                   fileLayout_T = bfLine.split("T99999999");

                   totCnt_1 = Integer.parseInt(fileLayout_T[1].substring(18,26));
                   totCnt_2 = iFileLayoutArrayIndex;

                   if (totCnt_1 != totCnt_2)
                   {
                       br.close();
                       throw new EgovBizException("결과 데이터의 개수가 맞지 않습니다.(전산팀 전달["+ totCnt_1 + "<>" + totCnt_2 + "])" );
                   }
                   else
                   {
                       br.close();
                   }

                   System.out.println("totCnt_1>>>>>>>" + totCnt_1);
                   System.out.println("totCnt_2>>>>>>>" + totCnt_2);

                   Connection connection = null;
                   PreparedStatement preparedStatement = null ;

                   String sAccessClassName = EgovProperties.getProperty("Globals.Fix.Database.Access.DriverClassName");
                   String sAccessIp        = EgovProperties.getProperty("Globals.Fix.Database.Access.Ip");
                   String sAccessUserName  = EgovProperties.getProperty("Globals.Fix.Database.Access.UserName");
                   String sAccessPassword  = EgovProperties.getProperty("Globals.Fix.Database.Access.Password");

                    System.out.println("프로퍼티[1] :: " + sAccessClassName + "," + "프로퍼티[2] :: " + sAccessIp + "," +
                                       "프로퍼티[3] :: " + sAccessUserName + "," + "프로퍼티[4] :: " + sAccessPassword);

                   String sqlStatement = "INSERT INTO TB_MEMBER_WDRW_RESULT (ACCNT_NO, REQ_DAY, REQ_NO, PAY_KIND, ";
                   sqlStatement += "RESULT_KEY, RESULT_CD, RESULT_MSG, PAY_MTHD, AUTH_DT, AUTH_CD, ETC, TID, ICHAE_CD, REG_DM, REG_MAN)";
                   sqlStatement += "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE, ?)";

                   Class.forName(sAccessClassName);
                   connection = DriverManager.getConnection(sAccessIp, sAccessUserName, sAccessPassword);
                   connection.setAutoCommit(false);
                   preparedStatement = connection.prepareStatement(sqlStatement) ;

                   for(int i = 0; i < iFileLayoutArrayIndex; i++)
                   {
                       String sAccntNo = fileLayout[i].substring(82,94); // 회원번호
                       String sReqDay = strReqDay; // 청구일
                       String sReqNo = "999"; // 청구납입회차
                       String sPayKind = "01"; // 01:파일, 02:실시간, 03:자유,04:기타
                       String sResultKey = fileLayout[i].substring(0,8) + strReqDay + "04N"; // CMS일련번호
                       String sResultCd = fileLayout[i].substring(68,72); // 결과 코드
                       String sResultMsg = "CMS실패"; // 결과 메세지
                       String sPayMthd = "04"; // 납입방법(CMS)
                       String sAuthDt = ""; // 승인일자(카드)
                       String sAuthCd = ""; // 승인번호(카드)
                       String sEtc = fileLayout[i].substring(72,80); // 통장기재내용
                       String sTid = fileLayout[i].substring(0,8) + strReqDay + "04N"; // CMS일련번호
                       String sIchaeCd = fileLayout[i].substring(18,21); // 출금참가기관(은행)점코드

                       preparedStatement.setString(1, sAccntNo.trim());
                       preparedStatement.setString(2, sReqDay.trim());
                       preparedStatement.setString(3, sReqNo.trim());
                       preparedStatement.setString(4, sPayKind.trim());
                       preparedStatement.setString(5, sResultKey.trim());
                       preparedStatement.setString(6, sResultCd.trim());
                       preparedStatement.setString(7, sResultMsg.trim());
                       preparedStatement.setString(8, sPayMthd.trim());
                       preparedStatement.setString(9, sAuthDt.trim());
                       preparedStatement.setString(10, sAuthCd.trim());
                       preparedStatement.setString(11, sEtc.trim());
                       preparedStatement.setString(12, sTid.trim());
                       preparedStatement.setString(13, sIchaeCd.trim());
                       preparedStatement.setString(14, empleNo.trim());

                       preparedStatement.addBatch();

                       // 파라미터 Clear
                       preparedStatement.clearParameters() ;


                       // OutOfMemory를 고려하여 만건 단위로 커밋
                       if( (i % 10000) == 0)
                       {
                           // Batch 실행
                           preparedStatement.executeBatch();

                           // Batch 초기화
                           preparedStatement.clearBatch();

                           // 커밋
                           connection.commit();
                       }
                   }

                   preparedStatement.executeBatch() ;
                   connection.commit() ;
               }

               if (fileCnt < 1)
               {
                   throw new EgovBizException("업로드된 파일이 없습니다.");
               }
           }
           catch (FileNotFoundException ex)
           {
               ex.printStackTrace();
               throw ex;
           }
           catch(Exception e)
            {
                e.printStackTrace();
            }
       }

       /**
        * CMS 결과반영 데이터 업로드(NEW)
        * 임동진
        * 20220615
        */
       public void cmsDataFileUpload(HttpServletRequest request, HttpServletResponse response, Map<String, Object> mResuslt) throws Exception {
           String tempDir = System.getProperty("java.io.tmpdir");
           MultipartRequest multipartRequest = new MultipartRequest(request, tempDir, 1024*1024*20);

           //업로드파일객체관리
           Enumeration Enm = multipartRequest.getFileNames();

           String sKey = "";
           String[] fileLayout_T = null; //회원 결과 로우데이터 하단 종합 결과
           String bfLine = "";
           String strReqDay = ""; //청구 일자

           int totCnt_1 = 0; //결과 데이터 하단 종합 건수
           int totCnt_2 = 0; //결과 데이터 레코드 건수

           UserSession oUserSession = (UserSession) SessionUtils.getLoginUser();
           String empleNo = CommonUtils.nvls(oUserSession.getUserid());

           try {
                       //객체 파일명 가져옴(STRING)
                       sKey = (String)Enm.nextElement();

                       //서버에 업로드된 파일 객체 자체를 반환
                    File upfile = multipartRequest.getFile(sKey);

                    //입력 버퍼 생성
                    BufferedReader br  =  new BufferedReader(new InputStreamReader(new FileInputStream(upfile), "euc-kr"));

                    String[] parseLine = br.readLine().split("     R",-1);	//마지막 배열 데이터가 비어있을때 -1

                    br.close();	//종료

                    // 결과 레코드 하단 결과 조회
                    fileLayout_T = parseLine[parseLine.length-1].split("T99999999");

                    //strReqDay = "20" + fileLayout_T[1].substring(12,18);
                    strReqDay = "20" + parseLine[0].substring(27, 33); //첫줄 년월일
                    totCnt_1 = Integer.parseInt(fileLayout_T[1].substring(18,26));
                    totCnt_2 = parseLine.length - 1;

                    System.out.println("XXXXXXXXXX CHEKING XXXXXXXXXXX" + totCnt_1 + "<>" + totCnt_2);

                    if (totCnt_1 != totCnt_2) {
                        throw new EgovBizException("결과 데이터의 개수가 맞지 않습니다.(전산팀 전달["+ totCnt_1 + "<>" + totCnt_2 + "])" );
                    }

                    Connection connection = null;
                    PreparedStatement preparedStatement = null ;

                    String sAccessClassName = EgovProperties.getProperty("Globals.Fix.Database.Access.DriverClassName");
                    String sAccessIp        = EgovProperties.getProperty("Globals.Fix.Database.Access.Ip");
                    String sAccessUserName  = EgovProperties.getProperty("Globals.Fix.Database.Access.UserName");
                    String sAccessPassword  = EgovProperties.getProperty("Globals.Fix.Database.Access.Password");

                    String sqlStatement = "INSERT INTO TB_MEMBER_WDRW_RESULT (ACCNT_NO, REQ_DAY, REQ_NO, PAY_KIND, ";
                    sqlStatement += "RESULT_KEY, RESULT_CD, RESULT_MSG, PAY_MTHD, AUTH_DT, AUTH_CD, ETC, TID, ICHAE_CD, REG_DM, REG_MAN)";
                    sqlStatement += "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE, ?)";

                    Class.forName(sAccessClassName);
                    connection = DriverManager.getConnection(sAccessIp, sAccessUserName, sAccessPassword);
                    connection.setAutoCommit(false);
                    preparedStatement = connection.prepareStatement(sqlStatement) ;
                    preparedStatement.setQueryTimeout(1000);

                    for(int i=1; i<parseLine.length;i++){
                       //System.out.println("XXXXXXXXXX CHEKING........ XXXXXXXXXXX" +parseLine[i+1]);
                       String sAccntNo = parseLine[i].substring(82,94); // 회원번호
                       String sReqDay = strReqDay; // 청구일
                       String sReqNo = "999"; // 청구납입회차
                       String sPayKind = "01"; // 01:파일, 02:실시간, 03:자유,04:기타
                       String sResultKey = parseLine[i].substring(0,8) + strReqDay + "04N"; // CMS일련번호
                       String sResultCd = parseLine[i].substring(68,72); // 결과 코드
                       String sResultMsg = "CMS실패"; // 결과 메세지
                       String sPayMthd = "04"; // 납입방법(CMS)
                       String sAuthDt = ""; // 승인일자(카드)
                       String sAuthCd = ""; // 승인번호(카드)
                       String sEtc = parseLine[i].substring(72,80); // 통장기재내용
                       String sTid = parseLine[i].substring(0,8) + strReqDay + "04N"; // CMS일련번호
                       String sIchaeCd = parseLine[i].substring(18,21); // 출금참가기관(은행)점코드

                       preparedStatement.setString(1, sAccntNo.trim());
                       preparedStatement.setString(2, sReqDay.trim());
                       preparedStatement.setString(3, sReqNo.trim());
                       preparedStatement.setString(4, sPayKind.trim());
                       preparedStatement.setString(5, sResultKey.trim());
                       preparedStatement.setString(6, sResultCd.trim());
                       preparedStatement.setString(7, sResultMsg.trim());
                       preparedStatement.setString(8, sPayMthd.trim());
                       preparedStatement.setString(9, sAuthDt.trim());
                       preparedStatement.setString(10, sAuthCd.trim());
                       preparedStatement.setString(11, sEtc.trim());
                       preparedStatement.setString(12, sTid.trim());
                       preparedStatement.setString(13, sIchaeCd.trim());
                       preparedStatement.setString(14, empleNo.trim());

                       preparedStatement.addBatch();

                       // 파라미터 Clear
                       preparedStatement.clearParameters() ;

                       // OutOfMemory를 고려하여 만건 단위로 커밋
                       if( (i % 1000) == 0)
                       {
                           // Batch 실행
                           preparedStatement.executeBatch();

                           // Batch 초기화
                           preparedStatement.clearBatch();

                           // 커밋
                           connection.commit();
                       }

                       preparedStatement.executeBatch() ;
                       connection.commit() ;
                    }

                    preparedStatement.close();
                    connection.close();
                    //bfLine = br.readLine().substring(0,151);
            } catch(Exception e) {
                e.printStackTrace();
                throw e;
            }

       }

       /**
        * 카드 / CMS 결과데이터 인서트 (실시간, 파일배치)
        * 임동진
        * 20181015
       */
       public int insertReqWdrwResult(Map<String, ?> pmParam) throws Exception {
           return ReqCustDAO.insertReqWdrwResult(pmParam);
       }

       /** ================================================================
         * 날짜 : 20190412
         * 이름 : 송준빈
         * 추가내용 : 일일청구 카드 회신 데이터 리스트 INSERT
         * 대상 테이블 : LF_DMUSER.TB_CARD_RECV_FILE_LIST
         * ================================================================
         * */
        public int insertCardRecvFileList(Map<String, ?> pmParam) throws Exception {
            return ReqCustDAO.insertCardRecvFileList(pmParam);
        }

        /** ================================================================
         * 날짜 : 20190412
         * 이름 : 송준빈
         * 추가내용 : 일일청구 카드 회신 데이터 리스트 조회
         * 대상 테이블 : LF_DMUSER.TB_CARD_RECV_FILE_LIST
         * ================================================================
         * */
        public List<Map<String, Object>> getCardRecvFileList(Map<String, Object> pmParam) {
            return ReqCustDAO.getCardRecvFileList(pmParam);
        }

        /** ================================================================
         * 날짜 : 20190412
         * 이름 : 송준빈
         * 추가내용 : 이니시스카드 회신파일 존재여부 확인
         * 대상 테이블 : LF_DMUSER.TB_INICARD_RECV_FILE_LIST
         * ================================================================
         * */
        public List<Map<String, Object>> getIniCardRecvFileConfirm(Map<String, Object> pmParam) {
            return ReqCustDAO.getIniCardRecvFileConfirm(pmParam);
        }

        /** ================================================================
         * 날짜 : 20181025
         * 이름 : 송준빈
         * 추가내용 : Card File List 수신리스트 결과 반영 여부 확인
         * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_RSLT
         * ================================================================
         * */
        public int getWdrwResultCount(Map<String, Object> pmParam) {
            return ReqCustDAO.getWdrwResultCount(pmParam);
        }

        /** ================================================================
         * 날짜 : 20190412
         * 이름 : 송준빈
         * 추가내용 :  일일청구 카드 회신 데이터 리스트 UPDATE
         * 대상 테이블 : LF_DMUSER.TB_CARD_RECV_FILE_LIST
         * ================================================================
         * */
        public int updateCardRecvFileList(Map<String, ?> pmParam) throws Exception {
            return ReqCustDAO.updateCardRecvFileList(pmParam);
        }

        /** ================================================================
         * 날짜 : 20190412
         * 이름 : 송준빈
         * 추가내용 : 일일청구 카드 회신 데이터 (무승인) 저장 여부
         * 대상 테이블 : LF_DMUSER.TB_CARDNAUTH_RECV_FILE_LIST
         * ================================================================
         * */
        public int getCardNauthRecvFileExist(Map<String, Object> pmParam) {
            return ReqCustDAO.getCardNauthRecvFileExist(pmParam);
        }

        /** ================================================================
         * 날짜 : 20181018
         * 이름 : 송준빈
         * 추가내용 : 출금이체의뢰내역(청구) 조회건수
         * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
         * ================================================================
         * */
        public int getWdrwReqListCount(Map<String, Object> pmParam) {
            return ReqCustDAO.getWdrwReqListCount(pmParam);
        }

        /** ================================================================
         * 날짜 : 20181018
         * 이름 : 송준빈
         * 추가내용 : 출금이체의뢰내역(청구) 조회리스트
         * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
         * ================================================================
         * */
        public List<Map<String, Object>> getPayWdrwReqList(Map<String, Object> pmParam) {
            return ReqCustDAO.getPayWdrwReqList(pmParam);
        }

        /** ================================================================
         * 날짜 : 20181026
         * 이름 : 송준빈
         * 추가내용 : 출금이체의뢰내역(청구) Card 총 합계
         * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
         * ================================================================
         * */
        public List<Map<String, Object>> getCalcAndChargeCardSummary(Map<String, Object> pmParam) {
            return ReqCustDAO.getCalcAndChargeCardSummary(pmParam);
        }

        /** ================================================================
         * 날짜 : 20191223
         * 이름 : 송준빈
         * 추가내용 : 출금이체의뢰내역(청구) Card무승인 총 합계
         * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
         * ================================================================
         * */
        public List<Map<String, Object>> getCalcAndChargeCardNauthSummary(Map<String, Object> pmParam) {
            return ReqCustDAO.getCalcAndChargeCardNauthSummary(pmParam);
        }

        /** ================================================================
         * 날짜 : 20181026
         * 이름 : 송준빈
         * 추가내용 : 출금이체의뢰내역(청구) CMS 총 합계
         * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
         * ================================================================
         * */
        public List<Map<String, Object>> getCalcAndChargeCMSSummary(Map<String, Object> pmParam) {
            return ReqCustDAO.getCalcAndChargeCMSSummary(pmParam);
        }

        /**
         * 특수회원 카운트조회
         * 정승철
         * 20181019
         * @param pmParam 검색 조건
         * @return
         * @throws Exception
         */
        public int getCntSpecialAcntList(Map<String, ?> pmParam) throws Exception {
            return ReqCustDAO.getCntSpecialAcntList(pmParam);
        }

        /**
         * 특수회원 조회
         * 정승철
         * 20181018
         * @param pmParam 검색 조건
         * @return
         * @throws Exception
         */
        public List<Map<String, Object>> getSpecialAcntList(Map<String, ?> pmParam) throws Exception {
            return ReqCustDAO.getSpecialAcntList(pmParam);
        }

        /**
         * 특수회원 입력체크
         * 정승철
         * 20181113
         * @param pmParam 검색 조건
         * @return
         * @throws Exception
         */
        public int getChkSpecialAcntList(Map<String, ?> pmParam) throws Exception {
            return ReqCustDAO.getChkSpecialAcntList(pmParam);
        }

        /**
         * 특수회원 입력
         * 정승철
         * 20181019
         * @param pmParam 검색 조건
         * @return
         * @throws Exception
         */
        public void insertExtSpecial(Map<String, ?> pmParam) throws Exception {

            ReqCustDAO.insertExtSpecial(pmParam);
        }

        /**
         * 특수회원 수정
         * 정승철
         * 20181019
         * @param pmParam 검색 조건
         * @return
         * @throws Exception
         */
        public void updateExtSpecial(Map<String, ?> pmParam) throws Exception {

            ReqCustDAO.updateExtSpecial(pmParam);
        }


        /**
         * 특수회원 삭제
         * 정승철
         * 20181019
         * @param pmParam 검색 조건
         * @return
         * @throws Exception
         */
        public void deleteExtSpecial(Map<String, ?> pmParam) throws Exception {

            ReqCustDAO.deleteExtSpecial(pmParam);
        }

        /** ================================================================
         * 날짜 : 20181029
         * 이름 : 정승철
         * 추가내용 : Card 결과로그 List 카운트 조회
         * ================================================================
         * */
        public int getReqRltmCardLogCount(Map<String, Object> pmParam) {
            return ReqCustDAO.getReqRltmCardLogCount(pmParam);
        }

        /** ================================================================
         * 날짜 : 20181029
         * 이름 : 정승철
         * 추가내용 : Card 결과로그 List 조회
         * ================================================================
         * */
        public List<Map<String, Object>> getReqRltmCardLogList(Map<String, ?> pmParam) throws Exception {
            return ReqCustDAO.getReqRltmCardLogList(pmParam);
        }

        /**
         * 고객-구좌 정보의 검색 수를 반환한다.
         *
         * @param pmParam 검색 조건
         * @return 고객-구좌 정보의 검색 건수
         * @throws Exception
         */
        public int getDlwCustAcntCount(Map<String, ?> pmParam) throws Exception {
            return (Integer) ReqCustDAO.getDlwCustAcntCount(pmParam);
        }

        /**
         * 고객-구좌 정보를 검색한다.
         *
         * @param pmParam 검색 조건
         * @return 고객-구좌 정보 목록
         * @throws Exception
         */
        public List<Map<String, Object>> getDlwCustAcntList(Map<String, ?> pmParam) throws Exception {
            return ReqCustDAO.getDlwCustAcntList(pmParam);
        }

        /** ================================================================
         * 날짜 : 20181107
         * 이름 : 임동진
         * 추가내용 : 실시간 카드 결제 취소 결과 반영
         * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_RESULT
         * ================================================================
         * */
        public Map<String, Object> updateRealtimeCardCancel(Map<String, Object> pmParam) throws Exception {
            String result_code = "";
            String result_msg = "";
            String filePayChkFg = "Y";

            if(!"Y".equals(filePayChkFg)) {
                pmParam.put("pay_result_msg", "파일결제의 입금일이 잘못되어 취소할 수 없습니다.");
            } else {
                Map<String, Object> cancResult = new HashMap();

                cancResult = CardCancelBillPay(pmParam);

                result_code = String.valueOf(cancResult.get("result_code"));
                result_msg = String.valueOf(cancResult.get("result_msg"));
                String cncl_day = String.valueOf(cancResult.get("cncl_day"));
                String cncl_tm = String.valueOf(cancResult.get("cncl_tm"));
                String uip = String.valueOf(cancResult.get("uip"));
                if("2001".equals(result_code))
                {
                    pmParam.put("result_code", "03");
                    pmParam.put("uip", uip);
                    pmParam.put("result_msg", result_msg);
                    pmParam.put("cncl_day", cncl_day);
                    pmParam.put("cncl_tm", cncl_tm);
                    pmParam.put("cncl_day", cncl_day);
                    pmParam.put("type", "cancel");

                    ReqCustDAO.updateCardResult(pmParam);
                    /*
                    if("0003".equals(menu_gbn) || "0004".equals(menu_gbn))
                    {
                        pmParam.put("pay_no", tmp_pay_no);
                        DlwCardDAO.updateDlwCardWdrwReqCanc(pmParam);
                    }
                    */

                } else{
                    pmParam.put("result_code", "01");
                    pmParam.put("result_msg", result_msg);
                    pmParam.put("type", "cancel_fail");
                }

                System.out.println("xxxxxxx" + pmParam);
            }
            return pmParam;
        }

        /** ================================================================
         * 날짜 : 20200504
         * 이름 : 임동진
         * 추가내용 : 실시간 카드 결제 취소 결과 반영(이니시스)
         * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_RESULT
         * ================================================================
         * */
        public Map<String, Object> updateRealtimeInicisCardCancel(Map<String, Object> pmParam) throws Exception {
            String result_code = "";
            String result_msg = "";
            String filePayChkFg = "Y";

            if(!"Y".equals(filePayChkFg)) {
                pmParam.put("pay_result_msg", "파일결제의 입금일이 잘못되어 취소할 수 없습니다.");
            } else {

                INICISPay inicisPay = new INICISPay();
                 inicisPay.setInicisKey("hmPLjIdyekyylSx9");
                 inicisPay.setInicisiv("fS7oGOerwBsEcQ==");
                 //inicisPay.setData("mid", "daemyungT1");
                 inicisPay.setData("mid", (String)pmParam.get("bill_key"));
                inicisPay.setData("clientIp", "127.0.0.1");
                inicisPay.setData("tid", (String)pmParam.get("tid"));
                inicisPay.setData("msg", "DLCC프로세스 취소");

                inicisPay.cancel();

                String resultCode = inicisPay.getData("resultCode");
                String resultMsg = inicisPay.getData("resultMsg");
                String cncl_day = inicisPay.getData("cancelDate");
                String cncl_tm = inicisPay.getData("cancelTime");

                String tid = inicisPay.getData("tid");
                String billKey = inicisPay.getData("billKey");

                if("00".equals(resultCode))
                {
                    pmParam.put("result_code", resultCode);
                    //pmParam.put("uip", uip);
                    pmParam.put("result_msg", resultMsg);
                    pmParam.put("cncl_day", cncl_day);
                    pmParam.put("cncl_tm", cncl_tm);
                    pmParam.put("type", "cancel");

                    ReqCustDAO.updateCardResult(pmParam);


                } else{
                    pmParam.put("result_code", resultCode);
                    pmParam.put("result_msg", resultMsg);
                    pmParam.put("type", "cancel_fail");
                }
            }
            return pmParam;
        }

        /** ================================================================
         * 날짜 : 20200504
         * 이름 : 임동진
         * 추가내용 : 실시간 카드 결제 취소 결과 반영(이니시스)
         * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_RESULT
         * ================================================================
         * */
        public Map<String, Object> saveTransInicisCardCancel(Map<String, Object> pmParam) throws Exception {
            String result_code = "";
            String result_msg = "";
            String filePayChkFg = "Y";

            if(!"Y".equals(filePayChkFg)) {
                pmParam.put("pay_result_msg", "파일결제의 입금일이 잘못되어 취소할 수 없습니다.");
            } else {

                INICISPay inicisPay = new INICISPay();
                 inicisPay.setInicisKey("hmPLjIdyekyylSx9");
                 inicisPay.setInicisiv("fS7oGOerwBsEcQ==");
                 inicisPay.setData("mid", "daemyungT1");
                inicisPay.setData("clientIp", "127.0.0.1");
                inicisPay.setData("tid", (String)pmParam.get("tid"));
                inicisPay.setData("msg", "DLCC프로세스 취소");

                inicisPay.cancel();

                String resultCode = inicisPay.getData("resultCode");
                String resultMsg = inicisPay.getData("resultMsg");
                String cncl_day = inicisPay.getData("cancelDate");
                String cncl_tm = inicisPay.getData("cancelTime");
                String sReqDay = (String)pmParam.get("req_day");

                String tid = inicisPay.getData("tid");
                String billKey = inicisPay.getData("billKey");

                if("00".equals(resultCode))
                {
                    pmParam.put("result_code", resultCode);
                    //pmParam.put("uip", uip);
                    pmParam.put("result_msg", resultMsg);
                    pmParam.put("cncl_day", cncl_day);
                    pmParam.put("cncl_tm", cncl_tm);
                    pmParam.put("type", "cancel");
                    pmParam.put("pay_cancel_day", sReqDay);
                    pmParam.put("pay_cancel_cd", "0001");
                    pmParam.put("pay_cancel_note", "전환결제 환불등록");

                    ReqCustDAO.saveTransInicisCardCancel(pmParam);
                } else{
                    pmParam.put("result_code", resultCode);
                    pmParam.put("result_msg", resultMsg);
                    pmParam.put("type", "cancel_fail");
                }
            }
            return pmParam;
        }

        /**
         * NicePay Card 빌링결제 취소
         * @param pmParam 검색 조건
         * @return 취소 여부
         * @throws Exception
         */
        public Map<String, Object> CardCancelBillPay(Map<String, Object> pmParam)
                throws Exception
        {
            String payAmt = String.valueOf(pmParam.get("pay_amt"));
            String tid = String.valueOf(pmParam.get("tid"));
            NicePayProcess nicepay = new NicePayProcess();
            try
            {
                nicepay = niceBillSetting(pmParam,"Real_Key");
                nicepay.setAmt(payAmt);
                nicepay.setTid(tid);
                nicepay.setCancelPwd("1111");     // 대명 취소 비밀번호
                //nicepay.setCancelPwd("123456"); //nicepay 테스트 취소 비밀번호
                nicepay.setCancelMsg((String)pmParam.get("emple_no"));

                NicePayWebConnector result = nicepay.doCancel();

                String resultCode = result.getResultData("ResultCode");
                String resultMsg = result.getResultData("ResultMsg");
                String cnclDay = result.getResultData("CancelDate");
                String cnclTm = result.getResultData("CancelTime");

                InetAddress inet = InetAddress.getLocalHost();
                String uip = inet.getHostAddress();
                pmParam.put("uip", uip);
                pmParam.put("result_code", resultCode);
                pmParam.put("result_msg", (new StringBuilder(String.valueOf(resultCode))).append(" : ").append(resultMsg).toString());
                pmParam.put("cncl_day", cnclDay);
                pmParam.put("cncl_tm", cnclTm);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return pmParam;
        }

        /** ================================================================
         * 날짜 : 20181031
         * 이름 : 정승철
         * 추가내용 : 빌키 생성전 설정
         * ================================================================
         * */
        public NicePayProcess niceBillSetting(Map<String, Object> pmParam, String gbn) throws Exception {
            NicePayProcess nicepay = new NicePayProcess();

            ParamUtils.addCenterParam(pmParam);

            String sPayFilePath = basVlService.getBasVlAsString("pay_file_path", (String) pmParam.get("cntr_cd"));

            nicepay.setNicePayHome(sPayFilePath+"/web_site/nicelog");
            //nicepay.setNicePayHome("c:/web_site/nicelog");

            //String menu_gbn = String.valueOf(pmParam.get("menu_gbn"));
            String prod_cd = String.valueOf(pmParam.get("prod_cd"));
            String bid = String.valueOf(pmParam.get("bid"));

            //MID 및 KEY값 DB에서 가져오기
            String strMidKey = ReqCustDAO.getAccntMid(pmParam);
            String mid = strMidKey.substring(0,10);  //mid
            String key = strMidKey.substring(10);	 //key

            /* 자유 결제 시 키를 별도 지정*/
            if (pmParam.get("pay_kind").equals("03")){
                mid = "dmlife001m";
                key = "Zq7E5qjYbqPMZWHvlZtt1HbC8jJIPfAkVkkGOvf7gRoKfweOMCiHu/VPob5uGrIDyYQyplxnknTXxX2D8F+emA==";
            }

            System.out.println("pay_kind      xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx>  :" + pmParam.get("pay_kind"));
            System.out.println("mid      xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx>  :" + mid);
            System.out.println("key      xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx>  :" + key);

            nicepay.setMID(mid);
            nicepay.setMerchantKey(key);

            return nicepay;
        }

        /**
         * 대명라이프웨이 나이스페이 실시간결제 취소
         *
         * @param pmParam 검색 조건
         * @return 취소여부
         * @throws Exception
         */
        public Map<String, Object> updateDlwPymnCancNicepay(Map<String, Object> pmParam) throws Exception {
            String result_code = "";
            String result_msg = "";
            String tmp_pay_no = String.valueOf((Integer.parseInt(String.valueOf(pmParam.get("pay_no"))) + Integer.parseInt(String.valueOf(pmParam.get("pay_cnt")))) - 1);
            String menu_gbn = String.valueOf(pmParam.get("menu_gbn"));
            String filePayChkFg = "Y";

            if(!"Y".equals(filePayChkFg)) {
                pmParam.put("pay_result_msg", "파일결제의 입금일이 잘못되어 취소할 수 없습니다.");
            } else {
                Map<String, Object> cancResult = new HashMap();
                cancResult = cancelBillPay(pmParam);

                result_code = String.valueOf(cancResult.get("result_code"));
                result_msg = String.valueOf(cancResult.get("result_msg"));
                String cncl_day = String.valueOf(cancResult.get("cncl_day"));
                String cncl_tm = String.valueOf(cancResult.get("cncl_tm"));
                String uip = String.valueOf(cancResult.get("uip"));
                if("2001".equals(result_code))
                {
                    pmParam.put("result_code", "03");
                    "0002".equals(menu_gbn);
                    pmParam.put("uip", uip);
                    pmParam.put("result_msg", result_msg);
                    pmParam.put("cncl_day", cncl_day);
                    pmParam.put("cncl_tm", cncl_tm);
                    pmParam.put("cncl_day", cncl_day);
                    pmParam.put("type", "cancel");
                    ReqCustDAO.updateDlwCardAckdCancLog(pmParam);
                    if("0003".equals(menu_gbn) || "0004".equals(menu_gbn))
                    {
                        pmParam.put("pay_no", tmp_pay_no);
                        ReqCustDAO.updateDlwCardWdrwReqCanc(pmParam);
                    }
                } else{
                    pmParam.put("result_code", "01");
                    pmParam.put("result_msg", result_msg);
                    pmParam.put("type", "cancel_fail");
                }
            }
            return pmParam;
        }

        /**
         * NicePay Card 빌링결제 취소
         *
         * @param pmParam 검색 조건
         * @return 취소 여부
         * @throws Exception
         */
        public Map<String, Object> cancelBillPay(Map<String, Object> pmParam)
                throws Exception
            {
                String payAmt = String.valueOf(pmParam.get("pay_amt"));
                String tid = String.valueOf(pmParam.get("tid"));
                NicePayProcess nicepay = new NicePayProcess();
                try
                {
                    nicepay = niceBillSetting(pmParam,"Real_Key");
                    nicepay.setAmt(payAmt);
                    nicepay.setTid(tid);
                    nicepay.setCancelPwd("1111");     // 대명 취소 비밀번호
                    //nicepay.setCancelPwd("123456"); //nicepay 테스트 취소 비밀번호
                    nicepay.setCancelMsg((String)pmParam.get("emple_no"));

                    NicePayWebConnector result = nicepay.doCancel();

                    String resultCode = result.getResultData("ResultCode");
                    String resultMsg = result.getResultData("ResultMsg");
                    String cnclDay = result.getResultData("CancelDate");
                    String cnclTm = result.getResultData("CancelTime");

                    InetAddress inet = InetAddress.getLocalHost();
                    String uip = inet.getHostAddress();
                    pmParam.put("uip", uip);
                    pmParam.put("result_code", resultCode);
                    pmParam.put("result_msg", (new StringBuilder(String.valueOf(resultCode))).append(" : ").append(resultMsg).toString());
                    pmParam.put("cncl_day", cnclDay);
                    pmParam.put("cncl_tm", cnclTm);
                    pmParam.put("cncl_day", cnclDay);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                return pmParam;
            }

        /** ================================================================
         * 날짜 : 20181022
         * 이름 : 송준빈
         * 추가내용 : 출금이체의뢰내역(산출, 청구(행사, 해약)) 조회
         * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
         * ================================================================
         * */
        public List<Map<String, Object>> getWdrwReqAccntEventConfirm(String accnt_no) throws Exception {
            return ReqCustDAO.getWdrwReqAccntEventConfirm(accnt_no);
        }

        /** ================================================================
         * 날짜 : 20190924
         * 이름 : 송준빈
         * 추가내용 : 전환결제 취소
         * 대상 테이블 : LF_DMUSER.TB_MEMBER_REQ_REFUND
         * ================================================================
         * */
        public int sendCancelNicePayment(Map<String, Object> pmParam) throws Exception {
            return ReqCustDAO.sendCancelNicePayment(pmParam);
        }

        /** ================================================================
         * 날짜 : 20191209
         * 이름 : 송준빈
         * 추가내용 : 카드 무승인 결제 취소건 조회
         * 대상 테이블 : LF_DMUSER.TB_MEMBER_REQ_NAUTH_CNCL
         * ================================================================
         * */
        public List<Map<String, Object>> getNauthPayCancel(Map<String, ?> pmParam) throws Exception {
            return ReqCustDAO.getNauthPayCancel(pmParam);
        }

        /** ================================================================
         * 날짜 : 20191216
         * 이름 : 김주용
         * 추가내용 : 무승인 등록 취소
         * 대상 테이블 : TB_MEMBER_REQ_NAUTH_CNCL
         * ================================================================
         * */
        public int cancelNauthpayDelete(Map<String, Object> pmParam) {
            return ReqCustDAO.cancelNauthpayDelete(pmParam);
        }

        /** ================================================================
         * 날짜 : 20191216
         * 이름 : 김주용
         * 추가내용 : 무승인 환불
         * 대상 테이블 : TB_MEMBER_REQ_NAUTH_CNCL
         * ================================================================
         * */
        public int cancelNauthpayRefund(Map<String, Object> pmParam) {
            return ReqCustDAO.cancelNauthpayRefund(pmParam);
        }

        /** ================================================================
         * 날짜 : 20200227
         * 이름 : 송준빈
         * 추가내용 : 회원별 카드,CMS 결과데이터 조회
         * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ, LF_DMUSER.TB_MEMBER_WDRW_RESULT, LF_DMUSER.TB_MEMBER_REQ_REFUND
         * ================================================================
         * */
        public List<Map<String, Object>> getWdrwAccntLogList(Map<String, ?> pmParam) throws Exception {
            return ReqCustDAO.getWdrwAccntLogList(pmParam);
        }

        /** ================================================================
         * 날짜 : 메인
         * 이름 : /dlw/cust/cust-acnt/list
         * 추가내용 :
         * 대상 테이블 :
         * ================================================================
         * */
        public int getMemberRefundListCount(Map<String, ?> pmParam) throws Exception {
            return ReqCustDAO.getMemberRefundListCount(pmParam);
        }

        public List<Map<String, Object>> getMemberRefundList(Map<String, ?> pmParam) throws Exception {
            return ReqCustDAO.getMemberRefundList(pmParam);
        }

        /** ================================================================
         * 날짜 : 메인
         * 이름 : /dlw/cms/gasu-dtl/list
         * 추가내용 :
         * 대상 테이블 :
         * ================================================================
         * */

        /**
         * 사원 상세정보를 검색한다.
         *
         * @param pmParam 검색 조건
         * @return 사원 상세정보
         * @throws Exception
         */
        public Map<String, Object> getEmplDtptList(String psParam) throws Exception {
            Map<String, Object> pmParam = new HashMap<String, Object>();
            pmParam.put("emple_no", psParam);
            return ReqCustDAO.getEmplDtptList(pmParam);
        }

        /**
         * 데이터 권한별 검색조건을 검색한다.
         *
         * @param pmParam 검색 조건
         * @return 데이터 권한별 검색조건
         * @throws Exception
         */
        public String getDataAthrFunc(Map<String, ?> pmParam) throws Exception {
            return ReqCustDAO.getDataAthrFunc(pmParam);
        }

        /**
         * 고객-구좌 정보의 검색 수를 반환한다.
         *
         * @param pmParam 검색 조건
         * @return 고객-구좌 정보의 검색 건수
         * @throws Exception
         */
        public int getDlwCustAccntListCount(Map<String, ?> pmParam) throws Exception {
            return (Integer) ReqCustDAO.getDlwCustAccntListCount(pmParam);
        }

        /**
         * 고객-구좌 정보를 검색한다.
         *
         * @param pmParam 검색 조건
         * @return 고객-구좌 정보 목록
         * @throws Exception
         */
        public List<Map<String, Object>> getDlwCustAccntList(Map<String, ?> pmParam) throws Exception {
            return ReqCustDAO.getDlwCustAccntList(pmParam);
        }

        /** ================================================================
         * 날짜 : 20181127
         * 이름 : 정승철
         * 추가내용 : 회원의 환불회차정보를 검색
         * 대상 테이블 : TB_MEMBER_REQ_REFUND
         * ================================================================
         * */
        public List<Map<String, Object>> getRefundReqNoOfAccnt(Map<String, ?> pmParam) throws Exception {
            return ReqCustDAO.getRefundReqNoOfAccnt(pmParam);
        }

        /**
         *  가수 삭제
         *
         * @param pmParam 검색 조건
         * @return
         * @throws Exception
         */
        public int deleteMemberRefundMng(Map<String, ?> pmParam) throws Exception {
            return ReqCustDAO.deleteMemberRefundMng(pmParam);
        }

        /**
         *  가수 환불 상세 내역 수정
         *
         * @param pmParam 검색 조건
         * @return
         * @throws Exception
         */
        public int updateMemberRefundDtl(Map<String, ?> pmParam) throws Exception {
            return ReqCustDAO.updateMemberRefundDtl(pmParam);
        }

        /**
         *  가수 환불 상세 내역 입금
         *
         * @param pmParam 검색 조건
         * @return
         * @throws Exception
         */
        public int insertMemberRefundDtl(Map<String, ?> pmParam) throws Exception {
            return ReqCustDAO.insertMemberRefundDtl(pmParam);
        }

        /** ================================================================
         * 날짜 : 팝업
         * 이름 : /dlw/cms/gasu-dtl/delete
         * 추가내용 :
         * 대상 테이블 :
         * ================================================================
         * */

        /**
         *  가수금 환불 내역조회
         *
         * @param pmParam 검색 조건
         * @return
         * @throws Exception
         */
        public int deleteMemberRefundDtl(Map<String, ?> pmParam) throws Exception {
            return ReqCustDAO.deleteMemberRefundDtl(pmParam);
        }

        /** ================================================================
         * 날짜 : 20181127
         * 이름 : 정승철
         * 추가내용 : (CMS) 환불반영
         * 대상 테이블 : TB_MEMBER_WDRW_REQ
         *               PAY_MNG
         *               PAY_MNG_DTL
         *               PAY_MNG_DTL1
         * ================================================================
         * */
        public Map<String, Object> updateByCmsRefundProcess(Map<String, Object> pmParam) throws Exception {
            System.out.println("CMS 입금(취소)반영 pmParam : " + pmParam);

            // 산출청구관리 UPDATE 및 입금데이터 삭제처리
            ReqCustDAO.updateCmsPayCancel(pmParam);


            // 1. 산출관리 UPDATE
            //DlwPayDAO.updateWdrwReqByPayCancel(pmParam);

            // 2. 입금데이터 삭제처리
            //DlwPayDAO.updateCmsPayCancel(pmParam);

            return pmParam;
        }

        /**
         * 고객정보 카운트조회
         * 정승철
         * 20181113
         */
        public int getCntDlwAccntBaseInfo(Map<String, ?> pmParam) throws Exception {
            return ReqCustDAO.getCntDlwAccntBaseInfo(pmParam);
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
            return ReqCustDAO.getDlwAccntBaseInfo(pmParam);
        }

        /**
         * 고객정보 조회(팝업용)
         * 정승철
         * 20181113
         */
        public List<Map<String, Object>> getDlwAccntBaseInfo_popup(Map<String, ?> pmParam) throws Exception {
            return ReqCustDAO.getDlwAccntBaseInfo_popup(pmParam);
        }

        /* 산출 회원정보 조회 */
        public List<Map<String, Object>> getDlwWdrwAcntInfo(Map<String, ?> pmParam) throws Exception {
            return ReqCustDAO.getDlwWdrwAcntInfo(pmParam);
        }

        /**
         * 휴일여부 체크
         * 정승철
         * 20181012
        */
        public String getHolidayChk(Map<String, ?> pmParam) throws Exception {
            return ReqCustDAO.getHolidayChk(pmParam);
        }

        /* 임의등록 */
        public void addWdrwTemp(Map<String, Object> pmParam) throws Exception {
            ReqCustDAO.addWdrwTemp(pmParam);

            //공통정보
            //if(!"Card".equals(pmParam.get("wdrw_gubun"))) {

//	              int inv_no = DlwWdrwDAO.getnew_inv_no(pmParam);
//	              pmParam.put("inv_no", inv_no);
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

        /* 자유결제용 임의등록 */
        public void addWdrwFreeTemp(Map<String, Object> pmParam) throws Exception {
            ReqCustDAO.addWdrwFreeTemp(pmParam);
        }

        /* 자유결제용 임의등록 삭제 */
        public void delWdrwFreeTemp(Map<String, Object> pmParam) throws Exception {
            ReqCustDAO.delWdrwFreeTemp(pmParam);
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
            return ReqCustDAO.getVirtualExtList(pmParam);
        }

        /**
         * 가상계좌 산출 INSERT
         * 임동진
         * 20181030
        */
        public int insertVirtualMemData(Map<String, ?> pmParam) throws Exception {
            return ReqCustDAO.insertVirtualMemData(pmParam);
        }

        /**
         * 가상계좌 산출데이터 청구 등록
         * 임동진ㄴ
         * 20181030
        */
        public int insertVirtualReq(Map<String, ?> pmParam) throws Exception {
            return ReqCustDAO.insertVirtualReq(pmParam);
        }

        /* 임의삭제 */
        public void delWdrwTemp(Map<String, Object> pmParam) throws Exception {

            ReqCustDAO.delWdrwTemp(pmParam);
        }

        /**
         * 실시간 결제 대상 회원 정보
         * 임동진
         * 20181012
        */
        public List<Map<String, Object>> getRealTimeReqList(Map<String, ?> pmParam) throws Exception {
            return ReqCustDAO.getRealTimeReqList(pmParam);
        }

        /**
         * 실시간 결제 대상 회원 정보 (자유결제 다수 회차)
         * 임동진
         * 20200924
        */
        public List<Map<String, Object>> getRealTimeReqFreeList(Map<String, ?> pmParam) throws Exception {
            return ReqCustDAO.getRealTimeReqFreeList(pmParam);
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
         * 이니시스 실시간 결제 실행 SERVICE
         * 임동진
         * 20200504
        */
        public Map<String, Object> RealTimeInicisReqProc(Map<String, Object> pmParam) throws Exception {
            String result_cd = "";
            String result_msg = "";
            String tid = "";
            String auth_dt = "";
            String auth_cd = "";
            String uip = "";
            String card_cd = "";
            String card_Quota = "";

            card_Quota = "00" + String.valueOf(pmParam.get("card_quota"));
            card_Quota =  card_Quota.substring(card_Quota.length()-2, card_Quota.length());

            try
            {
                INICISPay inicisPay = new INICISPay();
                 inicisPay.setInicisKey("hmPLjIdyekyylSx9");
                 inicisPay.setInicisiv("fS7oGOerwBsEcQ==");
                 //inicisPay.setData("mid", "daemyungT1");
                inicisPay.setData("clientIp", "127.0.0.1");
                inicisPay.setData("moid",  (String)pmParam.get("accnt_no"));
                inicisPay.setData("goodName", (String)pmParam.get("prod_nm"));
                inicisPay.setData("buyerName", (String)pmParam.get("mem_nm"));
                inicisPay.setData("buyerEmail", "admin@daemyung.com");
                inicisPay.setData("buyerTel", (String)pmParam.get("cell"));

                inicisPay.setData("price", (String)pmParam.get("pay_amt"));
                //inicisPay.setData("price", "1000");
                inicisPay.setData("cardQuota",card_Quota);
                inicisPay.setData("billKey", (String)pmParam.get("bid"));

                //장기할부로 인한 실시간 결제 수정
                inicisPay.setData("mid", (String)pmParam.get("bill_key"));
                inicisPay.setData("partnerDiscount", (String)pmParam.get("billGubun"));

                inicisPay.authBilling();

                result_cd = inicisPay.getData("resultCode");
                result_msg = inicisPay.getData("resultMsg");
                tid = inicisPay.getData("tid");
                auth_dt = inicisPay.getData("payDate");
                auth_cd = inicisPay.getData("payAuthCode");
                card_cd = inicisPay.getData("cardCode");

                //pmParam.put("uip", uip);
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


        /** ================================================================
         * 날짜 : 20181031
         * 이름 : 정승철
         * 추가내용 : Card 빌링결제 (자유금액)
         * ================================================================
         * */
        public Map<String, Object> billPayFreeProc(Map<String, Object> pmParam) throws Exception {

            //빌키생성정보 조회
            List<Map<String, Object>> tmpList = ReqCustDAO.getBillKeyCrtnInfo(pmParam);

            if(tmpList != null && tmpList.size() > 0) {
                Map<String, Object> tmpMap = (Map<String, Object>)tmpList.get(0);
                pmParam.put("email", String.valueOf(tmpMap.get("email")));
                pmParam.put("cell", String.valueOf(tmpMap.get("cell")));
                pmParam.put("prod_nm", String.valueOf(tmpMap.get("prod_nm")));
                pmParam.put("prod_cd", String.valueOf(tmpMap.get("prod_cd")));
                pmParam.put("mem_nm", String.valueOf(tmpMap.get("mem_nm")));

                System.out.println("############ 1. billPayFreeProc pmParam : " + pmParam);

                try {
                    String accnt_no = (String)pmParam.get("accnt_no");
                    String mem_nm = (String)pmParam.get("mem_nm");
                    String email = (String)pmParam.get("email");
                    String cell = (String)pmParam.get("cell");
                    String prod_nm = (String)pmParam.get("prod_nm");
                    String card_no = (String)pmParam.get("card_no");
                    String exp_year = (String)pmParam.get("exp_year");
                    String exp_mon = (String)pmParam.get("exp_mon");
                    String card_idn_no = (String)pmParam.get("card_idn_no");

                    NicePayProcess nicepay = new NicePayProcess();

                    ParamUtils.addCenterParam(pmParam);
                    String sPayFilePath = basVlService.getBasVlAsString("pay_file_path", (String) pmParam.get("cntr_cd"));

                    //nicepay.setNicePayHome(sPayFilePath+"/web_site/nicelog");
                    nicepay.setNicePayHome("c:/web_site/nicelog");
                    nicepay.setGoodsName(prod_nm);
                    nicepay.setMoid(accnt_no);
                    nicepay.setBuyerName(mem_nm);
                    nicepay.setBuyerTel(cell);
                    nicepay.setBuyerEmail(email);
                    nicepay.setCardNumber(card_no);
                    nicepay.setExpYear(exp_year);
                    nicepay.setExpMonth(exp_mon);
                    nicepay.setIdno(card_idn_no);

                    //임동진 수정
                    nicepay.setMallreserved("00");

                    NicePayWebConnector connector = nicepay.requestCardMemAuth();

                    System.out.println("@@@ : " + connector.getResultData("ResultCode"));

                    if(!"0000".equals(connector.getResultData("ResultCode"))) {
                        pmParam.put("result_cd", connector.getResultData("ResultCode"));
                        pmParam.put("result_msg", connector.getResultData("ResultMsg"));
                        //pmParam.put("result_cd", "01");
                        //pmParam.put("result_msg", "빌키생성 실패");
                    } else {

                        String billKeyArr[] = new String[5];
                        billKeyArr = billKeyCreate(pmParam);

                        System.out.println("XXXXXXXXXXXXXXX> : " + billKeyArr[0]);
                        System.out.println("XXXXXXXXXXXXXXX> : " + billKeyArr[2]);

                        if(billKeyArr[0].equals("F100")) {
                            pmParam.put("bid", billKeyArr[1]);
                            Map<String, Object> rsltParam = billPay(pmParam);

                            System.out.println("XXXXXXXXXXXXXXX> : " + String.valueOf(rsltParam.get("result_msg")));

                            pmParam.put("uip", String.valueOf(rsltParam.get("uip")));
                            pmParam.put("result_cd", String.valueOf(rsltParam.get("result_cd")));
                            pmParam.put("result_msg", String.valueOf(rsltParam.get("result_msg")));
                            pmParam.put("tid", String.valueOf(rsltParam.get("tid")));
                            pmParam.put("auth_dt", String.valueOf(rsltParam.get("auth_dt")));
                            pmParam.put("auth_cd", String.valueOf(rsltParam.get("auth_cd")));
                            pmParam.put("card_cd", String.valueOf(rsltParam.get("card_cd")));
                        } else {
                            pmParam.put("result_cd", "01");
                            pmParam.put("result_msg", "빌키생성 실패");
                        }

                    }
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
            return pmParam;
        }

        /** ================================================================
         * 날짜 : 20200507
         * 이름 : 임동진
         * 추가내용 : Card 이니시스 자유결제 프로세스
         * ================================================================
         * */
        public Map<String, Object> billPayInicisFreeProc(Map<String, Object> pmParam) throws Exception {

            /***********************************************************************************************************
             * 카드 신청 등록 시 본인 확인 부분 ( 생년월일 + 카드번호등으로 본인 확인 )
             /***********************************************************************************************************/

            String result_cd = "";
            String result_msg = "";
            String auth_dt = "";
            String auth_cd = "";
            String uip = "";
            String card_cd = "";
            String sCardQuota = "";

            try
            {
                 INICISPay inicisPay = new INICISPay();
                 inicisPay.setInicisKey("hmPLjIdyekyylSx9");
                 inicisPay.setInicisiv("fS7oGOerwBsEcQ==");
                 inicisPay.setData("mid", "daemyungT1");
                 inicisPay.setData("clientIp", "127.0.0.1");
                 inicisPay.setData("moid", (String)pmParam.get("accnt_no"));
                 inicisPay.setData("goodName", "대명스테이션상품");
                 inicisPay.setData("buyerName", (String)pmParam.get("mem_nm"));
                 //inicisPay.setData("buyerEmail", (String)pmParam.get("email"));
                 inicisPay.setData("buyerEmail", "lifeway@daemyung.com");
                 inicisPay.setData("buyerTel", (String)pmParam.get("cell"));
                 inicisPay.setData("cardNumber", (String)pmParam.get("card_no"));
                 inicisPay.setData("cardExpire", (String)pmParam.get("exp_year")+(String)pmParam.get("exp_mon") );
                 inicisPay.setData("regNo", (String)pmParam.get("card_idn_no"));


                 //inicisPay.setData("cardPw", "00");

                 inicisPay.authBillkey();

                 String resultCode = inicisPay.getData("resultCode");
                 String resultMsg = inicisPay.getData("resultMsg");
                 String tid = inicisPay.getData("tid");
                 String billKey = inicisPay.getData("billKey");

                 // 정상여부
                 if(!"00".equals(resultCode)) {
                     //정상결과 아닐경우 결과코드 출력
                     pmParam.put("result_cd", resultCode);
                     pmParam.put("result_msg", resultMsg);
                     return pmParam;
                 }

                 pmParam.put("ini_bid", billKey);

                 inicisPay.setData("cardQuota", (String)pmParam.get("card_quota"));
                 inicisPay.setData("moid",  (String)pmParam.get("accnt_no"));
                 inicisPay.setData("buyerEmail", "lifeway@daemyung.com");

                 inicisPay.setData("price", (String)pmParam.get("pay_amt"));
                 inicisPay.setData("billKey", billKey);

                 inicisPay.authBilling();

                 resultCode = inicisPay.getData("resultCode");
                 resultMsg = inicisPay.getData("resultMsg");
                 tid = inicisPay.getData("tid");
                 auth_dt = inicisPay.getData("payDate");
                 auth_cd = inicisPay.getData("payAuthCode");
                 card_cd = inicisPay.getData("cardCode");
                 sCardQuota = inicisPay.getData("cardQuota");

                 String sPayKind = (String)pmParam.get("pay_kind");
                 int iCardQuota = Integer.parseInt(sCardQuota);

                 if(sPayKind != null && sPayKind.equals("05"))
                 {
                     if(iCardQuota >= 2)
                     {
                         sCardQuota = "할부개월수 : " + sCardQuota + "개월 결제 (전환결제)";
                     }
                     else
                     {
                         sCardQuota = "일시불결제 (전환결제)";
                     }
                 }
                 else
                 {
                     if(iCardQuota >= 2)
                     {
                         sCardQuota = "할부개월수 : " + sCardQuota + "개월 결제 (자유결제)";
                     }
                     else
                     {
                         sCardQuota = "일시불결제 (자유결제)";
                     }
                 }

                 pmParam.put("result_cd", resultCode);
                 pmParam.put("result_msg", resultMsg);
                 pmParam.put("result_key", tid);
                 pmParam.put("auth_dt", auth_dt);
                 pmParam.put("auth_cd", auth_cd);
                 pmParam.put("card_cd", card_cd);
                 pmParam.put("etc", sCardQuota);

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

            return pmParam;
        }

        /** ================================================================
         * 날짜 : 20210824
         * 이름 : 임동진
         * 추가내용 : Card 이니시스 자유결제 프로세스 중 장기할부 결제
         * ================================================================
         * */
        public Map<String, Object> billPayInicisLongTermProc(Map<String, Object> pmParam) throws Exception {

            /***********************************************************************************************************
             * 카드 신청 등록 시 본인 확인 부분 ( 생년월일 + 카드번호등으로 본인 확인 )
             /***********************************************************************************************************/

            String resultCode = "";
            String resultMsg = "";
            String auth_dt = "";
            String auth_cd = "";
            String uip = "";
            String card_cd = "";
            String tid = "";

            try
            {
                 INICISPay inicisPay = new INICISPay();
                 inicisPay.setInicisKey("hmPLjIdyekyylSx9");
                 inicisPay.setInicisiv("fS7oGOerwBsEcQ==");
                 inicisPay.setData("mid", (String)pmParam.get("mid_info_1"));
                 inicisPay.setData("clientIp", "127.0.0.1");
                 inicisPay.setData("moid", (String)pmParam.get("accnt_no"));
                 inicisPay.setData("goodName", "대명스테이션상품");
                 inicisPay.setData("buyerName", (String)pmParam.get("mem_nm"));
                 inicisPay.setData("buyerEmail", "lifeway@daemyung.com");
                 inicisPay.setData("buyerTel", (String)pmParam.get("cell"));
                inicisPay.setData("cardQuota", (String)pmParam.get("card_quota"));
                inicisPay.setData("price", (String)pmParam.get("pay_amt"));
                inicisPay.setData("billKey", (String)pmParam.get("ini_bid"));

                 inicisPay.authBilling();

                 resultCode = inicisPay.getData("resultCode");
                 resultMsg = inicisPay.getData("resultMsg");
                 tid = inicisPay.getData("tid");
                 auth_dt = inicisPay.getData("payDate");
                 auth_cd = inicisPay.getData("payAuthCode");
                 card_cd = inicisPay.getData("cardCode");

                 pmParam.put("result_cd", resultCode);
                 //pmParam.put("result_cd", "00");
                 pmParam.put("result_msg", resultMsg);
                 pmParam.put("result_key", tid);
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

        /** ================================================================
         * 날짜 : 20181031
         * 이름 : 정승철
         * 추가내용 : 빌키 생성
         * ================================================================
         * */
        public String[] billKeyCreate(Map<String, Object> pmParam) throws Exception {
            String rtVal[] = new String[5];
            NicePayProcess nicepay = new NicePayProcess();
            String prod_cd = (String)pmParam.get("prod_cd");
            String menu_gbn = (String)pmParam.get("menu_gbn");
            String email = "lifeway@daemyung.com";
            String tmpEmail = CommonUtils.checkNull((String)pmParam.get("email"));

            /*20180723 37회 SDP카드변경 이슈로 인한 추가*/
            String pay_No = (String)pmParam.get("pay_no");
            String pay_kind = (String)pmParam.get("pay_kind");

            if("".equals(tmpEmail)) {
                email = tmpEmail;
            }

            Map<String, Object> hm = new HashMap<String, Object>();
            hm.put("prod_cd", prod_cd);

            /*20180723 37회 SDP카드변경 이슈로 인한 추가*/
            hm.put("pay_no", pay_No);
            hm.put("pay_kind", pay_kind);

            if(menu_gbn != null) {
                hm.put("menu_gbn", menu_gbn);
            } else {
                hm.put("menu_gbn", "");
            }

            //nicepay = niceBillSetting(hm);

            nicepay = niceBillSetting(hm,"billKeyCreate");
            nicepay.setMoid((String)pmParam.get("accnt_no"));
            nicepay.setBuyerName((String)pmParam.get("mem_nm"));
            nicepay.setBuyerEmail(email);
            nicepay.setBuyerTel((String)pmParam.get("cell"));
            nicepay.setGoodsName((String)pmParam.get("prod_nm"));
            nicepay.setCardNumber((String)pmParam.get("card_no"));
            nicepay.setExpYear((String)pmParam.get("exp_year"));
            nicepay.setExpMonth((String)pmParam.get("exp_mon"));
            nicepay.setCardIdNo("");
            nicepay.setCardPw("");
            nicepay.setIdno((String)pmParam.get("card_idn_no"));

            NicePayWebConnector result = nicepay.requestBillKey();
            for(int i = 0; i < 5; i++) {
                rtVal[i] = "";
            }
            rtVal[0] = result.getResultData("ResultCode");
            rtVal[1] = result.getResultData("BID");
            rtVal[2] = result.getResultData("ResultMsg");
            rtVal[3] = result.getResultData("CardCode");
            rtVal[4] = result.getResultData("CardName");
            return rtVal;
        }


        /** ================================================================
         * 날짜 : 20181031
         * 이름 : 정승철
         * 추가내용 : Card 빌링결제
         * ================================================================
         * */
        public Map<String, Object> billPay(Map<String, Object> pmParam) throws Exception {
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

            NicePayProcess nicepay = new NicePayProcess();

            try {
                nicepay = niceBillSetting(pmParam, "Real_Key");
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

        /** ================================================================
         * 날짜 : 20181031
         * 이름 : 정승철
         * 추가내용 : 자유결제 카드 결과데이터 INSERT
         * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_RSLT
         * ================================================================
         * */
        public int insertFreeCardResult(Map<String, ?> pmParam) throws Exception {
            return ReqCustDAO.insertFreeCardResult(pmParam);
        }

        /** ================================================================
         * 날짜 : 20210825
         * 이름 : 임동진
         * 추가내용 : 장기할부 정보 등록
         * 대상 테이블 : LF_DMUSER.TB_LTINSTALL_MNG
         * ================================================================
         * */
        public int insertLongTermData(Map<String, ?> pmParam) throws Exception {
            return ReqCustDAO.insertLongTermData(pmParam);
        }

        /** ================================================================
         * 날짜 : 20200511
         * 이름 : 임동진
         * 추가내용 : 이니시스 일자별 결과 받은 데이터건수 조회
         *
         * ================================================================
         * */
        public int getInicisResultCount(Map<String, ?> pmParam) throws Exception {
            return ReqCustDAO.getInicisResultCount(pmParam);
        }

        /**
         * 대명라이프웨이 가상계좌 정보 산출이력 건수 조회
         *
         * @param pmParam 검색 조건
         * @return 가상계좌 정보 산출 건수
         * @throws Exception
         */
        public int getDlwVrtlAcntClctCount(Map<String, ?> pmParam) throws Exception {
            return ReqCustDAO.getDlwVrtlAcntClctCount(pmParam);
        }

        /**
         * 대명라이프웨이 가상계좌 정보 산출이력 조회
         *
         * @param pmParam 검색 조건
         * @return 가상계좌 정보 산출정보
         * @throws Exception
         */
        public List<Map<String, Object>> getDlwVrtlAcntCsmm(Map<String, ?> pmParam) throws Exception {
            return ReqCustDAO.getDlwVrtlAcntClctList(pmParam);
        }

        /**
         * 대명라이프웨이 가상계좌 NICE전송 결과 반영
         *
         * @param pmParam 검색 조건
         * @return
         * @throws Exception
         */
        public int updateNiceVrtlAccntWdrwReq(Map<String, ?> pmParam) throws Exception {
            return ReqCustDAO.updateNiceVrtlAccntWdrwReq(pmParam);
        }

        /**
         * 대명라이프웨이 가상계좌 전송 데이터 삭제
         *
         * @param pmParam 검색 조건
         * @return 가상계좌 정보 산출정보
         * @throws Exception
         */
        public int deleteDlwVrtlAcntClct(Map<String, Object> pmParam) throws Exception {

            Map<String, Object> hmTime = ReqCustDAO.getCurrDthms(pmParam);
            pmParam.put("yyyymmdd", (String)hmTime.get("yyyymmdd"));
            pmParam.put("hhmmss", (String)hmTime.get("hhmmss"));

            return ReqCustDAO.deleteDlwVrtlAcntClct(pmParam);
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
            return ReqCustDAO.getVirtualReqList(pmParam);
        }

        /**
         * 가상계좌 NICE 전송 후 TID업데이트 혹은 실패 처리
         * 임동진
         * 20181101
         */
         public int updateVirtualReqSendtoNice(Map<String, ?> pmParam) throws Exception {
             return ReqCustDAO.updateVirtualReqSendtoNice(pmParam);
         }

         /**
          * 가상계좌 산출 대상자 삭제
          * 임동진
          * 20181105
          */
         public int updateVirtualExtDelete(Map<String, ?> pmParam) throws Exception {
             return ReqCustDAO.updateVirtualExtDelete(pmParam);
         }

         /** ================================================================
          * 날짜 : 20181211
          * 이름 : 송준빈
          * 추가내용 : 청구강제마감 업데이트
          * 대상 테이블 : TB_MEMBER_WDRW_REQ
          * ================================================================
          * */
         public int updateCompulsionWdrdList(Map<String, Object> pmParam) {
             return ReqCustDAO.updateCompulsionWdrdList(pmParam);
         }

         /** ================================================================
          * 날짜 : 20200518
          * 이름 : 임동진
          * 추가내용 : 가상계좌 메인정보 INSERT
          * ================================================================
          * */
            public int insertVirtualMain(Map<String, ?> pmParam) throws Exception {
                return ReqCustDAO.insertVirtualMain(pmParam);
            }

         /** ================================================================
          * 날짜 : 20200518
          * 이름 : 임동진
          * 추가내용 : 가상계좌 메인정보 삭제 및 청구 업데이트
          * ================================================================
          * */
            public int updateVirtualMain(Map<String, ?> pmParam) throws Exception {
                return ReqCustDAO.updateVirtualMain(pmParam);
            }

            /** ================================================================
             * 날짜 : 20200507
             * 이름 : 임동진
             * 추가내용 : Card 이니시스 자유결제 프로세스
             * ================================================================
             * */
            public Map<String, Object> InicisVirtualProc(Map<String, Object> pmParam) throws Exception {

                /***********************************************************************************************************
                 * 카드 신청 등록 시 본인 확인 부분 ( 생년월일 + 카드번호등으로 본인 확인 )
                 /***********************************************************************************************************/

                String result_cd = "";
                String result_msg = "";
                String auth_dt = "";
                String auth_cd = "";
                String uip = "";
                String card_cd = "";

                try
                {
                     INICISPay inicisPay = new INICISPay();
                     inicisPay.setInicisKey("hmPLjIdyekyylSx9");
                     inicisPay.setInicisiv("fS7oGOerwBsEcQ==");
                     inicisPay.setData("mid", "daemyungT1");
                     inicisPay.setData("clientIp", "127.0.0.1");
                     inicisPay.setData("moid", (String)pmParam.get("accnt_no"));
                     inicisPay.setData("goodName", "대명스테이션상품");
                     inicisPay.setData("buyerName", (String)pmParam.get("mem_nm"));
                     //inicisPay.setData("buyerEmail", (String)pmParam.get("email"));
                     inicisPay.setData("buyerEmail", "lifeway@daemyung.com");
                     inicisPay.setData("buyerTel", (String)pmParam.get("cell"));
                     inicisPay.setData("cardNumber", (String)pmParam.get("card_no"));
                     inicisPay.setData("cardExpire", (String)pmParam.get("exp_year")+(String)pmParam.get("exp_mon") );
                     inicisPay.setData("regNo", (String)pmParam.get("card_idn_no"));
                     //inicisPay.setData("cardPw", "00");

                     inicisPay.authBillkey();

                     String resultCode = inicisPay.getData("resultCode");
                     String resultMsg = inicisPay.getData("resultMsg");
                     String tid = inicisPay.getData("tid");
                     String billKey = inicisPay.getData("billKey");

                     // 정상여부
                     if(!"00".equals(resultCode)) {
                         //정상결과 아닐경우 결과코드 출력
                         pmParam.put("result_cd", resultCode);
                         pmParam.put("result_msg", resultMsg);
                         return pmParam;
                     }

                     pmParam.put("ini_bid", billKey);

                     inicisPay.setData("moid",  (String)pmParam.get("accnt_no"));
                     inicisPay.setData("buyerEmail", "lifeway@daemyung.com");

                     inicisPay.setData("price", (String)pmParam.get("pay_amt"));
                     inicisPay.setData("billKey", billKey);

                     inicisPay.authBilling();

                     resultCode = inicisPay.getData("resultCode");
                     resultMsg = inicisPay.getData("resultMsg");
                     tid = inicisPay.getData("tid");
                     auth_dt = inicisPay.getData("payDate");
                     auth_cd = inicisPay.getData("payAuthCode");
                     card_cd = inicisPay.getData("cardCode");

                     pmParam.put("result_cd", resultCode);
                     pmParam.put("result_msg", resultMsg);
                     pmParam.put("result_key", tid);
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

            public void uploadEctChk(HttpServletRequest request, HttpServletResponse response, Map<String, Object> mResuslt) throws Exception {

                String tempDir = System.getProperty("java.io.tmpdir");
                //LOGGER.info("---uploadToSms 서비스 - 1");

                MultipartRequest multipartRequest = new MultipartRequest(request, tempDir, 1024*1024*20);
                Enumeration enm = multipartRequest.getFileNames();
                String sKey = "";

                int fileCnt	= 0;

                Map<String, Object> mParam = null;

                // 엑셀파일 워크북 객체 생성
                XSSFWorkbook workbook = null;

                // 시트 지정
                XSSFSheet sheet = null;

                // 로우
                XSSFRow xrow = null;

                // cell
                XSSFCell xcell = null;

                List<Map<String,Object>> lst = new ArrayList<>();
                Map<String,Object> mRow = new HashMap<>();

                DataSetMap listMap = new DataSetMap();
                Map<String, Object> hmParam = new HashMap<String, Object>();

                try {

                        mParam = new HashMap<>();

                    // 실제로는 단건만 처리함
                    while (enm.hasMoreElements())
                    {
                        fileCnt++;
                        sKey = (String)enm.nextElement();

                        log.debug("sKey : " + sKey);

                        File upfile = multipartRequest.getFile(sKey);
                        String sUpFileName = upfile.getName();

                        log.debug("upfile.length() : " +upfile.length());

                        if (upfile.exists()) {
                            log.debug("file exists");
                        } else {
                            log.debug("file does not exists");
                        }

                        // 엑셀파일 워크북 객체 생성
                        workbook = new XSSFWorkbook(new FileInputStream(upfile));

                        sheet = workbook.getSheetAt(0);

                        String value;
                        int i=0, j;
                        String strRegman = "";

                        int rows = sheet.getPhysicalNumberOfRows();

                        if (rows > 10005)
                        {
                            throw new EgovBizException("업로드정보가 10000건이 넘습니다. 전산팀에 업로드 요청 부탁드립니다.!!");
                        }

                        ParamUtils.addSaveParam(hmParam);

                        strRegman = hmParam.get("rgsr_id").toString();

                        log.debug("Excel Rows : " + rows);

                        for (i=0; i < rows; i++)
                        {
                            xrow = sheet.getRow(i);
                            int cells = xrow.getPhysicalNumberOfCells();

                            if (i < 1)
                            {
                                continue;
                            }

                            mRow.put("ext_dt", "");
                            mRow.put("ext_req_dt", "");
                            mRow.put("ext_chk", "");
                            mRow.put("ext_req_bit", "");
                            mRow.put("ichae_dt", "");

                             for(j = 0; j < cells; j++)
                             {
                                  xcell = xrow.getCell(j);

                                  value = "";

                                switch (xcell.getCellType())
                                {
                                    case Cell.CELL_TYPE_BOOLEAN:
                                        value = "" + xcell.getBooleanCellValue();
                                    break;

                                    case Cell.CELL_TYPE_NUMERIC:
                                        value = "" + xcell.getNumericCellValue();
                                    break;

                                    case Cell.CELL_TYPE_STRING:
                                        value = "" + xcell.getStringCellValue();
                                    break;

                                    case Cell.CELL_TYPE_BLANK:
                                        value = " ";
                                    break;

                                    default:
                                        value = "" + xcell.getStringCellValue();
                                    break;
                                }

                                if (null != value)
                                {
                                    value = value.trim();
                                }

                                //mRow.put("rgsr_id", strRegman);
                                //LOGGER.debug("Excel B : " + value);
                                if(sUpFileName.equals("ectChkList.xlsx"))
                                {


                                    switch (j)
                                    {
                                        case 0:
                                            mRow.put("ext_dt", value);
                                        break;

                                        case 1:
                                            mRow.put("ext_req_dt", value);
                                        break;

                                        case 2:
                                            mRow.put("ext_chk", value);
                                        break;

                                        case 3:
                                            mRow.put("ext_req_bit", value);
                                        break;

                                        case 4:
                                            mRow.put("ichae_dt", value);
                                        break;

                                        default:

                                        break;
                                    }
                                }

                             }

                            lst.add(mRow);
                            listMap.setRowMaps(lst);
                            hmParam = listMap.get(0);

                            ReqCustDAO.uploadEctChk(hmParam);
                        }

                        upfile.delete();
                    }

                    if (fileCnt < 1)
                    {
                        throw new EgovBizException("업로드된 파일이 없습니다.");
                    }

                }
                catch (FileNotFoundException ex)
                {
                    ex.printStackTrace();
                    throw ex;
                }
                catch (IOException ex)
                {
                    ex.printStackTrace();
                    throw ex;
                }
                finally
                {

                }

            }

            /** ================================================================
             * 날짜 : 20191209
             * 이름 : 송준빈
             * 추가내용 : 카드 무승인 결제 취소 처리
             * 대상 테이블 : LF_DMUSER.TB_MEMBER_REQ_NAUTH_CNCL
             * ================================================================
             * */
            public int insertNauthPayCancel(Map<String, ?> pmParam) throws Exception {
                return ReqCustDAO.insertNauthPayCancel(pmParam);
            }


            /** ================================================================
             * 날짜 : 20201014
             * 이름 : 송준빈
             * 추가내용 : BillKey 자동생성 리스트 조회수
             * 대상 테이블 : LF_DMUSER.MEM_PROD_ACCNT, LF_DMUSER.CARD_MEM
             * ================================================================
             * */
            public int getCreateBillKeyCount(Map<String, ?> pmParam) throws Exception {
                return ReqCustDAO.getCreateBillKeyCount(pmParam);
            }

            /** ================================================================
             * 날짜 : 20201014
             * 이름 : 송준빈
             * 추가내용 : BillKey 자동생성 리스트
             * 대상 테이블 : LF_DMUSER.MEM_PROD_ACCNT, LF_DMUSER.CARD_MEM
             * ================================================================
             * */
            public List<Map<String, Object>> getCreateBillKeyList(Map<String, ?> pmParam) throws Exception {
                return ReqCustDAO.getCreateBillKeyList(pmParam);
            }

            /** ================================================================
             * 빌키 자동 생성 (대량건)
             * 임동진
             * 20200923
             * ================================================================
             * */
            public int insertBillkeyMakeData(Map<String, ?> pmParam) throws Exception {
                return ReqCustDAO.insertBillkeyMakeData(pmParam);
            }

            /** ================================================================
             * 날짜 : 20210909
             * 이름 : 김주용
             * 추가내용 : 환불관리팝업조회
             * 대상 테이블 : TB_MEMBER_REQ_REFUND
             * ================================================================
             * */
            public List<Map<String, Object>> getRefundReqPopList(Map<String, ?> pmParam) throws Exception {
                return ReqCustDAO.getRefundReqPopList(pmParam);
            }

            /** ================================================================
              * 날짜 : 20220217
              * 이름 : 김주용
              * 추가내용 : 2021년이전 결과관리 조회
              * ================================================================
              * */
            public int getReqRltmCardLogOldCount(Map<String, Object> pmParam) {
                return ReqCustDAO.getReqRltmCardLogOldCount(pmParam);
            }

            public List<Map<String, Object>> getReqRltmCardLogOldList(Map<String, ?> pmParam) throws Exception {
                return ReqCustDAO.getReqRltmCardLogOldList(pmParam);
            }

            /** ================================================================
              * 날짜 : 20220217
              * 이름 : 김주용
              * 추가내용 : 회원번호조회조건 결과관리 조회
              * ================================================================
              * */
            public int getReqRltmCardLogAccntNoCount(Map<String, Object> pmParam) {
                return ReqCustDAO.getReqRltmCardLogAccntNoCount(pmParam);
            }

            public List<Map<String, Object>> getReqRltmCardLogAccntNoList(Map<String, ?> pmParam) throws Exception {
                return ReqCustDAO.getReqRltmCardLogAccntNoList(pmParam);
            }

            /** ================================================================
              * 날짜 : 20220217
              * 이름 : 김주용
              * 추가내용 : 2021년이전 환불관리 조회
              * 대상 테이블 : TB_MEMBER_REQ_REFUND
              * ================================================================
              * */
            public int getMemberRefundListOldCount(Map<String, ?> pmParam) throws Exception {
                return ReqCustDAO.getMemberRefundListOldCount(pmParam);
            }

            public List<Map<String, Object>> getMemberRefundOldList(Map<String, ?> pmParam) throws Exception {
                return ReqCustDAO.getMemberRefundOldList(pmParam);
            }

            /** ================================================================
              * 날짜 : 20220217
              * 이름 : 김주용
              * 추가내용 : 회원번호조회조건 환불관리 조회
              * 대상 테이블 : TB_MEMBER_REQ_REFUND
              * ================================================================
              * */
            public int getMemberRefundListAccntNoCount(Map<String, ?> pmParam) throws Exception {
                return ReqCustDAO.getMemberRefundListAccntNoCount(pmParam);
            }

            public List<Map<String, Object>> getMemberRefundAccntNoList(Map<String, ?> pmParam) throws Exception {
                return ReqCustDAO.getMemberRefundAccntNoList(pmParam);
            }

            /**
             * 특수회원 카운트조회
             * 김주용
             * 20220516
             * @param pmParam 검색 조건
             * @return
             * @throws Exception
             */
            public int getCustExceptionCount(Map<String, ?> pmParam) throws Exception {
                return ReqCustDAO.getCustExceptionCount(pmParam);
            }

            /**
             * 특수회원 조회
             * 김주용
             * 20220516
             * @param pmParam 검색 조건
             * @return
             * @throws Exception
             */
            public List<Map<String, Object>> getCustExceptionList(Map<String, ?> pmParam) throws Exception {
                return ReqCustDAO.getCustExceptionList(pmParam);
            }

            /**
             * 특수회원 입력체크
             * 김주용
             * 20220516
             * @param pmParam 검색 조건
             * @return
             * @throws Exception
             */
            public int getChkCustExceptionList(Map<String, ?> pmParam) throws Exception {
                return ReqCustDAO.getChkCustExceptionList(pmParam);
            }

            /**
             * 특수회원 입력
             * 김주용
             * 20220516
             * @param pmParam 검색 조건
             * @return
             * @throws Exception
             */
            public void insertCustException(Map<String, ?> pmParam) throws Exception {

                ReqCustDAO.insertCustException(pmParam);
            }

            /**
             * 특수회원 수정
             * 김주용
             * 20220516
             * @param pmParam 검색 조건
             * @return
             * @throws Exception
             */
            public void updateCustException(Map<String, ?> pmParam) throws Exception {

                ReqCustDAO.updateCustException(pmParam);
            }


            /**
             * 특수회원 삭제
             * 김주용
             * 20220516
             * @param pmParam 검색 조건
             * @return
             * @throws Exception
             */
            public void deleteCustException(Map<String, ?> pmParam) throws Exception {

                ReqCustDAO.deleteCustException(pmParam);
            }

            /**
             * 회생 변제 관리 조회
             * 김주용
             * 20220516
             * @param pmParam 검색 조건
             * @return
             * @throws Exception
             */
            public List<Map<String, Object>> getBankRupSetup(Map<String, ?> pmParam) throws Exception {
                return ReqCustDAO.getBankRupSetup(pmParam);
            }

            /**
             * 회생 변제금 등록
             * 김주용
             * 20220516
             * @param pmParam 검색 조건
             * @return
             * @throws Exception
             */
            public void insertBankRup(Map<String, ?> pmParam) throws Exception {

                ReqCustDAO.insertBankRup(pmParam);
            }

            /**
             * 회생 변제금 수정
             * 김주용
             * 20220516
             * @param pmParam 검색 조건
             * @return
             * @throws Exception
             */
            public void updateBankRup(Map<String, ?> pmParam) throws Exception {

                ReqCustDAO.updateBankRup(pmParam);
            }

            /**
             * 회생 변제금 삭제
             * 김주용
             * 20220516
             * @param pmParam 검색 조건
             * @return
             * @throws Exception
             */
            public void deleteBankRup(Map<String, ?> pmParam) throws Exception {

                ReqCustDAO.deleteBankRup(pmParam);
            }


            /** ================================================================
             * 날짜 : 20220822
             * 이름 : 김주용
             * 추가내용 : Card 이니시스 자유결제 프로세스 중 장기할부 결제(복사)
             * ================================================================
             * */
            public Map<String, Object> billPayInicisNewTypeLongTermProc(Map<String, Object> pmParam) throws Exception {

                /***********************************************************************************************************
                 * 카드 신청 등록 시 본인 확인 부분 ( 생년월일 + 카드번호등으로 본인 확인 )
                 /***********************************************************************************************************/

                String resultCode = "";
                String resultMsg = "";
                String auth_dt = "";
                String auth_cd = "";
                String uip = "";
                String card_cd = "";
                String tid = "";

                try
                {
                     INICISPay inicisPay = new INICISPay();
                     inicisPay.setInicisKey("hmPLjIdyekyylSx9");
                     inicisPay.setInicisiv("fS7oGOerwBsEcQ==");
                     inicisPay.setData("mid", (String)pmParam.get("mid_info_1"));
                     inicisPay.setData("clientIp", "127.0.0.1");
                     inicisPay.setData("moid", (String)pmParam.get("accnt_no"));
                     inicisPay.setData("goodName", "대명스테이션상품");
                     inicisPay.setData("buyerName", (String)pmParam.get("mem_nm"));
                     inicisPay.setData("buyerEmail", "lifeway@daemyung.com");
                     inicisPay.setData("buyerTel", (String)pmParam.get("cell"));
                    inicisPay.setData("cardQuota", (String)pmParam.get("card_quota"));
                    inicisPay.setData("price", (String)pmParam.get("pay_amt"));
                    inicisPay.setData("billKey", (String)pmParam.get("ini_bid"));

                     inicisPay.authBilling();

                     resultCode = inicisPay.getData("resultCode");
                     resultMsg = inicisPay.getData("resultMsg");
                     tid = inicisPay.getData("tid");
                     auth_dt = inicisPay.getData("payDate");
                     auth_cd = inicisPay.getData("payAuthCode");
                     card_cd = inicisPay.getData("cardCode");

                     pmParam.put("result_cd", resultCode);
                     //pmParam.put("result_cd", "00");
                     pmParam.put("result_msg", resultMsg);
                     pmParam.put("result_key", tid);
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

            /** ================================================================
             * 날짜 : 20220822
             * 이름 : 김주용
             * 추가내용 : 자유결제 카드 결과데이터 INSERT(복사)
             * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_RSLT
             * ================================================================
             * */
            public int insertNewTypeFreeCardResult(Map<String, ?> pmParam) throws Exception {
                return ReqCustDAO.insertNewTypeFreeCardResult(pmParam);
            }


            /** ================================================================
             * 날짜 : 20220822
             * 이름 : 임동진
             * 추가내용 : 장기할부 정보 등록(복사)
             * 대상 테이블 : LF_DMUSER.TB_LTINSTALL_MNG
             * ================================================================
             * */
            public int insertNewTypeLongTermData(Map<String, ?> pmParam) throws Exception {
                return ReqCustDAO.insertNewTypeLongTermData(pmParam);
            }

            /** ================================================================
             * 날짜 : 20230206
             * 이름 : 조용우
             * 추가내용 : 2022년 결과관리 조회 카운트 조회
             * ================================================================
             * */
            public int getReqRltmCardLog2022Count(Map<String, Object> pmParam) {
                return ReqCustDAO.getReqRltmCardLog2022Count(pmParam);
            }

            /** ================================================================
             * 날짜 : 20230206
             * 이름 : 조용우
             * 추가내용 : 2022년 결과관리 조회 List 조회
             * ================================================================
             * */
            public List<Map<String, Object>> getReqRltmCardLog2022List(Map<String, ?> pmParam) throws Exception {
                return ReqCustDAO.getReqRltmCardLog2022List(pmParam);
            }

            /** ================================================================
             * 날짜 : 20230207
             * 이름 : 조용우
             * 추가내용 : 2022년 환불관리 조회 카운트 조회
             * ================================================================
             * */
            public int getMemberRefundList2022Count(Map<String, ?> pmParam) throws Exception {
                return ReqCustDAO.getMemberRefundList2022Count(pmParam);
            }

            /** ================================================================
             * 날짜 : 20230206
             * 이름 : 조용우
             * 추가내용 : 2022년 환불관리 조회 List 조회
             * ================================================================
             * */
            public List<Map<String, Object>> getMemberRefund2022List(Map<String, ?> pmParam) throws Exception {
                return ReqCustDAO.getMemberRefund2022List(pmParam);
            }
}
