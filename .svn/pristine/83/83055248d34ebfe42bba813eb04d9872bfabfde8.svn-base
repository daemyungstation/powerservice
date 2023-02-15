/*
 * (@)# DlwProdTrntDAO.java
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/06/21
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
 * 상품 변환 정보를 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/06/21
 * @프로그램ID DlwProdTrnt
 */
@Repository
public class DlwProdTrntDAO extends EgovAbstractMapper {

    /**
     * 대명라이프웨이 상품 변환 정보 DB SqlSession을 설정한다.
     *
     * @param sqlSession 대명라이프웨이 상품 변환 정보 DB SqlSession
     */
    @Resource(name="dlwSqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }

    /**
     * 공제 해지필요건 확인
     *
     * @param pmParam 검색 조건
     * @return 공제 해지필요건 확인
     * @throws Exception
     */
    public String getDedAppYn(Map<String, ?> pmParam) throws Exception {
        return (String) selectOne("DlwProdTrntMap.getDedAppYn", pmParam);
    }

    /**
     * 양도양수나 상품변경 공제 미신고건 확인
     *
     * @param pmParam 검색 조건
     * @return 양도양수나 상품변경 공제 미신고건 확인
     * @throws Exception
     */
    public String getChkProdTransDedAppYn(Map<String, ?> pmParam) throws Exception {
        return (String) selectOne("DlwProdTrntMap.getChkProdTransDedAppYn", pmParam);
    }

    /**
     * 상품 변환
     *
     * @param pmParam 검색 조건
     * @return 상품 변환
     * @throws Exception
     */
    public int updateTransMemProdAccnt(Map<String, ?> pmParam) throws Exception {
        return (Integer) update("DlwProdTrntMap.updateTransMemProdAccnt", pmParam);
    }

    /**
     * 상품 변경 이력 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 상품 변경 이력 정보의 검색 건수
     * @throws Exception
     */
    public int getProdTrntHstrCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwProdTrntMap.getProdTrntHstrCount", pmParam);
    }

    /**
     * 상품 변경 이력 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상품 변경 이력 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getProdTrntHstrList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwProdTrntMap.getProdTrntHstrList", pmParam);
    }

}
