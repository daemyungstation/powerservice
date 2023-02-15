/*
 * (@)# EdctCrsDAO.java
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
 * @프로그램ID EdctCrsDAO
 */
@Repository
public class EdctCrsDAO extends EgovAbstractMapper {

    /**
     * 교육평가 정보의 검색 수를 반환한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 교육평가 정보의 검색 건수
     * @throws Exception
     */
    public int getEdctCrsCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("EdctCrsMap.getEdctCrsCount", pmParam);
    }

    /**
     * 고육평가 정보를 검색한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 교육평가 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getEdctCrsList(Map<String, ?> pmParam) throws Exception {
        return selectList("EdctCrsMap.getEdctCrsList", pmParam);
    }

    /**
     * 교육평가 정보를 등록한다.
     *
     * @pmParam pmParam 교육평가 등록
     * @throws Exception
     */
    public int insertEdctCrs(Map<String, ?> pmParam) throws Exception {
        return insert("EdctCrsMap.insertEdctCrs", pmParam);
    }

    /**
     * 교육평가 정보를 복사한다.
     *
     * @pmParam pmParam 교육평가 복사
     * @throws Exception
     */
    public int copyEdctCrs(Map<String, ?> pmParam) throws Exception {
        return insert("EdctCrsMap.copyEdctCrs", pmParam);
    }

    /**
     * 교육평가 정보를 수정한다.
     *
     * @pmParam pmParam FAQ 정보
     * @throws Exception
     */
    public int updateEdctCrs(Map<String, Object> pmParam) throws Exception {
        return update("EdctCrsMap.updateEdctCrs", pmParam);
    }

    /**
     * 교육평가를 검색한다.
     *
     * @pmParam edctcrsid 교육평가 ID
     * @return 교육평가 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getEdctCrs(Map<String, ?> pmParam) throws Exception {
        return selectOne("EdctCrsMap.getEdctCrsList", pmParam);
    }

    /**
     * 교육평가를 삭제한다.
     *
     * @pmParam pmParam 교육평가
     * @throws Exception
     */
    public int deleteEdctCrs(Map<String, Object> pmParam) throws Exception {
        return delete("EdctCrsMap.deleteEdctCrs", pmParam);
    }


    /**
     * 해당 교육대상자가 교육시간을 지정받았는지 확인한다.
     *
     * @pmParam pmParam 교육시간 확인
     * @throws Exception
     */
    public List<Map<String, Object>> searchEdctTrprList(Map<String, ?> pmParam) throws Exception {
        return selectList("EdctTrprMap.searchEdctTrprList", pmParam);
    }

    /**
     * 월간 교육훈련 결과(상담사별) 정보의 검색 수를 반환한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 월간교육훈련 결과 정보의 검색 건수
     * @throws Exception
     */
    public int eduresultCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("EdctCrsMap.eduresultCount", pmParam);
    }
    /**
     * 월간 교육훈련 결과(상담사별) 정보를 검색 한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 월간교육훈련 결과 정보
     * @throws Exception
     */
    public List<Map<String, Object>> edctCrsResult(Map<String, ?> pmParam) throws Exception {
        return selectList("EdctCrsMap.edctCrsResult", pmParam);
    }

    /**
     * 사용자 교육 정보의 검색 수를 반환한다. -  MYPAGE
     *
     * @pmParam pmParam 검색 조건
     * @return 교육 정보의 검색 수
     * @throws Exception
     */
    public int getMyPageEdctCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("EdctCrsMap.getMyPageEdctCount", pmParam);
    }

    /**
     * 사용자 교육 정보(목록)를 검색한다. -  MYPAGE
     *
     * @pmParam pmParam 검색 조건
     * @return 시험 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getMyPageEdctList(Map<String, ?> pmParam) throws Exception {
        return selectList("EdctCrsMap.getMyPageEdctList", pmParam);
    }
}
