package powerservice.business.dlw.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import powerservice.business.dlw.service.DlwTermMangiInfoService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service
public class DlwTermMangiInfoServiceImpl extends EgovAbstractServiceImpl implements DlwTermMangiInfoService {

	@Resource
    public DlwTermMangiInfoDAO dlwTermMangiInfoDAO;
	
	private final Logger log = LoggerFactory.getLogger(DlwTermMangiInfoServiceImpl.class);
	
	/** ================================================================
     * 날짜 : 20210623
     * 이름 : 송준빈
     * 추가내용 : 기간별 만기정보 조회수
     * 대상 테이블 : DUAL
     * ================================================================
     * */
    public int getTermMangiCount(Map<String, ?> pmParam) throws Exception {
        return dlwTermMangiInfoDAO.getTermMangiCount(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20210623
     * 이름 : 송준빈
     * 추가내용 : 기간별 만기정보 조회 리스트
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_MANGI_EXT
     * ================================================================
     * */
    public List<Map<String, Object>> getTermMangiList(Map<String, ?> pmParam) throws Exception {
        return dlwTermMangiInfoDAO.getTermMangiList(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20210715
     * 이름 : 송준빈
     * 추가내용 : 기간별 만기고객 현황 상세 조회수
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_MANGI_EXT
     * ================================================================
     * */
    public int getMangiAccntInfoDtlCount(Map<String, ?> pmParam) throws Exception {
        return dlwTermMangiInfoDAO.getMangiAccntInfoDtlCount(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20210715
     * 이름 : 송준빈
     * 추가내용 : 기간별 만기고객 현황 상세 조회 리스트
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_MANGI_EXT
     * ================================================================
     * */
    public List<Map<String, Object>> getMangiAccntInfoDtlList(Map<String, ?> pmParam) throws Exception {
        return dlwTermMangiInfoDAO.getMangiAccntInfoDtlList(pmParam);
    }
}
