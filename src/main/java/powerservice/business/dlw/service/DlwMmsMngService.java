package powerservice.business.dlw.service;

import java.util.List;
import java.util.Map;

import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

public interface DlwMmsMngService {

    public List<Map<String, Object>> selectMMSList(Map<String, Object> pmParam) throws Exception; //상품별 부가서비스 정보를 검색한다.

}