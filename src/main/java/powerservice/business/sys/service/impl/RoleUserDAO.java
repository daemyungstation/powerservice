/*
 * (@)# RoleUserDAO.java
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
 * 역할 사용자 정보 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 박의준
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID RoleUser
 */
@Repository
public class RoleUserDAO extends EgovAbstractMapper {

    /**
     * 역할 대상을 등록한다.
     *
     * @param pmParam 역할대상 정보
     * @throws Exception
     */
    public int insertRoleUser(Map<String, ?> pmParam) throws Exception {
        return insert("RoleUserMap.insertRoleUser", pmParam);
    }

    /**
     * 역할 대상을 수정한다.
     *
     * @param pmParam 역할대상 정보
     * @throws Exception
     */
    public int updateRoleUser(Map<String, ?> pmParam) throws Exception {
        return update("RoleUserMap.updateRoleUser", pmParam);
    }

    /**
     * 역할 대상을 삭제한다.
     *
     * @param pmParam 역할대상 정보
     * @throws Exception
     */
    public int deleteRoleUser(Map<String, ?> pmParam) throws Exception {
        return delete("RoleUserMap.deleteRoleUser", pmParam);
    }

    /**
     * 역할대상을 조회하여 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 역할대상 정보의 검색 수
     * @throws Exception
     */

    public int getRoleUserCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("RoleUserMap.getRoleUserCount", pmParam);
    }

    /**
     * 역할대상 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 역할대상 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getRoleUserList(Map<String, ?> pmParam) throws Exception {
        return selectList("RoleUserMap.getRoleUserList", pmParam);
    }

    /**
     * 역할대상 정보(1건)를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 역할대상 리스트
     * @throws Exception
     */
    public Map<String, Object> getRoleUser(Map<String, ?> pmParam) throws Exception {
        return selectOne("RoleUserMap.getRoleUserList", pmParam);
    }

}
