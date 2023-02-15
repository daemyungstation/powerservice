/*
 * (@)# FaxSndgHstrDAO.java
 *
 * @author 김은지
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

package powerservice.business.chn.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * FAX 전송 이력 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 김은지
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID FaxSndgHstr
 */
@Repository
public class FaxSndgHstrDAO extends EgovAbstractMapper {

    /**
     * FAX 전송 이력 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return FAX 전송 이력 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getFaxSndgHstrList(Map<String, ?> pmParam) throws Exception {
        return selectList("FaxSndgHstrMap.getFaxSndgHstrList", pmParam);
    }

}
