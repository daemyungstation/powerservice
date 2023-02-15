package powerservice.business.cns.service;

import java.util.List;
import java.util.Map;

public interface PersInfoPcusCnsnService {

    public int insertPersInfoPcusCnsn(Map<String, ?> pmParam) throws Exception;

    public int updatePersInfoPcusCnsn(Map<String, ?> pmParam) throws Exception;

    public int getPersInfoPcusCnsnCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getPersInfoPcusCnsnList(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> getPersInfoPcusCnsn(String psId) throws Exception;

    public Map<String, Object> getPersInfoPcusSrctDtpt() throws Exception;

    public int savePersInfoPcusSrct(Map<String, ?> pmParam) throws Exception;

    public int getPersInfoPcusSrctHstrCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getPersInfoPcusSrctHstrList(Map<String, ?> pmParam) throws Exception;

}