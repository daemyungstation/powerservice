package powerservice.business.cam.service;

import java.util.List;
import java.util.Map;

public interface ExtcMstTrgtService {

    public int updateUnpy(Map<String, Object> pmParam) throws Exception;

    public List<Map<String, Object>> getDeleteTrgtCustAlct(Map<String, ?> pmParam) throws Exception;

}