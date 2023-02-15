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

import powerservice.business.biz.service.SlopIssDtlService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 *사업원장관리->영업이슈 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정훈
 * @version 1.0
 * @date 2015/08/07
 * @프로그램ID <PS-UI-DS10>
 */
@Service
public class SlopIssDtlServiceImpl extends EgovAbstractServiceImpl implements SlopIssDtlService {

    @Resource
    public SlopIssDtlDAO slopIssDtlDAO;

    /**
     * 사업원장관리->영업이슈 를 등록한다.
     *
     * @param pmParam 영업이슈 정보
     * @throws Exception
     */
    public String insertSlopIssDtl(Map<String, ?> pmParam) throws Exception {
        String sKey = "";

        int nResult = slopIssDtlDAO.insertSlopIssDtl(pmParam);
        if (nResult > 0) {
            sKey = (String) pmParam.get("slop_iss_id");
        }
        return sKey;
    }


    /**
     * 사업원장관리->영업이슈 를 수정한다.
     *
     * @param pmParam 영업이슈 정보
     * @throws Exception
     */
    public int updateSlopIssDtl(Map<String, ?> pmParam) throws Exception {
    	return  slopIssDtlDAO.updateSlopIssDtl(pmParam);
    }


    /**
     * 사업원장관리->영업이슈 의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 영업 이슈의 검색 건수
     * @throws Exception
     */
    public int getSlopIssDtlCount(Map<String, ?> pmParam) throws Exception {
        return slopIssDtlDAO.getSlopIssDtlCount(pmParam);
    }


    /**
     * 사업원장관리->영업이슈를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 영업이슈 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getSlopIssDtlList(Map<String, ?> pmParam) throws Exception {
    	return slopIssDtlDAO.getSlopIssDtlList(pmParam);
    }


    /**
     * 사업원장관리->영업이슈를 검색한다.
     *
     * @param psId 영업이슈ID
     * @return 영업이슈정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getSlopIssDtl(String psId) throws Exception {
        Map<String, String> pmParam = new HashMap<String, String>();

        pmParam.put("slop_iss_id", psId);
        return slopIssDtlDAO.getSlopIssDtl(pmParam);
    }

    /**
     * 사업원장관리 -> 영업 이슈를 삭제한다.
     *
     * @param pmParam 영업이슈정보
     * @throws Exception
     */
    public int deleteSlopIssDtl(Map<String, Object> pmParam) throws Exception {
        return slopIssDtlDAO.deleteSlopIssDtl(pmParam);
    }
}
