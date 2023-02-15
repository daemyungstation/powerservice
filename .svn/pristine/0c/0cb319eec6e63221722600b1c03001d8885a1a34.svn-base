package powerservice.business.dlw.service;

import java.util.List;
import java.util.Map;

import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;

public interface DlwMemOcbService {

    public List<Map<String, Object>> getSrchOcbCardCntList(Map<String, ?> pmParam)  throws Exception;

    public List<Map<String, Object>> getSrchOcbCardCodeList (Map<String, ?> pmParam)  throws Exception;

    public List<Map<String, Object>> getnewList (Map<String, ?> pmParam)  throws Exception;

    public List<Map<String, Object>> getOCBList (Map<String, ?> pmParam)  throws Exception;

    public int getsrchOcbPointcount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getSrchMemYHCardList (Map<String, ?> pmParam)  throws Exception;

    public List<Map<String, Object>> getsrchOcbPointhist (Map<String, ?> pmParam)  throws Exception;

    public List<Map<String, Object>> getSrchMemCrtFileList (Map<String, ?> pmParam)  throws Exception;

    public String  insrtOcbCardCode(Map<String, ?> pmParam) throws Exception;

    public String  insrtOcbpoint(Map<String, ?> pmParam) throws Exception;

    public int deleteCardCode(Map<String, ?> pmParam) throws Exception;

    public int deleteocb(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>>  getsrchOcbPointList  (Map<String, ?> pmParam)  throws Exception; //산출 리스트

    public int getOcbPointHistCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>>  getsrchOcbPointHisList  (Map<String, ?> pmParam)  throws Exception; //산출 이력 리스트

    public void ocb_save_start(DataSetMap srchInDs , String REG_MAN) throws Exception;

    public void ocb_update_start(Map<String, Object> srchInDs ) throws Exception;

    public void ocb_point_update_start(Map<String, Object> srchInDs ) throws Exception;

    public void ocb_update_hist(Map<String, Object> srchInDs ) throws Exception;

    public List<Map<String, Object>> getocb_rejectList (Map<String, ?> pmParam)  throws Exception;

    public String insertOcbPointCal (Map<String, ?> pmParam)  throws Exception; //ocb 산출 입력

    public void ocb_m_update(DataSetMap srchInDs) throws Exception;

    public void delete_ocb_result (DataSetMap srchInDs) throws Exception;

   // public void ocb_update_hist(Map<String, Object> srchInDs ) throws Exception;

    public int  insertOcbpointcnt(Map<String, ?> pmParam) throws Exception;

    public int uploadIssueExcelList(DataSetMap pmInDsList, Map<String, Object> pmParam) throws Exception; // OCB,멤버쉽 카드발급신청 엑셀업로드

    public int  getIssueListCnt(Map<String, ?> pmParam) throws Exception; // OCB,멤버쉽 카드발급신청 카운트조회

    public List<Map<String, Object>> getIssueList (Map<String, ?> pmParam)  throws Exception; // OCB,멤버쉽 카드발급신청 조회

    public void saveConsRetrunCard(Map<String, Object> pmParam) throws Exception; // OCB,멤버쉽 카드 반송처리 상담기록

    public void delRequestIssue(Map<String, Object> pmParam) throws Exception; // OCB,멤버쉽 카드요청 삭제처리

    public int chkIssuingStat(Map<String, ?> pmParam) throws Exception; // 발급진행중인 회원 체크

    public void updateReturnDt(Map<String, Object> pmParam) throws Exception; // OCB,멤버쉽 카드 반송처리시 반송일자 UPDATE
}
