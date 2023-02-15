package powerservice.business.dlw.service;

import java.util.List;
import java.util.Map;

import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

public interface DlwNewTypeResnService {

    public int getNewTypeEventChk(Map<String, ?> pmParam) throws Exception;

    public int getNewTypeResnChk(Map<String, ?> pmParam) throws Exception;

    public int getNewTypeTaxtChk(Map<String, ?> pmParam) throws Exception;

    public int getNewTypeCmsChk(Map<String, ?> pmParam) throws Exception;

    public int getNewTypecallcenterChk(Map<String, ?> pmParam) throws Exception;

    public int getNewTypeCmsReqCnt(Map<String, ?> pmParam) throws Exception;

    public String getNewTypeSelectProdCl(Map<String, ?> pmParam) throws Exception;

    public String getNewTypeSelectJoinType(Map<String, ?> pmParam) throws Exception;

    public int getNewTypeResnGubn(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> getNewTypeCmsInfo(Map<String, Object> pmParam) throws Exception;

    public int getNewTypeResnCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getNewTypeResnList(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> getNewTypeResnDtpt(Map<String, ?> pmParam) throws Exception;

    public String getNewTypeEmartIPoint(Map<String, ?> pmParam) throws Exception;

    public String getNewTypeResnGasuAmt(Map<String, ?> pmParam) throws Exception;

    public int updateNewTypeResnSenddt(Map<String, ?> pmParam) throws Exception;

    public int updateNewTypeResnProc(Map<String, ?> pmParam) throws Exception;

    public int insertNewTypeResn(Map<String, ?> pmParam) throws Exception;

    public int updateNewTypeResn(Map<String, ?> pmParam) throws Exception;

    public String resnNewTypeDel(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getNewTypeResnStat1List(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getNewTypeResnStat2List(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getNewTypeResnStat3List(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getNewTypeBuyChulList(Map<String, ?> pmParam) throws Exception; // 매입현황 - 매출입현황

    public List<Map<String, Object>> getNewTypeAccPurList(Map<String, ?> pmParam) throws Exception; // 매입현황 - 매출입현황 합계

    public List<Map<String, Object>> getNewTypeTmpMemPurList(Map<String, ?> pmParam) throws Exception; // (중간마감) 회원별매출입
    public List<Map<String, Object>> getNewTypeMemPurMgList(Map<String, ?> pmParam) throws Exception;  // (최종마감) 회원별매출입
    public List<Map<String, Object>> getNewTypeAccPurMgList(Map<String, ?> pmParam) throws Exception;  // (최종마감) 매출입현황

    public int getNewTypeCntMemPurList(Map<String, ?> pmParam) throws Exception;       // 회원별매출입 기본데이터 카운트 조회
    public int srchNewTypeCntAccntPurSale(Map<String, ?> pmParam) throws Exception;    // 회원별매출입 중간마감 카운트 조회
    public int srchNewTypeCntAccntPurSale2(Map<String, ?> pmParam) throws Exception;   // 회원별매출입 중간마감 카운트 조회 (** 조건 제외)
    public int srchNewTypeCntAccntPurSaleMg(Map<String, ?> pmParam) throws Exception;  // 회원별매출입 최종마감 카운트 조회
    public String srchNewTypeMgDtAccntPurSaleMg(Map<String, ?> pmParam) throws Exception;  // 회원별매출입 최종마감일 조회

    public int saveNewTypeAccntPurSale(Map<String, ?> pmParam) throws Exception;   // 회원별 상품모델 매입매출 저장
    public int updateNewTypeAccntPurSale(Map<String, ?> pmParam) throws Exception; // 회원별 상품모델 매입매출 수정
    public int deleteNewTypeAccntPurSale(Map<String, ?> pmParam) throws Exception; // 회원별 상품모델 매입매출 삭제

    public int saveNewTypeAccntPurSaleMg(Map<String, ?> pmParam) throws Exception; // 회원별 상품모델 매입매출 최종마감처리

    public int getNewTypeMemPurCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getNewTypeMemPurList(Map<String, ?> pmParam) throws Exception; // 매입현황 - 매출입 회원 조회

    public List<Map<String, Object>> getNewTypeBuyDtlList(Map<String, ?> pmParam) throws Exception; // 매입현황 - 매입현황상세조회

    public List<Map<String, Object>> selectNewTypeResnAmtAccnt(Map<String, Object> pmParam) throws Exception;

    public long selectNewTypeResnAmtSum(Map<String, Object> pmParam) throws Exception;

    public int callNewTypeUPResnAmt(XPlatformMapDTO xpDto) throws Exception;

    public int deleteNewTypeResnAmt(XPlatformMapDTO xpDto) throws Exception;

    /* ================================================================
     * 날짜 : 20171226
     * 이름 : 송준빈
     * 추가내용 : 쇼핑몰 연동 Table 에 고객의 상태를 해약접수, 해약완료, 유효 로 변경
     * ================================================================
     * */
    public int updateNewTypeResnMemberState(Map<String, ?> pmParam) throws Exception;
    
    /* ================================================================
     * 날짜 : 20181114
     * 이름 : 송준빈
     * 추가내용 : 청구가 존재하는 회원인지 확인
     * ================================================================
     * */
	public List<Map<String, Object>> getNewTypeWdrwReqConfirm(Map pmParam); 
	
	/* ================================================================
     * 날짜 : 20190710
     * 이름 : 송준빈
     * 추가내용 : 해약등록 엔터 입력시 해당 은행코드 리턴
     * 대상 테이블 : LF_DMUSER.COM_CD
     * ================================================================
     * */
	public List<Map<String, Object>> getNewTypeCodeBank(String sCdNm);
	
	
	/* ================================================================
     * 날짜 : 20200221
     * 이름 : 김주용
     * 추가내용 : 해약조회(NEW)
     * 대상 테이블 : LF_DMUSER.RESCISSION
     * ================================================================
     * */
	public int getNewTypeResnNewCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getNewTypeResnNewList(Map<String, ?> pmParam) throws Exception;
    
    /* ================================================================
     * 날짜 : 20200221
     * 이름 : 김주용
     * 추가내용 : 해약조회(NEW)
     * 대상 테이블 : LF_DMUSER.RESCISSION
     * ================================================================
     * */
	public int getNewTypeResnNewCount2(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getNewTypeResnNewList2(Map<String, ?> pmParam) throws Exception;
    
    /* ================================================================
     * 날짜 : 20201126
     * 이름 : 김주용
     * 추가내용 : 회원 만기일자 조회
     * 대상 테이블 : LF_DMUSER.MEM_PROD_ACCNT, LF_DMUSER.PRODUCT
     * ================================================================
     * */
    public String getNewTypeMangiDate(Map<String, ?> pmParam) throws Exception;
}