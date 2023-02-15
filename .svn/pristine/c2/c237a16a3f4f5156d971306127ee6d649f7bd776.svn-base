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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.exa.service.ExamBasiService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 시험지를 관리한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/15
 * @프로그램ID Exam
 */
@Service
public class ExamBasiServiceImpl extends EgovAbstractServiceImpl implements ExamBasiService {

    @Resource
    public ExamBasiDAO examBasiDAO;

    /**
     * 시험 정보의 검색 수를 반환한다.
     *
     * @param param 검색 조건
     * @return 시험 정보의 검색 수
     * @throws Exception
     */
    public int getExamBasiCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) examBasiDAO.getExamBasiCount(pmParam);
    }

    /**
     * 시험 정보(목록)를 검색한다.
     *
     * @param param 검색 조건
     * @return 시험 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getExamBasiList(Map<String, ?> pmParam) throws Exception {
        return examBasiDAO.getExamBasiList(pmParam);
    }

    /**
     * 시험 정보를 등록한다.
     *
     * @param param 시험 정보
     * @throws Exception
     */
    public String insertExamBasi(Map<String, Object> pmParam) throws Exception {
        String sKey = "";

        int nResult = examBasiDAO.insertExamBasi(pmParam);
        if (nResult > 0) {
            sKey = (String) pmParam.get("exam_id");
        }
        return sKey;
    }

    /**
     * 시험 정보(1건)를 검색한다.
     *
     * @param paramHash 검색 조건
     * @return 시험 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getExamBasi(String psId) throws Exception {
        Map<String, String> mParam = new HashMap<String, String>();
        mParam.put("exam_id", psId);

        return examBasiDAO.getExamBasi(mParam);
    }

    /**
     * 시험 정보를 수정한다.
     *
     * @param param 시험 정보
     * @throws Exception
     */
    public int updateExamBasi(Map<String, Object> pmParam) throws Exception {
        int nResult = 0;
        examBasiDAO.updateExamBasi(pmParam);

        // 종료인 경우 시험대상자 시험순위 저장
        if ("40".equals(pmParam.get("exam_prgr_stat_cd_change"))) {
            nResult += examBasiDAO.updateExamTrprExamRanking(pmParam);
        }
        return nResult;
    }

    /**
     * 시험 정보를 삭제한다.
     *
     * @param param 시험 정보
     * @throws Exception
     */
    public int deleteExamBasi(Map<String, Object> pmParam) throws Exception {
        int nResult = 0;
        // 시험대상자 삭제
        examBasiDAO.deleteExamTrpr(pmParam);
        // 시험 삭제
        nResult += examBasiDAO.deleteExamBasi(pmParam);

        return nResult;
    }

    /**
     * 사용자 시험 정보의 검색 수를 반환한다.
     *
     * @param param 검색 조건
     * @return 시험 정보의 검색 수
     * @throws Exception
     */
    public int getExamBasiMyPageCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) examBasiDAO.getExamBasiMyPageCount(pmParam);
    }

    /**
     * 사용자 시험 정보(목록)를 검색한다.
     *
     * @param param 검색 조건
     * @return 시험 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getExamBasiMyPageList(Map<String, ?> pmParam) throws Exception {
        return examBasiDAO.getExamBasiMyPageList(pmParam);
    }

    /**
     * 시험 결과 정보(목록)를 검색한다.
     *
     * @param param 검색 조건
     * @return 시험 결과 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getExamResultList(Map<String, ?> pmParam) throws Exception {
        return examBasiDAO.getExamResultList(pmParam);
    }

    /**
     * 시험 결과 정보(점수별)를 검색한다.
     *
     * @param param 검색 조건
     * @return 시험 결과 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getExamResultScoreList(Map<String, ?> pmParam) throws Exception {
        return examBasiDAO.getExamResultScoreList(pmParam);
    }

    /**
     * 시험 정보의 검색 수를 반환한다.
     *
     * @param param 검색 조건
     * @return 시험 정보의 검색 수
     * @throws Exception
     */
    public int getSearchExamBasiCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) examBasiDAO.getSearchExamBasiCount(pmParam);
    }

    /**
     * 시험 정보(목록)를 검색한다.
     *
     * @param param 검색 조건
     * @return 시험 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getSearchExamBasiList(Map<String, ?> pmParam) throws Exception {
        return examBasiDAO.getSearchExamBasiList(pmParam);
    }

}
