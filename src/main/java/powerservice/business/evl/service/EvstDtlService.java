package powerservice.business.evl.service;

import java.util.List;
import java.util.Map;

public interface EvstDtlService {

    public int getEvstDtlCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getEvstDtlList(Map<String, ?> pmParam) throws Exception;

    public String insertEvstDtl(Map<String, Object> pmParam) throws Exception;

    public Map<String, Object> getEvstDtl(String psId) throws Exception;

    public int updateEvstDtl(Map<String, Object> pmParam) throws Exception;

    public int deleteEvstDtl(Map<String, Object> pmParam) throws Exception;

    public String insertCopyEvstDtl(Map<String, ?> pmParam) throws Exception;
}