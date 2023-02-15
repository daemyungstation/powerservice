package powerservice.business.dlw.service;

import java.util.List;
import java.util.Map;

public interface DlwMangiMemberService {
	
    /** ================================================================
     * 날짜 : 20210215
     * 이름 : 송준빈
     * 추가내용 : 일일종합현황데이터 조회 조회건수
     * 대상 테이블 : LF_DMUSER.TB_MEM_ACCNT_ALTR_HIST
     * ================================================================
     * */
    public int getMangiMemberCount(Map<String, ?> pmParam);
    
    /** ================================================================
     * 날짜 : 20210215
     * 이름 : 송준빈
     * 추가내용 : 일일종합현황데이터 조회 조회리스트
     * 대상 테이블 : LF_DMUSER.TB_MEM_ACCNT_ALTR_HIST
     * ================================================================
     * */
    public List<Map<String, Object>> getMangiMemberList(Map<String, ?> pmParam);
    
   
	/** ================================================================
     * 날짜 : 20220105
     * 이름 : 임동진
     * 추가내용 : 만기방어를 위한 주간 만기현황 리스트
     * 대상 테이블 : LF_DMUSER.TB_CLOSE_MEMBER_MON
     * ================================================================
     * */
    public List<Map<String, Object>> getMangiLIst(Map<String, ?> pmParam);
    
    /** ================================================================
     * 날짜 : 20220929
     * 이름 : 김주용
     * 추가내용 : 만기해약집계
     * 대상 테이블 : LF_DMUSER.TB_CLOSE_MEMBER_MON
     * ================================================================
     * */
    public List<Map<String, Object>> getMangiResnLIst1(Map<String, ?> pmParam);
    
    /** ================================================================
     * 날짜 : 20220929
     * 이름 : 김주용
     * 추가내용 : 만기해약상세
     * 대상 테이블 : LF_DMUSER.TB_CLOSE_MEMBER_MON
     * ================================================================
     * */
    public List<Map<String, Object>> getMangiResnLIst2(Map<String, ?> pmParam);
    
    /** ================================================================
     * 날짜 : 20220929
     * 이름 : 김주용
     * 추가내용 : 만기추정집계
     * 대상 테이블 : LF_DMUSER.TB_CLOSE_MEMBER_MON
     * ================================================================
     * */
    public List<Map<String, Object>> getMangiResnLIst3(Map<String, ?> pmParam);
    
    /** ================================================================
     * 날짜 : 20220929
     * 이름 : 김주용
     * 추가내용 : 만기추정상세
     * 대상 테이블 : LF_DMUSER.TB_CLOSE_MEMBER_MON
     * ================================================================
     * */
    public List<Map<String, Object>> getMangiResnLIst4(Map<String, ?> pmParam);
}
