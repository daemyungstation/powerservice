package powerservice.business.dlw.service;

import java.util.List;
import java.util.Map;

public interface DlwReadyCashService {
	
	/** ================================================================
     * 날짜 : 20190509
     * 이름 : 송준빈
     * 추가내용 : 회원별 레디캐쉬 조회 수
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_DLWMALL
     * ================================================================
     * */
    public int getMemberDlwmallCount(Map<String, ?> pmParam) throws Exception;
    
	/** ================================================================
     * 날짜 : 20190509
     * 이름 : 송준빈
     * 추가내용 : 회원별 레디캐쉬 조회 리스트
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_DLWMALL
     * ================================================================
     * */
    public List<Map<String, Object>> getMemberDlwmallList(Map<String, ?> pmParam) throws Exception;
    
    /** ================================================================
     * 날짜 : 20190509
     * 이름 : 송준빈
     * 추가내용 : 회원별 레디캐쉬 조회 집계
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_DLWMALL
     * ================================================================
     * */
    public List<Map<String, Object>> getMemberDlwmallSummary(Map<String, ?> pmParam) throws Exception;
    
}
