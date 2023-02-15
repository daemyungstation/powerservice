/*
 * (@)# CertfMngServiceImpl.java
 *
 * @author 정용재
 * @version 1.0
 * @date 2016/07/04
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
package powerservice.business.kms.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.kms.service.CertfMngService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 증서를 관리한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정용재
 * @version 1.0
 * @date 2016/07/04
 * @프로그램ID PS994500
 *
 */
@Service
public class CertfMngServiceImpl extends EgovAbstractServiceImpl implements CertfMngService {

    @Resource
    public CertfMngDAO certfMngDAO;

    /**
     * 증서 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return CertfMng 정보의 검색 수
     * @throws Exception
     */
    public int getCertfMngCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) certfMngDAO.getCertfMngCount(pmParam);
    }

    /**
     * 증서 정보(목록)를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 물류 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getCertfMngList(Map<String, ?> pmParam) throws Exception {
        return certfMngDAO.getCertfMngList(pmParam);
    }

    /**
     * 증서 정보(목록)를 검색한다.(1건)
     *
     * @param pmParam 검색 조건
     * @return 물류 리스트
     * @throws Exception
     */
    public Map<String, Object> getCertfMng(Map<String, ?> pmParam) throws Exception {
        return certfMngDAO.getCertfMng(pmParam);
    }


    /**
     * 증서정보 update
     *
     */
    public void updatecertf_mng(Map<String, ?> pmParam) throws Exception {
         certfMngDAO.updatecertf_mng(pmParam);
    }
    /**
     * 증서정보 insert
     *
     */
    public void insertcertf_mng(Map<String, ?> pmParam) throws Exception {
         certfMngDAO.insertcertf_mng(pmParam);
    }



    /**
     * 증서정보 delete
     *
     */
    public void deletecertf_mng(Map<String, ?> pmParam) throws Exception {
         certfMngDAO.deletecertf_mng(pmParam);
    }

}
