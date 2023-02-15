package powerservice.business.dlw.service;

import java.util.List;
import java.util.Map;

import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

public interface DlwVatSvcService {

    public List<Map<String, Object>> getVatSvcList(Map<String, Object> pmParam) throws Exception;

    public Map<String, Object> selectSvcExceptIssueHist(Map<String, Object> pmParam) throws Exception;

    public void insertSvcIsuHist(Map<String, Object> pmParam) throws Exception; // 부가서비스 발급이력관리 등록
    public void insertConsulDetail(Map<String, Object> pmParam) throws Exception; // 상담내역 Detail 등록
    public void insertConsulMng(Map<String, Object> pmParam) throws Exception; // 상담내역 마스터 등록
    public void insertConsulEtc(Map<String, Object> pmParam) throws Exception; // 상담내역 마스터 등록


    public void insertExcelUploadErr(Map<String, Object> pmParam) throws Exception; // 엑셀업로드 오류건 등록

    public String selectDupIsuNo(Map<String, Object> pmParam) throws Exception;

    public String uploadExcelSvcIsuHist(Map<String, Object> pmParam) throws Exception; // 쿠폰정보 업로드

    public String regSvcIsuHist(Map<String, Object> pmParam) throws Exception; // 부가서비스 발급(대명투어몰(10만원)만)

    public String newRegSvcIsuHist(Map<String, Object> pmParam) throws Exception; // 부가서비스 발급(대명투어몰(10만원)만 제외한 모든 쿠폰)

    public String uploadExcelSvcPostInfo(Map<String, Object> pmParam) throws Exception; // 우편번호 업로드

    public void deleteSvcIsuHist(Map<String, Object> pmParam) throws Exception; // 쿠폰삭제 업로드

    public void vatSvcInvalid(Map<String, Object> pmParam) throws Exception; // 부가서비스 무효화

    public void updateCallCenterVatSvcHist(Map<String, Object> pmParam) throws Exception; //상담업무 수정시 부가서비스 업무 수정 및 로직체크

}