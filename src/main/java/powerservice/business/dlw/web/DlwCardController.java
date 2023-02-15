/*
 * (@)# DlwCardController.java
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
import java.io.File;
import java.io.FileInputStream;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.bean.UserSession;
import powerservice.business.cns.service.ConsService;
import powerservice.business.cns.web.ConsController;
//2018.01.05 로그 추가
import powerservice.business.common.service.CommonService;
import powerservice.business.dlw.service.DataAthrQuryService;
import powerservice.business.dlw.service.DlwCardService;
import powerservice.business.dlw.service.DlwCmsService;
import powerservice.business.dlw.service.DlwCustService;
import powerservice.business.dlw.service.DlwEvntService;
import powerservice.common.util.CommonUtils;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;

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
 * 대명라이프웨이 Card 정보를 관리한다
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 석민
 * @version 1.0
 * @date 2016/02/02
 * @프로그램ID DlwCard
 */
@Controller
@RequestMapping(value = "/dlw/card")
public class DlwCardController {

    @Resource
    private DlwCardService DlwCardService;

    @Resource
    private DlwCustService dlwCustService;

    @Resource
    private DlwEvntService DlwEvntService;

    @Resource
    private DataAthrQuryService dataAthrQuryService;

    @Resource
    private ConsController consController;

    @Resource
    private ConsService consService;

    @Resource
    private DlwCmsService dlwCmsService;

    private final Logger log = LoggerFactory.getLogger(DlwCardController.class);

    @Resource
    private CommonService commonService;


    /**
     * 대명라이프웨이 Card 고객정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/card-csmm/list")
    public ModelAndView getDlwCardCsmmList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            int nTotal = DlwCardService.getDlwCardCsmmCount(hmParam);

            mapOutVar.put("tc_cardCsmm", nTotal);

            List<Map<String, Object>> mList = DlwCardService.getDlwCardCsmm(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            //2018.01.12 접속 로그//////////////////////////////////////////////////////////////////////////////
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
     * 대명라이프웨이 회원별 Card 신청내역 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/card-rgsn-hstr")
    public ModelAndView getDlwCardRgsnHstrList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            int nTotal = DlwCardService.getDlwCardRgsnHstrCount(hmParam);

            mapOutVar.put("tc_cardRgsnHstr", nTotal);

            List<Map<String, Object>> mList = DlwCardService.getDlwCardRgsnHstrList(hmParam);
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
    @RequestMapping(value = "/card-wdrw-hstr")
    public ModelAndView getDlwCardWdrwHstrList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            int nTotal = DlwCardService.getDlwCardWdrwHstrCount(hmParam);

            mapOutVar.put("tc_cardWdrwHstr", nTotal);

            List<Map<String, Object>> mList = DlwCardService.getDlwCardWdrwHstrList(hmParam);
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
     * 대명라이프웨이 카드 산출중 여부를 확인한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/card-wdrw-chk")
    public ModelAndView getDlwCardWdrwChk(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            hmParam.put("accnt_no", mapInVar.get("accnt_no"));

            String nResult = "";

            List<Map<String, Object>> mList = DlwCardService.getDlwCardWdrwChk(hmParam);
            if (mList != null && mList.size() > 0) {
                nResult = (String)mList.get(0).get("card_wdrw");
            }

            mapOutVar.put("cardWdrwChk", nResult);

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
     * 대명라이프웨이 카드 중복 여부를 확인한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/card-dplc-chk")
    public ModelAndView getDlwCardDplcChk(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();

            hmParam.put("card_no", mapInVar.get("card_no"));

            String nResult = "N";

            List<Map<String, Object>> mList = DlwCardService.getDlwCardDplcChk(hmParam);
            if (mList != null && mList.size() > 0) {
                nResult = (String)mList.get(0).get("card_dplc");
            }

            mapOutVar.put("cardDplcChk", nResult);

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
     * 대명라이프웨이 Card 이체정보를 저장한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/card-csmm/save")
    public ModelAndView insertCardTranInfo(XPlatformMapDTO xpDto, Model model) throws Exception {
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

           //     int rowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

             //   if ( rowType == DataSet.ROW_TYPE_INSERTED ){
                    //DlwCardService.insertCmsTranInfo(hmParam);
               // } else if (rowType == DataSet.ROW_TYPE_UPDATED){
                    DlwCardService.updateCardTranInfo(hmParam);
             //   }

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
     * 대명라이프웨이 Card 금일 등록이력을 조회한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/card-aplc_dtl/list")
    public ModelAndView getDlwCardAplcDtl(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            int nTotal = DlwCardService.getDlwCardAplcDtlCount(hmParam);

            mapOutVar.put("tc_cardAplcDtl", nTotal);

            List<Map<String, Object>> mList = DlwCardService.getDlwCardAplcDtl(hmParam);
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
     * 대명라이프웨이 Card (신규,해지)를 등록한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/card-anxt-srvc/save")
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

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            hmParam = listInDs.get(0);

            //세션
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("emple_no", hmParam.get("rgsr_id"));
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            hmParam.put("upd_man", hmParam.get("rgsr_id"));

            hmParam.put("paramEmpleNo", hmParam.get("rgsr_id"));
            hmParam.put("paramAs", "EMP");

            String cardMsg = "";
            cardMsg = DlwCardService.insertDlwCardAnxtSrvc(hmParam);
            mapOutVar.put("cardReslMsg", cardMsg);

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
     * 대명라이프웨이 Card  결과  디비 입력
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/insertCARDErrorInfo")
    public ModelAndView insertCARDErrorInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map<String, Object> hmParam = new HashMap<String, Object>();

        String chkType = request.getHeader("Content-Type");
        if (null == chkType) {
            return modelAndView;
        }

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        Map<String, Object> mResult = new HashMap<>();
        String fileNm = "";

        PlatformData resData = new PlatformData();
        VariableList resVarList = resData.getVariableList();

        try {

            log.debug("--------^^--------");

            String filename = CommonUtils.nvls(request.getParameter("fname"));
            if ("".equals(filename)) {
                throw new EgovBizException("fname is requried!");
            }

            mResult.put("filename", filename);
            DlwCardService.insertCARDErrorInfo(request, response, mResult);


        } catch (EgovBizException e) {
            resVarList.add("ErrorCode", "-1");
            resVarList.add("ErrorMsg", e.getMessage());
            e.printStackTrace();

        } catch (Exception e) {
            resVarList.add("ErrorCode", "-1");
            resVarList.add("ErrorMsg", e.getMessage());
            e.printStackTrace();
        }

        HttpPlatformResponse res = new HttpPlatformResponse(response);
        res.setData(resData);
        res.sendData();

        return modelAndView;
    }

    /**
     * 대명라이프웨이 여신 대비 카드 결제 로그조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/yeosin-log/list")
    public ModelAndView getDlwRltmYeosinLogList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            String excel_fg = (String)mapInVar.get("excel_fg");
            hmParam.put("excel_fg", excel_fg);

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

            if (!"Y".equals(excel_fg)) {
                int nTotal = DlwCardService.getDlwRltmYeosinLogCount(hmParam);

                mapOutVar.put("tc_log", nTotal);
                List<Map<String, Object>> mList = DlwCardService.getDlwRltmYeosinLogList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            }
            listMap2.setRowMaps(hmParam);
            mapOutDs.put("ds_output_excel", listMap2);


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

    /* 여신 카드 파일 업로드 */
    @RequestMapping(value = "/excel-upload")
    public void uploadToNas(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        Map<String, Object> mResult = new HashMap<>();
        String fileNm = "";

        PlatformData resData = new PlatformData();
        VariableList resVarList = resData.getVariableList();

        log.debug("--------^^--------");

        try {
            log.debug("uploadToYeosin 컨트롤러 - 1");
            DlwCardService.uploadToYeosin(request, response, mResult);
            log.debug("uploadToYeosin 컨트롤러 - 2");

            CommonUtils.printLog("씨팔");

            resVarList.add("ErrorCode"	, szErrorCode);
            resVarList.add("ErrorMsg"	, szErrorMsg);

        } catch (EgovBizException e) {
            resVarList.add("ErrorCode", "-1");
            resVarList.add("ErrorMsg", e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            resVarList.add("ErrorCode", "-1");
            resVarList.add("ErrorMsg","업로드 중 오류가 발생하였습니다.");
            e.printStackTrace();
        }

        HttpPlatformResponse res = new HttpPlatformResponse(response);
        res.setData(resData);
        res.sendData();

    }

    /* 여신 카드 파일 업로드 */
    @RequestMapping(value = "/csv-upload")
    public void uploadToCsv(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        Map<String, Object> mResult = new HashMap<>();
        String fileNm = "";

        PlatformData resData = new PlatformData();
        VariableList resVarList = resData.getVariableList();

        log.debug("--------^^--------");

        try {
            log.debug("uploadToYeosin 컨트롤러 - 1");
            DlwCardService.insertCARDErrorInfo(request, response, mResult);
            log.debug("uploadToYeosin 컨트롤러 - 2");

            CommonUtils.printLog("씨팔");

            resVarList.add("ErrorCode"	, szErrorCode);
            resVarList.add("ErrorMsg"	, szErrorMsg);

        } catch (EgovBizException e) {
            resVarList.add("ErrorCode", "-1");
            resVarList.add("ErrorMsg", e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            resVarList.add("ErrorCode", "-1");
            resVarList.add("ErrorMsg","업로드 중 오류가 발생하였습니다.");
            e.printStackTrace();
        }

        HttpPlatformResponse res = new HttpPlatformResponse(response);
        res.setData(resData);
        res.sendData();

    }


    /**
     * 대명라이프웨이 나이스페이 실시간 카드결제 로그를 조회한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/nicepay-rltm-card-log/list")
    public ModelAndView getDlwRltmCardLogList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            String excel_fg = (String)mapInVar.get("excel_fg");
            hmParam.put("excel_fg", excel_fg);

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

            if (!"Y".equals(excel_fg)) {
                int nTotal = DlwCardService.getDlwRltmCardLogCount(hmParam);

                mapOutVar.put("tc_log", nTotal);
                List<Map<String, Object>> mList = DlwCardService.getDlwRltmCardLogList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);

                //2018.01.11 접속 로그////////////////////////////////////////////////////////////////////////////////
                DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

                if (listLogIn.size() > 0) {
                    hmParam = listLogIn.get(0);
                    commonService.insertLog(hmParam);
                }
                //////////////////////////////////////////////////////////////////////////////////////////////////////
            }
            listMap2.setRowMaps(hmParam);
            mapOutDs.put("ds_output_excel", listMap2);

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
     * 대명라이프웨이 나이스페이 실시간카드결제 내역을 취소한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/pymn-canc-nice")
    public ModelAndView updateDlwPymnCancNicepay(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            /*36회 가맹점 변경건으로 인한 이슈 처리 중 취소 시 BKKYdmlife001m기준으로 가져왔던 bid를 맞추기 위해 아래 string ADD*/
            hmParam.put("bid", "CNCL" + hmParam.get("tid"));

            Map <String, Object> result = new HashMap();
            result = DlwCardService.updateDlwPymnCancNicepay(hmParam);


            saveConsDlwRtCard(result);


            mapOutVar.put("rslt_cd", result.get("result_code"));
            mapOutVar.put("rslt_nm", result.get("result_msg"));

            if (result.get("pay_result_msg") == null) {
                mapOutVar.put("rslt_msg", result.get("result_msg"));
            } else {
                mapOutVar.put("rslt_msg", result.get("pay_result_msg"));
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
     * 대명라이프웨이 빌키있는 회원인지 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/chk-bid")
    public ModelAndView getChkBid(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            String chkBid = DlwCardService.getChkBid(hmParam);

            mapOutVar.put("chkBid", chkBid);

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
     * 대명라이프웨이 Card 결과 파일 정보 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/card-info")
    public ModelAndView getCardInfoByAccnt(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            List<Map<String, Object>> mList = DlwCardService.getCardInfoByAccnt(hmParam);
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
     * 대명라이프웨이 회원번호로 회원상태 호출
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/accnt-stat")
    public ModelAndView getAccntStat(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            List<Map<String, Object>> mList = DlwCardService.getAccntStat(hmParam);
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
     * 대명라이프웨이 나이스페이 실시간 카드결제
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/nicepay-realtime")
    public ModelAndView insertRealTimeNicePay(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            Map<String, Object> resultMap = new HashMap<String, Object>();
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            hmParam = listInDs.get(0);

            //세션
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("emple_no", hmParam.get("rgsr_id"));
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            hmParam.put("upd_man", hmParam.get("rgsr_id"));
            hmParam.put("result_cd", "99");
            //로그 저장 (신청중)
            DlwCardService.insertRTCardPayLog(hmParam);

            //결제 요청
            resultMap = DlwCardService.insertRealTimeNicePay(hmParam);
            String result_cd = (String)resultMap.get("result_cd");
            if ("3001".equals(result_cd)) {
                resultMap.put("result_cd", "00");
                if("0001".equals(hmParam.get("menu_gbn"))) {
                    resultMap.put("mode", "insert");
                    //납입내역 저장
                    mergePayMngAfterRTCard(hmParam);
                }
                //상담저장
                //consController.saveConsdlw(hmParam);
            } else {
                resultMap.put("result_cd", "01");
                resultMap.put("result_msg", (new StringBuilder(String.valueOf(result_cd))).append(" : ").append((String)resultMap.get("result_msg")).toString());
            }
            resultMap.put("menu_gbn", hmParam.get("menu_gbn"));
            //로그 저장 (결과 업데이트)
            DlwCardService.updateRTCardPayResult(resultMap);

            mapOutVar.put("result_cd", resultMap.get("result_cd"));
            mapOutVar.put("result_msg", resultMap.get("result_msg"));

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
     * 대명라이프웨이 나이스페이 실시간 카드결제 (자유결제)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/nicepay-realtime-free")
    public ModelAndView insertRealTimeNicePayFree(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            Map<String, Object> resultMap = new HashMap<String, Object>();
            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            hmParam = listInDs.get(0);

            //세션
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("emple_no", hmParam.get("rgsr_id"));
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            hmParam.put("upd_man", hmParam.get("rgsr_id"));

            hmParam.put("result_cd", "99");

            int sCnt = 0;
            int fCnt = 0;
            String menu_gbn = "0005";
            String pmResultCd = "";
            String pmResultMsg = "";
            hmParam.put("menu_gbn", menu_gbn);

            List<Map<String, Object>> statList = DlwCardService.getAccntStatByFreeCard(hmParam);
            if(statList != null && statList.size() > 0) {
                hmParam.put("no", "0");
                hmParam.put("pay_no", "0");
                hmParam.put("pay_dtl_no", "0");
                hmParam.put("pay_dtl1_no", "0");
                hmParam.put("pay_cnt", "0");
                hmParam.put("bid", "");
                hmParam.put("auth_cd", "");
                hmParam.put("prod_cl", String.valueOf(statList.get(0).get("prod_cl")));
                hmParam.put("email", String.valueOf(statList.get(0).get("email")));
                hmParam.put("cell", String.valueOf(statList.get(0).get("cell")));
                hmParam.put("prod_nm", String.valueOf(statList.get(0).get("prod_nm")));
                hmParam.put("pay_day", String.valueOf(statList.get(0).get("pay_day")));
                hmParam.put("mem_no", String.valueOf(statList.get(0).get("mem_no")));
                hmParam.put("del_fg", "N");
                hmParam.put("pay_mthd", "06");
                hmParam.put("mode", "insert");

                //로그 저장 (신청중)
                DlwCardService.insertRTCardPayLog(hmParam);

                try
                {
                    //결제
                    resultMap = DlwCardService.billPayFreeProc(hmParam);

                    String result_cd = (String)resultMap.get("result_cd");
                    if(result_cd == null || "".equals(result_cd) || !"3001".equals(result_cd)) {
                        result_cd = "01";
                    }
                    if("3001".equals(result_cd))
                    {
                        resultMap.put("result_cd", "00");
                        pmResultCd = "00";
                        sCnt++;
                    } else
                    {
                        resultMap.put("result_msg", (new StringBuilder(String.valueOf(result_cd))).append(" : ").append((String)resultMap.get("result_msg")).toString());
                        resultMap.put("result_cd", "01");
                        pmResultCd = "01";
                        pmResultMsg = (String)resultMap.get("result_msg");
                        resultMap.put("auth_cd", "");
                        resultMap.put("tid", "");
                        resultMap.put("uip", "");
                        fCnt++;
                    }
                    resultMap.put("accntNo", (String)hmParam.get("accnt_no"));
                    resultMap.put("menu_gbn", menu_gbn);
                    DlwCardService.updateRTCardPayResult(resultMap);
                }
                catch(Exception e)
                {
                    fCnt++;
                    resultMap.put("result_cd", "01");
                    resultMap.put("result_msg", "[Exception]개발자문의");
                    pmResultCd = "01";
                    pmResultMsg = (String)resultMap.get("result_msg");
                    resultMap.put("accnt_no", (String)hmParam.get("accnt_no"));
                    resultMap.put("menu_gbn", menu_gbn);
                    resultMap.put("auth_cd", "");
                    resultMap.put("tid", "");
                    resultMap.put("uip", "");
                    DlwCardService.updateRTCardPayResult(resultMap);
                }
            } else {
                hmParam.put("result_cd", "01");
                hmParam.put("result_msg", "회원상태 이상 [관리자 문의]");
                pmResultCd = "01";
                pmResultMsg = (String)resultMap.get("result_msg");
                hmParam.put("auth_cd", "");
                hmParam.put("tid", "");
                hmParam.put("uip", "");
                DlwCardService.updateRTCardPayResult(hmParam);
            }
            String msg = "";
            if(sCnt > 0) {
                msg = "[성공]";
            } else {
                msg = "[실패]";
            }
            mapOutVar.put("result_cd", pmResultCd);
            mapOutVar.put("result_msg", pmResultMsg);
            mapOutVar.put("msg", msg);

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

    public Map <String, Object> mergePayMngAfterRTCard(Map <String, Object> pmParam)throws Exception {
        String prod_cl = DlwCardService.getProdClByAccntNo(pmParam);
        String gbn = (String)pmParam.get("gbn");

        if("03".equals(prod_cl)) {
            if("prod".equalsIgnoreCase(gbn)) {
                mergePayMng(pmParam);
                pmParam.put("no", pmParam.get("pay_dtl_no"));
                mergePayMngDtl(pmParam);
                pmParam.put("no", pmParam.get("pay_dtl1_no"));
                mergePayMngDtl1(pmParam);
            } else if ("dtl".equals(gbn)) {
                pmParam.put("no", pmParam.get("pay_dtl_no"));
                mergePayMngDtl(pmParam);
                pmParam.put("no", pmParam.get("pay_dtl1_no"));
                mergePayMngDtl1(pmParam);
            }
        } else {
            mergePayMng(pmParam);
        }
        pmParam.put("type", "card");

        //상담 중복저장되어 임시 주석처리
        saveConsDlwRtCard(pmParam);

        return pmParam;
    }

    /**
     * 부가정보를 조회한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/buga-info")
    public ModelAndView getBugaInfo(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            String sAccntNo = StringUtils.defaultString((String) mapInVar.get("accnt_no"));
            if ("".equals(sAccntNo)) {
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, "-1");
                modelAndView.addObject(XPlatformConstant.ERROR_MSG, "회원번호가 없습니다.");

                return modelAndView;
            }

            hmParam.put("accnt_no", sAccntNo);
            List<Map<String, Object>> mList = DlwCardService.getBugaInfo(hmParam);
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
     * 대명라이프웨이 입금점표 등록/수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public Map <String, Object> mergePayMng(Map <String, Object> pmParam)throws Exception {
        pmParam.put("resn_yn", "N");
        List<Map<String, Object>> bugaList = DlwCardService.getBugaInfo(pmParam);

        if(bugaList.size() > 0) {
            Map <String, Object> tmpMap = bugaList.get(0);
            if("Y".equals(tmpMap.get("resn_yn")))
            {
                pmParam.put("resn_yn", "Y");
                return pmParam;
            }
        }

        String newYn = "";
        if("D".equals((String)pmParam.get("cl1")) && "N".equals(DlwCardService.getpayNewYnChk(pmParam))) {
            pmParam.put("new_yn", "N");
            return pmParam;
        }
        String empleNo = (String)pmParam.get("emple_no");
        if(empleNo == null) {
            UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
            empleNo = oLoginUser.getUserid();
        }
        String chk = closeDataSaveYnChk("0001", (String)pmParam.get("accnt_no"), (String)pmParam.get("pay_day"), empleNo);
        if("N".equals(chk)) {
            pmParam.put("edt_yn", "N");
            return pmParam;
        } else {
            DlwCardService.mergePayMng(pmParam);
            return pmParam;
        }
    }

    /**
     * 대명라이프웨이 입금점표 등록/수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public Map <String, Object> mergePayMngDtl(Map <String, Object> pmParam)throws Exception {
          String empleNo = "";
          if((String)pmParam.get("emple_no") == null) {
              UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
              empleNo = oLoginUser.getUserid();
          } else {
              //empleNo = (String)pmParam.get("regMan");
              empleNo = (String)pmParam.get("emple_no");
          }

          if("N".equals(closeDataSaveYnChk("0001", (String)pmParam.get("accnt_no"), (String)pmParam.get("pay_day"), empleNo))) {
              pmParam.put("edt_yn", "N");
              return pmParam;
          } else {
              DlwCardService.mergePayMngDtl(pmParam);
              return pmParam;
          }
    }

    /**
     * 대명라이프웨이 입금점표 등록/수정
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public Map <String, Object> mergePayMngDtl1(Map <String, Object> pmParam)throws Exception {
        String empleNo = "";
        if((String)pmParam.get("emple_no") == null) {
            UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
            empleNo = oLoginUser.getUserid();
        } else {
            //empleNo = (String)pmParam.get("regMan");
            empleNo = (String)pmParam.get("emple_no");
        }

        if("N".equals(closeDataSaveYnChk("0001", (String)pmParam.get("accnt_no"), (String)pmParam.get("pay_day"), empleNo))) {
            pmParam.put("edt_yn", "N");
            return pmParam;
        } else {
            DlwCardService.mergePayMngDtl1(pmParam);
            return pmParam;
        }
    }

    /**
     * 대명라이프웨이 마감권한 체크
     *
     * @param pmParam 검색 조건
     * @return
     * @throws Exception
     */
    public String closeDataSaveYnChk(String closeCl, String accntNo, String closeDt, String empleNo) throws Exception {
            String edt_yn = "";
            Map <String, Object> hmParam = new HashMap<String, Object>();
            hmParam.put("close_cl", closeCl);
            hmParam.put("accnt_no", accntNo);
            hmParam.put("close_dt", closeDt);
            hmParam.put("emple_no", empleNo);
            edt_yn = DlwEvntService.getCloseDataSaveYnChk(hmParam);
            return edt_yn;
    }

    /**
     * 상담 정보를 등록/수정한다. (대명 - 카드결제 시)
     *
     * @param pmParam Map<String, Object>
     * @return pmParam Map<String, Object>
     * @throws Exception
     */
    public Map <String, Object> saveConsDlwRtCard( Map <String, Object> pmParam) throws Exception {
        String cnslMngCon = "[실시간] 카드 결제";
        String cnslDtlCon = "";

        String type = (String)pmParam.get("type");

        if ("card".equals((String)pmParam.get("type"))) {
            cnslDtlCon = (new StringBuilder("[실시간]카드결제일 : ")).append((String)pmParam.get("pay_day")).append("  카드번호 : ").append((String)pmParam.get("card_no")).append("  결제금액 : ").append((String)pmParam.get("pay_amt")).toString();
        } else if ("card_fail".equals(type)){
            cnslMngCon = (new StringBuilder(String.valueOf(cnslMngCon))).append(" 실패").toString();
        } else if ("card_err".equals(type)) {
            cnslMngCon = (new StringBuilder(String.valueOf(cnslMngCon))).append(" 오류").toString();
        } else if ("cancel".equals(type)) {
            cnslMngCon = (new StringBuilder(String.valueOf(cnslMngCon))).append(" 취소").toString();
        } else if("cancel_fail".equals(type)) {
            cnslMngCon = (new StringBuilder(String.valueOf(cnslMngCon))).append(" 취소 실패").toString();
        } else if("cancel_err".equals(type)) {
            cnslMngCon = (new StringBuilder(String.valueOf(cnslMngCon))).append(" 취소 오류").toString();
        } else {
            cnslMngCon = (new StringBuilder(String.valueOf(cnslMngCon))).append(" 기타 오류").toString();
        }

        pmParam.put("cons_memo", cnslMngCon);
        pmParam.put("cnsl_con", cnslDtlCon);

        Map<String, Object> mCust = dlwCustService.getCustBasiConsInfo(pmParam);

        //회원정보 없을경우 상담저장 안함
        if (mCust != null && mCust.size() > 0) {
            consController.saveConsdlw(pmParam);
        }

        return pmParam;
    }

    /**
     * 대명라이프웨이 Card 신청내역조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/wdrw-hstr/list")
    public ModelAndView getDlwWdrwHstrEb21List(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap listMap1 = new DataSetMap();
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

            if (listInDs != null &&listInDs.size() > 0) {
                hmParam = listInDs.get(0);
            }

            // 페이징 정보
            DataSetMap srchInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
            if (srchInDs != null && srchInDs.size() > 0) {
                Map pMap = srchInDs.get(0);


           //     CommonUtils.printLog(pMap);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }


            int nTotal = DlwCardService.getCardWdrwReqcount(hmParam);

            mapOutVar.put("tc_condMem", nTotal);





            List<Map<String, Object>> mList = DlwCardService.getCardWdrwReqList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            ///  회원수, 청구납입횟수, 청구납입금액

            ///System.out.println("111111111111111");
            List<Map<String, Object>> mList1 = DlwCardService.getCardWdrwReqList_sum(hmParam);
            listMap1.setRowMaps(mList1);
            mapOutDs.put("ds_output1", listMap1);
            ///System.out.println("2222222222222");

            //2018.01.19 접속 로그//////////////////////////////////////////////////////////////////////////////
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
     * 대명라이프웨이 Card 신청내역조회 1건
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/wdrw-hstr/list_cnt")
    public ModelAndView getDlwWdrwHstrEb21cnt(XPlatformMapDTO xpDto, Model model) throws Exception {
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

         //   DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

            hmParam.put("accnt_no", mapInVar.get("accnt_no"));
            hmParam.put("seq",'0');

            //if (hmParam != null &&hmParam.size() > 0) {
            //    hmParam = listInDs.get(0);
            //}
           // CommonUtils.printLog(hmParam);

            List<Map<String, Object>> mList = DlwCardService.getCardWdrwReqList(hmParam);
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
     * 대명라이프웨이 산출데이터 할부건수를 업데이트한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = " /wdrw-tran-dtl/install-period-update")
    public ModelAndView updateDlwInstallPeriod(XPlatformMapDTO xpDto, Model model) throws Exception {
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


                int rowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();



                String seq = StringUtils.defaultString(String.valueOf(hmParam.get("seq")));
                String accnt_no = StringUtils.defaultString(String.valueOf(hmParam.get("accnt_no")));
                String inv_gunsu = StringUtils.defaultString(String.valueOf(hmParam.get("inv_gunsu")));
                String install_period = StringUtils.defaultString(String.valueOf(hmParam.get("install_period")));
                String mon_pay_amt = StringUtils.defaultString(String.valueOf(hmParam.get("mon_pay_amt")));
                String expr_no = StringUtils.defaultString(String.valueOf(hmParam.get("expr_no")));
                String inv_no = StringUtils.defaultString(String.valueOf(hmParam.get("inv_no")));

                hmParam.put("seq", seq);
                hmParam.put("accnt_no", accnt_no);
                hmParam.put("inv_gunsu", inv_gunsu);
                hmParam.put("install_period", install_period);
                hmParam.put("mon_pay_amt", mon_pay_amt);
                hmParam.put("expr_no", expr_no);
                hmParam.put("inv_no", inv_no);

                if (rowType == DataSet.ROW_TYPE_UPDATED) {
                    boolean update_fg = true;
                    if (1 == DlwCardService.updateInstallPeriodForCard(hmParam)) {
                        update_fg = true;
                    };

                    if (update_fg) {
                        msg = "ok";
                    } else {
                        msg = "err";
                    }

                }

                List<Map<String, Object>> mList = null;

                mList = DlwCardService.getCardWdrwReqList(hmParam);

                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);

                //message
                mapOutVar.put("update_period_msg", msg);
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
     * 대명라이프웨이 실시간 배치 결제
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/batch-card")
    public ModelAndView batchNiceRealtimeCard(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

            int batch_cnt = Integer.parseInt((String)mapInVar.get("batch_cnt"));
            String billGubun = "00";
            int sCnt = 0;
            int fCnt = 0;

            for (int i = 0; i < batch_cnt; i++) {
                /************************************************/
                //Thread.sleep(500L);
                Map<String, Object> hmParam = new HashMap<String, Object>();
                hmParam = listInDs.get(i);
                //실시간 배치 결제 : 0003
                hmParam.put("menu_gbn", "0003");
                hmParam.put("dc_no", String.valueOf(hmParam.get("install_period")));
                hmParam.put("card_quota", String.valueOf(hmParam.get("install_period")));
                hmParam.put("pay_cnt", String.valueOf(hmParam.get("inv_gunsu")));
                hmParam.put("mon_cnt", String.valueOf(hmParam.get("inv_gunsu")));
                hmParam.put("pay_amt", String.valueOf(hmParam.get("wdrw_req_amt")));

                hmParam.put("gbn_batch", "batch");
                //세션
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("emple_no", hmParam.get("rgsr_id"));
                hmParam.put("reg_man", hmParam.get("rgsr_id"));
                hmParam.put("upd_man", hmParam.get("rgsr_id"));

                //산출 된 회원 정보 가져오기
                List<Map<String, Object>> dtlList = DlwCardService.getAccntStat(hmParam);

                if(dtlList.size() > 0) {
                    Map<String, Object> tmpParam = dtlList.get(0);

                    hmParam.put("no", String.valueOf(tmpParam.get("pay_no")));
                    hmParam.put("pay_no", String.valueOf(tmpParam.get("pay_no")));
                    hmParam.put("pay_dtl_no", String.valueOf(tmpParam.get("pay_dtl_no")));
                    hmParam.put("pay_dtl1_no", String.valueOf(tmpParam.get("pay_dtl1_no")));
                    hmParam.put("prod_cl", String.valueOf(tmpParam.get("prod_cl")));
                    hmParam.put("gbn", String.valueOf(tmpParam.get("gbn")));
                    hmParam.put("email", String.valueOf(tmpParam.get("email")));
                    hmParam.put("cell", String.valueOf(tmpParam.get("cell")));
                    hmParam.put("prod_nm", String.valueOf(tmpParam.get("prod_nm")));
                    hmParam.put("pay_day", String.valueOf(tmpParam.get("pay_day")));
                    hmParam.put("mem_no", String.valueOf(tmpParam.get("mem_no")));
                    hmParam.put("del_fg", "N");
                    hmParam.put("pay_mthd", "06");
                    hmParam.put("mode", "insert");

                    //임동진 수정 (거래구분 설정)
                    String prodCd = (String)tmpParam.get("prod_cd");

                    if( "M2".equals(prodCd) || "F4".equals(prodCd) ){
                        if(Integer.parseInt(String.valueOf(hmParam.get("pay_no"))) < 36){
                            billGubun ="11";
                        }else if(Integer.parseInt(String.valueOf(hmParam.get("pay_no"))) == 36){
                            billGubun ="12";
                        }else{
                            billGubun ="00";
                        }
                    }else if("AO".equals(prodCd)){
                        if(Integer.parseInt(String.valueOf(hmParam.get("pay_no"))) <= 36){
                            billGubun ="21";
                        }else{
                            billGubun ="00";
                        }
                    }
                    //2017.09.19 CO,CP 추가
                    else if("CO".equals(prodCd))
                    {
                        if(Integer.parseInt(String.valueOf(hmParam.get("pay_no"))) <= 24){
                            billGubun ="26";
                        }else{
                            billGubun ="00";
                        }
                    }
                    else if("CP".equals(prodCd))
                    {
                        if(Integer.parseInt(String.valueOf(hmParam.get("pay_no"))) <= 24){
                            billGubun ="27";
                        }else{
                            billGubun ="00";
                        }
                    }
                    //2018.05.02 SDP추가
                    else if( "X5".equals(prodCd) || "S7".equals(prodCd) || "S8".equals(prodCd) || "EC".equals(prodCd) || "EE".equals(prodCd) )
                    {
                        if(Integer.parseInt(String.valueOf(hmParam.get("pay_no"))) <= 36){
                            billGubun ="999";
                        }else{
                            billGubun ="000";
                        }
                    }
                    //2018.05.31 SDP추가
                    else if( "CM".equals(prodCd) || "EG".equals(prodCd) )
                    {
                        if(Integer.parseInt(String.valueOf(hmParam.get("pay_no"))) <= 24){
                            billGubun ="999";
                        }else{
                            billGubun ="000";
                        }
                    }
                    else{
                        billGubun ="00";
                    }
                    hmParam.put("billGubun", billGubun);

                    //로그저장
                    DlwCardService.insertRTCardPayLog(hmParam);

                    //결제모듈 호출
                    Map<String, Object> resultParam = new HashMap<String, Object>();
                    try {
                        resultParam = DlwCardService.billPayBatchProc(hmParam);

                        String result_cd = (String)resultParam.get("result_cd");
                        if(result_cd.equals("3001"))
                        {
                            resultParam.put("type", "card");
                            //납입내역 저장
                            mergePayMngAfterRTCard(resultParam);
                            resultParam.put("stat", "04");
                        } else
                        {
                            resultParam.put("stat", "03");
                        }
                        resultParam.put("pay_no", String.valueOf(resultParam.get("inv_no")));
                        DlwCardService.updateCardWdrwReq(hmParam);

                        if(result_cd == null || "".equals(result_cd))
                            result_cd = "01";
                        if(result_cd.equals("3001"))
                        {
                            resultParam.put("result_cd", "00");
                            sCnt++;
                        } else
                        {
                            resultParam.put("result_cd", (new StringBuilder(String.valueOf(result_cd))).append(" : ").append((String)resultParam.get("result_cd")).toString());
                            resultParam.put("result_cd", "01");
                            fCnt++;
                        }
                        resultParam.put("accnt_no", (String)hmParam.get("accnt_no"));
                        resultParam.put("menu_gbn", "0003");
                        DlwCardService.updateRTCardPayResult(hmParam);
                    }
                    catch(Exception e) {
                        fCnt++;
                        hmParam.put("pay_no", String.valueOf(hmParam.get("inv_no")));
                        hmParam.put("stat", "03");
                        hmParam.put("result_cd", "01");
                        DlwCardService.updateCardWdrwReq(hmParam);
                        resultParam.put("result_cd", "01");
                        resultParam.put("result_msg", "[Exception]개발자문의");
                        resultParam.put("accnt_no", (String)hmParam.get("accnt_no"));
                        resultParam.put("menu_gbn", "0003");
                        DlwCardService.updateRTCardPayResult(hmParam);
                    }
                }




                else {
                    fCnt++;
                    hmParam.put("pay_no", String.valueOf(hmParam.get("inv_no")));
                    hmParam.put("stat", "03");
                    hmParam.put("result_cd", "01");
                    DlwCardService.updateCardWdrwReq(hmParam);
                }
                /************************************************/
            }
            String msg = (new StringBuilder("[성공] ")).append(String.valueOf(sCnt)).append("건, [실패]").append(String.valueOf(fCnt)).append("건").toString();
            mapOutVar.put("batch_msg", msg);

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
     * 대명라이프웨이 NiceCard 파일변환 파일명을 조회한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/nice-card/file-nm-list")
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

            List<Map<String, Object>> mList = DlwCardService.getReadNiceCardResultFileName(hmParam);

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
     * 대명라이프웨이 NiceCard 파일 결과처리 여부 조회한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/nice-card/file-is-trans")
    public ModelAndView getNiceCardFileisTrans(XPlatformMapDTO xpDto, Model model) throws Exception {
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
       //     String fileNm = (String)mapInVar.get("file_nm");
         //   fileNm = fileNm.toUpperCase().replace(".XLS", "");
           // mapOutVar.put("trans_yymmdd", yymmdd);

            hmParam.put("fileNm", (String)mapInVar.get("file_nm"));
            hmParam.put("result", "");

       //     CommonUtils.printLog(hmParam);

            System.out.println("------------ 1");
           // dlwCmsService.getCmsAppIsTransEb22(hmParam);
            DlwCardService.getCARDAppIsTransEb22(hmParam);


            String stat = (String)hmParam.get("result");

            System.out.println("------------ 3");
            System.out.println("stat : " + stat);

            String trans_yn = "";
            System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
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
     * 대명라이프웨이 NiceCard 파일을 읽어온다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/nice-card/read-file")
    public ModelAndView getNiceCardReadFile(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            String fileNm = (String)mapInVar.get("file_nm");

            int normalCnt = 0;
            int errCnt = 0;
            hmParam.put("file_nm", fileNm);


            // 페이징 정보
            DataSetMap srchInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
            if (srchInDs != null && srchInDs.size() > 0) {
                Map pMap = srchInDs.get(0);


           //     CommonUtils.printLog(pMap);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }

            int nTotal = DlwCardService.getReadFileNiceCardcount(hmParam);

            mapOutVar.put("tc_condMem", nTotal);


            List<Map<String, Object>> mList = DlwCardService.getReadFileNiceCard(hmParam);

            if(mList != null && mList.size() > 0) {
                for (int i = 0; i < mList.size(); i++) {
                    Map<String, Object> tmpParam = mList.get(i);
                    String result_cd = String.valueOf(tmpParam.get("result_cd")).replaceAll(" ", "");
                    if ("3001".equals(result_cd)) {
                        normalCnt++;
                    } else {
                        errCnt++;
                    }
                }
            }

            mapOutVar.put("nomalCnt", normalCnt);
            mapOutVar.put("errCnt", errCnt);

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
     * 대명라이프웨이 NiceCard 파일을 읽어온다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/nice-card/convert-file")
    public ModelAndView getNiceCardConvertFile(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            InetAddress inet = InetAddress.getLocalHost();
            String uip = inet.getHostAddress();

            //세션
            hmParam.put("file_nm", mapInVar.get("file_nm"));
            hmParam.put("pay_dt", mapInVar.get("pay_dt"));
            hmParam.put("uip", uip);
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("reg_man", hmParam.get("rgsr_id"));
            DlwCardService.niceCardResultProc(hmParam);


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
     * 스마트라이프 카드 저장
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/smart-life/card-save")
    public ModelAndView saveSmartLifeCard(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            if (listInDs != null && listInDs.size() > 0) {
                Map hmParam = listInDs.get(0);

                ParamUtils.addSaveParam(hmParam);

                hmParam.put("emple_no", hmParam.get("amnd_id"));
                mapOutVar.put("gv_rslMsg", DlwCardService.saveSmartLifeCard(hmParam));
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
     * 대명라이프웨이 나이스페이 카드 유효성을 확인한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/vld-insp")
    public ModelAndView getNiceVldInsp(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            String nResult = "";

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            if (listInDs != null && listInDs.size() > 0) {
                hmParam = listInDs.get(0);

                nResult = DlwCardService.getNiceVldInsp(hmParam);
            }

            mapOutVar.put("niceVldInspMsg", nResult);

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
     * 대명라이프웨이 입금정보를 저장한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/merge-paymng")
    public ModelAndView mergePayMngReq(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            DataSetMap listInDs2 = (DataSetMap)mapInDs.get("ds_input2"); // chg

            if (listInDs != null && listInDs.size() > 0) {
                hmParam = listInDs.get(0);

                //세션
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("emple_no", hmParam.get("rgsr_id"));
                hmParam.put("reg_man", hmParam.get("rgsr_id"));
                hmParam.put("upd_man", hmParam.get("rgsr_id"));

                if (listInDs2 != null && listInDs2.size() > 0) {
                    List<Map<String, Object>> hmChgACList = new ArrayList<Map<String,Object>>();
                    for (int i = 0; i < listInDs2.size(); i++) {
                        Map<String, Object> hmChgACmap = new HashMap<String, Object>();
                        hmChgACmap = listInDs2.get(i);
                        hmChgACList.add(i, hmChgACmap);
                    }
                    hmParam.put("chgAC", hmChgACList);
                }

                mergePayMng(hmParam);

                mapOutVar.put("r_resn_yn", hmParam.get("resn_yn"));
                mapOutVar.put("r_new_yn", hmParam.get("new_yn"));
                mapOutVar.put("r_edt_yn", hmParam.get("edt_yn"));
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
     * 대명라이프웨이 입금정보를 저장한다. - 결합상품할부원금
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/merge-paymng-dtl")
    public ModelAndView mergePayMngDtlReq(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            DataSetMap listInDs2 = (DataSetMap)mapInDs.get("ds_input2"); // chg

            if (listInDs != null && listInDs.size() > 0) {
                hmParam = listInDs.get(0);

                //세션
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("emple_no", hmParam.get("rgsr_id"));
                hmParam.put("reg_man", hmParam.get("rgsr_id"));
                hmParam.put("upd_man", hmParam.get("rgsr_id"));

                if (listInDs2 != null && listInDs2.size() > 0) {
                    List<Map<String, Object>> hmChgACList = new ArrayList<Map<String,Object>>();
                    for (int i = 0; i < listInDs2.size(); i++) {
                        Map<String, Object> hmChgACmap = new HashMap<String, Object>();
                        hmChgACmap = listInDs2.get(i);
                        hmChgACList.add(i, hmChgACmap);
                    }
                    hmParam.put("chgAC", hmChgACList);
                }

                mergePayMngDtl(hmParam);

                mapOutVar.put("r_resn_yn", hmParam.get("resn_yn"));
                mapOutVar.put("r_new_yn", hmParam.get("new_yn"));
                mapOutVar.put("r_edt_yn", hmParam.get("edt_yn"));
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
     * 대명라이프웨이 입금정보를 저장한다. - 결합상품추가부담금
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/merge-paymng-dtl1")
    public ModelAndView mergePayMngDtl1Req(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            DataSetMap listInDs2 = (DataSetMap)mapInDs.get("ds_input2"); // chg

            if (listInDs != null && listInDs.size() > 0) {
                hmParam = listInDs.get(0);

                //세션
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("emple_no", hmParam.get("rgsr_id"));
                hmParam.put("reg_man", hmParam.get("rgsr_id"));
                hmParam.put("upd_man", hmParam.get("rgsr_id"));

                if (listInDs2 != null && listInDs2.size() > 0) {
                    List<Map<String, Object>> hmChgACList = new ArrayList<Map<String,Object>>();
                    for (int i = 0; i < listInDs2.size(); i++) {
                        Map<String, Object> hmChgACmap = new HashMap<String, Object>();
                        hmChgACmap = listInDs2.get(i);
                        hmChgACList.add(i, hmChgACmap);
                    }
                    hmParam.put("chgAC", hmChgACList);
                }

                mergePayMngDtl1(hmParam);

                mapOutVar.put("r_resn_yn", hmParam.get("resn_yn"));
                mapOutVar.put("r_new_yn", hmParam.get("new_yn"));
                mapOutVar.put("r_edt_yn", hmParam.get("edt_yn"));
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
     * 카드파일 다운로드
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/carddownloadFile")
    public void carddownloadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
                successPath = EgovProperties.getProperty("card.down.file.windows.successPath");
            } else {
                successPath = EgovProperties.getProperty("card.down.file.unix.successPath");
            }



            String srcFilePath = successPath +"dmlife001m"+ yymmdd+".txt";


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


            System.out.println("contentType : " + contentType);
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
     * 대명라이프웨이 Card 신청내역조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/wdrw-hstr/listBasic")
    public ModelAndView getDlwWdrwHstrEb21List_Basic(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap listMap1 = new DataSetMap();
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

            if (listInDs != null &&listInDs.size() > 0) {
                hmParam = listInDs.get(0);
            }

            // 페이징 정보
            DataSetMap srchInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
            if (srchInDs != null && srchInDs.size() > 0) {
                Map pMap = srchInDs.get(0);


           //     CommonUtils.printLog(pMap);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }


            int nTotal = DlwCardService.getCardWdrwReqcount_Basic(hmParam);

            mapOutVar.put("tc_condMem", nTotal);

            List<Map<String, Object>> mList = DlwCardService.getCardWdrwReqList_Basic(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            ///  회원수, 청구납입횟수, 청구납입금액
            List<Map<String, Object>> mList1 = DlwCardService.getCardWdrwReqList_sum(hmParam);
            listMap1.setRowMaps(mList1);
            mapOutDs.put("ds_output1", listMap1);

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