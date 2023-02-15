/*
 * (@)# SampleDAO.java
 *
 * @author 홍길동
 * @version 1.0
 * @date 2016/01/02
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

package powerservice.business.sample.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * (테스트)샘플정보를 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 홍길동
 * @version 1.0
 * @date 2016/01/02
 * @프로그램ID Sample
 */
@Repository
public class SampleDAO extends EgovAbstractMapper {

    /**
     * (테스트)샘플정보를 입력한다.
     *
     * @param pmParam (테스트)샘플정보
     * @return
     * @throws Exception
     */
    public int insertSample(Map<String, Object> pmParam) throws Exception {
        return insert("SampleMap.insertSample", pmParam);
    }

    /**
     * (테스트)샘플정보를 수정한다.
     *
     * @param pmParam (테스트)샘플정보
     * @throws Exception
     */
    public int updateSample(Map<String, Object> pmParam) throws Exception {
        return update("SampleMap.updateSample", pmParam);
    }

    /**
     * (테스트)샘플정보를 삭제한다.
     *
     * @param pmParam (테스트)샘플정보
     * @throws Exception
     */
    public int deleteSample(Map<String, Object> pmParam) throws Exception {
        return update("SampleMap.deleteSample", pmParam);
    }

    /**
     * (테스트)샘플정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return (테스트)샘플정보의 검색 수
     * @throws Exception
     */
    public int getSampleCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("SampleMap.getSampleCount", pmParam);
    }

    /**
     * (테스트)샘플정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return (테스트)샘플정보 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getSampleList(Map<String, ?> pmParam) throws Exception {
        return selectList("SampleMap.getSampleList", pmParam);
    }
    
    /* 정출연 추가 */
    /**
     * 대명라이프웨이 DB SqlSession을 설정한다.
     *
     * @param sqlSession 대명라이프웨이 DB SqlSession
     */
//    @Resource(name="dlwSqlSession")
//    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
//        super.setSqlSessionFactory(sqlSession);
//    }
    
    /**
     * 프로시져를 이용한 목록 조회
     *
     * @param pmParam 검색 조건
     * @return MO수신 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getListByProc(Map<String, Object> pmParam) throws Exception {
    	System.out.println("SampDAO.getListByProc() called...");
    	System.out.println(super.getSqlSession().getConfiguration().toString());
    	
    	update("SampleMap.getListByProc", pmParam);
        List<Map<String, Object>> lstRslt = (List<Map<String, Object>>)pmParam.get("rslt");
        return lstRslt;
    }
    
    /**
     * 샘플데이터 PRODUCT 조회
     *
     * @param pmParam 검색 조건
     * @return MO수신 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getProductList(Map<String, Object> pmParam) throws Exception {
        return selectList("SampleMap.getProductList", pmParam);
    }
    
    /**
     * PLSQL 블럭 실행 테스트
     *
     * @param pmParam 검색 조건
     * @return MO수신 목록
     * @throws Exception
     */
    public void insertTestPlSql(Map<String, Object> pmParam) throws Exception {
        insert("SampleMap.insertTestPlSql", pmParam);
    }
}
