package powerservice.business.dlw.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

public interface DlwEvntReceiptService {

    public int getEventReceiptCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getEventReceiptList(Map<String, ?> pmParam) throws Exception;
    
    public int insertEventReceipt(Map<String, ?> pmParam) throws Exception;

    public int updateEventReceipt(Map<String, ?> pmParam) throws Exception;
    
    public int deleteEventReceipt(Map<String, ?> pmParam) throws Exception;
    
    public int insertEventReceiptComplete(Map<String, ?> pmParam) throws Exception;
    
    /*************************************************************************
     *  2017.07.27 김준호
     *  관리업무>행사조회>모니터링>모니터링 결과 보고서
     *  중복 입력시 오류건으로 인해 저장전 회원번호로 EVENT TABLE확인후 저장
     ***********************************************************************/
    public int countEventReceipt(Map<String, Object> hmParam) throws Exception;// 행사 등록시 해당 회원번호로 행사테이블 조회
    
    public int insertevntMngr(Map<String, ?> pmParam) throws Exception; // 행사자 등록
    
    public List<Map<String, Object>> getEventSeq(Map<String, ?> pmParam) throws Exception;
    
    public int getDlwCustAcntCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getDlwCustAcntList(Map<String, ?> pmParam) throws Exception;
    
    public int sndPushAlertMain(Map<String, ?> pmParam) throws Exception; // 행사자 등록
    
    public List<Map<String, Object>> getEventCancelList(Map<String, ?> pmParam) throws Exception;
    
    public int insertEventCancel(Map<String, ?> pmParam) throws Exception;
    
    public List<Map<String, Object>> getComCd(Map<String, ?> pmParam) throws Exception;
    
    public List<Map<String, Object>> getManagerList(Map<String, ?> pmParam) throws Exception;
    
    public List<Map<String, Object>> eventReceiptComplete(Map<String, ?> pmParam) throws Exception;
    
    public List<Map<String, Object>> getEventCallList(Map<String, ?> pmParam) throws Exception;
    
    
    
    
    
    
    
    
    
    
    
    public int getEventMainCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getEventMainList(Map<String, ?> pmParam) throws Exception;
    
    public List<Map<String, Object>> getEventDetailList(Map<String, ?> pmParam) throws Exception;
    
    public List<Map<String, Object>> getSuppliesClsfcDtlList(Map<String, ?> pmParam) throws Exception;
    
    public List<Map<String, Object>> getAddSales1List(Map<String, ?> pmParam) throws Exception;
    
    public List<Map<String, Object>> getAddSales2List(Map<String, ?> pmParam) throws Exception;
    
    public List<Map<String, Object>> getPaymentInfoList(Map<String, ?> pmParam) throws Exception;
    
    public int updateEventBasicInfo(Map<String, ?> pmParam) throws Exception;
    
    public int insertEventBasicInfo(Map<String, ?> pmParam) throws Exception;
    
    public int updateCancelEvent(Map<String, ?> pmParam) throws Exception;
    
    public int updateSuppliesClsfcDtl(Map<String, ?> pmParam) throws Exception;
    
    public int insertSuppliesClsfcDtl(Map<String, ?> pmParam) throws Exception;
    
    public int deleteSuppliesClsfcDtl(Map<String, ?> pmParam) throws Exception;
    
    public int updateAddSales1(Map<String, ?> pmParam) throws Exception;
    
    public int insertAddSales1(Map<String, ?> pmParam) throws Exception;
    
    public int deleteAddSales1(Map<String, ?> pmParam) throws Exception;
    
    public int updateAddSales2(Map<String, ?> pmParam) throws Exception;
    
    public int insertAddSales2(Map<String, ?> pmParam) throws Exception;
    
    public int deleteAddSales2(Map<String, ?> pmParam) throws Exception;
    
    public int updatePaymentInfo(Map<String, ?> pmParam) throws Exception;
    
    public int insertPaymentInfo(Map<String, ?> pmParam) throws Exception;
    
    public int deletePaymentInfo(Map<String, ?> pmParam) throws Exception;
    
    public int confirmRegFunrlOutsrcMst(Map<String, ?> pmParam) throws Exception;
    
    public int insertRegFunrlOutsrcMst(Map<String, ?> pmParam) throws Exception;
    
    public List<Map<String, Object>> getEventMngrList(Map<String, ?> pmParam) throws Exception;
    
    public List<Map<String, Object>> getMngrPayList(Map<String, ?> pmParam) throws Exception;
    
    public List<Map<String, Object>> getMemConsList(Map<String, ?> pmParam) throws Exception;
    
    public List<Map<String, Object>> getProtocolMngrHistList(Map<String, ?> pmParam) throws Exception;
    
    public int insertMemConsList(Map<String, ?> pmParam) throws Exception;
    
    public List<Map<String, Object>> getMemberAccntDtlList(Map<String, ?> pmParam) throws Exception;
    
    public List<Map<String, Object>> getPayTotList(Map<String, ?> pmParam) throws Exception;
    
    public int updateEventMngrInfo(Map<String, ?> pmParam) throws Exception;
    
    public int insertEventMngrInfo(Map<String, ?> pmParam) throws Exception;
    
    public int deleteEventMngrInfo(Map<String, ?> pmParam) throws Exception;
    
    public int getCustAccntCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getCustAccntList(Map<String, ?> pmParam) throws Exception;
    
    public int getProtocolSynthesisCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getProtocolSynthesisList(Map<String, ?> pmParam) throws Exception;

	public List<Map<String, Object>> getBranchList(Map<String, ?> pmParam);
}