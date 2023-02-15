package powerservice.business.dlw.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

@Repository
public class DlwAltrHistDAO extends EgovAbstractMapper {
	
	@Resource(name="dlwSqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }
    
	/** ================================================================
     * 날짜 : 20210215
     * 이름 : 송준빈
     * 추가내용 : 일일종합현황데이터 처리일자 등록
     * 대상 테이블 : LF_DMUSER.TB_MEM_ACCNT_ALTR_HIST
     * ================================================================
     * */
    public int insertMemAccntAltrHistList(Map<String, ?> pmParam) throws Exception {
        return insert("DlwAltrHistMap.insertMemAccntAltrHistList", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20210215
     * 이름 : 송준빈
     * 추가내용 : 일일종합현황데이터 처리일자 조회
     * 대상 테이블 : LF_DMUSER.TB_MEM_ACCNT_ALTR_HIST
     * ================================================================
     * */
    public List<Map<String, Object>> getMemAccntAltrHistConsList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwAltrHistMap.getMemAccntAltrHistConsList", pmParam);
    }
    
	/** ================================================================
     * 날짜 : 20210215
     * 이름 : 송준빈
     * 추가내용 : 일일종합현황데이터 조회 조회건수
     * 대상 테이블 : LF_DMUSER.TB_MEM_ACCNT_ALTR_HIST
     * ================================================================
     * */
    public int getMemAccntAltrHistCount(Map<String, ?> pmParam) {
        return selectOne("DlwAltrHistMap.getMemAccntAltrHistCount", pmParam);
    }

    /** ================================================================
     * 날짜 : 20210215
     * 이름 : 송준빈
     * 추가내용 : 일일종합현황데이터 조회 조회리스트
     * 대상 테이블 : LF_DMUSER.TB_MEM_ACCNT_ALTR_HIST
     * ================================================================
     * */
    public List<Map<String, Object>> getMemAccntAltrHistList(Map<String, ?> pmParam) {
        return selectList("DlwAltrHistMap.getMemAccntAltrHistList", pmParam);
    }
}
