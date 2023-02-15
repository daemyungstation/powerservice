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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import powerservice.business.wkly.service.WklyBswrTypService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 주간업무 유형을 관리한다.
 *
 * Copyright (c) 2013 Company Inwoo Tech Inc.
 *
 * @author 박상수
 * @version 1.0
 * @date 2013/05/17
 * @프로그램ID CodeSet
 */
@Service
public class WklyBswrTypServiceImpl extends EgovAbstractServiceImpl implements WklyBswrTypService {

    @Resource
    public WklyBswrTypDAO wklyBswrTypDAO;

    /**
     * 코드셋 정보를 등록한다.
     *
     * @pmParam pmParam 코드셋 정보
     * @throws Exception
     */
    public String insertCodeSet(Map<String, Object> pmParam) throws Exception {
        String result = "";
        // 중복된 순서 자동 증가
        Map<String, Object> searchParam = new HashMap<String, Object>();
        searchParam.put("wkly_bswr_typ_cd", pmParam.get("wkly_bswr_typ_cd"));

        if (wklyBswrTypDAO.getCodeSetCount(searchParam) > 0) {
            result = "이미 등록된 코드값입니다.";
        } else {
            Boolean bDplcFlag = (Boolean) pmParam.get("dplc_flag");
            if (bDplcFlag != null && bDplcFlag) {
                wklyBswrTypDAO.updateCodeSetSequence(pmParam);
            }

            wklyBswrTypDAO.insertCodeSet(pmParam);
        }

        return result;
    }

    /**
     * 코드셋 정보를 수정한다.
     *
     * @pmParam pmParam 코드셋 정보
     * @throws Exception
     */
    public void updateCodeSet(Map<String, Object> pmParam) throws Exception {
        // 중복된 순서 자동 증가
        Boolean bDplcFlag = (Boolean) pmParam.get("dplc_flag");
        if (bDplcFlag != null && bDplcFlag) {
            wklyBswrTypDAO.updateCodeSetSequence(pmParam);

            String bzdp_team_cd = StringUtils.defaultString((String) pmParam.get("bzdp_team_cd"));
            String bzdp_team_cd_old = StringUtils.defaultString((String) pmParam.get("bzdp_team_cd_old"));
            if (!bzdp_team_cd.equals(bzdp_team_cd_old)) {
                pmParam.put("bzdp_team_cd", bzdp_team_cd_old);
                wklyBswrTypDAO.updateCodeSetSequence(pmParam);
                pmParam.put("bzdp_team_cd", bzdp_team_cd);
            }
        }

        wklyBswrTypDAO.updateCodeSet(pmParam);
    }

    /**
     * 코드셋 정보의 검색 수를 반환한다.
     *
     * @pmParam paramHash 검색 조건
     * @return 코드셋 정보의 검색 건수
     * @throws Exception
     */
    public int getCodeSetCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) wklyBswrTypDAO.getCodeSetCount(pmParam);
    }

    /**
     * 코드셋 정보를 검색한다.
     *
     * @pmParam paramHash 검색 조건
     * @return 코드셋 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getCodeSetList(Map<String, ?> pmParam) throws Exception {
        return wklyBswrTypDAO.getCodeSetList(pmParam);
    }

    /**
     * 코드셋 정보를 검색한다.
     *
     * @pmParam id 코드셋 ID
     * @return 코드셋 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getCodeSet(Map<String, ?> pmParam) throws Exception {
        return wklyBswrTypDAO.getCodeSet(pmParam);
    }

    /**
     * 코드셋 순서 최대값을 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 순서 최대값
     * @throws Exception
     */
    public int getCodeSetMaxSequence(Map<String, ?> pmParam) throws Exception {
        return (Integer) wklyBswrTypDAO.getCodeSetMaxSequence(pmParam);
    }

    /**
     * 코드셋 순서의 중복 건수를 조회한다
     *
     * @param pmParam 검색 조건
     * @return 코드 순서의 중복 건수
     * @throws Exception
     */
    public int getCodeSetDuplicateCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) wklyBswrTypDAO.getCodeSetDuplicateCount(pmParam);
    }

}
