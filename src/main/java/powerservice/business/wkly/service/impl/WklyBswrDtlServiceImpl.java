/*
 * (@)# WklyRprgBasiService.java
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

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.wkly.service.WklyBswrDtlService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

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
@Service
public class WklyBswrDtlServiceImpl extends EgovAbstractServiceImpl implements WklyBswrDtlService {

    @Resource
    public WklyBswrDtlDAO wklyBswrDtlDAO;

    @Resource
    public WklyRprgBasiDAO wklyRprgBasiDAO;

    /**
     * 주간업무 검색 수를 반환한다.
     *
     * @param paramHash 검색 조건
     * @return 주간보고 대상자 건수
     * @throws Exception
     */
    public int getWklyBusinessCount(Map<String, ?> paramHash) throws Exception {
        return (Integer) wklyBswrDtlDAO.getWklyBusinessCount(paramHash);
    }

    /**
     * 주간보고 대상자를 조회한다.
     *
     * @param paramHash 검색 조건
     * @return 주간보고 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getWklyBusinessList(Map<String, ?> paramHash) throws Exception {
        return wklyBswrDtlDAO.getWklyBusinessList(paramHash);
    }

    /**
     * 주간보고 대상자를 조회한다.(1건)
     *
     * @param paramHash 검색 조건
     * @return 주간보고 목록
     * @throws Exception
     */
    public Map<String, Object> getWklyBusiness(Map<String, ?> paramHash) throws Exception {
        return wklyBswrDtlDAO.getWklyBusiness(paramHash);
    }

    /**
     * 주간보고 활동내역을 저장한다.
     *
     * @param paramHash 검색 조건
     * @return 주간보고 목록
     * @throws Exception
     */
    public int saveWklyBusiness(Map<String, Object> param) throws Exception {
        int result = 0;
        if (wklyBswrDtlDAO.getWklyBusinessCount(param) > 0) {
            result = wklyBswrDtlDAO.updateWklyBusiness(param);
        } else {
            result = wklyBswrDtlDAO.insertWklyBusiness(param);
        }

        if (param.get("superfg").equals("N")) {
            wklyRprgBasiDAO.updateWklyReport(param);
        }

        return result;
    }

    /**
     * 주간보고 활동내역을 삭제한다.
     *
     * @param param 검색 조건
     * @throws Exception
     */
    public int deleteWklyBusiness(Map<String, Object> param) throws Exception {
        if (param.get("superfg").equals("N")) {
            wklyRprgBasiDAO.updateWklyReport(param);
        }

        return wklyBswrDtlDAO.deleteWklyBusiness(param);
    }

}
