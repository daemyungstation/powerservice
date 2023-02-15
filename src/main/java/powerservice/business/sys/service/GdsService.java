package powerservice.business.sys.service;

import java.util.List;
import java.util.Map;

public interface GdsService {

    public String insertGds(Map<String, ?> pmParam) throws Exception;

    public int updateGds(Map<String, ?> pmParam) throws Exception;

    public int deleteGds(Map<String, ?> pmParam) throws Exception;

    public int getGdsCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getGdsList(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> getGds(Map<String, ?> pmParam) throws Exception;

}