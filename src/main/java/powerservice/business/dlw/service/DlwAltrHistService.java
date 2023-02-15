package powerservice.business.dlw.service;

import java.util.List;
import java.util.Map;

public interface DlwAltrHistService {
	
	/** ================================================================
     * 날짜 : 20210215
     * 이름 : 송준빈
     * 추가내용 : 일일종합현황데이터 처리일자 등록
     * 대상 테이블 : LF_DMUSER.TB_MEM_ACCNT_ALTR_HIST
     * ================================================================
     * */
    public int insertMemAccntAltrHistList(Map<String, ?> pmParam) throws Exception;
    
    /** ================================================================
     * 날짜 : 20210215
     * 이름 : 송준빈
     * 추가내용 : 일일종합현황데이터 처리일자 조회
     * 대상 테이블 : LF_DMUSER.TB_MEM_ACCNT_ALTR_HIST
     * ================================================================
     * */
    public List<Map<String, Object>> getMemAccntAltrHistConsList(Map<String, ?> pmParam) throws Exception;
	
    /** ================================================================
     * 날짜 : 20210215
     * 이름 : 송준빈
     * 추가내용 : 일일종합현황데이터 조회 조회건수
     * 대상 테이블 : LF_DMUSER.TB_MEM_ACCNT_ALTR_HIST
     * ================================================================
     * */
    public int getMemAccntAltrHistCount(Map<String, ?> pmParam);
    
    /** ================================================================
     * 날짜 : 20210215
     * 이름 : 송준빈
     * 추가내용 : 일일종합현황데이터 조회 조회리스트
     * 대상 테이블 : LF_DMUSER.TB_MEM_ACCNT_ALTR_HIST
     * ================================================================
     * */
    public List<Map<String, Object>> getMemAccntAltrHistList(Map<String, ?> pmParam);
}
