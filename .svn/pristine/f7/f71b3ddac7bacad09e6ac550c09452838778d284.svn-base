/*
 * (@)# DlwOnlnCustDAO.java
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/04/20
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

package powerservice.business.onln.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 온라인 회원 정보를 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/04/20
 * @프로그램ID DlwOnlnCust
 */
@Repository
public class SmartLifeDAO extends EgovAbstractMapper {

    /**
     * 대명라이프웨이 온라인 회원 DB SqlSession을 설정한다.
     *
     * @param sqlSession 대명라이프웨이 온라인 회원정보 DB SqlSession
     */
    @Resource(name="onlnSqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }

    /**
     * 온라인회원 가입자 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 온라인회원 가입자 정보의 검색 건수
     * @throws Exception
     */
    public int getMainCount(Map<String, ?> pmParam) throws Exception {
        return (Integer)selectOne("SmartLifeMap.getMainCount", pmParam);
    }

    /**
     * 온라인회원 가입자 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 온라인회원 가입자 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getMainList(Map<String, ?> pmParam) throws Exception {
        return selectList("SmartLifeMap.getMainList", pmParam);
    }
    
    public int existsSmartLifeListData(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("SmartLifeMap.existsSmartLifeListData", pmParam);
    }
    
    /**
     * 온라인 상태값을 변경한다.(할당) 사용
     *
     * @param pmParam 검색 조건
     * @return 대상고객추출조건 리스트
     * @throws Exception
     */
    public int updateOnlineStatAlct(Map<String, ?> pmParam) throws Exception {
        return update("SmartLifeMap.updateOnlineStatAlct", pmParam);
    }
    
    /**
     * 온라인회원 가입자 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 온라인회원 가입자 정보의 검색 건수
     * @throws Exception
     */
    public int getDliveMainCount(Map<String, ?> pmParam) throws Exception {
        return (Integer)selectOne("SmartLifeMap.getDliveMainCount", pmParam);
    }

    /**
     * 온라인회원 가입자 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 온라인회원 가입자 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDliveMainList(Map<String, ?> pmParam) throws Exception {
        return selectList("SmartLifeMap.getDliveMainList", pmParam);
    }
    
    /**
     * 온라인회원 가입자 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 온라인회원 가입자 정보의 검색 건수
     * @throws Exception
     */
    public int getCuckooMainCount(Map<String, ?> pmParam) throws Exception {
        return (Integer)selectOne("SmartLifeMap.getCuckooMainCount", pmParam);
    }

    /**
     * 온라인회원 가입자 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 온라인회원 가입자 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getCuckooMainList(Map<String, ?> pmParam) throws Exception {
        return selectList("SmartLifeMap.getCuckooMainList", pmParam);
    }
}
