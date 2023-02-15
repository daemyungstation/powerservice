/*
 * (@)# CmpgDAO.java
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

package powerservice.business.cam.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 캠페인 관리를 한다.
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 차건호
 * @version 1.0
 * @date 2015/04/30
 * @프로그램ID Cmpg
 */

@Repository
public class ExtcMstTrgtDAO extends EgovAbstractMapper {

    /**
     * 대상고객추출 정보를 검색한다.(반송)
     *
     * @param pmParam 검색 조건
     * @return 대상고객추출조건 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getSnbc(Map<String, ?> pmParam) throws Exception {
        return selectList("ExtcMstTrgtMap.getSnbc", pmParam);
    }

    /**
     * 대상고객추출 정보의 검색 수를 반환한다.(반송)
     *
     * @param pmParam 검색 조건
     * @return 대상고객추출조건 정보의 검색 수
     * @throws Exception
     */
    public int getSnbcCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("ExtcMstTrgtMap.getSnbcCount", pmParam);
    }

    /**
     * 회수시 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 대상고객추출조건 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getDeleteTrgtCustAlct(Map<String, ?> pmParam) throws Exception {
        return selectList("ExtcMstTrgtMap.getDeleteTrgtCustAlct", pmParam);
    }

    /**
     * 미납테이블 정보를 업데이트한다.
     *
     * @param pmParam E-mail 전송 이력 정보
     * @throws Exception
     */
    public int updateUnpy(Map<String, Object> pmParam) {
        return update("ExtcMstTrgtMap.updateUnpy", pmParam);
    }

    /**
     * 신규미납테이블 정보를 업데이트한다.
     * 임동진 20190104
     * @param pmParam E-mail 전송 이력 정보
     * @throws Exception
     */
    public int updateUnpy_new(Map<String, Object> pmParam) {
        return update("ExtcMstTrgtMap.updateUnpy_new", pmParam);
    }

    /**
     * 미납테이블 정보를 업데이트한다(실적).
     *
     * @param pmParam E-mail 전송 이력 정보
     * @throws Exception
     */
    public int updateAcrs(Map<String, Object> pmParam) {
        return update("ExtcMstTrgtMap.updateAcrs", pmParam);
    }

}
