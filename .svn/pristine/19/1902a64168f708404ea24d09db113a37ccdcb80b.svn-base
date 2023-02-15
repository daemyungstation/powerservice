/*
 * (@)# DpmsReslDAO.java
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

package powerservice.business.cam.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 캠페인 결과를 관리 한다.
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정용재
 * @version 1.0
 * @date 2015/05/07
 * @프로그램ID DpmsResl
 */

@Repository
public class DpmsReslDAO extends EgovAbstractMapper {

      /**
     * 캠페인 결과(고객별) 검색 수를 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 캠페인발신이력 정보의 검색 건수
     * @throws Exception
     */
    public int getDpmsReslByCustomerCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DpmsReslMap.getDpmsReslByCustomerCount", pmParam);
    }

    /**
     * 캠페인 결과(고객별)을 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 캠페인 결과현황 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDpmsReslByCustomerList(Map<String, ?> pmParam) throws Exception {
        return selectList("DpmsReslMap.getDpmsReslByCustomerList", pmParam);
    }

    /**
     * 캠페인 결과(상담사별) 검색 수를 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 캠페인발신이력 정보의 검색 건수
     * @throws Exception
     */
    public int getDpmsReslByUserCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DpmsReslMap.getDpmsReslByUserCount", pmParam);
    }

    /**
     * 캠페인 결과(상담사별)을 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 캠페인 결과현황 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDpmsReslByUserList(Map<String, ?> pmParam) throws Exception {
        return selectList("DpmsReslMap.getDpmsReslByUserList", pmParam);
    }

    /**
     * 센터현황(캠페인)>상담실적TOP10을 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 캠페인 상담실적TOP10 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDpmsReslTop10(Map<String, ?> pmParam) throws Exception {
        return selectList("DpmsReslMap.getDpmsReslTop10", pmParam);
    }

    /**
     * 센터현황(캠페인)>캠페인 처리현황을 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 캠페인 처리현황
     * @throws Exception
     */
    public Map<String, Object> getDpmsReslDsps(Map<String, ?> pmParam) throws Exception {
        return selectOne("DpmsReslMap.getDpmsReslDsps", pmParam);
    }

    /**
     * 센터현황(캠페인)>캠페인 금일처리가 있는 상담사의 검색 수를 조회한다.
     *
     * @param pmParam
     * @return 캠페인 금일처리가 있는 상담사의 검색 수
     * @throws Exception
     */
    public int getTdyDpmsCnsrCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DpmsReslMap.getTdyDpmsCnsrCount", pmParam);
    }

    /**
     * 센터현황(캠페인)>캠페인 일자별 처리현황을 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 캠페인 일자별 처리현황 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDpmsReslDclDsps(Map<String, ?> pmParam) throws Exception {
        return selectList("DpmsReslMap.getDpmsReslDclDsps", pmParam);
    }
}
