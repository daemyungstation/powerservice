package powerservice.business.biz.service;

import java.util.List;
import java.util.Map;

public interface BizBasiService {
	
	public int getBizBasiCount(Map<String, ?> pmParam) throws Exception;
	
	public String insertBizBasi(Map<String, ?> pmParam) throws Exception;

    public int updateBizBasi(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getBizBasiList(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> getBizBasi(String psId) throws Exception;
    
    public void mergeCncrBizDtl(Map<String, ?> pmParam) throws Exception;

	public int getCncrBizCount(Map<String, ?> pmParam) throws Exception;
	
	public List<Map<String, Object>> getCncrBizList(Map<String, ?> pmParam) throws Exception;
	
	public void deleteCncrBiz(Map<String, ?> pmParam) throws Exception;
	
	public int deleteBizBasi(Map<String, Object> pmParam) throws Exception;
	
	public int checkInfoCnt(Map<String, ?> pmParam) throws Exception;
}