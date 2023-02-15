package powerservice.business.dlw.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface DlwNewTypePayMngService {

	/** ================================================================
     * 날짜 : 20181022
     * 이름 : 송준빈
     * 추가내용 : 특정회원의 청구 여부 확인
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
     * ================================================================
     * */
    public int getWdrwReqAccntConfirm(String accnt_no);
    
    public void mergePayMng(Map<String, Object> pmParam) throws Exception;
    
    public void mergePayMngDtl(Map<String, Object> pmParam) throws Exception;

    public void mergePayMngDtl1(Map<String, Object> pmParam) throws Exception;
    
    public String getpayNewYnChk(Map<String, ?> pmParam) throws Exception;
    
    public List<Map<String, Object>> getBugaInfo(Map<String, ?> pmParam) throws Exception;
    
    public int getPayAmtByPayCnt(Map<String, ?> pmParam) throws Exception;
    
    public int getPayAmtDtlByPayCnt(Map<String, ?> pmParam) throws Exception;
    
    public int getPayAmtDtl1ByPayCnt(Map<String, ?> pmParam) throws Exception;
    
    public int getDlwCustAcntCount(Map<String, ?> pmParam) throws Exception;
    
    public List<Map<String, Object>> getDlwCustAcntList(Map<String, ?> pmParam) throws Exception;
    
    public List<Map<String, Object>> getPayMngBugaInfo(Map<String, ?> pmParam) throws Exception;
    
    public List<Map<String, Object>> getPayMngDtlBugaInfo(Map<String, ?> pmParam) throws Exception;
    
    public List<Map<String, Object>> getPayMngDtl1BugaInfo(Map<String, ?> pmParam) throws Exception;
    
    public List<Map<String, Object>> getPayMngList(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getPayMngDtlList(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getPayMngDtl1List(Map<String, ?> pmParam) throws Exception;
    
    public List<Map<String, Object>> getRefundList(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getRefundDtlList(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getRefundDtl1List(Map<String, ?> pmParam) throws Exception;
    
    public List<Map<String, Object>> getPayTotalList(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getProductDtl(Map<String, ?> pmParam) throws Exception;

    public String getProdCdByAccntNo(Map<String, ?> pmParam) throws Exception;
}