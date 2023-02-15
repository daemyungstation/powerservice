/*
 * (@)# ExtcQuryDAO.java
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
 * 대상고객추출조건을 관리한다.
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정용재
 * @version 1.0
 * @date 2015/04/28
 * @프로그램ID ExtcQury
 */

@Repository
public class ExtcQuryDAO extends EgovAbstractMapper {

    /**
     * 대상고객추출조건 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 대상고객추출조건 정보의 검색 수
     * @throws Exception
     */
    public int getExtcQuryCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("ExtcQuryMap.getExtcQuryCount", pmParam);
    }

    /**
     * 대상고객추출조건 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 대상고객추출조건 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getExtcQuryList(Map<String, ?> pmParam) throws Exception {
        return selectList("ExtcQuryMap.getExtcQuryList", pmParam);
    }

    /**
     * 대상고객추출조건 정보를 등록한다.
     *
     * @param pmParam 즐겨찾기 정보
     * @throws Exception
     */
    public int insertExtcQury(Map<String, ?> pmParam) throws Exception {
         return insert("ExtcQuryMap.insertExtcQury", pmParam);
    }

    public int updateExtcQury(Map<String, ?> pmParam) throws Exception {
         return update("ExtcQuryMap.updateExtcQury", pmParam);
    }

    /**
     * 대상고객추출조건 정보를 삭제한다.
     *
     * @param pmParam 대상고객추출조건 정보
     * @throws Exception
     */
    public int deleteExtcQury(Map<String, ?> pmParam) throws Exception {
        return delete("ExtcQuryMap.deleteExtcQury", pmParam);
    }

    /**
     * 대상고객추출조건 정보를 검색한다.
     *
     * @param String 추출조건 ID
     * @return 대상고객추출조건 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getExtc(Map<String, ?> pmParam) throws Exception {
        return selectOne("ExtcQuryMap.getExtcQuryList", pmParam);
    }
}
