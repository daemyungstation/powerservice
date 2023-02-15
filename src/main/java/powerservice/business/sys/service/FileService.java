package powerservice.business.sys.service;

import java.util.List;
import java.util.Map;

public interface FileService {

    public String insertFile(Map<String, ?> pmParam) throws Exception;

    public int deleteFile(String pmParam) throws Exception;

    public List<Map<String, Object>> getFileList(String psRelItemId) throws Exception;

    public List<Map<String, Object>> getFileList(Map<String,?> pmParam) throws Exception;

    public Map<String, Object> getFile(Map<String, ?> pmParam) throws Exception;

	public List<Map<String, Object>> getFileListWithBLOB(Map<String, Object> tempParam) throws Exception;
	
	public List<Map<String, Object>> getFileListAllByMap(Map<String,?> pmParam) throws Exception;
}