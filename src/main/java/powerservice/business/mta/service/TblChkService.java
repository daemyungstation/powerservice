package powerservice.business.mta.service;

import java.util.List;
import java.util.Map;

public interface TblChkService {

    public int getTblChkCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getTblChkList(Map<String, ?> pmParam) throws Exception;

    public int getTblDtailCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getTblDetailList(Map<String, ?> pmParam) throws Exception;

    public int getColmChkCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getColmChkList(Map<String, ?> pmParam) throws Exception;

    public int getColmDtailCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getColmDetailList(Map<String, ?> pmParam) throws Exception;

    public int getDonChkCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getDonChkList(Map<String, ?> pmParam) throws Exception;
}