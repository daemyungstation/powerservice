package powerservice.business.cam.service;

import java.util.List;
import java.util.Map;

import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;

public interface SubTrgtListService {

    public int getSubTrgtListCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getSubTrgtList(Map<String, ?> pmParam) throws Exception;

    public String insertSubTrgtList(Map<String, Object> pmParam, DataSetMap listInDataSet) throws Exception;

    public String insertSubTrgtList_Add(Map<String, Object> pmParam, DataSetMap listInDataSet) throws Exception;  //신규가 아닌 추가 서브 대상자 등록

    public int insertTrgtCustByDB(Map<String, Object> pmParam) throws Exception;

    public int insertSubTrgtCustByDB(Map<String, Object> pmParam) throws Exception;

    public int insertOneTrgtCust(Map<String, Object> pmParam) throws Exception;

    public int updateSubTrgtList(Map<String, Object> pmParam) throws Exception;

    public int deleteSubTrgtList(Map<String, Object> pmParam) throws Exception;

    public int deleteTrgtCustByTrgtList(Map<String, ?> pmParam) throws Exception;

    public void searchTrgtCustByDB(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> getAssigNum(String psId) throws Exception;

    public int insertTrgtCustByExcel(Map<String, ?> pmParam) throws Exception;

    public int getSubTrgtStatByCmpgCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getSubTrgtStatByCmpgList(Map<String, ?> pmParam) throws Exception;

}