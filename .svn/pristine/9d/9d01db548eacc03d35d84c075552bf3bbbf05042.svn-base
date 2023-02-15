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
     * 영업 이슈정보 이력을 조회 한다.
     *
     * Copyright (c) 2015 Company Inwoo Tech Inc.
     *
     * @author 정훈
     * @version 1.0
     * @date 2015/07/27
     * @프로그램ID PS-UI-DS11
     */

@Repository
public class SlopIssHstrDAO extends EgovAbstractMapper {

    /**
     * 영업 이슈정보 이력의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 영업 이슈정보 이력의 검색 건수
     * @throws Exception
     */
    public int getSlopIssHstrCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("SlopIssHstrMap.getSlopIssHstrCount", pmParam);
    }


    /**
     * 영업 이슈정보 이력을 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 영업 이슈정보 이력 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getSlopIssHstrList(Map<String, ?> pmParam) throws Exception {
        return selectList("SlopIssHstrMap.getSlopIssHstrList", pmParam);
    }



    /**
     * 영업 이슈정보 이력을 등록한다.
     *
     * @param pmParam 영업 이슈정보 이력
     * @throws Exception
     */
    public int insertSlopIssHstr(Map<String, ?> pmParam) throws Exception {
        return insert("SlopIssHstrMap.insertSlopIssHstr", pmParam);
     }

    /**
     * 사업원장관리 -> 영업 이슈 이력를 삭제한다.
     *
     * @param pmParam 영업이슈정보
     * @throws Exception
     */
    public int deleteSlopIssHstr(Map<String, Object> pmParam) throws Exception {
        return delete("SlopIssHstrMap.deleteSlopIssHstr", pmParam);
    }


}
