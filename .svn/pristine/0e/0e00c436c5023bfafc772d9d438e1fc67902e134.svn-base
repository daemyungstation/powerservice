package powerservice.business.dlw.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

@Repository
public class DlwTermMangiInfoDAO extends EgovAbstractMapper {
	
	@Resource(name="dlwSqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }
	
	/** ================================================================
     * 날짜 : 20210623
     * 이름 : 송준빈
     * 추가내용 : 기간별 만기정보 조회수
     * 대상 테이블 : DUAL
     * ================================================================
     * */
    public int getTermMangiCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwTermMangiInfoMap.getTermMangiCount", pmParam);
    }

    /** ================================================================
     * 날짜 : 20210623
     * 이름 : 송준빈
     * 추가내용 : 기간별 만기정보 조회 리스트
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_MANGI_EXT
     * ================================================================
     * */
    public List<Map<String, Object>> getTermMangiList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwTermMangiInfoMap.getTermMangiList", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20210715
     * 이름 : 송준빈
     * 추가내용 : 기간별 만기고객 현황 상세 조회수
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_MANGI_EXT
     * ================================================================
     * */
    public int getMangiAccntInfoDtlCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwTermMangiInfoMap.getMangiAccntInfoDtlCount", pmParam);
    }

    /** ================================================================
     * 날짜 : 20210715
     * 이름 : 송준빈
     * 추가내용 : 기간별 만기고객 현황 상세 조회 리스트
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_MANGI_EXT
     * ================================================================
     * */
    public List<Map<String, Object>> getMangiAccntInfoDtlList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwTermMangiInfoMap.getMangiAccntInfoDtlList", pmParam);
    }
}
