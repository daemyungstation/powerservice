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
import powerservice.business.dlw.service.DlwNonPayService;
import powerservice.common.util.CommonUtils;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;
import egovframework.rte.fdl.cmmn.exception.EgovBizException;

@Controller
public class DlwNonPayController {
	
	
	@Resource
	private DlwNonPayService dlwNonPayService;
	
	@Resource
	private CommonService commonService;
	
	@Resource
    private BasVlService basVlService;
	
	/** ================================================================
     * 날짜 : 20221026
     * 이름 : 김주용
     * 추가내용 : 미납대상생성관리 조회 조회리스트
     * 대상 테이블 : LF_DMUSER.TB_NONPAYMENT_MNG
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/nonPay/titList")
    public ModelAndView getNonPayTitList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                List<Map<String, Object>> mList = dlwNonPayService.getNonPayTitList(hmParam);
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
	
	/** ================================================================
     * 날짜 : 20221026
     * 이름 : 김주용
     * 추가내용 : 미납대상생성관리 조회 조회리스트
     * 대상 테이블 : LF_DMUSER.TB_NONPAYMENT_MNG
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/nonPay/NpDtlList")
    public ModelAndView getNonPayList(XPlatformMapDTO xpDto, Model model) throws Exception {
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
                
                int nTotal = dlwNonPayService.getNonPayCount(hmParam);
                mapOutVar.put("nTotalListCnt", nTotal);

                List<Map<String, Object>> mList = dlwNonPayService.getNonPayList(hmParam);
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
    
    /** ================================================================
     * 날짜 : 20221026
     * 이름 : 김주용
     * 추가내용 : 미납대상생성관리 조회 조회리스트
     * 대상 테이블 : LF_DMUSER.TB_NONPAYMENT_MNG
     * ================================================================
     * */
    @RequestMapping(value = "/dlw/nonPay/pop/list")
    public ModelAndView getNonPayPopList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            if (listInDs.size() > 0)
            {
                hmParam = listInDs.get(0);
                
                DataSetMap listInGds = (DataSetMap) mapInDs.get("gds_RequestCompVariable");
                if (listInGds != null && listInGds.size() > 0)
                {
                    Map pMap = listInGds.get(0);

                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));
               
                }
                
                int nTotal = dlwNonPayService.getNonPayPopCount(hmParam);
                mapOutVar.put("nTotalListCnt", nTotal);
                
                List<Map<String, Object>> mList = dlwNonPayService.getNonPayPopList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
                
                List<Map<String, Object>> mList2 = dlwNonPayService.getNonPayTitList(hmParam);
                listMap2.setRowMaps(mList2);
                mapOutDs.put("ds_output2", listMap2);
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
    
    /**
     * 대명스테이션 미납대상 TITLE생성
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/dlw/nonPay/insertNonPayMentMst")
    public ModelAndView insertNonPayMentMst(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            
            String strInputType = (String) mapInVar.get("sType");
            
            if (listInDs.size() > 0)            {
                hmParam = listInDs.get(0);    
                
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("reg_man", hmParam.get("rgsr_id"));
                
                if(strInputType.equals("I")){
                	int mList = dlwNonPayService.insertNonPayMentMst(hmParam);                	                	
                } else {
                	int mList = dlwNonPayService.updateNonPayMentMst(hmParam);
                }                               
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
    
    /**
     * 대명스테이션 고객미납관리 산출
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/dlw/nonPay/insertNonPayMentDtl")
    public ModelAndView insertNonPayMentDtl(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
        
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

                hmParam.put("reg_man", oLoginUser.getUserid());
                
                int mList = dlwNonPayService.insertNonPayMentDtl(hmParam);
                //listMap.setRowMaps(mList);
                //mapOutDs.put("ds_output", listMap);
            }
            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }
    
    /**
    *  미납대상결과(NEW) 현황 조회
    *
    * @param pmParam Map<String, Object>
    * @return ModelAndView
    * @throws Exception
    */
   @RequestMapping(value = "dlw/nonPay/getNonPayResultList")
   public ModelAndView getNonPayResultList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

               int nTotal = dlwNonPayService.getNonPayResultListCount(hmParam);
               mapOutVar.put("nTotalListCnt", nTotal);

               List<Map<String, Object>> mList = dlwNonPayService.getNonPayResultList(hmParam);
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
   
   /** ================================================================
    * 날짜 : 20221110
    * 이름 : 김주용
    * 추가내용 : 미납대상월별정보 조회 조회리스트
    * 대상 테이블 : LF_DMUSER.TB_NONPAYMENT_MST, LF_DMUSER.TB_NONPAYMENT_DTL
    * ================================================================
    * */
   @RequestMapping(value = "/dlw/nonPay/monTotalList")
   public ModelAndView getMonTotalList(XPlatformMapDTO xpDto, Model model) throws Exception {
       ModelAndView modelAndView = new ModelAndView("xplatformMapView");
       DataSetMap listMap1 = new DataSetMap();
       DataSetMap listMap2 = new DataSetMap();
       DataSetMap listMap3 = new DataSetMap();
       DataSetMap listMap4 = new DataSetMap();
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
               
               int nTotal = dlwNonPayService.getNonPayMntTotCount(hmParam);
               mapOutVar.put("nTotalCnt", nTotal);

               List<Map<String, Object>> mList1 = dlwNonPayService.getNonPayMstMonList(hmParam);
               listMap1.setRowMaps(mList1);
               mapOutDs.put("ds_output", listMap1);
               
               List<Map<String, Object>> mList2 = dlwNonPayService.getNonPayDtlBndList(hmParam);
               listMap2.setRowMaps(mList2);
               mapOutDs.put("ds_output2", listMap2);
               
               List<Map<String, Object>> mList3 = dlwNonPayService.getNonPayDtlOverList(hmParam);
               listMap3.setRowMaps(mList3);
               mapOutDs.put("ds_output3", listMap3);
               
               List<Map<String, Object>> mList4 = dlwNonPayService.getNonPayDtlSectionList(hmParam);
               listMap4.setRowMaps(mList4);
               mapOutDs.put("ds_output4", listMap4);
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
   
   /** ================================================================
    * 날짜 : 20221125
    * 이름 : 조용우
    * 추가내용 : 미납대상생성관리 조회 조회리스트
    * 대상 테이블 : LF_DMUSER.TB_NONPAYMENT_MNG , TB_MEMBER_BASIC_INFO_DAY
    * ================================================================
    * */
   @RequestMapping(value = "/dlw/nonPay/selectNonPayPopList")
   public ModelAndView selectNonPayPopList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

           DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
           if (listInDs.size() > 0)
           {
               hmParam = listInDs.get(0);
               
               DataSetMap listInGds = (DataSetMap) mapInDs.get("gds_RequestCompVariable");
               if (listInGds != null && listInGds.size() > 0)
               {
                   Map pMap = listInGds.get(0);

                   hmParam.put("startLine", pMap.get("startNum"));
                   hmParam.put("endLine", pMap.get("endNum"));
              
               }
               
               //int nTotal = dlwNonPayService.getNonPayPopCount(hmParam);
               //mapOutVar.put("nTotalListCnt", nTotal);
               
               List<Map<String, Object>> mList = dlwNonPayService.selectNonPayPopList(hmParam);
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
   
   /** ================================================================
    * 날짜 : 20221125
    * 이름 : 조용우
    * 추가내용 : 미납대상자 조회
    * 대상 테이블 : LF_DMUSER.TB_NONPAYMENT_MNG , TB_MEMBER_BASIC_INFO_DAY
    * ================================================================
    * */
   @RequestMapping(value = "dlw/nonPay/selectNonPayPopDetailList")
   public ModelAndView selectNonPayPopDetailList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

               int nTotal = dlwNonPayService.selectNonPayPopDetailListCount(hmParam);
               mapOutVar.put("nTotalListCnt", nTotal);

               List<Map<String, Object>> mList = dlwNonPayService.selectNonPayPopDetailList(hmParam);
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