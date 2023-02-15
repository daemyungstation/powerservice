/*
 * (@)# RprtService.java
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

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.rpt.service.RprtService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

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
@Service
public class RprtServiceImpl extends EgovAbstractServiceImpl implements RprtService {

    @Resource
    public RprtDAO rprtDAO;

    /**
     * 상담유형별 현황을 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상담유형별 현황 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getConsTypDspsStatList(Map<String, ?> pmParam) throws Exception {
        String sConsTyp = (String) pmParam.get("cons_typ");
        if (sConsTyp.equals("typ1")) {
            return rprtDAO.getConsTypDspsStatListTyp1(pmParam);
        } else if (sConsTyp.equals("typ2")) {
            return rprtDAO.getConsTypDspsStatListTyp2(pmParam);
        } else if (sConsTyp.equals("typ3")) {
            return rprtDAO.getConsTypDspsStatListTyp3(pmParam);
        }
        return null;
    }

    /**
     * 상담사별 현황을 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상사유형별 현황 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getUserDspsStatList(Map<String, ?> pmParam) throws Exception {
         return rprtDAO.getUserDspsStatList(pmParam);
    }

    /**
     * 처리방법별 현황을 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 처리방법별 현황 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getDspsmddlDtptDspsStatList(Map<String, ?> pmParam) throws Exception {
         return rprtDAO.getDspsmddlDtptDspsStatList(pmParam);
    }

    /**
     * 콜백처리 현황을 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 콜백처리 현황 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getCalbDspsStatList(Map<String, ?> pmParam) throws Exception {
         return rprtDAO.getCalbDspsStatList(pmParam);
    }

    /**
     * 상담현황의 추이(대분류 기준의 시간대/요일별/월별/일별 상담건수) 리스트를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상담현황 추이
     * @throws Exception
     */
    public List<Map<String, Object>> getConsCscntList(Map<String, ?> pmParam) throws Exception {
        String sSearchType = (String) pmParam.get("searchType");
        List<Map<String, Object>> mList = null;
        if ("time".equals(sSearchType)) {
            mList = rprtDAO.getConsCscntTimeList(pmParam);
        } else if ("day".equals(sSearchType)) {
            mList = rprtDAO.getConsCscntDayList(pmParam);
        } else if ("month".equals(sSearchType)) {
            mList = rprtDAO.getConsCscntMonthList(pmParam);
        } else if ("date".equals(sSearchType)) {
            mList = rprtDAO.getConsCscntDateList(pmParam);
        }
        return mList;
    }

    /**
     * 상담현황의 추이(대분류 기준의 시간대/요일별/월별/일별 상담건수) 차트를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상담현황 추이
     * @throws Exception
     */
    public List<Map<String, Object>> getConsCscntChart(Map<String, ?> pmParam) throws Exception {
        String sSearchType = (String) pmParam.get("searchType");
        List<Map<String, Object>> mList = null;
        if ("time".equals(sSearchType)) {
            mList = rprtDAO.getConsCscntTimeChart(pmParam);
        } else if ("day".equals(sSearchType)) {
            mList = rprtDAO.getConsCscntDayChart(pmParam);
        } else if ("month".equals(sSearchType)) {
            mList = rprtDAO.getConsCscntMonthChart(pmParam);
        } else if ("date".equals(sSearchType)) {
            mList = rprtDAO.getConsCscntDateChart(pmParam);
        }
        return mList;
    }

    /**
     * VOC처리 현황을 검색한다.
     *
     * @param pmParam 검색 조건
     * @return VOC처리 현황 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getVocDspsStatList(Map<String, ?> pmParam) throws Exception {
         return rprtDAO.getVocDspsStatList(pmParam);
    }

    /**
     * 콜백상세처리 현황을 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 콜백처리 현황 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getCalbDtptDspsStatList(Map<String, ?> pmParam) throws Exception {
         return rprtDAO.getCalbDtptDspsStatList(pmParam);
    }

    /**
     * 게시판사용 현황을 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 콜백처리 현황 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getNobdUseStatList(Map<String, ?> pmParam) throws Exception {
         return rprtDAO.getNobdUseStatList(pmParam);
    }

    /**
     * 상담목록건수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 상담목록건수
     * @throws Exception
     */
    public int getConsCscnt(Map<String, ?> pmParam) throws Exception {
        return rprtDAO.getConsCscnt(pmParam);
    }

    /**
     * 캠페인 설문결과(객관식)를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 캠페인 설문결과(객관식) 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getSurvansObjectiveList(Map<String, ?> pmParam) throws Exception {
         return rprtDAO.getSurvansObjectiveList(pmParam);
    }

    /**
     * 주관식 질문 리스트를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 주관식 질문 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getSurveyitmNmList(Map<String, ?> pmParam) throws Exception {
        return rprtDAO.getSurveyitmNmList(pmParam);
    }

    /**
     * 주관식 답변 건수를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 주관식 답변 건수
     * @throws Exception
     */
    public int getSurvansSubjectiveCount(Map<String, ?> pmParam) throws Exception {
        return rprtDAO.getSurvansSubjectiveCount(pmParam);
    }

   /**
    * 설문결과 건수를 반환한다.
    *
    * @param pmParam 검색 조건
    * @return 설문결과건수
    * @throws Exception
    */
   public int getSurvansCount(Map<String, ?> pmParam) throws Exception {
       return rprtDAO.getSurvansCount(pmParam);
   }

}
