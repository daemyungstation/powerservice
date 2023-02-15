package powerservice.business.usr.service;

import java.util.List;
import java.util.Map;

import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;

public interface UserService {

    public String insertUser(Map<String, Object> pmParam) throws Exception;

    public int updateUser(Map<String, Object> pmParam) throws Exception;

    public void updateFile(Map<String, Object> pmParam) throws Exception;

    public int updateUserPassword(Map<String, ?> pmParam) throws Exception;

    public int getUserCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getUserList(Map<String, ?> pmParam) throws Exception;
    
    public List<Map<String, Object>> getNewTypeUserList(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getUserList2(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getUserStatusList(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> getUser(Map<String, ?> pmParam) throws Exception;

    public int mergeChnlStatInfo(Map<String, ?> pmParam) throws Exception;

    public void mergeDlwEmpl() throws Exception;

    public List<Map<String, Object>> getUserListForMemo(Map<String, Object> pmParam) throws Exception;

    public int getevalUserCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getevalUserList(Map<String, ?> pmParam) throws Exception;

    public void evalsave(Map<String, DataSetMap> mapInDs) throws Exception;

    public void evalmsave(Map<String, Object> pmParam) throws Exception;

    public int getsearchevalUserCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getsearchevalUserList(Map<String, ?> pmParam) throws Exception;


    public void delevalsave(Map<String, DataSetMap> mapInDs) throws Exception;
    
    public int updateLastLoginDttm(Map<String, ?> pmParam) throws Exception;

	public List<Map<String, Object>> getInCallCount(Map<String, ?> mSearchParam) throws Exception; 

	public List<Map<String, Object>> getOutCallCount(Map<String, ?> mSearchParam) throws Exception;
    
	public int getUserFormCount(Map<String, ?> pmParam) throws Exception;
}
















