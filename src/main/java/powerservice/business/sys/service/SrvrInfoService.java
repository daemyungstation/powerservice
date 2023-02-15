package powerservice.business.sys.service;

import java.util.List;
import java.util.Map;

public interface SrvrInfoService {

    public String insertSrvrInfo(Map<String, ?> pmParam) throws Exception;

    public int updateSrvrInfo(Map<String, ?> pmParam) throws Exception;

    public int deleteSrvrInfo(Map<String, ?> pmParam) throws Exception;

    public int getSrvrInfoCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getSrvrInfoList(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> getSrvrInfo(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> getSrvrInfoBySrvrIpAddr(Map<String, ?> pmParam) throws Exception;

}