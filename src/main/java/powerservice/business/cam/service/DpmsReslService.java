package powerservice.business.cam.service;

import java.util.List;
import java.util.Map;

public interface DpmsReslService {

    public int getDpmsReslByCustomerCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getDpmsReslByCustomerList(Map<String, ?> pmParam) throws Exception;

    public int getDpmsReslByUserCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getDpmsReslByUserList(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getDpmsReslTop10(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> getDpmsReslDsps(Map<String, ?> pmParam) throws Exception;

    public int getTdyDpmsCnsrCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getDpmsReslDclDsps(Map<String, ?> pmParam) throws Exception;

}