/*
 * (@)# VctnAplcDtlService.java
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

package powerservice.business.evl.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.evl.service.EvstDtlService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 평가지를 관리한다.
 * Copyright (c) 2014 Company Inwoo Tech Inc.
 *
 * @author 최현식
 * @version 1.0
 * @date 2014/04/01
 * @프로그램ID QaevalSheet
 */
@Service
public class EvstDtlServiceImpl extends EgovAbstractServiceImpl implements EvstDtlService {

    @Resource
    public EvstDtlDAO evstDtlDAO;

    /**
     * 평가지 정보의 검색 수를 반환한다.
     *
     * @param param 검색 조건
     * @return 평가지 정보의 검색 수
     * @throws Exception
     */
    public int getEvstDtlCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) evstDtlDAO.getEvstDtlCount(pmParam);
    }

    /**
     * 평가지 정보(목록)를 검색한다.
     *
     * @param param 검색 조건
     * @return 평가지 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getEvstDtlList(Map<String, ?> pmParam) throws Exception {
        return evstDtlDAO.getEvstDtlList(pmParam);
    }

    /**
     * 평가지 정보를 등록한다.
     *
     * @param param 평가지 정보
     * @throws Exception
     */
    public String insertEvstDtl(Map<String, Object> pmParam) throws Exception {
        String sKey = "";

        int nResult = evstDtlDAO.insertEvstDtl(pmParam);

        if (nResult > 0) {
            sKey = (String) pmParam.get("evst_id");
        }
        return sKey;
    }

    /**
     * 평가지 정보(1건)를 검색한다.
     *
     * @param paramHash 검색 조건
     * @return 평가지 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getEvstDtl(String psId) throws Exception {
        Map<String, String> mParam = new HashMap<String, String>();
        mParam.put("evst_id", psId);

        return evstDtlDAO.selectOne("EvstDtlMap.getEvstDtlList", mParam);
    }

    /**
     * 평가지 정보를 수정한다.
     *
     * @param param 평가지 정보
     * @throws Exception
     */
    public int updateEvstDtl(Map<String, Object> pmParam) throws Exception {
        return evstDtlDAO.updateEvstDtl(pmParam);
    }

    /**
     * 평가지 정보를 삭제한다.
     *
     * @param param 평가지 정보
     * @throws Exception
     */
    public int deleteEvstDtl(Map<String, Object> pmParam) throws Exception {
        return evstDtlDAO.deleteEvstDtl(pmParam);
    }

    /**
     * 평가지 정보를 복사한다.
     *
     * @param param 평가지 정보
     * @throws Exception
     */
    public String insertCopyEvstDtl(Map<String, ?> pmParam) throws Exception {
        String sKey = "";

        int nResult = evstDtlDAO.insertCopyEvstDtl(pmParam);
        if (nResult > 0) {
            sKey = (String) pmParam.get("new_evst_id");
        }

        return sKey;
    }
}
