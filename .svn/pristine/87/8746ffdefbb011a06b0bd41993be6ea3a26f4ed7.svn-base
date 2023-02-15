package powerservice.business.dlw.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import kr.co.nicepay.module.lite.NicePayWebConnector;

import org.springframework.stereotype.Service;

import powerservice.business.dlw.service.DlwNewTypeMainPopService;
import powerservice.business.sys.service.BasVlService;
import powerservice.common.util.CommonUtils;
import powerservice.common.util.INICISPay;
import powerservice.common.util.NicePayProcess;
import powerservice.core.util.ParamUtils;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service
public class DlwNewTypeMainPopServiceImpl extends EgovAbstractServiceImpl implements DlwNewTypeMainPopService{
	
	@Resource
    public DlwNewTypeMainPopDAO DlwNewTypeMainPopDAO;
	
    @Resource
    public BasVlService basVlService;
	
	
	/** ================================================================
     * 날짜 : 20190730
     * 이름 : 김주용
     * 추가내용 : 회원 필드 검색
     * 대상 테이블 : LF_DMUSER.TB_USER_OPTION_DATA
     * ================================================================
     * */
    public List<Map<String, Object>> getUserOption(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeMainPopDAO.getUserOption(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20190730
     * 이름 : 김주용
     * 추가내용 : 회원 필드 등록
     * 대상 테이블 : LF_DMUSER.TB_USER_OPTION_DATA
     * ================================================================
     * */
    public int mergeUserOption(Map<String, Object> pmParam) {
        return DlwNewTypeMainPopDAO.mergeUserOption(pmParam);
    }
    
    /** ================================================================
     * 날짜 : 20190730
     * 이름 : 김주용
     * 추가내용 : 회원 필드 삭제
     * 대상 테이블 : LF_DMUSER.TB_USER_OPTION_DATA
     * ================================================================
     * */
    public int deleteUserOption(Map<String, Object> pmParam) {
        return DlwNewTypeMainPopDAO.deleteUserOption(pmParam);
    }
    
    /**
     * 고객-상품 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 고객-상품 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwCustPrdtList(Map<String, ?> pmParam) throws Exception {

        return DlwNewTypeMainPopDAO.getDlwCustPrdtList(pmParam);
    }
    
    /**
     * 대명라이프웨이의 CMS 금일 부가서비스 등록이력 건수를 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 등록이력 정보 건수
     * @throws Exception
     */
    public int getDlwCmsAplcDtlCount(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeMainPopDAO.getDlwCmsAplcDtlCount(pmParam);
    }

    /**
     * 대명라이프웨이의 CMS 금일 부가서비스 등록이력을 조회한다.
     *
     * @param pmParam 검색 조건
     * @return 등록이력 정보
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwCmsAplcDtl(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeMainPopDAO.getDlwCmsAplcDtl(pmParam);
    }
    
    /**
     * 대명라이프웨이의 Cms 산출중 여부 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 산출 여부 정보
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwCmsWdrwChk(Map<String, Object> pmParam) throws Exception {
        return DlwNewTypeMainPopDAO.getDlwCmsWdrwChk(pmParam);
    }
    
    /**
     *  구좌별 CMS 정보조회
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getCMSInfoByAccnt(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeMainPopDAO.getCMSInfoByAccnt(pmParam);
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

        //nicepay = niceBillSetting(hm);

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

        //int pay_No = Integer.valueOf(String.valueOf(pmParam.get("pay_no"))).intValue();

        //MID 및 KEY값 DB에서 가져오기
        String strMidKey = DlwNewTypeMainPopDAO.getAccntMid(pmParam);
        String mid = strMidKey.substring(0,10);  //mid
        String key = strMidKey.substring(10);	 //key

        System.out.println("bid      xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx>  :" + bid);
        System.out.println("menu_gbn xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx>  :" + menu_gbn);
        System.out.println("mid      xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx>  :" + mid);
        System.out.println("key      xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx>  :" + key);

        nicepay.setMID(mid);
        nicepay.setMerchantKey(key);

        return nicepay;
    }
    
    /**
     * 대명라이프웨이의 CMS 부가서비스(신규,해지)를 등록한다.
     * 나이스프로세스
     * @param pmParam 검색 조건
     * @return 등록결과
     * @throws Exception
     */
    public String insertDlwCardAnxtSrvc(Map<String, Object> pmParam) throws Exception {
        String rslt = "";
        
        //관계 값이 본인이 아닐 경우 승인값 대리납으로 변경
        String relVal = pmParam.get("reltn").toString();
        
        String sCardYn = pmParam.get("card_yn").toString();
        String billKeyArr[] = new String[5];	//빌키
        
        
        /***********************************************************************************************************
        * 카드 신청 등록 시 본인 확인 부분 ( 생년월일 + 카드번호등으로 본인 확인 )
        /***********************************************************************************************************/
        NicePayProcess nicepay = new NicePayProcess();        
        String sPayFilePath = basVlService.getBasVlAsString("pay_file_path", (String) pmParam.get("cntr_cd"));
        
        ParamUtils.addCenterParam(pmParam);

        nicepay.setNicePayHome(sPayFilePath+"/web_site/nicelog");
        //nicepay.setNicePayHome("c:/web_site/nicelog");
        nicepay.setGoodsName((String)pmParam.get("prod_cd"));
        nicepay.setMoid((String)pmParam.get("accnt_no"));
        nicepay.setBuyerName((String)pmParam.get("mem_nm"));
        nicepay.setBuyerTel((String)pmParam.get("cell"));
        nicepay.setBuyerEmail((String)pmParam.get("email"));
        nicepay.setCardNumber((String)pmParam.get("card_no"));
        nicepay.setExpYear((String)pmParam.get("exp_year"));
        nicepay.setExpMonth((String)pmParam.get("exp_mon"));
        nicepay.setIdno((String)pmParam.get("idn_no"));

        //승인요청
        NicePayWebConnector connector = nicepay.requestCardMemAuth();
        String resultCode = connector.getResultData("ResultCode");
        String resultMsg = connector.getResultData("ResultMsg");
        String cardCode = connector.getResultData("CardCode");
        String cardName = connector.getResultData("CardName");

        System.out.println("resultCode : " + resultCode);
        System.out.println("resultMsg : " + resultMsg);
        System.out.println("cardCode : " + cardCode);
        System.out.println("cardName : " + cardName);

        // 정상여부
        if(!"0000".equals(resultCode) && (!"5012".equals(resultCode))) {
            //정상결과 아닐경우 결과코드 출력
            rslt = "[NG] " + resultMsg;            
            return rslt;
        }
        
        /***********************************************************************************************************
        * 카드 정보가 본인이 확인 된 경우 빌키 생성
        /***********************************************************************************************************/
        billKeyArr = billKeyCreate(pmParam);
        pmParam.put("bidRtCd", billKeyArr[0]);
        pmParam.put("bid", billKeyArr[1]);
        pmParam.put("bidMsg", billKeyArr[2]);      
        
        
        
        if(!"F100".equals(billKeyArr[0])) {
        	rslt = "[NG] ]빌키 생성 시 오류[275줄] >> : " + billKeyArr[0].toString();
        	return rslt;
        }
        
        /***********************************************************************************************************/

        if(sCardYn.equals("NONE")){
        	DlwNewTypeMainPopDAO.insertDlwCardAnxtSrvc(pmParam); // CARD 정보등록
        	DlwNewTypeMainPopDAO.insertDlwCardMmbr(pmParam);     // CARD 회원 신규등록
        } else {
        	DlwNewTypeMainPopDAO.insertDlwCardAnxtSrvc(pmParam); // CARD 정보등록
        	DlwNewTypeMainPopDAO.updateDlwCardMmbr(pmParam);     // CARD 기존 회원정보 수정        	        	
        }
        
        //관계 값이 본인이 아닐 경우 승인값 대리납으로 변경
        if (!relVal.equals("20")){
        	DlwNewTypeMainPopDAO.updateHpCall(pmParam); 
        	DlwNewTypeMainPopDAO.insertHpCallHist(pmParam); // 승인 히스토리 등록
        }
        
        rslt = "[OK] 정상적으로 카드신청되었습니다.";
        
        return rslt;
    }
    
    /**
     * 대명라이프웨이의 CMS 부가서비스(신규,해지)를 등록한다.
     * 이니시스 프로세스
     * @param pmParam 검색 조건
     * @return 등록결과
     * @throws Exception
     */
    public String insertDlwInicisCardAnxtSrvc(Map<String, Object> pmParam) throws Exception {
        String rslt = "";
                
        //관계 값이 본인이 아닐 경우 승인값 대리납으로 변경
        String relVal = pmParam.get("reltn").toString();
        
        String sCardYn = pmParam.get("card_yn").toString();
        String billKeyArr[] = new String[5];	//빌키
        
        
        /***********************************************************************************************************
        * 카드 신청 등록 시 본인 확인 부분 ( 생년월일 + 카드번호등으로 본인 확인 )
        /***********************************************************************************************************/        
        String sPayFilePath = basVlService.getBasVlAsString("pay_file_path", (String) pmParam.get("cntr_cd"));
                     
        ParamUtils.addCenterParam(pmParam);      
        
    	INICISPay inicisPay = new INICISPay();
 		inicisPay.setInicisKey("hmPLjIdyekyylSx9");
 		inicisPay.setInicisiv("fS7oGOerwBsEcQ==");

		inicisPay.setData("mid", "daemyungT1");

		inicisPay.setData("clientIp", "127.0.0.1");
		inicisPay.setData("moid", (String)pmParam.get("accnt_no"));
		inicisPay.setData("goodName", "상품명");     
		inicisPay.setData("buyerName", "구매자명");
		//inicisPay.setData("buyerEmail", (String)pmParam.get("email"));
		inicisPay.setData("buyerEmail", "dongjin.lim@daemyung.com");
		inicisPay.setData("buyerTel", (String)pmParam.get("cell"));
		inicisPay.setData("cardNumber", (String)pmParam.get("card_no"));
		inicisPay.setData("cardExpire", (String)pmParam.get("exp_year")+(String)pmParam.get("exp_mon") );
		inicisPay.setData("regNo", (String)pmParam.get("idn_no"));
		//inicisPay.setData("cardPw", "00");

		inicisPay.authBillkey();

		String resultCode = inicisPay.getData("resultCode");
		String resultMsg = inicisPay.getData("resultMsg");
		String tid = inicisPay.getData("tid");
		String billKey = inicisPay.getData("billKey");
		
		//카드사 코드 
		String strCardCd= inicisPay.getData("cardCode");
		
		//체크카드 여부 (0:신용, 1:체크, 2:기프트카드)
		String strCheckFlg = inicisPay.getData("checkFlg");
		

        // 정상여부
        if(!"00".equals(resultCode) && (!"5012".equals(resultCode))) {
            //정상결과 아닐경우 결과코드 출력
            rslt = "[NG] " + resultMsg;            
            return rslt;
        }
        
        pmParam.put("ini_bid", billKey);
        
        if(sCardYn.equals("NONE")){
        	DlwNewTypeMainPopDAO.insertDlwCardAnxtSrvc(pmParam); // CMS 정보등록
        	DlwNewTypeMainPopDAO.insertDlwCardMmbr(pmParam);     // CMS 회원 신규등록
        } else {
        	DlwNewTypeMainPopDAO.insertDlwCardAnxtSrvc(pmParam); // CMS 정보등록
        	DlwNewTypeMainPopDAO.updateDlwCardMmbr(pmParam);     // CMS 기존 회원정보 수정        	        	
        }
        
        //관계 값이 본인이 아닐 경우 승인값 대리납으로 변경
        if (!relVal.equals("20")){
        	DlwNewTypeMainPopDAO.updateHpCall(pmParam); 
        	DlwNewTypeMainPopDAO.insertHpCallHist(pmParam); // 승인 히스토리 등록
        }
        
        rslt = "[OK] 정상적으로 카드신청되었습니다.";
        
        return rslt;
    }    
    
    /**
     * 장기할부 카드확인 및 저장 METHOD
     * 이니시스 프로세스
     * @param pmParam 검색 조건
     * @return 등록결과
     * @throws Exception
     */

    public Map<String, Object> getDlwInicisLongtermCardCheck(Map<String,Object > pmParam) throws Exception {
		Map<String, Object> rtnParam = new HashMap<String, Object>();
		
		String sCardYn = pmParam.get("card_yn").toString();  //넘어온 등록상태 (최초등록, 등록삭제 후 업데이트 카드 등록)
		String hanaMid = pmParam.get("mid_info_1").toString();  //하나카드 체크시 mid변경 
		String strCardNo = pmParam.get("card_no").toString();		// 카드번호
		String strCardExpr =  (String)pmParam.get("exp_year")+(String)pmParam.get("exp_mon");	// 유효일자
		
		String resultCode = "";		//카드전송 결과코드
		String resultMsg = "";		//카드전송 결과메세지
		String tid = "";					//결과TID(체크, 빌키)
		String billKey = "";			//빌키
		String hanaCheck = "";
		
    	INICISPay inicisPay = new INICISPay();
 		inicisPay.setInicisKey("hmPLjIdyekyylSx9");
 		inicisPay.setInicisiv("fS7oGOerwBsEcQ==");

		//inicisPay.setData("mid", "daemyungT1");	// - daemyunhn1 (싱글용 하나카드) - daemyunhn2 (더블용 하나카드)
 		inicisPay.setData("mid", hanaMid);				// - daemyunhn1 (싱글용 하나카드) - daemyunhn2 (더블용 하나카드)
		inicisPay.setData("clientIp", "127.0.0.1");
		inicisPay.setData("moid", (String)pmParam.get("accnt_no"));
		inicisPay.setData("goodName", "상품명");     
		inicisPay.setData("buyerName", "구매자명");
		//inicisPay.setData("buyerEmail", (String)pmParam.get("email"));
		inicisPay.setData("buyerEmail", "dongjin.lim@daemyung.com");
		inicisPay.setData("buyerTel", (String)pmParam.get("cell"));
		inicisPay.setData("cardNumber", strCardNo);
		inicisPay.setData("cardExpire", strCardExpr);
		inicisPay.setData("regNo", (String)pmParam.get("idn_no"));
		//inicisPay.setData("cardPw", "00");
		
		inicisPay.authBillkey();
		
		//카드사 코드 		
		String strCardCd= inicisPay.getData("cardCode");				
		//체크카드 여부 (0:신용, 1:체크, 2:기프트카드)
		String strCheckFlg = inicisPay.getData("checkFlg");
		String rtnMsg = "";

		resultCode = inicisPay.getData("resultCode");
		resultMsg = inicisPay.getData("resultMsg");
		tid = inicisPay.getData("tid");
		billKey = inicisPay.getData("billKey");
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>> 11111: " + resultMsg);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>> 11111: " + resultCode);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>> 11111: " + strCardCd);
						
        // 빌키 정상여부
        if(!"00".equals(resultCode) && (!"5012".equals(resultCode))) {
            //정상결과 아닐경우 결과코드 출력
            rtnMsg = "[NG] " + resultMsg;       
        } else {
        	// 하나카드가 아닐경우 결과코드 출력
        	if (strCardCd.equals("01")){
        		
        		//카드정보 초기화 (암호정보이슈로 인한 초기화)
        		inicisPay.setData("cardNumber", strCardNo);
        		inicisPay.setData("cardExpire", strCardExpr);
        		
        		inicisPay.chkHanaCard();
        		
        		resultCode = inicisPay.getData("resultCode");
        		resultMsg = inicisPay.getData("resultMsg");
        		tid = inicisPay.getData("tid");
        		hanaCheck = inicisPay.getData("affiliateFlg");
        	} else {        		        		        	
        		//하나카드 장기할부 가능한 카드인지 체크		
        		resultMsg = "[NG] 하나카드 회원이 아닙니다.!!" ;     
     	        rtnParam.put("rtn_msg", resultMsg);		     
    	        return rtnParam;        		
        	}        	
        }
                               				
		// 카드가 정상적이지 않음
		if (!resultCode.equals("00") && !hanaCheck.equals("1") ){
	        rtnParam.put("rtn_msg", resultMsg);		     
	        return rtnParam;
		}
								
		pmParam.put("card_cd", strCardCd);        		        	        	
        pmParam.put("ini_bid", billKey);            
                        
        if(sCardYn.equals("NONE")){        
        	DlwNewTypeMainPopDAO.insertDlwCardAnxtSrvc(pmParam); // CMS 정보등록
        	DlwNewTypeMainPopDAO.insertDlwCardMmbr(pmParam);     // CMS 회원 신규등록
        } else {            	
        	DlwNewTypeMainPopDAO.insertDlwCardAnxtSrvc(pmParam); // CMS 정보등록
        	DlwNewTypeMainPopDAO.updateDlwCardMmbr(pmParam);     // CMS 기존 회원정보 수정        	        	
        }        	
        
        rtnParam.put("rtn_msg", "[OK]: 정상적으로 장기할부 카드 등록되었습니다.");	

		return rtnParam;
     }

    /**
     * 대명라이프웨이의 CMS 부가서비스(신규,해지)를 등록한다.
     *
     * @param pmParam 검색 조건
     * @return 등록결과
     * @throws Exception
     */
    public String insertDlwCmsAnxtSrvc(Map<String, Object> pmParam) throws Exception {
        String rslt = pmParam.get("cms_yn").toString();
        
        //관계 값이 본인이 아닐 경우 승인값 대리납으로 변경
        String relVal = pmParam.get("reltn").toString();
        
        if(rslt.equals("NONE")){
        	DlwNewTypeMainPopDAO.insertDlwCmsAnxtSrvc(pmParam); // CMS 정보등록
        	DlwNewTypeMainPopDAO.insertDlwCmsMmbr(pmParam);     // CMS 회원 신규등록
        } else {
        	
        	System.out.println(pmParam);        	
        	DlwNewTypeMainPopDAO.insertDlwCmsAnxtSrvc(pmParam); // CMS 정보등록
        	DlwNewTypeMainPopDAO.updateDlwCmsMmbr(pmParam);     // CMS 기존 회원정보 수정        	        	
        }
        //관계 값이 본인이 아닐 경우 승인값 대리납으로 변경
        if (!relVal.equals("20")){
        	DlwNewTypeMainPopDAO.updateHpCall(pmParam); 
        	DlwNewTypeMainPopDAO.insertHpCallHist(pmParam); // 승인 히스토리 등록
        }
        return rslt;
    }
    
    public String deleteDlwCardAnxtSrvc(Map<String, Object> pmParam) throws Exception {
		String rslt = "delete";
	    DlwNewTypeMainPopDAO.insertDlwCardAnxtSrvc(pmParam);	  // 카드 로그정보등록	    
	    DlwNewTypeMainPopDAO.deleteDlwCardMember(pmParam);     // 카드 회원정보 해지   
		return rslt;
    }
    
    public String deleteDlwCmsAnxtSrvc(Map<String, Object> pmParam) throws Exception {
		String rslt = "delete";
	    DlwNewTypeMainPopDAO.insertDlwCmsAnxtSrvc(pmParam);	  // CMS 로그정보등록	    
	    DlwNewTypeMainPopDAO.deleteDlwCmsMmbr(pmParam);     // CMS 회원정보 해지   
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
    	DlwNewTypeMainPopDAO.insertDlwCmsHistoryLogs(pmParam);;
    }
    
    /**
     * 고객 정보를 검색한다. (상담테이블 설정)
     *
     * @param pmParam 고객ID
     * @return 고객 정보(1건)
     * @throws Exception
     */
    public Map<String, Object> getCustBasiConsInfo(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeMainPopDAO.getCustBasiConsInfo(pmParam);
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
        
        nResult = DlwNewTypeMainPopDAO.insertCons(pmParam);
                
        /*
        String sSeq = "";
        String sFistCallIncoYn = StringUtils.defaultString((String)pmParam.get("fist_call_inco_yn")); // 최초 전화 인입시 상담저장 여부
        int nResult = 0;

        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sMstRegDm = "";	// 대명 상담MNG 등록일시 yyyy-MM-dd HH:mm:ss.SSS
        String sDtptRegDm = ""; // 대명 상담DTL 등록일시 yyyy-MM-dd HH:mm:ss.SSS
        String sMstKey = "";	// 대명 상담MNG 키
        String sDtptKey = "";	// 대명 상담DTL 키
        String sGubn = "";		// 상담상세 구분-상담유형별

        String sConsMemoTit = StringUtils.defaultString((String)pmParam.get("cons_memo_tit")); 		// 제목
        String sConsMemoCntn = StringUtils.defaultString((String)pmParam.get("cons_memo_cntn")); 	// 내용
        String sCnslCd = StringUtils.defaultString((String)pmParam.get("cnsl_cd")); // COM_CD_GRP 테이블
        if ("".equals(sCnslCd)) {
            pmParam.put("cnsl_cd", "01"); // 기타
        }

        if ("".equals(sConsMemoTit)) {
            pmParam.put("cons_memo_tit", "인우상담 정보");
        }

        // 상담테이블 MEMBER / MEM_PROD_ACCNT 정보
        Map<String, Object> mCust = DlwNewTypeMainPopDAO.getCustBasiConsInfo(pmParam);
        pmParam.put("mem_nm", 		mCust.get("mem_nm"));
        pmParam.put("sex", 			mCust.get("sex"));
        pmParam.put("brth_mon_day", mCust.get("brth_mon_day"));
        pmParam.put("ci_val", 		mCust.get("ci_val"));
        pmParam.put("idn_no", 		mCust.get("idn_no"));
        pmParam.put("home_tel", 	mCust.get("home_tel"));
        pmParam.put("cell", 		mCust.get("cell"));
        pmParam.put("wrpl_tel", 	mCust.get("wrpl_tel"));
        pmParam.put("home_zip", 	mCust.get("home_zip"));
        pmParam.put("home_addr", 	mCust.get("home_addr"));
        pmParam.put("home_addr2", 	mCust.get("home_addr2"));
        pmParam.put("credit_rating", mCust.get("credit_rating"));
        pmParam.put("crdt_mng_no", 	mCust.get("crdt_mng_no"));
        pmParam.put("email", 		mCust.get("email"));
        pmParam.put("emple_no", 	mCust.get("emple_no"));
        pmParam.put("emple_nm", 	mCust.get("emple_nm"));
        pmParam.put("join_dt", 		mCust.get("join_dt"));
        pmParam.put("dept_cd", 		mCust.get("dept_cd"));
        pmParam.put("note", 		mCust.get("note"));

        System.out.println("-------------------------------->> ");
        CommonUtils.printLog(pmParam);
		*/
        // 콜 인입시에는 대명DB에 상담정보를 남기지 않음
        /* 2016/11/22 엔컴이력 INSERT 제외
        if (!"Y".equals(sFistCallIncoYn)) {
            // 상담유형 - 엔컴CNSL_DTL GUBN
            sGubn = StringUtils.defaultString((String) consTypDAO.getConsTypGubn(pmParam));
            if ("".equals(sGubn)) {
                sGubn = "80";
                pmParam.put("gubn", "80"); // COM_CD_GRP 테이블 기타
            } else {
                pmParam.put("gubn", sGubn);
            }

            // 대명 상담 등록 시간 설정
            sMstRegDm = sdfDate.format(new Date());
            pmParam.put("mst_reg_dm", sMstRegDm);
            pmParam.put("mst_upd_dm", sMstRegDm);

            nResult = 1; //dlwConsDAO.insertCons(pmParam);

            sSeq = (String) pmParam.get("skey");
            String tmpStr[] = sSeq.split("\\|");
            pmParam.put("seq", tmpStr[0]);
            pmParam.put("cnsl_seq", tmpStr[1]);

            if (nResult > 0) {
                // 대명 상담상세 등록 시간 설정
                sDtptRegDm = sdfDate.format(new Date());
                pmParam.put("dtpt_reg_dm", sDtptRegDm);
                pmParam.put("dtpt_upd_dm", sDtptRegDm);

                // 엔컴 상담내역 등록 제거(2016/11/21)
                //dlwConsDAO.insertConsDtpt(pmParam);
            }
        }
        */

        // 신규등록시 대명키 생성
        // mem_no seq clsn_seq accnt_no reg_man reg_dm
        // mem_no seq clsn_seq gubn     reg_man reg_dm
    	/*
        if (!"".equals(sSeq)) { // 대명 키 존재
            sMstKey = pmParam.get("mem_no")+"^"+pmParam.get("seq")+"^"+pmParam.get("cnsl_seq")+"^"+pmParam.get("accnt_no")+"^"+pmParam.get("rgsr_id")+"^"+sMstRegDm;
            sDtptKey = pmParam.get("mem_no")+"^"+pmParam.get("seq")+"^"+pmParam.get("cnsl_seq")+"^"+sGubn+"^"+pmParam.get("rgsr_id")+"^"+sDtptRegDm;
        }
        pmParam.put("mst_key", sMstKey);
        pmParam.put("dtpt_key", sDtptKey);

        if (!"".equals(sConsMemoTit)) {
            pmParam.put("cons_memo_cntn", sConsMemoTit + "\n" + sConsMemoCntn);
        }

        nResult = DlwNewTypeMainPopDAO.insertCons(pmParam);

        if (nResult > 0) {
            sKey = (String) pmParam.get("consno");
            DlwNewTypeMainPopDAO.insertConsHstr(pmParam);
        }
		*/
        return sKey;
    }
    
    /**
     * 온라인 가입회원 구좌번호로 카드정보조회
     *
     * @param pmParam 검색 조건
     * @return 부서 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwOnlineMemberCardInfo(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeMainPopDAO.getDlwOnlineMemberCardInfo(pmParam);
    }
    
    /**
     * 온라인 가입회원 구좌번호로 계좌정보조회
     *
     * @param pmParam 검색 조건
     * @return 부서 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwOnlineMemberCmsInfo(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeMainPopDAO.getDlwOnlineMemberCmsInfo(pmParam);
    }
    
    /**
     * CMS정보 Idn_no 추출
     *
     * @param pmParam 검색 조건
     * @return 등록이력 정보
     * @throws Exception
     */
    public String getNcaIdnNo(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeMainPopDAO.getNcaIdnNo(pmParam);
    }
    
    /**
     * 고객-구좌 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 고객-구좌 정보의 검색 건수
     * @throws Exception
     */
    public int getDlwCustAcntCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) DlwNewTypeMainPopDAO.getDlwCustAcntCount(pmParam);
    }

    /**
     * 고객-구좌 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 고객-구좌 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwCustAcntList(Map<String, ?> pmParam) throws Exception {
    	String sPay_mthd = pmParam.get("pay_mthd").toString();
    	List<Map<String, Object>> rtnList  = null;
    	if(sPay_mthd.equals("CMS")){
    		rtnList  = DlwNewTypeMainPopDAO.getDlwCustAcntList(pmParam);
    	} else {
    		rtnList  = DlwNewTypeMainPopDAO.getDlwCustAcntListCard(pmParam);
    	}
        return rtnList;
    }
      
    /**
     * 고객-구좌 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 고객-구좌 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwCustLongTermList(Map<String, ?> pmParam) throws Exception {
    	String sPay_mthd = pmParam.get("pay_mthd").toString();
    	List<Map<String, Object>> rtnList  = null;
    	rtnList  = DlwNewTypeMainPopDAO.getDlwCustLongTermList(pmParam);
        return rtnList;
    }
    
    /**
     * 사원 상세정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 사원 상세정보
     * @throws Exception
     */
    public Map<String, Object> getDlwEmplDtpt(String psParam) throws Exception {
        Map<String, Object> pmParam = new HashMap<String, Object>();
        pmParam.put("emple_no", psParam);
        return DlwNewTypeMainPopDAO.getDlwEmplDtpt(pmParam);
    }
    
    /**
     * 코드 정보의 검색 수를 반환한다.
     *
     * @param pmParam 검색 조건
     * @return 코드 정보의 검색 건수
     * @throws Exception
     */
    public int getDlwCdCount(Map<String, ?> pmParam) throws Exception {
        return (Integer) DlwNewTypeMainPopDAO.getDlwCdCount(pmParam);
    }

    /**
     * 코드 정보를 검색한다.
     *
     * @param pmParam 검색 조건
     * @return 코드 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwCdList(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeMainPopDAO.getDlwCdList(pmParam);
    }
    
    /**
     * 특수회원 카운트조회
     * 정승철
     * 20181019
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getCntSpecialAcntList(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeMainPopDAO.getCntSpecialAcntList(pmParam);
    }

    /**
     * 특수회원 조회
     * 정승철
     * 20181018
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getSpecialAcntList(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeMainPopDAO.getSpecialAcntList(pmParam);
    }
    
    /**
     * 특수회원 입력체크
     * 정승철
     * 20181113
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public int getChkSpecialAcntList(Map<String, ?> pmParam) throws Exception {
        return DlwNewTypeMainPopDAO.getChkSpecialAcntList(pmParam);
    }

    /**
     * 특수회원 입력
     * 정승철
     * 20181019
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public void insertExtSpecial(Map<String, ?> pmParam) throws Exception {

        DlwNewTypeMainPopDAO.insertExtSpecial(pmParam);
    }

    /**
     * 특수회원 수정
     * 정승철
     * 20181019
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public void updateExtSpecial(Map<String, ?> pmParam) throws Exception {

        DlwNewTypeMainPopDAO.updateExtSpecial(pmParam);
    }


    /**
     * 특수회원 삭제
     * 정승철
     * 20181019
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public void deleteExtSpecial(Map<String, ?> pmParam) throws Exception {

        DlwNewTypeMainPopDAO.deleteExtSpecial(pmParam);
    }
    
    /**
     * CMS 정보 업데이트
     * 
     * 
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public void updateCmsInfo(Map<String, ?> pmParam) throws Exception {

        DlwNewTypeMainPopDAO.updateCmsInfo(pmParam);
    }    
    
    /**
     * Card 정보 업데이트
     * 
     * 
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public void updateCardInfo(Map<String, ?> pmParam) throws Exception {

        DlwNewTypeMainPopDAO.updateCardInfo(pmParam);
    }      
    
    /** ================================================================
     * 날짜 : 20190924
     * 이름 : 송준빈
     * 추가내용 : 고객 자유결제 NICE 전문 발송시 필요한 기타 데이터 조회
     * 대상 테이블 : LF_DMUSER.MEM_PROD_ACCNT, LF_DMUSER.MEMBER
     * ================================================================
     * */
	public List<Map<String, Object>> getNiceProtocolMemberInfo(Map<String, Object> pmParam) {
		return DlwNewTypeMainPopDAO.getNiceProtocolMemberInfo(pmParam);
	}

	/** ================================================================
     * 날짜 : 20190924
     * 이름 : 송준빈
     * 추가내용 : 자유결제 카드 요청, 결과데이터 INSERT
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ, LF_DMUSER.TB_MEMBER_WDRW_RESULT
     * ================================================================
     * */
	public int insertFreeRealTimeCardResult(Map<String, ?> pmParam)throws Exception {
		return DlwNewTypeMainPopDAO.insertFreeRealTimeCardResult(pmParam);
	}

	/** ================================================================
     * 날짜 : 20190924
     * 이름 : 송준빈
     * 추가내용 : 전환결제 취소
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_REQ_REFUND
     * ================================================================
     * */
	public int sendCancelNicePayment(Map<String, Object> pmParam) throws Exception {
		return DlwNewTypeMainPopDAO.sendCancelNicePayment(pmParam);
	}

	/** ================================================================
     * 날짜 : 20210715
     * 이름 : 송준빈
     * 추가내용 : 우편발송용 고객 조회수
     * 대상 테이블 : LF_DMUSER.TB_CERTF_SEND_ADDR
     * ================================================================
     * */
	public int getSendCertfAccntAddrCount(Map<String, ?> pmParam) throws Exception {
		return DlwNewTypeMainPopDAO.getSendCertfAccntAddrCount(pmParam);
	}
	
	/** ================================================================
     * 날짜 : 20210715
     * 이름 : 송준빈
     * 추가내용 : 우편발송용 고객 조회 리스트
     * 대상 테이블 : LF_DMUSER.TB_CERTF_SEND_ADDR
     * ================================================================
     * */
	public List<Map<String, Object>> getSendCertfAccntAddrList(Map<String, ?> pmParam) throws Exception {
		return DlwNewTypeMainPopDAO.getSendCertfAccntAddrList(pmParam);
	}
	
	/** ================================================================
     * 날짜 : 20210715
     * 이름 : 송준빈
     * 추가내용 : 우편발송 고객 기본정보 불러오기 
     * 대상 테이블 : LF_DMUSER.MEM_PROD_ACCNT, LF_DMUSER.MEMBER
     * ================================================================
     * */
	public List<Map<String, Object>> getMemberAccntDtlInfo(Map<String, ?> pmParam) throws Exception {
		return DlwNewTypeMainPopDAO.getMemberAccntDtlInfo(pmParam);
	}

	/** ================================================================
     * 날짜 : 20210715
     * 이름 : 송준빈
     * 추가내용 : 우편발송용 고객정보 수정
     * 대상 테이블 : LF_DMUSER.TB_CERTF_SEND_ADDR
     * ================================================================
     * */
	public int updateSendCertfAccntAddrList(Map<String, ?> pmParam) throws Exception {
		return DlwNewTypeMainPopDAO.updateSendCertfAccntAddrList(pmParam);
	}

	/** ================================================================
     * 날짜 : 20210715
     * 이름 : 송준빈
     * 추가내용 : 우편발송용 고객정보 입력
     * 대상 테이블 : LF_DMUSER.TB_CERTF_SEND_ADDR
     * ================================================================
     * */
	public int insertSendCertfAccntAddrList(Map<String, ?> pmParam) throws Exception {
		return DlwNewTypeMainPopDAO.insertSendCertfAccntAddrList(pmParam);
	}

	/** ================================================================
     * 날짜 : 20210715
     * 이름 : 송준빈
     * 추가내용 : 우편발송용 고객정보 삭제
     * 대상 테이블 : LF_DMUSER.TB_CERTF_SEND_ADDR
     * ================================================================
     * */
	public int deleteSendCertfAccntAddrList(Map<String, ?> pmParam) throws Exception {
		return DlwNewTypeMainPopDAO.deleteSendCertfAccntAddrList(pmParam);
	}
	
	
	/**
     * 고객-구좌 정보를 검색한다.(복사_20220822)
     *
     * @param pmParam 검색 조건
     * @return 고객-구좌 정보 목록
     * @throws Exception
     */
    public List<Map<String, Object>> getDlwNewTypeCustLongTermList(Map<String, ?> pmParam) throws Exception {
    	String sPay_mthd = pmParam.get("pay_mthd").toString();
    	List<Map<String, Object>> rtnList  = null;
    	rtnList  = DlwNewTypeMainPopDAO.getDlwNewTypeCustLongTermList(pmParam);
        return rtnList;
    }
    
    /**
     * Card 정보 업데이트(복사_20220822)
     * 
     * 
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public void updateNewTypeCardInfo(Map<String, ?> pmParam) throws Exception {

        DlwNewTypeMainPopDAO.updateNewTypeCardInfo(pmParam);
    }
    
    public String deleteDlwNewTypeCardAnxtSrvc(Map<String, Object> pmParam) throws Exception {
		String rslt = "delete";
	    DlwNewTypeMainPopDAO.insertDlwNewTypeCardAnxtSrvc(pmParam);	  // 카드 로그정보등록	    
	    DlwNewTypeMainPopDAO.deleteDlwNewTypeCardMember(pmParam);     // 카드 회원정보 해지   
		return rslt;
    }
    
    /**
     * 대명라이프웨이의 CMS 부가서비스(신규,해지)를 등록한다.(복사_20220822)
     * 이니시스 프로세스
     * @param pmParam 검색 조건
     * @return 등록결과
     * @throws Exception
     */
    public String insertDlwNewTypeInicisCardAnxtSrvc(Map<String, Object> pmParam) throws Exception {
        String rslt = "";
                
        //관계 값이 본인이 아닐 경우 승인값 대리납으로 변경
        String relVal = pmParam.get("reltn").toString();
        
        String sCardYn = pmParam.get("card_yn").toString();
        String billKeyArr[] = new String[5];	//빌키
        
        
        /***********************************************************************************************************
        * 카드 신청 등록 시 본인 확인 부분 ( 생년월일 + 카드번호등으로 본인 확인 )
        /***********************************************************************************************************/        
        String sPayFilePath = basVlService.getBasVlAsString("pay_file_path", (String) pmParam.get("cntr_cd"));
                     
        ParamUtils.addCenterParam(pmParam);      
        
    	INICISPay inicisPay = new INICISPay();
 		inicisPay.setInicisKey("hmPLjIdyekyylSx9");
 		inicisPay.setInicisiv("fS7oGOerwBsEcQ==");

		inicisPay.setData("mid", "daemyungT1");

		inicisPay.setData("clientIp", "127.0.0.1");
		inicisPay.setData("moid", (String)pmParam.get("accnt_no"));
		inicisPay.setData("goodName", "상품명");     
		inicisPay.setData("buyerName", "구매자명");
		//inicisPay.setData("buyerEmail", (String)pmParam.get("email"));
		inicisPay.setData("buyerEmail", "dongjin.lim@daemyung.com");
		inicisPay.setData("buyerTel", (String)pmParam.get("cell"));
		inicisPay.setData("cardNumber", (String)pmParam.get("card_no"));
		inicisPay.setData("cardExpire", (String)pmParam.get("exp_year")+(String)pmParam.get("exp_mon") );
		inicisPay.setData("regNo", (String)pmParam.get("idn_no"));
		//inicisPay.setData("cardPw", "00");

		inicisPay.authBillkey();

		String resultCode = inicisPay.getData("resultCode");
		String resultMsg = inicisPay.getData("resultMsg");
		String tid = inicisPay.getData("tid");
		String billKey = inicisPay.getData("billKey");
		
		//카드사 코드 
		String strCardCd= inicisPay.getData("cardCode");
		
		//체크카드 여부 (0:신용, 1:체크, 2:기프트카드)
		String strCheckFlg = inicisPay.getData("checkFlg");
		

        // 정상여부
        if(!"00".equals(resultCode) && (!"5012".equals(resultCode))) {
            //정상결과 아닐경우 결과코드 출력
            rslt = "[NG] " + resultMsg;            
            return rslt;
        }
        
        pmParam.put("ini_bid", billKey);
        
        if(sCardYn.equals("NONE")){
        	DlwNewTypeMainPopDAO.insertDlwNewTypeCardAnxtSrvc(pmParam); // CMS 정보등록
        	DlwNewTypeMainPopDAO.insertDlwNewTypeCardMmbr(pmParam);     // CMS 회원 신규등록
        } else {
        	DlwNewTypeMainPopDAO.insertDlwNewTypeCardAnxtSrvc(pmParam); // CMS 정보등록
        	DlwNewTypeMainPopDAO.updateDlwNewTypeCardMmbr(pmParam);     // CMS 기존 회원정보 수정        	        	
        }
        
        //관계 값이 본인이 아닐 경우 승인값 대리납으로 변경
        if (!relVal.equals("20")){
        	DlwNewTypeMainPopDAO.updateNewTypeHpCall(pmParam); 
        	DlwNewTypeMainPopDAO.insertNewTypeHpCallHist(pmParam); // 승인 히스토리 등록
        }
        
        rslt = "[OK] 정상적으로 카드신청되었습니다.";
        
        return rslt;
    }
    
    /**
     * 장기할부 카드확인 및 저장 METHOD(복사_20220822)
     * 이니시스 프로세스 (KB프로세스)
     * @param pmParam 검색 조건
     * @return 등록결과
     * @throws Exception
     */

    public Map<String, Object> getDlwNewTypeInicisLongtermCardCheck(Map<String,Object > pmParam) throws Exception {
		Map<String, Object> rtnParam = new HashMap<String, Object>();
		
		String sCardYn = pmParam.get("card_yn").toString();  //넘어온 등록상태 (최초등록, 등록삭제 후 업데이트 카드 등록)
		String cardMid = pmParam.get("mid_info_1").toString();  //KB카드 체크시 mid변경 
		String strCardNo = pmParam.get("card_no").toString();		// 카드번호
		String strCardExpr =  (String)pmParam.get("exp_year")+(String)pmParam.get("exp_mon");	// 유효일자
		
		String resultCode = "";		//카드전송 결과코드
		String resultMsg = "";		//카드전송 결과메세지
		String tid = "";					//결과TID(체크, 빌키)
		String billKey = "";			//빌키
		String kbCheck = "";
		
    	INICISPay inicisPay = new INICISPay();
 		inicisPay.setInicisKey("hmPLjIdyekyylSx9");
 		inicisPay.setInicisiv("fS7oGOerwBsEcQ==");

		//inicisPay.setData("mid", "daemyungT1");	// - daemyunhn1 (싱글용 하나카드) - daemyunhn2 (더블용 하나카드)
 		inicisPay.setData("mid", cardMid);				// - daemyunhn1 (싱글용 하나카드) - daemyunhn2 (더블용 하나카드)
		inicisPay.setData("clientIp", "127.0.0.1");
		inicisPay.setData("moid", (String)pmParam.get("accnt_no"));
		inicisPay.setData("goodName", "상품명");     
		inicisPay.setData("buyerName", "구매자명");
		//inicisPay.setData("buyerEmail", (String)pmParam.get("email"));
		inicisPay.setData("buyerEmail", "dongjin.lim@daemyung.com");
		inicisPay.setData("buyerTel", (String)pmParam.get("cell"));
		inicisPay.setData("cardNumber", strCardNo);
		inicisPay.setData("cardExpire", strCardExpr);
		inicisPay.setData("regNo", (String)pmParam.get("idn_no"));
		//inicisPay.setData("cardPw", "00");
		
		inicisPay.authBillkey();
		
		//카드사 코드 		
		String strCardCd= inicisPay.getData("cardCode");				
		//체크카드 여부 (0:신용, 1:체크, 2:기프트카드)
		String strCheckFlg = inicisPay.getData("checkFlg");
		String rtnMsg = "";

		resultCode = inicisPay.getData("resultCode");
		resultMsg = inicisPay.getData("resultMsg");
		tid = inicisPay.getData("tid");
		billKey = inicisPay.getData("billKey");
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>> 11111: " + resultMsg);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>> 11111: " + resultCode);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>> 11111: " + strCardCd);
						
        // 빌키 정상여부
        if(!"00".equals(resultCode) && (!"5012".equals(resultCode))) {
            //정상결과 아닐경우 결과코드 출력
            rtnMsg = "[NG] " + resultMsg;       
        } else {
        	// 하나카드가 아닐경우 결과코드 출력
        	if (strCardCd.equals("06")){
        		
        		//카드정보 초기화 (암호정보이슈로 인한 초기화)
        		/*
        		inicisPay.setData("cardNumber", strCardNo);
        		inicisPay.setData("cardExpire", strCardExpr);
        		
        		inicisPay.chkCard();
        		
        		resultCode = inicisPay.getData("resultCode");
        		resultMsg = inicisPay.getData("resultMsg");
        		tid = inicisPay.getData("tid");
        		hanaCheck = inicisPay.getData("affiliateFlg");
        		*/
        		
        		kbCheck = "1";
        	} else {        		        		        	
        		resultMsg = "[NG] KB카드 회원이 아닙니다.!!" ;     
     	        rtnParam.put("rtn_msg", resultMsg);		     
    	        return rtnParam;        		
        	}        	
        }
                               				
		// 카드가 정상적이지 않음
		if (!resultCode.equals("00") && !kbCheck.equals("1") ){
	        rtnParam.put("rtn_msg", resultMsg);		     
	        return rtnParam;
		}
								
		pmParam.put("card_cd", strCardCd);        		        	        	
        pmParam.put("ini_bid", billKey);            
                        
        if(sCardYn.equals("NONE")){        
        	DlwNewTypeMainPopDAO.insertDlwNewTypeCardAnxtSrvc(pmParam); // CMS 정보등록
        	DlwNewTypeMainPopDAO.insertDlwNewTypeCardMmbr(pmParam);     // CMS 회원 신규등록
        } else {            	
        	DlwNewTypeMainPopDAO.insertDlwNewTypeCardAnxtSrvc(pmParam); // CMS 정보등록
        	DlwNewTypeMainPopDAO.updateDlwNewTypeCardMmbr(pmParam);     // CMS 기존 회원정보 수정        	        	
        }        	
        
        rtnParam.put("rtn_msg", "[OK]: 정상적으로 장기할부 카드 등록되었습니다.");	

		return rtnParam;
     }
}
