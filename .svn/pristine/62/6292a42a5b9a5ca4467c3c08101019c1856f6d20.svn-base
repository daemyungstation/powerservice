package powerservice.business.dlw.service;

import java.util.List;
import java.util.Map;

public interface DlwPerformInfoService {
	
	/** ================================================================
     * 날짜 : 20201209
     * 이름 : 송준빈
     * 추가내용 : 실적현황 마감처리 산출여부 확인
     * 대상 테이블 : LF_DMUSER.TB_RECORD_MAIN
     * ================================================================
     * */
	public int getInsertPerformConfirm(Map<String, ?> pmParam) throws Exception;
	
	/** ================================================================
     * 날짜 : 20201209
     * 이름 : 송준빈
     * 추가내용 : 실적현황 마감처리 산출
     * 대상 테이블 : LF_DMUSER.TB_RECORD_MAIN
     * ================================================================
     * */
	public int insertPerformInfo(Map<String, ?> pmParam) throws Exception;
	
	/** ================================================================
     * 날짜 : 20201209
     * 이름 : 송준빈
     * 추가내용 : 실적현황 주간보고 조회 수
     * 대상 테이블 : LF_DMUSER.TB_RECORD_MAIN
     * ================================================================
     * */
	public int getPerformInfoWeekCount(Map<String, ?> pmParam) throws Exception;
    
	/** ================================================================
     * 날짜 : 20201209
     * 이름 : 송준빈
     * 추가내용 : 실적현황 주간보고 조회 리스트
     * 대상 테이블 : LF_DMUSER.TB_RECORD_MAIN
     * ================================================================
     * */
    public List<Map<String, Object>> getPerformInfoWeekList(Map<String, ?> pmParam) throws Exception;
    
    /** ================================================================
     * 날짜 : 20201209
     * 이름 : 송준빈
     * 추가내용 : 실적현황 전체실적 조회
     * 대상 테이블 : LF_DMUSER.TB_RECORD_MAIN
     * ================================================================
     * */
    public List<Map<String, Object>> getPerformInfoTotList(Map<String, ?> pmParam) throws Exception;
    
    /** ================================================================
     * 날짜 : 20201209
     * 이름 : 송준빈
     * 추가내용 : 실적현황 월간접수실적 조회 수
     * 대상 테이블 : LF_DMUSER.TB_RECORD_MAIN
     * ================================================================
     * */
	public int getPerformInfoMonthCount(Map<String, ?> pmParam) throws Exception;
    
	/** ================================================================
     * 날짜 : 20201209
     * 이름 : 송준빈
     * 추가내용 : 실적현황 월간접수실적 조회 리스트
     * 대상 테이블 : LF_DMUSER.TB_RECORD_MAIN
     * ================================================================
     * */
    public List<Map<String, Object>> getPerformInfoMonthList(Map<String, ?> pmParam) throws Exception;
}
