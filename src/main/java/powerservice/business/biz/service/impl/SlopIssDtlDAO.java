/*
 * (@)# CompBasiDAO.java
 *
 * @author 정훈
 * @version 1.0
 * @date 2015/07/29
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

package powerservice.business.biz.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 사업원장관리->영업이슈 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정훈
 * @version 1.0
 * @date 2015/08/07
 * @프로그램ID <PS-UI-DS10>
 */
@Repository
public class SlopIssDtlDAO extends EgovAbstractMapper {

    /**
     *사업원장관리->영업이슈를 등록한다.
     *
     * @param pmParam 이슈정보
     * @throws Exception
     */
   public int insertSlopIssDtl(Map<String, ?> pmParam) throws Exception {
       return insert("SlopIssDtlMap.insertSlopIssDtl", pmParam);
    }


    /**
     * 사업원장관리->영업이슈 를 수정한다.
     *
     * @param pmParam 이슈정보
     * @throws Exception
     */
   public int updateSlopIssDtl(Map<String, ?> pmParam) throws Exception {
        return update("SlopIssDtlMap.updateSlopIssDtl", pmParam);
    }


    /**
     * 사업원장관리->영업이슈의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 영업이슈의 검색 건수
     * @throws Exception
     */
    public int getSlopIssDtlCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("SlopIssDtlMap.getSlopIssDtlCount", pmParam);
    }


    /**
     * 사업원장관리->영업이슈를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 영업이슈 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getSlopIssDtlList(Map<String, ?> pmParam) throws Exception {
        return selectList("SlopIssDtlMap.getSlopIssDtlList", pmParam);
    }


    /**
     * 사업원장관리->영업이슈를 검색한다.
     *
     * @param pmParam 영업이슈ID
     * @return 영업이슈(1건)
     * @throws Exception
     */
    public Map<String, Object> getSlopIssDtl(Map<String, ?> pmParam) throws Exception {
        return selectOne("SlopIssDtlMap.getSlopIssDtlList", pmParam);
    }

    /**
     * 사업원장관리 -> 영업 이슈를 삭제한다.
     *
     * @param pmParam 영업이슈정보
     * @throws Exception
     */
    public int deleteSlopIssDtl(Map<String, Object> pmParam) throws Exception {
        return delete("SlopIssDtlMap.deleteSlopIssDtl", pmParam);
    }
}
