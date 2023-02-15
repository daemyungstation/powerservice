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
import powerservice.business.dlw.service.DlwPerformInfoService;
import powerservice.core.util.ParamUtils;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

@Controller
public class DlwPerformInfoController {
	
	@Resource
	private DlwPerformInfoService dlwPerformInfoService;
	
	@Resource
	private CommonService commonService;
	
	@Resource
    private BasVlService basVlService;
	
	/** ================================================================
     * 날짜 : 20201209
     * 이름 : 송준빈
     * 추가내용 : 실적현황 마감처리
     * 대상 테이블 : LF_DMUSER.TB_RECORD_MAIN
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/perform/ext/insertPerformInfo")
    public ModelAndView insertEvtTranExt(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            DataSetMap dsPerformInfo = (DataSetMap)mapInDs.get("ds_input");
            
            if (dsPerformInfo != null && dsPerformInfo.size() > 0) 
            {
            	hmParam = dsPerformInfo.get(0);
            	int nCount = dlwPerformInfoService.getInsertPerformConfirm(hmParam);
            	
            	if(nCount > 0)
            	{
            		szErrorCode = "-1";
                    szErrorMsg = "이미 산출된 마감입니다. 중복 마감처리는 불가능 합니다.";
                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);
                    return modelAndView;
            	}
            	else
            	{
            		dlwPerformInfoService.insertPerformInfo(hmParam);
            	}
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
     * 날짜 : 20201209
     * 이름 : 송준빈
     * 추가내용 : 실적현황 주간보고, 월간접수실적
     * 대상 테이블 : LF_DMUSER.TB_RECORD_MAIN
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/perform/ext/getPerformInfo")
    public ModelAndView getEvtTranExt(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap listMapTot = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try 
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap dsPerformInfo = (DataSetMap)mapInDs.get("ds_input");
            
            if (dsPerformInfo != null && dsPerformInfo.size() > 0) 
            {
            	hmParam = dsPerformInfo.get(0);
            	String sSvcId = (String)hmParam.get("svc_id");
            	
            	DataSetMap listInGds = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
            	
                if (listInGds != null && listInGds.size() > 0) 
                {
                    Map pMap = listInGds.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));
                }
                
                if(sSvcId.equals("tp_performWeek"))
                {
                	int nTotal = dlwPerformInfoService.getPerformInfoWeekCount(hmParam);
                    mapOutVar.put("nTotalListCnt1", nTotal);
                    
                    List<Map<String, Object>> mList = dlwPerformInfoService.getPerformInfoWeekList(hmParam);
                    listMap.setRowMaps(mList);
                    mapOutDs.put("ds_output1", listMap); 
                    
                    List<Map<String, Object>> mListTot = dlwPerformInfoService.getPerformInfoTotList(hmParam);
                    listMapTot.setRowMaps(mListTot);
                    mapOutDs.put("ds_outputTot", listMapTot); 
                }
                else if(sSvcId.equals("tp_performMonth"))
                {
                	int nTotal = dlwPerformInfoService.getPerformInfoMonthCount(hmParam);
                    mapOutVar.put("nTotalListCnt2", nTotal);
                    
                    List<Map<String, Object>> mList = dlwPerformInfoService.getPerformInfoMonthList(hmParam);
                    listMap.setRowMaps(mList);
                    mapOutDs.put("ds_output2", listMap); 
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
}