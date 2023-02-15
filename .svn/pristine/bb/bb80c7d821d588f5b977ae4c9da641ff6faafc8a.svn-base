package powerservice.business.dlw.service;

import java.util.List;
import java.util.Map;

public interface DlwSdpService {
    public int getSdpCount(Map<String, ?> pmParam) throws Exception;

    public int getSdpManyCount(Map<String, ?> pmParam) throws Exception;

    public int getSdpPromoCount(Map<String, ?> pmParam) throws Exception;

    public int getSdpChargeCount(Map<String, ?> pmParam) throws Exception;

    public int saveChargeList(Map<String, Object> pmParam) throws Exception;

    public List<Map<String, Object>> getSdpList(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getSdpManyList(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getSdpPromoList(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getSdpChargeList(Map<String, ?> pmParam) throws Exception;

    public int selectSDPFileCount(Map<String, Object> hmParam) throws Exception;

    public void dtlFailFileSet(Map<String, Object> hmParam) throws Exception;

    public int insertSDPSendLog(Map<String, Object> hmParam) throws Exception;

    public int resultDtlInsert(Map<String, Object> hmParam) throws Exception;

    public List<Map<String, Object>> getSendFileList(Map<String, Object> hmParam) throws Exception;

    public int resultDtlUpdate(Map<String, Object> dRow) throws Exception;

    public int sendFileUpdate(Map<String, Object> hRow) throws Exception;

    public List<Map<String, Object>> getAllChargeDtList(String stt_dt) throws Exception;

    public int sendresult(Map<String, Object> hmParam) throws Exception;

    public int resultMstUpdate(Map<String, Object> hRow);

    public void dtlNoResultFileChange(Map<String, Object> hRow);
    
    public int updateInitSdpMesData(Map<String, Object> pmParam);
    
    public int insertSdpMesData(Map<String, Object> pmParam) throws Exception;

	public int getSdpMesDataListCount(Map<String, Object> pmParam);

	public List<Map<String, Object>> getSdpMesDataList(Map<String, Object> pmParam);
	
	public List<Map<String, Object>> getSdpMesDataSummary(Map<String, Object> pmParam);
	
	public int updateSdpMesDataList(Map<String, Object> pmParam);
	
	public int insertSdpMesDataMst(Map<String, Object> pmParam) throws Exception;
	
	public int insertSdpMesDataDtl(Map<String, Object> pmParam) throws Exception;

	public List<Map<String, Object>> getSendFileMesMstList(Map<String, Object> pmParam);
	
	public int insertSdpMesRecvLog(Map<String, Object> pmParam) throws Exception;
	
	public int sendSdpMesFileUpdate(Map<String, Object> pmParam) throws Exception;

	public List<Map<String, Object>> getAllSdpMesList(Map<String, Object> pmParam);
	
	public int resultSdpMesDtlUpdate(Map<String, Object> pmParam);
	
	public int resultSdpMesMstUpdate(Map<String, Object> pmParam);
	
	public int noResultSdpMesFileChange(Map<String, Object> pmParam);

	public int deleteSdpList(Map<String, Object> pmParam);
}