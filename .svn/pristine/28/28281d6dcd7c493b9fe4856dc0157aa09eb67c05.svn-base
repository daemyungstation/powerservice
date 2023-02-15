/*
 * (@)# DlwCertfServiceImpl.java
 *
 * @author 석민
 * @version 1.0
 * @date 2016/03/14
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

package powerservice.business.dlw.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.dlw.service.DlwCertfService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 대명라이프웨이 증서 정보를 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 석민
 * @version 1.0
 * @date 2016/03/14
 * @프로그램ID DlwCertfServiceImpl
 */
@Service
public class DlwCertfServiceImpl extends EgovAbstractServiceImpl implements DlwCertfService {

    @Resource
    public DlwCertfDAO DlwCertfDAO;

    /**
     * 증서 출력 정보 수
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getCertfCount(Map<String, ?> pmParam) throws Exception {
        return DlwCertfDAO.getCertfCount(pmParam);
    }

    /**
     * 증서 출력 정보
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getCertfList(Map<String, ?> pmParam) throws Exception {
        return DlwCertfDAO.getCertfList(pmParam);
    }


    public List<Map<String, Object>> getCertfprodList(Map<String, ?> pmParam) throws Exception {
        return DlwCertfDAO.getCertfprodList(pmParam);
    }
    /**
     * 증서 발급 여부 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getCertPrintChk(Map<String, ?> pmParam) throws Exception {
        return DlwCertfDAO.getCertPrintChk(pmParam);
    }

    /**
     * 증서발급 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertCertHist(Map<String, ?> pmParam) throws Exception {
        return DlwCertfDAO.insertCertHist(pmParam);
    }

    public int getjengseCount(Map<String, ?> pmParam) throws Exception {
        return DlwCertfDAO.getjengseCount(pmParam);
    }

    public List<Map<String, Object>> getjengseList(Map<String, ?> pmParam) throws Exception {
        return DlwCertfDAO.getjengseList(pmParam);
    }
}
