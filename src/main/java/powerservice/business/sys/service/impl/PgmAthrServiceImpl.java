/*
 * (@)# PgmAthrService.java
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

import powerservice.business.sys.service.PgmAthrService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 프로그램 권한 정보 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID PgmAthr
 */
@Service
public class PgmAthrServiceImpl extends EgovAbstractServiceImpl implements PgmAthrService {

    @Resource
    public PgmAthrDAO pgmAthrDAO;

    @Resource
    public PgmIndvFnctDAO pgmIndvFnctDAO;

    @Resource
    public PgmIndvFnctAthrDAO pgmIndvFnctAthrDAO;

    /**
     * 프로그램 권한 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 프로그램 권한 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getPgmAthrList(Map<String, ?> pmParam) throws Exception {
        return pgmAthrDAO.getPgmAthrList(pmParam);
    }

    /**
     * 프로그램 권한 추가 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 프로그램 권한 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getPgmAthrAddList(Map<String, ?> pmParam) throws Exception {
        return pgmAthrDAO.getPgmAthrAddList(pmParam);
    }

    /**
     * 프로그램 권한 정보를 등록한다
     *
     * @param pmParam 프로그램 권한 정보
     * @throws Exception
     */
    public int insertPgmAthr(Map<String, Object> pmParam) throws Exception {
        int nResult = 0;

        String[] sPgmCdList = (String[]) pmParam.get("sPgmCdList");

        if (sPgmCdList != null && sPgmCdList.length > 0) {
            for (int i = 0; i < sPgmCdList.length; i++) {
                pmParam.put("pgm_cd", sPgmCdList[i]);

                nResult += pgmAthrDAO.insertPgmAthr(pmParam);
            }
        }

        return nResult;
    }

    /**
     * 프로그램 권한 정보를 수정한다
     *
     * @param pmParam 프로그램 권한 정보
     * @throws Exception
     */
    public int updatePgmAthr(Map<String, Object> pmParam) throws Exception {
        return  pgmAthrDAO.updatePgmAthr(pmParam);
    }

    /**
     * 프로그램 권한 정보를 삭제한다.
     *
     * @param pmParam 프로그램 권한 정보
     * @throws Exception
     */
    public int deletePgmAthr(Map<String, ?> pmParam) throws Exception {
        pgmIndvFnctAthrDAO.deletePgmIndvFnctAthr(pmParam);
        return pgmAthrDAO.deletePgmAthr(pmParam);
    }

}
