package powerservice.business.web.service;

import java.util.List;
import java.util.Map;

public interface WebConsService {

    public List<Map<String, Object>> getWebConsList(Map<String, ?> pmParam) throws Exception;

    public int getWebConsCount(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> getWebConsTrgt(Map<String, Object> pmParam) throws Exception;

    public int updateWebConsChpr(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> getDspsPsct(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> getCons(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getWebConsChartWeeksList(Map<String, ?> pmParam) throws Exception;

}