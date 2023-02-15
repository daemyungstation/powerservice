package powerservice.business.mall.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

@Repository
public class DlwMallLinkedDAO extends EgovAbstractMapper {
	
	@Resource(name="mallSqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }
	
	/* ================================================================
     * 날짜 : 20171221
     * 이름 : 송준빈
     * 추가내용 : 쇼핑몰적립금사용금상세조회 기본정보 조회
     * ================================================================
     * */
	public List<Map<String, Object>> getMemberBasicList(Map<String, ?> pmParam) {
		return selectList("DlwMallLinkedMap.getMemberBasicList", pmParam);
	}

	/* ================================================================
     * 날짜 : 20171221
     * 이름 : 송준빈
     * 추가내용 : 쇼핑몰적립금사용금상세조회 기본정보 사용/취소금액 조회
     * ================================================================
     * */
	public List<Map<String, Object>> getMemberBasicListUseAmt(Map<String, ?> pmParam) {
		return selectList("DlwMallLinkedMap.getMemberBasicListUseAmt", pmParam);
	}

	/* ================================================================
     * 날짜 : 20171221
     * 이름 : 송준빈
     * 추가내용 : 쇼핑몰적립금사용금상세조회 상태값 변경
     * ================================================================
     * */
	public int updateResnMemberState(Map<String, ?> pmParam) {
		return update("DlwMallLinkedMap.updateResnMemberState", pmParam);
	}

	/* ================================================================
     * 날짜 : 20171221
     * 이름 : 송준빈
     * 추가내용 : 쇼핑몰적립금사용금상세조회 적립금 전송
     * ================================================================
     * */
	public int insertMemberBasicAmt(Map<String, ?> pmParam) {
		return insert("DlwMallLinkedMap.insertMemberBasicAmt", pmParam);
	}

	/**
     * 해약 구분(해약/청약)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getMallUseAmt(String sAccnt_no) throws Exception {
        return (Integer) selectOne("DlwMallLinkedMap.getMallUseAmt", sAccnt_no);
    }    
    
	public int updateResnMallState(Map<String, ?> pmParam) throws Exception {
        return update("DlwMallLinkedMap.updateResnMallState", pmParam);
    }
	
	
	public int getMallMemberCnt(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwMallLinkedMap.getMallMemberCnt", pmParam);
    }
	
	public int selectMaxMemberid(String sAccnt_no) throws Exception {
        return (Integer) selectOne("DlwMallLinkedMap.selectMaxMemberid", sAccnt_no);
    }
	
	public int insertMember(Map<String, ?> pmParam) {
		return insert("DlwMallLinkedMap.insertMember", pmParam);
	}
	
	public int insertMemberHistory(Map<String, ?> pmParam) {
		return insert("DlwMallLinkedMap.insertMemberHistory", pmParam);
	}
	
	public int insertCashHistory(Map<String, ?> pmParam) {
		return insert("DlwMallLinkedMap.insertCashHistory", pmParam);
	}
	
	public int getNewExistAllFlagCnt(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwMallLinkedMap.getNewExistAllFlagCnt", pmParam);
    }
	
	public int insertMemberAll(Map<String, ?> pmParam) {
		return insert("DlwMallLinkedMap.insertMemberAll", pmParam);
	}
}
