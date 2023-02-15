package powerservice.business.sys.service.impl;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import powerservice.business.bean.UserSession;
import powerservice.business.cns.service.impl.CustBasiDAO;
import powerservice.business.cns.service.impl.MemberAddrDtlDAO;
import powerservice.business.dlw.service.impl.DlwCustDAO;
import powerservice.business.dlw.service.impl.DlwMemDAO;
import powerservice.business.sys.service.NiceCreditService;
import powerservice.common.util.ByteDataReader;
import powerservice.common.util.CommonUtils;
import powerservice.common.util.DateUtil;
import powerservice.common.util.NiceCreditUtil;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 나이스 평가정보 신용등급 조회
 *
 * @author 정출연
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID NiceCredit
 */
@Service
public class NiceCreditServiceImpl extends EgovAbstractServiceImpl implements NiceCreditService {

    private final Logger log = LoggerFactory.getLogger(NiceCreditServiceImpl.class);

    @Resource
    public NiceCreditDAO niceCreditDAO;

    @Resource
    public DlwMemDAO dlwMemDAO;

    @Resource
    public CustBasiDAO custBasiDAO;

    @Resource
    public DlwCustDAO dlwCustDAO;

    @Resource
    public MemberAddrDtlDAO memberAddrDtlDAO;

    /**
     * NICE 평가정보에서 고객 신용등급을 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 고객 정보
     * @throws Exception
     */
    public Map<String, Object> getNiceCredit(Map<String, ?> pmParam) throws Exception {
        UserSession sess 			= (UserSession) SessionUtils.getLoginUser();
        StringBuffer sbCreditRmk 	= new StringBuffer();

        String sMemNo 				= StringUtils.defaultString((String) pmParam.get("mem_no")); // 회원번호
        String sSafekey 			= StringUtils.defaultString((String) pmParam.get("safekey")); // safekey

        String sSyGubun 			= ""; // 신용구분(신용등급)
        String sSyPjum				= ""; // 신용평점
        String sSyEdate				= ""; // 신용평가정보 만료일자
        String sResCode				= ""; // 요청결과 - Safe-Key 발급과 신용정보 조회 결과
        String sFitnessYn 			= "N"; // 자격요건에 맞는 신용등급인지 여부

        long lSyGubun_RK0400_000 = 100	; // CB스코어 등급
        long lSyScore_RK0400_000 = 100	; // CB스코어 평점
        long lSyGubun_RK0400_700 = 100	; // 서브프라임 스코어 등급
        long lSyGubun_RK0600_000 = 100	; // 서브프라임 스코어 등급

        String sLoginUserId = sess.getUserid();

        Map<String, Object> mapSumServiceItem = new HashMap<String, Object>(); // 요약서비스 항목
        String sSumServiceCode = ""; // 요약서비스 항목 코드
        String sSumServiceValue = ""; // 요약서비스 항목 값

        String sCurrDthms = DateUtil.getCurrDthms();

        String sArrData[] = new String[68];

        // 공통부
        NiceCreditUtil.setNiceCommonRequest("1F003", sCurrDthms, sArrData);

        // 개별요청부
        sArrData[14] = "1"										; // 개인사업자구분
        sArrData[15] = sSafekey									; // 주민등록번호
        sArrData[16] = "03"										; // 조회사유코드
        sArrData[17] = CommonUtils.rpad(sLoginUserId, 20, " ")	; // 조회자식별코드
        sArrData[18] = "00"										; // 재요청횟수
        sArrData[19] = "000000000000"							; // 보고서인증번호
        sArrData[20] = "000"									; // 주민등록번호 변동이력정보 수신건수
        sArrData[21] = "99"										; // 주민등록번호 변동이력정보  요청건수
        sArrData[22] = "000"									; // 요약서비스 수신건수
        //sArrData[23] = "99"										; // 요약서비스 요청건수
        sArrData[23] = CommonUtils.lpad("18", 2, " ")			; // 요약서비스 요청건수
        sArrData[24] = "000"									; // 평점서비스 수신건수
        // sArrData[25] = "99"										; // 평점서비스 요청건수
        sArrData[25] = CommonUtils.lpad("3", 2, " ")			; // 평점서비스 요청건수
        sArrData[26] = "62"										; // 요약서비스구분 정보( 62 )
        sArrData[27] = "B12000200"								; // (신용관리대상/공공정보)미해제등록 총 건수 1건이상
        sArrData[28] = "62"										; // 요약서비스구분 정보( 62 )
        sArrData[29] = "B22000200"								; // (채무불이행-신용정보사)미해제등록 총 건수 1건이상
        sArrData[30] = "62"										; // 요약서비스구분 정보( 62 )
        sArrData[31] = "BE0000020"								; // (채무불이행/공공+금융질서문란 3년내관리기간외포함)등록총 건수 1건이상
        sArrData[32] = "62"										; // 요약서비스구분 정보( 62 )
        sArrData[33] = "BS0000051"								; // 희망모아 신용회복지원 대상 구분 1,2
        sArrData[34] = "62"										; // 요약서비스구분 정보( 62 )
        sArrData[35] = "BS0000784"								; // 상록수 신용회복지원 구분 1,2
        sArrData[36] = "62"										; // 요약서비스구분 정보( 62 )
        sArrData[37] = "CRT000005"								; // 파산면책 등록 유무 1
        sArrData[38] = "62"										; // 요약서비스구분 정보( 62 )
        sArrData[39] = "CRT000006"								; // 개인회생 등록 유무 1
        sArrData[40] = "62"										; // 요약서비스구분 정보( 62 )
        sArrData[41] = "CRT000009"								; // 실종공시최고 유무 1
        sArrData[42] = "62"										; // 요약서비스구분 정보( 62 )
        sArrData[43] = "CRT000010"								; // 실종선고 유무 1
        sArrData[44] = "62"										; // 요약서비스구분 정보( 62 )
        sArrData[45] = "CRT000012"								; // 부재선고 유무 1
        sArrData[46] = "62"										; // 요약서비스구분 정보( 62 )
        sArrData[47] = "CRT000013"								; // 금치산선고 유무 1
        sArrData[48] = "62"										; // 요약서비스구분 정보( 62 )
        sArrData[49] = "CRT000015"								; // 한정치산선고 유무 1
        sArrData[50] = "62"										; // 요약서비스구분 정보( 62 )
        sArrData[51] = "CRT000017"								; // 국적상실 유무 1
        sArrData[52] = "62"										; // 요약서비스구분 정보( 62 )
        sArrData[53] = "CRT000019"								; // 국적이탈 유무 1
        sArrData[54] = "62"										; // 요약서비스구분 정보( 62 )
        sArrData[55] = "CRT000023"								; // 파산선고 유무 1
        sArrData[56] = "62"										; // 요약서비스구분 정보( 62 )
        sArrData[57] = "CRT000025"								; // 면책선고 유무 1
        sArrData[58] = "62"										; // 요약서비스구분 정보( 62 )
        sArrData[59] = "CRT000027"								; // 개인회생개시 유무 1
        sArrData[60] = "62"										; // 요약서비스구분 정보( 62 )
        sArrData[61] = "AS0000138"								; // 외국인 구분(0:외국인, 1:내국인) 0

        sArrData[62] = "64"										; // 평점서비스구분 정보( 64 )
        sArrData[63] = "RK0400_000"								; // 평점표  ID
        sArrData[64] = "64"										; // 평점서비스구분 정보( 64 )
        sArrData[65] = "RK0400_700"								; // 평점표  ID
        sArrData[66] = "64"										; // 평점서비스구분 정보( 64 )
        sArrData[67] = "RK0600_000"								; // 평점표  ID


        StringBuffer sbReqData = new StringBuffer();
        for (int i=0; i<sArrData.length; ++i) {
               sbReqData.append(sArrData[i]);
        }

        Socket socket = null;
        BufferedWriter bw = null;

        String sSafeKeySrvIp 	= EgovProperties.getProperty("nice.safekey.server.ip");
        int iSafeKeySrvPort  	= Integer.valueOf(EgovProperties.getProperty("nice.safekey.server.port"));
        String sSafeKeySrvType	= EgovProperties.getProperty("nice.safekey.server.type");

        if (null != sSafeKeySrvType && "test".equals(sSafeKeySrvType)) {
            sSafeKeySrvIp 	= "192.168.15.167"; // 개발자 PC
            iSafeKeySrvPort = 51096;
        }

        try {
            socket = new Socket(sSafeKeySrvIp, iSafeKeySrvPort);
        }
        catch(IOException e)
        {
            e.printStackTrace();
            System.out.println("IOException   ===>  Safe-Key 수신을 위한 연결에 오류가 있습니다.");
            throw new Exception("Safe-Key 수신을 위한 연결에 오류가 있습니다.");
        }

        bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
        bw.write( NiceCreditUtil.setTrCd(sbReqData.toString()) );

        System.out.println("sbReqData.length()===>"+sbReqData.length());
        System.out.println("sbReqData.toString().length()===>"+sbReqData.toString().length());
        System.out.println("sbReqData.toString()===>"+sbReqData.toString());

        String recvRecord = "";

        if(socket.isBound())
        {
            System.out.println("sock bind > 1");
            bw.flush();

            InputStream in = null;
            byte buffer[] = new byte[10000];

            System.out.println("sock bind > 2");

            in = socket.getInputStream();

            System.out.println("sock bind > 3");

            int bytesRead = in.read(buffer);

            System.out.println("sock bind===>"+bytesRead);

            socket.close();
            recvRecord = new String(buffer, 0, bytesRead);

            System.out.println("recvRecord===>"+recvRecord);
            System.out.println("recvRecord.length()===>"+recvRecord.length());

            ByteDataReader bdr = new ByteDataReader(buffer, bytesRead);
            bdr.setTrim(true); // 값을 읽을때 trim 할지 여부

            System.out.println("sock bind > 4");
            System.out.println("Transaction Code : " + bdr.get(10));
            System.out.println("전문그룹코드 : " + bdr.get(9));
            System.out.println("거래종별코드 : " + bdr.get(4));
            System.out.println("거래구분코드 : " + bdr.get(5));
            System.out.println("송수신Flag : " + bdr.get(1));
            System.out.println("단말기구분 : " + bdr.get(3));
            System.out.println("sock bind > 5");

            sResCode = bdr.get(4);

            System.out.println("응답코드 : " + sResCode);
            System.out.println("User ID : " + bdr.get(9));
            System.out.println("기관전문 관리번호 : " + bdr.get(10));
            System.out.println("기관전문 전송시간 : " + bdr.get(14));
            System.out.println("NICE 전문 관리번호 : " + bdr.get(10));
            System.out.println("NICE 전문 전송시간 : " + bdr.get(14));
            System.out.println("Primary Bitmap : " + bdr.get(16));
            System.out.println("공란(Extend Bitmap Code) : " + bdr.get(1));

            // 개별응답부
            System.out.println("개인/사업자/법인 구분 : " + bdr.get(1));
            System.out.println("주민등록번호 : " + bdr.get(13));
            System.out.println("정보 연속 : " + bdr.get(1));
            System.out.println("재요청횟수 : " + bdr.get(2));
            System.out.println("성명 : " + bdr.get(12));
            System.out.println("보고서 인증번호 : " + bdr.get(12));


            //log.debug("주민등록번호 변동이력정보 총건수 : " + bdr.get(3));
            System.out.println("주민등록번호 변동이력정보 총건수 : " + bdr.get(3));
            int iJuminRespCnt = NiceCreditUtil.parseInt(bdr.get(2));
            //log.debug("주민등록번호 변동이력정보 응답건수 : " + iJuminRespCnt);
            System.out.println("주민등록번호 변동이력정보 응답건수 : " + iJuminRespCnt);

            System.out.println("요약정보 총건수 : " + bdr.get(3));
            int iSumRespCnt = NiceCreditUtil.parseInt(bdr.get(2));
            System.out.println("요약정보 응답건수 : " + iSumRespCnt);
            System.out.println("평점정보 총건수 : " + bdr.get(3));

            int iPjumRespCnt = NiceCreditUtil.parseInt(bdr.get(2));
            //log.debug("평점정보 응답건수 : " + iPjumRespCnt);
            System.out.println("평점정보 응답건수 : " + iPjumRespCnt);

            // 응답정보부 - 정보내역
            String sInfoGubun = bdr.get(2);
            boolean bUsedInfoGubn = false;

            //log.debug("정보구분 : " + sInfoGubun);
            System.out.println("정보구분 : " + sInfoGubun);


            for (int i=0; i<iJuminRespCnt; ++i) {
                //log.debug("FOR - 주민등록번호");
                System.out.println("FOR - 주민등록번호");
                if (i > 0) {
                    //log.debug("정보구분 : " + bdr.get(2));
                    System.out.println("정보구분 : " + bdr.get(2));
                }

                System.out.println("주민등록번호 변경 구분 : " + bdr.get(1));
                System.out.println("변경주민등록번호 : " + bdr.get(13));
                System.out.println("변경주민등록번호성명 : " + bdr.get(12));
                System.out.println("등록기관 명 : " + bdr.get(30));
                System.out.println("등록기관 업계 코드 : " + bdr.get(2));
                System.out.println("등록기관 코드(NICE 점포코드) : " + bdr.get(7));
                System.out.println("등록일 : " + bdr.get(8));
                System.out.println("공란 : " + bdr.get(15));

                bUsedInfoGubn = true;
            }

            for (int i=0; i<iSumRespCnt; ++i) {
                //log.debug("FOR - 요약정보");
                 System.out.println("FOR - 요약정보");
                // 응답정보부 - 용약정보 내역

                if (i > 0 || bUsedInfoGubn) {
                    //log.debug("정보구분 : " + bdr.get(2));
                     System.out.println("정보구분 : " + bdr.get(2));
                }

                sSumServiceCode = bdr.get(9);
                //log.debug("요약서비스 코드 : " + sSumServiceCode);
                System.out.println("요약서비스 코드 : " + sSumServiceCode);

                sSumServiceValue = bdr.get(9);
                //log.debug("요약서비스 값 : " + sSumServiceValue);
                System.out.println("요약서비스 값 : " + sSumServiceValue);

                mapSumServiceItem.put(sSumServiceCode, sSumServiceValue);

                sbCreditRmk.append(NiceCreditUtil.getNiceCreditSumSrvItemNm(sSumServiceCode) + "\t" + NiceCreditUtil.parseNiceCreditSumSrvItemVal(sSumServiceCode, sSumServiceValue) + "\n");

                bUsedInfoGubn = true;
            }

            String sCbSCoreGbunNm 	= ""; // CB 스코어 구분명

            for (int i=0; i<iPjumRespCnt; ++i) {
                //log.debug("FOR - 평가정보내역");
                System.out.println("FOR - 평가정보내역");
                // 응답정보부 - 평점정보 내역
                if (i > 0 || bUsedInfoGubn) {
                    //log.debug("정보구분 : " + bdr.get(2));
                    System.out.println("정보구분 : " + bdr.get(2));
                }
                //log.debug("평정 결과 : " + bdr.get(2));
                System.out.println("평정 결과 : " + bdr.get(2));

                sCbSCoreGbunNm = bdr.get(10);

                System.out.println("CB 스코어 구분명 : " + sCbSCoreGbunNm);
                System.out.println("적용배제사유코드1 : " + bdr.get(4));
                System.out.println("적용배제사유코드2 : " + bdr.get(4));
                System.out.println("적용배제사유코드3 : " + bdr.get(4));
                System.out.println("평점 관련 사유코드 1 : " + bdr.get(4));
                System.out.println("평점 관련 사유코드 2 : " + bdr.get(4));
                System.out.println("평점 관련 사유코드 3 : " + bdr.get(4));
                System.out.println("평점 관련 사유코드 4 : " + bdr.get(4));
                System.out.println("평점 관련 사유코드 5 : " + bdr.get(4));
                System.out.println("평점 관련 사유코드 6 : " + bdr.get(4));

                sSyPjum = bdr.get(4);
                log.debug("신용평점 : " + sSyPjum);
                log.debug("sSyPjum : " + sSyPjum);

                System.out.println("신용평점 : " + sSyPjum);
                System.out.println("sSyPjum : " + sSyPjum);

                sSyGubun = bdr.get(4);

                System.out.println("신용등급 : " + sSyGubun);
                System.out.println("sSyGubun : " + sSyGubun);
                System.out.println("평점 관련 결과값1 : " + bdr.get(9));
                System.out.println("평점 관련 결과값2 : " + bdr.get(9));
                System.out.println("평점 관련 결과값3 : " + bdr.get(9));
                System.out.println("에러코드 : " + bdr.get(4));
                System.out.println("Profile Code 1 : " + bdr.get(3));
                System.out.println("Profile Code 2 : " + bdr.get(3));
                System.out.println("Profile Code 3 : " + bdr.get(3));
                System.out.println("공란 : " + bdr.get(2));

                if ("RK0400_000".equals(sCbSCoreGbunNm))
                {
                    lSyGubun_RK0400_000 = Long.valueOf(sSyGubun);
                    lSyScore_RK0400_000 = Long.valueOf(sSyPjum);
                }
                else if ("RK0600_000".equals(sCbSCoreGbunNm))
                {
                    lSyGubun_RK0600_000 = Long.valueOf(sSyPjum);
                }
                else if ("RK0400_700".equals(sCbSCoreGbunNm))
                {
                    lSyGubun_RK0400_700 = Long.valueOf(sSyGubun);
                }

                bUsedInfoGubn = true;
            }

            System.out.println("sock bind > 7");

        } else {
            throw new Exception("Safe-Key 발급 서버로부터 응답을 받지 못했습니다.");
        }

        // 애큐원 연계를 위한 나이스 신용등급을 가지고 있는지 체크

        if ( NiceCreditUtil.hasNiceCreditForAquon(lSyGubun_RK0400_000, lSyScore_RK0400_000, mapSumServiceItem) ) {
            sFitnessYn = "Y";
        }

        sbCreditRmk.append("CB스코어등급\t" + lSyGubun_RK0400_000 + "\n");
        sbCreditRmk.append("CB스코어평점 점수\t" + lSyScore_RK0400_000 + "\n");

        String sCreditRmk = sbCreditRmk.toString();
        
        System.out.println("sCreditRmk : "+sCreditRmk);

        /*******************************************************************************/
        /* 응답결과 생성 */
        /*******************************************************************************/
        Map<String, Object> mCredit = new HashMap<String, Object>();

        /* 업무 담당자 정보 */
        mCredit.put("loginid"		, sess.getLoginid());
        mCredit.put("emple_no"		, sess.getEmployeeid());

        /* 고객 정보 */
        mCredit.put("mem_no"			, sMemNo		); // 고유번호
        mCredit.put("safekey"			, sSafekey		);
        mCredit.put("sy_gubun"			, sSyGubun		); // 신용등급
        mCredit.put("sy_pjum"			, sSyPjum		); // 신용평점
        mCredit.put("sy_date"			, sCurrDthms	); // 신용조회일시
        mCredit.put("credit_edate"		, sSyEdate		); // 신용등급만료일자
        mCredit.put("credit_rmk"		, sCreditRmk	); // 신용등급만료일자
        mCredit.put("return_code"		, sResCode		); // 응답코드 - safekey 발급과 신용등급 조회 결과
        mCredit.put("acuon_fitness_yn"	, sFitnessYn	); // 자격요건에 맞는 신용등급인지 여부
        mCredit.put("srch_dt"			, sCurrDthms.substring(0,8)	); // 신용조회일자
        mCredit.put("safekey_srch_dt"	, sCurrDthms.substring(0,8)	); // 신용조회일자

        niceCreditDAO.insertNiceCreditSearchHis(mCredit);

        // 세이프키 자동 갱신 사용 안함 - 2016.09.26
//		if ("Y".equals(sFitnessYn)) {
//			ParamUtils.addSaveParam(mCredit);
//
//			if (!"".equals(sMemNo)) {
//				int iCnt = dlwMemDAO.updateNiceSafekey(mCredit);
//				if (iCnt > 0) {
//		            Map<String, Object> mDtpt = dlwCustDAO.getDlwMemberDtpt( sMemNo );
//		            custBasiDAO.insertCustBasiHstr(mDtpt);
//		        }
//			}
//		}


        return mCredit;
    }
    
    /**
     * NICE 평가정보에서 고객 신용등급을 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 고객 정보
     * @throws Exception
     */
    public Map<String, Object> getNiceCredit600(Map<String, ?> pmParam) throws Exception {
        UserSession sess 			= (UserSession) SessionUtils.getLoginUser();
        StringBuffer sbCreditRmk 	= new StringBuffer();

        String sMemNo 				= StringUtils.defaultString((String) pmParam.get("mem_no")); // 회원번호
        String sSafekey 			= StringUtils.defaultString((String) pmParam.get("safekey")); // safekey

        String sSyGubun 			= ""; // 신용구분(신용등급)
        String sSyPjum				= ""; // 신용평점
        String sSyEdate				= ""; // 신용평가정보 만료일자
        String sResCode				= ""; // 요청결과 - Safe-Key 발급과 신용정보 조회 결과
        String sFitnessYn 			= "N"; // 자격요건에 맞는 신용등급인지 여부

        long lSyGubun_RK0400_000 = 100	; // CB스코어 등급
        long lSyScore_RK0400_000 = 100	; // CB스코어 평점
        long lSyGubun_RK0400_700 = 100	; // 서브프라임 스코어 등급
        long lSyGubun_RK0600_000 = 100	; // 서브프라임 스코어 등급
        long lSyScore_RK0600_000 = 100	; // 서브프라임 스코어 평점

        String sLoginUserId = sess.getUserid();

        Map<String, Object> mapSumServiceItem = new HashMap<String, Object>(); // 요약서비스 항목
        String sSumServiceCode = ""; // 요약서비스 항목 코드
        String sSumServiceValue = ""; // 요약서비스 항목 값

        String sCurrDthms = DateUtil.getCurrDthms();

        String sArrData[] = new String[68];

        // 공통부
        NiceCreditUtil.setNiceCommonRequest("1F003", sCurrDthms, sArrData);

        // 개별요청부
        sArrData[14] = "1"										; // 개인사업자구분
        sArrData[15] = sSafekey									; // 주민등록번호
        sArrData[16] = "03"										; // 조회사유코드
        sArrData[17] = CommonUtils.rpad(sLoginUserId, 20, " ")	; // 조회자식별코드
        sArrData[18] = "00"										; // 재요청횟수
        sArrData[19] = "000000000000"							; // 보고서인증번호
        sArrData[20] = "000"									; // 주민등록번호 변동이력정보 수신건수
        sArrData[21] = "99"										; // 주민등록번호 변동이력정보  요청건수
        sArrData[22] = "000"									; // 요약서비스 수신건수
        //sArrData[23] = "99"										; // 요약서비스 요청건수
        sArrData[23] = CommonUtils.lpad("18", 2, " ")			; // 요약서비스 요청건수
        sArrData[24] = "000"									; // 평점서비스 수신건수
        // sArrData[25] = "99"										; // 평점서비스 요청건수
        sArrData[25] = CommonUtils.lpad("3", 2, " ")			; // 평점서비스 요청건수
        sArrData[26] = "62"										; // 요약서비스구분 정보( 62 )
        sArrData[27] = "B12000200"								; // (신용관리대상/공공정보)미해제등록 총 건수 1건이상
        sArrData[28] = "62"										; // 요약서비스구분 정보( 62 )
        sArrData[29] = "B22000200"								; // (채무불이행-신용정보사)미해제등록 총 건수 1건이상
        sArrData[30] = "62"										; // 요약서비스구분 정보( 62 )
        sArrData[31] = "BE0000020"								; // (채무불이행/공공+금융질서문란 3년내관리기간외포함)등록총 건수 1건이상
        sArrData[32] = "62"										; // 요약서비스구분 정보( 62 )
        sArrData[33] = "BS0000051"								; // 희망모아 신용회복지원 대상 구분 1,2
        sArrData[34] = "62"										; // 요약서비스구분 정보( 62 )
        sArrData[35] = "BS0000784"								; // 상록수 신용회복지원 구분 1,2
        sArrData[36] = "62"										; // 요약서비스구분 정보( 62 )
        sArrData[37] = "CRT000005"								; // 파산면책 등록 유무 1
        sArrData[38] = "62"										; // 요약서비스구분 정보( 62 )
        sArrData[39] = "CRT000006"								; // 개인회생 등록 유무 1
        sArrData[40] = "62"										; // 요약서비스구분 정보( 62 )
        sArrData[41] = "CRT000009"								; // 실종공시최고 유무 1
        sArrData[42] = "62"										; // 요약서비스구분 정보( 62 )
        sArrData[43] = "CRT000010"								; // 실종선고 유무 1
        sArrData[44] = "62"										; // 요약서비스구분 정보( 62 )
        sArrData[45] = "CRT000012"								; // 부재선고 유무 1
        sArrData[46] = "62"										; // 요약서비스구분 정보( 62 )
        sArrData[47] = "CRT000013"								; // 금치산선고 유무 1
        sArrData[48] = "62"										; // 요약서비스구분 정보( 62 )
        sArrData[49] = "CRT000015"								; // 한정치산선고 유무 1
        sArrData[50] = "62"										; // 요약서비스구분 정보( 62 )
        sArrData[51] = "CRT000017"								; // 국적상실 유무 1
        sArrData[52] = "62"										; // 요약서비스구분 정보( 62 )
        sArrData[53] = "CRT000019"								; // 국적이탈 유무 1
        sArrData[54] = "62"										; // 요약서비스구분 정보( 62 )
        sArrData[55] = "CRT000023"								; // 파산선고 유무 1
        sArrData[56] = "62"										; // 요약서비스구분 정보( 62 )
        sArrData[57] = "CRT000025"								; // 면책선고 유무 1
        sArrData[58] = "62"										; // 요약서비스구분 정보( 62 )
        sArrData[59] = "CRT000027"								; // 개인회생개시 유무 1
        sArrData[60] = "62"										; // 요약서비스구분 정보( 62 )
        sArrData[61] = "AS0000138"								; // 외국인 구분(0:외국인, 1:내국인) 0

        sArrData[62] = "64"										; // 평점서비스구분 정보( 64 )
        //sArrData[63] = "RK0400_000"								; // 평점표  ID
        sArrData[63] = "RK0600_000"								; // 평점표  ID
        sArrData[64] = "64"										; // 평점서비스구분 정보( 64 )
        //sArrData[65] = "RK0400_700"								; // 평점표  ID
        sArrData[65] = "RK0600_000"								; // 평점표  ID
        sArrData[66] = "64"										; // 평점서비스구분 정보( 64 )
        sArrData[67] = "RK0600_000"								; // 평점표  ID


        StringBuffer sbReqData = new StringBuffer();
        for (int i=0; i<sArrData.length; ++i) {
               sbReqData.append(sArrData[i]);
        }

        Socket socket = null;
        BufferedWriter bw = null;

        String sSafeKeySrvIp 	= EgovProperties.getProperty("nice.safekey.server.ip");
        int iSafeKeySrvPort  	= Integer.valueOf(EgovProperties.getProperty("nice.safekey.server.port"));
        String sSafeKeySrvType	= EgovProperties.getProperty("nice.safekey.server.type");

        if (null != sSafeKeySrvType && "test".equals(sSafeKeySrvType)) {
            sSafeKeySrvIp 	= "192.168.15.167"; // 개발자 PC
            iSafeKeySrvPort = 51096;
        }

        try {
            socket = new Socket(sSafeKeySrvIp, iSafeKeySrvPort);
        }
        catch(IOException e)
        {
            e.printStackTrace();
            System.out.println("IOException   ===>  Safe-Key 수신을 위한 연결에 오류가 있습니다.");
            throw new Exception("Safe-Key 수신을 위한 연결에 오류가 있습니다.");
        }

        bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
        bw.write( NiceCreditUtil.setTrCd(sbReqData.toString()) );

        System.out.println("sbReqData.length()===>"+sbReqData.length());
        System.out.println("sbReqData.toString().length()===>"+sbReqData.toString().length());
        System.out.println("sbReqData.toString()===>"+sbReqData.toString());

        String recvRecord = "";

        if(socket.isBound())
        {
            System.out.println("sock bind > 1");
            bw.flush();

            InputStream in = null;
            byte buffer[] = new byte[10000];

            System.out.println("sock bind > 2");

            in = socket.getInputStream();

            System.out.println("sock bind > 3");

            int bytesRead = in.read(buffer);

            System.out.println("sock bind===>"+bytesRead);

            socket.close();
            recvRecord = new String(buffer, 0, bytesRead);

            System.out.println("recvRecord===>"+recvRecord);
            System.out.println("recvRecord.length()===>"+recvRecord.length());

            ByteDataReader bdr = new ByteDataReader(buffer, bytesRead);
            bdr.setTrim(true); // 값을 읽을때 trim 할지 여부

            System.out.println("sock bind > 4");
            System.out.println("Transaction Code : " + bdr.get(10));
            System.out.println("전문그룹코드 : " + bdr.get(9));
            System.out.println("거래종별코드 : " + bdr.get(4));
            System.out.println("거래구분코드 : " + bdr.get(5));
            System.out.println("송수신Flag : " + bdr.get(1));
            System.out.println("단말기구분 : " + bdr.get(3));
            System.out.println("sock bind > 5");

            sResCode = bdr.get(4);

            System.out.println("응답코드 : " + sResCode);
            System.out.println("User ID : " + bdr.get(9));
            System.out.println("기관전문 관리번호 : " + bdr.get(10));
            System.out.println("기관전문 전송시간 : " + bdr.get(14));
            System.out.println("NICE 전문 관리번호 : " + bdr.get(10));
            System.out.println("NICE 전문 전송시간 : " + bdr.get(14));
            System.out.println("Primary Bitmap : " + bdr.get(16));
            System.out.println("공란(Extend Bitmap Code) : " + bdr.get(1));

            // 개별응답부
            System.out.println("개인/사업자/법인 구분 : " + bdr.get(1));
            System.out.println("주민등록번호 : " + bdr.get(13));
            System.out.println("정보 연속 : " + bdr.get(1));
            System.out.println("재요청횟수 : " + bdr.get(2));
            System.out.println("성명 : " + bdr.get(12));
            System.out.println("보고서 인증번호 : " + bdr.get(12));


            //log.debug("주민등록번호 변동이력정보 총건수 : " + bdr.get(3));
            System.out.println("주민등록번호 변동이력정보 총건수 : " + bdr.get(3));
            int iJuminRespCnt = NiceCreditUtil.parseInt(bdr.get(2));
            //log.debug("주민등록번호 변동이력정보 응답건수 : " + iJuminRespCnt);
            System.out.println("주민등록번호 변동이력정보 응답건수 : " + iJuminRespCnt);

            System.out.println("요약정보 총건수 : " + bdr.get(3));
            int iSumRespCnt = NiceCreditUtil.parseInt(bdr.get(2));
            System.out.println("요약정보 응답건수 : " + iSumRespCnt);
            System.out.println("평점정보 총건수 : " + bdr.get(3));

            int iPjumRespCnt = NiceCreditUtil.parseInt(bdr.get(2));
            //log.debug("평점정보 응답건수 : " + iPjumRespCnt);
            System.out.println("평점정보 응답건수 : " + iPjumRespCnt);

            // 응답정보부 - 정보내역
            String sInfoGubun = bdr.get(2);
            boolean bUsedInfoGubn = false;

            //log.debug("정보구분 : " + sInfoGubun);
            System.out.println("정보구분 : " + sInfoGubun);


            for (int i=0; i<iJuminRespCnt; ++i) {
                //log.debug("FOR - 주민등록번호");
                System.out.println("FOR - 주민등록번호");
                if (i > 0) {
                    //log.debug("정보구분 : " + bdr.get(2));
                    System.out.println("정보구분 : " + bdr.get(2));
                }

                System.out.println("주민등록번호 변경 구분 : " + bdr.get(1));
                System.out.println("변경주민등록번호 : " + bdr.get(13));
                System.out.println("변경주민등록번호성명 : " + bdr.get(12));
                System.out.println("등록기관 명 : " + bdr.get(30));
                System.out.println("등록기관 업계 코드 : " + bdr.get(2));
                System.out.println("등록기관 코드(NICE 점포코드) : " + bdr.get(7));
                System.out.println("등록일 : " + bdr.get(8));
                System.out.println("공란 : " + bdr.get(15));

                bUsedInfoGubn = true;
            }

            for (int i=0; i<iSumRespCnt; ++i) {
                //log.debug("FOR - 요약정보");
                 System.out.println("FOR - 요약정보");
                // 응답정보부 - 용약정보 내역

                if (i > 0 || bUsedInfoGubn) {
                    //log.debug("정보구분 : " + bdr.get(2));
                     System.out.println("정보구분 : " + bdr.get(2));
                }

                sSumServiceCode = bdr.get(9);
                //log.debug("요약서비스 코드 : " + sSumServiceCode);
                System.out.println("요약서비스 코드 : " + sSumServiceCode);

                sSumServiceValue = bdr.get(9);
                //log.debug("요약서비스 값 : " + sSumServiceValue);
                System.out.println("요약서비스 값 : " + sSumServiceValue);

                mapSumServiceItem.put(sSumServiceCode, sSumServiceValue);

                sbCreditRmk.append(NiceCreditUtil.getNiceCreditSumSrvItemNm(sSumServiceCode) + "\t" + NiceCreditUtil.parseNiceCreditSumSrvItemVal(sSumServiceCode, sSumServiceValue) + "\n");

                bUsedInfoGubn = true;
            }

            String sCbSCoreGbunNm 	= ""; // CB 스코어 구분명

            for (int i=0; i<iPjumRespCnt; ++i) {
                //log.debug("FOR - 평가정보내역");
                System.out.println("FOR - 평가정보내역");
                // 응답정보부 - 평점정보 내역
                if (i > 0 || bUsedInfoGubn) {
                    //log.debug("정보구분 : " + bdr.get(2));
                    System.out.println("정보구분 : " + bdr.get(2));
                }
                //log.debug("평정 결과 : " + bdr.get(2));
                System.out.println("평정 결과 : " + bdr.get(2));

                sCbSCoreGbunNm = bdr.get(10);

                System.out.println("CB 스코어 구분명 : " + sCbSCoreGbunNm);
                System.out.println("적용배제사유코드1 : " + bdr.get(4));
                System.out.println("적용배제사유코드2 : " + bdr.get(4));
                System.out.println("적용배제사유코드3 : " + bdr.get(4));
                System.out.println("평점 관련 사유코드 1 : " + bdr.get(4));
                System.out.println("평점 관련 사유코드 2 : " + bdr.get(4));
                System.out.println("평점 관련 사유코드 3 : " + bdr.get(4));
                System.out.println("평점 관련 사유코드 4 : " + bdr.get(4));
                System.out.println("평점 관련 사유코드 5 : " + bdr.get(4));
                System.out.println("평점 관련 사유코드 6 : " + bdr.get(4));

                sSyPjum = bdr.get(4);
                log.debug("신용평점 : " + sSyPjum);
                log.debug("sSyPjum : " + sSyPjum);

                System.out.println("신용평점 : " + sSyPjum);
                System.out.println("sSyPjum : " + sSyPjum);

                sSyGubun = bdr.get(4);

                System.out.println("신용등급 : " + sSyGubun);
                System.out.println("sSyGubun : " + sSyGubun);
                System.out.println("평점 관련 결과값1 : " + bdr.get(9));
                System.out.println("평점 관련 결과값2 : " + bdr.get(9));
                System.out.println("평점 관련 결과값3 : " + bdr.get(9));
                System.out.println("에러코드 : " + bdr.get(4));
                System.out.println("Profile Code 1 : " + bdr.get(3));
                System.out.println("Profile Code 2 : " + bdr.get(3));
                System.out.println("Profile Code 3 : " + bdr.get(3));
                System.out.println("공란 : " + bdr.get(2));

                if ("RK0400_000".equals(sCbSCoreGbunNm))
                {
                    lSyGubun_RK0400_000 = Long.valueOf(sSyGubun);
                    lSyScore_RK0400_000 = Long.valueOf(sSyPjum);
                }
                else if ("RK0600_000".equals(sCbSCoreGbunNm))
                {
                    lSyGubun_RK0600_000 = Long.valueOf(sSyGubun);
                    lSyScore_RK0600_000 = Long.valueOf(sSyPjum);
                }
                else if ("RK0400_700".equals(sCbSCoreGbunNm))
                {
                    lSyGubun_RK0400_700 = Long.valueOf(sSyGubun);
                }

                bUsedInfoGubn = true;
            }

            System.out.println("sock bind > 7");

        } else {
            throw new Exception("Safe-Key 발급 서버로부터 응답을 받지 못했습니다.");
        }

        // 애큐원 연계를 위한 나이스 신용등급을 가지고 있는지 체크

        if ( NiceCreditUtil.hasNiceCreditForAquon(lSyGubun_RK0600_000, lSyScore_RK0600_000, mapSumServiceItem) ) {
            sFitnessYn = "Y";
        }

        sbCreditRmk.append("CB스코어등급\t" + lSyGubun_RK0600_000 + "\n");
        sbCreditRmk.append("CB스코어평점 점수\t" + lSyScore_RK0600_000 + "\n");

        String sCreditRmk = sbCreditRmk.toString();
        
        System.out.println("sCreditRmk : "+sCreditRmk);

        /*******************************************************************************/
        /* 응답결과 생성 */
        /*******************************************************************************/
        Map<String, Object> mCredit = new HashMap<String, Object>();

        /* 업무 담당자 정보 */
        mCredit.put("loginid"		, sess.getLoginid());
        mCredit.put("emple_no"		, sess.getEmployeeid());

        /* 고객 정보 */
        mCredit.put("mem_no"			, sMemNo		); // 고유번호
        mCredit.put("safekey"			, sSafekey		);
        mCredit.put("sy_gubun"			, sSyGubun		); // 신용등급
        mCredit.put("sy_pjum"			, sSyPjum		); // 신용평점
        mCredit.put("sy_date"			, sCurrDthms	); // 신용조회일시
        mCredit.put("credit_edate"		, sSyEdate		); // 신용등급만료일자
        mCredit.put("credit_rmk"		, sCreditRmk	); // 신용등급만료일자
        mCredit.put("return_code"		, sResCode		); // 응답코드 - safekey 발급과 신용등급 조회 결과
        mCredit.put("acuon_fitness_yn"	, sFitnessYn	); // 자격요건에 맞는 신용등급인지 여부
        mCredit.put("srch_dt"			, sCurrDthms.substring(0,8)	); // 신용조회일자
        mCredit.put("safekey_srch_dt"	, sCurrDthms.substring(0,8)	); // 신용조회일자

        niceCreditDAO.insertNiceCreditSearchHis(mCredit);

        // 세이프키 자동 갱신 사용 안함 - 2016.09.26
//		if ("Y".equals(sFitnessYn)) {
//			ParamUtils.addSaveParam(mCredit);
//
//			if (!"".equals(sMemNo)) {
//				int iCnt = dlwMemDAO.updateNiceSafekey(mCredit);
//				if (iCnt > 0) {
//		            Map<String, Object> mDtpt = dlwCustDAO.getDlwMemberDtpt( sMemNo );
//		            custBasiDAO.insertCustBasiHstr(mDtpt);
//		        }
//			}
//		}


        return mCredit;
    }

    /**
     * NICE Safe-Key를 조회한다.
     *
     * @param pmParam 검색 조건
     * @return Safe-Key 발급정보
     * @throws Exception
     */
    public Map<String, Object> getNiceSafeKey(Map<String, ?> pmParam) throws Exception {

        System.out.println("> getNiceSafekey called..");

        Map<String, Object> hmResult = NiceCreditUtil.getNiceSafeKey(pmParam); //여기 푸세요!!!

        UserSession sess 	= (UserSession) SessionUtils.getLoginUser();

        /* 업무 담당자 정보 */
        hmResult.put("loginid"		, "ADMIN");
        hmResult.put("empl_no"		, "999999");

        System.out.println("sess.getEmployeeid()===>"+sess.getEmployeeid());
        System.out.println("sess.getLoginid()===>"+sess.getLoginid());
        // hmResult.put("loginid"		, sess.getLoginid());
        // hmResult.put("empl_no"		, sess.getEmployeeid());

        /* 나이스평가정보에서 세이프키를 조회하지 못한경우 세이프키SMS발급이력 테이블에서 조회한다 */
        String sSafekey = CommonUtils.nvls((String)hmResult.get("safekey"));
        if ("".equals(sSafekey))
        {
            System.out.println(">>>>> 나이스에서 세이프키가 조회되지 않아 NICE_SAFEKEY_SMS_HIS 테이블에서 조회함");

            List<Map<String, Object>> lstRet = niceCreditDAO.getSafekeKeyListFromHist(pmParam);
            if (null != lstRet && lstRet.size() > 0) {
                Map<String, Object> mRow = lstRet.get(0);
                hmResult.put("safekey", CommonUtils.nvls((String)mRow.get("safekey")));
                hmResult.put("credit_rmk", "나이스에서 세이프키가 조회되지 않아 NICE_SAFEKEY_SMS_HIS 테이블에서 조회함");
            }
        }

        niceCreditDAO.insertNiceSafekeySearchHis(hmResult);

        return hmResult;
    }

    /**
     * NICE Safe-Key 발급권유 SMS 발송결과
     *
     * @param pmParam 검색 조건
     * @return 고객 정보
     * @throws Exception
     */
    public Map<String, Object> updateSafeKeySmsSendResult(HttpServletRequest request, HttpSession sess) throws Exception {

        NiceID.Check.CPClient niceCheck = new  NiceID.Check.CPClient();

        String sEncodeData = NiceCreditUtil.requestReplace(request.getParameter("EncodeData"), "encodeData");
        String sReserved1  = NiceCreditUtil.requestReplace(request.getParameter("param_r1"), "");
        String sReserved2  = NiceCreditUtil.requestReplace(request.getParameter("param_r2"), "");
        String sReserved3  = NiceCreditUtil.requestReplace(request.getParameter("param_r3"), "");

        String sSiteCode 		= EgovProperties.getProperty("nice.safekey.weblink.sitecode"); 	// NICE로부터 부여받은 사이트 코드
        String sSitePassword 	= EgovProperties.getProperty("nice.safekey.weblink.sitepw");	// NICE로부터 부여받은 사이트 패스워드

        String sCipherTime = "";			// 복호화한 시간
        String sRequestNumber = "";			// 요청 번호
        String sReturnCode = "";			// 결과코드
        String sReturnMsg = "";				// 결과 메시지

        String sMessage = "";
        String sPlainData = "";

        int iReturn = niceCheck.fnDecode(sSiteCode, sSitePassword, sEncodeData);

        if( iReturn == 0 )
        {
            sPlainData = niceCheck.getPlainData();
            sCipherTime = niceCheck.getCipherDateTime();

            // 데이타를 추출합니다.
            HashMap mapresult = niceCheck.fnParse(sPlainData);

            //아래는 실제 데이타를 가져 오는 부분이다.
            sRequestNumber 	= (String)mapresult.get("REQ_SEQ");
            sReturnCode 	= (String)mapresult.get("RETURN_CODE");
            sReturnMsg 		= (String)mapresult.get("RETURN_MSG");

            String session_sRequestNumber 	= (String)sess.getAttribute("REQ_SEQ");

            if(!sRequestNumber.equals(session_sRequestNumber))
            {
                sMessage = "세션값이 다릅니다. 올바른 경로로 접근하시기 바랍니다.";
            }
        }
        else if( iReturn == -1)
        {
            sMessage = "복호화 시스템 에러입니다.";
        }
        else if( iReturn == -4)
        {
            sMessage = "복호화 처리오류입니다.";
        }
        else if( iReturn == -5)
        {
            sMessage = "복호화 해쉬 오류입니다.";
        }
        else if( iReturn == -6)
        {
            sMessage = "복호화 데이터 오류입니다.";
        }
        else if( iReturn == -9)
        {
            sMessage = "입력 데이터 오류입니다.";
        }
        else if( iReturn == -12)
        {
            sMessage = "사이트 패스워드 오류입니다.";
        }
        else
        {
            sMessage = "알수 없는 에러 입니다. iReturn : " + iReturn;
        }

        log.debug("################# updateSafeKeySmsSendResult.jsp ##############");
        log.debug("복호화한 시간 : " + sCipherTime);
        log.debug("요청번호 : " 		+ sRequestNumber);
        log.debug("결과코드 : " 		+ sReturnCode);
        log.debug("결과메세지 : " 	+ sReturnMsg);
        log.debug("RESERVED1 : " 	+ sReserved1);
        log.debug("RESERVED2 : " 	+ sReserved2);
        log.debug("RESERVED3 : " 	+ sReserved3);
        log.debug("sMessage : " 		+ sMessage);

        System.out.println("################# updateSafeKeySmsSendResult.jsp ##############");
        System.out.println("복호화한 시간 : " + sCipherTime);
        System.out.println("요청번호 : " 		+ sRequestNumber);
        System.out.println("결과코드 : " 		+ sReturnCode);
        System.out.println("결과메세지 : " 	+ sReturnMsg);
        System.out.println("RESERVED1 : " 	+ sReserved1);
        System.out.println("RESERVED2 : " 	+ sReserved2);
        System.out.println("RESERVED3 : " 	+ sReserved3);
        System.out.println("sMessage : " 	+ sMessage);




        // niceCreditDAO.insertNiceCreditSearchHis(mCredit);
        Map<String, Object> mRet = new HashMap<String, Object>();

        mRet.put("sCipherTime"		, sCipherTime	);
        mRet.put("sRequestNumber"	, sRequestNumber);
        mRet.put("sReturnCode"		, sReturnCode	);
        mRet.put("sReturnMsg"		, sReturnMsg	);
        mRet.put("sReserved1"		, sReserved1	);
        mRet.put("sReserved2"		, sReserved2	);
        mRet.put("sReserved3"		, sReserved3	);
        mRet.put("sMessage"			, sMessage		);

        request.setAttribute("sendResult", mRet);


        Map<String, Object> mParam = new HashMap<String, Object>();
        mParam.put("req_seq"		, sRequestNumber	); // SMS 발송요청일련번호
        mParam.put("req_dthms"		, sReserved1		); // SMS 발송요청시간
        mParam.put("loginid"		, sReserved2		); // SMS 발송요청자 로그인ID
        mParam.put("return_code"	, sReturnCode		); // SMS 발송결과코드

        // SMS 발송결과 갱신
        niceCreditDAO.updateNiceSafekeySmsSendResult(mParam);

        return mRet;
    }


    /**
     * NICE Safe-Key 발급권유 SMS 발송결과
     *
     * @param pmParam 검색 조건
     * @return 고객 정보
     * @throws Exception
     */
    public void updateNiceSafekeyMobileIssueResult(HttpServletRequest request, boolean isSuccess) throws Exception {
        System.out.println("");
        System.out.println("###################### 세이프키 발급 결과 리턴 #####################");
        System.out.println("");

        NiceID.Check.CPClient niceCheck = new  NiceID.Check.CPClient();

        String sEncodeData 		= NiceCreditUtil.requestReplace(request.getParameter("EncodeData"), "encodeData");

        String sSiteCode 		= EgovProperties.getProperty("nice.safekey.weblink.sitecode");
        String sSitePassword	= EgovProperties.getProperty("nice.safekey.weblink.sitepw");

        String sReqDthms  		= NiceCreditUtil.requestReplace(request.getParameter("param_r1"), "");
        String sLoginid  		= NiceCreditUtil.requestReplace(request.getParameter("param_r2"), "");
        String sMemNo  			= NiceCreditUtil.requestReplace(request.getParameter("param_r3"), "");

        System.out.println("########################## 파라미터 덤프 시작");

        Enumeration<String> e = request.getParameterNames();
        while(e.hasMoreElements()){
            String name = e.nextElement();
            String[] data = request.getParameterValues(name);
            String sVal = "";
            for (int i=0; i<data.length; ++i) {
                sVal = sVal + ", " + data[i];
            }
            System.out.println(name +  " : " + sVal);
        }

        System.out.println("########################## 파라미터 덤프 끝");

        String sPlainData 		= "";
        String sSafekey 		= "";
        // String sCipherTime 		= "";
        String sRequestNumber 	= "";
        // String sResponseNumber 	= "";
        String sReturnCode 		= "";
        String sReturnMsg 		= "";
        // String sRequestTime 	= "";
        String sAuthTime 		= "";
        String sAuthType 		= "";
        // String sAgree1Map 		= "";
        // String sAgree2Map 		= "";
        // String sAgree3Map 		= "";
        String sCi 				= "";
        String sMobileno 		= "";
        String sErrMsg			= "";

        int iReturn = niceCheck.fnDecode(sSiteCode, sSitePassword, sEncodeData);

        if( iReturn == 0 )
        {
            sPlainData = niceCheck.getPlainData();
            // sCipherTime = niceCheck.getCipherDateTime();

            // 데이타를 추출합니다.
            HashMap mapresult = niceCheck.fnParse(sPlainData);

            //아래는 실제 데이타를 가져 오는 부분이다.
            sSafekey = (String)mapresult.get("SAFE_KEY");
            sRequestNumber = (String)mapresult.get("REQ_SEQ");
            // sResponseNumber = (String)mapresult.get("RES_SEQ");
            sReturnCode = (String)mapresult.get("RETURN_CODE");
            sReturnMsg = (String)mapresult.get("RETURN_MSG");
            // sRequestTime = (String)mapresult.get("REQ_DATETIME");
            sAuthTime = (String)mapresult.get("AUTH_DATETIME");
            sAuthType = (String)mapresult.get("AUTH_TYPE");
            // sAgree1Map = (String)mapresult.get("AGREE1_MAP");
            // sAgree2Map = (String)mapresult.get("AGREE2_MAP");
            // sAgree3Map = (String)mapresult.get("AGREE3_MAP");
            sCi = (String)mapresult.get("CI");
            sMobileno = (String)mapresult.get("MOBILENO");
        }
        else if( iReturn == -1)
        {
            sErrMsg = "복호화 시스템 에러입니다.";
        }
        else if( iReturn == -4)
        {
            sErrMsg = "복호화 처리오류입니다.";
        }
        else if( iReturn == -5)
        {
            sErrMsg = "복호화 해쉬 오류입니다.";
        }
        else if( iReturn == -6)
        {
            sErrMsg = "복호화 데이터 오류입니다.";
        }
        else if( iReturn == -9)
        {
            sErrMsg = "입력 데이터 오류입니다.";
        }
        else if( iReturn == -12)
        {
            sErrMsg = "사이트 패스워드 오류입니다.";
        }
        else
        {
            sErrMsg = "알수 없는 에러 입니다. iReturn : " + iReturn;
        }

        Map<String, Object> mRet = new HashMap<String, Object>();

        mRet.put("safekey"			, sSafekey);

        mRet.put("auth_type"		, sAuthType);
        mRet.put("auth_result_code"	, sReturnCode);

        mRet.put("return_code"		, sReturnCode);

        mRet.put("return_msg"		, CommonUtils.nvls(sReturnMsg));

        mRet.put("error_msg"		, sErrMsg);

        mRet.put("auth_datetime"	, sAuthTime);
        mRet.put("req_seq"			, sRequestNumber);
        mRet.put("req_dthms"		, sReqDthms);
        mRet.put("cell"				, sMobileno);
        mRet.put("loginid"			, sLoginid);
        mRet.put("rgsr_id"			, sLoginid);
        mRet.put("mem_no"			, sMemNo);
        mRet.put("ci"				, sCi);

        CommonUtils.printLog(mRet);

        if (null != sAuthTime && !"".equals(sAuthTime)) {
            mRet.put("safekey_srch_dt"	, sAuthTime.substring(0,8));
        }

        mRet.put("success_yn", "N");

        if (isSuccess) {
            mRet.put("success_yn", "Y");
        }

        niceCreditDAO.updateNiceSafekeyMobileIssueResult(mRet);

        if (isSuccess && !"".equals(sSafekey) && !"".equals(sMemNo)) {
            ParamUtils.addSaveParam(mRet);

            /* - 회원 테이블에 대한 세이프키 변경은 반드시 신용등급 조회결과가 적합일 경우만 한다.
            int iCnt = dlwMemDAO.updateNiceSafekey(mRet);
            if (iCnt > 0) {
                Map<String, Object> mDtpt = dlwCustDAO.getDlwMemberDtpt( sMemNo );
                custBasiDAO.insertCustBasiHstr(mDtpt);
            }
             */
        }

        CommonUtils.printLog(mRet);

        request.setAttribute("result", mRet);

    }

    /**
     * NICE Safe-Key 발급권유 SMS 발송 - 화면에서 SMS발송 커맨드 호출
     *
     * @param pmParam 검색 조건
     * @return Safe-Key 발급정보
     * @throws Exception
     */
    public Map<String, Object> sendSafeKeyIssueSms(HttpServletRequest request) throws Exception {

        Map<String, Object> mRslt 	= new HashMap<String, Object>();

        System.out.println("> sendSafeKeyIssueSms -------- 1");

        String sMemNm = "";

        String sSafeKeySrvType	= EgovProperties.getProperty("nice.safekey.server.type");

        if (null != sSafeKeySrvType && "run".equals(sSafeKeySrvType.toLowerCase()))
        {
            sMemNm 		= CommonUtils.nvls((String)request.getParameter("mem_nm"));
            System.out.println("> 4. mem_nm : " + sMemNm);
        }
        else {
            sMemNm 		= new String(CommonUtils.nvls((String)request.getParameter("mem_nm")).getBytes("8859_1"), "euc-kr");
            System.out.println("> 1. mem_nm : " + sMemNm);
        }

        String sBrthMonDay	= CommonUtils.nvls((String)request.getParameter("brth_mon_day"));
        String sCell 		= CommonUtils.nvls((String)request.getParameter("cell"));
        String sSex 		= CommonUtils.nvls((String)request.getParameter("sex"));
        String sEmplNo 		= CommonUtils.nvls((String)request.getParameter("empl_no"));
        String sMemNo		= CommonUtils.nvls((String)request.getParameter("mem_no"));
        String sLgnId 		= CommonUtils.nvls((String)request.getParameter("lgn_id"));
        String sTeamCd 		= CommonUtils.nvls((String)request.getParameter("team_cd"));

        System.out.println("> sendSafeKeyIssueSms -------- 1.1");
        System.out.println("sTeamCd : " + sTeamCd);

        mRslt.put("empl_no"	, sEmplNo);
        mRslt.put("lgnId"	, sLgnId);
        mRslt.put("team_cd"	, sTeamCd);

        sMemNm 		= sMemNm.trim();
        sBrthMonDay = sBrthMonDay.replaceAll("-", "");
        sCell 		= sCell.replaceAll("-", "");

        mRslt.put("brth_mon_day"	, sBrthMonDay);
        mRslt.put("cell"			, sCell);
        mRslt.put("sex"				, sSex);
        mRslt.put("mem_no"			, sMemNo);

        sSex 		= String.valueOf(Integer.parseInt(sSex));
        if (!"1".equals(sSex)) { // 남자
            sSex = "0";
        }

        System.out.println("sMemNm : " + sMemNm);
        System.out.println("sBrthMonDay : " + sBrthMonDay);
        System.out.println("sCell : " + sCell);
        System.out.println("sSex : " + sSex);

        sMemNm = URLEncoder.encode(sMemNm, "EUC_KR");

        NiceID.Check.CPClient niceCheck = new  NiceID.Check.CPClient();

        String sSiteCode 		= EgovProperties.getProperty("nice.safekey.weblink.sitecode");
        String sSitePassword	= EgovProperties.getProperty("nice.safekey.weblink.sitepw");

        System.out.println("sSiteCode : " + sSiteCode);
        System.out.println("sSitePassword : " + sSitePassword);

        /*
        요청번호 생성
        요청번호는 안심키인증이 끝난 후 성공/실패 결과값에 포함하여 전송됩니다.
         */
        String sRequestNumber = niceCheck.getRequestNO(sSiteCode);	// 모듈을 통한 생성
        sRequestNumber = CommonUtils.rpad(sRequestNumber, 30, "0");
        mRslt.put("req_seq", sRequestNumber);
        System.out.println("sRequestNumber : " + sRequestNumber);

        // 요청일시 생성 yyyyMMddHHmmss
        String req_datetime  = powerservice.common.util.DateUtil.getCurrDthms();
        mRslt.put("req_dthms", req_datetime);

        System.out.println("req_datetime : " + req_datetime);
        /*
        값은 총 3자리로 첫번째자리는 휴대폰인증사용여부, 두번째자리는 신용카드인증사용여부, 세번째자리는 공인인증서인증사용여부를 입력합니다.
        값은 반드시 3자리로 입력해 주셔야 됩니다.
        사용인 경우는 1,미사용인 경우는 0으로 표기합니다.
        예) 휴대폰인증사용,신용카드인증미사용,공인인증서인증사용 인 경우
            sAuthType = "101";
         */
        String sAuthType = "110";
        System.out.println("sAuthType : " + sAuthType);

        String agree1_map 	= "0000000000"; // 신용인증송부 서비스 신청 동의
        String agree2_map 	= "0000000000"; // 업체 필수 동의문
        String agree3_map 	= "0000000000"; // 업체 선택 동의문

        /*
        결과로 CI값을 결과값으로 받을지를 결정
        Y : CI를 결과값으로 받음, N : CI를 결과값으로 받지 않음

        CI는 본인확인을 정상적으로 성공한 경우만 받을 수 있습니다.
         */
        String cigubun 		= "Y";
        System.out.println("cigubun : " + cigubun);

        /*
        발송 수단 구분(값이 없는 경우 기본 SMS발송)
        sendgubun = "SMS";
        sendgubun = "EMAIL";
        */
        String sendgubun 	= "SMS";
        System.out.println("sendgubun : " + sendgubun);

        /*
        안심키에 대한 모바일 인증정보를 보낼 이메일주소
        sendgubun값이 EMAIL인 경우 필수
        예) email ="이메일아이디@이메일도메인";
        미구현 상태입니다.
        */
        String email 	= "";
        System.out.println("email : " + email);

        /*
        팝업창을 구분하는 입력값으로 제공되는 페이지에 취소버튼 즉 팝업창을 닫는 버튼이 없어집니다.
        Y : 취소버튼 있음, N : 취소버튼 없음
        */
        String popgubun 	= "N";
        System.out.println("popgubun : " + popgubun);

        URL sRequestUrl = new URL(request.getRequestURL().toString());
        System.out.println("sRequestUrl : " + sRequestUrl);

        // 안심키에 대한 모바일 인증 연결정보를 전달에 대한 결과 페이지 - SMS 발송결과표시 페이지
        String sSendResultUrl = EgovProperties.getProperty("nice.safekey.sms.result.url");
        sSendResultUrl = sSendResultUrl.replaceAll("SERVER_HOST_NAME", sRequestUrl.getHost());
        System.out.println("sSendResultUrl : " + sSendResultUrl);

        /*
        안심키 결과 데이타를 리턴 받기위해 다음예제와 같이 http부터 입력합니다.
        받을 page는 https://~, http://~ 부터 입력해 주시기 바랍니다.
        부모창에 페이지와 결과페이지에 도메인은 반드시 일치하도록 입력해 주시기 바랍니다.
        */
        String sReturnUrl 	= EgovProperties.getProperty("nice.safekey.result.sucess.url");      // 성공시 이동될 URL
        String sErrorUrl 	= EgovProperties.getProperty("nice.safekey.result.fail.url");         // 실패시 이동될 URL

        System.out.println("sSendResultUrl : " + sSendResultUrl);
        System.out.println("sReturnUrl : " + sReturnUrl);
        System.out.println("sErrorUrl : " + sErrorUrl);

        // 입력될 plain 데이타를 만든다.
        String sPlainData =	"7:REQ_SEQ" + sRequestNumber.getBytes().length + ":" + sRequestNumber +
                            "12:REQ_DATETIME" + req_datetime.getBytes().length + ":" + req_datetime +
                            "8:SITECODE" + sSiteCode.getBytes().length + ":" + sSiteCode +
                            "9:AUTH_TYPE" + sAuthType.getBytes().length + ":" + sAuthType +
                            "10:AGREE1_MAP"  + agree1_map.getBytes().length + ":" + agree1_map +
                            "10:AGREE2_MAP"  + agree2_map.getBytes().length + ":" + agree2_map +
                            "10:AGREE3_MAP"  + agree3_map.getBytes().length + ":" + agree3_map +
                            "8:USERNAME"  + sMemNm.getBytes().length + ":" + sMemNm +
                            "9:BIRTHDATE"  + sBrthMonDay.getBytes().length + ":" + sBrthMonDay +
                            "6:GENDER"  + sSex.getBytes().length + ":" + sSex +
                            "8:CI_GUBUN" + cigubun.getBytes().length + ":" + cigubun +
                            "11:POPUP_GUBUN" + popgubun.getBytes().length + ":" + popgubun +
                            "7:RTN_URL" + sReturnUrl.getBytes().length + ":" + sReturnUrl +
                            "7:ERR_URL" + sErrorUrl.getBytes().length + ":" + sErrorUrl +
                            "10:SEND_GUBUN" + sendgubun.getBytes().length + ":" + sendgubun +
                            "8:MOBILENO" + sCell.getBytes().length + ":" + sCell +
                            "5:EMAIL" + email.getBytes().length + ":" + email +
                            "15:SEND_RESULT_URL" + sSendResultUrl.getBytes().length + ":" + sSendResultUrl;

        System.out.println("sPlainData : " + sPlainData);
        mRslt.put("sPlainData", sPlainData);

        mRslt.put("sReqDatetime", req_datetime);

        String sMessage = "";
        String sEncData = "";

        int iReturn = niceCheck.fnEncode(sSiteCode, sSitePassword, sPlainData);

        if( iReturn == 0 )
        {
            sEncData = niceCheck.getCipherData();
        }
        else if( iReturn == -1)
        {
            sMessage = "암호화 시스템 에러입니다.";
        }
            else if( iReturn == -2)
        {
            sMessage = "암호화 처리오류입니다.";
        }
        else if( iReturn == -3)
        {
            sMessage = "암호화 데이터 오류입니다.";
        }
            else if( iReturn == -9)
        {
            sMessage = "입력 데이터 오류입니다.";
        }
        else
        {
            sMessage = "알수 없는 에러 입니다. iReturn : " + iReturn;
        }

        System.out.println("> sendSafeKeyIssueSms -------- 2");

        System.out.println("sEncData : " + sEncData);
        System.out.println("sMessage : " + sMessage);

        mRslt.put("sEncData", sEncData);

        System.out.println("sEncData : " + sEncData);
        System.out.println("> sendSafeKeyIssueSms -------- 3");

        mRslt.put("mem_nm", CommonUtils.nvls((String)request.getParameter("mem_nm")));

        niceCreditDAO.insertNiceSafekeySmsHis(mRslt);

        return mRslt;

    }

    /**
     * NICE Safe-Key 발급 SMS 발송 목록 조회
     *
     * @param pmParam 검색 조건
     * @return SMS 발송목록
     * @throws Exception
     */
    public List<Map<String, Object>> getMoNiceSafeKeySmsList(Map<String, Object> pmParam) throws Exception {
        UserSession sess 			= (UserSession) SessionUtils.getLoginUser();

        String sAthrCd = CommonUtils.nvls(sess.getAuthoritycd()).toUpperCase();
        if ( "AT01".equals(sAthrCd) || "CC10".equals(sAthrCd) ||
                "CC20".equals(sAthrCd) || "OP10".equals(sAthrCd) ||"OP20".equals(sAthrCd) )
        {
            pmParam.put("team_cd", "");
        } else {
            pmParam.put("team_cd", sess.getTeamcd());
        }

        return niceCreditDAO.getMoNiceSafeKeySmsList(pmParam);
    }

    /**
     * NICE Safe-Key 발급 SMS 발송 건수 조회
     *
     * @param pmParam 검색 조건
     * @return SMS 발송건수
     * @throws Exception
     */
    public int getMoNiceSafeKeySmsCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) niceCreditDAO.getMoNiceSafeKeySmsCount(pmParam);
    }

    /**
     * 고객 정보를 수정한다.
     *
     * @param pmParam 고객 정보
     * @throws Exception
     */
    public int updateSafeKeyIssueStat(Map<String, Object> pmParam) throws Exception {

        UserSession sess 	= (UserSession) SessionUtils.getLoginUser();

        int nResult = 0;
        String sReqSeq 		= "";
        String sReqDthms 	= "";

        String sAthrCd = CommonUtils.nvls(sess.getAuthoritycd()).toUpperCase();
        if ( "AT01".equals(sAthrCd) || "CC10".equals(sAthrCd) ||
                "CC20".equals(sAthrCd) || "OP10".equals(sAthrCd) ||"OP20".equals(sAthrCd) )
        {
            pmParam.put("team_cd", "");
        } else {
            pmParam.put("team_cd", sAthrCd);
        }


        Map mTmp = new HashMap<String, Object>();
        ParamUtils.addSaveParam(mTmp);
        niceCreditDAO.updateExpiredSafekeySmsHist(mTmp);

        List<Map<String, Object>> lstRslt = null;
        String sCurrDthms 	= DateUtil.getCurrDthms();

        pmParam.put("safekey_null_yn", "Y");
        lstRslt = niceCreditDAO.getMoNiceSafeKeySmsList(pmParam);

        //log.debug(">lstRslt.size() : " + lstRslt.size());

        System.out.println(">lstRslt.size() : " + lstRslt.size());


        if (null != lstRslt && lstRslt.size() > 0) {
            for (int i=0; i<lstRslt.size(); ++i) {

                //log.debug("-------------- <" + i + "> ------------");

                System.out.println("-------------- <" + i + "> ------------");

                Map<String, Object> mapThis = lstRslt.get(i);
                sReqSeq = mapThis.get("req_seq").toString();
                sReqDthms = mapThis.get("req_dthms").toString();

                //log.debug("sReqSeq : " + sReqSeq);
                //log.debug("sReqDthms : " + sReqDthms);

                System.out.println("sReqSeq : " + sReqSeq);
                System.out.println("sReqDthms : " + sReqDthms);

                // SMS 발송 후 사용자가 액션을 행했을 경우 결과 가져오기
                Map<String, Object> mapResult = NiceCreditUtil.getSafeKeySmsIssueResult(mapThis);

                if (null != mapResult) {
                    ParamUtils.addSaveParam(mapResult);
                    niceCreditDAO.updateSafeKeyIssueStat(mapResult);
                    continue;
                } else {
                    //log.debug("해당안됨 -- 1");
                    System.out.println("해당안됨 -- 1");
                }

                // SMS 발송 후 사용자가 액션을 행했을 경우 결과 가져오기
//    			mapResult = NiceCreditUtil.getNiceSafeKey(mapThis);
//    			if (null != mapResult && null != mapResult.get("safekey") && !"".equals(mapResult.get("safekey").toString()) ) {
//    				ParamUtils.addSaveParam(mapResult);
//
//    				mapResult.put("req_seq", sReqSeq);
//    				mapResult.put("req_dthms", sReqDthms);
//    				mapResult.put("issue_dthms", sCurrDthms);
//    				mapResult.put("return_code", "0000");
//    				niceCreditDAO.updateSafeKeyIssueStat(mapResult);
//    				continue;
//    			} else {
//    				log.debug("해당안됨 -- 2");
//    			}

            }

        }

        return nResult;
    }

    /**
     * NICE Safe-Key 발급권유 SMS 발송결과
     *
     * @param pmParam 검색 조건
     * @return 고객 정보
     * @throws Exception
     */
    public int updateMemberNiceSafekey(Map<String, Object> pmParam) throws Exception {

        String sMemNo 		= CommonUtils.nvls((String)pmParam.get("mem_no"));
        String sFitnessYn 	= CommonUtils.nvls((String)pmParam.get("acuon_fitness_yn"));
        String sSafekey 	= CommonUtils.nvls((String)pmParam.get("safekey"));

        if ( !"Y".equals(sFitnessYn) || sSafekey.length() < 1 ) {
            // pmParam.put("safekey", ""); 세이프키는 남겨두어야 함
            pmParam.put("safekey_srch_dt", "");
            pmParam.put("acuon_fitness_yn", "N");
        }

        ParamUtils.addSaveParam(pmParam);
        int iCnt = dlwMemDAO.updateNiceSafekey(pmParam);

        if (iCnt > 0) {
            Map<String, Object> mDtpt = dlwCustDAO.getDlwMemberDtpt( sMemNo );
            custBasiDAO.insertCustBasiHstr(mDtpt);
        }

        return iCnt;
    }

    public void insertJcyTest1() throws Exception {
        Map<String, Object> pmParam = null;
        int seq = 0;
        List<Map<String, Object>> lstRows = new ArrayList<Map<String, Object>>();

        for (int i=0; i<1000; ++i) {
            pmParam = new HashMap<String, Object>();

            seq = seq + 1;
            pmParam.put("seq", String.valueOf(seq));
            lstRows.add(pmParam);

            if (lstRows.size() >= 100) {
                dlwMemDAO.insertJcyTest1(lstRows);
                lstRows.clear();
                Thread.sleep(1000);
            }
        }

        if (lstRows.size() > 0) {
            dlwMemDAO.insertJcyTest1(lstRows);
        }
    }

    /**
     * NICE Safe-Key 발급 SMS 최종 발송일자
     *
     * @param pmParam 검색 조건
     * @return SMS 발송목록
     * @throws Exception
     */
    public List<Map<String, Object>> getSmsSendDt(Map<String, ?> pmParam) throws Exception {

        List<Map<String, Object>> lstCurrDthms = niceCreditDAO.getCurrDbDthms();
        //log.debug("----------------- 1");
        System.out.println("----------------- 1");
        Map<String, Object> mRowDthms = lstCurrDthms.get(0);
        mRowDthms.put("safekey_sms_send_dt", "");
        mRowDthms.put("ci_val", "");

        List<Map<String, Object>> lst = niceCreditDAO.getSmsSendDt(pmParam);
        //log.debug("----------------- 2");
        System.out.println("----------------- 2");

        if (null != lst && lst.size() > 0) {
            Map<String, Object> mRow = lst.get(0);
            //log.debug("----------------- 3");
            System.out.println("----------------- 3");
            mRowDthms.put("safekey_sms_send_dt", mRow.get("safekey_sms_send_dt"));
            mRowDthms.put("ci_val", mRow.get("ci_val"));
        }
        //log.debug("----------------- 4");
        System.out.println("----------------- 4");

        return lstCurrDthms;
    }

    /**
     * NICE Safe-Key SMS 내역을 조회한다.
     *
     * @param pmParam 검색 조건
     * @return NICE Safe-Key SMS 내역조회
     * @throws Exception
     */
    public Map<String, Object> getNiceSafeSMSInfo(Map<String, ?> pmParam) throws Exception {

        System.out.println(">>>>>>>>>>> NICE Safe-Key SMS 내역조회");

        Map<String, Object> hmResult = new HashMap<String, Object>();

        List<Map<String, Object>> lstRet = niceCreditDAO.getSafekeKeyListFromHist(pmParam);
        if (null != lstRet && lstRet.size() > 0) {
            Map<String, Object> mRow = lstRet.get(0);
            hmResult.put("safekey", CommonUtils.nvls((String)mRow.get("safekey")));
            hmResult.put("safekey_sms_send_dt", CommonUtils.nvls((String)mRow.get("safekey_sms_send_dt")));
            hmResult.put("ci_val", CommonUtils.nvls((String)mRow.get("ci_val")));
        }

        return hmResult;
    }
}
