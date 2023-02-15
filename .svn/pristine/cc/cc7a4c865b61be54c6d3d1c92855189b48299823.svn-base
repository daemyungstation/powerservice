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

import powerservice.business.sys.service.PgmService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 프로그램 정보 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID Pgm
 */
@Service
public class PgmServiceImpl extends EgovAbstractServiceImpl implements PgmService {

    @Resource
    public PgmDAO pgmDAO;

    @Resource
    public PgmAthrDAO pgmAthrDAO;

    @Resource
    public PgmIndvFnctDAO pgmIndvFnctDAO;

    @Resource
    public PgmIndvFnctAthrDAO pgmIndvFnctAthrDAO;

    /**
     * 프로그램 정보를 등록한다.
     *
     * @param pmParam 프로그램 정보
     * @throws Exception
     */
    public String insertPgm(Map<String, ?> pmParam) throws Exception {
        // 중복된 순서 자동 증가
        Boolean bDplcFlag =  Boolean.valueOf((String)pmParam.get("dplc_flag")).booleanValue();
        if (bDplcFlag != null && bDplcFlag) {
            pgmDAO.updatePgmSequence(pmParam);
        }

        // 프로그램 정보 저장
        String sKey = "";
        int nResult = pgmDAO.insertPgm(pmParam);
        if (nResult > 0) {
            sKey = (String) pmParam.get("pgm_cd");
        }
        return sKey;
    }

    /**
     * 프로그램 정보를 수정한다.
     *
     * @param pmParam 프로그램 정보
     * @throws Exception
     */
    public int updatePgm(Map<String, ?> pmParam) throws Exception {
        // 중복된 순서 자동 증가
        Boolean bDplcFlag =  Boolean.valueOf((String)pmParam.get("dplc_flag")).booleanValue();

        if (bDplcFlag != null && bDplcFlag) {
            pgmDAO.updatePgmSequence(pmParam);
        }

        // 프로그램 정보 수정
        return pgmDAO.updatePgm(pmParam);
    }

    /**
     * 프로그램 정보를 삭제한다.
     *
     * @param param 프로그램 정보
     * @throws Exception
     */
    public int deletePgm(Map<String, ?> pmParam) throws Exception {
        pgmAthrDAO.deletePgmAthr(pmParam);
        pgmIndvFnctDAO.deletePgmIndvFnct(pmParam);
        pgmIndvFnctAthrDAO.deletePgmIndvFnctAthr(pmParam);
        return pgmDAO.deletePgm(pmParam);
    }

    /**
     * 프로그램 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 프로그램 정보의 검색 건수
     * @throws Exception
     */
    public int getPgmCount(Map<String, ?> pmParam) throws Exception {
        return pgmDAO.getPgmCount(pmParam);
    }

    /**
     * 프로그램 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 프로그램 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getPgmList(Map<String, ?> pmParam) throws Exception {
        return pgmDAO.getPgmList(pmParam);
    }

    /**
     * 프로그램 정보를 검색한다.
     *
     * @param pmParam 프로그램ID
     * @return 프로그램 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getPgm(Map<String, ?> pmParam) throws Exception {
        return pgmDAO.getPgm(pmParam);
    }

    /**
     * 프로그램 트리 목록을 검색한다.
     *
     * @param paramHash 검색 조건
     * @return 프로그램 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getPgmTree(Map<String, ?> pmParam) throws Exception {
        return pgmDAO.getPgmTree(pmParam);
    }

    /**
     * 프로그램 순서 최대값을 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 순서 최대값
     * @throws Exception
     */
    public int getPgmMaxSequence(Map<String, ?> pmParam) throws Exception {
        return pgmDAO.getPgmMaxSequence(pmParam);
    }

    /**
     * 프로그램 순서의 중복 건수를 조회한다
     *
     * @param pmParam 검색 조건
     * @return 프로그램 순서의 중복 건수
     * @throws Exception
     */
    public int getPgmDuplicateCount(Map<String, ?> pmParam) throws Exception {
        return pgmDAO.getPgmDuplicateCount(pmParam);
    }

    /**
     * 카드 청구시 카드결재 팝업 여부
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */

    public int updateCardPop(Map<String, ?> pmParam) throws Exception {
        return pgmDAO.updateCardPop(pmParam);

    }

    /**
     * 카드 청구시 카드결재 팝업 여부
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getCardPoplist(Map<String, ?> pmParam) throws Exception {
        return pgmDAO.getCardPoplist(pmParam);
    }

}
