/*
 * (@)# DataAthrQuryServiceImpl.java
 *
 * @author 최현식
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

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.dlw.service.DataAthrQuryService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 데이터 권한별 검색조건을 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/02/03
 * @프로그램ID DataAthrQury
 */
@Service
public class DataAthrQuryServiceImpl extends EgovAbstractServiceImpl implements DataAthrQuryService {

    @Resource
    public DataAthrQuryDAO dataAthrQuryDAO;

    /**
     * 데이터 권한별 검색조건을 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 데이터 권한별 검색조건
     * @throws Exception
     */
    public String getDataAthrQury(Map<String, ?> pmParam) throws Exception {
        return dataAthrQuryDAO.getDataAthrQury(pmParam);
    }

}
