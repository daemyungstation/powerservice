package powerservice.business.dlw.service;

import java.util.List;
import java.util.Map;

import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

public interface DlwTvFormatService {

    public List<Map<String, Object>> selectTvFormatList(Map<String, Object> pmParam) throws Exception; //리스트 정보를 검색한다.

    public List<Map<String, Object>> selectCompaignList(Map<String, Object> pmParam) throws Exception; //캠페인 서브대상목록 정보를 검색한다.

    public Map<String, Object> selectTvFormatMstDetail(Map<String, Object> pmParam) throws Exception; //방송편성 MST 상세 조회

    public List<Map<String, Object>> selectTvFormatDtlDetail(Map<String, Object> pmParam) throws Exception; //방송편성 DTL 상세 조회

    public void saveTvFormatMst(Map<String, Object> pmParam, DataSetMap srchInDs2) throws Exception;	//방송편성 MST 등록/수정

    public void deleteTvFormatMst(DataSetMap srchInDs) throws Exception;	//쿠폰정보 삭제


}