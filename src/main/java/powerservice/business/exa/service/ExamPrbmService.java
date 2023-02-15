package powerservice.business.exa.service;

import java.util.List;
import java.util.Map;

import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;

public interface ExamPrbmService {

    public int getExamPrbmCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getExamPrbmList(Map<String, ?> pmParam) throws Exception;

    public String saveExamPrbm(Map <String, DataSetMap> mapInDs) throws Exception;

    public int updateExamPrbmExpeSqnc(List<Map<String, Object>> pmdataModels, Map<String, Object> pmParam) throws Exception;

    public Map<String, Object> getExamPrbm(String psId) throws Exception;

    public void deleteExamPrbm(Map<String, Object> pmParam) throws Exception;

    public List<Map<String, Object>> getExamPrbmAnsrList(Map<String, ?> pmParam) throws Exception;

    public int getExamPrbmMarkingCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getExamPrbmMarkingList(Map<String, ?> pmParam) throws Exception;

    public int getExamPrbmFieldResultCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getExamPrbmFieldResultList(Map<String, ?> pmParam) throws Exception;

    public int getExamPrbmResultCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getExamPrbmResultList(Map<String, ?> pmParam) throws Exception;

}