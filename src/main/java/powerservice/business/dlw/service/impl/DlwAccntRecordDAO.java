package powerservice.business.dlw.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

@Repository
public class DlwAccntRecordDAO extends EgovAbstractMapper {
	
	@Resource(name="dlwSqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }
	
	/** ================================================================
     * 날짜 : 20200731
     * 이름 : 송준빈
     * 추가내용 : 고객 녹취파일 업로드 리스트
     * 대상 테이블 : LF_DMUSER.TB_MEM_ACCNT_RECORD
     * ================================================================
     * */
    public List<Map<String, Object>> getFileUploadList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwAccntRecordMap.getFileUploadList", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20200731
     * 이름 : 송준빈
     * 추가내용 : 고객 녹취파일 삭제
     * 대상 테이블 : LF_DMUSER.TB_MEM_ACCNT_RECORD
     * ================================================================
     * */
    public int deleteUploadRecData(Map<String, Object> pmParam) throws Exception {
        return update("DlwAccntRecordMap.deleteUploadRecData", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20200731
     * 이름 : 송준빈
     * 추가내용 : 고객 녹취파일 업로드 권한 확인
     * 대상 테이블 : LF_DMUSER.EMPLOYEE
     * ================================================================
     * */
    public String getUploadAuthConfirm(Map<String, Object> pmParam) throws Exception {
        return (String) selectOne("DlwAccntRecordMap.getUploadAuthConfirm", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20200731
     * 이름 : 송준빈
     * 추가내용 : 고객 녹취파일 업로드 중복여부 확인
     * 대상 테이블 : LF_DMUSER.TB_MEM_ACCNT_RECORD
     * ================================================================
     * */
    public int getRecFileDuplicateConfirm(Map<String, Object> pmParam) throws Exception {
        return (Integer) selectOne("DlwAccntRecordMap.getRecFileDuplicateConfirm", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20200731
     * 이름 : 송준빈
     * 추가내용 : 고객 녹취파일 업로드 정보 인서트
     * 대상 테이블 : LF_DMUSER.TB_MEM_ACCNT_RECORD
     * ================================================================
     * */
    public int insertRecUploadFile(Map<String, ?> pmParam) throws Exception {
        return insert("DlwAccntRecordMap.insertRecUploadFile", pmParam);
    }
}