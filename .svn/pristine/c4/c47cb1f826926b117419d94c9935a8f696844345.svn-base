package powerservice.business.wdrw.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import powerservice.business.wdrw.service.WdrwResultMgmtService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service
public class WdrwResultMgmtServiceImpl extends EgovAbstractServiceImpl implements WdrwResultMgmtService {

	@Resource
    public WdrwResultMgmtDAO wdrwResultMgmtDAO;
	
	private final Logger log = LoggerFactory.getLogger(WdrwResultMgmtServiceImpl.class);
	
	/** ================================================================
     * 날짜 : 20200401
     * 이름 : 송준빈
     * 추가내용 : 고객청구결과관리 데이터 조회 수
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ, LF_DMUSER.TB_MEMBER_WDRW_RESULT, LF_DMUSER.TB_MEMBER_REQ_REFUND
     * ================================================================
     * */
    public int getWdrwResultMgmtCount(Map<String, ?> pmParam) throws Exception {
        return wdrwResultMgmtDAO.getWdrwResultMgmtCount(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20200401
     * 이름 : 송준빈
     * 추가내용 : 고객청구결과관리 데이터 조회 리스트
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ, LF_DMUSER.TB_MEMBER_WDRW_RESULT, LF_DMUSER.TB_MEMBER_REQ_REFUND
     * ================================================================
     * */
    public List<Map<String, Object>> getWdrwResultMgmtList(Map<String, ?> pmParam) throws Exception {
        return wdrwResultMgmtDAO.getWdrwResultMgmtList(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20200401
     * 이름 : 송준빈
     * 추가내용 : 고객청구결과관리 고객별 데이터 조회 리스트
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ, LF_DMUSER.TB_MEMBER_WDRW_RESULT, LF_DMUSER.TB_MEMBER_REQ_REFUND
     * ================================================================
     * */
    public List<Map<String, Object>> getWdrwAccntLogList(Map<String, ?> pmParam) throws Exception {
        return wdrwResultMgmtDAO.getWdrwAccntLogList(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20200401
     * 이름 : 송준빈
     * 추가내용 : 고객청구결과관리 고객 산출,청구 여부 확인
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ
     * ================================================================
     * */
    public List<Map<String, Object>> getWdrwNRConfirm(String sAccntNo) throws Exception {
        return wdrwResultMgmtDAO.getWdrwNRConfirm(sAccntNo);
    }
    
    /** ================================================================
     * 날짜 : 20200224
     * 이름 : 송준빈
     * 추가내용 : 가상계좌 정보 강제마감처리
     * 대상 테이블 : LF_DMUSER.TB_NICE_VRTL_ACCNT_INFO
     * ================================================================
     * */
    public int updateVrtlAccntRecoverProc(Map<String, ?> pmParam) throws Exception {
        return wdrwResultMgmtDAO.updateVrtlAccntRecoverProc(pmParam);
    }
}
