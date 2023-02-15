/*
 * (@)# WebConsServiceImpl.java
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

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.web.service.WebConsService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 웹상담 관리를 한다.
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정훈
 * @version 1.0
 * @date 2015/10/14
 * @프로그램ID WebCons
 */

@Service
public class WebConsServiceImpl extends EgovAbstractServiceImpl implements WebConsService {

    @Resource
    public WebConsDAO webConsDAO;

    /**
     * 웹상담 목록을 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 캠페인할당 리스트
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> getWebConsList(Map<String, ?> pmParam) throws Exception {
        return webConsDAO.getWebConsList(pmParam);
    }

    /**
     * 웹상담 목록의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 대상고객할당 정보의 검색 건수
     * @throws Exception
     */
    @Override
    public int getWebConsCount(Map<String, ?> pmParam) throws Exception {
        return webConsDAO.getWebConsCount(pmParam);
    }

    /**
    * 웹상담 정보를 수정 변경 한다.(save_flag 에 의한 정보값들 다름 Y -그리드에서 찜버튼 X-상세정보에서 담당자 변경 Z-저장버튼클릭 C-상담에서 넘어온정보 업데이트)
    *
    * @param pmParam 캠페인 결과정보
    * @throws Exception
    */
   @Override
   public int updateWebConsChpr(Map<String, ?> pmParam) throws Exception {
       return  webConsDAO.updateWebConsChpr(pmParam);
   }

   /**
    * 웹상담 대상목록 리스트를 조회한다 (1건).
    *
    * @param pmParam
    * @return 대상목록 리스트(1건)
    * @throws Exception
    */
   @Override
   public Map<String, Object> getWebConsTrgt(Map<String, Object> pmParam) throws Exception {

       return webConsDAO.getWebConsTrgt(pmParam);
   }

   /**
    * 웹상담 처리현황을 조회한다.
    *
    * @param pmParam 검색 조건
    * @return 웹상담 처리현황 목록
    * @throws Exception
    */
   public Map<String, Object> getDspsPsct(Map<String, ?> pmParam) throws Exception {
       return webConsDAO.getDspsPsct(pmParam);
   }

   /**
    * 웹상담 차트 데이터를 조회한다
    *
    * @param pmParam 검색조건
    * @return 센터현황 차트 데이터
    * @throws Exception
    */
   public List<Map<String, Object>> getWebConsChartWeeksList(Map<String, ?> pmParam) throws Exception {
       return webConsDAO.getWebConsChartWeeksList(pmParam);
   }

   /**
    * 상담 정보를 검색한다.
    *
    * @param pmParam 상담번호
    * @return 상담 정보(1건)
    * @throws Exception
    */
   public Map<String, Object> getCons(Map<String, ?> pmParam) throws Exception {
       return webConsDAO.getCons(pmParam);
   }
}
