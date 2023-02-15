package powerservice.business.pds.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.pds.service.pdsService;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service
public class pdsServiceImpl extends EgovAbstractServiceImpl implements pdsService{
	
	@Resource
	public pdsDao pdsDao;

	@Override
	public List<Map<String, Object>> testConn02() {
		return pdsDao.testConn02();
	}

	@Override
	public List<Map<String, Object>> findData(Map<String, ?> hmParam) throws Exception {
		return pdsDao.findData(hmParam);
	}

	@Override
	public Map<String, Object> insertDF(Map<String, ?> srchInDs) {
		return pdsDao.insertDF(srchInDs);
	}

	@Override
	public Map<String, Object> insertCD(Map<String, ?> srchInDs) {
		return pdsDao.insertCD(srchInDs);
	}

	@Override
	public Map<String, Object> insertHC(Map<String, ?> srchInDs) {
		return pdsDao.insertHC(srchInDs);
	}

	@Override
	public void checkDelete(Map map) {
		pdsDao.checkDelete(map);
	}

	@Override
	public List<Map<String, Object>> findResultData(Map<String, Object> findDataInfo) throws Exception {
		return pdsDao.findResultData(findDataInfo);
	}

	@Override
	public int getAllCount() {
		return pdsDao.getAllCount();
	}
	
	@Override
	public void updateStatus(Map<String, Object> map) {
		pdsDao.updateStatus(map);
	}

	@Override
	public int updateDataDF(Map<String, Object> map) {
		return pdsDao.updateDataDF(map);
	}

	@Override
	public int updateDataCD(Map<String, Object> map) {
		return pdsDao.updateDataCD(map);
	}

	@Override
	public int updateDataHC(Map<String, Object> map) {
		return pdsDao.updateDataHC(map);
	}
	
	@Override
	public int updateAlct(Map<String, Object> map) {
		return pdsDao.updateAlct(map);
	}
	
	
	
	
	
	
	
	
}































