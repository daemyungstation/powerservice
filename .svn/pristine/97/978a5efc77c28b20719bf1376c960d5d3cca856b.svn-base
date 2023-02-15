/*
 * (@)# UnpyMngeDAO.java
 *
 * @author 정훈
 * @version 1.0
 * @date 2016/07/14
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

package powerservice.business.cam.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 원장테이블
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정훈
 * @version 1.0
 * @date 2016/07/14
 * @프로그램ID UnpyMnge
 */

@Repository
public class UnpyMngeDAO extends EgovAbstractMapper {

    /**
     *  원장 테이블 저장한다.
     *
     * @param param 캠페인 정보
     * @throws Exception
     */
    public int insertUnpy(Map<String, Object> pmParam) throws Exception {
        return insert("UnpyMngeMap.insertUnpy", pmParam);
    }

    /**
     * 당월미납 검색 수를 반환한다.((당월 미납, 1회미납))
     *
     * @param pmParam 검색 조건
     * @return 대상고객추출조건 정보의 검색 수
     * @throws Exception
     */
    public int getSmsUnpyCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("UnpyMngeMap.getSmsUnpyCount", pmParam);
    }

    /**
     * 당월미납 검색 수를 반환한다.((당월 미납, 1회미납))
     *
     * @param pmParam 검색 조건
     * @return 대상고객추출조건 정보의 검색 수
     * @throws Exception
     */
    public int getMonthUnpyCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("UnpyMngeMap.getMonthUnpyCount", pmParam);
    }

    /**
     * 당월미납 1회미납 반환한다.((당월 미납, 1회미납))
     *
     * @param pmParam 검색 조건
     * @return 대상고객추출조건 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getSmsUnpy(Map<String, ?> pmParam) throws Exception {
        return selectList("UnpyMngeMap.getSmsUnpy", pmParam);
    }
    /**
     * 원장 테이블 정보를 검색한다.(장기단기 (당월제외))
     *
     * @param pmParam 검색 조건
     * @return 대상고객추출조건 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getUnpy(Map<String, ?> pmParam) throws Exception {
        return selectList("UnpyMngeMap.getUnpy", pmParam);
    }

    /**
     * 신규 미납 데이터 조회 20190103
     * 임동진 등록
     * @param pmParam 검색 조건
     * @return 대상고객추출조건 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getMemYenche(Map<String, ?> pmParam) throws Exception {
        return selectList("UnpyMngeMap.getMemYenche", pmParam);
    }

    /**
     * 대상고객추출 정보의 검색 수를 반환한다.(장기단기 (당월제외))
     *
     * @param pmParam 검색 조건
     * @return 대상고객추출조건 정보의 검색 수
     * @throws Exception
     */
    public int getUnpyCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("UnpyMngeMap.getUnpyCount", pmParam);
    }

    /**
     * 신규 미납 데이터 조회 20190103
     * 임동진 등록
     * @param pmParam 검색 조건
     * @return 대상고객추출조건 정보의 검색 수
     * @throws Exception
     */
    public int getMemYenche_cnt(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("UnpyMngeMap.getMemYenche_cnt", pmParam);
    }


    /**
     * 원장 테이블의 정보 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 원장 테이블 정보의 검색 수
     * @throws Exception
     */
    public int getUnpyMngeCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("UnpyMngeMap.getUnpyMngeCount", pmParam);
    }

    /**
     * 원장 테이블의 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 원장테이블 정보 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getUnpyMngeList(Map<String, ?> pmParam) throws Exception {
        return selectList("UnpyMngeMap.getUnpyMngeList", pmParam);
    }

    /**
     * 원장 테이블의 제외건수를 업데이트 한다.(상품)
     * @param pmParam 검색 조건
     * @return 대상고객추출조건 리스트
     * @throws Exception
     */
    public int updateExcdProd(Map<String, ?> pmParam) throws Exception {
        return update("UnpyMngeMap.updateExcdProd", pmParam);
    }

    /**
     * 원장 테이블의 제외건수를 업데이트 한다.(회원번호)
     * @param pmParam 검색 조건
     * @return 대상고객추출조건 리스트
     * @throws Exception
     */
    public int updateExcdAccnt(Map<String, ?> pmParam) throws Exception {
        return update("UnpyMngeMap.updateExcdAccnt", pmParam);
    }

    /**
     * 원장 테이블의 제외건수를 업데이트 한다.(상품 Or 회원번호)
     * @param pmParam 검색 조건
     * @return 대상고객추출조건 리스트
     * @throws Exception
     */
    public int updateExcdAccntAndExcdProd(Map<String, ?> pmParam) throws Exception {
        return update("UnpyMngeMap.updateExcdAccntAndExcdProd", pmParam);
    }

    // 작성자 : 정훈연구원
    // 요청자 : 정성안팀장
    // 요청이유 : 미납의 현재 내야할 금액을 알고싶다.
    // 이슈사항 : 비채권형 - 월납액 * 연체횟수, 채권형 36회 이하 - (월납액+추가부담금) * 연체횟수 ,
    // 채권형 36회 초과 - 월납액 * 연체횟수 ,해약 - (추가부담금+결합월납)* 연체횟수

    /**
     * 원장 테이블의 분배액을 업데이트 한다.
     * @param pmParam 검색 조건
     * @return 대상고객추출조건 리스트
     * @throws Exception
     */
    public int updateAltnAmt (Map<String, ?> pmParam) throws Exception {
        return update("UnpyMngeMap.updateAltnAmt", pmParam);
    }


    /**
     * 원장 테이블의 정보를 검색한다.(제외대상 제외건들)
     *
     * @param pmParam 검색 조건
     * @return 원장테이블 정보 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getOrgUnpyMngeList(Map<String, ?> pmParam) throws Exception {
        return selectList("UnpyMngeMap.getOrgUnpyMngeList", pmParam);
    }


    /**
     * 원장 테이블의 정보를 검색한다.(제외대상 제외건들)
     *
     * @param pmParam 검색 조건
     * @return 원장테이블 정보 리스트
     * @throws Exception
     */
    public int updateExcdB2bComp(Map<String, ?> pmParam) throws Exception {
        return update("UnpyMngeMap.updateExcdB2bComp", pmParam);
    }


    /**
     * 통계 - 결제일 기준의 결제방법, 채권구분별 건수를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 미납통계리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getUnpyMngeReportWithIchaeDtCnt(Map<String, ?> pmParam) throws Exception {
        return selectList("UnpyMngeMap.getUnpyMngeReportWithIchaeDtCnt", pmParam);
    }

    /**
     * 통계 - 결제일 기준의 결제방법, 채권구분별 건수, 금액합계를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 미납통계리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getUnpyMngeReportWithIchaeDtPrice(Map<String, ?> pmParam) throws Exception {
        return selectList("UnpyMngeMap.getUnpyMngeReportWithIchaeDtPrice", pmParam);
    }


    /**
     * 통계 - 금일기준의 분배, 회수정보 합계를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 미납통계리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getUnpyMngeReportWithTotal(Map<String, ?> pmParam) throws Exception {
        return selectList("UnpyMngeMap.getUnpyMngeReportWithTotal", pmParam);
    }

    /**
     * 통계 - 금일기준의 캠페인할당된 상담사별 분배, 회수정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 미납통계리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getUnpyMngeReportWithCnsr(Map<String, ?> pmParam) throws Exception {
        return selectList("UnpyMngeMap.getUnpyMngeReportWithCnsr", pmParam);
    }

    /**
     * 통계 - 상품구분별 전체, 회수정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 미납통계리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getUnpyMngeReportWithProdGbnCdTotal(Map<String, ?> pmParam) throws Exception {
        return selectList("UnpyMngeMap.getUnpyMngeReportWithProdGbnCdTotal", pmParam);
    }

    /**
     * 통계 - 상품구분별 분배, 회수정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 미납통계리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getUnpyMngeReportWithProdGbnCd(Map<String, ?> pmParam) throws Exception {
        return selectList("UnpyMngeMap.getUnpyMngeReportWithProdGbnCd", pmParam);
    }

    /**
     * 통계 - 연체회차별 분배, 회수 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 미납통계리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getUnpyMngeReportWithPayState(Map<String, ?> pmParam) throws Exception {
        return selectList("UnpyMngeMap.getUnpyMngeReportWithPayState", pmParam);
    }

    /**
     * 통계 - 연체회차별 납입액정보, 실적정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 미납통계리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getUnpyMngeReportWithPayState2(Map<String, ?> pmParam) throws Exception {
        return selectList("UnpyMngeMap.getUnpyMngeReportWithPayState2", pmParam);
    }

    /**
     * 통계 - 채납미납 구분별 납입액정보, 실적정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 미납통계리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getUnpyMngeReportWithSection2(Map<String, ?> pmParam) throws Exception {
        return selectList("UnpyMngeMap.getUnpyMngeReportWithSection2", pmParam);
    }

    /**
     * 통계 - 상담유형별 제외/미제외 건수를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 미납통계리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getUnpyMngeReportWithCamptype(Map<String, ?> pmParam) throws Exception {
        return selectList("UnpyMngeMap.getUnpyMngeReportWithCamptype", pmParam);
    }
}
