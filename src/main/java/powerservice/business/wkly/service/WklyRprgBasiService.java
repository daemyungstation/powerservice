package powerservice.business.wkly.service;

import java.util.List;
import java.util.Map;

public interface WklyRprgBasiService {

    public int getWklyUserCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getWklyUserList(Map<String, ?> pmParam) throws Exception;

    public String insertWklyReport(Map<String, Object> pmParam) throws Exception;

    public int updateWklyReport(Map<String, Object> pmParam) throws Exception;

    public int commitWklyReport(Map<String, Object> pmParam) throws Exception;

    public int getWklyReportResultCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getWklyReportResult(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getExcelUserList(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> getWklybswrList(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getWklyFile(Map<String, ?> pmParam) throws Exception;

}