/*
 * (@)# OvrdProdExcdAdmnDAO.java
 *
 * @author 정용재
 * @version 1.0
 * @date 2016/07/07
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
 * 연체상품제외 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정용재
 * @version 1.0
 * @date 2016/08/02
 * @프로그램ID PS221200
 *
 */
@Repository
public class OvrdProdExcdAdmnDAO extends EgovAbstractMapper {
    /**
     * 연체상품제외 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return OvrdProdExcdAdmn 정보의 검색 수
     * @throws Exception
     */
    public int getOvrdProdExcdAdmnCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("OvrdProdExcdAdmnMap.getOvrdProdExcdAdmnCount", pmParam);
    }

    /**
     * 연체상품제외 정보(목록)를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 연체상품제외 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getOvrdProdExcdAdmnList(Map<String, ?> pmParam) throws Exception {
        return selectList("OvrdProdExcdAdmnMap.getOvrdProdExcdAdmnList", pmParam);
    }

    /**
     * 연체상품제외 정보를 등록한다.
     *
     * @param pmParam 검색 조건
     * @return insert 결과
     * @throws Exception
     */
    public int insertOvrdProdExcdAdmn(Map<String, ?> pmParam) throws Exception {
        return insert("OvrdProdExcdAdmnMap.insertOvrdProdExcdAdmn", pmParam);
    }

    /**
     * 연체상품제외 정보를 수정한다.
     *
     * @param pmParam 검색 조건
     * @return OvrdProdExcdAdmn 리스트
     * @throws Exception
     */
    public int updateOvrdProdExcdAdmn(Map<String, ?> pmParam) throws Exception {
        return update("OvrdProdExcdAdmnMap.updateOvrdProdExcdAdmn", pmParam);
    }
}
