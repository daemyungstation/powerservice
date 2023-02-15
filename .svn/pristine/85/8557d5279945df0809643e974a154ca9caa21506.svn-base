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

import powerservice.business.sch.service.VctnAplcDtlService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 휴가요청 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID VctnAplcDtl
 */
@Service
public class VctnAplcDtlServiceImpl extends EgovAbstractServiceImpl implements VctnAplcDtlService {

    @Resource
    public VctnAplcDtlDAO vctnAplcDtlDAO;

    /**
     * 휴가요청 정보를 등록한다.
     *
     * @param pmParam 휴가요청 정보
     * @throws Exception
     */
    public String insertVctnAplcDtl(Map<String, ?> pmParam) throws Exception {
        String sKey = "";

        int nResult = vctnAplcDtlDAO.insertVctnAplcDtl(pmParam);
        if (nResult > 0) {
            sKey = (String) pmParam.get("vctn_aplc_id");

            // 휴가일자 정보 등록
            saveVctnDtDtl(sKey, pmParam);
        }

        return sKey;
    }

    /**
     * 휴가요청 정보를 수정한다.
     *
     * @param pmParam 휴가요청 정보
     * @throws Exception
     */
    public int updateVctnAplcDtl(Map<String, ?> pmParam) throws Exception {
        int nResult = vctnAplcDtlDAO.updateVctnAplcDtl(pmParam);

        if (nResult > 0) {
            String sKey = (String) pmParam.get("vctn_aplc_id");

            // 휴가일자 정보 등록
            saveVctnDtDtl(sKey, pmParam);
        }

        return nResult;
    }

    /**
     * 휴가요청 정보를 삭제한다.
     *
     * @param pmParam 휴가요청 정보
     * @throws Exception
     */
    public int deleteVctnAplcDtl(Map<String, ?> pmParam) throws Exception {
        return vctnAplcDtlDAO.deleteVctnAplcDtl(pmParam);
    }

    /**
     * 휴가요청 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 휴가요청 정보의 검색 건수
     * @throws Exception
     */
    public int getVctnAplcDtlCount(Map<String, ?> pmParam) throws Exception {
        return vctnAplcDtlDAO.getVctnAplcDtlCount(pmParam);
    }

    /**
     * 휴가요청 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 휴가요청 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getVctnAplcDtlList(Map<String, ?> pmParam) throws Exception {
        return vctnAplcDtlDAO.getVctnAplcDtlList(pmParam);
    }

    /**
     * 휴가요청 정보를 검색한다.
     *
     * @param pmParam 휴가신청ID
     * @return 휴가요청 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getVctnAplcDtl(Map<String, ?> pmParam) throws Exception {
        return vctnAplcDtlDAO.getVctnAplcDtl(pmParam);
    }

    /**
     * 요청한 휴가일수 정보의 검색수를 반환한다.
     *
     * @param pmParam
     * @return 요청한 휴가일수 정보의 검색 건수
     * @throws Exception
     */
    public int getVctnDtDtlCount(Map<String, ?> pmParam) throws Exception {
        return vctnAplcDtlDAO.getVctnDtDtlCount(pmParam);
    }

    /**
     * 요청한 휴가일수 정보를 검색한다.
     *
     * @param pmParam
     * @return 요청한 휴가일수 정보
     * @throws Exception
     */
    public List<Map<String, Object>> getVctnDtDtlList(Map<String, ?> pmParam) throws Exception {
        return vctnAplcDtlDAO.getVctnDtDtlList(pmParam);
    }

    /**
     * 휴가일자 정보를 등록한다.
     *
     * @param pmParam
     * @throws Exception
     */
    public void saveVctnDtDtl(String sVctnAplcId, Map<String,?> pmParam) throws Exception {

        List<Map<String, Object>> mdataVctnDtDtlList = (List<Map<String, Object>>) pmParam.get("vctn_dt_dtl_list");
        if (mdataVctnDtDtlList != null) {
            for (Map<String, Object> mRow : mdataVctnDtDtlList) {
                String sVctnDtDtlId = StringUtils.defaultString((String) mRow.get("vctn_dt_dtl_id"));

                mRow.put("vctn_aplc_id", sVctnAplcId);
                mRow.put("rgsr_id", pmParam.get("rgsr_id"));
                mRow.put("amnd_id", pmParam.get("amnd_id"));
                mRow.put("cntr_cd", pmParam.get("cntr_cd"));

                if ("".equals(sVctnDtDtlId)) {
                    vctnAplcDtlDAO.insertVctnDtDtl(mRow);
                } else {
                    vctnAplcDtlDAO.updateVctnDtDtl(mRow);
                }
            }
        }
    }

    /**
     * 휴가일자 정보를 삭제한다.
     *
     * @param pmParam 휴가일자 정보
     * @throws Exception
     */
    public int deleteVctnDtDtl(Map<String, ?> pmParam) throws Exception {
        return vctnAplcDtlDAO.deleteVctnDtDtl(pmParam);
    }


    /**
     * 특정일자에 승인된 휴가요청 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 특정일자에 승인된 휴가요청 검색 건수
     * @throws Exception
     */
    public int getApproveCount(Map<String, ?> pmParam) throws Exception {
        return vctnAplcDtlDAO.getApproveCount(pmParam);
    }

    /**
     * 사용자의 휴가정보를 검색한다.
     *
     * @param 사용자 아이디
     * @return 사용자의 휴가정보
     * @throws Exception
     */
    public List<Map<String, Object>> getVctnInfoView(Map<String, ?> pmParam) throws Exception {
        return vctnAplcDtlDAO.getVctnInfoView(pmParam);
    }
}
