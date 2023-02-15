/*
 * (@)# CntrService.java
 *
 * @author 정용재
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

package powerservice.business.usr.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.usr.service.CntrService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 센터 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정용재
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID Cntr
 */
@Service
public class CntrServiceImpl extends EgovAbstractServiceImpl implements CntrService {

    @Resource
    public CntrDAO cntrDAO;

    /**
     * 센터 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 센터 정보의 검색 수
     * @throws Exception
     */
    public int getCntrCount(Map<String, ?> pmParam) throws Exception {
        return cntrDAO.getCntrCount(pmParam);
    }

    public int getCount(String sCntrCd) throws Exception {
        Map<String, String> mParam = new HashMap<String, String>();
        mParam.put("cntr_cd", sCntrCd);
        return (Integer)cntrDAO.getCount(mParam);
    }

    /**
     * 센터 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 센터 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getCntrList(Map<String, ?> pmParam) throws Exception {
        return cntrDAO.getCntrList(pmParam);
    }

    public Map<String, Object> getCntr(String sId) throws Exception {
        Map<String, String> mParam = new HashMap<String, String>();
        mParam.put("cntr_cd", sId);
        return cntrDAO.getCntr(mParam);
    }


    /**
     * 센터 정보를 등록한다.
     *
     * @param pmParam 센터 정보
     * @throws Exception
     */
    public String insertCntr(Map<String, ?> pmParam) throws Exception {
        // 중복된 순서 자동증가
        /*Boolean bDupYn = (Boolean) pmParam.get("dup_yn");
        if (bDupYn != null && bDupYn) {
            cntrDAO.updateCntrOrder(pmParam);
        }*/
        String bDupYn = (String) pmParam.get("dup_yn");
        if (bDupYn != null && bDupYn.equals("Y")) {
            cntrDAO.updateCntrOrder(pmParam);
        }

        String sKey = "";
        int nResult = cntrDAO.insertCntr(pmParam);
        if (nResult > 0) {
            sKey = (String) pmParam.get("cntr_cd");
        }
        return sKey;
    }

    /**
     * 센터 정보를 수정한다.
     *
     * @param param 기준값 정보
     * @throws Exception
     */
    public int updateCntr(Map<String, ?> pmParam) throws Exception {
        // 중복된 순서 자동증가
        /*Boolean bDupYn = (Boolean) pmParam.get("dup_yn");
        if (bDupYn != null && bDupYn) {
            cntrDAO.updateCntrOrder(pmParam);
        }*/
        String bDupYn = (String) pmParam.get("dup_yn");
        if (bDupYn != null && bDupYn.equals("Y")) {
            cntrDAO.updateCntrOrder(pmParam);
        }

        return cntrDAO.updateCntr(pmParam);
    }

    /**
     * 센터 순서 최대값을 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 순서 최대값
     * @throws Exception
     */
    public int getCntrMaxOrder(Map<String, ?> pmParam) throws Exception {
        return cntrDAO.getCntrMaxOrder(pmParam);
    }

    /**
     * 센터 순서의 중복 건수를 조회한다
     *
     * @param pmParam 검색 조건
     * @return 센터 순서의 중복 건수
     * @throws Exception
     */
    public int getCntrDuplicateOrderCount(Map<String, ?> pmParam) throws Exception {
        return cntrDAO.getCntrDuplicateOrderCount(pmParam);
    }

}
