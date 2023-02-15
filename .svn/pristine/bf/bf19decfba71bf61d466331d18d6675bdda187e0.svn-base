/*
 * (@)# PgmService.java
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

import powerservice.business.sys.service.PgmIndvFnctService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 프로그램 개별기능 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정훈
 * @version 1.0
 * @date 2016/04/06
 * @프로그램ID PgmIndvFnct
 */
@Service
public class PgmIndvFnctServiceImpl extends EgovAbstractServiceImpl implements PgmIndvFnctService {

    @Resource
    public PgmIndvFnctDAO pgmIndvFnctDAO;

    @Resource
    public PgmAthrDAO pgmAthrDAO;

    @Resource
    public PgmIndvFnctAthrDAO pgmIndvFnctAthrDAO;

    /**
     * 프로그램 개별기능 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 프로그램 정보의 검색 건수
     * @throws Exception
     */
    public int getPgmIndvFnctCount(Map<String, ?> pmParam) throws Exception {
        return pgmIndvFnctDAO.getPgmIndvFnctCount(pmParam);
    }

    /**
     * 프로그램 개별기능을 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 프로그램 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getPgmIndvFnctList(Map<String, ?> pmParam) throws Exception {
        return pgmIndvFnctDAO.getPgmIndvFnctList(pmParam);
    }

    /**
     * 프로그램 개별기능을 등록한다.
     *
     * @param pmParam 프로그램 정보
     * @throws Exception
     */
    public String insertPgmIndvFnct(Map<String, ?> pmParam) throws Exception {

        // 프로그램 정보 저장
        String sKey = "";
        int nResult = pgmIndvFnctDAO.insertPgmIndvFnct(pmParam);
        sKey = (String) pmParam.get("pgm_indv_fnct_id");
        return sKey;
    }

    /**
     * 프로그램 개별기능을 수정한다.
     *
     * @param pmParam 프로그램 정보
     * @throws Exception
     */
    public int updatePgmIndvFnct(Map<String, ?> pmParam) throws Exception {
        // 프로그램 정보 수정
        return pgmIndvFnctDAO.updatePgmIndvFnct(pmParam);
    }

    /**
     * 프로그램 개별기능을 삭제한다.
     *
     * @param param 프로그램 정보
     * @throws Exception
     */
    public int deletePgmIndvFnct(Map<String, ?> pmParam) throws Exception {
        pgmIndvFnctAthrDAO.deletePgmIndvFnctAthr(pmParam);
        return pgmIndvFnctDAO.deletePgmIndvFnct(pmParam);
    }

}
