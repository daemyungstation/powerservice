package powerservice.business.mall.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

@Repository
public class DlwMallLFLinkedDAO extends EgovAbstractMapper {
	
	@Resource(name="sqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }
	
	/* ================================================================
     * 날짜 : 20200128
     * 이름 : 김주용
     * 추가내용 : 쇼핑몰 선행 조건 조회
     * ================================================================
     * */
	public List<Map<String, Object>> getMemberBasicYn(Map<String, ?> pmParam) {
		return selectList("DlwMallLFLinkedMap.getMemberBasicYn", pmParam);
	}
	
	/* ================================================================
     * 날짜 : 20171221
     * 이름 : 송준빈
     * 추가내용 : 쇼핑몰적립금사용금상세조회 상태값 변경
     * ================================================================
     * */
	public int updateLFResnMemberState(Map<String, ?> pmParam) {
		return update("DlwMallLFLinkedMap.updateLFResnMemberState", pmParam);
	}
	
	/* ================================================================
     * 날짜 : 20171221
     * 이름 : 송준빈
     * 추가내용 : 쇼핑몰적립금사용금상세조회 행사여부 확인
     * ================================================================
     * */
	public List<Map<String, Object>> getResnMemberStateEvent(Map<String, ?> pmParam) {
		return selectList("DlwMallLFLinkedMap.getResnMemberStateEvent", pmParam);
	}

	/* ================================================================
     * 날짜 : 20171221
     * 이름 : 송준빈
     * 추가내용 : 쇼핑몰적립금사용금상세조회 해약여부 확인
     * ================================================================
     * */
	public List<Map<String, Object>> getResnMemberStateRescission(Map<String, ?> pmParam) {
		return selectList("DlwMallLFLinkedMap.getResnMemberStateRescission", pmParam);
	}
	
	/**
     * 해약 구분(해약/청약)
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getResnGubn(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwMallLFLinkedMap.getResnGubn", pmParam);
    }
    
    /**********************************************************************************************************/
	public int updateOptSvc(Map<String, Object> pmParam) throws Exception {
        return update("DlwMallLFLinkedMap.updateOptSvc", pmParam);
    }

	public String selectCrtNoPerDaySearch(Map<String, Object> pmParam) throws Exception {
        return selectOne("DlwMallLFLinkedMap.selectCrtNoPerDaySearch", pmParam);
    }

	public int insertCouponNoPerDayYMS(Map<String, Object> pmParam) throws Exception {
        return insert("DlwMallLFLinkedMap.insertCouponNoPerDayYMS", pmParam);
    }

	public int updateOtherCrtNoPerDay1(Map<String, Object> pmParam) throws Exception {
        return update("DlwMallLFLinkedMap.updateOtherCrtNoPerDay1", pmParam);
    }

	public String selectCrtNoSearch(Map<String, Object> pmParam) throws Exception {
        return selectOne("DlwMallLFLinkedMap.selectCrtNoSearch", pmParam);
    }

	public int insertCouponNoYMS(Map<String, Object> pmParam) throws Exception {
        return insert("DlwMallLFLinkedMap.insertCouponNoYMS", pmParam);
    }

	public int updateOtherCrtNo1(Map<String, Object> pmParam) throws Exception {
        return update("DlwMallLFLinkedMap.updateOtherCrtNo1", pmParam);
    }

	public int insertOptSvc(Map<String, Object> pmParam) throws Exception {
        return insert("DlwMallLFLinkedMap.insertOptSvc", pmParam);
    }
	/**********************************************************************************************************/
	
	/* ================================================================
     * 날짜 : 20200130
     * 이름 : 김주용
     * 추가내용 : 2-2. MEMBER_SHOPPING(대명라이프 쇼핑몰고객 임시테이블)을 삭제한다.
     * ================================================================
     * */
	public int deleteMemberShoppng(Map<String, ?> pmParam) {
		return delete("DlwMallLFLinkedMap.deleteMemberShoppng", pmParam);
	}
	
	public List<Map<String, Object>> selectMemberShoppng(Map<String, ?> pmParam) {
		return selectList("DlwMallLFLinkedMap.selectMemberShoppng", pmParam);
	}
	
	public int insertMemberShoppng(Map<String, ?> pmParam) throws Exception {
        return insert("DlwMallLFLinkedMap.insertMemberShoppng", pmParam);
    }
	
	public int updateMemberShopping1(Map<String, ?> pmParam) throws Exception {
        return update("DlwMallLFLinkedMap.updateMemberShopping1", pmParam);
    }
	
	public int updateMemberShopping2(Map<String, ?> pmParam) throws Exception {
        return update("DlwMallLFLinkedMap.updateMemberShopping2", pmParam);
    }
	
	public List<Map<String, Object>> selectAllMemberShoppng(Map<String, ?> pmParam) {
		return selectList("DlwMallLFLinkedMap.selectAllMemberShoppng", pmParam);
	}
	
	public int deleteMemberShoppngHistory(Map<String, ?> pmParam) {
		return delete("DlwMallLFLinkedMap.deleteMemberShoppngHistory", pmParam);
	}
	
	public int insertMemberShoppngHistory(Map<String, ?> pmParam) throws Exception {
        return insert("DlwMallLFLinkedMap.insertMemberShoppngHistory", pmParam);
    }
	
	public List<Map<String, Object>> selectAllMemberShoppngHistory(Map<String, ?> pmParam) {
		return selectList("DlwMallLFLinkedMap.selectAllMemberShoppngHistory", pmParam);
	}
	
	public int deleteCashShoppingHistory(Map<String, ?> pmParam) {
		return delete("DlwMallLFLinkedMap.deleteCashShoppingHistory", pmParam);
	}
	
	public int insertCashShoppngHistory(Map<String, ?> pmParam) throws Exception {
        return insert("DlwMallLFLinkedMap.insertCashShoppngHistory", pmParam);
    }
	
	public List<Map<String, Object>> selectAllCashShoppngHistory(Map<String, ?> pmParam) {
		return selectList("DlwMallLFLinkedMap.selectAllCashShoppngHistory", pmParam);
	}
	
	public List<Map<String, Object>> selectMember(Map<String, ?> pmParam) {
		return selectList("DlwMallLFLinkedMap.selectMember", pmParam);
	}
	
	public int getMemberCiChk(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("DlwMallLFLinkedMap.getMemberCiChk", pmParam);
    }
}
