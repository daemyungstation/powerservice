package powerservice.business.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

@Repository
public class DmWebSenderDAO extends EgovAbstractMapper {
    /**
     * APP푸쉬알림시 메시지 정보 가져오기
     * 임동진 20190327
     * @param pmParam 검색 조건
     * @return MO수신 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getSendPushInfo(Map<String, ?> pmParam) throws Exception {
        return selectList("DmWebSenderMap.getSendPushInfo", pmParam);
    }
}
