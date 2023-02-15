/*
 * (@)# WklyBswrTypDAO.java
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
 * @프로그램ID WklyBswrTypDAO
 */
@Repository
public class WklyBswrTypDAO extends EgovAbstractMapper {

    /**
     * 코드셋 정보를 등록한다.
     *
     * @pmParam pmParam 코드셋 정보
     * @throws Exception
     */
    public int insertCodeSet(Map<String, ?> pmParam) throws Exception {
        return insert("WklyBswrTypMap.insertCodeSet", pmParam);
    }

    /**
     * 코드셋 정보를 수정한다.
     *
     * @pmParam pmParam 코드셋 정보
     * @throws Exception
     */
    public int updateCodeSet(Map<String, ?> pmParam) throws Exception {
        return update("WklyBswrTypMap.updateCodeSet", pmParam);
    }

    /**
     * 코드셋 정보의 검색 수를 반환한다.
     *
     * @pmParam paramHash 검색 조건
     * @return 코드셋 정보의 검색 건수
     * @throws Exception
     */
    public int getCodeSetCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("WklyBswrTypMap.getCodeSetCount", pmParam);
    }

    /**
     * 코드셋 정보를 검색한다.
     *
     * @pmParam paramHash 검색 조건
     * @return 코드셋 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getCodeSetList(Map<String, ?> pmParam) throws Exception {
        return selectList("WklyBswrTypMap.getCodeSetList", pmParam);
    }

    /**
     * 코드셋 정보를 검색한다.
     *
     * @pmParam id 코드셋 ID
     * @return 코드셋 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getCodeSet(Map<String, ?> pmParam) throws Exception {
        return selectOne("WklyBswrTypMap.getCodeSetList", pmParam);
    }

    /**
     * 코드셋 순서 최대값을 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 순서 최대값
     * @throws Exception
     */
    public int getCodeSetMaxSequence(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("WklyBswrTypMap.getCodeSetMaxSequence", pmParam);
    }

    /**
     * 코드셋 순서의 중복 건수를 조회한다
     *
     * @param pmParam 검색 조건
     * @return 코드 순서의 중복 건수
     * @throws Exception
     */
    public int getCodeSetDuplicateCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("WklyBswrTypMap.getCodeSetDuplicateCount", pmParam);
    }

    /**
     * 코드셋 순서정보를 수정한다.
     *
     * @param pmParam 코드 정보
     * @throws Exception
     */
    public int updateCodeSetSequence(Map<String, ?> pmParam) throws Exception {
        return update("WklyBswrTypMap.updateCodeSetSequence", pmParam);
    }
}
