package powerservice.business.dlw.service;

import java.util.List;
import java.util.Map;

public interface DlwTermMangiInfoService {
	
	/** ================================================================
     * 날짜 : 20210623
     * 이름 : 송준빈
     * 추가내용 : 기간별 만기정보 조회수
     * 대상 테이블 : DUAL
     * ================================================================
     * */
	public int getTermMangiCount(Map<String, ?> pmParam) throws Exception;
    
	/** ================================================================
     * 날짜 : 20210623
     * 이름 : 송준빈
     * 추가내용 : 기간별 만기정보 조회 리스트
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_MANGI_EXT
     * ================================================================
     * */
    public List<Map<String, Object>> getTermMangiList(Map<String, ?> pmParam) throws Exception;
    
    /** ================================================================
     * 날짜 : 20210715
     * 이름 : 송준빈
     * 추가내용 : 기간별 만기고객 현황 상세 조회수
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_MANGI_EXT
     * ================================================================
     * */
	public int getMangiAccntInfoDtlCount(Map<String, ?> pmParam) throws Exception;
    
	/** ================================================================
     * 날짜 : 20210715
     * 이름 : 송준빈
     * 추가내용 : 기간별 만기고객 현황 상세 조회 리스트
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_MANGI_EXT
     * ================================================================
     * */
    public List<Map<String, Object>> getMangiAccntInfoDtlList(Map<String, ?> pmParam) throws Exception;
}
