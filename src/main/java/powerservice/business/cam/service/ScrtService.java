package powerservice.business.cam.service;

import java.util.List;
import java.util.Map;

import powerservice.core.bean.ActionResult;

public interface ScrtService {

    public int getScrtCount(Map<String, ?> pmParam) throws Exception;

    public List<Map<String, Object>> getScrtList(Map<String, ?> pmParam) throws Exception;

    public Map<String, Object> getScrtView(Map<String, ?> pmParam) throws Exception;

    public String insertScrt(Map<String, Object> pmParam) throws Exception;

    public int updateScrt(Map<String, Object> pmParam) throws Exception;

    public Map<String, Object> getScrt(String id) throws Exception;

    public ActionResult deleteScrt(Map<String, Object> pmParam) throws Exception;

    public Map<String, Object> getScrtPreview(Map<String, ?> pmParam) throws Exception;

    public int updateFile(Map<String, Object> pmParam) throws Exception;

}