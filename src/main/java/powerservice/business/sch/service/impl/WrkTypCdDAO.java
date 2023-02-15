/*
 * (@)# WrkTypCdDAO.java
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

package powerservice.business.sch.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 근무유형 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID WrkTypCd
 */
@Repository
public class WrkTypCdDAO extends EgovAbstractMapper {

    /**
     * 근무유형 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 근무유형 정보의 검색 수
     * @throws Exception
     */
    public int getWrkTypCdCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("WrkTypCdMap.getWrkTypCdCount", pmParam);
    }

    /**
     * 근무유형 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 근무유형 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getWrkTypCdList(Map<String, ?> pmParam) throws Exception {
        return selectList("WrkTypCdMap.getWrkTypCdList", pmParam);
    }

    /**
     * 근무유형 정보를 검색한다.(1건)
     *
     * @param pmParam 검색조건
     * @throws Exception
     */
    public Map<String, Object> getWrkTypCd(Map<String, ?> pmParam) {
        return selectOne("WrkTypCdMap.getWrkTypCdList", pmParam);
    }

    /**
     * 근무유형 정보를 등록한다.
     *
     * @param pmParam 근무유형 정보
     * @throws Exception
     */
    public int insertWrkTypCd(Map<String, ?> pmParam) throws Exception {
        return insert("WrkTypCdMap.insertWrkTypCd", pmParam);
    }

    /**
     * 근무유형 정보를 수정한다.
     *
     * @param pmParam 근무유형 정보
     * @throws Exception
     */
    public int updateWrkTypCd(Map<String, ?> pmParam) throws Exception {
        return update("WrkTypCdMap.updateWrkTypCd", pmParam);
    }

    /**
     * 근무유형 정보를 삭제한다.
     *
     * @param pmParam 근무유형 정보
     * @throws Exception
     */
    public int deleteWrkTypCd(Map<String, ?> pmParam) throws Exception {
        return delete("WrkTypCdMap.deleteWrkTypCd", pmParam);
    }

}
