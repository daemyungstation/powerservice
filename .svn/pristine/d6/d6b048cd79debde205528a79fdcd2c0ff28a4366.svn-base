package powerservice.business.dlw.service;

import java.util.List;
import java.util.Map;

import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

public interface DlwPayCloseService {

    public List<Map<String, Object>> selectPayCloseList(Map<String, Object> pmParam) throws Exception; //상품별 부가서비스 정보를 검색한다.

    public void execPayDlTotProc(Map<String, Object> pmParam) throws Exception; //전체 입금마감 데이타 수동생성

    public void execPayDlTotCancelProc(Map<String, Object> pmParam) throws Exception; //전체 마감 해지 처리

    public String selectDupChk(Map<String, Object> pmParam) throws Exception; //기준년월 중복 체크


}