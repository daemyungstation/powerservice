package powerservice.business.dlw.web;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import kr.co.nicevan.nicepay.adapter.web.NicePayHttpServletRequestWrapper;
import kr.co.nicevan.nicepay.adapter.web.NicePayWEB;
import kr.co.nicevan.nicepay.adapter.web.dto.WebMessageDTO;
import kr.co.nicevan.nicepay.adaptor.etc.HttpServletRequestMock;
import kr.co.nicevan.nicepay.adaptor.etc.HttpServletResponseMock;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tobesoft.xplatform.data.DataSet;

import powerservice.business.bean.UserSession;
import powerservice.business.common.service.CommonService;
import powerservice.business.dlw.service.DataAthrQuryService;
import powerservice.business.dlw.service.DlwNewTypeMainPopService;
import powerservice.business.sys.service.BasVlService;
import powerservice.common.util.CommonUtils;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;


/**
 * 대명 상담 메인 화면을 관리한다.
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 최현식
 * @version 1.0
 * @date 2016/02/03
 * @프로그램ID DlwCust
 */
@Controller
@RequestMapping(value="/dlw/newTypePop")
public class DlwNewTypeMainPopController {
	
	
	@Resource
    private DlwNewTypeMainPopService DlwNewTypeMainPopService;
	
	@Resource
    private DataAthrQuryService dataAthrQuryService;
	
	@Resource
    private CommonService commonService;
	
	@Resource
    private BasVlService basVlService;
	
	/**
     * 회원 필드 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/div4/getUserOption")
    public ModelAndView getUserOption(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            }

            //List<Map<String, Object>> mList = DlwPayService.getMemberMangiGiftset(hmParam);
            List<Map<String, Object>> mList = DlwNewTypeMainPopService.getUserOption(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

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
    
    /**
     * 만기연장 수수료 산출
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/div4/mergeUserOption")
    public ModelAndView mergeUserOption(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        List<Map<String, Object>> mList = new ArrayList<Map<String,Object>>();
        
        Map<String,Object> rowData = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try
        {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();
            
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            
            for (int i = 0; i < listInDs.size(); i++) {
                hmParam = listInDs.get(i);
                //세션
                int rowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();
                ParamUtils.addSaveParam(hmParam);
                
                if (rowType == DataSet.ROW_TYPE_DELETED) {
                	DlwNewTypeMainPopService.deleteUserOption(hmParam);
                } else if(rowType == DataSet.ROW_TYPE_INSERTED){
                	DlwNewTypeMainPopService.mergeUserOption(hmParam);
                } else if (rowType == DataSet.ROW_TYPE_UPDATED) {
                	DlwNewTypeMainPopService.mergeUserOption(hmParam);
                }
//                DlwPayService.updateMemberMangiAmtDataList(hmParam);
                
            }

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
    
    /**
     * 고객-상품 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/div1/list")
    public ModelAndView getDlwCustPrdtList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);

                ParamUtils.addCenterParam(hmParam);

                List<Map<String, Object>> mList = DlwNewTypeMainPopService.getDlwCustPrdtList(hmParam);
                mapOutVar.put("tc_mem_prod", mList.size());
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
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
    
    /**
     * 대명라이프웨이 CMS 금일 부가서비스 등록이력을 조회한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/div4/cms-aplc_dtl/list")
    public ModelAndView getDlwCmsAplcDtl(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            hmParam = listInDs.get(0);

            // 페이징 정보
            DataSetMap listInGds = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
            if (listInGds != null && listInGds.size() > 0) {
                Map pMap = listInGds.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }

            //세션
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("emple_no", hmParam.get("rgsr_id"));
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            hmParam.put("upd_man", hmParam.get("rgsr_id"));

            hmParam.put("paramEmpleNo", hmParam.get("rgsr_id"));
            hmParam.put("paramAs", "EMP");

            String dataAthrQury = dataAthrQuryService.getDataAthrQury(hmParam);
            hmParam.put("dataAthrQury", dataAthrQury);

            int nTotal = DlwNewTypeMainPopService.getDlwCmsAplcDtlCount(hmParam);

            mapOutVar.put("tc_cmsAplcDtl", nTotal);

            List<Map<String, Object>> mList = DlwNewTypeMainPopService.getDlwCmsAplcDtl(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            //2018.01.05 접속 로그//////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            ////////////////////////////////////////////////////////////////////////////////////////////////////

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
     * 대명라이프웨이 CMS 산출중 여부를 확인한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/div4/cms-wdrw-chk")
    public ModelAndView getDlwCmsWdrwChk(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();            

            hmParam.put("accnt_no", mapInVar.get("accnt_no"));            

            String nResult = "";

            List<Map<String, Object>> mList = DlwNewTypeMainPopService.getDlwCmsWdrwChk(hmParam);
            if (mList != null && mList.size() > 0) {
                nResult = (String)mList.get(0).get("cms_wdrw");
            }

            mapOutVar.put("cmsWdrwChk", nResult);

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
     * 대명라이프웨이 Cms 결과 파일 정보 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/div4/cms-info")
    public ModelAndView getCmsInfoByAccnt(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            Map<String, Object> hmParam = new HashMap<String, Object>();

            String accnt_no = (String)mapInVar.get("accnt_no");
            hmParam.put("accnt_no", accnt_no);

            List<Map<String, Object>> mList = DlwNewTypeMainPopService.getCMSInfoByAccnt(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

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
     * 대명라이프웨이 CMS 부가서비스(신규,해지)를 등록한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/div4/cms-anxt-srvc/save")
    public ModelAndView insertDlwCmsAnxtSrvc(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            hmParam = listInDs.get(0);

            //세션
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("emple_no", hmParam.get("rgsr_id"));
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            hmParam.put("upd_man", hmParam.get("rgsr_id"));
            
            // CMS 회원 여부
            String cmsMemYN = "";	// CMS 회원 여부
            String cmsMsg = "";		// 결과 전달 메세지
            
            //통합구좌 확인     
//            String strDblStat  = (String)mapInVar.get("double_stat");            
//            hmParam.put("double_stat", strDblStat);								// 통합구좌 상태값 (STAT값이 2인경우 기존회원번호 변경)	
            
            cmsMemYN  = hmParam.get("cms_yn").toString();
                        
            hmParam.put("depr_nm", hmParam.get("depr"));						// 예금주  
        	hmParam.put("inv_tg_dt", hmParam.get("ichae_dt"));					// 이체일자
            hmParam.put("idn_no", hmParam.get("birth"));							// 생년월일            
            hmParam.put("rltn_cd", hmParam.get("reltn"));							// 관계   
                                    
//            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx : " + strDblStat);
                          
            if (cmsMemYN.equals("N")){
            	//CMS 회원이 등록되어 있음 (DEL_FG = 'N')
                hmParam.put("acpt_mthd", "04");										// 04: 정상부가해지, 05: 보류부가해지, 03: 부가신규
                hmParam.put("app_cl", "3");											// 3: 해지, 1: 신규, 7 : 보류해지                            
                cmsMsg = DlwNewTypeMainPopService.deleteDlwCmsAnxtSrvc(hmParam);    
            } else {
            	 // CMS 회원이 등록된 적이 있거나(DEL_FG = 'Y') 등록되지 않음 (NONE)
                hmParam.put("acpt_mthd", "03");										// 04: 정상부가해지, 05: 보류부가해지, 03: 부가신규
                hmParam.put("app_cl", "1");												// 3: 해지, 1: 신규, 7 : 보류해지                
            	cmsMsg = DlwNewTypeMainPopService.insertDlwCmsAnxtSrvc(hmParam);            	
            }
            mapOutVar.put("cmsReslMsg", cmsMsg);
                       
            //2018.01.05 접속 로그////////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            //////////////////////////////////////////////////////////////////////////////////////////////////////

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
            
            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);
        }        
        return modelAndView;
    }
    
    /**
     * 대명라이프웨이 CMS 신청 실패 로그 등록
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/div4/cms-history-logs/save")
    public ModelAndView insertDlwCmsHistoryLogs(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            hmParam = listInDs.get(0);

            //세션
            ParamUtils.addSaveParam(hmParam);
            
            DlwNewTypeMainPopService.insertDlwCmsHistoryLogs(hmParam);
            
            //2018.01.05 접속 로그////////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            //////////////////////////////////////////////////////////////////////////////////////////////////////

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
            
            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);
        }        
        return modelAndView;
    }
    
    /**
     * 대명스테이션 카드 부가서비스(신규,해지)를 등록한다. (나이스 전용)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/div4/card-anxt-srvc/save")
    public ModelAndView insertDlwCardAnxtSrvc(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();
            
            // 카드 신청 시 카드 혜택이 끝난 체크 대상자는 강제로 납입회차를 100으로 늘린다. (납입회차 36기준으로 dmlife001 가맹번호 빌키 생성)
            String sBenefitYn = (String)mapInVar.get("benefit");
            
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            hmParam = listInDs.get(0);

            //세션
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("emple_no", hmParam.get("rgsr_id"));
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            hmParam.put("upd_man", hmParam.get("rgsr_id"));
            
            // 카드 회원 여부
            String cardMemYN = "";	// CARD 회원 여부
            String cardMsg = "";		// 결과 전달 메세지
            
            cardMemYN  = hmParam.get("card_yn").toString();
            String syyyy = hmParam.get("yyyy").toString();
            String smm = hmParam.get("mm").toString();
            String expr_Dt = syyyy + smm;
           
            // 카드 신청 시 카드 혜택이 끝난 체크 대상자는 강제로 납입회차를 100으로 늘린다. (납입회차 36기준으로 dmlife001 가맹번호 빌키 생성)
            hmParam.put("pay_no", sBenefitYn);	
            
            //통합구좌 확인     
//            String strDblStat  = (String)mapInVar.get("double_stat");            
//            hmParam.put("double_stat", strDblStat);								// 통합구좌 상태값 (STAT값이 2인경우 기존회원번호 변경)	
            
            //이니시스 여부 등록       
            hmParam.put("ini_yn", "N");
            
        	hmParam.put("inv_tg_dt", hmParam.get("ichae_dt"));					// 이체일자
            hmParam.put("idn_no", hmParam.get("birth"));							// 생년월일
            hmParam.put("exp_year", syyyy);											// 만기 연도            
            hmParam.put("exp_mon", smm);											// 만기 월		
            hmParam.put("exp_dt", expr_Dt);											// 이체일자
            
            hmParam.put("cell", "");													// 전화번호(기초데이터)
            hmParam.put("email", "");												// 이메일(기초데이터)
            
            System.out.println(hmParam);
                                                
            if (cardMemYN.equals("N")){
            	//CARD 회원이 등록되어 있음 (DEL_FG = 'N')
                hmParam.put("app_cl", "3");											// 3: 해지, 1: 신규, 7 : 보류해지                            
                cardMsg = DlwNewTypeMainPopService.deleteDlwCardAnxtSrvc(hmParam);    
            } else {    
            	 // CARD 회원이 등록된 적이 있거나(DEL_FG = 'Y') 등록되지 않음 (NONE)
                hmParam.put("app_cl", "1");											// 3: 해지, 1: 신규, 7 : 보류해지               
            	cardMsg = DlwNewTypeMainPopService.insertDlwCardAnxtSrvc(hmParam);         	
            }
            mapOutVar.put("gv_rslMsg", cardMsg);
                       
            //2018.01.05 접속 로그////////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            //////////////////////////////////////////////////////////////////////////////////////////////////////

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
            
            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);
        }        
        return modelAndView;
    }
    
    
    /**
     * 대명스테이션 카드 부가서비스(신규,해지)를 등록한다. (이니시스 전용)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/div4/card-inicis/save")
    public ModelAndView insertDlwInicisCardAnxtSrvc(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();
            
            // 카드 신청 시 카드 혜택이 끝난 체크 대상자는 강제로 납입회차를 100으로 늘린다. (납입회차 36기준으로 dmlife001 가맹번호 빌키 생성)
            String sBenefitYn = (String)mapInVar.get("benefit");
                                    
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            hmParam = listInDs.get(0);

            //세션
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("emple_no", hmParam.get("rgsr_id"));
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            hmParam.put("upd_man", hmParam.get("rgsr_id"));
            
            // 카드 회원 여부
            String cardMemYN = "";	// CARD 회원 여부
            String cardMsg = "";		// 결과 전달 메세지
            
            cardMemYN  = hmParam.get("card_yn").toString();
            String syyyy = hmParam.get("yyyy").toString();
            String smm = hmParam.get("mm").toString();
            String expr_Dt = syyyy + smm;
           
            // 카드 신청 시 카드 혜택이 끝난 체크 대상자는 강제로 납입회차를 100으로 늘린다. (납입회차 36기준으로 dmlife001 가맹번호 빌키 생성)
            hmParam.put("pay_no", sBenefitYn);	
                        
            //이니시스 여부 등록       
            hmParam.put("ini_yn", "Y");
            
        	hmParam.put("inv_tg_dt", hmParam.get("ichae_dt"));					// 이체일자
            hmParam.put("idn_no", hmParam.get("birth"));							// 생년월일
            hmParam.put("exp_year", syyyy);											// 만기 연도            
            hmParam.put("exp_mon", smm);											// 만기 월		
            hmParam.put("exp_dt", expr_Dt);											// 이체일자
            
            
            System.out.println(hmParam);
                                                
            if (cardMemYN.equals("N")){
            	//CARD 회원이 등록되어 있음 (DEL_FG = 'N')
                hmParam.put("app_cl", "3");											// 3: 해지, 1: 신규, 7 : 보류해지                            
                cardMsg = DlwNewTypeMainPopService.deleteDlwCardAnxtSrvc(hmParam);    
            } else {    
            	 // CARD 회원이 등록된 적이 있거나(DEL_FG = 'Y') 등록되지 않음 (NONE)
                hmParam.put("app_cl", "1");											// 3: 해지, 1: 신규, 7 : 보류해지               
            	cardMsg = DlwNewTypeMainPopService.insertDlwInicisCardAnxtSrvc(hmParam);         	
            }
            mapOutVar.put("gv_rslMsg", cardMsg);
                       
            //2018.01.05 접속 로그////////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            //////////////////////////////////////////////////////////////////////////////////////////////////////

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
            
            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);
        }        
        return modelAndView;
    }
    
    /**
     * 이니시스 장기할부 카드 체크 + 등록
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/div4/longterm-inicis/save")
    public ModelAndView insertDlwInicisLongtermCardCheck(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();
            
            Map <String, Object> mapCardCheck     = null; // 장기할부 카드 체크 (정상, 하나카드여부등)
            
            // 카드 신청 시 카드 혜택이 끝난 체크 대상자는 강제로 납입회차를 100으로 늘린다. (납입회차 36기준으로 dmlife001 가맹번호 빌키 생성)
            String sBenefitYn = (String)mapInVar.get("benefit");
                                    
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            hmParam = listInDs.get(0);

            //세션
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("emple_no", hmParam.get("rgsr_id"));
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            hmParam.put("upd_man", hmParam.get("rgsr_id"));
            
            // 카드 회원 여부
            String cardMemYN = "";	// CARD 회원 여부
            String cardMsg = "";		// 결과 전달 메세지
            
            cardMemYN  = hmParam.get("card_yn").toString();
            String syyyy = hmParam.get("yyyy").toString();
            String smm = hmParam.get("mm").toString();
            String expr_Dt = syyyy + smm;
           
            // 카드 신청 시 카드 혜택이 끝난 체크 대상자는 강제로 납입회차를 100으로 늘린다. (납입회차 36기준으로 dmlife001 가맹번호 빌키 생성)
            hmParam.put("pay_no", sBenefitYn);	
                        
            //이니시스 여부 등록       
            hmParam.put("ini_yn", "Y");
            
        	hmParam.put("inv_tg_dt", hmParam.get("ichae_dt"));					// 이체일자
            hmParam.put("idn_no", hmParam.get("birth"));							// 생년월일
            hmParam.put("exp_year", syyyy);											// 만기 연도            
            hmParam.put("exp_mon", smm);											// 만기 월		
            hmParam.put("exp_dt", expr_Dt);											// 이체일자
            
            System.out.println(hmParam);
            
            mapCardCheck = DlwNewTypeMainPopService.getDlwInicisLongtermCardCheck(hmParam);	//하나장기할부 카드 체크 진행
            
            //결과 메세지 리턴하기
            mapOutVar.put("gv_rslMsg", mapCardCheck.get("rtn_msg"));
                                   
            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
            
            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);
        }        
        return modelAndView;
    }

    /**
     * 상담 정보를 등록/수정한다. (대명 - CMS/카드 부가서비스 등록시)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/div4/update_cms")
    public ModelAndView updateCmsInfo(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input"); 				// 변경후 dataset
            DataSetMap listInOldDs = (DataSetMap)mapInDs.get("ds_input_old"); 				// 변경후 dataset

            if (listInDs.size() > 0) {
                Map <String, Object> hmParam = listInDs.get(0);
                Map <String, Object> hmParam_old = listInOldDs.get(0);
                                                
                // CMS 변경 정보 업데이트
                DlwNewTypeMainPopService.updateCmsInfo(hmParam);
                
                /**************************************************상담등록 필드 구성*********************************************************/ 
                String chgText = "[CMS] 이체일자가 변경되었습니다. [" + hmParam_old.get("ichae_dt").toString() + " >> " + hmParam.get("ichae_dt").toString() +  "]" ;

                // 상담유형 1, 2, 3
                hmParam.put("cons_typ1_cd", "CT01010000");		//회원정보
                hmParam.put("cons_typ2_cd", "CT01010100");		//결제정보
                hmParam.put("cons_typ3_cd", "CT01010102");		//결제수단변경
                hmParam.put("cons_memo_tit", chgText);
                /*******************************************************************************************************************************/
                
                // 상담 등록
                saveConsdlw(hmParam); 
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
            
            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);
        }
        return modelAndView;
    }
    
    /**
     * 상담 정보를 등록/수정한다. (대명 - CMS/카드 부가서비스 등록시)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/div4/update_card")
    public ModelAndView updateCardInfo(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input"); 				// 변경후 dataset
            DataSetMap listInOldDs = (DataSetMap)mapInDs.get("ds_input_old"); 				// 변경후 dataset

            if (listInDs.size() > 0) {
                Map <String, Object> hmParam = listInDs.get(0);
                Map <String, Object> hmParam_old = listInOldDs.get(0);
                                                
                // 카드 변경 정보 업데이트
                DlwNewTypeMainPopService.updateCardInfo(hmParam);
                
                /**************************************************상담등록 필드 구성*********************************************************/ 
                String chgText = "[CARD] 이체일자가 변경되었습니다. [" + hmParam_old.get("ichae_dt").toString() + " >> " + hmParam.get("ichae_dt").toString() +  "]" ;

                // 상담유형 1, 2, 3
                hmParam.put("cons_typ1_cd", "CT01010000");		//회원정보
                hmParam.put("cons_typ2_cd", "CT01010100");		//결제정보
                hmParam.put("cons_typ3_cd", "CT01010102");		//결제수단변경
                hmParam.put("cons_memo_tit", chgText);
                /*******************************************************************************************************************************/
                
                // 상담 등록
                saveConsdlw(hmParam); 
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
            
            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);
        }
        return modelAndView;
    }
    
    /**
     * 상담 정보를 등록/수정한다. (대명)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    public void saveConsdlw(Map <String, Object> pmParam) throws Exception {
        String sConsno = "";
        
        String sAccntNo = StringUtils.defaultString((String)pmParam.get("accnt_no")); 	// 회원번호
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();				// 담당자 아이디

        //상담이력 저장 (TB_CONS)
        pmParam.put("consno_sqno", "1");   				// SEQ
        pmParam.put("acpg_chnl_cd", "99");					// 접수채널 코드 (기타)
        pmParam.put("cons_stat_cd", "30"); 					//상담상태 - 처리완료
        pmParam.put("cons_dspsmddl_dtpt_cd", "10"); 		//상담처리중 - 일반     
        
        pmParam.put("rgsr_id", oLoginUser.getUserid());
        pmParam.put("amnd_id", oLoginUser.getUserid());
        pmParam.put("cnsl_man", oLoginUser.getUserid());
        
        sConsno = DlwNewTypeMainPopService.insertCons(pmParam);
        
        /*
        // 담당자 정보 저장
        pmParam.put("rsps_dept_cd", oLoginUser.getTeamcd());
        pmParam.put("chpr_id", oLoginUser.getUserid());
        ParamUtils.addSaveParam(pmParam);
        pmParam.put("cons_memo_tit", pmParam.get("cons_memo"));
        pmParam.put("cons_memo_cntn", pmParam.get("cnsl_con"));

        pmParam.put("clnt_nm", pmParam.get("mem_nm"));
        
        
        pmParam.put("conc_yn", "N");

        //대명상담 관련
        pmParam.put("gubn", "80"); // COM_CD_GRP 테이블 기타
        pmParam.put("cnsl_cd", "01"); // COM_CD_GRP 테이블 기타

        if ("Y".equals(pmParam.get("pyin_chng_yn"))) {
            pmParam.put("cons_typ1_cd", "CT01010000" );
            pmParam.put("cons_typ2_cd", "CT01010100");
            pmParam.put("cons_typ3_cd", "CT01010102");
        } else {
            pmParam.put("cons_typ1_cd", "CT01040000" );
            pmParam.put("cons_typ2_cd", "CT01040100");
            pmParam.put("cons_typ3_cd", "CT01040101");
        }

        // 회원번호가 없을 경우 기타문의 설정
        if ("".equals(sAccntNo)) {
            pmParam.put("accnt_no", "00000"); // 기타문의 lifeMngController.java 1199
        }

        // 신규등록
        if ("".equals(sConsno)) {
            pmParam.put("actp_id", oLoginUser.getUserid()); // 접수자 정보
            // 상담 등록
            sConsno = DlwNewTypeMainPopService.insertCons(pmParam);
        }
        */
    }
    
    /**
     * 온라인 가입회원 구좌번호로 CMS/카드정보 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/div4/online-accnt/list")
    public ModelAndView getDlwOnlineMemberInfo(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            hmParam.put("accnt_no", mapInVar.get("accnt_no"));

            String gubun = (String)mapInVar.get("gubun");

            String ncaIdnNo = "";

            List<Map<String, Object>> mList = new ArrayList<Map<String,Object>> ();

            if("card".equals(gubun)) {
            	DlwNewTypeMainPopService.getDlwOnlineMemberCardInfo(hmParam);
            } else {
            	DlwNewTypeMainPopService.getDlwOnlineMemberCmsInfo(hmParam);
                ncaIdnNo = DlwNewTypeMainPopService.getNcaIdnNo(hmParam);
            }

            mapOutVar.put("ncaIdnNo", ncaIdnNo);

            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

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
     * 장기할부용 고객-구좌 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/div4/cust-acnt/longterm_list")
    public ModelAndView getDlwCustLongTermList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();  

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();
            
            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
                
                // CMS 조회인지 카드 조회인지 구분
                String svcid = (String) mapInVar.get("pay_mthd");                
                hmParam.put("pay_mthd", svcid);
                                
                List<Map<String, Object>> mList = DlwNewTypeMainPopService.getDlwCustLongTermList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
            
            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);
        }

        return modelAndView;
    }
    
    
    /**
     * 고객-구좌 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/div4/cust-acnt/accnt_list")
    public ModelAndView getDlwCustAcntList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();  

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();
            
            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
                
                // CMS 조회인지 카드 조회인지 구분
                String svcid = (String) mapInVar.get("pay_mthd");                
                hmParam.put("pay_mthd", svcid);
                                
                List<Map<String, Object>> mList = DlwNewTypeMainPopService.getDlwCustAcntList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
            
            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);
        }

        return modelAndView;
    }
    
    /**
     * 코드 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/div4/cd-list")
    public ModelAndView getDlwCdList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            // 페이징 정보
            DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
            if (listInDs != null && listInDs.size() > 0) {
                Map pMap = listInDs.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }

            int nTotal = DlwNewTypeMainPopService.getDlwCdCount(hmParam);

            String svcid = (String) mapInVar.get("svcid");

            if("selectBankList".equals(svcid)) {
                mapOutVar.put("tc_bank", nTotal);
            } else if ("selectCremationList".equals(svcid)) {
                mapOutVar.put("tc_cremation", nTotal);
            } else if ("selectPurList".equals(svcid)) {
                mapOutVar.put("tc_pur", nTotal);
            }

            List<Map<String, Object>> mList = DlwNewTypeMainPopService.getDlwCdList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

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
     * 특수회원 조회
     * 정승철
     * 20181018
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/div4/srchSpecialAcntList")
    public ModelAndView getSpecialAcntList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();

        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();


            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
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

            int nTotal = DlwNewTypeMainPopService.getCntSpecialAcntList(hmParam);

            List<Map<String, Object>> mList = DlwNewTypeMainPopService.getSpecialAcntList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

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
     * 산출특수회원 입력/수정/삭제
     * 정승철
     * 20181019
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/div4/extSpecial/{crudType}")
    public ModelAndView crudExtSpecial(XPlatformMapDTO xpDto, Model model, @PathVariable("crudType") String crudTyp) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);

                ParamUtils.addSaveParam(hmParam);

                //System.out.println("==================crudTyp : " + crudTyp);

                if("insert".equals(crudTyp)){
                    // 기존 특수회원인지 체크
                    int chkCnt = DlwNewTypeMainPopService.getChkSpecialAcntList(hmParam);

                    if(chkCnt == 0) {
                        hmParam.put("reg_man", hmParam.get("rgsr_id"));
                        DlwNewTypeMainPopService.insertExtSpecial(hmParam);
                    }
                    else {
                        mapOutVar.put("g_reason_msg", "해당 회원은 이미 특수회원으로 등록되어 있습니다.");  // 실패 메시지 return
                    }
                }
                else if("update".equals(crudTyp)){
                    hmParam.put("upd_man", hmParam.get("rgsr_id"));
                    DlwNewTypeMainPopService.updateExtSpecial(hmParam);
                }
                else if("delete".equals(crudTyp)){
                    hmParam.put("upd_man", hmParam.get("rgsr_id"));
                    DlwNewTypeMainPopService.deleteExtSpecial(hmParam);
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
    
    /** ================================================================
     * 날짜 : 20190924
     * 이름 : 송준빈
     * 추가내용 : 고객 자유결제 NICE 전문 발송
     * 대상 테이블 : 
     * ================================================================
     * */
    @RequestMapping(value = "/div6/sendFreeRealTime")
    public ModelAndView sendFreeRealTime(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam1 = new HashMap<String, Object>();
        Map<String, Object> hmParam2 = new HashMap<String, Object>();
        Map<String, Object> hmParam3 = new HashMap<String, Object>();
        Map<String, Object> hmParam4 = new HashMap<String, Object>();
        
        InetAddress address = InetAddress.getLocalHost();
        HttpServletRequestMock request = new HttpServletRequestMock();
		HttpServletResponseMock response = new HttpServletResponseMock();
		NicePayHttpServletRequestWrapper httpRequestWrapper = new NicePayHttpServletRequestWrapper(request);
		NicePayWEB nicePayWEB = new NicePayWEB();
		WebMessageDTO result = new WebMessageDTO();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";
        String sReturnVal = "0";
        String sReturnMassage = "";

        try 
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap sendInDs1 = (DataSetMap)mapInDs.get("ds_inputBasicInfo");        // 회원기본납입정보
            DataSetMap sendInDs2 = (DataSetMap)mapInDs.get("ds_inputFreeRealTimeInfo"); // 회원결제납입정보
            
            if (sendInDs1 != null && sendInDs1.size() > 0 && sendInDs2 != null && sendInDs2.size() > 0) 
            {
                hmParam1 = sendInDs1.get(0);
                hmParam2 = sendInDs2.get(0);
                ParamUtils.addSaveParam(hmParam1);
                
                List<Map<String, Object>> mList = DlwNewTypeMainPopService.getNiceProtocolMemberInfo(hmParam1);
                hmParam3 = mList.get(0); // 회원결제정보 추가데이터 (상품코드, 상품명, 고객Email, 고객휴대전화)
                
                System.out.println("기본정보 파라미터 ::: " + hmParam1.toString());
                System.out.println("납입정보 파라미터 ::: " + hmParam2.toString());
                System.out.println("추가정보 파라미터 ::: " + hmParam3.toString());
                
                /* 자유결제를 하고자 하는 고객의 결제기본정보값을 세팅하는 부분이다. */
                //httpRequestWrapper.addParameter("MID","nictest04m");					          // 상점아이디
    			//httpRequestWrapper.addParameter("EncodeKey", "b+zhZ4yOZ7FsH8pm5lhDfHZEb79tIwnjsdA0FBXh86yLc6BJeFVrZFXhAoJ3gEWgrWwN+lJMV0W4hvDdbe4Sjw=="); // 가맹점라이센스키
                
                httpRequestWrapper.addParameter("MID","dmlifeon1m");					          // 상점아이디
    			httpRequestWrapper.addParameter("EncodeKey", "QeD6CTbpmDK2jYgi7P7yoBQnqPikowKdJuJjylYj87QicXbzPG8wOfwJPBKR/ZhMknNyc0RFy2ThW00QkeyhAg=="); // 가맹점라이센스키
    			httpRequestWrapper.addParameter("Paymethod", "CARD");                             // 지불수단 ("CARD" 고정)
    			httpRequestWrapper.addParameter("Moid", (String)hmParam1.get("accnt_no"));        // 상품주문번호
    			httpRequestWrapper.addParameter("GoodsName", (String)hmParam3.get("prod_nm"));    // 상품명
    			httpRequestWrapper.addParameter("GoodsCnt", "1");                                 // 상품갯수 ("1" 고정)
    			httpRequestWrapper.addParameter("Amt", (String)hmParam2.get("free_pay_amt"));     // 상품가격
    			httpRequestWrapper.addParameter("BuyerName", (String)hmParam1.get("mem_nm"));     // 구매자명
    			httpRequestWrapper.addParameter("BuyerEmail", (String)hmParam3.get("email"));     // 구매자이메일 (없는고객은요?) FIXME!!!
    			httpRequestWrapper.addParameter("BuyerTel", (String)hmParam3.get("cell"));        // 구매자연락처
    			httpRequestWrapper.addParameter("CardNo", (String)hmParam2.get("card_no"));       // 카드번호
    			httpRequestWrapper.addParameter("CardExpire", (String)hmParam2.get("card_year") + "" + (String)hmParam2.get("card_mon"));                 // 카드유효기간
    			httpRequestWrapper.addParameter("BuyerAuthNum",  (String)hmParam2.get("idn_no")); // 카드소유주생년월일 (YYMMDD)
    			//httpRequestWrapper.addParameter("CardPwd",  (String)hmParam2.get("card_pwd"));  // 카드비밀번호 (앞에두자리까지만)
    			httpRequestWrapper.addParameter("CardPwd",  "1111");                              // 카드비밀번호는 1111 으로 고정하도록 합의되었음.(파트장님)
    			httpRequestWrapper.addParameter("CardQuota", (String)hmParam2.get("dc_no"));      // 신용카드할부기간 (00 : 일시불, 02~12 : 2~12 개월)
    			httpRequestWrapper.addParameter("CardInterest", "0");                             // 무이자할부여부 (1:YES, 0:NO)
    			httpRequestWrapper.addParameter("MallIP", address.getHostAddress());              // 상점아이피
    			httpRequestWrapper.addParameter("TransType", "0");				                  // 결제타입 ("0" 고정)
    			httpRequestWrapper.addParameter("actionType", "PY0");			                  // actionType (CL0 : 취소, PY0 : 승인)
    			httpRequestWrapper.addParameter("AuthFlg", "2");                                  // 인증플래그 (2 : 승인, 3 : 인증)
    			
    			/* NICE 측에 전송할 전문의 서비스 기본정보를 세팅하는 부분이다. */
    			String sPayLogFilePath = basVlService.getBasVlAsString("pay_file_path", (String) hmParam1.get("cntr_cd"));  
    			  			
                nicePayWEB.setParam("NICEPAY_LOG_HOME", sPayLogFilePath + "/web_site/nicelog"); // 로그 디렉토리
    			nicePayWEB.setParam("APP_LOG", "1");        // 어플리케이션로그설정 (0 : DISABLE, 1 : ENABLE)
    			nicePayWEB.setParam("EVENT_LOG", "1");		// 이벤트로그설정 (0 : DISABLE, 1 : ENABLE)
    			nicePayWEB.setParam("EncFlag", "S");		// 로그모드설정 (N : 평문 , S:암호화)
    			nicePayWEB.setParam("SERVICE_MODE", "PY0");	// 서비스구분코드 설정 (승인 : PY0 , 취소 : CL0)
    			nicePayWEB.setParam("Currency", "KRW");		// 통화설정
    			nicePayWEB.setParam("PayMethod", "CARD");	// 결제수단설정 (신용카드 : CARD, 계좌이체 : BANK, 가상계좌 : VBANK)
    			
    			/* 전문의 결과값을 받는 부분이다. */
    			result = nicePayWEB.doService(httpRequestWrapper, response);//결제를 요청
    			
    			String sResultCode   = result.getParameter("ResultCode");	// 결과코드 (정상 : 3001, 그외에러)
    			String sResultMsg    = result.getParameter("ResultMsg");	// 결과메시지
    			String sTid          = result.getParameter("TID");			// 거래아이디TID
    			String sAuthDate     = result.getParameter("AuthDate");		// 승인날짜
    			String sAuthCode     = result.getParameter("AuthCode");		// 승인번호
    			String sCardCode     = result.getParameter("CardCode");		// 카드코드
    			String sCardName     = result.getParameter("CardName");		// 카드사명
    			String sAcquCardCode = result.getParameter("AcquCardCode");	// 매입사카드코드
    			String sAcquCardName = result.getParameter("AcquCardName");	// 매입사카드사명
    			String sCardQuota    = result.getParameter("CardQuota");	// 카드 할부 개월
    			
    			System.out.println("결과코드 : "     + sResultCode);
    			System.out.println("결과메시지 : "   + sResultMsg);
    			System.out.println("tid : "          + sTid);
    			System.out.println("인증일자 : "     + sAuthDate);
    			System.out.println("인증코드 : "     + sAuthCode);
    			System.out.println("카드코드 : "     + sCardCode);
    			System.out.println("카드명 : "       + sCardName);
    			System.out.println("애큐카드코드 : " + sAcquCardCode);
    			System.out.println("애큐카드명 : "   + sAcquCardName);
    			System.out.println("할부개월수 : "   + sCardQuota);
    			
    			
    			hmParam4.put("result_cd", sResultCode);
    			hmParam4.put("result_msg", sResultMsg);
    			hmParam4.put("auth_date", sAuthDate);
    			hmParam4.put("auth_code", sAuthCode);
    			hmParam4.put("tid", sTid);
    			
    			hmParam4.putAll(hmParam1);
    			hmParam4.putAll(hmParam2);
    			hmParam4.putAll(hmParam3);
    			
    			if("3001".equals(sResultCode))
                {
    				int iCardQuota = Integer.parseInt(sCardQuota);
    				
        			if(iCardQuota >= 2)
        			{
        				hmParam4.put("etc", "할부개월수 : " + sCardQuota + "개월 결제 (전환결제)");
        			}
        			else
        			{
        				hmParam4.put("etc", "일시불결제 (전환결제)");
        			}

    				hmParam4.put("del_fg", "C");
    				sReturnVal = "1";
    				sReturnMassage = sResultMsg;

    				DlwNewTypeMainPopService.insertFreeRealTimeCardResult(hmParam4);
                }
                else
                {
                	/* 2026 :: 유효하지 않은 회원, 5004 :: 유효기간오류, 3024 :: 할부 최소금액 오류(50000미만), 1604 :: 필수입력값 미입력 */
                	if("2026".equals(sResultCode))
                	{
                		sResultMsg = "유효하지 않은 카드번호 입니다.";
                	}
                	else if("5004".equals(sResultCode))
                	{
                		sResultMsg = "카드 유효기간이 틀립니다.";
                	}
                	else if("1604".equals(sResultCode))
                	{
                		sResultMsg = "가격을 입력해주시기 바랍니다.";
                	}
                	
                	hmParam4.put("del_fg", "F");
                	sReturnVal = "-1";
                	sReturnMassage = sResultMsg;
                }
            }
            
            mapOutDs.put("ds_output", listMap);
            mapOutVar.put("returnVal", sReturnVal);
            mapOutVar.put("returnMassage", sReturnMassage);
            
            /*
            List<Map<String, Object>> mList = DlwNewTypeMainPopService.getDlwCdList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);
            mapOutVar.put("returnVal", 1);
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
    
    /** ================================================================
     * 날짜 : 20190924
     * 이름 : 송준빈
     * 추가내용 : 고객 자유결제 NICE 전문 발송
     * 대상 테이블 : 
     * ================================================================
     * */
    @RequestMapping(value = "/sendCancelNicePayment")
    public ModelAndView sendCancelNicePayment(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam1 = new HashMap<String, Object>();
        Map<String, Object> hmParam2 = new HashMap<String, Object>();
        Map<String, Object> hmParam3 = new HashMap<String, Object>();
        Map<String, Object> hmParam4 = new HashMap<String, Object>();
        
        InetAddress address = InetAddress.getLocalHost();
        HttpServletRequestMock request = new HttpServletRequestMock();
		HttpServletResponseMock response = new HttpServletResponseMock();
		NicePayHttpServletRequestWrapper httpRequestWrapper = new NicePayHttpServletRequestWrapper(request);
		NicePayWEB nicePayWEB = new NicePayWEB();
		WebMessageDTO result = new WebMessageDTO();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";
        String sReturnVal = "0";
        String sReturnMassage = "";

        try 
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap sendInDs1 = (DataSetMap)mapInDs.get("ds_input");        // 회원기본취소정보
            
            System.out.println(sendInDs1);
            
            if (sendInDs1 != null && sendInDs1.size() > 0) 
            {
                hmParam1 = sendInDs1.get(0);
                ParamUtils.addSaveParam(hmParam1);
                
                httpRequestWrapper.addParameter("CancelAmt", (String)hmParam1.get("pay_amt"));	// 취소 금액 설정
    			httpRequestWrapper.addParameter("MID", "dmlifeon1m");						// 상점 아이디 설정
    			httpRequestWrapper.addParameter("CancelIP", address.getHostAddress());		// 취소자 IP 설정
    			httpRequestWrapper.addParameter("CancelMsg","전환결제 취소");				// 취소 사유 설정
    			httpRequestWrapper.addParameter("CancelPwd", "1111");						// 취소 패스워드 설정
    			httpRequestWrapper.addParameter("TID", (String)hmParam1.get("result_key"));	// 취소 TID			  
    			httpRequestWrapper.addParameter("PartialCancelCode", "0");    				// 부분 취소 유무 (1: 부분 취소 사용, 0 : 미사용)
    			
    			String sPayLogFilePath = basVlService.getBasVlAsString("pay_file_path", (String) hmParam1.get("cntr_cd"));

    			nicePayWEB.setParam("NICEPAY_LOG_HOME", sPayLogFilePath + "/web_site/nicelog");	//로그 디렉토리
    			nicePayWEB.setParam("APP_LOG", "0");										//이벤트로그 모드 설정(0: DISABLE, 1: ENABLE)
    			nicePayWEB.setParam("EVENT_LOG", "1");										//어플리케이션로그 모드 설정(0: DISABLE, 1: ENABLE)
    			nicePayWEB.setParam("EncFlag", "S");										//암호화플래그 설정(N: 평문, S:암호화)
    			nicePayWEB.setParam("SERVICE_MODE", "CL0");									//서비스모드 설정(결제 서비스 : PY0 , 취소 서비스 : CL0)

    			result = nicePayWEB.doService(httpRequestWrapper, response);	//결제취소 요청
    						
    			String sResultCode = result.getParameter("ResultCode");						// 결과코드 (정상 :2001 , 그 외 에러)
    			String sResultMsg  = result.getParameter("ResultMsg");						// 결과메시지	
    			String sCancelDate = result.getParameter("CancelDate");						// 취소 날짜
    			String sCancelTime = result.getParameter("CancelTime");						// 취소 시간
    			String sCancelAmt  = result.getParameter("CancelAmt");						// 취소 가격
    			
    			System.out.println("--------------------------------------------");	
    			System.out.println("결과			: " + sResultCode);		
    			System.out.println("결과 메시지		: " + sResultMsg);	
    			System.out.println("취소 날짜		: " + sCancelDate);
    			System.out.println("취소 가격		: " + sCancelAmt);	
    			System.out.println("--------------------------------------------");
    			
    			hmParam2.put("pay_cancel_day", sCancelDate);
				hmParam2.put("cncl_tm", sCancelTime);
				hmParam2.put("pay_cancel_note", "전환결재 취소");
				hmParam2.put("emple_no", (String)hmParam1.get("rgsr_id"));
				hmParam2.put("pay_cancel_cd", "01");
				
				hmParam2.putAll(hmParam1);
				
    			if("2001".equals(sResultCode))
                {    
    				sReturnVal = "1";
    				sReturnMassage = sResultMsg;

    				DlwNewTypeMainPopService.sendCancelNicePayment(hmParam2);
                }
                else
                {
                	sReturnVal = "-1";
                	sReturnMassage = sResultMsg;
                }
            }
            
            mapOutDs.put("ds_output", listMap);
            mapOutVar.put("returnVal", sReturnVal);
            mapOutVar.put("returnMassage", sReturnMassage);
            
            /*
            List<Map<String, Object>> mList = DlwNewTypeMainPopService.getDlwCdList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);
            mapOutVar.put("returnVal", 1);
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
    
    
    @RequestMapping(value = "/div6/getSendCertfAccntAddrList")
    public ModelAndView getSendCertfAccntAddrList(XPlatformMapDTO xpDto, Model model) throws Exception {
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
                
                int nTotal = DlwNewTypeMainPopService.getSendCertfAccntAddrCount(hmParam);
                mapOutVar.put("nTotalListCnt", nTotal);

                List<Map<String, Object>> mList = DlwNewTypeMainPopService.getSendCertfAccntAddrList(hmParam);
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
    
    @RequestMapping(value = "/div6/getMemberAccntDtlInfo")
    public ModelAndView getMemberAccntDtlInfo(XPlatformMapDTO xpDto, Model model) throws Exception {
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
                
                List<Map<String, Object>> mList = DlwNewTypeMainPopService.getMemberAccntDtlInfo(hmParam);
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
    
    @RequestMapping(value = "/div6/saveSendCertfAccntAddrList")
    public ModelAndView saveSendCertfAccntAddrList(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            
            if (listInDs.size() > 0) 
            {
                Map hmParam = listInDs.get(0);
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("reg_man", (String)hmParam.get("rgsr_id"));
                
                if (sSaveType.equals("update"))
                {
                	DlwNewTypeMainPopService.updateSendCertfAccntAddrList(hmParam);
                }
                else
                {
                	DlwNewTypeMainPopService.insertSendCertfAccntAddrList(hmParam);	
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

    @RequestMapping(value = "/div6/deleteSendCertfAccntAddrList")
    public ModelAndView deleteSendCertfAccntAddrList(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            	for(int idx = 0; idx < srchInDs.size(); idx++)
            	{
            		hmParam = srchInDs.get(idx);
            		DlwNewTypeMainPopService.deleteSendCertfAccntAddrList(hmParam);
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
    
    /**
     * 장기할부용 고객-구좌 정보를 검색한다.(복사_20220822)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/div4/cust-acnt/newType_longterm_list")
    public ModelAndView getDlwNewTypeCustLongTermList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();  

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();
            
            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
                
                // CMS 조회인지 카드 조회인지 구분
                String svcid = (String) mapInVar.get("pay_mthd");                
                hmParam.put("pay_mthd", svcid);
                                
                List<Map<String, Object>> mList = DlwNewTypeMainPopService.getDlwNewTypeCustLongTermList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
            
            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);
        }

        return modelAndView;
    }
    
    /**
     * 상담 정보를 등록/수정한다. (대명 - CMS/카드 부가서비스 등록시)(복사_20220822)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/div4/update_newType_card")
    public ModelAndView updateNewTypeCardInfo(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input"); 				// 변경후 dataset
            DataSetMap listInOldDs = (DataSetMap)mapInDs.get("ds_input_old"); 				// 변경후 dataset

            if (listInDs.size() > 0) {
                Map <String, Object> hmParam = listInDs.get(0);
                Map <String, Object> hmParam_old = listInOldDs.get(0);
                                                
                // 카드 변경 정보 업데이트
                DlwNewTypeMainPopService.updateNewTypeCardInfo(hmParam);
                
                /**************************************************상담등록 필드 구성*********************************************************/ 
                String chgText = "[CARD] 이체일자가 변경되었습니다. [" + hmParam_old.get("ichae_dt").toString() + " >> " + hmParam.get("ichae_dt").toString() +  "]" ;

                // 상담유형 1, 2, 3
                hmParam.put("cons_typ1_cd", "CT01010000");		//회원정보
                hmParam.put("cons_typ2_cd", "CT01010100");		//결제정보
                hmParam.put("cons_typ3_cd", "CT01010102");		//결제수단변경
                hmParam.put("cons_memo_tit", chgText);
                /*******************************************************************************************************************************/
                
                // 상담 등록
                saveConsdlw(hmParam); 
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
            
            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);
        }
        return modelAndView;
    }
    
    /**
     * 대명스테이션 카드 부가서비스(신규,해지)를 등록한다. (이니시스 전용)(복사_20220822)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/div4/card-inicis-newType/save")
    public ModelAndView insertDlwNewTypeInicisCardAnxtSrvc(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();
            
            // 카드 신청 시 카드 혜택이 끝난 체크 대상자는 강제로 납입회차를 100으로 늘린다. (납입회차 36기준으로 dmlife001 가맹번호 빌키 생성)
            String sBenefitYn = (String)mapInVar.get("benefit");
                                    
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            hmParam = listInDs.get(0);

            //세션
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("emple_no", hmParam.get("rgsr_id"));
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            hmParam.put("upd_man", hmParam.get("rgsr_id"));
            
            // 카드 회원 여부
            String cardMemYN = "";	// CARD 회원 여부
            String cardMsg = "";		// 결과 전달 메세지
            
            cardMemYN  = hmParam.get("card_yn").toString();
            String syyyy = hmParam.get("yyyy").toString();
            String smm = hmParam.get("mm").toString();
            String expr_Dt = syyyy + smm;
           
            // 카드 신청 시 카드 혜택이 끝난 체크 대상자는 강제로 납입회차를 100으로 늘린다. (납입회차 36기준으로 dmlife001 가맹번호 빌키 생성)
            hmParam.put("pay_no", sBenefitYn);	
                        
            //이니시스 여부 등록       
            hmParam.put("ini_yn", "Y");
            
        	hmParam.put("inv_tg_dt", hmParam.get("ichae_dt"));					// 이체일자
            hmParam.put("idn_no", hmParam.get("birth"));							// 생년월일
            hmParam.put("exp_year", syyyy);											// 만기 연도            
            hmParam.put("exp_mon", smm);											// 만기 월		
            hmParam.put("exp_dt", expr_Dt);											// 이체일자
            
            
            System.out.println(hmParam);
                                                
            if (cardMemYN.equals("N")){
            	//CARD 회원이 등록되어 있음 (DEL_FG = 'N')
                hmParam.put("app_cl", "3");											// 3: 해지, 1: 신규, 7 : 보류해지                            
                cardMsg = DlwNewTypeMainPopService.deleteDlwNewTypeCardAnxtSrvc(hmParam);    
            } else {    
            	 // CARD 회원이 등록된 적이 있거나(DEL_FG = 'Y') 등록되지 않음 (NONE)
                hmParam.put("app_cl", "1");											// 3: 해지, 1: 신규, 7 : 보류해지               
            	cardMsg = DlwNewTypeMainPopService.insertDlwNewTypeInicisCardAnxtSrvc(hmParam);         	
            }
            mapOutVar.put("gv_rslMsg", cardMsg);
                       
            //2018.01.05 접속 로그////////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            //////////////////////////////////////////////////////////////////////////////////////////////////////

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
            
            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);
        }        
        return modelAndView;
    }
    
    /**
     * 이니시스 장기할부 카드 체크 + 등록(복사_20220822)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/div4/longterm-inicis-newType/save")
    public ModelAndView insertDlwNewTypeInicisLongtermCardCheck(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();
            
            Map <String, Object> mapCardCheck     = null; // 장기할부 카드 체크 (정상, 하나카드여부등)
            
            // 카드 신청 시 카드 혜택이 끝난 체크 대상자는 강제로 납입회차를 100으로 늘린다. (납입회차 36기준으로 dmlife001 가맹번호 빌키 생성)
            String sBenefitYn = (String)mapInVar.get("benefit");
                                    
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            hmParam = listInDs.get(0);

            //세션
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("emple_no", hmParam.get("rgsr_id"));
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            hmParam.put("upd_man", hmParam.get("rgsr_id"));
            
            // 카드 회원 여부
            String cardMemYN = "";	// CARD 회원 여부
            String cardMsg = "";		// 결과 전달 메세지
            
            cardMemYN  = hmParam.get("card_yn").toString();
            String syyyy = hmParam.get("yyyy").toString();
            String smm = hmParam.get("mm").toString();
            String expr_Dt = syyyy + smm;
           
            // 카드 신청 시 카드 혜택이 끝난 체크 대상자는 강제로 납입회차를 100으로 늘린다. (납입회차 36기준으로 dmlife001 가맹번호 빌키 생성)
            hmParam.put("pay_no", sBenefitYn);	
                        
            //이니시스 여부 등록       
            hmParam.put("ini_yn", "Y");
            
        	hmParam.put("inv_tg_dt", hmParam.get("ichae_dt"));					// 이체일자
            hmParam.put("idn_no", hmParam.get("birth"));							// 생년월일
            hmParam.put("exp_year", syyyy);											// 만기 연도            
            hmParam.put("exp_mon", smm);											// 만기 월		
            hmParam.put("exp_dt", expr_Dt);											// 이체일자
            
            System.out.println(hmParam);
            
            mapCardCheck = DlwNewTypeMainPopService.getDlwNewTypeInicisLongtermCardCheck(hmParam);	//KB장기할부 카드 체크 진행
            
            //결과 메세지 리턴하기
            mapOutVar.put("gv_rslMsg", mapCardCheck.get("rtn_msg"));
                                   
            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
            
            modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
            modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);
        }        
        return modelAndView;
    }
}
