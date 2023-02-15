package powerservice.business.kms.service;

import java.util.List;
import java.util.Map;

public interface SuctSetService {

    public String insertSuctSet(Map<String, ?> pmParam) throws Exception;

    public int updateSuctSet(Map<String, ?> pmParam) throws Exception;

    public int getSuctSetCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getSuctSetList(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> getSuctSet(String id) throws Exception;

    public int getSuctSetMaxOrder(Map<String, ?> pmParam) throws Exception;

    public int getSuctSetDuplicateCount(Map<String, ?> pmParam) throws Exception;

    public int deleteSuctSet(Map<String, ?> pmParam) throws Exception;

}