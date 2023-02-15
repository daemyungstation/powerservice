/*
 * (@)# TblChkServiceImpl.java
 *
 * @author 김현경
 * @version 1.0
 * @date 2015/07/22
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
import powerservice.business.mta.service.TblChkService;

/**
 * 컬럼 기본값 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 김현경
 * @version 1.0
 * @date 2015/07/22
 * @프로그램ID TblChk
 */
@Service
public class TblChkServiceImpl extends EgovAbstractServiceImpl implements TblChkService {

    @Resource
    public TblChkDAO tblChkDAO;

    /**
     * 테이블 체크의 검색 수를 반환한다. (15.07.22)
     *
     * @param pmParamHash 검색 조건
     * @return 테이블 체크 정보의 검색 건수
     * @throws Exception
     */
    public int getTblChkCount(Map<String, ?> pmParam) throws Exception {
        return tblChkDAO.getTblChkCount(pmParam);
    }

    /**
     * 테이블 체크를 검색한다. (15.07.22)
     *
     * @param pmParamHash 검색 조건
     * @return 테이블 체크 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getTblChkList(Map<String, ?> pmParam) throws Exception {
        return tblChkDAO.getTblChkList(pmParam);
    }

    /**
     * 테이블 체크 상세 검색 수를 반환한다. (15.08.05)
     *
     * @param pmParamHash 검색 조건
     * @return 테이블 체크 상세 정보의 검색 건수
     * @throws Exception
     */
    public int getTblDtailCount(Map<String, ?> pmParam) throws Exception {
        return tblChkDAO.getTblDtailCount(pmParam);
    }

    /**
     * 테이블 체크 상세를 검색한다. (15.08.05)
     *
     * @param pmParamHash 검색 조건
     * @return 테이블 체크 상세 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getTblDetailList(Map<String, ?> pmParam) throws Exception {
        return tblChkDAO.getTblDetailList(pmParam);
    }

    /**
     * 컬럼 체크의 검색 수를 반환한다. (15.07.22)
     *
     * @param pmParamHash 검색 조건
     * @return 컬럼 체크 정보의 검색 건수
     * @throws Exception
     */
    public int getColmChkCount(Map<String, ?> pmParam) throws Exception {
        return tblChkDAO.getColmChkCount(pmParam);
    }

    /**
     * 컬럼 체크를 검색한다. (15.07.22)
     *
     * @param pmParamHash 검색 조건
     * @return 컬럼 체크 관리 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getColmChkList(Map<String, ?> pmParam) throws Exception {
        return tblChkDAO.getColmChkList(pmParam);
    }

    /**
     * 컬럼 상세 체크의 검색 수를 반환한다. (15.07.22)
     *
     * @param pmParamHash 검색 조건
     * @return 컬럼 상세 체크 정보의 검색 건수
     * @throws Exception
     */
    public int getColmDtailCount(Map<String, ?> pmParam) throws Exception {
        return tblChkDAO.getColmDtailCount(pmParam);
    }

    /**
     * 컬럼 상세 체크를 검색한다. (15.07.22)
     *
     * @param pmParamHash 검색 조건
     * @return 컬럼 상세 체크 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getColmDetailList(Map<String, ?> pmParam) throws Exception {
        return tblChkDAO.getColmDetailList(pmParam);
    }

    /**
    * 도메인 체크의 검색 수를 반환한다. (15.08.06)
    *
    * @param pmParamHash 검색 조건
    * @return 도메인 체크 정보의 검색 건수
    * @throws Exception
    */
   public int getDonChkCount(Map<String, ?> pmParam) throws Exception {
       return tblChkDAO.getDonChkCount(pmParam);
   }

   /**
    * 도메인 체크를 검색한다. (15.08.06)
    *
    * @param pmParamHash 검색 조건
    * @return 도메인 체크 목록
    * @throws Exception
    */
   public List<Map<String, Object>> getDonChkList(Map<String, ?> pmParam) throws Exception {
       return tblChkDAO.getDonChkList(pmParam);
   }
}
