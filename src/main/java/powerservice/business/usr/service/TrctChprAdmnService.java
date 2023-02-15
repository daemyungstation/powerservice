package powerservice.business.usr.service;

import java.util.List;
import java.util.Map;

public interface TrctChprAdmnService {

    public int insertTrctChprAdmn(Map<String, ?> pmParam) throws Exception;

    public int saveTrctChprAdmn(Map<String, ?> pmParam) throws Exception;

    public int updateTrctChprAdmn(Map<String, ?> pmParam) throws Exception;

    public int getTrctChprAdmnCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getTrctChprAdmnList(Map<String, ?> pmParam) throws Exception;

    public int deleteTrctChprAdmn(Map<String, Object> pmParam) throws Exception;

    public List<Map<String, Object>> getTrnsUserForTrnsbox(Map<String, ?> pmParam) throws Exception;

}