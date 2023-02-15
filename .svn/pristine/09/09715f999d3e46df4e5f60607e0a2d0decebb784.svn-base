/*
 * (@)# DlwEvntDAO.java
 *
 * @author 석민
 * @version 1.0
 * @date 2016/03/30
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

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 대명라이프웨이 행사 정보를 관리한다
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 석민
 * @version 1.0
 * @date 2016/03/30
 * @프로그램ID DlwEvnt
 */
@Repository
public class DlwEvntReceiptDAO extends EgovAbstractMapper {

    /**
     * 대명라이프웨이 DB SqlSession을 설정한다.
     *
     * @param sqlSession 대명라이프웨이 DB SqlSession
     */
    @Resource(name="dlwSqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }

    /**
     * 대명라이프웨이 행사현황 건수 조회
     *
     * @param pmParam 검색 조건
     * @return 행사현황 정보 건수
     * @throws Exception
     */
    public int getEventReceiptCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwEvntReceiptMap.getEventReceiptCount", pmParam);
    }

    /**
     * 대명라이프웨이 행사현황 조회
     *
     * @param pmParam 검색 조건
     * @return 행사현황 정보
     * @throws Exception
     */
    public List<Map<String, Object>> getEventReceiptList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntReceiptMap.getEventReceiptList", pmParam);
    }
    
    /**
     * 대명라이프웨이 행사등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertEventReceipt(Map<String, ?> pmParam) throws Exception {
        return insert("DlwEvntReceiptMap.insertEventReceipt", pmParam);
    }

    /**
     * 대명라이프웨이 행사수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateEventReceipt(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntReceiptMap.updateEventReceipt", pmParam);
    }
    
    /**
     * 대명라이프웨이 행사삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteEventReceipt(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntReceiptMap.deleteEventReceipt", pmParam);
    }
    
    /**
     * 대명라이프웨이 행사등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertEventReceiptComplete(Map<String, ?> pmParam) throws Exception {
        return insert("DlwEvntReceiptMap.insertEventReceiptComplete", pmParam);
    }
    
    /**
     * 행사 등록시 해당 회원번호로 행사테이블 조회
     *
     * @param hmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int countEventReceipt(Map<String, Object> hmParam) throws Exception {
        return selectOne("DlwEvntReceiptMap.countEventReceipt", hmParam);
    }
    
    /**
     * 행사자 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertevntMngr(Map<String, ?> pmParam) throws Exception {
        return insert("DlwEvntReceiptMap.insertevntMngr", pmParam);
    }
    
    /**
     * 대명라이프웨이 행사현황 조회
     *
     * @param pmParam 검색 조건
     * @return 행사현황 정보
     * @throws Exception
     */
    public List<Map<String, Object>> getEventSeq(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntReceiptMap.getEventSeq", pmParam);
    }
    
    /**
     * 고객-구좌 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 부서 정보의 검색 건수
     * @throws Exception
     */
    public int getDlwCustAcntCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwEvntReceiptMap.getDlwCustAcntCount", pmParam);
    }

    /**
     * 고객-구좌 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 부서 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwCustAcntList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntReceiptMap.getDlwCustAcntList", pmParam);
    }
    
    /**
     * 대명라이프웨이 행사취소사유 조회
     *
     * @param pmParam 검색 조건
     * @return 행사현황 정보
     * @throws Exception
     */
    public List<Map<String, Object>> getEventCancelList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntReceiptMap.getEventCancelList", pmParam);
    }
    
    /**
     * 대명라이프웨이 행사취소사유 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertEventCancel(Map<String, ?> pmParam) throws Exception {
        return insert("DlwEvntReceiptMap.insertEventCancel", pmParam);
    }
    
    /**
     * APP푸쉬알림시 메시지 정보 가져오기
     * 임동진 20190327
     * @param pmParam 검색 조건
     * @return MO수신 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getSendPushInfo(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntReceiptMap.getSendPushInfo", pmParam);
    }
    
    /**
     * 대명라이프웨이 지역 공통코드 조회
     *
     * @param pmParam 검색 조건
     * @return 행사현황 정보
     * @throws Exception
     */
    public List<Map<String, Object>> getComCd(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntReceiptMap.getComCd", pmParam);
    }
    
    /**
     * 대명라이프웨이 행사자 조회
     *
     * @param pmParam 검색 조건
     * @return 행사현황 정보
     * @throws Exception
     */
    public List<Map<String, Object>> getManagerList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntReceiptMap.getManagerList", pmParam);
    }
    
    /**
     * 행사접수완료
     * 김주용
     * 20210928
    */
    public List<Map<String, Object>> eventReceiptComplete(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntReceiptMap.eventReceiptComplete", pmParam);
    }
    
    /**
     * 대명라이프웨이 콜현황 조회
     *
     * @param pmParam 검색 조건
     * @return 행사현황 정보
     * @throws Exception
     */
    public List<Map<String, Object>> getEventCallList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntReceiptMap.getEventCallList", pmParam);
    }
    
    
    
    
    
    
    
    
    
    
    public int getEventMainCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwEvntReceiptMap.getEventMainCount", pmParam);
    }

    public List<Map<String, Object>> getEventMainList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntReceiptMap.getEventMainList", pmParam);
    }
    
    public List<Map<String, Object>> getEventDetailList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntReceiptMap.getEventDetailList", pmParam);
    }
    
    public List<Map<String, Object>> getSuppliesClsfcDtlList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntReceiptMap.getSuppliesClsfcDtlList", pmParam);
    }
    
    public List<Map<String, Object>> getAddSales1List(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntReceiptMap.getAddSales1List", pmParam);
    }
    
    public List<Map<String, Object>> getAddSales2List(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntReceiptMap.getAddSales2List", pmParam);
    }
    
    public List<Map<String, Object>> getPaymentInfoList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntReceiptMap.getPaymentInfoList", pmParam);
    }
    
    public int updateEventBasicInfo(Map<String, ?> pmParam) {
        return update("DlwEvntReceiptMap.updateEventBasicInfo", pmParam);
    }
    
    public int insertEventBasicInfo(Map<String, ?> pmParam) {
        return insert("DlwEvntReceiptMap.insertEventBasicInfo", pmParam);
    }
    
    public int updateCancelEvent(Map<String, ?> pmParam) {
        return update("DlwEvntReceiptMap.updateCancelEvent", pmParam);
    }
    
    public int updateSuppliesClsfcDtl(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntReceiptMap.updateSuppliesClsfcDtl", pmParam);
    }
    
    public int insertSuppliesClsfcDtl(Map<String, ?> pmParam) throws Exception {
        return insert("DlwEvntReceiptMap.insertSuppliesClsfcDtl", pmParam);
    }
    
    public int deleteSuppliesClsfcDtl(Map<String, ?> pmParam) throws Exception {
        return delete("DlwEvntReceiptMap.deleteSuppliesClsfcDtl", pmParam);
    }
    
    public int updateAddSales1(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntReceiptMap.updateAddSales1", pmParam);
    }
    
    public int insertAddSales1(Map<String, ?> pmParam) throws Exception {
        return insert("DlwEvntReceiptMap.insertAddSales1", pmParam);
    }
    
    public int deleteAddSales1(Map<String, ?> pmParam) throws Exception {
        return delete("DlwEvntReceiptMap.deleteAddSales1", pmParam);
    }
    
    public int updateAddSales2(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntReceiptMap.updateAddSales2", pmParam);
    }
    
    public int insertAddSales2(Map<String, ?> pmParam) throws Exception {
        return insert("DlwEvntReceiptMap.insertAddSales2", pmParam);
    }
    
    public int deleteAddSales2(Map<String, ?> pmParam) throws Exception {
        return delete("DlwEvntReceiptMap.deleteAddSales2", pmParam);
    }
    
    public int updatePaymentInfo(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntReceiptMap.updatePaymentInfo", pmParam);
    }
    
    public int insertPaymentInfo(Map<String, ?> pmParam) throws Exception {
        return insert("DlwEvntReceiptMap.insertPaymentInfo", pmParam);
    }
    
    public int deletePaymentInfo(Map<String, ?> pmParam) throws Exception {
        return delete("DlwEvntReceiptMap.deletePaymentInfo", pmParam);
    }
    
    public int confirmRegFunrlOutsrcMst(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwEvntReceiptMap.confirmRegFunrlOutsrcMst", pmParam);
    }
    
    public int insertRegFunrlOutsrcMst(Map<String, ?> pmParam) throws Exception {
        return insert("DlwEvntReceiptMap.insertRegFunrlOutsrcMst", pmParam);
    }
    
    public List<Map<String, Object>> getEventMngrList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntReceiptMap.getEventMngrList", pmParam);
    }
    
    public List<Map<String, Object>> getMngrPayList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntReceiptMap.getMngrPayList", pmParam);
    }
    
    public List<Map<String, Object>> getMemConsList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntReceiptMap.getMemConsList", pmParam);
    }
    
    public int insertMemConsList(Map<String, ?> pmParam) {
        return insert("DlwEvntReceiptMap.insertMemConsList", pmParam);
    }
    
    public List<Map<String, Object>> getProtocolMngrHistList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntReceiptMap.getProtocolMngrHistList", pmParam);
    }
    
    public List<Map<String, Object>> getMemberAccntDtlList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntReceiptMap.getMemberAccntDtlList", pmParam);
    }
    
    public List<Map<String, Object>> getPayTotList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntReceiptMap.getPayTotList", pmParam);
    }
    
    public int updateEventMngrInfo(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntReceiptMap.updateEventMngrInfo", pmParam);
    }
    
    public int insertEventMngrInfo(Map<String, ?> pmParam) throws Exception {
        return insert("DlwEvntReceiptMap.insertEventMngrInfo", pmParam);
    }
    
    public int deleteEventMngrInfo(Map<String, ?> pmParam) throws Exception {
        return update("DlwEvntReceiptMap.deleteEventMngrInfo", pmParam);
    }
    
    public int getCustAccntCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwEvntReceiptMap.getCustAccntCount", pmParam);
    }

    public List<Map<String, Object>> getCustAccntList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntReceiptMap.getCustAccntList", pmParam);
    }
    
    public int getProtocolSynthesisCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwEvntReceiptMap.getProtocolSynthesisCount", pmParam);
    }

    public List<Map<String, Object>> getProtocolSynthesisList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEvntReceiptMap.getProtocolSynthesisList", pmParam);
    }

	public List<Map<String, Object>> getBranchList(Map<String, ?> pmParam) {
		return selectList("DlwEvntReceiptMap.getBranchList", pmParam);
	}
}
