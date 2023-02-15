package powerservice.business.dlw.service;

import java.util.List;
import java.util.Map;

import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

public interface DlwMarktMngService {

	public List<Map<String, Object>> getList(Map<String, Object> pmParam) throws Exception;
    
    public int getListCount(Map<String, Object> pmParam);
    
    public List<Map<String, Object>> getMonCntPopList(Map<String, Object> pmParam) throws Exception;
    
    public List<Map<String, Object>> getMmsMngList(Map<String, Object> pmParam) throws Exception;
    
    public int insertMktSmsSend() throws Exception;
    
    public int insertSendMsgForm(Map<String, ?> pmParam) throws Exception;
    
    public int deleteSendMsgForm(Map<String, ?> pmParam) throws Exception;
}