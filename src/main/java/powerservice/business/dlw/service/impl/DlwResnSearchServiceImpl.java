/*
 * (@)# DlwResnSearchServiceImpl.java
 */
package powerservice.business.dlw.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.dlw.service.DlwResnSearchService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service
public class DlwResnSearchServiceImpl extends EgovAbstractServiceImpl implements DlwResnSearchService{
	
	@Resource
	public DlwResnSearchDAO dlwResnSearchDAO;
	
	/**
     * 해약현황 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 해약현황 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getResnSearchList(Map<String, ?> pmParam) throws Exception {
        return dlwResnSearchDAO.getResnSearchList(pmParam);
    }
    
    /**
     * 해약현황 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 해약현황 정보의 검색 건수
     * @throws Exception
     */
    public int getResnSearchCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) dlwResnSearchDAO.getResnSearchCount(pmParam);
    }
}
