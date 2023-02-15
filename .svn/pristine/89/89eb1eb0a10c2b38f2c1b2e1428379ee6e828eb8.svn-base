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
public class DlwTvFormatDAO extends EgovAbstractMapper {

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
     * 방송편성 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 리스트 정보
     * @throws Exception
     */
    public List<Map<String, Object>> selectTvFormatList(Map<String, Object> pmParam) throws Exception {
        return selectList("DlwTvFormatMap.selectTvFormatList", pmParam);
    }


    /**
     * 캠페인 서브대상목록 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 리스트 정보
     * @throws Exception
     */
    public List<Map<String, Object>> selectCompaignList(Map<String, Object> pmParam) throws Exception {
        return selectList("DlwTvFormatMap.selectCompaignList", pmParam);
    }


    /**
     * 방송편성 MST 상세 조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public Map<String, Object> selectTvFormatMstDetail(Map<String, Object> pmParam) throws Exception {
        return selectOne("DlwTvFormatMap.selectTvFormatMstDetail", pmParam);
    }


    /**
     * 방송편성 DTL 상세 조회
     *
     * @param pmParam 검색 조건
     * @return 리스트 정보
     * @throws Exception
     */
    public List<Map<String, Object>> selectTvFormatDtlDetail(Map<String, Object> pmParam) throws Exception {
        return selectList("DlwTvFormatMap.selectTvFormatDtlDetail", pmParam);
    }


    /**
     *  방송편성 MST 등록
     *
     * @param pmParam 검색 조건
     * @return 신규상품 등록
     * @throws Exception
     */
    public int insertTvFormatMst(Map<String, Object> pmParam) throws Exception {
        return insert("DlwTvFormatMap.insertTvFormatMst", pmParam);
    }


    /**
     *  방송편성 DTL 등록
     *
     * @param pmParam 검색 조건
     * @return 신규상품 등록
     * @throws Exception
     */
    public int insertTvFormatDtl(Map<String, Object> pmParam) throws Exception {
        return insert("DlwTvFormatMap.insertTvFormatDtl", pmParam);
    }


    /**
     * 방송편성 MST 수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateTvFormatMst(Map<String, Object> pmParam) throws Exception {
        return update("DlwTvFormatMap.updateTvFormatMst", pmParam);
    }



    /**
     * 방송편성 MST 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteTvFormatMst(Map<String, Object> pmParam) throws Exception {
        return delete("DlwTvFormatMap.deleteTvFormatMst", pmParam);
    }


    /**
     * 방송편성 DTL 삭제
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int deleteTvFormatDtl(Map<String, Object> pmParam) throws Exception {
        return delete("DlwTvFormatMap.deleteTvFormatDtl", pmParam);
    }


    /**
    *
    * 방송편성 MST 키 select
    * @param pmParam 검색 조건
    * @return
    * @throws Exception
    */
    public String selectKeyTvFormatMst(Map<String, Object> pmParam) throws Exception {
        return (String)selectOne("DlwTvFormatMap.selectKeyTvFormatMst", pmParam);
    }


}
