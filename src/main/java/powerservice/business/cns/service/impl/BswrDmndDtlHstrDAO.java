/*
 * (@)# BswrDmndDtlHstrDAO.java
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
 * 업무요청내역이력 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID BswrDmndDtlHstr
 */
@Repository
public class BswrDmndDtlHstrDAO extends EgovAbstractMapper {

    /**
     * 업무요청내역이력 정보를 등록한다.
     *
     * @param param 업무요청내역이력 정보
     * @throws Exception
     */
    public int insertBswrDmndDtlHstr(Map<String, ?> pmParam) throws Exception {
        return insert("BswrDmndDtlHstrMap.insertBswrDmndDtlHstr", pmParam);
    }

    /**
     * 업무요청내역이력 정보를 삭제한다.
     *
     * @param param 업무요청내역이력 정보
     * @throws Exception
     */
    public int deleteBswrDmndDtlHstr(Map<String, ?> pmParam) throws Exception {
        return delete("BswrDmndDtlHstrMap.deleteBswrDmndDtlHstr", pmParam);
    }

    /**
     * 이관 이력정보의 검색 수를 반환한다.
     *
     * @param paramHash 검색 조건
     * @return 이관이력 대상 건수
     * @throws Exception
     */
    public int getBswrDmndDtlHstrCount(Map<String, ?> paramHash) throws Exception {
        return (Integer) selectOne("BswrDmndDtlHstrMap.getBswrDmndDtlHstrCount", paramHash);
    }

    /**
     * 이관이력 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 이관이력 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getBswrDmndDtlHstrList(Map<String, ?> pmParam) throws Exception {
        return selectList("BswrDmndDtlHstrMap.getBswrDmndDtlHstrList", pmParam);
    }

}
