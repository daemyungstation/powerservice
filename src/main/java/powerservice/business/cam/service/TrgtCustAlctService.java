package powerservice.business.cam.service;

import java.util.List;
import java.util.Map;

import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;

public interface TrgtCustAlctService {

    public int insertTgtCustAssign(Map<String, Object> pmParam) throws Exception; //사용

    public int updateChangeCnsr(Map<String, Object> pmParam) throws Exception;

    public int insertTgtCustAlctFromSystem(Map<String, Object> mParam) throws Exception;

    public int insertAlct(Map<String, Object> mParam) throws Exception;

    public int deleteTgtcustassign(Map<String, Object> pmParam) throws Exception; //사용

    public int getTrgtCustAlctCount(Map<String, ?> pmParam) throws Exception; //사용
    
    public int getNewTypeTrgtCustAlctCount(Map<String, ?> pmParam) throws Exception; //사용

    public int getDupAlctCount(Map<String, ?> pmParam) throws Exception; //사용

    public List<Map<String, Object>> getDupAlctList(Map<String, ?> pmParam) throws Exception;

    public int getAlctCount(Map<String, ?> pmParam) throws Exception; //사용

    public int getChkAlct(Map<String, Object> pmParam) throws Exception;

    public int getTrgtAlctCustCount(Map<String, ?> pmParam) throws Exception;
    
    public int getNewTypeTrgtAlctCustCount(Map<String, ?> pmParam) throws Exception;
    
    public int deleteDupAlct(Map<String, Object> pmParam) throws Exception;

    public List<Map<String, Object>> getTrgtCustAlctList(Map<String, ?> pmParam) throws Exception;
    
    public List<Map<String, Object>> getNewTypeTrgtCustAlctList(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getTrgtAlctCustList(Map<String, ?> pmParam) throws Exception;
    
    public List<Map<String, Object>> getNewTypeTrgtAlctCustList(Map<String, ?> pmParam) throws Exception;

    public int getTgtCustAlctCountByCust(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getTrgtCustAlctListByCust(Map<String, ?> pmParam) throws Exception;

    public int updateCmpgResult(Map<String, Object> pmParam) throws Exception;

    public Map<String, Object> getCmpgResult(Map<String, ?> pmParam) throws Exception;

    public int getTgtCustAssignCountByCampaign(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getTgtCustAssignListByCampaign(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> getTrgtCustAlct(Map<String, Object> pmParam) throws Exception;

    public int updateTrgtCustCustId(Map<String, ?> pmParam) throws Exception;

    public int getCustHstrCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getCustHstrList(Map<String, ?> pmParam) throws Exception;

    public String updateCtiId(Map<String, Object> pmParam) throws Exception; //사용

    public int getTrgtCustAlctByCnsrCount(Map<String, ?> pmParam) throws Exception; //사용

    public int insertSelectAlct(Map<String, Object> pmParam, DataSetMap listInDataSet) throws Exception; //사용
    
    public int insertNewTypeSelectAlct(Map<String, Object> pmParam, DataSetMap listInDataSet) throws Exception; //사용

    public int insertNumSelectAlct(Map<String, Object> pmParam, DataSetMap listInDataSet) throws Exception; //사용
    
    public int insertNewTypeNumSelectAlct(Map<String, Object> pmParam, DataSetMap listInDataSet) throws Exception; //사용

    public List<Map<String, Object>> getTrgtCustAlctByCnsrList(Map<String, ?> pmParam) throws Exception;	// 사용
    
    public int getNewTypeTrgtCustAlctDirectCount(Map<String, ?> pmParam) throws Exception; //사용
    
    public List<Map<String, Object>> getNewTypeTrgtCustAlctDirectList(Map<String, ?> pmParam) throws Exception;
    
    public int insertNewTypeSelectDirectAlct(Map<String, Object> pmParam, DataSetMap listInDataSet) throws Exception; //사용
    
    public int getNewTypeTrgtCustAlctMainDirectCount(Map<String, ?> pmParam) throws Exception; //사용
    
    public List<Map<String, Object>> getNewTypeTrgtCustAlctMainDirectList(Map<String, ?> pmParam) throws Exception;
    
    public int updateDirectSessionClose(Map<String, ?> pmParam) throws Exception;
    
    
    public int getNewTypeTrgtCustAlctUplusCount(Map<String, ?> pmParam) throws Exception; //사용
    
    public List<Map<String, Object>> getNewTypeTrgtCustAlctUplusList(Map<String, ?> pmParam) throws Exception;
    
    public List<Map<String, Object>> getNewTypeTrgtCustAlctUplusPopList(Map<String, ?> pmParam) throws Exception;
    
    public List<Map<String, Object>> getNewTypeTrgtCustAlctUplusPopList2(Map<String, ?> pmParam) throws Exception;
    
    public int updateUplusSessionClose(Map<String, ?> pmParam) throws Exception;
    
    public int insertNewTypeSelectUplusAlct(Map<String, Object> pmParam, DataSetMap listInDataSet) throws Exception; //사용
    
    public int getNewTypeTrgtCustAlctMainUplusCount(Map<String, ?> pmParam) throws Exception; //사용
    
    public List<Map<String, Object>> getNewTypeTrgtCustAlctMainUplusList(Map<String, ?> pmParam) throws Exception;
    
    public int insertSelectPdsIntr(Map<String, Object> pmParam, DataSetMap listInDataSet) throws Exception; //사용
    
    public int getNewTypeTrgtCustAlctMangiCount(Map<String, ?> pmParam) throws Exception; //사용
    
    public List<Map<String, Object>> getNewTypeTrgtCustAlctMangilist(Map<String, ?> pmParam) throws Exception;
    
    public int insertNewTypeSelectMangiAlct(Map<String, Object> pmParam, DataSetMap listInDataSet) throws Exception; //사용
    
    public int getNewTypeTrgtCustAlctMainMangiCount(Map<String, ?> pmParam) throws Exception; //사용
    
    public List<Map<String, Object>> getNewTypeTrgtCustAlctMainMangiList(Map<String, ?> pmParam) throws Exception;

}