package powerservice.business.usr.service;

import java.util.List;
import java.util.Map;

public interface TeamService {

    public String insertTeam(Map<String, ?> pmParam) throws Exception;

    public int updateTeam(Map<String, ?> pmParam) throws Exception;

    public int getTeamCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getTeamList(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> getTeam(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getTeamTree(Map<String, ?> pmParam) throws Exception;

    public int getTeamMaxOrder(Map<String, ?> pmParam) throws Exception;

    public int getTeamDuplicateCount(Map<String, ?> pmParam) throws Exception;

}