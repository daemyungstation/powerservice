/*
 * (@)# TeamDAO.java
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

package powerservice.business.usr.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 로그인/아웃 이력 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 박의준
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID Team
 */
@Repository
public class TeamDAO extends EgovAbstractMapper {

    /**
     * 그룹 정보를 등록한다.
     *
     * @param pmParam 그룹 정보
     * @throws Exception
     */
    public int insertTeam(Map<String, ?> pmParam) throws Exception {
        return insert("TeamMap.insertTeam", pmParam);
    }

    /**
     * 그룹 정보를 수정한다.
     *
     * @param pmParam 그룹 정보
     * @throws Exception
     */
    public int updateTeam(Map<String, ?> pmParam) throws Exception {
        return update("TeamMap.updateTeam", pmParam);
    }

    /**
     * 그룹 정보를 수정한다.
     *
     * @param pmParam 그룹 정보
     * @throws Exception
     */
    public int updateTeamOrder(Map<String, ?> pmParam) throws Exception {
        return update("TeamMap.updateTeamOrder", pmParam);
    }

    /**
     * 그룹 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 그룹 정보의 검색 건수
     * @throws Exception
     */
    public int getTeamCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("TeamMap.getTeamCount", pmParam);
    }

    /**
     * 그룹 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 그룹 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getTeamList(Map<String, ?> pmParam) throws Exception {
        return selectList("TeamMap.getTeamList", pmParam);
    }

    /**
     * 그룹 정보를 검색한다.
     *
     * @param id 그룹 ID
     * @return 그룹 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getTeam(Map<String, ?> pmParam) throws Exception {
        return selectOne("TeamMap.getTeamList", pmParam);
    }

    /**
     * 그룹 트리 목록을 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 그룹 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getTeamTree(Map<String, ?> pmParam) throws Exception {
        return selectList("TeamMap.getTeamTree", pmParam);
    }

    /**
     * 그룹 순서 최대값을 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 순서 최대값
     * @throws Exception
     */
    public int getTeamMaxOrder(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("TeamMap.getTeamMaxOrder", pmParam);
    }

    /**
     * 그룹 순서의 중복 건수를 조회한다
     *
     * @param pmParam 검색 조건
     * @return 그룹 순서의 중복 건수
     * @throws Exception
     */
    public int getTeamDuplicateCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("TeamMap.getTeamDuplicateCount", pmParam);
    }

}
