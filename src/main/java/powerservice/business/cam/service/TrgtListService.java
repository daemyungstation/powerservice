package powerservice.business.cam.service;

import java.util.List;
import java.util.Map;

public interface TrgtListService {

    public int getTrgtListCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getTrgtListList(Map<String, ?> pmParam) throws Exception;

    public String insertTrgtList(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> getTrgtList(String psId) throws Exception;

    public int updateTrgtList(Map<String, Object> pmParam) throws Exception;

    public int deleteTrgtList(Map<String, Object> pmParam) throws Exception;

}