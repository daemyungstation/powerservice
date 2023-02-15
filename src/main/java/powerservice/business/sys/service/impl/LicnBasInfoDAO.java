/*
 * (@)# LicnBasInfoDAO.java
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

package powerservice.business.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 라이선스 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID LicnBasInfo
 */
@Repository
public class LicnBasInfoDAO extends EgovAbstractMapper {

    /**
     * 라이센스 적용 대상자 사용자 수를 구한다.
     *
     * @param pmParam 검색 조건
     * @return
     */
    public int getUserCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("LicnBasInfoMap.getLicnBasInfoUserCount", pmParam);
    }

    /**
     * 라이센스 적용 대상자 사용자 수를 구한다.
     *
     * @param pmParam 검색 조건
     * @return
     */
    public int getTodayLoginUserCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("LicnBasInfoMap.getLicnBasInfoTodayLoginUserCount", pmParam);
    }

    /**
     * 라이센스 적용 대상자 센터 수를 구한다.
     *
     * @param pmParam 검색 조건
     * @return
     */
    public int getCenterCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("LicnBasInfoMap.getLicnBasInfoCenterCount", pmParam);
    }

    /**
     * 라이센스 적용 대상 사이트명을 구한다.
     *
     * @param pmParam 검색 조건
     * @return
     */
    public String getSiteName(Map<String, ?> pmParam) throws Exception {
        return (String) selectOne("LicnBasInfoMap.getLicnBasInfoSiteName", pmParam);
    }

    /**
     * 라이선스 기준 정보 이력을 등록한다.
     *
     * @param pmParam 라이선스 기준 정보 이력
     * @throws Exception
     */
    public int insertLicnBasInfoHstr(Map<String, ?> pmParam) throws Exception {
        return insert("LicnBasInfoMap.insertLicnBasInfoHstr", pmParam);
    }

    /**
     * 일자별 사용자 세션 정보 이력 차트 데이터를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 일자별 사용자 세션 정보 이력 차트 데이터
     * @throws Exception
     */
    public List<Map<String, Object>> getSessInfoHstrMonthlyChartList(Map<String, ?> pmParam) throws Exception {
        return selectList("LicnBasInfoMap.getSessInfoHstrMonthlyChartList", pmParam);
    }

    /**
     * 라이선스 발급 정보 이력 목록을 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 라이선스 발급 정보 이력 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getLicnIsncInfoHstrList(Map<String, ?> pmParam) throws Exception {
        return selectList("LicnBasInfoMap.getLicnIsncInfoHstrList", pmParam);
    }
	
}
