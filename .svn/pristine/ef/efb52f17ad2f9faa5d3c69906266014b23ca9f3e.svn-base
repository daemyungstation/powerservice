package powerservice.business.dlw.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

public interface DlwProdService {

    public int getDlwProdCount(Map<String, ?> pmParam) throws Exception;

    public int getDlwProdNewCODE(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getDlwProdList(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> getDlwProdDtpt(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getDlwProductDtl(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getOCBCardCodeList(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getModelMstInfo(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getModelDtlInfo(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getProdKindList(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> getMemProdAccntWithDlv(String psParam) throws Exception;

    public int insertProdNew(Map<String, ?> pmParam) throws Exception; // 신규상품등록

    public int updateProdChng(Map<String, ?> pmParam) throws Exception; // 상품정보수정

    public int deleteProd(Map<String, ?> pmParam) throws Exception;     // 상품정보삭제

    public int insertProdDtl(Map<String, ?> pmParam) throws Exception; // 신규상품상세 등록

    public int deleteProdDtl(Map<String, ?> pmParam) throws Exception; // 신규상품상세 등록

    public List<Map<String, Object>> getProdDtlList(Map<String, ?> pmParam) throws Exception; // 상품별 상세분류 콤보리스트

    public int getClassProdCount(Map<String, ?> pmParam) throws Exception; // 상품별 상세분류 검색 건수

    public List<Map<String, Object>> getClassProdList(Map<String, ?> pmParam) throws Exception; // 상품별 상세분류 리스트 조회

    public int getRowProdDel(Map<String, ?> pmParam) throws Exception; // 상품별 상세분류 리스트 선택 행 삭제

    public int insertProdKindNew(Map<String, ?> pmParam) throws Exception; // 상품별 상세분류 등록

    public int getProdModelCount(Map<String, ?> pmParam) throws Exception; // 상품별 모델 분류 기초 코드 리스트 건수

    public List<Map<String, Object>> getProdModelList(Map<String, ?> pmParam) throws Exception; // 상품별 모델 분류 기초 코드 관리 조회

    public int getPeriodProdCostCount(Map<String, ?> pmParam) throws Exception; // 기간별 상품 의전원가 조회 건수
    public List<Map<String, Object>> getPeriodProdCost(Map<String, ?> pmParam) throws Exception; // 기간별 상품 의전원가 조회
    public int insertPeriodProdCost(Map<String, ?> pmParam) throws Exception; // 기간별 상품 의전원가 등록
    public String checkPeriodProdCost1(Map<String, ?> pmParam) throws Exception; // 기간별 상품 의전원가 입력가능여부 체크1
    public String checkPeriodProdCost2(Map<String, ?> pmParam) throws Exception; // 기간별 상품 의전원가 입력가능여부 체크2
    public String checkPeriodProdCost3(Map<String, ?> pmParam) throws Exception; // 기간별 상품 의전원가 입력가능여부 체크3
    public int getRowPeriodProdCostDel(Map<String, ?> pmParam) throws Exception; // 기간별 상품 의전원가 삭제
    public int updatePeriodProdCost(Map<String, ?> pmParam) throws Exception; // 기간별 상품 의전원가 수정 (** 등록 또는 삭제시 이어서 수정)

    //public int saveProd (XPlatformMapDTO xpDto, Map<String, Object> mOut)  throws Exception;  //거래처 등록/수정/삭제

    public List<Map<String, Object>> getTransfList(Map<String, ?> pmParam) throws Exception;

    public int insertProdModelClCd(Map<String, ?> pmParam) throws Exception; // 상품모델구분코드 등록
    public int updateProdModelClCd(Map<String, ?> pmParam) throws Exception; // 상품모델구분코드 수정
    public void deleteProdModelClCd(Map<String, ?> pmParam) throws Exception; // 상품모델구분코드 삭제

    public void deleteFgProdModelClCd(Map<String, Object> pmParam) throws Exception; // 상품모델구분코드 삭제(DEL_FG='Y')

    public String getModelClCd(Map<String, ?> pmParam) throws Exception; // 상품모델구분코드 코드조회

    public int insertProdKind(Map<String, ?> pmParam) throws Exception; // 결합상품 종류 관리 등록
    public int deleteProdKind(Map<String, ?> pmParam) throws Exception; // 결합상품 종류 관리 삭제
    public int deleteFgProdKind(Map<String, Object> pmParam) throws Exception; // 결합상품 종류 관리 삭제(DEL_FG='Y')


    public int updateProdtl(Map<String, ?> pmParam) throws Exception; // 상품모델명정보관리
    public int updateProd_dtl(Map<String, ?> pmParam) throws Exception; // 상품모델명list 업데이트
    public int deleteProdtl(Map<String, ?> pmParam) throws Exception; // 상품모델명list 삭제
    public int insertProdtl(Map<String, ?> pmParam) throws Exception; // 상품모델명list 등록
    //상품모델구분코드 검색
    public Map<String, Object> getProdMolelClCd(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getProddtl(Map<String, ?> pmParam) throws Exception;

    //결합상품 종류 검색
    public List<Map<String, Object>> getProdKind(Map<String, ?> pmParam) throws Exception;

    //상품모델명리스트
    public List<Map<String, Object>> getProdmodeldtl(Map<String, ?> pmParam) throws Exception;

    public int getRecBeforeCount(Map<String, ?> pmParam) throws Exception; // 회원별 녹취 확인 리스트 건수

    public List<Map<String, Object>> getRecBeforeList(Map<String, ?> pmParam) throws Exception; // 회원별 녹취 확인 리스트

    public List<Map<String, Object>> getFileUploadList(Map<String, ?> pmParam) throws Exception; // 파일업로드 리스트

    public List<Map<String, Object>> getUploadFailList(Map<String, ?> pmParam) throws Exception; // 업로드실패이력 리스트

    public void uploadToNas(HttpServletRequest request, HttpServletResponse response, Map<String, Object> mResuslt) throws Exception; // 녹취파일 개별 업로드

    public int hsUploadDataDelete(XPlatformMapDTO xpDto) throws Exception; // 업로드 녹취 파일 삭제

    public List<Map<String, Object>> getprodnList(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getmonitoringlist(Map<String, ?> pmParam) throws Exception;

    public int deletemonitoring_rec(Map<String, ?> pmParam) throws Exception;     //모니터링녹취 삭제(데이터만)

    public int deletemonitoring(Map<String, ?> pmParam) throws Exception;     //모니터링 삭제

    public int getDlwpurchaseCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getDlwpurchaseList(Map<String, ?> pmParam) throws Exception;

    public int updatepurchaseChng(Map<String, ?> pmParam) throws Exception; // 매입코드관리수정

    public int insertpurchaseNew(Map<String, ?> pmParam) throws Exception; // 매입코드관리등록

    public void deletepurchase(Map<String, DataSetMap> mapInDs ) throws Exception;  /// 매입코드관리
    public int getClassProdPriceCount(Map<String, ?> pmParam) throws Exception; // 상품모델 매입매출가 리스트 건수
    public List<Map<String, Object>> getClassProdPriceList(Map<String, ?> pmParam) throws Exception; // 상품모델 매입매출가 리스트 조회
    public int insertProdModelPrice(Map<String, ?> pmParam) throws Exception; // 상품모델 매입매출가 등록
    public int updateProdModelPrice(Map<String, ?> pmParam) throws Exception; // 상품모델 매입매출가 수정
    public int updateProdModelPrice2(Map<String, ?> pmParam) throws Exception; // 상품모델 매입매출가 수정2 (** 기존 데이터의 종료일은 (입력적용일-1) 로 UPDATE)
    public int deleteProdModelPrice(Map<String, ?> pmParam) throws Exception; // 상품모델 매입매출가 삭제
    public String chkProdModelPrice1(Map<String, ?> pmParam) throws Exception; // 상품모델 매입매출가 입력가능여부 체크1
    public String chkProdModelPrice2(Map<String, ?> pmParam) throws Exception; // 상품모델 매입매출가 입력가능여부 체크2
       
    /** ================================================================
     * 날짜 : 20190828
     * 이름 : 송준빈
     * 추가내용 : 상품별회차관리 조회 수 (마스터)
     * 대상 테이블 : LF_DMUSER.PRODUCT
     * ================================================================
     * */
    public int getProductMstListCount(Map<String, ?> pmParam) throws Exception;
    
    /** ================================================================
     * 날짜 : 20190828
     * 이름 : 송준빈
     * 추가내용 : 상품별회차관리 조회 리스트 (마스터)
     * 대상 테이블 : LF_DMUSER.PRODUCT
     * ================================================================
     * */
    public List<Map<String, Object>> getProductMstList(Map<String, ?> pmParam) throws Exception;
    
    /** ================================================================
     * 날짜 : 20190828
     * 이름 : 송준빈
     * 추가내용 : 상품별회차관리 조회 수 (디테일)
     * 대상 테이블 : LF_DMUSER.PRODUCT_NO_DATA
     * ================================================================
     * */
    public int getProductNoDataListCount(Map<String, ?> pmParam) throws Exception;
    
    /** ================================================================
     * 날짜 : 20190828
     * 이름 : 송준빈
     * 추가내용 : 상품별회차관리 조회 리스트 (디테일)
     * 대상 테이블 : LF_DMUSER.PRODUCT_NO_DATA
     * ================================================================
     * */
    public List<Map<String, Object>> getProductNoDataList(Map<String, ?> pmParam) throws Exception;
    
    /** ================================================================
     * 날짜 : 20190828
     * 이름 : 송준빈
     * 추가내용 : 상품별 회차 기본정보 
     * 대상 테이블 : LF_DMUSER.PRODUCT_DTL
     * ================================================================
     * */
    public List<Map<String, Object>> getProductDtlList(Map<String, ?> pmParam) throws Exception;
    
    /** ================================================================
     * 날짜 : 20190828
     * 이름 : 송준빈
     * 추가내용 : 상품 회차별 청구금액 입력 
     * 대상 테이블 : LF_DMUSER.PRODUCT_NO_DATA
     * ================================================================
     * */
    public int insertProductNoDataList(Map<String, Object> pmParam);
    
    /** ================================================================
     * 날짜 : 20190828
     * 이름 : 송준빈
     * 추가내용 : 상품 회차별 청구금액 삭제 
     * 대상 테이블 : LF_DMUSER.PRODUCT_NO_DATA
     * ================================================================
     * */
    public int deleteProductNoDataList(Map<String, Object> pmParam);
}