/*
 * (@)# EvlpInfoServiceImpl.java
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

import powerservice.business.evl.service.EvlpInfoService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 평가자 정보를 관리한다.
 * Copyright (c) 2014 Company Inwoo Tech Inc.
 *
 * @author 최현식
 * @version 1.0
 * @date 2014/04/01
 * @프로그램ID EvlpInfo
 */
@Service
public class EvlpInfoServiceImpl extends EgovAbstractServiceImpl implements EvlpInfoService {

    @Resource
    public EvlpInfoDAO evlpInfoDAO;


    /**
     * 사용자 정보의 검색 수를 반환한다.
     *
     * @param param 검색 조건
     * @return 사용자 정보의 검색 수
     * @throws Exception
     */
    public int getUserCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) evlpInfoDAO.getUserCount(pmParam);
    }

    /**
     * 사용자 정보(목록)를 검색한다.
     *
     * @param param 검색 조건
     * @return 사용자 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getUserList(Map<String, ?> pmParam) throws Exception {
        return evlpInfoDAO.getUserList(pmParam);
    }

    /**
     * 평가자 정보의 검색 수를 반환한다.
     *
     * @param param 검색 조건
     * @return 평가자 정보의 검색 수
     * @throws Exception
     */
    public int getEvlpInfoCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) evlpInfoDAO.selectOne("EvlpInfoMap.getEvlpInfoCount", pmParam);
    }

    /**
     * 평가자 정보(목록)를 검색한다.
     *
     * @param param 검색 조건
     * @return 평가자 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getEvlpInfoList(Map<String, ?> pmParam) throws Exception {
        return evlpInfoDAO.getEvlpInfoList(pmParam);
    }

    /**
     * 평가자 정보를 등록한다.
     *
     * @param param 평가자 정보
     * @throws Exception
     */
    public int insertEvlpInfo(Map<String, Object> pmParam) throws Exception {
        return evlpInfoDAO.insertEvlpInfo(pmParam);
    }

    /**
     * 평가자 정보를 수정한다.
     *
     * @param param 평가자 정보
     * @throws Exception
     */
    public int updateEvlpInfo(Map<String, Object> pmParam) throws Exception {
        int nEvltTrprCnt = evlpInfoDAO.getEvltTrprInfoCount(pmParam);
        pmParam.put("evlt_trpr_cnt", nEvltTrprCnt);
        return evlpInfoDAO.updateEvlpInfo(pmParam);
    }

    /**
     * 평가자 정보를 삭제한다.
     *
     * @param param 평가자 정보
     * @throws Exception
     */
    public int deleteEvlpInfo(Map<String, Object> pmParam) throws Exception {
        return evlpInfoDAO.deleteEvlpInfo(pmParam);
    }
}
