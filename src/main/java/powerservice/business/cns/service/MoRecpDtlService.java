package powerservice.business.cns.service;

import java.util.List;
import java.util.Map;

public interface MoRecpDtlService {

    public int deleteMoRecpDtl(Map<String, ?> pmParam) throws Exception;

    public int getMoRecpDtlCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getMoRecpDtlList(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> getMoRecpDtl(Map<String, ?> pmParam) throws Exception;

    public int getMoConsCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getMoConsList(Map<String, ?> pmParam) throws Exception;

}