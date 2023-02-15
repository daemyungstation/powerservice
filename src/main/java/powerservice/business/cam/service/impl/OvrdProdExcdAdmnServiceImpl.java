/*
 * (@)# OvrdProdExcdAdmnServiceImpl.java
 *
 * @author 정용재
 * @version 1.0
 * @date 2016/07/07
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

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import powerservice.business.cam.service.OvrdProdExcdAdmnService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 연체상품제외 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정용재
 * @version 1.0
 * @date 2016/08/02
 * @프로그램ID PS221200
 *
 */
@Service
public class OvrdProdExcdAdmnServiceImpl extends EgovAbstractServiceImpl implements OvrdProdExcdAdmnService {
    @Resource
    public OvrdProdExcdAdmnDAO ovrdProdExcdAdmnDAO;

    /**
     * 연체상품제외 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return OvrdProdExcdAdmn 정보의 검색 수
     * @throws Exception
     */
    public int getOvrdProdExcdAdmnCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) ovrdProdExcdAdmnDAO.getOvrdProdExcdAdmnCount(pmParam);
    }

    /**
     * 연체상품제외 정보(목록)를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 연체상품제외 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getOvrdProdExcdAdmnList(Map<String, ?> pmParam) throws Exception {
        return ovrdProdExcdAdmnDAO.getOvrdProdExcdAdmnList(pmParam);
    }

    /**
     * 연체상품제외 정보를 등록/수정한다.
     *
     * @param pmParam 연체상품제외 정보
     * @throws Exception
     */
    public String saveOvrdProdExcdAdmn(Map<String, Object> pmParam) throws Exception {
        String sKey = StringUtils.defaultString((String) pmParam.get("mode"));

        int nResult = 0;

        if ("C".equals(sKey)) {
            nResult = ovrdProdExcdAdmnDAO.insertOvrdProdExcdAdmn(pmParam);
        } else {
            nResult = ovrdProdExcdAdmnDAO.updateOvrdProdExcdAdmn(pmParam);
        }

        if (nResult > 0) {
            sKey = (String) pmParam.get("prod_cd");
        }

        return sKey;
    }
}
