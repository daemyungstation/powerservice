package powerservice.business.dlw.service;

import java.util.List;
import java.util.Map;

import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;

public interface DlwOcbListService {

    public List<Map<String, Object>> getOcbTreeList(Map<String, ?> pmParam) throws Exception; // 조직 트리 구조 조회

    public List<Map<String, Object>> getGrpEmpList(Map<String, ?> pmParam) throws Exception; // 조직 소속 사원 조회

    public int insertGrpDept(Map<String, ?> pmParam) throws Exception; // 조직도 입력

    public int updateGrpDept(Map<String, ?> pmParam) throws Exception; // 조직도 수정

    public int deleteGrpDept(Map<String, ?> pmParam) throws Exception; // 조직도 삭제

    public List<Map<String, Object>> getOcbAnnHist(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getOcbAnnCac(Map<String, ?> pmParam) throws Exception;

    public void  insertocbyearsave(Map<String, ?> mapInDs) throws Exception;

}