/*
 * (@)# TblDtlDAO.java
 *
 * @author 김현경
 * @version 1.0
 * @date 2015/04/27
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

package powerservice.business.mta.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 테이블 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 김현경
 * @version 1.0
 * @date 2015/04/27
 * @프로그램ID TblDtl
 */
@Repository
public class TblDtlDAO extends EgovAbstractMapper {

     /**
     * 테이블 관리의 테이블 상세정보의 검색 수를 반환한다. (15.04.27)
     *
     * @param pmParam 검색 조건
     * @return 테이블 관리 정보의 검색 수
     * @throws Exception
     */
    public int getTblDtlCount(Map<String, ?> pmParam) throws Exception {
        return (Integer)selectOne("TblDtlMap.getTblDtlCount", pmParam);
    }

    /**
     * 테이블 관리의 테이블 상세정보를 검색한다. (15.04.27)
     *
     * @param pmParam 검색 조건
     * @return 테이블 관리/상세정보 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getTblDtlList(Map<String, ?> pmParam) throws Exception {
        return selectList("TblDtlMap.getTblDtlList", pmParam);
    }

    /**
     * 테이블 관리의 테이블 상세정보를 등록한다. (15.04.28)
     *
     * @param pmParam 테이블 상세정보
     * @throws Exception
     */
    public int insertTblDtl(Map<String, ?> pmParam) throws Exception {
        return insert("TblDtlMap.insertTblDtl", pmParam);
    }

    /**
     * 테이블 관리의 테이블 상세정보를 수정한다. (15.04.28)
     *
     * @param pmParam 테이블 상세정보
     * @throws Exception
     */
    public int updateTblDtl(Map<String, ?> pmParam) throws Exception {
        return update("TblDtlMap.updateTblDtl", pmParam);
    }

    /**
     * 테이블 관리의 테이블 상세정보를 삭제한다. (15.04.28)
     *
     * @pmParam pmParam 테이블 상세정보
     * @throws Exception
     */
    public int deleteTblDtl(Map<String, ?> pmParam) throws Exception {
        return delete("TblDtlMap.deleteTblDtl", pmParam);
    }

    /**
     * 테이블 관리의 테이블 상세정보를 검색한다. (15.04.29)
     *
     * @param id 테이블 영문명
     * @return 테이블상세 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getTblDtl(Map<String, ?> pmParam) throws Exception {
        return selectOne("TblDtlMap.getTblDtlList", pmParam);
    }

    /**
     * 테이블 관리의 칼럼 상세정보의 검색 수를 반환한다. (15.04.27)
     *
     * @param pmParam 검색 조건
     * @return 테이블 관리 정보의 칼럼 상세정보 검색 수
     * @throws Exception
     */
    public int getTclDtlCount(Map<String, ?> pmParam) throws Exception {
        return (Integer)selectOne("TblDtlMap.getTclDtlCount", pmParam);
    }

    /**
     * 테이블 관리의 칼럼 상세정보를 검색한다. (15.04.27)
     *
     * @param pmParam 검색 조건
     * @return 칼럼 상세정보 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getTclDtlList(Map<String, ?> pmParam) throws Exception {
        return selectList("TblDtlMap.getTclDtlList", pmParam);
    }

    /**
     * 테이블 관리의 테이블 상세정보를 삭제한다. (15.05.11)
     *
     * @pmParam pmParam 테이블 상세정보
     * @throws Exception
     */
    public int deleteTclDtl(Map<String, ?> pmParam) throws Exception {
        return delete("TblDtlMap.deleteTclDtl", pmParam);
    }

    /**
     * 테이블 관리의 칼럼 상세정보 순서의 중복 건수를 조회한다 (15.05.07)
     *
     * @param pmParamHash 검색 조건
     * @return 칼럼 상세정보 순서의 중복 건수
     * @throws Exception
     */
    public int getTclDtlDuplicateCount(Map<String, ?> pmParam) throws Exception {
        return (Integer)selectOne("TblDtlMap.getTclDtlDuplicateCount", pmParam);
    }

    /**
     * 테이블 관리의 칼럼 상세정보 순서 최대값을 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 칼럼 상세정보 순서 최대값
     * @throws Exception
     */
    public int getTclDtlMaxOrder(Map<String, ?> pmParam) throws Exception {
        return (Integer)selectOne("TblDtlMap.getTclDtlMaxOrder", pmParam);
    }

    /**
     * 테이블 관리의 칼럼 상세정보를 검색한다. (15.04.29)
     *
     * @param id 테이블 영문명
     * @return 테이블 칼럼상세 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getTclDtl(Map<String, ?> pmParam) throws Exception {
        return selectOne("TblDtlMap.getTclDtlList", pmParam);
    }

    /**
     * 테이블 관리의 칼럼 상세정보를 등록한다. (15.05.07)
     *
     * @param pmParam 테이블 칼럼 상세정보
     * @throws Exception
     */
    public int insertTclDtl(Map<String, ?> pmParam) throws Exception {
        return insert("TblDtlMap.insertTclDtl", pmParam);
    }

    /**
     * 테이블 관리의 칼럼 상세정보를 수정한다. (15.05.07)
     *
     * @param pmParam 테이블 칼럼 상세정보
     * @throws Exception
     */
    public int updateTclDtl(Map<String, ?> pmParam) throws Exception {
        return update("TblDtlMap.updateTclDtl", pmParam);
    }

    /**
     * 테이블 관리의 칼럼 상세정보 순서를 수정한다. (15.05.07)
     *
     * @param pmParamHash 검색 조건
     * @return 칼럼 상세정보 순서 증가
     * @throws Exception
     */
    public int updateTclDtlOrd(Map<String, ?> pmParam) throws Exception {
        return update("TblDtlMap.updateTclDtlOrd", pmParam);
    }

    /**
     * 테이블 관리의 칼럼 상세정보 중복체크를 한다.  (15.05.07)
     *
     * @param nm 용어 영문 명
     * @return 단어사전 정보(1건)
     * @throws Exception
     */
    public int checkTclDtl(Map<String, ?> pmParam) throws Exception {
        return (Integer)selectOne("TblDtlMap.TclDtlCheck", pmParam);
    }

    /**
     * 테이블 관리의 테이블 상세정보의 검색 수를 반환한다. (15.04.27)
     *
     * @param pmParam 검색 조건
     * @return 테이블 관리 정보의 검색 수
     * @throws Exception
     */
    public int getReferTblDtlCount(Map<String, ?> pmParam) throws Exception {
        return (Integer)selectOne("TblDtlMap.getReferTblDtlCount", pmParam);
    }

    /**
     * 테이블 관리의 테이블 상세정보를 검색한다. (15.05.13)
     *
     * @param pmParam 검색 조건
     * @return 테이블 관리/상세정보 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getReferTblDtl(Map<String, ?> pmParam) throws Exception {
        return selectList("TblDtlMap.getReferTblDtl", pmParam);
    }
}
