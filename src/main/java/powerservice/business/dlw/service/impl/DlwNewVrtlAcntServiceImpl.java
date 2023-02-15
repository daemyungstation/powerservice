package powerservice.business.dlw.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import powerservice.business.dlw.service.DlwNewVrtlAcntService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service
public class DlwNewVrtlAcntServiceImpl extends EgovAbstractServiceImpl implements DlwNewVrtlAcntService {

	@Resource
    public DlwNewVrtlAcntDAO dlwNewVrtlAcntDAO;
	
	private final Logger log = LoggerFactory.getLogger(DlwNewVrtlAcntServiceImpl.class);
	
	/** ================================================================
     * 날짜 : 20200224
     * 이름 : 송준빈
     * 추가내용 : 가상계좌 정보 조회수
     * 대상 테이블 : LF_DMUSER.TB_NICE_VRTL_ACCNT_INFO
     * ================================================================
     * */
    public int getVrtlAccntInfoCount(Map<String, ?> pmParam) throws Exception {
        return dlwNewVrtlAcntDAO.getVrtlAccntInfoCount(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20200224
     * 이름 : 송준빈
     * 추가내용 : 가상계좌 정보 조회 리스트
     * 대상 테이블 : LF_DMUSER.TB_NICE_VRTL_ACCNT_INFO
     * ================================================================
     * */
    public List<Map<String, Object>> getVrtlAccntInfo(Map<String, ?> pmParam) throws Exception {
        return dlwNewVrtlAcntDAO.getVrtlAccntInfo(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20200224
     * 이름 : 송준빈
     * 추가내용 : 가상계좌 정보 강제마감처리
     * 대상 테이블 : LF_DMUSER.TB_NICE_VRTL_ACCNT_INFO
     * ================================================================
     * */
    public int updateVrtlAccntRecoverProc(Map<String, ?> pmParam) throws Exception {
        return dlwNewVrtlAcntDAO.updateVrtlAccntRecoverProc(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20200224
     * 이름 : 송준빈
     * 추가내용 : 가상계좌번호에 고객 고유번호를 부여하기 전 등록여부 확인
     * 대상 테이블 : LF_DMUSER.TB_NICE_VRTL_ACCNT_INFO
     * ================================================================
     * */
	public int getMemNoRegistCnt(Map<String, ?> pmParam) {
		return dlwNewVrtlAcntDAO.getMemNoRegistCnt(pmParam);
	}
	
	/** ================================================================
     * 날짜 : 20200224
     * 이름 : 송준빈
     * 추가내용 : 가상계좌번호에 고객 고유번호를 부여하기 전 고유번호 유효성체크
     * 대상 테이블 : LF_DMUSER.MEMBER
     * ================================================================
     * */
	public int getMemNoExists(Map<String, ?> pmParam) {
		return dlwNewVrtlAcntDAO.getMemNoExists(pmParam);
	}

    /** ================================================================
     * 날짜 : 20200224
     * 이름 : 송준빈
     * 추가내용 : 가상계좌번호에 고객 고유번호를 부여
     * 대상 테이블 : LF_DMUSER.TB_NICE_VRTL_ACCNT_INFO
     * ================================================================
     * */
	public int updateVrtlAccntMemNo(Map<String, ?> pmParam) throws Exception {
		return dlwNewVrtlAcntDAO.updateVrtlAccntMemNo(pmParam);
	}
}
