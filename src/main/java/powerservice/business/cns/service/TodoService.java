package powerservice.business.cns.service;

import java.util.Map;

public interface TodoService {

    public Map<String, Object> getTodoCount(Map<String, ?> pmParam) throws Exception;

}