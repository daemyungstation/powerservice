package powerservice.business.kms.service;

import java.util.List;
import java.util.Map;

public interface AncmMtrDtlService {

    public String insertAncmMtrDtl(Map<String, Object> pmParam) throws Exception;

    public int updateAncmMtrDtl(Map<String, Object> pmParam) throws Exception;

    public int updateAncmMtrDtlViewCnt(String psAncmMtrId) throws Exception;

    public int getAncmMtrDtlCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getAncmMtrDtlList(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> getAncmMtrDtl(Map<String, ?> pmParam) throws Exception;

    public int deleteAncmMtrDtl(Map<String, ?> pmParam) throws Exception;

}