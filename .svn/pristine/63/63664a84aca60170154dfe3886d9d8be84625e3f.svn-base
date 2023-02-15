/*
 * (@)# RprtDAO.java
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

package powerservice.business.evl.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 평가지를 관리한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 최현식
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID EvstDtl
 */
@Repository
public class EvstDtlDAO extends EgovAbstractMapper {

    /**
     * 평가지 정보의 검색 수를 반환한다.
     *
     * @param param 검색 조건
     * @return 평가지 정보의 검색 수
     * @throws Exception
     */
    public int getEvstDtlCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("EvstDtlMap.getEvstDtlCount", pmParam);
    }

    /**
     * 평가지 정보(목록)를 검색한다.
     *
     * @param param 검색 조건
     * @return 평가지 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getEvstDtlList(Map<String, ?> pmParam) throws Exception {
        return selectList("EvstDtlMap.getEvstDtlList", pmParam);
    }

    /**
     * 평가지 정보를 등록한다.
     *
     * @param param 평가지 정보
     * @throws Exception
     */
    public int insertEvstDtl(Map<String, Object> pmParam) throws Exception {
        return insert("EvstDtlMap.insertEvstDtl", pmParam);
    }

    /**
     * 평가지 정보(1건)를 검색한다.
     *
     * @param paramHash 검색 조건
     * @return 평가지 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getEvstDtl(Map<String, Object> pmParam) throws Exception {
        return selectOne("EvstDtlMap.getEvstDtlList", pmParam);
    }

    /**
     * 평가지 정보를 수정한다.
     *
     * @param param 평가지 정보
     * @throws Exception
     */
    public int updateEvstDtl(Map<String, Object> pmParam) throws Exception {
        return update("EvstDtlMap.updateEvstDtl", pmParam);
    }

    /**
     * 평가지 정보를 삭제한다.
     *
     * @param param 평가지 정보
     * @throws Exception
     */
    public int deleteEvstDtl(Map<String, Object> pmParam) throws Exception {
        return delete("EvstDtlMap.deleteEvstDtl", pmParam);
    }

    /**
     * 평가지 정보를 복사한다.
     *
     * @param param 평가지 정보
     * @throws Exception
     */
    public int insertCopyEvstDtl(Map<String, ?> pmParam) throws Exception {
        return insert("EvstDtlMap.insertCopyEvstDtl", pmParam);

    }

    /**
     * 평가진행상태 정보를 검색한다.
     *
     * @param paramHash 검색 조건
     * @return 진행상태 정보
     * @throws Exception
     */
    public Map<String, Object> checkEvltPrgrStatCd(String evstid) throws Exception {
        return selectOne("EvstDtlMap.checkEvltPrgrStatCd", evstid);
    }
}
