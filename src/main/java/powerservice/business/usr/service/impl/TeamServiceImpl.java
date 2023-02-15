/*
 * (@)# TeamService.java
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

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.usr.service.TeamService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 사용자 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 박의준
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID Team
 */
@Service
public class TeamServiceImpl extends EgovAbstractServiceImpl implements TeamService {

    @Resource
    public TeamDAO teamDAO;

    /**
     * 그룹 정보를 등록한다.
     *
     * @param param 그룹 정보
     * @throws Exception
     */
    public String insertTeam(Map<String, ?> pmParam) throws Exception {
        // 중복된 순서 자동증가
        String bDupYn = (String) pmParam.get("dup_yn");
        if (bDupYn != null && bDupYn.equals("Y")) {
            teamDAO.updateTeamOrder(pmParam);
        }
        /*Boolean bDupYn = (Boolean) pmParam.get("dup_yn");

        if (bDupYn != null && bDupYn) {
            teamDAO.updateTeamOrder(pmParam);
        }*/

        // 그룹 정보 저장
        String sKey = "";
        int nResult = teamDAO.insertTeam(pmParam);
        if (nResult > 0) {
            sKey = (String) pmParam.get("team_cd");
        }
        return sKey;
    }

    /**
     * 그룹 정보를 수정한다.
     *
     * @param param 그룹 정보
     * @throws Exception
     */
    public int updateTeam(Map<String, ?> pmParam) throws Exception {

        // 중복된 순서 자동증가
        String bDupYn = (String) pmParam.get("dup_yn");
        if (bDupYn != null && bDupYn.equals("Y")) {
            teamDAO.updateTeamOrder(pmParam);
        }
        /*Boolean bDupYn = (Boolean) pmParam.get("dup_yn");

        if (bDupYn != null && bDupYn) {
            teamDAO.updateTeamOrder(pmParam);
        }*/

        // 그룹 정보 수정
        return teamDAO.updateTeam(pmParam);
    }

    /**
     * 그룹 정보의 검색 수를 반환한다.
     *
     * @param paramHash 검색 조건
     * @return 그룹 정보의 검색 건수
     * @throws Exception
     */
    public int getTeamCount(Map<String, ?> pmParam) throws Exception {
        return teamDAO.getTeamCount(pmParam);
    }

    /**
     * 그룹 정보를 검색한다.
     *
     * @param paramHash 검색 조건
     * @return 그룹 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getTeamList(Map<String, ?> pmParam) throws Exception {
        return teamDAO.getTeamList(pmParam);
    }

    /**
     * 그룹 정보를 검색한다.
     *
     * @param id 그룹 ID
     * @return 그룹 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getTeam(Map<String, ?> pmParam) throws Exception {
        return teamDAO.getTeam(pmParam);
    }

    /**
     * 그룹 트리 목록을 검색한다.
     *
     * @param paramHash 검색 조건
     * @return 그룹 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getTeamTree(Map<String, ?> pmParam) throws Exception {
        return teamDAO.getTeamTree(pmParam);
    }

    /**
     * 그룹 순서 최대값을 조회한다.
     *
     * @param paramHash 검색 조건
     * @return 순서 최대값
     * @throws Exception
     */
    public int getTeamMaxOrder(Map<String, ?> pmParam) throws Exception {
        return teamDAO.getTeamMaxOrder(pmParam);
    }

    /**
     * 그룹 순서의 중복 건수를 조회한다
     *
     * @param paramHash 검색 조건
     * @return 그룹 순서의 중복 건수
     * @throws Exception
     */
    public int getTeamDuplicateCount(Map<String, ?> pmParam) throws Exception {
        return teamDAO.getTeamDuplicateCount(pmParam);
    }

}
