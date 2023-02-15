package powerservice.business.dlw.service;

import java.util.List;
import java.util.Map;

public interface DlwConsProdService {

    public List<Map<String, Object>> getDlwConsProdList(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getDlwModlMstInfo(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getDlwModlDtlInfo(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getDlwProdKindList(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> getProdInfoInq(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> getProdInfoInqNew(Map<String, ?> pmParam) throws Exception;

    public int getNowMonthCount(String psAccntNo) throws Exception;

    public int getResnAmt(String psProdCd, String psAccntNo, int pCnt, String sJoinDt) throws Exception;

    public int getProdSrvcHistForMPAS(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getProdSrvcMstList(Map<String, ?> pmParam) throws Exception;

    public String validateDiscntPassNo(String psParam) throws Exception;

    public String selectNewChanGunsuDPM(String psParam) throws Exception;

    public Map<String, Object> getResortInfo(String psParam) throws Exception;

    public int getB2bCompCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getB2bCompList(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getPurCompList(Map<String, ?> pmParam) throws Exception;

    public int getSmShopCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getSmShopList(Map<String, ?> pmParam) throws Exception;

    public String getNoSaleAccnt(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> getNiceAuthStat(Map<String, ?> pmParam) throws Exception;

    public String getCardCode(String psParam) throws Exception;

    public String createAccntNo(String psParam) throws Exception;

    public int insertMemProdAccnt(Map<String, Object> pmParam) throws Exception;

    public int insertSmartLifeCnslMng(Map<String, ?> pmParam) throws Exception;

    public int insertMemProdAccntSvc(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> getSmartLifeCardInfo(String psParam) throws Exception;

    public int insertSearchHist(Map<String, ?> pmParam) throws Exception;

    public int updateMemProdAccnt(Map<String, Object> pmParam) throws Exception;

    public int deleteMemProdAccntSvc(String psParam) throws Exception;
    /*
    * 2017-09-20 김준호 ACCNT_NO 값을 가져오지 못하여 해당 부분 변경
    * insertReqUpdDelInf(Map<String, ? > pmParam) ->> insertReqUpdDelInf(Map<String, Object> pmParam) 수정
    */
    public int insertReqUpdDelInf(Map<String, Object> pmParam) throws Exception;

    public String getCmsWdrwReqChk(String psParam) throws Exception;

    public String getCardWdrwReqChk(String psParam) throws Exception;

    public String getPayChk(String psParam) throws Exception;

    public Map<String, Object> getOdrgInfo(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> getTotCondQury(Map<String, ?> pmParam) throws Exception;

    public int getHpCallCnt(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getHpCallList(Map<String, ?> pmParam) throws Exception;

    public void saveHpclBndeAckd(Map<String, Object> pmParam) throws Exception;

    public List<Map<String, Object>> getTotCondListLv(Map<String, Object> pmParam) throws Exception;

    public int getdeliveryCnt(Map<String, ?> pmParam) throws Exception;

    public int updatedelivery(Map<String, Object> pmParam) throws Exception;
    
    public int insertMemTwinAccnt(Map<String, Object> pmParam) throws Exception;
}