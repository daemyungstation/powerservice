package powerservice.business.evl.service;

import java.util.List;
import java.util.Map;

public interface EvltTrprInfoService {

    public int getEvltTrprInfoCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getEvltTrprInfoList(Map<String, ?> pmParam) throws Exception;

    public int insertEvltTrprInfo(Map<String, Object> pmParam) throws Exception;

    public int deleteEvltTrprInfo(Map<String, Object> pmParam) throws Exception;

    public int deleteAllEvltTrprInfo(Map<String, Object> pmParam) throws Exception;


}