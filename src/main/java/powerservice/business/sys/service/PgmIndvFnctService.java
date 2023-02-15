package powerservice.business.sys.service;

import java.util.List;
import java.util.Map;

public interface PgmIndvFnctService {

    public List<Map<String, Object>> getPgmIndvFnctList(Map<String, ?> pmParam) throws Exception;

    public int getPgmIndvFnctCount(Map<String, ?> pmParam) throws Exception;

    public String insertPgmIndvFnct(Map<String, ?> pmParam) throws Exception;

    public int updatePgmIndvFnct(Map<String, ?> pmParam) throws Exception;

    public int deletePgmIndvFnct(Map<String, ?> pmParam) throws Exception;


}