package powerservice.business.cns.service;

import java.util.List;
import java.util.Map;

public interface RecrncResrDtlService {

    public String insertRecrncResrDtl(Map<String, ?> pmParam) throws Exception;

    public int updateRecrncResrDtl(Map<String, ?> pmParam) throws Exception;

    public int getRecrncResrDtlCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getRecrncResrDtlList(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> getRecrncResrDtl(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getTodoRecrncResrDtlList(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> getTodoRecrncResrDtl(Map<String, ?> pmParam) throws Exception;

}