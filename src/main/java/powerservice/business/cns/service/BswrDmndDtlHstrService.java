package powerservice.business.cns.service;

import java.util.List;
import java.util.Map;

public interface BswrDmndDtlHstrService {

    public int getBswrDmndDtlHstrCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getBswrDmndDtlHstrList(Map<String, ?> pmParam) throws Exception;

}