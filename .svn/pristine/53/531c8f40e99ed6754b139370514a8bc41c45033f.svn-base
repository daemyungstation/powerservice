/*
 * (@)# EmshService.java
 *
 * @author 김현경
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

import powerservice.business.exa.service.ExamPrbmService;
import powerservice.core.util.ParamUtils;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;

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
public class ExamPrbmServiceImpl extends EgovAbstractServiceImpl implements ExamPrbmService {

    @Resource
    public ExamPrbmDAO ExamPrbmDAO;

    /**
     * 시험문제 정보의 검색 수를 반환한다.
     *
     * @param param 검색 조건
     * @return 시험문제 정보의 검색 수
     * @throws Exception
     */
    public int getExamPrbmCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) ExamPrbmDAO.getExamPrbmCount(pmParam);
    }

    /**
     * 시험문제 정보(목록)를 검색한다.
     *
     * @param param 검색 조건
     * @return 시험문제 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getExamPrbmList(Map<String, ?> pmParam) throws Exception {
        return ExamPrbmDAO.getExamPrbmList(pmParam);
    }

    /**
     * 시험문제 정보를 저장한다.
     *
     * @param param 시험문제 정보
     * @throws Exception
     */
    public String saveExamPrbm(Map <String, DataSetMap> mapInDs) throws Exception {
        Map<String, Object> pmParam = new HashMap<String, Object>();

        DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
        if (srchInDs != null && srchInDs.size() > 0) {
            pmParam = srchInDs.get(0);
        }

        ParamUtils.addSaveParam(pmParam);
        String sExamPrbmId = StringUtils.defaultString((String) pmParam.get("exam_prbm_id"));
        String sExamPrbmTypCd = StringUtils.defaultString((String) pmParam.get("exam_prbm_typ_cd"));

        // 시험문제 저장
        if ("".equals(sExamPrbmId)) {
            int nResult = ExamPrbmDAO.insertExamPrbm(pmParam);
            if (nResult > 0) {
                sExamPrbmId = (String) pmParam.get("exam_prbm_id");
            }
        } else {
            ExamPrbmDAO.updateExamPrbm(pmParam);
        }

        // 시험문제항목 저장
        Map<String, Object> pmParam2 = new HashMap<String, Object>();

        DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input2");
        ParamUtils.addSaveParam(pmParam2);

        if (listInDs != null) {
            for(int i =0; listInDs.size() > i; i++){
                pmParam2 = listInDs.get(i);
                String sExamPrbmItemId = StringUtils.defaultString((String) pmParam2.get("exam_prbm_item_id"));

                pmParam2.put("exam_prbm_id", sExamPrbmId);
                pmParam2.put("emsh_id", pmParam.get("emsh_id"));
                pmParam2.put("rgsr_id", pmParam.get("rgsr_id"));
                pmParam2.put("amnd_id", pmParam.get("amnd_id"));
                pmParam2.put("cntr_cd", pmParam.get("cntr_cd"));

                // 주관식 시험문제항목아이디가 있는 경우 나머지 시험문제항목 삭제
                if ("30".equals(sExamPrbmTypCd) && !"".equals(sExamPrbmItemId)) {
                    pmParam2.remove("exam_prbm_item_id");

                    pmParam2.put("exam_prbm_item_idexcept", sExamPrbmItemId);
                    ExamPrbmDAO.deleteExamPrbmItem(pmParam2);
                    pmParam2.put("exam_prbm_item_id", sExamPrbmItemId);
                }

                if ("".equals(sExamPrbmItemId)) {
                    ExamPrbmDAO.insertExamPrbmItem(pmParam2);
                } else {
                    ExamPrbmDAO.updateExamPrbmItem(pmParam2);
                }

            }
        }

        return sExamPrbmId;
    }
    /*
    public String saveExamPrbm(Map<String, Object> pmParam) throws Exception {
        String sExamPrbmId = StringUtils.defaultString((String) pmParam.get("exam_prbm_id"));
        String sExamPrbmTypCd = StringUtils.defaultString((String) pmParam.get("exam_prbm_typ_cd"));

        // 시험문제 저장
        if ("".equals(sExamPrbmId)) {
            int nResult = ExamPrbmDAO.insertExamPrbm(pmParam);
            if (nResult > 0) {
                sExamPrbmId = (String) pmParam.get("exam_prbm_id");
            }
        } else {
            ExamPrbmDAO.updateExamPrbm(pmParam);
        }

        // 시험문제항목 저장
        List<Map<String, Object>> mdataExamPrbmItemList = (List<Map<String, Object>>) pmParam.get("exam_prbm_item_list");
        if (mdataExamPrbmItemList != null) {
            for (Map<String, Object> mRow : mdataExamPrbmItemList) {
                String sExamPrbmItemId = StringUtils.defaultString((String) mRow.get("exam_prbm_item_id"));

                mRow.put("exam_prbm_id", sExamPrbmId);
                mRow.put("emsh_id", pmParam.get("emsh_id"));
                mRow.put("rgsr_id", pmParam.get("rgsr_id"));
                mRow.put("amnd_id", pmParam.get("amnd_id"));
                mRow.put("cntr_cd", pmParam.get("cntr_cd"));

                // 주관식 시험문제항목아이디가 있는 경우 나머지 시험문제항목 삭제
                if ("30".equals(sExamPrbmTypCd) && !"".equals(sExamPrbmItemId)) {
                    mRow.remove("exam_prbm_item_id");

                    mRow.put("exam_prbm_item_idexcept", sExamPrbmItemId);
                    ExamPrbmDAO.deleteExamPrbmItem(mRow);
                    mRow.put("exam_prbm_item_id", sExamPrbmItemId);
                }

                if ("".equals(sExamPrbmItemId)) {
                    ExamPrbmDAO.insertExamPrbmItem(mRow);
                } else {
                    ExamPrbmDAO.updateExamPrbmItem(mRow);
                }
            }
        }

        return sExamPrbmId;
    }*/

    /**
     * 시험문제 정보(1건)를 검색한다.
     *
     * @param paramHash 검색 조건
     * @return 시험문제 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getExamPrbm(String psId) throws Exception {
        Map<String, String> mParam = new HashMap<String, String>();
        mParam.put("exam_prbm_id", psId);

        return ExamPrbmDAO.getExamPrbm(mParam);
    }

    /**
     * 시험문제 정보를 삭제한다.
     *
     * @param param 시험문제 정보
     * @throws Exception
     */
    public void deleteExamPrbm(Map<String, Object> pmParam) throws Exception {
        // 시험문제항목 삭제
        ExamPrbmDAO.deleteExamPrbmItem(pmParam);
        // 시험문제 삭제
        ExamPrbmDAO.deleteExamPrbm(pmParam);
    }

    /**
     * 시험문제 응시 정보(목록)를 검색한다.
     *
     * @param param 검색 조건
     * @return 시험문제 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getExamPrbmAnsrList(Map<String, ?> pmParam) throws Exception {
        return ExamPrbmDAO.getExamPrbmAnsrList(pmParam);
    }

    /**
     * 시험문제 채점 정보의 검색 수를 반환한다.
     *
     * @param param 검색 조건
     * @return 시험문제 채점 정보의 검색 수
     * @throws Exception
     */
    public int getExamPrbmMarkingCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) ExamPrbmDAO.getExamPrbmMarkingCount(pmParam);
    }

    /**
     * 시험문제 채점 정보(목록)를 검색한다.
     *
     * @param param 검색 조건
     * @return 시험문제 채점 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getExamPrbmMarkingList(Map<String, ?> pmParam) throws Exception {
        return ExamPrbmDAO.getExamPrbmMarkingList(pmParam);
    }

    /**
     * 시험문제 분야별 결과 정보의 검색 수를 반환한다.
     *
     * @param param 검색 조건
     * @return 시험문제 분야별 결과 정보의 검색 수
     * @throws Exception
     */
    public int getExamPrbmFieldResultCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) ExamPrbmDAO.getExamPrbmFieldResultCount(pmParam);
    }

    /**
     * 시험문제 분야별 결과 정보(목록)를 검색한다.
     *
     * @param param 검색 조건
     * @return 시험문제 분야별 결과 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getExamPrbmFieldResultList(Map<String, ?> pmParam) throws Exception {
        return ExamPrbmDAO.getExamPrbmFieldResultList(pmParam);
    }

    /**
     * 시험문제 결과 정보의 검색 수를 반환한다.
     *
     * @param param 검색 조건
     * @return 시험문제 결과 정보의 검색 수
     * @throws Exception
     */
    public int getExamPrbmResultCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) ExamPrbmDAO.getExamPrbmResultCount(pmParam);
    }

    /**
     * 시험문제 결과 정보(목록)를 검색한다.
     *
     * @param param 검색 조건
     * @return 시험문제 결과 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getExamPrbmResultList(Map<String, ?> pmParam) throws Exception {
        return ExamPrbmDAO.getExamPrbmResultList(pmParam);
    }

    /**
     * 시험문제 순서 정보를 저장한다.
     *
     * @param param 시험문제 순서 정보
     * @throws Exception
     */
    public int updateExamPrbmExpeSqnc(List<Map<String, Object>> pmdataModels, Map<String, Object> pmParam) throws Exception {
        int nResult = 0;

        for (Map<String, Object> mModel : pmdataModels) {
            String sExamPrbmId = StringUtils.defaultString((String) mModel.get("exam_prbm_id"));
            if (!"".equals(sExamPrbmId)) {
                pmParam.put("expe_sqnc", mModel.get("expe_sqnc"));
                pmParam.put("exam_prbm_id", mModel.get("exam_prbm_id"));
                pmParam.put("emsh_id", mModel.get("emsh_id"));

                nResult += ExamPrbmDAO.updateExamPrbmExpeSqnc(pmParam);		//updateExamPrbm(pmParam);
            }
        }

        return nResult;
    }
}
