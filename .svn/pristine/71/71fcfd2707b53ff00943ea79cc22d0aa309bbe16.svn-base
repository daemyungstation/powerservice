package powerservice.business.gongje.service;

import java.util.List;
import java.util.Map;

public interface GongjeService {

    // 임시테이블 삭제
    public int getGongjeTempDelete() throws Exception;

    // 엑셀 미전송 데이터 건수 체크
    public int getGongjeExcelchkCount() throws Exception;

    // 임시테이블 삭제
    public int getGongjeExcelcancel() throws Exception;

    // 신규
    public int getGongjeNewInsert_Temp(Map<String, ?> pmParam) throws Exception; // Temp Table

    public int getGongjeNewCount(Map<String, ?> pmParam) throws Exception; // Count

    public List<Map<String, Object>> getGongjeNewList(Map<String, ?> pmParam) throws Exception; // 조회

    public int getGongjeNewInsert(Map<String, ?> pmParam) throws Exception; // 엑셀

    // 해약
    public int getGongjeRessInsert_Temp(Map<String, ?> pmParam) throws Exception; // Temp Table

    public int getGongjeRessCount(Map<String, ?> pmParam) throws Exception; // Count

    public List<Map<String, Object>> getGongjeRessList(Map<String, ?> pmParam) throws Exception; // 조회

    public int getGongjeRessInsert(Map<String, ?> pmParam) throws Exception; // 엑셀

    // 정보변경
    public int getGongjeChngInsert_Temp(Map<String, ?> pmParam) throws Exception; // Temp Table

    public int getGongjeChngCount(Map<String, ?> pmParam) throws Exception; // Count

    public List<Map<String, Object>> getGongjeChngList(Map<String, ?> pmParam) throws Exception; // 조회

    public int getGongjeChngInsert(Map<String, ?> pmParam) throws Exception; // 엑셀

    // 양도양수
    public int getGongjeYdsInsert_Temp(Map<String, ?> pmParam) throws Exception; // Temp Table

    public int getGongjeYdsCount(Map<String, ?> pmParam) throws Exception; // Count

    public List<Map<String, Object>> getGongjeYdsList(Map<String, ?> pmParam) throws Exception; // 조회

    public int getGongjeYdsInsert(Map<String, ?> pmParam) throws Exception; // 엑셀

    // 행사
    public int getGongjeHangsaInsert_Temp(Map<String, ?> pmParam) throws Exception; // Temp Table

    public int getGongjeHangsaCount(Map<String, ?> pmParam) throws Exception; // Count

    public List<Map<String, Object>> getGongjeHangsaList(Map<String, ?> pmParam) throws Exception; // 조회

    public int getGongjeHangsaInsert(Map<String, ?> pmParam) throws Exception; // 엑셀

    // 선수급납입
    public int getGongjePayInsert_Temp(Map<String, ?> pmParam) throws Exception; // Temp Table

    public int getGongjePayCount(Map<String, ?> pmParam) throws Exception; // Count

    public List<Map<String, Object>> getGongjePayList(Map<String, ?> pmParam) throws Exception; // 조회

    public int getGongjePayInsert(Map<String, ?> pmParam) throws Exception; // 엑셀

    // 선수금취소
    public int getGongjePay_CnclCount(Map<String, ?> pmParam) throws Exception; // Count

    public List<Map<String, Object>> getGongjePay_CnclList(Map<String, ?> pmParam) throws Exception; // 조회

    public int getGongjePay_CnclInsert(Map<String, ?> pmParam) throws Exception; // 엑셀

    // 해약취소
    public List<Map<String, Object>> getGongjeRess_CnclList(Map<String, ?> pmParam) throws Exception; // 조회

    public int getGongjeRess_CnclInsert(Map<String, ?> pmParam) throws Exception; // 엑셀

    // 행사취소
    public List<Map<String, Object>> getGongjeHangsa_CnclList(Map<String, ?> pmParam) throws Exception; // 조회

    public int getGongjeHangsa_CnclInsert(Map<String, ?> pmParam) throws Exception; // 엑셀

    // 전송처리
    public int getGongjeNewUpdate(Map<String, ?> pmParam) throws Exception;

    public int getGongjePayUpdate(Map<String, ?> pmParam) throws Exception;

    public int getGongjePayCnclUpdate(Map<String, ?> pmParam) throws Exception;

    // 전송이력조회
    public int getGongjeHistCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getGongjeHistList(Map<String, ?> pmParam) throws Exception;

    // 결과처리(기본:신규/정보변경/해약/양도양수/행사/입금)
    public int getGongjeResultchkCount() throws Exception;

    public int getGongjeBaseResult() throws Exception;

    public int getGongjeBaseResult_Pay() throws Exception;

    public int getGongjeSinResult() throws Exception;

    public int getGongjeRessResult() throws Exception;

    public int getGongjeChngResult() throws Exception;

    public int getGongjeHangsaResult() throws Exception;

    public int getGongjeMemProdUpdate() throws Exception;

    public int getGongjeYdsResult() throws Exception;

    public int getGongjeYds_New_Result() throws Exception;

    public int getGongjeHistResult() throws Exception;

    public int getGongjeLastResult() throws Exception;

    public int getGongjePayResult() throws Exception;

    //  XML 업로드(신규/해약/정보변경/양도양수/행사/입금) - 데이터 삽입
    public int getGongjeUploadchkCount() throws Exception;

    public void insertFile(Map<String, ?> hmParam) throws Exception;

    public int getGongjeUploadResult(Map<String, ?> hmParam) throws Exception;

    // 선수금 엑셀 대량다운로드
    public List<Map<String, Object>> getGongjePayExcel(Map<String, ?> pmParam) throws Exception; // 조회

    // 월별 보고서 선수금 현황 조회
    public List<Map<String, Object>> getGongjeMagamPay(Map<String, ?> pmParam) throws Exception;
    // 월별 보고서 회원 현황 조회
    public List<Map<String, Object>> getGongjeMagamMem(Map<String, ?> pmParam) throws Exception;

    // 월별 보고서 신규내역
    public int getReportNewCount(Map<String, ?> pmParam) throws Exception; // Count

    public List<Map<String, Object>> getReportNewList(Map<String, ?> pmParam) throws Exception; // 조회

    // 월별 보고서 해약내역
    public int getReportResCount(Map<String, ?> pmParam) throws Exception; // Count

    public List<Map<String, Object>> getReportResList(Map<String, ?> pmParam) throws Exception; // 조회

    // 월별 보고서 행사내역
    public int getReportEvntCount(Map<String, ?> pmParam) throws Exception; // Count

    public List<Map<String, Object>> getReportEvntList(Map<String, ?> pmParam) throws Exception; // 조회

    // 월별 보고서 기타 환불내역
    public int getReportCnclCount(Map<String, ?> pmParam) throws Exception; // Count

    public List<Map<String, Object>> getReportCnclList(Map<String, ?> pmParam) throws Exception; // 조회

    // 월별 보고서 CMS 이체내역
    public int getGongjeReportCmsPayCount(Map<String, ?> pmParam) throws Exception; // Count

    public List<Map<String, Object>> getGongjeReportCmsPayList(Map<String, ?> pmParam) throws Exception; // 조회

    // 월별 보고서 현금 등 기타내역
    public int getGongjeReportOtPayCount(Map<String, ?> pmParam) throws Exception; // Count

    public List<Map<String, Object>> getGongjeReportOtPayList(Map<String, ?> pmParam) throws Exception; // 조회

    // 월별 보고서 누적 선수금내역
    public int getGongjeReportTotalPayCount(Map<String, ?> pmParam) throws Exception; // Count

    public List<Map<String, Object>> getGongjeReportTotalPayList(Map<String, ?> pmParam) throws Exception; // 조회

}