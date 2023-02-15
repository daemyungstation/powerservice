package powerservice.business.kms.service;

import java.util.List;
import java.util.Map;

public interface BkmkInfoService {

    public int getBkmkInfoCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getBkmkInfoList(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> getBkmkInfo(Map<String, ?> pmParam) throws Exception;

    public int getBkmkInfoMaxOrd(Map<String, ?> pmParam) throws Exception;

    public String insertBkmkInfo(Map<String, ?> pmParam) throws Exception;

    public int updateBkmkInfo(Map<String, ?> pmParam) throws Exception;

    public int deleteBkmkInfo(Map<String, Object> pmParam) throws Exception;

    public int getBkmkInfoDupCount(Map<String, ?> pmParam) throws Exception;

}