/*
 * (@)# BswrDmndDtlHstrService.java
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

import powerservice.business.cns.service.BswrDmndDtlHstrService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 이관이력 정보를 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/05/13
 * @프로그램ID BswrDmndDtlHstr
 */
@Service
public class BswrDmndDtlHstrServiceImpl extends EgovAbstractServiceImpl implements BswrDmndDtlHstrService {

    @Resource
    public BswrDmndDtlHstrDAO bswrDmndDtlHstrDAO;

    /**
     * 이관이력 정보의 검색 건을 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 이관이력 정보의 검색 건수
     * @throws Exception
     */
    public int getBswrDmndDtlHstrCount(Map<String, ?> pmParam) throws Exception {
        return bswrDmndDtlHstrDAO.getBswrDmndDtlHstrCount(pmParam);
    }

    /**
     * 이관이력 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 이관이력목록
     * @throws Exception
     */
    public List<Map<String, Object>> getBswrDmndDtlHstrList(Map<String, ?> pmParam) throws Exception {
        return bswrDmndDtlHstrDAO.getBswrDmndDtlHstrList(pmParam);
    }

}
