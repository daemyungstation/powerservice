/*
 * (@)# ExamPrbmDAO.java
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
 * @프로그램ID ExamPrbm
 */

@Repository
public class ExamPrbmDAO extends EgovAbstractMapper {

    /**
     * 시험문제 정보의 검색 수를 반환한다.
     *
     * @param param 검색 조건
     * @return 시험문제 정보의 검색 수
     * @throws Exception
     */
    public int getExamPrbmCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("ExamPrbmMap.getExamPrbmCount", pmParam);
    }

    /**
     * 시험문제 정보(목록)를 검색한다.
     *
     * @param param 검색 조건
     * @return 시험문제 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getExamPrbmList(Map<String, ?> pmParam) throws Exception {
        return selectList("ExamPrbmMap.getExamPrbmList", pmParam);
    }

    /**
     * 시험문제 정보를 저장한다.
     *
     * @param param 시험문제 정보
     * @throws Exception
     */
    public int insertExamPrbm(Map<String, Object> pmParam) throws Exception {
        return insert("ExamPrbmMap.insertExamPrbm", pmParam);
    }

    /**
     * 시험문제 정보를 저장한다.
     *
     * @param param 시험문제 정보
     * @throws Exception
     */
    public int updateExamPrbm(Map<String, Object> pmParam) throws Exception {
        return update("ExamPrbmMap.updateExamPrbm", pmParam);
    }

    /**
     * 시험문제 정보를 삭제한다.
     *
     * @param param 시험문제 정보
     * @throws Exception
     */
    public int deleteExamPrbmItem(Map<String, Object> pmParam) throws Exception {
        return delete("ExamPrbmItemMap.deleteExamPrbmItem", pmParam);
    }

    /**
     * 시험문제 정보를 수정한다.
     *
     * @param param 시험문제 정보
     * @throws Exception
     */
    public int insertExamPrbmItem(Map<String, Object> pmParam) throws Exception {
        return insert("ExamPrbmItemMap.insertExamPrbmItem", pmParam);
    }

    /**
     * 시험문제 정보를 수정한다.
     *
     * @param param 시험문제 정보
     * @throws Exception
     */
    public int updateExamPrbmItem(Map<String, Object> pmParam) throws Exception {
        return update("ExamPrbmItemMap.updateExamPrbmItem", pmParam);
    }

    /**
     * 시험문제 정보(1건)를 검색한다.
     *
     * @param paramHash 검색 조건
     * @return 시험문제 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getExamPrbm(Map<String, ?> pmParam) throws Exception {
        return selectOne("ExamPrbmMap.getExamPrbmList", pmParam);
    }

    /**
     * 시험문제 정보를 삭제한다.
     *
     * @param param 시험문제 정보
     * @throws Exception
     */
    public int deleteExamPrbm(Map<String, Object> pmParam) throws Exception {
        return delete("ExamPrbmMap.deleteExamPrbm", pmParam);
    }

    /**
     * 시험문제 응시 정보(목록)를 검색한다.
     *
     * @param param 검색 조건
     * @return 시험문제 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getExamPrbmAnsrList(Map<String, ?> pmParam) throws Exception {
        return selectList("ExamPrbmMap.getExamPrbmAnsrList", pmParam);
    }

    /**
     * 시험문제 채점 정보의 검색 수를 반환한다.
     *
     * @param param 검색 조건
     * @return 시험문제 채점 정보의 검색 수
     * @throws Exception
     */
    public int getExamPrbmMarkingCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("ExamPrbmMap.getExamPrbmMarkingCount", pmParam);
    }

    /**
     * 시험문제 채점 정보(목록)를 검색한다.
     *
     * @param param 검색 조건
     * @return 시험문제 채점 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getExamPrbmMarkingList(Map<String, ?> pmParam) throws Exception {
        return selectList("ExamPrbmMap.getExamPrbmMarkingList", pmParam);
    }

    /**
     * 시험문제 분야별 결과 정보의 검색 수를 반환한다.
     *
     * @param param 검색 조건
     * @return 시험문제 분야별 결과 정보의 검색 수
     * @throws Exception
     */
    public int getExamPrbmFieldResultCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("ExamPrbmMap.getExamPrbmFieldResultCount", pmParam);
    }

    /**
     * 시험문제 분야별 결과 정보(목록)를 검색한다.
     *
     * @param param 검색 조건
     * @return 시험문제 분야별 결과 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getExamPrbmFieldResultList(Map<String, ?> pmParam) throws Exception {
        return selectList("ExamPrbmMap.getExamPrbmFieldResultList", pmParam);
    }

    /**
     * 시험문제 결과 정보의 검색 수를 반환한다.
     *
     * @param param 검색 조건
     * @return 시험문제 결과 정보의 검색 수
     * @throws Exception
     */
    public int getExamPrbmResultCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("ExamPrbmMap.getExamPrbmResultCount", pmParam);
    }

    /**
     * 시험문제 결과 정보(목록)를 검색한다.
     *
     * @param param 검색 조건
     * @return 시험문제 결과 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getExamPrbmResultList(Map<String, ?> pmParam) throws Exception {
        return selectList("ExamPrbmMap.getExamPrbmResultList", pmParam);
    }

    /**
     * 시험문제 정보를 수정한다 (순서수정)
     *
     * @param pmParam 시험문제항목 정보
     * @throws Exception
     */
    public int updateExamPrbmExpeSqnc(Map<String, ?> pmParam) throws Exception {
        return update("ExamPrbmMap.updateExamPrbmExpeSqnc", pmParam);
    }
}
