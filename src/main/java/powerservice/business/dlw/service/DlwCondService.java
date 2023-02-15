package powerservice.business.dlw.service;

import java.util.List;
import java.util.Map;

public interface DlwCondService {
    public List<Map<String, Object>> getClList(Map<String, ?> pmParam) throws Exception;

    public String getInqCondQry(Map<String, ?> pmParam) throws Exception;

    public int getCondMemCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getCondMemList(Map<String, ?> pmParam) throws Exception;

    public int getPayCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getPayList(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getCertfCond(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getDelayPayByProd(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getRealPay(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getDelayPayByBond(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getAcrsProd(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getAcrsProdByBatch(Map<String, ?> pmParam) throws Exception;

    public void insertUserProdInfoMonth() throws Exception;

    public void insertUserProdInfo() throws Exception;

    public List<Map<String, Object>> getBasMonth(Map<String, ?> pmParam) throws Exception;

    public int getPayMonthCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getPayMonthList(Map<String, ?> pmParam) throws Exception;

    public int getPayMonthByMemCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getPayMonthByMemList(Map<String, ?> pmParam) throws Exception;

    public int insertMbidCsvFileMake() throws Exception;

    public int sendMessageBatchAuto() throws Exception;

    public int insertMemberYencheHstr() throws Exception;

    public int insertAlowBasicInfo() throws Exception;

    public int getCloseDataCount(Map<String, ?> pmParam) throws Exception;

    public int insertCloseDataComm(Map<String, ?> pmParam) throws Exception;

    public int insertCloseDataAlow(Map<String, ?> pmParam) throws Exception;

	public List<Map<String, Object>> getAlowCalcMgmtExtList(Map<String, Object> pmParam) throws Exception;
	
	public int insertAlowCalcMgmtExtList(Map<String, Object> pmParam) throws Exception;
}