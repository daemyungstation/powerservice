/*
 * (@)# ExtnNoAdmnService.java
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

package powerservice.business.usr.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.usr.service.ExtnNoAdmnService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 센터 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 김은지
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID ExtnNoAdmn
 */
@Service
public class ExtnNoAdmnServiceImpl extends EgovAbstractServiceImpl implements ExtnNoAdmnService {

    @Resource
    public ExtnNoAdmnDAO extnNoAdmnDAO;

    /**
     * 아이피 및 내선번호 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 아이피 및 내선번호 정보의 검색 수
     * @throws Exception
     */
    public int getExtnNoCount(Map<String, ?> pmParam) throws Exception {
        return extnNoAdmnDAO.getExtnNoCount(pmParam);
    }

    /**
     * 아이피 및 내선번호 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 아이피 및 내선번호 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getExtnNoList(Map<String, ?> pmParam) throws Exception {
        return extnNoAdmnDAO.getExtnNoList(pmParam);
    }

    /**
     * 아이피 및 내선번호 정보를 검색한다.
     *
     * @param param 검색 조건
     * @return 아이피 및 내선번호 정보
     * @throws Exception
     */
    public Map<String, Object> getExtnNo(String psId) throws Exception {
        Map<String, String> mParam = new HashMap<String, String>();
        mParam.put("extn_no_admn_id", psId);
        return extnNoAdmnDAO.getExtnNo(mParam);
    }

    /**
     * 아이피 및 내선번호 정보를 등록한다.(ajax)
     *
     * @param pmParam 아이피 및 내선번호 정보
     * @throws Exception
     */
    public String insertExtnNo(Map<String, ?> pmParam) throws Exception {
        String sKey = "";
        int nResult = extnNoAdmnDAO.insertExtnNo(pmParam);
        if (nResult > 0) {
            sKey = (String) pmParam.get("extn_no_admn_id");
        }
        return sKey;
    }

    /**
     * 아이피 및 내선번호 정보를 수정한다.(ajax)
     *
     * @param pmParam 아이피 및 내선번호 정보
     * @throws Exception
     */
    public int updateExtnNo(Map<String, ?> pmParam) throws Exception {
        return extnNoAdmnDAO.updateExtnNo(pmParam);
    }

    /**
     * 아이피 및 내선번호 정보를 삭제한다.
     *
     * @param pmParam 아이피 및 내선번호 정보
     * @throws Exception
     */
    public int deleteExtnNo(Map<String, ?> pmParam) throws Exception {
        return extnNoAdmnDAO.deleteExtnNo(pmParam);
    }

    /**
     * 아이피로 내선번호 정보를 검색한다.
     *
     * @param psIp 아이피
     * @return 내선번호 정보
     * @throws Exception
     */
    public Map<String, Object> getExtnNoByIpAddr(String psIp) throws Exception {
        Map<String, String> mParam = new HashMap<String, String>();
        //mParam.put("ip_addr", psIp);
        mParam.put("user_id", psIp);
        return extnNoAdmnDAO.getExtnNoByIpAddr(mParam);
    }

}
