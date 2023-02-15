package powerservice.business.dlw.service;

import java.util.List;
import java.util.Map;

import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

public interface EmpService {
	
	public String createDataAuthQry(Map<String, Object> pmParam) throws Exception;
	
	public List<Map<String, Object>> selectEmpleList(Map<String, Object> pmParam) throws Exception;
	
	public int mergeEmployee(XPlatformMapDTO xpDto, Map<String, Object> mResult) throws Exception;
	
	public int resinCancel(XPlatformMapDTO xpDto) throws Exception;
	
	public List<Map<String, Object>> selectEmpleMemChangeList(Map<String, Object> pmParam) throws Exception;
	
	public int insertMstrChgInf(XPlatformMapDTO xpDto) throws Exception;
	
	public int updateEmployeeResin(XPlatformMapDTO xpDto) throws Exception;
	
	public int insertHistPosnEmp(XPlatformMapDTO xpDto) throws Exception;
	
	public List<Map<String, Object>> selectDiscntPassMstlist(Map<String, Object> pmParam) throws Exception;
	
	public List<Map<String, Object>> selectRandNumList(Map<String, Object> pmParam) throws Exception;
	
	public int noteSave(XPlatformMapDTO xpDto) throws Exception;
	
	public List<Map<String, Object>> selectMemberList(Map<String, Object> pmParam) throws Exception;
	
	public List<Map<String, Object>> selectMemberListByEmpleNo(Map<String, Object> pmParam) throws Exception;
}