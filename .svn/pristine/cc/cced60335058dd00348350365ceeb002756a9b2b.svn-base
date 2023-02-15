/*
 * (@)# CntrService.java
 *
 * @author 정용재
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

package powerservice.business.wkly.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import powerservice.business.sys.service.impl.FileDAO;
import powerservice.business.wkly.service.WklyRprgBasiService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 센터 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정용재
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID Cntr
 */
@Service
public class WklyRprgBasiServiceImpl extends EgovAbstractServiceImpl implements WklyRprgBasiService {

    @Resource
    public WklyRprgBasiDAO wklyRprgBasiDAO;

    @Resource
    public FileDAO fileDAO;

    /**
     * 주간보고 대상자 검색 수를 반환한다.(관리자)
     *
     * @pmParam pmParam 검색 조건
     * @return 주간보고 대상자 건수
     * @throws Exception
     */
    public int getWklyUserCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) wklyRprgBasiDAO.getWklyUserCount(pmParam);
    }

    /**
     * 주간보고 대상자를 조회한다.(관리자)
     *
     * @pmParam pmParam 검색 조건
     * @return 주간보고 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getWklyUserList(Map<String, ?> pmParam) throws Exception {
        return wklyRprgBasiDAO.getWklyUserList(pmParam);
    }

    /**
     * 주간보고 기본정보를 저장한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 주간보고 목록
     * @throws Exception
     */
    public String insertWklyReport(Map<String, Object> pmParam) throws Exception {
        String skey = "";

        int nResult = wklyRprgBasiDAO.insertWklyReport(pmParam);
        if (nResult > 0) {
            String sNew_wkly_rprg_id = StringUtils.defaultString((String) pmParam.get("new_wkly_rprg_id"));
            if (!sNew_wkly_rprg_id.equals("")) {
                skey = (String) pmParam.get("new_wkly_rprg_id");
            } else {
                skey = (String) pmParam.get("wkly_rprg_id");
            }
        }

        return skey;
    }

    /**
     * 주간보고 수정시 제출시간과 상태를 변경한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 주간보고 목록
     * @throws Exception
     */
    public int updateWklyReport(Map<String, Object> pmParam) throws Exception {
        int nResult = wklyRprgBasiDAO.updateWklyReport(pmParam);
        return nResult;
    }
    /**

     * 주간보고를 제출한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 주간보고 목록
     * @throws Exception
     */
    public int commitWklyReport(Map<String, Object> pmParam) throws Exception {
        return wklyRprgBasiDAO.commitWklyReport(pmParam);
    }


    /**
     * 주간보고 결과 검색 수를 반환한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 주간보고 대상자 건수
     * @throws Exception
     */
    public int getWklyReportResultCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) wklyRprgBasiDAO.getWklyReportResultCount(pmParam);
    }


    /**
     * 주간보고 결과를 조회한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 주간보고 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getWklyReportResult(Map<String, ?> pmParam) throws Exception {
        return wklyRprgBasiDAO.getWklyReportResult(pmParam);
    }

    /**
     * 주간보고 대상자를 조회한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 주간보고 목록
     * @throws Exception
     */
    public Map<String, Object> getWklybswrList(Map<String, ?> pmParam) throws Exception {
        return wklyRprgBasiDAO.getWklybswrList(pmParam);
    }

    /**
     * 사용자를 조회한다.
     *
     * @pmParam pmParam 검색 조건
     * @return 주간보고 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getExcelUserList(Map<String, ?> pmParam) throws Exception {
        return wklyRprgBasiDAO.getExcelUserList(pmParam);
    }

    /**
     * 첨부파일을 조회한다.(주간보고 결과서용)
     *
     * @pmParam pmParam 검색 조건
     * @return 첨부파일 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getWklyFile(Map<String, ?> pmParam) throws Exception {
        return wklyRprgBasiDAO.getWklyFile(pmParam);
    }
}
