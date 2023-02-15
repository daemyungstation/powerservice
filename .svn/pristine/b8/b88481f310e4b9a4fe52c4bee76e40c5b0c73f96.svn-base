package powerservice.business.web.service;

import java.util.List;
import java.util.Map;

public interface WebNiceSenderService {

    /* ================================================================
     * 날짜 : 20180221
     * 이름 : 송준빈
     * 추가내용 : 고객 타켓리스트 정보 조회
     * 대상 테이블 : TB_TRGT_CUST_DTPT
     * ================================================================
     * */
    public List<Map<String, Object>> getMemberNiceInfo(Map<String, ?> pmParam) throws Exception;


    /* ================================================================
     * 날짜 : 20180221
     * 이름 : 송준빈
     * 추가내용 : NICE 전자서명 대상 고객 저장
     * 대상 테이블 : TB_MEMBER_NICE_INFO
     * ================================================================
     * */
    public int insertMemberNiceInfo(Map<String, ?> pmParam) throws Exception;

    /* ================================================================
     * 날짜 : 20180221
     * 이름 : 송준빈
     * 추가내용 : 고객 타켓리스트 정보 업데이트 (고객생년월일)
     * 대상 테이블 : TB_TRGT_CUST_DTPT
     * ================================================================
     * */
    public int updateMemberNiceInfo(Map<String, ?> pmParam) throws Exception;

    /* ================================================================
     * 날짜 : 20180221
     * 이름 : 송준빈
     * 추가내용 : NICE 전자서명 대상 고객 정보 업데이트 (NICE에서 보내주는 데이터를 통하여 업데이트 됨)
     * 대상 테이블 : TB_MEMBER_NICE_INFO
     * ================================================================
     * */
    public int updateNiceSendStatement(Map<String, Object> pmParam);

    /* ================================================================
     * 날짜 : 20180221
     * 이름 : 송준빈
     * 추가내용 : NICE 전자서명 SMS 발송 내역 조회 총 건수
     * 대상 테이블 : TB_MEMBER_NICE_INFO, TB_MEMBER_NICE_INFO_HIST, TB_TRGT_CUST_DTPT, TB_USER
     * ================================================================
     * */
    public int getTotalCount(Map<String, Object> pmParam);

    /* ================================================================
     * 날짜 : 20180221
     * 이름 : 송준빈
     * 추가내용 : NICE 전자서명 SMS 발송 내역 조회
     * 대상 테이블 : TB_MEMBER_NICE_INFO, TB_MEMBER_NICE_INFO_HIST, TB_TRGT_CUST_DTPT, TB_USER
     * ================================================================
     * */
    public List<Map<String, Object>> getNiceSenderInfoList(Map<String, Object> pmParam);

    /* ================================================================
     * 날짜 : 20180221
     * 이름 : 송준빈
     * 추가내용 : NICE 전자서명 대상고객 히스토리 저장
     * 대상 테이블 : TB_MEMBER_NICE_INFO_HIST
     * ================================================================
     * */
    public int insertMemberNiceInfoHist(Map<String, Object> pmParam);

    /* ================================================================
     * 날짜 : 20180221
     * 이름 : 송준빈
     * 추가내용 : NICE 전자서명 대상 고객 조회
     * 대상 테이블 : TB_MEMBER_NICE_INFO
     * ================================================================
     * */
    public List<Map<String, Object>> getMemberNiceRetrieve(Map<String, Object> pmParam);

    /* ================================================================
     * 날짜 : 20180221
     * 이름 : 송준빈
     * 추가내용 : 고객 타켓리스트 정보 업데이트 (2차 방송시 수행됨, 고객의 고유번호와 회원번호가 업데이트됨)
     * 대상 테이블 : TB_TRGT_CUST_DTPT
     * ================================================================
     * */
    public int updateMemberNiceInfo2(Map<String, Object> pmParam);

    /* ================================================================
     * 날짜 : 20180221
     * 이름 : 송준빈
     * 추가내용 : NICE 전자서명 대상 고객 (2차 방송시 수행됨, 고객의 고유번호와 회원번호가 업데이트됨)
     * 대상 테이블 : TB_MEMBER_NICE_INFO
     * ================================================================
     * */
    public int updateMemberNiceInfo3(Map<String, Object> pmParam);

    /* ================================================================
     * 날짜 : 20180221
     * 이름 : 송준빈
     * 추가내용 : 고객의 증서내용 조회 (2차 발송시 NICE 개별부로 보내지게 됨)
     * ================================================================
     * */
    public List<Map<String, Object>> getCertfMng(Map<String, Object> pmParam);

    /* ================================================================
     * 날짜 : 20180221
     * 이름 : 송준빈
     * 추가내용 : 고객의 해약율과 해약금 조회 (2차 발송시 NICE 개별부로 보내지게 됨)
     * ================================================================
     * */
    public List<Map<String, Object>> getResnAmt(Map<String, Object> pmParam);

    /* ================================================================
     * 날짜 : 20180221
     * 이름 : 송준빈
     * 추가내용 : 고객의 NICE SafeKey 조회 이력 저장
     * ================================================================
     * */
    public int insertNiceSafekeySearchHis(Map<String, Object> pmParam) throws Exception;

    /* ================================================================
     * 날짜 : 20180302
     * 이름 : 송준빈
     * 추가내용 : NICE 1차완료/2차완료/1차취소/2차취소 여부 업데이트
     * 대상 테이블 : TB_MEMBER_NICE_INFO
     * ================================================================
     * */
    public int updateNiceFinishStatement(Map<String, Object> pmParam) throws Exception;

    /* ================================================================
     * 날짜 : 20180305
     * 이름 : 송준빈
     * 추가내용 : 1차 전문 처리중 에러(Exception)시 해당 값 삭제
     * 대상 테이블 : TB_MEMBER_NICE_INFO
     * ================================================================
     * */
    public int deleteExceptionHandlerNiceInfo(Map<String, Object> pmParam);

    /* ================================================================
     * 날짜 : 20180305
     * 이름 : 송준빈
     * 추가내용 : 1차 전문 처리중 에러(Exception)시 해당 값 초기화
     * 대상 테이블 : TB_TRGT_CUST_DTPT
     * ================================================================
     * */
    public int updateExceptionHandlerTrgtCust(Map<String, Object> pmParam);

    /* ================================================================
     * 날짜 : 20180305
     * 이름 : 송준빈
     * 추가내용 : 2차 전문 처리중 에러(Exception)시 고유번호와 회원번호 초기화
     * 대상 테이블 : TB_MEMBER_NICE_INFO
     * ================================================================
     * */
    public int updateExceptionHandlerNiceInfo2(Map<String, Object> pmParam);

    /* ================================================================
     * 날짜 : 20180305
     * 이름 : 송준빈
     * 추가내용 : 2차 전문 처리중 에러(Exception)시 고유번호와 회원번호 초기화
     * 대상 테이블 : TB_TRGT_CUST_DTPT
     * ================================================================
     * */
    public int updateExceptionHandlerTrgtCust2(Map<String, Object> pmParam);

    /* ================================================================
     * 날짜 : 20180309
     * 이름 : 송준빈
     * 추가내용 : 고객의 증서 이미지를 관리할 폴더를 만들기 위함임.
     * 대상 테이블 : MEM_PROD_ACCNT
     * ================================================================
     * */
    public List<Map<String, Object>> getMemberNiceJoinDt(Map<String, Object> pmParam);

    /* ================================================================
     * 날짜 : 20180710
     * 이름 : 송준빈
     * 추가내용 : 가입후 재전송 회원 정보를 조회한다.
     * 대상 테이블 : TB_MEMBER_NICE_INFO
     * ================================================================
     * */
    public int updateResendNicePdfFile(Map<String, Object> pmParam);

    /* ================================================================
     * 날짜 : 20180809
     * 이름 : 송준빈
     * 추가내용 : 최근 애큐온 인증 여부 Y/N
     * 대상 테이블 : TB_MEMBER_NICE_INFO
     * ================================================================
     * */
    public List<Map<String, Object>> getAcuonLatelyAuth(Map<String, Object> pmParam);

    /* ================================================================
     * 날짜 : 20180809
     * 이름 : 송준빈
     * 추가내용 : NICE 전자서명 2차대상 고객 저장
     * 대상 테이블 : TB_MEMBER_NICE_INFO
     * ================================================================
     * */
    public int insertMemberNiceInfoSecond(Map<String, ?> pmParam) throws Exception;

    /* ================================================================
     * 날짜 : 20180809
     * 이름 : 송준빈
     * 추가내용 : NICE 전자서명 2차 대상 고객 정보 업데이트
     * 대상 테이블 : TB_MEMBER_NICE_INFO_SECOND
     * ================================================================
     * */
    public int updateNiceSendStatement2(Map<String, Object> pmParam );

    /* ================================================================
     * 날짜 : 20180809
     * 이름 : 송준빈
     * 추가내용 : NICE 전자서명 대상 고객 조회
     * 대상 테이블 : TB_MEMBER_NICE_INFO
     * ================================================================
     * */
    public List<Map<String, Object>> getMemberNiceRetrieve2(Map<String, Object> pmParam);

    /* ================================================================
     * 날짜 : 20180809
     * 이름 : 송준빈
     * 추가내용 : NICE 1차완료/2차완료/1차취소/2차취소 여부 업데이트
     * 대상 테이블 : TB_MEMBER_NICE_INFO
     * ================================================================
     * */
    public int updateNiceFinishStatement2(Map<String, Object> pmParam) throws Exception;


    /* ================================================================
     * 날짜 : 20180824
     * 이름 : 송준빈
     * 추가내용 : 전자서명인증고객 관리 List
     * ================================================================
     * */
	public List<Map<String, Object>> getNiceAuthInformation(Map<String, Object> pmParam);

	/* ================================================================
     * 날짜 : 20180824
     * 이름 : 송준빈
     * 추가내용 : 전자서명가입고객 관리 List
     * ================================================================
     * */
	public List<Map<String, Object>> getNiceJoinInformation(Map<String, Object> pmParam);
	
	/* ================================================================
     * 날짜 : 20181015
     * 이름 : 송준빈
     * 추가내용 : 가입고객데이터 삭제
     * 대상 테이블 : TB_MEMBER_NICE_INFO_SECOND
     * ================================================================
     * */
    public int updateNiceJoinData(Map<String, Object> pmParam);
	
	/* ================================================================
     * 날짜 : 20181015
     * 이름 : 송준빈
     * 추가내용 : 2차 전문 처리중 에러(Exception)시 해당 값 삭제
     * 대상 테이블 : TB_MEMBER_NICE_INFO_SECOND
     * ================================================================
     * */
    public int deleteExceptionHandlerNiceInfoSecond(Map<String, Object> pmParam);
}
