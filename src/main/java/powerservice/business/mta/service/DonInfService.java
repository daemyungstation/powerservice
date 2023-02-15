package powerservice.business.mta.service;

import java.util.List;
import java.util.Map;

public interface DonInfService {

    public List<Map<String, Object>> getDonInfTreeList(Map<String, ?> pmParam) throws Exception;

    public int getDonInfCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getDonInfList(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> getDonInf(Map<String, ?> pmParam) throws Exception;

    public void mergeDonInf(Map<String, ?> pmParam) throws Exception;

    public int getDonInfDuplicateCount(Map<String, ?> pmParam) throws Exception;

    public int deleteDonInf(Map<String, ?> pmParam) throws Exception;

    public int getRefercolmCount(Map<String, ?> pmParam) throws Exception;

    public int getDonInfMaxSequence(Map<String, ?> pmParam) throws Exception;

}