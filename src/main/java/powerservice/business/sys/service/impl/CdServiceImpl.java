/*
 * (@)# CdService.java
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import powerservice.business.sys.service.CdService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 코드 정보 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID Cd
 */
@Service
public class CdServiceImpl extends EgovAbstractServiceImpl implements CdService {

    @Resource
    public CdDAO cdDAO;

    /**
     * 대명 기초 코드 정보를 등록한다.
     *
     * @param pmParam 코드 정보
     * @throws Exception
     */
    public String insertComCd(Map<String, ?> pmParam) throws Exception {

        // 중복된 순서 자동 증가
        String bDplcFlag = (String) pmParam.get("dplc_flag");
        if (bDplcFlag != null && "Y".equals(bDplcFlag)) {
            cdDAO.updateComCdSequence(pmParam);
        }

        // 코드 정보 저장
        String sKey = "";
        int sResult = cdDAO.insertComCd(pmParam);
        if (sResult > 0) {
            sKey = (String) pmParam.get("cd");
        }
        return sKey;
    }

    /**
     * 코드 정보를 등록한다.
     *
     * @param pmParam 코드 정보
     * @throws Exception
     */
    public String insertCd(Map<String, ?> pmParam) throws Exception {
        // 중복된 순서 자동 증가
        String bDplcFlag = (String) pmParam.get("dplc_flag");
        if (bDplcFlag != null && "Y".equals(bDplcFlag)) {
            cdDAO.updateCdSequence(pmParam);
        }

        // 중복된 순서 자동 증가
        /*Boolean bDplcFlag = (Boolean) pmParam.get("dplc_flag");
        if (bDplcFlag != null && bDplcFlag) {
            cdDAO.updateCdSequence(pmParam);
        }*/

        // 코드 정보 저장
        String sKey = "";
        int sResult = cdDAO.insertCd(pmParam);
        if (sResult > 0) {
            sKey = (String) pmParam.get("cd");
        }
        return sKey;
    }

    /**
     * 대명 기초 코드 정보를 수정한다.
     *
     * @param pmParam 코드 정보
     * @throws Exception
     */
    public int updateComCd(Map<String, ?> pmParam) throws Exception {
        // 중복된 순서 자동 증가
        String bDplcFlag = (String) pmParam.get("dplc_flag");
        if (bDplcFlag != null && "Y".equals(bDplcFlag)) {
            cdDAO.updateComCdSequence(pmParam);
        }

        // 중복된 순서 자동 증가
        /*Boolean bDplcFlag = (Boolean) pmParam.get("dplc_flag");
        if (bDplcFlag != null && bDplcFlag) {
            cdDAO.updateCdSequence(pmParam);
        }*/

        return cdDAO.updateComCd(pmParam);
    }

    /**
     * 코드 정보를 수정한다.
     *
     * @param pmParam 코드 정보
     * @throws Exception
     */
    public int updateCd(Map<String, ?> pmParam) throws Exception {
        // 중복된 순서 자동 증가
        String bDplcFlag = (String) pmParam.get("dplc_flag");
        if (bDplcFlag != null && "Y".equals(bDplcFlag)) {
            cdDAO.updateCdSequence(pmParam);
        }

        // 중복된 순서 자동 증가
        /*Boolean bDplcFlag = (Boolean) pmParam.get("dplc_flag");
        if (bDplcFlag != null && bDplcFlag) {
            cdDAO.updateCdSequence(pmParam);
        }*/

        return cdDAO.updateCd(pmParam);
    }

    /**
     * 코드 정보2의 검색 수를 반환한다.(대명 기초 코드)
     *
     * @param pmParam 검색 조건
     * @return 코드 정보의 검색 수
     * @throws Exception
     */
    public int getComCdCount(Map<String, ?> pmParam) throws Exception {
        return cdDAO.getCdCount(pmParam);
    }


    /**
     * 코드 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 코드 정보의 검색 수
     * @throws Exception
     */
    public int getCdCount(Map<String, ?> pmParam) throws Exception {
        return cdDAO.getCdCount(pmParam);
    }

    /**
     * 코드 정보(목록)를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 코드 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getCdList(Map<String, ?> pmParam) throws Exception {
        String sType_Cd = StringUtils.defaultString((String) pmParam.get("type_cd"));

        List<Map<String, Object>> getCdSet = new ArrayList<Map<String,Object>>();

        System.out.println("==>" + sType_Cd);

        if(sType_Cd.equals("counsel")){//상담코드
        	getCdSet = cdDAO.getCdList(pmParam);
        }
        else{
        	getCdSet = cdDAO.getCdMemList(pmParam);
        }
        return  getCdSet;

    }

    /**
     * 코드 정보(1건)를 검색한다.
     *
     * @param param 검색 조건
     * @return 코드 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getCd(Map<String, ?> pmParam) throws Exception {
        return cdDAO.getCd(pmParam);
    }

    /**
     * 코드셋 목록에 따라 코드 정보(목록)를 검색한다.
     *
     * @param sCdSetList 검색 조건
     * @return 코드 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getCdList(String[] sCdSetList) throws Exception {
        return cdDAO.getCdList(sCdSetList);
    }

    /**
     * 코드 순서 최대값을 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 순서 최대값
     * @throws Exception
     */
    public int getCdMaxSequence(Map<String, ?> pmParam) throws Exception {
        return (Integer) cdDAO.getCdMaxSequence(pmParam);
    }

    /**
     * 코드 순서의 중복 건수를 조회한다
     *
     * @param pmParam 검색 조건
     * @return 코드 순서의 중복 건수
     * @throws Exception
     */
    public int getCdDuplicateCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) cdDAO.getCdDuplicateCount(pmParam);
    }

}
