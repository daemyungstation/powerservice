/*
 * (@)# RecrncResrDtlDAO.java
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
 * 예약콜 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID RecrncResrDtl
 */
@Repository
public class RecrncResrDtlDAO extends EgovAbstractMapper {

    /**
     * 예약콜 정보를 등록한다.
     *
     * @param pmParam 예약콜 정보
     * @throws Exception
     */
    public int insertRecrncResrDtl(Map<String, ?> pmParam) throws Exception {
        return insert("RecrncResrDtlMap.insertRecrncResrDtl", pmParam);
    }

    /**
     * 예약콜 정보를 수정한다.
     *
     * @param pmParam 예약콜 정보
     * @throws Exception
     */
    public int updateRecrncResrDtl(Map<String, ?> pmParam) throws Exception {
        return update("RecrncResrDtlMap.updateRecrncResrDtl", pmParam);
    }

    /**
     * 예약콜 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 예약콜 정보의 검색 건수
     * @throws Exception
     */
    public int getRecrncResrDtlCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("RecrncResrDtlMap.getRecrncResrDtlCount", pmParam);
    }

    /**
     * 예약콜 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 예약콜 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getRecrncResrDtlList(Map<String, ?> pmParam) throws Exception {
        return selectList("RecrncResrDtlMap.getRecrncResrDtlList", pmParam);
    }

    /**
     * 예약콜 정보를 검색한다.
     *
     * @param pmParam 상담번호
     * @return 예약콜 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getRecrncResrDtl(Map<String, ?> pmParam) throws Exception {
        return selectOne("RecrncResrDtlMap.getRecrncResrDtlList", pmParam);
    }


    /**
     * 예약콜 TO-DO 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 예약콜 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getTodoRecrncResrDtlList(Map<String, ?> pmParam) throws Exception {
        return selectList("RecrncResrDtlMap.getTodoRecrncResrDtlList", pmParam);
    }

    /**
     * 예약콜 TO-DO 정보를 검색한다.
     *
     * @param pmParam 상담번호
     * @return 예약콜 TO-DO 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getTodoRecrncResrDtl(Map<String, ?> pmParam) throws Exception {
        return selectOne("RecrncResrDtlMap.getTodoRecrncResrDtlList", pmParam);
    }

}
