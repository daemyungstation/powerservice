/*
 * (@)# DlwCdService.java
 *
 * @author 석민
 * @version 1.0
 * @date 2016/02/03
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

package powerservice.business.dlw.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.dlw.service.DlwCdService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 대명라이프 코드 관리를 한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 석민
 * @version 1.0
 * @date 2016/02/03
 * @프로그램ID DlwCd
 */
@Service
public class DlwCdServiceImpl extends EgovAbstractServiceImpl implements DlwCdService {

    @Resource
    public DlwCdDAO dlwCdDAO;

    /**
     * 코드 정보(목록)를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 코드 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwCdListAll(Map<String, ?> pmParam) throws Exception {
        return dlwCdDAO.getDlwCdListAll(pmParam);
    }


    /**
     * 코드 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 코드 정보의 검색 건수
     * @throws Exception
     */
    public int getDlwCdCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) dlwCdDAO.getDlwCdCount(pmParam);
    }

    /**
     * 코드 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 코드 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwCdList(Map<String, ?> pmParam) throws Exception {
        return dlwCdDAO.getDlwCdList(pmParam);
    }
}
