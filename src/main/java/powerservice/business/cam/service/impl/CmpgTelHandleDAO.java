/*
 * (@)# CmpgAlctDAO.java
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

package powerservice.business.cam.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 캠페인할당 관리를 한다.
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정훈
 * @version 1.0
 * @date 2015/10/01
 * @프로그램ID CmpgTelHandle
 */

@Repository
public class CmpgTelHandleDAO extends EgovAbstractMapper {


    /**
     * 진행중인 캠페인 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 캠페인할당 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getCmpgTelHandleTotalList(Map<String, ?> pmParam) throws Exception {
        return selectList("CmpgTelHandleMap.getCmpgTelHandleTotalList", pmParam);
    }

    /**
     * 대상고객 할당정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 대상고객할당 정보의 검색 건수
     * @throws Exception
     */
    public int getTrgtCustTelHandleCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("CmpgTelHandleMap.getTrgtCustTelHandleCount", pmParam);
    }

    /**
     * 대상고객 할당정보를 검색한다(캠페인(전화)처리현황).
     *
     * @param pmParam 검색 조건
     * @return 대상고객 할당 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getTrgtCustTelHandleList(Map<String, ?> pmParam) throws Exception {
        return selectList("CmpgTelHandleMap.getTrgtCustTelHandleList", pmParam);
    }
    
    /**
     * 대상고객 할당정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 대상고객할당 정보의 검색 건수
     * @throws Exception
     */
    public int getNewTypeTrgtCustTelHandleCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("CmpgTelHandleMap.getNewTypeTrgtCustTelHandleCount", pmParam);
    }

    /**
     * 대상고객 할당정보를 검색한다(캠페인(전화)처리현황).
     *
     * @param pmParam 검색 조건
     * @return 대상고객 할당 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getNewTypeTrgtCustTelHandleList(Map<String, ?> pmParam) throws Exception {
        return selectList("CmpgTelHandleMap.getNewTypeTrgtCustTelHandleList", pmParam);
    }

    /**
     * 캠페인 결과정보를 수정한다.(ajax)
     *
     * @param pmParam 캠페인 결과정보
     * @throws Exception
     */
    public int updateCmpgTelHandleResult(Map<String, ?> pmParam) throws Exception {
        return update("CmpgTelHandleMap.updateCmpgTelHandleResult", pmParam);
    }

    public int updateTrgtCustByCustId(Map<String, ?> pmParam) throws Exception {
        return update("CmpgTelHandleMap.updateTrgtCustByCustId", pmParam);
    }

    /**
     * 대상고객 할당정보를 검색한다.
     *
     * @param pmParam 할당정보 Key (TRGT_LIST_ID,CNSR_ID,CMPG_ID,TRGT_CUST_DTPT_ID)
     * @return 할당정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getTrgtCustCmpgTelHandle(Map<String, Object> pmParam) throws Exception {
        return selectOne("CmpgTelHandleMap.getTrgtCustTelHandleList", pmParam);
    }

    /**
     * 캠페인발신이력 정보를 등록한다.
     *
     * @param pmParam 검색 조건
     * @return 캠페인발신이력 정보
     * @throws Exception
     */
    public int insertDpmsReslHstr(Map<String, ?> pmParam) throws Exception {
        return insert("CmpgTelHandleMap.insertDpmsReslHstr", pmParam);
    }

    /**
     * 대상고객 할당정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 대상고객할당 정보의 검색 건수
     * @throws Exception
     */
    public int getDirectTrgtCustTelHandleCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("CmpgTelHandleMap.getDirectTrgtCustTelHandleCount", pmParam);
    }

    /**
     * 대상고객 할당정보를 검색한다(캠페인(전화)처리현황).
     *
     * @param pmParam 검색 조건
     * @return 대상고객 할당 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getDirectTrgtCustTelHandleList(Map<String, ?> pmParam) throws Exception {
        return selectList("CmpgTelHandleMap.getDirectTrgtCustTelHandleList", pmParam);
    }
    
    /**
     * 대상고객 할당정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 대상고객할당 정보의 검색 건수
     * @throws Exception
     */
    public int getUplusTrgtCustTelHandleCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("CmpgTelHandleMap.getUplusTrgtCustTelHandleCount", pmParam);
    }

    /**
     * 대상고객 할당정보를 검색한다(캠페인(전화)처리현황).
     *
     * @param pmParam 검색 조건
     * @return 대상고객 할당 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getUplusTrgtCustTelHandleList(Map<String, ?> pmParam) throws Exception {
        return selectList("CmpgTelHandleMap.getUplusTrgtCustTelHandleList", pmParam);
    }
}
