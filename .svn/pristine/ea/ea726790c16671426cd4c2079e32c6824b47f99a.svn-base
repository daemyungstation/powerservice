package powerservice.business.dlw.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import powerservice.business.dlw.service.DlwDirectSalesService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service
public class DlwDirectSalesServiceImpl extends EgovAbstractServiceImpl implements DlwDirectSalesService {

	@Resource
    public DlwDirectSalesDAO dlwDirectSalesDAO;
	
	private final Logger log = LoggerFactory.getLogger(DlwDirectSalesServiceImpl.class);
    
    /** ================================================================
     * 날짜 : 20200422
     * 이름 : 송준빈
     * 추가내용 : 행사전환상품 조회수
     * 대상 테이블 : LF_DMUSER.TB_EVT_TRAN_EXT
     * ================================================================
     * */
    public int getDirectSalesChannelCount(Map<String, ?> pmParam) throws Exception {
        return dlwDirectSalesDAO.getDirectSalesChannelCount(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20200422
     * 이름 : 송준빈
     * 추가내용 : 행사전환상품 조회리스트
     * 대상 테이블 : LF_DMUSER.TB_EVT_TRAN_EXT
     * ================================================================
     * */
    public List<Map<String, Object>> getDirectSalesChannelList(Map<String, ?> pmParam) throws Exception {
        return dlwDirectSalesDAO.getDirectSalesChannelList(pmParam);
    }
}
