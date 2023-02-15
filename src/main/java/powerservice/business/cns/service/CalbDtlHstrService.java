package powerservice.business.cns.service;

import java.util.List;
import java.util.Map;

public interface CalbDtlHstrService {

    public int getCalbDtlHstrCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getCalbDtlHstrList(Map<String, ?> pmParam) throws Exception;

}