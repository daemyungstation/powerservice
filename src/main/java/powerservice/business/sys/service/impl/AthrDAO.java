/*
 * (@)# AthrDAO.java
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
 * 권한 정보 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID Athr
 */
@Repository
public class AthrDAO extends EgovAbstractMapper {

    /**
     * 권한 정보를 등록한다.
     *
     * @param pmParam 권한 정보
     * @throws Exception
     */
    public int insertAthr(Map<String, ?> pmParam) throws Exception {
        return insert("AthrMap.insertAthr", pmParam);
    }

    /**
     * 권한 정보를 수정한다.
     *
     * @param pmParam 권한 정보
     * @throws Exception
     */
    public int updateAthr(Map<String, ?> pmParam) throws Exception {
        return update("AthrMap.updateAthr", pmParam);
    }

    /**
     * 권한 순서를 수정한다.
     *
     * @param pmParam 권한 정보
     * @throws Exception
     */
    public int updateAthrSequence(Map<String, ?> pmParam) throws Exception {
        return update("AthrMap.updateAthrSequence", pmParam);
    }

    /**
     * 권한 정보를 삭제한다.
     *
     * @param pmParam 권한 정보
     * @throws Exception
     */
    public int deleteAthr(Map<String, ?> pmParam) throws Exception {
        return delete("AthrMap.deleteAthr", pmParam);
    }

    /**
     * 권한 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 권한 정보의 검색 건수
     * @throws Exception
     */
    public int getAthrCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("AthrMap.getAthrCount", pmParam);
    }

    /**
     * 권한 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 권한 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getAthrList(Map<String, ?> pmParam) throws Exception {
        return selectList("AthrMap.getAthrList", pmParam);
    }

    /**
     * 권한 정보를 검색한다.
     *
     * @param pmParam 권한CD
     * @return 권한 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getAthr(Map<String, ?> pmParam) throws Exception {
        return selectOne("AthrMap.getAthrList", pmParam);
    }

    /**
     * 권한 순서 최대값을 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 권한 순서 최대값
     * @throws Exception
     */
    public int getAthrMaxSequence(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("AthrMap.getAthrMaxSequence", pmParam);
    }

    /**
     * 권한 순서의 중복 건수를 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 권한 순서의 중복 건수
     * @throws Exception
     */
    public int getAthrDuplicateCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("AthrMap.getAthrDuplicateCount", pmParam);
    }

}
