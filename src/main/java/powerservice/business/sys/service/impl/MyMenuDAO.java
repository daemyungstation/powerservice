/*
 * (@)# MyMenuDAO.java
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

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

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
@Repository
public class MyMenuDAO extends EgovAbstractMapper {
    /**
     * MyMenu 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return MyMenu 정보의 검색 수
     * @throws Exception
     */
    public int getMyMenuCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("MyMenuMap.getMyMenuCount", pmParam);
    }

    /**
     * MyMenu 정보(목록)를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return MyMenu 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getMyMenuList(Map<String, ?> pmParam) throws Exception {
        return selectList("MyMenuMap.getMyMenuList", pmParam);
    }

    /**
     * MyMenu 정보를 등록한다.
     *
     * @param pmParam 검색 조건
     * @return MyMenu 리스트
     * @throws Exception
     */
    public int insertMyMenu(Map<String, ?> pmParam) throws Exception {
        return insert("MyMenuMap.insertMyMenu", pmParam);
    }

    /**
     * MyMenu 정보를 수정한다.
     *
     * @param pmParam 검색 조건
     * @return MyMenu 리스트
     * @throws Exception
     */
    public int updateMyMenu(Map<String, ?> pmParam) throws Exception {
        return update("MyMenuMap.updateMyMenu", pmParam);
    }

    /**
     * MyMenu 정보를 삭제한다.
     *
     * @param pmParam 검색 조건
     * @return MyMenu 리스트
     * @throws Exception
     */
    public int deleteMyMenu(Map<String, ?> pmParam) throws Exception {
        return delete("MyMenuMap.deleteMyMenu", pmParam);
    }
}
