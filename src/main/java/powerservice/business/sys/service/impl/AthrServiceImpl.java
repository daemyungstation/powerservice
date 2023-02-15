/*
 * (@)# AthrService.java
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

package powerservice.business.sys.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.sys.service.AthrService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 권한 정보 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID Athr
 */
@Service
public class AthrServiceImpl extends EgovAbstractServiceImpl implements AthrService {

    @Resource
    public AthrDAO athrDAO;

    @Resource
    public PgmAthrDAO pgmAthrDAO;

    /**
     * 권한 정보를 등록한다.
     *
     * @param pmParam 권한 정보
     * @throws Exception
     */
    public String insertAthr(Map<String, ?> pmParam) throws Exception {
        // 중복된 순서 자동 증가
        Boolean bDplcFlag =  Boolean.valueOf((String)pmParam.get("dplc_flag")).booleanValue();
        if (bDplcFlag != null && bDplcFlag) {
            athrDAO.updateAthrSequence(pmParam);
        }

        // 권한 정보 저장
        String sKey = "";
        int nResult = athrDAO.insertAthr(pmParam);
        if (nResult > 0) {
            sKey = (String) pmParam.get("athr_cd");
        }
        return sKey;
    }

    /**
     * 권한 정보를 수정한다.
     *
     * @param pmParam 권한 정보
     * @throws Exception
     */
    public int updateAthr(Map<String, ?> pmParam) throws Exception {
        // 중복된 순서 자동 증가
        Boolean bDplcFlag =  Boolean.valueOf((String)pmParam.get("dplc_flag")).booleanValue();
        if (bDplcFlag != null && bDplcFlag) {
            athrDAO.updateAthrSequence(pmParam);
        }

        // 권한 정보 저장
        return athrDAO.updateAthr(pmParam);
    }

    /**
     * 권한 정보를 삭제한다.
     *
     * @param pmParam 권한 정보
     * @throws Exception
     */
    public int deleteAthr(Map<String, ?> pmParam) throws Exception {
        pgmAthrDAO.deletePgmAthr(pmParam);
        return athrDAO.deleteAthr(pmParam);
    }

    /**
     * 권한 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 권한 정보의 검색 건수
     * @throws Exception
     */
    public int getAthrCount(Map<String, ?> pmParam) throws Exception {
        return athrDAO.getAthrCount(pmParam);
    }

    /**
     * 권한 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 권한 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getAthrList(Map<String, ?> pmParam) throws Exception {
        return athrDAO.getAthrList(pmParam);
    }

    /**
     * 권한 정보를 검색한다.
     *
     * @param pmParam 권한CD
     * @return 권한 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getAthr(Map<String, ?> pmParam) throws Exception {
        return athrDAO.getAthr(pmParam);
    }

    /**
     * 권한 순서 최대값을 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 권한 순서 최대값
     * @throws Exception
     */
    public int getAthrMaxSequence(Map<String, ?> pmParam) throws Exception {
        return athrDAO.getAthrMaxSequence(pmParam);
    }

    /**
     * 권한 순서의 중복 건수를 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 권한 순서의 중복 건수
     * @throws Exception
     */
    public int getAthrDuplicateCount(Map<String, ?> pmParam) throws Exception {
        return athrDAO.getAthrDuplicateCount(pmParam);
    }

}
