package powerservice.business.sys.service;

import java.util.List;
import java.util.Map;


public interface PgmIndvFnctAthrService {

    public List<Map<String, Object>> getPgmIndvFnctAthrAddList(Map<String, ?> pmParam) throws Exception;

    public int insertPgmIndvFnctAthr(Map<String, Object> pmParam) throws Exception;

    public List<Map<String, Object>> getPgmIndvFnctAthrList(Map<String, ?> pmParam) throws Exception;

    public int deletePgmIndvFnctAthr(Map<String, ?> pmParam) throws Exception;

    public int updatePgmIndvFnctAthr(Map<String, Object> pmParam) throws Exception;




  /*  public int getPgmIndvFnctAthrCount(Map<String, ?> pmParam) throws Exception;

    public int updatePgmIndvFnctAthr(Map<String, Object> pmParam) throws Exception;

    ;*/

}