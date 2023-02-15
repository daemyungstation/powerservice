/*
 * (@)# GdsService.java
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

package powerservice.business.sys.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.sys.service.GdsService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 제품 정보 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID Gds
 */
@Service
public class GdsServiceImpl extends EgovAbstractServiceImpl implements GdsService {

    @Resource
    public GdsDAO gdsDAO;

    /**
     * 제품 정보를 등록한다.
     *
     * @param pmParam 제품 정보
     * @throws Exception
     */
    public String insertGds(Map<String, ?> pmParam) throws Exception {
        String sKey = "";
        int nResult = gdsDAO.insertGds(pmParam);
        if (nResult > 0) {
            sKey = (String) pmParam.get("gds_cd");
        }
        return sKey;
    }

    /**
     * 제품 정보를 수정한다.
     *
     * @param pmParam 제품 정보
     * @throws Exception
     */
    public int updateGds(Map<String, ?> pmParam) throws Exception {
        return gdsDAO.updateGds(pmParam);
    }

    /**
     * 제품 정보를 삭제한다.
     *
     * @param pmParam 제품 정보
     * @throws Exception
     */
    public int deleteGds(Map<String, ?> pmParam) throws Exception {
        return gdsDAO.deleteGds(pmParam);
    }

    /**
     * 제품 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 제품 정보의 검색 건수
     * @throws Exception
     */
    public int getGdsCount(Map<String, ?> pmParam) throws Exception {
        return gdsDAO.getGdsCount(pmParam);
    }

    /**
     * 제품 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 제품 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getGdsList(Map<String, ?> pmParam) throws Exception {
        return gdsDAO.getGdsList(pmParam);
    }

    /**
     * 제품 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 제품 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getGds(Map<String, ?> pmParam) throws Exception {
        return gdsDAO.getGds(pmParam);
    }

}
