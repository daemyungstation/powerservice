package powerservice.business.dlw.service;

import java.util.List;
import java.util.Map;

import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

public interface DlwHsResultService {

    public List<Map<String, Object>> selectHsResultList(Map<String, Object> pmParam) throws Exception; //홈쇼핑 실적현황 정보를 검색한다.

    public List<Map<String, Object>> selectHsProdList(Map<String, Object> pmParam) throws Exception; //홈쇼핑 상품현황 정보를 검색한다.



}