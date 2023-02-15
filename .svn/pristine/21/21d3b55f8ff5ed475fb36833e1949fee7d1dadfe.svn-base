/*
 * (@)# RprtDAO.java
 *
 * @author 김은지
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

package powerservice.business.rpt.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 통계 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 김은지
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID Rprt
 */
@Repository
public class RprtDAO extends EgovAbstractMapper {

    /**
     * 상담유형별 현황을 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상담유형별 현황 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getConsTypDspsStatListTyp1(Map<String, ?> pmParam) throws Exception {
        return selectList("RprtMap.getConsTypDspsStatListTyp1", pmParam);
    }
    public List<Map<String, Object>> getConsTypDspsStatListTyp2(Map<String, ?> pmParam) throws Exception {
        return selectList("RprtMap.getConsTypDspsStatListTyp2", pmParam);
    }
    public List<Map<String, Object>> getConsTypDspsStatListTyp3(Map<String, ?> pmParam) throws Exception {
        return selectList("RprtMap.getConsTypDspsStatListTyp3", pmParam);
    }

    /**
     * 상담사별 현황을 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상사유형별 현황 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getUserDspsStatList(Map<String, ?> pmParam) throws Exception {
         return selectList("RprtMap.getUserDspsStatList", pmParam);
    }

    /**
     * 처리방법별 현황을 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 처리방법별 현황 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getDspsmddlDtptDspsStatList(Map<String, ?> pmParam) throws Exception {
         return selectList("RprtMap.getDspsmddlDtptDspsStatList", pmParam);
    }

    /**
     * 콜백처리 현황을 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 콜백처리 현황 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getCalbDspsStatList(Map<String, ?> pmParam) throws Exception {
         return selectList("RprtMap.getCalbDspsStatList", pmParam);
    }

    /**
     * 상담현황의 추이(대분류 기준의 시간대/요일별/월별/일별 상담건수) 리스트를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상담현황 추이
     * @throws Exception
     */
    public List<Map<String, Object>> getConsCscntTimeList(Map<String, ?> pmParam) throws Exception {
        return selectList("RprtMap.getConsCscntTimeList", pmParam);
    }
    public List<Map<String, Object>> getConsCscntDayList(Map<String, ?> pmParam) throws Exception {
        return selectList("RprtMap.getConsCscntDayList", pmParam);
    }
    public List<Map<String, Object>> getConsCscntMonthList(Map<String, ?> pmParam) throws Exception {
        return selectList("RprtMap.getConsCscntMonthList", pmParam);
    }
    public List<Map<String, Object>> getConsCscntDateList(Map<String, ?> pmParam) throws Exception {
        return selectList("RprtMap.getConsCscntDateList", pmParam);
    }

    /**
     * 상담현황의 추이(대분류 기준의 시간대/요일별/월별/일별 상담건수) 차트를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상담현황 추이
     * @throws Exception
     */
    public List<Map<String, Object>> getConsCscntTimeChart(Map<String, ?> pmParam) throws Exception {
        return selectList("RprtMap.getConsCscntTimeChart", pmParam);
    }
    public List<Map<String, Object>> getConsCscntDayChart(Map<String, ?> pmParam) throws Exception {
        return selectList("RprtMap.getConsCscntDayChart", pmParam);
    }
    public List<Map<String, Object>> getConsCscntMonthChart(Map<String, ?> pmParam) throws Exception {
        return selectList("RprtMap.getConsCscntMonthChart", pmParam);
    }
    public List<Map<String, Object>> getConsCscntDateChart(Map<String, ?> pmParam) throws Exception {
        return selectList("RprtMap.getConsCscntDateChart", pmParam);
    }

    /**
     * VOC처리 현황을 검색한다.
     *
     * @param pmParam 검색 조건
     * @return VOC처리 현황 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getVocDspsStatList(Map<String, ?> pmParam) throws Exception {
         return selectList("RprtMap.getVocDspsStatList", pmParam);
    }

    /**
     * 콜백상세처리 현황을 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 콜백처리 현황 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getCalbDtptDspsStatList(Map<String, ?> pmParam) throws Exception {
         return selectList("RprtMap.getCalbDtptDspsStatList", pmParam);
    }

    /**
     * 게시판사용 현황을 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 콜백처리 현황 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getNobdUseStatList(Map<String, ?> pmParam) throws Exception {
         return selectList("RprtMap.getNobdUseStatList", pmParam);
    }

    /**
     * 상담목록건수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 상담목록건수
     * @throws Exception
     */
    public int getConsCscnt(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("RprtMap.getConsCscnt", pmParam);
    }

    /**
     * 캠페인 설문결과(객관식)를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 캠페인 설문결과(객관식) 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getSurvansObjectiveList(Map<String, ?> pmParam) throws Exception {
         return selectList("RprtMap.getSurvansObjectiveList", pmParam);
    }

    /**
     * 주관식 질문 리스트를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 주관식 질문 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getSurveyitmNmList(Map<String, ?> pmParam) throws Exception {
        return selectList("RprtMap.getSurveyitmNmList", pmParam);
    }

    /**
     * 주관식 답변 건수를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 주관식 답변 건수
     * @throws Exception
     */
    public int getSurvansSubjectiveCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("RprtMap.getSurvansSubjectiveCount", pmParam);
    }

    /**
     * 설문결과 건수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 설문결과건수
     * @throws Exception
     */
    public int getSurvansCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("RprtMap.getSurvansCount", pmParam);
    }

}
