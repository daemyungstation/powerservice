package powerservice.business.dlw.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import powerservice.business.dlw.service.DlwReadyCashService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service
public class DlwReadyCashServiceImpl extends EgovAbstractServiceImpl implements DlwReadyCashService {

	@Resource
    public DlwReadyCashDAO dlwReadyCashDAO;
	
	private final Logger log = LoggerFactory.getLogger(DlwReadyCashServiceImpl.class);
	
	/** ================================================================
     * 날짜 : 20190509
     * 이름 : 송준빈
     * 추가내용 : 회원별 레디캐쉬 조회 수
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_DLWMALL
     * ================================================================
     * */
	public int getMemberDlwmallCount(Map<String, ?> pmParam) throws Exception {
		return dlwReadyCashDAO.getMemberDlwmallCount(pmParam);
	}

	/** ================================================================
     * 날짜 : 20190509
     * 이름 : 송준빈
     * 추가내용 : 회원별 레디캐쉬 조회 리스트
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_DLWMALL
     * ================================================================
     * */
	public List<Map<String, Object>> getMemberDlwmallList(Map<String, ?> pmParam) throws Exception {
		return dlwReadyCashDAO.getMemberDlwmallList(pmParam);
	}
	
	/** ================================================================
     * 날짜 : 20190509
     * 이름 : 송준빈
     * 추가내용 : 회원별 레디캐쉬 조회 집계
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_DLWMALL
     * ================================================================
     * */
	public List<Map<String, Object>> getMemberDlwmallSummary(Map<String, ?> pmParam) throws Exception {
		return dlwReadyCashDAO.getMemberDlwmallSummary(pmParam);
	}

}
