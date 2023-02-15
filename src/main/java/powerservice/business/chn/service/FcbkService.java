package powerservice.business.chn.service;

import java.util.List;
import java.util.Map;

public interface FcbkService {

    public int getFcbkCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getFcbkList(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> getFcbk(String id) throws Exception;

    public int getFcbkCmmnCount(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> getFcbkCmmn(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getFcbkCmmnList(Map<String, ?> pmParam) throws Exception;

    public int getFcbkPhtoCount(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> getFcbkPhto(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getFcbkPhtoList(Map<String, ?> pmParam) throws Exception;

    public String insertFcbkCmmn(Map<String, ?> pmParam) throws Exception;

    public int updateFcbkCmmn(Map<String, ?> pmParam) throws Exception;

    public int insertFcbk() throws Exception;

}