package powerservice.business.dlw.service;

import java.util.List;
import java.util.Map;

public interface DlwOverallStatusService {
	
	/** ================================================================
     * 날짜 : 20210204
     * 이름 : 김주용
     * 추가내용 : 일일종합현황데이터 조회 조회건수
     * 대상 테이블 : LF_DMUSER.TB_MBID_CSV_FILE_LIST
     * ================================================================
     * */
    public int getOverallStatusCount(Map<String, Object> pmParam);
    
    /** ================================================================
     * 날짜 : 20210204
     * 이름 : 김주용
     * 추가내용 : 일일종합현황데이터 조회 조회리스트
     * 대상 테이블 : LF_DMUSER.TB_MBID_CSV_FILE_LIST
     * ================================================================
     * */
    public List<Map<String, Object>> getOverallStatusList(Map<String, Object> pmParam);
    
    /** ================================================================
     * 날짜 : 20210204
     * 이름 : 김주용
     * 추가내용 : 일일종합현황데이터 다운로드시행자 업데이트
     * 대상 테이블 : LF_DMUSER.TB_MBID_CSV_FILE_LIST
     * ================================================================
     * */
    public int updateOverallStatusDownLoadEmplFileList(Map<String, ?> pmParam) throws Exception;

    /** ================================================================
     * 날짜 : 20210204
     * 이름 : 김주용
     * 추가내용 : 일일종합현황데이터 다운로드시행자 이력정보 등록
     * 대상 테이블 : LF_DMUSER.TB_MBID_CSV_DNDL_HIST
     * ================================================================
     * */
    public int insertOverallStatusDownLoadEmplHist(Map<String, ?> pmParam) throws Exception;
    
    /** ================================================================
     * 날짜 : 20210204
     * 이름 : 김주용
     * 추가내용 : 일일종합현황데이터 처리일자 조회
     * 대상 테이블 : LF_DMUSER.TB_CSV_FILE_BATCH_DAY
     * ================================================================
     * */
    public List<Map<String, Object>> getOverallStatusBatchDay(Map<String, ?> pmParam) throws Exception;
    
    /** ================================================================
     * 날짜 : 20210204
     * 이름 : 김주용
     * 추가내용 : 일일종합현황데이터 유효성 체크
     * 대상 테이블 : LF_DMUSER.TB_CSV_FILE_BATCH_DAY
     * ================================================================
     * */
    public int validationOverallStatusFile(Map<String, ?> pmParam) throws Exception;
    
    /** ================================================================
     * 날짜 : 20210204
     * 이름 : 김주용
     * 추가내용 : 일일종합현황데이터 처리일자 등록
     * 대상 테이블 : LF_DMUSER.TB_CSV_FILE_BATCH_DAY
     * ================================================================
     * */
    public int insertOverallStatusBatchDay(Map<String, ?> pmParam) throws Exception;

    /** ================================================================
     * 날짜 : 20210204
     * 이름 : 김주용
     * 추가내용 : 일일종합현황데이터 처리일자 삭제
     * 대상 테이블 : LF_DMUSER.TB_CSV_FILE_BATCH_DAY
     * ================================================================
     * */
    public int deleteOverallStatusBatchDay(Map<String, ?> pmParam) throws Exception;
    
    /** ================================================================
     * 날짜 : 20210204
     * 이름 : 김주용
     * 추가내용 : 일일종합현황데이터 생성 (배치처리)
     * 대상 테이블 : LF_DMUSER.TB_CSV_FILE_BATCH_DAY
     * ================================================================
     * */
    public int batchOverallStatusFileMake() throws Exception;
}
