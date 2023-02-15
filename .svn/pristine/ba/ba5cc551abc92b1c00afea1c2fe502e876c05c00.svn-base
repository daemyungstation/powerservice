/*
 * (@)# DlwResnSearchDAO.java
 */
package powerservice.business.dlw.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

@Repository
public class DlwResnSearchDAO extends EgovAbstractMapper {
	
	/**
     * 대명라이프웨이 해약정보 DB SqlSession을 설정한다.
     *
     * @param sqlSession 대명라이프웨이 해약 DB SqlSession
     */
    @Resource(name="dlwSqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }
	/** ================================================================
     * 날짜 : 20190521
     * 이름 : 김주용
     * 추가내용 : 해약 데이터 조회
     * 대상 테이블 : LF_DMUSER.RESCISSION_DAMO
     * ================================================================
     * */
    public List<Map<String, Object>> getResnSearchList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwResnSearchMap.getResnSearchList", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20190521
     * 이름 : 김주용
     * 추가내용 : 해약 데이터 검색 수 조회
     * 대상 테이블 : LF_DMUSER.RESCISSION_DAMO
     * ================================================================
     * */
    public int getResnSearchCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwResnSearchMap.getResnSearchCount", pmParam);
    }
}
