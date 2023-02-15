/*
 * (@)# CdDAO.java
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

package powerservice.business.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 코드 정보 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID Cd
 */
@Repository
public class CdDAO extends EgovAbstractMapper {

    /**
     * 대명 기초 코드 정보를 등록한다.
     *
     * @param pmParam 코드 정보
     * @throws Exception
     */
    public int insertComCd(Map<String, ?> pmParam) throws Exception {
        return insert("CdMap.insertComCd", pmParam);
    }


    /**
     * 코드 정보를 등록한다.
     *
     * @param pmParam 코드 정보
     * @throws Exception
     */
    public int insertCd(Map<String, ?> pmParam) throws Exception {
        return insert("CdMap.insertCd", pmParam);
    }

    /**
     * 대명 기초 코드 정보를 수정한다.
     *
     * @param pmParam 코드 정보
     * @throws Exception
     */
    public int updateComCd(Map<String, ?> pmParam) throws Exception {
        return update("CdMap.updateComCd", pmParam);
    }

    /**
     * 코드 정보를 수정한다.
     *
     * @param pmParam 코드 정보
     * @throws Exception
     */
    public int updateCd(Map<String, ?> pmParam) throws Exception {
        return update("CdMap.updateCd", pmParam);
    }

    /**
     * 코드 순서를 수정한다.
     *
     * @param pmParam 코드 정보
     * @throws Exception
     */
    public int updateComCdSequence(Map<String, ?> pmParam) throws Exception {
        return update("CdMap.updateComCdSequence", pmParam);
    }


    /**
     * 코드 순서를 수정한다.
     *
     * @param pmParam 코드 정보
     * @throws Exception
     */
    public int updateCdSequence(Map<String, ?> pmParam) throws Exception {
        return update("CdMap.updateCdSequence", pmParam);
    }

    /**
     * 코드 정보2의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 대명기초코드
     * @throws Exception
     */
    public int getComCdCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("CdMap.getCdCount", pmParam);
    }


    /**
     * 코드 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 코드 정보의 검색 수
     * @throws Exception
     */
    public int getCdCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("CdMap.getCdCount", pmParam);
    }

    /**
     * 코드 정보(목록)를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 코드 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getCdList(Map<String, ?> pmParam) throws Exception {
        return selectList("CdMap.getCdList", pmParam);
    }

    /**
     * 코드 정보(목록)를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 코드 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getCdMemList(Map<String, ?> pmParam) throws Exception {
        return selectList("CdMap.getCdMemList", pmParam);
    }

    /**
     * 코드 정보(1건)를 검색한다.
     *
     * @param param 검색 조건
     * @return 코드 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getCd(Map<String, ?> pmParam) throws Exception {
        return selectOne("CdMap.getCdList", pmParam);
    }

    /**
     * 코드셋 목록에 따라 코드 정보(목록)를 검색한다.
     *
     * @param sCdSetList 검색 조건
     * @return 코드 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getCdList(String[] sCdSetList) throws Exception {
        return selectList("CdMap.getCdListByStringArray", sCdSetList);
    }

    /**
     * 코드 순서 최대값을 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 순서 최대값
     * @throws Exception
     */
    public int getCdMaxSequence(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("CdMap.getCdMaxSequence", pmParam);
    }

    /**
     * 코드 순서의 중복 건수를 조회한다
     *
     * @param pmParam 검색 조건
     * @return 코드 순서의 중복 건수
     * @throws Exception
     */
    public int getCdDuplicateCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("CdMap.getCdDuplicateCount", pmParam);
    }

}
