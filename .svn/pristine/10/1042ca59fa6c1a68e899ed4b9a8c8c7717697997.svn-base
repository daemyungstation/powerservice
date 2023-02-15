/*
 * (@)# AcsDAO.java
 *
 * @author 박의준
 * @version 1.0
 * @date 2015/11/05
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

package powerservice.business.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 프로그램 접근 IP를 관리한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 박의준
 * @version 1.0
 * @date 2015/11/05
 * @프로그램ID Acs
 */
@Repository
public class AcsDAO extends EgovAbstractMapper {

    /**
     * 접근가능한 IP 정보를 등록한다.
     *
     * @param pmParam 접근가능한 IP 정보
     * @throws Exception
     */
    public int insertAcs(Map<String, ?> pmParam) throws Exception {
        return insert("AcsMap.insertAcs", pmParam);
    }

    /**
     * 접근가능한 IP 정보를 수정한다.
     *
     * @param pmParam 접근가능한 IP 정보
     * @throws Exception
     */
    public int updateAcs(Map<String, ?> pmParam) throws Exception {
        return update("AcsMap.updateAcs", pmParam);
    }

    /**
     * 접근가능한 IP 정보를 삭제한다.
     *
     * @param pmParam 접근가능한 IP 정보
     * @throws Exception
     */
    public int deleteAcs(Map<String, ?> pmParam) throws Exception {
        // 게시물 삭제
        return delete("AcsMap.deleteAcs", pmParam);
    }

    /**
     * 접근가능한 IP 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 접근가능한 IP 정보의 검색 수
     * @throws Exception
     */
    public int getAcsCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("AcsMap.getAcsCount", pmParam);
    }

    /**
     * 접근가능한 IP 정보(목록)를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 접근가능한 IP 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getAcsIpList() throws Exception {
        return selectList("AcsMap.getAcsIpList");
    }

    /**
     * 접근가능한 IP 정보(목록)를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 접근가능한 IP 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getAcsList(Map<String, ?> pmParam) throws Exception {
        return selectList("AcsMap.getAcsList", pmParam);
    }

    /**
     * 접근가능한 IP 정보(1건)를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 접근가능한 IP 리스트
     * @throws Exception
     */
    public Map<String, Object> getAcs(Map<String, ?> pmParam) throws Exception {
        return selectOne("AcsMap.getAcs", pmParam);
    }

    /**
     * 접근가능한 IP 중복여부.
     *
     * @param pmParam 검색 조건
     * @return 접근가능한 IP 리스트 (1건)
     * @throws Exception
     */
    public int getAcsDuplicateCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("AcsMap.getAcsDuplicateCount", pmParam);
    }


}
