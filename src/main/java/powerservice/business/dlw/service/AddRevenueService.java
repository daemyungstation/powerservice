package powerservice.business.dlw.service;

import java.util.List;
import java.util.Map;


public interface AddRevenueService {

	public List<Map<String, Object>> selectAddSalesCondList(Map<String, Object> pmParam) throws Exception;
	
	public List<Map<String, Object>> selectAddSalesOutCondList(Map<String, Object> pmParam) throws Exception;
	
	
    /*
	public int sss(XPlatformMapDTO xpDto) throws Exception;
    
    public List<Map<String, Object>> sss(Map<String, Object> pmParam) throws Exception;
    
    public List<Map<String, Object>> sss(Map<String, Object> pmParam) throws Exception;
     */
    
}