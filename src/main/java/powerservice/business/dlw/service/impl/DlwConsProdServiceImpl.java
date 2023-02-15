/*
 * (@)# DlwConsProdServiceImpl.java
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/03/08
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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import powerservice.business.cns.service.impl.MemberAddrDtlDAO;
import powerservice.business.dlw.service.DlwConsProdService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 상담-상품 정보를 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/03/08
 * @프로그램ID DlwConsProd
 */
@Service
public class DlwConsProdServiceImpl extends EgovAbstractServiceImpl implements DlwConsProdService {

    private final Logger LOGGER = LoggerFactory.getLogger(DlwConsProdServiceImpl.class);

    @Resource
    public DlwConsProdDAO dlwConsProdDAO;

    @Resource
    public DlwCmsDAO dlwCmsDAO;

    @Resource
    public MemberAddrDtlDAO memberAddrDtlDAO;

    @Resource
    public DlwCustDAO dlwCustDAO;

    /**
     * 상담-상품 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상담-상품 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwConsProdList(Map<String, ?> pmParam) throws Exception {

        return dlwConsProdDAO.getDlwConsProdList(pmParam);
    }

    /**
     * 상담-상품 모델분류 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 모델분류 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwModlMstInfo(Map<String, ?> pmParam) throws Exception {
        return dlwConsProdDAO.getDlwModlMstInfo(pmParam);
    }

    /**
     * 상담-상품 모델명 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 모델명 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwModlDtlInfo(Map<String, ?> pmParam) throws Exception {
        return dlwConsProdDAO.getDlwModlDtlInfo(pmParam);
    }

    /**
     * 상담-상품 상품종류 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상품종류 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwProdKindList(Map<String, ?> pmParam) throws Exception {
        return dlwConsProdDAO.getDlwProdKindList(pmParam);
    }

    /**
     * 회원 상품정보를 검색한다.
     *
     * @param pmParam 회원정보
     * @return 회원 상품 정보
     * @throws Exception
     */
    public Map<String, Object> getProdInfoInq(Map<String, ?> pmParam) throws Exception {
        return dlwConsProdDAO.getProdInfoInq(pmParam);
    }

    /**
     * 회원 상품정보를 검색한다.(메인화면에서 해약금 조회 2018.01.30 김찬영
     *
     * @param pmParam 회원정보
     * @return 회원 상품 정보
     * @throws Exception
     */
    public Map<String, Object> getProdInfoInqNew(Map<String, ?> pmParam) throws Exception {
        return dlwConsProdDAO.getProdInfoInqNew(pmParam);
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
        return dlwConsProdDAO.getNowMonthCount(pmParam);
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
        return dlwConsProdDAO.getResnAmt(pmParam);
    }

    /**
     *
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getProdSrvcHistForMPAS(Map<String, ?> pmParam) throws Exception {
        return dlwConsProdDAO.getProdSrvcHistForMPAS(pmParam);
    }

    /**
     * 상품-리조트회원에 따른 부가서비스 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 부가서비스 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getProdSrvcMstList(Map<String, ?> pmParam) throws Exception {
        return dlwConsProdDAO.getProdSrvcMstList(pmParam);
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

        return dlwConsProdDAO.validateDiscntPassNo(pmParam);
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

        return dlwConsProdDAO.selectNewChanGunsuDPM(pmParam);
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

        return dlwConsProdDAO.getResortInfo(pmParam);
    }

    /**
     * B2B회사 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return B2B회사 정보의 검색 건수
     * @throws Exception
     */
    public int getB2bCompCount(Map<String, ?> pmParam) throws Exception {
       return dlwConsProdDAO.getB2bCompCount(pmParam);
    }

    /**
     * B2B회사 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return B2B회사 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getB2bCompList(Map<String, ?> pmParam) throws Exception {
        return dlwConsProdDAO.getB2bCompList(pmParam);
    }

    /**
     * 매입업체(세금계산서)회사 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return B2B회사 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getPurCompList(Map<String, ?> pmParam) throws Exception {
        return dlwConsProdDAO.getPurCompList(pmParam);
    }

    /**
     * 삼성매장 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 삼성매장 정보의 검색 건수
     * @throws Exception
     */
    public int getSmShopCount(Map<String, ?> pmParam) throws Exception {
       return dlwConsProdDAO.getSmShopCount(pmParam);
    }

    /**
     * 삼성매장 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 삼성매장 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getSmShopList(Map<String, ?> pmParam) throws Exception {
        return dlwConsProdDAO.getSmShopList(pmParam);
    }

    /**
     * 2구좌 가입 제한 체크
     *
     * @param pmParam 검색 조건
     * @return 체크 결과
     * @throws Exception
     */
    public String getNoSaleAccnt(Map<String, ?> pmParam) throws Exception {
        return dlwConsProdDAO.getNoSaleAccnt(pmParam);
    }

    /**
     * 전자서명 상태값 [00(인증값)_00(상품계약서 상태값)]
     *
     * @param pmParam 검색 조건
     * @return 해피콜 승인여부
     * @throws Exception
     */
    public Map<String, Object> getNiceAuthStat(Map<String, ?> pmParam) throws Exception {
        return dlwConsProdDAO.getNiceAuthStat(pmParam);
    }


    /**
     * 카드 코드 조회
     *
     * @param pmParam 검색 조건
     * @return 카드 코드
     * @throws Exception
     */
    public String getCardCode(String psParam) throws Exception {
        return dlwConsProdDAO.getCardCode(psParam);
    }

    /**
     * 회원번호 생성
     *
     * @param pmParam 검색 조건
     * @return 회원번호
     * @throws Exception
     */
    public String createAccntNo(String psParam) throws Exception {
        return dlwConsProdDAO.createAccntNo(psParam);
    }

    /**
     * 회원_상품_계좌 등록
     *
     * @param pmParam 검색 조건
     * @return 회원번호
     * @throws Exception
     */
    public int insertMemProdAccnt(Map<String, Object> pmParam) throws Exception {
        String sAccntNo = StringUtils.defaultString((String) pmParam.get("accnt_no"));
        //String sMembership = StringUtils.defaultString((String) pmParam.get("membership"));
        String sHpclStatCd = StringUtils.defaultString((String) pmParam.get("hpcl_stat_cd"));	// 해피콜 상담상태
        String sResortNo = "";
        String sInsplZip = StringUtils.defaultString((String)pmParam.get("inspl_zip")); // 우편번호
        int nResult = 0;

        nResult = dlwConsProdDAO.insertMemProdAccnt(pmParam);

        if (nResult > 0) {
            if (!"".equals(sHpclStatCd)) {	// 해피콜 등록
                String chkTyp = dlwConsProdDAO.getHpclAckdStatChk(pmParam);	// 승인상태 체크
                if (!"A".equals(chkTyp)) {
                    if ("I".equals(chkTyp)) {
                        dlwConsProdDAO.insertHpclAckd(pmParam);
                    } else if ("U".equals(chkTyp)) {
                        dlwConsProdDAO.updateHpclAckd(pmParam);
                    }
                    pmParam.put("hpcl_note", "단건승인");
                    dlwConsProdDAO.insertHpclHist(pmParam);
                }
            }

            sResortNo = dlwConsProdDAO.getResortNo(sAccntNo);
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
                    nExistCount = memberAddrDtlDAO.getMemberAddrDtlExistCount(pmParam);
                } catch (Exception exception) {
                    LOGGER.error(exception.getMessage());
                }
                // 설치장소 고객 주소 정보 저장
                int nResultMemberAddrDtl = memberAddrDtlDAO.mergeMemberAddrDtl(pmParam);
                // 고객 주소 정보가 변경된 경우 고객 주소 이력 저장
                if (nResultMemberAddrDtl > 0 && nExistCount == 0) {
                    try {
                        pmParam.put("member_addr_dtl_merge_yn", "Y");
                        memberAddrDtlDAO.insertMemberAddrDtlHstr(pmParam);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
        }
        return nResult;
    }

    /**
     * 회원_상품_계좌 수정
     *
     * @param pmParam 검색 조건
     * @return 회원번호
     * @throws Exception
     */
    public int updateMemProdAccnt(Map<String, Object> pmParam) throws Exception {
        String sAccntNo = StringUtils.defaultString((String) pmParam.get("accnt_no"));
        //String sMembership = StringUtils.defaultString((String) pmParam.get("membership"));
        String sResortNo = StringUtils.defaultString((String) pmParam.get("resort_no"));
        String sHpclStatCd = StringUtils.defaultString((String) pmParam.get("hpcl_stat_cd"));	// 해피콜 상담상태
        String sInsplZip = StringUtils.defaultString((String)pmParam.get("inspl_zip")); // 우편번호
        int nResult = 0;

        nResult = dlwConsProdDAO.updateMemProdAccnt(pmParam);

        if (nResult > 0) {
            if (!"".equals(sHpclStatCd)) {	// 해피콜 등록
                String chkTyp = dlwConsProdDAO.getHpclAckdStatChk(pmParam);	// 승인상태 체크
                if (!"A".equals(chkTyp)) {
                    if ("I".equals(chkTyp)) {
                        dlwConsProdDAO.insertHpclAckd(pmParam);
                    } else if ("U".equals(chkTyp)) {
                        dlwConsProdDAO.updateHpclAckd(pmParam);
                    }
                    pmParam.put("hpcl_note", "단건승인");
                    dlwConsProdDAO.insertHpclHist(pmParam);
                }
            }

            sResortNo = dlwConsProdDAO.getResortNo(sAccntNo);
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
                        nExistCount = memberAddrDtlDAO.getMemberAddrDtlExistCount(pmParam);
                    } catch (Exception exception) {
                        LOGGER.error(exception.getMessage());
                    }
                    // 설치장소 고객 주소 정보 저장
                    int nResultMemberAddrDtl = memberAddrDtlDAO.mergeMemberAddrDtl(pmParam);
                    // 고객 주소 정보가 변경된 경우 고객 주소 이력 저장
                    if (nResultMemberAddrDtl > 0 && nExistCount == 0) {
                        try {
                            pmParam.put("member_addr_dtl_merge_yn", "Y");
                            memberAddrDtlDAO.insertMemberAddrDtlHstr(pmParam);
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                }
            }
        }

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
        return dlwConsProdDAO.insertSmartLifeCnslMng(pmParam);
    }

    /**
     * 부가서비스 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertMemProdAccntSvc(Map<String, ?> pmParam) throws Exception {
        return dlwConsProdDAO.insertMemProdAccntSvc(pmParam);
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
        return dlwConsProdDAO.deleteMemProdAccntSvc(pmParam);
    }

    /**
     * 스마트라이프 카드정보 조회
     *
     * @param pmParam 검색 조건
     * @return 스마트라이프 카드정보
     * @throws Exception
     */
    public Map<String, Object> getSmartLifeCardInfo(String psParam) throws Exception {
        Map<String, Object> pmParam = new HashMap<String, Object>();
        pmParam.put("accnt_no", psParam);

        return dlwConsProdDAO.getSmartLifeCardInfo(pmParam);
    }

    /**
     * 조회이력 저장
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertSearchHist(Map<String, ?> pmParam) throws Exception {
        return dlwConsProdDAO.insertSearchHist(pmParam);
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
            List<Map<String, Object>> ProdList = dlwConsProdDAO.selectCountProd(pmParam);
            // 회원번호의 갯수만큼 for문 돌면서 인서트.
            for (int i = 0; i < ProdList.size(); i++) {
                // 변수에 회원번호를 넣어준다.
                pmParam.put("accnt_no", StringUtils.defaultString((String) ProdList.get(i).get("ACCNT_NO")));
                // 인서트
                nResult = nResult+ dlwConsProdDAO.insertReqUpdDelInf(pmParam);
            }
            //
            if (ProdList.size() != nResult){
                nResult = -1;
            }
        } else {
            nResult = dlwConsProdDAO.insertReqUpdDelInf(pmParam);
        }



        if (nResult > 0) {
            if ("D".equals(sCl1)) {  // 삭제
                // 삭제여부 수정
                dlwConsProdDAO.delFlagMemProdAccnt(pmParam);
                dlwConsProdDAO.delFlagPayMng(pmParam);
                dlwConsProdDAO.delFlagRescission(pmParam);
                dlwConsProdDAO.delFlagCnclBrkdnMng(pmParam);
                dlwConsProdDAO.delFlagEvent(pmParam);

                dlwConsProdDAO.delFlagTaxtProc(pmParam);
                dlwConsProdDAO.delFlagCmsMemb(pmParam);
                dlwConsProdDAO.delFlagMstrChgInf(pmParam);
                dlwConsProdDAO.delFlagGasuAmtReg(pmParam);
                dlwConsProdDAO.delFlagDcAmtReg(pmParam);

                dlwConsProdDAO.delFlagCardMemb(pmParam);
                dlwCmsDAO.deleteDlwCmsMmbr(pmParam);

                //memProdAccntDAO.delFlagMemProdAccnt(pmParam);
            }
        }

        return nResult;
    }

    /**
     * CMS 출금이체 신청중인지 체크
     *
     * @param pmParam 검색 조건
     * @throws Exception
     */
    public String getCmsWdrwReqChk(String psParam) throws Exception {
        return dlwConsProdDAO.getCmsWdrwReqChk(psParam);
    }

    /**
     * 카드 출금이체 신청중인지 체크
     *
     * @param pmParam 검색 조건
     * @throws Exception
     */
    public String getCardWdrwReqChk(String psParam) throws Exception {
        return dlwConsProdDAO.getCardWdrwReqChk(psParam);
    }

    /**
     * 카드 출금이체 신청중인지 체크
     *
     * @param pmParam 검색 조건
     * @throws Exception
     */
    public String getPayChk(String psParam) throws Exception {
        return dlwConsProdDAO.getPayChk(psParam);
    }

    /**
     * 발주정보를 조회한다.
     *
     * @param pmParam 회원정보
     * @return 발주정보
     * @throws Exception
     */
    public Map<String, Object> getOdrgInfo(Map<String, ?> pmParam) throws Exception {
        return dlwConsProdDAO.getOdrgInfo(pmParam);
    }

    /**
     *
     *
     * @param pmParam 회원정보
     * @return
     * @throws Exception
     */
    public Map<String, Object> getTotCondQury(Map<String, ?> pmParam) throws Exception {
        return dlwConsProdDAO.getTotCondQury(pmParam);
    }

    /**
     * 해피콜 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 해피콜 정보의 검색 건수
     * @throws Exception
     */
    public int getHpCallCnt(Map<String, ?> pmParam) throws Exception {
       return dlwConsProdDAO.getHpCallCnt(pmParam);
    }

    /**
     * 해피콜 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 해피콜 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getHpCallList(Map<String, ?> pmParam) throws Exception {
        return dlwConsProdDAO.getHpCallList(pmParam);
    }

    /**
     * 해피콜을 일괄승인한다.
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public void saveHpclBndeAckd(Map<String, Object> pmParam) throws Exception {
        String[] sAccntNoList = StringUtils.defaultString((String) pmParam.get("selectcheck")).split(",");
        int chkCnt = 0;

        if (sAccntNoList != null) {
            for (String sAccntNo : sAccntNoList) {
                pmParam.put("accnt_no", sAccntNo);
                pmParam.put("hpcl_note", "일괄승인");
                pmParam.put("hpcl_stat_cd", "01");

                chkCnt = dlwConsProdDAO.getHpCallCount(sAccntNo);
                if (chkCnt > 0) {
                    dlwConsProdDAO.updateHpclAckd(pmParam);
                } else {
                    dlwConsProdDAO.insertHpclAckd(pmParam);
                }
                dlwConsProdDAO.insertHpclHist(pmParam);
            }
        }
    }

    /**
     * 종합현황 목록
     * - ASIS 에서 3개의 메소드가 있었으나 모두 삭제하고 이 메소드로 대체
     * - 삭제 메소드 getTotCondListLv1, getTotCondListLv2, getTotCondListLv3
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getTotCondListLv(Map<String, Object> pmParam) throws Exception {
        return dlwConsProdDAO.getTotCondListLv(pmParam);
    }

    /**
     * 발주목록데이터 체크
     * */
    public int getdeliveryCnt(Map<String, ?> pmParam) throws Exception {
        return dlwConsProdDAO.getHpCallCnt(pmParam);
     }


    /**
     * 발주관리  설치장소 업데이트
     *
     * @param pmParam 검색 조건
     * @return 회원번호
     * @throws Exception
     */
    public int updatedelivery(Map<String, Object> pmParam) throws Exception {
        return  dlwConsProdDAO.updatedelivery(pmParam);
    }
    
    /**
     * 두구좌 계정 정보저장
     *
     * @param pmParam 검색 조건
     * @return 회원번호
     * @throws Exception
     */
    public int insertMemTwinAccnt(Map<String, Object> pmParam) throws Exception {
        return dlwConsProdDAO.insertMemTwinAccnt(pmParam);
    }
}
