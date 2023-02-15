package powerservice.business.kms.service;

import java.util.List;
import java.util.Map;

public interface FaqHstrService {

    public int getFaqHstrCount(Map<String, ?> mpParam) throws Exception;

    public List<Map<String, Object>> getFaqHstrList(Map<String, ?> mpParam) throws Exception;

}