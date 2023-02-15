/*
 * (@)# SessService.java
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

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.usr.service.SessInfoService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 세션 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID SessInfo
 */
@Service
public class SessInfoServiceImpl extends EgovAbstractServiceImpl implements SessInfoService {

    @Resource
    public SessInfoDAO sessInfoDAO;

    /**
     * 세션정보를 등록한다.
     *
     * @param pmParam 세션정보
     * @throws Exception
     */
    public int insertSessInfo(Map<String, ?> pmParam) throws Exception {
        return sessInfoDAO.insertSessInfo(pmParam);
    }

    /**
     * 세션정보를 검색한다.(1건)
     *
     * @param sId 사용자ID
     * @return 사용자 세션정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getSessInfo(String sId, String sCntrCd) throws Exception {
        Map<String, String> mParam = new HashMap<String, String>();
        mParam.put("user_id", sId);
        mParam.put("cntr_cd", sCntrCd);

        return sessInfoDAO.getSessInfo(mParam);
    }


    /**
     * 접근체크 정보를 검색한다.
     *
     * @param pmParam 접근정보
     * @return 접근체크정보
     * @throws Exception
     */
    public Map<String, Object> getAccessCheckInfo(Map<String, ?> pmParam) throws Exception {
        return sessInfoDAO.getAccessCheckInfo(pmParam);
    }

}
