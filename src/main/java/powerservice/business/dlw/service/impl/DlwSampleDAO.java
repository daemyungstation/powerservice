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

package powerservice.business.dlw.service.impl;

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
public class DlwSampleDAO extends EgovAbstractMapper {
	
    /**
     * 대명라이프웨이 해약정보 DB SqlSession을 설정한다.
     *
     * @param sqlSession 대명라이프웨이 해약 DB SqlSession
     */
    @Resource(name="dlwSqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }

    
    /**
     * 프로시져를 이용한 목록 조회2
     *
     * @param pmParam 검색 조건
     * @return MO수신 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getListByProc2(Map<String, Object> pmParam) throws Exception {
    	System.out.println("SampDAO.getListByProc2() called...");
    	update("DlwSampleMap.getListByProc2", pmParam);
        List<Map<String, Object>> lstRslt = (List<Map<String, Object>>)pmParam.get("rslt");
        return lstRslt;
    }
}
