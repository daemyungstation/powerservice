/*
 * (@)# ConsHstrDAO.java
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
 * 상담이력 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID ConsHstr
 */
@Repository
public class ConsHstrDAO extends EgovAbstractMapper {

    /**
     * 상담이력 정보를 등록한다.
     *
     * @param pmParam 상담이력 정보
     * @throws Exception
     */
    public int insertConsHstr(Map<String, ?> pmParam) throws Exception {
        return insert("ConsHstrMap.insertConsHstr", pmParam);
    }

    /**
     * 상담이력 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 상담이력 정보의 검색 건수
     * @throws Exception
     */
    public int getConsHstrCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("ConsHstrMap.getConsHstrCount", pmParam);
    }

    /**
     * 상담이력 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상담이력 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getConsHstrList(Map<String, ?> pmParam) throws Exception {
        return selectList("ConsHstrMap.getConsHstrList", pmParam);
    }

}
