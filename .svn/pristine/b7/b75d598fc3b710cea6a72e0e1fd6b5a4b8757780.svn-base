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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import powerservice.business.sch.service.ScdlDtlService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 일정내역 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID ScdlDtl
 */
@Service
public class ScdlDtlServiceImpl extends EgovAbstractServiceImpl implements ScdlDtlService {

    @Resource
    public ScdlDtlDAO scdlDtlDAO;

    /**
     * 일정내역 정보를 저장한다.
     *
     * @param pmModelList 일정내역 정보
     * @param pmParam 공통 정보
     * @throws Exception
     */
    public int saveScdlDtl(List<Map<String, Object>> pmModelList, Map<String, Object> pmParam) throws Exception {
        int nResult = 0;
        for (Map<String, Object> mModel : pmModelList) {
            SimpleDateFormat oDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            oDateFormat.setTimeZone(TimeZone.getTimeZone("KR"));
            oDateFormat.setLenient(false);

            String sScdlSttDt = StringUtils.defaultString((String) mModel.get("scdl_stt_dttm"));
            String sScdlEndDt = StringUtils.defaultString((String) mModel.get("scdl_end_dttm"));
            Date dScdlSttDt = null;
            Date dScdlEndDt = null;
            boolean dateValidity = true;

            try {
                // 타임존 변경
                dScdlSttDt = oDateFormat.parse(sScdlSttDt);
                dScdlEndDt = oDateFormat.parse(sScdlEndDt);
            } catch (ParseException pe) {
                dateValidity = false;
            } catch (IllegalArgumentException ae){
                dateValidity = false;
            } finally {
                // 타임존 변경(계속)
                if (dateValidity && dScdlSttDt != null && dScdlEndDt != null) {
                    oDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                    pmParam.put("scdl_stt_dttm", oDateFormat.format(dScdlSttDt));
                    pmParam.put("scdl_end_dttm", oDateFormat.format(dScdlEndDt));
                } else {
                    pmParam.put("scdl_stt_dttm", sScdlSttDt);
                    pmParam.put("scdl_end_dttm", sScdlEndDt);
                }

                pmParam.put("scdl_typ_cd", mModel.get("scdl_typ_cd"));
                pmParam.put("scdl_titl", mModel.get("scdl_titl"));
                pmParam.put("scdl_cntn", mModel.get("scdl_cntn"));
                pmParam.put("aldy_flag", mModel.get("aldy_flag"));

                String sScdlId = StringUtils.defaultString((String) mModel.get("scdl_id"));
                if ("".equals(sScdlId)) { // 등록
                    pmParam.remove("scdl_id");

                    nResult += scdlDtlDAO.insertScdlDtl(pmParam);
                } else { // 수정
                    pmParam.put("scdl_id", sScdlId);

                    nResult += scdlDtlDAO.updateScdlDtl(pmParam);
                }
            }
        }
        return nResult;
    }

    /**
     * 일정내역 정보를 삭제한다.
     *
     * @param mModelList 일정내역 정보
     * @throws Exception
     */
    public int deleteScdlDtl(List<Map<String, Object>> mModelList) throws Exception {
        int nResult = 0;
        for (Map<String, Object> mModel : mModelList) {
            nResult += scdlDtlDAO.deleteScdlDtl(mModel);
        }
        return nResult;
    }

    /**
     * 일정내역 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 일정내역 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getScdlDtlList(Map<String, ?> pmParam) throws Exception {
        return scdlDtlDAO.getScdlDtlList(pmParam);
    }

}
