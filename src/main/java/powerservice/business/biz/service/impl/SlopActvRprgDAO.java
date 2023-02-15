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
 * 사업원장관리 -> 영업 활동정보
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정훈
 * @version 1.0
 * @date 2015/08/07
 * @프로그램ID <PS-UI-DS09>
 */
@Repository
public class SlopActvRprgDAO extends EgovAbstractMapper {

    /**
     * 사업원장관리 -> 영업 활동정보를 등록한다.
     *
     * @param pmParam 영업 활동정보
     * @throws Exception
     */
   public int insertSlopActvRprg(Map<String, ?> pmParam) throws Exception {
       return insert("SlopActvRprgMap.insertSlopActvRprg", pmParam);
    }


    /**
     * 사업원장관리 -> 영업 활동정보를 수정한다.
     *
     * @param pmParam 영업 활동정보
     * @throws Exception
     */
   public int updateSlopActvRprg(Map<String, ?> pmParam) throws Exception {
         return update("SlopActvRprgMap.updateSlopActvRprg", pmParam);
    }


    /**
     * 사업원장관리 -> 영업 활동정보 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 영업 활동정보 건수
     * @throws Exception
     */
    public int getSlopActvRprgCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("SlopActvRprgMap.getSlopActvRprgCount", pmParam);
    }


    /**
     * 사업원장관리 -> 영업 활동정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 영업 활동정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getSlopActvRprgList(Map<String, ?> pmParam) throws Exception {
        return selectList("SlopActvRprgMap.getSlopActvRprgList", pmParam);
    }


    /**
     * 사업원장관리 -> 영업 활동정보를 검색한다.
     *
     * @param pmParam 영업 활동정보 ID
     * @return 영업 활동정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getSlopActvRprg(Map<String, ?> pmParam) throws Exception {
        return selectOne("SlopActvRprgMap.getSlopActvRprgList", pmParam);
    }

    /**
     * 사업원장관리 -> 영업 활동정보를 삭제한다.
     *
     * @param pmParam 영업활동정보
     * @throws Exception
     */
    public int deleteSlopActvRprg(Map<String, Object> pmParam) throws Exception {
        return delete("SlopActvRprgMap.deleteSlopActvRprg", pmParam);
    }

}
