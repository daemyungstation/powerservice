package powerservice.business.kms.service;

import java.util.List;
import java.util.Map;

public interface FaqDmndService {

    public String insertFaqDmnd(Map<String, Object> pmParam) throws Exception;

    public int updateFaqDmnd(Map<String, Object> pmParam) throws Exception;

    public int updateFile(Map<String, Object> pmParam) throws Exception;

    public int updateFaqDmndStat(Map<String, ?> pmParam) throws Exception;

    public int getFaqDmndCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getFaqDmndList(Map<String, Object> pmParam) throws Exception;

    public Map<String, Object> getFaqDmnd(Map<String, ?> pmParam) throws Exception;

    public int deleteFaqDmnd(String id) throws Exception;

}