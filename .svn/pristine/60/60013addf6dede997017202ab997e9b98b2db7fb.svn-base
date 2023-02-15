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

import powerservice.common.util.CommonUtils;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 상품 정보를 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 석민
 * @version 1.0
 * @date 2016/02/16
 * @프로그램ID DlwProd
 */
@Repository
public class DlwProdDAO extends EgovAbstractMapper {


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
     * 상품 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 상품 정보의 검색 건수
     * @throws Exception
     */
    public int getDlwProdCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwProdMap.getDlwProdCount", pmParam);
    }

    /**
     * 신규등록시 상품코드 있는지 확인..
     *
     * @param pmParam 검색 조건
     * @return 상품 정보의 검색 건수
     * @throws Exception
     */
    public int getDlwProdNewCODE(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwProdMap.getDlwProdNewCODE", pmParam);
    }
    /**
     * 상품 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상품 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwProdList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwProdMap.getDlwProdList", pmParam);
    }

    /**
     * 상품조회_상품상세정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상품 상세정보
     * @throws Exception
     */
    public Map<String, Object> getDlwProdDtpt(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwProdMap.getDlwProdDtpt", pmParam);
    }

    /**
     * 상품조회_상품상세정보를 검색한다.(비정기형 월청구금액)
     *
     * @param pmParam 검색 조건
     * @return 비정기형 월청구금액 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwProductDtl(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwProdMap.getDlwProductDtl", pmParam);
    }

    /**
     * 상품조회_상품상세정보를 검색한다.(멤버쉽카드종류)
     *
     * @param pmParam 검색 조건
     * @return 멤버쉽카드종류
     * @throws Exception
     */
    public List<Map<String, Object>> getOCBCardCodeList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwProdMap.getOCBCardCodeList", pmParam);
    }

    /**
     * 상품상세분류(모델Mst) 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상품상세분류(모델Mst) 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getModelMstInfo(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwProdMap.getModelMstInfo", pmParam);
    }

    /**
     * 모델리스트(모델 Dtl) 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 모델리스트(모델 Dtl) 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getModelDtlInfo(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwProdMap.getModelDtlInfo", pmParam);
    }

    /**
     * 상품종류리스트 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 상품종류리스트 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getProdKindList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwProdMap.getProdKindList", pmParam);
    }

    /**
     * 발송/반송 관리에서 고유번호+회원명+우대번호 조회
     *
     * @param pmParam 검색 조건
     * @return 상품종류리스트 정보 목록
     * @throws Exception
     */
    public Map<String, Object> getMemProdAccntWithDlv(String psParam) throws Exception {
        return selectOne("DlwProdMap.getMemProdAccntWithDlv", psParam);
    }

    /**
     * 신규상품 등록
     *
     * @param pmParam 검색 조건
     * @return 신규상품 등록
     * @throws Exception
     */
    public int insertProdNew(Map<String, ?> pmParam) throws Exception {
        return insert("DlwProdMap.insertProdNew", pmParam);
    }


    /**
     * 상품정보 수정
     *
     * @param pmParam 검색 조건
     * @return 상품정보 수정
     * @throws Exception
     */
    public int updateProdChng(Map<String, ?> pmParam) throws Exception {
        return update("DlwProdMap.updateProdChng", pmParam);
    }

    /**
     * 상품정보 삭제
     *
     * @param pmParam 검색 조건
     * @return 상품정보 수정
     * @throws Exception
     */
    public int deleteProd(Map<String, ?> pmParam) throws Exception {
        return update("DlwProdMap.deleteProd", pmParam);
    }


    /**
     * 신규 상품상세 등록
     *
     * @param pmParam 검색 조건
     * @return 신규상품상세 등록
     * @throws Exception
     */
    public int insertProdDtl(Map<String, ?> pmParam) throws Exception {
        return insert("DlwProdMap.insertProdDtl", pmParam);
    }


    /**
     * 상품상세 정보 삭제
     *
     * @param pmParam 검색 조건
     * @return 상품상세 정보 수정
     * @throws Exception
     */
    public int deleteProdDtl(Map<String, ?> pmParam) throws Exception {
        return update("DlwProdMap.deleteProdDtl", pmParam);
    }

    /**
     * 상품별 상세분류 콤보 리스트 목록
     *
     * @param pmParam 검색 조건
     * @return 상품별 상세분류 콤보 리스트 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getProdDtlList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwProdMap.getProdDtlList", pmParam);
    }

    /**
     * 상품별 상세분류 리스트 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 상품 정보의 검색 건수
     * @throws Exception
     */
    public int getClassProdCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwProdMap.getClassProdCount", pmParam);
    }

    /**
     * 상품별 상세분류 리스트 조회
     *
     * @param pmParam 검색 조건
     * @return 상품별 상세분류 리스트 조회
     * @throws Exception
     */
    public List<Map<String, Object>> getClassProdList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwProdMap.getClassProdList", pmParam);
    }

    /**
     * 상품별 상세분류 선택 행 삭제
     *
     * @param pmParam 검색 조건
     * @return 상품별 상세분류 선택 행 삭제
     * @throws Exception
     */
    public int getRowProdDel(Map<String, ?> pmParam) throws Exception {
        return update("DlwProdMap.getRowProdDel", pmParam);
    }

    /**
     * 상품별 상세 등록
     *
     * @param pmParam 검색 조건
     * @return 상품별 상세 등록
     * @throws Exception
     */
    public int insertProdKindNew(Map<String, ?> pmParam) throws Exception {
        return insert("DlwProdMap.insertProdKindNew", pmParam);
    }

    /**
     * 상품별 모델 분류 기초 코드 리스트 건수
     *
     * @param pmParam 검색 조건
     * @return 상품별 모델 분류 기초 코드 리스트 건수
     * @throws Exception
     */
    public int getProdModelCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwProdMap.getProdModelCount", pmParam);
    }

    /**
     * 상품별 모델 분류 기초 코드 관리 조회
     *
     * @param pmParam 검색 조건
     * @return 상품별 모델 분류 기초 코드 관리 조회
     * @throws Exception
     */
    public List<Map<String, Object>> getProdModelList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwProdMap.getProdModelList", pmParam);
    }

    /**
     * 기간별 상품 의전원가 조회 건수
     *
     * @param pmParam 검색 조건
     * @return 상품별 기간별 상품 의전원가 조회 건수
     * @throws Exception
     */
    public int getPeriodProdCostCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwProdMap.getPeriodProdCostCount", pmParam);
    }

    /**
     * 기간별 상품 의전원가 조회
     *
     * @param pmParam 검색 조건
     * @return 기간별 상품 의전원가 조회
     * @throws Exception
     */
    public List<Map<String, Object>> getPeriodProdCost(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwProdMap.getPeriodProdCost", pmParam);
    }

    /**
     * 기간별 상품 의전원가 등록
     *
     * @param pmParam 검색 조건
     * @return 기간별 상품 의전원가 등록
     * @throws Exception
     */
    public int insertPeriodProdCost(Map<String, ?> pmParam) throws Exception {
        return insert("DlwProdMap.insertPeriodProdCost", pmParam);
    }

    /**
     * 기간별 상품 의전원가 입력가능여부 체크1
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String checkPeriodProdCost1(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwProdMap.checkPeriodProdCost1", pmParam);
    }

    /**
     * 기간별 상품 의전원가 입력가능여부 체크2
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String checkPeriodProdCost2(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwProdMap.checkPeriodProdCost2", pmParam);
    }

    /**
     * 기간별 상품 의전원가 입력가능여부 체크3
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String checkPeriodProdCost3(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwProdMap.checkPeriodProdCost3", pmParam);
    }

    /**
     * 기간별 상품 의전원가 삭제
     *
     * @param pmParam 검색 조건
     * @return 기간별 상품 의전원가 삭제
     * @throws Exception
     */
    public int getRowPeriodProdCostDel(Map<String, ?> pmParam) throws Exception {
        return update("DlwProdMap.getRowPeriodProdCostDel", pmParam);
    }

    /**
     * 기간별 상품 의전원가 수정 (** 등록 또는 삭제시 이어서 수정)
     *
     * @param pmParam 검색 조건
     * @return 기간별 상품 의전원가 수정
     * @throws Exception
     */
    public int updatePeriodProdCost(Map<String, ?> pmParam) throws Exception {
        return update("DlwProdMap.updatePeriodProdCost", pmParam);
    }


    /**
     * 양도양수 리스트 조회
     *
     * @param pmParam 검색 조건
     * @return 상품별 모델 분류 기초 코드 관리 조회
     * @throws Exception
     */
    public List<Map<String, Object>> getTransfList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwProdMap.getTransfList", pmParam);
    }


    /**
     * 신규 상품분류 등록
     *
     * @param pmParam 검색 조건
     * @return int
     * @throws Exception
     */
    public int insertProdModelClCd(Map<String, ?> pmParam) throws Exception {

        /*
        int rowType = ((Integer) pmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();
        System.out.println("rowType===========>"+rowType);

        if (rowType == DataSet.ROW_TYPE_INSERTED){ //INSERT
            return insert("DlwProdMap.insertProdModelClCd", pmParam);
        }else if (rowType == DataSet.ROW_TYPE_UPDATED){
            return update("DlwProdMap.updateProdModelClCd", pmParam);
        }else{
            return rowType;
        }
        */
        return insert("DlwProdMap.insertProdModelClCd", pmParam);
    }


    /**
     * 상품모델구분코드 수정
     *
     * @param pmParam 검색 조건
     * @return int
     * @throws Exception
     */
    public int updateProdModelClCd(Map<String, ?> pmParam) throws Exception {
        return update("DlwProdMap.updateProdModelClCd", pmParam);
    }


    public int updateProdtl(Map<String, ?> pmParam) throws Exception {
        return update("DlwProdMap.updateProdtl", pmParam);
    }


    public int updateProd_dtl(Map<String, ?> pmParam) throws Exception {
        return update("DlwProdMap.updateProd_dtl", pmParam);
    }
    /**
     * 상품모델구분코드 삭제
     *
     * @param pmParam 검색 조건
     * @return int
     * @throws Exception
     */
    public void deleteProdModelClCd(Map<String, ?> pmParam) throws Exception {
        delete("DlwProdMap.deleteProdModelClCd", pmParam);
    }


    /**
     * 상품모델구분코드 삭제 (DEL_FG='Y')
     *
     * @param pmParam 검색 조건
     * @return int
     * @throws Exception
     */
    public void deleteFgProdModelClCd(Map<String, Object> pmParam) throws Exception {
        update("DlwProdMap.deleteFgProdModelClCd", pmParam);
    }


    /**
     * 신규등록시 상품코드 있는지 확인..
     *
     * @param pmParam 검색 조건
     * @return 상품 정보의 검색 건수
     * @throws Exception
     */
    public String getModelClCd(Map<String, ?> pmParam) throws Exception {
        return (String) selectOne("DlwProdMap.getModelClCd", pmParam);
    }


    /**
     * 결합상품 종류 관리 등록
     *
     * @param pmParam 검색 조건
     * @return int
     * @throws Exception
     */
    public int insertProdKind(Map<String, ?> pmParam) throws Exception {
        return insert("DlwProdMap.insertProdKind", pmParam);
    }

    /**
     * 결합상품 종류 관리 삭제
     *
     * @param pmParam 검색 조건
     * @return int
     * @throws Exception
     */
    public int deleteProdKind(Map<String, ?> pmParam) throws Exception {
        return delete("DlwProdMap.deleteProdKind", pmParam);
    }


    /**
     * 결합상품 종류 관리 삭제(DEL_FG='Y')
     *
     * @param pmParam 검색 조건
     * @return int
     * @throws Exception
     */
    public int deleteFgProdKind(Map<String, Object> pmParam) throws Exception {
        return update("DlwProdMap.deleteFgProdKind", pmParam);
    }


    /**
     * 상품모델명리스트 삭제
     *
     * @param pmParam 검색 조건
     * @return int
     * @throws Exception
     */

    public int deleteProdtl(Map<String, ?> pmParam) throws Exception {
        return delete("DlwProdMap.deleteProdtl", pmParam);
    }

    /**
     * 상품모델명리스트 등록
     *
     * @param pmParam 검색 조건
     * @return int
     * @throws Exception
     */
    public int insertProdtl(Map<String, ?> pmParam) throws Exception {
        return insert("DlwProdMap.insertProdtl", pmParam);
    }





    /**
     * 상품모델구분코드 검색
     *
     * @param pmParam 검색 조건
     * @return 상품 상세정보
     * @throws Exception
     */
    public Map<String, Object> getProdMolelClCd(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwProdMap.getProdMolelClCd", pmParam);
    }



    public List<Map<String, Object>> getProddtl(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwProdMap.getProddtl", pmParam);
    }


    /**
     * 결합상품 종류를 검색
     *
     * @param pmParam 검색 조건
     * @return 비정기형  목록
     * @throws Exception
     */
    public List<Map<String, Object>> getProdKind(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwProdMap.getProdKind", pmParam);
    }

    /**
     * 상품 모델명리스트디테일
     * @param pmParam
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getProdmodeldtl(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwProdMap.getProdmodeldtl", pmParam);
    }


    /**
     * 회원별 녹취 확인 리스트 건수
     *
     * @param pmParam 검색 조건
     * @return 회원별 녹취 확인 리스트 건수
     * @throws Exception
     */
    public int getRecBeforeCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwProdMap.getRecBeforeCount", pmParam);
    }

    /**
     * 회원별 녹취 확인 리스트
     *
     * @param pmParam 검색 조건
     * @return 회원별 녹취 확인 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getRecBeforeList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwProdMap.getRecBeforeList", pmParam);
    }

    /**
     * 파일업로드 리스트
     *
     * @param pmParam 검색 조건
     * @return 회원별 녹취 확인 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getFileUploadList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwProdMap.getFileUploadList", pmParam);
    }

    /**
     * 업로드실패이력 리스트
     *
     * @param pmParam 검색 조건
     * @return 회원별 녹취 확인 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getUploadFailList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwProdMap.getUploadFailList", pmParam);
    }

    /**
     * 파일 업로드 권한 조회
     *
     * @param pmParam 검색 조건
     * @return 상품 정보의 검색 건수
     * @throws Exception
     */
    public String selectHSFileUploadAuth(Map<String, Object> pmParam) throws Exception {
        return (String) selectOne("DlwProdMap.selectHSFileUploadAuth", pmParam);
    }

    /**
     * 녹취파일 중복건수 조회
     *
     * @param pmParam 검색 조건
     * @return 상품 정보의 검색 건수
     * @throws Exception
     */
    public int recFileDupChk(String file_nm) throws Exception {
        return (Integer) selectOne("DlwProdMap.recFileDupChk", file_nm);
    }


    public List<Map<String, Object>> getprodnList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwProdMap.getprodnList", pmParam);
    }
    /**
     * 유효한 녹취파일 등록
     *
     * @param pmParam 검색 조건
     * @return 상품정보 수정
     * @throws Exception
     */
    public int insertUploadFile(Map<String, ?> pmParam) throws Exception {
        return update("DlwProdMap.insertUploadFile", pmParam);
    }

    /**
     * 에러있는 녹취파일 등록
     *
     * @param pmParam 검색 조건
     * @return 상품정보 수정
     * @throws Exception
     */
    public int insertUploadErrorFile(Map<String, ?> pmParam) throws Exception {
        return update("DlwProdMap.insertUploadErrorFile", pmParam);
    }

    /**
     * 업로드 녹취 파일 삭제
     *
     * @param pmParam 검색 조건
     * @return 삭제건수
     * @throws Exception
     */
    public int hsUploadDataDelete(Map<String, Object> pmParam) throws Exception {
        return update("DlwProdMap.hsUploadDataDelete", pmParam);
    }

    public List<Map<String, Object>> getmonitoringlist(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwProdMap.getmonitoringlist", pmParam);
    }


    /**
     * 모니터링 녹취  삭제
     *
     * @param pmParam 검색 조건
     * @return 상품정보 수정
     * @throws Exception
     */
    public int deletemonitoring_rec(Map<String, ?> pmParam) throws Exception {
        return update("DlwProdMap.deletemonitoring_rec", pmParam);
    }

    /**
     * 모니터링   삭제
     *
     * @param pmParam 검색 조건
     * @return 상품정보 수정
     * @throws Exception
     */
    public int deletemonitoring(Map<String, ?> pmParam) throws Exception {
        return update("DlwProdMap.deletemonitoring", pmParam);
    }


    /**
     * 매입코드관리의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 상품 정보의 검색 건수
     * @throws Exception
     */
    public int getDlwpurchaseCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwProdMap.getDlwpurchaseCount", pmParam);
    }
    /**
     * 매입코드관리의 검색 리스트를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 상품 정보의 검색 건수
     * @throws Exception
     */

    public List<Map<String, Object>> getDlwpurchaseList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwProdMap.getDlwpurchaseList", pmParam);
    }

    /**
     * 매입코드관리 수정
     *
     * @param pmParam 검색 조건
     * @return 매입코드관리
     * @throws Exception
     */
    public int updatepurchaseChng(Map<String, ?> pmParam) throws Exception {
        //CommonUtils.printLog(pmParam);
        return update("DlwProdMap.updatepurchaseChng", pmParam);
    }

    /**
     * 매입코드관리 등록
     *
     * @param pmParam 검색 조건
     * @return 상품별 상세 등록
     * @throws Exception
     */
    public int insertpurchaseNew(Map<String, ?> pmParam) throws Exception {
        return insert("DlwProdMap.insertpurchaseNew", pmParam);
    }

    /**매입코드관리 삭제
     *
     * */


    public int deletepurchase(Map<String, ?> pmParam) throws Exception {
        return update("DlwProdMap.deletepurchase", pmParam);
    }

    /**
     * 상품모델 매입매출가 리스트 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 상품모델 매입매출가 검색 건수
     * @throws Exception
     */
    public int getClassProdPriceCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwProdMap.getClassProdPriceCount", pmParam);
    }

    /**
     * 상품모델 매입매출가 리스트 조회
     *
     * @param pmParam 검색 조건
     * @return 상품모델 매입매출가 리스트 조회
     * @throws Exception
     */
    public List<Map<String, Object>> getClassProdPriceList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwProdMap.getClassProdPriceList", pmParam);
    }

    /**
     * 상품모델 매입매출가 등록
     *
     * @param pmParam 검색 조건
     * @return 상품모델 매입매출가 등록
     * @throws Exception
     */
    public int insertProdModelPrice(Map<String, ?> pmParam) throws Exception {
        return insert("DlwProdMap.insertProdModelPrice", pmParam);
    }

    /**
     * 상품모델 매입매출가 수정
     *
     * @param pmParam 검색 조건
     * @return 상품모델 매입매출가 수정
     * @throws Exception
     */
    public int updateProdModelPrice(Map<String, ?> pmParam) throws Exception {
        return update("DlwProdMap.updateProdModelPrice", pmParam);
    }

    /**
     * 상품모델 매입매출가 수정2 (**기존 데이터의 종료일은 (입력적용일-1) 로 UPDATE))
     *
     * @param pmParam 검색 조건
     * @return 상품모델 매입매출가 수정2 (**기존 데이터의 종료일은 (입력적용일-1) 로 UPDATE))
     * @throws Exception
     */
    public int updateProdModelPrice2(Map<String, ?> pmParam) throws Exception {
        return update("DlwProdMap.updateProdModelPrice2", pmParam);
    }

    /**
     * 상품모델 매입매출가 삭제
     *
     * @param pmParam 검색 조건
     * @return 상품모델 매입매출가 삭제
     * @throws Exception
     */
    public int deleteProdModelPrice(Map<String, ?> pmParam) throws Exception {
        return update("DlwProdMap.deleteProdModelPrice", pmParam);
    }


    /**
     * 상품모델 매입매출가 입력가능여부 체크1
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String chkProdModelPrice1(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwProdMap.chkProdModelPrice1", pmParam);
    }

    /**
     * 상품모델 매입매출가 입력가능여부 체크2
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String chkProdModelPrice2(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwProdMap.chkProdModelPrice2", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190828
     * 이름 : 송준빈
     * 추가내용 : 상품별회차관리 조회 수 (마스터)
     * 대상 테이블 : LF_DMUSER.PRODUCT
     * ================================================================
     * */
	public int getProductMstListCount(Map<String, ?> pmParam) {
		return selectOne("DlwProdMap.getProductMstListCount", pmParam);
	}

	/** ================================================================
     * 날짜 : 20190828
     * 이름 : 송준빈
     * 추가내용 : 상품별회차관리 조회 리스트 (마스터)
     * 대상 테이블 : LF_DMUSER.PRODUCT
     * ================================================================
     * */
	public List<Map<String, Object>> getProductMstList(Map<String, ?> pmParam) {
		return selectList("DlwProdMap.getProductMstList", pmParam);
	}

	/** ================================================================
     * 날짜 : 20190828
     * 이름 : 송준빈
     * 추가내용 : 상품별회차관리 조회 수 (디테일)
     * 대상 테이블 : LF_DMUSER.PRODUCT_NO_DATA
     * ================================================================
     * */
	public int getProductNoDataListCount(Map<String, ?> pmParam) {
		return selectOne("DlwProdMap.getProductNoDataListCount", pmParam);
	}

	/** ================================================================
     * 날짜 : 20190828
     * 이름 : 송준빈
     * 추가내용 : 상품별회차관리 조회 리스트 (디테일)
     * 대상 테이블 : LF_DMUSER.PRODUCT_NO_DATA
     * ================================================================
     * */
	public List<Map<String, Object>> getProductNoDataList(Map<String, ?> pmParam) {
		return selectList("DlwProdMap.getProductNoDataList", pmParam);
	}
	
	/** ================================================================
     * 날짜 : 20190828
     * 이름 : 송준빈
     * 추가내용 : 상품별 회차 기본정보 
     * 대상 테이블 : LF_DMUSER.PRODUCT_DTL
     * ================================================================
     * */
	public List<Map<String, Object>> getProductDtlList(Map<String, ?> pmParam) {
		return selectList("DlwProdMap.getProductDtlList", pmParam);
	}

	/** ================================================================
     * 날짜 : 20190828
     * 이름 : 송준빈
     * 추가내용 : 상품 회차별 청구금액 입력 
     * 대상 테이블 : LF_DMUSER.PRODUCT_NO_DATA
     * ================================================================
     * */
	public int insertProductNoDataList(Map<String, Object> pmParam) {
		return insert("DlwProdMap.insertProductNoDataList", pmParam);
	}

	/** ================================================================
     * 날짜 : 20190828
     * 이름 : 송준빈
     * 추가내용 : 상품 회차별 청구금액 삭제 
     * 대상 테이블 : LF_DMUSER.PRODUCT_NO_DATA
     * ================================================================
     * */
	public int deleteProductNoDataList(Map<String, Object> pmParam) {
		return delete("DlwProdMap.deleteProductNoDataList", pmParam);
	}
}
