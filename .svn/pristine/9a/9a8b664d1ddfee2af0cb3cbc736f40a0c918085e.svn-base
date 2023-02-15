package powerservice.business.dlw.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tobesoft.xplatform.data.DataSet;

import powerservice.business.common.service.CommonService;
import powerservice.business.sys.service.BasVlService;
import powerservice.business.dlw.service.DlwIvrMgmtService;
import powerservice.core.util.ParamUtils;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

@Controller
public class DlwIvrMgmtController {
	
	@Resource
	private DlwIvrMgmtService dlwIvrMgmtService;
	
	@Resource
	private CommonService commonService;
	
	@Resource
    private BasVlService basVlService;
	
	/** ================================================================
     * 날짜 : 20210810
     * 이름 : 송준빈
     * 추가내용 : IVR관리 전체(탭별) 조회
     * 대상 테이블 : PS_WILLVI.TB_IVR_MAIN_MNG, PS_WILLVI.TB_IVR_WORK_MNG, PS_WILLVI.TB_IVR_MASTER_NO, PS_WILLVI.TB_IVR_HOLIDAY_MNG, PS_WILLVI.TB_IVR_SCRIPT_MNG
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/ivrMgmt/ext/getIvrMgmtList")
    public ModelAndView getIvrMgmtList(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            	String sSvcId = (String)hmParam.get("tab_gubun");
            	
            	DataSetMap listInGds = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
            	
                if (listInGds != null && listInGds.size() > 0) 
                {
                    Map pMap = listInGds.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));
                }
                
                if(sSvcId.equals("tap_ivr"))
                {                    
                    int nTotal = dlwIvrMgmtService.getIvrMgmtExtCount(hmParam);
                    mapOutVar.put("nTotalListCnt1", nTotal);
                    
                    List<Map<String, Object>> mList = dlwIvrMgmtService.getIvrMgmtExtList(hmParam);
                    listMap.setRowMaps(mList);
                    mapOutDs.put("ds_output1", listMap); 

                }
                else if(sSvcId.equals("tap_work"))
                {
                	int nTotal = dlwIvrMgmtService.getWorkMgmtExtCount(hmParam);
                    mapOutVar.put("nTotalListCnt2", nTotal);
                    
                    List<Map<String, Object>> mList = dlwIvrMgmtService.getWorkMgmtExtList(hmParam);
                    listMap.setRowMaps(mList);
                    mapOutDs.put("ds_output2", listMap); 
                }
                else if(sSvcId.equals("tap_genenum"))
                {
                	int nTotal = dlwIvrMgmtService.getGenenumMgmtExtCount(hmParam);
                    mapOutVar.put("nTotalListCnt3", nTotal);
                    
                    List<Map<String, Object>> mList = dlwIvrMgmtService.getGenenumMgmtExtList(hmParam);
                    listMap.setRowMaps(mList);
                    mapOutDs.put("ds_output3", listMap); 
                }
                else if(sSvcId.equals("tap_holyday"))
                {
                	int nTotal = dlwIvrMgmtService.getHolydayMgmtExtCount(hmParam);
                    mapOutVar.put("nTotalListCnt4", nTotal);
                    
                    List<Map<String, Object>> mList = dlwIvrMgmtService.getHolydayMgmtExtList(hmParam);
                    listMap.setRowMaps(mList);
                    mapOutDs.put("ds_output4", listMap); 
                }
                else if(sSvcId.equals("tap_script"))
                {
                	int nTotal = dlwIvrMgmtService.getScriptMgmtExtCount(hmParam);
                    mapOutVar.put("nTotalListCnt5", nTotal);
                    
                    List<Map<String, Object>> mList = dlwIvrMgmtService.getScriptMgmtExtList(hmParam);
                    listMap.setRowMaps(mList);
                    mapOutDs.put("ds_output5", listMap); 
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
     * 날짜 : 20210810
     * 이름 : 송준빈
     * 추가내용 : IVR관리 전체(탭별) 수정, 입력
     * 대상 테이블 : PS_WILLVI.TB_IVR_MAIN_MNG, PS_WILLVI.TB_IVR_WORK_MNG, PS_WILLVI.TB_IVR_MASTER_NO, PS_WILLVI.TB_IVR_HOLIDAY_MNG, PS_WILLVI.TB_IVR_SCRIPT_MNG
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/ivrMgmt/ext/saveIvrMgmtList")
    public ModelAndView saveIvrMgmtList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap dtptMap = new DataSetMap();

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
            String sSaveType = (String) mapInVar.get("save_type");
            String sSvcId = (String) mapInVar.get("svc_id");
            
            if (listInDs.size() > 0) 
            {
                Map hmParam = listInDs.get(0);
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("reg_man", (String)hmParam.get("rgsr_id"));
                
                if(sSvcId.equals("tap_ivr"))
                {                    
                	if (sSaveType.equals("update"))
                    {
                		dlwIvrMgmtService.updateIvrMgmtExtList(hmParam);
                    }
                    else if (sSaveType.equals("insert"))
                    {
                    	int nExists = dlwIvrMgmtService.existIvrMgmtExtList(hmParam);
                    			
                    	if(nExists > 0)
                    	{
                    		szErrorCode = "-1";
                            szErrorMsg = "이미 저장되어 있는 데이터 입니다.";
                            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                            modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);
                            return modelAndView;
                    	}
                    	else
                    	{
                    		dlwIvrMgmtService.insertIvrMgmtExtList(hmParam);
                    	}
                    }
                    else
                    {
                    	szErrorCode = "-1";
                        szErrorMsg = "할당되지 아니한 저장 분류입니다.";
                        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);
                        return modelAndView;
                    }
                }
                else if(sSvcId.equals("tap_work"))
                {
                	if (sSaveType.equals("update"))
                    {
                		dlwIvrMgmtService.updateWorkMgmtExtList(hmParam);
                    }
                    else if (sSaveType.equals("insert"))
                    {
                    	int nExists = dlwIvrMgmtService.existWorkMgmtExtList(hmParam);
            			
                    	if(nExists > 0)
                    	{
                    		szErrorCode = "-1";
                            szErrorMsg = "이미 저장되어 있는 데이터 입니다.";
                            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                            modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);
                            return modelAndView;
                    	}
                    	else
                    	{
                    	    dlwIvrMgmtService.insertWorkMgmtExtList(hmParam);
                    	}
                    }
                    else
                    {
                    	szErrorCode = "-1";
                        szErrorMsg = "할당되지 아니한 저장 분류입니다.";
                        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);
                        return modelAndView;
                    }
                }
                else if(sSvcId.equals("tap_genenum"))
                {
                	if (sSaveType.equals("update"))
                    {
                		dlwIvrMgmtService.updateGenenumMgmtExtList(hmParam);
                    }
                    else if (sSaveType.equals("insert"))
                    {
                        int nExists = dlwIvrMgmtService.existGenenumMgmtExtList(hmParam);
            			
                    	if(nExists > 0)
                    	{
                    		szErrorCode = "-1";
                            szErrorMsg = "이미 저장되어 있는 데이터 입니다.";
                            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                            modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);
                            return modelAndView;
                    	}
                    	else
                    	{
                    	    dlwIvrMgmtService.insertGenenumMgmtExtList(hmParam);
                    	}
                    }
                    else
                    {
                    	szErrorCode = "-1";
                        szErrorMsg = "할당되지 아니한 저장 분류입니다.";
                        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);
                        return modelAndView;
                    }
                }
                else if(sSvcId.equals("tap_holyday"))
                {
                	if (sSaveType.equals("update"))
                    {
                		dlwIvrMgmtService.updateHolydayMgmtExtList(hmParam);
                    }
                    else if (sSaveType.equals("insert"))
                    {
                        int nExists = dlwIvrMgmtService.existHolydayMgmtExtList(hmParam);
            			
                    	if(nExists > 0)
                    	{
                    		szErrorCode = "-1";
                            szErrorMsg = "이미 저장되어 있는 데이터 입니다.";
                            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                            modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);
                            return modelAndView;
                    	}
                    	else
                    	{
                    	    dlwIvrMgmtService.insertHolydayMgmtExtList(hmParam);	
                    	}
                    }
                    else
                    {
                    	szErrorCode = "-1";
                        szErrorMsg = "할당되지 아니한 저장 분류입니다.";
                        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);
                        return modelAndView;
                    }
                }
                else if(sSvcId.equals("tap_script"))
                {
                	if (sSaveType.equals("update"))
                    {
                		dlwIvrMgmtService.updateScriptMgmtExtList(hmParam);
                    }
                    else if (sSaveType.equals("insert"))
                    {
                    	dlwIvrMgmtService.insertScriptMgmtExtList(hmParam);	
                    }
                    else
                    {
                    	szErrorCode = "-1";
                        szErrorMsg = "할당되지 아니한 저장 분류입니다.";
                        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);
                        return modelAndView;
                    }
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
     * 날짜 : 20210810
     * 이름 : 송준빈
     * 추가내용 : IVR관리 전체(탭별) 삭제
     * 대상 테이블 : PS_WILLVI.TB_IVR_MAIN_MNG, PS_WILLVI.TB_IVR_WORK_MNG, PS_WILLVI.TB_IVR_MASTER_NO, PS_WILLVI.TB_IVR_HOLIDAY_MNG, PS_WILLVI.TB_IVR_SCRIPT_MNG
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/ivrMgmt/ext/deleteIvrMgmtList")
    public ModelAndView deleteIvrMgmtList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map<String, Object> hmParam = new HashMap<String, Object>();
        DataSetMap listMap = new DataSetMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";
        
        try 
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            //조회조건
            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
            String sSvcId = (String) mapInVar.get("svc_id");
            
            if (srchInDs != null && srchInDs.size() > 0) 
            {
            	if(sSvcId.equals("tap_ivr"))
                {                    
            		for(int idx = 0; idx < srchInDs.size(); idx++)
                	{
                		hmParam = srchInDs.get(idx);
                		dlwIvrMgmtService.deleteIvrMgmtExtList(hmParam);
                	}
                }
                else if(sSvcId.equals("tap_work"))
                {
                	for(int idx = 0; idx < srchInDs.size(); idx++)
                	{
                		hmParam = srchInDs.get(idx);
                		dlwIvrMgmtService.deleteWorkMgmtExtList(hmParam);
                	}
                }
                else if(sSvcId.equals("tap_genenum"))
                {
                	for(int idx = 0; idx < srchInDs.size(); idx++)
                	{
                		hmParam = srchInDs.get(idx);
                		dlwIvrMgmtService.deleteGenenumMgmtExtList(hmParam);
                	}
                }
                else if(sSvcId.equals("tap_holyday"))
                {
                	for(int idx = 0; idx < srchInDs.size(); idx++)
                	{
                		hmParam = srchInDs.get(idx);
                		dlwIvrMgmtService.deleteHolydayMgmtExtList(hmParam);
                	}
                }
                else if(sSvcId.equals("tap_script"))
                {
                	for(int idx = 0; idx < srchInDs.size(); idx++)
                	{
                		hmParam = srchInDs.get(idx);
                		dlwIvrMgmtService.deleteScriptMgmtExtList(hmParam);
                	}
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
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,   xpDto.getOutDataSetMap());
        }
        catch(Exception e)
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
     * 날짜 : 20210810
     * 이름 : 송준빈
     * 추가내용 : 대표번호목록조회 팝업 조회
     * 대상 테이블 : PS_WILLVI.TB_IVR_MASTER_NO
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/ivrMgmt/pop/getGenenumMgmtPopList")
    public ModelAndView getGenenumMgmtPopList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map<String, Object> hmParam = new HashMap<String, Object>();
        DataSetMap listMap = new DataSetMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";
        
        try 
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            //조회조건
            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
            
            if (srchInDs != null && srchInDs.size() > 0) 
            {
            	hmParam = srchInDs.get(0); 
            	List<Map<String, Object>> mList = dlwIvrMgmtService.getGenenumMgmtExtList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap); 
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,   xpDto.getOutDataSetMap());
        }
        catch(Exception e)
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
     * 날짜 : 20210810
     * 이름 : 송준빈
     * 추가내용 : 근무정보목록조회 팝업 조회
     * 대상 테이블 : PS_WILLVI.TB_IVR_WORK_MNG
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/ivrMgmt/pop/getWorkMgmtPopList")
    public ModelAndView getWorkMgmtPopList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map<String, Object> hmParam = new HashMap<String, Object>();
        DataSetMap listMap = new DataSetMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";
        
        try 
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            //조회조건
            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
            
            if (srchInDs != null && srchInDs.size() > 0) 
            {
            	hmParam = srchInDs.get(0);
            	List<Map<String, Object>> mList = dlwIvrMgmtService.getWorkMgmtExtList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap); 
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,   xpDto.getOutDataSetMap());
        }
        catch(Exception e)
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
     * 날짜 : 20210810
     * 이름 : 송준빈
     * 추가내용 : 휴일정보목록조회 팝업 조회
     * 대상 테이블 : PS_WILLVI.TB_IVR_HOLIDAY_MNG
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/ivrMgmt/pop/getHolydayPopList")
    public ModelAndView getHolydayPopList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map<String, Object> hmParam = new HashMap<String, Object>();
        DataSetMap listMap = new DataSetMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";
        
        try 
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            //조회조건
            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
            
            if (srchInDs != null && srchInDs.size() > 0) 
            {
            	hmParam = srchInDs.get(0);
            	List<Map<String, Object>> mList = dlwIvrMgmtService.getHolydayMgmtExtList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap); 
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,   xpDto.getOutDataSetMap());
        }
        catch(Exception e)
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