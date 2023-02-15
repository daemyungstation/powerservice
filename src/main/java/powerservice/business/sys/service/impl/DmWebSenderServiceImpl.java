package powerservice.business.sys.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import powerservice.business.sys.service.DmWebSenderService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;


@Service
public class DmWebSenderServiceImpl extends EgovAbstractServiceImpl implements DmWebSenderService {

    private final Logger log = LoggerFactory.getLogger(DmWebSenderServiceImpl.class);

    @Resource
    public DmWebSenderDAO dmWebSenderDAO;

    /**
     * APP푸쉬알림시 메시지 정보 가져오기
     * 임동진 20190327
     * @throws Exception
     */
    public List<Map<String, Object>> getSendPushInfo(Map<String, ?> pmParam) throws Exception {
    	return dmWebSenderDAO.getSendPushInfo(pmParam);

    }
}
