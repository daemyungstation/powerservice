/*
 * (@)# WklyRprgBasiDAO.java
 *
 * @author 박상수
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

package powerservice.business.wkly.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 주간보고 관리를 한다.
 *
 * Copyright (c) 2013 Company Inwoo Tech Inc.
 *
 * @author 박상수
 * @version 1.0
 * @date 2015/01/05
 * @프로그램ID WklyRprgBasiDAO
 */
@Repository
public class WklyRprgBasiDAO extends EgovAbstractMapper {

    /**
     * 주간보고 대상자 검색 수를 반환한다.(관리자)
     *
     * @pmParam pmParam 검색 조건
     * @return 주간보고 대상자 건수
     * @throws Exception
     */
    public int getWklyUserCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("WklyRprgBasiMap.getWklyUserCount", pmParam);
    }

    /**
     * 주간보고 대상자를 조회한다.(관리자)
     *
     * @pmParam pmParam 검색 조건
     * @return 주간보고 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getWklyUserList(Map<String, ?> pmParam) throws Exception {
        return selectList("WklyRprgBasiMap.getWklyUserList", pmParam);
    }

    /**
     * 주간보고 대상자를 조회한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 주간보고 목록
     * @throws Exception
     */
    public Map<String, Object> getWklybswrList(Map<String, ?> pmParam) throws Exception {
        return selectOne("WklyRprgBasiMap.getWklybswrList", pmParam);
    }

    /**
     * 주간보고 기본정보를 저장한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 주간보고 목록
     * @throws Exception
     */
    public int insertWklyReport(Map<String, Object> pmParam) throws Exception {
        return insert("WklyRprgBasiMap.insertWklyReport", pmParam);
    }

    /**
     * 주간보고 수정시 제출시간과 상태를 변경한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 주간보고 목록
     * @throws Exception
     */
    public int updateWklyReport(Map<String, Object> pmParam) throws Exception {
        return update("WklyRprgBasiMap.updateWklyReport", pmParam);
    }
    /**

     * 주간보고를 제출한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 주간보고 목록
     * @throws Exception
     */
    public int commitWklyReport(Map<String, Object> pmParam) throws Exception {
        return update("WklyRprgBasiMap.commitWklyReport", pmParam);
    }


    /**
     * 주간보고 결과 검색 수를 반환한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 주간보고 대상자 건수
     * @throws Exception
     */
    public int getWklyReportResultCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("WklyRprgBasiMap.getWklyReportResultCount", pmParam);
    }


    /**
     * 주간보고 결과를 조회한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 주간보고 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getWklyReportResult(Map<String, ?> pmParam) throws Exception {
        return selectList("WklyRprgBasiMap.getWklyReportResult", pmParam);
    }
    /**
     * 사용자를 조회한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 주간보고 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getExcelUserList(Map<String, ?> pmParam) throws Exception {
        return selectList("WklyRprgBasiMap.getExcelUserList", pmParam);
    }

    /**
     * 첨부파일을 조회한다.(주간보고 결과서용)
     *
     * @pmParam pmParam 검색 조건
     * @return 첨부파일 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getWklyFile(Map<String, ?> pmParam) throws Exception {
        return selectList("WklyRprgBasiMap.getWklyFile", pmParam);
    }

}
