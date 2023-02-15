package powerservice.business.kms.service;

import java.util.List;
import java.util.Map;

public interface FaqDtlService {

    public String insertFaq(Map<String, Object> mpParam) throws Exception;

    public int updateFaq(Map<String, Object> mpParam) throws Exception;

    public int updateFile(Map<String, Object> mpParam) throws Exception;

    public int updateInqCnt(String faq_id) throws Exception;

    public int getFaqCount(Map<String, ?> mpParam) throws Exception;

    public List<Map<String, Object>> getFaqList(Map<String, ?> mpParam) throws Exception;

    public Map<String, Object> getFaq(Map<String, ?> mpParam) throws Exception;

    public void deleteFaq(Map<String, ?> mpParam) throws Exception;

}