package powerservice.business.dlw.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.maven.model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.dlw.service.DlwResnReceiptService;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

@Controller
@RequestMapping(value = "/dlw/resnreceipt")
public class DlwResnReceiptController {


    @Resource
    private DlwResnReceiptService dlwResnReceiptService;

    /** ================================================================
     * 날짜 : 20230118
     * 이름 : 임동진
     * 추가내용 : 해약접수회원정보
     * 대상 테이블 : LF_DMUSER.TB_RESN_RECEIPT
     * ================================================================
     */
    @RequestMapping(value = "/getReceiptAccntInfo")
    public ModelAndView getResnReceiptAccntInfo(XPlatformMapDTO xpDto, Model model) throws Exception {
    	ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();
            
            String strAccntNo = mapInVar.get("accnt_no").toString();            
            hmParam.put("accnt_no", strAccntNo);
            
            List<Map<String, Object>> mList = dlwResnReceiptService.getReceiptAccntInfo(hmParam);            
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);                                                         
        }
        catch (Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }
    
    /** ================================================================
     * 날짜 : 20230118
     * 이름 : 임동진
     * 추가내용 : 해약접수리스트
     * 대상 테이블 : LF_DMUSER.TB_RESN_RECEIPT
     * ================================================================
     */
    @RequestMapping(value = "/getReceiptList")
    public ModelAndView getResnReceiptList(XPlatformMapDTO xpDto, Model model) throws Exception {
    	ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();
            
            String strAccntNo = mapInVar.get("accnt_no").toString();            
            hmParam.put("accnt_no", strAccntNo);          
            
            List<Map<String, Object>> mListReceipt = dlwResnReceiptService.getReceiptList(hmParam);
            listMap.setRowMaps(mListReceipt);
            mapOutDs.put("ds_output", listMap);                                              
        }
        catch (Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }   
    
    /** ================================================================
     * 날짜 : 20230118
     * 이름 : 임동진
     * 추가내용 : 해약접수히스토리
     * 대상 테이블 : LF_DMUSER.TB_RESN_RECEIPT
     * ================================================================
     */
    @RequestMapping(value = "/getResnproHstr")
    public ModelAndView getResnProcHistory(XPlatformMapDTO xpDto, Model model) throws Exception {
    	ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();
            
            String strAccntNo = mapInVar.get("receipt_no").toString();            
            hmParam.put("receipt_no", strAccntNo);          
                  	                
        	List<Map<String, Object>> mListRHistory = dlwResnReceiptService.getResnProcHistory(hmParam);
            listMap.setRowMaps(mListRHistory);
            mapOutDs.put("ds_output", listMap);                 
        }
        catch (Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }
    
    /** ================================================================
     * 날짜 : 20230120
     * 이름 : 임동진
     * 추가내용 : 해약방어접수리스트
     * 대상 테이블 : LF_DMUSER.TB_RESN_RECEIPT
     * ================================================================
     */
    @RequestMapping(value = "/getReceiptProcList")
    public ModelAndView getResnReceiptProcList(XPlatformMapDTO xpDto, Model model) throws Exception {
    	ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap listMap2 = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();
            
            int nTotal = dlwResnReceiptService.getReceiptProcCount(hmParam);
            mapOutVar.put("nTotalListCnt", nTotal);
            
            // 해약방어접수리스트 
            List<Map<String, Object>> mListReceipt = dlwResnReceiptService.getReceiptProcList(hmParam);
            listMap.setRowMaps(mListReceipt);
            mapOutDs.put("ds_output", listMap);
            
            // 해약방어상담원리스트 
            List<Map<String, Object>> mReceiptCnsr = dlwResnReceiptService.getReceiptProcCounsel(hmParam);
            listMap2.setRowMaps(mReceiptCnsr);
            mapOutDs.put("ds_output2", listMap2);    
        }
        catch (Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    } 
    
    /** ================================================================
     * 날짜 : 20230120
     * 이름 : 임동진
     * 추가내용 : 해약등록리스트 
     * 대상 테이블 : LF_DMUSER.TB_RESN_RECEIPT
     * ================================================================
     */
    @RequestMapping(value = "/getResnProcList")
    public ModelAndView getResnProcList(XPlatformMapDTO xpDto, Model model) throws Exception {
    	ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();        
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();
            
            int nTotal = dlwResnReceiptService.getResnProcCount(hmParam);
            mapOutVar.put("nTotalListCnt", nTotal); 
            
            // 해약등록리스트 
            List<Map<String, Object>> mListReceipt = dlwResnReceiptService.getResnProcList(hmParam);
            listMap.setRowMaps(mListReceipt);
            mapOutDs.put("ds_output", listMap);            
        }
        catch (Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    } 
    
    /** ================================================================
     * 날짜 : 20230120
     * 이름 : 임동진
     * 추가내용 : 해약방어회원
     * 대상 테이블 : LF_DMUSER.TB_RESN_RECEIPT
     * ================================================================
     */
    @RequestMapping(value = "/getResnProtectList")
    public ModelAndView getResnProtectList(XPlatformMapDTO xpDto, Model model) throws Exception {
    	ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();        
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();
            
            int nTotal = dlwResnReceiptService.getResnProtectCount(hmParam);
            mapOutVar.put("nTotalListCnt", nTotal); 
            
            // 해약방어리스트 
            List<Map<String, Object>> mListReceipt = dlwResnReceiptService.getResnProtectList(hmParam);
            listMap.setRowMaps(mListReceipt);
            mapOutDs.put("ds_output", listMap);            
        }
        catch (Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    } 
}