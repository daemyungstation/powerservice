/*
 * (@)# CoBaVlServiceImpl.java
 *
 * @author 김현경
 * @version 1.0
 * @date 2015/04/15
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

package powerservice.business.mta.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import powerservice.business.mta.service.CoBaVlService;

/**
 * 컬럼 기본값 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 김현경
 * @version 1.0
 * @date 2015/04/15
 * @프로그램ID CoBaVl
 */
@Service
public class CoBaVlServiceImpl extends EgovAbstractServiceImpl implements CoBaVlService {

    @Resource
    public CoBaVlDAO coBaVlDAO;

    /**
     * 컬럼 기본값 관리의 검색 수를 반환한다. (15.04.15)
     *
     * @param pmParamHash 검색 조건
     * @return 컬럼 기본값 관리 정보의 검색 건수
     * @throws Exception
     */
    public int getCoBaVlCount(Map<String, ?> pmParam) throws Exception {
        return coBaVlDAO.getCoBaVlCount(pmParam);
    }

    /**
     * 컬럼 기본값 관리를 검색한다. (15.04.15)
     *
     * @param pmParamHash 검색 조건
     * @return 컬럼 기본값 관리 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getCoBaVlList(Map<String, ?> pmParam) throws Exception {
        return coBaVlDAO.getCoBaVlList(pmParam);
    }

    /**
     * 컬럼 기본값 관리 정보를 검색한다. (15.04.15)
     *
     * @param id 컬럼 기본값
     * @return 컬럼 기본값 관리 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getCoBaVl(String id) throws Exception {
        Map<String, String> pmParam = new HashMap<String, String>();
        pmParam.put("colm_basi_vl_id", id);

        return coBaVlDAO.getCoBaVl(pmParam);
    }

    /**
     * 컬럼 기본값 관리 정보를 등록한다. (15.04.15)
     *
     * @param pmParam 컬럼 기본값 관리 정보
     * @throws Exception
     */
    public String insertCoBaVl(Map<String, ?> pmParam) throws Exception {
        String key = "";

        int nResult = coBaVlDAO.insertCoBaVl(pmParam);

        if(nResult > 0){
            key = (String) pmParam.get("colm_basi_vl_id");
        }

        return key;
    }

    /**
     * 컬럼 기본값 관리 정보를 수정한다. (15.04.15)
     *
     * @param pmParam 컬럼 기본값 관리 정보
     * @throws Exception
     */
    public int updateCoBaVl(Map<String, ?> pmParam) throws Exception {
        return coBaVlDAO.updateCoBaVl(pmParam);
    }
}
