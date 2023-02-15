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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.biz.service.CompBasiService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 고객사/협력사 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정훈
 * @version 1.0
 * @date 2015/07/27
 * @프로그램ID <PS-UI-DS01>
 */
@Service
public class CompBasiServiceImpl extends EgovAbstractServiceImpl implements CompBasiService {

    @Resource
    public CompBasiDAO compBasiDAO;


    /**
     * 고객사/협력사 를 등록한다.
     *
     * @param pmParam 고객사/협력사 정보
     * @throws Exception
     */
    public String insertCompBasi(Map<String, ?> pmParam) throws Exception {
        String sKey = "";

        int nResult = compBasiDAO.insertCompBasi(pmParam);
        if (nResult > 0) {
            sKey = (String) pmParam.get("comp_id");

                 }

        return sKey;
    }


    /**
     * 고객사/협력사 를 수정한다.
     *
     * @param pmParam 고객사/협력사 정보
     * @throws Exception
     */
  public int updateCompBasi(Map<String, ?> pmParam) throws Exception {
        return  compBasiDAO.updateCompBasi(pmParam);
    }


    /**
     * 고객사/협력사 의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return고객사/협력사 정보의 검색 건수
     * @throws Exception
     */
    public int getCompBasiCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) compBasiDAO.getCompBasiCount(pmParam);
    }


    /**
     * 고객사/협력사를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 고객사/협력사 정보 목록
     * @throws Exception
     */
   public List<Map<String, Object>> getCompBasiList(Map<String, ?> pmParam) throws Exception {
        return compBasiDAO.getCompBasiList(pmParam);
    }


    /**
     * 고객 정보를 검색한다.
     *
     * @param psId Comp ID
     * @return 고객사/협력사 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getCompBasi(String psId) throws Exception {

        Map<String, String> pmParam = new HashMap<String, String>();

        pmParam.put("comp_id", psId);
        return compBasiDAO.getCompBasi(pmParam);
    }


    /**
     * 고객사/협력사 팝업의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return고객사/협력사 정보의 검색 건수
     * @throws Exception
     */
    public int getCompBasiPopupCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) compBasiDAO.getCompBasiPopupCount(pmParam);
    }


    /**
     * 고객사/협력사 팝업을 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 고객사/협력사 정보 목록
     * @throws Exception
     */
   public List<Map<String, Object>> getCompBasiPopupList(Map<String, ?> pmParam) throws Exception {
        return compBasiDAO.getCompBasiPopupList(pmParam);
    }

}
