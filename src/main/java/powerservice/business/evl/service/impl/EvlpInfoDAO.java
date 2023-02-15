/*
 * (@)# EvlpInfoService.java
 *
 * @author 최현식
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
public class EvlpInfoDAO extends EgovAbstractMapper {

    /**
     * 사용자 정보의 검색 수를 반환한다.
     *
     * @param param 검색 조건
     * @return 사용자 정보의 검색 수
     * @throws Exception
     */
    public int getUserCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("EvlpInfoMap.getUserCount", pmParam);
    }

    /**
     * 사용자 정보(목록)를 검색한다.
     *
     * @param param 검색 조건
     * @return 사용자 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getUserList(Map<String, ?> pmParam) throws Exception {
        return selectList("EvlpInfoMap.getUserList", pmParam);
    }

    /**
     * 평가자 정보의 검색 수를 반환한다.
     *
     * @param param 검색 조건
     * @return 평가자 정보의 검색 수
     * @throws Exception
     */
    public int getEvltTrprInfoCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("EvltTrprInfoMap.getEvltTrprInfoCount", pmParam);
    }

    /**
     * 평가자 정보의 검색 수를 반환한다.
     *
     * @param param 검색 조건
     * @return 평가자 정보의 검색 수
     * @throws Exception
     */
    public int getEvlpInfoCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("EvlpInfoMap.getEvlpInfoCount", pmParam);
    }

    /**
     * 평가자 정보(목록)를 검색한다.
     *
     * @param param 검색 조건
     * @return 평가자 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getEvlpInfoList(Map<String, ?> pmParam) throws Exception {
        return selectList("EvlpInfoMap.getEvlpInfoList", pmParam);
    }

    /**
     * 평가자 정보를 등록한다.
     *
     * @param param 평가자 정보
     * @throws Exception
     */
    public int insertEvlpInfo(Map<String, Object> pmParam) throws Exception {
        return insert("EvlpInfoMap.insertEvlpInfo", pmParam);
    }

    /**
     * 평가자 정보를 수정한다.
     *
     * @param param 평가자 정보
     * @throws Exception
     */
    public int updateEvlpInfo(Map<String, Object> pmParam) throws Exception {
        return update("EvlpInfoMap.updateEvlpInfo", pmParam);
    }

    /**
     * 평가자 정보를 삭제한다.
     *
     * @param param 평가자 정보
     * @throws Exception
     */
    public int deleteEvlpInfo(Map<String, Object> pmParam) throws Exception {
        return delete("EvlpInfoMap.deleteEvlpInfo", pmParam);
    }

}
