/*
 * (@)# WklyRprgBasiDAO.java
 *
 * @author 박상수
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

package powerservice.business.wkly.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 주간보고 관리를 한다.
 *
 * Copyright (c) 2013 Company Inwoo Tech Inc.
 *
 * @author 박상수
 * @version 1.0
 * @date 2015/01/05
 * @프로그램ID WklyRprgEtcDAO
 */
@Repository
public class WklyRprgEtcDAO extends EgovAbstractMapper {


    /**
     * 주간보고 기타사항 정보의 검색 수를 반환한다.
     *
     * @param paramHash 검색 조건
     * @return 주간보고 검색 수
     * @throws Exception
     */
    public int getWklyEtcCount(Map<String, ?> paramHash) throws Exception {
        return (Integer) selectOne("WklyRprgEtcMap.getWklyEtcCount", paramHash);
    }

    /**
     * 주간보고 기타사항을 조회한다.
     *
     * @param paramHash 검색 조건
     * @return 주간보고 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getWklyEtcList(Map<String, ?> paramHash) throws Exception {
        return selectList("WklyRprgEtcMap.getWklyEtcList", paramHash);
    }

    /**
     * 기타사항 정보를 저장한다.
     *
     * @param paramHash 검색 조건
     * @return 주간보고 목록
     * @throws Exception
     */
    public int insertWklyEtc(Map<String, Object> param) throws Exception {
        return insert("WklyRprgEtcMap.insertWklyEtc", param);
    }

    /**
     * 기타사항 정보를 수정한다.
     *
     * @param paramHash 검색 조건
     * @return 주간보고 목록
     * @throws Exception
     */
    public int updateWklyEtc(Map<String, Object> param) throws Exception {
        return update("WklyRprgEtcMap.updateWklyEtc", param);
    }

    /**
     * 주간보고 기타사항을 조회한다.
     *
     * @param paramHash 검색 조건
     * @return 주간보고 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getEtcResult(Map<String, ?> paramHash) throws Exception {
        return selectList("WklyRprgEtcMap.getEtcResult", paramHash);
    }

    /**
     * 주간보고 - 지시사항을 삭제한다.
     *
     * @param param 교육평가
     * @throws Exception
     */
    public int deleteWklyEtc(Map<String, Object> param) throws Exception {
        return delete("WklyRprgEtcMap.deleteWklyEtc", param);
    }
}
