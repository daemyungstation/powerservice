/*
 * (@)# ExamPrbmItemServiceImpl.java
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

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import powerservice.business.exa.service.ExamPrbmItemService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 시험지를 관리한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/15
 * @프로그램ID ExamPrbmItem
 */
@Service
public class ExamPrbmItemServiceImpl extends EgovAbstractServiceImpl implements ExamPrbmItemService {

    @Resource
    public ExamPrbmItemDAO examPrbmItemDAO;

    /**
     * 시험문제항목 정보의 검색 수를 반환한다.
     *
     * @param param 검색 조건
     * @return 시험문제항목 정보의 검색 수
     * @throws Exception
     */
    public int getExamPrbmItemCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) examPrbmItemDAO.getExamPrbmItemCount(pmParam);
    }

    /**
     * 시험문제항목 정보(목록)를 검색한다.
     *
     * @param param 검색 조건
     * @return 시험문제항목 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getExamPrbmItemList(Map<String, ?> pmParam) throws Exception {
        return examPrbmItemDAO.getExamPrbmItemList(pmParam);
    }

    /**
     * 시험문제항목 정보를 저장한다.
     *
     * @param param 시험문제항목 정보
     * @throws Exception
     */
    public int saveExamPrbmItem(List<Map<String, Object>> pmdataModels, Map<String, Object> pmParam) throws Exception {
        int nResult = 0;
        for (Map<String, Object> mModel : pmdataModels) {
            String sExamPrbmItemId = StringUtils.defaultString((String) mModel.get("exam_prbm_item_id"));

            pmParam.put("exam_prbm_id", mModel.get("exam_prbm_id"));
            pmParam.put("exam_prbm_item_cntn", mModel.get("exam_prbm_item_cntn"));
            pmParam.put("cran_yn", mModel.get("cran_yn"));

            if ("".equals(sExamPrbmItemId)) {
                examPrbmItemDAO.insertExamPrbmItem(pmParam);
            } else {
                pmParam.put("expe_sqnc", mModel.get("expe_sqnc"));
                pmParam.put("exam_prbm_item_id", mModel.get("exam_prbm_item_id"));

                nResult += examPrbmItemDAO.updateExamPrbmItem(pmParam);
            }
        }

        return nResult;
    }

    /**
     * 시험문제항목 정보를 삭제한다.
     *
     * @param param 시험문제항목 정보
     * @throws Exception
     */
    public int deleteExamPrbmItem(Map<String, Object> pmParam) throws Exception {
        return examPrbmItemDAO.deleteExamPrbmItem(pmParam);
    }

    /**
     * 시험문제항목 응시 정보(목록)를 검색한다.
     *
     * @param param 검색 조건
     * @return 시험문제항목 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getExamPrbmItemAnsrList(Map<String, ?> pmParam) throws Exception {
        return examPrbmItemDAO.getExamPrbmItemAnsrList(pmParam);
    }

    /**
     * 시험문제항목 순서를 변경한다.
     *
     * @param pmModelList List<Map<String, Object>>
     * @param pmParam 시험항목 순서정보
     * @throws Exception
     */
    public int updateExamPrbmItemExpeSqnc(List<Map<String, Object>> pmModelList, Map<String, Object> pmParam) throws Exception {
        int nResult = 0;

        for (Map<String, Object> mModel : pmModelList) {
            String exam_prbm_item_id = StringUtils.defaultString((String) mModel.get("exam_prbm_item_id"));
            if (!"".equals(exam_prbm_item_id)) {
                pmParam.put("expe_sqnc", mModel.get("expe_sqnc"));
                pmParam.put("exam_prbm_item_id", mModel.get("exam_prbm_item_id"));

                nResult += examPrbmItemDAO.updateExamPrbmItemExpeSqnc(pmParam);
            }
        }
        return nResult;
    }
}
