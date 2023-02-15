/*
 * (@)# TrgtListDAO.java
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

package powerservice.business.cam.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 캠페인 대상리스트 관리를 한다.
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정용재
 * @version 1.0
 * @date 2015/04/29
 * @프로그램ID TrgtList
 */

@Repository
public class TrgtListDAO extends EgovAbstractMapper {

    /**
     * 대상리스트 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 대상리스트 정보의 검색 수
     * @throws Exception
     */
    public int getTrgtListCount(Map<String, ?> pmParam) throws Exception {
        return (Integer)selectOne("TrgtListMap.getTrgtListCount", pmParam);
    }

    /**
     * 대상리스트 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 대상리스트 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getTrgtListList(Map<String, ?> pmParam) throws Exception {
        return selectList("TrgtListMap.getTrgtListList", pmParam);
    }

    /**
     * 대상리스트 정보를 등록한다.
     *
     * @param pmParam 대상리스트 정보
     * @throws Exception
     */
    public int insertTrgtList(Map<String, ?> pmParam) throws Exception {
        return insert("TrgtListMap.insertTrgtList", pmParam);
    }

    /**
     * 대상리스트 정보(1건)를 검색한다.
     *
     * @param String 대상리스트 ID
     * @return 대상리스트 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getTrgtList(Map<String, ?> pmParam) throws Exception {
        return selectOne("TrgtListMap.getTrgtListList", pmParam);
    }

    /**
     * 대상리스트 정보를 수정한다. 사용
     *
     * @param pmParam 대상리스트 정보
     * @throws Exception
     */
    public int updateTrgtList(Map<String, ?> pmParam) throws Exception {
        return update("TrgtListMap.updateTrgtList", pmParam);
    }

    /**
     * 대상리스트 정보를 삭제한다.
     *
     * @param pmParam 대상리스트 정보
     * @throws Exception
     */
    public int deleteTrgtList(Map<String, ?> pmParam) throws Exception {
        return delete("TrgtListMap.deleteTrgtList", pmParam); // 대상리스트 삭제
            // delete("SubTargetListMap.deleteSubTargetListByTargetListId", param); // 서브대상리스트 삭제
            // delete("TrgtCustMap.deleteDupcustinfoByTargetListid", param); // 중복테이블 삭제
    }

    public int deleteTrgtCustByTrgtList(Map<String, ?> pmParam) throws Exception {
        return delete("TrgtCustMap.deleteTrgtCustByTrgtList", pmParam); // 대상고객 삭제
    }

}
