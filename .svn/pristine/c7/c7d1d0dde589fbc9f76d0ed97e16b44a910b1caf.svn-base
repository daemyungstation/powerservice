/*
 * (@)# ConsService.java
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
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

package powerservice.business.cns.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import powerservice.business.cns.service.ConsService;
import powerservice.business.dlw.service.impl.DlwConsDAO;
import powerservice.business.dlw.service.impl.DlwConsProdDAO;
import powerservice.business.dlw.service.impl.DlwCustDAO;
import powerservice.business.kms.service.impl.ConsTypDAO;
import powerservice.common.util.CommonUtils;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 상담 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID Cons
 */
@Service
public class ConsServiceImpl extends EgovAbstractServiceImpl implements ConsService {

    @Resource
    public ConsDAO consDAO;

    @Resource
    public ConsHstrDAO consHstrDAO;

    @Resource
    public DlwConsDAO dlwConsDAO;

    @Resource
    public DlwConsProdDAO dlwConsProdDAO;

    @Resource
    public ConsTypDAO consTypDAO;

    @Resource
    public DlwCustDAO dlwCustDAO;

    /**
     * 상담 정보를 등록한다.
     *
     * @param pmParam 상담 정보
     * @throws Exception
     */
    public String insertCons(Map<String, Object> pmParam) throws Exception {
        String sKey = "";
        String sSeq = "";
        String sFistCallIncoYn = StringUtils.defaultString((String)pmParam.get("fist_call_inco_yn")); // 최초 전화 인입시 상담저장 여부
        int nResult = 0;

        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sMstRegDm = "";	// 대명 상담MNG 등록일시 yyyy-MM-dd HH:mm:ss.SSS
        String sDtptRegDm = ""; // 대명 상담DTL 등록일시 yyyy-MM-dd HH:mm:ss.SSS
        String sMstKey = "";	// 대명 상담MNG 키
        String sDtptKey = "";	// 대명 상담DTL 키
        String sGubn = "";		// 상담상세 구분-상담유형별

        String sConsMemoTit = StringUtils.defaultString((String)pmParam.get("cons_memo_tit")); 		// 제목
        String sConsMemoCntn = StringUtils.defaultString((String)pmParam.get("cons_memo_cntn")); 	// 내용
        String sCnslCd = StringUtils.defaultString((String)pmParam.get("cnsl_cd")); // COM_CD_GRP 테이블
        if ("".equals(sCnslCd)) {
            pmParam.put("cnsl_cd", "01"); // 기타
        }

        if ("".equals(sConsMemoTit)) {
            pmParam.put("cons_memo_tit", "인우상담 정보");
        }

        // 상담테이블 MEMBER / MEM_PROD_ACCNT 정보
        Map<String, Object> mCust = dlwCustDAO.getCustBasiConsInfo(pmParam);
        pmParam.put("mem_nm", 		mCust.get("mem_nm"));
        pmParam.put("sex", 			mCust.get("sex"));
        pmParam.put("brth_mon_day", mCust.get("brth_mon_day"));
        pmParam.put("ci_val", 		mCust.get("ci_val"));
        pmParam.put("idn_no", 		mCust.get("idn_no"));
        pmParam.put("home_tel", 	mCust.get("home_tel"));
        pmParam.put("cell", 		mCust.get("cell"));
        pmParam.put("wrpl_tel", 	mCust.get("wrpl_tel"));
        pmParam.put("home_zip", 	mCust.get("home_zip"));
        pmParam.put("home_addr", 	mCust.get("home_addr"));
        pmParam.put("home_addr2", 	mCust.get("home_addr2"));
        pmParam.put("credit_rating", mCust.get("credit_rating"));
        pmParam.put("crdt_mng_no", 	mCust.get("crdt_mng_no"));
        pmParam.put("email", 		mCust.get("email"));
        pmParam.put("emple_no", 	mCust.get("emple_no"));
        pmParam.put("emple_nm", 	mCust.get("emple_nm"));
        pmParam.put("join_dt", 		mCust.get("join_dt"));
        pmParam.put("dept_cd", 		mCust.get("dept_cd"));
        pmParam.put("note", 		mCust.get("note"));

        System.out.println("-------------------------------->> ");
        CommonUtils.printLog(pmParam);

        // 콜 인입시에는 대명DB에 상담정보를 남기지 않음
        /* 2016/11/22 엔컴이력 INSERT 제외
        if (!"Y".equals(sFistCallIncoYn)) {
            // 상담유형 - 엔컴CNSL_DTL GUBN
            sGubn = StringUtils.defaultString((String) consTypDAO.getConsTypGubn(pmParam));
            if ("".equals(sGubn)) {
                sGubn = "80";
                pmParam.put("gubn", "80"); // COM_CD_GRP 테이블 기타
            } else {
                pmParam.put("gubn", sGubn);
            }

            // 대명 상담 등록 시간 설정
            sMstRegDm = sdfDate.format(new Date());
            pmParam.put("mst_reg_dm", sMstRegDm);
            pmParam.put("mst_upd_dm", sMstRegDm);

            nResult = 1; //dlwConsDAO.insertCons(pmParam);

            sSeq = (String) pmParam.get("skey");
            String tmpStr[] = sSeq.split("\\|");
            pmParam.put("seq", tmpStr[0]);
            pmParam.put("cnsl_seq", tmpStr[1]);

            if (nResult > 0) {
                // 대명 상담상세 등록 시간 설정
                sDtptRegDm = sdfDate.format(new Date());
                pmParam.put("dtpt_reg_dm", sDtptRegDm);
                pmParam.put("dtpt_upd_dm", sDtptRegDm);

                // 엔컴 상담내역 등록 제거(2016/11/21)
                //dlwConsDAO.insertConsDtpt(pmParam);
            }
        }
        */

        // 신규등록시 대명키 생성
        // mem_no seq clsn_seq accnt_no reg_man reg_dm
        // mem_no seq clsn_seq gubn     reg_man reg_dm
        if (!"".equals(sSeq)) { // 대명 키 존재
            sMstKey = pmParam.get("mem_no")+"^"+pmParam.get("seq")+"^"+pmParam.get("cnsl_seq")+"^"+pmParam.get("accnt_no")+"^"+pmParam.get("rgsr_id")+"^"+sMstRegDm;
            sDtptKey = pmParam.get("mem_no")+"^"+pmParam.get("seq")+"^"+pmParam.get("cnsl_seq")+"^"+sGubn+"^"+pmParam.get("rgsr_id")+"^"+sDtptRegDm;
        }
        pmParam.put("mst_key", sMstKey);
        pmParam.put("dtpt_key", sDtptKey);

        if (!"".equals(sConsMemoTit)) {
            pmParam.put("cons_memo_cntn", sConsMemoTit + "\n" + sConsMemoCntn);
        }

        nResult = consDAO.insertCons(pmParam);

        if (nResult > 0) {
            sKey = (String) pmParam.get("consno");
            consHstrDAO.insertConsHstr(pmParam);
        }

        return sKey;
    }

    /**
     * 상담 정보를 수정한다.
     *
     * @param pmParam 상담 정보
     * @throws Exception
     */
    public int updateCons(Map<String, Object> pmParam) throws Exception {
        int nResult = 0;
        String sSeq = "";
        String sMstKey = StringUtils.defaultString((String)pmParam.get("mst_key"));		// 대명 상담MNG 키
        String sDtptKey = StringUtils.defaultString((String)pmParam.get("dtpt_key"));	// 대명 상담DTL 키

        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String sMstRegDm = "";	// 대명 상담MNG 등록일시 yyyy-MM-dd HH:mm:ss.SSS
        String sDtptRegDm = ""; // 대명 상담DTL 등록일시 yyyy-MM-dd HH:mm:ss.SSS
        String sMemNo = StringUtils.defaultString((String)pmParam.get("mem_no"));
        String sTmpKey[] = new String[6];		// mem_no seq clsn_seq accnt_no reg_man reg_dm
        String sTmpDtptKey[] = new String[6];	// mem_no seq clsn_seq gubn     reg_man reg_dm

        String sConsMemoTit = StringUtils.defaultString((String)pmParam.get("cons_memo_tit")); 		// 제목
        String sConsMemoCntn = StringUtils.defaultString((String)pmParam.get("cons_memo_cntn")); 	// 내용
        String sCnslCd = StringUtils.defaultString((String)pmParam.get("cnsl_cd")); // COM_CD_GRP 테이블

        if ("".equals(sConsMemoTit)) {
            pmParam.put("cons_memo_tit", "인우상담 정보");
        }
        if ("".equals(sCnslCd)) {
            pmParam.put("cnsl_cd", "01"); // 기타
        }

        // 상담테이블 MEMBER / MEM_PROD_ACCNT 정보
        Map<String, Object> mCust = dlwCustDAO.getCustBasiConsInfo(pmParam);
        pmParam.put("mem_nm", 		mCust.get("mem_nm"));
        pmParam.put("sex", 			mCust.get("sex"));
        pmParam.put("brth_mon_day", mCust.get("brth_mon_day"));
        pmParam.put("ci_val", 		mCust.get("ci_val"));
        pmParam.put("idn_no", 		mCust.get("idn_no"));
        pmParam.put("home_tel", 	mCust.get("home_tel"));
        pmParam.put("cell", 		mCust.get("cell"));
        pmParam.put("wrpl_tel", 	mCust.get("wrpl_tel"));
        pmParam.put("home_zip", 	mCust.get("home_zip"));
        pmParam.put("home_addr", 	mCust.get("home_addr"));
        pmParam.put("home_addr2", 	mCust.get("home_addr2"));
        pmParam.put("credit_rating", mCust.get("credit_rating"));
        pmParam.put("crdt_mng_no", 	mCust.get("crdt_mng_no"));
        pmParam.put("email", 		mCust.get("email"));
        pmParam.put("emple_no", 	mCust.get("emple_no"));
        pmParam.put("emple_nm", 	mCust.get("emple_nm"));
        pmParam.put("join_dt", 		mCust.get("join_dt"));
        pmParam.put("dept_cd", 		mCust.get("dept_cd"));
        pmParam.put("note", 		mCust.get("note"));

        // 상담유형 - 엔컴CNSL_DTL GUBN
        String sGubn = StringUtils.defaultString((String) consTypDAO.getConsTypGubn(pmParam));
        if ("".equals(sGubn)) {
            sGubn = "80";
            pmParam.put("gubn", "80"); // COM_CD_GRP 테이블 기타
        } else {
            pmParam.put("gubn", sGubn);
        }
/*  엔컴상담내역은 컨트롤 안함!!!!!20161130
        if ("".equals(sMstKey)) {
            // 대명 상담 등록 시간 설정
            sMstRegDm = sdfDate.format(new Date());
            pmParam.put("mst_reg_dm", sMstRegDm);
            pmParam.put("mst_upd_dm", sMstRegDm);

            nResult = dlwConsDAO.insertCons(pmParam);

            sSeq = (String) pmParam.get("skey");
            String tmpStr[] = sSeq.split("\\|");
            pmParam.put("seq", tmpStr[0]);
            pmParam.put("cnsl_seq", tmpStr[1]);

            if (nResult > 0) {
                // 대명 상담상세 등록 시간 설정
                sDtptRegDm = sdfDate.format(new Date());
                pmParam.put("dtpt_reg_dm", sDtptRegDm);
                pmParam.put("dtpt_upd_dm", sDtptRegDm);

                // 엔컴 상담내역 등록 제거(2016/11/21)
                dlwConsDAO.insertConsDtpt(pmParam);
            }
        } else {
            // 1, 이전 key와 현재 key 비교
            // 1-1. mem_no 변경시 / mem_no accnt_no 변경시
            // => delete insert
            // 1-2. accnt_no 변경시
            // => update

            // 2. 이전 key와 현재 key 비교
            // 2.1. mem_no 변경시 / mem_no accnt_no 변경시
            // => delete insert
            // 2.2. gubn 변경시
            // => update
            sTmpKey = sMstKey.split("\\^");
            sTmpDtptKey = sDtptKey.split("\\^");
            sMstRegDm = sTmpKey[5];
            sDtptRegDm = sTmpDtptKey[5];

            if (sTmpKey[0].equals(sMemNo)) {
                // accnt_no gubn update
                pmParam.put("seq", sTmpKey[1]);
                pmParam.put("cnsl_seq", sTmpKey[2]);

                nResult = dlwConsDAO.updateCons(pmParam);
                if (nResult > 0) {
                    dlwConsDAO.updateConsDtpt(pmParam);
                }
            } else {
                // delete insert
                pmParam.put("old_mem_no", sTmpKey[0]);
                pmParam.put("old_seq", sTmpKey[1]);
                pmParam.put("old_cnsl_seq", sTmpKey[2]);
                pmParam.put("old_gubn", sTmpDtptKey[3]);
                nResult = dlwConsDAO.deleteCons(pmParam);		// 대명 상담 삭제

                pmParam.put("rgsr_id", sTmpKey[4]);
                pmParam.put("mst_reg_dm", sMstRegDm);

                if (nResult > 0) {
                    nResult = 1; //dlwConsDAO.insertCons(pmParam);	// 대명 상담MNG 등록

                    sSeq = (String) pmParam.get("skey");
                    String tmpStr[] = sSeq.split("\\|");
                    pmParam.put("seq", tmpStr[0]);
                    pmParam.put("cnsl_seq", tmpStr[1]);

                    if (nResult > 0) {
                        // 대명 상담상세 등록 시간 설정
                        sDtptRegDm = sdfDate.format(new Date());
                        pmParam.put("dtpt_reg_dm", sDtptRegDm);

                     // 엔컴 상담내역 등록 제거(2016/11/21)
                        //dlwConsDAO.insertConsDtpt(pmParam);		// 대명 상담DTL 등록
                    }
                }

            }
        }
        */

        // 신규등록시 대명키 생성
        // mem_no seq clsn_seq accnt_no reg_man reg_dm
        // mem_no seq clsn_seq gubn     reg_man reg_dm
        sMstKey = pmParam.get("mem_no")+"^"+pmParam.get("seq")+"^"+pmParam.get("cnsl_seq")+"^"+pmParam.get("accnt_no")+"^"+pmParam.get("rgsr_id")+"^"+sMstRegDm;
        sDtptKey = pmParam.get("mem_no")+"^"+pmParam.get("seq")+"^"+pmParam.get("cnsl_seq")+"^"+sGubn+"^"+pmParam.get("rgsr_id")+"^"+sDtptRegDm;
        pmParam.put("mst_key", sMstKey);
        pmParam.put("dtpt_key", sDtptKey);

        if (!"".equals(sConsMemoTit)) {
            pmParam.put("cons_memo_cntn", sConsMemoTit + "\n" + sConsMemoCntn);
        }

        nResult = consDAO.updateCons(pmParam);

        if (nResult > 0) {
            consHstrDAO.insertConsHstr(pmParam);
        }

        return nResult;
    }

    /**
     * 상담 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 상담 정보의 검색 건수
     * @throws Exception
     */
    public int getConsCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) consDAO.getConsCount(pmParam);
    }

    /**
     * 상담 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상담 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getConsList(Map<String, ?> pmParam) throws Exception {
        return consDAO.getConsList(pmParam);
    }
    
    /**
     * 상담 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 상담 정보의 검색 건수
     * @throws Exception
     */
    public int getConsCount2(Map<String, ?> pmParam) throws Exception {
        return (Integer) consDAO.getConsCount2(pmParam);
    }

    /**
     * 상담 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상담 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getConsList2(Map<String, ?> pmParam) throws Exception {
        return consDAO.getConsList2(pmParam);
    }

    /**
     * 상담 그룹 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상담 그룹 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getConsGroup(String psConsnoGropNo) throws Exception {
        Map<String, Object> mParam = new HashMap<String, Object>();
        mParam.put("consno_grop_no", psConsnoGropNo);
        return consDAO.getConsGroup(mParam);
    }

    /**
     * 상담 정보를 검색한다.
     *
     * @param pmParam 상담번호
     * @return 상담 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getCons(Map<String, ?> pmParam) throws Exception {
        return consDAO.getCons(pmParam);
    }


    /**
     * 엔컴 상담 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 엔컴 상담 정보의 검색 건수
     * @throws Exception
     */
    public int getDlwConsCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) dlwConsDAO.getDlwConsCount(pmParam);
    }

    /**
     * 엔컴 상담 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 엔컴 상담 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwConsList(Map<String, ?> pmParam) throws Exception {
        return dlwConsDAO.getDlwConsList(pmParam);
    }


    /**
     * 센터현황 건수를 조회한다
     *
     * @param pmParam 검색조건
     * @return 센터현황 건수
     * @throws Exception
     */
    public Map<String, Object> getConsChartCount(Map<String, ?> pmParam) throws Exception {
        return consDAO.getConsChartCount(pmParam);
    }


    /**
     * 센터현황 차트 데이터를 조회한다
     *
     * @param pmParam 검색조건
     * @return 센터현황 차트 데이터
     * @throws Exception
     */
    public List<Map<String, Object>> getConsChartList(Map<String, ?> pmParam) throws Exception {
        String sSrchTyp = (String) pmParam.get("srch_typ");
        if ("week".equals(sSrchTyp)) {
            return consDAO.getConsChartWeeklyList(pmParam);
        } else if ("month".equals(sSrchTyp)) {
            return consDAO.getConsChartMonthlyList(pmParam);
        } else if ("2weeks".equals(sSrchTyp)) {
            return consDAO.getConsChart2WeeksList(pmParam);
        } else {
            return consDAO.getConsChartDailyList(pmParam);
        }
    }


    /**
     * 관리자 메인화면 > 우수상담사 TOP5 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상담 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getConsTop5List(Map<String, ?> pmParam) throws Exception {
        return consDAO.getConsTop5List(pmParam);
    }


    /**
     * 상담사 상담현황 > 상담사별 당일 상담건수를 조회한다
     *
     * @return 상담사별 상담건수
     * @throws Exception
     */
    public List<Map<String, Object>> getTodayConsCount() throws Exception {
        return consDAO.getTodayConsCount();
    }

    /**
     * 상담사 상담현황 > 상담사별 당월 상담건수를 조회한다
     *
     * @return 상담사별 상담건수
     * @throws Exception
     */
    public List<Map<String, Object>> getThisMonthConsCount() throws Exception {
        return consDAO.getThisMonthConsCount();
    }

    /**
     * 인우 상담내역 변경(TB_CONS)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateYdysCons(Map<String, ?> pmParam) throws Exception {
        return consDAO.updateYdysCons(pmParam);
    }

    /**
     * 인우 상담이력 변경(TB_CONS_HSTR)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateYdysConsHstr(Map<String, ?> pmParam) throws Exception {
        return consDAO.updateYdysConsHstr(pmParam);
    }
    
    /**
     * 홈페이지 회원변경 이력 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 홈페이지 회원변경 정보의 검색 건수
     * @throws Exception
     */
    public int getDlwlifeweyCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) dlwConsDAO.getDlwlifeweyCount(pmParam);
    }
    
    /**
     * 홈페이지 회원변경 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 홈페이지 회원변경 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwlifeweyList(Map<String, ?> pmParam) throws Exception {
        return dlwConsDAO.getDlwlifeweyList(pmParam);
    }

}
