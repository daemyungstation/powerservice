/*
 * (@)# WrdDicServiceImpl.java
 *
 * @author 김현경
 * @version 1.0
 * @date 2015/03/19
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

package powerservice.business.mta.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

import powerservice.business.mta.service.WrdDicService;

/**
 * 단어사전 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 김현경
 * @version 1.0
 * @date 2015/03/19
 * @프로그램ID WrdDics
 */
@Service
public class WrdDicServiceImpl extends EgovAbstractServiceImpl implements WrdDicService {

    @Resource
    public WrdDicDAO wrdDicDAO;

    /**
     * 단어 정보의 검색 수를 반환한다.
     *
     * @param pmParamHash 검색 조건
     * @return 단어사전 정보의 검색 건수
     * @throws Exception
     */
    public int getWrdDicCount(Map<String, ?> pmParam) throws Exception {
        return wrdDicDAO.getWrdDicCount(pmParam);
    }

    /**
     * 단어 정보를 검색한다.
     *
     * @param pmParamHash 검색 조건
     * @return 단어사전 관리 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getWrdDicList(Map<String, ?> pmParam) throws Exception {
        return wrdDicDAO.getWrdDicList(pmParam);
    }

    /**
     * 단어 정보를 검색한다.
     *
     * @param id 단어
     * @return 단어사전 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getWrdDic(String id) throws Exception {
        Map<String, String> pmParam = new HashMap<String, String>();
        pmParam.put("word_id", id);

        return wrdDicDAO.getWrdDic(pmParam);
    }

    /**
     * 단어 정보를 등록한다.
     *
     * @param pmParam 단어사전 정보
     * @throws Exception
     */
    public String insertWrdDic(Map<String, ?> pmParam) throws Exception {
        String key = "";

        int nResult = wrdDicDAO.insertWrdDic(pmParam);

        if (nResult > 0) {
            key = (String) pmParam.get("word_id");
        }
        return key;
    }

    /**
     * 단어 정보를 수정한다.
     *
     * @param pmParam 단어사전 정보
     * @throws Exception
     */
    public int updateWrdDic(Map<String, ?> pmParam) throws Exception {
        return wrdDicDAO.updateWrdDic(pmParam);
    }

    /**
     * 단어 정보를 삭제한다.
     *
     * @param pmParam 단어 정보
     * @throws Exception
     */
    public int deleteWrdDic(Map<String, ?> pmParam) throws Exception {
        return wrdDicDAO.deleteWrdDic(pmParam);
    }

    /**
     * 단어명 중복체크를 한다.
     *
     * @param nm 단어명
     * @return 단어사전 정보(1건)
     * @throws Exception
     */
    public int checkWrdDic(Map<String, ?> pmParam) throws Exception {
        return wrdDicDAO.checkWrdDic(pmParam);
    }

    /**
     * 단어ID를 받아온다.
     *
     * @param nm 단어명
     * @return 단어ID 정보(1건)
     * @throws Exception
     */
    public String getWrdDicId(Map<String, ?> pmParam) throws Exception {
        return wrdDicDAO.getWrdDicId(pmParam);
    }

    /**
     * 용어 구성 내역에서 단어ID의 갯수를 확인한다.
     *
     * @param nm 단어명
     * @return 단어ID 정보
     * @throws Exception
     */
    public int getWrdDicIdCount(Map<String, ?> pmParam) throws Exception {
        return wrdDicDAO.getWrdDicIdCount(pmParam);
    }
}
