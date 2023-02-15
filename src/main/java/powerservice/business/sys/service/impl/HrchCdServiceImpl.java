/*
 * (@)# HrchCdService.java
 *
 * @author 박의준
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

import powerservice.business.sys.service.HrchCdService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 계층코드 정보 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 박의준
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID HrchCd
 */
@Service
public class HrchCdServiceImpl extends EgovAbstractServiceImpl implements HrchCdService {

    @Resource
    public HrchCdDAO hrchCdDAO;

    /**
     * 계층유형코드 정보를 등록한다.
     *
     * @param pmParam 계층유형코드 정보
     * @throws Exception
     */
    public String insertHrchCd(Map<String, ?> pmParam) throws Exception {
        // 중복된 순서 자동 증가
        String bDplcFlag = (String) pmParam.get("dplc_flag");
        if (bDplcFlag != null && "Y".equals(bDplcFlag)) {
            hrchCdDAO.updateHrchCdSequence(pmParam);
        }
        /*Boolean bDplcFlag = (Boolean) pmParam.get("dplc_flag");
        if (bDplcFlag != null && bDplcFlag) {
            hrchCdDAO.updateHrchCdSequence(pmParam);
        }*/

        // 계층유형코드 정보 저장
        String sKey = "";
        int nResult = hrchCdDAO.insertHrchCd(pmParam);
        if (nResult > 0) {
            sKey = (String) pmParam.get("hrch_cd");
        }
        return sKey;
    }

    /**
     * 계층유형코드 정보를 수정한다.
     *
     * @param pmParam 계층유형코드 정보
     * @throws Exception
     */
    public int updateHrchCd(Map<String, ?> pmParam) throws Exception {
        // 중복된 순서 자동 증가
        String bDplcFlag = (String) pmParam.get("dplc_flag");
        if (bDplcFlag != null && "Y".equals(bDplcFlag)) {
            hrchCdDAO.updateHrchCdSequence(pmParam);
        }
        /*Boolean bDplcFlag = (Boolean) pmParam.get("dplc_flag");
        if (bDplcFlag != null && bDplcFlag) {
            hrchCdDAO.updateHrchCdSequence(pmParam);
        }*/

        // 계층유형코드 정보 수정
        return hrchCdDAO.updateHrchCd(pmParam);
    }

    /**
     * 계층유형코드 정보를 삭제한다.
     *
     * @param pmParam 계층유형코드 정보
     * @throws Exception
     */
    public int deleteHrchCd(Map<String, ?> pmParam) throws Exception {
        return hrchCdDAO.delete("HrchCdMap.deleteHrchCd", pmParam);
    }

    /**
     * 계층유형코드 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 계층유형코드 정보의 검색 건수
     * @throws Exception
     */
    public int getHrchCdCount(Map<String, ?> pmParam) throws Exception {
        return hrchCdDAO.getHrchCdCount(pmParam);
    }

    /**
     * 계층유형코드 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 계층유형코드 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getHrchCdList(Map<String, ?> pmParam) throws Exception {
        return hrchCdDAO.getHrchCdList(pmParam);
    }

    /**
     * 계층유형코드 정보를 검색한다.
     *
     * @param pmParam 계층코드ID
     * @return 계층유형코드 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getHrchCd(Map<String, ?> pmParam) throws Exception {
        return hrchCdDAO.getHrchCd(pmParam);
    }

    /**
     * 계층유형코드 트리 목록을 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 계층유형코드 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getHrchCdTree(Map<String, ?> pmParam) throws Exception {
        return hrchCdDAO.getHrchCdTree(pmParam);
    }

    /**
     * 계층유형코드 순서 최대값을 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 순서 최대값
     * @throws Exception
     */
    public int getHrchCdMaxSequence(Map<String, ?> pmParam) throws Exception {
        return hrchCdDAO.getHrchCdMaxSequence(pmParam);
    }

    /**
     * 계층유형코드 순서의 중복 건수를 조회한다
     *
     * @param pmParam 검색 조건
     * @return 계층유형코드 순서의 중복 건수
     * @throws Exception
     */
    public int getHrchCdDuplicateCount(Map<String, ?> pmParam) throws Exception {
        return hrchCdDAO.getHrchCdDuplicateCount(pmParam);
    }

    /**
     * 계층유형코드 드롭다운 리스트를 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 계층유형코드 드롭다운 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getClscdDropDownList(Map<String, ?> pmParam) throws Exception {
        return hrchCdDAO.getClscdDropDownList(pmParam);
    }

    /**
     * 계층유형코드 목록에 따라 계층코드 정보(목록)를 검색한다.
     *
     * @param sHrchTypList 검색 조건
     * @return 계층 코드 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getHrchCdListByStringArray(String[] sHrchTypList) throws Exception {
        return hrchCdDAO.getHrchCdListByStringArray(sHrchTypList);
    }

}
