package powerservice.business.dlw.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

@Repository
public class DlwOverallStatusDAO extends EgovAbstractMapper {
	
	@Resource(name="dlwSqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }

	/** ================================================================
     * 날짜 : 20210204
     * 이름 : 김주용
     * 추가내용 : 일일종합현황데이터 조회 조회건수
     * 대상 테이블 : LF_DMUSER.TB_MBID_CSV_FILE_LIST
     * ================================================================
     * */
    public int getOverallStatusCount(Map<String, Object> pmParam) {
        return selectOne("DlwOverallStatusMap.getOverallStatusCount", pmParam);
    }

    /** ================================================================
     * 날짜 : 20210204
     * 이름 : 김주용
     * 추가내용 : 일일종합현황데이터 조회 조회리스트
     * 대상 테이블 : LF_DMUSER.TB_MBID_CSV_FILE_LIST
     * ================================================================
     * */
    public List<Map<String, Object>> getOverallStatusList(Map<String, Object> pmParam) {
        return selectList("DlwOverallStatusMap.getOverallStatusList", pmParam);
    }

    /** ================================================================
     * 날짜 : 20210204
     * 이름 : 김주용
     * 추가내용 : 일일종합현황데이터 다운로드시행자 업데이트
     * 대상 테이블 : LF_DMUSER.TB_MBID_CSV_FILE_LIST
     * ================================================================
     * */
    public int updateOverallStatusDownLoadEmplFileList(Map<String, ?> pmParam) {
        return update("DlwOverallStatusMap.updateOverallStatusDownLoadEmplFileList", pmParam);
    }

    /** ================================================================
     * 날짜 : 20210204
     * 이름 : 김주용
     * 추가내용 : 일일종합현황데이터 다운로드시행자 이력정보 등록
     * 대상 테이블 : LF_DMUSER.TB_MBID_CSV_DNDL_HIST
     * ================================================================
     * */
    public int insertOverallStatusDownLoadEmplHist(Map<String, ?> pmParam) throws Exception {
        return insert("DlwOverallStatusMap.insertOverallStatusDownLoadEmplHist", pmParam);
    }

    /** ================================================================
     * 날짜 : 20210204
     * 이름 : 김주용
     * 추가내용 : 일일종합현황데이터 처리일자 조회
     * 대상 테이블 : LF_DMUSER.TB_CSV_FILE_BATCH_DAY
     * ================================================================
     * */
    public List<Map<String, Object>> getOverallStatusBatchDay(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwOverallStatusMap.getOverallStatusBatchDay", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20210204
     * 이름 : 김주용
     * 추가내용 : 일일종합현황데이터 유효성 체크
     * 대상 테이블 : LF_DMUSER.TB_CSV_FILE_BATCH_DAY
     * ================================================================
     * */
    public int validationOverallStatusFile(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwOverallStatusMap.validationOverallStatusFile", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20210204
     * 이름 : 김주용
     * 추가내용 : 일일종합현황데이터 처리일자 등록
     * 대상 테이블 : LF_DMUSER.TB_CSV_FILE_BATCH_DAY
     * ================================================================
     * */
    public int insertOverallStatusBatchDay(Map<String, ?> pmParam) throws Exception {
        return insert("DlwOverallStatusMap.insertOverallStatusBatchDay", pmParam);
    }

    /** ================================================================
     * 날짜 : 20210204
     * 이름 : 김주용
     * 추가내용 : 일일종합현황데이터 처리일자 삭제
     * 대상 테이블 : LF_DMUSER.TB_CSV_FILE_BATCH_DAY
     * ================================================================
     * */
    public int deleteOverallStatusBatchDay(Map<String, ?> pmParam) throws Exception {
        return delete("DlwOverallStatusMap.deleteOverallStatusBatchDay", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20210204
     * 이름 : 김주용
     * 추가내용 : 일일종합현황데이터 엑셀파일 배치일자 조회
     * 대상 테이블 : LF_DMUSER.TB_CSV_FILE_BATCH_DAY
     * ================================================================
     * */
    public List<Map<String, Object>> getOverallStatusExcelBatchDay(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwOverallStatusMap.getOverallStatusExcelBatchDay", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20210204
     * 이름 : 김주용
     * 추가내용 : 일일종합현황데이터 File 목록 INSERT
     * 대상 테이블 : LF_DMUSER.TB_MBID_CSV_FILE_LIST
     * ================================================================
     * */
    public int insertOverallStatusExcelFileList(Map<String, ?> pmParam) throws Exception {
        return insert("DlwOverallStatusMap.insertOverallStatusExcelFileList", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20210204
     * 이름 : 김주용
     * 추가내용 : 종합현황데이터 건수
     * 대상 테이블 : LF_DMUSER.TB_CSV_FILE_BATCH_DAY
     * ================================================================
     * */
    public int selectTotStatusCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwOverallStatusMap.selectTotStatusCount", pmParam);
    }
}
