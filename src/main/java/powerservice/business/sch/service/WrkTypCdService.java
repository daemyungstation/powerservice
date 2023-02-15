package powerservice.business.sch.service;

import java.util.List;
import java.util.Map;

public interface WrkTypCdService {

    public int getWrkTypCdCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getWrkTypCdList(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> getWrkTypCd(Map<String, ?> pmParam);

    public String insertWrkTypCd(Map<String, ?> pmParam) throws Exception;

    public int updateWrkTypCd(Map<String, ?> pmParam) throws Exception;

    public int deleteWrkTypCd(Map<String, ?> pmParam) throws Exception;

}