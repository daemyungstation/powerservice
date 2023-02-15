/*
 * (@)# EvltPlan.java
 *
 * @author 최현식
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.evl.service.EvltPlanService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 평가지를 관리한다.
 * Copyright (c) 2014 Company Inwoo Tech Inc.
 *
 * @author 최현식
 * @version 1.0
 * @date 2014/04/01
 * @프로그램ID EvltPlan
 */
@Service
public class EvltPlanServiceImpl extends EgovAbstractServiceImpl implements EvltPlanService {

    @Resource
    public EvltPlanDAO evltPlanDAO;

    /**
     * 평가계획 정보의 검색 수를 반환한다.
     *
     * @param param 검색 조건
     * @return 평가계획 정보의 검색 수
     * @throws Exception
     */
    public int getEvltPlanCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) evltPlanDAO.getEvltPlanCount(pmParam);
    }


    /**
     * 평가계획 정보(목록)를 검색한다.
     *
     * @param param 검색 조건
     * @return 평가계획 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getEvltPlanList(Map<String, ?> pmParam) throws Exception {
        return evltPlanDAO.getEvltPlanList(pmParam);
    }

    /**
     * 평가계획 정보를 등록한다.
     *
     * @param param 평가계획 정보
     * @throws Exception
     */
    public String insertEvltPlan(Map<String, Object> pmParam) throws Exception {
        String sKey = "";

        int nResult = evltPlanDAO.insertEvltPlan(pmParam);

        if (nResult > 0) {
            sKey = (String) pmParam.get("evlt_plan_id");
        }
        return sKey;
    }

    /**
     * 평가계획 정보(1건)를 검색한다.
     *
     * @param paramHash 검색 조건
     * @return 평가계획 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getEvltPlan(String psId) throws Exception {
        Map<String, String> mParam = new HashMap<String, String>();
        mParam.put("evlt_plan_id", psId);
        return evltPlanDAO.getEvltPlan(mParam);
    }


    /**
     * 평가계획 정보를 수정한다.
     *
     * @param param 평가계획 정보
     * @throws Exception
     */
    public int updateEvltPlan(Map<String, Object> pmParam) throws Exception {
        int nResult = 0;
        String sEvltPrgrStatCd = String.valueOf(pmParam.get("evlt_prgr_stat_cd"));
        if ("40".equals(sEvltPrgrStatCd)) { // 종료일 경우 TB_QAUSER 전체평균, 팀평균, 순위 등록
            evltPlanDAO.updateEvltTrprInfo(pmParam);
        }

        nResult += evltPlanDAO.updateEvltPlan(pmParam);
        return nResult;
    }

    /**
     * 평가계획 정보를 삭제한다.
     *
     * @param param 평가계획 정보
     * @throws Exception
     */
    public int deleteEvltPlan(Map<String, Object> pmParam) throws Exception {
        int nResult = 0;
        Map<String, Object> mParam = new HashMap<String, Object>();
        String[] list = (String[])pmParam.get("selectcheck");

        evltPlanDAO.delete("EvltPlanMap.deleteEvltPlan", pmParam);

        // 평가자 대상자 삭제
        for (int  i = 0; i < list.length; i++) {
            mParam.put("evlt_plan_id", list[i]);
            mParam.put("cntr_cd", pmParam.get("cntr)cd"));
            nResult += evltPlanDAO.deleteEvlpInfo(mParam);
            nResult += evltPlanDAO.deleteEvltTrprInfo(mParam);
        }
        return nResult;
    }

    /**
     * 평가자별 평가계획 정보를 검색한다.
     *
     * @param param 검색 조건
     * @return 평가계획 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getEvltPlanByEvlp(Map<String, ?> pmParam) throws Exception {
        return evltPlanDAO.getEvltPlanByEvlp(pmParam);
    }

    /**
     * 평가자별 미완료된 건를 조회한다.
     *
     * @param param 검색 조건
     * @return 미완료된 건
     * @throws Exception
     */
    public List<Map<String, Object>> getUserFinishCount(Map<String, ?> pmParam) throws Exception {
        return evltPlanDAO.getUserFinishCount(pmParam);
    }


    public void insertmonitoring(Map<String, Object> pmParam) throws Exception {


       evltPlanDAO.insertmonitoring(pmParam);


    }

    /**
     * 모니터링 전체조회 카운트
     * a
     * */
    public int getmonitoringCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) evltPlanDAO.getmonitoringCount(pmParam);
    }

    public List<Map<String, Object>> getmonitoringList(Map<String, ?> pmParam) throws Exception {
        return evltPlanDAO.getmonitoringList(pmParam);
    }

    public List<Map<String, Object>> getmonitoringsearch(Map<String, ?> pmParam) throws Exception {
        return evltPlanDAO.getmonitoringsearch(pmParam);
    }

    /**
     * 모니터링 업데이트
     * */
    public void updatemonitoring(Map<String, Object> pmParam) throws Exception {

         evltPlanDAO.updatemonitoring(pmParam);

    }


}
