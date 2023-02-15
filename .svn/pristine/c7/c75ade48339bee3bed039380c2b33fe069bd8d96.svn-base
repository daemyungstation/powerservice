/*
 * (@)# PgmDAO.java
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
 * 프로그램 개별기능 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정훈
 * @version 1.0
 * @date 2016/04/06
 * @프로그램ID PgmIndvFnct
 */
@Repository
public class PgmIndvFnctDAO extends EgovAbstractMapper {
    /**
     * 프로그램 개별기능 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 프로그램 정보의 검색 건수
     * @throws Exception
     */
    public int getPgmIndvFnctCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("PgmIndvFnctMap.getPgmIndvFnctCount", pmParam);
    }

    /**
     * 프로그램 개별기능을 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 프로그램 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getPgmIndvFnctList(Map<String, ?> pmParam) throws Exception {
        return selectList("PgmIndvFnctMap.getPgmIndvFnctList", pmParam);
    }

    /**
     * 프로그램 개별기능을 등록한다.
     *
     * @param pmParam 프로그램 정보
     * @throws Exception
     */
    public int insertPgmIndvFnct(Map<String, ?> pmParam) throws Exception {
        return insert("PgmIndvFnctMap.insertPgmIndvFnct", pmParam);
    }

    /**
     * 프로그램 개별기능을 수정한다.
     *
     * @param pmParam 프로그램 정보
     * @throws Exception
     */
    public int updatePgmIndvFnct(Map<String, ?> pmParam) throws Exception {
        return update("PgmIndvFnctMap.updatePgmIndvFnct", pmParam);
    }
    /**
     * 프로그램 정보를 삭제한다.
     *
     * @param param 프로그램 정보
     * @throws Exception
     */
    public int deletePgmIndvFnct(Map<String, ?> pmParam) throws Exception {
        return delete("PgmIndvFnctMap.deletePgmIndvFnct", pmParam);
    }

}
