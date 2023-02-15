/*
 * (@)# PgmDAO.java
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
 * 프로그램 정보 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID Pgm
 */
@Repository
public class PgmDAO extends EgovAbstractMapper {

    /**
     * 프로그램 정보를 등록한다.
     *
     * @param pmParam 프로그램 정보
     * @throws Exception
     */
    public int insertPgm(Map<String, ?> pmParam) throws Exception {
        return insert("PgmMap.insertPgm", pmParam);
    }

    /**
     * 프로그램 정보를 수정한다.
     *
     * @param pmParam 프로그램 정보
     * @throws Exception
     */
    public int updatePgm(Map<String, ?> pmParam) throws Exception {
        return update("PgmMap.updatePgm", pmParam);
    }

    /**
     * 프로그램 순서를 수정한다.
     *
     * @param pmParam 프로그램 순서
     * @throws Exception
     */
    public int updatePgmSequence(Map<String, ?> pmParam) throws Exception {
        return update("PgmMap.updatePgmSequence", pmParam);
    }

    /**
     * 프로그램 정보를 삭제한다.
     *
     * @param param 프로그램 정보
     * @throws Exception
     */
    public int deletePgm(Map<String, ?> pmParam) throws Exception {
        return delete("PgmMap.deletePgm", pmParam);
    }

    /**
     * 프로그램 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 프로그램 정보의 검색 건수
     * @throws Exception
     */
    public int getPgmCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("PgmMap.getPgmCount", pmParam);
    }

    /**
     * 프로그램 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 프로그램 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getPgmList(Map<String, ?> pmParam) throws Exception {
        return selectList("PgmMap.getPgmList", pmParam);
    }

    /**
     * 프로그램 정보를 검색한다.
     *
     * @param pmParam 프로그램ID
     * @return 프로그램 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getPgm(Map<String, ?> pmParam) throws Exception {
        return selectOne("PgmMap.getPgmList", pmParam);
    }

    /**
     * 프로그램 트리 목록을 검색한다.
     *
     * @param paramHash 검색 조건
     * @return 프로그램 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getPgmTree(Map<String, ?> pmParam) throws Exception {
        return selectList("PgmMap.getPgmTree", pmParam);
    }

    /**
     * 프로그램 순서 최대값을 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 순서 최대값
     * @throws Exception
     */
    public int getPgmMaxSequence(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("PgmMap.getPgmMaxSequence", pmParam);
    }

    /**
     * 프로그램 순서의 중복 건수를 조회한다
     *
     * @param pmParam 검색 조건
     * @return 프로그램 순서의 중복 건수
     * @throws Exception
     */
    public int getPgmDuplicateCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("PgmMap.getPgmDuplicateCount", pmParam);
    }

    /**
     * 카드 청구시 카드결재 팝업 여부
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateCardPop(Map<String, ?> pmParam) throws Exception {
        return update("PgmMap.updateCardPop", pmParam);
    }

    /**
     * 프로그램 정보를 검색한다.
     *
     * @param pmParam 프로그램ID
     * @return 프로그램 정보(1건)
     * @throws Exception
     */

    public List<Map<String, Object>> getCardPoplist(Map<String, ?> pmParam) throws Exception {
        return selectList("PgmMap.getCardPoplist", pmParam);
    }

}
