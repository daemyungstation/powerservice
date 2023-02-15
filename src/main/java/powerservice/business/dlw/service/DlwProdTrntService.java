package powerservice.business.dlw.service;

import java.util.List;
import java.util.Map;

public interface DlwProdTrntService {

    public String getChkProdTransDedAppYn(Map<String, ?> pmParam) throws Exception;

    public String updateTransMemProdAccnt(Map<String, Object> pmParam) throws Exception;

    public int getProdTrntHstrCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getProdTrntHstrList(Map<String, ?> pmParam) throws Exception;

}