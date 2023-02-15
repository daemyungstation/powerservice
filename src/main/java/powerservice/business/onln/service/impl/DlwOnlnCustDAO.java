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
public class DlwOnlnCustDAO extends EgovAbstractMapper {

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
    public int getDlwOnlnCustJnerCount(Map<String, ?> pmParam) throws Exception {
        return (Integer)selectOne("DlwOnlnCustMap.getDlwOnlnCustJnerCount", pmParam);
    }

    /**
     * 온라인회원 가입자 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 온라인회원 가입자 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwOnlnCustJnerList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwOnlnCustMap.getDlwOnlnCustJnerList", pmParam);
    }

    /**
     * 회원고유번호 생성 전 변환 중복 체크
     *
     * @param pmParam 검색 조건
     * @return 회원고유번호
     * @throws Exception
     */
    public String getOnlnMemNo(Map<String, ?> pmParam) throws Exception {
        return (String) selectOne("DlwOnlnCustMap.getOnlnMemNo", pmParam);
    }

    /**
     * 회원고유번호 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateMemMstUnqNo(Map<String, ?> pmParam) throws Exception {
        return update("DlwOnlnCustMap.updateMemMstUnqNo", pmParam);
    }

    /**
     * 변환 상태 변경
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateMemChngPtcYn(Map<String, ?> pmParam) throws Exception {
        return update("DlwOnlnCustMap.updateMemChngPtcYn", pmParam);
    }

    /**
     * 변환 상태 변경
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateOnlnProdMst(Map<String, ?> pmParam) throws Exception {
        return update("DlwOnlnCustMap.updateOnlnProdMst", pmParam);
    }

    /**
     * 회원고유번호 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertOnlnProdLog(Map<String, ?> pmParam) throws Exception {
        return insert("DlwOnlnCustMap.insertOnlnProdLog", pmParam);
    }

}
