package powerservice.business.onln.service;

import java.util.List;
import java.util.Map;

import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

public interface ExtcTrgtService {

    public int getExtcTrgtCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getExtcTrgtList(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getUnpyStat(Map<String, Object> pmParam) throws Exception;

    public int updateOnlineStatAlct(Map<String, ?> pmParam) throws Exception;

    public int insertOnlnCons(Map<String, ?> pmParam) throws Exception;

    public void insertSubTrgtList() throws Exception;

    public void insertB2QTmJoinCust() throws Exception;

    public void insertLntmBzptAdmn() throws Exception;

    public void insertLntmCnctPrtc() throws Exception;

    public void insertUnpy() throws Exception;

    public int insertMonthUnpy(Map<String, Object> pmParam) throws Exception;

    public void updateUnpyAcrs() throws Exception;

    public void updateCnctAcrs() throws Exception;

    public void updatExcd() throws Exception;

    public void updateAltnAmt() throws Exception;

    public void insertDlwYenche() throws Exception;

    public List<Map<String, Object>> selectB2BList(Map<String, Object> pmParam) throws Exception; //B2B 업체코드 조회


    public String  insertB2BComp(Map<String, Object> pmParam) throws Exception; //그룹 코드 등록

    public int updateB2BComp(Map<String, Object> pmParam)  throws Exception;  //그룹 코드 수정

    public int deleteB2bcd(Map<String, ?> pmParam)  throws Exception;  //거래처 삭제

    public int countCpHist(Map<String, Object> pmParam) throws Exception;	//레저연동 조회 건수

    public List<Map<String, Object>> getCpHist(Map<String, Object> pmParam) throws Exception;	//레저연동 리스트

    public String  isEvent(String paramString) throws Exception; //행사 여부

    public Map<String, Object>  sendDataDgns(Map<String, Object> pmParam ) throws Exception; //레저 데이터 전송 작업(웹 redirecting)

    public int updateRsSndEnd(Map<String, ?> pmParam) throws Exception; //TMS 전송 결과

    public int countResortYenCheInfoList() throws Exception;	//레저연동 조회 건수
    
    public int countResortYenCheInfoDataCnt(Map<String, Object> pmParam) throws Exception; //레저연체 리스트 페이지 조회용

    public List<Map<String, Object>> selectResortYenCheInfoList(Map<String, Object> pmParam) throws Exception; //레저연체 리스트

    public int updateRsYenCheSndEnd(Map<String, ?> pmParam) throws Exception; //레저연체 TMS 전송 결과

    public int insertResortMngHstr(Map<String, ?> pmParam) throws Exception; //레저연동 데이터 히스토리

    public int yenCheInfoRefresh(XPlatformMapDTO xpDto, Map<String, Object> mOut) throws Exception;

    //레저연동 이력 20170810
    public int selectResortMngHstr_cnt(Map<String, Object> pmParam) throws Exception;	//레저연동 이력 건수

    public List<Map<String, Object>> selectResortMngHstr(Map<String, Object> pmParam) throws Exception; //레저연동 이력 리스트

}