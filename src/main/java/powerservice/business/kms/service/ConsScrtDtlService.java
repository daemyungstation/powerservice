package powerservice.business.kms.service;

import java.util.List;
import java.util.Map;

public interface ConsScrtDtlService {

    public int getConsScrtCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getConsScrtList(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> getConsScrt(Map<String, ?> pmParam) throws Exception;

    public String insertConsScrt(Map<String, ?> pmParam) throws Exception;

    public int updateConsScrt(Map<String, ?> pmParam) throws Exception;

    public int deleteConsScrt(Map<String, ?> pmParam) throws Exception;

}