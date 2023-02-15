/*
 * (@)# EvltPlan.java
 *
 * @author 최현식
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

package powerservice.business.evl.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.evl.service.EvltTrprInfoService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 평가지를 관리한다.
 * Copyright (c) 2014 Company Inwoo Tech Inc.
 *
 * @author 최현식
 * @version 1.0
 * @date 2014/04/01
 * @프로그램ID EvltTrprInfo
 */
@Service
public class EvltTrprInfoServiceImpl extends EgovAbstractServiceImpl implements EvltTrprInfoService {

    @Resource
    public EvltTrprInfoDAO evltTrprInfoDAO;

    /**
     * 대상자 정보의 검색 수를 반환한다.
     *
     * @param param 검색 조건
     * @return 대상자 정보의 검색 수
     * @throws Exception
     */
    public int getEvltTrprInfoCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) evltTrprInfoDAO.getEvltTrprInfoCount(pmParam);
    }

    /**
     * 대상자 정보(목록)를 검색한다.
     *
     * @param param 검색 조건
     * @return 대상자 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getEvltTrprInfoList(Map<String, ?> pmParam) throws Exception {
        return evltTrprInfoDAO.getEvltTrprInfoList(pmParam);
    }

    /**
     * 대상자 정보를 등록한다.
     *
     * @param param 대상자 정보
     * @throws Exception
     */
    public int insertEvltTrprInfo(Map<String, Object> pmParam) throws Exception {
        return evltTrprInfoDAO.insertEvltTrprInfo(pmParam);
    }

    /**
     * 대상자 정보를 삭제한다.
     *
     * @param param 대상자 정보
     * @throws Exception
     */
    public int deleteEvltTrprInfo(Map<String, Object> pmParam) throws Exception {
        return evltTrprInfoDAO.deleteEvltTrprInfo(pmParam);
    }

    /**
     * 대상자 정보를 삭제한다.
     *
     * @param param 대상자 정보
     * @throws Exception
     */
    public int deleteAllEvltTrprInfo(Map<String, Object> pmParam) throws Exception {
        return evltTrprInfoDAO.deleteAllEvltTrprInfo(pmParam);
    }


}
