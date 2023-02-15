package powerservice.business.cns.service;

import java.util.List;
import java.util.Map;

public interface CustBasiService {

    public String insertCustBasi(Map<String, Object> pmParam) throws Exception;

    public int updateCustBasi(Map<String, Object> pmParam) throws Exception;

    public int getCustBasiCount(Map<String, ?> pmParam) throws Exception;

    public int getCompUseListCount(Map<String, ?> pmParam) throws Exception;

    //직권해지
    public int getResnAuthListCount(Map<String, ?> pmParam) throws Exception;

    //채권추심 리스트
    public int getResnCreditMainListCount(Map<String, ?> pmParam) throws Exception;

    //채권추심 입급현황 리스트
    public int getResnCreditpayMainListCount(Map<String, ?> pmParam) throws Exception;

    public int getResnTargetListCount(Map<String, ?> pmParam) throws Exception;

    //직권해지
    public List<Map<String, Object>> getResnTargetList(Map<String, ?> pmParam) throws Exception;

    //채권추심
    public List<Map<String, Object>> getResnCreditList(Map<String, ?> pmParam) throws Exception;


    public List<Map<String, Object>> getCustBasiList(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getCustBasiList2(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getCompUseList(Map<String, ?> pmParam) throws Exception;

    //직권해지 리스트
    public List<Map<String, Object>> getResnAuthList(Map<String, ?> pmParam) throws Exception;

    //채권추심 리스트
    public List<Map<String, Object>> getResnCreditMainList(Map<String, ?> pmParam) throws Exception;

    //채권추심 리스트
    public List<Map<String, Object>> getResnCreditpayMainList(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> getCustBasi(String psId) throws Exception;

    public String insertCustInqLog(Map<String, ?> pmParam) throws Exception;

    //직권해지 인서트
    public int insertTargetList(Map<String, ?> pmParam) throws Exception;

    //채권추심 인서트
    public int insertCreditList(Map<String, ?> pmParam) throws Exception;

    //채권 추심 대상자 삭제
    public int delResnCredit(Map<String, ?> pmParam) throws Exception;


    public int updateResnSenddt(Map<String, ?> pmParam) throws Exception;

    //해피콜 상태값 변경
    public int updateHpclAckd(Map<String, ?> pmParam) throws Exception;

    public void updateCustBasi_web(Map<String, Object> pmParam) throws Exception;

}