package powerservice.business.dlw.service;

import java.util.List;
import java.util.Map;

import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

public interface DlwDoubleService {

    public List<Map<String, Object>> selectDoubleList(Map<String, Object> pmParam) throws Exception; //다구좌 조회
    
    public List<Map<String, Object>> selectDoubleSrchList(Map<String, Object> pmParam) throws Exception; //다구좌 조회
    
    public int saveDouble(Map<String, ?> pmParam) throws Exception;
    
    public List<Map<String, Object>> getDlwDoubleKey(Map<String, Object> pmParam) throws Exception; //다구좌 조회
    
    public List<Map<String, Object>> getAccntNoDbl(Map<String, Object> pmParam) throws Exception; //다구좌 조회
    
    public int getAccntNoDblCount(Map<String, Object> pmParam);
    
    public List<Map<String, Object>> getDlwPayInfo(Map<String, Object> pmParam) throws Exception; //다구좌 조회
    
    public void updateCardAccnt(Map<String, ?> pmParam) throws Exception;
    
    public void updateCmsAccnt(Map<String, ?> pmParam) throws Exception;
    
    public List<Map<String, Object>> getDlwDoubleTripleChk(Map<String, Object> pmParam) throws Exception; //다구좌 조회
    
    public int deleteDouble(Map<String, ?> pmParam) throws Exception;
}