package powerservice.business.sys.service;

import java.util.List;
import java.util.Map;

public interface CdSetService {

    public String insertCdSet(Map<String, ?> pmParam) throws Exception;

    public String insertComCdSet(Map<String, ?> pmParam) throws Exception;

    public int updateComCdSet(Map<String, ?> pmParam) throws Exception;

    public int updateCdSet(Map<String, ?> pmParam) throws Exception;

    public int getComCdSetCount(Map<String, ?> pmParam) throws Exception;

    public int getCdSetCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getCdSetList(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> getCdSet(Map<String, ?> pmParam) throws Exception;

}