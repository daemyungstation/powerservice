/*
 * (@)# WrkScdlDtlService.java
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

import powerservice.business.sch.service.WrkScdlDtlService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 근무 스케줄 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID WrkScdlDtl
 */
@Service
public class WrkScdlDtlServiceImpl extends EgovAbstractServiceImpl implements WrkScdlDtlService {

    @Resource
    public WrkScdlDtlDAO wrkScdlDtlDAO;

    /**
     * 근무 스케줄 정보의 검색 수를 반환한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 근무 스케줄 정보의 검색 수
     * @throws Exception
     */
    public int getWrkScdlDtlCount(Map<String, ?> pmParam) throws Exception {
        return wrkScdlDtlDAO.getWrkScdlDtlCount(pmParam);
    }

    /**
     * 근무 스케줄 정보를 검색한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 근무 스케줄 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getWrkScdlDtlList(Map<String, ?> pmParam) throws Exception {
        return wrkScdlDtlDAO.getWrkScdlDtlList(pmParam);
    }

    /**
     * 근무 스케줄 개별배정 정보를 저장한다
     *
     * @pmParam pmModelList 근무 스케줄 정보
     * @pmParam pmParam 공통 정보
     * @throws Exception
     */
    public int saveWrkScdlDtlPart(List<Map<String, Object>> pmModelList, Map<String, Object> pmParam) throws Exception {
        int nResult = 0;

        // 근무 스케줄 저장
        for (Map<String, Object> mModel : pmModelList) {
            // 해당월의 모든 근무 스케줄 삭제
            pmParam.put("user_id", mModel.get("user_id"));
            pmParam.put("wrk_scdl_yrmn", mModel.get("wrk_scdl_yrmn"));
            wrkScdlDtlDAO.deleteWrkScdlDtlPart(pmParam);

            // 근무 스케줄이 있는 정보만 등록
            for (int i = 1; i <= 31; i++) {
                String sWrkScdlDy = StringUtils.leftPad(Integer.toString(i), 2, "0");
                String sWrkTypCd = StringUtils.defaultString((String) mModel.get("d" + sWrkScdlDy));

                if (!"".equals(sWrkTypCd) && !"-".equals(sWrkTypCd)) {
                    pmParam.put("wrk_scdl_dy", sWrkScdlDy);
                    pmParam.put("wrk_typ_cd", sWrkTypCd);
                    nResult += wrkScdlDtlDAO.insertWrkScdlDtl(pmParam);
                }
            }
        }

        return nResult;
    }

    /**
     * 근무 스케줄 일괄배정 정보를 저장한다
     *
     * @pmParam pmParam 근무 스케줄 정보
     * @throws Exception
     */
    public int saveWrkScdlDtlAll(Map<String, Object> pmParam) throws Exception {
        int nResult = 0;

        String sUserIds = StringUtils.defaultString((String)pmParam.get("user_id_list"));
        String[] sUserIdList = sUserIds.split(",");

        String sWrkTypCd = StringUtils.defaultString((String)pmParam.get("wrk_typ_cd"));
        if (sUserIdList != null && sUserIdList.length > 0) {
            // 근무 스케줄 입력할 날짜 조회
            List<Map<String, Object>> mDtList = wrkScdlDtlDAO.getDtList(pmParam);

            if (mDtList != null && mDtList.size() > 0) {
                // 해당월의 모든 근무 스케줄 삭제
                pmParam.put("userList", sUserIdList);
                wrkScdlDtlDAO.deleteWrkScdlDtlAll(pmParam);

                // 상담사 리스트 반복
                if (!"".equals(sWrkTypCd)) {
                    for (String sUserId : sUserIdList) {
                        pmParam.put("user_id", sUserId);

                        // 날짜 반복
                        for (Map<String, Object> mDt : mDtList) {
                            pmParam.put("wrk_scdl_yrmn", mDt.get("yrmn"));
                            pmParam.put("wrk_scdl_dy", mDt.get("dt"));
                            nResult += wrkScdlDtlDAO.insertWrkScdlDtl(pmParam);
                        }
                    }
                }
            }
        }

        return nResult;
    }

}
