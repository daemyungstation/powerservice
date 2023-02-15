/*
 * (@)# EvltPlan.java
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

package powerservice.business.evl.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 평가지를 관리한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 최현식
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID EvltPlan
 */
@Repository
public class EvltPlanDAO extends EgovAbstractMapper {
    /**
     * 평가계획 정보의 검색 수를 반환한다.
     *
     * @param param 검색 조건
     * @return 평가계획 정보의 검색 수
     * @throws Exception
     */
    public int getEvltPlanCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("EvltPlanMap.getEvltPlanCount", pmParam);
    }

    /**
     * 평가계획 정보(목록)를 검색한다.
     *
     * @param param 검색 조건
     * @return 평가계획 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getEvltPlanList(Map<String, ?> pmParam) throws Exception {
        return selectList("EvltPlanMap.getEvltPlanList", pmParam);
    }

    /**
     * 평가계획 정보를 등록한다.
     *
     * @param param 평가계획 정보
     * @throws Exception
     */
    public int insertEvltPlan(Map<String, Object> pmParam) throws Exception {
        return insert("EvltPlanMap.insertEvltPlan", pmParam);
    }

    /**
     * 평가계획 정보(1건)를 검색한다.
     *
     * @param paramHash 검색 조건
     * @return 평가계획 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getEvltPlan(Map<String, ?>pmParam) throws Exception {
        return selectOne("EvltPlanMap.getEvltPlanList", pmParam);
    }

    /**
     * 평가계획 정보를 수정한다.
     *
     * @param param 평가계획 정보
     * @throws Exception
     */
    public int updateEvltTrprInfo(Map<String, Object> pmParam) throws Exception {
        return update("EvltTrprInfoMap.updateEvltTrprInfo", pmParam);
    }

    /**
     * 평가계획 정보를 수정한다.
     *
     * @param param 평가계획 정보
     * @throws Exception
     */
    public int updateEvltPlan(Map<String, Object> pmParam) throws Exception {
        return update("EvltPlanMap.updateEvltPlan", pmParam);
    }

    /**
     * 평가계획 정보를 삭제한다.
     *
     * @param param 평가계획 정보
     * @throws Exception
     */
    public int deleteEvltPlan(Map<String, Object> pmParam) throws Exception {
        return delete("EvltPlanMap.deleteEvltPlan", pmParam);
    }

    /**
     * 평가계획 정보를 삭제한다.
     *
     * @param param 평가계획 정보
     * @throws Exception
     */
    public int deleteEvlpInfo(Map<String, Object> pmParam) throws Exception {
        return delete("EvlpInfoMap.deleteEvlpInfo", pmParam);
    }

    /**
     * 평가계획 정보를 삭제한다.
     *
     * @param param 평가계획 정보
     * @throws Exception
     */
    public int deleteEvltTrprInfo(Map<String, Object> pmParam) throws Exception {
        return delete("EvltTrprInfoMap.deleteEvltTrprInfo", pmParam);
    }

    /**
     * 평가자별 평가계획 정보를 검색한다.
     *
     * @param param 검색 조건
     * @return 평가계획 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getEvltPlanByEvlp(Map<String, ?> pmParam) throws Exception {
        return selectList("EvltPlanMap.getEvltPlanByEvlp", pmParam);
    }

    /**
     * 평가자별 미완료된 건를 조회한다.
     *
     * @param param 검색 조건
     * @return 미완료된 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getUserFinishCount(Map<String, ?> pmParam) throws Exception {
        return selectList("EvltPlanMap.getUserFinishCount", pmParam);
    }

    public int insertmonitoring(Map<String, Object> pmParam) throws Exception {
        return insert("EvltPlanMap.insertmonitoring", pmParam);
    }

    /**
     *  모니터링 전체 조회
     * */
    public int getmonitoringCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("EvltPlanMap.getmonitoringCount", pmParam);
    }

    public List<Map<String, Object>> getmonitoringList(Map<String, ?> pmParam) throws Exception {
        return selectList("EvltPlanMap.getmonitoringList", pmParam);
    }

    public List<Map<String, Object>> getmonitoringsearch(Map<String, ?> pmParam) throws Exception {
        return selectList("EvltPlanMap.getmonitoringList", pmParam);
    }

    public int updatemonitoring(Map<String, Object> pmParam) throws Exception {
        return update("EvltPlanMap.updatemonitoring", pmParam);
    }

}
