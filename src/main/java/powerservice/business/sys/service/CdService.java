package powerservice.business.sys.service;

import java.util.List;
import java.util.Map;

public interface CdService {

	public String insertComCd(Map<String, ?> pmParam) throws Exception;

    public String insertCd(Map<String, ?> pmParam) throws Exception;

    public int updateComCd(Map<String, ?> pmParam) throws Exception;

    public int updateCd(Map<String, ?> pmParam) throws Exception;

    public int getComCdCount(Map<String, ?> pmParam) throws Exception;

    public int getCdCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getCdList(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> getCd(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getCdList(String[] sCdSetList) throws Exception;

    public int getCdMaxSequence(Map<String, ?> pmParam) throws Exception;

    public int getCdDuplicateCount(Map<String, ?> pmParam) throws Exception;

}