package powerservice.business.dlw.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

@Repository
public class DlwMangiMemberDAO extends EgovAbstractMapper {
	
	@Resource(name="dlwSqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }

	/** ================================================================
     * 날짜 : 20210215
     * 이름 : 송준빈
     * 추가내용 : 일일종합현황데이터 조회 조회건수
     * 대상 테이블 : LF_DMUSER.TB_MEM_ACCNT_ALTR_HIST
     * ================================================================
     * */
    public int getMangiMemberCount(Map<String, ?> pmParam) {
        return selectOne("DlwMangiMemberMap.getMangiMemberCount", pmParam);
    }

    /** ================================================================
     * 날짜 : 20210215
     * 이름 : 송준빈
     * 추가내용 : 일일종합현황데이터 조회 조회리스트
     * 대상 테이블 : LF_DMUSER.TB_MEM_ACCNT_ALTR_HIST
     * ================================================================
     * */
    public List<Map<String, Object>> getMangiMemberList(Map<String, ?> pmParam) {
        return selectList("DlwMangiMemberMap.getMangiMemberList", pmParam);
    }
    
	/** ================================================================
     * 날짜 : 20220105
     * 이름 : 임동진
     * 추가내용 : 만기방어를 위한 주간 만기현황 리스트
     * 대상 테이블 : LF_DMUSER.TB_CLOSE_MEMBER_MON
     * ================================================================
     * */
    public List<Map<String, Object>> getMangiLIst(Map<String, ?> pmParam) {
        return selectList("DlwMangiMemberMap.getMangiLIst", pmParam);
    }
    
	/** ================================================================
     * 날짜 : 20220105
     * 이름 : 임동진
     * 추가내용 : 만기방어를 위한 주간 만기현황 리스트 (금액)
     * 대상 테이블 : LF_DMUSER.TB_CLOSE_MEMBER_MON
     * ================================================================
     * */
    public List<Map<String, Object>> getMangiAmtLIst(Map<String, ?> pmParam) {
        return selectList("DlwMangiMemberMap.getMangiAmtLIst", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20220929
     * 이름 : 김주용
     * 추가내용 : 만기해약집계
     * 대상 테이블 : LF_DMUSER.TB_CLOSE_MEMBER_MON
     * ================================================================
     * */
    public List<Map<String, Object>> getMangiResnLIst1(Map<String, ?> pmParam) {
        return selectList("DlwMangiMemberMap.getMangiResnLIst1", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20220929
     * 이름 : 김주용
     * 추가내용 : 만기해약상세
     * 대상 테이블 : LF_DMUSER.TB_CLOSE_MEMBER_MON
     * ================================================================
     * */
    public List<Map<String, Object>> getMangiResnLIst2(Map<String, ?> pmParam) {
        return selectList("DlwMangiMemberMap.getMangiResnLIst2", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20220929
     * 이름 : 김주용
     * 추가내용 : 만기추정집계
     * 대상 테이블 : LF_DMUSER.TB_CLOSE_MEMBER_MON
     * ================================================================
     * */
    public List<Map<String, Object>> getMangiResnLIst3(Map<String, ?> pmParam) {
        return selectList("DlwMangiMemberMap.getMangiResnLIst3", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20220929
     * 이름 : 김주용
     * 추가내용 : 만기추정상세
     * 대상 테이블 : LF_DMUSER.TB_CLOSE_MEMBER_MON
     * ================================================================
     * */
    public List<Map<String, Object>> getMangiResnLIst4(Map<String, ?> pmParam) {
        return selectList("DlwMangiMemberMap.getMangiResnLIst4", pmParam);
    }
}
