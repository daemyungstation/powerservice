/*
 * (@)# DlwEmplDAO.java
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/02/02
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
 * 사원 정보를 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/02/02
 * @프로그램ID DlwEmpl
 */
@Repository
public class DlwEmplDAO extends EgovAbstractMapper {

    /**
     * 대명라이프웨이 사원정보 DB SqlSession을 설정한다.
     *
     * @param sqlSession 대명라이프웨이 사원정보 DB SqlSession
     */
    @Resource(name="dlwSqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }

    /**
     * 사원 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 사원 정보의 검색 건수
     * @throws Exception
     */
    public int getDlwEmplCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwEmplMap.getDlwEmplCount", pmParam);
    }

    /**
     * 사원 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 사원 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwEmplList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEmplMap.getDlwEmplList", pmParam);
    }

    /**
     * 사원 상세정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 사원 상세정보
     * @throws Exception
     */
    public Map<String, Object> getDlwEmplDtpt(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwEmplMap.getDlwEmplList", pmParam);
    }

    /**
     * 사원 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 사원 정보의 검색 건수
     * @throws Exception
     */
    public int getEmplCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwEmplMap.getEmplCount", pmParam);
    }

    /**
     * 사원 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 사원 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getEmplList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwEmplMap.getEmplList", pmParam);
    }

    /**
     * 사원정보를 저장한다.
     *
     * @param pmParam 검색 조건
     * @return 사원정보
     * @throws Exception
     */
    public int mergeEmpl(Map<String, ?> pmParam) throws Exception {
        return insert("DlwEmplMap.mergeEmpl", pmParam);
    }

}
