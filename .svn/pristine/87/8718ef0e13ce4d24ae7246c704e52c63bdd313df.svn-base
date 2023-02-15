package powerservice.business.dlw.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.dlw.service.DlwReadyCashService;
import powerservice.core.util.ParamUtils;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

@Controller
@RequestMapping(value = "/dlw/readycash")
public class DlwReadyCashController {
	
	@Resource
	private DlwReadyCashService dlwReadyCashService;

	/** ================================================================
     * 날짜 : 20190509
     * 이름 : 송준빈
     * 추가내용 : 회원별 레디캐쉬 조회
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_DLWMALL
     * ================================================================
     * */
    @RequestMapping(value = "/dlwmall/getMemberDlwmallList")
    public ModelAndView getMemberUploadDataList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap1 = new DataSetMap();
        DataSetMap listMap2 = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

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
                ParamUtils.addSaveParam(hmParam);

                // 페이징 정보
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

                int nTotal = dlwReadyCashService.getMemberDlwmallCount(hmParam);
                mapOutVar.put("nTotalListCnt", nTotal);

                List<Map<String, Object>> mList1 = dlwReadyCashService.getMemberDlwmallList(hmParam);
                listMap1.setRowMaps(mList1);
                mapOutDs.put("ds_output1", listMap1);
                
                List<Map<String, Object>> mList2 = dlwReadyCashService.getMemberDlwmallSummary(hmParam);
                listMap2.setRowMaps(mList2);
                mapOutDs.put("ds_output2", listMap2);
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }
    
}
