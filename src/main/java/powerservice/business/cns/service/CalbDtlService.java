package powerservice.business.cns.service;

import java.util.List;
import java.util.Map;

public interface CalbDtlService {

    public int getCalbDtlCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getCalbDtlList(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> getCalbDtl(Map<String, ?> pmParam) throws Exception;

    public int updateCalbDtl(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getTodoCalbDtlCount() throws Exception;

}