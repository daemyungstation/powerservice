package powerservice.business.dlw.web;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import kr.co.nicepay.module.lite.NicePayWebConnector;
import kr.co.nicevan.nicepay.adapter.web.NicePayHttpServletRequestWrapper;
import kr.co.nicevan.nicepay.adapter.web.NicePayWEB;
import kr.co.nicevan.nicepay.adapter.web.dto.WebMessageDTO;
import kr.co.nicevan.nicepay.adaptor.etc.HttpServletRequestMock;
import kr.co.nicevan.nicepay.adaptor.etc.HttpServletResponseMock;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tobesoft.xplatform.data.DataSet;

import powerservice.business.bean.UserSession;
import powerservice.business.common.service.CommonService;
import powerservice.business.dlw.service.DataAthrQuryService;
import powerservice.business.dlw.service.DlwMemPayMthdService;
import powerservice.business.sys.service.BasVlService;
import powerservice.common.util.CommonUtils;
import powerservice.common.util.INICISPay;
import powerservice.common.util.NicePayProcess;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;


/**
 * 대명 고객 납입방법 관리
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/02/03
 * @프로그램ID DlwCust
 */
@Controller
@RequestMapping(value="/dlw/memPayMthd")
public class DlwMemPayMthdController {
	
	@Resource
    private DlwMemPayMthdService DlwMemPayMthdService;
	
	@Resource
    private DataAthrQuryService dataAthrQuryService;
	
	@Resource
    private CommonService commonService;
	
	@Resource
    private BasVlService basVlService;
	
	
	/**
     * 고객-구좌 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/getMemPayMthdList")
    public ModelAndView getMemPayMthdList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap listMap2 = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();  

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();
            
            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
                
                // CMS 조회인지 카드 조회인지 구분
                //String svcid = (String) mapInVar.get("pay_mthd");                
                //hmParam.put("pay_mthd", svcid);
                                
                List<Map<String, Object>> mList = DlwMemPayMthdService.getMemPayMthdList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
                
                List<Map<String, Object>> mList2 = DlwMemPayMthdService.getDlwPymnHstrList(hmParam);
                listMap2.setRowMaps(mList2);
                mapOutDs.put("ds_output2", listMap2);
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
            
            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);
        }

        return modelAndView;
    }
    
    /**
     * 상담 정보를 등록/수정한다. (대명 - CMS/카드 부가서비스 등록시)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/updateCard")
    public ModelAndView updateCardMemInfo(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input"); 				// 변경후 dataset
            DataSetMap listInOldDs = (DataSetMap)mapInDs.get("ds_input_old"); 				// 변경후 dataset

            if (listInDs.size() > 0) {
                Map <String, Object> hmParam = listInDs.get(0);
                Map <String, Object> hmParam_old = listInOldDs.get(0);
                
                //세션
                ParamUtils.addSaveParam(hmParam);
                                                
                // 카드 변경 정보 업데이트
                DlwMemPayMthdService.updateCardMemInfo(hmParam);
                
                /**************************************************상담등록 필드 구성*********************************************************/ 
                String chgText = "[CARD] 이체일자가 변경되었습니다. [" + hmParam_old.get("pay_dt").toString() + " >> " + hmParam.get("pay_dt").toString() +  "]" ;

                // 상담유형 1, 2, 3
                hmParam.put("cons_typ1_cd", "CT01010000");		//회원정보
                hmParam.put("cons_typ2_cd", "CT01010100");		//결제정보
                hmParam.put("cons_typ3_cd", "CT01010102");		//결제수단변경
                hmParam.put("cons_memo_tit", chgText);
                /*******************************************************************************************************************************/
                
                // 상담 등록
                saveConsdlw(hmParam); 
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
            
            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);
        }
        return modelAndView;
    }
    
    /**
     * CMS-카드의 등록된 정보 삭제 처리
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/deleteAccnt")
    public ModelAndView deleteCardMemInfo(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map <String, Object> mapInVar     = xpDto.getInVariableMap();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input"); 				// 변경후 dataset
            String sPayMthd = (String)mapInVar.get("pay_mthd");
                        
            String chgText = "";
            


            if (listInDs.size() > 0) {
                Map <String, Object> hmParam = listInDs.get(0);
                
                //세션
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("emple_no", hmParam.get("rgsr_id"));
                hmParam.put("reg_man", hmParam.get("rgsr_id"));
                hmParam.put("upd_man", hmParam.get("rgsr_id"));
                
                if(sPayMthd.equals("CARD")){                	
                	DlwMemPayMthdService.deleteDlwAccntCard(hmParam);
                	chgText = "[CARD] 정보가 삭제 되었습니다.";
                } else {
                	DlwMemPayMthdService.deleteDlwAccntCms(hmParam);
                	chgText = "[CMS] 정보가 삭제 되었습니다.";
                }                  
                               
                /**************************************************상담등록 필드 구성*********************************************************/ 
                //String chgText = "[CARD] 이체일자가 변경되었습니다. [" + hmParam_old.get("ichae_dt").toString() + " >> " + hmParam.get("ichae_dt").toString() +  "]" ;

                // 상담유형 1, 2, 3
                //20220809 요청으로 삭제시 상담이력 등록 안함
//                hmParam.put("cons_typ1_cd", "CT01010000");		//회원정보
//                hmParam.put("cons_typ2_cd", "CT01010100");		//결제정보
//                hmParam.put("cons_typ3_cd", "CT01010102");		//결제수단변경
//                hmParam.put("cons_memo_tit", chgText);
                /*******************************************************************************************************************************/
                
                // 상담 등록
//                saveConsdlw(hmParam); 
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
            
            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);
        }
        return modelAndView;
    }
    
    /**
     * 이니시스 장기할부 카드 체크
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/saveCard_INI")
    public ModelAndView insertDlwInicisCardCheck(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();
            
            Map <String, Object> mapCardCheck     = null; 
            
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            hmParam = listInDs.get(0);

            //세션
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("emple_no", hmParam.get("rgsr_id"));
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            hmParam.put("upd_man", hmParam.get("rgsr_id"));
            
            // 카드 회원 여부
            String cardMemYN = "";	// CARD 회원 여부
            String cardMsg = "";		// 결과 전달 메세지
            
            //cardMemYN  = hmParam.get("card_yn").toString();
            String syyyy = hmParam.get("yy").toString();
            String smm = hmParam.get("mm").toString();
            String expr_Dt = syyyy + smm;
           
            // 카드 신청 시 카드 혜택이 끝난 체크 대상자는 강제로 납입회차를 100으로 늘린다. (납입회차 36기준으로 dmlife001 가맹번호 빌키 생성)
            //hmParam.put("pay_no", sBenefitYn);	
                        
            //이니시스 여부 등록       
            hmParam.put("ini_yn", "Y");
            
        	hmParam.put("inv_tg_dt", hmParam.get("pay_dt"));					// 이체일자
            hmParam.put("idn_no", hmParam.get("card_birth"));							// 생년월일
            hmParam.put("exp_year", syyyy);											// 만기 연도            
            hmParam.put("exp_mon", smm);											// 만기 월		
            hmParam.put("exp_dt", expr_Dt);											// 이체일자
            
            System.out.println(hmParam);
            
            mapCardCheck = getDlwInicisCardCheck(hmParam);	//이니시스 카드 체크 진행
            
            //결과 메세지 리턴하기
            mapOutVar.put("gv_rslMsg", mapCardCheck.get("rtn_msg"));
                                              
            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
            
            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);
        }        
        return modelAndView;
    }
    
    /**
     * 이니시스 결과 데이터 카드등록
     * 이니시스 프로세스
     * @param pmParam 검색 조건
     * @return 등록결과
     * @throws Exception
     */

    public Map<String, Object> getDlwInicisCardCheck(Map<String,Object > pmParam) throws Exception {
		Map<String, Object> rtnParam = new HashMap<String, Object>();
		
		//String sCardYn = pmParam.get("card_yn").toString();  //넘어온 등록상태 (최초등록, 등록삭제 후 업데이트 카드 등록)
		//String hanaMid = pmParam.get("mid_info_1").toString();  //하나카드 체크시 mid변경 
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
		inicisPay.setData("mid", "daemyungT1");	// - daemyunhn1 (싱글용 하나카드) - daemyunhn2 (더블용 하나카드)
 		//inicisPay.setData("mid", hanaMid);				// - daemyunhn1 (싱글용 하나카드) - daemyunhn2 (더블용 하나카드)
		inicisPay.setData("clientIp", "127.0.0.1");
		inicisPay.setData("moid", (String)pmParam.get("accnt_no"));
		inicisPay.setData("goodName", "상품명");     
		inicisPay.setData("buyerName", "구매자명");
		inicisPay.setData("buyerEmail", "dongjin.lim@daemyung.com");
		inicisPay.setData("buyerTel", (String)pmParam.get("cell"));
		inicisPay.setData("cardNumber", strCardNo);
		inicisPay.setData("cardExpire", strCardExpr);
		inicisPay.setData("regNo", (String)pmParam.get("idn_no"));
		
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
								                               				
		// 카드가 정상적이지 않음
		if (!resultCode.equals("00") ){
	        rtnParam.put("rtn_msg", resultMsg);		     
	        return rtnParam;
		}
								
		pmParam.put("card_cd", strCardCd);
		pmParam.put("card_gubun", strCheckFlg);
        pmParam.put("ini_bid", billKey);
        
   	 // CARD 회원이 등록된 적이 있거나(DEL_FG = 'Y') 등록되지 않음 (NONE)
        pmParam.put("app_cl", "1");											// 3: 해지, 1: 신규, 7 : 보류해지               
    	DlwMemPayMthdService.insertDlwAccntCard(pmParam);     
                
        rtnParam.put("rtn_msg", "[OK]: 정상적으로 카드 등록되었습니다.");	
        
        /**************************************************상담등록 필드 구성*********************************************************/ 
//        String chgText = "[CARD] 결제 정보가 등록되었습니다.(이니시스)" ;

        // 상담유형 1, 2, 3
        //20220809 카드등록 상담 이력 등록 안함
//        pmParam.put("cons_typ1_cd", "CT01010000");		//회원정보
//        pmParam.put("cons_typ2_cd", "CT01010100");		//결제정보
//        pmParam.put("cons_typ3_cd", "CT01010102");		//결제수단변경
//        pmParam.put("cons_memo_tit", chgText);
        /*******************************************************************************************************************************/
        
        // 상담 등록
//        saveConsdlw(pmParam); 

		return rtnParam;
     }

    
    /**
     * 대명스테이션 카드 부가서비스(신규,해지)를 등록한다. (나이스 전용)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/saveCard")
    public ModelAndView insertDlwCardAnxtSrvc(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        Map<String, Object> hmParam2 = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();
                        
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            hmParam = listInDs.get(0);
            
            Map <String, Object> mapCardCheck     = null; 

            //세션
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("emple_no", hmParam.get("rgsr_id"));
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            hmParam.put("upd_man", hmParam.get("rgsr_id"));                        
            
            // 카드 회원 여부
            String cardMemYN = "";	// CARD 회원 여부            
            String cardMsg = "";		// 결과 전달 메세지
            String rslt = "";			//승인 전달 메세지
            
            String syyyy = hmParam.get("yy").toString();
            String smm = hmParam.get("mm").toString();
            String expr_Dt = syyyy + smm;
                                   
            hmParam.put("ini_yn", "N");            
        	hmParam.put("inv_tg_dt", hmParam.get("pay_dt"));					// 이체일자
            hmParam.put("idn_no", hmParam.get("card_birth"));							// 생년월일
            hmParam.put("exp_year", syyyy);											// 만기 연도            
            hmParam.put("exp_mon", smm);											// 만기 월		
            hmParam.put("exp_dt", expr_Dt);											// 이체일자            
            hmParam.put("cell", "");													// 전화번호(기초데이터)
            hmParam.put("email", "");												// 이메일(기초데이터)
                        
            /***********************************************************************************************************
             * 카드 신청 등록 시 본인 확인 부분 ( 생년월일 + 카드번호등으로 본인 확인 )
             /***********************************************************************************************************/
             NicePayProcess nicepay = new NicePayProcess();        
             String sPayFilePath = basVlService.getBasVlAsString("pay_file_path", "CCA");
             
             nicepay.setNicePayHome(sPayFilePath+"/web_site/nicelog");
             //nicepay.setNicePayHome("c:/web_site/nicelog");
             nicepay.setGoodsName((String)hmParam.get("prod_cd"));
             nicepay.setMoid((String)hmParam.get("accnt_no"));
             nicepay.setBuyerName("");
             nicepay.setBuyerTel("");
             nicepay.setBuyerEmail("");
             nicepay.setCardNumber((String)hmParam.get("card_no"));
             nicepay.setExpYear((String)hmParam.get("exp_year"));
             nicepay.setExpMonth((String)hmParam.get("exp_mon"));
             nicepay.setIdno((String)hmParam.get("idn_no"));

             //승인요청
             NicePayWebConnector connector = nicepay.requestCardMemAuth();
             String resultCode = connector.getResultData("ResultCode");
             String resultMsg = connector.getResultData("ResultMsg");
             String cardCode = connector.getResultData("CardCode");
             String cardName = connector.getResultData("CardName");
             
             // 정상여부
             if(!"0000".equals(resultCode) && (!"5012".equals(resultCode))) {
                 //정상결과 아닐경우 결과코드 출력
            	 cardMsg = "[NG] " + resultMsg;   
             } else {            	 
                 String billKeyArr[] = new String[5];
                 billKeyArr = DlwMemPayMthdService.billKeyCreate(hmParam);
                             
                 if(billKeyArr[0].equals("F100")) {
                 	hmParam.put("bid", billKeyArr[1]);
                 	hmParam.put("card_cd", billKeyArr[3]);            	
         			hmParam.put("app_cl", "1");											// 3: 해지, 1: 신규, 7 : 보류해지
         			hmParam.put("ini_yn", "N");
         			hmParam.put("card_gubun", "0");
         			
         			DlwMemPayMthdService.insertDlwAccntCard(hmParam);
         			
         			cardMsg = "[OK]: 정상적으로 카드 등록되었습니다.(NICE)";	
         			
                    /**************************************************상담등록 필드 구성*********************************************************/ 
//                    String chgText = "[CARD] 결제 정보가 등록되었습니다.(나이스)"; 
                    
                    // 상담유형 1, 2, 3
         			//20220809 카드등록 상담 이력 등록 안함
//                    hmParam.put("cons_typ1_cd", "CT01010000");		//회원정보
//                    hmParam.put("cons_typ2_cd", "CT01010100");		//결제정보
//                    hmParam.put("cons_typ3_cd", "CT01010102");		//결제수단변경
//                    hmParam.put("cons_memo_tit", chgText);
                    /*******************************************************************************************************************************/
                    
                    // 상담 등록
//                    saveConsdlw(hmParam); 

                 } else {
         			cardMsg = "[NG] :" + billKeyArr[2];		         	        
                 }            	 
             }
                                                                         		
            //결과 메세지 리턴하기
            mapOutVar.put("gv_rslMsg", cardMsg);
			                       
            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
            
            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);
        }        
        return modelAndView;
    }
    
	/**
     * 상담 정보를 등록/수정한다. (대명 - CMS/카드 부가서비스 등록시)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/updateCms")
    public ModelAndView updateCmsMemInfo(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input"); 				// 변경후 dataset
            DataSetMap listInOldDs = (DataSetMap)mapInDs.get("ds_input_old"); 				// 변경후 dataset

            if (listInDs.size() > 0) {
                Map <String, Object> hmParam = listInDs.get(0);
                Map <String, Object> hmParam_old = listInOldDs.get(0);
                                                
                // CMS 변경 정보 업데이트
                DlwMemPayMthdService.updateCmsMemInfo(hmParam);
                
                /**************************************************상담등록 필드 구성*********************************************************/ 
                String chgText = "[CMS] 이체일자가 변경되었습니다. [" + hmParam_old.get("ichae_dt").toString() + " >> " + hmParam.get("ichae_dt").toString() +  "]" ;

                // 상담유형 1, 2, 3
                hmParam.put("cons_typ1_cd", "CT01010000");		//회원정보
                hmParam.put("cons_typ2_cd", "CT01010100");		//결제정보
                hmParam.put("cons_typ3_cd", "CT01010102");		//결제수단변경
                hmParam.put("cons_memo_tit", chgText);
                /*******************************************************************************************************************************/
                
                // 상담 등록
                saveConsdlw(hmParam); 
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
            
            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);
        }
        return modelAndView;
    }
    
    /**
     * 대명라이프웨이 CMS 부가서비스(신규,해지)를 등록한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/saveCms")
    public ModelAndView insertDlwAccntCms(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        Map<String, Object> hmParam2 = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            DataSetMap listInDs2 = (DataSetMap)mapInDs.get("ds_input2");
            hmParam = listInDs.get(0);

            //세션
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("emple_no", hmParam.get("rgsr_id"));
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            hmParam.put("upd_man", hmParam.get("rgsr_id"));
            
            // CMS 회원 여부
            String cmsMsg = "";		// 결과 전달 메세지
            String strRtnName = "";	//결과 예금주명
     
            hmParam.put("depr_nm", hmParam.get("depr"));						// 예금주  
        	hmParam.put("inv_tg_dt", hmParam.get("ichae_dt"));					// 이체일자
            hmParam.put("idn_no", hmParam.get("cms_birth"));					// 생년월일            
            hmParam.put("rltn_cd", hmParam.get("reltn"));							// 관계   
            
            /*
            * 	CMS등록 시 계좌정보 확인  
            */
            
            String strBankCd = hmParam.get("bank_ini").toString();
            String strBankNo = hmParam.get("bank_accnt_no").toString();
            String strDepr = hmParam.get("depr").toString();
            String strRtnCode = "";
            
            String ActionUrl = "https://iniweb-api.inicis.com/api/acctCheck.jsp";
            
    		Map<String, String> param = new HashMap<>();
    		param.put("mid", "daemyungim");
    		param.put("banksett",strBankCd); // d);
    		param.put("noacct", strBankNo);
    		param.put("rltURL", "https://test.com");
    		    		
    		String res = processHTTP(ActionUrl, param);
    		Map<String, String> resMap = splitQuery(res);
    		
    		strRtnName = resMap.get("strRet").toString();
    		cmsMsg = resMap.get("strErrMsg").toString();
    		strRtnCode = resMap.get("code").toString();
    		
    		if (strRtnCode.equals("000")){
        		if (!strRtnName.equals(strDepr)){
        			cmsMsg = "예금주명이 상이합니다. 확인바랍니다 ("+strRtnName+")";
        		} else {        
        			
               	 	//성공
                    hmParam.put("acpt_mthd", "03");										// 04: 정상부가해지, 05: 보류부가해지, 03: 부가신규
                    hmParam.put("app_cl", "1");												// 3: 해지, 1: 신규, 7 : 보류해지                
                	DlwMemPayMthdService.insertDlwAccntCms(hmParam);
                	
                	cmsMsg = "[OK] " + cmsMsg;
                    
                    /**************************************************상담등록 필드 구성*********************************************************/ 
//                    String chgText = "[CMS] 결제 정보가 등록되었습니다."; 
                    
                    // 상담유형 1, 2, 3
                	//20220809 cms등록시 상담이력 등록 안함
//                    hmParam.put("cons_typ1_cd", "CT01010000");		//회원정보
//                    hmParam.put("cons_typ2_cd", "CT01010100");		//결제정보
//                    hmParam.put("cons_typ3_cd", "CT01010102");		//결제수단변경
//                    hmParam.put("cons_memo_tit", chgText);
                    /*******************************************************************************************************************************/
                    
                    // 상담 등록
//                    saveConsdlw(hmParam);         			
        		}

    		} else {
    			//실패
    			cmsMsg = "[NG] " + cmsMsg;
    		}    		                                                                       
     	
            mapOutVar.put("gv_rslMsg", cmsMsg);
                                   
            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
            
            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);
        }        
        return modelAndView;
    }
    
    /************************************************************************************************************************
    *  통장이름 확인에 필요한 메소드 
    /***********************************************************************************************************************/
	public static String processHTTP(String url, Map<String, String> params) throws Exception
	{
		HttpPost post = new HttpPost(url);

		List<NameValuePair> urlParameters = new ArrayList<>(params.size());
		for (Map.Entry<String, String> entry : params.entrySet())
		{
			urlParameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		post.setEntity(new UrlEncodedFormEntity(urlParameters, "euc-kr"));

		String res = "";
		try (CloseableHttpClient httpClient = HttpClients.createDefault(); CloseableHttpResponse response = httpClient.execute(post))
		{
			Header[] headerNames = response.getAllHeaders();
			for (Header header : headerNames)
			{
				if (header.getName().equals("Location"))
					res = header.getValue();
			}

			response.close();
			httpClient.close();
		}
		return res;
	}
	
	public static Map<String, String> splitQuery(String location) throws UnsupportedEncodingException, Exception
	{
		Map<String, String> query_pairs = new LinkedHashMap<String, String>();
		URL url = new URL(location);

		String query = url.getQuery();
		String[] pairs = query.split("&");
		for (String pair : pairs)
		{
			int idx = pair.indexOf("=");
			query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "utf-8"), URLDecoder.decode(pair.substring(idx + 1), "utf-8"));
		}
		return query_pairs;
	}
    
    
    /**
     * 대명라이프웨이 CMS 신청 실패 로그 등록
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/saveCmsHistoryLogs")
    public ModelAndView insertDlwCmsHistoryLogs(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            hmParam = listInDs.get(0);

            //세션
            ParamUtils.addSaveParam(hmParam);
            
            DlwMemPayMthdService.insertDlwCmsHistoryLogs(hmParam);
            
            //2018.01.05 접속 로그////////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            //////////////////////////////////////////////////////////////////////////////////////////////////////

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
            
            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);
        }        
        return modelAndView;
    }
    
    /**
     * 상담 정보를 등록/수정한다. (대명)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    public void saveConsdlw(Map <String, Object> pmParam) throws Exception {
        String sConsno = "";
        
        String sAccntNo = StringUtils.defaultString((String)pmParam.get("accnt_no")); 	// 회원번호
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();				// 담당자 아이디

        //상담이력 저장 (TB_CONS)
        pmParam.put("consno_sqno", "1");   				// SEQ
        pmParam.put("acpg_chnl_cd", "99");					// 접수채널 코드 (기타)
        pmParam.put("cons_stat_cd", "30"); 					//상담상태 - 처리완료
        pmParam.put("cons_dspsmddl_dtpt_cd", "10"); 		//상담처리중 - 일반     
        
        pmParam.put("rgsr_id", oLoginUser.getUserid());
        pmParam.put("amnd_id", oLoginUser.getUserid());
        pmParam.put("cnsl_man", oLoginUser.getUserid());
        
        sConsno = DlwMemPayMthdService.insertCons(pmParam);
    }
    
	/**
     	* CMS등록현황
	     * 임동진
	     * 20190903
     */
    @RequestMapping(value = "/getCmsAccntList")
    public ModelAndView getCmsAccntList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap listMap2 = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();  

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();
            
            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
                
                int nTotal = DlwMemPayMthdService.getCmsAccntCount(hmParam);
                               
                List<Map<String, Object>> mList = DlwMemPayMthdService.getCmsAccntList(hmParam);
                
                /*
                List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();                
                Map<String, Object> mTest = new HashMap<String, Object>(); 
                String sAccntNo = "";
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
                String sRegDm =sdf.format(new Date());
                String cmsFilePaht = "C:\\CMS\\9993083157\\EVDC\\";
                String sfilePath = "";
                boolean bFileExists = false; 
                String sFileExists = "";
                		                
                for(int i=0; i<mList.size(); i++){
                	mTest = mList.get(i);      

                	sAccntNo = mTest.get("accnt_no").toString();                  	
                	sfilePath = cmsFilePaht + sRegDm +  "\\" + sAccntNo + ".wav";
                	
                	File homeDir = FileUtils.getUserDirectory();                	
                	System.out.println("xxxxxxxxxxxxx" + FileUtils.getUserDirectoryPath());
                	
                	File sFileList = new File(sfilePath);                	
                	bFileExists = sFileList.exists();
                	if(bFileExists){
                		sFileExists = "파일있음";
                	} else {
                		sFileExists = "파일없음";
                	}
                	
                	mTest.put("file_yn", sFileExists);
                	listResult.add(mTest);
                	
                	System.out.println(">>>>>>> " +  sfilePath);                	
                }
                */                
                mapOutVar.put("tc_list", nTotal);                
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);                
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
            
            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);
        }

        return modelAndView;
    }
    
    /**
	 	* CMS전송 대상자 조회
	     * 임동진
	     * 20190903
	 */
	@RequestMapping(value = "/getCmsSendList")
	public ModelAndView getCmsSendList(XPlatformMapDTO xpDto, Model model) throws Exception {
	    ModelAndView modelAndView = new ModelAndView("xplatformMapView");
	    DataSetMap listMap = new DataSetMap();
	    DataSetMap listMap2 = new DataSetMap();
	    Map<String, Object> hmParam = new HashMap<String, Object>();  
	
	    // 에러코드및 메시지
	    String szErrorCode="0";
	    String szErrorMsg="OK";
	
	    try {
	        Map <String, Object> mapInVar     = xpDto.getInVariableMap();
	        Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
	        Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
	        Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();
	        
	        DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
	        if (srchInDs != null && srchInDs.size() > 0) {
	            hmParam = srchInDs.get(0);
	            
	            UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();				// 담당자 아이디	            
	            hmParam.put("rgsr_id", oLoginUser.getUserid());
	            
	            int nTotal = DlwMemPayMthdService.getCmsSendCount(hmParam);
	                           
	            List<Map<String, Object>> mList = DlwMemPayMthdService.getCmsSendList(hmParam);
	            
	            mapOutVar.put("tc_list", nTotal);                
	            listMap.setRowMaps(mList);
	            mapOutDs.put("ds_output", listMap);                
	        }
	
	        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
	        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
	    } catch (Exception e) {
	        e.printStackTrace();
	        szErrorCode = "-1";
	        szErrorMsg  = e.getMessage();
	        
	        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
	        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);
	    }
	
	    return modelAndView;
	}  
	
    /**
     * 이니시스 장기할부 카드 체크
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/insertCmsTarget")
    public ModelAndView insertCmsTarget(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();
            
            String sGbn = (String)mapInVar.get("gbn");
            String sSendDt = (String)mapInVar.get("send_dt");
            String chgText = "";
            
            if (!sGbn.equals("out")){
            	
                DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");                        
                
                for(int i=0; i<listInDs.size(); i++){
                	hmParam = listInDs.get(i);
                	
                	hmParam.put("gbn",sGbn);
                	
                	//세션
                    ParamUtils.addSaveParam(hmParam);
                    hmParam.put("emple_no", hmParam.get("rgsr_id"));
                    hmParam.put("reg_man", hmParam.get("rgsr_id"));
                    hmParam.put("upd_man", hmParam.get("rgsr_id"));
                                    
                    DlwMemPayMthdService.insertCmsTarget(hmParam);        
                }
            } else {            	            	
            	hmParam.put("gbn",sGbn);
            	hmParam.put("send_dt",sSendDt);
            	
            	//세션
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("reg_man", hmParam.get("rgsr_id"));

                DlwMemPayMthdService.insertCmsTarget(hmParam);        
            }
            
            //결과 메세지 리턴하기
            mapOutVar.put("gv_rslMsg", "[OK]");
                                              
            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
            
            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);
        }        
        return modelAndView;
    }
}
