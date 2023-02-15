/*
 * (@)# ExamTrprDAO.java
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/15
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

package powerservice.business.exa.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 시험을 관리한다.
 * Copyright (c) 2014 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2014/04/01
 * @프로그램ID Exam
 */

@Repository
public class ExamTrprDAO extends EgovAbstractMapper {

    /**
     * 시험대상자 정보의 검색 수를 반환한다.
     *
     * @param param 검색 조건
     * @return 시험대상자 정보의 검색 수
     * @throws Exception
     */
    public int getExamTrprCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("ExamTrprMap.getExamTrprCount", pmParam);
    }

    /**
     * 시험대상자 정보(목록)를 검색한다.
     *
     * @param param 검색 조건
     * @return 시험대상자 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getExamTrprList(Map<String, ?> pmParam) throws Exception {
        return selectList("ExamTrprMap.getExamTrprList", pmParam);
    }

    /**
     * 시험대상자 정보를 등록한다.
     *
     * @param param 시험대상자 정보
     * @throws Exception
     */
    public int insertExamTrpr(Map<String, Object> pmParam) throws Exception {
        return insert("ExamTrprMap.insertExamTrpr", pmParam);
    }

    /**
     * 시험대상자 정보를 수정한다.
     *
     * @param param 시험대상자 정보
     * @throws Exception
     */
    public int updateExamTrpr(Map<String, Object> pmParam) throws Exception {
        return update("ExamTrprMap.updateExamTrpr", pmParam);
    }

    /**
     * 시험대상자 정보를 삭제한다.
     *
     * @param param 시험대상자 정보
     * @throws Exception
     */
    public int deleteExamTrpr(Map<String, Object> pmParam) throws Exception {
        return delete("ExamTrprMap.deleteExamTrpr", pmParam);
    }

    /**
     * 상담사 정보의 검색 수를 반환한다.
     *
     * @param param 검색 조건
     * @return 상담사 정보의 검색 수
     * @throws Exception
     */
    public int getUserCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("ExamTrprMap.getUserCount", pmParam);
    }

    /**
     * 상담사 정보(목록)를 검색한다.
     *
     * @param param 검색 조건
     * @return 상담사 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getUserList(Map<String, ?> pmParam) throws Exception {
        return selectList("ExamTrprMap.getUserList", pmParam);
    }

    /**
     * 시험대상자 채점 정보의 검색 수를 반환한다.
     *
     * @param param 검색 조건
     * @return 시험대상자 채점 정보의 검색 수
     * @throws Exception
     */
    public int getExamTrprMarkingCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("ExamTrprMap.getExamTrprMarkingCount", pmParam);
    }

    /**
     * 시험대상자 채점 정보(목록)를 검색한다.
     *
     * @param param 검색 조건
     * @return 시험대상자 채점 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getExamTrprMarkingList(Map<String, ?> pmParam) throws Exception {
        return selectList("ExamTrprMap.getExamTrprMarkingList", pmParam);
    }

    /**
     * 시험대상자 결과 정보의 검색 수를 반환한다.
     *
     * @param param 검색 조건
     * @return 시험대상자 결과 정보의 검색 수
     * @throws Exception
     */
    public int getExamTrprResultCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("ExamTrprMap.getExamTrprResultCount", pmParam);
    }

    /**
     * 시험대상자 결과 정보(목록)를 검색한다.
     *
     * @param param 검색 조건
     * @return 시험대상자 결과 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getExamTrprResultList(Map<String, ?> pmParam) throws Exception {
        return selectList("ExamTrprMap.getExamTrprResultList", pmParam);
    }
}
