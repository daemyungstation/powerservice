package powerservice.business.biz.service;

import java.util.List;
import java.util.Map;

public interface SlopActvRprgService {

     public int getSlopActvRprgCount(Map<String, ?> pmParam) throws Exception;
     public List<Map<String, Object>> getSlopActvRprgList(Map<String, ?> pmParam) throws Exception;
     public String insertSlopActvRprg(Map<String, ?> pmParam) throws Exception;
     public int updateSlopActvRprg(Map<String, ?> pmParam) throws Exception;
     public Map<String, Object> getSlopActvRprg(String psId) throws Exception;
     public int deleteSlopActvRprg(Map<String, Object> pmParam) throws Exception;
}
