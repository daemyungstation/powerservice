/*
 * (@)# FaqHstrDAO.java
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

package powerservice.business.kms.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * FAQ 이력 관리를 한다.
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 차건호
 * @version 1.0
 * @date 2015/04/14
 * @프로그램ID FaqHstr
 */

@Repository
public class FaqHstrDAO extends EgovAbstractMapper {

    /**
     * FAQ 이력 정보의 검색 수를 반환한다.
     *
     * @param mpParam 검색 조건
     * @return FAQ 이력 정보의 검색 수
     * @throws Exception
     */
    public int getFaqHstrCount(Map<String, ?> mpParam) throws Exception {
        return (Integer) selectOne("FaqHstrMap.getFaqHstrCount", mpParam);
    }

    /**
     * FAQ 이력 정보를 검색한다.
     *
     * @param mpParam 검색 조건
     * @return FAQ 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getFaqHstrList(Map<String, ?> mpParam) throws Exception {
        return selectList("FaqHstrMap.getFaqHstrList", mpParam);
    }

    /**
     * FAQ 요청 정보를 등록한다.
     *
     * @param pmParam FAQ 요청 정보
     * @throws Exception
     *
     */
    public int insertFaqHstr(Map<String, Object> pmParam) throws Exception {
        return insert("FaqHstrMap.insertFaqHstr", pmParam);
    }

    /**
     * 상담스크립트 정보를 삭제한다.
     *
     * @param pmParam 상담스크립트 정보
     * @throws Exception
     */
    public int deleteFaqHstr(Map<String, ?> pmParam) throws Exception {
        return delete("FaqHstrMap.deleteFaqHstr", pmParam);
    }

}
