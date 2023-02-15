package powerservice.business.pds.service;

import java.util.List;
import java.util.Map;

import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;

public interface pdsService {

	public List<Map<String, Object>> testConn02();

	public List<Map<String, Object>> findData(Map<String, ?> hmParam) throws Exception;

	public Map<String, Object> insertDF(Map<String, ?> srchInDs);

	public Map<String, Object> insertCD(Map<String, ?> srchInDs);

	public Map<String, Object> insertHC(Map<String, ?> srchInDs);

	public void checkDelete(Map map);

	public List<Map<String, Object>> findResultData(Map<String, Object> findDataInfo) throws Exception;

	public int getAllCount();
	
	public void updateStatus(Map<String, Object> map);

	public int updateDataDF(Map<String, Object> map);
	
	public int updateDataCD(Map<String, Object> map);
	
	public int updateDataHC(Map<String, Object> map);
	
	public int updateAlct(Map<String, Object> map);
	
	
	
	
	
	
	
	
	
}

































