package powerservice.business.dlw.service;

import java.util.List;
import java.util.Map;

import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

public interface DlwProdVasService {

    public List<Map<String, Object>> selectProdOptSvcMstList(Map<String, Object> pmParam) throws Exception; //상품별 부가서비스 정보를 검색한다.

    public List<Map<String, Object>> selectProdList(Map<String, Object> pmParam) throws Exception;	//상품코드 콤보 검색한다.

    public List<Map<String, Object>> selectOptSvcList(Map<String, Object> pmParam) throws Exception; //부가서비스 조회

    public List<Map<String, Object>> selectProdOptSvcDtl(Map<String, Object> pmParam) throws Exception; //상품별 부가서비스 DTL 상세조회

    public String saveProdOptSvc(DataSetMap srchInDs1, DataSetMap srchInDs2) throws Exception;	//상품별 부가서비 등록/수정

    public void deleteProdOptSvc(Map<String, Object> pmParam) throws Exception;	//상품별 부가서비 삭제

    public String saveOptSvcList(DataSetMap srchInDs) throws Exception;	//상품권 종류 구분 저장

    public Map<String, Object> selectDetailVas(Map<String, Object> pmParam) throws Exception;

}