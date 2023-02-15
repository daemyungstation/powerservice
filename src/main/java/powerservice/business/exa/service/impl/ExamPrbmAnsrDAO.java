/*
 * (@)# ExamAnswerDAO.java
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
 * 시험지를 관리한다.
 * Copyright (c) 2014 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2014/04/01
 * @프로그램ID ExamAnswer
 */

@Repository
public class ExamPrbmAnsrDAO extends EgovAbstractMapper {

    /**
     * 시험답안 정보의 검색 수를 반환한다.
     *
     * @param param 검색 조건
     * @return 시험답안 정보의 검색 수
     * @throws Exception
     */
    public int getExamPrbmAnsrCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("ExamPrbmAnsrMap.getExamPrbmAnsrCount", pmParam);
    }

    /**
     * 시험답안 정보(목록)를 검색한다.
     *
     * @param param 검색 조건
     * @return 시험답안 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getExamPrbmAnsrList(Map<String, ?> pmParam) throws Exception {
        return selectList("ExamPrbmAnsrMap.getExamPrbmAnsrList", pmParam);
    }

    /**
     * 시험답안 정보를 등록한다.
     *
     * @param param 시험답안 정보
     * @throws Exception
     */
    public int insertExamPrbmAnsr(Map<String, Object> pmParam) throws Exception {
         return insert("ExamPrbmAnsrMap.insertExamPrbmAnsr", pmParam);
    }

    /**
     * 시험답안 리스트 정보를 등록한다.
     *
     * @param param 시험답안 정보
     * @throws Exception
     */
    public int insertExamPrbmAnsrList(Map<String, Object> pmParam) throws Exception {
          return delete("ExamPrbmAnsrMap.deleteExamPrbmAnsr", pmParam);
    }

    /**
     * 시험답안 정보(1건)를 검색한다.
     *
     * @param paramHash 검색 조건
     * @return 시험답안 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getExamPrbmAnsr(Map<String, ?> pmParam) throws Exception {
        return selectOne("ExamPrbmAnsrMap.getExamPrbmAnsrList", pmParam);
    }

    /**
     * 시험답안 정보를 수정한다.
     *
     * @param param 시험답안 정보
     * @throws Exception
     */
    public int updateExamPrbmAnsr(Map<String, Object> pmParam) throws Exception {
        return update("ExamPrbmAnsrMap.updateExamPrbmAnsr", pmParam);
    }

    /**
     * 시험답안 정보를 삭제한다.
     *
     * @param param 시험답안 정보
     * @throws Exception
     */
    public int deleteExamPrbmAnsr(Map<String, Object> pmParam) throws Exception {
        return delete("ExamPrbmAnsrMap.deleteExamPrbmAnsr", pmParam);
    }

    /**
     * 시험답안 채점 정보를 저장한다.
     *
     * @param param 시험답안 채점 정보
     * @throws Exception
     */
    public int updateExamTrprTotalScore(Map<String, Object> pmParam) throws Exception {
        return update("ExamTrprMap.updateExamTrprTotalScore", pmParam);
    }

    /**
     * 시험답안 채점 정보를 저장한다.
     *
     * @param param 시험답안 채점 정보
     * @throws Exception
     */
    public int updateExamTrprExamRanking(Map<String, Object> pmParam) throws Exception {
        return update("ExamTrprMap.updateExamTrprExamRanking", pmParam);
    }

}
