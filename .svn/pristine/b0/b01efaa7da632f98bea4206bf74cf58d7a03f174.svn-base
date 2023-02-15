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
public class DlwCouponInfoDAO extends EgovAbstractMapper {

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
     * 쿠폰 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 리스트 정보
     * @throws Exception
     */
    public List<Map<String, Object>> selectCouponInfoList(Map<String, Object> pmParam) throws Exception {
        return selectList("DlwCouponInfoMap.selectCouponInfoList", pmParam);
    }


    /**
     * 쿠폰현황 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 리스트 정보
     * @throws Exception
     */
    public List<Map<String, Object>> selectCouponStatList(Map<String, Object> pmParam) throws Exception {
        return selectList("DlwCouponInfoMap.selectCouponStatList", pmParam);
    }

    /**
     * 쿠폰정보 상세 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public Map<String, Object> selectCouponDetail(Map<String, Object> pmParam) throws Exception {
        return selectOne("DlwCouponInfoMap.selectCouponDetail", pmParam);
    }


    /**
     * 쿠폰종류 별 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public Map<String, Object> selectCouponTpSearch(Map<String, Object> pmParam) throws Exception {
        return selectOne("DlwCouponInfoMap.selectCouponTpSearch", pmParam);
    }


    /**
     * 쿠폰정보 이력을 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 리스트 정보
     * @throws Exception
     */
    public List<Map<String, Object>> selectCouponHist(Map<String, Object> pmParam) throws Exception {
        return selectList("DlwCouponInfoMap.selectCouponHist", pmParam);
    }

    /**
     * 쿠폰번호 중복 체크
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String selectDupCouponNo(Map<String, Object> pmParam) throws Exception {
        return selectOne("DlwCouponInfoMap.selectDupCouponNo", pmParam);
    }




    /**
     *  쿠폰정보 MST 등록
     *
     * @param pmParam 검색 조건
     * @return 신규상품 등록
     * @throws Exception
     */
    public int insertCouponInfoMst(Map<String, Object> pmParam) throws Exception {
        return insert("DlwCouponInfoMap.insertCouponInfoMst", pmParam);
    }


    /**
     *  쿠폰정보 DTL 등록
     *
     * @param pmParam 검색 조건
     * @return 신규상품 등록
     * @throws Exception
     */
    public int insertCouponInfoDtl(Map<String, Object> pmParam) throws Exception {
        return insert("DlwCouponInfoMap.insertCouponInfoDtl", pmParam);
    }


    /**
     * 쿠폰정보 MST 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateCouponInfoMst(Map<String, Object> pmParam) throws Exception {
        return update("DlwCouponInfoMap.updateCouponInfoMst", pmParam);
    }


    /**
     * 쿠폰정보 DTL 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateCouponInfoDtl(Map<String, Object> pmParam) throws Exception {
        return update("DlwCouponInfoMap.updateCouponInfoDtl", pmParam);
    }


    /**
     * 쿠폰정보 MST 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteCouponInfoMst(Map<String, Object> pmParam) throws Exception {
        return delete("DlwCouponInfoMap.deleteCouponInfoMst", pmParam);
    }


    /**
     * 쿠폰정보 사용유무 MST UPDATE
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateCouponInfoMstUseYn(Map<String, Object> pmParam) throws Exception {
        return update("DlwCouponInfoMap.updateCouponInfoMstUseYn", pmParam);
    }


    /**
     * 쿠폰정보 사용유무 DTL UPDATE
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateCouponInfoDtlUseYn(Map<String, Object> pmParam) throws Exception {
        return update("DlwCouponInfoMap.updateCouponInfoDtlUseYn", pmParam);
    }


    /**
     * 쿠폰정보 DTL 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteCouponInfoDtl(Map<String, Object> pmParam) throws Exception {
        return delete("DlwCouponInfoMap.deleteCouponInfoDtl", pmParam);
    }



    /**
    *
    * 쿠폰정보 MST 키 select
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
    public String selectKeyCouponMst(Map<String, Object> pmParam) throws Exception {
        return (String)selectOne("DlwCouponInfoMap.selectKeyCouponMst", pmParam);
    }

    /**
    *
    * 쿠폰정보 DTL 키 select
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
    public String selectKeyCouponDtl(Map<String, Object> pmParam) throws Exception {
        return (String)selectOne("DlwCouponInfoMap.selectKeyCouponDtl", pmParam);
    }

}
