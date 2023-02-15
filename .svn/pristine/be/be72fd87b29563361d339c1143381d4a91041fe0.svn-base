/*
 * (@)# PgmAthrDAO.java
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
 * 프로그램 권한 정보 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID PgmAthr
 */
@Repository
public class PgmAthrDAO extends EgovAbstractMapper {

    /**
     * 프로그램 권한 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 프로그램 권한 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getPgmAthrList(Map<String, ?> pmParam) throws Exception {
        return selectList("PgmAthrMap.getPgmAthrList", pmParam);
    }

    /**
     * 프로그램 권한 추가 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 프로그램 권한 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getPgmAthrAddList(Map<String, ?> pmParam) throws Exception {
        return selectList("PgmAthrMap.getPgmAthrAddList", pmParam);
    }

    /**
     * 프로그램 권한 정보를 등록한다
     *
     * @param pmParam 프로그램 권한 정보
     * @throws Exception
     */
    public int insertPgmAthr(Map<String, Object> pmParam) throws Exception {
        return insert("PgmAthrMap.insertPgmAthr", pmParam);
    }

    /**
     * 프로그램 권한 정보를 수정한다
     *
     * @param pmParam 프로그램 권한 정보
     * @throws Exception
     */
    public int updatePgmAthr(Map<String, Object> pmParam) throws Exception {
        return insert("PgmAthrMap.updatePgmAthr", pmParam);
    }

    /**
     * 프로그램 권한 정보를 삭제한다.
     *
     * @param pmParam 프로그램 권한 정보
     * @throws Exception
     */
    public int deletePgmAthr(Map<String, ?> pmParam) throws Exception {
        return delete("PgmAthrMap.deletePgmAthr", pmParam);
    }

}
