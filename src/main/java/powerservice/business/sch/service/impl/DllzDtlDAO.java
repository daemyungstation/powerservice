/*
 * (@)# DllzDtlDAO.java
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

package powerservice.business.sch.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 근태내역 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID DllzDtl
 */
@Repository
public class DllzDtlDAO extends EgovAbstractMapper {

    /**
     * 근태내역 정보의 검색 수를 반환한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 근태내역 정보의 검색 수
     * @throws Exception
     */
    public int getDllzDtlCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DllzDtlMap.getDllzDtlCount", pmParam);
    }

    /**
     * 근태내역 정보를 검색한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 근태내역 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getDllzDtlList(Map<String, ?> pmParam) throws Exception {
        return selectList("DllzDtlMap.getDllzDtlList", pmParam);
    }

    /**
     * 근태내역 정보를 검색한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 근태내역 리스트
     * @throws Exception
     */
    public Map<String, Object> getDllzDtl(Map<String, ?> pmParam) throws Exception {
        return selectOne("DllzDtlMap.getDllzDtlList", pmParam);
    }

    /**
     * 근태 정보를 등록한다.
     *
     * @pmParam pmParam 근태 정보
     * @throws Exception
     */
    public int insertDllzDtl(Map<String, ?> pmParam) throws Exception {
        return insert("DllzDtlMap.insertDllzDtl", pmParam);
    }

    /**
     * 근태 정보를 수정한다.
     *
     * @pmParam pmParam 근태 정보
     * @throws Exception
     */
    public int updateDllzDtl(Map<String, ?> pmParam) throws Exception {
        return update("DllzDtlMap.updateDllzDtl", pmParam);
    }

    /**
     * 근태내역 정보를 삭제한다.
     *
     * @pmParam pmParam 근태내역 정보
     * @throws Exception
     */
    public int deleteDllzDtl(Map<String, ?> pmParam) throws Exception {
        return delete("DllzDtlMap.deleteDllzDtl", pmParam);
    }


    /**
     * 로그인 근태내역 정보를 등록한다.
     *
     * @pmParam pmParam 근태내역 정보
     * @throws Exception
     */
    public int insertDllzDtlForLogin(Map<String, Object> pmParam) throws Exception {
        return insert("DllzDtlMap.insertDllzDtlForLogin", pmParam);
    }

    /**
     * 로그아웃 근태내역 정보를 등록한다.
     *
     * @pmParam pmParam 근태내역 정보
     * @throws Exception
     */
    public int updateDllzDtlForLogout(Map<String, Object> pmParam) throws Exception {
        return update("DllzDtlMap.updateDllzDtlForLogout", pmParam);
    }

    /**
     * 근태 등록을 위한 근무유형 정보를 검색한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 근태내역 정보의 검색 수
     * @throws Exception
     */
    public Map<String, Object> getScdlWrkTypForDllz(Map<String, ?> pmParam) throws Exception {
        return selectOne("DllzDtlMap.getScdlWrkTypForDllz", pmParam);
    }

    /**
     * 근태관리 근무유형 정보를 검색한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 근태내역 정보의 검색 수
     * @throws Exception
     */
    public Map<String, Object> getDllzWrkTyp(Map<String, ?> pmParam) throws Exception {
        return selectOne("DllzDtlMap.getDllzWrkTyp", pmParam);
    }

}
