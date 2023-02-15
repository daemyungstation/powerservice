package powerservice.business.sys.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface NiceCreditService {

    public Map<String, Object> getNiceSafeKey(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> getNiceSafeSMSInfo(Map<String, ?> pmParam) throws Exception; // NICE Safekey SMS 내역조회

    public Map<String, Object> getNiceCredit(Map<String, ?> pmParam) throws Exception;
    
    public Map<String, Object> getNiceCredit600(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> updateSafeKeySmsSendResult(HttpServletRequest request, HttpSession sess) throws Exception;

    public void updateNiceSafekeyMobileIssueResult(HttpServletRequest request, boolean isSuccess) throws Exception;

    public Map<String, Object> sendSafeKeyIssueSms(HttpServletRequest request) throws Exception;

    public List<Map<String, Object>> getMoNiceSafeKeySmsList(Map<String, Object> pmParam) throws Exception;

    public int getMoNiceSafeKeySmsCount(Map<String, ?> pmParam) throws Exception;

    public int updateSafeKeyIssueStat(Map<String, Object> pmParam) throws Exception;

    public void insertJcyTest1() throws Exception;

    public int updateMemberNiceSafekey(Map<String, Object> pmParam) throws Exception;

    public List<Map<String, Object>> getSmsSendDt(Map<String, ?> pmParam) throws Exception;
}