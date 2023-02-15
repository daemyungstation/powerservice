package powerservice.business.sys.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * * 나이스 평가정보사의 신용정보 조회 클래스
 *
 * @author 정출연
 * @version 1.0
 * @date 2016/09/01
 * @프로그램ID NiceCredit
 */
@Repository
public class NiceCreditDAO extends EgovAbstractMapper {
	
    /**
     * 고객 정보를 수정한다.(대명고객정보로 업데이트)
     *
     * @param pmParam 고객 정보
     * @throws Exception
     */
    /*public int updateConsCustBasi(Map<String, ?> pmParam) throws Exception {
        return update("NiceCreditMap.updateConsCustBasi", pmParam);
    }*/

    /**
     * NICE 신용등급 조회 후 Safe-Key 발급 프로세스 안내 문자메시지를 전송한다.
     *
     * @param pmParam NICE 신용등급 Safe-Key 발급 관련 정보
     * @throws Exception
     */
    public int insertNiceSafekeySmsHis(Map<String, ?> pmParam) throws Exception {
        return insert("NiceCreditMap.insertNiceSafekeySmsHis", pmParam);
    }
    
    /**
     * Safe-Key 조회 이력을 등록한다.
     *
     * @param pmParam NICE 신용등급 조회 정보
     * @throws Exception
     */
    public int insertNiceSafekeySearchHis(Map<String, ?> pmParam) throws Exception {
        return insert("NiceCreditMap.insertNiceSafekeySearchHis", pmParam);
    }
    
    /**
     * NICE 신용등급 조회 이력을 등록한다.
     *
     * @param pmParam NICE 신용등급 조회 정보
     * @throws Exception
     */
    public int insertNiceCreditSearchHis(Map<String, ?> pmParam) throws Exception {
        return insert("NiceCreditMap.insertNiceCreditSearchHis", pmParam);
    }
    
    /**
     * NICE Safe-Key 발급 안내 SMS 발송결과
     *  - SMS 전송결과 업데이트
     *
     * @param pmParam NICE 신용등급 조회 정보
     * @throws Exception
     */
    public int updateNiceSafekeySmsSendResult(Map<String, ?> pmParam) throws Exception {
        return insert("NiceCreditMap.updateNiceSafekeySmsSendResult", pmParam);
    }
    
    /**
     * NICE Safe-Key 발급 완료 등록
     *  - SMS 전송후 고객이 폰에서 Safe-Key를 발급 완료한 내역을 등록
     *
     * @param pmParam NICE 신용등급 조회 정보
     * @throws Exception
     */
    public int updateNiceSafekeyMobileIssueResult(Map<String, ?> pmParam) throws Exception {
        return insert("NiceCreditMap.updateNiceSafekeyMobileIssueResult", pmParam);
    }
    
    /**
     * NICE Safe-Key 발급 SMS 발송 목록 조회
     *
     * @param pmParam 검색 조건
     * @return MO수신 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getMoNiceSafeKeySmsList(Map<String, ?> pmParam) throws Exception {    	
        return selectList("NiceCreditMap.getMoNiceSafeKeySmsList", pmParam);
    }
    
    /**
     * NICE Safe-Key 발급 SMS 발송 건수 조회
     *
     * @param pmParam 검색 조건
     * @return SMS 발송 건수
     * @throws Exception
     */
    public int getMoNiceSafeKeySmsCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) selectOne("NiceCreditMap.getMoNiceSafeKeySmsCount", pmParam);
    }
    
    /**
     * NICE Safe-Key 발급 완료 등록
     *  - SMS 전송후 고객이 폰에서 Safe-Key를 발급 완료한 내역을 등록
     *
     * @param pmParam NICE 신용등급 조회 정보
     * @throws Exception
     */
    public int updateSafeKeyIssueStat(Map<String, ?> pmParam) throws Exception {
        return insert("NiceCreditMap.updateSafeKeyIssueStat", pmParam);
    }
    
    /**
     * NICE Safe-Key 발급 SMS 무효 처리
     *  - SMS 전송후 고객의 액션이 없이 24시간이 경과된 경우 만료처리
     *
     * @param 처리건수
     * @throws Exception
     */
    public int updateExpiredSafekeySmsHist(Map<String, ?> pmParam) throws Exception {
        return insert("NiceCreditMap.updateExpiredSafekeySmsHist", pmParam);
    }
    
    /**
     * NICE Safe-Key 발급 SMS 최종 발송일자
     *
     * @param pmParam 검색 조건
     * @return 발송일자
     * @throws Exception
     */
    public List<Map<String, Object>> getSmsSendDt(Map<String, ?> pmParam) throws Exception {
        return selectList("NiceCreditMap.getSmsSendDt", pmParam);
    }
    
    /**
     * 데이터베이스의 현재시간
     *
     * @param void
     * @return 현재시간
     * @throws Exception
     */
    public List<Map<String, Object>> getCurrDbDthms() throws Exception {
        return selectList("NiceCreditMap.getCurrDbDthms");
    }
    
    /**
     * NICE Safe-Key 목록 조회
     *   - Safekey 발급이력 테이블에서 조회한다. 
     * @param pmParam 검색 조건
     * @return MO수신 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getSafekeKeyListFromHist(Map<String, ?> pmParam) throws Exception {    	
        return selectList("NiceCreditMap.getSafekeKeyListFromHist", pmParam);
    }
}
