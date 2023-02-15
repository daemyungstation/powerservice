/*
 * (@)# ExccWrkrDAO.java
 *
 * @author 배윤정
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

package powerservice.business.usr.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 우수사원 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 배윤정
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID ExccWrkr
 */
@Repository
public class ExccWrkrDAO extends EgovAbstractMapper {

    /**
     * 우수사원 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 우수사원 정보의 검색 수
     * @throws Exception
     */
    public int getExccWrkrCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("ExccWrkrMap.getExccWrkrCount", pmParam);
    }

    /**
     * 우수사원 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 우수사원 정보 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getExccWrkrList(Map<String, ?> pmParam) throws Exception {
        return selectList("ExccWrkrMap.getExccWrkrList", pmParam);
    }

    /**
     * 우수사원 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 우수사원 정보 리스트
     * @throws Exception
     */
    public Map<String, Object> getExccWrkr(Map<String, ?> pmParam) throws Exception {
        return selectOne("ExccWrkrMap.getExccWrkrList", pmParam);
    }

    /**
     * 우수사원 정보를 등록한다.
     *
     * @param pmParam 우수사원 정보
     * @throws Exception
     */
    public int insertExccWrkr(Map<String, ?> pmParam) throws Exception {
        return insert("ExccWrkrMap.insertExccWrkr", pmParam);
    }

    /**
     * 우수사원 정보를 수정한다.
     *
     * @param pmParam 우수사원 정보
     * @throws Exception
     */
    public int updateExccWrkr(Map<String, ?> pmParam) throws Exception {
        return update("ExccWrkrMap.updateExccWrkr", pmParam);
    }

    /**
     * 우수사원 순서 정보를 수정한다.
     *
     * @param pmParam 우수사원 순서 정보
     * @throws Exception
     */
    public int updateExccWrkrOrder(Map<String, ?> pmParam) throws Exception {
        return update("ExccWrkrMap.updateExccWrkrOrder", pmParam);
    }

    /**
     * 우수사원 정보를 삭제한다.
     *
     * @param pmParam 우수사원 정보
     * @throws Exception
     */
    public int deleteExccWrkr(Map<String, ?> pmParam) throws Exception {
        return delete("ExccWrkrMap.deleteExccWrkr", pmParam);
    }

    /**
     * 선정순서의 중복 건수를 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 권한 순서의 중복 건수
     * @throws Exception
     */
    public int getExccWrkrDuplicateCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("ExccWrkrMap.getExccWrkrDuplicateCount", pmParam);
    }

}
