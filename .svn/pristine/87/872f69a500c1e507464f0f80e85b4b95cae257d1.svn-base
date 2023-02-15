/*
 * (@)# ExamBasiDAO.java
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
public class ExamBasiDAO extends EgovAbstractMapper {

    /**
     * 시험 정보의 검색 수를 반환한다.
     *
     * @param param 검색 조건
     * @return 시험 정보의 검색 수
     * @throws Exception
     */
    public int getExamBasiCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("ExamBasiMap.getExamBasiCount", pmParam);
    }

    /**
     * 시험 정보(목록)를 검색한다.
     *
     * @param param 검색 조건
     * @return 시험 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getExamBasiList(Map<String, ?> pmParam) throws Exception {
        return selectList("ExamBasiMap.getExamBasiList", pmParam);
    }

    /**
     * 시험 정보를 등록한다.
     *
     * @param param 시험 정보
     * @throws Exception
     */
    public int insertExamBasi(Map<String, Object> pmParam) throws Exception {
        return insert("ExamBasiMap.insertExamBasi", pmParam);
    }

    /**
     * 시험 정보(1건)를 검색한다.
     *
     * @param paramHash 검색 조건
     * @return 시험 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getExamBasi(Map<String, ?> pmParam) throws Exception {
        return selectOne("ExamBasiMap.getExamBasiList", pmParam);
    }

    /**
     * 시험 정보를 수정한다.
     *
     * @param param 시험 정보
     * @throws Exception
     */
    public int updateExamBasi(Map<String, Object> pmParam) throws Exception {
        return update("ExamBasiMap.updateExamBasi", pmParam);
    }

    /**
     * 시험 정보를 수정한다.
     *
     * @param param 시험 정보
     * @throws Exception
     */
    public int updateExamTrprExamRanking(Map<String, Object> pmParam) throws Exception {
        return update("ExamTrprMap.updateExamTrprExamRanking", pmParam);
    }

    /**
     * 시험 정보를 삭제한다.
     *
     * @param param 시험 정보
     * @throws Exception
     */
    public int deleteExamTrpr(Map<String, Object> pmParam) throws Exception {
        return delete("ExamTrprMap.deleteExamTrpr", pmParam);
    }

    /**
     * 시험 정보를 삭제한다.
     *
     * @param param 시험 정보
     * @throws Exception
     */
    public int deleteExamBasi(Map<String, Object> pmParam) throws Exception {
        return delete("ExamBasiMap.deleteExamBasi", pmParam);
    }
    /**
     * 사용자 시험 정보의 검색 수를 반환한다.
     *
     * @param param 검색 조건
     * @return 시험 정보의 검색 수
     * @throws Exception
     */
    public int getExamBasiMyPageCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("ExamBasiMap.getExamBasiMyPageCount", pmParam);
    }

    /**
     * 사용자 시험 정보(목록)를 검색한다.
     *
     * @param param 검색 조건
     * @return 시험 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getExamBasiMyPageList(Map<String, ?> pmParam) throws Exception {
        return selectList("ExamBasiMap.getExamBasiMyPageList", pmParam);
    }

    /**
     * 시험 결과 정보(목록)를 검색한다.
     *
     * @param param 검색 조건
     * @return 시험 결과 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getExamResultList(Map<String, ?> pmParam) throws Exception {
        return selectList("ExamBasiMap.getExamResultList", pmParam);
    }

    /**
     * 시험 결과 정보(점수별)를 검색한다.
     *
     * @param param 검색 조건
     * @return 시험 결과 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getExamResultScoreList(Map<String, ?> pmParam) throws Exception {
        return selectList("ExamBasiMap.getExamResultScoreList", pmParam);
    }

    /**
     * 시험 정보의 검색 수를 반환한다.
     *
     * @param param 검색 조건
     * @return 시험 정보의 검색 수
     * @throws Exception
     */
    public int getSearchExamBasiCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("ExamBasiMap.getSearchExamBasiCount", pmParam);
    }

    /**
     * 시험 정보(목록)를 검색한다.
     *
     * @param param 검색 조건
     * @return 시험 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getSearchExamBasiList(Map<String, ?> pmParam) throws Exception {
        return selectList("ExamBasiMap.getSearchExamBasiList", pmParam);
    }
}
