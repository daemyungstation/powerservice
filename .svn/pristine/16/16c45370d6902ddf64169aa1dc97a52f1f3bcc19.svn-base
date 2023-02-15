/*
 * (@)# WrdDicServiceImpl.java
 *
 * @author 김현경
 * @version 1.0
 * @date 2015/03/26
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

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

import powerservice.business.mta.service.DonInfService;

/**
 * 도메인 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 김현경
 * @version 1.0
 * @date 2015/04/06
 * @프로그램ID DonInf
 */
@Service
public class DonInfServiceImpl extends EgovAbstractServiceImpl implements DonInfService {

    @Resource
    public DonInfDAO donInfDAO;

    /**
     * 도메인 관리 트리 리스트를 검색한다. (15.04.06)
     *
     * @param pmParam 검색 조건
     * @return 도메인 관리 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getDonInfTreeList(Map<String, ?> pmParam) throws Exception {
        return donInfDAO.getDonInfTreeList(pmParam);
    }

    /**
     * 도메인 관리 정보의 검색 수를 반환한다. (15.04.06)
     *
     * @param pmParam 검색 조건
     * @return 도메인 관리 정보의 검색 수
     * @throws Exception
     */
    public int getDonInfCount(Map<String, ?> pmParam) throws Exception {
        return donInfDAO.getDonInfCount(pmParam);
    }

    /**
     * 도메인 관리 정보를 검색한다. (15.04.06)
     *
     * @param pmParam 검색 조건
     * @return 도메인 관리 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getDonInfList(Map<String, ?> pmParam) throws Exception {
        return donInfDAO.getDonInfList(pmParam);
    }

    /**
     * 도메인 관리 정보를 검색한다. (15.04.07)
     *
     * @param 도메인 관리 코드
     * @return 도메인 관리 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getDonInf(Map<String, ?> pmParam) throws Exception {
        return donInfDAO.getDonInf(pmParam);
    }

    /**
     * 도메인 관리 정보를 등록 및 수정한다. (15.04.07)
     *
     * @param pmParam 상담유형 정보
     * @throws Exception
     */
    public void mergeDonInf(Map<String, ?> pmParam) throws Exception {
        // 중복된 순서 자동증가
        Boolean duplicatefg = (Boolean) pmParam.get("duplicatefg");

        if (duplicatefg != null && duplicatefg) {
            donInfDAO.updateDonInf(pmParam);
        }

        donInfDAO.insertDonInf(pmParam);
    }


    /**
     * 도메인 관리 순서의 중복 건수를 조회한다. (15.04.07)
     *
     * @param pmParamHash 검색 조건
     * @return 도메인 관리 순서의 중복 건수
     * @throws Exception
     */
    public int getDonInfDuplicateCount(Map<String, ?> pmParam) throws Exception {
        return donInfDAO.getDonInfDuplicateCount(pmParam);
    }

    /**
     * 도메인 관리 정보를 삭제한다. (15.04.07)
     *
     * @param pmParam 도메인 관리 정보
     * @throws Exception
     */
    public int deleteDonInf(Map<String, ?> pmParam) throws Exception {
        return donInfDAO.deleteDonInf(pmParam);
    }

    /**
     * 칼럼상세 정보의 검색 수를 반환한다. (15.05.14)
     *
     * @param pmParam 검색 조건
     * @return 칼럼상세 정보의 검색 수
     * @throws Exception
     */
    public int getRefercolmCount(Map<String, ?> pmParam) throws Exception {
        return donInfDAO.getRefercolmCount(pmParam);
    }

    /**
     * 도메인 관리 정보 순서 최대값을 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 순서 최대값
     * @throws Exception
     */
    public int getDonInfMaxSequence(Map<String, ?> pmParam) throws Exception {
        return (Integer) donInfDAO.getDonInfMaxSequence(pmParam);
    }
}
