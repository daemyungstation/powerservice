/*
 * (@)# VctnAplcDtlService.java
 *
 * @author 이희철
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

package powerservice.business.sch.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import powerservice.business.sch.service.VctnScdlDtlService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 휴가요청 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID VctnScdlDtl
 */
@Service
public class VctnScdlDtlServiceImpl extends EgovAbstractServiceImpl implements VctnScdlDtlService {

    @Resource
    public VctnScdlDtlDAO vctnScdlDtlDAO;

    /**
     * 휴가일정 건수를 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 휴가일정 수
     * @throws Exception
     */
    public int getVctnScdlDtlNmprCnt(Map<String, ?> pmParam) throws Exception {
        String oResult = vctnScdlDtlDAO.getVctnScdlDtlNmprCnt(pmParam);

        int nResult = 0;
        if (oResult != null) {
            nResult = Integer.parseInt(oResult);
        }

        return nResult;
    }

    /**
     * 휴가일정 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return휴가일정 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getVctnScdlDtlList(Map<String, ?> pmParam) throws Exception {
        return vctnScdlDtlDAO.getVctnScdlDtlList(pmParam);
    }

    /**
     * 휴가일정 정보를 저장한다
     *
     * @param mModelList 휴가일정 리스트
     * @param pmParam 휴가일정 파라미터
     * @throws Exception
     */
    public int saveVctnScdlDtl(List<Map<String, Object>> mModelList, Map<String, Object> pmParam) throws Exception {
        int nResult = 0;

        for (Map<String, Object> mModel : mModelList) {
            // 해당월의 모든 휴가일정 정보 삭제
            pmParam.put("team_cd", (String) mModel.get("team2_cd"));
            pmParam.put("vctn_yrmn", (String) mModel.get("vctn_yrmn"));
            vctnScdlDtlDAO.deleteVctnScdlDtl(pmParam);

            // 배정일정 정보 등록
            for (int i = 1; i <= 31; i++) {
                String sVctnDy = StringUtils.leftPad(Integer.toString(i), 2, "0");

                int sVctnNmprCnt = (mModel.get("d" + sVctnDy) == null) ? 0 : (Integer) mModel.get("d" + sVctnDy);
                if (sVctnNmprCnt > 0) {
                    pmParam.put("vctn_dy", sVctnDy);
                    pmParam.put("vctn_nmpr_cnt", sVctnNmprCnt);
                    nResult += vctnScdlDtlDAO.insertVctnScdlDtl(pmParam);
                }
            }
        }
        return nResult;
    }

}
