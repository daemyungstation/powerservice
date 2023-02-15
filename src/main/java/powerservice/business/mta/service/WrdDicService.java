package powerservice.business.mta.service;

import java.util.List;
import java.util.Map;

public interface WrdDicService {

    public int getWrdDicCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getWrdDicList(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> getWrdDic(String id) throws Exception;

    public String insertWrdDic(Map<String, ?> pmParam) throws Exception;

    public int updateWrdDic(Map<String, ?> pmParam) throws Exception;

    public int deleteWrdDic(Map<String, ?> pmParam) throws Exception;

    public int checkWrdDic(Map<String, ?> pmParam) throws Exception;

    public String getWrdDicId(Map<String, ?> pmParam) throws Exception;

    public int getWrdDicIdCount(Map<String, ?> pmParam) throws Exception;

}