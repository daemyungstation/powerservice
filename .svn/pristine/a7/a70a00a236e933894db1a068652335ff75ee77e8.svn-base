/*
 * (@)# DfctDataServiceImpl.java
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

package powerservice.business.cam.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.cam.service.DfctDataService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 불량데이터를 관리한다.
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 차건호
 * @version 1.0
 * @date 2015/04/29
 * @프로그램ID DfctData
 */

@Service
public class DfctDataServiceImpl extends EgovAbstractServiceImpl implements DfctDataService {

    @Resource
    public DfctDataDAO dfctDataDAO;

    /**
     * 불량데이터 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 대상리스트 정보의 검색 수
     * @throws Exception
     */
    public int getDfctDataCount(Map<String, ?> pmParam) throws Exception {
        return (Integer)dfctDataDAO.getDfctDataCount(pmParam);
    }

    /**
     * 불량데이터 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 대상리스트 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getDfctDataList(Map<String, ?> pmParam) throws Exception {
        return dfctDataDAO.getDfctDataList(pmParam);
    }

    /**
     * 불량데이터 정보를 등록한다.
     *
     * @param pmParam 대상리스트 정보
     * @throws Exception
     */
    public int insertDfctDataByExcel(Map<String, Object> pmParam) throws Exception {
        return dfctDataDAO.insertDfctDataByExcel(pmParam);
    }

    /**
     * 불량데이터 정보를 삭제한다.
     *
     * @param pmParam 대상고객리스트 아이디를 기준으로 삭제
     * @throws Exception
     */
    public int deleteDfctDataByTrgtList(Map<String, ?> pmParam) throws Exception {
        return dfctDataDAO.deleteDfctDataByTrgtList(pmParam);
    }

}
