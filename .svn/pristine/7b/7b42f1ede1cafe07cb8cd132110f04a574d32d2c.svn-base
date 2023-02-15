/*
 * (@)# TrctBoxAdmnDAO.java
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
 * 이관박스 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID TrctBoxAdmn
 */
@Repository
public class TrctBoxAdmnDAO extends EgovAbstractMapper {

    /**
     * 이관박스 정보를 등록한다.
     *
     * @param pmParam 이관박스 정보
     * @throws Exception
     */
    public int insertTrctBoxAdmn(Map<String, ?> pmParam) throws Exception {
        return insert("TrctBoxAdmnMap.insertTrctBoxAdmn", pmParam);
    }

    /**
     * 이관박스 정보를 수정한다.
     *
     * @param pmParam 이관박스 정보
     * @throws Exception
     */
    public int updateTrctBoxAdmn(Map<String, ?> pmParam) throws Exception {
        return update("TrctBoxAdmnMap.updateTrctBoxAdmn", pmParam);
    }

    /**
     * 이관박스 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 이관박스 정보의 검색 건수
     * @throws Exception
     */
    public int getTrctBoxAdmnCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("TrctBoxAdmnMap.getTrctBoxAdmnCount", pmParam);
    }

    /**
     * 이관박스 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 이관박스 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getTrctBoxAdmnList(Map<String, ?> pmParam) throws Exception {
        return selectList("TrctBoxAdmnMap.getTrctBoxAdmnList", pmParam);
    }

    /**
     * 화면 로드 이관박스 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 이관박스 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getTrctBoxAdmnListForScreenData(Map<String, ?> pmParam) throws Exception {
        return selectList("TrctBoxAdmnMap.getTrctBoxAdmnListForScreenData", pmParam);
    }

    /**
     * 이관박스 정보를 검색한다.
     *
     * @param pmParam 상담아이디
     * @return 이관박스 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getTrctBoxAdmn(Map<String, ?> pmParam) throws Exception {
        return selectOne("TrctBoxAdmnMap.getTrctBoxAdmnList", pmParam);
    }

    /**
     * 이관박스 정보를 삭제한다.
     *
     * @param pmParam 이관박스 정보
     * @throws Exception
     */
    public int deleteTrctBoxAdmn(Map<String, Object> pmParam) throws Exception {
        return delete("TrctBoxAdmnMap.deleteTrctBoxAdmn", pmParam);
    }

    /**
     * 이관박스 전송을 위한 정보를 가져온다.
     *
     * @param psTrctBoxId 검색 조건
     * @return 이관박스 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getTrctBox(String psTrctBoxId) throws Exception {
        return selectList("TrctBoxAdmnMap.getTrctBox", psTrctBoxId);
    }

    /**
     * 이관박스 전송을 위한 정보를 가져온다.(이관박스를 받을 대상 아이디를 전달한다.)
     *
     * @param pmParam 검색 조건
     * @return 이관박스 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getTrctBoxWithUserId(Map<String, Object> pmParam) throws Exception {
        return selectList("TrctBoxAdmnMap.getTrctBoxWithUserId", pmParam);
    }

}
