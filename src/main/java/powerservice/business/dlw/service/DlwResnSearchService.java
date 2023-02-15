/*
 * (@)# DlwResnSearchService.java
 */
package powerservice.business.dlw.service;

import java.util.List;
import java.util.Map;

public interface DlwResnSearchService {
	
	/* ================================================================
     * 날짜 : 20190521
     * 이름 : 김주용
     * 추가내용 : 해약 데이터 조회
     * ================================================================
     * */
	public List<Map<String, Object>> getResnSearchList(Map<String, ?> pmParam) throws Exception;
	
	/* ================================================================
     * 날짜 : 20190521
     * 이름 : 김주용
     * 추가내용 : 해약 데이터 검색 수 조회
     * ================================================================
     * */
	public int getResnSearchCount(Map<String, ?> pmParam) throws Exception;
}
