package powerservice.business.stat.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import powerservice.business.stat.service.statService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service
public class statServiceImpl extends EgovAbstractServiceImpl implements statService{
	
	@Resource
	public statDao statDao;

	@Override
	public List<Map<String, Object>> selectInfoData() {
		return statDao.selectInfoData();
	}

	@Override
	public List<Map<String, Object>> selectQueData() {
		return statDao.selectQueData();
	}
	
	
	
	
	
	
}


































