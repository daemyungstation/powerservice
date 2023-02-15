/*
 * (@)# DlwProdDAO.java
 *
 * @author 석민
 * @version 1.0
 * @date 2016/02/16
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

import powerservice.core.util.ParamUtils;

import com.tobesoft.xplatform.data.DataSet;

import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 상품별 부가서비스를 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 석민
 * @version 1.0
 * @date 2016/02/16
 * @프로그램ID DlwVatSvc
 */
@Repository
public class DlwProdVasDAO extends EgovAbstractMapper {

    /**
     * 대명라이프웨이 상품정보 DB SqlSession을 설정한다.
     *
     * @param sqlSession 대명라이프웨이 사원정보 DB SqlSession
     */
    @Resource(name="dlwSqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }


    /**
     * 상품별 부가서비스 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상품 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> selectProdOptSvcMstList(Map<String, Object> pmParam) throws Exception {
        return selectList("DlwProdVasMap.selectProdOptSvcMstList", pmParam);
    }

    /**
     * 상품코드 콤보 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상품 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> selectProdList(Map<String, Object> pmParam) throws Exception {
        return selectList("DlwProdVasMap.selectProdList", pmParam);
    }


    /**
     * 부가서비스 상세 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> selectOptSvcList(Map<String, Object> pmParam) throws Exception {
        return selectList("DlwProdVasMap.selectOptSvcList", pmParam);
    }

    /**
     * 부가서비스 상세 조회3
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public Map<String, Object> selectDetailVas(Map<String, Object> pmParam) throws Exception {
        return selectOne("DlwProdVasMap.selectDetailVas", pmParam);
    }


    /**
     * 상품별 부가서비스 DTL 상세조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> selectProdOptSvcDtl(Map<String, Object> pmParam) throws Exception {
        return selectList("DlwProdVasMap.selectProdOptSvcDtl", pmParam);
    }


    /**
     *  상품별 부가서비스 마스터 등록
     *
     * @param pmParam 검색 조건
     * @return 신규상품 등록
     * @throws Exception
     */
    public int insertProdOptSvcMst(Map<String, Object> pmParam) throws Exception {
        return insert("DlwProdVasMap.insertProdOptSvcMst", pmParam);
    }


    /**
     *
     * 마스터 키 select
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String selectKeyOptSvcMst(Map<String, Object> pmParam) throws Exception {
        return (String)selectOne("DlwProdVasMap.selectKeyOptSvcMst", pmParam);
    }



    /**
     * 상품별 부가서비스 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateProdOptSvc(Map<String, Object> pmParam) throws Exception {
        return update("DlwProdVasMap.updateProdOptSvcMst", pmParam);
    }


    /**
     * 상품별 부가서비스 상세 정보 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteProdOptSvcDtl(Map<String, Object> pmParam) throws Exception {
        return delete("DlwProdVasMap.deleteProdOptSvcDtl", pmParam);
    }


    /**
     * 상품별 부가서비스 상세 정보 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteProdOptSvc(Map<String, Object> pmParam) throws Exception {
        return delete("DlwProdVasMap.deleteProdOptSvc", pmParam);
    }



    /**
     *  상품별 부가서비스 상세 정보 등록
     *
     * @param pmParam 검색 조건
     * @return 신규상품 등록
     * @throws Exception
     */
    public int insertProdOptSvcDtl(Map<String, Object> pmParam) throws Exception {
        return insert("DlwProdVasMap.insertProdOptSvcDtl", pmParam);
    }


    /**
     * 상품권 종류 저장 관련1
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateOptSvc(Map<String, Object> pmParam) throws Exception {
        return update("DlwProdVasMap.updateOptSvc", pmParam);
    }


    /**
     *  상품권 종류 저장 관련2
     *
     * @param pmParam 검색 조건
     * @return 신규상품 등록
     * @throws Exception
     */
    public int insertCouponNoYMS(Map<String, Object> pmParam) throws Exception {
        return insert("DlwProdVasMap.insertCouponNoYMS", pmParam);
    }


    /**
     * 상품권 종류 저장 관련3
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateOtherCrtNo1(Map<String, Object> pmParam) throws Exception {
        return update("DlwProdVasMap.updateOtherCrtNo1", pmParam);
    }


    /**
     *  상품권 종류 저장 관련4
     *
     * @param pmParam 검색 조건
     * @return 신규상품 등록
     * @throws Exception
     */
    public int insertOptSvc(Map<String, Object> pmParam) throws Exception {
        return insert("DlwProdVasMap.insertOptSvc", pmParam);
    }

}
