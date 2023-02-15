package powerservice.business.req.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import powerservice.business.req.service.ReqRecordService;
import powerservice.business.sys.service.BasVlService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service
public class ReqRecordServiceImpl extends EgovAbstractServiceImpl implements ReqRecordService {

	private final Logger log = LoggerFactory.getLogger(ReqRecordServiceImpl.class);
	
	@Resource
    public BasVlService basVlService;
	
	@Resource
    public ReqRecordDAO reqRecordDAO;
	
	/**
     * 회원별 녹취 확인 리스트 건수
     *
     * @param pmParam 검색 조건
     * @return 회원별 녹취 확인 리스트 건수
     * @throws Exception
     */
    public int getRecBeforeCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) reqRecordDAO.getRecBeforeCount(pmParam);
    }

    /**
     * 회원별 녹취 확인 리스트
     *
     * @param pmParam 검색 조건
     * @return 회원별 녹취 확인 리스트
     * @throws Exception
     */
    public List<Map<String, Object>> getRecBeforeList(Map<String, ?> pmParam) throws Exception {
        return reqRecordDAO.getRecBeforeList(pmParam);
    }
}
