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
 * 부가서비스를 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 석민
 * @version 1.0
 * @date 2016/02/16
 * @프로그램ID DlwVatSvc
 */
@Repository
public class DlwVatSvcDAO extends EgovAbstractMapper {

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
     * 부가서비스 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상품 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getVatSvcList(Map<String, Object> pmParam) throws Exception {
        //return selectList("DlwVatSvcMap.selectVatSvcList", pmParam);
    	return selectList("DlwVatSvcMap.selectVatSvcList_New", pmParam);
    }


    /**
     *   관련 체크 쿼리
     *
     * @param pmParam 검색 조건
     * @return 상품 정보 목록
     * @throws Exception
     */
    public Map<String, Object> selectSvcExceptIssueHist(Map<String, Object> pmParam) throws Exception {
        return selectOne("DlwVatSvcMap.selectSvcExceptIssueHist", pmParam);
    }



    /**
     *   관련 체크 쿼리
     *
     * @param pmParam 검색 조건
     * @return 상품 정보 목록
     * @throws Exception
     */

    public List<Map<String, Object>> selectSvcList(Map<String, Object> pmParam) throws Exception {
        return selectList("DlwVatSvcMap.selectSvcList",pmParam);
    }


    /**
     *   관련 체크 쿼리
     *
     * @param pmParam 검색 조건
     * @return 상품 정보 목록
     * @throws Exception
     */

    public List<Map<String, Object>> selectMemProdAccntSvc(Map<String, Object> pmParam) throws Exception {
        return selectList("DlwVatSvcMap.selectMemProdAccntSvc", pmParam);
    }


    /**
     *
     *
     * @param pmParam 검색 조건
     * @return 상품 정보 목록
     * @throws Exception
     */

    public List<Map<String, Object>> selectSvcIssueHist(Map<String, Object> pmParam) throws Exception {
        return selectList("DlwVatSvcMap.selectSvcIssueHist", pmParam);
    }


    /**
     * 저장되어 있는 회원번호별 부가서비스관리 이력 조회
     *
     * @param pmParam 검색 조건
     * @return map
     * @throws Exception
     */

    public List<Map<String, Object>> selectSvcAccntNoHist(Map<String, Object> pmParam) throws Exception {
        return selectList("DlwVatSvcMap.selectSvcAccntNoHist", pmParam);
    }


    /**
     * 저장되어 있는 회원번호별 전체 부가서비스 이력 조회
     *
     * @param pmParam 검색 조건
     * @return map
     * @throws Exception
     */

    public List<Map<String, Object>> selectMemProdAccntSvcTot(Map<String, Object> pmParam) throws Exception {
        return selectList("DlwVatSvcMap.selectMemProdAccntSvcTot", pmParam);
    }



    /**
     * 부가서비스 발급이력관리 등록
     *
     * @param pmParam 검색 조건
     * @return 신규상품 등록
     * @throws Exception
     */
    public int insertSvcIsuHist(Map<String, Object> pmParam) throws Exception {
        return insert("DlwVatSvcMap.insertSvcIsuHist", pmParam);
    }

    /**
     * 상담내역 Detail 등록
     *
     * @param pmParam 검색 조건
     * @return 신규상품 등록
     * @throws Exception
     */
    public int insertConsulDetail(Map<String, Object> pmParam) throws Exception {
        return insert("DlwVatSvcMap.insertConsulDetail", pmParam);
    }

    /**
     * 상담내역 마스터 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertConsulMng(Map<String, Object> pmParam) throws Exception {
        return insert("DlwVatSvcMap.insertConsulMng", pmParam);
    }


    /**
     * 나무병원 쿠폰일련번호 삽입
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertCouponNoPerDayYMS(Map<String, Object> pmParam) throws Exception {
        return insert("DlwVatSvcMap.insertCouponNoPerDayYMS", pmParam);
    }


    /**
     * 쿠폰일련번호 삽입(SVC_CD가 0001인 것 제외)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertCouponNoYM(Map<String, Object> pmParam) throws Exception {
        return insert("DlwVatSvcMap.insertCouponNoYM", pmParam);
    }


    /**
     * [나무병원(SVC_CD = 0014) 당일 발급정보가 있으면 업데이트]
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateOtherCrtNoPerDay1(Map<String, Object> pmParam) throws Exception {
        return update("DlwVatSvcMap.updateOtherCrtNoPerDay1", pmParam);
    }


    /**
     * [쿠폰일련번호 수정(SVC_CD가 0001인 것 제외)]
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateOtherCrtNo(Map<String, Object> pmParam) throws Exception {
        return update("DlwVatSvcMap.updateOtherCrtNo", pmParam);
    }


    /**
     * [쿠폰일련번호 수정]
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateCrtNo(Map<String, Object> pmParam) throws Exception {
        return update("DlwVatSvcMap.updateCrtNo", pmParam);
    }

    /**
     * 우편번호 업로드 관련 update
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateVatSvc(Map<String, Object> pmParam) throws Exception {
        return update("DlwVatSvcMap.updateVatSvc", pmParam);
    }




    /**
     * 엑셀업로드 오류건 등록
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int insertExcelUploadErr(Map<String, Object> pmParam) throws Exception {
        return insert("DlwVatSvcMap.insertExcelUploadErr", pmParam);
    }


    /**
     * 부가서비스 등록 중복 체크.
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public Map<String, Object> selectKeyInsertConsulMng(Map<String, Object> pmParam) throws Exception {
        return selectOne("DlwVatSvcMap.selectKeyInsertConsulMng", pmParam);
    }


    /**
     * 삭제
     *
     * @param pmParam 검색 조건
     * @return 신규상품 등록
     * @throws Exception
     */
    public int deleteSvcIsuHist(Map<String, Object> pmParam) throws Exception {
        return delete("DlwVatSvcMap.deleteSvcIsuHist", pmParam);
    }


    /**
     * 부가서비스무효화
     *
     * @param pmParam 검색 조건
     * @return 신규상품 등록
     * @throws Exception
     */
    public int insertVatSvcInvalid(Map<String, Object> pmParam) throws Exception {
        return insert("DlwVatSvcMap.insertVatSvcInvalid", pmParam);
    }

    /**
     * 부가서비스무효화
     *
     * @param pmParam 검색 조건
     * @return 신규상품 등록
     * @throws Exception
     */
    public int updateVatSvcInvalid(Map<String, Object> pmParam) throws Exception {
        return update("DlwVatSvcMap.updateVatSvcInvalid", pmParam);
    }





    /**
     * 부가서비스 등록 중복 체크.
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String selectDupIsuNo(Map<String, Object> pmParam) throws Exception {
        return selectOne("DlwVatSvcMap.selectDupIsuNo", pmParam);
    }

    /**
     * 부가서비스 등록 중복 체크.
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String selectCrtNo(Map<String, Object> pmParam) throws Exception {
        return selectOne("DlwVatSvcMap.selectCrtNo", pmParam);
    }

    /**
     * 중복 체크
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String dupNoChk(Map<String, Object> pmParam) throws Exception {
        return selectOne("DlwVatSvcMap.dupNoChk", pmParam);
    }


    /**
     * 당일에 해당하는 쿠폰번호 있는지 확인
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String selectCrtNoPerDaySearch(Map<String, Object> pmParam) throws Exception {
        return selectOne("DlwVatSvcMap.selectCrtNoPerDaySearch", pmParam);
    }


    /**
     * 쿠폰일련번호 생성
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String selectOtherCrtNoPerDay(Map<String, Object> pmParam) throws Exception {
        return selectOne("DlwVatSvcMap.selectOtherCrtNoPerDay", pmParam);
    }


    /**
     * [당년,월에 해당하는 쿠폰번호 있는지 확인]
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String selectCrtNoSearch(Map<String, Object> pmParam) throws Exception {
        return selectOne("DlwVatSvcMap.selectCrtNoSearch", pmParam);
    }

    /**
     *
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String selectOtherCrtNo(Map<String, Object> pmParam) throws Exception {
        return selectOne("DlwVatSvcMap.selectOtherCrtNo", pmParam);
    }



    /**
     *  상담에서 변경되면 부가서비스 이력에도 수정하도록 처리하는 로직
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int updateCallCenterVatSvcHist(Map<String, Object> pmParam) throws Exception {
        return update("DlwVatSvcMap.updateCallCenterVatSvcHist", pmParam);
    }


}
