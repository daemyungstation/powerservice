package powerservice.business.biz.service;

import java.util.List;
import java.util.Map;

public interface SlopIssHstrService {

     public int getSlopIssHstrCount(Map<String, ?> pmParam) throws Exception;
     public List<Map<String, Object>> getSlopIssHstrList(Map<String, ?> pmParam) throws Exception;
     public String insertSlopIssHstr(Map<String, ?> pmParam) throws Exception;
     public int deleteSlopIssHstr(Map<String, Object> pmParam) throws Exception;

}
