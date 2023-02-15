package powerservice.business.sch.service;

import java.util.List;
import java.util.Map;

public interface VctnScdlDtlService {

    public int getVctnScdlDtlNmprCnt(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getVctnScdlDtlList(Map<String, ?> pmParam) throws Exception;

    public int saveVctnScdlDtl(List<Map<String, Object>> mModelList, Map<String, Object> pmParam) throws Exception;

}