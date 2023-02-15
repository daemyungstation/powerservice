package powerservice.business.dlw.service;

import java.util.List;
import java.util.Map;

public interface DlwCashReceiptService {
	
	/** ================================================================
     * 날짜 : 20220726
     * 이름 : 김주용
     * 추가내용 : 현금영수증발행 조회 수
     * 대상 테이블 : LF_DMUSER.TB_CASH_RECEIPT
     * ================================================================
     * */
    public int getCashReceiptCount(Map<String, ?> pmParam) throws Exception;
    
	/** ================================================================
     * 날짜 : 20220726
     * 이름 : 김주용
     * 추가내용 : 현금영수증발행 조회 리스트
     * 대상 테이블 : LF_DMUSER.TB_CASH_RECEIPT
     * ================================================================
     * */
    public List<Map<String, Object>> getCashReceiptList(Map<String, ?> pmParam) throws Exception;
    
    /** ================================================================
     * 날짜 : 20220726
     * 이름 : 김주용
     * 추가내용 : 현금영수증발행 산출 TB insert
     * 대상 테이블 : LF_DMUSER.TB_CASH_RECEIPT
     * ================================================================
     * */
    public int insertCashReceipt(Map<String, Object> pmParam);
    
    /** ================================================================
     * 날짜 : 20220726
     * 이름 : 김주용
     * 추가내용 : 현금영수증발행 산출체크
     * 대상 테이블 : LF_DMUSER.TB_CASH_RECEIPT
     * ================================================================
     * */
    public int getCashReceiptCountChk(Map<String, ?> pmParam) throws Exception;
    
    /** ================================================================
     * 날짜 : 20220726
     * 이름 : 김주용
     * 추가내용 : 현금영수증발행 엑셀 전송 저장
     * 대상 테이블 : LF_DMUSER.TB_CASH_RECEIPT
     * ================================================================
     * */
    public int updateCashReceipt(Map<String, Object> pmParam);
    
    /** ================================================================
     * 날짜 : 20220726
     * 이름 : 김주용
     * 추가내용 : 현금영수증발행 삭제
     * 대상 테이블 : LF_DMUSER.TB_CASH_RECEIPT
     * ================================================================
     * */
    public int deleteCashReceipt(Map<String, Object> pmParam);
}
