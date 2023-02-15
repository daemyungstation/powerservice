/*
 * (@)# RoleDAO.java
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
 * 역할 정보 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 박의준
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID Role
 */
@Repository
public class RoleDAO extends EgovAbstractMapper {

    /**
     * 사용자역할 정보를 등록한다.
     *
     * @param pmParam 사용자역할 정보
     * @throws Exception
     */
    public int insertRole(Map<String, ?> pmParam) throws Exception {
        return insert("RoleMap.insertRole", pmParam);
    }

    /**
     * 사용자역할 정보를 수정한다.
     *
     * @param pmParam 사용자역할 정보
     * @throws Exception
     */
    public int updateRole(Map<String, ?> pmParam) throws Exception {
        return update("RoleMap.updateRole", pmParam);
    }

    /**
     * 사용자역할 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 사용자역할 정보의 검색 건수
     * @throws Exception
     */
    public int getRoleCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("RoleMap.getRoleCount", pmParam);
    }

    /**
     * 사용자역할 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 사용자역할 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getRoleList(Map<String, ?> pmParam) throws Exception {
        return selectList("RoleMap.getRoleList", pmParam);
    }

    /**
     * 사용자역할 정보를 검색한다.
     *
     * @param pmParam 사용자역할CD
     * @return 사용자역할 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getRole(Map<String, ?> pmParam) throws Exception {
        return selectOne("RoleMap.getRoleList", pmParam);
    }

}
