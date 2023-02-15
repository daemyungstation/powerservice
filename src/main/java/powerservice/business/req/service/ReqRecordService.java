package powerservice.business.req.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;

public interface ReqRecordService {
	
	public int getRecBeforeCount(Map<String, ?> pmParam) throws Exception; // 회원별 녹취 확인 리스트 건수

    public List<Map<String, Object>> getRecBeforeList(Map<String, ?> pmParam) throws Exception; // 회원별 녹취 확인 리스트
}
