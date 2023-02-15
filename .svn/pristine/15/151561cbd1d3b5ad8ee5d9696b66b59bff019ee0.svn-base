/*
 * (@)# RlvtInttServiceImpl.java
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

package powerservice.business.kms.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.kms.service.RlvtInttService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 유관기관 정보관리를 한다.
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 차건호
 * @version 1.0
 * @date 2015/04/16
 * @프로그램ID RlvtIntt
 */

@Service
public class RlvtInttServiceImpl extends EgovAbstractServiceImpl implements RlvtInttService {

    @Resource
    public RlvtInttDAO rlvtInttDAO;

    /**
     * 유관기관 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 유관기관 정보의 검색 수
     * @throws Exception
     */
    public int getRlvtInttCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) rlvtInttDAO.getRlvtInttCount(pmParam);
    }

    /**
     * 유관기관 정보(목록)를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 유관기관  리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getRlvtInttList(Map<String, ?> pmParam) throws Exception {
        return rlvtInttDAO.getRlvtInttList(pmParam);
    }

    /**
     * 유관기관 정보(1건)를 검색한다.
     *
     * @param 유관기관 ID
     * @return 유관기관 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getRlvtIntt(String id) throws Exception {
        Map<String, String> mParam = new HashMap<String, String>();
        mParam.put("rlvt_intt_id", id);

        return rlvtInttDAO.getRlvtIntt(mParam);
    }

    /**
     * 유관기관 순서 최대값을 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 유관기관 순서 최대값
     * @throws Exception
     */
    public int getRlvtInttMaxOrder(Map<String, ?> pmParam) throws Exception {
        return (Integer) rlvtInttDAO.getRlvtInttMaxOrder(pmParam);
    }

    /**
     * 유관기관 정보를 등록한다.
     *
     * @param pmParam 유관기관 정보
     * @throws Exception
     */
    public String insertRlvtIntt(Map<String, ?> pmParam) throws Exception {
        // 중복된 순서 자동증가
        Boolean bDupFg = (Boolean) pmParam.get("dup_fg");

        if (bDupFg != null && bDupFg) {
            rlvtInttDAO.updateRlvtInttOrd(pmParam);
        }

        String sKey = "";

        int nResult = rlvtInttDAO.insertRlvtIntt(pmParam);

        if (nResult > 0) {
            sKey = (String) pmParam.get("rlvt_intt_id");
        }
        return sKey;
    }

    /**
     * 유관기관 정보를 수정한다.(ajax)
     *
     * @param pmParam 유관기관 정보
     * @throws Exception
     */
    public int updateRlvtIntt(Map<String, ?> pmParam) throws Exception {
        // 중복된 순서 자동증가
        Boolean bDupFg = (Boolean) pmParam.get("dup_fg");

        if (bDupFg != null && bDupFg) {
            rlvtInttDAO.updateRlvtInttOrd(pmParam);
        }
        return rlvtInttDAO.updateRlvtIntt(pmParam);
    }

    /**
     * 유관기관 정보를 삭제한다.
     *
     * @param paramHash 유관기관 정보
     * @throws Exception
     */
    public int deleteRlvtIntt(Map<String, Object> pmParam) throws Exception {
        return rlvtInttDAO.deleteRlvtIntt(pmParam);
    }

    /**
     * 유관기관의 중복 건수를 조회한다.
     *
     * @param paramHash 검색 조건
     * @return 권한 순서의 중복 건수
     * @throws Exception
     */
    public int getRlvtInttDupCout(Map<String, ?> pmParam) throws Exception {
        return (Integer) rlvtInttDAO.getRlvtInttDupCout(pmParam);
    }
}
