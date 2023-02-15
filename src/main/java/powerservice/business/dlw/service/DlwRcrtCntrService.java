package powerservice.business.dlw.service;

import java.util.List;
import java.util.Map;

public interface DlwRcrtCntrService {
    public int getRcrtCntrCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getRcrtCntrList(Map<String, ?> pmParam) throws Exception;
    
    public String insertFile(Map<String, ?> pmParam) throws Exception;

}