/*
 * (@)# ConsService.java
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

package powerservice.business.cns.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import powerservice.business.cns.service.ConsNewTypeService;
import powerservice.business.cns.service.ConsService;
import powerservice.business.dlw.service.impl.DlwConsDAO;
import powerservice.business.dlw.service.impl.DlwConsProdDAO;
import powerservice.business.dlw.service.impl.DlwCustDAO;
import powerservice.business.kms.service.impl.ConsTypDAO;
import powerservice.common.util.CommonUtils;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 상담 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID Cons
 */
@Service
public class ConsNewTypeServiceImpl extends EgovAbstractServiceImpl implements ConsNewTypeService {

    @Resource
    public ConsNewTypeDAO consNewTypeDAO;

    @Resource
    public ConsHstrDAO consHstrDAO;

    @Resource
    public DlwConsDAO dlwConsDAO;

    @Resource
    public DlwConsProdDAO dlwConsProdDAO;

    @Resource
    public ConsTypDAO consTypDAO;

    @Resource
    public DlwCustDAO dlwCustDAO;

    /**
     * 상담 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 상담 정보의 검색 건수
     * @throws Exception
     */
    public int getConsCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) consNewTypeDAO.getConsCount(pmParam);
    }

    /**
     * 상담 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상담 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getConsList(Map<String, ?> pmParam) throws Exception {
        return consNewTypeDAO.getConsList(pmParam);
    }
    
}
