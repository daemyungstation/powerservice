package powerservice.business.dlw.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

@Repository
public class DlwCashReceiptDAO extends EgovAbstractMapper {
	
	@Resource(name="dlwSqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }
	
	/** ================================================================
     * 날짜 : 20220726
     * 이름 : 김주용
     * 추가내용 : 현금영수증발행 조회 수
     * 대상 테이블 : LF_DMUSER.TB_CASH_RECEIPT
     * ================================================================
     * */
    public int getCashReceiptCount(Map<String, ?> pmParam) {
        return selectOne("DlwCashReceiptMap.getCashReceiptCount", pmParam);
    }

    /** ================================================================
     * 날짜 : 20220726
     * 이름 : 김주용
     * 추가내용 : 현금영수증발행 조회 리스트
     * 대상 테이블 : LF_DMUSER.TB_CASH_RECEIPT
     * ================================================================
     * */
    public List<Map<String, Object>> getCashReceiptList(Map<String, ?> pmParam) throws Exception {
        return selectList("DlwCashReceiptMap.getCashReceiptList", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20220726
     * 이름 : 김주용
     * 추가내용 : 현금영수증발행 산출 TB insert
     * 대상 테이블 : LF_DMUSER.TB_CASH_RECEIPT
     * ================================================================
     * */
    public int insertCashReceipt(Map<String, Object> pmParam) {
        return insert("DlwCashReceiptMap.insertCashReceipt", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20220726
     * 이름 : 김주용
     * 추가내용 : 현금영수증발행 산출 체크
     * 대상 테이블 : LF_DMUSER.TB_CASH_RECEIPT
     * ================================================================
     * */
    public int getCashReceiptCountChk(Map<String, ?> pmParam) {
        return selectOne("DlwCashReceiptMap.getCashReceiptCountChk", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20220726
     * 이름 : 김주용
     * 추가내용 : 현금영수증발행 엑셀 전송 저장
     * 대상 테이블 : LF_DMUSER.TB_CASH_RECEIPT
     * ================================================================
     * */
    public int updateCashReceipt(Map<String, Object> pmParam) {
        return insert("DlwCashReceiptMap.updateCashReceipt", pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20220726
     * 이름 : 김주용
     * 추가내용 : 현금영수증발행 삭제
     * 대상 테이블 : LF_DMUSER.TB_CASH_RECEIPT
     * ================================================================
     * */
    public int deleteCashReceipt(Map<String, Object> pmParam) {
        return insert("DlwCashReceiptMap.deleteCashReceipt", pmParam);
    }
}
