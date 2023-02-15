/*
 * (@)# DlwOnlnCustServiceImpl.java
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/04/20
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

package powerservice.business.onln.service.impl;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.stereotype.Service;

import powerservice.business.cns.service.impl.ConsDAO;
import powerservice.business.cns.service.impl.ConsHstrDAO;
import powerservice.business.cns.service.impl.PersInfoPcusCnsnDAO;
import powerservice.business.dlw.service.DlwNewTypeMainConsService;
import powerservice.business.dlw.service.impl.DlwCdDAO;
import powerservice.business.dlw.service.impl.DlwConsDAO;
import powerservice.business.dlw.service.impl.DlwConsProdDAO;
import powerservice.business.dlw.service.impl.DlwCustDAO;
import powerservice.business.onln.service.DlwOnlnCustService;
import powerservice.business.sys.service.BasVlService;
import powerservice.common.util.CommonUtils;
import egovframework.com.utl.sim.service.SeedCipher;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 온라인 회원 정보를 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/04/20
 * @프로그램ID DlwOnlnCust
 */
@Service
public class DlwOnlnCustServiceImpl extends EgovAbstractServiceImpl implements DlwOnlnCustService {

    @Resource
    public DlwOnlnCustDAO dlwOnlnCustDAO;

    @Resource
    public DlwCustDAO dlwCustDAO;

    @Resource
    public DlwConsProdDAO dlwConsProdDAO;

    @Resource
    public PersInfoPcusCnsnDAO persInfoPcusCnsnDAO;

    @Resource
    public BasVlService basVlService;

    @Resource
    public DlwConsDAO dlwConsDAO;

    @Resource
    public ConsDAO consDAO;

    @Resource
    public ConsHstrDAO consHstrDAO;

    @Resource
    public DlwCdDAO dlwCdDAO;
    
    @Resource
	private DlwNewTypeMainConsService dlwNewTypeMainConsService;

    /**
     * 온라인회원 가입자 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 온라인 회원 정보의 검색 건수
     * @throws Exception
     */
    public int getDlwOnlnCustJnerCount(Map<String, ?> pmParam) throws Exception {
        return dlwOnlnCustDAO.getDlwOnlnCustJnerCount(pmParam);
    }

    /**
     * 온라인회원 가입자 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 온라인 회원 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwOnlnCustJnerList(Map<String, ?> pmParam) throws Exception {
        return dlwOnlnCustDAO.getDlwOnlnCustJnerList(pmParam);
    }

    /**
     * 온라인회원 가입자 정보를 변환한다.
     *
     * @param pmParam 검색 조건
     * @return 온라인회원 가입자 정보
     * @throws Exception
     */
    public String trntMemInfo(Map<String, Object> pmParam) throws Exception {
        String sMemNm = StringUtils.defaultString((String) pmParam.get("mem_nm"));
        String sCiVal = StringUtils.defaultString((String) pmParam.get("ci_val"));
        String sDi = StringUtils.defaultString((String) pmParam.get("di"));
        String sPrdctCsGb = StringUtils.defaultString((String) pmParam.get("prdct_cs_gb"));
        String sProdCd = StringUtils.defaultString((String) pmParam.get("prod_cd"));
        String sProdModelKind = StringUtils.defaultString((String) pmParam.get("prod_model_kind"));
        String sProdModelNm = StringUtils.defaultString((String) pmParam.get("prod_model_nm"));
        String sPayMthd = StringUtils.defaultString((String) pmParam.get("pay_mthd"));
        String sEmpleNo = StringUtils.defaultString((String) pmParam.get("emple_no"));
        String sCell = StringUtils.defaultString((String) pmParam.get("cell"));
        sCell = CommonUtils.convertPhoneString(sCell);
        pmParam.put("cell", sCell);

        String sChatYn = StringUtils.defaultString((String) pmParam.get("chat_pcus_cnsn_yn"));
        String sTelYn = StringUtils.defaultString((String) pmParam.get("tel_pcus_cnsn_yn"));
        String sDmYn = StringUtils.defaultString((String) pmParam.get("dm_pcus_cnsn_yn"));
        String sEmilYn = StringUtils.defaultString((String) pmParam.get("emil_pcus_cnsn_yn"));

        String sMarktAgrYn = StringUtils.defaultString((String) pmParam.get("markt_agr_yn"));	// 마케팅여부 추가

        String sPayMthdNm = StringUtils.defaultString((String) pmParam.get("pay_mthd_nm"));
        String sDepr = StringUtils.defaultString((String) pmParam.get("depr"));
        String sBankCd = StringUtils.defaultString((String) pmParam.get("bank_cd"));
        String sBankAccntNo = StringUtils.defaultString((String) pmParam.get("bank_accnt_no"));
        String sExpYr = StringUtils.defaultString((String) pmParam.get("exp_yr"));
        String sExpMm = StringUtils.defaultString((String) pmParam.get("exp_mm"));
        String sTranDt = StringUtils.defaultString((String) pmParam.get("tran_dt"));

        Map<String,Object> mModelMst = new HashMap<>();
        String sOnlnEmpNo = "";

        try {
            if ("".equals(sEmpleNo)) { // 담당사원정보가 없을 경우
                sOnlnEmpNo = basVlService.getBasVlAsString("onln_emp_no");
                pmParam.put("emple_no", sOnlnEmpNo);  //온라인가입 (2012050025)
                pmParam.put("bef_emple_cd", sOnlnEmpNo);
            } else {
                pmParam.put("bef_emple_cd", sEmpleNo);
            }

            if ("".equals(sProdModelKind)) {
                mModelMst = dlwConsProdDAO.getProdModelMst(pmParam);
                if (null != mModelMst) {
                    sProdModelKind = CommonUtils.nvls((String)mModelMst.get("prod_kind"));
                    pmParam.put("prod_model_kind", sProdModelKind);
                }
            }

            String sErpMemNm = "";
            int nResult = 0;
            String sAccntNo = "";


            pmParam.put("del_fg", "N");
            if ("".equals(sPayMthd)) {
                pmParam.put("pay_mthd", "04");
            }
            pmParam.put("compt_dt", "");
            pmParam.put("org_join_gunsu", "0");
            pmParam.put("before_dc_cnt", "0");
            //pmParam.put("join_cl", "02"); // 2016.10.06 온라인에서 정보가져와 주석처리함
            pmParam.put("idn_no_chk_not_use", "N");

            pmParam.put("ja_subscrpt_yn", "N");
            if ("".equals(sMarktAgrYn)) sMarktAgrYn ="N";

            pmParam.put("markt_agr_yn", sMarktAgrYn);

            if (!"".equals(sCiVal) || !"".equals(sDi)) {
                sErpMemNm = StringUtils.defaultString((String) dlwCustDAO.getErpMemNm(pmParam));
            }


            if (!"".equals(sMemNm) && !"".equals(sErpMemNm) && !sMemNm.equals(sErpMemNm)) {
                return "nameErr";
            }

            //2016.10.06 고유번호 확인 기준 변경 [생년월일,, 휴대폰번호, 이름, 성별]
            //String sMemNo = StringUtils.defaultString((String) dlwOnlnCustDAO.getOnlnMemNo(pmParam));
            String sMemNo = "";
            String sNote = "";
            String sBankNm = "";
            Map<String, Object> mMemInfo = dlwCustDAO.getMemInfoByOnln(pmParam);
            if (mMemInfo != null && mMemInfo.size() > 0) {
                sMemNo = StringUtils.defaultString((String)mMemInfo.get("mem_no"));
                sNote = StringUtils.defaultString((String)mMemInfo.get("note"));

                pmParam.put("mem_no", sMemNo);
            }

            //은행명 조회
            pmParam.put("grp_cd", pmParam.get("grp_cd"));
            pmParam.put("cd", pmParam.get("bank_cd"));
            List<Map<String, Object>> cdList = dlwCdDAO.getDlwCdList(pmParam);
            if (cdList != null && cdList.size() > 0) {
                sBankNm = (String)cdList.get(0).get("cd_nm");
            }

            sNote += "\n[온라인 정보]";
            sNote += "\n- 결제방법:" + sPayMthdNm;
            sNote += "\n- 회원명(예금주):" + sDepr;
            sNote += "\n- 은행:" + sBankNm + "("+ sBankCd +")";
            sNote += "\n- 카드번호(계좌번호):" + sBankAccntNo;
            sNote += "\n- 카드유효기간:" + sExpYr + sExpMm;
            sNote += "\n- 이체일:" + sTranDt;

            pmParam.put("note", sNote);

            if ("".equals(sCiVal)) {
                pmParam.put("ci_val", "di");
            }

            if ("".equals(sMemNo)) {
                dlwCustDAO.insertMember(pmParam); 			// 대명회원정보 등록
            } else {
                dlwCustDAO.updateMember(pmParam); 			// 대명회원정보 수정
            }

            // 채녈동의여부 수정
            if ("".equals(sChatYn)) sChatYn = "N";
            if ("".equals(sTelYn)) sTelYn = "N";
            if ("".equals(sDmYn)) sDmYn = "N";
            if ("".equals(sEmilYn)) sEmilYn = "N";

            pmParam.put("chat_pcus_cnsn_yn", sChatYn);
            pmParam.put("tel_pcus_cnsn_yn", sTelYn);
            pmParam.put("dm_pcus_cnsn_yn", sDmYn);
            pmParam.put("emil_pcus_cnsn_yn", sEmilYn);

            persInfoPcusCnsnDAO.mergePersInfoPcusCnsn(pmParam);

            persInfoPcusCnsnDAO.insertPersInfoPcusCnsnHstr(pmParam);

            pmParam.put("membership", dlwConsProdDAO.getCardCode(sProdCd));   // 카드코드 조회


            String sCnt = CommonUtils.nvls((String)pmParam.get("prdct_acnt"));
            if ("".equals(sCnt)) {
                sCnt = "1";
            }

            int iPrdctAcnt = Integer.parseInt(sCnt);

            for (int i=0; i<iPrdctAcnt; ++i) {
                sAccntNo = dlwConsProdDAO.createAccntNo(sProdCd);
                pmParam.put("accnt_no", sAccntNo);

              //모델명 있을 경우 모델코드 조회 추가
                if (!"".equals(sProdCd) && !"".equals(sProdModelKind) && !"".equals(sProdModelNm)) {
                    pmParam.put("prod_model_cd", dlwConsProdDAO.getProdModelCd(pmParam));
                }

                nResult = dlwConsProdDAO.insertMemProdAccnt(pmParam); 	// MEM_PROD_ACCCNT
                if (nResult < 0) {
                    return "fail";
                }
            }

            if ("normal".equals(sPrdctCsGb)) {
                dlwOnlnCustDAO.updateMemMstUnqNo(pmParam);	// unq_no(mem_no) update
                dlwOnlnCustDAO.updateMemChngPtcYn(pmParam);	// 상태값 변경
            }

            dlwOnlnCustDAO.updateOnlnProdMst(pmParam);		// 상태값 변경

            dlwOnlnCustDAO.insertOnlnProdLog(pmParam);		// 로그 저장

            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }

    /**
     * 온라인 회원 수정
     * 2016-07-11 추가
     *
     * @param 없음
     * @return 팀 리스트
     * @throws Exception
     */
    public void updateDlwOnlnCust() throws Exception {
        Map<String, Object> mParam = new HashMap<String, Object>();

        String sAutoConsTyp3Cd = basVlService.getBasVlAsString("auto_cons_typ3_cd");
        if ("".equals(sAutoConsTyp3Cd)) {
            sAutoConsTyp3Cd = "CT01010201";
        }
        String sConsTyp1Cd = sAutoConsTyp3Cd.substring(0, 6) + "0000";
        String sConsTyp2Cd = sAutoConsTyp3Cd.substring(0, 8) + "00";

        mParam.put("cons_typ1_cd", sConsTyp1Cd);
        mParam.put("cons_typ2_cd", sConsTyp2Cd);
        mParam.put("cons_typ3_cd", sAutoConsTyp3Cd);

        dlwOnlnCustDAO.getSqlSession().select("DlwOnlnCustMap.getScdlOnlnCustList", mParam, new dlwOnlnCustRowHandler(mParam));
    }

    protected class dlwOnlnCustRowHandler implements ResultHandler {
        private Map<String, Object> gmParam = null;

        public dlwOnlnCustRowHandler(Map<String, Object> mParam) {
            super();

            this.gmParam = new HashMap<String, Object>();

            if (mParam != null) {
                Set<String> oKeySet = mParam.keySet();
                Iterator<String> oIterator = oKeySet.iterator();
                while (oIterator.hasNext()) {
                    String sKey = oIterator.next();
                    this.gmParam.put(sKey, mParam.get(sKey));
                }
            }
        }

        public void handleResult(ResultContext context) {
            @SuppressWarnings("unchecked")
            Map<String, Object> rslObj = (Map<String, Object>)context.getResultObject();
            Map<String, Object> rowData = new HashMap<String, Object>();
            Map<String, Object> marketParam = new HashMap<String, Object>();

            String sChngSeq     = StringUtils.defaultString(String.valueOf(rslObj.get("chng_seq"))).trim();	// 시퀀스
            String sId    		= StringUtils.defaultString((String) rslObj.get("id")).trim();			// 홈페이지 ID
            String sUnqNo    	= StringUtils.defaultString((String) rslObj.get("unq_no")).trim();		// 회원고유번호

            String sBfrName     = StringUtils.defaultString((String) rslObj.get("bfr_name")).trim();	// 변경전 이름
            String sBfrZipcd    = StringUtils.defaultString((String) rslObj.get("bfr_zipcd")).trim();	// 변경전 우편번호
            String sBfrAdr      = StringUtils.defaultString((String) rslObj.get("bfr_adr")).trim();		// 변경전 주소

            String sBfrEmail    = StringUtils.defaultString((String) rslObj.get("bfr_email")).trim();	// 변경전 메일
            String sBfrHp       = StringUtils.defaultString((String) rslObj.get("bfr_hp")).trim();		// 변경전 핸드폰
            String sBfrTel      = StringUtils.defaultString((String) rslObj.get("bfr_tel")).trim();		// 변경전 전화
            String sBfrAdrDtl   = StringUtils.defaultString((String) rslObj.get("bfr_adr_dtl")).trim();	// 변경전 주소 상세

            String sAftName     = StringUtils.defaultString((String) rslObj.get("aft_name")).trim();
            String sAftZipcd    = StringUtils.defaultString((String) rslObj.get("aft_zipcd")).trim();
            String sAftAdr      = StringUtils.defaultString((String) rslObj.get("aft_adr")).trim();

            String sAftEmail    = StringUtils.defaultString((String) rslObj.get("aft_email")).trim();
            String sAftHp 		= StringUtils.defaultString((String) rslObj.get("aft_hp")).trim();
            String sAftTel		= StringUtils.defaultString((String) rslObj.get("aft_tel")).trim();
            String sAftAdrDtl	= StringUtils.defaultString((String) rslObj.get("aft_adr_dtl")).trim();

            String sChatYn = StringUtils.defaultString((String) rslObj.get("chat_pcus_cnsn_yn"));
            String sTelYn = StringUtils.defaultString((String) rslObj.get("tel_pcus_cnsn_yn"));
            String sDmYn = StringUtils.defaultString((String) rslObj.get("dm_pcus_cnsn_yn"));
            String sEmilYn = StringUtils.defaultString((String) rslObj.get("emil_pcus_cnsn_yn"));

            String sMarketingYn = StringUtils.defaultString((String) rslObj.get("marketing_yn"));	// 마케팅활용여부

            try {
                // 복호화
                /*
                if (!"".equals(sBfrEmail)) {
                    sBfrEmail = SeedCipher.decrypt(sBfrEmail, "UTF-8").replaceAll("-", "");
                }
                if (!"".equals(sBfrHp)) {
                    sBfrHp = SeedCipher.decrypt(sBfrHp, "UTF-8");
                }
                if (!"".equals(sBfrTel)) {
                    sBfrTel = SeedCipher.decrypt(sBfrTel, "UTF-8");
                }
                if (!"".equals(sBfrAdrDtl)) {
                    sBfrAdrDtl = SeedCipher.decrypt(sBfrAdrDtl, "UTF-8");
                }

                if (!"".equals(sAftEmail)) {
                    rowData.put("email", SeedCipher.decrypt(sAftEmail, "UTF-8").replaceAll("-", ""));
                }
                if (!"".equals(sAftHp)) {
                    rowData.put("cell", SeedCipher.decrypt(sAftHp, "UTF-8"));
                }
                if (!"".equals(sAftTel)) {
                    rowData.put("home_tel", SeedCipher.decrypt(sAftTel, "UTF-8"));
                }
                if (!"".equals(sAftAdrDtl)) {
                    rowData.put("home_addr2", SeedCipher.decrypt(sAftAdrDtl, "UTF-8"));
                }
                */
                if (!"".equals(sBfrEmail)) {
                    sBfrEmail = sBfrEmail.replaceAll("-", "");
                }
                if (!"".equals(sBfrHp)) {
                    sBfrHp = sBfrHp;
                }
                if (!"".equals(sBfrTel)) {
                    sBfrTel = sBfrTel;
                }
                if (!"".equals(sBfrAdrDtl)) {
                    sBfrAdrDtl = sBfrAdrDtl;
                }

                if (!"".equals(sAftEmail)) {
                    rowData.put("email", sAftEmail.replaceAll("-", ""));
                }
                if (!"".equals(sAftHp)) {
                    rowData.put("cell", sAftHp);
                }
                if (!"".equals(sAftTel)) {
                    rowData.put("home_tel", sAftTel);
                }
                if (!"".equals(sAftAdrDtl)) {
                    rowData.put("home_addr2", sAftAdrDtl);
                }
                if ("".equals(sMarketingYn)) sMarketingYn = "N";

                rowData.put("mem_no", sUnqNo);
                rowData.put("mem_nm", sAftName);
                rowData.put("home_zip", sAftZipcd);
                rowData.put("home_addr", sAftAdr);

                rowData.put("markt_agr_yn", sMarketingYn);

                rowData.put("cntr_cd", "CCA");
                rowData.put("rgsr_id", "ADMIN");
                rowData.put("amnd_id", "ADMIN");

                if (!"".equals(sUnqNo)) {
                    dlwCustDAO.getSqlSession().update("DlwCustMap.updateOnlnMember", rowData);	// 고객정보 변경
                    
                    //마케팅정보 동기화
                    marketParam = dlwNewTypeMainConsService.getDlwMarketGroup(rowData);
                    
                    //sDmYn sEmilYn sMarketingYn
                    if(marketParam != null && marketParam.size() > 0) {
            			
            			if(!marketParam.get("mkt_yn").equals(sMarketingYn)) {
            				rowData.put("mkt_gubun", "01");
                			dlwNewTypeMainConsService.mergeMktInfo(rowData);  
                		}
                		  
                		if(!marketParam.get("dm_yn").equals(sDmYn)) {
                			rowData.put("mkt_gubun", "02");
                			dlwNewTypeMainConsService.mergeMktInfo(rowData);
                		}
                		  
                		if(!marketParam.get("em_yn").equals(sEmilYn)) {
                			rowData.put("mkt_gubun", "03");
                			dlwNewTypeMainConsService.mergeMktInfo(rowData);
                		}
            		} else {
            			if("Y".equals(sMarketingYn)) {
            				rowData.put("mkt_gubun", "01");
                			dlwNewTypeMainConsService.mergeMktInfo(rowData);    
            			}
            			  
						if("Y".equals(sDmYn)) {
							rowData.put("mkt_gubun", "02");
                			dlwNewTypeMainConsService.mergeMktInfo(rowData);              				  
						}
						if("Y".equals(sEmilYn)) {
							rowData.put("mkt_gubun", "03");
                			dlwNewTypeMainConsService.mergeMktInfo(rowData);  
						}
            		}
                }

                // 채녈동의여부 수정
                if ("".equals(sChatYn)) sChatYn = "N";
                if ("".equals(sTelYn)) sTelYn = "N";
                if ("".equals(sDmYn)) sDmYn = "N";
                if ("".equals(sEmilYn)) sEmilYn = "N";
                rowData.put("chat_pcus_cnsn_yn", sChatYn);
                rowData.put("tel_pcus_cnsn_yn", sTelYn);
                rowData.put("dm_pcus_cnsn_yn", sDmYn);
                rowData.put("emil_pcus_cnsn_yn", sEmilYn);
                persInfoPcusCnsnDAO.mergePersInfoPcusCnsn(rowData);
                persInfoPcusCnsnDAO.insertPersInfoPcusCnsnHstr(rowData);

                // 상담등록
                SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                String sMstRegDm = "";	// 대명 상담MNG 등록일시 yyyy-MM-dd HH:mm:ss.SSS
                String sDtptRegDm = ""; // 대명 상담DTL 등록일시 yyyy-MM-dd HH:mm:ss.SSS
                String sMstKey = "";	// 대명 상담MNG 키
                String sDtptKey = "";	// 대명 상담DTL 키
                String sGubn = "";		// 상담상세 구분-상담유형별

                String sConsMemoCntn = (new StringBuilder("온라인 변경전 정보\n1.이름 : ")).append(sBfrName).append("\n2.핸드폰 : ").append(sBfrHp)
                        .append("\n3.전화번호 : ").append(sBfrTel).append("\n4.이메일 : ").append(sBfrEmail).append("\n5.우편번호 : ").append(sBfrZipcd)
                        .append("\n6.주소 : ").append(sBfrAdr).append("\n7.상세주소 : ").append(sBfrAdrDtl).toString();
                rowData.put("cons_memo_tit", "온라인 회원정보 변경");
                rowData.put("cons_memo_cntn", sConsMemoCntn);

                // 상담테이블 MEMBER / MEM_PROD_ACCNT 정보
                Map<String, Object> mCust = dlwCustDAO.getSqlSession().selectOne("DlwCustMap.getCustBasiConsInfo", rowData);
                rowData.put("mem_nm", 		 mCust.get("mem_nm"));
                rowData.put("sex", 			 mCust.get("sex"));
                rowData.put("brth_mon_day",	 mCust.get("brth_mon_day"));
                rowData.put("ci_val", 		 mCust.get("ci_val"));
                rowData.put("idn_no", 		 mCust.get("idn_no"));
                rowData.put("home_tel", 	 mCust.get("home_tel"));
                rowData.put("cell", 		 mCust.get("cell"));
                rowData.put("wrpl_tel", 	 mCust.get("wrpl_tel"));
                rowData.put("home_zip", 	 mCust.get("home_zip"));
                rowData.put("home_addr", 	 mCust.get("home_addr"));
                rowData.put("home_addr2", 	 mCust.get("home_addr2"));
                rowData.put("credit_rating", mCust.get("credit_rating"));
                rowData.put("crdt_mng_no", 	 mCust.get("crdt_mng_no"));
                rowData.put("email", 		 mCust.get("email"));
                rowData.put("emple_no", 	 mCust.get("emple_no"));
                rowData.put("emple_nm", 	 mCust.get("emple_nm"));
                rowData.put("join_dt", 		 mCust.get("join_dt"));
                rowData.put("dept_cd", 		 mCust.get("dept_cd"));
                rowData.put("note", 		 mCust.get("note"));

                rowData.put("gubn",     "80");
                rowData.put("cnsl_cd",  "01");
                rowData.put("cnsl_man", "ADMIN");

                rowData.put("consno_sqno",  "1");                   // 순번
                rowData.put("actp_id",      "ADMIN");        		// 접수자ID
                rowData.put("cons_stat_cd", "30");                  // 처리상태
                rowData.put("cons_dspsmddl_dtpt_cd", "10");         // 처리방법
                rowData.put("chpr_id",      "ADMIN");    			// 담당자ID
                rowData.put("acpg_chnl_cd", "99");             		// 접수채널 - 기타

                rowData.put("cons_typ1_cd", gmParam.get("cons_typ1_cd"));
                rowData.put("cons_typ2_cd", gmParam.get("cons_typ2_cd"));
                rowData.put("cons_typ3_cd", gmParam.get("cons_typ3_cd"));

                // 대명 상담 등록 시간 설정
                sMstRegDm = sdfDate.format(new Date());
                rowData.put("mst_reg_dm", sMstRegDm);
                rowData.put("mst_upd_dm", sMstRegDm);

                //dlwConsDAO.getSqlSession().insert("DlwConsMap.insertCons", rowData);
                /*  skey ㄱㅏㅂ ㄱㅏㅈㅕㅇㅛㄱㅣ */

                /*
                String sSeq = (String) rowData.get("skey");
                String tmpStr[] = sSeq.split("\\|");
                rowData.put("seq", tmpStr[0]);
                rowData.put("cnsl_seq", tmpStr[1]);

                // 대명 상담상세 등록 시간 설정
                sDtptRegDm = sdfDate.format(new Date());
                rowData.put("dtpt_reg_dm", sDtptRegDm);
                rowData.put("dtpt_upd_dm", sDtptRegDm);

                // 엔컴 상당등록 삭제(2016/11/21)
                //dlwConsDAO.getSqlSession().insert("DlwConsMap.insertConsDtpt", rowData);

                // 신규등록시 대명키 생성
                // mem_no seq clsn_seq accnt_no reg_man reg_dm
                // mem_no seq clsn_seq gubn     reg_man reg_dm
                if (!"".equals(sSeq)) { // 대명 키 존재
                    sMstKey = rowData.get("mem_no")+"^"+rowData.get("seq")+"^"+rowData.get("cnsl_seq")+"^"+rowData.get("accnt_no")+"^"+rowData.get("rgsr_id")+"^"+sMstRegDm;
                    sDtptKey = rowData.get("mem_no")+"^"+rowData.get("seq")+"^"+rowData.get("cnsl_seq")+"^"+sGubn+"^"+rowData.get("rgsr_id")+"^"+sDtptRegDm;
                }
                rowData.put("mst_key", sMstKey);
                rowData.put("dtpt_key", sDtptKey);
                */
                consDAO.getSqlSession().insert("ConsMap.insertCons", rowData);
                consHstrDAO.getSqlSession().insert("ConsHstrMap.insertConsHstr", rowData);

                String sLocalIpAddr = InetAddress.getLocalHost().getHostAddress();
                rowData.put("chng_seq", sChngSeq);
                rowData.put("id", sId);
                rowData.put("mod_ip", sLocalIpAddr);
                dlwOnlnCustDAO.getSqlSession().update("DlwOnlnCustMap.updateOnlnCustYn", rowData);	// 온라인 상태값 변경
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

}
