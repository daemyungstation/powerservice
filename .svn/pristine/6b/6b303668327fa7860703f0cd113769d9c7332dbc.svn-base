package powerservice.business.pds.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

@Repository
public class pdsDao extends EgovAbstractMapper{
	
	@Resource(name="DMLIFESqlSession")
	public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
	    super.setSqlSessionFactory(sqlSession);
	}

	public List<Map<String, Object>> testConn02() {
		return selectList("pdsMap.testConn02");
	}

	public List<Map<String, Object>> findData(Map<String, ?> hmParam) {
		return selectList("pdsMap.findData", hmParam);
	}

	public Map<String, Object> insertDF(Map<String, ?> srchInDs) {
		insert("pdsMap.insertDF", srchInDs);
		
		return (Map<String, Object>) srchInDs.get("reg_dm");
	}

	public Map<String, Object> insertCD(Map<String, ?> srchInDs) {
		insert("pdsMap.insertCD", srchInDs);
		
		return (Map<String, Object>) srchInDs.get("reg_dm");
	}

	public Map<String, Object> insertHC(Map<String, ?> srchInDs) {
		insert("pdsMap.insertHC", srchInDs);
		
		return (Map<String, Object>) srchInDs.get("reg_dm");
	}

	public void checkDelete(Map map) {
		update("pdsMap.checkDelete", map);
	}

	public List<Map<String, Object>> findResultData(Map<String, Object> findDataInfo) {
		return selectList("pdsMap.findResultData", findDataInfo);
	}

	public int getAllCount() {
		return (Integer) selectOne("pdsMap.getAllCount");
	}
	
	public void updateStatus(Map<String, Object> map) {
		update("pdsMap.updateStatus", map);
	}

	public int updateDataDF(Map<String, Object> map) {
		return update("pdsMap.updateDataDF", map);
	}
	
	public int updateDataCD(Map<String, Object> map) {
		return update("pdsMap.updateDataCD", map);
	}
	
	public int updateDataHC(Map<String, Object> map) {
		return update("pdsMap.updateDataHC", map);
	}
	
	public int updateAlct(Map<String, Object> map) {
		return update("pdsMap.updateAlct", map);
	}
	
	
	
	
	
	
	
	
	
	
}

































