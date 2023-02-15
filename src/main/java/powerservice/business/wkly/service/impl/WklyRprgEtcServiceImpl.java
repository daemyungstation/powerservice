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

package powerservice.business.wkly.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.wkly.service.WklyRprgEtcService;
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
public class WklyRprgEtcServiceImpl extends EgovAbstractServiceImpl implements WklyRprgEtcService {

    @Resource
    public WklyRprgEtcDAO wklyRprgEtcDAO;

    @Resource
    public WklyRprgBasiDAO wklyRprgBasiDAO;


    /**
     * 주간보고 기타사항을 조회한다.
     *
     * @param param 검색 조건
     * @return 주간보고 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getWklyEtcList(Map<String, ?> param) throws Exception {
        return wklyRprgEtcDAO.getWklyEtcList(param);
    }

    /**
     * 기타사항 정보를 저장한다.
     *
     * @param param 검색 조건
     * @return 주간보고 목록
     * @throws Exception
     */
    public int saveWklyEtc(Map<String, Object> param) throws Exception {
        int result = 0;
        if (wklyRprgEtcDAO.getWklyEtcCount(param) > 0) {
            result = wklyRprgEtcDAO.updateWklyEtc(param);
        } else {
            result = wklyRprgEtcDAO.insertWklyEtc(param);
        }

        if (param.get("superfg").equals("N")) {
            wklyRprgBasiDAO.updateWklyReport(param);
        }

        return result;
    }

    /**
     * 주간보고 기타사항을 조회한다.
     *
     * @param param 검색 조건
     * @return 주간보고 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getEtcResult(Map<String, ?> param) throws Exception {
        return wklyRprgEtcDAO.getEtcResult(param);
    }

    /**
     * 주간보고 - 지시사항을 삭제한다.
     *
     * @param param 교육평가
     * @throws Exception
     */
    public int deleteWklyEtc(Map<String, Object> param) throws Exception {
        if (param.get("superfg").equals("N")) {
            wklyRprgBasiDAO.updateWklyReport(param);
        }

        return wklyRprgEtcDAO.deleteWklyEtc(param);
    }
}
