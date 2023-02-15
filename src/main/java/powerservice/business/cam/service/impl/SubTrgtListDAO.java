/*
 * (@)# SursDAO.java
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
 * 서브타겟 리스트 관리를 한다.
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정훈
 * @version 1.0
 * @date 2016/05/10
 * @프로그램ID SubTrgtListDAO
 */

@Repository
public class SubTrgtListDAO extends EgovAbstractMapper {

    /**
     * 서브타겟 리스트의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 서브타겟 리스트의 검색 수
     * @throws Exception
     */
    public int getSubTrgtListCount(Map<String, ?> pmParam) throws Exception {
        return (Integer)selectOne("SubTrgtListMap.getSubTrgtListCount", pmParam);
    }

    /**
     * 서브타겟 리스트의 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 서브타겟 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getSubTrgtList(Map<String, ?> pmParam) throws Exception {
        return selectList("SubTrgtListMap.getSubTrgtList", pmParam);
    }

    /**
     * 서브타겟 리스트를 등록한다.
     *
     * @param pmParam 서브타겟 리스트
     * @throws Exception
     */
    public int insertSubTrgtList(Map<String, ?> pmParam) throws Exception {
        return insert("SubTrgtListMap.insertSubTrgtList", pmParam);
    }

    /**
     * 서브타겟 리스트를 수정한다.
     *
     * @param pmParam 서브타겟 리스트
     * @throws Exception
     */
    public int updateSubTrgtList(Map<String, ?> pmParam) throws Exception {
        return update("SubTrgtListMap.updateSubTrgtList", pmParam);
    }

    /**
     *서브타겟 리스트를 삭제한다.
     *
     * @param pmParam 서브타겟 리스트
     * @throws Exception
     */
    public int deleteSubTrgtList(Map<String, ?> pmParam) throws Exception {
        return delete("SubTrgtListMap.deleteSubTrgtList", pmParam);
    }

    /**
     * 대상리스트 정보를 삭제한다.(소분류리스트)
     *
     * @param pmParam 대상리스트 정보
     * @throws Exception
     */
    public int deleteTrgtCustByTrgtList(Map<String, ?> pmParam) throws Exception {
         return delete("SubTrgtListMap.deleteTrgtCustByTrgtList", pmParam); // 대상고객 삭제
    }

    /**
     * 대상 정보를 저장한다.(DB로 부터 추출)
     *
     * @param pmParam 서브 대상리스트 정보
     * @throws Exception
     */
    public int insertTrgtCustByDB(Map<String, Object> pmParam) throws Exception {
        return insert("SubTrgtListMap.insertTrgtCustByDB", pmParam);
    }

    /**
     * 대상 정보를 등록한다.
     *
     * @param pmParam 서브 대상리스트 정보
     * @throws Exception
     */
    public int insertOneTrgtCust(Map<String, ?> pmParam) throws Exception {
        return insert("SubTrgtListMap.insertOneTrgtCust", pmParam);
    }

    /**
     * 대상 정보를 저장한다.(File로 부터 추출)
     *
     * @param pmParam 서브 대상리스트 정보
     * @throws Exception
     */
    public int insertTrgtCustByExcel(Map<String, ?> pmParam) throws Exception {
        return insert("SubTrgtListMap.insertTrgtCustByExcel", pmParam);
    }

    /**
     * 캠페인의 서브타겟별 대상 수와 배정건수를 조회한다. --!
     *
     * @param String 캠페인 ID
     * @return 캠페인의 할당건수(1건)
     * @throws Exception
     */
    public Map<String, Object> getAssigNum(Map<String, Object> pmParam) throws Exception {
        return selectOne("SubTrgtListMap.getAssigNum", pmParam);
    }


    /**
     * 서브타겟 리스트의 검색 수를 반환한다.(캠페인별 대상목록 현황)
     *
     * @param pmParam 검색 조건
     * @return 서브타겟 리스트의 검색 수
     * @throws Exception
     */
    public int getSubTrgtStatByCmpgCount(Map<String, ?> pmParam) throws Exception {
        return (Integer)selectOne("SubTrgtListMap.getSubTrgtStatByCmpgCount", pmParam);
    }

    /**
     * 서브타겟 리스트 정보를 검색한다.(캠페인별 대상목록 현황)
     *
     * @param pmParam 검색 조건
     * @return 서브타겟 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getSubTrgtStatByCmpgList(Map<String, ?> pmParam) throws Exception {
        return selectList("SubTrgtListMap.getSubTrgtStatByCmpgList", pmParam);
    }

}
