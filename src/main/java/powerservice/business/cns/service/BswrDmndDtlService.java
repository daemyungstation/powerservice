package powerservice.business.cns.service;

import java.util.List;
import java.util.Map;

public interface BswrDmndDtlService {

    public int insertBswrDmndDtl(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> getDpmsReslDsps(Map<String, ?> pmParam) throws Exception;

    public int getTrctConsCount(Map<String, ?> pmParam) throws Exception;

    public int getTrctConsHstrCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getTrctConsList(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getTrctConsHstr(Map<String, ?> pmParam) throws Exception;

    public int saveBswrDmndDsps(Map<String, Object> pmParam) throws Exception;

    public Map<String, Object> getTrctCons(Map<String, ?> pmParam) throws Exception;

    public int deleteBswrDmnd(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getTrctConsChartWeeksList(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> checkTrctChpr(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getTodoConsTrctHstrCount() throws Exception;

    public int getTrctConsStatCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getTrctConsStatList(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getConsTrctBoxChprCount() throws Exception;

}