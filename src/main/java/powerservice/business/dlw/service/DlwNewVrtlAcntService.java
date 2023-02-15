package powerservice.business.dlw.service;

import java.util.List;
import java.util.Map;

public interface DlwNewVrtlAcntService {
	
	/** ================================================================
     * 날짜 : 20200224
     * 이름 : 송준빈
     * 추가내용 : 가상계좌 정보 조회수
     * 대상 테이블 : LF_DMUSER.TB_NICE_VRTL_ACCNT_INFO
     * ================================================================
     * */
	public int getVrtlAccntInfoCount(Map<String, ?> pmParam) throws Exception;
    
	/** ================================================================
     * 날짜 : 20200224
     * 이름 : 송준빈
     * 추가내용 : 가상계좌 정보 조회 리스트
     * 대상 테이블 : LF_DMUSER.TB_NICE_VRTL_ACCNT_INFO
     * ================================================================
     * */
    public List<Map<String, Object>> getVrtlAccntInfo(Map<String, ?> pmParam) throws Exception;
    
    /** ================================================================
     * 날짜 : 20200224
     * 이름 : 송준빈
     * 추가내용 : 가상계좌 정보 강제마감처리
     * 대상 테이블 : LF_DMUSER.TB_NICE_VRTL_ACCNT_INFO
     * ================================================================
     * */
    public int updateVrtlAccntRecoverProc(Map<String, ?> pmParam) throws Exception;
    
    /** ================================================================
     * 날짜 : 20200224
     * 이름 : 송준빈
     * 추가내용 : 가상계좌번호에 고객 고유번호를 부여하기 전 등록여부 확인
     * 대상 테이블 : LF_DMUSER.TB_NICE_VRTL_ACCNT_INFO
     * ================================================================
     * */
    public int getMemNoRegistCnt(Map<String, ?> pmParam);
    
    /** ================================================================
     * 날짜 : 20200224
     * 이름 : 송준빈
     * 추가내용 : 가상계좌번호에 고객 고유번호를 부여하기 전 고유번호 유효성체크
     * 대상 테이블 : LF_DMUSER.MEMBER
     * ================================================================
     * */
    public int getMemNoExists(Map<String, ?> pmParam);
    
    /** ================================================================
     * 날짜 : 20200224
     * 이름 : 송준빈
     * 추가내용 : 가상계좌번호에 고객 고유번호를 부여
     * 대상 테이블 : LF_DMUSER.TB_NICE_VRTL_ACCNT_INFO
     * ================================================================
     * */
    public int updateVrtlAccntMemNo(Map<String, ?> pmParam) throws Exception;
}
