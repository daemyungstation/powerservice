/*
 * (@)# RoleService.java
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

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.sys.service.RoleService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

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
@Service
public class RoleServiceImpl extends EgovAbstractServiceImpl implements RoleService {

    @Resource
    public RoleDAO releDAO;

    /**
     * 사용자역할 정보를 등록한다.
     *
     * @param pmParam 사용자역할 정보
     * @throws Exception
     */
    public String insertRole(Map<String, ?> pmParam) throws Exception {
        String sKey = "";
        int nResult = releDAO.insertRole(pmParam);
        if (nResult > 0) {
            sKey = (String) pmParam.get("role_cd");
        }
        return sKey;
    }

    /**
     * 사용자역할 정보를 수정한다.
     *
     * @param pmParam 사용자역할 정보
     * @throws Exception
     */
    public int updateRole(Map<String, ?> pmParam) throws Exception {
        return releDAO.updateRole(pmParam);
    }

    /**
     * 사용자역할 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 사용자역할 정보의 검색 건수
     * @throws Exception
     */
    public int getRoleCount(Map<String, ?> pmParam) throws Exception {
        return releDAO.getRoleCount(pmParam);
    }

    /**
     * 사용자역할 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 사용자역할 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getRoleList(Map<String, ?> pmParam) throws Exception {
        return releDAO.getRoleList(pmParam);
    }

    /**
     * 사용자역할 정보를 검색한다.
     *
     * @param pmParam 사용자역할CD
     * @return 사용자역할 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getRole(Map<String, ?> pmParam) throws Exception {
        return releDAO.getRole(pmParam);
    }

}
