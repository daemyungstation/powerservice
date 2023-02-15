/*
 * (@)# WebConsDAO.java
 *
 * @author 정훈
 * @version 1.0
 * @date 2015/10/14
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

package powerservice.business.web.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 웹상담 관리를 한다.
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정훈
 * @version 1.0
 * @date 2015/10/15
 * @프로그램ID WebCons
 */

@Repository
public class WebConsDAO extends EgovAbstractMapper {


    /**
     * 웹상담 리스트를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 웹상담 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getWebConsList(Map<String, ?> pmParam) throws Exception {
        return selectList("WebConsMap.getWebConsList", pmParam);
    }

    /**
     * 웹상담 목록의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 웹상담 정보의 검색 건수
     * @throws Exception
     */
    public int getWebConsCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("WebConsMap.getWebConsCount", pmParam);
    }

    /**
    * 웹상담 상담조회
    *
    * @param pmParam 검색조건
    * @return 웹상담 목록 (1건)
    * @throws Exception
    */
   public Map<String, Object> getWebConsTrgt(Map<String, Object> pmParam) throws Exception {
       return selectOne("WebConsMap.getWebConsList", pmParam);
   }

   /**
    * 웹상담 정보를 수정한다.(ajax)
    *
    * @param pmParam 웹상담 결과정보
    * @throws Exception
    */
   public int updateWebConsChpr(Map<String, ?> pmParam) throws Exception {
       return update("WebConsMap.updateWebConsChpr", pmParam);
   }

   /**
    * 웹상담 처리현황을 조회한다.
    *
    * @param pmParam 검색 조건
    * @return 웹상담 처리현황
    * @throws Exception
    */
   public Map<String, Object> getDspsPsct(Map<String, ?> pmParam) throws Exception {
       return selectOne("WebConsMap.getDspsPsct", pmParam);
   }

   /**
    * 웹상담 차트 데이터를 조회한다
    *
    * @param pmParam 검색조건
    * @return 웹상담 차트 데이터
    * @throws Exception
    */
   public List<Map<String, Object>> getWebConsChartWeeksList(Map<String, ?> pmParam) throws Exception {
       return selectList("WebConsMap.getWebConsChartWeeksList", pmParam);
   }

   /**
    * 상담 정보를 검색한다.
    *
    * @param pmParam 상담번호
    * @return 상담 정보(1건)
    * @throws Exception
    */
   public Map<String, Object> getCons(Map<String, ?> pmParam) throws Exception {
       return selectOne("WebConsMap.getConsList", pmParam);
   }

}
