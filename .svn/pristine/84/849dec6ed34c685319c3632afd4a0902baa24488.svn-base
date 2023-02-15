package powerservice.business.dlw.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * 대명라이프웨이 회원정보를 관리한다.
 *
 * @author 정출연
 * @version 1.0
 * @date 2016/09/14
 * @프로그램ID DlwMemDAO
 */
@Repository
public class DlwMemDAO extends EgovAbstractMapper {

    /**
     * 대명라이프웨이 DB SqlSession을 설정한다.
     *
     * @param sqlSession 대명라이프웨이 DB SqlSession
     */
    @Resource(name="dlwSqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }

  /**
   * 회원 NICE 세이프키 변경
   *
   * @param pmParam 검색 조건
   * @return
   * @throws Exception
   */
    public int updateNiceSafekey(Map<String, ?> pmParam) throws Exception {
    	return insert("DlwMemMap.updateNiceSafekey", pmParam);
    }
    
    public int insertJcyTest1(List<Map<String, Object>> pmParam) throws Exception {
        return insert("DlwMemMap.insertJcyTest1", pmParam);
    }

}
