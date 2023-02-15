package powerservice.business.sys.service;

import java.util.List;
import java.util.Map;

public interface BasVlService {

    public String insertBasVl(Map<String, ?> pmParam) throws Exception;

    public void updateBasVl(Map<String, ?> pmParam) throws Exception;

    public int getBasVlCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getBasVlList(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getBasVlList(String[] psItemList) throws Exception;

    public Map<String, Object> getBasVl(String psId, String psCntrCd) throws Exception;

    public Map<String, Object> getBasVl(String psId) throws Exception;

    public String getBasVlAsString(String psBasVlNm, String psCntrCd) throws Exception;

    public String getBasVlAsString(String psBasVlNm) throws Exception;

}