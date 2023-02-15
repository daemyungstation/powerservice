/*
 * (@)# WklyBswrDtlsCdDAO.java
 *
 * @author 박상수
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

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 주간보고 관리를 한다.
 *
 * Copyright (c) 2013 Company Inwoo Tech Inc.
 *
 * @author 박상수
 * @version 1.0
 * @date 2015/01/05
 * @프로그램ID WklyCode
 */
@Repository
public class WklyBswrDtlsCdDAO extends EgovAbstractMapper {

    /**
     * 코드 정보를 등록한다.
     *
     * @param pmParam 코드 정보
     * @throws Exception
     */
    public int updateCodeSequence(Map<String, ?> pmParam) throws Exception {
        return update("WklyBswrDtlsCdMap.updateCodeSequence", pmParam);
    }
    /**
     * 코드 정보를 등록한다.
     *
     * @param pmParam 코드 정보
     * @throws Exception
     */
    public int insertCode(Map<String, ?> pmParam) throws Exception {
        return insert("WklyBswrDtlsCdMap.insertCode", pmParam);
    }

        /**
     * 코드 정보를 수정한다.
     *
     * @param pmParam 코드 정보
     * @throws Exception
     */
    public int updateCode(Map<String, ?> pmParam) throws Exception {
        return update("WklyBswrDtlsCdMap.updateCode", pmParam);
    }

    /**
     * 코드 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 코드 정보의 검색 수
     * @throws Exception
     */

    public int getCodeCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("WklyBswrDtlsCdMap.getCodeCount", pmParam);
    }

    /**
     * 코드 정보(목록)를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 코드 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getCodeList(Map<String, ?> pmParam) throws Exception {
        return selectList("WklyBswrDtlsCdMap.getCodeList", pmParam);
    }

    /**
     * 코드 정보(1건)를 검색한다.
     *
     * @param param 검색 조건
     * @return 코드 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getCode(Map<String, ?> pmParam) throws Exception {
        return selectOne("WklyBswrDtlsCdMap.getCodeList", pmParam);
    }

    /**
     * 코드셋 목록에 따라 코드 정보(목록)를 검색한다.
     *
     * @param sCodeSetList 검색 조건
     * @return 코드 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getCodeList(String[] sCodeSetList) throws Exception {
        return selectList("WklyBswrDtlsCdMap.getCodeListByStringArray", sCodeSetList);
    }

    /**
     * 코드 순서 최대값을 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 순서 최대값
     * @throws Exception
     */
    public int getCodeMaxSequence(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("WklyBswrDtlsCdMap.getCodeMaxSequence", pmParam);
    }

    /**
     * 코드 순서의 중복 건수를 조회한다
     *
     * @param pmParam 검색 조건
     * @return 코드 순서의 중복 건수
     * @throws Exception
     */
    public int getCodeDuplicateCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("WklyBswrDtlsCdMap.getCodeDuplicateCount", pmParam);
    }
}
