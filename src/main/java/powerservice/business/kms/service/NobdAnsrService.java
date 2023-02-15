package powerservice.business.kms.service;

import java.util.List;
import java.util.Map;

public interface NobdAnsrService {

    public String insertNobdAnsr(Map<String, ?> pmParam) throws Exception;

    public int updateNobdAnsr(Map<String, ?> pmParam) throws Exception;

    public int getNobdAnsrCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getNobdAnsrList(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> getNobdAnsr(Map<String, ?> pmParam) throws Exception;

    public int deleteNobdAnsr(Map<String, Object> pmParam) throws Exception;

}