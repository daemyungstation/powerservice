package powerservice.business.evl.service;

import java.util.List;
import java.util.Map;

public interface EvltExecService {

    public int getEvltExecCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getEvltExecList(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> getEvltExec(Map<String, ?> pmParam) throws Exception;

}