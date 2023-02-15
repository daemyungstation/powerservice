package powerservice.business.evl.service;

import java.util.List;
import java.util.Map;

public interface EvltReslDtlService {

    /**
     * 평가결과(상담별) 정보의 검색 수를 반환한다.
     *
     * @param param 검색 조건
     * @return 평가결과 정보의 검색 수
     * @throws Exception
     */
    public int getEvltReslDtlCountByCons(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getEvltReslDtlListByCons(Map<String, ?> pmParam) throws Exception;

    public int getEvltReslDtlCount(Map<String, ?> param) throws Exception;

    public List<Map<String, Object>> getEvltReslDtlList(Map<String, ?> param) throws Exception;

    public Map<String, Object> getQaResultSheet(Map<String, ?> param) throws Exception;

    public Map<String, Object> getQaResultSheetId(String id) throws Exception;

    public String insertEvltReslDtl(Map<String, Object> pmParam) throws Exception;

    public int updateEvltReslDtl(Map<String, Object> pmParam) throws Exception;

}