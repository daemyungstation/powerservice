package powerservice.business.evl.service;

import java.util.List;
import java.util.Map;

public interface EvltItemBankService {

    public int getEvltItemBankCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getEvltItemBankList(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> getEvltItemBank(String psId) throws Exception;

    public String insertEvltItemBank(Map<String, Object> pmParam) throws Exception;

    public int updateEvltItemBank(Map<String, Object> pmParam) throws Exception;

    public void deleteEvltItemBank(Map<String, Object> pmParam) throws Exception;

    public int insertCopyEvltItemBank(Map<String, ?> pmParam) throws Exception;

}