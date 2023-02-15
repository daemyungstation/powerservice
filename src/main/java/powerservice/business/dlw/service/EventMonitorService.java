package powerservice.business.dlw.service;

import java.util.List;
import java.util.Map;

import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

public interface EventMonitorService {
    public List<Map<String, Object>> getMonitorQuestList(Map<String, Object> pmParam) throws Exception;

    public int saveMonitorQuestList(XPlatformMapDTO xpDto) throws Exception;

    public List<Map<String, Object>> selectMontrRptList(Map<String, Object> pmParam) throws Exception;

    public int saveMonitorResultReport(XPlatformMapDTO xpDto) throws Exception;

    public List<Map<String, Object>> selectMontrRptDtl(Map<String, Object> pmParam) throws Exception;

    public String getEventAccntYn(Map<String, Object> pmParam) throws Exception;

    public List<Map<String, Object>> getEventInfo(Map<String, Object> pmParam) throws Exception;

    public List<Map<String, Object>> selectMontrRptAnalysisList(Map<String, Object> pmParam) throws Exception;

    public List<Map<String, Object>> selectMontrRptAnalysisList1(Map<String, Object> pmParam) throws Exception;

    public List<Map<String, Object>> selectMontrRptAnalysisList2(Map<String, Object> pmParam) throws Exception;

    public List<Map<String, Object>> selectMontrRptAnalysisList3(Map<String, Object> pmParam) throws Exception;

    public List<Map<String, Object>> getMontrRptDtlList(Map<String, Object> pmParam) throws Exception; // 모니터링 상세보고서 조회

    public int updateMontrRptDtlBigo(Map<String, ?> pmParam) throws Exception; // 의전행사 모니터링 상세 보고서 수정

    public int insertMontrRptDtlBigo(Map<String, ?> pmParam) throws Exception; // 의전행사 모니터링 상세 보고서 신규저장

    public int deleteMontrRptDtlBigo(Map<String, ?> pmParam) throws Exception; // 의전행사 모니터링 상세 보고서 삭제
}