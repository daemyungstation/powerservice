package powerservice.business.pds.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.pds.service.pdsPartService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service
public class pdsPartServiceImpl extends EgovAbstractServiceImpl implements pdsPartService{
	
	@Resource
	public pdsPartDao pdsPartDao;

	@Override
	public List<Map<String, Object>> testConn() {
		return pdsPartDao.testConn();
	}

	@Override
	public void connPds(Map map) {
		pdsPartDao.connPds(map);
	}

	@Override
	public List<Map<String, Object>> selectDataDF() {
		return pdsPartDao.selectDataDF();
	}
	
	@Override
	public List<Map<String, Object>> selectDataCD() {
		return pdsPartDao.selectDataCD();
	}
	
	@Override
	public List<Map<String, Object>> selectDataHC() {
		return pdsPartDao.selectDataHC();
	}
	
	@Override
	public List<Map<String, Object>> findResultRealData(Map<String, Object> pmParam) {
		return pdsPartDao.findResultRealData(pmParam);
	}
	
	@Override
	public List<Map<String, Object>> chkConnection() {
		return pdsPartDao.chkConnection();
	}
	
}










































