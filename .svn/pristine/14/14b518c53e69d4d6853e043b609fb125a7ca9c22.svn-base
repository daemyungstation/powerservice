/*
 * (@)# DlwOnlnPymnAcntDAO.java
 *
 * @author 석민
 * @version 1.0
 * @date 2016/04/22
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
 * 온라인회원 결제계좌 정보를 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 석민
 * @version 1.0
 * @date 2016/04/22
 * @프로그램ID DlwOnlnPymnAcnt
 */
@Repository
public class DlwOnlnPymnAcntDAO extends EgovAbstractMapper {

    /**
     * 대명라이프웨이 온라인 DB SqlSession을 설정한다.
     *
     * @param sqlSession 대명라이프웨이 온라인정보 DB SqlSession
     */
    @Resource(name="onlnSqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }

    /**
     * 온라인회원 결제계좌 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 온라인회원 결제계좌 정보의 검색 건수
     * @throws Exception
     */
    public int getOnlnPymnAcntChngCount(Map<String, ?> pmParam) throws Exception {
        return (Integer)selectOne("DlwOnlnPymnAcntMap.getOnlnPymnAcntChngCount", pmParam);
    }

    /**
     * 온라인회원 결제계좌 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 온라인회원 결제계좌 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getOnlnPymnAcntChngList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwOnlnPymnAcntMap.getOnlnPymnAcntChngList", pmParam);
    }

    /**
     * 온라인회원 결제계좌 변경 변환완료여부를 업데이트한다.
     *
     * @param pmParam 검색 조건
     * @return 온라인회원 결제계좌 정보의 검색 건수
     * @throws Exception
     */
    public int updateOnlnTrntCmplAcntChng(Map<String, ?> pmParam) throws Exception {
        return update("DlwOnlnPymnAcntMap.updateOnlnTrntCmplAcntChng", pmParam);
    }

    /**
     * 온라인회원 회원정보 변경 변환완료여부를 업데이트한다.
     *
     * @param pmParam 검색 조건
     * @return 온라인회원 결제계좌 정보의 검색 건수
     * @throws Exception
     */
    public int updateOnlnTrntCmplMemChng(Map<String, ?> pmParam) throws Exception {
        return update("DlwOnlnPymnAcntMap.updateOnlnTrntCmplMemChng", pmParam);
    }
}
