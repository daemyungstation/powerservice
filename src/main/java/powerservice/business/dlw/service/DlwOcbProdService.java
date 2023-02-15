package powerservice.business.dlw.service;

import java.util.List;
import java.util.Map;

public interface DlwOcbProdService {
    public int getOcbProdCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getOcbProdList(Map<String, ?> pmParam) throws Exception;

    public int insertOcbProd(Map<String, ?> pmParam) throws Exception;

    public int updateOcbProd(Map<String, ?> pmParam) throws Exception;

    public int getOcbProdSaveCount(Map<String, ?> pmParam) throws Exception;

    public int getOcbTransHistCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getOcbTransHistList(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getOcbTransList(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getOcbDelList(Map<String, ?> pmParam) throws Exception;

    //임동진 수정
    public List<Map<String, Object>> getOcbAddList(Map<String, ?> pmParam) throws Exception;

    public int getOcbTransHistCnt() throws Exception;

    public int insertOcbTransHist(Map<String, ?> pmParam) throws Exception;

    public int updateOcbTransHist(Map<String, ?> pmParam) throws Exception;
}