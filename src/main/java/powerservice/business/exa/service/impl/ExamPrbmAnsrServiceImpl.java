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

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import powerservice.business.exa.service.ExamPrbmAnsrService;
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
public class ExamPrbmAnsrServiceImpl extends EgovAbstractServiceImpl implements ExamPrbmAnsrService {

    @Resource
    public ExamPrbmAnsrDAO examPrbmAnsrDAO;

    /**
     * 시험답안 정보의 검색 수를 반환한다.
     *
     * @param param 검색 조건
     * @return 시험답안 정보의 검색 수
     * @throws Exception
     */
    public int getExamPrbmAnsrCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) examPrbmAnsrDAO.getExamPrbmAnsrCount(pmParam);
    }

    /**
     * 시험답안 정보(목록)를 검색한다.
     *
     * @param param 검색 조건
     * @return 시험답안 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getExamPrbmAnsrList(Map<String, ?> pmParam) throws Exception {
        return examPrbmAnsrDAO.getExamPrbmAnsrList(pmParam);
    }

    /**
     * 시험답안 정보를 등록한다.
     *
     * @param param 시험답안 정보
     * @throws Exception
     */
    public String insertExamPrbmAnsr(Map<String, Object> pmParam) throws Exception {
        String sKey = "";

        int nResult = examPrbmAnsrDAO.insertExamPrbmAnsr(pmParam);
        if (nResult > 0) {
            sKey = (String) pmParam.get("exam_ansr_id");
        }
        return sKey;
    }

    /**
     * 시험답안 리스트 정보를 등록한다.
     *
     * @param param 시험답안 정보
     * @throws Exception
     */
    public int insertExamPrbmAnsrList(Map<String, Object> pmParam) throws Exception {
        int nResult = 0;

        pmParam.remove("ansr_acqr_scr_vl");

        // 시험답안 삭제
        examPrbmAnsrDAO.deleteExamPrbmAnsr(pmParam);

       // 시험답안 등록
       if (!"".equals(pmParam.get("prbm_ansr_cntn")) && pmParam.get("prbm_ansr_cntn") != null) {
           nResult += examPrbmAnsrDAO.insertExamPrbmAnsr(pmParam);
       }

        return nResult;
    }
/*
    public int insertExamPrbmAnsrList(Map<String, Object> pmParam) throws Exception {
        int nResult = 0;
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> mdataExamPrbmAnsr = (List<Map<String, Object>>) pmParam.get("exam_prbm_ansr_list");
        if (mdataExamPrbmAnsr == null) {
            return nResult;
        }

        pmParam.remove("ansr_acqr_scr_vl");
        for (Map<String, Object> mExamPrbmAnsr : mdataExamPrbmAnsr) {
            pmParam.put("exam_prbm_id", mExamPrbmAnsr.get("exam_prbm_id"));
            pmParam.put("prbm_ansr_cntn", mExamPrbmAnsr.get("prbm_ansr_cntn"));

            // 시험답안 삭제
            examPrbmAnsrDAO.deleteExamPrbmAnsr(pmParam);

            // 시험답안 등록
            if (!"".equals(mExamPrbmAnsr.get("prbm_ansr_cntn")) && mExamPrbmAnsr.get("prbm_ansr_cntn") != null) {
                nResult += examPrbmAnsrDAO.insertExamPrbmAnsr(pmParam);
            }
        }
        return nResult;
    }
*/
    /**
     * 시험답안 정보(1건)를 검색한다.
     *
     * @param paramHash 검색 조건
     * @return 시험답안 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getExamPrbmAnsr(String psId) throws Exception {
        Map<String, String> mParam = new HashMap<String, String>();
        mParam.put("exam_ansr_id", psId);

        return examPrbmAnsrDAO.getExamPrbmAnsr(mParam);
    }

    /**
     * 시험답안 정보를 수정한다.
     *
     * @param param 시험답안 정보
     * @throws Exception
     */
    public int updateExamPrbmAnsr(Map<String, Object> pmParam) throws Exception {
        return examPrbmAnsrDAO.updateExamPrbmAnsr(pmParam);
    }

    /**
     * 시험답안 정보를 삭제한다.
     *
     * @param param 시험답안 정보
     * @throws Exception
     */
    public int deleteExamPrbmAnsr(Map<String, Object> pmParam) throws Exception {
        return examPrbmAnsrDAO.deleteExamPrbmAnsr(pmParam);
    }

    /**
     * 시험답안 채점 정보를 저장한다.
     *
     * @param param 시험답안 채점 정보
     * @throws Exception
     */
    public int saveExamPrbmAnsrMarkingList(Map<String, Object> pmParam) throws Exception {
        int nResult = 0;

        // 시험대상자 최종점수 저장
        nResult += examPrbmAnsrDAO.updateExamTrprTotalScore(pmParam);

        // 시험대상자 시험순위 저장
        pmParam.put("mode", "marking");
        nResult += examPrbmAnsrDAO.updateExamTrprExamRanking(pmParam);

        return nResult;
    }
    /*
    public int saveExamPrbmAnsrMarkingList(Map<String, Object> pmParam) throws Exception {
        int nResult = 0;
        // 시험답안 채점 저장
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> mdataExamMarking = (List<Map<String, Object>>) pmParam.get("exammarkinglist");
        if (mdataExamMarking != null) {
            for (Map<String, Object> mExamMarking : mdataExamMarking) {
                pmParam.put("emsh_id", mExamMarking.get("emsh_id"));
                pmParam.put("exam_prbm_id", mExamMarking.get("exam_prbm_id"));
                pmParam.put("exam_ansr_id", mExamMarking.get("exam_ansr_id"));
                pmParam.put("ansr_acqr_scr_vl", mExamMarking.get("ansr_acqr_scr_vl"));

                String sExamAnsrId = StringUtils.defaultString((String) mExamMarking.get("exam_ansr_id"));
                if ("".equals(sExamAnsrId)) {
                    examPrbmAnsrDAO.insertExamPrbmAnsr(pmParam);
                } else {
                    examPrbmAnsrDAO.updateExamPrbmAnsr(pmParam);
                }
            }

            // 시험대상자 최종점수 저장
            nResult += examPrbmAnsrDAO.updateExamTrprTotalScore(pmParam);

            // 시험대상자 시험순위 저장
            pmParam.put("mode", "marking");
            nResult += examPrbmAnsrDAO.updateExamTrprExamRanking(pmParam);
        }

        return nResult;
    }
    */
}
