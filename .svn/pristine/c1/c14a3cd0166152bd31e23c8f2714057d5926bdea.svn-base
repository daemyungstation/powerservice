package powerservice.business.wdrw.service;

import java.util.List;
import java.util.Map;

public interface WdrwResultMgmtService {
	
	/** ================================================================
     * 날짜 : 20200401
     * 이름 : 송준빈
     * 추가내용 : 고객청구결과관리 데이터 조회 수
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ, LF_DMUSER.TB_MEMBER_WDRW_RESULT, LF_DMUSER.TB_MEMBER_REQ_REFUND
     * ================================================================
     * */
	public int getWdrwResultMgmtCount(Map<String, ?> pmParam) throws Exception;
    
	/** ================================================================
     * 날짜 : 20200401
     * 이름 : 송준빈
     * 추가내용 : 고객청구결과관리 데이터 조회 리스트
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ, LF_DMUSER.TB_MEMBER_WDRW_RESULT, LF_DMUSER.TB_MEMBER_REQ_REFUND
     * ================================================================
     * */
    public List<Map<String, Object>> getWdrwResultMgmtList(Map<String, ?> pmParam) throws Exception;
    
    /** ================================================================
     * 날짜 : 20200401
     * 이름 : 송준빈
     * 추가내용 : 고객청구결과관리 고객별 데이터 조회 리스트
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ, LF_DMUSER.TB_MEMBER_WDRW_RESULT, LF_DMUSER.TB_MEMBER_REQ_REFUND
     * ================================================================
     * */
    public List<Map<String, Object>> getWdrwAccntLogList(Map<String, ?> pmParam) throws Exception;
    
    /** ================================================================
     * 날짜 : 20200401
     * 이름 : 송준빈
     * 추가내용 : 고객청구결과관리 고객 산출,청구 여부 확인
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
     * ================================================================
     * */
    public List<Map<String, Object>> getWdrwNRConfirm(String sAccntNo) throws Exception;
    
    /** ================================================================
     * 날짜 : 20200224
     * 이름 : 송준빈
     * 추가내용 : 가상계좌 정보 강제마감처리
     * 대상 테이블 : LF_DMUSER.TB_NICE_VRTL_ACCNT_INFO
     * ================================================================
     * */
    public int updateVrtlAccntRecoverProc(Map<String, ?> pmParam) throws Exception;
}
