/*
 * (@)# GdsDAO.java
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

package powerservice.business.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 제품 정보 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID Gds
 */
@Repository
public class GdsDAO extends EgovAbstractMapper {

    /**
     * 제품 정보를 등록한다.
     *
     * @param pmParam 제품 정보
     * @throws Exception
     */
    public int insertGds(Map<String, ?> pmParam) throws Exception {
        return insert("GdsMap.insertGds", pmParam);
    }

    /**
     * 제품 정보를 수정한다.
     *
     * @param pmParam 제품 정보
     * @throws Exception
     */
    public int updateGds(Map<String, ?> pmParam) throws Exception {
        return update("GdsMap.updateGds", pmParam);
    }

    /**
     * 제품 정보를 삭제한다.
     *
     * @param pmParam 제품 정보
     * @throws Exception
     */
    public int deleteGds(Map<String, ?> pmParam) throws Exception {
        return delete("GdsMap.deleteGds", pmParam);
    }

    /**
     * 제품 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 제품 정보의 검색 건수
     * @throws Exception
     */
    public int getGdsCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("GdsMap.getGdsCount", pmParam);
    }

    /**
     * 제품 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 제품 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getGdsList(Map<String, ?> pmParam) throws Exception {
        return selectList("GdsMap.getGdsList", pmParam);
    }

    /**
     * 제품 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 제품 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getGds(Map<String, ?> pmParam) throws Exception {
        return selectOne("GdsMap.getGdsList", pmParam);
    }

}
