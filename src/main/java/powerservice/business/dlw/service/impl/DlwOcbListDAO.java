/*
 * (@)# DlwProdDAO.java
 *
 * @author 석민
 * @version 1.0
 * @date 2016/02/16
 * Copyright (c) 2016 by Inwoo tech inc, KOREA. All Rights Reserved.
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

package powerservice.business.dlw.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * OCB 정보를 관리한다.
 *
 * Copyright (c) 2016 Company Sinan Soft
 *
 * @author 김준혁
 * @version 1.0
 * @date 2016/10/19
 * @프로그램ID DlwProd
 */
@Repository
public class DlwOcbListDAO extends EgovAbstractMapper {

    /**
     * 대명라이프웨이 OCB DB SqlSession을 설정한다.
     *
     * @param sqlSession 대명라이프웨이 사원정보 DB SqlSession
     */
    @Resource(name="dlwSqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }

    /**
     * 조직정보 트리뷰
     *
     * @param pmParam 검색 조건
     * @return 조직도 트리
     * @throws Exception
     */
    public List<Map<String, Object>> getOcbTreeList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwOcbListMap.getOcbTreeList", pmParam);
    }

    /**
     * 조직 소속 사원 조회
     *
     * @param pmParam 검색 조건
     * @return 조직도 트리
     * @throws Exception
     */
    public List<Map<String, Object>> getGrpEmpList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwOcbListMap.getGrpEmpList", pmParam);
    }

    /**
     * 조직도 입력
     *
     * @param pmParam 검색 조건
     * @return 등록 건수
     * @throws Exception
     */
    public int insertGrpDept(Map<String, ?> pmParam) throws Exception {
        return insert("DlwOcbListMap.insertGrpDept", pmParam);
    }

    /**
     * 조직도 수정
     *
     * @param pmParam 검색 조건
     * @return 등록 건수
     * @throws Exception
     */
    public int updateGrpDept(Map<String, ?> pmParam) throws Exception {
        return insert("DlwOcbListMap.updateGrpDept", pmParam);
    }

    /**
     * 조직도 삭제
     *
     * @param pmParam 검색 조건
     * @return 등록 건수
     * @throws Exception
     */
    public int deleteGrpDept(Map<String, ?> pmParam) throws Exception {
        return insert("DlwOcbListMap.deleteGrpDept", pmParam);
    }


    /**
     * OCB 연회비 산출 이력 조회
     *
     * @param pmParam 검색 조건
     * @return OCB 연회비 산출 이력 조회
     * @throws Exception
     */
    public List<Map<String, Object>> getOcbAnnHist(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwOcbListMap.getOcbAnnHist", pmParam);
    }

    /**
     * OCB 연회비 산출 조회
     *
     * @param pmParam 검색 조건
     * @return OCB 연회비 산출
     * @throws Exception
     */
    public List<Map<String, Object>> getOcbAnnCac(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwOcbListMap.getOcbAnnCac", pmParam);
    }

    /**
     * OCB 연회비 산출insert
     *
     * @param pmParam 검색 조건
     * @return 등록 건수
     * @throws Exception
     */
    public int insertocbyearsave(Map<String, ?> pmParam) throws Exception {
        return insert("DlwOcbListMap.insertocbyearsave", pmParam);
    }


}
