package powerservice.business.pds.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

@Repository
public class pdsPartDao extends EgovAbstractMapper{
	
	@Resource(name="gocsSqlSession")
	public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
	    super.setSqlSessionFactory(sqlSession);
	}

	public List<Map<String, Object>> testConn() {
		return selectList("pdsPartMap.testConn");
	}

	public void connPds(Map map) {
		insert("pdsPartMap.connPds", map);
	}

	public List<Map<String, Object>> selectDataDF() {
		return selectList("pdsPartMap.selectDataDF");
	}
	
	public List<Map<String, Object>> selectDataCD() {
		return selectList("pdsPartMap.selectDataCD");
	}
	
	public List<Map<String, Object>> selectDataHC() {
		return selectList("pdsPartMap.selectDataHC");
	}
	
	public List<Map<String, Object>> findResultRealData(Map<String, Object> pmParam) {
		return selectList("pdsPartMap.findResultRealData", pmParam);
	}
	
	public List<Map<String, Object>> chkConnection() {
		return selectList("pdsPartMap.chkConnection");
	}
	
	
}







































