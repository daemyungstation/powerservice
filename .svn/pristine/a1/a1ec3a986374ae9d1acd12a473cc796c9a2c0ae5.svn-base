/*
 * (@)# ExccWrkrService.java
 *
 * @author 배윤정
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
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.usr.service.ExccWrkrService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 우수사원 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 배윤정
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID ExccWrkr
 */
@Service
public class ExccWrkrServiceImpl extends EgovAbstractServiceImpl implements ExccWrkrService {

    @Resource
    public ExccWrkrDAO exccWrkrDAO;

    /**
     * 우수사원 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 우수사원 정보의 검색 수
     * @throws Exception
     */
    public int getExccWrkrCount(Map<String, ?> pmParam) throws Exception {
        return exccWrkrDAO.getExccWrkrCount(pmParam);
    }

    /**
     * 우수사원 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 우수사원 정보 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getExccWrkrList(Map<String, ?> pmParam) throws Exception {
        return exccWrkrDAO.getExccWrkrList(pmParam);
    }

    /**
     * 우수사원 정보를 검색한다.
     *
     * @param param 검색 조건
     * @return 우수사원 정보 리스트
     * @throws Exception
     */
    public Map<String, Object> getExccWrkr(String psId) throws Exception {
        Map<String, String> mParam = new HashMap<String, String>();
        mParam.put("excc_wrkr_id", psId);

        return exccWrkrDAO.getExccWrkr(mParam);
    }

    /**
     * 우수사원 정보를 등록한다.
     *
     * @param pmParam 우수사원 정보
     * @throws Exception
     */
    public String insertExccWrkr(Map<String, ?> pmParam) throws Exception {
        // 중복된 순서 자동증가
        String bDupYn = (String) pmParam.get("dup_yn");
        if (bDupYn != null && bDupYn.equals("Y")) {
            exccWrkrDAO.updateExccWrkrOrder(pmParam);
        }
        /*Boolean bDupYn = (Boolean) pmParam.get("dup_yn");
        if (bDupYn != null && bDupYn) {
            exccWrkrDAO.updateExccWrkrOrder(pmParam);
        }*/

        // 우수사원 정보 저장
        String sKey = "";
        int nResult = exccWrkrDAO.insertExccWrkr(pmParam);
        if (nResult > 0) {
            sKey = (String) pmParam.get("excc_wrkr_id");
        }
        return sKey;
    }

    /**
     * 우수사원 정보를 수정한다.
     *
     * @param pmParam 우수사원 정보
     * @throws Exception
     */
    public int updateExccWrkr(Map<String, ?> pmParam) throws Exception {
        // 중복된 순서 자동증가
        String bDupYn = (String) pmParam.get("dup_yn");
        if (bDupYn != null && bDupYn.equals("Y")) {
            exccWrkrDAO.updateExccWrkrOrder(pmParam);
        }
        /*Boolean bDupYn = (Boolean) pmParam.get("dup_yn");
        if (bDupYn != null && bDupYn) {
            exccWrkrDAO.updateExccWrkrOrder(pmParam);
        }*/

        return exccWrkrDAO.updateExccWrkr(pmParam);
    }

    /**
     * 우수사원 정보를 삭제한다.
     *
     * @param pmParam 우수사원 정보
     * @throws Exception
     */
    public int deleteExccWrkr(Map<String, ?> pmParam) throws Exception {
        return exccWrkrDAO.deleteExccWrkr(pmParam);
    }

    /**
     * 선정순서의 중복 건수를 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 권한 순서의 중복 건수
     * @throws Exception
     */
    public int getExccWrkrDuplicateCount(Map<String, ?> pmParam) throws Exception {
        return exccWrkrDAO.getExccWrkrDuplicateCount(pmParam);
    }

}
