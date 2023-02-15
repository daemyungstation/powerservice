/*
 * (@)# UnpyMngeServiceImpl.java
 *
 * @author 정용재
 * @version 1.0
 * @date 2016/07/14
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

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.cam.service.UnpyMngeService;
import powerservice.common.util.CommonUtils;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 원장테이블
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정훈
 * @version 1.0
 * @date 2016/07/14
 * @프로그램ID UnpyMnge
 */


@Service
public class UnpyMngeServiceImpl extends EgovAbstractServiceImpl implements UnpyMngeService {

    @Resource
    public UnpyMngeDAO unpyMngeDAO;

    public int getUnpyMngeCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) unpyMngeDAO.getUnpyMngeCount(pmParam);
    }

    public List<Map<String, Object>> getUnpyMngeList(Map<String, ?> pmParam) throws Exception {
        return unpyMngeDAO.getUnpyMngeList(pmParam);
    }

    public List<Map<String, Object>> getUnpyMngeReportWithIchaeDtCnt(Map<String, ?> pmParam) throws Exception {
        return unpyMngeDAO.getUnpyMngeReportWithIchaeDtCnt(pmParam);
    }

    public List<Map<String, Object>> getUnpyMngeReportWithIchaeDtPrice(Map<String, ?> pmParam) throws Exception {
        return unpyMngeDAO.getUnpyMngeReportWithIchaeDtPrice(pmParam);
    }

    public List<Map<String, Object>> getUnpyMngeReportWithTotal(Map<String, ?> pmParam) throws Exception {
        return unpyMngeDAO.getUnpyMngeReportWithTotal(pmParam);
    }

    public List<Map<String, Object>> getUnpyMngeReportWithCnsr(Map<String, ?> pmParam) throws Exception {
        return unpyMngeDAO.getUnpyMngeReportWithCnsr(pmParam);
    }

    public List<Map<String, Object>> getUnpyMngeReportWithProdGbnCdTotal(Map<String, ?> pmParam) throws Exception {
      //   CommonUtils.printLog(pmParam);
        return unpyMngeDAO.getUnpyMngeReportWithProdGbnCdTotal(pmParam);
    }

    public List<Map<String, Object>> getUnpyMngeReportWithProdGbnCd(Map<String, ?> pmParam) throws Exception {

        return unpyMngeDAO.getUnpyMngeReportWithProdGbnCd(pmParam);
    }

    public List<Map<String, Object>> getUnpyMngeReportWithPayState(Map<String, ?> pmParam) throws Exception {
        return unpyMngeDAO.getUnpyMngeReportWithPayState(pmParam);
    }

    public List<Map<String, Object>> getUnpyMngeReportWithPayState2(Map<String, ?> pmParam) throws Exception {
        return unpyMngeDAO.getUnpyMngeReportWithPayState2(pmParam);
    }

    public List<Map<String, Object>> getUnpyMngeReportWithSection2(Map<String, ?> pmParam) throws Exception {
        return unpyMngeDAO.getUnpyMngeReportWithSection2(pmParam);
    }

    public List<Map<String, Object>> getUnpyMngeReportWithCamptype(Map<String, ?> pmParam) throws Exception {
        return unpyMngeDAO.getUnpyMngeReportWithCamptype(pmParam);
    }
}
