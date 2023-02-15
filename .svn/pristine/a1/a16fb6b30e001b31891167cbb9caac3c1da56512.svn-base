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
import powerservice.business.dlw.service.DlwCtiEmplInfoService;
import powerservice.core.util.ParamUtils;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

@Controller
public class DlwCtiEmplInfoController {
	
	@Resource
	private DlwCtiEmplInfoService dlwCtiEmplInfoService;
	
	@Resource
	private CommonService commonService;
	
	@Resource
    private BasVlService basVlService;
	
	/** ================================================================
     * 날짜 : 20200619
     * 이름 : 송준빈
     * 추가내용 : CTI 사원 기본정보 조회 리스트
     * 대상 테이블 : LF_DMUSER.TB_CTI_MAIN_INFO
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/ctiEmpl/info/getCtiEmplInfoList")
    public ModelAndView getCtiMainInfoList(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            	String sSvcId = (String)hmParam.get("svc_id");
            	
            	DataSetMap listInGds = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
            	
                if (listInGds != null && listInGds.size() > 0) 
                {
                    Map pMap = listInGds.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));
                }
                
                if(sSvcId.equals("tp_ctiMainInfo"))
                {                    
                    int nTotal = dlwCtiEmplInfoService.getCtiMainInfoCount(hmParam);
                    mapOutVar.put("nTotalListCount1", nTotal);
                    
                    List<Map<String, Object>> mList = dlwCtiEmplInfoService.getCtiMainInfoList(hmParam);
                    listMap.setRowMaps(mList);
                    mapOutDs.put("ds_output1", listMap); 

                }
                else if(sSvcId.equals("tp_ctiQueInfo"))
                {
                	int nTotal = dlwCtiEmplInfoService.getCtiQueInfoCount(hmParam);
                    mapOutVar.put("nTotalListCount2", nTotal);
                    
                    List<Map<String, Object>> mList = dlwCtiEmplInfoService.getCtiQueInfoList(hmParam);
                    listMap.setRowMaps(mList);
                    mapOutDs.put("ds_output2", listMap); 
                }
                else if(sSvcId.equals("tp_ctiInfoSum"))
                {
                	int nTotal = dlwCtiEmplInfoService.getCtiInfoSumCount(hmParam);
                    mapOutVar.put("nTotalListCount3", nTotal);
                    
                    List<Map<String, Object>> mList = dlwCtiEmplInfoService.getCtiInfoSumList(hmParam);
                    listMap.setRowMaps(mList);
                    mapOutDs.put("ds_output3", listMap); 
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