/*
 * (@)# SuctServiceImpl.java
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
import powerservice.business.kms.service.SuctService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 퀵링크 세부항목 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 차건호
 * @version 1.0
 * @date 2015/04/23
 * @프로그램ID Suct
 */

@Service
public class SuctServiceImpl extends EgovAbstractServiceImpl implements SuctService {

    @Resource
    public SuctDAO suctDAO;

    /**
     * 퀵링크 세부항목 정보를 등록한다.
     *
     * @param pmParam 퀵링크 세부항목 정보
     * @throws Exception
     */
    public String insertSuct(Map<String, ?> pmParam) throws Exception {
        // 중복된 순서 자동증가
        Boolean bDupFg = (Boolean) pmParam.get("dup_fg");

        if (bDupFg != null && bDupFg) {
            suctDAO.updateSuctOrder(pmParam);
        }

        // 퀵링크 정보 저장
        String sKey = "";

        int nResult = suctDAO.insertSuct(pmParam);

        if (nResult > 0) {
            sKey = (String) pmParam.get("suct_id");
        }
        return sKey;
    }

    /**
     * 퀵링크 세부항목 정보를 수정한다.
     *
     * @param pmParam 퀵링크 세부항목 정보
     * @throws Exception
     */
    public int updateSuct(Map<String, ?> pmParam) throws Exception {
        // 중복된 순서 자동증가
        Boolean bDupFg = (Boolean) pmParam.get("dup_fg");

        if (bDupFg != null && bDupFg) {
            suctDAO.updateSuctOrder(pmParam);
        }

        // 퀵링크 세부항목 정보 수정
        return suctDAO.updateSuct(pmParam);
    }

    /**
     * 퀵링크 세부항목 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 퀵링크 세부항목 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getSuctList(Map<String, ?> pmParam) throws Exception {
        return suctDAO.getSuctList(pmParam);
    }

    /**
     * 퀵링크 세부항목 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 퀵링크 세부항목 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getSuct(Map<String, ?> pmParam) throws Exception {
        return suctDAO.getSuct(pmParam);
    }

    /**
     * 퀵링크 세부항목 순서 최대값을 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 퀵링크 세부항목 순서 최대값
     * @throws Exception
     */
    public int getSuctMaxOrder(Map<String, ?> pmParam) throws Exception {
        return (Integer) suctDAO.getSuctMaxOrder(pmParam);
    }

    /**
     * 사용중인 퀵링크 세부항목 건수를 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 사용중인 퀵링크 세부항목 건수
     * @throws Exception
     */
    public int getSuctUsedCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) suctDAO.getSuctUsedCount(pmParam);
    }

    /**
     * 퀵링크 세부항목 순서의 중복 건수를 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 퀵링크 세부항목 순서의 중복 건수
     * @throws Exception
     */
    public int getSuctDuplicateCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) suctDAO.getSuctDuplicateCount(pmParam);
    }

    /**
     * 퀵링크 세부항목 정보를 삭제한다.
     *
     * @param pmParam 퀵링크 세부항목 정보
     * @throws Exception
     */
    public int deleteSuct(Map<String, ?> pmParam) throws Exception {
        return suctDAO.deleteSuct(pmParam);
    }
}
