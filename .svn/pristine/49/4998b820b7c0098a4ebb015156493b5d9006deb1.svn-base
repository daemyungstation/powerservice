/*
 * (@)# ScrtServiceImpl.java
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

package powerservice.business.cam.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.cam.service.ExtcMstTrgtService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 스크립트 관리를 한다.
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 차건호
 * @version 1.0
 * @date 2015/04/28
 * @프로그램ID Scrt
 */

@Service
public class ExtcMstTrgtServiceImpl extends EgovAbstractServiceImpl implements ExtcMstTrgtService {

    @Resource
    public ExtcMstTrgtDAO extcMstTrgtDAO;

    /**
     * 스크립트 정보를 수정한다. 사용
     *
     * @param pmParam 스크립트 정보
     * @throws Exception
     */
    public int updateUnpy(Map<String, Object> pmParam) throws Exception {

        int nResult = 0;

        nResult =  extcMstTrgtDAO.updateUnpy(pmParam);

        return nResult;
    }

    /**
     * 회수시 정보를 검색한다. 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 대상고객추출조건 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getDeleteTrgtCustAlct(Map<String, ?> pmParam) throws Exception {
        return extcMstTrgtDAO.getDeleteTrgtCustAlct(pmParam);
    }

}
