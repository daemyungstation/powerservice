package powerservice.business.dlw.service;

import java.util.List;
import java.util.Map;

public interface DlwIvrMgmtService {
	
	/** ================================================================
     * 날짜 : 20210810
     * 이름 : 송준빈
     * 추가내용 : IVR관리 조회수
     * 대상 테이블 : PS_WILLVI.TB_IVR_MAIN_MNG
     * ================================================================
     * */
	public int getIvrMgmtExtCount(Map<String, ?> pmParam) throws Exception;
    
	/** ================================================================
     * 날짜 : 20210810
     * 이름 : 송준빈
     * 추가내용 : IVR관리 조회리스트
     * 대상 테이블 : PS_WILLVI.TB_IVR_MAIN_MNG
     * ================================================================
     * */
    public List<Map<String, Object>> getIvrMgmtExtList(Map<String, ?> pmParam) throws Exception;
    
    /** ================================================================
     * 날짜 : 20210810
     * 이름 : 송준빈
     * 추가내용 : WORK관리 조회수
     * 대상 테이블 : PS_WILLVI.TB_IVR_WORK_MNG
     * ================================================================
     * */
	public int getWorkMgmtExtCount(Map<String, ?> pmParam) throws Exception;
    
	/** ================================================================
     * 날짜 : 20210810
     * 이름 : 송준빈
     * 추가내용 : WORK관리 조회리스트
     * 대상 테이블 : PS_WILLVI.TB_IVR_WORK_MNG
     * ================================================================
     * */
    public List<Map<String, Object>> getWorkMgmtExtList(Map<String, ?> pmParam) throws Exception;
    
    /** ================================================================
     * 날짜 : 20210810
     * 이름 : 송준빈
     * 추가내용 : 대표번호관리 조회수
     * 대상 테이블 : PS_WILLVI.TB_IVR_MASTER_NO
     * ================================================================
     * */
	public int getGenenumMgmtExtCount(Map<String, ?> pmParam) throws Exception;
    
	/** ================================================================
     * 날짜 : 20210810
     * 이름 : 송준빈
     * 추가내용 : 대표번호관리 조회리스트
     * 대상 테이블 : PS_WILLVI.TB_IVR_MASTER_NO
     * ================================================================
     * */
    public List<Map<String, Object>> getGenenumMgmtExtList(Map<String, ?> pmParam) throws Exception;
    
    /** ================================================================
     * 날짜 : 20210810
     * 이름 : 송준빈
     * 추가내용 : 휴일관리 조회수
     * 대상 테이블 : PS_WILLVI.TB_IVR_HOLIDAY_MNG
     * ================================================================
     * */
	public int getHolydayMgmtExtCount(Map<String, ?> pmParam) throws Exception;
    
	/** ================================================================
     * 날짜 : 20210810
     * 이름 : 송준빈
     * 추가내용 : 휴일관리 조회리스트
     * 대상 테이블 : PS_WILLVI.TB_IVR_HOLIDAY_MNG
     * ================================================================
     * */
    public List<Map<String, Object>> getHolydayMgmtExtList(Map<String, ?> pmParam) throws Exception;
    
    /** ================================================================
     * 날짜 : 20210810
     * 이름 : 송준빈
     * 추가내용 : 스크립트관리 조회수
     * 대상 테이블 : PS_WILLVI.TB_IVR_SCRIPT_MNG
     * ================================================================
     * */
	public int getScriptMgmtExtCount(Map<String, ?> pmParam) throws Exception;
    
	/** ================================================================
     * 날짜 : 20210810
     * 이름 : 송준빈
     * 추가내용 : 스크립트관리 조회리스트
     * 대상 테이블 : PS_WILLVI.TB_IVR_SCRIPT_MNG
     * ================================================================
     * */
    public List<Map<String, Object>> getScriptMgmtExtList(Map<String, ?> pmParam) throws Exception;
    
    /** ================================================================
     * 날짜 : 20210810
     * 이름 : 송준빈
     * 추가내용 : IVR관리 기본정보 수정
     * 대상 테이블 : PS_WILLVI.TB_IVR_MAIN_MNG
     * ================================================================
     * */
    public int updateIvrMgmtExtList(Map<String, ?> pmParam) throws Exception;
    
    /** ================================================================
     * 날짜 : 20210810
     * 이름 : 송준빈
     * 추가내용 : IVR관리 기본정보 중복 저장여부
     * 대상 테이블 : PS_WILLVI.TB_IVR_MAIN_MNG
     * ================================================================
     * */
    public int existIvrMgmtExtList(Map<String, ?> pmParam) throws Exception;
    
    /** ================================================================
     * 날짜 : 20210810
     * 이름 : 송준빈
     * 추가내용 : IVR관리 기본정보 입력
     * 대상 테이블 : PS_WILLVI.TB_IVR_MAIN_MNG
     * ================================================================
     * */
    public int insertIvrMgmtExtList(Map<String, ?> pmParam) throws Exception;
    
    /** ================================================================
     * 날짜 : 20210810
     * 이름 : 송준빈
     * 추가내용 : WORK관리 기본정보 수정
     * 대상 테이블 : PS_WILLVI.TB_IVR_WORK_MNG 
     * ================================================================
     * */
    public int updateWorkMgmtExtList(Map<String, ?> pmParam) throws Exception;
    
    /** ================================================================
     * 날짜 : 20210810
     * 이름 : 송준빈
     * 추가내용 : WORK관리 기본정보 중복 저장여부
     * 대상 테이블 : PS_WILLVI.TB_IVR_WORK_MNG 
     * ================================================================
     * */
    public int existWorkMgmtExtList(Map<String, ?> pmParam) throws Exception;
    
    /** ================================================================
     * 날짜 : 20210810
     * 이름 : 송준빈
     * 추가내용 : WORK관리 기본정보 입력
     * 대상 테이블 : PS_WILLVI.TB_IVR_WORK_MNG 
     * ================================================================
     * */
    public int insertWorkMgmtExtList(Map<String, ?> pmParam) throws Exception;
    
    /** ================================================================
     * 날짜 : 20210810
     * 이름 : 송준빈
     * 추가내용 : 대표번호관리 기본정보 수정
     * 대상 테이블 : PS_WILLVI.TB_IVR_MASTER_NO
     * ================================================================
     * */
    public int updateGenenumMgmtExtList(Map<String, ?> pmParam) throws Exception;
    
    /** ================================================================
     * 날짜 : 20210810
     * 이름 : 송준빈
     * 추가내용 : 대표번호관리 기본정보 중복 저장여부
     * 대상 테이블 : PS_WILLVI.TB_IVR_MASTER_NO
     * ================================================================
     * */
    public int existGenenumMgmtExtList(Map<String, ?> pmParam) throws Exception;
    
    /** ================================================================
     * 날짜 : 20210810
     * 이름 : 송준빈
     * 추가내용 : 대표번호관리 기본정보 입력
     * 대상 테이블 : PS_WILLVI.TB_IVR_MASTER_NO
     * ================================================================
     * */
    public int insertGenenumMgmtExtList(Map<String, ?> pmParam) throws Exception;
    
    /** ================================================================
     * 날짜 : 20210810
     * 이름 : 송준빈
     * 추가내용 : 휴일관리 기본정보 수정
     * 대상 테이블 : PS_WILLVI.TB_IVR_HOLIDAY_MNG
     * ================================================================
     * */
    public int updateHolydayMgmtExtList(Map<String, ?> pmParam) throws Exception;
    
    /** ================================================================
     * 날짜 : 20210810
     * 이름 : 송준빈
     * 추가내용 : 휴일관리 기본정보 중복 저장여부
     * 대상 테이블 : PS_WILLVI.TB_IVR_HOLIDAY_MNG
     * ================================================================
     * */
    public int existHolydayMgmtExtList(Map<String, ?> pmParam) throws Exception;
    
    /** ================================================================
     * 날짜 : 20210810
     * 이름 : 송준빈
     * 추가내용 : 휴일관리 기본정보 입력
     * 대상 테이블 : PS_WILLVI.TB_IVR_HOLIDAY_MNG
     * ================================================================
     * */
    public int insertHolydayMgmtExtList(Map<String, ?> pmParam) throws Exception;
    
    /** ================================================================
     * 날짜 : 20210810
     * 이름 : 송준빈
     * 추가내용 : 스크립트관리 기본정보 수정
     * 대상 테이블 : PS_WILLVI.TB_IVR_SCRIPT_MNG
     * ================================================================
     * */
    public int updateScriptMgmtExtList(Map<String, ?> pmParam) throws Exception;
    
    /** ================================================================
     * 날짜 : 20210810
     * 이름 : 송준빈
     * 추가내용 : 스크립트관리 기본정보 입력
     * 대상 테이블 : PS_WILLVI.TB_IVR_SCRIPT_MNG
     * ================================================================
     * */
    public int insertScriptMgmtExtList(Map<String, ?> pmParam) throws Exception;
    
    /** ================================================================
     * 날짜 : 20210810
     * 이름 : 송준빈
     * 추가내용 : IVR관리 기본정보 삭제
     * 대상 테이블 : PS_WILLVI.TB_IVR_MAIN_MNG
     * ================================================================
     * */
    public int deleteIvrMgmtExtList(Map<String, ?> pmParam) throws Exception;
    
    /** ================================================================
     * 날짜 : 20210810
     * 이름 : 송준빈
     * 추가내용 : WORK관리 기본정보 삭제
     * 대상 테이블 : PS_WILLVI.TB_IVR_WORK_MNG
     * ================================================================
     * */
    public int deleteWorkMgmtExtList(Map<String, ?> pmParam) throws Exception;
    
    /** ================================================================
     * 날짜 : 20210810
     * 이름 : 송준빈
     * 추가내용 : 대표번호관리 기본정보 삭제
     * 대상 테이블 : PS_WILLVI.TB_IVR_MASTER_NO
     * ================================================================
     * */
    public int deleteGenenumMgmtExtList(Map<String, ?> pmParam) throws Exception;
    
    /** ================================================================
     * 날짜 : 20210810
     * 이름 : 송준빈
     * 추가내용 : 휴일관리 기본정보 삭제
     * 대상 테이블 : PS_WILLVI.TB_IVR_HOLIDAY_MNG
     * ================================================================
     * */
    public int deleteHolydayMgmtExtList(Map<String, ?> pmParam) throws Exception;
    
    /** ================================================================
     * 날짜 : 20210810
     * 이름 : 송준빈
     * 추가내용 : 스크립트관리 기본정보 삭제
     * 대상 테이블 : PS_WILLVI.TB_IVR_SCRIPT_MNG
     * ================================================================
     * */
    public int deleteScriptMgmtExtList(Map<String, ?> pmParam) throws Exception;
}
