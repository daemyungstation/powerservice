package powerservice.business.mall.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

@Repository
public class DlwMallMngDAO extends EgovAbstractMapper {
	
	@Resource(name="mallSqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }
	
	/* ================================================================
     * 날짜 : 20220617
     * 이름 : 김주용
     * 추가내용 : 쇼핑몰회원조회
     * ================================================================
     * */
	public int getMallMemberCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwMallMngMap.getMallMemberCount", pmParam);
    }
	
 	public List<Map<String, Object>> getMallMemberList(Map<String, ?> pmParam) {
		return selectList("DlwMallMngMap.getMallMemberList", pmParam);
	}
 	
 	/* ================================================================
     * 날짜 : 20220617
     * 이름 : 김주용
     * 추가내용 : 레디캐시이력조회
     * ================================================================
     * */
	public int getMallCashHistroyCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwMallMngMap.getMallCashHistroyCount", pmParam);
    }
	
 	public List<Map<String, Object>> getMallCashHistroyList(Map<String, ?> pmParam) {
		return selectList("DlwMallMngMap.getMallCashHistroyList", pmParam);
	}
 	
 	/* ================================================================
     * 날짜 : 20220617
     * 이름 : 김주용
     * 추가내용 : 쇼핑몰회원이력조회
     * ================================================================
     * */
	public int getMallMemHistroyCount(Map<String, ?> pmParam) throws Exception {
        return selectOne("DlwMallMngMap.getMallMemHistroyCount", pmParam);
    }
	
 	public List<Map<String, Object>> getMallMemHistroyList(Map<String, ?> pmParam) {
		return selectList("DlwMallMngMap.getMallMemHistroyList", pmParam);
	}
 	
 	/* ================================================================
     * 날짜 : 20220627
     * 이름 : 김주용
     * 추가내용 : 쇼핑몰회원수정
     * ================================================================
     * */
    public int updateMallMember(Map<String, ?> pmParam) throws Exception {
        return update("DlwMallMngMap.updateMallMember", pmParam);
    }
    
    /* ================================================================
     * 날짜 : 20220627
     * 이름 : 김주용
     * 추가내용 : 쇼핑몰회원수정이력등록
     * ================================================================
     * */
    public int insertMallMemberHistory(Map<String, ?> pmParam) throws Exception {
        return update("DlwMallMngMap.insertMallMemberHistory", pmParam);
    }
}
