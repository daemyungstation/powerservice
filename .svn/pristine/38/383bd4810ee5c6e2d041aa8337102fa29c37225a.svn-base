/*
 * (@)# DataAthrQuryDAO.java
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/02/03
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

import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 데이터 권한별 검색조건을 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/02/03
 * @프로그램ID DataAthrQury
 */
@Repository
public class DataAthrQuryDAO extends EgovAbstractMapper {

    /**
     * 대명라이프웨이 권한별 조회조건 DB SqlSession을 설정한다.
     *
     * @param sqlSession 대명라이프웨이 권한별 조회조건 DB SqlSession
     */
    @Resource(name="dlwSqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }

    /**
     * 사원 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 사원 정보 목록
     * @throws Exception
     */
    public String getDataAthrQury(Map<String, ?> pmParam) throws Exception {
        return selectOne("DataAthrQuryMap.getDataAthrQury", pmParam);
    }

}
