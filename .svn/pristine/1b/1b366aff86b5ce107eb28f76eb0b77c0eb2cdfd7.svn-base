/*
 * (@)# TblChkDAO.java
 *
 * @author 김현경
 * @version 1.0
 * @date 2015/07/22
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

package powerservice.business.mta.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 컬럼 기본값 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 김현경
 * @version 1.0
 * @date 2015/07/22
 * @프로그램ID TblChk
 */
@Repository
public class TblChkDAO extends EgovAbstractMapper {

    /**
     * 테이블 체크의 검색 수를 반환한다. (15.07.22)
     *
     * @param pmParamHash 검색 조건
     * @return 테이블 체크 정보의 검색 건수
     * @throws Exception
     */
    public int getTblChkCount(Map<String, ?> pmParam) throws Exception {
        return (Integer)selectOne("TblChkMap.getTblChkCount", pmParam);
    }

    /**
     * 테이블 체크를 검색한다. (15.07.22)
     *
     * @param pmParamHash 검색 조건
     * @return 테이블 체크 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getTblChkList(Map<String, ?> pmParam) throws Exception {
        return selectList("TblChkMap.getTblChkList", pmParam);
    }

    /**
     * 테이블 체크 상세 검색 수를 반환한다. (15.08.05)
     *
     * @param pmParamHash 검색 조건
     * @return 테이블 체크 상세 정보의 검색 건수
     * @throws Exception
     */
    public int getTblDtailCount(Map<String, ?> pmParam) throws Exception {
        return (Integer)selectOne("TblChkMap.getTblDtailCount", pmParam);
    }

    /**
     * 테이블 체크 상세를 검색한다. (15.08.05)
     *
     * @param pmParamHash 검색 조건
     * @return 테이블 체크 상세 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getTblDetailList(Map<String, ?> pmParam) throws Exception {
        return selectList("TblChkMap.getTblDetailList", pmParam);
    }

    /**
     * 컬럼 체크의 검색 수를 반환한다. (15.07.22)
     *
     * @param pmParamHash 검색 조건
     * @return 컬럼 체크 정보의 검색 건수
     * @throws Exception
     */
    public int getColmChkCount(Map<String, ?> pmParam) throws Exception {
        return (Integer)selectOne("TblChkMap.getColmChkCount", pmParam);
    }

    /**
     * 컬럼 체크를 검색한다. (15.07.22)
     *
     * @param pmParamHash 검색 조건
     * @return 컬럼 체크 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getColmChkList(Map<String, ?> pmParam) throws Exception {
        return selectList("TblChkMap.getColmChkList", pmParam);
    }

    /**
     * 컬럼 상세 체크의 검색 수를 반환한다. (15.07.22)
     *
     * @param pmParamHash 검색 조건
     * @return 컬럼 상세 체크 정보의 검색 건수
     * @throws Exception
     */
    public int getColmDtailCount(Map<String, ?> pmParam) throws Exception {
        return (Integer)selectOne("TblChkMap.getColmDtailCount", pmParam);
    }

    /**
     * 컬럼 상세 체크를 검색한다. (15.07.22)
     *
     * @param pmParamHash 검색 조건
     * @return 컬럼 상세 체크 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getColmDetailList(Map<String, ?> pmParam) throws Exception {
        return selectList("TblChkMap.getColmDetailList", pmParam);
    }

    /**
     * 도메인 체크의 검색 수를 반환한다. (15.08.06)
     *
     * @param pmParamHash 검색 조건
     * @return 도메인 체크 정보의 검색 건수
     * @throws Exception
     */
    public int getDonChkCount(Map<String, ?> pmParam) throws Exception {
        return (Integer)selectOne("TblChkMap.getDonChkCount", pmParam);
    }

    /**
     * 도메인 체크를 검색한다. (15.08.06)
     *
     * @param pmParamHash 검색 조건
     * @return 도메인 체크 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDonChkList(Map<String, ?> pmParam) throws Exception {
        return selectList("TblChkMap.getDonChkList", pmParam);
    }
}
