package powerservice.business.cam.service;

import java.util.List;
import java.util.Map;

public interface CmpgTelHandleService {

    public List<Map<String, Object>> getCmpgTelHandleTotalList(Map<String, ?> pmParam) throws Exception;

    public int getTrgtCustTelHandleCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getTrgtCustTelHandleList(Map<String, ?> pmParam) throws Exception;

    public int updateCmpgTelHandleResult(Map<String, Object> pmParam) throws Exception;

    public Map<String, Object> getTrgtCustCmpgTelHandle(Map<String, Object> pmParam) throws Exception;

    public int insertDpmsReslHstr(Map<String, ?> pmParam) throws Exception;
    
    public int getNewTypeTrgtCustTelHandleCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getNewTypeTrgtCustTelHandleList(Map<String, ?> pmParam) throws Exception;
    
    public int getDirectTrgtCustTelHandleCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getDirectTrgtCustTelHandleList(Map<String, ?> pmParam) throws Exception;
    
    public int getUplusTrgtCustTelHandleCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getUplusTrgtCustTelHandleList(Map<String, ?> pmParam) throws Exception;

}