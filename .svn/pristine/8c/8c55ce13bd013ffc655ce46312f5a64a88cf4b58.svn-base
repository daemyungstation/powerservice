/*
 * (@)# HrchCdDAO.java
 *
 * @author 박의준
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
 * 서버 정보 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 박의준
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID HrchCd
 */
@Repository
public class HrchCdDAO extends EgovAbstractMapper {

    /**
     * 계층유형코드 정보를 등록한다.
     *
     * @param pmParam 계층유형코드 정보
     * @throws Exception
     */
    public int insertHrchCd(Map<String, ?> pmParam) throws Exception {
        return insert("HrchCdMap.insertHrchCd", pmParam);
    }

    /**
     * 계층유형코드 정보를 수정한다.
     *
     * @param pmParam 계층유형코드 정보
     * @throws Exception
     */
    public int updateHrchCd(Map<String, ?> pmParam) throws Exception {
        return update("HrchCdMap.updateHrchCd", pmParam);
    }

    /**
     * 계층유형코드 순서를 수정한다.
     *
     * @param pmParam 계층유형코드 정보
     * @throws Exception
     */
    public int updateHrchCdSequence(Map<String, ?> pmParam) throws Exception {
        return update("HrchCdMap.updateHrchCdSequence", pmParam);
    }

    /**
     * 계층유형코드 정보를 삭제한다.
     *
     * @param pmParam 계층유형코드 정보
     * @throws Exception
     */
    public int deleteHrchCd(Map<String, ?> pmParam) throws Exception {
        return delete("HrchCdMap.deleteHrchCd", pmParam);
    }

    /**
     * 계층유형코드 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 계층유형코드 정보의 검색 건수
     * @throws Exception
     */
    public int getHrchCdCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("HrchCdMap.getHrchCdCount", pmParam);
    }

    /**
     * 계층유형코드 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 계층유형코드 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getHrchCdList(Map<String, ?> pmParam) throws Exception {
        return selectList("HrchCdMap.getHrchCdList", pmParam);
    }

    /**
     * 계층유형코드 정보를 검색한다.
     *
     * @param pmParam 계층코드ID
     * @return 계층유형코드 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getHrchCd(Map<String, ?> pmParam) throws Exception {
        return selectOne("HrchCdMap.getHrchCdList", pmParam);
    }

    /**
     * 계층유형코드 트리 목록을 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 계층유형코드 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getHrchCdTree(Map<String, ?> pmParam) throws Exception {
        return selectList("HrchCdMap.getHrchCdTree", pmParam);
    }

    /**
     * 계층유형코드 순서 최대값을 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 순서 최대값
     * @throws Exception
     */
    public int getHrchCdMaxSequence(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("HrchCdMap.getHrchCdMaxSequence", pmParam);
    }

    /**
     * 계층유형코드 순서의 중복 건수를 조회한다
     *
     * @param pmParam 검색 조건
     * @return 계층유형코드 순서의 중복 건수
     * @throws Exception
     */
    public int getHrchCdDuplicateCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("HrchCdMap.getHrchCdDuplicateCount", pmParam);
    }

    /**
     * 계층유형코드 드롭다운 리스트를 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 계층유형코드 드롭다운 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getClscdDropDownList(Map<String, ?> pmParam) throws Exception {
        return selectList("HrchCdMap.getClscdDropDownList", pmParam);
    }

    /**
     * 계층유형코드 목록에 따라 계층코드 정보(목록)를 검색한다.
     *
     * @param sHrchTypList 검색 조건
     * @return 계층 코드 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getHrchCdListByStringArray(String[] sHrchTypList) throws Exception {
        return selectList("HrchCdMap.getHrchCdListByStringArray", sHrchTypList);
    }

}
