/*
 * (@)# AcsServiceImpl.java
 *
 * @author 박의준
 * @version 1.0
 * @date 2015/11/05
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

package powerservice.business.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.sys.service.AcsService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 접근가능한 IP 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 박의준
 * @version 1.0
 * @date 2015/11/05
 * @프로그램ID Acs
 */
@Service
public class AcsServiceImpl extends EgovAbstractServiceImpl implements AcsService {

    @Resource
    public AcsDAO acsDAO;

    /**
     * 접근가능한 IP 정보를 등록한다.
     *
     * @param pmParam 접근가능한 IP 정보
     * @throws Exception
     */
    public String insertAcs(Map<String, ?> pmParam) throws Exception {
        String skey = "";

        int nResult = acsDAO.insertAcs(pmParam);
        if (nResult > 0) {
            skey = (String) pmParam.get("prms_id_addr");
        }

        return skey;
    }

    /**
     * 접근가능한 IP 정보를 수정한다.
     *
     * @param pmParam 접근가능한 IP 정보
     * @throws Exception
     */
    public String updateAcs(Map<String, ?> pmParam) throws Exception {
        String skey = "";

        int nResult = acsDAO.updateAcs(pmParam);
        if (nResult > 0) {
            skey = (String) pmParam.get("prms_id_addr");
        }

        return skey;
    }

    /**
     * 접근가능한 IP 정보를 삭제한다.
     *
     * @param pmParam 접근가능한 IP 정보
     * @throws Exception
     */
    public int deleteAcs(Map<String, ?> pmParam) throws Exception {
        // 게시물 삭제
        int nResult = acsDAO.deleteAcs(pmParam);

        return nResult;
    }

    /**
     * 접근가능한 IP 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 접근가능한 IP 정보의 검색 수
     * @throws Exception
     */
    public int getAcsCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) acsDAO.getAcsCount(pmParam);
    }

    /**
     * 접근가능한 IP 정보(목록)를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 접근가능한 IP 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getAcsIpList() throws Exception {
        return acsDAO.getAcsIpList();
    }

    /**
     * 접근가능한 IP 정보(목록)를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 접근가능한 IP 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getAcsList(Map<String, ?> pmParam) throws Exception {
        return acsDAO.getAcsList(pmParam);
    }

    /**
     * 접근가능한 IP 정보(1건)를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 접근가능한 IP 정보
     * @throws Exception
     */
    public Map<String, Object> getAcs(String sAcsAdmnId) throws Exception {
        Map<String, String> mParam = new HashMap<String, String>();
        mParam.put("prms_ip_addr", sAcsAdmnId);
        return acsDAO.getAcs(mParam);
    }

    /**
     * 접근가능한 IP 중복여부.
     *
     * @param pmParam 검색 조건
     * @return 접근가능한 IP 리스트 (1건)
     * @throws Exception
     */
    public int getAcsDuplicateCount(Map<String, ?> pmParam) throws Exception {
        return acsDAO.getAcsDuplicateCount(pmParam);
    }



}
