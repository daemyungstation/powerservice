/*
 * (@)# TrctChprAdmnDAO.java
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

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 이관담당자 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID TrctChprAdmn
 */
@Repository
public class TrctChprAdmnDAO extends EgovAbstractMapper {

    /**
     * 이관담당자 정보를 등록한다.
     *
     * @param pmParam 이관담당자 정보
     * @throws Exception
     */
    public int insertTrctChprAdmn(Map<String, ?> pmParam) throws Exception {
        return insert("TrctChprAdmnMap.insertTrctChprAdmn", pmParam);
    }

    /**
     * 이관담당자 정보를 저장한다.
     *
     * @param pmParam 이관담당자 정보
     * @throws Exception
     */
    public int saveTrctChprAdmn(Map<String, ?> pmParam) throws Exception {
        return update("TrctChprAdmnMap.updateTrctChprAdmn", pmParam);
    }

    /**
     * 이관담당자 정보를 수정한다.
     *
     * @param pmParam 이관담당자 정보
     * @throws Exception
     */
    public int updateTrctChprAdmn(Map<String, ?> pmParam) throws Exception {
        return update("TrctChprAdmnMap.updateTrctChprAdmn", pmParam);
    }

    /**
     * 이관담당자 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 이관담당자 정보의 검색 건수
     * @throws Exception
     */
    public int getTrctChprAdmnCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("TrctChprAdmnMap.getTrctChprAdmnCount", pmParam);
    }

    /**
     * 이관담당자 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 이관담당자 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getTrctChprAdmnList(Map<String, ?> pmParam) throws Exception {
        return selectList("TrctChprAdmnMap.getTrctChprAdmnList", pmParam);
    }

    /**
     * 이관담당자 정보를 삭제한다.
     *
     * @param pmParam 이관담당자 정보
     * @throws Exception
     */
    public int deleteTrctChprAdmn(Map<String, Object> pmParam) throws Exception {
        return delete("TrctChprAdmnMap.deleteTrctChprAdmn", pmParam);
    }


    /**
     * 이관담당자 정보를 검색한다.(이관박스현황에서 사용)
     *
     * @param 이관박스아이디
     * @param 담당자아이디
     * @return 이관담당자 정보
     * @throws Exception
     */
    public List<Map<String, Object>> getTrnsUserForTrnsbox(Map<String, ?> pmParam) throws Exception {
        return selectList("TrctChprAdmnMap.getTrnsUserForTrnsbox", pmParam);
    }

}
