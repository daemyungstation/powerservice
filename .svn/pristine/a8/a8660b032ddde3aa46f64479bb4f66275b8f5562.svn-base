/*
 * (@)# RoleUserService.java
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

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.sys.service.RoleUserService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 역할 사용자 정보 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID RoleUser
 */
@Service
public class RoleUserServiceImpl extends EgovAbstractServiceImpl implements RoleUserService {

    @Resource
    public RoleUserDAO roleUserDAO;

    /**
     * 역할 대상을 등록한다.
     *
     * @param pmParam 역할대상 정보
     * @throws Exception
     */
    public String insertRoleUser(Map<String, ?> pmParam) throws Exception {
        String sKey = "";
        int nResult = roleUserDAO.insertRoleUser(pmParam);
        if (nResult > 0) {
            sKey = (String) pmParam.get("role_trgt_id");
        }
        return sKey;
    }

    /**
     * 역할 대상을 수정한다.
     *
     * @param pmParam 역할대상 정보
     * @throws Exception
     */
    public int updateRoleUser(Map<String, ?> pmParam) throws Exception {
        return roleUserDAO.updateRoleUser(pmParam);
    }

    /**
     * 역할 대상을 삭제한다.
     *
     * @param pmParam 역할대상 정보
     * @throws Exception
     */
    public int deleteRoleUser(Map<String, ?> pmParam) throws Exception {
        return roleUserDAO.deleteRoleUser(pmParam);
    }

    /**
     * 역할대상을 조회하여 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 역할대상 정보의 검색 수
     * @throws Exception
     */

    public int getRoleUserCount(Map<String, ?> pmParam) throws Exception {
        return roleUserDAO.getRoleUserCount(pmParam);
    }

    /**
     * 역할대상 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 역할대상 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getRoleUserList(Map<String, ?> pmParam) throws Exception {
        return roleUserDAO.getRoleUserList(pmParam);
    }

    /**
     * 역할대상 정보(1건)를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 역할대상 리스트
     * @throws Exception
     */
    public Map<String, Object> getRoleUser(Map<String, ?> pmParam) throws Exception {
        return roleUserDAO.getRoleUser(pmParam);
    }

}
