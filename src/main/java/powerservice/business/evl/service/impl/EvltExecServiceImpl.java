/*
 * (@)# EvltItemBankServiceImpl.java
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

package powerservice.business.evl.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.evl.service.EvltExecService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 휴가요청 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID EvltItemBank
 */
@Service
public class EvltExecServiceImpl extends EgovAbstractServiceImpl implements EvltExecService {

    @Resource
    public EvltExecDAO evltExecDAO;

    /**
     * 평가수행 정보의 검색 수를 반환한다.
     *
     * @param param 검색 조건
     * @return 평가수행 정보의 검색 수
     * @throws Exception
     */
    public int getEvltExecCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) evltExecDAO.getEvltExecCount(pmParam);
    }

    /**
     * 평가수행 정보(목록)를 검색한다.
     *
     * @param param 검색 조건
     * @return 평가수행 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getEvltExecList(Map<String, ?> pmParam) throws Exception {
        return evltExecDAO.getEvltExecList(pmParam);
    }

    /**
     * 평가수행-상담정보 정보(1건)를 검색한다.
     *
     * @param param 검색 조건
     * @return 상담정보
     * @throws Exception
     */
    public Map<String, Object> getEvltExec(Map<String, ?> pmParam) throws Exception {
        return evltExecDAO.getEvltExec(pmParam);
    }

}
