package powerservice.business.kms.service;

import java.util.List;
import java.util.Map;

public interface EmilFxfrService {

    public String insertEmilFxfr(Map<String, Object> pmParam) throws Exception;

    public int updateEmilFxfr(Map<String, Object> pmParam) throws Exception;

    public int updateFile(Map<String, Object> pmParam) throws Exception;

    public int getEmilFxfrCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getEmilFxfrList(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> getEmilFxfr(Map<String, ?> pmParam) throws Exception;

    public int deleteEmilFxfr(Map<String, ?> pmParam) throws Exception;

    public int getEmilFxfrDpmsDivCount(Map<String, ?> pmParam) throws Exception;
}