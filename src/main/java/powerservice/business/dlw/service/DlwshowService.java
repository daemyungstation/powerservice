package powerservice.business.dlw.service;

import java.util.List;
import java.util.Map;

import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;

public interface DlwshowService {

    public int getDlwshowCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getDlwshowList(Map<String, ?> pmParam) throws Exception;


    public int getDlwshownmCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getDlwshownmList(Map<String, ?> pmParam) throws Exception;


    public int insertshow(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getDlwshowselect(Map<String, ?> pmParam) throws Exception;


    public int showupdate(Map<String, ?> pmParam) throws Exception;


    public int getshowChk1(Map<String, ?> pmParam) throws Exception;

    public int getshowChk2(Map<String, ?> pmParam) throws Exception;

    public int getshowChk3(Map<String, ?> pmParam) throws Exception;

    public int getshowChk4(Map<String, ?> pmParam) throws Exception;

    public int getshowChk5(Map<String, ?> pmParam) throws Exception;

    public int getshowChk6(Map<String, ?> pmParam) throws Exception;

    public int getshowChk7(Map<String, ?> pmParam) throws Exception;

    public void updateshowsave(Map<String, DataSetMap> mapInDs ) throws Exception;
    
    public int getDlwmagazineCount(Map<String, ?> pmParam) throws Exception;
    
    public List<Map<String, Object>> getDlwmagazineList(Map<String, ?> pmParam) throws Exception;
    
    public void insertmagazinesave(Map<String,?> pmParam ) throws Exception;
    
    public void del_magazine_all(Map<String, ?> pmParam ) throws Exception;
    
    public void del_magazine_cnt(Map<String,  DataSetMap> mapInDs ) throws Exception;
    
    public void updatemagazine_magam(Map<String,  ?> pmParam  ) throws Exception;
    
}