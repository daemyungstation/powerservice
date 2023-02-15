package powerservice.business.sample.service;

import java.util.List;
import java.util.Map;

public interface SampleService {

    public String insertSample(Map<String, Object> pmParam) throws Exception;

    public int updateSample(Map<String, Object> pmParam) throws Exception;

    public int deleteSample(Map<String, Object> pmParam) throws Exception;

    public int getSampleCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getSampleList(Map<String, ?> pmParam) throws Exception;
    
    /* 정출연 추가 */
    public List<Map<String, Object>> getListByProc(Map<String, Object> pmParam) throws Exception;
    
    public List<Map<String, Object>> getListByProc2(Map<String, Object> pmParam) throws Exception;
    
    public List<Map<String, Object>> getProductList(Map<String, Object> pmParam) throws Exception;

    public void insertTestPlSql(Map<String, Object> pmParam) throws Exception;
}