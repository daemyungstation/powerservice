package powerservice.business.dlw.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import powerservice.business.dlw.service.DlwFullConsHistService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service
public class DlwFullConsHistServiceImpl extends EgovAbstractServiceImpl implements DlwFullConsHistService {

	@Resource
    public DlwFullConsHistDAO dlwFullConsHistDAO;
	
	private final Logger log = LoggerFactory.getLogger(DlwFullConsHistServiceImpl.class);
	
	/** ================================================================
     * 날짜 : 20200619
     * 이름 : 송준빈
     * 추가내용 : CTI 사원 기본정보 조회수
     * 대상 테이블 : LF_DMUSER.TB_CTI_MAIN_INFO
     * ================================================================
     * */
    public int getFullConsHistCount(Map<String, ?> pmParam) throws Exception {
        return dlwFullConsHistDAO.getFullConsHistCount(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20200619
     * 이름 : 송준빈
     * 추가내용 : CTI 사원 기본정보 조회 리스트
     * 대상 테이블 : LF_DMUSER.TB_CTI_MAIN_INFO
     * ================================================================
     * */
    public List<Map<String, Object>> getFullConsHistList(Map<String, ?> pmParam) throws Exception {
        return dlwFullConsHistDAO.getFullConsHistList(pmParam);
    }
}
