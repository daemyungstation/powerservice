package powerservice.business.dlw.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

@Repository
public class DlwReadyCashDAO extends EgovAbstractMapper {
	
	@Resource(name="dlwSqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }
	
	/** ================================================================
     * 날짜 : 20190509
     * 이름 : 송준빈
     * 추가내용 : 회원별 레디캐쉬 조회 수
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_DLWMALL
     * ================================================================
     * */
    public int getMemberDlwmallCount(Map<String, ?> pmParam) {
        return selectOne("DlwReadyCashMap.getMemberDlwmallCount", pmParam);
    }

    /** ================================================================
     * 날짜 : 20190509
     * 이름 : 송준빈
     * 추가내용 : 회원별 레디캐쉬 조회 리스트
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_DLWMALL
     * ================================================================
     * */
    public List<Map<String, Object>> getMemberDlwmallList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwReadyCashMap.getMemberDlwmallList", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20190509
     * 이름 : 송준빈
     * 추가내용 : 회원별 레디캐쉬 조회 집계
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_DLWMALL
     * ================================================================
     * */
    public List<Map<String, Object>> getMemberDlwmallSummary(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwReadyCashMap.getMemberDlwmallSummary", pmParam);
    }
    
}
