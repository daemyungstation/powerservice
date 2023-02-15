package powerservice.business.wdrw.web;

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
import powerservice.business.wdrw.service.WdrwResultMgmtService;
import powerservice.core.util.ParamUtils;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

@Controller
public class WdrwResultMgmtController {
	
	@Resource
	private WdrwResultMgmtService wdrwResultMgmtService;
	
	@Resource
	private CommonService commonService;

	/** ================================================================
     * 날짜 : 20200401
     * 이름 : 송준빈
     * 추가내용 : 고객청구결과관리 데이터 조회 리스트
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ, LF_DMUSER.TB_MEMBER_WDRW_RESULT, LF_DMUSER.TB_MEMBER_REQ_REFUND
     * ================================================================
     * */
    @RequestMapping(value = "/wdrw/resultMgmt/main/getWdrwResultMgmtList")
    public ModelAndView getWdrwResultMgmtList(XPlatformMapDTO xpDto, Model model) throws Exception {
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
                
                int nTotal = wdrwResultMgmtService.getWdrwResultMgmtCount(hmParam);
                mapOutVar.put("nTotalCount", nTotal);
                
                List<Map<String, Object>> mList = wdrwResultMgmtService.getWdrwResultMgmtList(hmParam);
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
     * 날짜 : 20200401
     * 이름 : 송준빈
     * 추가내용 : 고객청구결과관리 고객별 데이터 조회 리스트
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ, LF_DMUSER.TB_MEMBER_WDRW_RESULT, LF_DMUSER.TB_MEMBER_REQ_REFUND
     * ================================================================
     * */
    @RequestMapping(value = "/wdrw/resultMgmt/pop/getWdrwAccntLogList")
    public ModelAndView getWdrwAccntLogList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
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
                
                List<Map<String, Object>> mList = wdrwResultMgmtService.getWdrwAccntLogList(hmParam);
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
     * 날짜 : 20200401
     * 이름 : 송준빈
     * 추가내용 : 고객청구결과관리 고객별 데이터 조회 리스트
     * 대상 테이블 : LF_DMUSER.TB_MEMBER_WDRW_REQ, LF_DMUSER.TB_MEMBER_WDRW_RESULT, LF_DMUSER.TB_MEMBER_REQ_REFUND
     * ================================================================
     * */
    @RequestMapping(value = "/wdrw/resultMgmt/pop/getWdrwNRConfirm")
    public ModelAndView getWdrwNRConfirm(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try 
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();
            
            String sAccntNo = (String)mapInVar.get("accnt_no");

            if (sAccntNo != null || sAccntNo != "" || !sAccntNo.equalsIgnoreCase(""))
            {
            	List<Map<String, Object>> mList = wdrwResultMgmtService.getWdrwNRConfirm(sAccntNo);

                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap); //tc_mem=전체건수
            }
            else
            {
                szErrorCode = "-1";
                szErrorMsg = "회원번호가 공백입니다. 청구 판정이 불가능합니다.";
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);
                return modelAndView;
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
    
    /**
     * 대명라이프웨이 가상계좌 정보를 강제마감처리한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/wdrw/resultMgmt/pop/skjdnsdoj")
    public ModelAndView updateVrtlAccntRecoverProc(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try 
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

            for (int i = 0; i < listInDs.size(); i++) 
            {
                hmParam = listInDs.get(i);

                int rowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

                if (rowType == DataSet.ROW_TYPE_UPDATED)
                {
                	wdrwResultMgmtService.updateVrtlAccntRecoverProc(hmParam);
                }
            }

            //2018.01.17 접속 로그//////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) 
            {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            ////////////////////////////////////////////////////////////////////////////////////////////////////

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