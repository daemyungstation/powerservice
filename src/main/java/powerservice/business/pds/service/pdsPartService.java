package powerservice.business.pds.service;

import java.util.List;
import java.util.Map;

public interface pdsPartService {

	List<Map<String, Object>> testConn();

	void connPds(Map map);

	List<Map<String, Object>> selectDataDF();
	
	List<Map<String, Object>> selectDataCD();
	
	List<Map<String, Object>> selectDataHC();
	
	List<Map<String, Object>> findResultRealData(Map<String, Object> pmParam);
	
	List<Map<String, Object>> chkConnection();
	
	
	
	
	
	
}









































