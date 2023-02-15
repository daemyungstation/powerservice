/*
 * (@)# CalbDtlDAO.java
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

package powerservice.business.cns.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 콜백 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID CalbDtl
 */
@Repository
public class CalbDtlDAO extends EgovAbstractMapper {

    /**
     * 콜백 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 콜백 정보의 검색 수
     * @throws Exception
     */
    public int getCalbDtlCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("CalbDtlMap.getCalbDtlCount", pmParam);
    }

    /**
     * 콜백 정보(목록)를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 콜백 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getCalbDtlList(Map<String, ?> pmParam) throws Exception {
        return selectList("CalbDtlMap.getCalbDtlList", pmParam);
    }

    /**
     * 콜백 정보(1건)를 검색한다.
     *
     * @param 검색조건
     * @return 콜백 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getCalbDtl(Map<String, ?> pmParam) throws Exception {
        return selectOne("CalbDtlMap.getCalbDtlList", pmParam);
    }

    /**
     * 콜백 정보를 수정한다.
     *
     * @param pmParam 콜백 정보
     * @throws Exceptionz
     */
    public int updateCalbDtl(Map<String, ?> pmParam) throws Exception {
        return update("CalbDtlMap.updateCalbDtl", pmParam);
    }

    /**
     * 상담사별 콜백 미처리 건수를 검색한다.
     *
     * @return 상담사별 콜백 미처리 건수
     * @throws Exception
     */
    public List<Map<String, Object>> getTodoCalbDtlCount() throws Exception {
        return selectList("CalbDtlMap.getTodoCalbDtlCount");
    }

}
