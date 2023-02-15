package powerservice.business.dlw.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

@Repository
public class DlwFullConsHistDAO extends EgovAbstractMapper {
	
	@Resource(name="dlwSqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }
	
	/** ================================================================
     * 날짜 : 20200619
     * 이름 : 송준빈
     * 추가내용 : CTI 사원 기본정보 조회수
     * 대상 테이블 : LF_DMUSER.TB_CTI_MAIN_INFO
     * ================================================================
     * */
    public int getFullConsHistCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwFullConsHistMap.getFullConsHistCount", pmParam);
    }

    /** ================================================================
     * 날짜 : 20200619
     * 이름 : 송준빈
     * 추가내용 : CTI 사원 기본정보 조회 리스트
     * 대상 테이블 : LF_DMUSER.TB_CTI_MAIN_INFO
     * ================================================================
     * */
    public List<Map<String, Object>> getFullConsHistList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwFullConsHistMap.getFullConsHistList", pmParam);
    }
}
