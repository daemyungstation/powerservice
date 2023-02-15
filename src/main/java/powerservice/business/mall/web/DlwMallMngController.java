package powerservice.business.mall.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.bean.UserSession;
import powerservice.business.mall.service.DlwMallLinkedService;
import powerservice.business.mall.service.DlwMallMngService;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

@Controller
public class DlwMallMngController {
	
	@Resource
	private DlwMallMngService dlwMallMngService;

	/* ================================================================
     * 날짜 : 20220617
     * 이름 : 김주용
     * 추가내용 : 쇼핑몰회원조회
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/mallMng/getMallMemberList")
    public ModelAndView getMemberBasicList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap01 = new DataSetMap();
        DataSetMap listMap02 = new DataSetMap();
        DataSetMap listMap03 = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        List<Map<String, Object>> mList01 = new ArrayList<Map<String, Object>>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";
        String sMpaYn = "";

        try 
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");

            if (srchInDs != null && srchInDs.size() > 0)
            {
                hmParam = srchInDs.get(0);
            }
            
            //String sExcelYn = StringUtils.defaultString((String) mapInVar.get("excel_yn"));
            String sExcelYn = "N"; 

            if (!"Y".equals(sExcelYn)) {
                // 페이징 정보
                DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                if (listInDs != null && listInDs.size() > 0) {
                    Map pMap = listInDs.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));
                }
            }
            
            int nTotal = dlwMallMngService.getMallMemberCount(hmParam);
            
            mList01 = dlwMallMngService.getMallMemberList(hmParam);
        	listMap01.setRowMaps(mList01);
            mapOutDs.put("ds_output", listMap01);
            mapOutVar.put("tc_list", nTotal);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }
    
    /* ================================================================
     * 날짜 : 20220617
     * 이름 : 김주용
     * 추가내용 : 쇼핑몰레디캐시이력조회
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/mallMng/getMallCashHistroyList")
    public ModelAndView getMallCashHistroyList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap01 = new DataSetMap();
        DataSetMap listMap02 = new DataSetMap();
        DataSetMap listMap03 = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        List<Map<String, Object>> mList01 = new ArrayList<Map<String, Object>>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";
        String sMpaYn = "";

        try 
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");

            if (srchInDs != null && srchInDs.size() > 0)
            {
                hmParam = srchInDs.get(0);
            }
            
            String sExcelYn = StringUtils.defaultString((String) mapInVar.get("excel_yn"));

            if (!"Y".equals(sExcelYn)) {
                // 페이징 정보
                DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                if (listInDs != null && listInDs.size() > 0) {
                    Map pMap = listInDs.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));
                }
            }
            
            int nTotal = dlwMallMngService.getMallCashHistroyCount(hmParam);
            
            mList01 = dlwMallMngService.getMallCashHistroyList(hmParam);
        	listMap01.setRowMaps(mList01);
            mapOutDs.put("ds_output", listMap01);
            mapOutVar.put("tc_list", nTotal);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }
    
    /* ================================================================
     * 날짜 : 20220617
     * 이름 : 김주용
     * 추가내용 : 쇼핑몰회원이력조회
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/mallMng/getMallMemHistroyList")
    public ModelAndView getMallMemHistroyList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap01 = new DataSetMap();
        DataSetMap listMap02 = new DataSetMap();
        DataSetMap listMap03 = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        List<Map<String, Object>> mList01 = new ArrayList<Map<String, Object>>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";
        String sMpaYn = "";

        try 
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");

            if (srchInDs != null && srchInDs.size() > 0)
            {
                hmParam = srchInDs.get(0);
            }
            
            String sExcelYn = StringUtils.defaultString((String) mapInVar.get("excel_yn"));

            if (!"Y".equals(sExcelYn)) {
                // 페이징 정보
                DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                if (listInDs != null && listInDs.size() > 0) {
                    Map pMap = listInDs.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));
                }
            }
            
            int nTotal = dlwMallMngService.getMallMemHistroyCount(hmParam);
            
            mList01 = dlwMallMngService.getMallMemHistroyList(hmParam);
        	listMap01.setRowMaps(mList01);
            mapOutDs.put("ds_output", listMap01);
            mapOutVar.put("tc_list", nTotal);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }
    
    /**
     * 쇼핑몰 회원 업데이트
     * 김주용
     * 20220627
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/dlw/mallMng/updateMallMember")
    public ModelAndView updateMallMember(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
            DataSetMap srchInDs2 = (DataSetMap)mapInDs.get("ds_input_old");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
                hmParam2 = srchInDs2.get(0);

                ParamUtils.addSaveParam(hmParam);

                //System.out.println("==================crudTyp : " + crudTyp);

                dlwMallMngService.updateMallMember(hmParam);
                
                if(!hmParam.get("state").equals(hmParam2.get("state"))) {
                	dlwMallMngService.insertMallMemberHistory(hmParam);
                }
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }
}