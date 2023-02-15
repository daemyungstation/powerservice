package powerservice.business.cam.service;

import java.util.List;
import java.util.Map;

public interface TrgtCustService {

    public int getTrgtCustCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getTrgtCustList(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> getTrgtCust(Map<String, ?> pmParam) throws Exception;

    public int deleteTrgtCust(Map<String, Object> pmParam) throws Exception;

    /*public int insertTrgtCustByDB(Map<String, ?> pmParam) throws Exception;*/

    /*public void searchTrgtCustByDB(Map<String, ?> pmParam) throws Exception;*/

    //public int deleteTrgtCustByTrgtList(Map<String, ?> pmParam) throws Exception;

    public int deleteTrgtCustByExcel(Map<String, Object> pmParam) throws Exception;

    public int getTrgtCustHstrCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getTrgtCustHstrList(Map<String, ?> pmParam) throws Exception;

}