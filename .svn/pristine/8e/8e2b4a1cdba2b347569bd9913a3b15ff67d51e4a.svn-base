/*
 * (@)# TrctBoxAdmnService.java
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

package powerservice.business.usr.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.usr.service.TrctBoxAdmnService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 이관박스 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID TrctBoxAdmn
 */
@Service
public class TrctBoxAdmnServiceImpl extends EgovAbstractServiceImpl implements TrctBoxAdmnService {

    @Resource
    public TrctBoxAdmnDAO trctBoxAdmnDAO;

    /**
     * 이관박스 정보를 등록한다.
     *
     * @param pmParam 이관박스 정보
     * @throws Exception
     */
    public String insertTrctBoxAdmn(Map<String, ?> pmParam) throws Exception {
        String sKey = "";
        int nResult = trctBoxAdmnDAO.insertTrctBoxAdmn(pmParam);
        if (nResult > 0) {
            sKey = (String) pmParam.get("trct_box_id");
        }
        return sKey;
    }

    /**
     * 이관박스 정보를 수정한다.
     *
     * @param pmParam 이관박스 정보
     * @throws Exception
     */
    public int updateTrctBoxAdmn(Map<String, ?> pmParam) throws Exception {
        return trctBoxAdmnDAO.updateTrctBoxAdmn(pmParam);
    }

    /**
     * 이관박스 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 이관박스 정보의 검색 건수
     * @throws Exception
     */
    public int getTrctBoxAdmnCount(Map<String, ?> pmParam) throws Exception {
        return trctBoxAdmnDAO.getTrctBoxAdmnCount(pmParam);
    }

    /**
     * 이관박스 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 이관박스 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getTrctBoxAdmnList(Map<String, ?> pmParam) throws Exception {
        return trctBoxAdmnDAO.getTrctBoxAdmnList(pmParam);
    }

    /**
     * 화면 로드 이관박스 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 이관박스 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getTrctBoxAdmnListForScreenData(Map<String, ?> pmParam) throws Exception {
        return trctBoxAdmnDAO.getTrctBoxAdmnListForScreenData(pmParam);
    }

    /**
     * 이관박스 정보를 검색한다.
     *
     * @param id 상담아이디
     * @return 이관박스 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getTrctBoxAdmn(String psId) throws Exception {
        Map<String, String> mParam = new HashMap<String, String>();
        mParam.put("trct_box_id", psId);
        return trctBoxAdmnDAO.getTrctBoxAdmn(mParam);
    }

    /**
     * 이관박스 정보를 삭제한다.
     *
     * @param param 이관박스 정보
     * @throws Exception
     */
    public int deleteTrctBoxAdmn(Map<String, Object> pmParam) throws Exception {
        return trctBoxAdmnDAO.deleteTrctBoxAdmn(pmParam);
    }

    /**
     * 이관박스 전송을 위한 정보를 가져온다.
     *
     * @param psTrctBoxId 검색 조건
     * @return 이관박스 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getTrctBox(String psTrctBoxId) throws Exception {
        return trctBoxAdmnDAO.getTrctBox(psTrctBoxId);
    }

    /**
     * 이관박스 전송을 위한 정보를 가져온다.(이관박스를 받을 대상 아이디를 전달한다.)
     *
     * @param psTrctBoxId, psUserId 검색 조건
     * @return 이관박스 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getTrctBox(String psTrctBoxId, String psUserId) throws Exception {
        Map<String, Object> mParam = new HashMap<String, Object>();
        mParam.put("trct_box_id", psTrctBoxId);
        mParam.put("user_id", psUserId);
        return trctBoxAdmnDAO.getTrctBoxWithUserId(mParam);
    }

}
