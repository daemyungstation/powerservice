/*
 * (@)# BkmkInfoDAO.java
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

package powerservice.business.kms.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 즐겨찾기 정보 관리를 한다.
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 차건호
 * @version 1.0
 * @date 2015/04/16
 * @프로그램ID BkmkInfo
 */

@Repository
public class BkmkInfoDAO extends EgovAbstractMapper {

    /**
     * 즐겨찾기 정보 의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 즐겨찾기 정보 의 검색 수
     * @throws Exception
     */
    public int getBkmkInfoCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("BkmkInfoMap.getBkmkInfoCount", pmParam);
    }

    /**
     * 즐겨찾기 정보 (목록)를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 바로가기  리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getBkmkInfoList(Map<String, ?> pmParam) throws Exception {
        return selectList("BkmkInfoMap.getBkmkInfoList", pmParam);
    }

    /**
     * 즐겨찾기 정보 (1건)를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 즐겨찾기 정보 (1건)
     * @throws Exception
     */
    public Map<String, Object> getBkmkInfo(Map<String, ?> pmParam) throws Exception {
        return selectOne("BkmkInfoMap.getBkmkInfoList", pmParam);
    }

    /**
     * 바로가기 순서 최대값을 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 바로가기 순서 최대값
     * @throws Exception
     */
    public int getBkmkInfoMaxOrd(Map<String, ?> pmParam) throws Exception {
        return selectOne("BkmkInfoMap.getBkmkInfoMaxOrd", pmParam);
    }

    /**
     * 즐겨찾기 정보 를 등록한다.
     *
     * @param pmParam 즐겨찾기 정보
     * @throws Exception
     */
    public int insertBkmkInfo(Map<String, ?> pmParam) throws Exception {
        return insert("BkmkInfoMap.insertBkmkInfo", pmParam);
    }

    /**
     * 즐겨찾기 정보 를 수정한다.(ajax)
     *
     * @param pmParam 즐겨찾기 정보
     * @throws Exception
     */
    public int updateBkmkInfo(Map<String, ?> pmParam) throws Exception {
        return update("BkmkInfoMap.updateBkmkInfo", pmParam);
    }

    /**
     * 즐겨찾기 순서 를 수정한다.
     *
     * @param pmParam 즐겨찾기 정보
     * @throws Exception
     */
    public int updateBkmkInfoOrd(Map<String, ?> pmParam) throws Exception {
        return update("BkmkInfoMap.updateBkmkInfoOrd", pmParam);
    }


    /**
     * 즐겨찾기 정보 를 삭제한다.
     *
     * @param pmParam 즐겨찾기 정보
     * @throws Exception
     */
    public int deleteBkmkInfo(Map<String, Object> pmParam) throws Exception {
        return delete("BkmkInfoMap.deleteBkmkInfo", pmParam);
    }

    /**
     * 바로가기의 중복 건수를 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 권한 순서의 중복 건수
     * @throws Exception
     */
    public int getBkmkInfoDupCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("BkmkInfoMap.getBkmkInfoDupCount", pmParam);
    }

}
