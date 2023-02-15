package powerservice.business.dlw.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

public interface DlwCmsService {

    public int getDlwCmsCsmmCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getDlwCmsCsmm(Map<String, ?> pmParam) throws Exception;

    public int getDlwCmsRgsnHstrCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getDlwCmsRgsnHstrList(Map<String, ?> pmParam) throws Exception;

    public int getDlwCmsWdrwHstrCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getDlwCmsWdrwHstrList(Map<String, ?> pmParam) throws Exception;

    public int updateCmsTranInfo(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getDlwCmsWdrwChk(Map<String, Object> pmParam) throws Exception;

    public int getDlwCmsAplcDtlCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getDlwCmsAplcDtl(Map<String, ?> pmParam) throws Exception;

    public String insertDlwCmsAnxtSrvc(Map<String, Object> pmParam) throws Exception;

    public int getDlwWdrwTranDtlCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getDlwWdrwTranDtlList(Map<String, ?> pmParam) throws Exception;

    public String getDlwWdrwReqDtTtamt(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getDlwCmsComnInfo(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getDlwComnInfo(Map<String, ?> pmParam) throws Exception;

    public boolean updateDlwInvGunsuCall(Map<String, Object> pmParam) throws Exception;

    public boolean updateDlwInvGunsu(Map<String, Object> pmParam) throws Exception;

//    public void updateCmsWdrwReq(Map<String, ?> pmParam) throws Exception;
//
//    public void updateCardWdrwReq(Map<String, ?> pmParam) throws Exception;

    public void updateCmsAndCardWdrwReq(Map<String, ?> mapInDs, Map<String, ?> mapInVar) throws Exception;

    public List<Map<String, Object>> getDlwCombErrList(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getDlwTempMember(Map<String, ?> pmParam) throws Exception;

    //카드오류회원_임동진
    public List<Map<String, Object>> getDlwErrorMember(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getDlwDelayMember(Map<String, ?> pmParam) throws Exception;

    public void insertDlwWdrwEb21Add(Map<String, Object> pmParam) throws Exception;

    public List<Map<String, Object>> getDlwWdrwAddData(Map<String, ?> pmParam) throws Exception;

    public int getInvAmt(Map<String, ?> pmParam) throws Exception;

    public int getDlwLastPayNo(Map<String, ?> pmParam) throws Exception;

    public void insertAddTempEb21(Map<String, Object> pmParam) throws Exception;

    public void insertAddTempCallEb21(Map<String, Object> pmParam) throws Exception;

    public List<Map<String, Object>> getDlwWdrwHstr(Map<String, ?> pmParam) throws Exception;

    public int updateAllWdrwCallCenterTemp(Map<String, Object> pmParam) throws Exception;

    public int deleteAllWdrwTemp(Map<String, Object> pmParam) throws Exception;

    public List<Map<String, Object>> getCmsMemberByIchaeDt(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getCmsMemberByimsiIchaeDt(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getCallDupWdrw(Map<String, ?> pmParam) throws Exception;

    public int getCallWdrwReqCheck(Map<String, Object> pmParam) throws Exception;

    public int updateCmsAppStateLrqnt(Map<String, Object> pmParam) throws Exception;

    public List<Map<String, Object>> getCmsResultFileNm(Map<String, Object> pmParam) throws Exception;

    public void getCmsAppIsTransEb22(Map<String, ?> pmParam) throws Exception;

    public void immsiinsert(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getReadFileCms(Map<String, ?> pmParam) throws Exception;

    public int getEB22WdrwCount(Map<String, ?> pmParam) throws Exception;

    public int CmsMemberByIchaeCount(Map<String, ?> pmParam) throws Exception;


    public List<Map<String, Object>> cmsResultProc(Map<String, Object> pmParam) throws Exception;

    public List<Map<String, Object>> getWdrwHstrByInvDt(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getWdrwHstrSttc(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getWdrwHstrStatsSumByInvDt(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getWdrwHstrStatsCombByInvDt(Map<String, ?> pmParam) throws Exception;

    public int updateWdrwApclCancCard(Map<String, ?> pmParam) throws Exception;

    public int deleteNiceCardLog(Map<String, ?> pmParam) throws Exception;

    public int updateWdrwApclCancCms(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> updateConvertFileEB11(Map<String, Object> pmParam) throws Exception;

    public List<Map<String, Object>> getCMSInfoByAccnt(Map<String, ?> pmParam) throws Exception;

    public String getNcaIdnNo(Map<String, ?> pmParam) throws Exception;

    public int updateWdrwReqYn1(Map<String, ?> pmParam) throws Exception;

    public int updateWdrwReqYn1all(Map<String, ?> pmParam) throws Exception;

    public int deleteWDRWHistory(Map<String, ?> pmParam) throws Exception;

    public int deleteWDRWHistoryall(Map<String, ?> pmParam) throws Exception;

    public int updateWdrwReqYn2(Map<String, ?> pmParam) throws Exception;



    public int deleteCardWdrw(Map<String, ?> pmParam) throws Exception;

    public String getDltnFlagCmsMmbr(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getAccntInfo(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getPaySoftInfo(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getPayMngBugaInfo(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getPayMngList(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getPayMngDtlList(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getPayMngDtl1List(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getPayMngDtlBugaInfo(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getPayMngDtl1BugaInfo(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getRefundList(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getRefundDtlList(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getRefundDtl1List(Map<String, ?> pmParam) throws Exception;

    public int getPayAmtByPayCnt(Map<String, ?> pmParam) throws Exception;

    public int getPayAmtDtlByPayCnt(Map<String, ?> pmParam) throws Exception;

    public int getPayAmtDtl1ByPayCnt(Map<String, ?> pmParam) throws Exception;

    public int getGasuMngCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getGasuMngList(Map<String, ?> pmParam) throws Exception;

    public int updateDlwCmsPymtMthd(Map<String, ?> pmParam) throws Exception;

  //  public int deleteWDRWCall(Map<String, ?> mapInDs) throws Exception;

    public List<Map<String, Object>> getPayTotalList(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getProductDtl(Map<String, ?> pmParam) throws Exception;

    public String getProdCdByAccntNo(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getGasuDetail(Map<String, ?> pmParam) throws Exception;

    public int updateGasuDtl(Map<String, ?> pmParam) throws Exception;

    public int deleteGasuDtl(Map<String, ?> pmParam) throws Exception;

    public int insertGasuDtl(Map<String, ?> pmParam) throws Exception;

    public int insertGasuMng(Map<String, ?> pmParam) throws Exception;

    public int updateGasuMng(Map<String, ?> pmParam) throws Exception;

    public int deleteGasuMng(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> updateDlwInvGunsuNew(Map<String, ?> mapInDs, Map<String, ?> mapInVar, String psPathTyp) throws Exception;

    public int insertEB22ErrorInfo(XPlatformMapDTO xpDto, Map hmOutParam) throws Exception;

    public int deleteWDRWCall(XPlatformMapDTO xpDto) throws Exception;

    public void eb22upload(HttpServletRequest request, HttpServletResponse response, Map<String, Object> mResuslt) throws Exception; // 녹취파일 개별 업로드

    public List<Map<String, Object>> getCallDupList(Map<String, ?> pmParam) throws Exception; // 대명라이프웨이 콜센터 산출 중복건 리스트

    public List<Map<String, Object>> getCmsMemberByIchaeDt_Basic(Map<String, ?> pmParam) throws Exception; // CMS 산출기초데이터작업(170523)

    public List<Map<String, Object>> saveWdrwimsiIchaeDt_Basic(Map<String, ?> pmParam) throws Exception; // 임시건 산출

    public List<Map<String, Object>> getBasicWdrw_Fix(Map<String, ?> pmParam) throws Exception; // 산출 기초데이터 확정

    public String getDlwWdrwReqDtTtamt_Basic(Map<String, ?> pmParam) throws Exception; // 산출금액 합계 기초데이터작업(170523)

    public int updateBasicAmt(Map<String, ?> pmParam) throws Exception; // CMS 기초데이터 수정

    public List<Map<String, Object>> getDlwWdrwHstr_Basic(Map<String, ?> pmParam) throws Exception; // CMS 산출데이터 조회

    public int updateWdrwReqYn_Basic(Map<String, ?> pmParam) throws Exception; // 산출내역삭제(고객만족센터 청구비트 변경)

    public int deleteCmsBasic(Map<String, ?> pmParam) throws Exception; // 산출내역삭제(CMS 기초테이블 삭제비트 변경)

    public int deleteCardBasic(Map<String, ?> pmParam) throws Exception; // 산출내역삭제(CARD 기초테이블 삭제비트 변경)

    public int deleteWDRWHistoryall_Basic(Map<String, ?> pmParam) throws Exception; // 산출내역일괄삭제(기초테이블 삭제비트 변경)

    public void updateCmsAndCardWdrwReq_Basic(Map<String, ?> mapInDs, Map<String, ?> mapInVar) throws Exception; // 콜센터 청구파일 적용(기초테이블로 입력)

    public List<Map<String, Object>> getdaRefundList(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getdaRefundDtlList(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getdaRefundDtl1List(Map<String, ?> pmParam) throws Exception;
    
    public List<Map<String, Object>> getAcquResultDataMst(Map<String, ?> pmParam);
    
    public int insertAcquResultDataMst(Map<String, ?> pmParam) throws Exception;
    
    public int insertAcquResultDataDtl(Map<String, ?> pmParam) throws Exception;

	public int getAcquResultDataListTotalCnt(Map<String, ?> pmParam);

	public List<Map<String, Object>> getAcquResultDataList(Map<String, ?> pmParam);

	public List<Map<String, Object>> getAcquResultDataListTotalSummary1(Map<String, ?> pmParam);
	
	public List<Map<String, Object>> getAcquResultDataListTotalSummary2(Map<String, ?> pmParam);
	
	public List<Map<String, Object>> getAcquResultDataListExcel(Map<String, ?> pmParam);
	
	public int getNotAuthResultDataCount(Map<String, ?> pmParam);

	public List<Map<String, Object>> getNotAuthResultDataList(Map<String, ?> pmParam);
	
    public List<Map<String, Object>> getNotAuthResultDataListTotalSummary1(Map<String, ?> pmParam);
    
    public List<Map<String, Object>> getNotAuthResultDataCancelList(Map<String, ?> pmParam);
	
    public List<Map<String, Object>> getNotAuthResultDataCancelListTotalSummary1(Map<String, ?> pmParam);
	
	public List<Map<String, Object>> getNotAuthResultDataListTotalSummary2(Map<String, ?> pmParam);
	
	public int getAcquInicisResultDataListTotalCnt(Map<String, ?> pmParam);

	public List<Map<String, Object>> getAcquInicisResultDataList(Map<String, ?> pmParam);

	public List<Map<String, Object>> getAcquInicisResultDataListTotalSummary1(Map<String, ?> pmParam);
	
	public int getLoanPurchResultDataTotalCnt(Map<String, ?> pmParam);

	public List<Map<String, Object>> getLoanPurchResultDataList(Map<String, ?> pmParam);
	
    public List<Map<String, Object>> getLoanPurchResultDataListTotalSummary(Map<String, ?> pmParam);
	
	public void loanPurchResultUpload(HttpServletRequest request, HttpServletResponse response, Map<String, Object> mResuslt) throws Exception;
	
	//public int updateLoanPurchResultAccntNoRealTID(Map<String, Object> pmParam) throws Exception;
	
	public List<Map<String, Object>> getMemberWdrwResultAuthList(Map<String, ?> pmParam);
	
	public List<Map<String, Object>> getMemberWdrwResultCancelList(Map<String, ?> pmParam);
	
	public int updateLoanPurchMemberWdrwResultAuth(Map<String, Object> pmParam) throws Exception;
	
	public int updateLoanPurchMemberWdrwResultCancel(Map<String, Object> pmParam) throws Exception;
	
	public List<Map<String, Object>> getDlwCmsEb11List(Map<String, ?> pmParam) throws Exception;
	
	public String insertDlwCmsDataInsert(Map<String, Object> pmParam) throws Exception; 
	
	public String insertDlwCmsHistoryInsert(Map<String, Object> pmParam) throws Exception; 
	
	public int updateDlwCmsInfo(Map<String, ?> pmParam) throws Exception;
}