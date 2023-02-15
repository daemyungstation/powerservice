package powerservice.business.dlw.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import kr.co.nicepay.module.lite.NicePayWebConnector;

import org.springframework.stereotype.Service;

import powerservice.business.dlw.service.DlwMemPayMthdService;
import powerservice.business.dlw.service.DlwNewTypeMainPopService;
import powerservice.business.sys.service.BasVlService;
import powerservice.common.util.CommonUtils;
import powerservice.common.util.INICISPay;
import powerservice.common.util.NicePayProcess;
import powerservice.core.util.ParamUtils;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service
public class DlwMemPayMthdServiceImpl extends EgovAbstractServiceImpl implements DlwMemPayMthdService{
	
	@Resource
    public DlwMemPayMthdDAO DlwMemPayMthdDAO;
	
    @Resource
    public BasVlService basVlService;
    
    /**
     * 고객-구좌 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 고객-구좌 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getMemPayMthdList(Map<String, ?> pmParam) throws Exception {    	    	
    	
    	List<Map<String, Object>> rtnList  = null;
    	
    	rtnList  = DlwMemPayMthdDAO.getMemPayMthdList(pmParam);
    	
        return rtnList;
    }
    
    /** ================================================================
     * 날짜 : 20220608
     * 이름 : 김주용
     * 추가내용 : 고객구좌 이력
     * 대상 테이블 : LF_DMUSER.NEW_CANCL_APP, LF_DMUSER.CARD_NEW_CANCL_APP
     * ================================================================
     * */
    public List<Map<String, Object>> getDlwPymnHstrList(Map<String, ?> pmParam) throws Exception {
        return DlwMemPayMthdDAO.getDlwPymnHstrList(pmParam);
    }
	
    /**
     * Card 정보 업데이트
     * 
     * 
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public void updateCardMemInfo(Map<String, ?> pmParam) throws Exception {

        DlwMemPayMthdDAO.updateCardMemInfo(pmParam);
    }      
	
    /**
     * 나이스 이니시스 카드정보 등록 (NEW)
     * @param pmParam 검색 조건
     * @return 등록결과
     * @throws Exception
     */
    public String insertDlwAccntCard(Map<String, Object> pmParam) throws Exception {
        String rslt = "";
        
        //관계 값이 본인이 아닐 경우 승인값 대리납으로 변경
        String relVal = pmParam.get("reltn").toString();
             
    	DlwMemPayMthdDAO.insertDlwCardLog(pmParam); // CARD 정보등록
    	DlwMemPayMthdDAO.insertDlwCardMmbr(pmParam);     // CARD 회원 신규등록
        
        //관계 값이 본인이 아닐 경우 승인값 대리납으로 변경
        if (!relVal.equals("20")){
        	DlwMemPayMthdDAO.updateHpCall(pmParam); 
        	DlwMemPayMthdDAO.insertHpCallHist(pmParam); // 승인 히스토리 등록
        } 
        
        return rslt;
    }

    public String deleteDlwAccntCard(Map<String, Object> pmParam) throws Exception {
		String rslt = "delete";
		pmParam.put("app_cl","3");
	    DlwMemPayMthdDAO.insertDlwCardLog(pmParam);	  // 카드 로그정보등록	    
	    DlwMemPayMthdDAO.deleteDlwCardMember(pmParam);     // 카드 회원정보 해지   
		return rslt;
    }
    
    /**
     * CMS 정보 업데이트
     * 
     * 
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public void updateCmsMemInfo(Map<String, ?> pmParam) throws Exception {

        DlwMemPayMthdDAO.updateCmsMemInfo(pmParam);
    }  
    
    public String deleteDlwAccntCms(Map<String, Object> pmParam) throws Exception {
		String rslt = "delete";
		pmParam.put("app_cl","3");
	    DlwMemPayMthdDAO.insertDlwCmsLog(pmParam);	  // CMS 로그정보등록	    
	    DlwMemPayMthdDAO.deleteDlwCmsMmbr(pmParam);     // CMS 회원정보 해지   
		return rslt;
    }
    
    /**
     * 대명라이프웨이의 CMS 부가서비스(신규,해지)를 등록한다.
     *
     * @param pmParam 검색 조건
     * @return 등록결과
     * @throws Exception
     */
    public String insertDlwAccntCms(Map<String, Object> pmParam) throws Exception {
        String rslt ="";
        
        //관계 값이 본인이 아닐 경우 승인값 대리납으로 변경
        String relVal = pmParam.get("reltn").toString();
        
    	DlwMemPayMthdDAO.insertDlwCmsLog(pmParam); // CMS 정보등록
    	DlwMemPayMthdDAO.insertDlwCmsMmbr(pmParam);     // CMS 회원 신규등록
          
        //관계 값이 본인이 아닐 경우 승인값 대리납으로 변경
        if (!relVal.equals("20")){
        	DlwMemPayMthdDAO.updateHpCall(pmParam); 
        	DlwMemPayMthdDAO.insertHpCallHist(pmParam); // 승인 히스토리 등록
        }
        return rslt;
    }
    
    /**
     * 대명라이프웨이 CMS 신청 실패 로그 등록
     *
     * @param pmParam 검색 조건
     * @return 등록결과
     * @throws Exception
     */
    public void insertDlwCmsHistoryLogs(Map<String, ?> pmParam) throws Exception {
    	DlwMemPayMthdDAO.insertDlwCmsHistoryLogs(pmParam);;
    }
    
    /**
     * NicePay Card 빌키 생성
     *
     * @param pmParam 검색 조건
     * @return 빌키생성 정보
     * @throws Exception
     */
    public String[] billKeyCreate(Map<String, Object> pmParam) throws Exception {
        String rtVal[] = new String[5];
        NicePayProcess nicepay = new NicePayProcess();
        String prod_cd = (String)pmParam.get("prod_cd");
        String menu_gbn = (String)pmParam.get("menu_gbn");
        String email = "lifeway@daemyung.com";
        String tmpEmail = CommonUtils.checkNull((String)pmParam.get("email"));

        /*20180723 37회 SDP카드변경 이슈로 인한 추가*/
        String pay_No = (String)pmParam.get("pay_no");

        if("".equals(tmpEmail)) {
            email = tmpEmail;
        }

        Map<String, Object> hm = new HashMap<String, Object>();
        hm.put("prod_cd", prod_cd);

        /*20180723 37회 SDP카드변경 이슈로 인한 추가*/
        hm.put("pay_no", pay_No);

        if(menu_gbn != null) {
            hm.put("menu_gbn", menu_gbn);
        } else {
            hm.put("menu_gbn", "");
        }

        nicepay = niceBillSetting(hm,"billKeyCreate");
        nicepay.setMoid((String)pmParam.get("accnt_no"));
        nicepay.setBuyerName((String)pmParam.get("mem_nm"));
        nicepay.setBuyerEmail(email);
        nicepay.setBuyerTel((String)pmParam.get("cell"));
        nicepay.setGoodsName((String)pmParam.get("prod_nm"));
        nicepay.setCardNumber((String)pmParam.get("card_no"));
        nicepay.setExpYear((String)pmParam.get("exp_year"));
        nicepay.setExpMonth((String)pmParam.get("exp_mon"));
        nicepay.setCardIdNo("");
        nicepay.setCardPw("");
        nicepay.setIdno((String)pmParam.get("idn_no"));

        NicePayWebConnector result = nicepay.requestBillKey();
        for(int i = 0; i < 5; i++) { 
            rtVal[i] = "";
        }
        rtVal[0] = result.getResultData("ResultCode");
        rtVal[1] = result.getResultData("BID");
        rtVal[2] = result.getResultData("ResultMsg");
        rtVal[3] = result.getResultData("CardCode");
        rtVal[4] = result.getResultData("CardName"); 
        return rtVal;
    }
    
    public NicePayProcess niceBillSetting(Map<String, Object> pmParam, String gbn) throws Exception {
        NicePayProcess nicepay = new NicePayProcess();

        ParamUtils.addCenterParam(pmParam);

        String sPayFilePath = basVlService.getBasVlAsString("pay_file_path", (String) pmParam.get("cntr_cd"));

        nicepay.setNicePayHome(sPayFilePath+"/web_site/nicelog");
        //nicepay.setNicePayHome("c:/web_site/nicelog");

        String menu_gbn = String.valueOf(pmParam.get("menu_gbn"));
        String prod_cd = String.valueOf(pmParam.get("prod_cd"));
        String bid = String.valueOf(pmParam.get("bid"));

        //MID 및 KEY값 DB에서 가져오기
        String strMidKey = DlwMemPayMthdDAO.getAccntMid(pmParam);
        String mid = strMidKey.substring(0,10);  //mid
        String key = strMidKey.substring(10);	 //key

        nicepay.setMID(mid);
        nicepay.setMerchantKey(key);

        return nicepay;
    }
    
    /**
     * 상담 정보를 등록한다.
     *
     * @param pmParam 상담 정보
     * @throws Exception
     */
    public String insertCons(Map<String, Object> pmParam) throws Exception {
    	
        String sKey = "";
        int nResult = 0;
        
        nResult = DlwMemPayMthdDAO.insertCons(pmParam);
        
        return sKey;
    }
    
    /**
     * 납입방법 업데이트
     * 
     * 
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public void updateAccntPayMthd(Map<String, ?> pmParam) throws Exception {

        DlwMemPayMthdDAO.updateAccntPayMthd(pmParam);
    }
        
    /**
     * CMS등록전송대상자조회
     * 임동진
     * 20220624
     */
    public int getCmsAccntCount(Map<String, ?> pmParam) throws Exception {
        return DlwMemPayMthdDAO.getCmsAccntCount(pmParam);
    }
    
    public int getCmsSendCount(Map<String, ?> pmParam) throws Exception {
        return DlwMemPayMthdDAO.getCmsSendCount(pmParam);
    }
    
    public  List<Map<String, Object>> getCmsAccntList(Map<String, ?> pmParam) throws Exception {
        return DlwMemPayMthdDAO.getCmsAccntList(pmParam);
    }
    
    public  List<Map<String, Object>> getCmsSendList(Map<String, ?> pmParam) throws Exception {
        return DlwMemPayMthdDAO.getCmsSendList(pmParam);
    }    
    
    public String insertCmsTarget(Map<String, Object> pmParam) throws Exception {
        String rslt = "";
        String sGbn = pmParam.get("gbn").toString();
        
        switch(sGbn){
        case "insert" :
        	DlwMemPayMthdDAO.insertCmsTarget(pmParam);     // CMS대상 등록
        	break;
        case "delete" :
        	DlwMemPayMthdDAO.deleteCmsTarget(pmParam);     // CMS대상 삭제
        	break;	
        case "out" :
        	DlwMemPayMthdDAO.outCmsTarget(pmParam);     // CMS대상 추출
        	break;
        
        }
                    	               
        return rslt;
    }
    
    /**
     * WEB에서 변경 요청된 회원업데이트
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public void updateMemInfo_web(Map<String, ?> pmParam) throws Exception {

        DlwMemPayMthdDAO.updateMemInfo_web(pmParam);
    }    
}
