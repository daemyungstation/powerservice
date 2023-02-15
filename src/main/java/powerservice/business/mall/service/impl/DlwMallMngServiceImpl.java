package powerservice.business.mall.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import powerservice.business.bean.UserSession;
import powerservice.business.mall.service.DlwMallLinkedService;
import powerservice.business.mall.service.DlwMallMngService;
import powerservice.business.sys.service.BasVlService;
import powerservice.core.util.SessionUtils;

import com.tobesoft.xplatform.data.DataSet;

import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service
public class DlwMallMngServiceImpl extends EgovAbstractServiceImpl implements DlwMallMngService {

	@Resource
    public DlwMallMngDAO dlwMallMngDAO;
	
	@Resource
    public BasVlService basVlService;
	
	private final Logger log = LoggerFactory.getLogger(DlwMallMngServiceImpl.class);
	
	
	/* ================================================================
     * 날짜 : 20220617
     * 이름 : 김주용
     * 추가내용 : 쇼핑몰회원조회
     * ================================================================
     * */
	
	public int getMallMemberCount(Map<String, ?> pmParam) throws Exception {
        return dlwMallMngDAO.getMallMemberCount(pmParam);
    }
	
	public List<Map<String, Object>> getMallMemberList(Map<String, ?> pmParam)throws Exception {
		return dlwMallMngDAO.getMallMemberList(pmParam);
	}

	/* ================================================================
     * 날짜 : 20220617
     * 이름 : 김주용
     * 추가내용 : 레디캐시이력조회
     * ================================================================
     * */
	
	public int getMallCashHistroyCount(Map<String, ?> pmParam) throws Exception {
        return dlwMallMngDAO.getMallCashHistroyCount(pmParam);
    }
	
	public List<Map<String, Object>> getMallCashHistroyList(Map<String, ?> pmParam)throws Exception {
		return dlwMallMngDAO.getMallCashHistroyList(pmParam);
	}
	
	/* ================================================================
     * 날짜 : 20220617
     * 이름 : 김주용
     * 추가내용 : 쇼핑몰회원이력조회
     * ================================================================
     * */
	
	public int getMallMemHistroyCount(Map<String, ?> pmParam) throws Exception {
        return dlwMallMngDAO.getMallMemHistroyCount(pmParam);
    }
	
	public List<Map<String, Object>> getMallMemHistroyList(Map<String, ?> pmParam)throws Exception {
		return dlwMallMngDAO.getMallMemHistroyList(pmParam);
	}
	
	/* ================================================================
     * 날짜 : 20220627
     * 이름 : 김주용
     * 추가내용 : 쇼핑몰회원수정
     * ================================================================
     * */
    public void updateMallMember(Map<String, ?> pmParam) throws Exception {

    	dlwMallMngDAO.updateMallMember(pmParam);
    }
    
    /* ================================================================
     * 날짜 : 20220627
     * 이름 : 김주용
     * 추가내용 : 쇼핑몰회원수정이력등록
     * ================================================================
     * */
    public void insertMallMemberHistory(Map<String, ?> pmParam) throws Exception {

    	dlwMallMngDAO.insertMallMemberHistory(pmParam);
    }
}
