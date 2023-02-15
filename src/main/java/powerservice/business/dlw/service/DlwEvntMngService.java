package powerservice.business.dlw.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

public interface DlwEvntMngService {
		
	
	public List<Map<String, Object>> getEvntBranchList(Map<String, ?> pmParam) throws Exception; //지부 관리 리스트
	 
    public  List<Map<String, Object>> getBranchCode(Map<String, ?> pmParam)  throws Exception;  //지부코드 code
    
    public  int insertEvntBranch(Map<String, ?> pmParam)  throws Exception;  //지부 insert저장
    
    public int updateEvntBranch(Map<String, ?> pmParam)  throws Exception;  //지부 update

    public int deleteEvntBranch(Map<String, ?> pmParam)  throws Exception;  //지부 delete
    
    public List<Map<String, Object>> selectEnvtBranch (Map<String, ?> pmParam) throws Exception; //지부장조회1 조회
    
    public List<Map<String, Object>> getEventManList(Map<String, ?> pmParam) throws Exception; //행사자 관리 리스트
    
    public String  insrtEventMan(Map<String, Object> pmParam) throws Exception; //행사자 등록

    public int deleteEventMan(Map<String, ?> pmParam)  throws Exception;  //행사자 삭제

    public int updateEventMan(Map<String, Object> pmParam)  throws Exception;  //행사자 수정

    public int chkEventManaCnt(Map<String, ?> pmParam) throws Exception; // 지부장 등록건수 조회
    
    public List<Map<String, Object>> getEvntWHList(Map<String, ?> pmParam) throws Exception; //창고 관리 리스트
    
    public int saveEvntWarehouse(XPlatformMapDTO xpDto, Map<String, Object> mOut)  throws Exception;  //창고 등록/수정/삭제
    
    public List<Map<String, Object>> getEvntSPList(Map<String, ?> pmParam) throws Exception; //제공용품 리스트
    
    public int saveEvntSupplies(XPlatformMapDTO xpDto, Map<String, Object> mOut)  throws Exception;  //제공용품 등록/수정/삭제
    
    public List<Map<String, Object>> getEvntSPmaxSqncList(Map<String, ?> pmParam) throws Exception; //제공용품seq
    
    public List<Map<String, Object>> getEvntSPCtgMstList(Map<String, ?> pmParam) throws Exception; //제공용품 카테고리
    
    public List<Map<String, Object>> getEvntSPCtgDtlList(Map<String, ?> pmParam) throws Exception; //제공용품 카테고리
    
    public List<Map<String, Object>> getEvntSPCtgSubList(Map<String, ?> pmParam) throws Exception; //제공용품 카테고리
    
    public List<Map<String, Object>> getEvntSPCtgSeqList(Map<String, ?> pmParam) throws Exception; //제공용품 카테고리
    
    public int updateEvntSPCtgMst(Map<String, ?> pmParam) throws Exception;
    
    public int insertEvntSPCtgMst(Map<String, ?> pmParam) throws Exception;
    
    public int deleteEvntSPCtgMst(Map<String, ?> pmParam) throws Exception;
    
    public int updateEvntSPCtgDtl(Map<String, ?> pmParam) throws Exception;
    
    public int insertEvntSPCtgDtl(Map<String, ?> pmParam) throws Exception;
    
    public int deleteEvntSPCtgDtl(Map<String, ?> pmParam) throws Exception;
    
    public int insertEvntSPCtgSub(Map<String, ?> pmParam) throws Exception;
    
    public int deleteEvntSPCtgSub(Map<String, ?> pmParam) throws Exception;
    
    public List<Map<String, Object>> getEvntSPpopList(Map<String, ?> pmParam) throws Exception; //제공용품 팝업 리스트
}