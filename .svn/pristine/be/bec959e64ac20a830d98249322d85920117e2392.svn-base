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

import powerservice.business.biz.service.ChprBasiService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 담당자 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정훈
 * @version 1.0
 * @date 2015/07/27
 * @프로그램ID ChprBasi
 */
@Service
public class ChprBasiServiceImpl extends EgovAbstractServiceImpl implements ChprBasiService {

    @Resource
    public ChprBasiDAO chprBasiDAO;


    /**
     * 담당자 정보를 등록한다.
     *
     * @param pmParam 담당자 관리 정보
     * @throws Exception
     */
    public String insertChprBasi(Map<String, ?> pmParam) throws Exception {
        String sKey = "";

        int nResult = chprBasiDAO.insertChprBasi(pmParam);
        if (nResult > 0) {
            sKey = (String) pmParam.get("chpr_id");

           }

        return sKey;
    }


    /**
     * 담당자 정보를 수정한다.
     *
     * @param pmParam 담당자 관리 정보
     * @throws Exception
     */
  public int updateChprBasi(Map<String, ?> pmParam) throws Exception {
        int nResult = chprBasiDAO.updateChprBasi(pmParam);
        return nResult;
    }


    /**
     * 담당자 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 담당자 관리 정보의 검색 건수
     * @throws Exception
     */
    public int getChprBasiCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) chprBasiDAO.getChprBasiCount(pmParam);
    }


    /**
     * 담당자 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 담당자 관리 정보 목록
     * @throws Exception
     */
   public List<Map<String, Object>> getChprBasiList(Map<String, ?> pmParam) throws Exception {
        return chprBasiDAO.getChprBasiList(pmParam);
    }


    /**
     * 담당자 정보를 검색한다.
     *
     * @param psId ChprID
     * @return 담당자 관리 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getChprBasi(String psId) throws Exception {
        Map<String, String> pmParam = new HashMap<String, String>();
        pmParam.put("chpr_id", psId);
        return chprBasiDAO.getChprBasi(pmParam);
    }

    /**
     * 담당자 정보(팝업)의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 담당자 관리 정보의 검색 건수
     * @throws Exception
     */
    public int getChprBasiPopupCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) chprBasiDAO.getChprBasiPopupCount(pmParam);
    }


    /**
     * 담당자 정보(팝업)를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 담당자 관리 정보 목록
     * @throws Exception
     */
   public List<Map<String, Object>> getChprBasiPopupList(Map<String, ?> pmParam) throws Exception {
        return chprBasiDAO.getChprBasiPopupList(pmParam);
    }
}
