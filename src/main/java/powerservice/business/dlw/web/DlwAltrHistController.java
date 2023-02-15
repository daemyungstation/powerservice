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

import powerservice.business.bean.UserSession;
import powerservice.business.common.service.CommonService;
import powerservice.business.sys.service.BasVlService;
import powerservice.business.dlw.service.DlwAltrHistService;
import powerservice.common.util.CommonUtils;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;
import egovframework.rte.fdl.cmmn.exception.EgovBizException;

@Controller
public class DlwAltrHistController {
	
	@Resource
	private DlwAltrHistService dlwAltrHistService;
	
	@Resource
	private CommonService commonService;
	
	@Resource
    private BasVlService basVlService;
	
	/** ================================================================
     * 날짜 : 20210215
     * 이름 : 송준빈
     * 추가내용 : 일일종합현황데이터 처리일자 등록
     * 대상 테이블 : LF_DMUSER.TB_MEM_ACCNT_ALTR_HIST
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/altrHist/main/insertMemAccntAltrHistList")
    public ModelAndView insertOverallStatusBatchDay(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();


            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if(listInDs != null && listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("upd_man", hmParam.get("rgsr_id"));

                dlwAltrHistService.insertMemAccntAltrHistList(hmParam);
            }
 
            modelAndView.addObject("outVariables", xpDto.getOutVariableMap());
            modelAndView.addObject("outDataSets", xpDto.getOutDataSetMap());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }
        
        modelAndView.addObject("ErrorCode", szErrorCode);
        modelAndView.addObject("ErrorMsg", szErrorMsg);
        return modelAndView;
    }
    
    /** ================================================================
     * 날짜 : 20210215
     * 이름 : 송준빈
     * 추가내용 : 일일종합현황데이터 처리일자 조회
     * 대상 테이블 : LF_DMUSER.TB_MEM_ACCNT_ALTR_HIST
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/altrHist/main/getMemAccntAltrHistConsList")
    public ModelAndView getOverallStatusBatchDay(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if(listInDs != null && listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);
                
                List<Map<String, Object>> mList = dlwAltrHistService.getMemAccntAltrHistConsList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            }

            modelAndView.addObject("outVariables", xpDto.getOutVariableMap());
            modelAndView.addObject("outDataSets", xpDto.getOutDataSetMap());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }
        
        modelAndView.addObject("ErrorCode", szErrorCode);
        modelAndView.addObject("ErrorMsg", szErrorMsg);
        return modelAndView;
    }
	
	/** ================================================================
     * 날짜 : 20210215
     * 이름 : 송준빈
     * 추가내용 : 일일종합현황데이터 조회 조회리스트
     * 대상 테이블 : LF_DMUSER.TB_MEM_ACCNT_ALTR_HIST
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/altrHist/ext/getMemAccntAltrHistList")
    public ModelAndView getOverallStatusList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            if (listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);

                DataSetMap listInGds = (DataSetMap) mapInDs.get("gds_RequestCompVariable");
                if (listInGds != null && listInGds.size() > 0)
                {
                    Map pMap = listInGds.get(0);

                    String excel_fg = (String) mapInVar.get("excel_fg");
                    if (!"Y".equals(excel_fg))
                    {
                        hmParam.put("startLine", pMap.get("startNum"));
                        hmParam.put("endLine", pMap.get("endNum"));
                    }
                }
                
                int nTotal = dlwAltrHistService.getMemAccntAltrHistCount(hmParam);
                mapOutVar.put("nTotalListCnt", nTotal);

                List<Map<String, Object>> mList = dlwAltrHistService.getMemAccntAltrHistList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            }
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