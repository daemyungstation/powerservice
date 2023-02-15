package powerservice.business.dlw.service;

import java.util.List;
import java.util.Map;

public interface DlwNewTypeMainConsService {
	
	public Map<String, Object> getDlwCustMemList(Map<String, ?> pmParam) throws Exception;
	
	public Map<String, Object> getPersInfoPcusCnsn(String psId) throws Exception;
	
	public Map<String, Object> getPersInfoPcusSrctDtpt() throws Exception;
	
	public String insertCustInqLog(Map<String, ?> pmParam) throws Exception;
	
	public int getCustUnusMemoCnt(Map<String, ?> pmParam) throws Exception;
	
	public int getOcbTransHistCnt() throws Exception;
	
	public int updateMemberOCB(Map<String, ?> pmParam) throws Exception;
	
	public String insertCustMemSave(Map<String, Object> pmParam) throws Exception;
	
	public int updateCustMemSave(Map<String, Object> pmParam) throws Exception;
	
	public int updateAddrChageData(Map<String, Object> pmParam) throws Exception;
	
	public void insertMemLogSave(Map<String, Object> pmParam) throws Exception;
	
	/*
    * 2017-09-20 김준호 ACCNT_NO 값을 가져오지 못하여 해당 부분 변경
    * insertReqUpdDelInf(Map<String, ? > pmParam) ->> insertReqUpdDelInf(Map<String, Object> pmParam) 수정
    */
    public int insertReqUpdDelInf(Map<String, Object> pmParam) throws Exception;
    
    public void updateCustBasi_web(Map<String, Object> pmParam) throws Exception;
    
    public Map<String, Object> getDlwCustPrdtDtpt(String psParam) throws Exception;
    
    public int getIbDpmsReslHstrCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getIbDpmsReslHstrList(Map<String, ?> pmParam) throws Exception;
    
    public int getVocDtlCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getVocDtlList(Map<String, ?> pmParam) throws Exception;
    
    public int getChatSndgHstrCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getChatSndgHstrList(Map<String, ?> param) throws Exception;
    
    public List<Map<String, Object>> getDlwConsProdList(Map<String, ?> pmParam) throws Exception;
    
    public List<Map<String, Object>> getDlwConsProdListDtl(Map<String, ?> pmParam) throws Exception;
    
    public Map<String, Object> getProdInfoInqNew(Map<String, ?> pmParam) throws Exception;
    
    public int getNowMonthCount(String psAccntNo) throws Exception;

    public int getResnAmt(String psProdCd, String psAccntNo, int pCnt, String sJoinDt) throws Exception;
    
    public int getResnGubn(Map<String, ?> pmParam) throws Exception;
    
    public Map<String, Object> getNiceAuthStat(Map<String, ?> pmParam) throws Exception;
    
    public int getProdSrvcHistForMPAS(Map<String, ?> pmParam) throws Exception;
    
    public List<Map<String, Object>> getProdSrvcMstList(Map<String, ?> pmParam) throws Exception;
    
    public String validateDiscntPassNo(String psParam) throws Exception;

    public String selectNewChanGunsuDPM(String psParam) throws Exception;
    
    public List<Map<String, Object>> getDlwModlMstInfo(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getDlwModlDtlInfo(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getDlwProdKindList(Map<String, ?> pmParam) throws Exception;
    
    public Map<String, Object> getResortInfo(String psParam) throws Exception;
    
    public Map<String, Object> getAccntCheck(String psParam) throws Exception;
    
    public String getNoSaleAccnt(Map<String, ?> pmParam) throws Exception;
    
    public String getCardCode(String psParam) throws Exception;
    
    public String createAccntNo(String psParam) throws Exception;
    
    public int insertMemProdAccnt(Map<String, Object> pmParam) throws Exception;
    
    public int insertSmartLifeCnslMng(Map<String, ?> pmParam) throws Exception;
    
    public int insertMemProdAccntSvc(Map<String, ?> pmParam) throws Exception;
    
    public int insertMemTwinAccnt(Map<String, Object> pmParam) throws Exception;
    
    public int updateMemProdAccnt(Map<String, Object> pmParam) throws Exception;

    public int deleteMemProdAccntSvc(String psParam) throws Exception;
    
    public int getdeliveryCnt(Map<String, ?> pmParam) throws Exception;
    
    public int updatedelivery(Map<String, Object> pmParam) throws Exception;
    
    public String mergeRecProdDtl(Map<String, Object> pmParam) throws Exception;
    
    public String getCmsWdrwReqChk(String psParam) throws Exception;

    public String getCardWdrwReqChk(String psParam) throws Exception;

    public String getPayChk(String psParam) throws Exception;
    
    public List<Map<String, Object>> getBugaInfo(Map<String, ?> pmParam) throws Exception;
    
    public Map<String, Object> getDlwEmplDtpt(String psParam) throws Exception;
    
    public Map<String, Object> getBugaSrvcMemChk(Map<String, ?> pmParam) throws Exception;
    
    public List<Map<String, Object>> getDlwProductList(Map<String, ?> pmParam) throws Exception;
    
    /* ================================================================
     * 날짜 : 20180809
     * 이름 : 송준빈
     * 추가내용 : 최근 애큐온 인증 여부 Y/N
     * 대상 테이블 : TB_MEMBER_NICE_INFO
     * ================================================================
     * */
    public List<Map<String, Object>> getAcuonLatelyAuth(Map<String, Object> pmParam);
    
    public void updateCallCenterVatSvcHist(Map<String, Object> pmParam) throws Exception; //상담업무 수정시 부가서비스 업무 수정 및 로직체크
    
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
     * 추가내용 : NICE 전자서명 대상 고객 조회
     * 대상 테이블 : TB_MEMBER_NICE_INFO
     * ================================================================
     * */
    public List<Map<String, Object>> getMemberNiceRetrieve2(Map<String, Object> pmParam);
    
    /** ================================================================
     * 날짜 : 20181022
     * 이름 : 송준빈
     * 추가내용 : 특정회원의 청구 여부 확인
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
     * ================================================================
     * */
    public int getWdrwReqAccntConfirm(String accnt_no);
    
    /** ================================================================
     * 날짜 : 20181022
     * 이름 : 송준빈
     * 추가내용 : 출금이체의뢰내역(산출, 청구(행사, 해약)) 조회
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
     * ================================================================
     * */
    public List<Map<String, Object>> getWdrwReqAccntEventConfirm(String accnt_no) throws Exception;
    
    /** ================================================================
     * 날짜 : 20190103
     * 이름 : 송준빈
     * 추가내용 : 만기연장 관리회원 현황 리스트
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_MANGI_EXT
     * ================================================================
     * */
    public List<Map<String, Object>> getMemberMangiExtConfirmList(Map<String, Object> pmParam) throws Exception;
    
    public int getDlwCardCsmmCount(Map<String, ?> pmParam) throws Exception;
    
    public List<Map<String, Object>> getDlwCardCsmm(Map<String, ?> pmParam) throws Exception;
    
    public int getDlwCmsCsmmCount(Map<String, ?> pmParam) throws Exception;
    
    public List<Map<String, Object>> getDlwCmsCsmm(Map<String, ?> pmParam) throws Exception;
    
    public Map<String, Object> getOdrgInfo(Map<String, ?> pmParam) throws Exception;
    
    public List<Map<String, Object>> getDlvList(Map<String, ?> pmParam) throws Exception;
    
    public List<Map<String, Object>> getDlwPayMngList(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getDlwPayMngDtlList(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getDlwPayMngDtl1List(Map<String, ?> pmParam) throws Exception;
    
    public List<Map<String, Object>> getDlwPayMngAllList(Map<String, ?> pmParam) throws Exception;
    
    public Map<String, Object> getDlwResnAmtList(Map<String, ?> pmParam) throws Exception;
    
    public List<Map<String, Object>> getResnAmtDetailList(Map<String, ?> pmParam) throws Exception;
    
    public List<Map<String, Object>> getDlwPymnHstrList(Map<String, ?> pmParam) throws Exception;
    
    public List<Map<String, Object>> getDlwAskHstrList(Map<String, ?> pmParam) throws Exception;
    
    /** ================================================================
     * 날짜 : 20181113
     * 이름 : 송준빈
     * 추가내용 : 회원고객정보 탭 (청구이력)
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ, LF_DMUSER.TB_MEMBER_WDRW_RESULT
     * ================================================================
     * */
    public List<Map<String, Object>> getMainWdrwLogList(Map<String, Object> pmParam);
    
    public String insertCons(Map<String, Object> pmParam) throws Exception;
    
    public List<Map<String, Object>> getConsGroup(String psConsnoGropNo) throws Exception;
    
    public int getConsCount(Map<String, ?> pmParam) throws Exception;
    
    public int getDlwConsCount(Map<String, ?> pmParam) throws Exception;
    
    public List<Map<String, Object>> getDlwConsList(Map<String, ?> pmParam) throws Exception;
    
    public int getDlwlifeweyCount(Map<String, ?> pmParam) throws Exception;
    
    public List<Map<String, Object>> getDlwlifeweyList(Map<String, ?> pmParam) throws Exception;
    
    public String insertSmsSend(Map<String, Object> pmParam) throws Exception;
    
    public String insertChatSndgHstr(Map<String, Object> pmParam) throws Exception;
    
    public Map<String, Object> getDlwMarketingInfoList(Map<String, ?> pmParam) throws Exception;
    
    public String insertMktInfo(Map<String, Object> pmParam) throws Exception;
    
    public String updateMktInfo(Map<String, Object> pmParam) throws Exception;
    
    public  List<Map<String, Object>>  getDlwMarketinghstrList(Map<String, ?> pmParam) throws Exception;
    
    public Map<String, Object> getDlwMarketGroup(Map<String, ?> pmParam) throws Exception;
    
    public String mergeMktInfo(Map<String, Object> pmParam) throws Exception;
    
    public String updateMktInfoRe(Map<String, Object> pmParam) throws Exception;
}


