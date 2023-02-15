/*
 * (@)# ConsHstrService.java
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/05/13
 * Copyright (c) 2016 by Inwoo tech inc, KOREA. All Rights Reserved.
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

package powerservice.business.cns.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.cns.service.ConsHstrService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 상담이력을 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/05/13
 * @프로그램ID ConsHstr
 */
@Service
public class ConsHstrServiceImpl extends EgovAbstractServiceImpl implements ConsHstrService {

    @Resource
    public ConsHstrDAO consHstrDAO;

    /**
     * 상담이력 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 상담이력 정보의 검색 건수
     * @throws Exception
     */
    public int getConsHstrCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) consHstrDAO.getConsHstrCount(pmParam);
    }

    /**
     * 상담이력 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상담이력 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getConsHstrList(Map<String, ?> pmParam) throws Exception {
        return consHstrDAO.getConsHstrList(pmParam);
    }

}
