package powerservice.business.dlw.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

public interface DlwAccntRecordService {
    
	/** ================================================================
     * 날짜 : 20200731
     * 이름 : 송준빈
     * 추가내용 : 고객 녹취파일 업로드 리스트
     * 대상 테이블 : LF_DMUSER.TB_MEM_ACCNT_RECORD
     * ================================================================
     * */
    public List<Map<String, Object>> getFileUploadList(Map<String, ?> pmParam) throws Exception;
    
    /** ================================================================
     * 날짜 : 20200731
     * 이름 : 송준빈
     * 추가내용 : 고객 녹취파일 삭제
     * 대상 테이블 : LF_DMUSER.TB_MEM_ACCNT_RECORD
     * ================================================================
     * */
    public int deleteUploadRecData(XPlatformMapDTO xpDto) throws Exception;
    
    /** ================================================================
     * 날짜 : 20200731
     * 이름 : 송준빈
     * 추가내용 : 고객 녹취파일 업로드
     * 대상 테이블 : LF_DMUSER.EMPLOYEE, LF_DMUSER.TB_MEM_ACCNT_RECORD
     * ================================================================
     * */
	public void uploadRecData(HttpServletRequest request,HttpServletResponse response, Map<String, Object> mResult) throws Exception;
}
