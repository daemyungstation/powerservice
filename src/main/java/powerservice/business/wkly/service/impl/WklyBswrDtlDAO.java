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
 * @프로그램ID WklyRprgBasiDAO
 */
@Repository
public class WklyBswrDtlDAO extends EgovAbstractMapper {

    /**
     * 주간업무 검색 수를 반환한다.
     *
     * @param paramHash 검색 조건
     * @return 주간보고 대상자 건수
     * @throws Exception
     */
    public int getWklyBusinessCount(Map<String, ?> paramHash) throws Exception {
        return (Integer) selectOne("WklyBswrDtlMap.getWklyBusinessCount", paramHash);
    }

    /**
     * 주간보고 대상자를 조회한다.
     *
     * @param paramHash 검색 조건
     * @return 주간보고 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getWklyBusinessList(Map<String, ?> paramHash) throws Exception {
        return selectList("WklyBswrDtlMap.getWklyBusinessList", paramHash);
    }


    /**
     * 주간보고 대상자를 조회한다.(1건)
     *
     * @param paramHash 검색 조건
     * @return 주간보고 목록
     * @throws Exception
     */
    public Map<String, Object> getWklyBusiness(Map<String, ?> paramHash) throws Exception {
        return selectOne("WklyBswrDtlMap.getWklyBusinessList", paramHash);
    }

    /**
     * 주간보고 활동내역을 저장한다.
     *
     * @param paramHash 검색 조건
     * @return 주간보고 목록
     * @throws Exception
     */
    public int insertWklyBusiness(Map<String, Object> param) throws Exception {
        return insert("WklyBswrDtlMap.insertWklyBusiness", param);
    }

    /**
     * 주간보고 활동내역을 수정한다.
     *
     * @param paramHash 검색 조건
     * @return 주간보고 목록
     * @throws Exception
     */
    public int updateWklyBusiness(Map<String, Object> param) throws Exception {
        return update("WklyBswrDtlMap.updateWklyBusiness", param);
    }

    /**
     * 주간보고 활동내역을 삭제한다.
     *
     * @param param 검색 조건
     * @throws Exception
     */
    public int deleteWklyBusiness(Map<String, Object> param) throws Exception {
        return delete("WklyBswrDtlMap.deleteWklyBusiness", param);
    }

}
