package powerservice.business.stat.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

@Repository
public class ctiDao extends EgovAbstractMapper{
	
	@Resource(name="DMLIFESqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }

	public void insertinfoData(Map<String, Object> map) {
		update("ctiMap.insertinfoData", map);
	}

	public void insertQueData(Map<String, Object> map) {
		update("ctiMap.insertQueData", map);
	}
	
	
	
	
	
	
}





































