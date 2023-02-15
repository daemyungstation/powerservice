package powerservice.business.sys.service;

import java.util.List;
import java.util.Map;

public interface XlsService {

    public int getXlsCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getXlsList(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> getXls(String psId) throws Exception;

    public String insertXls(Map<String, ?> pmParam) throws Exception;

    public int updateXls(Map<String, ?> pmParam) throws Exception;

    public int deleteXls(Map<String, ?> pmParam) throws Exception;


    public int getXlsDataCount(Map<String, ?> pmParam) throws Exception;

    public void downloadXls(Map<String, Object> pmParam) throws Exception;

    public void downloadXlsBigGrid(Map<String, Object> pmParam, List<Map<String, Object>> lmColumn) throws Exception;

}