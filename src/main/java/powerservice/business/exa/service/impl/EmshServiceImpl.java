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

import powerservice.business.exa.service.EmshService;
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
public class EmshServiceImpl extends EgovAbstractServiceImpl implements EmshService {

    @Resource
    public EmshDAO emshDAO;


    /**
     * 시험지 정보의 검색 수를 반환한다.
     *
     * @param param 검색 조건
     * @return 시험지 정보의 검색 수
     * @throws Exception
     */
    public int getEmshCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) emshDAO.getEmshCount(pmParam);
    }

    /**
     * 시험지 정보(목록)를 검색한다.
     *
     * @param param 검색 조건
     * @return 시험지 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getEmshList(Map<String, ?> pmParam) throws Exception {
        return emshDAO.getEmshList(pmParam);
    }

    /**
     * 시험지 정보를 등록한다.
     *
     * @param param 시험지 정보
     * @throws Exception
     */
    public String insertEmsh(Map<String, Object> pmParam) throws Exception {
        String sKey = "";

        int nResult = emshDAO.insertEmsh(pmParam);
        if (nResult > 0) {
            sKey = (String) pmParam.get("emsh_id");
        }
        return sKey;
    }

    /**
     * 시험지 정보(1건)를 검색한다.
     *
     * @param paramHash 검색 조건
     * @return 시험지 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getEmsh(String psId) throws Exception {
        Map<String, String> mParam = new HashMap<String, String>();
        mParam.put("emsh_id", psId);

        return emshDAO.getEmsh(mParam);
    }

    /**
     * 시험지 정보를 수정한다.
     *
     * @param param 시험지 정보
     * @throws Exception
     */
    public int updateEmsh(Map<String, Object> pmParam) throws Exception {
        int nResult = 0;
        // 시험지 수정
        nResult += emshDAO.updateEmsh(pmParam);
        // 시험문제배점 수정
        emshDAO.updateExamPrbmDtscVl(pmParam);
        return nResult;
    }

    /**
     * 시험지 정보를 삭제한다.
     *
     * @param param 시험지 정보
     * @throws Exception
     */
    public int deleteEmsh(Map<String, Object> pmParam) throws Exception {
        int nResult = 0;
        // 시험문제항목 삭제
        emshDAO.deleteExamPrbmItemByEmshId(pmParam);
        // 시험문제 삭제
        emshDAO.deleteExamPrbm(pmParam);
        // 시험지 삭제
        nResult += emshDAO.deleteEmsh(pmParam);

        return nResult;
    }

    /**
     * 시험지 정보를 복사한다.
     *
     * @param param 시험지 정보
     * @throws Exception
     */
    public int copyEmsh(Map<String, Object> pmParam) throws Exception {
        int nResult = 0;
        String sNewEmshId = StringUtils.defaultString((String) pmParam.get("new_emsh_id"));

        // 복사대상 시험지가 없는 경우 시험지 복사
        if ("".equals(sNewEmshId)) {
            nResult = emshDAO.copyEmsh(pmParam);
            if (nResult > 0) {
                sNewEmshId = (String) pmParam.get("new_emsh_id");
            }
        }

        // 복사대상 시험문제 조회
        String sSelectcheck = StringUtils.defaultString((String) pmParam.get("selectcheck"));
        if (!"".equals(sSelectcheck)) {
            pmParam.put("exam_prbm_ids", sSelectcheck.split(","));
        }
        pmParam.put("orderBy", "EXPE_SQNC");
        pmParam.put("orderDirection", "ASC");
        List<Map<String, Object>> mdataExamPrbmList = emshDAO.getExamPrbmList(pmParam);
        if (mdataExamPrbmList != null) {
            for (Map<String, Object> mExamPrbm : mdataExamPrbmList) {
                mExamPrbm.put("new_emsh_id", sNewEmshId);
                mExamPrbm.put("rgsr_id", pmParam.get("rgsr_id"));
                mExamPrbm.put("amnd_id", pmParam.get("amnd_id"));
                mExamPrbm.put("cntr_cd", pmParam.get("cntr_cd"));

                // 시험문제 복사
                nResult = emshDAO.copyExamPrbm(mExamPrbm);
                if (nResult > 0) {
                    // 시험문제항목 복사
                    emshDAO.copyExamPrbmItem(mExamPrbm);
                }
            }
        }
        return nResult;
    }
}
