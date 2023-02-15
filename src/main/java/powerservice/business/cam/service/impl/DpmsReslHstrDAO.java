/*
 * (@)# DpmsReslHstrDAo.java
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
 * 캠페인발신이력 관리를 한다.
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정용재
 * @version 1.0
 * @date 2015/05/07
 * @프로그램ID DpmsReslHstr
 */

@Repository
public class DpmsReslHstrDAO extends EgovAbstractMapper {

    /**
     * 캠페인발신이력 정보의 검색수를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 캠페인발신이력 정보의 검색 건수
     * @throws Exception
     */
    public int getDpmsReslHstrCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DpmsReslHstrMap.getDpmsReslHstrCount", pmParam);
    }


    /**
     * 캠페인발신이력 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 캠페인발신이력 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getDpmsReslHstrList(Map<String, ?> pmParam) throws Exception {
        return selectList("DpmsReslHstrMap.getDpmsReslHstrList", pmParam);
    }

    /**
     * 캠페인발신이력 정보의 검색수를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 캠페인발신이력 정보의 검색 건수
     * @throws Exception
     */
    public int getIbDpmsReslHstrCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DpmsReslHstrMap.getIbDpmsReslHstrCount", pmParam);
    }


    /**
     * 캠페인발신이력 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 캠페인발신이력 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getIbDpmsReslHstrList(Map<String, ?> pmParam) throws Exception {
        return selectList("DpmsReslHstrMap.getIbDpmsReslHstrList", pmParam);
    }

    /**
     * 캠페인발신이력 정보를 등록한다.
     *
     * @param pmParam 검색 조건
     * @return 캠페인발신이력 정보
     * @throws Exception
     */
    public int insertDpmsReslHstr(Map<String, ?> pmParam) throws Exception {
        return insert("DpmsReslHstrMap.insertDpmsReslHstr", pmParam);
    }



}
