package powerservice.business.kms.service;

import java.util.List;
import java.util.Map;

public interface AncmTrgtAthrService {

    public List<Map<String, Object>> getAncmTrgtAthrList(String sAncmMtrId) throws Exception;

    public int insertAncmTrgtAthr(Map<String, ?> pmParam) throws Exception;

    public int deleteAncmTrgtAthr(Map<String, ?> pmParam) throws Exception;

}