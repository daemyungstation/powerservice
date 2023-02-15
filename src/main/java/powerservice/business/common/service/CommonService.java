package powerservice.business.common.service;

import java.util.List;
import java.util.Map;

import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

public interface CommonService {

    public List<Map<String, Object>> getBranchList(Map<String, Object> pmParam) throws Exception;

    public List<Map<String, Object>> getWhCdList(Map<String, Object> pmParam) throws Exception;

    public List<Map<String, Object>> getCustomerCdList(Map<String, Object> pmParam) throws Exception;

    public List<Map<String, Object>> selectGoodsComList(Map<String, Object> pmParam) throws Exception;

    public List<Map<String, Object>> selectCpInfo(Map<String, Object> pmParam) throws Exception;

    public List<Map<String, Object>> selectOrgChartMap(Map<String, Object> pmParam) throws Exception;

    public List<Map<String, Object>> selectStGrpCdList(Map<String, Object> pmParam) throws Exception;

    public List<Map<String, Object>> selectOptSvcList(Map<String, Object> pmParam) throws Exception;

    public int insertExcelDownHist(XPlatformMapDTO xpDto) throws Exception;

    public List<Map<String, Object>> selectDataAuthGrpList(Map<String, Object> pmParam) throws Exception;

    public List<Map<String, Object>> selectReportAuthGrpList(Map<String, Object> pmParam) throws Exception;

    public void insertSMS(Map<String, Object> pmParam) throws Exception;

    public int insertFileDown(Map<String, Object> hmParam) throws Exception;

    public int updateFileDownDthms(Map<String, Object> hmParam) throws Exception;

    public List<Map<String, Object>> selectFileDown(Map<String, Object> pmParam) throws Exception;

    public List<Map<String, Object>> selectSaleTypeByStGrp(Map<String, Object> pmParam) throws Exception;

    public List<Map<String, Object>> selectProdCdList(Map<String, Object> pmParam) throws Exception;

    public List<Map<String, Object>> getB2bList(Map<String, Object> pmParam) throws Exception;


    //접속 로그 2017.11.30
    public void insertLog(Map<String, Object> pmParam) throws Exception;

    //접속 로그카운트 검색 - 2017.12.08 정승철
    public int getLogCount(Map<String, ?> pmParam) throws Exception;

    //접속 로그 검색 - 2017.12.08 정승철
    public List<Map<String, Object>> getLogList(Map<String, ?> pmParam) throws Exception;
    
    //엑셀다운로드 사유기입
    public int insertExcelDownloadReason(Map<String, Object> pmParam) throws Exception;

}