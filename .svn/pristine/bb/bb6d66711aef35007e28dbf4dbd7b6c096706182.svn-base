/*
 * (@)# CscoBasiServiceImpl.java
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

package powerservice.business.biz.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.biz.service.SlopActvRprgService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 사업원장관리 -> 영업 활동정보  관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정훈
 * @version 1.0
 * @date 2015/08/07
 * @프로그램ID <PS-UI-DS09>
 */
@Service
public class SlopActvRprgServiceImpl extends EgovAbstractServiceImpl implements SlopActvRprgService {

    @Resource
    public SlopActvRprgDAO slopActvRprgDAO;


    /**
     * 사업원장관리 -> 영업 활동정보를 등록한다.
     *
     * @param pmParam 영업 활동정보
     * @throws Exception
     */
    public String insertSlopActvRprg(Map<String, ?> pmParam) throws Exception {
        String sKey = "";

        int nResult = slopActvRprgDAO.insertSlopActvRprg(pmParam);
        if (nResult > 0) {
            sKey = (String) pmParam.get("slop_actv_rprg_id");
        }
        return sKey;
    }


    /**
     *사업원장관리 -> 영업 활동정보를 수정한다.
     *
     * @param pmParam 영업 활동정보
     * @throws Exception
     */
    public int updateSlopActvRprg(Map<String, ?> pmParam) throws Exception {
    	return  slopActvRprgDAO.updateSlopActvRprg(pmParam);
    }

    /**
     *사업원장관리 -> 영업 활동정보의 검색 수를 반환한다.
     *
     * @param pmParam 영업활동정보
     * @return 영업활동정보의 검색수
     * @throws Exception
     */
    public int getSlopActvRprgCount(Map<String, ?> pmParam) throws Exception {
    	return slopActvRprgDAO.getSlopActvRprgCount(pmParam);
    }


    /**
     * 사업원장관리 -> 영업 활동정보를 검색한다.
     *
     * @param pmParam 영업 활동정보
     * @return 영업 활동 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getSlopActvRprgList(Map<String, ?> pmParam) throws Exception {
    	return slopActvRprgDAO.getSlopActvRprgList(pmParam);
    }


    /**
     * 사업원장관리 -> 영업 활동정보를 검색한다.
     *
     * @param psId 영업활동정보 ID
     * @return 영업 활동정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getSlopActvRprg(String psId) throws Exception {
        Map<String, String> pmParam = new HashMap<String, String>();

        pmParam.put("slop_actv_rprg_id", psId);
        return slopActvRprgDAO.getSlopActvRprg(pmParam);
    }

    /**
     * 사업원장관리 -> 영업 활동정보를 삭제한다.
     *
     * @param pmParam 영업활동정보
     * @throws Exception
     */
    public int deleteSlopActvRprg(Map<String, Object> pmParam) throws Exception {
        return slopActvRprgDAO.deleteSlopActvRprg(pmParam);
    }

}
