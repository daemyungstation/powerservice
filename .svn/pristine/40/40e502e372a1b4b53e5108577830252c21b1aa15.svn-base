/*
 * (@)# TrmDtlDAO.java
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

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

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
@Repository
public class TrmDtlDAO extends EgovAbstractMapper {

    /**
     * 용어 정보의 검색 수를 반환한다. (15.03.26)
     *
     * @param pmParamHash 검색 조건
     * @return 용어내역 정보의 검색 건수
     * @throws Exception
     */
    public int getTrmDtlCount(Map<String, ?> pmParam) throws Exception {
        return (Integer)selectOne("TrmDtlMap.getTrmDtlCount", pmParam);
    }

    /**
     * 용어 정보를 검색한다. (15.03.26)
     *
     * @param pmParamHash 검색 조건
     * @return 용어내역 관리 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getTrmDtlList(Map<String, ?> pmParam) throws Exception {
        return selectList("TrmDtlMap.getTrmDtlList", pmParam);
    }

    /**
     * 용어 정보를 검색한다. (15.03.26)
     *
     * @param id 용어
     * @return 용어내역 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getTrmDtl(Map<String, ?> pmParam) throws Exception {
        return selectOne("TrmDtlMap.getTrmDtlList", pmParam);
    }

    /**
     * 용어 정보를 용어 내역 테이블에 등록한다. (15.03.26)
     *
     * @param pmParam 용어내역 정보
     * @throws Exception
     */
    public int insertTrmDtl(Map<String, ?> pmParam) throws Exception {
        return insert("TrmDtlMap.insertTrmDtl", pmParam);
    }

    /**
     * 용어 정보를 용어 구성 내역 테이블에 등록한다. (15.03.31)
     *
     * @param pmParam 용어내역 정보
     * @throws Exception
     */
    public int insertCommondiction(Map<String, ?> pmParam) throws Exception {
        return insert("TrmDtlMap.insertCommondiction", pmParam);
    }


    /**
     * 용어 정보를 수정한다. (15.03.26) //미완성
     *
     * @param pmParam 용어내역 정보
     * @throws Exception
     */
    public int updateTrmDtl(Map<String, ?> pmParam) throws Exception {
        return update("TrmDtlMap.updateTrmDtl", pmParam);
    }

    /**
     * 용어 내역, 용어 구성 내역을 삭제한다. (15.03.26)
     *
     * @param pmParam 용어내역 정보
     * @throws Exception
     */
    public void deleteTrmDtl(Map<String, ?> pmParam) throws Exception {
        delete("TrmDtlMap.deleteTrmDtl", pmParam);
        delete("TrmDtlMap.deleteCommondiction", pmParam);
    }

    /**
     * 용어명을 중복체크한다. (15.03.27)
     *
     * @param pmParam 용어명 정보
     * @throws Exception
     */
    public int checkTrmDtl(Map<String, ?> pmParam) throws Exception {
        return (Integer)selectOne("TrmDtlMap.checkTrmDtl", pmParam);
    }

    /**
     * 입력받은 용어명이 단어사전관리 테이블의 단어명에 있는지 확인한다. (15.03.27, 31)
     *
     * @param pmParam 용어명 정보
     * @throws Exception
     */
    public String getWrdDicCheck(String data){
        return selectOne("TrmDtlMap.getWrdDicCheck", data);
    };

    /**
     * 용어명을 조회한다. (15.03.31)
     *
     * @param pmParam 용어내역 정보
     * @throws Exception
     */
    public String getTrmDtlnm(Map<String, ?> pmParam) throws Exception {
        return selectOne("TrmDtlMap.getTrmDtlnm", pmParam);
    }

    /**
     * 용어 정보를 검색한다. (15.05.11)
     *
     * @param pmParamHash 검색 조건
     * @return 용어 한글명 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getTrmDtlNmList(Map<String, ?> pmParam) throws Exception {
        return selectList("TrmDtlMap.getTrmDtlNmList", pmParam);
    }

    /**
     * 칼럼상세 정보의 검색 수를 반환한다. (15.05.14)
     *
     * @param pmParam 검색 조건
     * @return 칼럼상세 정보의 검색 수
     * @throws Exception
     */
    public int getRefercolmCount(Map<String, ?> pmParam) throws Exception {
        return (Integer)selectOne("TrmDtlMap.getRefercolmCount", pmParam);
    }
}
