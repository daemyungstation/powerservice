/*
 * (@)# DlwCnctRtamtDAO.java
 *
 * @author 석민
 * @version 1.0
 * @date 2016/04/05
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
 * 대명라이프웨이 해약환급금 정보를 관리한다
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 석민
 * @version 1.0
 * @date 2016/04/05
 * @프로그램ID DlwCnctRtamt
 */
@Repository
public class DlwCnctRtamtDAO extends EgovAbstractMapper {

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
     * 대명라이프웨이 해약환급금 M 목록 건수를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getResnAmtCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCnctRtamtMap.getResnAmtCount", pmParam);
    }

    /**
     * 대명라이프웨이 해약환급금 M 목록을 검색한다.
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getResnAmtList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCnctRtamtMap.getResnAmtList", pmParam);
    }

    /**
     * 대명라이프웨이 해약환급금 D 목록 건수를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getResnAmtDetailCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCnctRtamtMap.getResnAmtDetailCount", pmParam);
    }

    /**
     * 대명라이프웨이 해약환급금 D 목록을 검색한다.
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getResnAmtDetailList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCnctRtamtMap.getResnAmtDetailList", pmParam);
    }

    /**
     * 대명라이프웨이 해약환급금 (Master)을 등록한다.
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertResnAmtHd(Map<String, ?> pmParam) throws Exception {
        return insert("DlwCnctRtamtMap.insertResnAmtHd", pmParam);
    }

    /**
     * 대명라이프웨이 해약환급금 (Detail)을 등록한다.
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertResnAmtDt(Map<String, ?> pmParam) throws Exception {
        return insert("DlwCnctRtamtMap.insertResnAmtDt", pmParam);
    }

    /**
     * 대명라이프웨이 해약환급금 (Master)을 수정한다.
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateResnAmtHd(Map<String, ?> pmParam) throws Exception {
        return update("DlwCnctRtamtMap.updateResnAmtHd", pmParam);
    }

    /**
     * 대명라이프웨이 해약환급금 (Detail)을 수정한다.
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateResnAmtDt(Map<String, ?> pmParam) throws Exception {
        return insert("DlwCnctRtamtMap.updateResnAmtDt", pmParam);
    }

    /**
     * 해약환급금 등록 중복 체크.
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String checkResnFg(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwCnctRtamtMap.checkResnFg", pmParam);
    }

    /**
     * 해약환급금 마스터 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteResnAmtM(Map<String, ?> pmParam) throws Exception {
        return delete("DlwCnctRtamtMap.deleteResnAmtM", pmParam);
    }

    /**
     * 해약환급금 상세 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteResnAmtD(Map<String, ?> pmParam) throws Exception {
        return delete("DlwCnctRtamtMap.deleteResnAmtD", pmParam);
    }
}
