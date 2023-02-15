/*
 * (@)# EvltAckdDtl.java
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

package powerservice.business.evl.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 평가지를 관리한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 최현식
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID EvltAckdDtl
 */
@Repository
public class EvltAckdDtlDAO extends EgovAbstractMapper {

    /**
     * 평가자승인 정보의 검색 수를 반환한다.
     *
     * @param param 검색 조건
     * @return 평가자승인 정보의 검색 수
     * @throws Exception
     */
    public int getEvltAckdDtlCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("EvltAckdDtlMap.getEvltAckdDtlCount", pmParam);
    }

    /**
     * 평가자승인 정보(목록)를 검색한다.
     *
     * @param param 검색 조건
     * @return 평가자승인 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getEvltAckdDtlList(Map<String, ?> pmParam) throws Exception {
        return selectList("EvltAckdDtlMap.getEvltAckdDtlList", pmParam);
    }

    /**
     * 평가자승인 정보를 등록한다.
     *
     * @param param 평가자승인 정보
     * @throws Exception
     */
    public int getApprCnt(Map<String, Object> pmParam) throws Exception {
        return (Integer) selectOne("EvltAckdDtlMap.getApprCnt", pmParam);
    }
    /**
     * 평가자승인 정보를 등록한다.
     *
     * @param param 평가자승인 정보
     * @throws Exception
     */
    public int insertEvltAckdDtl(Map<String, Object> pmParam) throws Exception {
        return insert("EvltAckdDtlMap.insertEvltAckdDtl", pmParam);
    }

    /**
     * 평가자승인 정보(1건)를 검색한다.
     *
     * @param paramHash 검색 조건
     * @return 평가자승인 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getEvltAckdDtl(Map<String, ?>pmParam) throws Exception {
        return selectOne("EvltAckdDtlMap.getEvltAckdDtlList", pmParam);
    }

    /**
     * 평가자승인 정보를 수정한다.
     *
     * @param param 평가자승인 정보
     * @throws Exception
     */
    public int updateEvltAckdDtl(Map<String, Object> pmParam) throws Exception {
        return update("EvltAckdDtlMap.updateEvltAckdDtl", pmParam);
    }

    /**
     * 이의제기 요청 검색 수를 반환한다.
     *
     * @param param 검색 조건
     * @return 이이제기요청의 검색 수
     * @throws Exception
     */
    public int getApprRequsetCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("EvltAckdDtlMap.getApprRequsetCount", pmParam);
    }

}
