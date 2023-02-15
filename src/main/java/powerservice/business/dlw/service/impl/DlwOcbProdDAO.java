/*
 * (@)# DlwOcbProdDAO.java
 *
 * @author 석민
 * @version 1.0
 * @date 2016/04/11
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
 * 대명라이프웨이 OCB 상품 정보를 관리한다
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 석민
 * @version 1.0
 * @date 2016/04/11
 * @프로그램ID DlwOcbProd
 */
@Repository
public class DlwOcbProdDAO extends EgovAbstractMapper {

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
     * 대명라이프웨이 OCB 적립 상품 조회 건수를 조회한다.
     *
     * @param pmParam 검색 조건
     * @throws Exception
     */
    public int getOcbProdCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwOcbProdMap.getOcbProdCount", pmParam);
    }

    /**
     * 대명라이프웨이 OCB 적립 상품 정보를 조회한다.
     *
     * @param pmParam 검색 조건
     * @throws Exception
     */
    public List<Map<String, Object>> getOcbProdList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwOcbProdMap.getOcbProdList", pmParam);
    }

    /**
     * 대명라이프웨이 OCB 적립 상품정보를 등록한다.
     *
     * @param pmParam 검색 조건
     * @throws Exception
     */
    public int insertOcbProd(Map<String, ?> pmParam) throws Exception {
        return insert("DlwOcbProdMap.insertOcbProd", pmParam);
    }

    /**
     * 대명라이프웨이 OCB 적립 상품정보를 수정한다.
     *
     * @param pmParam 검색 조건
     * @throws Exception
     */
    public int updateOcbProd(Map<String, ?> pmParam) throws Exception {
        return update("DlwOcbProdMap.updateOcbProd", pmParam);
    }

    /**
     * 대명라이프웨이 OCB이력 카운트
     *
     * @param pmParam 검색 조건
     * @throws Exception
     */
    public int getOcbProdSaveCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwOcbProdMap.getOcbProdSaveCount", pmParam);
    }


    /**
     * 대명라이프웨이 OCB 이력 건수를 조회한다.
     *
     * @param pmParam 검색 조건
     * @throws Exception
     */
    public int getOcbTransHistCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwOcbProdMap.getOcbTransHistCount", pmParam);
    }

    /**
     * 대명라이프웨이 OCB 이력 정보를 조회한다.
     *
     * @param pmParam 검색 조건
     * @throws Exception
     */
    public List<Map<String, Object>> getOcbTransHistList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwOcbProdMap.getOcbTransHistList", pmParam);
    }

    /**
     * 대명라이프웨이 OCB상품 적립 전송 정보를 조회한다.
     *
     * @param pmParam 검색 조건
     * @throws Exception
     */
    public List<Map<String, Object>> getOcbTransList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwOcbProdMap.getOcbTransList", pmParam);
    }

    /**
     * 대명라이프웨이 OCB상품 취소 전송 정보를 조회한다.
     *
     * @param pmParam 검색 조건
     * @throws Exception
     */
    public List<Map<String, Object>> getOcbDelList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwOcbProdMap.getOcbDelList", pmParam);
    }

    /**
     * 대명라이프웨이 OCB상품(적립) 취소 전송 정보를 조회한다.
     *
     * @param pmParam 검색 조건
     * @throws Exception
     */
    public List<Map<String, Object>> getOcbSavDelList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwOcbProdMap.getOcbSavDelList", pmParam);
    }


    /**
     * 대명라이프웨이 OCB상품(사용) 취소 전송 정보를 조회한다.
     *
     * @param pmParam 검색 조건
     * @throws Exception
     */
    public List<Map<String, Object>> getOcbUseDelList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwOcbProdMap.getOcbUseDelList", pmParam);
    }

    /**
     * 대명라이프웨이 OCB상품 적립 전송 정보를 조회한다.
     * 임동진 수정
     * @param pmParam 검색 조건
     * @throws Exception
     */
    public List<Map<String, Object>> getOcbAddList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwOcbProdMap.getOcbAddList", pmParam);
    }

    /**
     * 대명라이프웨이 OCB상품 사용 전송 정보를 조회한다.
     * 임동진 수정
     * @param pmParam 검색 조건
     * @throws Exception
     */
    public List<Map<String, Object>> getOcbUseList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwOcbProdMap.getOcbUseList", pmParam);
    }



    /**
     * 대명라이프웨이 OCB 전송 이력 카운트
     *
     * @param pmParam 검색 조건
     * @throws Exception
     */
    public int getOcbTransHistCnt() throws Exception {
        return selectOne("DlwOcbProdMap.getOcbTransHistCnt");
    }

    /**
     * 대명라이프웨이 OCB 전송이력 등록
     *
     * @param pmParam 검색 조건
     * @throws Exception
     */
    public int insertOcbTransHist(Map<String, ?> pmParam) throws Exception {
        return insert("DlwOcbProdMap.insertOcbTransHist", pmParam);
    }

    /**
     * 대명라이프웨이 OCB 전송이력 수정
     *
     * @param pmParam 검색 조건
     * @throws Exception
     */
    public int updateOcbTransHist(Map<String, ?> pmParam) throws Exception {
        return update("DlwOcbProdMap.updateOcbTransHist", pmParam);
    }

}
