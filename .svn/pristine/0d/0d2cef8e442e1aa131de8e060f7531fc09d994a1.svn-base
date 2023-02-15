/*
 * (@)# CscoBasiServiceImpl.java
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

package powerservice.business.biz.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.biz.service.SlopIssHstrService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

    /**
     * 영업 이슈정보 이력 관리를 한다.
     *
     * Copyright (c) 2015 Company Inwoo Tech Inc.
     *
     * @author 정훈
     * @version 1.0
     * @date 2015/08/08
     * @프로그램ID <PS-UI-DS11>
     */
@Service
public class SlopIssHstrServiceImpl extends EgovAbstractServiceImpl implements SlopIssHstrService {

    @Resource
    public SlopIssHstrDAO slopIssHstrDAO;


    /**
     * 영업 이슈정보 이력의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 영업 이슈정보 이력의 검색 건수
     * @throws Exception
     */
    public int getSlopIssHstrCount(Map<String, ?> pmParam) throws Exception {
        return slopIssHstrDAO.getSlopIssHstrCount(pmParam);
    }


    /**
     * 영업 이슈정보 이력을 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 영업 이슈정보 이력 목록
     * @throws Exception
     */
   public List<Map<String, Object>> getSlopIssHstrList(Map<String, ?> pmParam) throws Exception {
       return slopIssHstrDAO.getSlopIssHstrList(pmParam);
    }


   /**
    * 영업 이슈정보 이력을 등록한다.
    *
    * @param pmParam 영업 이슈정보
    * @throws Exception
    */
    public String insertSlopIssHstr(Map<String, ?> pmParam) throws Exception {
        String sKey = "";

        int nResult = slopIssHstrDAO.insertSlopIssHstr(pmParam);
        if (nResult > 0) {
            sKey = (String) pmParam.get("slop_iss_id");

                 }
        return sKey;
    }

    /**
     * 사업원장관리 -> 영업 이슈 이력를 삭제한다.
     *
     * @param pmParam 영업이슈정보
     * @throws Exception
     */
    public int deleteSlopIssHstr(Map<String, Object> pmParam) throws Exception {
        return slopIssHstrDAO.deleteSlopIssHstr(pmParam);
    }


}
