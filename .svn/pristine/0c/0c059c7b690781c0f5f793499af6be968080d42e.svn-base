/*
 * (@)# TrmDtlServiceImpl.java
 *
 * @author 김현경
 * @version 1.0
 * @date 2015/03/26
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

import powerservice.business.mta.service.TrmDtlService;

/**
 * 용어사전 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 김현경
 * @version 1.0
 * @date 2015/03/26
 * @프로그램ID TrmDtl
 */
@Service
public class TrmDtlServiceImpl extends EgovAbstractServiceImpl implements TrmDtlService {

    @Resource
    public TrmDtlDAO trmDtlDAO;

    /**
     * 용어 정보의 검색 수를 반환한다. (15.03.26)
     *
     * @param pmParamHash 검색 조건
     * @return 용어내역 정보의 검색 건수
     * @throws Exception
     */
    public int getTrmDtlCount(Map<String, ?> pmParam) throws Exception {
        return trmDtlDAO.getTrmDtlCount(pmParam);
    }

    /**
     * 용어 정보를 검색한다. (15.03.26)
     *
     * @param pmParamHash 검색 조건
     * @return 용어내역 관리 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getTrmDtlList(Map<String, ?> pmParam) throws Exception {
        return trmDtlDAO.getTrmDtlList(pmParam);
    }

    /**
     * 용어 정보를 검색한다. (15.03.26)
     *
     * @param id 용어
     * @return 용어내역 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getTrmDtl(String id) throws Exception {
        Map<String, String> pmParam = new HashMap<String, String>();
        pmParam.put("term_id", id);

        return trmDtlDAO.getTrmDtl(pmParam);
    }

    /**
     * 용어 정보를 용어 내역 테이블에 등록한다. (15.03.26)
     *
     * @param pmParam 용어내역 정보
     * @throws Exception
     */
    public String insertTrmDtl(Map<String, ?> pmParam) throws Exception {
        String key = "";

        int nResult = trmDtlDAO.insertTrmDtl(pmParam);

        if (nResult > 0) {
            key = (String) pmParam.get("term_id");
        }

        return key;
    }

    /**
     * 용어 정보를 용어 구성 내역 테이블에 등록한다. (15.03.31)
     *
     * @param pmParam 용어내역 정보
     * @throws Exception
     */
    public int insertCommondiction(Map<String, ?> pmParam) throws Exception {
        return trmDtlDAO.insertCommondiction(pmParam);
    }


    /**
     * 용어 정보를 수정한다. (15.03.26)
     *
     * @param pmParam 용어내역 정보
     * @throws Exception
     */
    public int updateTrmDtl(Map<String, ?> pmParam) throws Exception {
        return trmDtlDAO.updateTrmDtl(pmParam);
    }

    /**
     * 용어 내역, 용어 구성 내역을 삭제한다. (15.03.26)
     *
     * @param pmParam 용어내역 정보
     * @throws Exception
     */
    public void deleteTrmDtl(Map<String, ?> pmParam) throws Exception {
        trmDtlDAO.deleteTrmDtl(pmParam);
    }

    /**
     * 용어명을 중복체크한다. (15.03.27)
     *
     * @param pmParam 용어명 정보
     * @throws Exception
     */
    public int checkTrmDtl(Map<String, ?> pmParam) throws Exception {
        return trmDtlDAO.checkTrmDtl(pmParam);
    }

    /**
     * 입력받은 용어명이 단어사전관리 테이블의 단어명에 있는지 확인한다. (15.03.27, 31)
     *
     * @param pmParam 용어명 정보
     * @throws Exception
     */
    public String getWrdDicCheck(String data){
        String word_check = trmDtlDAO.getWrdDicCheck(data);

        if ( word_check == null ){
            word_check = data;
        } else {   	}

        return word_check;
    };

    /**
     * 용어명을 조회한다. (15.03.31)
     *
     * @param pmParam 용어내역 정보
     * @throws Exception
     */
    public String getTrmDtlnm(Map<String, ?> pmParam) throws Exception {
        return trmDtlDAO.getTrmDtlnm(pmParam);
    }

    /**
     * 용어 정보를 검색한다. (15.05.11)
     *
     * @param pmParamHash 검색 조건
     * @return 용어 한글명 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getTrmDtlNmList(Map<String, ?> pmParam) throws Exception {
        return trmDtlDAO.getTrmDtlNmList(pmParam);
    }

    /**
     * 칼럼상세 정보의 검색 수를 반환한다. (15.05.14)
     *
     * @param pmParam 검색 조건
     * @return 칼럼상세 정보의 검색 수
     * @throws Exception
     */
    public int getRefercolmCount(Map<String, ?> pmParam) throws Exception {
        return trmDtlDAO.getRefercolmCount(pmParam);
    }
}
