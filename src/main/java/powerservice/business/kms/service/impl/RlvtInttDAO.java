/*
 * (@)# RlvtInttDAO.java
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
 * 유관기관 정보관리를 한다.
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 차건호
 * @version 1.0
 * @date 2015/04/16
 * @프로그램ID RlvtIntt
 */

@Repository
public class RlvtInttDAO extends EgovAbstractMapper {

    /**
     * 유관기관 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 유관기관 정보의 검색 수
     * @throws Exception
     */
    public int getRlvtInttCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("RlvtInttMap.getRlvtInttCount", pmParam);
    }

    /**
     * 유관기관 정보(목록)를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 유관기관  리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getRlvtInttList(Map<String, ?> pmParam) throws Exception {
        return selectList("RlvtInttMap.getRlvtInttList", pmParam);
    }

    /**
     * 유관기관 정보(1건)를 검색한다.
     *
     * @param 유관기관 ID
     * @return 유관기관 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getRlvtIntt(Map<String, ?> mpParam) throws Exception {
        return selectOne("RlvtInttMap.getRlvtInttList", mpParam);
    }

    /**
     * 유관기관 순서 최대값을 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 유관기관 순서 최대값
     * @throws Exception
     */
    public int getRlvtInttMaxOrder(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("RlvtInttMap.getRlvtInttMaxOrder", pmParam);
    }

    /**
     * 유관기관 정보를 등록한다.
     *
     * @param pmParam 유관기관 정보
     * @throws Exception
     */
    public int insertRlvtIntt(Map<String, ?> pmParam) throws Exception {
        return insert("RlvtInttMap.insertRlvtIntt", pmParam);
    }

    /**
     * 유관기관 정보를 수정한다.(ajax)
     *
     * @param pmParam 유관기관 정보
     * @throws Exception
     */
    public int updateRlvtIntt(Map<String, ?> pmParam) throws Exception {
        return update("RlvtInttMap.updateRlvtIntt", pmParam);
    }

    /**
     * 유관기관 정보를 삭제한다.
     *
     * @param paramHash 유관기관 정보
     * @throws Exception
     */
    public int deleteRlvtIntt(Map<String, Object> pmParam) throws Exception {
        return delete("RlvtInttMap.deleteRlvtIntt", pmParam);
    }

    /**
     * 유관기관의 중복 건수를 조회한다.
     *
     * @param paramHash 검색 조건
     * @return 권한 순서의 중복 건수
     * @throws Exception
     */
    public int getRlvtInttDupCout(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("RlvtInttMap.getRlvtInttDupCout", pmParam);
    }

    /**
     * 유관기관 정보를 수정한다.(ajax)
     *
     * @param pmParam 유관기관 정보
     * @throws Exception
     */
    public int updateRlvtInttOrd(Map<String, ?> pmParam) throws Exception {
        return update("RlvtInttMap.updateRlvtInttOrd", pmParam);
    }

}
