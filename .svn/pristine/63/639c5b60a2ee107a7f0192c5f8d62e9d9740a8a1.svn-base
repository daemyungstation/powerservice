package powerservice.business.dlw.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface DlwWdrwService {

    public List<Map<String, Object>> getDlwWdrwListByReqbit(Map<String, ?> pmParam) throws Exception; // (상세구분별) 산출 조회

    public List<Map<String, Object>> saveTempWdrw(Map<String, ?> pmParam) throws Exception; // 임시건 산출

    public List<Map<String, Object>> getDlwWdrwAcntInfo(Map<String, ?> pmParam) throws Exception; // 산출 회원정보 조회

    public void addWdrwTemp(Map<String, Object> pmParam) throws Exception; // 임의등록

    public void delWdrwTemp(Map<String, Object> pmParam) throws Exception; // 임의삭제

    public List<Map<String, Object>> getDlwAccntBaseInfo(Map<String, ?> pmParam) throws Exception; // 고객정보 조회

    /**
     * 고객정보 카운트조회
     * 정승철
     * 20181113
     */
    public int getCntDlwAccntBaseInfo(Map<String, ?> pmParam) throws Exception;

    /**
     * 고객정보 조회(팝업용)
     * 정승철
     * 20181113
     */
    public List<Map<String, Object>> getDlwAccntBaseInfo_popup(Map<String, ?> pmParam) throws Exception;

    public int getDlwLastPayNo(Map<String, ?> pmParam) throws Exception; // 최종납입회차 조회

    public int getInvAmt(Map<String, ?> pmParam) throws Exception; //  납입액 조회


    /**
     * 파일 작성 기초 리스트
     * 임동진
     * 20180927
    */
    public List<Map<String, Object>> getWdrwReqList(Map<String, ?> pmParam) throws Exception;
    
    /**
     * CARD 무승인 파일 작성 취소 리스트
     * 송준빈
     * 20191211
    */
    public List<Map<String, Object>> getWdrwReqCnclList(Map<String, ?> pmParam) throws Exception;

    /**
     * 청구데이터 INSERT
     * 임동진
     * 20181004
    */
    public int insertReqWdrw(Map<String, ?> pmParam) throws Exception;

    /**
     * CMS기초정보 READ
     * 임동진
     * 20181004
    */
    public List<Map<String, Object>> getWdrwDlwCmsComnInfo(Map<String, ?> pmParam) throws Exception;


    /**
     * 산출 가능 일자 체크
     * 임동진
     * 20181005
    */
    public List<Map<String, Object>> getWdrwReqCheck(Map<String, ?> pmParam) throws Exception; // (상세구분별) 산출 조회

    /**
     * 산출마감체크 조회
     * 정승철
     * 20181012
    */
    public List<Map<String, Object>> getWdrwExtCheck(Map<String, ?> pmParam) throws Exception;

    /**
     * 산출마감체크 저장
     * 정승철
     * 20181012
    */
    public void saveExtChk(Map<String, ?> pmParam) throws Exception;

    /**
     * 산출마감체크 삭제
     * 정승철
     * 20181012
    */
    public void delExtChk(Map<String, ?> pmParam) throws Exception;

    /**
     * 산출마감 입력유효성체크
     * 정승철
     * 20181012
    */
    public int chkWdrwExt(Map<String, ?> pmParam) throws Exception;

    /**
     * 휴일여부 체크
     * 정승철
     * 20181012
    */
    public String getHolidayChk(Map<String, ?> pmParam) throws Exception;

    /**
     * 실시간 결제 대상 회원 정보
     * 임동진
     * 20181012
    */
    public List<Map<String, Object>> getRealTimeReqList(Map<String, ?> pmParam) throws Exception;

    /**
     * 실시간 결제 실행 SERVICE
     * 임동진
     * 20181012
    */
    public Map<String, Object> RealTimeReqProc(Map<String, Object> pmParam) throws Exception;

    /**
     * 카드 / CMS 결과데이터 인서트 (실시간, 파일배치)
     * 임동진
     * 20181015
    */
    public int insertReqWdrwResult(Map<String, ?> pmParam) throws Exception;

    /**
     * 성공한 데이터 회원 입금 데이터 insert
     * 임동진
     * 20181016
    */
    public int insertMemPayData(Map<String, ?> pmParam) throws Exception;

    /**
     * CMS 결과반영 데이터 업로드
     * 임동진
     * 20181022
    */
    public void dataFileUpload(HttpServletRequest request, HttpServletResponse response, Map<String, Object> mResuslt) throws Exception; // 데이터 파일 업로드

    /**
     * CMS파일 작성 시 RESULT키 업데이트 처리  향후 CMS 결과 데이터와 조인
     * 임동진
     * 20181023
    */
    public int uptReqResultKey(Map<String, ?> pmParam) throws Exception;

    /**
     * 가상계좌 청구된 회원 리스트
     * 임동진
     * 20181029
    */
    public List<Map<String, Object>> getVirtualReqList(Map<String, ?> pmParam) throws Exception;

    /**
     * 가상계좌 산출 대상 리스트
     * 임동진
     * 20181029
    */
    public List<Map<String, Object>> getVirtualExtList(Map<String, ?> pmParam) throws Exception;

    /**
     * 가상계좌 산출 INSERT
     * 임동진
     * 20181030
    */
    public int insertVirtualMemData(Map<String, ?> pmParam) throws Exception;

    /**
    * 가상계좌 산출데이터 청구 등록
    * 임동진
    * 20181030
    */
    public int insertVirtualReq(Map<String, ?> pmParam) throws Exception;

    /**
    * 가상계좌 전송 전  청구 여부 확인
    * 임동진
    * 20181101
    */
    public List<Map<String, Object>> getVirtualReqYn(Map<String, ?> pmParam) throws Exception; // 산출 회원정보 조회

    /**
    * 가상계좌 NICE 전송 후 TID업데이트 혹은 실패 처리
    * 임동진
    * 20181101
    */
    public int updateVirtualReqSendtoNice(Map<String, ?> pmParam) throws Exception;

    /**
     * 가상계좌 산출 현황 조회 LIST
     * 임동진
     * 20181105
     */
    public List<Map<String, Object>> getVirtualReqResultList(Map<String, ?> pmParam) throws Exception;

    /**
     * 가상계좌 산출 현황 조회 LIST COUNT
     * 임동진
     * 20181105
     */
    public int getVirtualReqResultCnt(Map<String, ?> pmParam) throws Exception;

    /**
     * 가상계좌 청구 중 강제 취소
     * 임동진
     * 20181105
     */
    public int updateVirtualReqDelete(Map<String, Object> pmParam) throws Exception;

    /**
     * 가상계좌 산출 대상자 삭제
     * 임동진
     * 20181105
     */
    public int updateVirtualExtDelete(Map<String, ?> pmParam) throws Exception;

    /** ================================================================
     * 날짜 : 20181211
     * 이름 : 송준빈
     * 추가내용 : 청구강제마감 업데이트
     * 대상 테이블 : TB_MEMBER_WDRW_REQ
     * ================================================================
     * */
    public int updateCompulsionWdrdList(Map<String, Object> pmParam);

    /**
     * 특수 산출
     * 정승철
     * 20181211
     */
    public List<Map<String, Object>> saveSpecialWdrw(Map<String, ?> pmParam) throws Exception;
    
    /** ================================================================
     * 날짜 : 20191202
     * 이름 : 송준빈
     * 추가내용 : 카드 무승인 데이터 resultKey추출 (가맹점 번호 기준)
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
     * ================================================================
     */
    public int uptReqNauthResultKey(Map<String, ?> pmParam) throws Exception;
    
    /** ================================================================
     * 날짜 : 20191202
     * 이름 : 송준빈
     * 추가내용 : 카드 무승인 데이터 개수 추출 (가맹점 번호 기준)
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
     * ================================================================
     */
    public List<Map<String, Object>> getWdrwNauthReqFranCnt(Map<String, ?> pmParam) throws Exception;
    
    /** ================================================================
     * 날짜 : 20191202
     * 이름 : 송준빈
     * 추가내용 : 카드 무승인 데이터 추출
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
     * ================================================================
     */
    public List<Map<String, Object>> getWdrwNauthReqList(Map<String, ?> pmParam) throws Exception;
    
    /** ================================================================
     * 날짜 : 20191202
     * 이름 : 송준빈
     * 추가내용 : 카드 무승인 데이터 개수 추출 (가맹점 번호 기준)
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
     * ================================================================
     */
    public List<Map<String, Object>> getWdrwNauthReqTotal(Map<String, ?> pmParam) throws Exception;
    
    /** ================================================================
     * 날짜 : 20191209
     * 이름 : 송준빈
     * 추가내용 : 카드 무승인 데이터 산출
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_REQ_NAUTH_CNCL
     * ================================================================
     */
    public int updateNauthCancelCalc(Map<String, ?> pmParam) throws Exception;
    
    /** ================================================================
     * 날짜 : 20191202
     * 이름 : 송준빈
     * 추가내용 : 카드 무승인 프랜차이즈 개수 추출
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
     * ================================================================
     * */
    public int getWdrwNauthReqFranCount(Map<String, ?> pmParam) throws Exception;
    
    /** ================================================================
     * 날짜 : 20200226
     * 이름 : 송준빈
     * 추가내용 : 가상계좌 산출데이터 청구 등록 NEW
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
     * ================================================================
     * */
    public int insertVirtualReqRefac(Map<String, ?> pmParam) throws Exception;
    
    /** ================================================================
     * 날짜 : 20181018
     * 이름 : 송준빈
     * 추가내용 : 출금이체의뢰내역(청구) 조회건수
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
     * ================================================================
     * */
    public int getDayCardNauthCount(Map<String, Object> pmParam);
}