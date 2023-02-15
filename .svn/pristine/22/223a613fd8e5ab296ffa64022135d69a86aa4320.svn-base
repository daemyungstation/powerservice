/*
 * (@)# CustBasiController.java
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * Copyright (c) 2015 by Inwoo tech inc, KOREA. All Rights Reserved.
 *
 * http://www.inwoo.co.kr
 *
 * NOTICE! This software is the confidential and proprietary
 * information of
 * Inwoo Tech Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms
 * of the license agreement you
 * entered into with Inwoo Tech Inc.
 *
 */

package powerservice.business.cns.web;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import kcb.jni.Okname;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.bean.UserSession;
import powerservice.business.cns.service.ConsService;
import powerservice.business.cns.service.CustBasiService;
//2017.11.30 로그 추가
import powerservice.business.common.service.CommonService;
import powerservice.business.dlw.service.DataAthrQuryService;
import powerservice.business.dlw.service.DlwConsProdService;
import powerservice.business.dlw.service.DlwCustService;
import powerservice.business.sys.service.BasVlService;
import powerservice.common.util.CommonUtils;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;

import com.tobesoft.xplatform.data.DataSet;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;
/**
 * 고객정보를 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID CustBasi
 */
@Controller
@RequestMapping(value = "/cons/cust-basi")
public class CustBasiController {

    @Resource
    private CustBasiService custBasiService;

    @Resource
    private DataAthrQuryService dataAthrQuryService;

    @Resource
    private DlwCustService dlwCustService;

    @Resource
    private DlwConsProdService dlwConsProdService;

    @Resource
    private BasVlService basVlService;

    @Resource
    private ConsService consService;

    @Resource
    private CommonService commonService;

    /**
     * 고객 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public ModelAndView getCustBasiList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                // 엔컴 부서코드 조건여부
                String sChkDeptYn = StringUtils.defaultString((String) hmParam.get("chk_dept_yn"));

                // 페이징 정보
                DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                if (listInDs != null && listInDs.size() > 0) {
                    Map pMap = listInDs.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));
                }

                ParamUtils.addCenterParam(hmParam);

                if ("Y".equals(sChkDeptYn)) {
                    UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
                    hmParam.put("paramEmpleNo", oLoginUser.getUserid());
                    hmParam.put("paramAs", "EMP");
                    CommonUtils.printLog(hmParam);
                    String dataAthrQury = StringUtils.defaultString((String) dataAthrQuryService.getDataAthrQury(hmParam));

                    if (!"".equals(dataAthrQury.trim()) ) {

                        dataAthrQury = dataAthrQury.replace("AND", "AND (");


                        dataAthrQury += "OR EMP.DEPT_CD IS NULL)";

                        hmParam.put("dataAthrQury", dataAthrQury);
                    }
                }

                int nTotal = custBasiService.getCustBasiCount(hmParam);

                System.out.println("sssssssssssssssssssssssssssssssssssssssssssssssss" + nTotal);

                mapOutVar.put("tc_mem", nTotal);

                List<Map<String, Object>> mList = custBasiService.getCustBasiList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            }

            //2017.11.30 접속 로그////////////////////////////////////////////////////////////////////////////////

            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            /////////////////////////////////////////////////////////////////////////////////////////////////


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
     * 고객 정보를 검색한다. (관리업무 - 고객정보관리)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list2")
    public ModelAndView getCustBasiList2(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                // 엔컴 부서코드 조건여부
                String sChkDeptYn = StringUtils.defaultString((String) hmParam.get("chk_dept_yn"));

                // 페이징 정보
                DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                if (listInDs != null && listInDs.size() > 0) {
                    Map pMap = listInDs.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));
                }

                ParamUtils.addCenterParam(hmParam);

                if ("Y".equals(sChkDeptYn)) {
                    UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
                    hmParam.put("paramEmpleNo", oLoginUser.getUserid());
                    hmParam.put("paramAs", "EMP");
                    CommonUtils.printLog(hmParam);
                    String dataAthrQury = StringUtils.defaultString((String) dataAthrQuryService.getDataAthrQury(hmParam));

                    if (!"".equals(dataAthrQury.trim()) ) {

                        dataAthrQury = dataAthrQury.replace("AND", "AND (");


                        dataAthrQury += "OR EMP.DEPT_CD IS NULL)";

                        hmParam.put("dataAthrQury", dataAthrQury);
                    }
                }

                int nTotal = custBasiService.getCustBasiCount(hmParam);

                System.out.println("sssssssssssssssssssssssssssssssssssssssssssssssss" + nTotal);

                mapOutVar.put("tc_mem", nTotal);

                List<Map<String, Object>> mList = custBasiService.getCustBasiList2(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            }

            //2017.11.30 접속 로그////////////////////////////////////////////////////////////////////////////////

            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            /////////////////////////////////////////////////////////////////////////////////////////////////


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
     * 업체용 고객 정보를 검색한다.
     * 임동진
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/usecomplist")
    public ModelAndView getCustBasiUsecompList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                // 엔컴 부서코드 조건여부
                String sChkDeptYn = StringUtils.defaultString((String) hmParam.get("chk_dept_yn"));

                // 페이징 정보
                DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                if (listInDs != null && listInDs.size() > 0) {
                    Map pMap = listInDs.get(0);

                    String excel_fg = (String)mapInVar.get("excel_fg");
                    if (!"Y".equals(excel_fg)) {
                        hmParam.put("startLine", pMap.get("startNum"));
                        hmParam.put("endLine", pMap.get("endNum"));
                    }
                }

                ParamUtils.addCenterParam(hmParam);
                
                String strGroupCd = "";
                if ("Y".equals(sChkDeptYn)) {
                    UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
                    hmParam.put("paramEmpleNo", oLoginUser.getUserid());
                    
                    strGroupCd =  oLoginUser.getTeamcd();		//상담그룹코드 에이앤젯(T20600)
                    
                    hmParam.put("paramAs", "EMP");
                    CommonUtils.printLog(hmParam);
                    String dataAthrQury = StringUtils.defaultString((String) dataAthrQuryService.getDataAthrQury(hmParam));
                    
                    // (주)에인앤젯 업체 종합현황 조회 시 
                    // 스페셜라이프 상품만 조회 가능 (타 업체의 경우 스페셜조회 불가)_20210430
                    if (!strGroupCd.equals("T20600")){
	                    if (!"".equals(dataAthrQury.trim()) ) {
	                        dataAthrQury = dataAthrQury.replace("AND", "AND (");  
	                        dataAthrQury += "OR EMP.DEPT_CD IS NULL) AND SECTION_THR NOT IN ('0029', '0001', '0027') ";	                        
	                    }
                    } else {                    	
                    	dataAthrQury = " AND SECTION_THR IN ('0029', '0001', '0027')  ";
                    }

                    hmParam.put("dataAthrQury", dataAthrQury);
                }

               //CommonUtils.printLog(hmParam);   
                int nTotal = custBasiService.getCompUseListCount(hmParam);

                mapOutVar.put("tc_mem", nTotal);
                mapOutVar.put("tc_dataAthrQury",hmParam.get("dataAthrQury"));

                List<Map<String, Object>> mList = custBasiService.getCompUseList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);   
            } 

            //2018.05.15 접속 로그///////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");
            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            /////////////////////////////////////////////////////////////////////////////////////////////////

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
     * 직권해지용 고객정보
     * 임동진
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/resnauth")
    public ModelAndView getCustBasiResnAuthList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                // 엔컴 부서코드 조건여부
                String sChkDeptYn = StringUtils.defaultString((String) hmParam.get("chk_dept_yn"));

                // 페이징 정보
                DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                if (listInDs != null && listInDs.size() > 0) {
                    Map pMap = listInDs.get(0);

                    String excel_fg = (String)mapInVar.get("excel_fg");
                    if (!"Y".equals(excel_fg)) {
                        hmParam.put("startLine", pMap.get("startNum"));
                        hmParam.put("endLine", pMap.get("endNum"));
                    }
                }
                ParamUtils.addCenterParam(hmParam);

                int nTotal = custBasiService.getResnAuthListCount(hmParam);

                mapOutVar.put("tc_mem", nTotal);

                List<Map<String, Object>> mList = custBasiService.getResnAuthList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            }

            //2018.05.15 접속 로그///////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");
            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            /////////////////////////////////////////////////////////////////////////////////////////////////

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
     * 채권 추심용 고객정보
     * 임동진
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/resncredit")
    public ModelAndView getCustBasiResnCreditMainList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                // 엔컴 부서코드 조건여부
                String sChkDeptYn = StringUtils.defaultString((String) hmParam.get("chk_dept_yn"));

                // 페이징 정보
                DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                if (listInDs != null && listInDs.size() > 0) {
                    Map pMap = listInDs.get(0);

                    String excel_fg = (String)mapInVar.get("excel_fg");
                    if (!"Y".equals(excel_fg)) {
                        hmParam.put("startLine", pMap.get("startNum"));
                        hmParam.put("endLine", pMap.get("endNum"));
                    }
                }
                ParamUtils.addCenterParam(hmParam);

                int nTotal = custBasiService.getResnCreditMainListCount(hmParam);

                mapOutVar.put("tc_mem", nTotal);

                List<Map<String, Object>> mList = custBasiService.getResnCreditMainList(hmParam);
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
     * 채권 추심용 입금 현황
     * 임동진
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/resncreditpay")
    public ModelAndView getCustBasiResnCreditpayMainList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                // 엔컴 부서코드 조건여부
                String sChkDeptYn = StringUtils.defaultString((String) hmParam.get("chk_dept_yn"));

                // 페이징 정보
                DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                if (listInDs != null && listInDs.size() > 0) {
                    Map pMap = listInDs.get(0);

                    String excel_fg = (String)mapInVar.get("excel_fg");
                    if (!"Y".equals(excel_fg)) {
                        hmParam.put("startLine", pMap.get("startNum"));
                        hmParam.put("endLine", pMap.get("endNum"));
                    }
                }
                ParamUtils.addCenterParam(hmParam);

                int nTotal = custBasiService.getResnCreditpayMainListCount(hmParam);

                mapOutVar.put("tc_mem", nTotal);

                List<Map<String, Object>> mList = custBasiService.getResnCreditpayMainList(hmParam);
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
     * 대명라이프웨이 직권해지 대상자 산출
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/target-list")
    public ModelAndView getCustBasiResnTargetList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            //int nTotal = custBasiService.getResnTargetListCount(hmParam);

            List<Map<String, Object>> mList = custBasiService.getResnTargetList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            //List<Map<String, Object>> mComnList = DlwCmsService.getDlwCmsComnInfo(hmParam);
            //String mnthLimitAmt = "";
            //CMS 당월 출금의뢰 총액 조회

            //String mnthAmt = DlwCmsService.getDlwWdrwReqDtTtamt(hmParam);


            //double dmnthAmt = Double.valueOf(mnthAmt).doubleValue();

            //mapOutVar.put("tc_wdrwTran", nTotal);
            //mapOutVar.put("mnthLimitAmt", mnthLimitAmt);
            //mapOutVar.put("mnthAmt", dmnthAmt);

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
     * 채권추심 선태건 삭제
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/delresncredit")
    public ModelAndView delResnSenddt(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map<String, Object> hmParam = new HashMap<String, Object>();

        System.out.println("1111111111111111111111111111111111111");

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

            for (int i = 0; i < listInDs.size(); i++) {
                hmParam = listInDs.get(i);
                custBasiService.delResnCredit(hmParam);
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
     * 대명라이프웨이 채권추심 대상자 산출
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/credit-list")
    public ModelAndView getCustBasiResnCreditList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            //String dataAthrQury = dataAthrQuryService.getDataAthrQury(hmParam);
            //hmParam.put("dataAthrQury", dataAthrQury);

            //int nTotal = custBasiService.getResnTargetListCount(hmParam);

            List<Map<String, Object>> mList = custBasiService.getResnCreditList(hmParam);
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
     * 고객조회로그 정보를 등록한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/target-save")
    public ModelAndView insertCustBasiResnTargetList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap dtptMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if (listInDs.size() > 0) {
                for (int i = 0; i < listInDs.size(); i++) {
                    hmParam = listInDs.get(i);
                    ParamUtils.addSaveParam(hmParam);

                    int rowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

                    if (rowType == DataSet.ROW_TYPE_UPDATED) {
                        custBasiService.insertTargetList(hmParam);
                    }
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

    /**
     * 고객조회로그 정보를 등록한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/credit-save")
    public ModelAndView insertCustBasiResnCreditList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap dtptMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if (listInDs.size() > 0) {
                for (int i = 0; i < listInDs.size(); i++) {
                    hmParam = listInDs.get(i);
                    ParamUtils.addSaveParam(hmParam);

                    int rowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

                    if (rowType == DataSet.ROW_TYPE_UPDATED) {
                        custBasiService.insertCreditList(hmParam);
                    }
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

    /**
     * 직권해지 설정값을 업데이트 한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/senddt")
    public ModelAndView updateResnSenddt(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

            //System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX>  " + listInDs.size());

            for (int i = 0; i < listInDs.size(); i++) {
                hmParam = listInDs.get(i);

                //세션
                ParamUtils.addSaveParam(hmParam);

                hmParam.put("upd_man", hmParam.get("rgsr_id"));

                //System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX>  " + mapInVar.get("p_select"));
                hmParam.put("select_type", mapInVar.get("p_select"));

                int rowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

                if (rowType == DataSet.ROW_TYPE_UPDATED) {
                    custBasiService.updateResnSenddt(hmParam);
                }

                //System.out.println("::::::::::::::>>>> " + mapInVar.get("p_select"));
                //CommonUtils.printLog(hmParam);

                //직권성정 후 해피콜 자동 등록
                if("03".equals(mapInVar.get("p_select"))){
                    hmParam.put("hpcl_stat_cd", "20");
                    hmParam.put("hpcl_note", "직권해지자동적용");
                    custBasiService.updateHpclAckd(hmParam);

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


    /**
     * 고객 정보를 등록/수정한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    public ModelAndView saveCustBasi(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap dtptMap = new DataSetMap();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if (listInDs.size() > 0) {
                Map hmParam = listInDs.get(0);

                String sMemNo = StringUtils.defaultString((String) hmParam.get("mem_no"));
                String sAccntNo = StringUtils.defaultString((String) hmParam.get("accnt_no"));

                ParamUtils.addSaveParam(hmParam);

                if ("".equals(sMemNo)) {
                    sMemNo = custBasiService.insertCustBasi(hmParam);
                } else {
                    custBasiService.updateCustBasi(hmParam);

                    String[] arrAccntCol = {"mem_nm", "idn_no", "home_zip"
                            , "home_addr2" , "cell", "home_tel", "wrpl_tel", "brth_mon_day"
                            , "email", "note", "sex", "credit_rating", "crdt_mng_no" ,"dm_yn","email_yn","sms_yn","tel_yn","markt_agr_yn" };

                    String[] arrAccntColNm = {"성명", "주민번호", "자택우편번호"
                            , "자택주소", "휴대폰" , "자택전화", "회사전화", "생년월일"
                            , "이메일", "비고", "성별", "신용등급", "관리번호","채널동의 DM","채널동의 EMAIL" ,"채널동의 SMS" ,"채널동의 전화","마켓팅동의"};

                    DataSetMap chbfProdDs = (DataSetMap)mapInDs.get("ds_old_input");


                  //  System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
                 //   CommonUtils.printLog(chbfProdDs);


                   Map chbfProdParam = chbfProdDs.get(0);

                    String sConsMemoCntn = "";

                    for (int i = 0; i < arrAccntCol.length; i++) {
                        Map<String, Object> mChngAccntInfo = new HashMap<String, Object>();
                        ParamUtils.addSaveParam(mChngAccntInfo);

                        String sChbfProdInfo = StringUtils.defaultString((String) chbfProdParam.get(arrAccntCol[i]));
                        String sProdInfo = StringUtils.defaultString((String)hmParam.get(arrAccntCol[i]));

                        if (!sChbfProdInfo.equals(sProdInfo)) {
                            mChngAccntInfo.put("mem_no", sMemNo);
                            mChngAccntInfo.put("accnt_no", sAccntNo);
                            mChngAccntInfo.put("cl1", "U");
                            mChngAccntInfo.put("dat1", arrAccntColNm[i]);                  // 필드명




                            String sDat2 = StringUtils.defaultString((String)chbfProdParam.get(arrAccntCol[i]));
                            if ("".equals(sDat2)) {
                                sDat2 = "빈값";
                            }
                            String sDat3 = StringUtils.defaultString((String)hmParam.get(arrAccntCol[i]));
                            if ("".equals(sDat3)) {
                                sDat3 = "빈값";
                            }

                            mChngAccntInfo.put("dat2", sDat2); // 변경전값
                            mChngAccntInfo.put("dat3", sDat3);       // 변경후값

                            // 순서이동 - 2018.02.08
                            // 상담메모내역 , 변경 삭제 내역 등록
                            sConsMemoCntn = (new StringBuilder(String.valueOf(sConsMemoCntn))).append((String)mChngAccntInfo.get("dat1")).append(": ").append(sDat2).append(" -> ").append(sDat3).append("\n").toString();
                            dlwConsProdService.insertReqUpdDelInf(mChngAccntInfo);

                            /** 민경현매니져 요청
                             *  개명신청자들 데이터변경 !!
                             *  디비링크로  웹데이터 수정
                             *  회원명이 미상일 경우는 변경 안함
                             * */

                            if ("성명".equals(arrAccntColNm[i])) {

                                if (!"미상".equals(sDat3))  {

                                    custBasiService.updateCustBasi_web(mChngAccntInfo);
                                /*	System.out.println("회원코드:" + mChngAccntInfo.get("mem_no"));
                                    System.out.println("전 : "+ sDat2   );
                                    System.out.println("후 : "+ sDat3   );
                                    */
                                }


                            }



                            //sConsMemoCntn = (new StringBuilder(String.valueOf(sConsMemoCntn))).append((String)mChngAccntInfo.get("dat1")).append(": ").append(sDat2).append(" -> ").append(sDat3).append("\n").toString();
                            //dlwConsProdService.insertReqUpdDelInf(mChngAccntInfo);         // 변경 삭제 내역 등록
                        }
                    }


                    hmParam.put("cons_memo_tit", "회원정보수정 정보");
                    hmParam.put("cons_memo_cntn", sConsMemoCntn);
                    hmParam.put("consno_sqno", "1");

                    //*************************************************************
                    /*
                    수정자 : 정훈연구원
                    요청자 : 정성안팀장
                    수정이유 : 상담유형 기타.
                    수정일시 : 2016-07-28
                    문의사항있을시 요청자에게 문의하세요
                     */
                    String sAutoConsTyp3Cd = basVlService.getBasVlAsString("auto_cons_typ3_cd");
                    if ("".equals(sAutoConsTyp3Cd)) {
                        sAutoConsTyp3Cd = "CT01010201";
                    }
                    String sConsTyp1Cd = sAutoConsTyp3Cd.substring(0, 6) + "0000";
                    String sConsTyp2Cd = sAutoConsTyp3Cd.substring(0, 8) + "00";

                    hmParam.put("cons_typ1_cd", sConsTyp1Cd);
                    hmParam.put("cons_typ2_cd", sConsTyp2Cd);
                    hmParam.put("cons_typ3_cd", sAutoConsTyp3Cd);

                    if (!"".equals(sConsMemoCntn)) {
                        consService.insertCons(hmParam);
                    }

                    //*************************************************************

                }

                dtptMap.setRowMaps(dlwCustService.getDlwCustPrdtDtpt(sMemNo));
                mapOutDs.put("ds_output", dtptMap);


                //2017.11.30 접속 로그////////////////////////////////////////////////////////////////////////////////

                DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

                if (listLogIn.size() > 0) {
                    hmParam = listLogIn.get(0);
                    commonService.insertLog(hmParam);
                }
                /////////////////////////////////////////////////////////////////////////////////////////////////

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
     * 인증번호 요청
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/athn-no")
    public ModelAndView requestAthnNo(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap dsm = new DataSetMap();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if (listInDs.size() > 0) {
                Map hmParam = listInDs.get(0);

                String sMemNm = StringUtils.defaultString((String) hmParam.get("mem_nm"));
                String sbrthMonDay = StringUtils.defaultString((String) hmParam.get("brth_mon_day"));
                String sSex = StringUtils.defaultString((String) hmParam.get("sex"));

                if ("0001".equals(sSex)) sSex = "1";
                else sSex = "0";
                String sCell = StringUtils.defaultString((String) hmParam.get("cell")).replaceAll("-", "");
                String sTelCd = StringUtils.defaultString((String) hmParam.get("tel_cd"));
                // 2017.11.02 김경욱 if ("05".equals(sTelCd)) sTelCd = "x"; // 기타
                String seqno = "";

                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                seqno = sdf.format(cal.getTime());

                //String sOknameIp = basVlService.getBasVlAsString("okname_ip");
                String sOknamePort = basVlService.getBasVlAsString("okname_port");                 
                String sOknameIp = EgovProperties.getProperty("nice.safekey.server.ip");                                                

                String cmd[] = new String[19];
                cmd[0] = seqno;
                cmd[1] = sMemNm;      			// 홍길동
                cmd[2] = sbrthMonDay; 			// 19700101
                cmd[3] = sSex;        			// 성별: 여자(0), 남자(1)
                cmd[4] = "1";		  			// 내외국인구분: 내국인(1), 외국인(2)
                cmd[5] = sTelCd;	  			// 통신사: SKT(01), KT(02), LGU+(03), SKT알뜰폰(04), KT알뜰폰(05), LGU알뜰폰(06) // 기존 : x(05)
                cmd[6] = sCell;       			// 01012341234
                cmd[7] = "N";         			// SMS재전송여부 (Y:재전송, N:최초전송)
                cmd[8] = "0";         			// 예비
                cmd[9] = "0";         			// 예비
                cmd[10] = "10";       			// 요청수단코드 (10:핸드폰)
                cmd[11] = "00";       			// 인증요청사유코드 (00:회원가입, 01:성인인증, 02:회원정보수정, 03:비밀번호찾기, 04:상품구매, 99:기타)
                cmd[12] = "V18290000000";		// 회원사 ID : KCB에서 아이디를 발급 받은후 사용함

                //cmd[13] = "x";				// 회원사 해당 서버의 IP
                cmd[13] = sOknameIp;		// 운영
                //cmd[13] = "211.169.49.253";	// 테스트

                cmd[14] = "http://"+sOknameIp+":"+sOknamePort;  	// 회원사 사이트 URL
                cmd[15] = "dmsweb";				// 회원사 사이트 도메인명

                // 실명확인 서버 접속 URL
                //cmd[16] = "http://tsafe.ok-name.co.kr:29080/KcbWebService/OkNameService"; 	// 테스트서버
                //cmd[17] = "C:\\okname\\log";	// 로그 경로
                cmd[16] = "http://safe.ok-name.co.kr/KcbWebService/OkNameService";		// 운영서버
                cmd[17] = "/Logs/okname";
                cmd[18] = "JULD";

                String retcode = null;
                String retMsg = null;
                List result = new ArrayList();
                int ret = -999;

                Okname okname = new Okname();
                ret = okname.exec(cmd, result);

                if (ret == 0) {
                    retcode = (String)result.get(0);
                } else {
                    DecimalFormat dcf = new DecimalFormat("000");
                    if(ret <= 200) retcode = (new StringBuilder("B")).append(dcf.format(ret)).toString();
                    else retcode = (new StringBuilder("S")).append(dcf.format(ret)).toString();
                    retMsg = (String)result.get(1);
                }

                Map<String, Object> mSelfAthn = new HashMap<String, Object>();
                mSelfAthn.put("mem_nm", sMemNm);
                mSelfAthn.put("brth_mon_day", sbrthMonDay);
                mSelfAthn.put("sex", sSex);
                mSelfAthn.put("cell", sCell);
                mSelfAthn.put("tel_cd", sTelCd);
                mSelfAthn.put("seq_no", seqno);
                mSelfAthn.put("result", retcode);
                mSelfAthn.put("retMsg", retMsg);

                dsm.setRowMaps(mSelfAthn);
                mapOutDs.put("ds_output", dsm);
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
     * 인증번호 인증
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/self-athn")
    public ModelAndView selfAthnCustBasi(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap dsm = new DataSetMap();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if (listInDs.size() > 0) {
                Map hmParam = listInDs.get(0);

                String seqNo = StringUtils.defaultString((String) hmParam.get("seq_no"));
                String sCell = StringUtils.defaultString((String) hmParam.get("cell")).replaceAll("-", "");
                String sIdNo = StringUtils.defaultString((String) hmParam.get("id_no"));
                //Runtime oRuntime = Runtime.getRuntime();

                //String sOknameIp = basVlService.getBasVlAsString("okname_ip");
                String sOknameIp 	= EgovProperties.getProperty("nice.safekey.server.ip");   

                String cmd[] = new String[8];
                cmd[0] = seqNo;
                cmd[1] = sCell;					// 01012341234
                cmd[2] = sIdNo;					// SMS인증번호
                cmd[3] = "V18290000000";		// 회원사 ID : KCB에서 아이디를 발급 받은후 사용함

                // 회원사 해당 서버의 IP
                //cmd[4] = "x";
                cmd[4] = sOknameIp;		// 운영
                //cmd[4] = "211.169.49.253";	// 테스트

                // 실명확인 서버 접속 URL
                //cmd[5] = "http://tsafe.ok-name.co.kr:29080/KcbWebService/OkNameService";	// 테스트서버
                //cmd[6] = "C:\\okname\\log";		// 로그 경로
                cmd[5] = "http://safe.ok-name.co.kr/KcbWebService/OkNameService";			// 운영서버
                cmd[6] = "/Logs/okname";
                cmd[7] = "MULD";
                String retcode = null;
                String retmsg = null;
                String di = null;
                String ci = null;
                List result = new ArrayList();
                int ret = -999;

                Okname okname = new Okname();
                ret = okname.exec(cmd, result);

                if (ret == 0) {
                    retcode = (String)result.get(0);
                    retmsg = (String)result.get(1);
                    di = (String)result.get(4);
                    ci = (String)result.get(5);
                } else {
                    DecimalFormat dcf = new DecimalFormat("000");
                    if(ret <= 200) retcode = (new StringBuilder("B")).append(dcf.format(ret)).toString();
                    else retcode = (new StringBuilder("S")).append(dcf.format(ret)).toString();
                }

                Map<String, Object> mSelfAthn = new HashMap<String, Object>();
                mSelfAthn.put("seq_no", seqNo);
                mSelfAthn.put("cell", sCell);
                mSelfAthn.put("id_no", sIdNo);
                mSelfAthn.put("result", retcode);
                mSelfAthn.put("resultMsg", retmsg);
                mSelfAthn.put("di", di);
                mSelfAthn.put("ci", ci);
                dsm.setRowMaps(mSelfAthn);
                mapOutDs.put("ds_output", dsm);
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
     * 고객상세 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/view")
    public ModelAndView getCustBasi(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            String sMemNo = StringUtils.defaultString((String) mapInVar.get("mem_no"));

            if ("".equals(sMemNo)) {
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                modelAndView.addObject(XPlatformConstant.ERROR_MSG, "회원아이디가 없습니다.");

                return modelAndView;
            }

            Map<String, Object> mData = custBasiService.getCustBasi(sMemNo);

            if (mData != null) {
                listMap.setRowMaps(mData);
                mapOutDs.put("ds_output", listMap);
            }

            // 고객조회로그 등록
            //insertCustInqLog(pmParam);

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
     * 고객조회로그 정보를 등록한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/cust-inq-log/insert")
    public ModelAndView insertCustInqLog(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap dtptMap = new DataSetMap();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if (listInDs.size() > 0) {
                Map hmParam = listInDs.get(0);

                String sMemNo = StringUtils.defaultString((String) hmParam.get("mem_no"));
                if ("".equals(sMemNo)) {
                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG, "회원아이디가 없습니다.");

                    return modelAndView;
                }

                ParamUtils.addSaveParam(hmParam);

                // 고객조회대상코드가 없는 경우 기본값 "상담이력" 입력
                String sCustInqTrgtCd = StringUtils.defaultString((String) hmParam.get("cust_inq_trgt_cd"));
                if ("".equals(sCustInqTrgtCd)) {
                    hmParam.put("cust_inq_trgt_cd", "20");
                }

                custBasiService.insertCustInqLog(hmParam);
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
     * 대명회원정보로 상담회원정보를 업데이트 한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    /*@RequestMapping(value = "/update-cons/member")
    public ModelAndView updateConsCustBasi(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            String sMemNo = StringUtils.defaultString((String) mapInVar.get("mem_no"));

            if ("".equals(sMemNo)) {
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                modelAndView.addObject(XPlatformConstant.ERROR_MSG, "회원아이디가 없습니다.");

                return modelAndView;
            }

            ParamUtils.addSaveParam(hmParam);
            hmParam.put("mem_no", sMemNo);
            int updateCnt = custBasiService.updateConsCustBasi(hmParam);

            mapOutVar.put("gv_update_cnt", updateCnt);

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
    }*/

    /**
     * 고객 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/member-cnt")
    public ModelAndView getCustBasiCnt(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        //DataSetMap listMap = new DataSetMap();
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

                int nTotal = custBasiService.getCustBasiCount(hmParam);
                mapOutVar.put("tc_mem", nTotal);

                /*List<Map<String, Object>> mList = custBasiService.getCustBasiList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);*/
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