package powerservice.business.dlw.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import powerservice.business.dlw.service.DlwCashReceiptService;
import powerservice.business.dlw.service.DlwReadyCashService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service
public class DlwCashReceiptServiceImpl extends EgovAbstractServiceImpl implements DlwCashReceiptService {

	@Resource
    public DlwCashReceiptDAO dlwCashReceiptDAO;
	
	private final Logger log = LoggerFactory.getLogger(DlwCashReceiptServiceImpl.class);
	
	/** ================================================================
     * 날짜 : 20220726
     * 이름 : 김주용
     * 추가내용 : 현금영수증발행 조회 수
     * 대상 테이블 : LF_DMUSER.TB_CASH_RECEIPT
     * ================================================================
     * */
	public int getCashReceiptCount(Map<String, ?> pmParam) throws Exception {
		return dlwCashReceiptDAO.getCashReceiptCount(pmParam);
	}

	/** ================================================================
     * 날짜 : 20220726
     * 이름 : 김주용
     * 추가내용 : 현금영수증발행 조회 리스트
     * 대상 테이블 : LF_DMUSER.TB_CASH_RECEIPT
     * ================================================================
     * */
	public List<Map<String, Object>> getCashReceiptList(Map<String, ?> pmParam) throws Exception {
		return dlwCashReceiptDAO.getCashReceiptList(pmParam);
	}
	
	/** ================================================================
     * 날짜 : 20220726
     * 이름 : 김주용
     * 추가내용 : 현금영수증발행 산출 TB insert
     * 대상 테이블 : LF_DMUSER.TB_CASH_RECEIPT
     * ================================================================
     * */
    public int insertCashReceipt(Map<String, Object> pmParam) {
        return dlwCashReceiptDAO.insertCashReceipt(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20220726
     * 이름 : 김주용
     * 추가내용 : 현금영수증발행 산출 체크
     * 대상 테이블 : LF_DMUSER.TB_CASH_RECEIPT
     * ================================================================
     * */
	public int getCashReceiptCountChk(Map<String, ?> pmParam) throws Exception {
		return dlwCashReceiptDAO.getCashReceiptCountChk(pmParam);
	}
	
	/** ================================================================
     * 날짜 : 20220726
     * 이름 : 김주용
     * 추가내용 : 현금영수증발행 엑셀 전송 저장
     * 대상 테이블 : LF_DMUSER.TB_CASH_RECEIPT
     * ================================================================
     * */
    public int updateCashReceipt(Map<String, Object> pmParam) {
        return dlwCashReceiptDAO.updateCashReceipt(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20220726
     * 이름 : 김주용
     * 추가내용 : 현금영수증발행 삭제
     * 대상 테이블 : LF_DMUSER.TB_CASH_RECEIPT
     * ================================================================
     * */
    public int deleteCashReceipt(Map<String, Object> pmParam) {
        return dlwCashReceiptDAO.deleteCashReceipt(pmParam);
    }
}
