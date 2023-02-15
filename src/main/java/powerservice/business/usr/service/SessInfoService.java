package powerservice.business.usr.service;

import java.util.Map;

public interface SessInfoService {

    public int insertSessInfo(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> getSessInfo(String sId, String sCntrCd) throws Exception;

    public Map<String, Object> getAccessCheckInfo(Map<String, ?> pmParam) throws Exception;

}