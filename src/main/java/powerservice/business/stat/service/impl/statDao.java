package powerservice.business.stat.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

@Repository
public class statDao extends EgovAbstractMapper{
	
	@Resource(name="gimSqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }

	public List<Map<String, Object>> selectInfoData() {
		return selectList("statMap.selectInfoData");
	}

	public List<Map<String, Object>> selectQueData() {
		return selectList("statMap.selectQueData");
	}
	
	
	
	
	
	
	
	
	
	
	
}






































