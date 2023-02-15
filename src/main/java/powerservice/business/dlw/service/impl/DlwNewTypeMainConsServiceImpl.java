package powerservice.business.dlw.service.impl;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import powerservice.business.bean.UserSession;
import powerservice.business.cns.service.impl.CustBasiServiceImpl;
import powerservice.business.dlw.service.DlwNewTypeMainConsService;
import powerservice.common.util.CommonUtils;
import powerservice.core.util.SessionUtils;

/**
 * 대명고객 정보를 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/02/04
 * @프로그램ID DlwCust
 */
@Service
public class DlwNewTypeMainConsServiceImpl extends EgovAbstractServiceImpl implements DlwNewTypeMainConsService {
    
	private final Logger LOGGER = LoggerFactory.getLogger(CustBasiServiceImpl.class);
	
    @Resource
    public DlwNewTypeMainConsDAO dlwNewTypeMainConsDAO;
    
	
	/**
     * 고객-상품 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 고객-상품 정보
     * @throws Exception
     */
    public Map<String, Object> getDlwCustMemList(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeMainConsDAO.getDlwCustMemList(pmParam);
    }
    
    
    /**
     * 고객 개인정보 활용동의 정보를 검색한다.
     *
     * @param psId 고객ID
     * @return 고객 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getPersInfoPcusCnsn(String psId) throws Exception {
        Map<String, String> mParam = new HashMap<String, String>();
        mParam.put("mem_no", psId);
        return dlwNewTypeMainConsDAO.getPersInfoPcusCnsn(mParam);
    }
    
    
    /**
     * 고객 개인정보 활용동의 스크립트 상세정보를 검색한다.
     *
     * @param 없음
     * @return 스크립트 상세정보
     * @throws Exception
     */
    public Map<String, Object> getPersInfoPcusSrctDtpt() throws Exception {
        Map<String, String> mParam = new HashMap<String, String>();
        mParam.put("use_yn", "Y");
        return dlwNewTypeMainConsDAO.getPersInfoPcusSrctDtpt(mParam);
    }
    
    /**
     * 고객조회로그 정보를 등록한다.
     *
     * @param pmParam 고객조회로그 정보
     * @throws Exception
     */
    public String insertCustInqLog(Map<String, ?> pmParam) throws Exception {
        String sKey = "";

        int nResult = dlwNewTypeMainConsDAO.insertCustInqLog(pmParam);
        if (nResult > 0) {
            sKey = (String) pmParam.get("cust_inq_log_id");
        }

        return sKey;
    }
    
    /**
     * 고객 특이메모 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 고객 특이메모 정보의 검색 건수
     * @throws Exception
     */
    public int getCustUnusMemoCnt(Map<String, ?> pmParam) throws Exception {
        return (Integer) dlwNewTypeMainConsDAO.getCustUnusMemoCnt(pmParam);
    }
    
    /**
     * 대명라이프웨이 OCB 전송 이력 카운트
     *
     * @param pmParam 검색 조건
     * @throws Exception
     */

    public int getOcbTransHistCnt() throws Exception {
        return dlwNewTypeMainConsDAO.getOcbTransHistCnt();
    }
    
    /**
     * OCB정보를 수정한다.
     *
     * @param pmParam 검색 조건
     * @return 회원정보
     * @throws Exception
     */
    public int updateMemberOCB(Map<String, ?> pmParam) throws Exception {
        String sMemNo = StringUtils.defaultString((String)pmParam.get("mem_no"));

        int iCnt = dlwNewTypeMainConsDAO.updateMemberOCB(pmParam);
        if (iCnt > 0 && !"".equals(sMemNo)) {
            Map<String, Object> mDtpt = dlwNewTypeMainConsDAO.getDlwMemberDtpt(sMemNo);
            dlwNewTypeMainConsDAO.insertCustBasiHstr(mDtpt);
        }

        return iCnt;
    }
    
    /**
     * 고객 정보를 등록한다. (신규 등록)
     *
     * @param pmParam 고객 정보
     * @throws Exception
     */
    public String insertCustMemSave(Map<String, Object> pmParam) throws Exception {
        String sKey = "";
       
        int nResult = dlwNewTypeMainConsDAO.insertMember(pmParam); // 대명 회원정보 등록
        
        if (nResult > 0) {
            sKey = (String) pmParam.get("mem_no");
            
            if(!"".equals(pmParam.get("credit_point")) && pmParam.get("credit_point") != null) {
            	dlwNewTypeMainConsDAO.insertMemberCredit(pmParam); // 대명 회원정보 신용점수 등록
            }
            
            if("Y".equals(pmParam.get("markt_agr_yn"))) {
            	pmParam.put("gubun", "01");
            	dlwNewTypeMainConsDAO.insertMktInfo(pmParam); // 대명 마케팅정보 등록
            }
            
            if("Y".equals(pmParam.get("dm_yn"))) {
            	pmParam.put("gubun", "02");
            	dlwNewTypeMainConsDAO.insertMktInfo(pmParam); // 대명 마케팅정보 등록
            }
            
            if("Y".equals(pmParam.get("email_yn"))) {
            	pmParam.put("gubun", "03");
            	dlwNewTypeMainConsDAO.insertMktInfo(pmParam); // 대명 마케팅정보 등록
            }
        }                
        return sKey;
    }
    
    /**
     * 고객 정보를 수정한다.
     *
     * @param pmParam 고객 정보
     * @throws Exception
     */
    public int updateCustMemSave(Map<String, Object> pmParam) throws Exception {
    	// MEMBER UPDATE
        int nResult = dlwNewTypeMainConsDAO.updateMember(pmParam);
        
        return nResult;
    }
    
    /**
     * 고객정보 변경 건중 주소의 경우 별도 주소관리를 해야하기 때문에 별도 MERGE-INSERT 프로세스 도입
     *
     * @param pmParam 고객 정보
     * @throws Exception
     */
    public int updateAddrChageData(Map<String, Object> pmParam) throws Exception {        
		//접속자 정보 
		UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();  	
	    String sAthrCd = oLoginUser.getAuthoritycd();
	    String sTeamCd = oLoginUser.getTeamcd();
	    
	    pmParam.put("bzpt_div", sTeamCd);
	    pmParam.put("rltn_cd", "10");	// 본인
	    
	    // 주소 및 전화 번호관리 MERGE INTO 
	    int nResult_Addr = dlwNewTypeMainConsDAO.mergeMemberAddrDtl(pmParam);
	    
	    // 주소 및 전화 번호관리 이력 INSERT
	    if (nResult_Addr > 0){
	        pmParam.put("member_addr_dtl_merge_yn", "Y");
	        dlwNewTypeMainConsDAO.insertMemberAddrDtlHstr(pmParam);
	    }
        return nResult_Addr;
    }    
    
    /**
     * 변경 삭제 내역 저장
     *
     * @param pmParam 변경 삭제 내역
     * @return
     * @throws Exception
     * 2017-09-20 김준호 ACCNT_NO 값을 가져오지 못하여 해당 부분 변경
     * insertReqUpdDelInf(Map<String, ? > pmParam) ->> insertReqUpdDelInf(Map<String, Object> pmParam) 수정
     */
    public int insertReqUpdDelInf(Map<String, Object> pmParam) throws Exception {
        String sCl1 = StringUtils.defaultString((String) pmParam.get("cl1"));
        int nResult = 0;

        // 2017.09.21
        //nResult = dlwConsProdDAO.insertReqUpdDelInf(pmParam);

        /******************************************************************
         * 기본정보 변경시 해당 고객의 모든 상품들을 변경이력 등록 2017-09-20 김준호
         * dat1 :: 기본정보 변경시 해당 변경 정보 기록
         ******************************************************************/
        String dat1 = (String) pmParam.get("dat1");
        if (
               dat1.equals("자택우편번호")
            || dat1.equals("자택주소")
            || dat1.equals("생년월일")
            || dat1.equals("성명")
            || dat1.equals("휴대폰")
            || dat1.equals("주민번호")
            || dat1.equals("이메일")
        ) {
            // 회원고유번호로 해당 고객의 회원번호를 가져온다.
            List<Map<String, Object>> ProdList = dlwNewTypeMainConsDAO.selectCountProd(pmParam);
            // 회원번호의 갯수만큼 for문 돌면서 인서트.
            for (int i = 0; i < ProdList.size(); i++) {
                // 변수에 회원번호를 넣어준다.
                pmParam.put("accnt_no", StringUtils.defaultString((String) ProdList.get(i).get("ACCNT_NO")));
                // 인서트
                nResult = nResult+ dlwNewTypeMainConsDAO.insertReqUpdDelInf(pmParam);
            }
            //
            if (ProdList.size() != nResult){
                nResult = -1;
            }
        } else {
            nResult = dlwNewTypeMainConsDAO.insertReqUpdDelInf(pmParam);
        }



        if (nResult > 0) {
            if ("D".equals(sCl1)) {  // 삭제
                // 삭제여부 수정
            	dlwNewTypeMainConsDAO.delFlagMemProdAccnt(pmParam);
            	dlwNewTypeMainConsDAO.delFlagPayMng(pmParam);
                dlwNewTypeMainConsDAO.delFlagRescission(pmParam);
                dlwNewTypeMainConsDAO.delFlagCnclBrkdnMng(pmParam);
                dlwNewTypeMainConsDAO.delFlagEvent(pmParam);

                dlwNewTypeMainConsDAO.delFlagTaxtProc(pmParam);
                dlwNewTypeMainConsDAO.delFlagCmsMemb(pmParam);
                dlwNewTypeMainConsDAO.delFlagMstrChgInf(pmParam);
                dlwNewTypeMainConsDAO.delFlagGasuAmtReg(pmParam);
                dlwNewTypeMainConsDAO.delFlagDcAmtReg(pmParam);

                dlwNewTypeMainConsDAO.delFlagCardMemb(pmParam);
                dlwNewTypeMainConsDAO.deleteDlwCmsMmbr(pmParam);

                //memProdAccntDAO.delFlagMemProdAccnt(pmParam);
            }
        }

        return nResult;
    }
    
    /**
     * 입력 혹은 수정된 고객 정보 상담이력 및 변견 LOG 등록
     *
     * @param pmParam 고객 정보
     * @throws Exception
     */

    public void insertMemLogSave(Map<String, Object> pmParam) throws Exception {
    	dlwNewTypeMainConsDAO.insertMemLogSave(pmParam);
    }
    
    /**
     * 고객 정보를 수정한다.(대명고객정보로 업데이트)
     *
     * @param pmParam 고객 정보
     * @throws Exception
     */

    public void updateCustBasi_web(Map<String, Object> pmParam) throws Exception {
    	dlwNewTypeMainConsDAO.updateMember_web(pmParam); // 대명홈페이지  회원명 수정(개명신청자)
    }
    
    /**
     * 고객-상품 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 고객-상품 정보
     * @throws Exception
     */
    public Map<String, Object> getDlwCustPrdtDtpt(String psParam) throws Exception {
        Map<String, Object> pmParam = new HashMap<String, Object>();
        pmParam.put("mem_no", psParam);
        pmParam.put("unty_inq_yn", "Y");
        return dlwNewTypeMainConsDAO.getDlwCustPrdtDtpt(pmParam);
    }
    
    /**
     * 캠페인발신이력 정보의 검색수를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 캠페인발신이력 정보의 검색 건수
     * @throws Exception
     */
    public int getIbDpmsReslHstrCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) dlwNewTypeMainConsDAO.getIbDpmsReslHstrCount(pmParam);
    }


    /**
     * 캠페인발신이력 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 캠페인발신이력 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getIbDpmsReslHstrList(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeMainConsDAO.getIbDpmsReslHstrList(pmParam);
    }
    
    /**
     * VOC 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return VOC 정보의 검색 수
     * @throws Exception
     */
    public int getVocDtlCount(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeMainConsDAO.getVocDtlCount(pmParam);
    }

    /**
     * VOC 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return VOC 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getVocDtlList(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeMainConsDAO.getVocDtlList(pmParam);
    }
     
    /**
     * 문자 전송 이력 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 문자 전송 이력 정보의 검색 건수
     * @throws Exception
     */
    public int getChatSndgHstrCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) dlwNewTypeMainConsDAO.getChatSndgHstrCount(pmParam);
    }

    /**
     * 문자 전송 이력 정보를 검색한다.
     *
     * @param paramHash 검색 조건
     * @return 문자 전송 이력 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getChatSndgHstrList(Map<String, ?> param) throws Exception {
        return dlwNewTypeMainConsDAO.getChatSndgHstrList(param);
    }
    
    /**
     * 상담-상품 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상담-상품 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwConsProdList(Map<String, ?> pmParam) throws Exception {

        return dlwNewTypeMainConsDAO.getDlwConsProdList(pmParam);
    }
    
    /**
     * 선택된 상품 정보를 가져온다
     *
     * @param pmParam 검색 조건
     * @return 상담-상품 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwConsProdListDtl(Map<String, ?> pmParam) throws Exception {

        return dlwNewTypeMainConsDAO.getDlwConsProdListDtl(pmParam);
    }
    
    /**
     * 회원 상품정보를 검색한다.(메인화면에서 해약금 조회 2018.01.30 김찬영
     *
     * @param pmParam 회원정보
     * @return 회원 상품 정보
     * @throws Exception
     */
    public Map<String, Object> getProdInfoInqNew(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeMainConsDAO.getProdInfoInqNew(pmParam);
    }
    
    /**
     * 현재 회차 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 회차
     * @throws Exception
     */
    public int getNowMonthCount(String psAccntNo) throws Exception {
        Map<String, Object> pmParam = new HashMap<String, Object>();
        pmParam.put("accnt_no", psAccntNo);
        return dlwNewTypeMainConsDAO.getNowMonthCount(pmParam);
    }

    /**
     * 해약환급금 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 해약환급금
     * @throws Exception
     */
    public int getResnAmt(String psProdCd, String psAccntNo, int pCnt, String sJoinDt) throws Exception {
        Map<String, Object> pmParam = new HashMap<String, Object>();
        pmParam.put("prod_cd", psProdCd);
        pmParam.put("accnt_no", psAccntNo);
        pmParam.put("pay_cnt", pCnt);
        pmParam.put("join_dt", sJoinDt);
        return dlwNewTypeMainConsDAO.getResnAmt(pmParam);
    }
    
    /**
     * 해약 구분(해약/청약)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getResnGubn(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeMainConsDAO.getResnGubn(pmParam);
    }
    
    /**
     * 전자서명 상태값 [00(인증값)_00(상품계약서 상태값)]
     *
     * @param pmParam 검색 조건
     * @return 해피콜 승인여부
     * @throws Exception
     */
    public Map<String, Object> getNiceAuthStat(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeMainConsDAO.getNiceAuthStat(pmParam);
    }
    
    /**
    *
    *
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
    public int getProdSrvcHistForMPAS(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeMainConsDAO.getProdSrvcHistForMPAS(pmParam);
    }
    
    /**
     * 상품-리조트회원에 따른 부가서비스 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 부가서비스 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getProdSrvcMstList(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeMainConsDAO.getProdSrvcMstList(pmParam);
    }
    
    /**
     * 할인우대권을 체크한다.
     *
     * @param pmParam 검색 조건
     * @return 할인우대권 체크 결과
     * @throws Exception
     */
    public String validateDiscntPassNo(String psParam) throws Exception {
        Map<String, Object> pmParam = new HashMap<String, Object>();
        pmParam.put("rand_num", psParam);

        return dlwNewTypeMainConsDAO.validateDiscntPassNo(pmParam);
    }

    /**
     * 특별할인
     *
     * @param pmParam 검색 조건
     * @return 특별할인
     * @throws Exception
     */
    public String selectNewChanGunsuDPM(String psParam) throws Exception {
        Map<String, Object> pmParam = new HashMap<String, Object>();
        pmParam.put("rand_num", psParam);

        return dlwNewTypeMainConsDAO.selectNewChanGunsuDPM(pmParam);
    }
    
    /**
     * 상담-상품 모델분류 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 모델분류 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwModlMstInfo(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeMainConsDAO.getDlwModlMstInfo(pmParam);
    }

    /**
     * 상담-상품 모델명 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 모델명 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwModlDtlInfo(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeMainConsDAO.getDlwModlDtlInfo(pmParam);
    }

    /**
     * 상담-상품 상품종류 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상품종류 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwProdKindList(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeMainConsDAO.getDlwProdKindList(pmParam);
    }
    
    /**
     * 회원의 리조트 정보 조회
     *
     * @param pmParam 검색 조건
     * @return 회원의 리조트 정보
     * @throws Exception
     */
    public Map<String, Object> getResortInfo(String psParam) throws Exception {
        Map<String, Object> pmParam = new HashMap<String, Object>();
        pmParam.put("mem_no", psParam);

        return dlwNewTypeMainConsDAO.getResortInfo(pmParam);
    }
    
    /**
     * 회원 상품 정보 수정 시 체크 해야 할 사항 (청구중 or 두구좌 제한 등등)
     *
     * @param pmParam 검색 조건
     * @return 회원의 체크할 데이
     * @throws Exception
     */
    public Map<String, Object> getAccntCheck(String psParam) throws Exception {
        Map<String, Object> pmParam = new HashMap<String, Object>();
        pmParam.put("accnt_no", psParam);

        return dlwNewTypeMainConsDAO.getAccntCheck(pmParam);
    }    
    
    /**
     * 2구좌 가입 제한 체크
     *
     * @param pmParam 검색 조건
     * @return 체크 결과
     * @throws Exception
     */
    public String getNoSaleAccnt(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeMainConsDAO.getNoSaleAccnt(pmParam);
    }
    
    /**
     * 카드 코드 조회
     *
     * @param pmParam 검색 조건
     * @return 카드 코드
     * @throws Exception
     */
    public String getCardCode(String psParam) throws Exception {
        return dlwNewTypeMainConsDAO.getCardCode(psParam);
    }
    
    /**
     * 회원번호 생성
     *
     * @param pmParam 검색 조건
     * @return 회원번호
     * @throws Exception
     */
    public String createAccntNo(String psParam) throws Exception {
        return dlwNewTypeMainConsDAO.createAccntNo(psParam);
    }
    
    /**
     * 회원_상품_계좌 등록
     *
     * @param pmParam 검색 조건
     * @return 회원번호
     * @throws Exception
     */
    public int insertMemProdAccnt(Map<String, Object> pmParam) throws Exception {
    	int nResult = 0;
    	
        String sAccntNo = StringUtils.defaultString((String) pmParam.get("accnt_no"));        
        String sHpclStatCd = StringUtils.defaultString((String) pmParam.get("hpcl_stat_cd"));	// 해피콜 상담상태
        String sResortNo = "";
        //String sInsplZip = StringUtils.defaultString((String)pmParam.get("inspl_zip")); // 우편번호
        
        nResult = dlwNewTypeMainConsDAO.insertMemProdAccnt(pmParam);
        
        /*
        if (nResult > 0) {
            if (!"".equals(sHpclStatCd)) {	// 해피콜 등록
                String chkTyp = dlwNewTypeMainConsDAO.getHpclAckdStatChk(pmParam);	// 승인상태 체크
                if (!"A".equals(chkTyp)) {
                    if ("I".equals(chkTyp)) {
                    	dlwNewTypeMainConsDAO.insertHpclAckd(pmParam);
                    } else if ("U".equals(chkTyp)) {
                    	dlwNewTypeMainConsDAO.updateHpclAckd(pmParam);
                    }
                    pmParam.put("hpcl_note", "단건승인");
                    dlwNewTypeMainConsDAO.insertHpclHist(pmParam);
                }
            }

            sResortNo = dlwNewTypeMainConsDAO.getResortNo(sAccntNo);
            pmParam.put("resort_no", sResortNo);

            if (!"".equals(sInsplZip)) {
                String sInsplPhone = StringUtils.defaultString((String)pmParam.get("inspl_phone")).replaceAll("-", ""); // 배송지 연락처
                String[] sHpnoList = {"010", "011", "016", "017", "018", "019"};
                boolean incoTlnoClphFlag = false;

                pmParam.put("mem_nm", 		pmParam.get("mem_nm"));
                pmParam.put("rltn_cd",      "80");	// 설치장소

                if (sInsplPhone.length() == 10 || sInsplPhone.length() == 11) {
                    incoTlnoClphFlag = Arrays.asList(sHpnoList).contains(sInsplPhone.substring(0, 3));
                }
                if (incoTlnoClphFlag) {
                    pmParam.put("cell", sInsplPhone); 		// 휴대전화
                } else {
                    pmParam.put("home_tel", sInsplPhone); 	// 자택전화
                }
                pmParam.put("home_zip", 	sInsplZip);
                pmParam.put("home_addr", 	StringUtils.defaultString((String)pmParam.get("inspl_addr")));
                pmParam.put("home_addr2", 	StringUtils.defaultString((String)pmParam.get("inspl_addr2")));

                // 변경 고객 주소 정보 존재 건수 조회
                int nExistCount = 0;
                try {
                    nExistCount = dlwNewTypeMainConsDAO.getMemberAddrDtlExistCount(pmParam);
                } catch (Exception exception) {
                    LOGGER.error(exception.getMessage());
                }
                // 설치장소 고객 주소 정보 저장
                int nResultMemberAddrDtl = dlwNewTypeMainConsDAO.mergeMemberAddrDtl(pmParam);
                // 고객 주소 정보가 변경된 경우 고객 주소 이력 저장
                if (nResultMemberAddrDtl > 0 && nExistCount == 0) {
                    try {
                        pmParam.put("member_addr_dtl_merge_yn", "Y");
                        dlwNewTypeMainConsDAO.insertMemberAddrDtlHstr(pmParam);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
        }
        */
        return nResult;
    }
    
    /**
     * 스마트라이프 상담 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertSmartLifeCnslMng(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeMainConsDAO.insertSmartLifeCnslMng(pmParam);
    }
    
    /**
     * 부가서비스 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertMemProdAccntSvc(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeMainConsDAO.insertMemProdAccntSvc(pmParam);
    }
    
    /**
     * 두구좌 계정 정보저장
     *
     * @param pmParam 검색 조건
     * @return 회원번호
     * @throws Exception
     */
    public int insertMemTwinAccnt(Map<String, Object> pmParam) throws Exception {
        return dlwNewTypeMainConsDAO.insertMemTwinAccnt(pmParam);
    }
    
    /**
     * 회원_상품_계좌 수정
     *
     * @param pmParam 검색 조건
     * @return 회원번호
     * @throws Exception
     */
    public int updateMemProdAccnt(Map<String, Object> pmParam) throws Exception {
		int nResult = 0;
		String sAuthChk = "";
		
		// 상품 업데이트 처리
		nResult = dlwNewTypeMainConsDAO.updateMemProdAccnt(pmParam);
		
		//상품 승인상태 업데이트 처리 (승인값이 변경된 항목만 실행함)
		sAuthChk = StringUtils.defaultString((String) pmParam.get("auth_chk"));		
		if (sAuthChk.equals("Y")){
			dlwNewTypeMainConsDAO.updateHpclAckd(pmParam);
		}
    	/*
        String sAccntNo = StringUtils.defaultString((String) pmParam.get("accnt_no"));
        //String sMembership = StringUtils.defaultString((String) pmParam.get("membership"));
        String sResortNo = StringUtils.defaultString((String) pmParam.get("resort_no"));
        String sHpclStatCd = StringUtils.defaultString((String) pmParam.get("hpcl_stat_cd"));	// 해피콜 상담상태
        String sInsplZip = StringUtils.defaultString((String)pmParam.get("inspl_zip")); // 우편번호
        int nResult = 0;

        nResult = dlwNewTypeMainConsDAO.updateMemProdAccnt(pmParam);

        if (nResult > 0) {
            if (!"".equals(sHpclStatCd)) {	// 해피콜 등록
                String chkTyp = dlwNewTypeMainConsDAO.getHpclAckdStatChk(pmParam);	// 승인상태 체크
                if (!"A".equals(chkTyp)) {
                    if ("I".equals(chkTyp)) {
                    	dlwNewTypeMainConsDAO.insertHpclAckd(pmParam);
                    } else if ("U".equals(chkTyp)) {
                    	dlwNewTypeMainConsDAO.updateHpclAckd(pmParam);
                    }
                    pmParam.put("hpcl_note", "단건승인");
                    dlwNewTypeMainConsDAO.insertHpclHist(pmParam);
                }
            }

            sResortNo = dlwNewTypeMainConsDAO.getResortNo(sAccntNo);
            pmParam.put("new_resort_no", sResortNo);


            if (!"".equals(sInsplZip)) {
                if (!"".equals(sInsplZip)) {
                    String sInsplPhone = StringUtils.defaultString((String)pmParam.get("inspl_phone")).replaceAll("-", ""); // 배송지 연락처
                    String[] sHpnoList = {"010", "011", "016", "017", "018", "019"};
                    boolean incoTlnoClphFlag = false;

                    pmParam.put("mem_nm", 		pmParam.get("mem_nm"));
                    pmParam.put("rltn_cd",      "80");	// 설치장소

                    if (sInsplPhone.length() == 10 || sInsplPhone.length() == 11) {
                        incoTlnoClphFlag = Arrays.asList(sHpnoList).contains(sInsplPhone.substring(0, 3));
                    }
                    if (incoTlnoClphFlag) {
                        pmParam.put("cell", sInsplPhone); 		// 휴대전화
                    } else {
                        pmParam.put("home_tel", sInsplPhone); 	// 자택전화
                    }
                    pmParam.put("home_zip", 	sInsplZip);
                    pmParam.put("home_addr", 	StringUtils.defaultString((String)pmParam.get("inspl_addr")));
                    pmParam.put("home_addr2", 	StringUtils.defaultString((String)pmParam.get("inspl_addr2")));

                    // 변경 고객 주소 정보 존재 건수 조회
                    int nExistCount = 0;
                    try {
                        nExistCount = dlwNewTypeMainConsDAO.getMemberAddrDtlExistCount(pmParam);
                    } catch (Exception exception) {
                        LOGGER.error(exception.getMessage());
                    }
                    // 설치장소 고객 주소 정보 저장
                    int nResultMemberAddrDtl = dlwNewTypeMainConsDAO.mergeMemberAddrDtl(pmParam);
                    // 고객 주소 정보가 변경된 경우 고객 주소 이력 저장
                    if (nResultMemberAddrDtl > 0 && nExistCount == 0) {
                        try {
                            pmParam.put("member_addr_dtl_merge_yn", "Y");
                            dlwNewTypeMainConsDAO.insertMemberAddrDtlHstr(pmParam);
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                }
            }
        }
	*/
        return nResult;
    }
    
    /**
     * 부가서비스 삭제
     *
     * @param pmParam 검색 조건
     * @throws Exception
     */
    public int deleteMemProdAccntSvc(String psParam) throws Exception {
        Map<String, Object> pmParam = new HashMap<String, Object>();
        pmParam.put("accnt_no", psParam);
        return dlwNewTypeMainConsDAO.deleteMemProdAccntSvc(pmParam);
    }
    
    /**
     * 발주목록데이터 체크
     * */
    public int getdeliveryCnt(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeMainConsDAO.getdeliveryCnt(pmParam);
     }
    
    /**
     * 발주관리  설치장소 업데이트
     *
     * @param pmParam 검색 조건
     * @return 회원번호
     * @throws Exception
     */
    public int updatedelivery(Map<String, Object> pmParam) throws Exception {
        return  dlwNewTypeMainConsDAO.updatedelivery(pmParam);
    }
    
    /**
     * 녹취 상품 정보를 등록한다.
     *
     * @param pmParam 녹취 상품 정보
     * @throws Exception
     */
    public String mergeRecProdDtl(Map<String, Object> pmParam) throws Exception {
        String skey = "";

        int nResult = dlwNewTypeMainConsDAO.mergeRecProdDtl(pmParam);
        if (nResult > 0) {
            skey = (String) pmParam.get("rec_prod_dtl_id");
        }
        return skey;
    }
    
    /**
     * CMS 출금이체 신청중인지 체크
     *
     * @param pmParam 검색 조건
     * @throws Exception
     */
    public String getCmsWdrwReqChk(String psParam) throws Exception {
        return dlwNewTypeMainConsDAO.getCmsWdrwReqChk(psParam);
    }

    /**
     * 카드 출금이체 신청중인지 체크
     *
     * @param pmParam 검색 조건
     * @throws Exception
     */
    public String getCardWdrwReqChk(String psParam) throws Exception {
        return dlwNewTypeMainConsDAO.getCardWdrwReqChk(psParam);
    }

    /**
     * 카드 출금이체 신청중인지 체크
     *
     * @param pmParam 검색 조건
     * @throws Exception
     */
    public String getPayChk(String psParam) throws Exception {
        return dlwNewTypeMainConsDAO.getPayChk(psParam);
    }
    
    /**
     * 대명라이프웨이 부가정보 조회
     *
     * @param pmParam 검색 조건
     * @return  부가정보
     * @throws Exception
     */
    public List<Map<String, Object>> getBugaInfo(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeMainConsDAO.getBugaInfo(pmParam);
    }
    
    /**
     * 사원 상세정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 사원 상세정보
     * @throws Exception
     */
    public Map<String, Object> getDlwEmplDtpt(String psParam) throws Exception {
        Map<String, Object> pmParam = new HashMap<String, Object>();
        pmParam.put("emple_no", psParam);
        return dlwNewTypeMainConsDAO.getDlwEmplDtpt(pmParam);
    }
    
    /**
     * 납입정보(부가서비스)등록 회원을 체크한다.
     *
     * @param pmParam 검색 조건
     * @return 회원정보
     * @throws Exception
     */
    public Map<String, Object> getBugaSrvcMemChk(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeMainConsDAO.getBugaSrvcMemChk(pmParam);
    }
    
    /* ================================================================
     * 날짜 : 20180809
     * 이름 : 송준빈
     * 추가내용 : 최근 애큐온 인증 여부 Y/N
     * 대상 테이블 : TB_MEMBER_NICE_INFO
     * ================================================================
     * */
	public List<Map<String, Object>> getAcuonLatelyAuth(Map<String, Object> pmParam) {
		return dlwNewTypeMainConsDAO.getAcuonLatelyAuth(pmParam);
	}
	
	/**
     * 상담업무 수정시 부가서비스 업무 수정 및 로직체크
     *
     * @param pmParam 회원번호
     * @return void
     * @throws Exception
     */
    public void updateCallCenterVatSvcHist(Map<String, Object> pmParam) throws Exception {
       // return dlwVatSvcDAO.selectSvcAccntNoHist(pmParam);


        Map<String, Object> hmParam = null;
        Map<String, Object> list01Map = null;
        Map<String, Object> list02Map = null;

        try
        {
            List<Map<String, Object>> accntNoHistList01 = dlwNewTypeMainConsDAO.selectSvcAccntNoHist(pmParam);
            List<Map<String, Object>> memSvcList02 = dlwNewTypeMainConsDAO.selectMemProdAccntSvcTot(pmParam);
            String list01Pk = "";
            String list01AccntNo = "";
            String list01DtlSeq = "";
            String list01Seq = "";
            String list01OptSvcSeq = "";

            String list02Pk = "";
            String list02AccntNo = "";
            String list02DtlSeq = "";
            String list02Seq = "";
            String list02OptSvcCd = "";


            for (int i = 0; i < accntNoHistList01.size(); i++) { // 부가서비스 관리 이력에 저장되어 있는 데이타 loop
                list01Map = accntNoHistList01.get(i);
                list01AccntNo = String.valueOf(list01Map.get("accnt_no"));
                list01DtlSeq = String.valueOf(list01Map.get("dtl_seq"));
                list01Seq = String.valueOf(list01Map.get("seq"));
                list01OptSvcSeq = String.valueOf(list01Map.get("optsvc_seq"));

                list01Pk = list01AccntNo+list01DtlSeq+list01OptSvcSeq;

                for (int j = 0; j < memSvcList02.size(); j++) { // 회원별 부가서비스 이력에 저장되어 있는 데이타 loop
                    list02Map = memSvcList02.get(i);
                    list02AccntNo = String.valueOf(list02Map.get("accnt_no"));
                    list02DtlSeq = String.valueOf(list02Map.get("dtl_seq"));
                    list02Seq = String.valueOf(list02Map.get("seq"));
                    list02OptSvcCd = String.valueOf(list02Map.get("opt_svc_cd"));

                    list02Pk = list02AccntNo+list02DtlSeq+list02OptSvcCd;

                    if(list01Pk.equals(list02Pk)){ // accnt_no,dtl_seq 데이타가 같은 키가 비교 타켓
                        if(!list01Seq.equals(list02Seq)){ //seq 틀릴시에 상담에서 변경되면 부가서비스 이력에도 수정하도록 처리하는 로직
                            hmParam = new HashMap<String, Object>();
                            hmParam.put("accnt_no", 	list01AccntNo);
                            hmParam.put("dtl_seq", 		list01DtlSeq);
                            hmParam.put("seq", 			list02Seq);
                            hmParam.put("optsvc_seq", 	list01OptSvcSeq);

                            dlwNewTypeMainConsDAO.updateCallCenterVatSvcHist(hmParam);
                        }
                    }

                }

            }


        } catch (Exception e) {
            throw e;
        }
    }
    
    /* ================================================================
     * 날짜 : 20180221
     * 이름 : 송준빈
     * 추가내용 : 고객의 증서내용 조회 (2차 발송시 NICE 개별부로 보내지게 됨)
     * ================================================================
     * */
    public List<Map<String, Object>> getCertfMng(Map<String, Object> pmParam) {
        return dlwNewTypeMainConsDAO.getCertfMng(pmParam);
    }

    /* ================================================================
     * 날짜 : 20180221
     * 이름 : 송준빈
     * 추가내용 : 고객의 해약율과 해약금 조회 (2차 발송시 NICE 개별부로 보내지게 됨)
     * ================================================================
     * */
    public List<Map<String, Object>> getResnAmt(Map<String, Object> pmParam) {
        return dlwNewTypeMainConsDAO.getResnAmt_01(pmParam);
    }
    
    /* ================================================================
     * 날짜 : 20180809
     * 이름 : 송준빈
     * 추가내용 : NICE 전자서명 2차대상 고객 저장
     * 대상 테이블 : TB_MEMBER_NICE_INFO
     * ================================================================
     * */
	public int insertMemberNiceInfoSecond(Map<String, ?> pmParam) throws Exception {
		return dlwNewTypeMainConsDAO.insertMemberNiceInfoSecond(pmParam);
	}
	
	/* ================================================================
     * 날짜 : 20180809
     * 이름 : 송준빈
     * 추가내용 : NICE 전자서명 대상 고객 조회
     * 대상 테이블 : TB_MEMBER_NICE_INFO
     * ================================================================
     * */
    public List<Map<String, Object>> getMemberNiceRetrieve2(Map<String, Object> pmParam) {
        return dlwNewTypeMainConsDAO.getMemberNiceRetrieve2(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20181022
     * 이름 : 송준빈
     * 추가내용 : 특정회원의 청구 여부 확인
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
     * ================================================================
     * */
    public int getWdrwReqAccntConfirm(String accnt_no) {
        return dlwNewTypeMainConsDAO.getWdrwReqAccntConfirm(accnt_no);
    }
    
    /** ================================================================
     * 날짜 : 20181022
     * 이름 : 송준빈
     * 추가내용 : 출금이체의뢰내역(산출, 청구(행사, 해약)) 조회
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
     * ================================================================
     * */
    public List<Map<String, Object>> getWdrwReqAccntEventConfirm(String accnt_no) throws Exception {
        return dlwNewTypeMainConsDAO.getWdrwReqAccntEventConfirm(accnt_no);
    }
    
    /** ================================================================
     * 날짜 : 20190103
     * 이름 : 송준빈
     * 추가내용 : 만기연장 상품등록 팝업 조회
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_MANGI_GIFTSET
     * ================================================================
     * */
    public List<Map<String, Object>> getMemberMangiExtConfirmList(Map<String, Object> pmParam) throws Exception {
        return dlwNewTypeMainConsDAO.getMemberMangiExtConfirmList(pmParam);
    }
    
    /**
     * 대명라이프웨이의 Card 회원정보의 건수를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 고객 정보 건수
     * @throws Exception
     */
    public int getDlwCardCsmmCount(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeMainConsDAO.getDlwCardCsmmCount(pmParam);
    }
    
    /**
     * 대명라이프웨이의 Card 회원정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 고객 정보
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwCardCsmm(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeMainConsDAO.getDlwCardCsmm(pmParam);
    }
    
    /**
     * 대명라이프웨이의 Cms 회원정보의 건수를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 고객 정보 건수
     * @throws Exception
     */
    public int getDlwCmsCsmmCount(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeMainConsDAO.getDlwCmsCsmmCount(pmParam);
    }

    /**
     * 대명라이프웨이의 Cms 회원정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 고객 정보
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwCmsCsmm(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeMainConsDAO.getDlwCmsCsmm(pmParam);
    }

    /**
     * 발주정보를 조회한다.
     *
     * @param pmParam 회원정보
     * @return 발주정보
     * @throws Exception
     */
    public Map<String, Object> getOdrgInfo(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeMainConsDAO.getOdrgInfo(pmParam);
    }
    
    /**
     * 물류 정보(목록)를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 물류 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getDlvList(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeMainConsDAO.getDlvList(pmParam);
    }
    
    /**
     * 납입이력(상조부금) 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 납입이력(상조부금) 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwPayMngList(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeMainConsDAO.getDlwPayMngList(pmParam);
    }

    /**
     * 납입이력(결합상품할부원금) 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 납입이력(결합상품할부원금) 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwPayMngDtlList(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeMainConsDAO.getDlwPayMngDtlList(pmParam);
    }

    /**
     * 납입이력(결합상품추가부담금) 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 납입이력(결합상품추가부담금) 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwPayMngDtl1List(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeMainConsDAO.getDlwPayMngDtl1List(pmParam);
    }
    
    /**
     * 납입이력(상조부금 + 결합상품할부원금 + 결합상품추가부담금) 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 납입이력(상조부금 + 결합상품할부원금 + 결합상품추가부담금) 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwPayMngAllList(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeMainConsDAO.getDlwPayMngAllList(pmParam);
    }
    
    /**
     * 해약정보_상품정보 및 가입일자에 따른 SEQ, 적용일자를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 해약정보
     * @throws Exception
     */
    public Map<String, Object> getDlwResnAmtList(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeMainConsDAO.getDlwResnAmtList(pmParam);
    }
    
    /**
     * 대명라이프웨이 해약환급금 D 목록을 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 고객정보
     * @throws Exception
     */
    public List<Map<String, Object>> getResnAmtDetailList(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeMainConsDAO.getResnAmtDetailList(pmParam);
    }
    
    /**
     * 결제정보를 검색한다. (개별목록 신청내역 CMS+Card)
     *
     * @param pmParam 검색 조건
     * @return 결제정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwPymnHstrList(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeMainConsDAO.getDlwPymnHstrList(pmParam);
    }
    
    /**
     * 청구내역을 검색한다. (개별목록 청구내역 CMS+Card)
     *
     * @param pmParam 검색 조건
     * @return 청구내역 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwAskHstrList(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeMainConsDAO.getDlwAskHstrList(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20181113
     * 이름 : 송준빈
     * 추가내용 : 회원고객정보 탭 (청구이력)
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ, LF_DMUSER.TB_MEMBER_WDRW_RESULT
     * ================================================================
     * */
    public List<Map<String, Object>> getMainWdrwLogList(Map<String, Object> pmParam) {
        return dlwNewTypeMainConsDAO.getMainWdrwLogList(pmParam);
    }
    
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
        Map<String, Object> mCust = dlwNewTypeMainConsDAO.getCustBasiConsInfo(pmParam);
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

        nResult = dlwNewTypeMainConsDAO.insertCons(pmParam);

        if (nResult > 0) {
            sKey = (String) pmParam.get("consno");
            dlwNewTypeMainConsDAO.insertConsHstr(pmParam);
        }

        return sKey;
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
        return dlwNewTypeMainConsDAO.getConsGroup(mParam);
    }
    
    /**
     * 상담 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 상담 정보의 검색 건수
     * @throws Exception
     */
    public int getConsCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) dlwNewTypeMainConsDAO.getConsCount(pmParam);
    }
    
    /**
     * 엔컴 상담 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 엔컴 상담 정보의 검색 건수
     * @throws Exception
     */
    public int getDlwConsCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) dlwNewTypeMainConsDAO.getDlwConsCount(pmParam);
    }
    
    /**
     * 엔컴 상담 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 엔컴 상담 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwConsList(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeMainConsDAO.getDlwConsList(pmParam);
    }
    
    /**
     * 홈페이지 회원변경 이력 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 홈페이지 회원변경 정보의 검색 건수
     * @throws Exception
     */
    public int getDlwlifeweyCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) dlwNewTypeMainConsDAO.getDlwlifeweyCount(pmParam);
    }
    
    /**
     * 홈페이지 회원변경 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 홈페이지 회원변경 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwlifeweyList(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeMainConsDAO.getDlwlifeweyList(pmParam);
    }
    
	/**
     * 상품 검색한다. (NEWTYPE)
     *
     * @param pmParam 검색 조건
     * @return 고객-상품 정보
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwProductList(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeMainConsDAO.getDlwProductList(pmParam);
    }
    
    /**
     * 문자메세지를 전송한다.
     *
     * @param pmParam 문자메세지 목록
     * @throws Exception
     */
    public String insertSmsSend(Map<String, Object> pmParam) throws Exception {

        String sKey = "";
        int nResult = 0;

        nResult = dlwNewTypeMainConsDAO.insertSmsSend(pmParam);
        
        if (nResult > 0) {
            sKey = (String) pmParam.get("mt_pr");
        }
        return sKey;
    }
    
    /**
     * 문자전송 이력 정보를 등록한다.
     *
     * @param pmParam 문자 정송 이력 정보
     * @throws Exception
     */
    public String insertChatSndgHstr(Map<String, Object> pmParam) throws Exception {
        String sKey = "";

        dlwNewTypeMainConsDAO.insertChatSndgHstr(pmParam);

        return sKey;
    }
    
	/**
     * 회원 정보 중 마케팅 정보 조회
     * TB_MEMBER_MKT_INFO
     */
    public Map<String, Object> getDlwMarketingInfoList(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeMainConsDAO.getDlwMarketingInfoList(pmParam);
    }
    
    /**
     * 마케팅정보 등록
     *
     * @param pmParam 고객 정보
     * @throws Exception
     */
    public String insertMktInfo(Map<String, Object> pmParam) throws Exception {
        String sKey = "";
       
        int nResult = dlwNewTypeMainConsDAO.insertMktInfo(pmParam); // 대명 회원정보 등록
        
        if("01".equals(pmParam.get("gubun"))) {
        	pmParam.put("markt_agr_yn", "Y");
        	dlwNewTypeMainConsDAO.updateMemberMkt(pmParam);
        }
        
        if("02".equals(pmParam.get("gubun"))) {
        	pmParam.put("dm_yn", "Y");
        	dlwNewTypeMainConsDAO.updateMemberMkt(pmParam);
        }
		
        if("03".equals(pmParam.get("gubun"))) {
			pmParam.put("email_yn", "Y");
        	dlwNewTypeMainConsDAO.updateMemberMkt(pmParam);
		}
                       
        return sKey;
    }
    
    /**
     * 마케팅정보 삭제
     *
     * @param pmParam 고객 정보
     * @throws Exception
     */
    public String updateMktInfo(Map<String, Object> pmParam) throws Exception {
        String sKey = "";
       
        int nResult = dlwNewTypeMainConsDAO.updateMktInfo(pmParam); // 대명 회원정보 등록
        
        if("01".equals(pmParam.get("gubun"))) {
        	pmParam.put("markt_agr_yn", "N");
        	dlwNewTypeMainConsDAO.updateMemberMkt(pmParam);
        }
        
        if("02".equals(pmParam.get("gubun"))) {
        	pmParam.put("dm_yn", "N");
        	dlwNewTypeMainConsDAO.updateMemberMkt(pmParam);
        }
		
        if("03".equals(pmParam.get("gubun"))) {
			pmParam.put("email_yn", "N");
        	dlwNewTypeMainConsDAO.updateMemberMkt(pmParam);
		}
                       
        return sKey;
    }
    
	/**
     * 회원 정보 중 마케팅 히스토리 등록
     * TB_MEMBER_MKT_INFO
     */
    public  List<Map<String, Object>>  getDlwMarketinghstrList(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeMainConsDAO.getDlwMarketinghstrList(pmParam);
    }
    
    /**
     * 회원 정보 중 마케팅 정보 조회
     * TB_MEMBER_MKT_INFO
     */
    public Map<String, Object> getDlwMarketGroup(Map<String, ?> pmParam) throws Exception {
        return dlwNewTypeMainConsDAO.getDlwMarketGroup(pmParam);
    }
    
    /**
     * 마케팅정보 등록및 삭제
     *
     * @param pmParam 고객 정보
     * @throws Exception
     */
    public String mergeMktInfo(Map<String, Object> pmParam) throws Exception {
        String sKey = "";
       
        int nResult = dlwNewTypeMainConsDAO.mergeMktInfo(pmParam); // 대명 회원정보 등록
                       
        return sKey;
    }
    
    /**
     * 마케팅정보 갱신
     *
     * @param pmParam 고객 정보
     * @throws Exception
     */
    public String updateMktInfoRe(Map<String, Object> pmParam) throws Exception {
        String sKey = "";
        
        dlwNewTypeMainConsDAO.updateMktInfo(pmParam);
        dlwNewTypeMainConsDAO.insertMktInfo(pmParam);
        
        return sKey;
    }
}
