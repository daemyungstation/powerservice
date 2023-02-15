package powerservice.business.acn.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
@Repository
public interface AcnTranResultService {

    //전송월로 검색한 처리구분 결과 수
    public int getTranResulCount(Map<String, ?> pmParam) throws Exception;

    //전송월로 검색한 처리구분 결과 리스트
    public List<Map<String, Object>> getTranResultList(Map<String, ?> pmParam)  throws Exception;

  //전송월로 검색한 처리구분 결과 리스트
    public List<Map<String, Object>> getTranMonResultList(Map<String, ?> pmParam)  throws Exception;

    public int convertTranMonMainResult(DataSetMap dsm) throws Exception;

    public int processTranMonMainResult(Map<String, ?> pmParam) throws Exception;
    
    public List<Map<String, Object>> getacoyencheResultList(Map<String, ?> pmParam)  throws Exception;

}
