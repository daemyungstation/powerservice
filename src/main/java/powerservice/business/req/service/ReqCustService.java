package powerservice.business.req.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;

public interface ReqCustService {

    /**
     * 산출 가능 일자 체크
     * 임동진
     * 20181005
    */
    public List<Map<String, Object>> getWdrwReqCheck(Map<String, ?> pmParam) throws Exception; // (상세구분별) 산출 조회

    public List<Map<String, Object>> getDlwWdrwListByReqbit(Map<String, ?> pmParam) throws Exception; // (상세구분별) 산출 조회


    /**
     * 결과 데이터 건수조회
     * 정승철
     * 20181015
    */
    public List<Map<String, Object>> getReqResultCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> saveTempWdrw(Map<String, ?> pmParam) throws Exception; // 임시건 산출

    /**
     * 특수 산출
     * 정승철
     * 20181211
     */
    public List<Map<String, Object>> saveSpecialWdrw(Map<String, ?> pmParam) throws Exception;

    /** ================================================================
     * 날짜 : 20181023
     * 이름 : 임동진
     * 추가내용 : 결과 데이터 확인 후 청구 테이블 결과 업데이트 처리
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
     * ================================================================
     * */
    public int uptReqResultStat(Map<String, ?> pmParam) throws Exception;

    /**
     * 성공한 데이터 회원 입금 데이터 insert
     * 임동진
     * 20181016
    */
    public int insertMemPayData(Map<String, ?> pmParam) throws Exception;

    /**
     * 빌키 대량 자동 생성기
     * 임동진
     * 20181016
    */
    public int insertBillkeyMakeData(Map<String, ?> pmParam) throws Exception;

    /** ================================================================
     * 날짜 : 20181018
     * 이름 : 송준빈
     * 추가내용 : 출금이체의뢰내역(청구) 조회건수
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
     * ================================================================
     * */
    public int getDayCardNauthCount(Map<String, Object> pmParam);

    /** ================================================================
     * 날짜 : 20191209
     * 이름 : 송준빈
     * 추가내용 : 카드 무승인 데이터 산출
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_REQ_NAUTH_CNCL
     * ================================================================
     */
    public int updateNauthCancelCalc(Map<String, ?> pmParam) throws Exception;

    /**
     * CMS파일 작성 시 RESULT키 업데이트 처리  향후 CMS 결과 데이터와 조인
     * 임동진
     * 20181023
    */
    public int uptReqResultKey(Map<String, ?> pmParam) throws Exception;

    /**
     * 파일 작성 기초 리스트
     * 임동진
     * 20180927
    */
    public List<Map<String, Object>> getWdrwReqList(Map<String, ?> pmParam) throws Exception;

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

    /**
     * CMS 결과반영 데이터 업로드
     * 임동진
     * 20181022
    */
    public void dataFileUpload(HttpServletRequest request, HttpServletResponse response, Map<String, Object> mResuslt) throws Exception; // 데이터 파일 업로드

    /**
     * CMS 결과 업로드 변경 작업
     * 임동진
     * 20220615
    */
    public void cmsDataFileUpload(HttpServletRequest request, HttpServletResponse response, Map<String, Object> mResuslt) throws Exception; // 데이터 파일 업로드

    /** ================================================================
     * 날짜 : 20190412
     * 이름 : 송준빈
     * 추가내용 : 일일청구 카드 회신 데이터 리스트 INSERT
     * 대상 테이블 : LF_DMUSER.TB_CARD_RECV_FILE_LIST
     * ================================================================
     * */
    public int insertCardRecvFileList(Map<String, ?> pmParam) throws Exception;

    /** ================================================================
     * 날짜 : 20190412
     * 이름 : 송준빈
     * 추가내용 : 일일청구 카드 회신 데이터 리스트 조회
     * 대상 테이블 : LF_DMUSER.TB_CARD_RECV_FILE_LIST
     * ================================================================
     * */
    public List<Map<String, Object>> getCardRecvFileList(Map<String, Object> pmParam);

    /** ================================================================
     * 날짜 : 20190412
     * 이름 : 송준빈
     * 추가내용 : 이니시스카드 회신파일 존재여부 확인
     * 대상 테이블 : LF_DMUSER.TB_INICARD_RECV_FILE_LIST
     * ================================================================
     * */
    public List<Map<String, Object>> getIniCardRecvFileConfirm(Map<String, Object> pmParam);

    /** ================================================================
     * 날짜 : 20181025
     * 이름 : 송준빈
     * 추가내용 : Card File List 수신리스트 결과 반영 여부 확인
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_RSLT
     * ================================================================
     * */
    public int getWdrwResultCount(Map<String, Object> pmParam);

    /** ================================================================
     * 날짜 : 20190412
     * 이름 : 송준빈
     * 추가내용 :  일일청구 카드 회신 데이터 리스트 UPDATE
     * 대상 테이블 : LF_DMUSER.TB_CARD_RECV_FILE_LIST
     * ================================================================
     * */
    public int updateCardRecvFileList(Map<String, ?> pmParam) throws Exception;

    /** ================================================================
     * 날짜 : 20190412
     * 이름 : 송준빈
     * 추가내용 : 일일청구 카드 회신 데이터 (무승인) 저장 여부
     * 대상 테이블 : LF_DMUSER.TB_CARDNAUTH_RECV_FILE_LIST
     * ================================================================
     * */
    public int getCardNauthRecvFileExist(Map<String, Object> pmParam);

    /** ================================================================
     * 날짜 : 20181018
     * 이름 : 송준빈
     * 추가내용 : 출금이체의뢰내역(청구) 조회건수
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
     * ================================================================
     * */
    public int getWdrwReqListCount(Map<String, Object> pmParam);

    /** ================================================================
     * 날짜 : 20181018
     * 이름 : 송준빈
     * 추가내용 : 출금이체의뢰내역(청구) 조회리스트
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
     * ================================================================
     * */
    public List<Map<String, Object>> getPayWdrwReqList(Map<String, Object> pmParam);

    /** ================================================================
     * 날짜 : 20181026
     * 이름 : 송준빈
     * 추가내용 : 출금이체의뢰내역(청구) Card 총 합계
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
     * ================================================================
     * */
    public List<Map<String, Object>> getCalcAndChargeCardSummary(Map<String, Object> pmParam);

    /** ================================================================
     * 날짜 : 20191223
     * 이름 : 송준빈
     * 추가내용 : 출금이체의뢰내역(청구) Card무승인 총 합계
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
     * ================================================================
     * */
    public List<Map<String, Object>> getCalcAndChargeCardNauthSummary(Map<String, Object> pmParam);

    /** ================================================================
     * 날짜 : 20181026
     * 이름 : 송준빈
     * 추가내용 : 출금이체의뢰내역(청구) CMS 총 합계
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
     * ================================================================
     * */
    public List<Map<String, Object>> getCalcAndChargeCMSSummary(Map<String, Object> pmParam);

    /**
     * 특수회원 카운트조회
     * 정승철
     * 20181019
    */
    public int getCntSpecialAcntList(Map<String, ?> pmParam) throws Exception;

    /**
     * 특수회원 조회
     * 정승철
     * 20181018
    */
    public List<Map<String, Object>> getSpecialAcntList(Map<String, ?> pmParam) throws Exception;

    /**
     * 특수회원 입력체크
     * 정승철
     * 20181113
    */
    public int getChkSpecialAcntList(Map<String, ?> pmParam) throws Exception;

    /**
     * 특수회원 입력
     * 정승철
     * 20181019
    */
    public void insertExtSpecial(Map<String, ?> pmParam) throws Exception;

    /**
     * 특수회원 수정
     * 정승철
     * 20181019
    */
    public void updateExtSpecial(Map<String, ?> pmParam) throws Exception;

    /**
     * 특수회원 삭제
     * 정승철
     * 20181019
    */
    public void deleteExtSpecial(Map<String, ?> pmParam) throws Exception;

    /** ================================================================
     * 날짜 : 20181029
     * 이름 : 정승철
     * 추가내용 : Card 결과로그 List 카운트 조회
     * ================================================================
     * */
    public int getReqRltmCardLogCount(Map<String, Object> pmParam);

    /** ================================================================
     * 날짜 : 20181029
     * 이름 : 정승철
     * 추가내용 : Card 결과로그 List 조회
     * ================================================================
     * */
    public List<Map<String, Object>> getReqRltmCardLogList(Map<String, ?> pmParam) throws Exception;

    public int getDlwCustAcntCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getDlwCustAcntList(Map<String, ?> pmParam) throws Exception;

    /** ================================================================
     * 날짜 : 20181107
     * 이름 : 임동진
     * 추가내용 : 실시간 카드 결제 취소 처리
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_RESULT
     * ================================================================
     * */
    public Map<String, Object> updateRealtimeCardCancel(Map<String, Object> pmParam) throws Exception;

    /** ================================================================
     * 날짜 : 20200504
     * 이름 : 임동진
     * 추가내용 : 실시간 카드 결제 취소 처리(이니시스)
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_RESULT
     * ================================================================
     * */
    public Map<String, Object> updateRealtimeInicisCardCancel(Map<String, Object> pmParam) throws Exception;

    /** ================================================================
     * 날짜 : 20200504
     * 이름 : 임동진
     * 추가내용 : 전환결제 취소처리 (이니시스)
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_RESULT
     * ================================================================
     * */
    public Map<String, Object> saveTransInicisCardCancel(Map<String, Object> pmParam) throws Exception;

    public Map<String, Object> updateDlwPymnCancNicepay(Map<String, Object> pmParam) throws Exception;

    /** ================================================================
     * 날짜 : 20181022
     * 이름 : 송준빈
     * 추가내용 : 출금이체의뢰내역(산출, 청구(행사, 해약)) 조회
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
     * ================================================================
     * */
    public List<Map<String, Object>> getWdrwReqAccntEventConfirm(String accnt_no) throws Exception;

    /** ================================================================
     * 날짜 : 20190924
     * 이름 : 송준빈
     * 추가내용 : 전환결제 취소
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_REQ_REFUND
     * ================================================================
     * */
    public int sendCancelNicePayment(Map<String, Object> pmParam) throws Exception;

    /** ================================================================
     * 날짜 : 20191209
     * 이름 : 송준빈
     * 추가내용 : 카드 무승인 결제 취소건 조회
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_REQ_NAUTH_CNCL
     * ================================================================
     * */
    public List<Map<String, Object>> getNauthPayCancel(Map<String, ?> pmParam) throws Exception;

    /** ================================================================
     * 날짜 : 20191216
     * 이름 : 김주용
     * 추가내용 : 무승인 등록 취소
     * 대상 테이블 : TB_MEMBER_REQ_NAUTH_CNCL
     * ================================================================
     * */
    public int cancelNauthpayDelete(Map<String, Object> pmParam);

    /** ================================================================
     * 날짜 : 20191216
     * 이름 : 김주용
     * 추가내용 : 무승인 환불
     * 대상 테이블 : TB_MEMBER_REQ_NAUTH_CNCL
     * ================================================================
     * */
    public int cancelNauthpayRefund(Map<String, Object> pmParam);

    /** ================================================================
     * 날짜 : 20200227
     * 이름 : 송준빈
     * 추가내용 : 회원별 카드,CMS 결과데이터 조회
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ, LF_DMUSER.TB_MEMBER_WDRW_RESULT, LF_DMUSER.TB_MEMBER_REQ_REFUND
     * ================================================================
     * */
    public List<Map<String, Object>> getWdrwAccntLogList(Map<String, ?> pmParam) throws Exception;

    /** ================================================================
     * 날짜 : 메인
     * 이름 : /dlw/cms/gasu-list
     * 추가내용 :
     * 대상 테이블 :
     * ================================================================
     * */
    public int getMemberRefundListCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getMemberRefundList(Map<String, ?> pmParam) throws Exception;

    /** ================================================================
     * 날짜 : 메인
     * 이름 : /dlw/cust/cust-acnt/list
     * 추가내용 :
     * 대상 테이블 :
     * ================================================================
     * */
    public Map<String, Object> getEmplDtptList(String psParam) throws Exception;

    public String getDataAthrFunc(Map<String, ?> pmParam) throws Exception;

    public int getDlwCustAccntListCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getDlwCustAccntList(Map<String, ?> pmParam) throws Exception;

    /** ================================================================
     * 날짜 : 20181127
     * 이름 : 정승철
     * 추가내용 : 회원의 환불회차정보를 검색
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_REQ_REFUND
     * ================================================================
     * */
    public List<Map<String, Object>> getRefundReqNoOfAccnt(Map<String, ?> pmParam) throws Exception;

    /** ================================================================
     * 날짜 : 팝업
     * 이름 : /dlw/cms/gasu-mng/delete
     * 추가내용 :
     * 대상 테이블 :
     * ================================================================
     * */
    public int deleteMemberRefundMng(Map<String, ?> pmParam) throws Exception;

    /** ================================================================
     * 날짜 : 팝업
     * 이름 : /dlw/cms/gasu-dtl/merge
     * 추가내용 :
     * 대상 테이블 :
     * ================================================================
     * */
    public int updateMemberRefundDtl(Map<String, ?> pmParam) throws Exception;

    public int insertMemberRefundDtl(Map<String, ?> pmParam) throws Exception;

    /** ================================================================
     * 날짜 : 팝업
     * 이름 : /dlw/cms/gasu-dtl/delete
     * 추가내용 :
     * 대상 테이블 :
     * ================================================================
     * */
    public int deleteMemberRefundDtl(Map<String, ?> pmParam) throws Exception;

    /** ================================================================
     * 날짜 : 20181127
     * 이름 : 정승철
     * 추가내용 : (CMS) 환불반영
     * ================================================================
     * */
    public Map<String, Object> updateByCmsRefundProcess(Map<String, Object> pmParam) throws Exception;

    /**
     * 고객정보 카운트조회
     * 정승철
     * 20181113
     */
    public int getCntDlwAccntBaseInfo(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getDlwAccntBaseInfo(Map<String, ?> pmParam) throws Exception; // 고객정보 조회

    /**
     * 고객정보 조회(팝업용)
     * 정승철
     * 20181113
     */
    public List<Map<String, Object>> getDlwAccntBaseInfo_popup(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getDlwWdrwAcntInfo(Map<String, ?> pmParam) throws Exception; // 산출 회원정보 조회

    /**
     * 휴일여부 체크
     * 정승철
     * 20181012
    */
    public String getHolidayChk(Map<String, ?> pmParam) throws Exception;

    public void addWdrwTemp(Map<String, Object> pmParam) throws Exception; // 임의등록

    public void addWdrwFreeTemp(Map<String, Object> pmParam) throws Exception; // 자유결제 임의등록

    public void delWdrwFreeTemp(Map<String, Object> pmParam) throws Exception; // 자유결제 임의등록 삭제

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

    public void delWdrwTemp(Map<String, Object> pmParam) throws Exception; // 임의삭제

    /**
     * 실시간 결제 대상 회원 정보
     * 임동진
     * 20181012
    */
    public List<Map<String, Object>> getRealTimeReqList(Map<String, ?> pmParam) throws Exception;


    /**
     * 실시간 결제 대상 회원 정보 (자유결제 다수 회차)
     * 임동진
     * 20200924
    */
    public List<Map<String, Object>> getRealTimeReqFreeList(Map<String, ?> pmParam) throws Exception;


    /**
     * 실시간 결제 실행 SERVICE
     * 임동진
     * 20181012
    */
    public Map<String, Object> RealTimeReqProc(Map<String, Object> pmParam) throws Exception;

    /**
     * 이니시스 실시간 결제 실행 SERVICE
     * 임동진
     * 20200504
    */
    public Map<String, Object> RealTimeInicisReqProc(Map<String, Object> pmParam) throws Exception;

    /**
     * 카드 / CMS 결과데이터 인서트 (실시간, 파일배치)
     * 임동진
     * 20181015
    */
    public int insertReqWdrwResult(Map<String, ?> pmParam) throws Exception;

    /** ================================================================
     * 날짜 : 20181031
     * 이름 : 정승철
     * 추가내용 : 자유결제 프로세스
     * ================================================================
     * */
    public Map<String, Object> billPayFreeProc(Map<String, Object> pmParam) throws Exception;

    /** ================================================================
     * 날짜 : 20200507
     * 이름 : 임동진
     * 추가내용 : 자유결제 프로세스(이니시스)
     * ================================================================
     * */
    public Map<String, Object> billPayInicisFreeProc(Map<String, Object> pmParam) throws Exception;


    /** ================================================================
     * 날짜 : 20210824
     * 이름 : 임동진
     * 추가내용 : 자유결제 프로세스(이니시스) 장기할부 결제
     * ================================================================
     * */
    public Map<String, Object> billPayInicisLongTermProc(Map<String, Object> pmParam) throws Exception;

    /** ================================================================
     * 날짜 : 20181031
     * 이름 : 정승철
     * 추가내용 : 자유결제 카드 결과데이터 INSERT
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_RSLT
     * ================================================================
     * */
    public int insertFreeCardResult(Map<String, ?> pmParam) throws Exception;


    /** ================================================================
     * 날짜 : 20210825
     * 이름 : 임동진
     * 추가내용 : 장기할부 정보 등록
     * 대상 테이블 : LF_DMUSER.TB_LTINSTALL_MNG
     * ================================================================
     * */
    public int insertLongTermData(Map<String, ?> pmParam) throws Exception;

    /**
     *  이니시스 일자별 결과 받은 데이터건수 조회
     * 임동진
     * 20200511
    */
    public int getInicisResultCount(Map<String, ?> pmParam) throws Exception;

    public int getDlwVrtlAcntClctCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getDlwVrtlAcntCsmm(Map<String, ?> pmParam) throws Exception;

    public int updateNiceVrtlAccntWdrwReq(Map<String, ?> pmParam) throws Exception;

    public int deleteDlwVrtlAcntClct(Map<String, Object> pmParam) throws Exception;

    /**
     * 가상계좌 청구된 회원 리스트
     * 임동진
     * 20181029
    */
    public List<Map<String, Object>> getVirtualReqList(Map<String, ?> pmParam) throws Exception;

    /**
     * 가상계좌 NICE 전송 후 TID업데이트 혹은 실패 처리
     * 임동진
     * 20181101
     */
     public int updateVirtualReqSendtoNice(Map<String, ?> pmParam) throws Exception;

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

     /** ================================================================
      * 날짜 : 20200518
      * 이름 : 임동진
      * 추가내용 : 가상계좌 메인정보 INSERT
      * ================================================================
      * */
     public int insertVirtualMain(Map<String, ?> pmParam) throws Exception;

     /** ================================================================
      * 날짜 : 20200518
      * 이름 : 임동진
      * 추가내용 : 가상계좌 메인정보 삭제 및 청구 업데이트
      * ================================================================
      * */
     public int updateVirtualMain(Map<String, ?> pmParam) throws Exception;

     /** ================================================================
      * 날짜 : 20200520
      * 이름 : 임동진
      * 추가내용 : 가상계좌 전송 프로세스(이니시스)
      * ================================================================
      * */
     public Map<String, Object> InicisVirtualProc(Map<String, Object> pmParam) throws Exception;

     /** ================================================================
      * 날짜 : 20191209
      * 이름 : 송준빈
      * 추가내용 : 카드 무승인 결제 취소 처리
      * 대상 테이블 : LF_DMUSER.TB_MEMBER_REQ_NAUTH_CNCL
      * ================================================================
      * */
     public int insertNauthPayCancel(Map<String, ?> pmParam) throws Exception;

     public void uploadEctChk(HttpServletRequest request, HttpServletResponse response, Map<String, Object> mResuslt) throws Exception;

     /** ================================================================
      * 날짜 : 20201014
      * 이름 : 송준빈
      * 추가내용 : BillKey 자동생성 리스트 조회수
      * 대상 테이블 : LF_DMUSER.MEM_PROD_ACCNT, LF_DMUSER.CARD_MEM
      * ================================================================
      * */
     public int getCreateBillKeyCount(Map<String, ?> pmParam) throws Exception;

     /** ================================================================
      * 날짜 : 20201014
      * 이름 : 송준빈
      * 추가내용 : BillKey 자동생성 리스트
      * 대상 테이블 : LF_DMUSER.MEM_PROD_ACCNT, LF_DMUSER.CARD_MEM
      * ================================================================
      * */
     public List<Map<String, Object>> getCreateBillKeyList(Map<String, ?> pmParam) throws Exception;

     /** ================================================================
      * 날짜 : 20210909
      * 이름 : 김주용
      * 추가내용 : 환불관리팝업조회
      * 대상 테이블 : LF_DMUSER.TB_MEMBER_REQ_REFUND
      * ================================================================
      * */
     public List<Map<String, Object>> getRefundReqPopList(Map<String, ?> pmParam) throws Exception;

     /** ================================================================
      * 날짜 : 20220217
      * 이름 : 김주용
      * 추가내용 : 2021년이전 결과관리 조회
      * ================================================================
      * */
     public int getReqRltmCardLogOldCount(Map<String, Object> pmParam);

     public List<Map<String, Object>> getReqRltmCardLogOldList(Map<String, ?> pmParam) throws Exception;

     /** ================================================================
      * 날짜 : 20220217
      * 이름 : 김주용
      * 추가내용 : 회원번호조회조건 결과관리 조회
      * ================================================================
      * */
     public int getReqRltmCardLogAccntNoCount(Map<String, Object> pmParam);

     public List<Map<String, Object>> getReqRltmCardLogAccntNoList(Map<String, ?> pmParam) throws Exception;

     /** ================================================================
      * 날짜 : 20220217
      * 이름 : 김주용
      * 추가내용 : 2021년이전 환불관리 조회
      * 대상 테이블 : TB_MEMBER_REQ_REFUND
      * ================================================================
      * */
     public int getMemberRefundListOldCount(Map<String, ?> pmParam) throws Exception;

     public List<Map<String, Object>> getMemberRefundOldList(Map<String, ?> pmParam) throws Exception;

     /** ================================================================
      * 날짜 : 20220217
      * 이름 : 김주용
      * 추가내용 : 회원번호조회조건 환불관리 조회
      * 대상 테이블 : TB_MEMBER_REQ_REFUND
      * ================================================================
      * */
     public int getMemberRefundListAccntNoCount(Map<String, ?> pmParam) throws Exception;

     public List<Map<String, Object>> getMemberRefundAccntNoList(Map<String, ?> pmParam) throws Exception;

     /**
      * 특수회원 카운트조회
      * 김주용
      * 20220516
     */
     public int getCustExceptionCount(Map<String, ?> pmParam) throws Exception;

     /**
      * 특수회원 조회
      * 김주용
      * 20220516
     */
     public List<Map<String, Object>> getCustExceptionList(Map<String, ?> pmParam) throws Exception;

     /**
      * 특수회원 입력체크
      * 김주용
      * 20220516
     */
     public int getChkCustExceptionList(Map<String, ?> pmParam) throws Exception;

     /**
      * 특수회원 입력
      * 김주용
      * 20220516
     */
     public void insertCustException(Map<String, ?> pmParam) throws Exception;

     /**
      * 특수회원 수정
      * 김주용
      * 20220516
     */
     public void updateCustException(Map<String, ?> pmParam) throws Exception;

     /**
      * 특수회원 삭제
      * 김주용
      * 20220516
     */
     public void deleteCustException(Map<String, ?> pmParam) throws Exception;

     /**
      * 회생 변제 관리 조회
      * 김주용
      * 20220516
     */
     public List<Map<String, Object>> getBankRupSetup(Map<String, ?> pmParam) throws Exception;

     /**
      * 회생 변제금 등록
      * 김주용
      * 20220516
     */
     public void insertBankRup(Map<String, ?> pmParam) throws Exception;

     /**
      * 회생 변제금 수정
      * 김주용
      * 20220516
     */
     public void updateBankRup(Map<String, ?> pmParam) throws Exception;

     /**
      * 회생 변제금 삭제
      * 김주용
      * 20220516
     */
     public void deleteBankRup(Map<String, ?> pmParam) throws Exception;

     /** ================================================================
      * 날짜 : 20220822
      * 이름 : 김주용
      * 추가내용 : 자유결제 프로세스(이니시스) 장기할부 결제(복사)
      * ================================================================
      * */
     public Map<String, Object> billPayInicisNewTypeLongTermProc(Map<String, Object> pmParam) throws Exception;

     /** ================================================================
      * 날짜 : 20220822
      * 이름 : 김주용
      * 추가내용 : 자유결제 카드 결과데이터 INSERT(복사)
      * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_RSLT
      * ================================================================
      * */
     public int insertNewTypeFreeCardResult(Map<String, ?> pmParam) throws Exception;

     /** ================================================================
      * 날짜 : 20220822
      * 이름 : 김주용
      * 추가내용 : 장기할부 정보 등록(복사)
      * 대상 테이블 : LF_DMUSER.TB_LTINSTALL_MNG
      * ================================================================
      * */
     public int insertNewTypeLongTermData(Map<String, ?> pmParam) throws Exception;

     /** ================================================================
      * 날짜 : 20230206
      * 이름 : 조용우
      * 추가내용 : 2022년 결과관리 조회
      * ================================================================
      * */
     public int getReqRltmCardLog2022Count(Map<String, Object> pmParam);

     public List<Map<String, Object>> getReqRltmCardLog2022List(Map<String, ?> pmParam) throws Exception;

     /** ================================================================
      * 날짜 : 20230207
      * 이름 : 조용우
      * 추가내용 : 2022년 환불관리 조회
      * ================================================================
      * */
     public int getMemberRefundList2022Count(Map<String, ?> pmParam) throws Exception;

     public List<Map<String, Object>> getMemberRefund2022List(Map<String, ?> pmParam) throws Exception;
}
