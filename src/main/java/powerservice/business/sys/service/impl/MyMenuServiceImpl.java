/*
 * (@)# MyMenuServiceImpl.java
 *
 * @author 정용재
 * @version 1.0
 * @date 2016/06/09
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

import powerservice.business.sys.service.MyMenuService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * MyMenu 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정용재
 * @version 1.0
 * @date 2016/06/09
 * @프로그램ID MyMenu
 */
@Service
public class MyMenuServiceImpl extends EgovAbstractServiceImpl implements MyMenuService {

    @Resource
    public MyMenuDAO myMenuDAO;

    public int getMyMenuCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) myMenuDAO.getMyMenuCount(pmParam);
    }

    public List<Map<String, Object>> getMyMenuList(Map<String, ?> pmParam) throws Exception {
        return myMenuDAO.getMyMenuList(pmParam);
    }

    /**
     * MyMenu 정보를 등록한다.
     *
     * @param pmParam MyMenu 정보
     * @throws Exception
     */
    public String insertMyMenu(Map<String, Object> pmParam) throws Exception {
        String skey = "";

        int nResult = myMenuDAO.insertMyMenu(pmParam);
        if (nResult > 0) {
            skey = (String) pmParam.get("my_menu_id");
        }

        return skey;
    }

    /**
     * MyMenu 정보를 수정한다.
     *
     * @param pmParam MyMenu 정보
     * @throws Exception
     */
    public String updateMyMenu(Map<String, Object> pmParam) throws Exception {
        String skey = "";

        int nResult = myMenuDAO.updateMyMenu(pmParam);
        if (nResult > 0) {
            skey = (String) pmParam.get("my_menu_id");
        }

        return skey;
    }

    /**
     * MyMenu 정보를 삭제한다.
     *
     * @param pmParam MyMenu 정보
     * @throws Exception
     */
    public int deleteMyMenu(Map<String, ?> pmParam) throws Exception {
        int nResult = myMenuDAO.deleteMyMenu(pmParam);

        return nResult;
    }
}
