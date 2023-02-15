package powerservice.business.dlw.service;

import java.util.List;
import java.util.Map;

public interface DlwEmplService {

    public int getDlwEmplCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getDlwEmplList(Map<String, Object> pmParam) throws Exception;

    public Map<String, Object> getDlwEmplDtpt(String psParam) throws Exception;

    public int getEmplCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getEmplList(Map<String, ?> pmParam) throws Exception;

}