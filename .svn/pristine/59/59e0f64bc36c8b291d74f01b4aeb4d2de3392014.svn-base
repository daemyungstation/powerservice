/*
 * (@)# FaxSndgHstrServiceImpl.java
 *
 * @author 김은지
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

package powerservice.business.chn.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.chn.service.FaxSndgHstrService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * FAX 전송 이력 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 김은지
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID FaxSndgHstr
 */
@Service
public class FaxSndgHstrServiceImpl extends EgovAbstractServiceImpl implements FaxSndgHstrService {

    @Resource
    public FaxSndgHstrDAO faxSndgHstrDAO;

    /**
     * FAX 전송 이력 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return FAX 전송 이력 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getFaxSndgHstrList(Map<String, ?> pmParam) throws Exception {
        return faxSndgHstrDAO.getFaxSndgHstrList(pmParam);
    }

}
