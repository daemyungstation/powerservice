/*
 * (@)# DlwCustServiceImpl.java
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/02/04
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import powerservice.business.cns.service.impl.CustBasiDAO;
import powerservice.business.dlw.service.DlwCustService;
import powerservice.common.util.CommonUtils;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

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
public class DlwCustServiceImpl extends EgovAbstractServiceImpl implements DlwCustService {

    @Resource
    public DlwCustDAO dlwCustDAO;

    @Resource
    public CustBasiDAO custBasiDAO;

    /**
     * 고객-상품 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 고객-상품 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwCustPrdtList(Map<String, ?> pmParam) throws Exception {

        CommonUtils.printLog("AAAAAAAAA"+pmParam);
        return dlwCustDAO.getDlwCustPrdtList(pmParam);
    }

    /**
     * 고객-구좌 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 고객-구좌 정보의 검색 건수
     * @throws Exception
     */
    public int getDlwCustAcntCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) dlwCustDAO.getDlwCustAcntCount(pmParam);
    }

    /**
     * 고객-구좌 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 고객-구좌 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwCustAcntList(Map<String, ?> pmParam) throws Exception {
        return dlwCustDAO.getDlwCustAcntList(pmParam);
    }

    /**
     * 고객-상품 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 고객-상품 정보
     * @throws Exception
     */
    public Map<String, Object> getDlwCustPrdtDtpt(Map<String, ?> pmParam) throws Exception {
        return dlwCustDAO.getDlwCustPrdtDtpt(pmParam);
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
        return dlwCustDAO.getDlwCustPrdtDtpt(pmParam);
    }

    /**
     * 온라인 가입회원 구좌번호로 계좌정보조회
     *
     * @param pmParam 검색 조건
     * @return 부서 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwOnlineMemberCmsInfo(Map<String, ?> pmParam) throws Exception {
        return dlwCustDAO.getDlwOnlineMemberCmsInfo(pmParam);
    }

    /**
     * 온라인 가입회원(결합상품) 구좌번호로 계좌정보조회
     *
     * @param pmParam 검색 조건
     * @return 부서 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwOnlineSSMemberCmsInfo(Map<String, ?> pmParam) throws Exception {
        return dlwCustDAO.getDlwOnlineSSMemberCmsInfo(pmParam);
    }

    /**
     * 온라인 가입회원 구좌번호로 카드정보조회
     *
     * @param pmParam 검색 조건
     * @return 부서 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwOnlineMemberCardInfo(Map<String, ?> pmParam) throws Exception {
        return dlwCustDAO.getDlwOnlineMemberCardInfo(pmParam);
    }

    /**
     * 온라인 가입회원(결합상품) 구좌번호로 카드정보조회
     *
     * @param pmParam 검색 조건
     * @return 부서 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwOnlineSSMemberCardInfo(Map<String, ?> pmParam) throws Exception {
        return dlwCustDAO.getDlwOnlineSSMemberCardInfo(pmParam);
    }

    /**
     * 납입정보(부가서비스)등록 회원을 체크한다.
     *
     * @param pmParam 검색 조건
     * @return 회원정보
     * @throws Exception
     */
    public Map<String, Object> getBugaSrvcMemChk(Map<String, ?> pmParam) throws Exception {
        return dlwCustDAO.getBugaSrvcMemChk(pmParam);
    }

    /**
     * 부가서비스(CMS/CARD) 신청구분을 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 신청구분 코드(1:신청, 3:해지, 7:임의해지)
     * @throws Exception
     */
    public String getBugaSrvcAppCl(Map<String, ?> pmParam) throws Exception {
        return dlwCustDAO.getBugaSrvcAppCl(pmParam);
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

        int iCnt = dlwCustDAO.updateMemberOCB(pmParam);
        if (iCnt > 0 && !"".equals(sMemNo)) {
            Map<String, Object> mDtpt = dlwCustDAO.getDlwMemberDtpt(sMemNo);
            custBasiDAO.insertCustBasiHstr(mDtpt);
        }

        return iCnt;
    }

    /**
     * 고객 정보를 검색한다. (상담테이블 설정)
     *
     * @param pmParam 고객ID
     * @return 고객 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getCustBasiConsInfo(Map<String, ?> pmParam) throws Exception {
        return dlwCustDAO.getCustBasiConsInfo(pmParam);
    }

    /**
     * 양도양수 정보 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertYdysMemTrans(Map<String, ?> pmParam) throws Exception {
        return dlwCustDAO.insertYdysMemTrans(pmParam);
    }

    /**
     * 회원상품정보 고유번호 변경
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateYdysMemProd(Map<String, ?> pmParam) throws Exception {
        return dlwCustDAO.updateYdysMemProd(pmParam);
    }

    /**
     * 상담내역 고유번호 변경
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateYdysClsl(Map<String, ?> pmParam) throws Exception {
        return dlwCustDAO.updateYdysClsl(pmParam);
    }

    /**
     * 정보변경 내역 변경
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateYdysChng(Map<String, ?> pmParam) throws Exception {
        return dlwCustDAO.updateYdysChng(pmParam);
    }

    /**
     * 고객별 실적통계 결과를 보여준다.
     *
     * @param pmParam 검색 조건
     * @return 고객-상품 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getAnalResultList(Map<String, ?> pmParam) throws Exception {

        return dlwCustDAO.getAnalResultList(pmParam);
    }


    /* ================================================================
     * 날짜 : 20180403
     * 이름 : 김찬영
     * 추가내용 : 쇼핑몰 연동 Table 에 고객의 상태를 양도로 변경
     * ================================================================
     * */
    public int updateResnMemberState(Map<String, ?> pmParam) throws Exception {
        return dlwCustDAO.updateResnMemberState(pmParam);
    }



}
