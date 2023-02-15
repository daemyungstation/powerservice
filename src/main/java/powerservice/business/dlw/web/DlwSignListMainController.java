package powerservice.business.dlw.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.common.service.CommonService;
import powerservice.business.sys.service.BasVlService;
import powerservice.business.dlw.service.DlwSignListMainService;
import powerservice.common.util.CommonUtils;
import powerservice.core.util.ParamUtils;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

@Controller
public class DlwSignListMainController {
	
	@Resource
	private DlwSignListMainService dlwSignListMainService;
	
	@Resource
	private CommonService commonService;
	
	@Resource
    private BasVlService basVlService;
	
	/** ================================================================
     * 날짜 : 20201026
     * 이름 : 송준빈
     * 추가내용 : 전자서명관리리스트 조회
     * 대상 테이블 : LF_DMUSER.TB_SIGN_LIST_MAIN
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/sign/main/getSignListMainList")
    public ModelAndView getSignListMainList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap1 = new DataSetMap();
        DataSetMap listMap2 = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try 
        {
        	Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            
            if (listInDs != null && listInDs.size() > 0) 
            {
            	hmParam = listInDs.get(0);
            	String sSvcId = (String)hmParam.get("svc_id");
            	
            	DataSetMap listInGds = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
            	
                if (listInGds != null && listInGds.size() > 0) 
                {
                    Map pMap = listInGds.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));
                }
                
                if(sSvcId.equals("tp_signList"))
                {                    
                	int nTotal1 = dlwSignListMainService.getSignListMainCount(hmParam);
                    mapOutVar.put("nTotalListCount1", nTotal1);
                    
                    List<Map<String, Object>> mList1 = dlwSignListMainService.getSignListMainList(hmParam);
                    listMap1.setRowMaps(mList1);
                    mapOutDs.put("ds_output1", listMap1); 

                }
                else if(sSvcId.equals("tp_signHist"))
                {
                	int nTotal2 = dlwSignListMainService.getSignHistMainCount(hmParam);
                    mapOutVar.put("nTotalListCount2", nTotal2);
                    
                    List<Map<String, Object>> mList2 = dlwSignListMainService.getSignHistMainList(hmParam);
                    listMap2.setRowMaps(mList2);
                    mapOutDs.put("ds_output2", listMap2); 
                }
                else
                {
                    szErrorCode = "-1";
                    szErrorMsg = "할당되지 아니한 조회(업무) 영역입니다.";
                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);
                    return modelAndView;
                }
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }
    
    /** ================================================================
     * 날짜 : 20201026
     * 이름 : 송준빈
     * 추가내용 : 전자서명관리리스트 대상 삭제
     * 대상 테이블 : LF_DMUSER.TB_SIGN_LIST_MAIN
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/sign/main/deleteSignListMainList")
    public ModelAndView deleteSignListMainList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try 
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            
            if (listInDs != null && listInDs.size() > 0) 
            {
            	hmParam = listInDs.get(0);
            	/*
            	List<Map<String, Object>> mList = dlwSignListMainService.getDeleteSignYnConfirm(hmParam);
            	
            	if(mList.size() > 0)
            	{
            		String sSignGbn1 = (String)hmParam.get("sign_gbn1");
            		
            		if(sSignGbn1.equals("03") || sSignGbn1.equals("04"))
            		{
            			dlwSignListMainService.deleteSignListMainList(hmParam);
            		}
            		else
            		{
            			throw new Exception("민원서식과 전환서비스가 등록되어 있는 회원은 계약서 삭제가 불가능합니다. \n 등록된 민원서식과 전환서비스를 삭제하여 주십시오.");
            		}
            	}
            	else
            	{
            		dlwSignListMainService.deleteSignListMainList(hmParam);
            	}
            	*/
            	
            	dlwSignListMainService.deleteSignListMainList(hmParam);
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }
    
    /** ================================================================
     * 날짜 : 20200422
     * 이름 : 송준빈
     * 추가내용 : 전자서명거절
     * 대상 테이블 : LF_DMUSER.TB_SIGN_LIST_MAIN
     * */
    @RequestMapping(value = "/dlw/sign/main/updateSignMemberReject")
    public ModelAndView deleteEvtTranModel(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        List<Map<String, Object>> mList = new ArrayList<Map<String,Object>>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try 
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            
            if (listInDs != null && listInDs.size() > 0) 
            {
            	hmParam = listInDs.get(0);
            	dlwSignListMainService.updateSignMemberReject(hmParam);
            }
            
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }
    
    /** ================================================================
     * 날짜 : 20201026
     * 이름 : 송준빈
     * 추가내용 : 전자서명 대상 고객 조회 팝업
     * 대상 테이블 : LF_DMUSER.MEM_PROD_ACCNT, LF_DMUSER.MEMBER, LF_DMUSER.PRODUCT
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/sign/popaccnt/getAccntList")
    public ModelAndView getAccntList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap01 = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try 
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            
            if (listInDs != null && listInDs.size() > 0) 
            {
            	hmParam = listInDs.get(0);
                
                List<Map<String, Object>> mList01 = dlwSignListMainService.getAccntList(hmParam);
                listMap01.setRowMaps(mList01);
                mapOutDs.put("ds_output", listMap01);
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }
    
	/** ================================================================
     * 날짜 : 20201026
     * 이름 : 송준빈
     * 추가내용 : 전자서명관리리스트 조회
     * 대상 테이블 : LF_DMUSER.TB_SIGN_LIST_MAIN
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/sign/popaccnt/getDoubleAccntList")
    public ModelAndView getDoubleAccntList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try 
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            
            if (listInDs != null && listInDs.size() > 0) 
            {
            	hmParam = listInDs.get(0);
            	
            	DataSetMap listInGds = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
            	
                if (listInGds != null && listInGds.size() > 0) 
                {
                    Map pMap = listInGds.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));
                }
                
                int nTotal = dlwSignListMainService.getDoubleAccntCount(hmParam);
                mapOutVar.put("nTotalListCount", nTotal);
                
                List<Map<String, Object>> mList = dlwSignListMainService.getDoubleAccntList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap); 
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }
    
    /** ================================================================
     * 날짜 : 20210111
     * 이름 : 송준빈
     * 추가내용 : 전자서명관리리스트 구분 코드 로드(전자서명구분1, 전자서명구분2)
     * 대상 테이블 : LF_DMUSER.TB_SIGN_CODE_MST, LF_DMUSER.TB_SIGN_CODE_DTL
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/sign/popinfo/getCodeSignGbnFull")
    public ModelAndView getCodeSignGbnFull(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap1 = new DataSetMap();
        DataSetMap listMap2 = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try 
        {
        	Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            List<Map<String, Object>> mList1 = dlwSignListMainService.getCodeSignGbn1Full(hmParam);
            listMap1.setRowMaps(mList1);
            mapOutDs.put("ds_output1", listMap1); 

            List<Map<String, Object>> mList2 = dlwSignListMainService.getCodeSignGbn2Full(hmParam);
            listMap2.setRowMaps(mList2);
            mapOutDs.put("ds_output2", listMap2); 

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }
    
    /** ================================================================
     * 날짜 : 20201026
     * 이름 : 송준빈
     * 추가내용 : 전자서명관리리스트 팝업 사전 조회
     * 대상 테이블 : LF_DMUSER.TB_SIGN_LIST_MAIN
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/sign/popinfo/getPreSignAccntInfo")
    public ModelAndView getPreSignAccntInfo(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap01 = new DataSetMap();
        DataSetMap listMap02 = new DataSetMap();
        DataSetMap listMap03 = new DataSetMap();
        DataSetMap listMap04 = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try 
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            
            if (listInDs != null && listInDs.size() > 0) 
            {
            	hmParam = listInDs.get(0);
            	hmParam.put("pre_accnt_no", (String)hmParam.get("accnt_no"));
            	
            	/* 기준 회원번호를 이용하여 이미 전자서명에 등록이 된 회원인지 확인 (비대면, 대면계약서를 작성했는지도 확인)*/
            	List<Map<String, Object>> mListAMccnt = dlwSignListMainService.getAMccntNoRegSignConfirm(hmParam);
            	
            	if(mListAMccnt.size() > 0) // 대면계약이나 비대면 계약이 이미 등록 되어있는 고객은 민원서식과 전환서비스를 오픈한다.
            	{
            		String sAccntNo = String.valueOf(mListAMccnt.get(0).get("accnt_no"));
            		String sSignNo = String.valueOf(mListAMccnt.get(0).get("sign_no"));
            		String sAccntGbn = sAccntNo.substring(0,1).equals("M") ? "MDBL" : "ACCNT";
            		
            		hmParam.put("sign_no", sSignNo);
            		hmParam.put("sign_gbn_init", "SI");
            		
            		if(sAccntGbn == "ACCNT")
            		{
            			/*
            			List<Map<String, Object>> mList02 = dlwSignListMainService.getSignAccnt2Info(hmParam);
                        listMap02.setRowMaps(mList02);
                        mapOutDs.put("ds_output2", listMap02);
                        
                        List<Map<String, Object>> mList03 = dlwSignListMainService.getRegistDoubleBasicSignGbn(hmParam);
                        listMap03.setRowMaps(mList03);
                        mapOutDs.put("ds_output3", listMap03);
                        
                        List<Map<String, Object>> mList04 = dlwSignListMainService.getSignAccntSignTypeInfo(hmParam);
                        listMap04.setRowMaps(mList04);
                        mapOutDs.put("ds_output4", listMap04);
                        */
            			
            			List<Map<String, Object>> mListDouble = dlwSignListMainService.getSignAccnt2Info(hmParam);
            			listMap01.setRowMaps(mListDouble);
                        mapOutDs.put("ds_output1", listMap01);
                        
                        List<Map<String, Object>> mList02 = dlwSignListMainService.getSignAccntSignTypeInfo(hmParam);
                        listMap02.setRowMaps(mList02);
                        mapOutDs.put("ds_output2", listMap02);
                        
                        List<Map<String, Object>> mList03 = dlwSignListMainService.getSignAccntInfoHist(hmParam);
                        listMap03.setRowMaps(mList03);
                        mapOutDs.put("ds_output3", listMap03);
                        
                        List<Map<String, Object>> mList04 = dlwSignListMainService.getCodeSignGbn1Init(hmParam);
                        listMap04.setRowMaps(mList04);
                        mapOutDs.put("ds_output4", listMap04);
            		}
            		else 
            		{
            			/*
            			hmParam.put("accnt_no", sAccntNo);
            			hmParam.put("accnt_no_dbl", sAccntNo);
            			
            			List<Map<String, Object>> mList02 = dlwSignListMainService.getSignDouble2Info(hmParam);
                        listMap02.setRowMaps(mList02);
                        mapOutDs.put("ds_output1", listMap02);
                        
                        List<Map<String, Object>> mList03 = dlwSignListMainService.getRegistDoubleBasicSignGbn(hmParam);
                        listMap03.setRowMaps(mList03);
                        mapOutDs.put("ds_output3", listMap03);
                        
                        List<Map<String, Object>> mList04 = dlwSignListMainService.getSignDoubleSignTypeInfo(hmParam);
                        listMap04.setRowMaps(mList04);
                        mapOutDs.put("ds_output4", listMap04);
                        */
            			
            			List<Map<String, Object>> mListDouble = dlwSignListMainService.getDoubleInfo(hmParam);
            			listMap01.setRowMaps(mListDouble);
                        mapOutDs.put("ds_output1", listMap01);
                        
                        hmParam.put("accnt_no", sAccntNo);
                        
                        List<Map<String, Object>> mList02 = dlwSignListMainService.getSignDoubleSignTypeInfo(hmParam);
                        listMap02.setRowMaps(mList02);
                        mapOutDs.put("ds_output2", listMap02);
                        
                        List<Map<String, Object>> mList03 = dlwSignListMainService.getSignAccntInfoHist(hmParam);
                        listMap03.setRowMaps(mList03);
                        mapOutDs.put("ds_output3", listMap03);
                        
                        List<Map<String, Object>> mList04 = dlwSignListMainService.getCodeSignGbn1Init(hmParam);
                        listMap04.setRowMaps(mList04);
                        mapOutDs.put("ds_output4", listMap04);
            		}
            		
            		mapOutVar.put("xSearchType", "SI");
            	}
            	else // 대면계약이나 비대면 계약이 등록되지 않은 고객은 SECTION_THR을 통하여 비대면, 대면계약서를 작성토록 한다.
            	{
            		hmParam.put("sign_gbn_init", "II");
            		List<Map<String, Object>> mListDouble = dlwSignListMainService.getDoubleInfo(hmParam); // 더블회원, 트리회원 조회(기준값을 제한 나머지 쌍을 찾는다.)
            		
            		if(mListDouble.size() > 0) // 더블구좌, 트리구좌 회원인 경우
            		{
            			/*
                        String sAccntNoDbl = String.valueOf(mListDouble.get(0).get("accnt_no_dbl"));
                        hmParam.put("accnt_no_dbl", sAccntNoDbl);

                        List<Map<String, Object>> mListDoubleSignGbn = dlwSignListMainService.getDoubleBasicSignGbn(hmParam); // 더블구좌 회원 전자서명구분 기본코드 조회
                        
                        listMap02.setRowMaps(mListDouble);
                        mapOutDs.put("ds_output1", listMap01);
                        
                        listMap03.setRowMaps(mListDoubleSignGbn);
                        mapOutDs.put("ds_output3", listMap03);
                        
                        List<Map<String, Object>> mList04 = dlwSignListMainService.getSignDoubleSignTypeInfo(hmParam);
                        listMap04.setRowMaps(mList04);
                        mapOutDs.put("ds_output4", listMap04);
                        */
            			
            			listMap01.setRowMaps(mListDouble);
                        mapOutDs.put("ds_output1", listMap01);
                        
                        List<Map<String, Object>> mList02 = dlwSignListMainService.getSignDoubleSignTypeInfo(hmParam);
                        listMap02.setRowMaps(mList02);
                        mapOutDs.put("ds_output2", listMap02);
                        
                        
                        List<Map<String, Object>> mList03 = dlwSignListMainService.getSignAccntInfoHist(hmParam);
                        listMap03.setRowMaps(mList03);
                        mapOutDs.put("ds_output3", listMap03);
                        
                        List<Map<String, Object>> mList04 = dlwSignListMainService.getCodeSignGbn1Init(hmParam);
                        listMap04.setRowMaps(mList04);
                        mapOutDs.put("ds_output4", listMap04);
                        
                        //mapOutVar.put("xSearchType", "SI");
            		}
            		else // 다구좌, 단일회원인경우
            		{
            			/*
            			hmParam.put("accnt_no_dbl", "");
            			List<Map<String, Object>> mListDoubleSignGbn = dlwSignListMainService.getDoubleBasicSignGbn(hmParam); // 더블구좌 회원 전자서명구분 기본코드 조회
            			
            			listMap03.setRowMaps(mListDoubleSignGbn);
                        mapOutDs.put("ds_output3", listMap03);
                        
                        List<Map<String, Object>> mList04 = dlwSignListMainService.getSignDoubleSignTypeInfo(hmParam);
                        listMap04.setRowMaps(mList04);
                        mapOutDs.put("ds_output4", listMap04);
                        */
            			
            			List<Map<String, Object>> mList02 = dlwSignListMainService.getSignDoubleSignTypeInfo(hmParam);
                        listMap02.setRowMaps(mList02);
                        mapOutDs.put("ds_output2", listMap02);
                        
                        List<Map<String, Object>> mList03 = dlwSignListMainService.getSignAccntInfoHist(hmParam);
                        listMap03.setRowMaps(mList03);
                        mapOutDs.put("ds_output3", listMap03);
                        
                        List<Map<String, Object>> mList04 = dlwSignListMainService.getCodeSignGbn1Init(hmParam);
                        listMap04.setRowMaps(mList04);
                        mapOutDs.put("ds_output4", listMap04);
                        
                        //mapOutVar.put("xSearchType", "II");
            		}
            		
            		mapOutVar.put("xSearchType", "II");
            	}
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }
    
    /** ================================================================
     * 날짜 : 20210111
     * 이름 : 송준빈
     * 추가내용 : 전자서명 구분 코드2 조회 
     * 대상 테이블 : LF_DMUSER.TB_SIGN_CODE_DTL
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/sign/popinfo/getCodeSignGbn2")
    public ModelAndView getCodeSignGbn2(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap01 = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try 
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            String sSignGbn1 = (String) mapInVar.get("sign_gbn1");
            
            hmParam.put("sign_gbn1", sSignGbn1);
            
            List<Map<String, Object>> mList01 = dlwSignListMainService.getCodeSignGbn2(hmParam);
            listMap01.setRowMaps(mList01);
            mapOutDs.put("ds_output", listMap01);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }
    
    /** ================================================================
     * 날짜 : 20201026
     * 이름 : 송준빈
     * 추가내용 : 전자서명 이력 조회
     * 대상 테이블 : LF_DMUSER.MEM_PROD_ACCNT, LF_DMUSER.MEMBER, LF_DMUSER.PRODUCT
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/sign/popinfo/getSignAccntInfoHist")
    public ModelAndView getSignAccntInfoHist(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap01 = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try 
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            
            if (listInDs != null && listInDs.size() > 0) 
            {
            	hmParam = listInDs.get(0);
                
                List<Map<String, Object>> mList01 = dlwSignListMainService.getSignAccntInfoHist(hmParam);
                listMap01.setRowMaps(mList01);
                mapOutDs.put("ds_output", listMap01);
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }
    
    /** ================================================================
     * 날짜 : 20201026
     * 이름 : 송준빈
     * 추가내용 : 전자서명 대상 고객 조회 팝업
     * 대상 테이블 : LF_DMUSER.MEM_PROD_ACCNT, LF_DMUSER.MEMBER, LF_DMUSER.PRODUCT
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/sign/popinfo/insertSignMainList")
    public ModelAndView insertSignMainList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        Map<String, Object> hmParam1 = new HashMap<String, Object>();
        Map<String, Object> hmParam2 = new HashMap<String, Object>();
        Map<String, Object> hmParam3 = new HashMap<String, Object>();
        Map<String, Object> hmSaveParam = new HashMap<String, Object>();

        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try 
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs1 = (DataSetMap)mapInDs.get("ds_input");
            String sSignGbn1 = (String) mapInVar.get("sign_gbn1");
            String sSignGbn2 = (String) mapInVar.get("sign_gbn2");
            sSignGbn1 = String.valueOf(sSignGbn1);
            sSignGbn2 = String.valueOf(sSignGbn2);
            
            if (listInDs1 != null && listInDs1.size() > 0) 
            {
            	hmParam1 = listInDs1.get(0);

            	List<Map<String, Object>> mListCreateSignNo = dlwSignListMainService.getCreateSignNo(hmParam1);
            	String sAccntNoDbl = StringUtils.defaultString((String) hmParam1.get("accnt_no_dbl"));
            	
            	if(!sAccntNoDbl.equals("") && (sSignGbn1.equals("01") || sSignGbn1.equals("02"))) // 더블구좌이고 대면계약서, 비대면계약서인 경우
            	{
            		ParamUtils.addSaveParam(hmSaveParam);
            		
            		String sSignNo = String.valueOf(mListCreateSignNo.get(0).get("sign_no"));
            		String sAccntNo = sAccntNoDbl;
                    String sSendMan = (String)hmSaveParam.get("rgsr_id");
                    
                    hmSaveParam.put("sign_no", sSignNo);
                    hmSaveParam.put("accnt_no", sAccntNo);
                    hmSaveParam.put("sign_gbn1", sSignGbn1);
                    hmSaveParam.put("sign_gbn2", sSignGbn2);
                    hmSaveParam.put("send_man", sSendMan);
                    
                    dlwSignListMainService.insertSignMainList(hmSaveParam);
            	}
            	else
            	{
            		for(int idx = 0; idx < listInDs1.size(); idx++)
            		{
            			Map<String, Object> accntRow = listInDs1.get(idx);
            			
            			if(accntRow != null)
            			{
            				ParamUtils.addSaveParam(hmSaveParam);
            				
            			    String sSignNo = String.valueOf(mListCreateSignNo.get(0).get("sign_no"));
            			    String sAccntNo = String.valueOf(accntRow.get("accnt_no"));
                            String sSendMan = (String)hmSaveParam.get("rgsr_id");
                            
                            hmSaveParam.put("sign_no", sSignNo);
                            hmSaveParam.put("accnt_no", sAccntNo);
                            hmSaveParam.put("sign_gbn1", sSignGbn1);
                            hmSaveParam.put("sign_gbn2", sSignGbn2);
                            hmSaveParam.put("send_man", sSendMan);
                            
                            dlwSignListMainService.insertSignMainList(hmSaveParam);
            			}
            		}
            	}
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }
    
    /** ================================================================
     * 날짜 : 20201026
     * 이름 : 송준빈
     * 추가내용 : 전자서명 구분1 공통 코드 조회 
     * 대상 테이블 : LF_DMUSER.TB_SIGN_CODE_MST
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/sign/popinfo/getSignCodeMst")
    public ModelAndView getSignCodeMst(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try 
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            
            if (listInDs != null && listInDs.size() > 0) 
            {
            	hmParam = listInDs.get(0);
            	
                List<Map<String, Object>> mList = dlwSignListMainService.getSignCodeMst(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap); 
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }
    
    /** ================================================================
     * 날짜 : 20201026
     * 이름 : 송준빈
     * 추가내용 : 전자서명 구분2 공통 코드 조회 
     * 대상 테이블 : LF_DMUSER.TB_SIGN_CODE_DTL
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/sign/popinfo/getSignCodeDtl")
    public ModelAndView getSignCodeDtl(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try 
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            
            if (listInDs != null && listInDs.size() > 0) 
            {
            	hmParam = listInDs.get(0);
            	
                List<Map<String, Object>> mList = dlwSignListMainService.getSignCodeDtl(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap); 
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }
    
    @RequestMapping(value = "/dlw/sign/popinfo/insertSignCodeMst")
    public ModelAndView insertSignCodeMst(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try 
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            
            if (listInDs != null && listInDs.size() > 0) 
            {
            	hmParam = listInDs.get(0);
            	ParamUtils.addSaveParam(hmParam);
            	
            	int nCount = dlwSignListMainService.insertSignCodeMstConfirm(hmParam);
            	
            	if(nCount <= 0)
            	{
                    int x = dlwSignListMainService.insertSignCodeMst(hmParam);
            	}
            	else 
            	{
            		szErrorCode = "-1";
                    szErrorMsg = "이미 등록된 코드입니다. 중복으로 저장할수 없습니다.";
                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);
                    return modelAndView;
            	}
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }
    
    @RequestMapping(value = "/dlw/sign/popinfo/insertSignCodeDtl")
    public ModelAndView insertSignCodeDtl(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try 
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            
            if (listInDs != null && listInDs.size() > 0) 
            {
            	hmParam = listInDs.get(0);
            	ParamUtils.addSaveParam(hmParam);
            	
            	int nCount = dlwSignListMainService.insertSignCodeDtlConfirm(hmParam);
            	
            	if(nCount <= 0)
            	{
            		int x = dlwSignListMainService.insertSignCodeDtl(hmParam);
            	}
            	else 
            	{
            		szErrorCode = "-1";
                    szErrorMsg = "이미 등록된 코드입니다. 중복으로 저장할수 없습니다.";
                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);
                    return modelAndView;
            	}
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }
    
    @RequestMapping(value = "/dlw/sign/popinfo/updateSignCodeMst")
    public ModelAndView updateSignCodeMst(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try 
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            
            if (listInDs != null && listInDs.size() > 0) 
            {
            	hmParam = listInDs.get(0);
            	ParamUtils.addSaveParam(hmParam);
            	
                int x = dlwSignListMainService.updateSignCodeMst(hmParam);
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }
    
    @RequestMapping(value = "/dlw/sign/popinfo/updateSignCodeDtl")
    public ModelAndView updateSignCodeDtl(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try 
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            
            if (listInDs != null && listInDs.size() > 0) 
            {
            	hmParam = listInDs.get(0);
            	ParamUtils.addSaveParam(hmParam);
            	
                int x = dlwSignListMainService.updateSignCodeDtl(hmParam);
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }
    
    
    @RequestMapping(value = "/dlw/sign/popinfo/sendDirectSignInfo")
    public ModelAndView sendDirectSignInfo(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try 
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            
            if (listInDs != null && listInDs.size() > 0) 
            {
            	hmParam = listInDs.get(0);
            	ParamUtils.addSaveParam(hmParam);
            	
            	String sURL = "https://192.168.220.77/link_view/doc_send.jsp?sign_no=";
            	String sSignNo = CommonUtils.nvls((String)hmParam.get("sign_no"));
            	String resultMsg = new String();
            	String resultLine = "";
            	
            	System.out.println("sURL + sSignNo ::: " + sURL + sSignNo);
            	
            	disableSslVerification();
            	
            	URL url = new URL(sURL + sSignNo);
            	HttpsURLConnection httpsURLConnection = (HttpsURLConnection)url.openConnection();
            	InputStream is = null; // httpsURLConnection.getInputStream();
                InputStreamReader isr = null; // new InputStreamReader(is);
                BufferedReader br = null; // new BufferedReader(isr);
                
            	httpsURLConnection.setDoInput(true);
            	httpsURLConnection.setDoOutput(true);
            	httpsURLConnection.setUseCaches(false);
            	httpsURLConnection.setConnectTimeout(10000);
            	httpsURLConnection.setReadTimeout(20000);

            	int iResponseCode = httpsURLConnection.getResponseCode(); 
            	System.out.println("응답코드 : " + iResponseCode); 
            	System.out.println("응답메세지 : " + httpsURLConnection.getResponseMessage());
            	
            	SSLContext context = SSLContext.getInstance("TLS"); 
            	context.init(null, null, null);
            	httpsURLConnection.setSSLSocketFactory(context.getSocketFactory());

            	httpsURLConnection.connect(); 
            	httpsURLConnection.setInstanceFollowRedirects(true);
            	
            	if (iResponseCode == HttpsURLConnection.HTTP_OK)
            	{
            		is = httpsURLConnection.getInputStream(); 
            	} 
            	else 
            	{ 
            		is = httpsURLConnection.getErrorStream();
            	}
            	
            	isr = new InputStreamReader(is);
            	br = new BufferedReader(isr);
            	
            	while ((resultLine = br.readLine()) != null) 
            	{
            		resultMsg += resultLine.trim();
            		System.out.println("logs::::" + resultMsg);
                }
            	
            	br.close();
                isr.close();
                is.close();
            	
                mapOutVar.put("xDirectResultMsg", resultMsg);
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }
    
    public void disableSslVerification() {
		try
	    {
	        TrustManager[] trustAllCerts = new TrustManager[] 
	        {
	        	new X509TrustManager()
	        	{
	        		public java.security.cert.X509Certificate[] getAcceptedIssuers() 
	        	    {
	                    return null;
	                }
	                public void checkClientTrusted(X509Certificate[] certs, String authType)
	                {
	                	
	                }
	                public void checkServerTrusted(X509Certificate[] certs, String authType)
	                {
	                	
	                }
	            }
	        };
	
	        // Install the all-trusting trust manager
	        SSLContext sc = SSLContext.getInstance("SSL");
	        sc.init(null, trustAllCerts, new java.security.SecureRandom());
	        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	
	        // Create all-trusting host name verifier
	        HostnameVerifier allHostsValid = new HostnameVerifier() 
	        {
				public boolean verify(String arg0, SSLSession arg1) 
				{
					return true;
				}
	        };
	
	        // Install the all-trusting host verifier
	        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
	    } 
		catch (NoSuchAlgorithmException e) 
		{
	        e.printStackTrace();
	    } 
		catch (KeyManagementException e) 
		{
	        e.printStackTrace();
	    }
	}
    
    /** ================================================================
     * 날짜 : 20211230
     * 이름 : 임동진
     * 추가내용 : 전자서명 기초 회원 조회 (전자서명 상단 조회)
     * 대상 테이블 : LF_DMUSER.TB_SIGN_LIST_MAIN
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/sign/popinfo/getAccntInfoList")
    public ModelAndView getAccntInfoList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try 
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            
            if (listInDs != null && listInDs.size() > 0) {
            	hmParam = listInDs.get(0);            	            	        
            }
            
            System.out.println("xxxxxxxxxxxxxxx" + hmParam );
            
			List<Map<String, Object>> mList = dlwSignListMainService.getAccntInfoList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }
    
    /** ================================================================
     * 날짜 : 20211230
     * 이름 : 임동진
     * 추가내용 : 전자서명 전송 정보 (왼쪽 하단)
     * 대상 테이블 : LF_DMUSER.TB_SIGN_LIST_MAIN
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/sign/popinfo/getAMccntNoRegSignConfirm")
    public ModelAndView getAMccntNoRegSignConfirm(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try 
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            String strAccntNo = mapInVar.get("pAccntNo").toString();
            
            hmParam.put("accnt_no",strAccntNo);
            
            System.out.println("xxxxxxxxxxxxxxx" + hmParam );
            
         	/* 기준 회원번호를 이용하여 이미 전자서명에 등록이 된 회원인지 확인 (비대면, 대면계약서를 작성했는지도 확인)*/
        	List<Map<String, Object>> mList = dlwSignListMainService.getAMccntNoRegSignConfirm(hmParam);

            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }
    
    /** ================================================================
     * 날짜 : 20211230
     * 이름 : 임동진
     * 추가내용 : 전자서명 신규 등록 
     * 대상 테이블 : LF_DMUSER.TB_SIGN_LIST_MAIN
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/sign/popinfo/insertSignNew")
    public ModelAndView insertSignNew(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        Map<String, Object> hmParam = new HashMap<String, Object>();

        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try 
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs1 = (DataSetMap)mapInDs.get("ds_input");

            String sSignGbn2 = (String) mapInVar.get("sign_gbn2");
            sSignGbn2 = String.valueOf(sSignGbn2);
            
            if (listInDs1 != null && listInDs1.size() > 0) 
            {
            	hmParam = listInDs1.get(0);
            }
            
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("sign_gbn2", sSignGbn2);
                        
            System.out.println("xxxxxxxxxx" + hmParam );
                                    
            dlwSignListMainService.insertSignNew(hmParam);
            
            /*
            
            if (listInDs1 != null && listInDs1.size() > 0) 
            {
            	hmParam1 = listInDs1.get(0);

            	List<Map<String, Object>> mListCreateSignNo = dlwSignListMainService.getCreateSignNo(hmParam1);
            	String sAccntNoDbl = StringUtils.defaultString((String) hmParam1.get("accnt_no_dbl"));
            	
            	if(!sAccntNoDbl.equals("") && (sSignGbn1.equals("01") || sSignGbn1.equals("02"))) // 더블구좌이고 대면계약서, 비대면계약서인 경우
            	{
            		ParamUtils.addSaveParam(hmSaveParam);
            		
            		String sSignNo = String.valueOf(mListCreateSignNo.get(0).get("sign_no"));
            		String sAccntNo = sAccntNoDbl;
                    String sSendMan = (String)hmSaveParam.get("rgsr_id");
                    
                    hmSaveParam.put("sign_no", sSignNo);
                    hmSaveParam.put("accnt_no", sAccntNo);
                    hmSaveParam.put("sign_gbn1", sSignGbn1);
                    hmSaveParam.put("sign_gbn2", sSignGbn2);
                    hmSaveParam.put("send_man", sSendMan);
                    
                    dlwSignListMainService.insertSignMainList(hmSaveParam);
            	}
            	else
            	{
            		for(int idx = 0; idx < listInDs1.size(); idx++)
            		{
            			Map<String, Object> accntRow = listInDs1.get(idx);
            			
            			if(accntRow != null)
            			{
            				ParamUtils.addSaveParam(hmSaveParam);
            				
            			    String sSignNo = String.valueOf(mListCreateSignNo.get(0).get("sign_no"));
            			    String sAccntNo = String.valueOf(accntRow.get("accnt_no"));
                            String sSendMan = (String)hmSaveParam.get("rgsr_id");
                            
                            hmSaveParam.put("sign_no", sSignNo);
                            hmSaveParam.put("accnt_no", sAccntNo);
                            hmSaveParam.put("sign_gbn1", sSignGbn1);
                            hmSaveParam.put("sign_gbn2", sSignGbn2);
                            hmSaveParam.put("send_man", sSendMan);
                            
                            dlwSignListMainService.insertSignMainList(hmSaveParam);
            			}
            		}
            	}
            }
            */

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }
    
}