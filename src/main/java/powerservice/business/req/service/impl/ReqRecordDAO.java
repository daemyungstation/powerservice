package powerservice.business.req.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

@Repository
public class ReqRecordDAO extends EgovAbstractMapper {
	
	/**
     * DB SqlSession을 설정한다.
     *
     * @param sqlSession DB SqlSession
     */
    @Resource(name="reqSqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }
    
    /**
     * 회원별 녹취 확인 리스트 건수
     *
     * @param pmParam 검색 조건
     * @return 회원별 녹취 확인 리스트 건수
     * @throws Exception
     */
    public int getRecBeforeCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("ReqRecordMap.getRecBeforeCount", pmParam);
    }

    /**
     * 회원별 녹취 확인 리스트
     *
     * @param pmParam 검색 조건
     * @return 회원별 녹취 확인 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getRecBeforeList(Map<String, ?> pmParam) throws Exception {
        return selectList("ReqRecordMap.getRecBeforeList", pmParam);
    }
}
