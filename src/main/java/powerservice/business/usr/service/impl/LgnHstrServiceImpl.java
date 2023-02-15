/*
 * (@)# LgnHstrService.java
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

package powerservice.business.usr.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.usr.service.LgnHstrService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 로그인/아웃 이력 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID LgnHstr
 */
@Service
public class LgnHstrServiceImpl extends EgovAbstractServiceImpl implements LgnHstrService {

    @Resource
    public LgnHstrDAO lgnHstrDAO;

    /**
     * 로그인/아웃 이력 정보의 검색 수를 반환한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 로그인/아웃 이력 정보의 검색 수
     * @throws Exception
     */
    public int getLgnHstrCount(Map<String, ?> pmParam) throws Exception {
        return lgnHstrDAO.getLgnHstrCount(pmParam);
    }

    /**
     * 로그인/아웃 이력 정보를 검색한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 로그인/아웃 이력 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getLgnHstrList(Map<String, ?> pmParam) throws Exception {
        return lgnHstrDAO.getLgnHstrList(pmParam);
    }

    /**
     * 로그인/아웃 이력정보
     *
     * @pmParam pmParam 로그온이력 정보
     * @throws Exception
     */
    public int insertLgnHstr(Map<String, ?> pmParam) throws Exception {
        return lgnHstrDAO.insertLgnHstr(pmParam);
    }

}
