/*
 * (@)# CtiCrncHstrDAO.java
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/05/09
 * Copyright (c) 2016 by Inwoo tech inc, KOREA. All Rights Reserved.
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

package powerservice.business.cns.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * CTI통화 이력을 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/05/09
 * @프로그램ID CtiCrncHstr
 */
@Repository
public class CtiCrncHstrDAO extends EgovAbstractMapper {

    /**
     * 녹취 이력 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 녹취 이력 정보의 검색 건수
     * @throws Exception
     */
    public int getCtiCrncHstrCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("CtiCrncHstrMap.getCtiCrncHstrCount", pmParam);
    }

    /**
     * 녹취 이력 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 녹취 이력 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getCtiCrncHstrList(Map<String, ?> pmParam) throws Exception {
        return selectList("CtiCrncHstrMap.getCtiCrncHstrList", pmParam);
    }

    /**
     * CTI통화 이력 정보를 등록한다.
     *
     * @param pmParam CTI통화 이력 정보
     * @throws Exception
     */
    public int insertCtiCrncDtl(Map<String, ?> pmParam) throws Exception {
        return insert("CtiCrncHstrMap.insertCtiCrncDtl", pmParam);
    }

    /**
     * CTI통화 이력 정보를 수정한다.
     *
     * @param pmParam CTI통화 이력 정보
     * @throws Exception
     */
    public int updateCtiCrncDtl(Map<String, ?> pmParam) throws Exception {
        return update("CtiCrncHstrMap.updateCtiCrncDtl", pmParam);
    }

    /**
     * 녹취 상담 정보를 등록한다.
     *
     * @param pmParam 녹취 상담 정보
     * @throws Exception
     */
    public int mergeRecConsDtl(Map<String, ?> pmParam) throws Exception {
        return insert("CtiCrncHstrMap.mergeRecConsDtl", pmParam);
    }

    /**
     * 녹취 상품 정보를 등록한다.
     *
     * @param pmParam 녹취 상품 정보
     * @throws Exception
     */
    public int mergeRecProdDtl(Map<String, ?> pmParam) throws Exception {
        return insert("CtiCrncHstrMap.mergeRecProdDtl", pmParam);
    }

    /**
     * 녹취 TM 정보를 등록한다.
     *
     * @param pmParam 녹취 TM 정보
     * @throws Exception
     */
    public int mergeRecTmDtl(Map<String, ?> pmParam) throws Exception {
        return insert("CtiCrncHstrMap.mergeRecTmDtl", pmParam);
    }

    /**
     * CTI 통화이력 정보를 등록한다.
     *
     * @param pmParam CTI 통화이력 정보
     * @throws Exception
     */
    public int insertCtiCrncHstr(Map<String, ?> pmParam) throws Exception {
        return insert("CtiCrncHstrMap.insertCtiCrncHstr", pmParam);
    }

}
