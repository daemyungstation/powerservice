/*
 * (@)# EdctTrprDAO.java
 *
 * @author 박상수
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

package powerservice.business.edc.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 교육평가 관리를 한다.
 *
 * Copyright (c) 2013 Company Inwoo Tech Inc.
 *
 * @author 박상수
 * @version 1.0
 * @date 2015/01/05
 * @프로그램ID EdctTrprDAO
 */
@Repository
public class EdctTrprDAO extends EgovAbstractMapper {

    /**
     * 교육평가대상자 정보의 검색 수를 반환한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 교육평가대상자 정보의 검색 건수
     * @throws Exception
     */
    public int getEdctTrprcount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("EdctTrprMap.getEdctTrprcount", pmParam);
    }

    /**
     * 고육평가 정보를 검색한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 교육평가대상자 목록
     * @throws Exception
     */
    public List<Map<String, Object>> searchEdctTrprList(Map<String, ?> pmParam) throws Exception {
        return selectList("EdctTrprMap.searchEdctTrprList", pmParam);
    }

    /**
     * 교육평가대상자를 검색한다.
     *
     * @pmParam id 교육평가대상자 ID
     * @return 교육평가대상자 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getEduUser(Map<String, ?>pmParam) throws Exception {
        return selectOne("EdctTrprMap.getEduUserList", pmParam);
    }

    /**
     * 상담사 정보의 검색 수를 반환한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 상담사 정보의 검색 수
     * @throws Exception
     */
    public int getUserCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("EdctTrprMap.getUserCount", pmParam);
    }

    /**
     * 상담사 정보(목록)를 검색한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 상담사 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> searchUserList(Map<String, ?> pmParam) throws Exception {
        return selectList("EdctTrprMap.searchUserList", pmParam);
    }

    /**
     * 교육대상자 정보를 등록한다.
     *
     * @pmParam pmParam 교육대상자 정보
     * @throws Exception
     */
    public int insertEdctTrpr(Map<String, Object> pmParam) throws Exception {
        return insert("EdctTrprMap.insertEdctTrpr", pmParam);
    }

    /**
     * 교육대상자 정보를 삭제한다.
     *
     * @pmParam pmParam 교육대상자 정보
     * @throws Exception
     */
    public int deleteEdctTrpr(Map<String, Object> pmParam) throws Exception {
        return delete("EdctTrprMap.deleteEdctTrpr", pmParam);
    }

    /**
     * 교육대상자 정보를 수정한다.
     *
     * @pmParam pmParam 교육 정보
     * @throws Exception
     */
    public int updateEdctTrpr(Map<String, Object> pmParam) throws Exception {
        return update("EdctTrprMap.updateEdctTrpr", pmParam);
    }


    /**
     * 고육평가 정보를 검색한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 교육평가대상자 목록
     * @throws Exception
     */
    public List<Map<String, Object>> searchEdctTrprCardList(Map<String, ?> pmParam) throws Exception {
        return selectList("EdctTrprMap.searchEdctTrprCardList", pmParam);
    }

    /**
     * 월간 교육훈련 결과(상담사별) 정보의 검색 수를 반환한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 월간교육훈련 결과 정보의 검색 건수
     * @throws Exception
     */
    public int edctTrprResultCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("EdctTrprMap.edctTrprResultCount", pmParam);
    }

    /**
     * 월간 교육훈련 결과(상담사별) 정보를 검색 한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 월간교육훈련 결과 정보
     * @throws Exception
     */
    public List<Map<String, Object>> edctTrprResult(Map<String, ?> pmParam) throws Exception {
        return selectList("EdctTrprMap.edctTrprResult", pmParam);
    }
}
