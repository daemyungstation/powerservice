/*
 * (@)# DlwCertfDAO.java
 *
 * @author 석민
 * @version 1.0
 * @date 2016/06/17
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

package powerservice.business.dlw.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 대명라이프웨이 증서 정보를 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 석민
 * @version 1.0
 * @date 2016/06/17
 * @프로그램ID DlwCertfDAO
 */
@Repository
public class DlwCertfDAO extends EgovAbstractMapper {

    /**
     * 대명라이프웨이 DB SqlSession을 설정한다.
     *
     * @param sqlSession 대명라이프웨이 DB SqlSession
     */
    @Resource(name="dlwSqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }

    /**
     * 증서 출력 정보 수
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getCertfCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCertfMap.getCertfCount", pmParam);
    }

    /**
     * 증서 출력 정보
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getCertfList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCertfMap.getCertfList", pmParam);
    }

   /**
    * 증서관리
    *
    */
    public List<Map<String, Object>> getCertfprodList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCertfMap.getCertfprodList", pmParam);
    }

    /**
     * 증서 발급 여부 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getCertPrintChk(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCertfMap.getCertPrintChk", pmParam);
    }

    /**
     * 증서발급 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertCertHist(Map<String, ?> pmParam) throws Exception {
        return insert("DlwCertfMap.insertCertHist", pmParam);
    }

    /**
     * 증서 재발행 리스트
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getjengseList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCertfMap.getjengseList", pmParam);
    }

    /**
     * 증서  재발행 정보 수
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getjengseCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCertfMap.getjengseCount", pmParam);
    }


}
