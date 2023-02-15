package powerservice.business.onln.service;

import java.util.List;
import java.util.Map;


public interface DlwOnlnPymnAcntService {

    public int getOnlnPymnAcntChngCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getOnlnPymnAcntChngList(Map<String, ?> pmParam) throws Exception;

    public int updateOnlnTrntCmplAcntChng(Map<String, ?> pmParam) throws Exception;

    public int updateOnlnTrntCmplMemChng(Map<String, ?> pmParam) throws Exception;
}