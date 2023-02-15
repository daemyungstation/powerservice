/*
 * (@)# WrkScdlDtlDAO.java
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
 * 근무 스케줄 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID WrkScdlDtl
 */
@Repository
public class WrkScdlDtlDAO extends EgovAbstractMapper {

    /**
     * 근무 스케줄 정보의 검색 수를 반환한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 근무 스케줄 정보의 검색 수
     * @throws Exception
     */
    public int getWrkScdlDtlCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("WrkScdlDtlMap.getWrkScdlDtlCount", pmParam);
    }

    /**
     * 근무 스케줄 정보를 검색한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 근무 스케줄 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getWrkScdlDtlList(Map<String, ?> pmParam) throws Exception {
        return selectList("WrkScdlDtlMap.getWrkScdlDtlList", pmParam);
    }

    /**
     * 근무 스케줄 개별배정 정보를 등록한다
     *
     * @pmParam pmParam 근무 스케줄 정보
     * @throws Exception
     */
    public int insertWrkScdlDtl(Map<String, Object> pmParam) throws Exception {
        return insert("WrkScdlDtlMap.insertWrkScdlDtl", pmParam);
    }

    /**
     * 근무 스케줄 정보를 삭제한다.
     *
     * @param pmParam 근무유형 정보
     * @throws Exception
     */
    public int deleteWrkScdlDtlPart(Map<String, ?> pmParam) throws Exception {
        return delete("WrkScdlDtlMap.deleteWrkScdlDtlPart", pmParam);
    }

    /**
     * 근무 스케줄 정보를 삭제한다.
     *
     * @param pmParam 근무유형 정보
     * @throws Exception
     */
    public int deleteWrkScdlDtlAll(Map<String, ?> pmParam) throws Exception {
        return delete("WrkScdlDtlMap.deleteWrkScdlDtlAll", pmParam);
    }

    /**
     * 근무 일자 정보를 검색한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 근무 스케줄 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getDtList(Map<String, ?> pmParam) throws Exception {
        return selectList("WrkScdlDtlMap.getDtList", pmParam);
    }

}
