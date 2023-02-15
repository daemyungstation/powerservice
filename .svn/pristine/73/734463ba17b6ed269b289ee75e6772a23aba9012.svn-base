package powerservice.business.dlw.service;

import java.util.List;
import java.util.Map;

import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

public interface EtcService {

	public List<Map<String, Object>> selectCardFeeRtMngList(Map<String, Object> pmParam) throws Exception;
	
	public List<Map<String, Object>> selectDeptIPInfoList(Map<String, Object> pmParam) throws Exception;
	
	public int deleteDeptIPInfoList(XPlatformMapDTO xpDto) throws Exception;
	
	public int saveCardFeeRt(XPlatformMapDTO xpDto) throws Exception;
	
	public int saveDeptIPInfoList(XPlatformMapDTO xpDto) throws Exception;
	
	/** ================================================================
     * 날짜 : 20190725
     * 이름 : 송준빈
     * 추가내용 : 상담대상자관리(NEW) 조회수
     * 대상 테이블 : PS_WILLVI.TB_COUNSEL_TARGET
     * ================================================================
     * */
    public int getCounselTargetCount(Map<String, ?> pmParam) throws Exception;

    /** ================================================================
     * 날짜 : 20190725
     * 이름 : 송준빈
     * 추가내용 : 상담대상자관리(NEW) 리스트 조회
     * 대상 테이블 : PS_WILLVI.TB_COUNSEL_TARGET
     * ================================================================
     * */
    public List<Map<String, Object>> getCounselTargetList(Map<String, ?> pmParam) throws Exception;
    
    /** ================================================================
     * 날짜 : 20200617
     * 이름 : 김주용
     * 추가내용 : 다이렉트상담관리(NEW) 조회수
     * 대상 테이블 : 
     * ================================================================
     * */
    public int getDirectCounselCount(Map<String, ?> pmParam) throws Exception;

    /** ================================================================
     * 날짜 : 20200617
     * 이름 : 김주용
     * 추가내용 : 다이렉트상담관리(NEW) 리스트 조회
     * 대상 테이블 : 
     * ================================================================
     * */
    public List<Map<String, Object>> getDirectCounselList(Map<String, ?> pmParam) throws Exception;
    
    public int updateDirectSessionClose(Map<String, ?> pmParam) throws Exception;
}