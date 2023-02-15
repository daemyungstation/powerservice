package powerservice.business.dlw.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import powerservice.business.bean.UserSession;
import powerservice.business.dlw.service.DlwHanaCardMngService;
import powerservice.core.util.SessionUtils;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service
public class DlwHanaCardMngServiceImpl extends EgovAbstractServiceImpl implements DlwHanaCardMngService {

	@Resource
    public DlwHanaCardMngDAO dlwHanaCardMngDAO;	
    
    /** ================================================================
     * 날짜 : 20230111
     * 이름 : 임동진
     * 추가내용 : 하나카드 전송 관리
     * 대상 테이블 : LF_DMUSER.TB_HANA_SEND_INFO
     * ================================================================
     * */

    public List<Map<String, Object>> getHanaSendList(Map<String, Object> pmParam) {
        return dlwHanaCardMngDAO.getHanaSendList(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20230111
     * 이름 : 임동진
     * 추가내용 : 하나카드 전송 이력 저장
     * 대상 테이블 : LF_DMUSER.TB_HANA_SEND_INFO
     * ================================================================
     * */
    
    public int insertHanaRecieveHistory(Map<String, ?> pmParam) throws Exception {
    	//혜택정보 전송의 경우 혜택정보 전송정보 업데이트 
    	if(pmParam.get("send_loc").equals("OUT")){    		
    		dlwHanaCardMngDAO.updateHanaPaySendInfo(pmParam);    		
    	}
    	    	
        return dlwHanaCardMngDAO.insertHanaRecieveHistory(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20230111
     * 이름 : 임동진
     * 추가내용 : 하나카드 발급 회원정보 수
     * 대상 테이블 : LF_DMUSER.TB_HANA_SEND_INFO
     * ================================================================
     * */
    public int getHanaMemCount(Map<String, Object> pmParam) {
        return dlwHanaCardMngDAO.getHanaMemCount(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20230111
     * 이름 : 임동진
     * 추가내용 : 하나카드 발급 회원정보 리스트
     * 대상 테이블 : LF_DMUSER.TB_HANA_SEND_INFO
     * ================================================================
     * */

    public List<Map<String, Object>> getHanaMemList(Map<String, Object> pmParam) {
        return dlwHanaCardMngDAO.getHanaMemList(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20230111
     * 이름 : 임동진
     * 추가내용 : 하나카드 발급정보 등록
     * 대상 테이블 : LF_DMUSER.TB_HANA_MEMBER
     * ================================================================
     * */
    
    public int insertHanaMember(Map<String, ?> pmParam) throws Exception {
        return dlwHanaCardMngDAO.insertHanaMember(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20230210
     * 이름 : 임동진
     * 추가내용 : 하나카드 전월실적 대상자 등록
     * 대상 테이블 : LF_DMUSER.TB_HANA_MEMBER
     * ================================================================
     * */
    
    public int insertHanaPayments(Map<String, ?> pmParam) throws Exception {
        return dlwHanaCardMngDAO.insertHanaPayments(pmParam);
    }    
    
    /** ================================================================
     * 날짜 : 20230111
     * 이름 : 임동진
	     * 추가내용 : 하나카드혜택정보관리
	     * 대상 테이블 : LF_DMUSER.TB_HANA_PAYMENTS
     * ================================================================
     * */
    public int getHanaPayCount(Map<String, Object> pmParam) {
        return dlwHanaCardMngDAO.getHanaPayCount(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20230111
     * 이름 : 임동진
	     * 추가내용 : 하나카드혜택정보관리
	     * 대상 테이블 : LF_DMUSER.TB_HANA_PAYMENTS
     * ================================================================
     * */

    public List<Map<String, Object>> getHanaPayList(Map<String, Object> pmParam) {
        return dlwHanaCardMngDAO.getHanaPayList(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20230210
     * 이름 : 임동진
     * 추가내용 : 하나카드혜택현재상태
     * 대상 테이블 : LF_DMUSER.TB_HANA_SEND_INFO
     * ================================================================
     * */
    public String getHanaPayProcessStat(Map<String, Object> pmParam) {
        return dlwHanaCardMngDAO.getHanaPayProcessStat(pmParam);
    }
    
    
 }
