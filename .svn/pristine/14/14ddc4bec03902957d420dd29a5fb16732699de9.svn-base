/*
 * (@)# SuctDAO.java
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

package powerservice.business.kms.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 퀵링크 세부항목 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 차건호
 * @version 1.0
 * @date 2015/04/23
 * @프로그램ID Suct
 */

@Repository
public class SuctDAO extends EgovAbstractMapper {

    /**
     * 퀵링크 세부항목 정보를 등록한다.
     *
     * @param pmParam 퀵링크 세부항목 정보
     * @throws Exception
     */
    public int insertSuct(Map<String, ?> pmParam) throws Exception {
        return insert("SuctMap.insertSuct", pmParam);
    }

    /**
     * 퀵링크 중복순서를 자동증가한다.
     *
     * @param pmParam 퀵링크 세부항목 정보
     * @throws Exception
     */
    public int updateSuctOrder(Map<String, ?> pmParam) throws Exception {
        return update("SuctMap.updateSuctOrder", pmParam);
    }

    /**
     * 퀵링크 세부항목 정보를 수정한다.
     *
     * @param pmParam 퀵링크 세부항목 정보
     * @throws Exception
     */
    public int updateSuct(Map<String, ?> pmParam) throws Exception {
        return update("SuctMap.updateSuct", pmParam);
    }

    /**
     * 퀵링크 세부항목 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 퀵링크 세부항목 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getSuctList(Map<String, ?> pmParam) throws Exception {
        return selectList("SuctMap.getSuctList", pmParam);
    }

    /**
     * 퀵링크 세부항목 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 퀵링크 세부항목 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getSuct(Map<String, ?> pmParam) throws Exception {
        return selectOne("SuctMap.getSuctList", pmParam);
    }

    /**
     * 퀵링크 세부항목 순서 최대값을 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 퀵링크 세부항목 순서 최대값
     * @throws Exception
     */
    public int getSuctMaxOrder(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("SuctMap.getSuctMaxOrder", pmParam);
    }

    /**
     * 사용중인 퀵링크 세부항목 건수를 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 사용중인 퀵링크 세부항목 건수
     * @throws Exception
     */
    public int getSuctUsedCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("SuctMap.getSuctUsedCount", pmParam);
    }

    /**
     * 퀵링크 세부항목 순서의 중복 건수를 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 퀵링크 세부항목 순서의 중복 건수
     * @throws Exception
     */
    public int getSuctDuplicateCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("SuctMap.getSuctDuplicateCount", pmParam);
    }

    /**
     * 퀵링크 세부항목 정보를 삭제한다.
     *
     * @param pmParam 퀵링크 세부항목 정보
     * @throws Exception
     */
    public int deleteSuct(Map<String, ?> pmParam) throws Exception {
        return delete("SuctMap.deleteSuct", pmParam);
    }

}
