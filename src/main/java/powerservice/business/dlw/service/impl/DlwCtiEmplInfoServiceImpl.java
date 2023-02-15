package powerservice.business.dlw.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import powerservice.business.dlw.service.DlwCtiEmplInfoService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service
public class DlwCtiEmplInfoServiceImpl extends EgovAbstractServiceImpl implements DlwCtiEmplInfoService {

	@Resource
    public DlwCtiEmplInfoDAO dlwCtiEmplInfoDAO;
	
	private final Logger log = LoggerFactory.getLogger(DlwCtiEmplInfoServiceImpl.class);
	
	/** ================================================================
     * 날짜 : 20200619
     * 이름 : 송준빈
     * 추가내용 : CTI 사원 기본정보 조회수
     * 대상 테이블 : LF_DMUSER.TB_CTI_MAIN_INFO
     * ================================================================
     * */
    public int getCtiMainInfoCount(Map<String, ?> pmParam) throws Exception {
        return dlwCtiEmplInfoDAO.getCtiMainInfoCount(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20200619
     * 이름 : 송준빈
     * 추가내용 : CTI 사원 기본정보 조회 리스트
     * 대상 테이블 : LF_DMUSER.TB_CTI_MAIN_INFO
     * ================================================================
     * */
    public List<Map<String, Object>> getCtiMainInfoList(Map<String, ?> pmParam) throws Exception {
        return dlwCtiEmplInfoDAO.getCtiMainInfoList(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20200619
     * 이름 : 송준빈
     * 추가내용 : CTI 사원 Que정보 조회수
     * 대상 테이블 : LF_DMUSER.TB_CTI_QUE_INFO
     * ================================================================
     * */
    public int getCtiQueInfoCount(Map<String, ?> pmParam) throws Exception {
        return dlwCtiEmplInfoDAO.getCtiQueInfoCount(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20200619
     * 이름 : 송준빈
     * 추가내용 : CTI 사원 Que정보 조회 리스트
     * 대상 테이블 : LF_DMUSER.TB_CTI_QUE_INFO
     * ================================================================
     * */
    public List<Map<String, Object>> getCtiQueInfoList(Map<String, ?> pmParam) throws Exception {
        return dlwCtiEmplInfoDAO.getCtiQueInfoList(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20200619
     * 이름 : 송준빈
     * 추가내용 : CTI 사원 기본정보 조회수
     * 대상 테이블 : LF_DMUSER.TB_CTI_MAIN_INFO
     * ================================================================
     * */
    public int getCtiInfoSumCount(Map<String, ?> pmParam) throws Exception {
        return dlwCtiEmplInfoDAO.getCtiInfoSumCount(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20200619
     * 이름 : 송준빈
     * 추가내용 : CTI 사원 기본정보 조회 리스트
     * 대상 테이블 : LF_DMUSER.TB_CTI_MAIN_INFO
     * ================================================================
     * */
    public List<Map<String, Object>> getCtiInfoSumList(Map<String, ?> pmParam) throws Exception {
        return dlwCtiEmplInfoDAO.getCtiInfoSumList(pmParam);
    }
}
