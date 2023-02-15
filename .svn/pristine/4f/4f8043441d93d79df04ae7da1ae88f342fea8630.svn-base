package powerservice.business.dlw.service;

import java.util.List;
import java.util.Map;

import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

public interface DlwResnService {

    public int getEventChk(Map<String, ?> pmParam) throws Exception;

    public int getResnChk(Map<String, ?> pmParam) throws Exception;

    public int getTaxtChk(Map<String, ?> pmParam) throws Exception;

    public int getCmsChk(Map<String, ?> pmParam) throws Exception;

    public int getcallcenterChk(Map<String, ?> pmParam) throws Exception;

    public int getCmsReqCnt(Map<String, ?> pmParam) throws Exception;

    public String getSelectProdCl(Map<String, ?> pmParam) throws Exception;

    public String getSelectJoinType(Map<String, ?> pmParam) throws Exception;

    public int getResnGubn(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> getCmsInfo(Map<String, Object> pmParam) throws Exception;

    public int getResnCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getResnList(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> getResnDtpt(Map<String, ?> pmParam) throws Exception;

    public String getEmartIPoint(Map<String, ?> pmParam) throws Exception;

    public String getResnGasuAmt(Map<String, ?> pmParam) throws Exception;

    public int updateResnSenddt(Map<String, ?> pmParam) throws Exception;

    public int updateResnProc(Map<String, ?> pmParam) throws Exception;

    public int insertResn(Map<String, ?> pmParam) throws Exception;

    public int updateResn(Map<String, ?> pmParam) throws Exception;

    public String resnDel(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getResnStat1List(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getResnStat2List(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getResnStat3List(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getBuyChulList(Map<String, ?> pmParam) throws Exception; // 매입현황 - 매출입현황

    public List<Map<String, Object>> getAccPurList(Map<String, ?> pmParam) throws Exception; // 매입현황 - 매출입현황 합계

    public List<Map<String, Object>> getTmpMemPurList(Map<String, ?> pmParam) throws Exception; // (중간마감) 회원별매출입
    public List<Map<String, Object>> getMemPurMgList(Map<String, ?> pmParam) throws Exception;  // (최종마감) 회원별매출입
    public List<Map<String, Object>> getAccPurMgList(Map<String, ?> pmParam) throws Exception;  // (최종마감) 매출입현황

    public int getCntMemPurList(Map<String, ?> pmParam) throws Exception;       // 회원별매출입 기본데이터 카운트 조회
    public int srchCntAccntPurSale(Map<String, ?> pmParam) throws Exception;    // 회원별매출입 중간마감 카운트 조회
    public int srchCntAccntPurSale2(Map<String, ?> pmParam) throws Exception;   // 회원별매출입 중간마감 카운트 조회 (** 조건 제외)
    public int srchCntAccntPurSaleMg(Map<String, ?> pmParam) throws Exception;  // 회원별매출입 최종마감 카운트 조회
    public String srchMgDtAccntPurSaleMg(Map<String, ?> pmParam) throws Exception;  // 회원별매출입 최종마감일 조회

    public int saveAccntPurSale(Map<String, ?> pmParam) throws Exception;   // 회원별 상품모델 매입매출 저장
    public int updateAccntPurSale(Map<String, ?> pmParam) throws Exception; // 회원별 상품모델 매입매출 수정
    public int deleteAccntPurSale(Map<String, ?> pmParam) throws Exception; // 회원별 상품모델 매입매출 삭제

    public int saveAccntPurSaleMg(Map<String, ?> pmParam) throws Exception; // 회원별 상품모델 매입매출 최종마감처리

    public int getMemPurCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getMemPurList(Map<String, ?> pmParam) throws Exception; // 매입현황 - 매출입 회원 조회

    public List<Map<String, Object>> getBuyDtlList(Map<String, ?> pmParam) throws Exception; // 매입현황 - 매입현황상세조회

    public List<Map<String, Object>> selectResnAmtAccnt(Map<String, Object> pmParam) throws Exception;

    public long selectResnAmtSum(Map<String, Object> pmParam) throws Exception;

    public int callUPResnAmt(XPlatformMapDTO xpDto) throws Exception;

    public int deleteResnAmt(XPlatformMapDTO xpDto) throws Exception;

    /* ================================================================
     * 날짜 : 20171226
     * 이름 : 송준빈
     * 추가내용 : 쇼핑몰 연동 Table 에 고객의 상태를 해약접수, 해약완료, 유효 로 변경
     * ================================================================
     * */
    public int updateResnMemberState(Map<String, ?> pmParam) throws Exception;
    
    /* ================================================================
     * 날짜 : 20181114
     * 이름 : 송준빈
     * 추가내용 : 청구가 존재하는 회원인지 확인
     * ================================================================
     * */
	public List<Map<String, Object>> getWdrwReqConfirm(Map pmParam); 
	
	/* ================================================================
     * 날짜 : 20190710
     * 이름 : 송준빈
     * 추가내용 : 해약등록 엔터 입력시 해당 은행코드 리턴
     * 대상 테이블 : LF_DMUSER.COM_CD
     * ================================================================
     * */
	public List<Map<String, Object>> getCodeBank(String sCdNm);
	
	
	/* ================================================================
     * 날짜 : 20200221
     * 이름 : 김주용
     * 추가내용 : 해약조회(NEW)
     * 대상 테이블 : LF_DMUSER.RESCISSION
     * ================================================================
     * */
	public int getResnNewCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getResnNewList(Map<String, ?> pmParam) throws Exception;
    
    /* ================================================================
     * 날짜 : 20200221
     * 이름 : 김주용
     * 추가내용 : 해약조회(NEW)
     * 대상 테이블 : LF_DMUSER.RESCISSION
     * ================================================================
     * */
	public int getResnNewCount2(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getResnNewList2(Map<String, ?> pmParam) throws Exception;
    
    /* ================================================================
     * 날짜 : 20201126
     * 이름 : 김주용
     * 추가내용 : 회원 만기일자 조회
     * 대상 테이블 : LF_DMUSER.MEM_PROD_ACCNT, LF_DMUSER.PRODUCT
     * ================================================================
     * */
    public String getMangiDate(Map<String, ?> pmParam) throws Exception;
    
    /* ================================================================
     * 날짜 : 20220209
     * 이름 : 임동진
     * 추가내용 : 해약조회(NEW)
     * 대상 테이블 : LF_DMUSER.RESCISSION
     * ================================================================
     * */
	public int getResnNewCount3(Map<String, ?> pmParam) throws Exception;
	
    /* ================================================================
     * 날짜 : 20220209
     * 이름 : 임동진
     * 추가내용 : 해약조회(NEW)
     * 대상 테이블 : LF_DMUSER.RESCISSION
     * ================================================================
     * */
    public List<Map<String, Object>> getResnNewList3(Map<String, ?> pmParam) throws Exception;

    /* ================================================================
     * 날짜 : 20220210
     * 이름 : 임동진
     * 추가내용 : 해약등록 회원 조회(NEW)
     * 대상 테이블 : LF_DMUSER.RESCISSION
     * ================================================================
     * */
    public Map<String, Object> getResnAccntInfo(Map<String, ?> pmParam) throws Exception;
    
    /* ================================================================
     * 날짜 : 20220210
     * 이름 : 임동진
     * 추가내용 : 해약 상세 조회(NEW)
     * 대상 테이블 : LF_DMUSER.RESCISSION
     * ================================================================
     * */
    public Map<String, Object> getResnDetailInfo(Map<String, ?> pmParam) throws Exception;
    
    /* ================================================================
     * 날짜 : 20220210
     * 이름 : 임동진
     * 추가내용 : 해약 전자서명 조회(NEW)
     * 대상 테이블 : LF_DMUSER.RESCISSION
     * ================================================================
     * */
    public List<Map<String, Object>> getResnSignInfo(Map<String, ?> pmParam) throws Exception;
    
    /* ================================================================
     * 날짜 : 20220210
     * 이름 : 임동진
     * 추가내용 : 해약 신규 등록 (NEW)
     * 대상 테이블 : LF_DMUSER.RESCISSION
     * ================================================================
     * */
    public int insertResnInfo(Map<String, ?> pmParam) throws Exception;
    
    /* ================================================================
     * 날짜 : 20220210
     * 이름 : 임동진
     * 추가내용 : 해약 업데이트 등록 (NEW)
     * 대상 테이블 : LF_DMUSER.RESCISSION
     * ================================================================
     * */
    public int updateResnInfo(Map<String, ?> pmParam) throws Exception;    
    
    /* ================================================================
     * 날짜 : 20220210
     * 이름 : 임동진
     * 추가내용 : 레디캐시 실시간 조회
     * ================================================================
     * */
    public String getReadyCashRealAmt(Map<String, ?> pmParam) throws Exception;
    
    /* ================================================================
     * 날짜 : 20220210
     * 이름 : 임동진
     * 추가내용 : 해약등록 청구 조회(NEW)
     * 대상 테이블 : LF_DMUSER.RESCISSION
     * ================================================================
     * */
    public List<Map<String, Object>> getResnReqInfo(Map<String, ?> pmParam) throws Exception;
    
    /* ================================================================
     * 날짜 : 20220307
     * 이름 : 임동진
     * 추가내용 : 해약 환불 등록 (NEW)
     * 대상 테이블 : LF_DMUSER.RESCISSION
     * ================================================================
     * */
    public int insertResnRefund(Map<String, ?> pmParam) throws Exception;
    
    /* ================================================================
     * 날짜 : 20220210
     * 이름 : 임동진
     * 추가내용 : 해약 상담 로그 등록 
     * 대상 테이블 : LF_DMUSER.RESCISSION
     * ================================================================
     * */
    public int insertResnCounsel(Map<String, ?> pmParam) throws Exception;
    
    /* ================================================================
     * 날짜 : 20220209
     * 이름 : 임동진
     * 추가내용 : 해약조회(NEW)
     * 대상 테이블 : LF_DMUSER.RESCISSION
     * ================================================================
     * */
	public int getResnNewCount0(Map<String, ?> pmParam) throws Exception;
	
    /* ================================================================
     * 날짜 : 20220209
     * 이름 : 임동진
     * 추가내용 : 해약조회(NEW)
     * 대상 테이블 : LF_DMUSER.RESCISSION
     * ================================================================
     * */
    public List<Map<String, Object>> getResnNewList0(Map<String, ?> pmParam) throws Exception;
    
    /* ================================================================
     * 날짜 : 20220915
     * 이름 : 김주용
     * 추가내용 : 해약조회(전자서명)
     * 대상 테이블 : LF_DMUSER.RESCISSION
     * ================================================================
     * */
	public int getResnSignCount(Map<String, ?> pmParam) throws Exception;
	
    /* ================================================================
     * 날짜 : 20220915
     * 이름 : 김주용
     * 추가내용 : 해약조회(전자서명)
     * 대상 테이블 : LF_DMUSER.RESCISSION
     * ================================================================
     * */
    public List<Map<String, Object>> getResnSignList(Map<String, ?> pmParam) throws Exception;
}