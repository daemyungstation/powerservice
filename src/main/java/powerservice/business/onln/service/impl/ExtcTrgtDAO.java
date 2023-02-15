/*
 * (@)# DlwCmsDAO.java
 *
 * @author 석민
 * @version 1.0
 * @date 2016/02/02
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


@Repository
public class ExtcTrgtDAO extends EgovAbstractMapper {

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
     * 대상고객추출 정보의 검색 수를 반환한다.(TM/가입)
     *
     * @param pmParam 검색 조건
     * @return 대상고객추출조건 정보의 검색 수
     * @throws Exception
     */
    public int getExtcTrgtCount4100(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("ExtcTrgtMap.getExtcTrgtCount4100", pmParam);
    }

    /**
     * 대상고객추출 정보를 검색한다.(TM/가입)
     *
     * @param pmParam 검색 조건
     * @return 대상고객추출조건 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getExtcTrgtList4100(Map<String, ?> pmParam) throws Exception {
        return selectList("ExtcTrgtMap.getExtcTrgtList4100", pmParam);
    }

    /**
     * 온라인 상태값을 변경한다.(추출)
     *
     * @param pmParam 검색 조건
     * @return 대상고객추출조건 리스트
     * @throws Exception
     */
    public int updateOnlineStat(Map<String, ?> pmParam) throws Exception {
        return update("ExtcTrgtMap.updateOnlineStat", pmParam);
    }

    /**
     * 온라인 상태값을 변경한다.(할당) 사용
     *
     * @param pmParam 검색 조건
     * @return 대상고객추출조건 리스트
     * @throws Exception
     */
    public int updateOnlineStatAlct(Map<String, ?> pmParam) throws Exception {
        return update("ExtcTrgtMap.updateOnlineStatAlct", pmParam);
    }

    /**
     * 온라인 상담 등록
     *
     * @param
     * @return
     * @throws Exception
     */
    public int insertOnlnCons(Map<String, ?> pmParam) throws Exception {
        return insert("ExtcTrgtMap.insertOnlnCons", pmParam);
    }

    /**
     * 온라인 상담 등록 시퀀스 업뎉이트
     *
     * @param
     * @return
     * @throws Exception
     */
    public int updateOnlineSeq(Map<String, ?> pmParam) throws Exception {
        return update("ExtcTrgtMap.updateOnlineSeq", pmParam);
    }
        
    /**
     * 온라인 상담 등록
     *
     * @param
     * @return
     * @throws Exception
     */
    public int insertOnlnUplusCons(Map<String, ?> pmParam) throws Exception {
        return insert("ExtcTrgtMap.insertOnlnUplusCons", pmParam);
    }
}
