/*
 * (@)# DlwController.java
 *
 * @author 석민
 * @version 1.0
 * @date 2016/02/02
 * Copyright (c) 2016 by Inwoo tech inc, KOREA. All Rights Reserved.
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

package powerservice.business.dlw.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.bean.UserSession;
import powerservice.business.cns.web.ConsController;
//2018.01.05 로그 추가
import powerservice.business.common.service.CommonService;
import powerservice.business.dlw.service.DataAthrQuryService;
import powerservice.business.dlw.service.DlwCardService;
import powerservice.business.dlw.service.DlwCmsService;
import powerservice.business.dlw.service.DlwCondService;
import powerservice.business.dlw.service.DlwCustService;
import powerservice.business.dlw.service.DlwWdrwService;
import powerservice.business.mall.service.DlwMallLinkedService;
import powerservice.business.sys.service.BasVlService;
import powerservice.common.util.CommonUtils;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.tobesoft.xplatform.data.DataSet;
import com.tobesoft.xplatform.data.PlatformData;
import com.tobesoft.xplatform.data.VariableList;
import com.tobesoft.xplatform.tx.HttpPlatformResponse;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;
import egovframework.rte.fdl.cmmn.exception.EgovBizException;
/**
 * 대명라이프웨이 Cms 정보를 관리한다
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 석민
 * @version 1.0
 * @date 2016/02/02
 * @프로그램ID DlwCms
 */
@Controller
@RequestMapping(value = "/dlw/cms")
public class DlwCmsController {

    //private final Logger log = LoggerFactory.getLogger(DlwCmsController.class);

    @Resource
    private DlwCmsService DlwCmsService;

    @Resource
    private DlwCardService DlwCardService;

    @Resource
    private DataAthrQuryService dataAthrQuryService;

    @Resource
    private DlwCondService DlwCondService;

    @Resource
    private ConsController consController;

    @Resource
    private BasVlService basVlService;

    @Resource
    private DlwCustService dlwCustService;

    @Resource
    private CommonService commonService;
    
    @Resource
    private DlwWdrwService DlwWdrwService;
    
    @Resource
    private DlwMallLinkedService DlwMallLinkedService;



    /**
     * 대명라이프웨이 Cms 고객정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/cms-csmm/list")
    public ModelAndView getDlwCmsCsmmList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            int nTotal = DlwCmsService.getDlwCmsCsmmCount(hmParam);

            mapOutVar.put("tc_cmsCsmm", nTotal);

            List<Map<String, Object>> mList = DlwCmsService.getDlwCmsCsmm(hmParam);
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
     * 대명라이프웨이 회원별 Cms 신청내역 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/cms-rgsn-hstr")
    public ModelAndView getDlwCmsRgsnHstrList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            hmParam.put("accnt_no", mapInVar.get("accnt_no"));
            hmParam.put("orderBy", mapInVar.get("orderBy"));
            hmParam.put("orderDirection", mapInVar.get("orderDirection"));

            // 페이징 정보
            DataSetMap listInGds = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
            if (listInGds != null && listInGds.size() > 0) {
                Map pMap = listInGds.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }

            int nTotal = DlwCmsService.getDlwCmsRgsnHstrCount(hmParam);

            mapOutVar.put("tc_cmsRgsnHstr", nTotal);

            List<Map<String, Object>> mList = DlwCmsService.getDlwCmsRgsnHstrList(hmParam);
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
     * 대명라이프웨이 회원별 Cms 출금의뢰내역 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/cms-wdrw-hstr")
    public ModelAndView getDlwCmsWdrwHstrList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            hmParam.put("accnt_no", mapInVar.get("accnt_no"));
            hmParam.put("orderBy", mapInVar.get("orderBy"));
            hmParam.put("orderDirection", mapInVar.get("orderDirection"));

            // 페이징 정보
            DataSetMap listInGds = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
            if (listInGds != null && listInGds.size() > 0) {
                Map pMap = listInGds.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }

            int nTotal = DlwCmsService.getDlwCmsWdrwHstrCount(hmParam);

            mapOutVar.put("tc_cmsWdrwHstr", nTotal);

            List<Map<String, Object>> mList = DlwCmsService.getDlwCmsWdrwHstrList(hmParam);
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
     * 대명라이프웨이 CMS 이체정보를 저장한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/cms-csmm/save")
    public ModelAndView insertCmsTranInfo(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

            if (listInDs.size() > 0) {
                Map hmParam = listInDs.get(0);

                //세션
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("emple_no", hmParam.get("rgsr_id"));
                hmParam.put("reg_man", hmParam.get("rgsr_id"));
                hmParam.put("upd_man", hmParam.get("rgsr_id"));




            //    int rowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

               // if ( rowType == DataSet.ROW_TYPE_INSERTED ){
                    //DlwCmsService.insertCmsTranInfo(hmParam);
               // } else if (rowType == DataSet.ROW_TYPE_UPDATED){
                    DlwCmsService.updateCmsTranInfo(hmParam);
               // }

                //2018.01.05 접속 로그//////////////////////////////////////////////////////////////////////////////
                DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

                if (listLogIn.size() > 0) {
                    hmParam = listLogIn.get(0);
                    commonService.insertLog(hmParam);
                }
                ////////////////////////////////////////////////////////////////////////////////////////////////////
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
     * 대명라이프웨이 CMS 산출중 여부를 확인한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/cms-wdrw-chk")
    public ModelAndView getDlwCmsWdrwChk(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            //Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            //Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            hmParam.put("accnt_no", mapInVar.get("accnt_no"));
            //hmParam.put("orderBy", mapInVar.get("orderBy"));
            //hmParam.put("orderDirection", mapInVar.get("orderDirection"));

            // 페이징 정보
            //DataSetMap listInGds = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
            //if (listInGds != null && listInGds.size() > 0) {
                //Map pMap = listInGds.get(0);
                //hmParam.put("startLine", pMap.get("startNum"));
                //hmParam.put("endLine", pMap.get("endNum"));
            //}

            String nResult = "";

            List<Map<String, Object>> mList = DlwCmsService.getDlwCmsWdrwChk(hmParam);
            if (mList != null && mList.size() > 0) {
                nResult = (String)mList.get(0).get("cms_wdrw");
            }

            mapOutVar.put("cmsWdrwChk", nResult);

            //List<Map<String, Object>> mList = DlwCmsService.getDlwCardWdrwHstrList(hmParam);
            //listMap.setRowMaps(mList);
            //mapOutDs.put("ds_output", listMap);

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
    @RequestMapping(value = "/cms-aplc_dtl/list")
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

            int nTotal = DlwCmsService.getDlwCmsAplcDtlCount(hmParam);

            mapOutVar.put("tc_cmsAplcDtl", nTotal);

            List<Map<String, Object>> mList = DlwCmsService.getDlwCmsAplcDtl(hmParam);
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
     * 대명라이프웨이 CMS 부가서비스(신규,해지)를 등록한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/cms-anxt-srvc/save")
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

            hmParam.put("paramEmpleNo", hmParam.get("rgsr_id"));
            hmParam.put("paramAs", "EMP");

            //세션
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("emple_no", hmParam.get("rgsr_id"));
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            hmParam.put("upd_man", hmParam.get("rgsr_id"));

            //웹모듈
            String rltm_web_module = (String)hmParam.get("rltm_web_module");

            List accntNoList = new ArrayList();
            accntNoList = (ArrayList)hmParam.get("accnt_list");

            String cmsMsg = "";

            cmsMsg = DlwCmsService.insertDlwCmsAnxtSrvc(hmParam);

/*            if (accntNoList != null && accntNoList.size() > 0) {
                for(int i = 0; i < accntNoList.size(); i++)
                {
                    HashMap accntNoMap = (HashMap)accntNoList.get(i);
                    hmParam.put("accnt_no", (String)accntNoMap.get("accnt_no"));
                    if("Y".equals(rltm_web_module))
                    {
                        if("0000".equals((String)accntNoMap.get("code"))) {
                            cmsMsg = DlwCmsService.insertCMSMemberNewByBuga(hmParam);
                        } else {
                        }
                    } else {
                        cmsMsg = DlwCmsService.insertCMSMemberNewByBuga(hmParam);
                    }
                }
            } else {
                cmsMsg = DlwCmsService.insertCMSMemberNewByBuga(hmParam);
            }*/

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
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

    /**
     * 대명라이프웨이 CMS 출금이체 신청내역조회-신청전인 데이터를 조회한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/wdrw-tran-dtl/list")
    public ModelAndView getDlwWdrwTranDtl(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            int nTotal = DlwCmsService.getDlwWdrwTranDtlCount(hmParam);

            List<Map<String, Object>> mList = DlwCmsService.getDlwWdrwTranDtlList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            List<Map<String, Object>> mComnList = DlwCmsService.getDlwCmsComnInfo(hmParam);
            String mnthLimitAmt = "";
            //CMS 당월 출금의뢰 총액 조회

            String mnthAmt = DlwCmsService.getDlwWdrwReqDtTtamt(hmParam);


            double dmnthAmt = Double.valueOf(mnthAmt).doubleValue();

            mapOutVar.put("tc_wdrwTran", nTotal);
            mapOutVar.put("mnthLimitAmt", mnthLimitAmt);
            mapOutVar.put("mnthAmt", dmnthAmt);

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
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

    /**
     * 대명라이프웨이 산출데이터 청구건수를 업데이트한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/wdrw-tran-dtl/inv-gunsu-update/{pageType}")
    public ModelAndView updateDlwInvGunsu(XPlatformMapDTO xpDto, Model model, @PathVariable("pageType") String psPathTyp) throws Exception {
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

            String msg = "";

            for (int i = 0; i < listInDs.size(); i++) {
                hmParam = listInDs.get(i);

                hmParam.put("mode", mapInVar.get("mode"));
                hmParam.put("wdrw_gubun", mapInVar.get("wdrw_gubun"));

                //세션
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("emple_no", hmParam.get("rgsr_id"));
                hmParam.put("reg_man", hmParam.get("rgsr_id"));
                hmParam.put("upd_man", hmParam.get("rgsr_id"));

                int rowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

                String seq = StringUtils.defaultString(String.valueOf(hmParam.get("seq")));
                String accnt_no = StringUtils.defaultString(String.valueOf(hmParam.get("accnt_no")));
                String inv_gunsu = StringUtils.defaultString(String.valueOf(hmParam.get("inv_gunsu")));
                String mon_pay_amt = StringUtils.defaultString(String.valueOf(hmParam.get("mon_pay_amt")));
                String expr_no = StringUtils.defaultString(String.valueOf(hmParam.get("expr_no")));
                String inv_no = StringUtils.defaultString(String.valueOf(hmParam.get("inv_no")));

                hmParam.put("seq", seq);
                hmParam.put("accnt_no", accnt_no);
                hmParam.put("inv_gunsu", inv_gunsu);
                hmParam.put("mon_pay_amt", mon_pay_amt);
                hmParam.put("expr_no", expr_no);
                hmParam.put("inv_no", inv_no);

                if (rowType == DataSet.ROW_TYPE_UPDATED) {
                    boolean update_fg = true;
                    if("call".equals(psPathTyp)){
                        update_fg = DlwCmsService.updateDlwInvGunsuCall(hmParam);
                    } else {
                        update_fg = DlwCmsService.updateDlwInvGunsu(hmParam);
                    }

                    if (update_fg) {
                        msg = "ok";
                    } else {
                        msg = "limit";
                    }

                }

                List<Map<String, Object>> mList = null;
                if("call".equals(psPathTyp)){
                    mList = DlwCmsService.getDlwWdrwTranDtlList(hmParam);
                } else {
                    if (!"Card".equals(mapInVar.get("wdrw_gubun"))) {
                        mList = DlwCmsService.getDlwWdrwHstr(hmParam);
                    } else {
                        mList = DlwCardService.getCardWdrwReqList(hmParam);
                    }
                }


                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);

                //message
                mapOutVar.put("update_msg", msg);
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
     * 대명라이프웨이 청구파일 적용한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/wdrw-req")
    public ModelAndView updateDlwWdrwReq(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();

            String msg = "";
            DlwCmsService.updateCmsAndCardWdrwReq(mapInDs, mapInVar);

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
     * 대명라이프웨이 결합상품 산출 오류 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/comb-err")
    public ModelAndView getDlwCombErr(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            //int nTotal = DlwCmsService.getDlwCmsWdrwHstrCount(hmParam);
            int nTotal = 0;

            List<Map<String, Object>> mList = DlwCmsService.getDlwCombErrList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            if (mList != null && mList.size() > 0) {
                nTotal = mList.size();
            }

            mapOutVar.put("tc_combErr", nTotal);

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
     * 대명라이프웨이 출금이체 임시회원을 산출한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/wdrw-temp-member/list")
    public ModelAndView getDlwWdrwTempMemberList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            //int nTotal = DlwCmsService.getDlwCmsWdrwHstrCount(hmParam);
            int nTotal = 0;

            List<Map<String, Object>> mList = DlwCmsService.getDlwTempMember(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            if (mList != null && mList.size() > 0) {
                nTotal = mList.size();
            }

            mapOutVar.put("tc_wdrwMember", nTotal);

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
     * 대명라이프웨이 카드오류 회원을 산출한다.
     * 임동진
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/wdrw-error-member/list")
    public ModelAndView getDlwWdrwErrorMemberList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            //int nTotal = DlwCmsService.getDlwCmsWdrwHstrCount(hmParam);
            int nTotal = 0;

            List<Map<String, Object>> mList = DlwCmsService.getDlwErrorMember(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            if (mList != null && mList.size() > 0) {
                nTotal = mList.size();
            }

            mapOutVar.put("tc_wdrwMember", nTotal);

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
     * 대명라이프웨이 출금이체 연체회원을 산출한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/wdrw-delay-member/list")
    public ModelAndView getDlwWdrwDelayMemberList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            //int nTotal = DlwCmsService.getDlwCmsWdrwHstrCount(hmParam);
            int nTotal = 0;

            List<Map<String, Object>> mList = DlwCmsService.getDlwDelayMember(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            if (mList != null && mList.size() > 0) {
                nTotal = mList.size();
            }

            mapOutVar.put("tc_wdrwMember", nTotal);

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
     * 대명라이프웨이  임시/연체건 > 청구내역 추가
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/wdrw-eb21-add")
    public ModelAndView insertDlwWdrwEb21Add(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            String msg = "";

            String mode = (String)mapInVar.get("mode");
            String wdrw_gubun = (String)mapInVar.get("wdrw_gubun");

            List<Map<String, Object>> mComnList = DlwCmsService.getDlwCmsComnInfo(hmParam);
            String use_inst_cl_cd = (String)mComnList.get(0).get("use_inst_cl_cd");
            String bank_filin_brkdn = (String)mComnList.get(0).get("bank_filin_brkdn");
            String mon_wdrw_limit = (String)mComnList.get(0).get("mon_wdrw_limit");
            String gundang_wdrw_limit = (String)mComnList.get(0).get("gundang_wdrw_limit");
            //int gundang_wdrw_limit = Integer.parseInt((String)mComnList.get(0).get("gundang_wdrw_limit"));



            for (int i = 0; i < listInDs.size(); i++) {
                hmParam = listInDs.get(i);

                hmParam.put("mode", mode);
                hmParam.put("wdrw_gubun", wdrw_gubun);

                hmParam.put("use_inst_cl_cd", use_inst_cl_cd);
                hmParam.put("bank_filin_brkdn", bank_filin_brkdn);
                hmParam.put("mon_wdrw_limit", mon_wdrw_limit);
                hmParam.put("gundang_wdrw_limit", gundang_wdrw_limit);

                //세션
                ParamUtils.addSaveParam(hmParam);

                hmParam.put("emple_no", hmParam.get("rgsr_id"));
                hmParam.put("reg_man", hmParam.get("rgsr_id"));
                hmParam.put("upd_man", hmParam.get("rgsr_id"));

                int rowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

                if (rowType == DataSet.ROW_TYPE_UPDATED) {
                    DlwCmsService.insertDlwWdrwEb21Add(hmParam);
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
     * 대명라이프웨이 회원별 Cms 출금의뢰내역 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/wdrw-add-data")
    public ModelAndView getDlwWdrwAddData(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            hmParam.put("accnt_no", mapInVar.get("accnt_no"));
            hmParam.put("mode", mapInVar.get("mode"));
            hmParam.put("wdrw_gubun", mapInVar.get("wdrw_gubun"));

            List<Map<String, Object>> mList = DlwCmsService.getDlwWdrwAddData(hmParam);
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
     * 대명라이프웨이 청구금액을 조회한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/inv-amt")
    public ModelAndView getDlwInvAmt(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            int inv_cnt = 0;
            int inv_amt = 0;

            hmParam.put("accnt_no", mapInVar.get("accnt_no"));
            hmParam.put("gunsu", mapInVar.get("inv_gunsu"));

            inv_cnt = DlwCmsService.getDlwLastPayNo(hmParam);
            hmParam.put("inv_cnt",inv_cnt);

            int totalAmt = 0;
            boolean flag = false;
            totalAmt = DlwCmsService.getInvAmt(hmParam);

            if(totalAmt <= 0) {
                totalAmt = 0;
            }

            mapOutVar.put("mon_pay_amt", totalAmt);

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
     * 대명라이프웨이 EB21 산출내역 적용
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/wdrw-eb21-temp-add")
    public ModelAndView insertAddTempEb21(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            String msg = "";

            List<Map<String, Object>> mComnList = DlwCmsService.getDlwCmsComnInfo(hmParam);
            String use_inst_cl_cd = (String)mComnList.get(0).get("use_inst_cl_cd");
            String bank_filin_brkdn = (String)mComnList.get(0).get("bank_filin_brkdn");

            hmParam = listInDs.get(0);


            hmParam.put("use_inst_cl_cd", use_inst_cl_cd);
            hmParam.put("bank_filin_brkdn", bank_filin_brkdn);

            //세션
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("emple_no", hmParam.get("rgsr_id"));
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            hmParam.put("upd_man", hmParam.get("rgsr_id"));


            DlwCmsService.insertAddTempEb21(hmParam);

            Map<String, Object> mCust = dlwCustService.getCustBasiConsInfo(hmParam);

            //회원정보 없을경우 상담저장 안함
            if (mCust != null && mCust.size() > 0) {
                consController.saveConsdlw(hmParam);
            }

            List<Map<String, Object>> mList = null;
            if(!"Card".equals(hmParam.get("wdrw_gubun")))  {
                mList = DlwCmsService.getDlwWdrwHstr(hmParam);
            } else {
                hmParam.put("seq", "0");
               System.out.println("1111111111");
                CommonUtils.printLog(hmParam);
                System.out.println("555555555");
                mList = DlwCardService.getCardWdrwReqList(hmParam);
                System.out.println("222222");
            }
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
     * 대명라이프웨이 EB21 콜센터 산출내역 적용
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/wdrw-eb21-temp-call-add")
    public ModelAndView insertAddTempCallEb21(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            String msg = "";

            List<Map<String, Object>> mComnList = DlwCmsService.getDlwCmsComnInfo(hmParam);
            String use_inst_cl_cd = (String)mComnList.get(0).get("use_inst_cl_cd");
            String bank_filin_brkdn = (String)mComnList.get(0).get("bank_filin_brkdn");

            hmParam = listInDs.get(0);


            hmParam.put("use_inst_cl_cd", use_inst_cl_cd);
            hmParam.put("bank_filin_brkdn", bank_filin_brkdn);

            //세션
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("emple_no", hmParam.get("rgsr_id"));
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            hmParam.put("upd_man", hmParam.get("rgsr_id"));

            DlwCmsService.insertAddTempCallEb21(hmParam);

            Map<String, Object> mCust = dlwCustService.getCustBasiConsInfo(hmParam);

            //회원정보 없을경우 상담저장 안함
            if (mCust != null && mCust.size() > 0) {
                consController.saveConsdlw(hmParam);
            }

            List<Map<String, Object>> mList = null;
            if(!"Card".equals(hmParam.get("wdrw_gubun")))  {
                mList = DlwCmsService.getDlwWdrwHstr(hmParam);
            } else {
                hmParam.put("seq", "0");
                mList = DlwCardService.getCardWdrwReqList(hmParam);
            }
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
     * 대명라이프웨이 CMS 출금이체 파일산출 데이터를 조회한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/wdrw-hstr-eb21/list")
    public ModelAndView getDlwWdrwHstrEb21List(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            //hmParam = listInDs.get(0);

            ParamUtils.addSaveParam(hmParam);
            hmParam.put("emple_no", hmParam.get("rgsr_id"));
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            hmParam.put("upd_man", hmParam.get("rgsr_id"));

            hmParam.put("paramEmpleNo", hmParam.get("rgsr_id"));
            hmParam.put("paramAs", "EMP");

            List<Map<String, Object>> mList = DlwCmsService.getDlwWdrwHstr(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            List<Map<String, Object>> mComnList = DlwCmsService.getDlwCmsComnInfo(hmParam);
            String mon_wdrw_limit = (String)mComnList.get(0).get("mon_wdrw_limit");

            //CMS 당월 출금의뢰 총액 조회
            String mnthAmt = DlwCmsService.getDlwWdrwReqDtTtamt(hmParam);

            double dmnthAmt = Double.valueOf(mnthAmt).doubleValue();

            //mapOutVar.put("tc_list", mList.size());
            mapOutVar.put("mnthLimitAmt", mon_wdrw_limit);
            mapOutVar.put("mnthAmt", dmnthAmt);

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
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

    /**
     * 대명라이프웨이 대기중인 모든 청구목록 삭제
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/wdrw-all/delete")
    public ModelAndView deleteWdrwAll(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();



            Map<String, Object> hmParam = new HashMap<String, Object>();
            String wdrw_gubun = (String)mapInVar.get("wdrw_gubun");

            //세션
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("emple_no", hmParam.get("rgsr_id"));
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            hmParam.put("upd_man", hmParam.get("rgsr_id"));

            if(!"Card".equals(wdrw_gubun)) {
                DlwCmsService.updateAllWdrwCallCenterTemp(hmParam);
                DlwCmsService.deleteAllWdrwTemp(hmParam);
            } else {
                DlwCardService.deleteAllCardWdrwTemp(hmParam);
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
     * 대명라이프웨이 이체일자에 의한 산출
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/wdrw-ichae/save")
    public ModelAndView saveWdrwIchaeDt(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            hmParam.put("wdrw_gubun", mapInVar.get("wdrw_gubun"));

            //세션
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("emple_no", hmParam.get("rgsr_id"));
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            hmParam.put("upd_man", hmParam.get("rgsr_id"));

       /*     hmParam.put("ichae_stt_dt", Integer.parseInt((String)mapInVar.get("ichae_stt_dt")));
            hmParam.put("ichae_end_dt", Integer.parseInt((String)mapInVar.get("ichae_end_dt")));
*/
            hmParam.put("ichae_stt_dt", (String)mapInVar.get("ichae_stt_dt"));
            hmParam.put("ichae_end_dt", (String)mapInVar.get("ichae_end_dt"));
            hmParam.put("sgubun", (String)mapInVar.get("sgubun"));

            ///////////  파일 작성된게 있으면  확인후 없으면  파일작성후 처리해야된다고 메세지박스띄어줌      ---박영선 매니져 요창
/*
            System.out.println((String)mapInVar.get("sgubun"));

            int cmsIchaecnt;
            if (!"2".equals((String)mapInVar.get("sgubun")))  {

                cmsIchaecnt = DlwCmsService.CmsMemberByIchaeCount(hmParam);
            } else {

                cmsIchaecnt = 0 ;
            }

            if (cmsIchaecnt < 1)  {

            */

            List<Map<String, Object>> result = DlwCmsService.getCmsMemberByIchaeDt(hmParam);

            //CMS 당월 출금의뢰 총액 조회
        //    int mnthAmt = DlwCmsService.getDlwWdrwReqDtTtamt(hmParam);
            String mnthAmt = DlwCmsService.getDlwWdrwReqDtTtamt(hmParam);
            double dmnthAmt = Double.valueOf(mnthAmt).doubleValue();

            List<Map<String, Object>> mComnList = DlwCmsService.getDlwCmsComnInfo(hmParam);
            String mon_wdrw_limit = (String)mComnList.get(0).get("mon_wdrw_limit");
            mapOutVar.put("mnthLimitAmt", mon_wdrw_limit);
            mapOutVar.put("mnthAmt", mnthAmt);

            listMap.setRowMaps(result);
            mapOutDs.put("ds_output", listMap);

            //2018.01.05 접속 로그////////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            //////////////////////////////////////////////////////////////////////////////////////////////////////

            /*
            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());

            }  else {
                szErrorCode = "-1";
                szErrorMsg  = "산출건수 결과 반영 후 산출 가능합니다.";
            }
            */




        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

    @RequestMapping(value = "/wdrw-imsi-ichae/save")
    public ModelAndView saveWdrwimsiIchaeDt(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            hmParam.put("wdrw_gubun", mapInVar.get("wdrw_gubun"));
        //    System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            //세션
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("emple_no", hmParam.get("rgsr_id"));
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            hmParam.put("upd_man", hmParam.get("rgsr_id"));

            CommonUtils.printLog(hmParam);


            DlwCmsService.getCmsMemberByimsiIchaeDt(hmParam);

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
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

    /**
     * 대명라이프웨이 산출된 건중 고객만족센터에 미적용으로 등록되어 있는건이 없는 경우
     * 				  CMS산출메뉴와 콜센터 메뉴 모두에서 출금신청했을 경우
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/call-wdrw-req-chk")
    public ModelAndView callWdrwReqCheck(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            hmParam.put("wdrw_gubun", mapInVar.get("wdrw_gubun"));
            String wdrw_gubun = (String)mapInVar.get("wdrw_gubun");
            //세션
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("emple_no", hmParam.get("rgsr_id"));
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            hmParam.put("upd_man", hmParam.get("rgsr_id"));

            hmParam.put("inv_dt",mapInVar.get("inv_dt"));

            String msg = "success";
            if(!"Card".equals(wdrw_gubun)) {
                if (DlwCmsService.getCallWdrwReqCheck(hmParam) > 0) {
                    msg = "fail";
                }
            } else {
                if(DlwCardService.getCallCardWdrwReqCheck(hmParam) > 0) {
                    msg = "fail";
                }
            }

            List<Map<String, Object>> mList = new ArrayList<Map<String,Object>>();
            if(!"Card".equals(wdrw_gubun)) {
                mList = DlwCmsService.getCallDupWdrw(hmParam);
            } else {
                mList = DlwCardService.getCardCallDupWdrw(hmParam);
            }

            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);
            mapOutVar.put("callWdrwReqChkMsg", msg);


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
     * 대명라이프웨이 산출내역 파일생성
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/create-file/eb21")
    public ModelAndView createFileEb21(XPlatformMapDTO xpDto, Model model) throws Exception {
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
           /* ************************************************************************************** */

            ArrayList arr;
            String totalAmt;
            String useInstCd;
            String yymmdd;
            String invDt;
            String fileName;
            StringBuffer strbuf;
            BufferedWriter out;

            Map<String, Object> hmParam = new HashMap<String, Object>();
            List<Map<String, Object>> mComnList = DlwCmsService.getDlwCmsComnInfo(hmParam);
            Map comnMap = mComnList.get(0);
            String mon_wdrw_limit = (String)comnMap.get("mon_wdrw_limit");
            useInstCd = (String)comnMap.get("use_inst_cl_cd");
            String bankFillIn = (String)comnMap.get("bank_filin_brkdn");
            String filePath = (String)comnMap.get("file_path");

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

            //for (int i = 0; i < listInDs.size(); i++) {
                //hmParam = listInDs.get(i);
            //}

            totalAmt = (String)mapInVar.get("total_amt");
            yymmdd = (String)mapInVar.get("yymmdd");
            invDt = (String)mapInVar.get("inv_dt");


            fileName = (new StringBuilder("EB21")).append(yymmdd.substring(2, 6)).toString();
            strbuf = new StringBuffer();
            out = null;
            try
            {
                String s = "";
                s = (new StringBuilder("H00000000")).append(useInstCd).append(fileName).append(yymmdd).append((String)comnMap.get("bank_op_brach_cd")).append(CommonUtils.fillEmpVal(16, (String)comnMap.get("bank_accnt_no"), "L", " ")).append(CommonUtils.fillEmpVal(94, "", "L", " ")).toString();
                strbuf.append(s);
                for(int i = 0; listInDs.size() > i; i++)
                {
                	System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx :    " + i);
                    HashMap temp = (HashMap)listInDs.get(i);
                    s = (new StringBuilder("R")).append(CommonUtils.fillEmpVal(8, String.valueOf(i + 1), "R", "0")).append(useInstCd).append(CommonUtils.fillEmpVal(7, (String)temp.get("bank_cd"), "L", "0")).append(CommonUtils.fillEmpVal(16, (String)temp.get("bank_accnt_no"), "L", " ")).append(CommonUtils.fillEmpVal(13, String.valueOf(temp.get("wdrw_req_amt")), "R", "0")).append(CommonUtils.fillEmpVal(13, (String)temp.get("idn_no"), "L", " ")).append(CommonUtils.fillEmpVal(5, "", "L", " ")).append(CommonUtils.fillEmpVal(8, CommonUtils.transByteForBankFillIn(String.valueOf(temp.get("bank_filin_brkdn")).trim()), "L", "　")).append(CommonUtils.fillEmpVal(2, "", "L", " ")).append(CommonUtils.fillEmpVal(20, (String)temp.get("sync_no"), "L", " ")).append(CommonUtils.fillEmpVal(5, String.valueOf(temp.get("seq")), "L", " ")).append("1").append(CommonUtils.fillEmpVal(12, "", "L", " ")).append(CommonUtils.fillEmpVal(21, "", "L", " ")).toString();
                    strbuf.append(s);
                }

                s = (new StringBuilder("T99999999")).append(useInstCd).append(fileName).append(CommonUtils.fillEmpVal(8, String.valueOf(listInDs.size()), "R", "0")).append(CommonUtils.fillEmpVal(8, String.valueOf(listInDs.size()), "R", "0")).append(CommonUtils.fillEmpVal(13, totalAmt, "R", "0")).append(CommonUtils.fillEmpVal(8, "", "L", "0")).append(CommonUtils.fillEmpVal(13, "", "L", "0")).append(CommonUtils.fillEmpVal(63, "", "L", " ")).append(CommonUtils.fillEmpVal(10, "", "L", " ")).toString();
                strbuf.append(s);

                ParamUtils.addCenterParam(hmParam);
                String sPayFilePath = basVlService.getBasVlAsString("pay_file_path", (String) hmParam.get("cntr_cd"));
                String sPayFilePathGbn = basVlService.getBasVlAsString("pay_file_path_gbn", (String) hmParam.get("cntr_cd"));
/*CMS경로*/
                File f = new File((new StringBuilder("/app/CMS/9993083157/EB21/")).toString(), fileName);
             //   File f = new File((new StringBuilder(sPayFilePath)).append(sPayFilePathGbn).append("CMS").append(sPayFilePathGbn).append(useInstCd).append(sPayFilePathGbn).append("EB21").append(sPayFilePathGbn).toString(), fileName);
               System.out.println(f);
              // C:\CARD\9993083157CMS\CARD\99930831579993083157\CARD\9993083157EB21\CARD\9993083157\EB211129
/*
인우경
                //File f = new File((new StringBuilder("C:\\CMS\\")).append(useInstCd).append("\\EB21\\").toString(), fileName);
                File f = new File((new StringBuilder(sPayFilePath)).append(sPayFilePathGbn).append("CMS").append(sPayFilePathGbn).append(useInstCd).append(sPayFilePathGbn).append("EB21").append(sPayFilePathGbn).toString(), fileName);


*/
                f.createNewFile();

                out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), "EUC-KR"));
                out.write(strbuf.toString());
            }
            catch(IOException e)
            {
                e.printStackTrace();
            } finally {
                out.close();
            }

            hmParam.put("inv_dt", invDt);
            DlwCmsService.updateCmsAppStateLrqnt(hmParam);
           /* ************************************************************************************** */


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
     * 대명라이프웨이 EB22 파일변환 파일명을 조회한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/eb22/file-nm-list")
    public ModelAndView getEB22FileNmList(XPlatformMapDTO xpDto, Model model) throws Exception {
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


            List<Map<String, Object>> mList = DlwCmsService.getCmsResultFileNm(hmParam);

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
     * 대명라이프웨이  EB22 파일 결과처리 여부 조회한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/eb22/file-is-trans")
    public ModelAndView getCmsAppIsTransEb22(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            hmParam.put("yymmdd", mapInVar.get("yymmdd"));
            hmParam.put("result", "");


            System.out.println("------------ 1");
            DlwCmsService.getCmsAppIsTransEb22(hmParam);
            System.out.println("------------ 2");


            String stat = (String)hmParam.get("result");

            System.out.println("------------ 3");
            System.out.println("stat : " + stat);

            String trans_yn = "";

            if ("A".equals(stat) || "B".equals(stat)) {
                trans_yn = "N";
            } else if ("C".equals(stat)) {
                trans_yn = "Y";
            } else if ("C1".equals(stat)) {
                trans_yn = "T";
            }  else {
                trans_yn = "E";
            }


            mapOutVar.put("trans_yn", trans_yn);

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
    @RequestMapping(value = "/eb22/read-file")
    public ModelAndView getReadFileCms(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            hmParam.put("file_nm", mapInVar.get("file_nm"));
            hmParam.put("yymmdd", mapInVar.get("yymmdd"));

            List<Map<String, Object>> mList = DlwCmsService.getReadFileCms(hmParam);

            ////////////// mList 없음
            if (mList != null && mList.size() > 0 ) {
                String  s_stat =  (String)mList.get(0).get("stat");
                  mapOutVar.put("stat", s_stat);
         }

            List<Map<String, Object>> mComnList = DlwCmsService.getDlwCmsComnInfo(hmParam);
            String wdrw_imps_com_str = (String)mComnList.get(0).get("wdrw_imps_com");
            String wdrw_com_str = (String)mComnList.get(0).get("wdrw_com");

            int wdrw_imps_com = Integer.parseInt(wdrw_imps_com_str);
            int wdrw_com = Integer.parseInt(wdrw_com_str);
            int recordCnt = mList.size();

            int impsAmt = recordCnt * wdrw_imps_com;
            int totalRecordCnt = DlwCmsService.getEB22WdrwCount(hmParam);
            int totalCGAmt =  wdrw_com * (totalRecordCnt - recordCnt);

            mapOutVar.put("totalCGAmt", totalCGAmt);
            mapOutVar.put("recordCnt", recordCnt);
            mapOutVar.put("impsAmt", impsAmt);
            mapOutVar.put("impsCnt", recordCnt);

            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            //2018.01.08 접속 로그////////////////////////////////////////////////////////////////////////////////
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
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

    /**
     * 대명라이프웨이 Cms 결과 파일변환
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/eb22/convert-file")
    public ModelAndView updateConvertFileCms(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            //세션
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("emple_no", hmParam.get("rgsr_id"));
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            hmParam.put("upd_man", hmParam.get("rgsr_id"));

            hmParam.put("file_nm", mapInVar.get("file_nm"));
            hmParam.put("yymmdd", mapInVar.get("yymmdd"));
            hmParam.put("pay_dt", mapInVar.get("pay_dt"));

            String gasu = "";
            List<Map<String, Object>> mList = DlwCmsService.cmsResultProc(hmParam);
            if (mList != null && mList.size() > 0 ) {
                gasu = (String)mList.get(0).get("gasu");
            }
            mapOutVar.put("gasu", gasu);

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
     * 대명라이프웨이 출금이체내역 조회를 한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/wdrw-hstr")
    public ModelAndView getDlwWdrwHstr(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap listMap2 = new DataSetMap();
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

            List<Map<String, Object>> mList = new ArrayList<Map<String,Object>>();
            String wdrw_gubun = CommonUtils.checkNull((String)hmParam.get("wdrw_gubun"));
            if("Stats".equals(wdrw_gubun)) {
                //세션
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("emple_no", hmParam.get("rgsr_id"));
                hmParam.put("reg_man", hmParam.get("rgsr_id"));
                hmParam.put("upd_man", hmParam.get("rgsr_id"));

                hmParam.put("paramEmpleNo", hmParam.get("rgsr_id"));
                hmParam.put("paramAs", "emp");
                String dataAthrQury = dataAthrQuryService.getDataAthrQury(hmParam);
                hmParam.put("dataAthrQury", dataAthrQury);

                mList = DlwCmsService.getWdrwHstrSttc(hmParam);
                List<Map<String, Object>> mSumList = DlwCmsService.getWdrwHstrStatsSumByInvDt(hmParam);
                hmParam.put("menu_cd", "0033");

                List<Map<String, Object>> mClList = DlwCondService.getClList(hmParam);
                for (int i = 0; i < mClList.size(); i++) {
                    String cl_nm = (String)mClList.get(i).get("cl_nm");
                    String cl_cd = (String)mClList.get(i).get("cl_cd");

                    hmParam.put("cl_nm", cl_nm);
                    hmParam.put("cl_cd", cl_cd);
                    String condQury = DlwCondService.getInqCondQry(hmParam);
                    hmParam.put("condQury", condQury);

                    List<Map<String, Object>> mTmpList = DlwCmsService.getWdrwHstrStatsCombByInvDt(hmParam);
                    mList.addAll(mTmpList);
                }
                listMap2.setRowMaps(mSumList);
                mapOutDs.put("ds_output2", listMap2);

                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output3", listMap);
            }

            else {
                //세션
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("emple_no", hmParam.get("rgsr_id"));
                hmParam.put("reg_man", hmParam.get("rgsr_id"));
                hmParam.put("upd_man", hmParam.get("rgsr_id"));

                hmParam.put("paramEmpleNo",  hmParam.get("rgsr_id"));
                hmParam.put("paramAs", "empl");
                String dataAthrQury = dataAthrQuryService.getDataAthrQury(hmParam);
                hmParam.put("dataAthrQury", dataAthrQury);

                mList = DlwCmsService.getWdrwHstrByInvDt(hmParam);

                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);

            }

            List<Map<String, Object>> mComnList = DlwCmsService.getDlwCmsComnInfo(hmParam);
            String wdrw_com = (String)mComnList.get(0).get("wdrw_com");
            String wdrw_imps_com = (String)mComnList.get(0).get("wdrw_imps_com");

            mapOutVar.put("wdrwHanAmt", wdrw_com);
            mapOutVar.put("impsHanAmt", wdrw_imps_com);

            //2018.01.08 접속 로그////////////////////////////////////////////////////////////////////////////////
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
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

    /**
     * 대명라이프웨이 출금이체내역 조회를 한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/wdrw-apcl-canc")
    public ModelAndView updateWdrwApclCanc(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap listMap2 = new DataSetMap();
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
            DataSetMap listInDs2 = (DataSetMap)mapInDs.get("ds_input2");
            hmParam = listInDs.get(0);

            String result = "";
            List<Map<String, Object>> mComnList = DlwCmsService.getDlwCmsComnInfo(hmParam);

            ParamUtils.addCenterParam(hmParam);
            String sPayFilePath = basVlService.getBasVlAsString("pay_file_path", (String) hmParam.get("cntr_cd"));
            String sPayFilePathGbn = basVlService.getBasVlAsString("pay_file_path_gbn", (String) hmParam.get("cntr_cd"));

            String wdrw_gubun = CommonUtils.checkNull((String)hmParam.get("wdrw_gubun"));
            if("Card".equals(wdrw_gubun)) {
                DlwCmsService.updateWdrwApclCancCard(hmParam);
                DlwCmsService.deleteNiceCardLog(hmParam);

                File f = new File((new StringBuilder("/app/CARD/NICE/SEND/dmlife001m")).append((String)hmParam.get("mmdd")).append(".txt").toString());

          //      System.out.println(f);
               // File  f = new StringBuilder("dmlife001m").append("22").append(".txt").toString();  // 파일명
                //File f = new File((new StringBuilder("C:\\CARD\\NICE\\SEND\\dmlife001m")).append((String)hmParam.get("mmdd")).append(".txt").toString());
                //File f = new File((new StringBuilder(sPayFilePath)).append(sPayFilePathGbn).append("CARD").append(sPayFilePathGbn).append("NICE").append(sPayFilePathGbn).append("SEND").append(sPayFilePathGbn).append("dmlife001m").append((String)hmParam.get("mmdd")).append(".txt").toString());
/*
인우경로          //File f = new File((new StringBuilder("C:\\CARD\\NICE\\SEND\\dmlife001m")).append((String)hmParam.get("mmdd")).append(".txt").toString());
                File f = new File((new StringBuilder(sPayFilePath)).append(sPayFilePathGbn).append("CARD").append(sPayFilePathGbn).append("NICE").append(sPayFilePathGbn).append("SEND").append(sPayFilePathGbn).append("dmlife001m").append((String)hmParam.get("mmdd")).append(".txt").toString());

*/
                if(f.delete()) {
                    result = "complete";
                } else {
                    result = "error";
                }
            } else {
                DlwCmsService.updateWdrwApclCancCms(hmParam);
                String use_inst_cl_cd = (String)mComnList.get(0).get("use_inst_cl_cd");
               // File f = new File((new StringBuilder("C:\\CMS\\")).append(use_inst_cl_cd).append("\\EB21\\").toString(), (new StringBuilder("EB21")).append(String.valueOf((String)hmParam.get("mmdd"))).toString());
/* 인우경로                //File f = new File((new StringBuilder("C:\\CMS\\")).append(use_inst_cl_cd).append("\\EB21\\").toString(), (new StringBuilder("EB21")).append(String.valueOf((String)hmParam.get("mmdd"))).toString());
*/
                File f = new File((new StringBuilder(sPayFilePath)).append(sPayFilePathGbn).append("CMS").append(sPayFilePathGbn).append(use_inst_cl_cd).append(sPayFilePathGbn).append("EB21").append(sPayFilePathGbn).toString(), (new StringBuilder("EB21")).append(String.valueOf((String)hmParam.get("mmdd"))).toString());
                if(f.delete()) {
                    result = "complete";
                } else {
                    result = "error";
                }
            }

            mapOutVar.put("result", result);

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
     * 대명라이프웨이 EB11 파일변환 파일명을 조회한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/eb11/file-nm-list")
    public ModelAndView getEB11FileNmList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            List<Map<String, Object>> mComnList = DlwCmsService.getDlwCmsComnInfo(hmParam);
            String use_inst_cl_cd = (String)mComnList.get(0).get("use_inst_cl_cd");

            ParamUtils.addCenterParam(hmParam);
            String sPayFilePath = basVlService.getBasVlAsString("pay_file_path", (String) hmParam.get("cntr_cd"));
            String sPayFilePathGbn = basVlService.getBasVlAsString("pay_file_path_gbn", (String) hmParam.get("cntr_cd"));

            File f = new File((new StringBuilder("C:\\CMS\\")).append(use_inst_cl_cd).append("\\").toString(), "EB11");
            //File f = new File((new StringBuilder(sPayFilePath)).append(sPayFilePathGbn).append("CMS").append(sPayFilePathGbn).append(use_inst_cl_cd).append(sPayFilePathGbn).append("EB11").append(sPayFilePathGbn).toString());
            String arr[] = f.list();

            List<Map<String, Object>> mList = new ArrayList<Map<String,Object>>();

            if (arr != null && arr.length > 0) {
                Arrays.sort(arr);

                int j = arr.length - 1;
                for(int i = 0; i<arr.length; i++)
                {
                    Map<String, Object> tmpParam = new HashMap<String, Object>();
                    tmpParam.put("label", arr[j]);
                    tmpParam.put("data", arr[j]);
                    mList.add(i, tmpParam);
                    j--;
                }
            }
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
     * 대명라이프웨이  EB11 파일 결과처리 여부 조회한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/eb11/read-file")
    public ModelAndView getEB11ReadFile(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            hmParam.put("file_nm", mapInVar.get("file_nm"));

            int wdrwCnt = 0;
            int wdrwCallCnt = 0;
            List<Map<String, Object>> mComnList = DlwCmsService.getDlwCmsComnInfo(hmParam);
            String use_inst_cl_cd = (String)mComnList.get(0).get("use_inst_cl_cd");

            ParamUtils.addCenterParam(hmParam);
            String sPayFilePath = basVlService.getBasVlAsString("pay_file_path", (String) hmParam.get("cntr_cd"));
            String sPayFilePathGbn = basVlService.getBasVlAsString("pay_file_path_gbn", (String) hmParam.get("cntr_cd"));

            //String filePath_N_Name = (new StringBuilder("C:\\CMS\\")).append(use_inst_cl_cd).append("\\EB11\\").append(String.valueOf(hmParam.get("file_nm"))).toString();
            String filePath_N_Name = (new StringBuilder(sPayFilePath)).append(sPayFilePathGbn).append("CMS").append(sPayFilePathGbn).append(use_inst_cl_cd).append(sPayFilePathGbn).append("EB11").append(sPayFilePathGbn).append(String.valueOf(hmParam.get("file_nm"))).toString();

            System.out.println("filePath_N_Name ="+filePath_N_Name);

            BufferedReader br = new BufferedReader(new FileReader(filePath_N_Name));
            String fullData = br.readLine();
            int recordCnt = fullData.length() / 120 - 2;
            int newCnt = Integer.parseInt(fullData.substring(fullData.length() - 85, fullData.length() - 77));
            int canCnt = Integer.parseInt(fullData.substring(fullData.length() - 69, fullData.length() - 61));
            int can7Cnt = Integer.parseInt(fullData.substring(fullData.length() - 61, fullData.length() - 53));
            Vector vecRecord = new Vector();
            List<Map<String, Object>> mList = new ArrayList<Map<String,Object>>();
            for(int i = 1; recordCnt >= i; i++)
            {
                int oneRecordStartIdx = i * 120;
                int oneRecordEndIdx = oneRecordStartIdx + 120;
                vecRecord.addElement(fullData.substring(oneRecordStartIdx, oneRecordEndIdx));
            }

            for(int i = 0; vecRecord.size() > i; i++)
            {
                String currRecord = (String)vecRecord.get(i);
                HashMap enti = new HashMap();
                if(currRecord.substring(26, 46).trim().length() == 6 || currRecord.substring(26, 46).trim().length() == 12)
                {
                    enti.put("app_cl", currRecord.substring(25, 26));
                    enti.put("accnt_no", currRecord.substring(26, 46).trim());
                    enti.put("bank_cd", currRecord.substring(46, 49));
                    enti.put("bank_accnt_no", currRecord.substring(53, 69).trim());
                    enti.put("idn_no", currRecord.substring(69, 85).trim());
                    enti.put("area_cd", currRecord.substring(85, 89).trim());
                    String result = "";
                    if("1".equals((String)enti.get("app_cl"))) {
                        mList.add(enti);
                    } else {
                        hmParam.put("accnt_no", currRecord.substring(26, 46).trim());
                        List<Map<String, Object>> mWdrwChkList = DlwCmsService.getDlwCmsWdrwChk(hmParam);
                        if (mList != null && mList.size() > 0) {
                            result = (String)mList.get(0).get("cms_wdrw");
                        }
                        enti.put("wdrw_chk", result);
                        mList.add(enti);
                        if(!"".equals(result)) {
                            if("CMS".equals(result)) {
                                wdrwCnt++;
                            } else if ("콜센터".equals(result)) {
                                wdrwCallCnt++;
                            }
                        }
                    }
                }
            }

            String result_msg = "";
            if(wdrwCnt > 0 || wdrwCallCnt > 0) {
                result_msg = (new StringBuilder("CMS산출중인 건 :")).append(wdrwCnt).append(", 콜센터 등록건 :").append(wdrwCallCnt).toString();
            } else {
                result_msg = "";
            }
            mapOutVar.put("newCnt", newCnt);
            mapOutVar.put("canCnt", canCnt);
            mapOutVar.put("can7Cnt", can7Cnt);

            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            //2018.01.11 접속 로그////////////////////////////////////////////////////////////////////////////////
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
    @RequestMapping(value = "/eb11/convert-file")
    public ModelAndView updateConvertFileEB11(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            DataSetMap arrEB11 = (DataSetMap)mapInDs.get("ds_input");

            String mmdd = (String)mapInVar.get("mmdd");

            hmParam.put("list", arrEB11);

            //세션
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("emple_no", hmParam.get("rgsr_id"));
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            hmParam.put("upd_man", hmParam.get("rgsr_id"));
            hmParam.put("mmdd", mmdd);
            /********************************************************************************************************/
            List<Map<String, Object>> mList = new ArrayList<Map<String,Object>>();
            List<Map<String, Object>> arrImpsCdList = new ArrayList();

            StringBuffer strbuf = new StringBuffer();
            BufferedWriter out = null;

            arrImpsCdList = DlwCmsService.updateConvertFileEB11(hmParam);

            List<Map<String, Object>> mComnList = DlwCmsService.getDlwCmsComnInfo(hmParam);

            String useInstCd = (String)mComnList.get(0).get("use_inst_cl_cd");
            String filePath = (String)mComnList.get(0).get("file_path");
            String fileName = (new StringBuilder("EB12")).append(mmdd).toString();
            String yy = "";

            int appCnt = 0;
            int canCnt = 0;

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy", Locale.KOREA);
            Date currentTime = new Date();
            String yyyy = formatter.format(currentTime);
            yy = yyyy.substring(2, 4);

            try {
                String s = "";
                s = (new StringBuilder("H00000000")).append(useInstCd).append(fileName).append(yy).append(mmdd).append(CommonUtils.fillEmpVal(87, "", "L", " ")).toString();
                strbuf.append(s);
                for(int i = 0; arrImpsCdList.size() > i; i++)
                {
                    HashMap temp = (HashMap)arrImpsCdList.get(i);
                    s = (new StringBuilder("R")).append(CommonUtils.fillEmpVal(8, String.valueOf(i + 1), "R", "0")).append(useInstCd).append(yy).append(mmdd).append((String)temp.get("app_cl")).append(CommonUtils.fillEmpVal(20, (String)temp.get("accnt_no"), "L", " ")).append(CommonUtils.fillEmpVal(7, (String)temp.get("bank_cd"), "L", "0")).append(CommonUtils.fillEmpVal(16, (String)temp.get("bank_accnt_no"), "L", " ")).append(CommonUtils.fillEmpVal(16, (String)temp.get("idn_no"), "L", " ")).append(CommonUtils.fillEmpVal(4, (String)temp.get("area_cd"), "L", " ")).append(CommonUtils.fillEmpVal(2, "", "L", " ")).append("N").append((String)temp.get("imps_cd")).append(CommonUtils.fillEmpVal(1, "", "L", " ")).append(CommonUtils.fillEmpVal(12, "", "L", " ")).append(CommonUtils.fillEmpVal(11, "", "L", " ")).toString();
                    strbuf.append(s);
                }

                s = (new StringBuilder("T99999999")).append(useInstCd).append(fileName).append(CommonUtils.fillEmpVal(8, String.valueOf(arrImpsCdList.size()), "R", "0")).append(CommonUtils.fillEmpVal(8, String.valueOf(1), "R", "0")).append(CommonUtils.fillEmpVal(8, String.valueOf(0), "R", "0")).append(CommonUtils.fillEmpVal(8, String.valueOf(15), "R", "0")).append(CommonUtils.fillEmpVal(8, String.valueOf(0), "R", "0")).append(CommonUtils.fillEmpVal(43, "", "L", " ")).append(CommonUtils.fillEmpVal(10, "", "R", " ")).toString();
                strbuf.append(s);

                ParamUtils.addCenterParam(hmParam);
                String sPayFilePath = basVlService.getBasVlAsString("pay_file_path", (String) hmParam.get("cntr_cd"));
                String sPayFilePathGbn = basVlService.getBasVlAsString("pay_file_path_gbn", (String) hmParam.get("cntr_cd"));

                //File f = new File((new StringBuilder("C:\\CMS\\")).append(useInstCd).append("\\EB12\\").toString(), fileName);
                File f = new File((new StringBuilder(sPayFilePath)).append(sPayFilePathGbn).append("CMS").append(sPayFilePathGbn).append(useInstCd).append(sPayFilePathGbn).append("EB12").append(sPayFilePathGbn).toString(), fileName);
                f.createNewFile();

                out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), "EUC-KR"));
                out.write(strbuf.toString());

                //File fromFile = new File((new StringBuilder("C:\\CMS\\")).append(useInstCd).append("\\EB11\\").toString(), (new StringBuilder("EB11")).append(mmdd).toString());
                File fromFile = new File((new StringBuilder(sPayFilePath)).append(sPayFilePathGbn).append("CMS").append(sPayFilePathGbn).append(useInstCd).append(sPayFilePathGbn).append("EB11").append(sPayFilePathGbn).toString(), (new StringBuilder("EB11")).append(mmdd).toString());
                //File toFile = new File((new StringBuilder("C:\\CMS\\")).append(useInstCd).append("\\EB11\\").toString(), (new StringBuilder("EB11")).append(mmdd).append("_").toString());
                File toFile = new File((new StringBuilder(sPayFilePath)).append(sPayFilePathGbn).append("CMS").append(sPayFilePathGbn).append(useInstCd).append(sPayFilePathGbn).append("EB11").append(sPayFilePathGbn).toString(), (new StringBuilder("EB11")).append(mmdd).append("_").toString());
                boolean exist = fromFile.exists();
                boolean exist2 = toFile.exists();
                boolean success = fromFile.renameTo(toFile);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
              out.close();
            }

            /********************************************************************************************************/
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
    @RequestMapping(value = "/cms-info")
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

            List<Map<String, Object>> mList = DlwCmsService.getCMSInfoByAccnt(hmParam);
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
     * 상담 정보를 등록/수정한다. (대명 - CMS/카드 부가서비스 등록시)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/save-cons")
    public ModelAndView saveConsDlwBugaRgsn(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input"); 		// 상담정보

            if (listInDs.size() > 0) {
                Map <String, Object> hmParam = listInDs.get(0);

                Map<String, Object> mCust = dlwCustService.getCustBasiConsInfo(hmParam);

                //회원정보 없을경우 상담저장 안함
                if (mCust != null && mCust.size() > 0) {
                    String pyin_chng_yn = CommonUtils.checkNull((String)hmParam.get("pyin_chng_yn"));
                    if ("".equals(pyin_chng_yn)) {
                        hmParam.put("pyin_chng_yn", "Y"); //납입수단변경
                    } else {
                        hmParam.put("pyin_chng_yn", "N"); //기타
                    }

                    consController.saveConsdlw(hmParam);
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
     * 대명라이프웨이 산출내역 삭제
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/eb21/delete")
    public ModelAndView deleteWdrwEb21(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            Map<String, Object> hmParam = new HashMap<String, Object>();
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

            for (int i = 0; i < listInDs.size(); i++) {
                hmParam = listInDs.get(i);
                //세션
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("emple_no", hmParam.get("rgsr_id"));
                hmParam.put("reg_man", hmParam.get("rgsr_id"));
                hmParam.put("upd_man", hmParam.get("rgsr_id"));

                hmParam.put("wdrw_gubun", mapInVar.get("wdrw_gubun"));

                int rowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

                if (rowType == DataSet.ROW_TYPE_UPDATED) {
                    if(!"Card".equals(hmParam.get("wdrw_gubun"))) {
                        DlwCmsService.updateWdrwReqYn1(hmParam);
                        DlwCmsService.deleteWDRWHistory(hmParam);
                    } else {
                        DlwCmsService.updateWdrwReqYn2(hmParam);
                        DlwCmsService.deleteCardWdrw(hmParam);
                    }
                }
            }

            //2018.01.25 접속 로그////////////////////////////////////////////////////////////////////////////////
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
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }
/*
    * 대명라이프웨이 산출내역 일괄삭제
    *
    * @param pmParam Map<String, Object>
    * @return ModelAndView
    * @throws Exception
    */
   @RequestMapping(value = "/eb21/deleteall")
   public ModelAndView deleteWdrwEb21all(XPlatformMapDTO xpDto, Model model) throws Exception {
       ModelAndView modelAndView = new ModelAndView("xplatformMapView");

       // 에러코드및 메시지
       String szErrorCode = "0";
       String szErrorMsg  = "OK";

       try {
           Map <String, Object> mapInVar     = xpDto.getInVariableMap();
           Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
           Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
           Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

           Map<String, Object> hmParam = new HashMap<String, Object>();
           DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

          // hmParam = listInDs.get(0);



           hmParam.put("delete", "Y");
           hmParam.put("gubun",  mapInVar.get("wdrw_gubun"));
           /// 고객산출 부분 원상복귀

           DlwCmsService.updateWdrwReqYn1all(hmParam);


           ///  산출삭제
           DlwCmsService.deleteWDRWHistoryall(hmParam);

           //2018.01.05 접속 로그////////////////////////////////////////////////////////////////////////////////
           DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

           if (listLogIn.size() > 0) {
               hmParam = listLogIn.get(0);
               commonService.insertLog(hmParam);
           }
           //////////////////////////////////////////////////////////////////////////////////////////////////////

         /*  for (int i = 0; i < listInDs.size(); i++) {
               hmParam = listInDs.get(i);
               //세션
               ParamUtils.addSaveParam(hmParam);
               hmParam.put("emple_no", hmParam.get("rgsr_id"));
               hmParam.put("reg_man", hmParam.get("rgsr_id"));
               hmParam.put("upd_man", hmParam.get("rgsr_id"));

               hmParam.put("wdrw_gubun",);

               int rowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

               if (rowType == DataSet.ROW_TYPE_UPDATED) {
                   if(!"Card".equals(hmParam.get("wdrw_gubun"))) {
                       DlwCmsService.updateWdrwReqYn1(hmParam);
                       DlwCmsService.deleteWDRWHistory(hmParam);
                   } else {
                       DlwCmsService.updateWdrwReqYn2(hmParam);
                       DlwCmsService.deleteCardWdrw(hmParam);
                   }
               }
           }
*/
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
     * 대명라이프웨이 카드 산출 산출내역 파일생성
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/create-file/nice")
    public ModelAndView createFileNice(XPlatformMapDTO xpDto, Model model) throws Exception {
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
           /* ************************************************************************************** */

            ArrayList arr;
            String totalAmt;
            String useInstCd;
            String yymmdd;
            String invDt;
            String fileName;
            StringBuilder strbuf;
            BufferedWriter out;

            Map<String, Object> hmParam = new HashMap<String, Object>();

            List<Map<String, Object>> mComnList = DlwCmsService.getDlwCmsComnInfo(hmParam);
            Map<String, Object> comnMap = mComnList.get(0);
            String mon_wdrw_limit = (String)comnMap.get("mon_wdrw_limit");
            useInstCd = (String)comnMap.get("use_inst_cl_cd");
            String bankFillIn = (String)comnMap.get("bank_filin_brkdn");

            List<Map<String, Object>> mComnUserList = DlwCmsService.getDlwComnInfo(hmParam);
            Map<String, Object> comnUserMap = mComnUserList.get(0);
            //uservo...
            String filePath = (String)comnUserMap.get("card_file_path");
            String card_terminl_no = (String)comnUserMap.get("card_terminl_no");

            hmParam.put("emple_no", (String)mapInVar.get("total_amt"));
            hmParam.put("reg_man", (String)mapInVar.get("yymmdd"));
            hmParam.put("upd_man", (String)mapInVar.get("inv_dt"));
            Map<String, Object> pmParam = new HashMap<String, Object>();
            List<Map<String, Object>> mList = DlwCardService.getCardWdrwReqList1(hmParam);
            String billGubun = "00";

            totalAmt = (String)mapInVar.get("total_amt");
            yymmdd = (String)mapInVar.get("yymmdd");
            invDt = (String)mapInVar.get("inv_dt");


            Calendar cal = Calendar.getInstance();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            String today = formatter.format(cal.getTime());

            /* 20180730 송준빈
             * NICE 출금이체의뢰내역 파일산출 - 파일전송 클릭시 해당 SFTP에 전송하는 기능 추가
             * ip:121.133.126.8  port:22 dmlife000g / eoaudqlffld1!
             *
             *
             * */
            //String mid = "dmlife001m";        // id
            //fileName =(new StringBuilder(mid)).append(yymmdd).append(".txt").toString();  // 파일명

            String mid = "dmlife000g";          // id가 dmlife001m 에서 dmlife000g 로 변경되었음.
            fileName = (new StringBuilder(mid)).append("_").append(today).append(".REQ").toString(); // 파일 이름체계가 dmlife001m20180801.txt 에서 dmlife000g_20180724.REQ 로 변경되었음.

            File f = new File((new StringBuilder("/app/CARD/NICE/SEND/")).toString(), fileName);
            //File f = new File((new StringBuilder("c://app/CARD/NICE/SEND/")).toString(), fileName);

            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), "EUC-KR"));
            FileInputStream fis = null;
            BufferedInputStream bis = null;

            JSch jsch = null;
            Session session = null;
            Channel channel = null;
            ChannelSftp channelSftp = null;


            strbuf = new StringBuilder();
            strbuf.append("H").append(",").append(today).append(",").append(mList.size()); // 파일의 1번째행은 헤더로서 "H,YYYYMMDD,건수" 의 형태를 가진다
            strbuf.append("\r\n");

            out.write(strbuf.toString());

            strbuf.delete(0, strbuf.length());
            //\app\CMS\9993083157\EB21\EB211123
            //CommonUtils.printLog(mList);

            try
            {

                String s = "";
            /*  //로그 저장 (신청중)
            DlwCardService.insertRTCardPayLog(hmParam);*/

                for(int i = 0; mList.size() > i; i++)
                {

                    pmParam = mList.get(i);
                   // CommonUtils.printLog(pmParam);

                     //임동진 수정 (거래구분 설정)
                    String prodCd = (String) pmParam.get("prod_cd");

                    if( "M2".equals(prodCd) || "F4".equals(prodCd) ){
                        if(Integer.parseInt(String.valueOf(pmParam.get("inv_no"))) < 36){
                            billGubun ="11";
                        }else if(Integer.parseInt(String.valueOf(pmParam.get("inv_no"))) == 36){
                            billGubun ="12";
                        }else{
                            billGubun ="00";
                        }
                    }else if("AO".equals(prodCd)){
                        if(Integer.parseInt(String.valueOf(pmParam.get("inv_no"))) <= 36){
                            billGubun ="21";
                        }else{
                            billGubun ="00";
                        }
                    }
                    //2017.09.19 CO,CP 추가
                    else if("CO".equals(prodCd))
                    {
                        if(Integer.parseInt(String.valueOf(pmParam.get("inv_no"))) <= 24){
                            billGubun ="26";
                        }else{
                            billGubun ="00";
                        }
                    }
                    else if("CP".equals(prodCd))
                    {
                        if(Integer.parseInt(String.valueOf(pmParam.get("inv_no"))) <= 24){
                            billGubun ="27";
                        }else{
                            billGubun ="00";
                        }
                    }
                    //2018.05.02 SDP추가
                    else if( "X5".equals(prodCd) || "S7".equals(prodCd) || "S8".equals(prodCd) || "EC".equals(prodCd) || "EE".equals(prodCd))
                    {
                        if(Integer.parseInt(String.valueOf(pmParam.get("inv_no"))) <= 36){
                            billGubun ="999";
                        }else{
                            billGubun ="000";
                        }
                    }
                    //2018.05.31 SDP추가
                    else if( "CM".equals(prodCd) || "EG".equals(prodCd) )
                    {
                        if(Integer.parseInt(String.valueOf(pmParam.get("inv_no"))) <= 24){
                            billGubun ="999";
                        }else{
                            billGubun ="000";
                        }
                    }
                    else{
                        billGubun ="00";
                    }
                    pmParam.put("billGubun", billGubun);

                    /*파일 작성 */


//                    s = (new StringBuilder(CommonUtils.fillEmpVal(6, String.valueOf(i + 1)+",", "R", "0")))
//                            .append(CommonUtils.fillEmpVal(30, (String)pmParam.get("bid")+",", "L", " "))
//                            .append((String)pmParam.get("prod_nm")+",")
//                            .append(CommonUtils.stringValueOf(pmParam.get("wdrw_req_amt"))+",")
//                            .append((String)pmParam.get("accnt_no")+",")
//                            .append((String)pmParam.get("MEM_NM")+",")
//                            .append((String)pmParam.get("billGubun")).toString();
//                    strbuf.append(s);

                    strbuf.append(CommonUtils.fillEmpVal(8, String.valueOf(i + 1), "R", "0")+",");
                    strbuf.append(CommonUtils.fillEmpVal(30, (String)pmParam.get("bid"), "L", " ")+",");
                    strbuf.append((String)pmParam.get("prod_nm")+",");
                    strbuf.append(CommonUtils.stringValueOf(pmParam.get("wdrw_req_amt"))+",");
                    strbuf.append((String)pmParam.get("accnt_no")+",");
                    strbuf.append((String)pmParam.get("MEM_NM")+",");
                    strbuf.append((String)pmParam.get("billGubun")+",");
                    strbuf.append(invDt);
                    strbuf.append("\r\n");

                    out.write(strbuf.toString());

                    strbuf.delete(0, strbuf.length());

                    if(i % 100 == 0)
                    {
                    	out.flush();
                    }
                }

                out.close();

                ParamUtils.addCenterParam(hmParam);
                String sPayFilePath = basVlService.getBasVlAsString("pay_file_path", (String) hmParam.get("cntr_cd"));
                String sPayFilePathGbn = basVlService.getBasVlAsString("pay_file_path_gbn", (String) hmParam.get("cntr_cd"));

                //   s = (new StringBuilder("T")).append(CommonUtils.fillEmpVal(8, String.valueOf(mList.size()), "R", "0")).append(CommonUtils.fillEmpVal(8, "", "R", "0")).append(CommonUtils.fillEmpVal(108, "", "L", " ")).toString();
                //   strbuf.append(s);
                //   strbuf.append("\r\n");

                 //  File f = new File((new StringBuilder("/app/CARD/NICE/SEND/")).toString(), fileName);


                   //filePath = filePath.replaceAll("\\", sPayFilePathGbn);
                  // File f = new File((new StringBuilder(sPayFilePath)).append(sPayFilePathGbn).append("CARD").append(sPayFilePathGbn).append(useInstCd).append(filePath).append(sPayFilePathGbn).append("SEND").toString(), fileName);

                /* 20180730 송준빈
                 * NICE 출금이체의뢰내역 파일산출 - 파일전송 클릭시 해당 SFTP에 전송하는 기능 추가
                 * ip:121.133.126.8  port:22 dmlife000g / eoaudqlffld1!
                 * */

                /* NICEPAYMENT에서 자료를 처리한후 저장한 자료를 조회. START !!! (이로직는 FTP 접속시 사용) */
                /*
                FileInputStream fis = null;
                FTPClient ftpClient = null;

                try
                {
                	ftpClient = new FTPClient();

                    //ftpClient.connect("192.168.15.6", 21);                      // 테스트용 NICEPAYMENT SFTP 서버의 ip와 포트
                    ftpClient.connect("121.133.126.8", 22);                       // 실제 NICEPAYMENT SFTP 서버의 ip와 포트

                    //boolean bLogin = ftpClient.login("DMDLCCFTP", "31206love"); // 테스트용 FTP 서버로 로그인한다.
                    boolean bLogin = ftpClient.login("dmlife000g", "skdltmqlf1"); // 실제 FTP 서버로 로그인한다.

                    //ftpClient.changeWorkingDirectory("/CARD/NICE/SEND/");       // 테스트용 파일을 받는 FTP 상의 경로
                    ftpClient.changeWorkingDirectory("/home/dmlife000g/send"); // 실제 파일을 받는 FTP 상의 경로

                    //ftpClient.enterLocalPassiveMode();                        // FTP 전송 모드를 패시브 모드로 한다.
                    int iReply = ftpClient.getReplyCode();                      // FTP의 응답상태.

                    if(FTPReply.isPositiveCompletion(iReply) == true)           // FTP의 응답 상태가 정상이라면
                    {
                    	System.out.println("NICEPAYMENT SFTP에 연결되었습니다.");
                    	ftpClient.setSoTimeout(30000);                          // timeout 시간을 30초로 부여함.

                    	if(bLogin == true)
                    	{
                    		fis = new FileInputStream(f);
                    		//String sUploadFileName = "/CARD/NICE/SEND/" + f.getName();

                    		System.out.println("본래 파일의 경로" + f.getName());
                    		boolean bResult = ftpClient.storeFile(f.getName(), fis);  // 해당 경로에 있는 파일을 파일을 받는 FTP 상의 경로로 보낸다.
                    		if(bResult == true)
                    		{
                    			System.out.println("NICEPAYMENT SFTP로 파일 전송이 성공하였습니다. 파일의 원 경로는 : " + f.getName() + " 이고" );
                    			System.out.println("받는 곳은 FTP 상의 /CARD/NICE/SEND/ 입니다.");
                    		}
                    		else
                    		{
                    			System.out.println("NICEPAYMENT SFTP로 파일 전송이 실패하였습니다." );
                    		}
                    	}
                    	else
                    	{
                    		System.out.println("ID, Password가 다릅니다.");
                    	}
                    }
                    else
                    {
                    	System.out.println("NICEPAYMENT SFTP에 연결할수 없습니다.");
                    }
                }
                catch(IOException ioe)
                {
                	fis.close();
                	ftpClient.logout();
                	ftpClient.disconnect();
                }
                finally
                {
                	fis.close();
                	ftpClient.logout();
                	ftpClient.disconnect();
                }
                */
                /* NICEPAYMENT에서 자료를 처리한후 저장한 자료를 조회. END !!! */

                /* NICEPAYMENT SFTP 에서 자료를 처리한후 저장한 자료를 조회. START !!! (이 로직은 SFTP 접속시 사용) */
            	jsch = new JSch();

            	session = jsch.getSession(basVlService.getBasVlAsString("nice_ftp_access_id"), "121.133.126.8", 22);
            	session.setPassword(basVlService.getBasVlAsString("nice_ftp_access_pw"));
            	
            	System.out.println("basVlService.getBasVlAsString(nice_ftp_access_id) ::: " + basVlService.getBasVlAsString("nice_ftp_access_id") + 
                                   "basVlService.getBasVlAsString(nice_ftp_access_pw) ::: " + basVlService.getBasVlAsString("nice_ftp_access_pw")); 

                java.util.Properties config = new java.util.Properties();

                config.put("StrictHostKeyChecking", "no");
                session.setConfig(config);

                session.connect();

                // 6. sftp 채널을 연다.
                channel = session.openChannel("sftp");
                channel.connect();

                channelSftp = (ChannelSftp)channel;
                //channelSftp.connect();

                //디렉토리 접근
                channelSftp.cd("/home/dmlife000g/send");
                //channelSftp.cd("/app/CARD/NICE/SEND/");

                //디렉토리 안의 목록 조회
                fis = new FileInputStream(f);
                bis = new BufferedInputStream(fis, 1024*4);
                channelSftp.put(bis,f.getName());
                /* NICEPAYMENT SFTP 에서 자료를 처리한후 저장한 자료를 조회. END !!! */

            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            finally
            {
                if(out != null)
                {
                	out.close();
                }
                if(fis != null)
                {
                	fis.close();
                }
                if(bis != null)
                {
                	bis.close();
                }
                if(channelSftp != null)
                {
                	channelSftp.disconnect();
                }
                if(channel != null)
                {
                	channel.disconnect();
                }
                if(session != null)
                {
                	session.disconnect();
                }
            }

            //세션
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("emple_no", hmParam.get("rgsr_id"));
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            hmParam.put("upd_man", hmParam.get("rgsr_id"));

        //    System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");

            hmParam.put("yymmdd", yymmdd);

            DlwCardService.updateCardAppStateLrqnt(hmParam); //운영테스트 종료시 여기를 해제
            DlwCardService.insertRTCardPayLogLrqnt(hmParam); //운영테스트 종료시 여기를 해제

            //hmParam.put("inv_dt", invDt);
            //DlwCmsService.updateCmsAppStateLrqnt(hmParam);
           /* ************************************************************************************** */

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
     * 대명스테이션 NICEPAYMENT FTP File 전송리스트 및 수신리스트
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     * 접속정보 >  ip:121.133.126.8  port:22
	 * 계정&비번 > dmlife000g / eoaudqlffld1!
     */
    @RequestMapping(value = "/nicepayment/sendrecvlist")
    public ModelAndView sendFile(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        /**********************************************************************************
         * 기본적인 변수들 선언
         * ********************************************************************************/
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> mapInVar = xpDto.getInVariableMap();     // 화면에서 받은 값
        Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();   // 화면에서 보내온 DS
        Map<String, Object> mapOutVar = xpDto.getOutVariableMap();   // 화면에 보낼 값
        Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap(); // 화면에 보낼 DS

        Map<String, Object> hmParam = new HashMap<String, Object>(); // DS에서 한건씩 꺼내서 쓸 맵
        List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();

        // 현재 날짜
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");

        // 조회월을 받음.
        String stt_dt = (String) mapInVar.get("stt_dt").toString().substring(0,6);
        hmParam.put("charge_dt", stt_dt); // 송신월
        System.out.println("송신월 ::: " + stt_dt);

        // 세션
        //ParamUtils.addSaveParam(hmParam);
        //String reg_man = (String) hmParam.get("rgsr_id"); // 현재 세션 접속자 사번

        // 연결될 FTP 주소및 포트
        //FTPClient ftpClient = null;
        Session session = null;
    	JSch jsch = null;
    	Channel channel = null;
    	ChannelSftp channelSftp = null;

        //String sNicePaymentFtpIp =  "192.168.15.6"; // 테스트용 ip
        //int iNicePaymentFtpPort = 21;               // 테스트용 port
        //String sFTPLoginId = "DMDLCCFTP";           // 테스트용 접속 Id
        //String sFTPLoginPw = "31206love";           // 테스트용 접속 PassWord

        String sNicePaymentFtpIp = "121.133.126.8";   // 실제 ip
        int iNicePaymentFtpPort = 22;                 // 실제 port
        String sFTPLoginId = basVlService.getBasVlAsString("nice_ftp_access_id"); // 실제 접속 Id (dmlife000g)
        String sFTPLoginPw = basVlService.getBasVlAsString("nice_ftp_access_pw"); // 실제 접속 PassWord (eoaudqlffld1!)

        System.out.println("sFTPLoginId ::: " + sFTPLoginId + " sFTPLoginPw ::: " + sFTPLoginPw);
        // 저장할 파일 위치
        //String sSendFileDirectoryPath = "C://app/CARD/NICE/SEND/"; // 로컬테스트용
        //String sSendFileDirectoryPath = "/app/CARD/NICE/SEND/";
        String sSendFileDirectoryPath = "/home/dmlife000g/send";

        //String sFTPReceiveDirectoryPath = "/CARD/NICE/SEND_OK/";      // 로컬 테스트 FTP상의 Path
        String sFTPReceiveDirectoryPath = "/home/dmlife000g/send_ok"; // 개발/운영서버테스트용

        BufferedReader br = null;

        InputStreamReader isr = null;
    	BufferedReader ftpBr = null;

        //File sendFileDirectory = new File(sSendFileDirectoryPath);
        //String[] sendFileList = sendFileDirectory.list();
        try
        {
        	/* 대명스테이션 -> NICEPAYMENT 로 보낸 자료를 조회한뒤 정보를 조회 */
        	/*
        	for(int idx = 0; idx < sendFileList.length; idx++)
            {
        		if(sendFileList[idx].indexOf(stt_dt) > 0)
        		{
        			Map<String,Object> map = new HashMap<String,Object>();
        			File singleListFile = new File(sSendFileDirectoryPath + sendFileList[idx]);
        			br = new BufferedReader(new FileReader(singleListFile));
        			String line = null;
        			String sSendDate = null;
        			String sTotalCnt = null;

        			if((line = br.readLine()) != null)
        			{
        				sSendDate = line.split(",")[1];
        				sTotalCnt = line.split(",")[2];
        				System.out.println("읽은 데이터 일람 ::: " + line);
        			}

        			map.put("file_name", sendFileList[idx]);
        			map.put("count", sTotalCnt);
        			map.put("date", sSendDate);
        			map.put("file_type", "1");
        			map.put("recv_yn", "N");

        			mList.add(map);
        		}
            }


        	System.out.println("로컬 서버에 있는 데이터 일람 : " + mList.toString());
        	*/

        	/* NICEPAYMENT SFTP 에서 자료를 처리한후 저장한 자료를 조회. START !!! (이 로직은 SFTP 접속시 사용) */
        	jsch = new JSch();

        	session = jsch.getSession(sFTPLoginId, sNicePaymentFtpIp, iNicePaymentFtpPort);
        	session.setPassword(sFTPLoginPw);

            java.util.Properties config = new java.util.Properties();

            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            session.connect();

            // 6. sftp 채널을 연다.
            channel = session.openChannel("sftp");

            channel.connect();
            channelSftp = (ChannelSftp)channel;
            //channelSftp.connect();

            //수신 디렉토리 접근
            channelSftp.cd(sSendFileDirectoryPath);

            //디렉토리 안의 목록 조회
            Vector<ChannelSftp.LsEntry> sendFileList = channelSftp.ls(sSendFileDirectoryPath);

            for(ChannelSftp.LsEntry entry : sendFileList)
            {
            	String singleListFile = null;
            	String line = null;
            	String sRecvDate = null;
            	String sTotalCnt = null;

            	System.out.println(entry.getFilename());
                //if(!entry.getFilename().equals(".") && !entry.getFilename().equals("..") && entry.getFilename().indexOf(".") != 0)

            	if(entry.getFilename().indexOf(stt_dt) > 0 && !entry.getFilename().equals(".") && !entry.getFilename().equals("..") && entry.getFilename().indexOf(".") != 0)
                {
                	System.out.println("조건에 맞는 파일명은 다음과 같습니다.(보낸파일) : " + entry.getFilename());

                	Map<String,Object> map = new HashMap<String,Object>();
                	ftpBr = new BufferedReader(new InputStreamReader(channelSftp.get(entry.getFilename())));

                	if((line = ftpBr.readLine()) != null)
            		{
            			sRecvDate = line.split(",")[1];
    					sTotalCnt = line.split(",")[2];
    					System.out.println("읽은 데이터 일람 ::: " + line);
            		}

            		map.put("file_name", entry.getFilename());
            		map.put("count", sTotalCnt);
            		map.put("date", sRecvDate);
            		map.put("file_type", "1");
            		map.put("recv_yn", "N");
            		mList.add(map);

            		ftpBr.close();
            		//isr.close();
                }
            	else
            	{
            		System.out.println("조건에 맞지 않습니다.");
            	}
            }

            channelSftp.cd(sFTPReceiveDirectoryPath);
            Vector<ChannelSftp.LsEntry> recvFileList = channelSftp.ls(sFTPReceiveDirectoryPath);

            for(ChannelSftp.LsEntry entry : recvFileList)
            {
            	String singleListFile = null;
            	String line = null;
            	String sRecvDate = null;
            	String sTotalCnt = null;

            	System.out.println(entry.getFilename());
                //if(!entry.getFilename().equals(".") && !entry.getFilename().equals("..") && entry.getFilename().indexOf(".") != 0)

            	if(entry.getFilename().indexOf(stt_dt) > 0 && !entry.getFilename().equals(".") && !entry.getFilename().equals("..") && entry.getFilename().indexOf(".") != 0)
                {
                	System.out.println("조건에 맞는 파일명은 다음과 같습니다.(받은파일) : " + entry.getFilename());

                	Map<String,Object> map = new HashMap<String,Object>();
                	ftpBr = new BufferedReader(new InputStreamReader(channelSftp.get(entry.getFilename())));

                	if((line = ftpBr.readLine()) != null)
            		{
            			sRecvDate = line.split(",")[1];
    					sTotalCnt = line.split(",")[2];
    					System.out.println("읽은 데이터 일람 ::: " + line);
            		}

            		map.put("file_name", entry.getFilename());
            		map.put("count", sTotalCnt);
            		map.put("date", sRecvDate);
            		map.put("file_type", "2");
            		map.put("recv_yn", "N");
            		mList.add(map);

            		ftpBr.close();
            		//isr.close();
                }
            	else
            	{
            		System.out.println("조건에 맞지 않습니다.");
            	}
            }

            /* NICEPAYMENT SFTP 에서 자료를 처리한후 저장한 자료를 조회. END !!! */

            System.out.println(mList.toString());
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);
        }
        catch(IOException ioe)
        {
        	ioe.printStackTrace();
        }
        finally
        {
        	if(br != null)
        	{
        		br.close();
        	}
        	if(isr != null)
        	{
        		isr.close();
        	}
        	if(ftpBr != null)
        	{
        		ftpBr.close();
        	}
        	if(channelSftp != null)
        	{
        		channelSftp.disconnect();
        	}
        	if(channel != null)
        	{
        		channel.disconnect();
        	}
        	if(session != null)
        	{
        		session.disconnect();
        	}
        }

        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }
    
    /**
     * 대명스테이션 NICEPAYMENT FTP File 전송리스트 및 수신리스트
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     * 접속정보 >  ip:121.133.126.8  port:22
	 * 계정&비번 > dmlife000g / eoaudqlffld1!
     */
    @RequestMapping(value = "/nicepayment/sendnauthrecvlist")
    public ModelAndView sendnauthrecvlist(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        /**********************************************************************************
         * 기본적인 변수들 선언
         * ********************************************************************************/
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> mapInVar = xpDto.getInVariableMap();     // 화면에서 받은 값
        Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();   // 화면에서 보내온 DS
        Map<String, Object> mapOutVar = xpDto.getOutVariableMap();   // 화면에 보낼 값
        Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap(); // 화면에 보낼 DS

        Map<String, Object> hmParam = new HashMap<String, Object>(); // DS에서 한건씩 꺼내서 쓸 맵
        List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();

        // 현재 날짜
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");

        // 조회월을 받음.
        String stt_dt = (String) mapInVar.get("stt_dt").toString().substring(0,6);
        String sSearchMonth =  (String) mapInVar.get("stt_dt").toString().substring(4,6);
        
        Calendar cal = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("yyyyMM");
        cal.setTime(df.parse(stt_dt));
        cal.add(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
        String sNextMonth = df.format(cal.getTime());
        String sParseNextMonth = sNextMonth.substring(4, 6);
        
        hmParam.put("charge_dt", stt_dt); // 송신월
        System.out.println("송신월 ::: " + stt_dt);
        
        
        // 저장할 파일 위치
        String sSendFileDirectoryPath = "/sftp_home/nicevan/send/";
        String sRecvFileDirectoryPath = "/sftp_home/nicevan/recv/";

        BufferedReader br = null;
        InputStreamReader isr = null;
        RandomAccessFile rafile = null;
        
        try
        {
        	File fileSendFileDirectoryPath = new File(sSendFileDirectoryPath);
        	String[] arrSendFileList = fileSendFileDirectoryPath.list();
        	
        	for(int idx = 0; idx < arrSendFileList.length; idx++)
        	{
        		String sFileName = arrSendFileList[idx];
        		String sTarget = sFileName.substring(10, 12);
        		System.out.println(sFileName);
        		
        		if(sTarget.equals(sSearchMonth))
        		{
        			Map<String,Object> rowData = new HashMap<String, Object>();
        			
        			rafile = new RandomAccessFile(sSendFileDirectoryPath + sFileName, "r"); // r : 읽기, w : 쓰기, rw : 읽기,쓰기
        			long pos = rafile.length() - 2;

        			while(true)
        			{
        				rafile.seek(pos);

        				if (rafile.readByte() == '\n')
        				{
        					break;
        				}
            			
        				pos--;
        			}
            		
        			rafile.seek(pos + 1);
            		
        			String sLastLine  = rafile.readLine();
        			String sReqConut  = sLastLine.substring(11, 18);
        			String sCnclCount = sLastLine.substring(54, 61);
        			int    iReqConut  = Integer.parseInt(sReqConut);
        			int    iCnclCount = Integer.parseInt(sCnclCount);

        			rafile.close();
        			
        			String sSendDt = mapInVar.get("stt_dt").toString().substring(0, 4) + sFileName.substring(10, 14);
                    Calendar calSendDt = Calendar.getInstance();
                    SimpleDateFormat sdfSendDt = new SimpleDateFormat("yyyyMMdd");
                    Date dateFileNameDay = sdfSendDt.parse(sSendDt);
                    calSendDt.setTime(dateFileNameDay);
                    
                    calSendDt.add(Calendar.DAY_OF_MONTH, +1);
                    String sSendDay = sdfSendDt.format(calSendDt.getTime());
            		
        			rowData.put("file_name", sFileName);
        			rowData.put("count", iReqConut + iCnclCount);
        			rowData.put("date", sSendDay);
        			rowData.put("recv_yn", "");
        			rowData.put("file_type", "1");
        			mList.add(rowData);
        		}
        	}
        	
        	File fileRecvFileDirectoryPath = new File(sRecvFileDirectoryPath);
        	String[] arrRecvFileList = fileRecvFileDirectoryPath.list();
        	
        	for(int idx = 0; idx < arrRecvFileList.length; idx++)
        	{
        		String sFileName = arrRecvFileList[idx];
        		//String sTarget = sFileName.substring(3, 5); // 파일명 B191205.DMS 기준
        		String sTarget = sFileName.substring(8, 10); // 파일명 NICEVAN_1225 기준
        		
        		if(sTarget.equals(sSearchMonth) || sTarget.equals(sParseNextMonth))
        		{
        			Map<String,Object> rowData = new HashMap<String, Object>();
        			
        			rafile = new RandomAccessFile(sRecvFileDirectoryPath + sFileName, "r"); // r : 읽기, w : 쓰기, rw : 읽기,쓰기
        			long pos = rafile.length() - 2;

        			while(true)
        			{
        				rafile.seek(pos);

        				if (rafile.readByte() == '\n')
        				{
        					break;
        				}
            			
        				pos--;
        			}
            		
        			rafile.seek(pos + 1);
            		
        			String sLastLine = rafile.readLine();
        			String sReqConut = sLastLine.substring(2, 8);
        			int    iReqConut = Integer.parseInt(sReqConut);
        			
        			String recvMMDD = sFileName.substring(8, 12); // 해당파일의 월일
        			Integer recvYYYY = Integer.parseInt(mapInVar.get("stt_dt").toString().substring(0, 4)); // 해당파일의 년도
        			Integer recvNextYYYY = recvYYYY + 1; // 다음해의 년도
        			
        			//if(recvMMDD.equals("0101") || recvMMDD.equals("0102")) // 20191230 -> 20200101, 20191231 -> 20200102 에 대한 검색 조건 분기
        			
        			if(recvMMDD.equals("0101"))
        			{
        				hmParam.put("req_day", recvNextYYYY.toString() + recvMMDD);
        			}
        			else
        			{
        				hmParam.put("req_day", recvYYYY.toString() + recvMMDD);
        			}
        			
        			//hmParam.put("req_day", recvYYYY.toString() + recvMMDD);
        			
        			/*
        			String sRecvDt = mapInVar.get("stt_dt").toString().substring(0, 4) + sFileName.substring(8, 12);
                    Calendar calRecvDt = Calendar.getInstance();
                    SimpleDateFormat sdfRecvDt = new SimpleDateFormat("yyyyMMdd");
                    Date dateFileNameDay = sdfRecvDt.parse(sRecvDt);
                    calRecvDt.setTime(dateFileNameDay);
                    
                    calRecvDt.add(Calendar.DAY_OF_MONTH, +2);
                    String sRecvDay = sdfRecvDt.format(calRecvDt.getTime());
                    */

        			rafile.close();

        			//int    iFranCnt  = DlwWdrwService.getWdrwNauthReqFranCount(hmParam);
        			//iReqConut = iReqConut - ((iFranCnt * 2) + 2); // iReqConut = 전체 레코드의 개수 - (요청한 프랜차이즈의 헤더와 테일을 제외한다. 그리고 start와 end를 제외한다.)
            		
        			rowData.put("file_name", sFileName);
        			rowData.put("count", iReqConut);
        			rowData.put("date", ((String)hmParam.get("req_day")).substring(0,4) + recvMMDD);
        			//rowData.put("date", sRecvDay);
        			rowData.put("recv_yn", "");
        			rowData.put("file_type", "2");
        			mList.add(rowData);
        		}
        	}
        	
        	listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);
        }
        catch(IOException ioe)
        {
        	ioe.printStackTrace();
        }
        finally
        {
        	
        }

        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 대명라이프웨이 CMS 산출중 여부를 확인한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/vld-insp")
    public ModelAndView getCmsVldInsp(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            String dltnFlag = DlwCmsService.getDltnFlagCmsMmbr(hmParam);
            if("N".equals(dltnFlag)) {//유효한 회원존재
                nResult = "overlap";
            }
            mapOutVar.put("cmsVldInspMsg", nResult);


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
     * 대명라이프웨이 회원번호로 CMS 계좌정보를 가져온다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/accnt-info")
    public ModelAndView getAccntInfo(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            hmParam.put("accnt_no", mapInVar.get("accnt_no"));

            String nResult = "";

            List<Map<String, Object>> mList = DlwCmsService.getAccntInfo(hmParam);
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
     * 입금 간소화 정보
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/totPaySel")
    public ModelAndView getPaySoftInfo(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            
            String sAccntNo = mapInVar.get("accnt_no").toString();
            hmParam.put("accnt_no", sAccntNo);
            
            /* 회원몰 정보 조회*/            
            int iMallAmt = Integer.parseInt(String.valueOf(DlwMallLinkedService.getMallUseAmt(sAccntNo)));                
            hmParam.put("ready_cash", iMallAmt);

            List<Map<String, Object>> mList = DlwCmsService.getPaySoftInfo(hmParam);

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
     * 대명라이프웨이 입금/결합/상품/부가정보 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/pay-mng")
    public ModelAndView getPayMngInfo(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap listMap1 = new DataSetMap();
        DataSetMap listMap2 = new DataSetMap();
        DataSetMap listMap3 = new DataSetMap();
        DataSetMap listMap4 = new DataSetMap();
        DataSetMap listMap5 = new DataSetMap();
        DataSetMap listMap6 = new DataSetMap();
        DataSetMap listMap7 = new DataSetMap();
        DataSetMap listMap8 = new DataSetMap();
        DataSetMap listMap9 = new DataSetMap();
        DataSetMap listMap10 = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            hmParam.put("accnt_no", mapInVar.get("accnt_no"));

            String nResult = "";


            List<Map<String, Object>> mList = DlwCmsService.getPayMngBugaInfo(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);
            if (mList != null && mList.size() > 0) {
                List<Map<String, Object>> mDtlList = DlwCmsService.getPayMngDtlBugaInfo(hmParam);
                List<Map<String, Object>> mDtl1List = DlwCmsService.getPayMngDtl1BugaInfo(hmParam);

                //입금정보
                List<Map<String, Object>> mPayList = DlwCmsService.getPayMngList(hmParam);
                List<Map<String, Object>> mPayDtlList = DlwCmsService.getPayMngDtlList(hmParam);
                List<Map<String, Object>> mPayDtl1List = DlwCmsService.getPayMngDtl1List(hmParam);

                //상품/입금정보
                List<Map<String, Object>> mPayTotalList = DlwCmsService.getPayTotalList(hmParam);
                Map<String, Object> hmParam2 = new HashMap<String, Object>();
                String prod_cd = DlwCmsService.getProdCdByAccntNo(hmParam);
                hmParam2.put("prod_cd", prod_cd);
                List<Map<String, Object>> mProdDtlList = DlwCmsService.getProductDtl(hmParam2);

                //환불정보
                List<Map<String, Object>> mRefundList = DlwCmsService.getRefundList(hmParam);
                List<Map<String, Object>> mRefundDtlList = DlwCmsService.getRefundDtlList(hmParam);
                List<Map<String, Object>> mRefundDtl1List = DlwCmsService.getRefundDtl1List(hmParam);

                listMap1.setRowMaps(mPayList);
                listMap2.setRowMaps(mPayDtlList);
                listMap3.setRowMaps(mPayDtl1List);
                listMap4.setRowMaps(mDtlList);
                listMap5.setRowMaps(mDtl1List);
                listMap6.setRowMaps(mRefundList);
                listMap7.setRowMaps(mRefundDtlList);
                listMap8.setRowMaps(mRefundDtl1List);
                listMap9.setRowMaps(mPayTotalList);
                listMap10.setRowMaps(mProdDtlList);
                mapOutDs.put("ds_output1", listMap1);
                mapOutDs.put("ds_output2", listMap2);
                mapOutDs.put("ds_output3", listMap3);
                mapOutDs.put("ds_output4", listMap4);
                mapOutDs.put("ds_output5", listMap5);
                mapOutDs.put("ds_output6", listMap6);
                mapOutDs.put("ds_output7", listMap7);
                mapOutDs.put("ds_output8", listMap8);
                mapOutDs.put("ds_output9", listMap9);
                mapOutDs.put("ds_output10", listMap10);
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
     * 납입건수에 따른 납입금액 계산
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/payamt")
    public ModelAndView getPayAmtBytPayCnt(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            hmParam.put("mon_cnt", mapInVar.get("mon_cnt"));
            hmParam.put("no", mapInVar.get("no"));

            int nResult = 0;
            nResult = DlwCmsService.getPayAmtByPayCnt(hmParam);
            mapOutVar.put("r_pay_amt", nResult);

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
     * 납입건수에 따른 납입금액 계산 - 결합상품 할부원금
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/payamt-dtl")
    public ModelAndView getPayAmtDtlBytPayCnt(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            hmParam.put("mon_cnt", mapInVar.get("mon_cnt"));
            hmParam.put("no", mapInVar.get("no"));

            int nResult = 0;
            nResult = DlwCmsService.getPayAmtDtlByPayCnt(hmParam);
            mapOutVar.put("r_pay_amt_dtl", nResult);

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
     * 납입건수에 따른 납입금액 계산 - 결합상품 추가부담금
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/payamt-dtl1")
    public ModelAndView getPayAmtDtl1BytPayCnt(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            hmParam.put("mon_cnt", mapInVar.get("mon_cnt"));
            hmParam.put("no", mapInVar.get("no"));

            int nResult = 0;
            nResult = DlwCmsService.getPayAmtDtl1ByPayCnt(hmParam);
            mapOutVar.put("r_pay_amt_dtl1", nResult);

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
     * 대명라이프웨이 가수금 리스트를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/gasu-list")
    public ModelAndView getGasuMngList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            int nTotal = DlwCmsService.getGasuMngCount(hmParam);

            mapOutVar.put("tc_gasu", nTotal);

            List<Map<String, Object>> mList = DlwCmsService.getGasuMngList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            //2018.01.25 접속 로그////////////////////////////////////////////////////////////////////////////////
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
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

    /**
     * 대명라이프웨이 산출내역 삭제
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/wdrw-call/delete")
    public ModelAndView deleteWdrwCall(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map<String, Object> hmParam = new HashMap<String, Object>();
     // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        try {
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();

            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaa");
            DlwCmsService.deleteWDRWCall(xpDto);

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
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

    /**
     * 대명라이프웨이 가수금 상세정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/gasu-dtl/list")
    public ModelAndView getGasuDetailList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap listMap2 = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            hmParam.put("seq", mapInVar.get("seq"));

            List<Map<String, Object>> mList = DlwCmsService.getGasuMngList(hmParam);
            listMap.setRowMaps(mList);
            List<Map<String, Object>> mDtlList = DlwCmsService.getGasuDetail(hmParam);
            listMap2.setRowMaps(mDtlList);
            mapOutDs.put("ds_output", listMap);
            mapOutDs.put("ds_outputDtl", listMap2);

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
     * 대명라이프웨이 가수금 환불정보를 저장한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/gasu-dtl/merge")
    public ModelAndView mergeGasuDetail(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            if (listInDs != null && listInDs.size() > 0) {
                hmParam = listInDs.get(0);

                //세션
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("emple_no", hmParam.get("rgsr_id"));
                hmParam.put("reg_man", hmParam.get("rgsr_id"));
                hmParam.put("upd_man", hmParam.get("rgsr_id"));

                String mode = (String)hmParam.get("mode");

                System.out.println("mode :: " + mode);
                if ("update".equals(mode)) {
                    DlwCmsService.updateGasuDtl(hmParam);
                } else {
                    DlwCmsService.insertGasuDtl(hmParam);
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
     * 대명라이프웨이 가수금 환불정보를 삭제한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/gasu-dtl/delete")
    public ModelAndView deleteGasuDetail(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            hmParam.put("gasu_seq", mapInVar.get("gasu_seq"));
            hmParam.put("seq", mapInVar.get("seq"));

            //세션
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("emple_no", hmParam.get("rgsr_id"));
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            hmParam.put("upd_man", hmParam.get("rgsr_id"));

            DlwCmsService.deleteGasuDtl(hmParam);

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
     * 대명라이프웨이 가수금 환불정보를 저장한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/gasu-mng/merge")
    public ModelAndView mergeGasuMng(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            if (listInDs != null && listInDs.size() > 0) {
                hmParam = listInDs.get(0);

                //세션
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("emple_no", hmParam.get("rgsr_id"));
                hmParam.put("reg_man", hmParam.get("rgsr_id"));
                hmParam.put("upd_man", hmParam.get("rgsr_id"));

                String mode = (String)hmParam.get("mode");

                System.out.println("mode : " + mode);

                if ("update".equals(mode)) {
                    DlwCmsService.updateGasuMng(hmParam);
                } else {
                    DlwCmsService.insertGasuMng(hmParam);
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
     * 대명라이프웨이 가수금 환불정보를 삭제한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/gasu-mng/delete")
    public ModelAndView deleteGasuMng(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            hmParam.put("gasu_seq", mapInVar.get("gasu_seq"));
            hmParam.put("seq", mapInVar.get("seq"));

            //세션
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("emple_no", hmParam.get("rgsr_id"));
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            hmParam.put("upd_man", hmParam.get("rgsr_id"));

            DlwCmsService.deleteGasuMng(hmParam);

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
     * 출금이체의뢰결과파일변환(EB22) 오류 내역 등록
     *
     * @param XPlatformMapDTO xpDto, Model model
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/eb22/insertEB22ErrorInfo")
    public ModelAndView insertEB22ErrorInfo(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
        Map<String, Object> hmOutParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            int cudCnt = DlwCmsService.insertEB22ErrorInfo(xpDto, hmOutParam);
            if (-1 == cudCnt) {
                throw new Exception("파일이 존재하지 않습니다.");
            } else if (-2 == cudCnt) {
                throw new Exception("파일을 읽을 권한이 없습니다.");
            }

            if (null != hmOutParam.get("file_nm")) {
                mapOutVar.put("fv_insertEB22ErrorInfoFileNm", hmOutParam.get("file_nm").toString());
            } else {
                mapOutVar.put("fv_insertEB22ErrorInfoFileNm", "");
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

 /*
  임시 결과반영 로직..나중에 삭제해도 무방
 */
    @RequestMapping(value = "/eb22/imsiinsert")
    public ModelAndView imsiinsertErrorInfo(XPlatformMapDTO xpDto, Model model) throws Exception {


        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
        Map<String, Object> hmOutParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {

             Map <String, Object> mapInVar     = xpDto.getInVariableMap();
             Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();


           System.out.println("bbbbbbbbbbbbb");


            hmOutParam.put("yymmdd",  mapInVar.get("yymmdd"));
            DlwCmsService.immsiinsert(hmOutParam);


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
     * eb21파일 다운로드
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/eb21downloadFile")
    public void eb21downloadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> hmParam = null;
        Map<String, Object> mRow = null;
        String upload_cl = "";
        String file_nm = "";
        String reg_man = "";
        String reg_dm = "";

        try {
            String yymmdd = CommonUtils.nvls(request.getParameter("yymmdd"));
            hmParam = new HashMap<>();
            hmParam.put("yymmdd", yymmdd);



            String  successPath	= "";

            String osName = System.getProperty("os.name").toLowerCase();
            if (osName.indexOf("windows") >= 0) {
                successPath = EgovProperties.getProperty("cms.file.windows.successPath");
            } else {
                successPath = EgovProperties.getProperty("cms.file.unix.successPath");
            }



            String srcFilePath = successPath +"EB21/" +"EB21"+ yymmdd.substring(4,8);


            if (osName.indexOf("windows") >= 0) {
                srcFilePath = srcFilePath.replaceAll("/", "\\\\");
            }


            System.out.println(srcFilePath);

        //    log.debug("srcFilePath : " + srcFilePath);

            File xDownFile = new File(srcFilePath);
            if (!xDownFile.exists()) {
                throw new EgovBizException("디스크에 실제 파일이 존재하지 않습니다.");


            }

            String contentType = request.getContentType();


            System.out.println("contentType1 : " + contentType);
  //          log.debug("contentType : " + contentType);

            if (contentType == null) {
                if (request.getHeader("user-agent").indexOf("MSIE 5.5") != -1)
                    response.setContentType("doesn/matter;");
                else
                    response.setContentType("application/octet-stream");
            } else {
                response.setContentType(contentType);
            }

            response.setHeader("Content-Transfer-Encoding:", "binary");

            // String sDisplayFileName = "상품목록.xlsx";
            // response.setHeader("Content-Disposition", "attachment;filename=" + sDisplayFileName + ";");

            response.setHeader("Content-Length", "" + xDownFile.length());
            response.setHeader("Pragma", "no-cache;");
            response.setHeader("Expires", "-1;");

            byte b[] = new byte[1024*4];
            BufferedInputStream fin = new BufferedInputStream(new FileInputStream(xDownFile));
            BufferedOutputStream outs = new BufferedOutputStream(response.getOutputStream());

            try{
                int read = 0;
                while ((read = fin.read(b)) != -1)
                {
                    outs.write(b,0,read);
                }

            }catch (Exception e){
            }finally{
                if (outs!=null) outs.close();
                if (fin!=null) fin.close();
            }

            outs.flush();
            outs.close();

        } catch (Exception e) {
            e.printStackTrace();

            ServletOutputStream os = response.getOutputStream();
            os.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            os.println("<Root xmlns=\"http://www.tobesoft.com/platform/dataset\" ver=\"5000\">");
            os.println("<Parameters>");
            os.println("<Parameter id=\"ErrorCode\" type=\"string\">-1</Parameter>");
            os.println("<Parameter id=\"ErrorMsg\" type=\"string\">" + e.getMessage() + "</Parameter>");
            os.println("</Parameters>");
            os.println("</Root>");
            os.close();

        }
    }

    @RequestMapping(value = "/eb22upload")
    public void cmseb22upload(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        Map<String, Object> mResult = new HashMap<>();
        String fileNm = "";

        PlatformData resData = new PlatformData();
        VariableList resVarList = resData.getVariableList();

   //     log.debug("--------^^--------");

        try {
  //          log.debug("uploadToNas 컨트롤러 - 1");
            DlwCmsService.eb22upload(request, response, mResult);
   //         log.debug("uploadToNas 컨트롤러 - 2");

            fileNm = CommonUtils.nvls((String)mResult.get("file_nm"));

            resVarList.add("ErrorCode"	, szErrorCode);
            resVarList.add("ErrorMsg"	, fileNm + "|" + szErrorMsg);

        } catch (EgovBizException e) {
            resVarList.add("ErrorCode", "-1");

            fileNm = CommonUtils.nvls((String)mResult.get("file_nm"));
            resVarList.add("ErrorMsg", fileNm + "|" + e.getMessage());

            e.printStackTrace();
        } catch (Exception e) {
            resVarList.add("ErrorCode", "-1");

            fileNm = CommonUtils.nvls((String)mResult.get("file_nm"));
            resVarList.add("ErrorMsg", fileNm + "|업로드 중 오류가 발생하였습니다.");

            e.printStackTrace();
        }

        HttpPlatformResponse res = new HttpPlatformResponse(response);
        res.setData(resData);
        res.sendData();

    }

    /**
     * eb21파일 다운로드
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/eb12downloadFile")
    public void eb12downloadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> hmParam = null;
        Map<String, Object> mRow = null;
        String upload_cl = "";
        String file_nm = "";
        String reg_man = "";
        String reg_dm = "";

        try {
            String yymmdd = CommonUtils.nvls(request.getParameter("yymmdd"));
            hmParam = new HashMap<>();
            hmParam.put("yymmdd", yymmdd);



            String  successPath	= "";

            String osName = System.getProperty("os.name").toLowerCase();
            if (osName.indexOf("windows") >= 0) {
                successPath = EgovProperties.getProperty("cms.file.windows.successPath");
            } else {
                successPath = EgovProperties.getProperty("cms.file.unix.successPath");
            }



            String srcFilePath = successPath +"EB12/" +"EB12"+ yymmdd.substring(4,8);


            if (osName.indexOf("windows") >= 0) {
                srcFilePath = srcFilePath.replaceAll("/", "\\\\");
            }


            System.out.println(srcFilePath);

        //    log.debug("srcFilePath : " + srcFilePath);

            File xDownFile = new File(srcFilePath);
            if (!xDownFile.exists()) {
                throw new EgovBizException("디스크에 실제 파일이 존재하지 않습니다.");


            }

            String contentType = request.getContentType();


            System.out.println("contentType1 : " + contentType);
  //          log.debug("contentType : " + contentType);

            if (contentType == null) {
                if (request.getHeader("user-agent").indexOf("MSIE 5.5") != -1)
                    response.setContentType("doesn/matter;");
                else
                    response.setContentType("application/octet-stream");
            } else {
                response.setContentType(contentType);
            }

            response.setHeader("Content-Transfer-Encoding:", "binary");

            // String sDisplayFileName = "상품목록.xlsx";
            // response.setHeader("Content-Disposition", "attachment;filename=" + sDisplayFileName + ";");

            response.setHeader("Content-Length", "" + xDownFile.length());
            response.setHeader("Pragma", "no-cache;");
            response.setHeader("Expires", "-1;");

            byte b[] = new byte[1024*4];
            BufferedInputStream fin = new BufferedInputStream(new FileInputStream(xDownFile));
            BufferedOutputStream outs = new BufferedOutputStream(response.getOutputStream());

            try{
                int read = 0;
                while ((read = fin.read(b)) != -1)
                {
                    outs.write(b,0,read);
                }

            }catch (Exception e){
            }finally{
                if (outs!=null) outs.close();
                if (fin!=null) fin.close();
            }

            outs.flush();
            outs.close();

        } catch (Exception e) {
            e.printStackTrace();

            ServletOutputStream os = response.getOutputStream();
            os.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            os.println("<Root xmlns=\"http://www.tobesoft.com/platform/dataset\" ver=\"5000\">");
            os.println("<Parameters>");
            os.println("<Parameter id=\"ErrorCode\" type=\"string\">-1</Parameter>");
            os.println("<Parameter id=\"ErrorMsg\" type=\"string\">" + e.getMessage() + "</Parameter>");
            os.println("</Parameters>");
            os.println("</Root>");
            os.close();

        }
    }

    /**
     * 대명라이프웨이 콜센터 산출 중복건 목록
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/callduplist")
    public ModelAndView getCallDupList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            List<Map<String, Object>> mList = DlwCmsService.getCallDupList(hmParam);
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
     * 대명라이프웨이 이체일자에 의한 산출_기초데이터(170523일 작업)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/wdrw-ichae/saveBasic")
    public ModelAndView saveWdrwIchaeDt_Basic(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            hmParam.put("wdrw_gubun", mapInVar.get("wdrw_gubun"));

            String Inv_dt = "";

            //세션
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("inv_dt", Inv_dt);
            hmParam.put("emple_no", hmParam.get("rgsr_id"));
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            hmParam.put("upd_man", hmParam.get("rgsr_id"));
            hmParam.put("ichae_stt_dt", (String)mapInVar.get("ichae_stt_dt"));
            hmParam.put("ichae_end_dt", (String)mapInVar.get("ichae_end_dt"));
            hmParam.put("sgubun", (String)mapInVar.get("sgubun"));

            List<Map<String, Object>> result = DlwCmsService.getCmsMemberByIchaeDt_Basic(hmParam);

            String mnthAmt = DlwCmsService.getDlwWdrwReqDtTtamt(hmParam);
            double dmnthAmt = Double.valueOf(mnthAmt).doubleValue();

            List<Map<String, Object>> mComnList = DlwCmsService.getDlwCmsComnInfo(hmParam);
            String mon_wdrw_limit = (String)mComnList.get(0).get("mon_wdrw_limit");
            mapOutVar.put("mnthLimitAmt", mon_wdrw_limit);
            mapOutVar.put("mnthAmt", mnthAmt);

            listMap.setRowMaps(result);
            mapOutDs.put("ds_output", listMap);


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
     * CMS 기초데이터 임시건 산출
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/wdrw-imsi-ichae/saveBasic")
    public ModelAndView saveWdrwimsiIchaeDt_Basic(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
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

            hmParam.put("wdrw_gubun", mapInVar.get("wdrw_gubun"));

            //세션
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("emple_no", oLoginUser.getUserid());
            hmParam.put("reg_man", oLoginUser.getUserid());
            hmParam.put("upd_man", oLoginUser.getUserid());

            //CommonUtils.printLog(hmParam);
            DlwCmsService.saveWdrwimsiIchaeDt_Basic(hmParam);

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
     * 산출 기초데이터 확정(Basic -> Req)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/basicfix/saveWdrw")
    public ModelAndView saveWdrwBasic_Fix(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
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

            hmParam.put("wdrw_gubun", mapInVar.get("wdrw_gubun"));

            //세션
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("emple_no", oLoginUser.getUserid());
            hmParam.put("reg_man", oLoginUser.getUserid());
            hmParam.put("upd_man", oLoginUser.getUserid());

            //CommonUtils.printLog(hmParam);
            DlwCmsService.getBasicWdrw_Fix(hmParam);


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
     * CMS 기초데이터 수정
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/updateBasicAmt")
    public ModelAndView updateBasicAmt(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if (listInDs != null && listInDs.size() > 0) {
                hmParam = listInDs.get(0);

                //세션
                ParamUtils.addSaveParam(hmParam);

                hmParam.put("reg_man", oLoginUser.getUserid());
                hmParam.put("upd_man", oLoginUser.getUserid());

                DlwCmsService.updateBasicAmt(hmParam);
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
     * 대명라이프웨이 CMS 출금이체 파일산출 데이터를 조회(기초데이터 조회)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/wdrw-hstr-eb21/listBasic")
    public ModelAndView getDlwWdrwHstrEb21List_Basic(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            //hmParam = listInDs.get(0);

            ParamUtils.addSaveParam(hmParam);
            hmParam.put("emple_no", hmParam.get("rgsr_id"));
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            hmParam.put("upd_man", hmParam.get("rgsr_id"));

            hmParam.put("paramEmpleNo", hmParam.get("rgsr_id"));
            hmParam.put("paramAs", "EMP");

            List<Map<String, Object>> mList = DlwCmsService.getDlwWdrwHstr_Basic(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            List<Map<String, Object>> mComnList = DlwCmsService.getDlwCmsComnInfo(hmParam);
            String mon_wdrw_limit = (String)mComnList.get(0).get("mon_wdrw_limit");

            //CMS 당월 출금의뢰 총액 조회
            String mnthAmt = DlwCmsService.getDlwWdrwReqDtTtamt(hmParam);

            double dmnthAmt = Double.valueOf(mnthAmt).doubleValue();

            //mapOutVar.put("tc_list", mList.size());
            mapOutVar.put("mnthLimitAmt", mon_wdrw_limit);
            mapOutVar.put("mnthAmt", dmnthAmt);

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
     * 대명라이프웨이 산출내역 개별 삭제
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/eb21/deleteBasic")
    public ModelAndView deleteWdrwEb21_Basic(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            Map<String, Object> hmParam = new HashMap<String, Object>();
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

            for (int i = 0; i < listInDs.size(); i++) {
                hmParam = listInDs.get(i);
                //세션
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("upd_man", oLoginUser.getUserid());

                hmParam.put("wdrw_gubun", mapInVar.get("wdrw_gubun"));

                int rowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

                if (rowType == DataSet.ROW_TYPE_UPDATED) {
                    // 고객만족센터 테이블 청구여부 비트 변경
                    DlwCmsService.updateWdrwReqYn_Basic(hmParam);

                    // 산출기초테이블 삭제여부 변경
                    if(!"Card".equals(hmParam.get("wdrw_gubun"))) {
                        DlwCmsService.deleteCmsBasic(hmParam);
                    } else {
                        DlwCmsService.deleteCardBasic(hmParam);
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

    /*
     * 대명라이프웨이 산출내역 기초데이터 일괄삭제
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/eb21/deleteallBasic")
    public ModelAndView deleteWdrwEb21all_Basic(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            Map<String, Object> hmParam = new HashMap<String, Object>();
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

            //세션
            ParamUtils.addSaveParam(hmParam);

            hmParam.put("delete", "Y");
            hmParam.put("gubun",  mapInVar.get("wdrw_gubun"));

            /// 고객산출 부분 원상복귀
            DlwCmsService.updateWdrwReqYn1all(hmParam);

            /// 기초테이블 산출비트 변경(CMS/CARD) -> impl 내부적으로 구분
            DlwCmsService.deleteWDRWHistoryall_Basic(hmParam);

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
     * 대명라이프웨이 청구파일 기초테이블로 적용한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/wdrw-req-basic")
    public ModelAndView updateDlwWdrwReq_Basic(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();

            String msg = "";
            DlwCmsService.updateCmsAndCardWdrwReq_Basic(mapInDs, mapInVar);

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
     * 대명라이프웨이  다중환불조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/pay-mng_refund")
    public ModelAndView getPayMngInfo_refund(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        DataSetMap listMap6 = new DataSetMap();
        DataSetMap listMap7 = new DataSetMap();
        DataSetMap listMap8 = new DataSetMap();
       // Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

       //     hmParam.put("accnt_no", mapInVar.get("accnt_no"));

            Map<String, Object> hmParam = new HashMap<String, Object>();
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if (listInDs != null && listInDs.size() > 0) {
                hmParam = listInDs.get(0);

                //세션
                ParamUtils.addSaveParam(hmParam);

               // hmParam.put("reg_man", oLoginUser.getUserid());
               // hmParam.put("upd_man", oLoginUser.getUserid());
                //환불정보

                List<Map<String, Object>> mRefundList = DlwCmsService.getdaRefundList(hmParam);
                List<Map<String, Object>> mRefundDtlList = DlwCmsService.getdaRefundDtlList(hmParam);
                List<Map<String, Object>> mRefundDtl1List = DlwCmsService.getdaRefundDtl1List(hmParam);

                listMap6.setRowMaps(mRefundList);
                listMap7.setRowMaps(mRefundDtlList);
                listMap8.setRowMaps(mRefundDtl1List);

                mapOutDs.put("ds_output6", listMap6);
                mapOutDs.put("ds_output7", listMap7);
                mapOutDs.put("ds_output8", listMap8);
            }

            //2018.01.25 접속 로그////////////////////////////////////////////////////////////////////////////////
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
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }


    /**
     * 대명스테이션 NICEPAYMENT FTP File 전송리스트 및 수신리스트
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     * 접속정보 >  ip:121.133.126.8  port:22
	 * 계정&비번 > dmlife000g / eoaudqlffld1!
     */

    @RequestMapping(value = "/acqusamsung/recvfilelist")
    public ModelAndView recvfilelist(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        DataSetMap listMap = new DataSetMap();
        Map<String, Object> mapInVar = xpDto.getInVariableMap();     // 화면에서 받은 값
        Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();   // 화면에서 보내온 DS
        Map<String, Object> mapOutVar = xpDto.getOutVariableMap();   // 화면에 보낼 값
        Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap(); // 화면에 보낼 DS

        Map<String, Object> hmParam = new HashMap<String, Object>(); // DS에서 한건씩 꺼내서 쓸 맵
        List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();

        // 현재 날짜
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");

        // 조회월을 받음.
        String stt_dt = (String) mapInVar.get("stt_dt").toString().substring(0,6);
        System.out.println("송신월 ::: " + stt_dt);

        String sFTPFileDirectoryPath  = "/home/dmlife000g/acqu/" + stt_dt + "/";
        //String sRecvFileDirectoryPath = "C:\\app\\dmlife000g\\acqu\\" + stt_dt + "\\"; // 로컬에서 테스트 수행시
        String sRecvFileDirectoryPath = "/app/dmlife000g/acqu/" + stt_dt + "/"; // 서버에서 수행시

        Session session = null;
    	JSch jsch = null;
    	Channel channel = null;
    	ChannelSftp channelSftp = null;

    	InputStream ftpIn = null;
    	InputStreamReader ftpIsr= null;
    	BufferedReader ftpBr = null;

        FileOutputStream ftpOut = null;
        OutputStreamWriter ftpOsw = null;
        BufferedWriter ftpBw = null;

        String sNicePaymentFtpIp = "121.133.126.8";   // 실제 ip
        int iNicePaymentFtpPort = 22;                 // 실제 port
        String sFTPLoginId = basVlService.getBasVlAsString("nice_ftp_access_id"); // 실제 접속 Id (dmlife000g)
        String sFTPLoginPw = basVlService.getBasVlAsString("nice_ftp_access_pw"); // 실제 접속 PassWord (eoaudqlffld1!)
        //System.out.println("sFTPLoginId ::: " + sFTPLoginId + " sFTPLoginPw ::: " + sFTPLoginPw);
        
        File fileRecvFileDirectoryPath = new File(sRecvFileDirectoryPath);

        if(fileRecvFileDirectoryPath.exists() == false)
		{
			fileRecvFileDirectoryPath.mkdirs();
		}

        String[] asRecvFileList = fileRecvFileDirectoryPath.list();
        Vector<String> vRecvFileList = new Vector<String>();

        for(int idx = 0; idx < asRecvFileList.length; idx++)
        {
        	vRecvFileList.add(asRecvFileList[idx]);
        }

        /* 이 부분에서 본서버(192.168.10.6X) 로 받아오지 않은 파일을 모두 받아옴 */
        try
        {
        	jsch = new JSch();

        	session = jsch.getSession(sFTPLoginId, sNicePaymentFtpIp, iNicePaymentFtpPort);
        	session.setPassword(sFTPLoginPw);

            java.util.Properties config = new java.util.Properties();

            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            session.connect();

            // 6. sftp 채널을 연다.
            channel = session.openChannel("sftp");

            channel.connect();
            channelSftp = (ChannelSftp)channel;
            //channelSftp.connect();

            //수신 디렉토리 접근
            channelSftp.cd(sFTPFileDirectoryPath);

            //디렉토리 안의 목록 조회
            Vector<ChannelSftp.LsEntry> sendFileList = channelSftp.ls(sFTPFileDirectoryPath);

            //System.out.println("받은 파일 내역 : " + vRecvFileList.toString());
            //System.out.println("FTP 파일 내역 : " + sendFileList.toString());
            for(ChannelSftp.LsEntry entry : sendFileList)
            {
            	String ftpFileName = entry.getFilename();
            	if(ftpFileName.indexOf(stt_dt) > 0 && !ftpFileName.equals(".") && !ftpFileName.equals("..") && ftpFileName.indexOf(".") != 0)
            	{
            		if(vRecvFileList.contains(ftpFileName) == false)
                	{
            			//System.out.println("없는 파일이니 파일을 쓰겠습니다.");
                		ftpIn = channelSftp.get(ftpFileName);
                		ftpIsr = new InputStreamReader(ftpIn);
                		ftpBr = new BufferedReader(ftpIsr);

                		//System.out.println("파일의 경로와이름은 : " + sRecvFileDirectoryPath + ftpFileName);
                		ftpOut = new FileOutputStream(new File(sRecvFileDirectoryPath + ftpFileName));
                		ftpOsw = new OutputStreamWriter(ftpOut);
                		ftpBw = new BufferedWriter(ftpOsw);

                        String ftpLine = "";
                        //System.out.println("파일을 쓰고 있습니다.");


                        while ((ftpLine = ftpBr.readLine()) != null)
                        {
                        	ftpBw.write(ftpLine);
                        	ftpBw.newLine();
                        }

                        //System.out.println("파일을 모두 작성하였습니다.");
                        ftpBw.flush();
                	}
                	else
                	{
                		//System.out.println("이미 존재하는 파일입니다. 다운로드를 하지 않습니다.");
                	}
            	}
            	else
            	{
            		//System.out.println("기준에 맞지 않는데이터 입니다.");
            	}

            }
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        	szErrorCode = "-1";
        	String exMessage = e.getMessage();
        	//System.out.println("에러메시지 ::: " + exMessage);
        	if(exMessage.equals("No such file"))
        	{
        		szErrorMsg = "조회된 데이터가 없습니다.";
        	}
        	else
        	{
        		szErrorMsg = exMessage;
        	}
        }
        finally
        {
        	if(ftpBw != null)
        	{
        		ftpBw.close();
        	}
        	if(ftpOsw != null)
        	{
        		ftpOsw.close();
        	}
        	if(ftpOut != null)
        	{
        		ftpOut.close();
        	}
        	if(ftpBr != null)
        	{
        		ftpBr.close();
        	}
        	if(ftpIsr != null)
        	{
        		ftpIsr.close();
        	}
        	if(ftpIn != null)
        	{
        		ftpIn.close();
        	}
        	if(channelSftp != null)
        	{
        		channelSftp.quit();
        	}
        	if(channel != null)
        	{
        		channel.disconnect();
        	}
        	if(session != null)
        	{
        		session.disconnect();
        	}
        }

        /* 본서버에 있는 파일을 읽어옴 */
        File files = new File(sRecvFileDirectoryPath);
        String[] aRecvFiles = files.list();

        RandomAccessFile rafile = null; // 마지막줄을 읽어오기 위함임.
        File safile = null;
        FileReader fr = null;
        BufferedReader br = null;

        try
        {

        	for(int idx = 0; idx < aRecvFiles.length; idx++)
        	{
        		String fileName = aRecvFiles[idx];
        		if(fileName.indexOf(stt_dt) > 0 )
        		{
        			Map<String,Object> rowData = new HashMap<String, Object>();

            		rafile = new RandomAccessFile(sRecvFileDirectoryPath + fileName, "r"); // r : 읽기, w : 쓰기, rw : 읽기,쓰기
            		long pos = rafile.length() - 2;

            		while(true)
            		{
            			rafile.seek(pos);

            			if (rafile.readByte() == '\n')
            			{
    						break;
    					}
            			pos--;
            		}

            		rafile.seek(pos + 1);

            		String sLastLine = rafile.readLine();
            		String sConut = sLastLine.substring(19, 29);
            		int    iConut = Integer.parseInt(sConut);


            		//System.out.println("파일의 이름은 :: " + fileName + " 이고 마지막 줄의 내용은 :: " + sLastLine);
            		//System.out.println("파일의전송 건수는 :: " + sConut);

            		rowData.put("file_name", fileName);
            		rowData.put("count", iConut);

            		rafile.close();

            		safile = new File(sRecvFileDirectoryPath + fileName);
            		fr = new FileReader(safile);
            		br = new BufferedReader(fr);

            		String sStartLine = br.readLine();
            		String sDate = sStartLine.substring(1, 9);
            		String sCalcYn = "";

            		rowData.put("date", sDate);

            		hmParam.put("file_name", fileName);
            		List<Map<String, Object>> isFileExist = DlwCmsService.getAcquResultDataMst(hmParam);

            		if(isFileExist.size() <= 0)
                	{
                		sCalcYn = "미산출";
                	}
                	else
                	{
                		sCalcYn = "산출완료";
                	}

            		rowData.put("calc_yn", sCalcYn);

            		if(iConut > 0)
            		{
            			mList.add(rowData);
            		}
            		//System.out.println("파일의제작 날짜는 :: " + sDate);

                    br.close();
            		fr.close();


        		}
        		else
        		{
        			//System.out.println("조건에 맞지 않습니다.");
        		}

        	}

            //System.out.println(mList.toString());
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);
        }
        catch(IOException ioe)
        {
        	ioe.printStackTrace();
        	szErrorCode = "-1";
            szErrorMsg = ioe.getMessage();
        }
        finally
        {
        	if(br != null)
        	{
        		br.close();
        	}
        	if(fr != null)
        	{
        		fr.close();
        	}
        	if(rafile != null)
        	{
        		rafile.close();
        	}
        }

        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 대명스테이션 NICEPAYMENT FTP File 전송리스트 및 수신리스트
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     * 접속정보 >  ip:121.133.126.8  port:22
	 * 계정&비번 > dmlife000g / eoaudqlffld1!
     */
    @RequestMapping(value = "/acqusamsung/insertAcquResultData")
    public ModelAndView insertAcquResultData(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        /**********************************************************************************
         * 기본적인 변수들 선언
         * ********************************************************************************/
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> mapInVar = xpDto.getInVariableMap();     // 화면에서 받은 값
        Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();   // 화면에서 보내온 DS
        Map<String, Object> mapOutVar = xpDto.getOutVariableMap();   // 화면에 보낼 값
        Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap(); // 화면에 보낼 DS

        Map<String, Object> hmParam = new HashMap<String, Object>(); // DS에서 한건씩 꺼내서 쓸 맵
        List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();

        // 현재 날짜
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");

        // 조회월을 받음.
        String sCalcFileName = (String) mapInVar.get("file_name").toString().trim();
        hmParam.put("file_name", sCalcFileName);
        //System.out.println("산출파일명 ::: " + sCalcFileName);

        //String sRecvFileDirectoryPath = "C:\\app\\dmlife000g\\acqu\\" + sCalcFileName.substring(16, 22) + "\\"; // 로컬에서 테스트 수행시
        String sRecvFileDirectoryPath = "/app/dmlife000g/acqu/" + sCalcFileName.substring(16, 22) + "/"; // 서버에서 수행시


        File files = new File(sRecvFileDirectoryPath, sCalcFileName);

        RandomAccessFile rafile = null; // 마지막줄을 읽어오기 위함임.
        File safile = null;
        FileReader fr = null;
        BufferedReader br = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null ;

        //String sDriverClassName = "oracle.jdbc.driver.OracleDriver";
        //String sUrl             = "jdbc:oracle:thin:@61.39.175.227:1521/dmlife"; // Client상에서 oracle로 붙을때. 로컬개발때는 여기를 사용
        //String sUrl             = "jdbc:oracle:thin:@192.168.10.71:1521/dmlife";   // Server상에서 oracle로 붙을때. 개발서버반영때는 여기를 사용
        //String sUrl             = "jdbc:oracle:thin:@192.168.10.84:1521/dmlife";   // Server상에서 oracle로 붙을때. 운영서버반영때는 여기를 사용
        //String sUserName        = "LF_DMUSER";
        //String sPassword        = "dmuser123";
        
        String sAccessClassName = EgovProperties.getProperty("Globals.Fix.Database.Access.DriverClassName");
    	String sAccessIp        = EgovProperties.getProperty("Globals.Fix.Database.Access.Ip");
    	String sAccessUserName  = EgovProperties.getProperty("Globals.Fix.Database.Access.UserName");
    	String sAccessPassword  = EgovProperties.getProperty("Globals.Fix.Database.Access.Password");

        String sqlStatement = "INSERT INTO LF_DMUSER.TB_ACQU_RSLT_DTL(MST_SERIAL_NUM, DATA_GB,       DATA_TID,       DATA_FRAN_ID,   DATA_TRAD_GB,";
        sqlStatement += "DATA_TRAD_DTTM, DATA_PURC_DTTM, DATA_APPY_NUM, DATA_PURC_CRCO, DATA_CARD_GB,   DATA_CARD_NUM,";
        sqlStatement += "DATA_PURC_PAY,  DATA_FRAN_FEE,  DATA_ETC_FEE,  DATA_AMOT_PAID, DATA_AMOT_DTTM, DATA_TRAD_BLGB,";
        sqlStatement += "DATA_FILLER,    RGSR_ID,        RGSR_DTTM,     DEL_FG)";
        sqlStatement += "VALUES ( (SELECT NVL(MAX(MST_SERIAL_NUM) , 0) FROM LF_DMUSER.TB_ACQU_RSLT_MST WHERE 1=1),";
        sqlStatement += "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE, 'N')";
        
        int iDataIdx = 0;

        //System.out.println("개발프로퍼티 ::: " + sAccessClassName + " ::: " + sAccessIp + " ::: " + sAccessUserName + " ::: " + sAccessPassword);

        Class.forName(sAccessClassName);
        connection = DriverManager.getConnection(sAccessIp, sAccessUserName, sAccessPassword);
        connection.setAutoCommit(false);
        preparedStatement = connection.prepareStatement(sqlStatement) ;

        try
        {
        	List<Map<String, Object>> isFileExist = DlwCmsService.getAcquResultDataMst(hmParam);
        	//System.out.println("건수 : " + isFileExist.size());

        	if(isFileExist.size() <= 0)
        	{
        		Map<String,Object> rowDataMst = new HashMap<String, Object>();

                rafile = new RandomAccessFile(sRecvFileDirectoryPath + sCalcFileName, "r"); // r : 읽기, w : 쓰기, rw : 읽기,쓰기
                long pos = rafile.length() - 2;

                while(true)
                {
                	rafile.seek(pos);

                	if (rafile.readByte() == '\n')
                	{
        				break;
        			}
                	pos--;
                }

                rafile.seek(pos + 1);

                String sLastLine = rafile.readLine();
                String sTotRecordGb = sLastLine.substring(0, 1).trim();
                String sTotRecvDttm = sLastLine.substring(1, 9).trim();
                String sTotSendId   = sLastLine.substring(9, 19).trim();
                String sTotSendNum  = sLastLine.substring(19, 29).trim();
                String sTotAppyNum  = sLastLine.substring(29, 39).trim();
                String sTotAppyPay  = sLastLine.substring(39, 54).trim();
                String sTotCnclNum  = sLastLine.substring(54, 64).trim();
                String sTotCnclPay  = sLastLine.substring(64, 79).trim();
                String sTotFiller   = sLastLine.substring(79, 200).trim();

                /*
                System.out.println("파일의 이름은 ::: "  + sRecvFileDirectoryPath + sCalcFileName + " 이고 마지막 줄의 내용은 :: " + sLastLine);
                System.out.println("RECORD 구분 ::: "    + sTotRecordGb);
                System.out.println("매입결과수신일 ::: " + sTotRecvDttm);
                System.out.println("전송대상ID ::: "     + sTotSendId);
                System.out.println("전송 건수 ::: "      + sTotSendNum);
                System.out.println("승인 건수 ::: "      + sTotAppyNum);
                System.out.println("승인 금액 ::: "      + sTotAppyPay);
                System.out.println("취소 건수 ::: "      + sTotCnclNum);
                System.out.println("취소 금액 ::: "      + sTotCnclPay);
                System.out.println("FILLER ::: "         + sTotFiller);
                */

                rowDataMst.put("tot_record_gb", sTotRecordGb);
                rowDataMst.put("tot_recv_dttm", sTotRecvDttm);
                rowDataMst.put("tot_send_id"  , sTotSendId);
                rowDataMst.put("tot_send_num" , sTotSendNum);
                rowDataMst.put("tot_appy_num" , sTotAppyNum);
                rowDataMst.put("tot_appy_pay" , sTotAppyPay);
                rowDataMst.put("tot_cncl_num" , sTotCnclNum);
                rowDataMst.put("tot_cncl_pay" , sTotCnclPay);
                rowDataMst.put("tot_filler"   , sTotFiller);
                rowDataMst.put("file_name"    , sCalcFileName);
                rowDataMst.put("file_path"    , sRecvFileDirectoryPath + sCalcFileName);

                rafile.close();

                fr = new FileReader(files);
                br = new BufferedReader(fr);

                String sLine = "";

                while ((sLine = br.readLine()) != null)
                {
                	//System.out.println("라인 내용 ::: " + sLine);
                	String sRecordGb = sLine.substring(0, 1);

                    if(sRecordGb.equals("H") == true)
                    {
                    	String sStaMakeDttm = sLine.substring(1, 9).trim();
                    	String sStaSendId   = sLine.substring(9, 19).trim();
                    	String sStaRecvDttm = sLine.substring(19, 27).trim();
                    	String sStaFiller   = sLine.substring(27, 200).trim();

                    	rowDataMst.put("sta_record_gb", sRecordGb);
                    	rowDataMst.put("sta_make_dttm", sStaMakeDttm);
                    	rowDataMst.put("sta_send_id"  , sStaSendId);
                    	rowDataMst.put("sta_recv_dttm", sStaRecvDttm);
                    	rowDataMst.put("sta_filler"   , sStaFiller);

                    	//System.out.println("마스터의 내용 ::: " + rowDataMst.toString());
                    	DlwCmsService.insertAcquResultDataMst(rowDataMst);
                    }
                    else if(sRecordGb.equals("D") == true)
                    {
                    	Map<String,Object> rowDataDtl = new HashMap<String, Object>();

                    	ParamUtils.addSaveParam(rowDataDtl);

                    	String sDataTid      = sLine.substring(1, 31).trim();
                    	String sDataFranId   = sLine.substring(31, 41).trim();
                    	String sDataTradGb   = sLine.substring(41, 42).trim();
                    	String sDataTradDttm = sLine.substring(42, 50).trim();
                    	String sDataPurcDttm = sLine.substring(50, 58).trim();
                    	String sDataAppyNum  = sLine.substring(58, 68).trim();
                    	String sDataPurcCrco = sLine.substring(68, 70).trim();
                    	String sDataCardGb   = sLine.substring(70, 71).trim();
                    	String sDataCardNum  = sLine.substring(71, 91).trim();
                    	String sDataPurcPay  = sLine.substring(91, 102).trim();
                    	String sDataFranFee  = sLine.substring(102, 112).trim();
                    	String sDataEtcFee   = sLine.substring(112, 122).trim();
                    	String sDataAmotPaid = sLine.substring(122, 133).trim();
                    	String sDataAmotDttm = sLine.substring(133, 141).trim();
                    	String sDataBlgb     = sLine.substring(141, 144).trim();
                    	String sDataFiller   = sLine.substring(144, 200).trim();
                    	String sRgsrId       = (String)rowDataDtl.get("rgsr_id");

                    	preparedStatement.setString(1, sRecordGb);
                    	preparedStatement.setString(2, sDataTid);
                        preparedStatement.setString(3, sDataFranId);
                        preparedStatement.setString(4, sDataTradGb);
                        preparedStatement.setString(5, sDataTradDttm);
                        preparedStatement.setString(6, sDataPurcDttm);
                        preparedStatement.setString(7, sDataAppyNum);
                        preparedStatement.setString(8, sDataPurcCrco);
                        preparedStatement.setString(9, sDataCardGb);
                        preparedStatement.setString(10, sDataCardNum);
                        preparedStatement.setInt(11, Integer.parseInt(sDataPurcPay));
                        preparedStatement.setInt(12, Integer.parseInt(sDataFranFee));
                        preparedStatement.setInt(13, Integer.parseInt(sDataEtcFee));
                        preparedStatement.setInt(14, Integer.parseInt(sDataAmotPaid));
                        preparedStatement.setString(15, sDataAmotDttm);
                        preparedStatement.setString(16, sDataBlgb);
                        preparedStatement.setString(17, sDataFiller);
                        preparedStatement.setString(18, sRgsrId);
  
                        iDataIdx++;

                        // addBatch에 담기
                        preparedStatement.addBatch();

                        // 파라미터 Clear
                        preparedStatement.clearParameters() ;


                        // OutOfMemory를 고려하여 만건 단위로 커밋
                        if( (iDataIdx % 1000) == 0)
                        {
                            // Batch 실행
                        	preparedStatement.executeBatch();

                            // Batch 초기화
                        	preparedStatement.clearBatch();

                            // 커밋
                        	connection.commit();
                        }

                    	//System.out.println("디테일의 내용 ::: " + rowDataDtl.toString());
                    	//DlwCmsService.insertAcquResultDataDtl(rowDataDtl);
                    }
                }

                preparedStatement.executeBatch() ;
                connection.commit() ;

                br.close();
                fr.close();

        	}
        	else
        	{
        		szErrorCode = "2";
                szErrorMsg = "이미 산출되어있는 수수료데이터 입니다. 중복산출은 불가능합니다.";
        	}

            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);
        }
        catch(SQLException sqle)
        {
        	if(connection != null)
        	{
        		connection.rollback() ;
        	}
            szErrorCode = "-1";
            szErrorMsg  = sqle.getMessage();
        }
        catch(IOException ioe)
        {
        	ioe.printStackTrace();
        	szErrorCode = "-1";
            szErrorMsg  = ioe.getMessage();
        }
        finally
        {
        	if(preparedStatement != null)
        	{
        		preparedStatement.close();
        	}
        	if(connection != null)
        	{
        		connection.close();
        	}
        	if(br != null)
        	{
        		br.close();
        	}
        	if(fr != null)
        	{
        		fr.close();
        	}
        	if(rafile != null)
        	{
        		rafile.close();
        	}
        }

        modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
        modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());
        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    @RequestMapping(value = "/acqusamsung/getAcquResultDataList")
    public ModelAndView getAcquResultDataList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMapMst = new DataSetMap();
        DataSetMap listMapDtl = new DataSetMap();
        DataSetMap listMapExcel = new DataSetMap();

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

            DataSetMap listInDsInput = (DataSetMap)mapInDs.get("ds_input");

            if (listInDsInput != null && listInDsInput.size() > 0)
            {
                hmParam = listInDsInput.get(0);
            }

            int nTotal = DlwCmsService.getAcquResultDataListTotalCnt(hmParam);
            mapOutVar.put("nTotalListCnt", nTotal);

            // 페이징 정보
            DataSetMap listInDsGds = (DataSetMap)mapInDs.get("gds_RequestCompVariable");

            if (listInDsGds != null && listInDsGds.size() > 0)
            {
                Map pMap = listInDsGds.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }

            String sDataTradGb = (String)hmParam.get("data_trad_gb");
            System.out.println("이값은 뭐지? " + sDataTradGb);
            if(sDataTradGb == null || sDataTradGb.equalsIgnoreCase(""))
            {
            	List<Map<String, Object>> mListMst = DlwCmsService.getAcquResultDataListTotalSummary2(hmParam);
            	listMapMst.setRowMaps(mListMst);
            }
            else
            {
            	List<Map<String, Object>> mListMst = DlwCmsService.getAcquResultDataListTotalSummary1(hmParam);
            	listMapMst.setRowMaps(mListMst);
            }

            List<Map<String, Object>> mListDtl = DlwCmsService.getAcquResultDataList(hmParam);
            listMapDtl.setRowMaps(mListDtl);
            //List<Map<String, Object>> mListExcel = DlwCmsService.getAcquResultDataListExcel(hmParam);
            //listMapExcel.setRowMaps(mListExcel);

            mapOutDs.put("ds_outputMst", listMapMst);
            mapOutDs.put("ds_outputDtl", listMapDtl);
            //mapOutDs.put("ds_outputExcel", listMapExcel);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

    @RequestMapping(value = "/acqusamsung/getAcquResultDataListExcel")
    public ModelAndView getAcquResultDataListExcel(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMapExcel = new DataSetMap();

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

            DataSetMap listInDsInput = (DataSetMap)mapInDs.get("ds_input");

            if (listInDsInput != null && listInDsInput.size() > 0)
            {
                hmParam = listInDsInput.get(0);
            }

            int nTotal = DlwCmsService.getAcquResultDataListTotalCnt(hmParam);
            mapOutVar.put("nTotalListCnt", nTotal);

            // 페이징 정보
            DataSetMap listInDsGds = (DataSetMap)mapInDs.get("gds_RequestCompVariable");

            List<Map<String, Object>> mListExcel = DlwCmsService.getAcquResultDataListExcel(hmParam);
            listMapExcel.setRowMaps(mListExcel);
            mapOutDs.put("ds_outputExcel", listMapExcel);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

    @RequestMapping(value = "/acqusamsung/getNotAuthResultDataList")
    public ModelAndView getNotAuthResultDataList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMapMst = new DataSetMap();
        DataSetMap listMapDtl = new DataSetMap();

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

            DataSetMap listInDsInput = (DataSetMap)mapInDs.get("ds_input");

            if (listInDsInput != null && listInDsInput.size() > 0)
            {
                hmParam = listInDsInput.get(0);
            }

            int nTotal = DlwCmsService.getNotAuthResultDataCount(hmParam);
            mapOutVar.put("nTotalListCnt2", nTotal);

            // 페이징 정보
            DataSetMap listInDsGds = (DataSetMap)mapInDs.get("gds_RequestCompVariable");

            if (listInDsGds != null && listInDsGds.size() > 0)
            {
                Map pMap = listInDsGds.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }
            
            String sDataTradGb = (String)hmParam.get("data_trad_gb");  //매입 or 취소 구분값
            
            if("2".equals(sDataTradGb)) { //거래구분 취소
            	List<Map<String, Object>> mListDtl = DlwCmsService.getNotAuthResultDataCancelList(hmParam);
                listMapDtl.setRowMaps(mListDtl);
                
                List<Map<String, Object>> mListMst = DlwCmsService.getNotAuthResultDataCancelListTotalSummary1(hmParam);
            	listMapMst.setRowMaps(mListMst);
            } else { 
            	List<Map<String, Object>> mListDtl = DlwCmsService.getNotAuthResultDataList(hmParam);
                listMapDtl.setRowMaps(mListDtl);
                
                List<Map<String, Object>> mListMst = DlwCmsService.getNotAuthResultDataListTotalSummary1(hmParam);
            	listMapMst.setRowMaps(mListMst);
            }
            
            
             
            /*
            if(sDataTradGb == null || sDataTradGb.equalsIgnoreCase(""))
            {
            	List<Map<String, Object>> mListMst = DlwCmsService.getNotAuthResultDataListTotalSummary2(hmParam);
            	listMapMst.setRowMaps(mListMst);
            }
            else
            {
            	List<Map<String, Object>> mListMst = DlwCmsService.getNotAuthResultDataListTotalSummary1(hmParam);
            	listMapMst.setRowMaps(mListMst);
            }
            */
                        
            mapOutDs.put("ds_outputMst", listMapMst);
            mapOutDs.put("ds_outputDtl", listMapDtl);
            
            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }
    
    @RequestMapping(value = "/acqusamsung/getAcquInicisResultDataList")
    public ModelAndView getAcquInicisResultDataList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMapMst = new DataSetMap();
        DataSetMap listMapDtl = new DataSetMap();
        DataSetMap listMapExcel = new DataSetMap();

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

            DataSetMap listInDsInput = (DataSetMap)mapInDs.get("ds_input");

            if (listInDsInput != null && listInDsInput.size() > 0)
            {
                hmParam = listInDsInput.get(0);
            }

            int nTotal = DlwCmsService.getAcquInicisResultDataListTotalCnt(hmParam);
            mapOutVar.put("nTotalListCnt", nTotal);

            // 페이징 정보
            DataSetMap listInDsGds = (DataSetMap)mapInDs.get("gds_RequestCompVariable");

            if (listInDsGds != null && listInDsGds.size() > 0)
            {
                Map pMap = listInDsGds.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }

            String sDataTradGb = (String)hmParam.get("data_trad_gb");

        	List<Map<String, Object>> mListMst = DlwCmsService.getAcquInicisResultDataListTotalSummary1(hmParam);
        	listMapMst.setRowMaps(mListMst);

            List<Map<String, Object>> mListDtl = DlwCmsService.getAcquInicisResultDataList(hmParam);
            listMapDtl.setRowMaps(mListDtl);

            mapOutDs.put("ds_outputMst", listMapMst);
            mapOutDs.put("ds_outputDtl", listMapDtl);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }
    
    @RequestMapping(value = "/loanPurchResultUpload")
    public void loanPurchResultUpload(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        Map<String, Object> mResult = new HashMap<>();
        String fileNm = "";

        PlatformData resData = new PlatformData();
        VariableList resVarList = resData.getVariableList();

        try
        {
        	DlwCmsService.loanPurchResultUpload(request, response, mResult);

            resVarList.add("ErrorCode", mResult.get("ErrorCode"));
            resVarList.add("ErrorMsg", mResult.get("ErrorMsg"));
        } 
        catch (EgovBizException e) 
        {
            resVarList.add("ErrorCode", mResult.get("ErrorCode"));
            resVarList.add("ErrorMsg", mResult.get("ErrorMsg"));
            e.printStackTrace();
        } 
        catch (Exception e) 
        {
            resVarList.add("ErrorCode", "-1");
            resVarList.add("ErrorMsg","업로드 중 오류가 발생하였습니다.");
            e.printStackTrace();
        }

        HttpPlatformResponse res = new HttpPlatformResponse(response);
        res.setData(resData);
        res.sendData();
    }
    
    @RequestMapping(value = "/acqusamsung/getLoanPurchResultDataList")
    public ModelAndView getLoanPurchResultDataList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMapMst = new DataSetMap();
        DataSetMap listMapDtl = new DataSetMap();
        DataSetMap listMapExcel = new DataSetMap();

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

            DataSetMap listInDsInput = (DataSetMap)mapInDs.get("ds_input");
            
            List<Map<String, Object>> mListMst = null;
            List<Map<String, Object>> mListDtl = null;
                        
            if (listInDsInput != null && listInDsInput.size() > 0){
                hmParam = listInDsInput.get(0);
            }
            
            //String connYn = (String)mapInVar.get("chk_conn").toString();
                        
            //hmParam.put("chk_conn", connYn);

            int nTotal = DlwCmsService.getLoanPurchResultDataTotalCnt(hmParam);
            mapOutVar.put("nTotalListCnt3", nTotal);

            // 페이징 정보
            DataSetMap listInDsGds = (DataSetMap)mapInDs.get("gds_RequestCompVariable");

            if (listInDsGds != null && listInDsGds.size() > 0)
            {
                Map pMap = listInDsGds.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }
            
            if (nTotal > 0){
	        	mListMst = DlwCmsService.getLoanPurchResultDataListTotalSummary(hmParam);
	        	listMapMst.setRowMaps(mListMst);	        	
	
	            mListDtl = DlwCmsService.getLoanPurchResultDataList(hmParam);
	            listMapDtl.setRowMaps(mListDtl);
            }

            mapOutDs.put("ds_outputMst", listMapMst);
            mapOutDs.put("ds_outputDtl", listMapDtl);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }
    
    /*
    @RequestMapping(value = "/acqusamsung/updateLoanPurchResultAccntNoRealTID")
    public ModelAndView updateLoanPurchResultAccntNoRealTID(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

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

            //hmParam = listInDs.get(0);
            ParamUtils.addSaveParam(hmParam);

            DlwCmsService.updateLoanPurchResultAccntNoRealTID(hmParam);

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
    */
    
    @RequestMapping(value = "/acqusamsung/getMemberWdrwResult")
    public ModelAndView getMemberWdrwResult(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();

        Map<String, Object> hmParam = new HashMap<String, Object>();
        List<Map<String, Object>> mList = null;

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try
        {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDsInput = (DataSetMap)mapInDs.get("ds_input");

            if (listInDsInput != null && listInDsInput.size() > 0)
            {
                hmParam = listInDsInput.get(0);
                
                String sDataTradGb = (String)hmParam.get("data_trad_gb");
                
                if(sDataTradGb.equals("0"))
                {
                	mList = DlwCmsService.getMemberWdrwResultAuthList(hmParam);
                }
                else if(sDataTradGb.equals("2"))
                {
                	mList = DlwCmsService.getMemberWdrwResultCancelList(hmParam);
                }
            }
            
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }
    
    @RequestMapping(value = "/acqusamsung/updateLoanPurchMemberWdrwResult")
    public ModelAndView updateLoanPurchMemberWdrwResult(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

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
            	ParamUtils.addSaveParam(hmParam);
            	
            	String sDataTradGb = (String)hmParam.get("data_trad_gb");
            	
            	if(sDataTradGb.equals("0"))
                {
            		DlwCmsService.updateLoanPurchMemberWdrwResultAuth(hmParam);
                }
            	else if(sDataTradGb.equals("2"))
            	{
            		DlwCmsService.updateLoanPurchMemberWdrwResultCancel(hmParam);
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
    
    /**
     * EB11 파일여부 확인 조회한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/eb11/getData")
    public ModelAndView getFileData(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            
            String strExtdt = mapInVar.get("ext_dt").toString();
            
            hmParam.put("ext_dt", strExtdt);            
            
            //int nTotal = DlwCmsService.getDlwCmsRgsnHstrCount(hmParam);

            List<Map<String, Object>> mComnList = DlwCmsService.getDlwCmsEb11List(hmParam);    

            ParamUtils.addCenterParam(hmParam);

            listMap.setRowMaps(mComnList);
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
     *  EB11정보 등록 및 CMS결제정보 수정
     * 임동진
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/eb11/dataInsert")
    public ModelAndView insertDlwCmsDataInsert(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            
            String strEmpleNo = "";
            String strExtdt = mapInVar.get("ext_dt").toString();            
                        		            
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            
            ParamUtils.addSaveParam(hmParam);
            strEmpleNo = hmParam.get("rgsr_id").toString();
            
            System.out.println("xxxxxxxxx " + listInDs.size());
                      
            for(int i=0; i<listInDs.size(); i++ ){
            	            	            	
            	hmParam = listInDs.get(i);	
            	hmParam.put("ext_dt", strExtdt);
            	hmParam.put("upd_man", strEmpleNo);
            	
            	DlwCmsService.insertDlwCmsDataInsert(hmParam); //EB11기초 데이터 INSERT           	            	
            }
            //[01] CMS결제정보이력저장
        	DlwCmsService.insertDlwCmsHistoryInsert(hmParam);
        	
            //[02] CMS계좌정보변경
            DlwCmsService.updateDlwCmsInfo(hmParam);
        	
            String cmsMsg = "[OK]";           

            mapOutVar.put("cmsReslMsg", cmsMsg);

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