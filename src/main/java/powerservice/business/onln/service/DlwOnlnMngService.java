package powerservice.business.onln.service;

import java.util.List;
import java.util.Map;

import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;

public interface DlwOnlnMngService { 
	
	/* ================================================================
     * 날짜 : 20220617
     * 이름 : 김주용
     * 추가내용 : 홈페이지 회원 조회
     * ================================================================
     * */
	
	public int getOnlnMemberCount(Map<String, ?> pmParam) throws Exception;
	
    public List<Map<String, Object>> getOnlnMemberList(Map<String, ?> pmParam) throws Exception;
    
    public List<Map<String, Object>> getOnlnMemberDtlList(Map<String, ?> pmParam) throws Exception;
    
    public List<Map<String, Object>> getOnlnPayChgDtlList(Map<String, ?> pmParam) throws Exception;
    
    public void updateWebMemInfo(Map<String, ?> pmParam) throws Exception;
    
    public void updateWebDormancy(Map<String, ?> pmParam) throws Exception;
    
    public void updateWebPayChgComp(Map<String, ?> pmParam) throws Exception;
    
	public int getOnlnConsCount(Map<String, ?> pmParam) throws Exception;
	
    public List<Map<String, Object>> getOnlnConsList(Map<String, ?> pmParam) throws Exception;    
    
    
    /* ================================================================
     * 날짜 : 20220617
     * 이름 : 김주용
     * 추가내용 : 홈페이지 회원 변경이력
     * ================================================================
     * */
	
	public int getOnlnMemChngCount(Map<String, ?> pmParam) throws Exception;
	
    public List<Map<String, Object>> getOnlnMemChngList(Map<String, ?> pmParam) throws Exception;
    
    public void insertOnlnConsLog(Map<String, ?> pmParam) throws Exception;
    
    public void updateOnlnConsMst(Map<String, ?> pmParam) throws Exception;
}
