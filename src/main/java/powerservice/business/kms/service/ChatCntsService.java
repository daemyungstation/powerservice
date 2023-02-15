package powerservice.business.kms.service;

import java.util.List;
import java.util.Map;

public interface ChatCntsService {

    public int getChatCntsCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getChatCntsList(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> getChatCnts(Map<String, ?> pmParam) throws Exception;

    public String insertChatCnts(Map<String, Object> pmParam) throws Exception;

    public int updateChatCnts(Map<String, Object> pmParam) throws Exception;

    public int deleteChatCnts(Map<String, ?> pmParam) throws Exception;

    public int updateFile(Map<String, Object> pmParam) throws Exception;
    
    public int getcjChatCntsCount(Map<String, ?> pmParam) throws Exception;
    
    public List<Map<String, Object>> getcjChatCntsList(Map<String, ?> pmParam) throws Exception;

}