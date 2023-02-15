/*
 * (@)# DlwProdServiceImpl.java
 *
 * @author 석민
 * @version 1.0
 * @date 2016/02/16
 * Copyright (c) 2016 by Inwoo tech inc, KOREA. All Rights Reserved.
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

import powerservice.business.cam.service.DlwUplusService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 사원 정보를 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 석민
 * @version 1.0
 * @date 2016/02/16
 * @프로그램ID DlwVatSvc
 */
@Service
public class DlwUplusServiceImpl extends EgovAbstractServiceImpl implements DlwUplusService {

    @Resource
    public DlwUplusDAO dlwUplusDAO;
    
    /**
     * 제품의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 해약현황 정보의 검색 건수
     * @throws Exception
     */
    public int getUplusMainCount(Map<String, Object> pmParam) throws Exception {
        return (Integer) dlwUplusDAO.getUplusMainCount(pmParam);
    }

    /**
     * 제품 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 상품 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getUplusMainList(Map<String, Object> pmParam) throws Exception {
        return dlwUplusDAO.getUplusMainList(pmParam);
    }
    
    /**
     * 제품 조회
     *
     * @param pmParam 검색 조건
     * @return 상품 상세정보
     * @throws Exception
     */
    public List<Map<String, Object>> getUplusPopList(Map<String, Object> pmParam) throws Exception {
        return dlwUplusDAO.getUplusPopList(pmParam);
    }
    
    public int existsUplusListData(Map<String, ?> pmParam) throws Exception {
        return dlwUplusDAO.existsUplusListData(pmParam);
    }

    public int insertUplusList(Map<String, ?> pmParam) throws Exception {
        return dlwUplusDAO.insertUplusList(pmParam);
    }
}
