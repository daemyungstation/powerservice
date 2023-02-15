/*
 * (@)# BswrDmndDtlDAO.java
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

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 업무요청내역 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID BswrDmndDtl
 */
@Repository
public class BswrDmndDtlDAO extends EgovAbstractMapper {

    /**
     * 업무요청내역 정보를 등록한다.
     *
     * @param param 업무요청내역 정보
     * @throws Exception
     */
    public int insertBswrDmndDtl(Map<String, ?> pmParam) throws Exception {
        return insert("BswrDmndDtlMap.insertBswrDmndDtl", pmParam);
    }

    /**
     * 이관업무 처리현황을 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 이관업무 처리현황
     * @throws Exception
     */
    public Map<String, Object> getDpmsReslDsps(Map<String, ?> pmParam) throws Exception {
        return selectOne("BswrDmndDtlMap.getDpmsReslDsps", pmParam);
    }

    /**
     * 요청상담처리 검색 수 반환한다.
     *
     * @param paramHash 검색 조건
     * @return 요청상담처리 대상 건수
     * @throws Exception
     */
    public int getTrctConsCount(Map<String, ?> paramHash) throws Exception {
        return (Integer) selectOne("BswrDmndDtlMap.getTrctConsCount", paramHash);
    }

    /**
     * 이관대상목록 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상담이관이력 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getTrctConsList(Map<String, ?> pmParam) throws Exception {
        return selectList("BswrDmndDtlMap.getTrctConsList", pmParam);
    }

    /**
     * 이관상담 이력 검색 수 반환한다.
     *
     * @param paramHash 검색 조건
     * @return 이관상담 이력 대상 건수
     * @throws Exception
     */
    public int getTrctConsHstrCount(Map<String, ?> paramHash) throws Exception {
        return (Integer) selectOne("BswrDmndDtlMap.getTrctConsHstrCount", paramHash);
    }

    /**
     * 이관상담 이력정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 이관상담 이력목록
     * @throws Exception
     */
    public List<Map<String, Object>> getTrctConsHstr(Map<String, ?> pmParam) throws Exception {
        return selectList("BswrDmndDtlMap.getTrctConsHstr", pmParam);
    }

    /**
     * 요청상담처리 검색 수 반환한다.
     *
     * @param paramHash 검색 조건
     * @return 요청상담처리 대상 건수
     * @throws Exception
     */
    public int getBswrDmndDspsCount(Map<String, ?> paramHash) throws Exception {
        return (Integer) selectOne("BswrDmndDtlMap.getBswrDmndDspsCount", paramHash);
    }

    /**
     * 요청상담처리 내역을 수정한다.
     *
     * @param paramHash 검색 조건
     * @return 요청상담처리 목록
     * @throws Exception
     */
    public int updateBswrDmndDsps(Map<String, Object> param) throws Exception {
        return update("BswrDmndDtlMap.updateBswrDmndDsps", param);
    }

    /**
     * 이관업무 처리현황을 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 이관업무 처리현황
     * @throws Exception
     */
    public Map<String, Object> getTrctCons(Map<String, ?> pmParam) throws Exception {
        return selectOne("BswrDmndDtlMap.getTrctConsList", pmParam);
    }

    /**
     * 이관상담 정보를 삭제한다.
     *
     * @param param 이관상담 정보
     * @throws Exception
     */
    public int deleteBswrDmnd(Map<String, ?> pmParam) throws Exception {
        return delete("BswrDmndDtlMap.deleteBswrDmnd", pmParam);
    }

    /**
     * 이관상담 차트 데이터를 조회한다
     *
     * @param pmParam 검색조건
     * @return 이관상담 차트 데이터
     * @throws Exception
     */
    public List<Map<String, Object>> getTrctConsChartWeeksList(Map<String, ?> pmParam) throws Exception {
        return selectList("BswrDmndDtlMap.getTrctConsChartWeeksList", pmParam);
    }

    /**
     * 사용자별 이관접수 미처리건수를 조회한다
     *
     * @param pmParam 검색 조건
     * @return 사용자별 이관접수 미처리건수
     * @throws Exception
     */
    public List<Map<String, Object>> getTodoConsTrctHstrCount() throws Exception {
        return selectList("BswrDmndDtlMap.getTodoConsTrctHstrCount");
    }

    /**
     * 해당 이관상담의 담당자를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 이관상담의 담당자
     * @throws Exception
     */
    public Map<String, Object> checkTrctChpr(Map<String, ?> pmParam) throws Exception {
        return selectOne("BswrDmndDtlMap.checkTrctChpr", pmParam);
    }

    /**
     * 이관상담 처리상태 검색 수를 반환한다.
     *
     * @param paramHash 검색 조건
     * @return 이관상담 처리상태 대상 건수
     * @throws Exception
     */
    public int getTrctConsStatCount(Map<String, ?> paramHash) throws Exception {
        return (Integer) selectOne("BswrDmndDtlMap.getTrctConsStatCount", paramHash);
    }

    /**
     * 이관상담 처리상태 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 이관상담 처리상태목록
     * @throws Exception
     */
    public List<Map<String, Object>> getTrctConsStatList(Map<String, ?> pmParam) throws Exception {
        return selectList("BswrDmndDtlMap.getTrctConsStatList", pmParam);
    }

    /**
     * 이관상담 처리상태 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 이관상담 처리상태목록
     * @throws Exception
     */
    public List<Map<String, Object>> getConsTrctBoxChprCount() throws Exception {
        return selectList("BswrDmndDtlMap.getConsTrctBoxChprCount");
    }

}
