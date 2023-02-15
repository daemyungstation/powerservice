package powerservice.business.wkly.service;

import java.util.List;
import java.util.Map;

public interface WklyBswrDtlsCdService {

    public String insertCode(Map<String, ?> pmParam) throws Exception;

    public int updateCode(Map<String, ?> pmParam) throws Exception;

    public int getCodeCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getCodeList(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> getCode(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getCodeList(String[] sCodeSetList) throws Exception;

    public int getCodeMaxSequence(Map<String, ?> pmParam) throws Exception;

    public int getCodeDuplicateCount(Map<String, ?> pmParam) throws Exception;
}