/*
 * (@)# DlwEvntController.java
 *
 * @author 석민
 * @version 1.0
 * @date 2016/03/30
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
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.bean.UserSession;
import powerservice.business.common.service.CommonService;
import powerservice.business.dlw.service.DataAthrQuryService;
import powerservice.business.dlw.service.DlwCmsService;
import powerservice.business.dlw.service.DlwEvntService;
import powerservice.business.dlw.service.DlwPayService;
import powerservice.business.mall.service.DlwMallLinkedService;
import powerservice.business.sys.web.DmWebSenderController;
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

/**
 * 대명라이프웨이 행사 정보를 관리한다
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 석민
 * @version 1.0
 * @date 2016/03/30
 * @프로그램ID DlwEvnt
 */
@Controller
@RequestMapping(value = "/dlw/evnt")
public class DlwEvntController {

    @Resource
    private DlwEvntService DlwEvntService;

    @Resource
    private DataAthrQuryService dataAthrQuryService;

    @Resource
    private CommonService commonService;

    @Resource
    private DlwCmsService DlwCmsService;

    @Resource
    private DlwPayService dlwPayService;

    @Resource
    private DmWebSenderController dlwWebSenderController;
    
    @Resource
    private DlwMallLinkedService DlwMallLinkedService;
    /**
     * 대명라이프웨이 행사 정보를 검색한다.
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public ModelAndView getEvntList(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap listStatMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            if (listInDs.size() > 0) {
                hmParam = listInDs.get(0);
                // 페이징 정보
                DataSetMap listInGds = (DataSetMap) mapInDs
                        .get("gds_RequestCompVariable");
                if (listInGds != null && listInGds.size() > 0) {
                    Map pMap = listInGds.get(0);
                }

                // 세션
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("reg_man", hmParam.get("rgsr_id"));
                hmParam.put("upd_man", hmParam.get("rgsr_id"));

                hmParam.put("paramEmpleNo", hmParam.get("rgsr_id"));
                if ("Y".equals(hmParam.get("intra"))) {
                    hmParam.put("paramEmpleNo", hmParam.get("emple_no"));
                }
                hmParam.put("paramAs", "E");
                String dataAthrQury = dataAthrQuryService
                        .getDataAthrQury(hmParam);
                hmParam.put("dataAthrQury", dataAthrQury);

                if (!"".equals(hmParam.get("accnt_no"))) {
                    hmParam.put("stt_dt", "");
                    hmParam.put("end_dt", "");
                    hmParam.put("event", "");
                    hmParam.put("prod_cd", "");
                    hmParam.put("pay_mthd", "");
                    hmParam.put("dept_cd", "");
                }

                int nTotal = DlwEvntService.getEventCount(hmParam);
                mapOutVar.put("tc_evt", nTotal);

                List<Map<String, Object>> mList = DlwEvntService
                        .getEventList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);

                List<Map<String, Object>> mStatList = DlwEvntService
                        .getEventStats(hmParam);
                listStatMap.setRowMaps(mStatList);
                mapOutDs.put("ds_output2", listStatMap);
            }

            //2018.03.05 접속 로그////////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            //////////////////////////////////////////////////////////////////////////////////////////////////////

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 대명라이프웨이 행사 상세 정보를 검색한다.
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/detail-list")
    public ModelAndView getDetailEvntList(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        /*
         * DataSetMap listMap2 = new DataSetMap(); DataSetMap listMap3 = new
         * DataSetMap(); DataSetMap listMap4 = new DataSetMap(); DataSetMap
         * listMap5 = new DataSetMap(); DataSetMap listMap6 = new DataSetMap();
         * DataSetMap listMap7 = new DataSetMap(); DataSetMap listMap8 = new
         * DataSetMap(); DataSetMap listMap9 = new DataSetMap(); DataSetMap
         * listMap10 = new DataSetMap(); DataSetMap listMap11 = new
         * DataSetMap(); DataSetMap listMap12 = new DataSetMap(); DataSetMap
         * listMap13 = new DataSetMap(); DataSetMap listMap14 = new
         * DataSetMap(); DataSetMap listMap15 = new DataSetMap(); DataSetMap
         * listMap16 = new DataSetMap(); DataSetMap listMap17 = new
         * DataSetMap(); DataSetMap listMap18 = new DataSetMap();
         */
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            List<Map<String, Object>> acDirMst = new ArrayList<Map<String, Object>>();
            // 2차때 사용..
            /*
             * List<Map<String, Object>> acOutMst = new
             * ArrayList<Map<String,Object>>(); List<Map<String, Object>> ac1 =
             * new ArrayList<Map<String,Object>>(); List<Map<String, Object>>
             * ac1B = new ArrayList<Map<String,Object>>(); List<Map<String,
             * Object>> ac1C = new ArrayList<Map<String,Object>>();
             * List<Map<String, Object>> ac1D = new
             * ArrayList<Map<String,Object>>(); List<Map<String, Object>> ac1E =
             * new ArrayList<Map<String,Object>>(); List<Map<String, Object>>
             * ac2 = new ArrayList<Map<String,Object>>(); List<Map<String,
             * Object>> ac3 = new ArrayList<Map<String,Object>>();
             * List<Map<String, Object>> ac4 = new
             * ArrayList<Map<String,Object>>(); List<Map<String, Object>> ac5 =
             * new ArrayList<Map<String,Object>>(); List<Map<String, Object>>
             * ac6 = new ArrayList<Map<String,Object>>(); List<Map<String,
             * Object>> ac7 = new ArrayList<Map<String,Object>>();
             * List<Map<String, Object>> ac9 = new
             * ArrayList<Map<String,Object>>(); List<Map<String, Object>> ac10 =
             * new ArrayList<Map<String,Object>>(); List<Map<String, Object>>
             * dirPayInfo = new ArrayList<Map<String,Object>>();
             * List<Map<String, Object>> outPayInfo = new
             * ArrayList<Map<String,Object>>();
             */

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            if (listInDs.size() > 0) {
                hmParam = listInDs.get(0);
                List<Map<String, Object>> mList = DlwEvntService.getDetailEvent(hmParam);
                if (mList != null && mList.size() > 0) {
                    Map<String, Object> EvntMap = mList.get(0);
                    
                    String sAccntNo = EvntMap.get("accnt_no").toString();
                    
                    /* 회원몰 정보 조회*/                      
                    int iMallAmt = Integer.parseInt(String.valueOf(DlwMallLinkedService.getMallUseAmt(sAccntNo)));                  
                    EvntMap.put("shop_use_amt", (iMallAmt));      

                    int payAmt = Integer.parseInt(String.valueOf(EvntMap.get("pay_amt")));
                    int dcAmt = Integer.parseInt(String.valueOf(EvntMap.get("dc_amt")));
                    int sumPayAmt = payAmt - dcAmt;

                    int newChanMoney = Integer.parseInt(String.valueOf(EvntMap.get("new_chan_money")));
                    int prodMo = Integer.parseInt(String.valueOf(EvntMap.get("prod_mo")));
                    int totalMoney = prodMo - payAmt - dcAmt - newChanMoney;

                    EvntMap.put("sum_pay_amt", String.valueOf(sumPayAmt));
                    EvntMap.put("total_money", String.valueOf(totalMoney));

                    List<Map<String, Object>> mEvtMngrRegList = DlwEvntService.getEvtMngrRegList(hmParam);

                    String emp_gubun = "";
                    for (int i = 0; i < mEvtMngrRegList.size(); i++) {
                        hmParam.put("evt_mngr_cd",
                                mEvtMngrRegList.get(i).get("evt_mngr_cd"));
                        if ("0001".equals(mEvtMngrRegList.get(i).get(
                                "evt_mngr_gubun"))) {
                            emp_gubun = DlwEvntService.getCpGubun(hmParam);
                        }
                    }
                    EvntMap.put("emp_gubun", emp_gubun);

                    int payAmtCash = 0;

                    acDirMst = DlwEvntService.getEvtRptDirMst(hmParam);
                    // 2차때 사용..
                    /*
                     * if (acDirMst != null && acDirMst.size() > 0) {
                     * hmParam.put("rpt_no", acDirMst.get(0).get("rpt_no")); ac1
                     * = DlwEvntService.getEvtRptDirGdsList(hmParam); ac1B =
                     * DlwEvntService.getEvtRptDirGdsListB(hmParam); ac1C =
                     * DlwEvntService.getEvtRptDirGdsListC(hmParam); ac1D =
                     * DlwEvntService.getEvtRptDirGdsListD(hmParam); ac1E =
                     * DlwEvntService.getEvtRptDirGdsListE(hmParam); ac2 =
                     * DlwEvntService.getEvtRptDirAddSale1List(hmParam); ac3 =
                     * DlwEvntService.getEvtRptDirAddSale2List(hmParam); ac4 =
                     * DlwEvntService.getEvtRptDirNormlCostList(hmParam); ac5 =
                     * DlwEvntService.getEvtRptDirPayList(hmParam); }
                     *
                     * dirPayInfo = DlwEvntService.getEvtRptDirPayInfo(hmParam);
                     * acOutMst = DlwEvntService.getEvtRptOutMst(hmParam); ac6 =
                     * DlwEvntService.getEvtRptOutDtl1List(hmParam); ac7 =
                     * DlwEvntService.getEvtRptOutDtl2List(hmParam); ac9 =
                     * DlwEvntService.getEvtRptOutDtl4List(hmParam); ac10 =
                     * DlwEvntService.getEvtRptOutDtl5List(hmParam);
                     *
                     * outPayInfo = DlwEvntService.getEvtRptOutPayInfo(hmParam);
                     */
                    String whMvYn = "N";
                    if (acDirMst.size() > 0) {
                        whMvYn = DlwEvntService.getWhMvYn(hmParam);
                    }
                    EvntMap.put("wh_mv_yn", whMvYn);

                    payAmtCash = DlwEvntService.getPayAmtCash(hmParam);
                    EvntMap.put("pay_amt_cash", payAmtCash);
                }
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);

                // 2차때 사용..
                /*
                 * listMap2.setRowMaps(acDirMst); mapOutDs.put("ds_output_2",
                 * listMap2); listMap3.setRowMaps(acOutMst);
                 * mapOutDs.put("ds_output_3", listMap3);
                 * listMap4.setRowMaps(ac1); mapOutDs.put("ds_output_4",
                 * listMap4); listMap5.setRowMaps(ac1B);
                 * mapOutDs.put("ds_output_5", listMap5);
                 * listMap6.setRowMaps(ac1C); mapOutDs.put("ds_output_6",
                 * listMap6); listMap7.setRowMaps(ac1D);
                 * mapOutDs.put("ds_output_7", listMap7);
                 * listMap8.setRowMaps(ac1E); mapOutDs.put("ds_output_8",
                 * listMap8); listMap9.setRowMaps(ac2);
                 * mapOutDs.put("ds_output_9", listMap9);
                 * listMap10.setRowMaps(ac3); mapOutDs.put("ds_output_10",
                 * listMap10); listMap11.setRowMaps(ac4);
                 * mapOutDs.put("ds_output_11", listMap11);
                 * listMap12.setRowMaps(ac5); mapOutDs.put("ds_output_12",
                 * listMap12); listMap13.setRowMaps(ac6);
                 * mapOutDs.put("ds_output_13", listMap13);
                 * listMap14.setRowMaps(ac7); mapOutDs.put("ds_output_14",
                 * listMap14); listMap15.setRowMaps(ac9);
                 * mapOutDs.put("ds_output_15", listMap15);
                 * listMap16.setRowMaps(ac10); mapOutDs.put("ds_output_16",
                 * listMap16); listMap17.setRowMaps(dirPayInfo);
                 * mapOutDs.put("ds_output_17", listMap17);
                 * listMap18.setRowMaps(outPayInfo);
                 * mapOutDs.put("ds_output_18", listMap18);
                 */
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 장례식장 정보를 검색한다.
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/funrl-hall-list")
    public ModelAndView getFunrlHallList(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            // 페이징 정보
            DataSetMap listInDs = (DataSetMap) mapInDs
                    .get("gds_RequestCompVariable");
            if (listInDs != null && listInDs.size() > 0) {
                Map pMap = listInDs.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }

            int nTotal = DlwEvntService.getFunrlHallCount(hmParam);

            mapOutVar.put("tc_funrlHall", nTotal);

            List<Map<String, Object>> mList = DlwEvntService
                    .getFunrlHallList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 행사 정보를 입력한다.
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/insert")
    public ModelAndView insertEvent(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            if (listInDs.size() > 0) {
                hmParam = listInDs.get(0);
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("emple_no", hmParam.get("rgsr_id"));
                hmParam.put("reg_man", hmParam.get("rgsr_id"));
                hmParam.put("close_cl", "0002");
                hmParam.put("close_dt", hmParam.get("event_comp_day"));

                String result_msg = "";
                /***************************************
                 *  2017.07.27 김준호
                 *  관리업무>행사조회>모니터링>모니터링 결과 보고서
                 *  중복 입력시 오류건으로 인해 저장전 회원번호로 EVENT TABLE확인후 저장
                 ***************************************/

                int count = DlwEvntService.countEvent(hmParam);
                if (count == 0) {

                    if ("N".equals(closeDataSaveYnChk(hmParam))) {
                        mapOutVar.put("edt_yn", "N");
                    } else {
                        int result = DlwEvntService.insertEvent(hmParam);
                        /*
                         * if (result <= 0) { result_msg = "error"; } else {
                         * result_msg = "success"; }
                         */
                        List<Map<String, Object>> mList = DlwEvntService
                                .getEvtSeq(hmParam);

                        listMap.setRowMaps(mList);
                        mapOutDs.put("ds_output", listMap);
                        mapOutVar.put("result_msg", result_msg);

                        /* ================================================================
                         * 날짜 : 20171226
                         * 이름 : 송준빈
                         * 추가내용 : 쇼핑몰 연동 Table에 해당 고객의 상태값을 행사대기로 바꿈
                         * ================================================================
                         * */

                        hmParam.put("P_MEM_NO", hmParam.get("mem_no"));
                        hmParam.put("P_ACCNT_NO", hmParam.get("accnt_no"));
                        hmParam.put("P_STATE", "3");
                        System.out.println("insert인풋데이터 :: " +hmParam );

//                        int result1 = DlwEvntService.updateEventMemberState(hmParam);
                        int result1 = DlwMallLinkedService.updateResnMallState(hmParam);

                        /* ================================================================
                         * 날짜 : 20181114
                         * 이름 : 송준빈
                         * 추가내용 : 청구가 존재하는 회원인지 확인후 청구가 존재한다면 가수금 테이블에 insert
                         * ================================================================
                         * */
                        List<Map<String, Object>> mList1 = DlwEvntService.getWdrwReqConfirm(hmParam);
                        if (mList1.size() > 0)
                        {
                        	System.out.println("행사로 인한 환불금 등록");
                        	mList1.get(0).put("auto_name", "evnt");
                            dlwPayService.insertMemberReqRefund(mList1.get(0));
                        	
                        	if("N".equals(mList1.get(0).get("AUTH_YN"))) {
                        		System.out.println("행사로 인한 환불금 무승인 등록");
                                //dlwPayService.insertNauthPayCancelResnEvnt(mList1.get(0));
                        	}
                        }


                    }
                }else{
                    result_msg = "error";
                    mapOutVar.put("result_msg", result_msg);
                }
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 행사 정보를 수정한다.
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/update")
    public ModelAndView updateEvent(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            if (listInDs.size() > 0) {
                hmParam = listInDs.get(0);
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("emple_no", hmParam.get("rgsr_id"));
                hmParam.put("upd_man", hmParam.get("rgsr_id"));
                hmParam.put("close_cl", "0002");
                hmParam.put("close_dt", hmParam.get("event_comp_day"));

                String result_msg = "";

                if ("N".equals(closeDataSaveYnChk(hmParam))) {
                    mapOutVar.put("edt_yn", "N");
                } else {
                    System.out.println(hmParam);

                    int result = DlwEvntService.updateEvent(hmParam);

                    /* ================================================================
                     * 날짜 : 20171228
                     * 이름 : 송준빈
                     * 추가내용 : 쇼핑몰 연동 Table에 해당 고객의 상태값을 행사완료로 바꿈
                     * ================================================================
                     * */
                    String sCompH = CommonUtils.nvls((String)hmParam.get("comp_h"));
                    String sCompM = CommonUtils.nvls((String)hmParam.get("comp_m"));

                    if((sCompH != null && sCompM != null) || (sCompH.equals("") == false && sCompM.equals("") == false))
                    {
                        int hLength = sCompH.length();
                        int mLength = sCompM.length();

                        if(hLength == 2 && mLength == 2)
                        {
                            hmParam.put("P_MEM_NO", hmParam.get("mem_no"));
                            hmParam.put("P_ACCNT_NO", hmParam.get("accnt_no"));
                            hmParam.put("P_STATE", "4");
                            System.out.println("update인풋데이터 :: " +hmParam );

//                            int result1 = DlwEvntService.updateEventMemberState(hmParam);
                            int result1 = DlwMallLinkedService.updateResnMallState(hmParam);
                        }
                    }

                    /* ================================================================
                     * 날짜 : 20181114
                     * 이름 : 송준빈
                     * 추가내용 : 청구가 존재하는 회원인지 확인후 청구가 존재한다면 가수금 테이블에 insert
                     * ================================================================
                     * */
                    List<Map<String, Object>> mList1 = DlwEvntService.getWdrwReqConfirm(hmParam);
                    if (mList1.size() > 0)
                    {
                    	System.out.println("행사 환불정보를 insert");
                    	mList1.get(0).put("auto_name", "evnt");
                    	dlwPayService.insertMemberReqRefund(mList1.get(0));
                    	
                    	if("N".equals(mList1.get(0).get("AUTH_YN"))) {
                    		System.out.println("행사로 인한 환불금 무승인 등록");
                            //dlwPayService.insertNauthPayCancelResnEvnt(mList1.get(0));
                    	}
                    }
                }
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 취소 내역 등록
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/cncl-reg")
    public ModelAndView insertCnclReg(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            hmParam.put("accnt_no", mapInVar.get("accnt_no"));
            hmParam.put("cl", mapInVar.get("cl"));
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("reg_man", hmParam.get("rgsr_id"));

            DlwEvntService.insertCnclReg(hmParam);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 취소 내역 등록
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/input-check")
    public ModelAndView inputCheck(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            hmParam.put("accnt_no", mapInVar.get("accnt_no"));

            mapOutVar.put("eventChk", DlwEvntService.getEventCheck(hmParam));
            mapOutVar.put("resnChk", DlwEvntService.getResnCheck(hmParam));
            mapOutVar.put("taxtChk", DlwEvntService.getTaxtCheck(hmParam));
            mapOutVar.put("cmsChk", DlwEvntService.getCmsCheck(hmParam));
            mapOutVar.put("cmsReqCheck", DlwEvntService.getCmsReqCnt(hmParam));
            mapOutVar.put("prodCl", DlwEvntService.getProdCl(hmParam));
            mapOutVar.put("joinType", DlwEvntService.getJoinType(hmParam));

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 취소 내역 등록
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/evnt-accnt-list")
    public ModelAndView getEventAccnt(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();
            
            String sAccntNo = mapInVar.get("accnt_no").toString();

            hmParam.put("accnt_no", sAccntNo);

            List<Map<String, Object>> mList = DlwEvntService.getAccntSearch(hmParam);                        

            if (mList != null && mList.size() > 0) {
                int payAmt = 0;
                int dcAmt = 0;
                int sumPayAmt = 0;
                int newChanMoney = 0;
                int prodMo = 0;
                int totalMoney = 0;

                if (hmParam.get("pay_amt") != null) {
                    payAmt = (int) hmParam.get("pay_amt");
                }
                if (hmParam.get("dc_amt") != null) {
                    dcAmt = (int) hmParam.get("dc_amt");
                }
                if (hmParam.get("new_chan_money") != null) {
                    newChanMoney = (int) hmParam.get("new_chan_money");
                }
                if (hmParam.get("prod_mo") != null) {
                    prodMo = (int) hmParam.get("prod_mo");
                }

                sumPayAmt = payAmt - dcAmt;
                totalMoney = prodMo - payAmt - dcAmt - newChanMoney;

                Map<String, Object> tmpMap = mList.get(0);
                tmpMap.put("sum_pay_amt", sumPayAmt);
                tmpMap.put("total_money", totalMoney); 
                
                /* 회원몰 정보 조회*/            
                int iMallAmt = Integer.parseInt(String.valueOf(DlwMallLinkedService.getMallUseAmt(sAccntNo)));                  
                tmpMap.put("shop_use_amt", (iMallAmt));      
            }
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 행사를 취소한다.
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    public ModelAndView deleteEvent(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            if (listInDs.size() > 0) {
                hmParam = listInDs.get(0);
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("emple_no", hmParam.get("rgsr_id"));
                hmParam.put("upd_man", hmParam.get("rgsr_id"));
                hmParam.put("close_cl", "0002");
                hmParam.put("close_dt", hmParam.get("event_reg_day"));

                String result_msg = "";

                if ("Y".equals(DlwEvntService.getIsEvtRptGen(hmParam))) {
                    mapOutVar.put("isEvtRptGen", "Y");
                } else {
                    if ("N".equals(closeDataSaveYnChk(hmParam))) {
                        mapOutVar.put("edt_yn", "N");
                    } else {
                        String newYn = DlwEvntService.getEvtNewYnChk(hmParam);
                        if ("Y".equals(newYn) || "E".equals(newYn)) {
                            DlwEvntService.deleteEvent(hmParam);

                            /* ================================================================
                             * 날짜 : 20171226
                             * 이름 : 송준빈
                             * 추가내용 : 쇼핑몰 연동 Table에 해당 고객의 상태값을 유효(행사취소)로 바꿈
                             * ================================================================
                             * */
                            hmParam.put("P_MEM_NO", hmParam.get("mem_no"));
                            hmParam.put("P_ACCNT_NO", hmParam.get("accnt_no"));
                            hmParam.put("P_STATE", "1");
                            System.out.println("delete인풋데이터 :: " +hmParam );

//                            int result1 = DlwEvntService.updateEventMemberState(hmParam);
                            int result1 = DlwMallLinkedService.updateResnMallState(hmParam);
                        }
                        mapOutVar.put("newYn", newYn);
                    }
                }
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 취소 내역 등록
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/check-b2b-pay")
    public ModelAndView checkB2bPay(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            hmParam.put("accnt_no", mapInVar.get("accnt_no"));

            mapOutVar
                    .put("checkB2bPay", DlwEvntService.getCheckB2bPay(hmParam));

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 회원번호로 행사정보 조회가능여부, seq 조회
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/accnt-info")
    public ModelAndView getAccntInfo(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            hmParam.put("accnt_no", mapInVar.get("accnt_no"));

            mapOutVar.put("accnt-info", DlwEvntService.getCheckB2bPay(hmParam));

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 마감데이터 확인
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    public String closeDataSaveYnChk(Map<String, Object> pmParam)
            throws Exception {
        String edtYn = "";
        edtYn = DlwEvntService.getCloseDataSaveYnChk(pmParam);
        return edtYn;
    }

    /**
     * 행사자 팝업 정보를 검색한다.
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/manager-list")
    public ModelAndView getDlwMngrList(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            Object reg = (Object) mapInVar.get("reg");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            // 페이징 정보
            DataSetMap listInDs = (DataSetMap) mapInDs
                    .get("gds_RequestCompVariable");
            if (listInDs != null && listInDs.size() > 0) {
                Map pMap = listInDs.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }

            if (reg == null || ((String) reg).trim().length() == 0) {

            } else {
                if (reg.equals("CP")) {
                    hmParam.put("reg", reg);
                }
            }

            ParamUtils.addCenterParam(hmParam);
            System.out.println("===================================>>>>>");
            CommonUtils.printLog(reg);
            CommonUtils.printLog(hmParam);

            int nTotal = DlwEvntService.getDlwManrCount(hmParam);
            mapOutVar.put("tc_prod", nTotal);

            List<Map<String, Object>> mList = DlwEvntService
                    .getDlwMngrList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 거래처 장례식장 팝업 정보를 검색한다.
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/funr-list")
    public ModelAndView getFunrList(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            // 페이징 정보
            DataSetMap listInDs = (DataSetMap) mapInDs
                    .get("gds_RequestCompVariable");
            if (listInDs != null && listInDs.size() > 0) {
                Map pMap = listInDs.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }

            ParamUtils.addCenterParam(hmParam);

            int nTotal = DlwEvntService.getFunrCount(hmParam);
            mapOutVar.put("tc_prod", nTotal);

            System.out.println("===================================>>>>>");
            CommonUtils.printLog(hmParam);
            List<Map<String, Object>> mList = DlwEvntService
                    .getFunrList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 거래처 매입업체 팝업 정보를 검색한다.
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/getCustmrPopList")
    public ModelAndView getCustmrPopList(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            // 페이징 정보
            DataSetMap listInDs = (DataSetMap) mapInDs
                    .get("gds_RequestCompVariable");
            if (listInDs != null && listInDs.size() > 0) {
                Map pMap = listInDs.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }

            ParamUtils.addCenterParam(hmParam);

            int nTotal = DlwEvntService.getCustmrPopCount(hmParam);
            mapOutVar.put("tc_prod", nTotal);

            System.out.println("===================================>>>>>");
            CommonUtils.printLog(hmParam);
            List<Map<String, Object>> mList = DlwEvntService
                    .getCustmrPopList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 거래처 매입업체 팝업 정보를 검색한다.
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/getCustmrOutList")
    public ModelAndView getCustmrOutList(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            // 페이징 정보
            DataSetMap listInDs = (DataSetMap) mapInDs
                    .get("gds_RequestCompVariable");
            if (listInDs != null && listInDs.size() > 0) {
                Map pMap = listInDs.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }

            ParamUtils.addCenterParam(hmParam);

            int nTotal = DlwEvntService.getCustmrOutCount(hmParam);
            mapOutVar.put("tc_prod", nTotal);

            System.out.println("===================================>>>>>");
            CommonUtils.printLog(hmParam);
            List<Map<String, Object>> mList = DlwEvntService
                    .getCustmrOutList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 행사자 정보를 검색한다.
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/srch-mngr")
    public ModelAndView getEvntMngrList(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            // 페이징 정보
            DataSetMap listInDs = (DataSetMap) mapInDs
                    .get("gds_RequestCompVariable");
            if (listInDs != null && listInDs.size() > 0) {
                Map pMap = listInDs.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }

            ParamUtils.addCenterParam(hmParam);

            // int nTotal = DlwEvntService.getDlwManrCount(hmParam);
            // mapOutVar.put("tc_prod", nTotal);

            System.out.println("===================================>>>>>");
            CommonUtils.printLog(hmParam);
            List<Map<String, Object>> mList = DlwEvntService
                    .getEvntMngrList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 행사자 기초 수당 자료를 조회한다.
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/srch-mngrpay")
    public ModelAndView getEvntMngrPayList(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            Object accnt_no = (Object) mapInVar.get("accnt_no");

            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            hmParam.put("accnt_no", accnt_no);

            // 페이징 정보
            DataSetMap listInDs = (DataSetMap) mapInDs
                    .get("gds_RequestCompVariable");

            ParamUtils.addCenterParam(hmParam);

            System.out.println("===================================>>>>>");
            CommonUtils.printLog(hmParam);
            List<Map<String, Object>> mList = DlwEvntService
                    .getEvntMngrPayList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 행사자 등록
     *
     * @param pmParam
     *            Map<String, ?>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/save-mngr")
    public ModelAndView newevntMngrins(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            // CommonUtils.printLog("========================================= newprod");

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            Object evt_seq = (Object) mapInVar.get("seq");

            for (int i = 0; i < srchInDs.size(); i++) {
                hmParam = srchInDs.get(i);

                hmParam.put("reg_man", oLoginUser.getUserid());
                hmParam.put("upd_man", oLoginUser.getUserid());
                hmParam.put("evt_seq", evt_seq);

                ParamUtils.addCenterParam(hmParam);

                int rowType = ((Integer) hmParam.get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

                System.out.println("==============================================>>"+ rowType);
                CommonUtils.printLog(hmParam);

                if (rowType == DataSet.ROW_TYPE_UPDATED) {
                    System.out.println("<<<<<<<<<<<<< 업데이트 >>>>>>>>>>>>>>>>>>");
                    DlwEvntService.updateevntMngr(hmParam); // 수정
                }

                if (rowType == DataSet.ROW_TYPE_INSERTED) {
                    System.out.println("<<<<<<<<<<<<< 신규 >>>>>>>>>>>>>>>>>>");
                    DlwEvntService.insertevntMngr(hmParam); // 등록
                }

                if (rowType == DataSet.ROW_TYPE_DELETED) {
                    System.out.println("<<<<<<<<<<<<< 삭제 >>>>>>>>>>>>>>>>>>");
                    DlwEvntService.deleteRowMngr(hmParam); // 삭제
                }

                // mapOutVar.put("tc_mem_prod", mList.size());
                // listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 품목 팝업 정보를 검색한다.
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/goods-list")
    public ModelAndView getGoodsList(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            // 페이징 정보
            DataSetMap listInDs = (DataSetMap) mapInDs
                    .get("gds_RequestCompVariable");
            if (listInDs != null && listInDs.size() > 0) {
                Map pMap = listInDs.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }

            ParamUtils.addCenterParam(hmParam);

            int nTotal = DlwEvntService.getGoodsCount(hmParam);
            mapOutVar.put("tc_prod", nTotal);

            System.out.println("===================================>>>>>");
            CommonUtils.printLog(hmParam);
            List<Map<String, Object>> mList = DlwEvntService
                    .getGoodsList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 행사보고서(직영) - 조회 리스트
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/srchjik-tab1")
    public ModelAndView getTab1JikList(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap(); // 장례용품
        DataSetMap listMap2 = new DataSetMap(); // 현장발주
        DataSetMap listMap3 = new DataSetMap(); // 장례식장
        DataSetMap listMap4 = new DataSetMap(); // 협력업체
        DataSetMap listMap5 = new DataSetMap(); // 기타(용품/입력)
        DataSetMap listMap6 = new DataSetMap(); // 일반경비
        DataSetMap listMap7 = new DataSetMap(); // 추가매출
        DataSetMap listMap8 = new DataSetMap(); // 추가매출2
        DataSetMap listMap9 = new DataSetMap(); // 결제정보
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            // 페이징 정보
            DataSetMap listInDs = (DataSetMap) mapInDs
                    .get("gds_RequestCompVariable");
            if (listInDs != null && listInDs.size() > 0) {
                Map pMap = listInDs.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }

            ParamUtils.addCenterParam(hmParam);

            System.out.println("===================================>>>>>");
            CommonUtils.printLog(hmParam);

            // 장례용품 조회
            List<Map<String, Object>> mList = DlwEvntService
                    .getTab1JikList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            // 현장발주 조회
            List<Map<String, Object>> mList2 = DlwEvntService
                    .getTab2JikList(hmParam);
            listMap2.setRowMaps(mList2);
            mapOutDs.put("ds_output2", listMap2);

            // 장례식장 조회
            List<Map<String, Object>> mList3 = DlwEvntService
                    .getTab3JikList(hmParam);
            listMap3.setRowMaps(mList3);
            mapOutDs.put("ds_output3", listMap3);

            // 협력업체 조회
            List<Map<String, Object>> mList4 = DlwEvntService
                    .getTab4JikList(hmParam);
            listMap4.setRowMaps(mList4);
            mapOutDs.put("ds_output4", listMap4);

            // 기타(용품/입력) 조회
            List<Map<String, Object>> mList5 = DlwEvntService
                    .getTab5JikList(hmParam);
            listMap5.setRowMaps(mList5);
            mapOutDs.put("ds_output5", listMap5);

            // 일반경비 조회
            List<Map<String, Object>> mList6 = DlwEvntService
                    .getTab6JikList(hmParam);
            listMap6.setRowMaps(mList6);
            mapOutDs.put("ds_output6", listMap6);

            // 추가매출1 조회
            List<Map<String, Object>> mList7 = DlwEvntService
                    .getTab7JikList(hmParam);
            listMap7.setRowMaps(mList7);
            mapOutDs.put("ds_output7", listMap7);

            // 추가매출2 조회
            List<Map<String, Object>> mList8 = DlwEvntService
                    .getTab8JikList(hmParam);
            listMap8.setRowMaps(mList8);
            mapOutDs.put("ds_output8", listMap8);

            // 결제정보 조회
            List<Map<String, Object>> mList9 = DlwEvntService
                    .getTab9JikList(hmParam);
            listMap9.setRowMaps(mList9);
            mapOutDs.put("ds_output9", listMap9);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 행사보고서(직영) - 수정 & 저장
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/savejik-tab1")
    public ModelAndView insertTab1JikSave(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap(); // 장례용품
        DataSetMap listMap2 = new DataSetMap(); // 현장발주
        DataSetMap listMap3 = new DataSetMap(); // 장례식장
        DataSetMap listMap4 = new DataSetMap(); // 협력업체
        DataSetMap listMap5 = new DataSetMap(); // 기타(용품/입력)
        DataSetMap listMap6 = new DataSetMap(); // 일반경비
        DataSetMap listMap7 = new DataSetMap(); // 추가매출1
        DataSetMap listMap8 = new DataSetMap(); // 추가매출2
        DataSetMap listMap9 = new DataSetMap(); // 결제정보
        Map<String, Object> hmParam = new HashMap<String, Object>();
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            // CommonUtils.printLog("========================================= newprod");

            DataSetMap srch_tab1 = (DataSetMap) mapInDs.get("ds_input"); // 장례용품
            DataSetMap srch_tab2 = (DataSetMap) mapInDs.get("ds_input2"); // 현장발주
            DataSetMap srch_tab3 = (DataSetMap) mapInDs.get("ds_input3"); // 장례식장
            DataSetMap srch_tab4 = (DataSetMap) mapInDs.get("ds_input4"); // 협력업체
            DataSetMap srch_tab5 = (DataSetMap) mapInDs.get("ds_input5"); // 기타(용품/입력)
            DataSetMap srch_tab6 = (DataSetMap) mapInDs.get("ds_input6"); // 일반경비
            DataSetMap srch_tab7 = (DataSetMap) mapInDs.get("ds_input7"); // 추가매출1
            DataSetMap srch_tab8 = (DataSetMap) mapInDs.get("ds_input8"); // 추가매출2
            DataSetMap srch_tab9 = (DataSetMap) mapInDs.get("ds_input9"); // 결제정보

            Object rpt_no = (Object) mapInVar.get("rpt_no");
            Object accnt_no = (Object) mapInVar.get("accnt_no");
            CommonUtils.printLog(accnt_no);

            // 장례용품 수정&저장
            for (int i = 0; i < srch_tab1.size(); i++) {
                hmParam = srch_tab1.get(i);

                hmParam.put("reg_man", oLoginUser.getUserid());
                hmParam.put("upd_man", oLoginUser.getUserid());
                hmParam.put("rpt_no", rpt_no);
                hmParam.put("accnt_no", accnt_no);

                ParamUtils.addCenterParam(hmParam);

                int rowType = ((Integer) hmParam
                        .get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

                System.out
                        .println("==============================================>>"
                                + rowType);
                CommonUtils.printLog(hmParam);

                if (rowType == DataSet.ROW_TYPE_UPDATED) {
                    System.out.println("<<<<<<<<<<<<< 업데이트 >>>>>>>>>>>>>>>>>>");
                    DlwEvntService.updateJiktab1(hmParam); // 수정
                }

                if (rowType == DataSet.ROW_TYPE_INSERTED) {

                    System.out.println("<<<<<<<<<<<<< 신규 >>>>>>>>>>>>>>>>>>");
                    DlwEvntService.insertJiktab1(hmParam); // 등록
                }

                if (rowType == DataSet.ROW_TYPE_DELETED) {

                    System.out.println("<<<<<<<<<<<<< 삭제 >>>>>>>>>>>>>>>>>>");
                    DlwEvntService.deleteGoodsTab1(hmParam); // 삭제
                }

                mapOutDs.put("ds_output", listMap);
            } // end tab1 for

            // 현장발주 수정&저장
            for (int i = 0; i < srch_tab2.size(); i++) {
                CommonUtils.printLog("AA");
                hmParam = srch_tab2.get(i);

                hmParam.put("reg_man", oLoginUser.getUserid());
                hmParam.put("upd_man", oLoginUser.getUserid());
                hmParam.put("rpt_no", rpt_no);
                hmParam.put("accnt_no", accnt_no);

                ParamUtils.addCenterParam(hmParam);

                int rowType = ((Integer) hmParam
                        .get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

                System.out
                        .println("==============================================>>"
                                + rowType);
                CommonUtils.printLog(hmParam);

                if (rowType == DataSet.ROW_TYPE_UPDATED) {
                    System.out.println("<<<<<<<<<<<<< 업데이트 >>>>>>>>>>>>>>>>>>");
                    DlwEvntService.updateJiktab2(hmParam); // 수정
                }

                if (rowType == DataSet.ROW_TYPE_INSERTED) {

                    System.out.println("<<<<<<<<<<<<< 신규 >>>>>>>>>>>>>>>>>>");
                    DlwEvntService.insertJiktab2(hmParam); // 등록
                }

                if (rowType == DataSet.ROW_TYPE_DELETED) {

                    System.out.println("<<<<<<<<<<<<< 삭제 >>>>>>>>>>>>>>>>>>");
                    DlwEvntService.deleteGoodsTab2(hmParam); // 삭제
                }

                mapOutDs.put("ds_output", listMap2);
            } // end tab2 for

            // 장례식장 수정&저장
            for (int i = 0; i < srch_tab3.size(); i++) {
                CommonUtils.printLog("AA");
                hmParam = srch_tab3.get(i);

                hmParam.put("reg_man", oLoginUser.getUserid());
                hmParam.put("upd_man", oLoginUser.getUserid());
                hmParam.put("rpt_no", rpt_no);
                hmParam.put("accnt_no", accnt_no);

                ParamUtils.addCenterParam(hmParam);

                int rowType = ((Integer) hmParam
                        .get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

                System.out
                        .println("==============================================>>"
                                + rowType);
                CommonUtils.printLog(hmParam);

                if (rowType == DataSet.ROW_TYPE_UPDATED) {
                    System.out.println("<<<<<<<<<<<<< 업데이트 >>>>>>>>>>>>>>>>>>");
                    DlwEvntService.updateJiktab3(hmParam); // 수정
                }

                if (rowType == DataSet.ROW_TYPE_INSERTED) {

                    System.out.println("<<<<<<<<<<<<< 신규 >>>>>>>>>>>>>>>>>>");
                    DlwEvntService.insertJiktab3(hmParam); // 등록
                }

                if (rowType == DataSet.ROW_TYPE_DELETED) {

                    System.out.println("<<<<<<<<<<<<< 삭제 >>>>>>>>>>>>>>>>>>");
                    DlwEvntService.deleteGoodsTab3(hmParam); // 삭제
                }

                mapOutDs.put("ds_output", listMap3);
            } // end tab3 for

            // 협력업체 수정&저장
            for (int i = 0; i < srch_tab4.size(); i++) {
                hmParam = srch_tab4.get(i);

                hmParam.put("reg_man", oLoginUser.getUserid());
                hmParam.put("upd_man", oLoginUser.getUserid());
                hmParam.put("rpt_no", rpt_no);
                hmParam.put("accnt_no", accnt_no);

                ParamUtils.addCenterParam(hmParam);

                int rowType = ((Integer) hmParam
                        .get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

                System.out
                        .println("==============================================>>"
                                + rowType);
                CommonUtils.printLog(hmParam);

                if (rowType == DataSet.ROW_TYPE_UPDATED) {
                    System.out.println("<<<<<<<<<<<<< 업데이트 >>>>>>>>>>>>>>>>>>");
                    DlwEvntService.updateJiktab4(hmParam); // 수정
                }

                if (rowType == DataSet.ROW_TYPE_INSERTED) {

                    System.out.println("<<<<<<<<<<<<< 신규 >>>>>>>>>>>>>>>>>>");
                    DlwEvntService.insertJiktab4(hmParam); // 등록
                }

                if (rowType == DataSet.ROW_TYPE_DELETED) {

                    System.out.println("<<<<<<<<<<<<< 삭제 >>>>>>>>>>>>>>>>>>");
                    DlwEvntService.deleteGoodsTab4(hmParam); // 삭제
                }

                mapOutDs.put("ds_output", listMap4);
            } // end tab4 for

            // 기타(용품/인력) 수정&저장
            for (int i = 0; i < srch_tab5.size(); i++) {
                hmParam = srch_tab5.get(i);

                hmParam.put("reg_man", oLoginUser.getUserid());
                hmParam.put("upd_man", oLoginUser.getUserid());
                hmParam.put("rpt_no", rpt_no);
                hmParam.put("accnt_no", accnt_no);

                ParamUtils.addCenterParam(hmParam);

                int rowType = ((Integer) hmParam
                        .get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

                System.out
                        .println("==============================================>>"
                                + rowType);
                CommonUtils.printLog(hmParam);

                if (rowType == DataSet.ROW_TYPE_UPDATED) {
                    System.out.println("<<<<<<<<<<<<< 업데이트 >>>>>>>>>>>>>>>>>>");
                    DlwEvntService.updateJiktab5(hmParam); // 수정
                }

                if (rowType == DataSet.ROW_TYPE_INSERTED) {

                    System.out.println("<<<<<<<<<<<<< 신규 >>>>>>>>>>>>>>>>>>");
                    DlwEvntService.insertJiktab5(hmParam); // 등록
                }

                if (rowType == DataSet.ROW_TYPE_DELETED) {

                    System.out.println("<<<<<<<<<<<<< 삭제 >>>>>>>>>>>>>>>>>>");
                    DlwEvntService.deleteGoodsTab5(hmParam); // 삭제
                }

                mapOutDs.put("ds_output", listMap5);
            } // end tab5 for

            // 일반경비 수정&저장
            for (int i = 0; i < srch_tab6.size(); i++) {
                hmParam = srch_tab6.get(i);

                hmParam.put("reg_man", oLoginUser.getUserid());
                hmParam.put("upd_man", oLoginUser.getUserid());
                hmParam.put("rpt_no", rpt_no);
                hmParam.put("accnt_no", accnt_no);

                ParamUtils.addCenterParam(hmParam);

                int rowType = ((Integer) hmParam
                        .get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

                System.out
                        .println("==============================================>>"
                                + rowType);
                CommonUtils.printLog(hmParam);

                if (rowType == DataSet.ROW_TYPE_UPDATED) {
                    System.out.println("<<<<<<<<<<<<< 업데이트 >>>>>>>>>>>>>>>>>>");
                    DlwEvntService.updateJiktab6(hmParam); // 수정
                }

                if (rowType == DataSet.ROW_TYPE_INSERTED) {

                    System.out.println("<<<<<<<<<<<<< 신규 >>>>>>>>>>>>>>>>>>");
                    DlwEvntService.insertJiktab6(hmParam); // 등록
                }

                if (rowType == DataSet.ROW_TYPE_DELETED) {

                    System.out.println("<<<<<<<<<<<<< 삭제 >>>>>>>>>>>>>>>>>>");
                    DlwEvntService.deleteGoodsTab6(hmParam); // 삭제
                }

                mapOutDs.put("ds_output", listMap6);
            } // end tab6 for

            // 추가매출1 수정&저장
            for (int i = 0; i < srch_tab7.size(); i++) {
                hmParam = srch_tab7.get(i);

                hmParam.put("reg_man", oLoginUser.getUserid());
                hmParam.put("upd_man", oLoginUser.getUserid());
                hmParam.put("rpt_no", rpt_no);
                hmParam.put("accnt_no", accnt_no);

                ParamUtils.addCenterParam(hmParam);

                int rowType = ((Integer) hmParam
                        .get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

                System.out
                        .println("==============================================>>"
                                + rowType);
                CommonUtils.printLog(hmParam);

                if (rowType == DataSet.ROW_TYPE_UPDATED) {
                    System.out.println("<<<<<<<<<<<<< 업데이트 >>>>>>>>>>>>>>>>>>");
                    DlwEvntService.updateJiktab7(hmParam); // 수정
                }

                if (rowType == DataSet.ROW_TYPE_INSERTED) {

                    System.out.println("<<<<<<<<<<<<< 신규 >>>>>>>>>>>>>>>>>>");
                    DlwEvntService.insertJiktab7(hmParam); // 등록
                }

                if (rowType == DataSet.ROW_TYPE_DELETED) {

                    System.out.println("<<<<<<<<<<<<< 삭제 >>>>>>>>>>>>>>>>>>");
                    DlwEvntService.deleteGoodsTab7(hmParam); // 삭제
                }

                mapOutDs.put("ds_output", listMap7);
            } // end tab7 for

            // 추가매출2 수정&저장
            for (int i = 0; i < srch_tab8.size(); i++) {
                hmParam = srch_tab8.get(i);

                hmParam.put("reg_man", oLoginUser.getUserid());
                hmParam.put("upd_man", oLoginUser.getUserid());
                hmParam.put("rpt_no", rpt_no);
                hmParam.put("accnt_no", accnt_no);

                ParamUtils.addCenterParam(hmParam);

                int rowType = ((Integer) hmParam
                        .get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

                System.out
                        .println("==============================================>>"
                                + rowType);
                CommonUtils.printLog(hmParam);

                if (rowType == DataSet.ROW_TYPE_UPDATED) {
                    System.out.println("<<<<<<<<<<<<< 업데이트 >>>>>>>>>>>>>>>>>>");
                    DlwEvntService.updateJiktab8(hmParam); // 수정
                }

                if (rowType == DataSet.ROW_TYPE_INSERTED) {

                    System.out.println("<<<<<<<<<<<<< 신규 >>>>>>>>>>>>>>>>>>");
                    DlwEvntService.insertJiktab8(hmParam); // 등록
                }

                if (rowType == DataSet.ROW_TYPE_DELETED) {

                    System.out.println("<<<<<<<<<<<<< 삭제 >>>>>>>>>>>>>>>>>>");
                    DlwEvntService.deleteGoodsTab8(hmParam); // 삭제
                }

                mapOutDs.put("ds_output", listMap8);
            } // end tab8 for

            // 결제정보 수정&저장
            for (int i = 0; i < srch_tab9.size(); i++) {
                hmParam = srch_tab9.get(i);

                hmParam.put("reg_man", oLoginUser.getUserid());
                hmParam.put("upd_man", oLoginUser.getUserid());
                hmParam.put("rpt_no", rpt_no);
                hmParam.put("accnt_no", accnt_no);

                ParamUtils.addCenterParam(hmParam);

                int rowType = ((Integer) hmParam
                        .get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

                System.out
                        .println("==============================================>>"
                                + rowType);
                CommonUtils.printLog(hmParam);

                if (rowType == DataSet.ROW_TYPE_UPDATED) {
                    System.out.println("<<<<<<<<<<<<< 업데이트 >>>>>>>>>>>>>>>>>>");
                    DlwEvntService.updateJiktab9(hmParam); // 수정
                }

                if (rowType == DataSet.ROW_TYPE_INSERTED) {

                    System.out.println("<<<<<<<<<<<<< 신규 >>>>>>>>>>>>>>>>>>");
                    DlwEvntService.insertJiktab9(hmParam); // 등록
                }

                if (rowType == DataSet.ROW_TYPE_DELETED) {

                    System.out.println("<<<<<<<<<<<<< 삭제 >>>>>>>>>>>>>>>>>>");
                    DlwEvntService.deleteGoodsTab9(hmParam); // 삭제
                }

                mapOutDs.put("ds_output", listMap9);
            } // end tab9 for

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 카드 수수료 조회
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/srch-cardfee")
    public ModelAndView getCardFeeList(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            // 페이징 정보
            DataSetMap listInDs = (DataSetMap) mapInDs
                    .get("gds_RequestCompVariable");
            if (listInDs != null && listInDs.size() > 0) {
                Map pMap = listInDs.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }

            ParamUtils.addCenterParam(hmParam);

            System.out.println("===================================>>>>>");
            CommonUtils.printLog(hmParam);
            List<Map<String, Object>> mList = DlwEvntService
                    .getCardFeeList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 행사보고서(외주) - 조회 리스트
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/srchout-list")
    public ModelAndView getOutsrcList(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap(); // 제공용품1
        DataSetMap listMap2 = new DataSetMap(); // 추가매출1
        DataSetMap listMap3 = new DataSetMap(); // 제공용품2 및 추가매출2
        DataSetMap listMap4 = new DataSetMap(); // 결제정보 관련
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            // 페이징 정보
            DataSetMap listInDs = (DataSetMap) mapInDs
                    .get("gds_RequestCompVariable");
            if (listInDs != null && listInDs.size() > 0) {
                Map pMap = listInDs.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }

            ParamUtils.addCenterParam(hmParam);

            System.out
                    .println("==========================>>>>> srchout-list (외주 결과보고서) <<<<<<<<< ==========================");
            CommonUtils.printLog(hmParam);

            // 제공용품1
            List<Map<String, Object>> mList = DlwEvntService
                    .getOutsrc_1(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            // 추가매출1
            List<Map<String, Object>> mList2 = DlwEvntService
                    .getOutsrc_2(hmParam);
            listMap2.setRowMaps(mList2);
            mapOutDs.put("ds_output2", listMap2);

            // 제공용품2 및 추가매출2
            List<Map<String, Object>> mList3 = DlwEvntService
                    .getOutsrc_3(hmParam);
            listMap3.setRowMaps(mList3);
            mapOutDs.put("ds_output3", listMap3);

            // 결제정보 관련
            List<Map<String, Object>> mList4 = DlwEvntService
                    .getOutsrc_4(hmParam);
            listMap4.setRowMaps(mList4);
            mapOutDs.put("ds_output4", listMap4);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 행사보고서(외주) - 수정 & 저장
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/srchout-save")
    public ModelAndView insertOutsrcSave(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap(); // 제공용품1
        DataSetMap listMap2 = new DataSetMap(); // 추가매출1
        DataSetMap listMap3 = new DataSetMap(); // 제공용품2 및 추가매출2
        DataSetMap listMap4 = new DataSetMap(); // 결제정보 관련
        Map<String, Object> hmParam = new HashMap<String, Object>();
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            CommonUtils
                    .printLog("========================================= insertOutsrcSave");

            DataSetMap srch_tab1 = (DataSetMap) mapInDs.get("ds_input"); // 제공용품1
            DataSetMap srch_tab2 = (DataSetMap) mapInDs.get("ds_input2"); // 추가매출1
            DataSetMap srch_tab3 = (DataSetMap) mapInDs.get("ds_input3"); // 제공용품2
                                                                            // 및
                                                                            // 추가매출2
            DataSetMap srch_tab4 = (DataSetMap) mapInDs.get("ds_input4"); // 결제정보

            CommonUtils.printLog(srch_tab1.size());

            Object rpt_no = (Object) mapInVar.get("rpt_no");
            Object accnt_no = (Object) mapInVar.get("accnt_no");
            CommonUtils.printLog(rpt_no);

            // 제공용품1 수정&저장
            for (int i = 0; i < srch_tab1.size(); i++) {
                hmParam = srch_tab1.get(i);

                hmParam.put("reg_man", oLoginUser.getUserid());
                hmParam.put("upd_man", oLoginUser.getUserid());
                hmParam.put("rpt_no", rpt_no);
                hmParam.put("accnt_no", accnt_no);

                ParamUtils.addCenterParam(hmParam);

                int rowType = ((Integer) hmParam
                        .get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

                System.out
                        .println("==============================================>>"
                                + rowType);
                CommonUtils.printLog(hmParam);

                if (rowType == DataSet.ROW_TYPE_UPDATED) {
                    System.out.println("<<<<<<<<<<<<< 업데이트 >>>>>>>>>>>>>>>>>>");
                    DlwEvntService.updateOutsrc_1(hmParam); // 수정
                }

                if (rowType == DataSet.ROW_TYPE_INSERTED) {

                    System.out.println("<<<<<<<<<<<<< 신규 >>>>>>>>>>>>>>>>>>");
                    DlwEvntService.insertOutsrc_1(hmParam); // 등록
                }

                if (rowType == DataSet.ROW_TYPE_DELETED) {

                    System.out.println("<<<<<<<<<<<<< 삭제 >>>>>>>>>>>>>>>>>>");
                    DlwEvntService.deleteGoodsOutds1(hmParam); // 삭제
                }

                mapOutDs.put("ds_output", listMap);
            } // end tab1 for

            // 추가매출1 수정&저장
            for (int i = 0; i < srch_tab2.size(); i++) {
                CommonUtils.printLog("AA");
                hmParam = srch_tab2.get(i);

                hmParam.put("reg_man", oLoginUser.getUserid());
                hmParam.put("upd_man", oLoginUser.getUserid());
                hmParam.put("rpt_no", rpt_no);
                hmParam.put("accnt_no", accnt_no);

                ParamUtils.addCenterParam(hmParam);

                int rowType = ((Integer) hmParam
                        .get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

                System.out
                        .println("==============================================>>"
                                + rowType);
                CommonUtils.printLog(hmParam);

                if (rowType == DataSet.ROW_TYPE_UPDATED) {
                    System.out.println("<<<<<<<<<<<<< 업데이트 >>>>>>>>>>>>>>>>>>");
                    DlwEvntService.updateOutsrc_2(hmParam); // 수정
                }

                if (rowType == DataSet.ROW_TYPE_INSERTED) {

                    System.out.println("<<<<<<<<<<<<< 신규 >>>>>>>>>>>>>>>>>>");
                    DlwEvntService.insertOutsrc_2(hmParam); // 등록
                }

                if (rowType == DataSet.ROW_TYPE_DELETED) {

                    System.out.println("<<<<<<<<<<<<< 삭제 >>>>>>>>>>>>>>>>>>");
                    DlwEvntService.deleteGoodsOutds2(hmParam); // 삭제
                }

                mapOutDs.put("ds_output", listMap2);
            } // end tab2 for

            // 제공용품2 및 추가매출2 수정&저장
            for (int i = 0; i < srch_tab3.size(); i++) {
                CommonUtils.printLog("AA");
                hmParam = srch_tab3.get(i);

                hmParam.put("reg_man", oLoginUser.getUserid());
                hmParam.put("upd_man", oLoginUser.getUserid());
                hmParam.put("rpt_no", rpt_no);
                hmParam.put("accnt_no", accnt_no);

                ParamUtils.addCenterParam(hmParam);

                int rowType = ((Integer) hmParam
                        .get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

                System.out
                        .println("==============================================>>"
                                + rowType);
                CommonUtils.printLog(hmParam);

                if (rowType == DataSet.ROW_TYPE_UPDATED) {
                    System.out.println("<<<<<<<<<<<<< 업데이트 >>>>>>>>>>>>>>>>>>");
                    DlwEvntService.updateOutsrc_3(hmParam); // 수정
                }

                if (rowType == DataSet.ROW_TYPE_INSERTED) {

                    System.out.println("<<<<<<<<<<<<< 신규 >>>>>>>>>>>>>>>>>>");
                    DlwEvntService.insertOutsrc_3(hmParam); // 등록
                }

                if (rowType == DataSet.ROW_TYPE_DELETED) {

                    System.out.println("<<<<<<<<<<<<< 삭제 >>>>>>>>>>>>>>>>>>");
                    DlwEvntService.deleteGoodsOutds3(hmParam); // 삭제
                }

                mapOutDs.put("ds_output", listMap3);
            } // end tab3 for

            // 결제정보 수정&저장
            for (int i = 0; i < srch_tab4.size(); i++) {
                hmParam = srch_tab4.get(i);

                hmParam.put("reg_man", oLoginUser.getUserid());
                hmParam.put("upd_man", oLoginUser.getUserid());
                hmParam.put("rpt_no", rpt_no);
                hmParam.put("accnt_no", accnt_no);

                ParamUtils.addCenterParam(hmParam);

                int rowType = ((Integer) hmParam
                        .get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

                System.out
                        .println("==============================================>>"
                                + rowType);
                CommonUtils.printLog(hmParam);

                if (rowType == DataSet.ROW_TYPE_UPDATED) {
                    System.out.println("<<<<<<<<<<<<< 업데이트 >>>>>>>>>>>>>>>>>>");
                    DlwEvntService.updateOutsrc_4(hmParam); // 수정
                }

                if (rowType == DataSet.ROW_TYPE_INSERTED) {

                    System.out.println("<<<<<<<<<<<<< 신규 >>>>>>>>>>>>>>>>>>");
                    DlwEvntService.insertOutsrc_4(hmParam); // 등록
                }

                if (rowType == DataSet.ROW_TYPE_DELETED) {

                    System.out.println("<<<<<<<<<<<<< 삭제 >>>>>>>>>>>>>>>>>>");
                    DlwEvntService.deleteGoodsOutds4(hmParam); // 삭제
                }

                mapOutDs.put("ds_output", listMap4);
            } // end tab9 for

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 행사현황 분석 리스트(메인) 조회
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/annl-list")
    public ModelAndView getAnnlList(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            // 페이징 정보
            DataSetMap listInDs = (DataSetMap) mapInDs
                    .get("gds_RequestCompVariable");
            if (listInDs != null && listInDs.size() > 0) {
                Map pMap = listInDs.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }

            ParamUtils.addCenterParam(hmParam);

            int nTotal = DlwEvntService.getAnnlCount(hmParam);
            mapOutVar.put("tc_prod", nTotal);

            System.out.println("===================================>>>>>");
            CommonUtils.printLog(hmParam);
            List<Map<String, Object>> mList = DlwEvntService
                    .getAnnlList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            //2018.03.12 접속 로그////////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            //////////////////////////////////////////////////////////////////////////////////////////////////////

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 지부 콤보 리스트 조회
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/branch-list")
    public ModelAndView getBranchList(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            List<Map<String, Object>> mList = DlwEvntService
                    .getBranchList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 행사정산 입금정보 조회
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/evnt-paymng")
    public ModelAndView getEvntPayData(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            Object accnt_no = (Object) mapInVar.get("accnt_no");

            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            hmParam.put("accnt_no", accnt_no);

            System.out
                    .println("===================================>>>>> getEvntPayData");
            CommonUtils.printLog(hmParam);

            List<Map<String, Object>> mList = DlwEvntService
                    .getEvntPayData(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 입금회차에 따른 입금상세정보 조회
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/evnt-paydtl")
    public ModelAndView getEvntPayDtl(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            Object prod_cd = (Object) mapInVar.get("prod_cd");
            Object prod_cl = (Object) mapInVar.get("prod_cl");

            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            hmParam.put("prod_cd", prod_cd);
            hmParam.put("prod_cl", prod_cl);

            System.out
                    .println("===================================>>>>> getEvntPayDtl");
            CommonUtils.printLog(hmParam);

            List<Map<String, Object>> mList = DlwEvntService
                    .getEvntPayDtl(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 입금회차에 따른 입금상세정보 조회(회차별 금액)
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/evnt-paydtlcnt")
    public ModelAndView getEvntPayDtlCnt(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            Object prod_cd = (Object) mapInVar.get("prod_cd");
            Object prod_cl = (Object) mapInVar.get("prod_cl");

            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            hmParam.put("prod_cd", prod_cd);
            hmParam.put("prod_cl", prod_cl);

            System.out
                    .println("===================================>>>>> getEvntPayDtlCnt");
            CommonUtils.printLog(hmParam);

            List<Map<String, Object>> mList = DlwEvntService
                    .getEvntPayDtlCnt(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 행사보고서(직영) 상세 조회(입금정보 포함)
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/evnt-rptdtl")
    public ModelAndView getEvntRptDtl(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            Object accnt_no = (Object) mapInVar.get("accnt_no");

            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            System.out
                    .println("===================================>>>>> getEvntRptDtl");
            CommonUtils.printLog(hmParam);

            List<Map<String, Object>> mList = DlwEvntService
                    .getEvntRptDtl(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 행사보고서(직영) 저장(임시저장)
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/evnt-rptsave")
    public ModelAndView insertEvntRptDtl(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            Object rpt_no = (Object) mapInVar.get("rpt_no");
            Object gubun = (Object) mapInVar.get("gubun");
            Object seq = (Object) mapInVar.get("seq");
            Object accnt_no = (Object) mapInVar.get("accnt_no");

            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            hmParam.put("seq", seq);
            hmParam.put("rpt_no", rpt_no);
            hmParam.put("accnt_no", accnt_no);
            hmParam.put("reg_man", oLoginUser.getUserid());
            hmParam.put("upd_man", oLoginUser.getUserid());

            System.out
                    .println("===================================>>>>> insertEvntRptDtl");
            CommonUtils.printLog(hmParam);

            if (gubun.equals("new")) {
                System.out.println("<<<<<<<<<<<<< 신규 >>>>>>>>>>>>>>>>>>");
                
                int existsRptDtl = DlwEvntService.existsRptDtlConfirm(hmParam);
                
                if (existsRptDtl <= 0)
                {
                	DlwEvntService.insertEvntRptDtl(hmParam); // 신규
                }
                else 
                {
                	throw new Exception("이미 보고서가 등록된 회원입니다. 중복 보고서 등록은 불가합니다.");
                }
                
                // DlwEvntService.insertUpdBitHis(hmParam); // 회원상태 변경이력 등록
                // DlwEvntService.updateMemprodKstbit(hmParam); // 회원상태 변경
            }

            if (gubun.equals("upd")) {
                System.out.println("<<<<<<<<<<<<< 수정 >>>>>>>>>>>>>>>>>>");
                DlwEvntService.updateEvntRptDtl(hmParam); // 수정
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 행사보고서(직영) 저장(마감저장)
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/evnt-rptmagam")
    public ModelAndView updateEvntRptMagam(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        DataSetMap listMap = new DataSetMap(); // 행사보고서

        Map<String, Object> hmParam = new HashMap<String, Object>();
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");

            Object rpt_no = (Object) mapInVar.get("rpt_no");
            Object gubun = (Object) mapInVar.get("gubun");
            Object seq = (Object) mapInVar.get("seq");
            Object accnt_no = (Object) mapInVar.get("accnt_no");

            String swh_out_no = "";

            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            hmParam.put("seq", seq);
            hmParam.put("rpt_no", rpt_no);
            hmParam.put("accnt_no", accnt_no);
            hmParam.put("reg_man", oLoginUser.getUserid());
            hmParam.put("upd_man", oLoginUser.getUserid());

            System.out
                    .println("===================================>>>>> insertEvntRptDtl");
            CommonUtils.printLog(hmParam);

            DlwEvntService.updateEvntRptMagam(hmParam); // 마감저장

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 행사보고서(직영) 마감저장시 출고 마스터 / 디테일 저장
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/evnt-rptmagam-chul")
    public ModelAndView updateEvntRptMagamChul(XPlatformMapDTO xpDto,
            Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        DataSetMap listMap = new DataSetMap(); // 장례용품 출고
        DataSetMap listMap2 = new DataSetMap(); // 기타용품 출고

        Map<String, Object> bmParam = new HashMap<String, Object>();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        Map<String, Object> pmParam = new HashMap<String, Object>();
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input"); // 장례용품
                                                                        // 출고
            DataSetMap srchInDs2 = (DataSetMap) mapInDs.get("ds_input2"); // 기타용품
                                                                            // 출고

            Object rpt_no = (Object) mapInVar.get("rpt_no");
            Object gubun = (Object) mapInVar.get("gubun");
            Object seq = (Object) mapInVar.get("seq");
            Object accnt_no = (Object) mapInVar.get("accnt_no");

            String swh_out_no = "";
            String chk = "N";

            if (srchInDs != null && srchInDs.size() > 0) {
                bmParam = srchInDs.get(0);
            } else {
                bmParam = new HashMap<String, Object>();
            }
            bmParam.put("rpt_no", rpt_no);
            bmParam.put("upd_man", oLoginUser.getUserid());
            bmParam.put("accnt_no", accnt_no);
            bmParam.put("out_cl", "0001"); // 행사출고

            String wh_out = DlwEvntService.getWhcd(bmParam); // 출고창고번호 가져오기

            String wh_out_no = DlwEvntService.getWhoutNo(bmParam); // 출고번호 가져오기
            bmParam.put("wh_out_no", wh_out_no);

            String swhoutno = DlwEvntService.getSinWhoutNo(bmParam); // 출고번호
                                                                        // 신규생성

            DlwEvntService.deleteWhOutMst(bmParam); // 출고 마스터 삭제비트 변경
            DlwEvntService.deleteWhOutDtl(bmParam); // 출고 디테일 데이터 삭제

            System.out
                    .println("===================================>>>>> updateEvntRptMagamChul");
            CommonUtils.printLog(bmParam);
            if (srchInDs != null && srchInDs.size() > 0) {
                for (int i = 0; i < srchInDs.size(); i++) { // 장례용품 출고
                    hmParam = srchInDs.get(i);

                    hmParam.put("rpt_no", rpt_no);
                    hmParam.put("accnt_no", accnt_no);
                    hmParam.put("reg_man", oLoginUser.getUserid());
                    hmParam.put("upd_man", oLoginUser.getUserid());
                    hmParam.put("wh_out_no", wh_out_no);
                    hmParam.put("swhoutno", swhoutno);
                    hmParam.put("wh_out", wh_out);
                    hmParam.put("cl_gubun", "0001");
                    hmParam.put("out_cl", "0001"); // 행사출고

                    CommonUtils.printLog(hmParam);

                    if (i == 0) {
                        DlwEvntService.insertWhOutMst(hmParam); // 출고 마스터 저장
                        chk = "Y";
                    }

                    DlwEvntService.insertWhOutDtl(hmParam); // 출고 디테일 저장

                }
            }

            if (srchInDs2 != null && srchInDs2.size() > 0) {
                for (int j = 0; j < srchInDs2.size(); j++) { // 기타용품 출고
                    pmParam = srchInDs2.get(j);

                    pmParam.put("rpt_no", rpt_no);
                    pmParam.put("accnt_no", accnt_no);
                    pmParam.put("reg_man", oLoginUser.getUserid());
                    pmParam.put("upd_man", oLoginUser.getUserid());
                    pmParam.put("wh_out_no", wh_out_no);
                    pmParam.put("swhoutno", swhoutno);
                    pmParam.put("wh_out", wh_out);
                    pmParam.put("cl_gubun", "0001");
                    pmParam.put("out_cl", "0001"); // 행사출고

                    CommonUtils.printLog(pmParam);

                    if (j == 0) {
                        if (chk.equals("N")) {
                            DlwEvntService.insertWhOutMst(pmParam); // 출고 마스터 저장
                        }
                    }
                    DlwEvntService.insertWhOutDtl(pmParam); // 출고 디테일 저장
                }
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 행사보고서(직영) 마감저장시 발주 마스터 / 디테일 저장
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/evnt-rptmagam-ord")
    public ModelAndView updateEvntRptMagamOrd(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        DataSetMap listMap = new DataSetMap(); // 현장발주

        Map<String, Object> bmParam = new HashMap<String, Object>();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input"); // 현장발주

            Object rpt_no = (Object) mapInVar.get("rpt_no");
            Object gubun = (Object) mapInVar.get("gubun");
            Object seq = (Object) mapInVar.get("seq");
            Object accnt_no = (Object) mapInVar.get("accnt_no");
            Object bdate = (Object) mapInVar.get("bdate");

            String swh_out_no = "";
            String swh_in_no = "";
            String chk = "N";

            if (srchInDs != null && srchInDs.size() > 0) {
                bmParam = srchInDs.get(0);
            } else {
                bmParam = new HashMap<String, Object>();
            }
            bmParam.put("rpt_no", rpt_no);
            bmParam.put("upd_man", oLoginUser.getUserid());
            bmParam.put("accnt_no", accnt_no);
            bmParam.put("bdate", bdate);
            bmParam.put("out_cl", "0006"); // 발주출고
            bmParam.put("in_cl", "0001"); // 발주입고

            String wh_out = DlwEvntService.getWhcd(bmParam); // 출고창고번호 가져오기

            String ord_no = DlwEvntService.getOrdNo(bmParam); // 발주번호 가져오기
            String wh_in_no = DlwEvntService.getWhinNo(bmParam); // 입고번호 가져오기
            String wh_out_no = DlwEvntService.getWhoutNo(bmParam); // 출고번호 가져오기

            bmParam.put("ord_no", ord_no);
            bmParam.put("wh_in_no", wh_in_no);
            bmParam.put("wh_out_no", wh_out_no);

            String swhordno = DlwEvntService.getSinOrdNo(bmParam); // 발주번호 신규생성
            String swhinno = DlwEvntService.getSinWhinNo(bmParam); // 입고번호 신규생성
            String swhoutno = DlwEvntService.getSinWhoutNo(bmParam); // 출고번호
                                                                        // 신규생성

            swh_in_no = swhinno;

            DlwEvntService.deleteOrdMst(bmParam); // 발주 마스터 삭제비트 변경
            DlwEvntService.deleteOrdDtl(bmParam); // 발주 디테일 데이터 삭제

            DlwEvntService.deleteWhinMst(bmParam); // 입고 마스터 삭제비트 변경
            DlwEvntService.deleteWhinDtl(bmParam); // 입고 디테일 데이터 삭제

            DlwEvntService.deleteWhOutMst(bmParam); // 출고 마스터 삭제비트 변경
            DlwEvntService.deleteWhOutDtl(bmParam); // 출고 디테일 데이터 삭제

            System.out
                    .println("===================================>>>>> 발주 마스터 / 디테일 저장");
            if (srchInDs != null && srchInDs.size() > 0) {
                for (int i = 0; i < srchInDs.size(); i++) { // 현장발주
                    hmParam = srchInDs.get(i);

                    hmParam.put("rpt_no", rpt_no);
                    hmParam.put("reg_man", oLoginUser.getUserid());
                    hmParam.put("upd_man", oLoginUser.getUserid());
                    hmParam.put("emple_no", oLoginUser.getUserid());
                    hmParam.put("ord_no", ord_no);
                    hmParam.put("swhordno", swhordno);
                    hmParam.put("swh_in_no", swh_in_no);
                    hmParam.put("swhoutno", swhoutno);
                    hmParam.put("wh_out", wh_out);
                    hmParam.put("unit", "");
                    hmParam.put("note", "");
                    hmParam.put("wh_in", wh_out);
                    hmParam.put("bdate", bdate);
                    hmParam.put("out_cl", "0006"); // 발주출고
                    hmParam.put("in_cl", "0001"); // 발주입고
                    hmParam.put("cl_gubun", "0001"); // 직영

                    CommonUtils.printLog(hmParam);

                    if (i == 0) {
                        DlwEvntService.insertOrdMst(hmParam); // 발주 마스터 저장
                        DlwEvntService.insertWhOutMst(hmParam); // 출고 마스터 저장
                        DlwEvntService.insertWhinMst(hmParam); // 입고 마스터 저장
                    }

                    DlwEvntService.insertOrdDtl(hmParam); // 발주 디테일 저장
                    DlwEvntService.insertWhOutDtl(hmParam); // 출고 디테일 저장
                    DlwEvntService.insertWhinDtl(hmParam); // 입고 디테일 저장

                }
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 행사보고서(직영) 삭제
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     *             창고이동 삭제 추가 작업
     */
    @RequestMapping(value = "/evnt-rptdel")
    public ModelAndView deleteEvntRptDtl(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            Object rpt_no = (Object) mapInVar.get("rpt_no");
            Object accnt_no = (Object) mapInVar.get("accnt_no");

            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            hmParam.put("rpt_no", rpt_no);
            hmParam.put("accnt_no", accnt_no);
            hmParam.put("upd_man", oLoginUser.getUserid());
            hmParam.put("reg_man", oLoginUser.getUserid());
            hmParam.put("out_cl", "0001"); // 행사출고
            hmParam.put("in_cl", "0001"); // 발주입고

            String wh_out_no = DlwEvntService.getWhoutNo(hmParam); // 행사출고번호
                                                                    // 가져오기
            hmParam.put("wh_out_no", wh_out_no);

            String ord_no = DlwEvntService.getOrdNo(hmParam); // 발주번호 가져오기
            hmParam.put("ord_no", ord_no);

            String wh_in_no = DlwEvntService.getWhinNo(hmParam); // 입고번호 가져오기
            hmParam.put("wh_in_no", wh_in_no);

            System.out
                    .println("===================================>>>>> deleteEvntRptDtl");
            CommonUtils.printLog(hmParam);

            DlwEvntService.deleteWhOutMst(hmParam); // 행사출고 마스터 삭제비트 변경
            DlwEvntService.deleteWhOutDtl(hmParam); // 행사출고 디테일 데이터 삭제

            DlwEvntService.deleteOrdMst(hmParam); // 발주 마스터 삭제비트 변경
            DlwEvntService.deleteOrdDtl(hmParam); // 발주 디테일 데이터 삭제

            hmParam.put("out_cl", "0006"); // 발주출고
            wh_out_no = DlwEvntService.getWhoutNo(hmParam); // 발주출고번호 가져오기
            hmParam.put("wh_out_no", wh_out_no);
            DlwEvntService.deleteWhOutMst(hmParam); // 발주출고 마스터 삭제비트 변경
            DlwEvntService.deleteWhOutDtl(hmParam); // 발주출고 디테일 데이터 삭제

            DlwEvntService.deleteWhinMst(hmParam); // 입고 마스터 삭제비트 변경
            DlwEvntService.deleteWhinDtl(hmParam); // 입고 디테일 데이터 삭제

            DlwEvntService.deleteEvntRptDtl(hmParam); // 삭제
            // DlwEvntService.updateMemprodKstbit_cncl(hmParam); // 회원상태
            // 변경(행사취소시)
            // DlwEvntService.insertUpdBitHis_cncl(hmParam); // 회원상태 변경이력
            // 등록(행사취소시)

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 행사보고서(외주) 상세 조회
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/evnt-outrptdtl")
    public ModelAndView getEvntOutRptDtl(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            Object accnt_no = (Object) mapInVar.get("accnt_no");

            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            System.out
                    .println("===================================>>>>> getEvntOutRptDtl");
            CommonUtils.printLog(hmParam);

            List<Map<String, Object>> mList = DlwEvntService
                    .getEvntOutRptDtl(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 행사보고서(외주) 저장
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/evnt-outrptsave")
    public ModelAndView insertEvntOutRptDtl(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            Object rpt_no = (Object) mapInVar.get("rpt_no");
            Object gubun = (Object) mapInVar.get("gubun");
            Object seq = (Object) mapInVar.get("seq");
            Object accnt_no = (Object) mapInVar.get("accnt_no");

            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            hmParam.put("seq", seq);
            hmParam.put("rpt_no", rpt_no);
            hmParam.put("accnt_no", accnt_no);
            hmParam.put("reg_man", oLoginUser.getUserid());
            hmParam.put("upd_man", oLoginUser.getUserid());

            System.out
                    .println("===================================>>>>> insertEvntOutRptDtl");
            CommonUtils.printLog(hmParam);

            if (gubun.equals("new")) {
                System.out.println("<<<<<<<<<<<<< 신규 >>>>>>>>>>>>>>>>>>");
                
                int existsOutRptDtl = DlwEvntService.existsOutRptDtlConfirm(hmParam);
                
                if (existsOutRptDtl <= 0)
                {
                	DlwEvntService.insertEvntOutRptDtl(hmParam); // 신규
                }
                else 
                {
                	throw new Exception("이미 보고서가 등록된 회원입니다. 중복 보고서 등록은 불가합니다.");
                }
                
                // DlwEvntService.insertUpdBitHis(hmParam); // 회원상태 변경이력 등록
                // DlwEvntService.updateMemprodKstbit(hmParam); // 회원상태 변경
            }

            if (gubun.equals("upd")) {
                System.out.println("<<<<<<<<<<<<< 수정 >>>>>>>>>>>>>>>>>>");
                DlwEvntService.updateEvntOutRptDtl(hmParam); // 수정
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 행사보고서(외주) 마감 저장
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/evnt-outrptsavemagam")
    public ModelAndView updateEvntOutRptDtlMagam(XPlatformMapDTO xpDto,
            Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            Object rpt_no = (Object) mapInVar.get("rpt_no");
            Object gubun = (Object) mapInVar.get("gubun");
            Object seq = (Object) mapInVar.get("seq");
            Object accnt_no = (Object) mapInVar.get("accnt_no");

            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            hmParam.put("seq", seq);
            hmParam.put("rpt_no", rpt_no);
            hmParam.put("accnt_no", accnt_no);
            hmParam.put("reg_man", oLoginUser.getUserid());
            hmParam.put("upd_man", oLoginUser.getUserid());

            System.out
                    .println("===================================>>>>> updateEvntOutRptDtlMagam");
            CommonUtils.printLog(hmParam);

            DlwEvntService.updateEvntOutRptDtlMagam(hmParam); // 수정

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 행사보고서(외주) 마감저장시 출고 마스터 / 디테일 저장
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/evnt-outrptsavemagam-chul")
    public ModelAndView updateEvntOutRptSaveMagamChul(XPlatformMapDTO xpDto,
            Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        DataSetMap listMap = new DataSetMap(); // 제공용품2 및 추가매출2 출고

        Map<String, Object> bmParam = new HashMap<String, Object>();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        Map<String, Object> pmParam = new HashMap<String, Object>();
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input"); // 제공용품2
                                                                        // 및
                                                                        // 추가매출2
                                                                        // 출고

            Object rpt_no = (Object) mapInVar.get("rpt_no");
            Object gubun = (Object) mapInVar.get("gubun");
            Object seq = (Object) mapInVar.get("seq");
            Object accnt_no = (Object) mapInVar.get("accnt_no");

            String swh_out_no = "";

            if (srchInDs.size() > 0) {
                bmParam = srchInDs.get(0);
            } else {
                bmParam = new HashMap<String, Object>();
            }

            bmParam.put("rpt_no", rpt_no);
            bmParam.put("upd_man", oLoginUser.getUserid());
            bmParam.put("accnt_no", accnt_no);
            bmParam.put("out_cl", "0001"); // 행사출고

            String wh_out = DlwEvntService.getWhcd(bmParam); // 출고창고번호 가져오기

            String wh_out_no = DlwEvntService.getWhoutNo(bmParam); // 출고번호 가져오기
            bmParam.put("wh_out_no", wh_out_no);

            String swhoutno = DlwEvntService.getSinWhoutNo(bmParam); // 출고번호
                                                                        // 신규생성

            DlwEvntService.deleteWhOutMst(bmParam); // 출고 마스터 삭제비트 변경
            DlwEvntService.deleteWhOutDtl(bmParam); // 출고 디테일 데이터 삭제

            System.out
                    .println("===================================>>>>> updateEvntRptMagamChul");
            CommonUtils.printLog(bmParam);

            for (int i = 0; i < srchInDs.size(); i++) { // 장례용품 출고
                hmParam = srchInDs.get(i);

                hmParam.put("rpt_no", rpt_no);
                hmParam.put("accnt_no", accnt_no);
                hmParam.put("reg_man", oLoginUser.getUserid());
                hmParam.put("upd_man", oLoginUser.getUserid());
                hmParam.put("wh_out_no", wh_out_no);
                hmParam.put("swhoutno", swhoutno);
                hmParam.put("wh_out", wh_out);
                hmParam.put("cl_gubun", "0002");
                hmParam.put("out_cl", "0001"); // 행사출고

                CommonUtils.printLog(hmParam);
                CommonUtils.printLog("i = " + i);
                if (i == 0) {
                    DlwEvntService.insertWhOutMst(hmParam); // 출고 마스터 저장
                }

                DlwEvntService.insertWhOutDtl(hmParam); // 출고 디테일 저장

            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 행사보고서(외주) 삭제
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/evnt-outrptdel")
    public ModelAndView deleteEvntOutRptDtl(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            Object rpt_no = (Object) mapInVar.get("rpt_no");
            Object accnt_no = (Object) mapInVar.get("accnt_no");

            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            hmParam.put("rpt_no", rpt_no);
            hmParam.put("accnt_no", accnt_no);
            hmParam.put("upd_man", oLoginUser.getUserid());
            hmParam.put("reg_man", oLoginUser.getUserid());
            hmParam.put("out_cl", "0001"); // 행사출고

            String wh_out_no = DlwEvntService.getWhoutNo(hmParam); // 출고번호 가져오기
            hmParam.put("wh_out_no", wh_out_no);

            System.out
                    .println("===================================>>>>> deleteEvntOutRptDtl");
            CommonUtils.printLog(hmParam);

            DlwEvntService.deleteWhOutMst(hmParam); // 출고 마스터 삭제비트 변경
            DlwEvntService.deleteWhOutDtl(hmParam); // 출고 디테일 데이터 삭제

            DlwEvntService.deleteEvntOutRptDtl(hmParam); // 행사보고서 삭제
            // DlwEvntService.updateMemprodKstbit_cncl(hmParam); // 회원상태
            // 변경(행사취소시)
            // DlwEvntService.insertUpdBitHis_cncl(hmParam); // 회원상태 변경이력
            // 등록(행사취소시)

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 사업장내 창고이동 물품리스트 조회
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/movegoods-list")
    public ModelAndView getMoveGoodsList(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            Object accnt_no = (Object) mapInVar.get("accnt_no");

            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            hmParam.put("reg_man", oLoginUser.getUserid());
            hmParam.put("upd_man", oLoginUser.getUserid());

            System.out
                    .println("===================================>>>>> getMoveGoodsList");
            CommonUtils.printLog(hmParam);

            List<Map<String, Object>> mList = DlwEvntService
                    .getMoveGoodsList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 사업장내 창고이동 마스터 조회
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/movegoods-Mst")
    public ModelAndView getMoveGoodsMst(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            Object accnt_no = (Object) mapInVar.get("accnt_no");

            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            hmParam.put("reg_man", oLoginUser.getUserid());
            hmParam.put("upd_man", oLoginUser.getUserid());

            System.out
                    .println("===================================>>>>> getMoveGoodsMst");
            CommonUtils.printLog(hmParam);

            List<Map<String, Object>> mList = DlwEvntService
                    .getMoveGoodsMst(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 사업장내 행사물품 등록(업데이트 개념없으며 저장시 무조건 기존데이터 삭제 이후 신규 저장) <마스터 테이블 삭제컬럼값 업데이트 //
     * 디테일 테이블 데이터 삭제> 1.창고이동 마스터 저장 2.창고이동 상세 저장 3.입고마스터 저장 4.입고디테일 저장
     *
     * @param pmParam
     *            Map<String, ?>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/savegoods-move")
    public ModelAndView insertGoodsMoveWh(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            // CommonUtils.printLog("========================================= newprod");

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            Object rpt_no = (Object) mapInVar.get("rpt_no");
            Object mode = (Object) mapInVar.get("mode");
            Object mv_dt = (Object) mapInVar.get("mv_dt");
            Object note = (Object) mapInVar.get("note");
            Object wh_out = (Object) mapInVar.get("wh_out");
            Object wh_in = (Object) mapInVar.get("wh_in");
            Object emple_no = (Object) mapInVar.get("emple_no");
            Object accnt_no = (Object) mapInVar.get("accnt_no");

            String swh_mv_no = "";
            String swh_in_no = "";
            String swh_out_no = "";

            for (int i = 0; i < srchInDs.size(); i++) {
                hmParam = srchInDs.get(i);

                hmParam.put("reg_man", oLoginUser.getUserid());
                hmParam.put("upd_man", oLoginUser.getUserid());
                hmParam.put("rpt_no", rpt_no);
                hmParam.put("mv_dt", mv_dt);
                hmParam.put("note", note);
                hmParam.put("wh_in", wh_in);
                hmParam.put("wh_out", wh_out);
                hmParam.put("emple_no", emple_no);
                hmParam.put("accnt_no", accnt_no);
                hmParam.put("out_cl", "0002"); // 창고이동출고
                hmParam.put("in_cl", "0002"); // 창고이동입고
                hmParam.put("cl_gubun", "0001"); // 직영
                hmParam.put("end_dt", "");

                ParamUtils.addCenterParam(hmParam);

                if (mode.equals("Y")) { // 기존 창고이동 저장경우 가져오기

                    String wh_mv_no = DlwEvntService.getWhmvNo(hmParam); // 창고번호
                                                                            // 가져오기
                    String wh_in_no = DlwEvntService.getWhinNo(hmParam); // 입고번호
                                                                            // 가져오기
                    String wh_out_no = DlwEvntService.getWhoutNo(hmParam); // 출고번호
                                                                            // 가져오기

                    hmParam.put("wh_mv_no", wh_mv_no);
                    hmParam.put("wh_in_no", wh_in_no);
                    hmParam.put("wh_out_no", wh_out_no);

                }

                if (i == 0) {
                    // 신규코드 가져오기
                    String swhmvno = DlwEvntService.getSinWhmvNo(hmParam); // 창고번호
                                                                            // 신규생성
                    String swhinno = DlwEvntService.getSinWhinNo(hmParam); // 입고번호
                                                                            // 신규생성
                    String swhoutno = DlwEvntService.getSinWhoutNo(hmParam); // 출고번호
                                                                                // 신규생성

                    swh_mv_no = swhmvno;
                    swh_in_no = swhinno;
                    swh_out_no = swhoutno;

                }

                hmParam.put("swh_mv_no", swh_mv_no);
                hmParam.put("swh_in_no", swh_in_no);
                hmParam.put("swhoutno", swh_out_no);

                System.out
                        .println("===================================>>>>> insertGoodsMoveWh");
                CommonUtils.printLog(hmParam);

                if (mode.equals("N")) { // 신규일 경우 저장만

                    if (i == 0) {

                        DlwEvntService.insertWhOutMst(hmParam); // 출고 마스터 저장
                        DlwEvntService.insertWhinMst(hmParam); // 입고 마스터 저장
                        DlwEvntService.insertWhmvMst(hmParam); // 창고이동 마스터 저장

                    }

                    DlwEvntService.insertWhOutDtl(hmParam); // 출고 디테일 저장
                    DlwEvntService.insertWhinDtl(hmParam); // 입고 디테일 저장
                    DlwEvntService.insertWhmvDtl(hmParam); // 창고이동 디테일 저장

                } else { // 수정일 경우 마스터는 삭제비트 & 디테일은 삭제

                    if (i == 0) {

                        DlwEvntService.deleteWhmvMst(hmParam); // 창고이동 마스터 삭제비트
                                                                // 변경
                        DlwEvntService.deleteWhmvDtl(hmParam); // 창고이동 디테일 데이터
                                                                // 삭제

                        DlwEvntService.deleteWhinMst(hmParam); // 입고 마스터 삭제비트 변경
                        DlwEvntService.deleteWhinDtl(hmParam); // 입고 디테일 데이터 삭제

                        DlwEvntService.deleteWhOutMst(hmParam); // 출고 마스터 삭제비트
                                                                // 변경
                        DlwEvntService.deleteWhOutDtl(hmParam); // 출고 디테일 데이터 삭제

                        DlwEvntService.insertWhOutMst(hmParam); // 출고 마스터 저장
                        DlwEvntService.insertWhinMst(hmParam); // 입고 마스터 저장
                        DlwEvntService.insertWhmvMst(hmParam); // 창고이동 마스터 저장
                    }

                    DlwEvntService.insertWhinDtl(hmParam); // 입고 디테일 저장
                    DlwEvntService.insertWhmvDtl(hmParam); // 창고이동 디테일 저장
                    DlwEvntService.insertWhOutDtl(hmParam); // 출고 디테일 저장
                }

                mapOutDs.put("ds_output", listMap);
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 창고이동 삭제
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/delgoods-move")
    public ModelAndView deleteGoodsMove(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            Object rpt_no = (Object) mapInVar.get("rpt_no");
            Object mode = (Object) mapInVar.get("mode");
            Object mv_dt = (Object) mapInVar.get("mv_dt");
            Object note = (Object) mapInVar.get("note");
            Object wh_out = (Object) mapInVar.get("wh_out");
            Object wh_in = (Object) mapInVar.get("wh_in");
            Object emple_no = (Object) mapInVar.get("emple_no");

            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            hmParam.put("reg_man", oLoginUser.getUserid());
            hmParam.put("upd_man", oLoginUser.getUserid());
            hmParam.put("rpt_no", rpt_no);
            hmParam.put("mv_dt", mv_dt);
            hmParam.put("note", note);
            hmParam.put("wh_in", wh_in);
            hmParam.put("wh_out", wh_out);
            hmParam.put("emple_no", emple_no);
            hmParam.put("out_cl", "0002"); // 창고이동출고
            hmParam.put("in_cl", "0002"); // 창고이동입고

            // if(mode.equals("Y")){ // 기존 창고이동 저장경우 가져오기

            String wh_mv_no = DlwEvntService.getWhmvNo(hmParam); // 창고번호 가져오기
            String wh_in_no = DlwEvntService.getWhinNo(hmParam); // 입고번호 가져오기
            String wh_out_no = DlwEvntService.getWhoutNo(hmParam); // 출고번호 가져오기

            hmParam.put("wh_mv_no", wh_mv_no);
            hmParam.put("wh_in_no", wh_in_no);
            hmParam.put("wh_out_no", wh_out_no);

            // }

            System.out
                    .println("===================================>>>>> deleteGoodsMove");
            CommonUtils.printLog(hmParam);

            DlwEvntService.deleteWhmvMst(hmParam); // 창고이동 마스터 삭제비트 변경
            DlwEvntService.deleteWhmvDtl(hmParam); // 창고이동 디테일 데이터 삭제
            DlwEvntService.deleteWhinMst(hmParam); // 입고 마스터 삭제비트 변경
            DlwEvntService.deleteWhinDtl(hmParam); // 입고 디테일 데이터 삭제
            DlwEvntService.deleteWhOutMst(hmParam); // 출고 마스터 삭제비트 변경
            DlwEvntService.deleteWhOutDtl(hmParam); // 출고 디테일 데이터 삭제

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 입금마감 팝업 리스트 조회
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/paytotallist")
    public ModelAndView getPayTotalList(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            Object accnt_no = (Object) mapInVar.get("accnt_no");

            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            System.out
                    .println("===================================>>>>> getPayTotalList");
            CommonUtils.printLog(hmParam);

            List<Map<String, Object>> mList = DlwEvntService
                    .getPayTotalList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 행사 입금마감처리
     *
     * @param pmParam
     *            Map<String, ?>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/insevnt-paymng")
    public ModelAndView insertEvntPayMngSave(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            // CommonUtils.printLog("========================================= newprod");

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            Object accnt_no = (Object) mapInVar.get("accnt_no");
            Object pay_day = (Object) mapInVar.get("pay_day");
            Object next_cnt1 = (Object) mapInVar.get("next_cnt1");
            Object tcnt1 = (Object) mapInVar.get("tcnt1");
            Object next_cnt2 = (Object) mapInVar.get("next_cnt2");
            Object tcnt2 = (Object) mapInVar.get("tcnt2");
            Object next_cnt3 = (Object) mapInVar.get("next_cnt3");
            Object tcnt3 = (Object) mapInVar.get("tcnt3");
            Object prod_cd = (Object) mapInVar.get("prod_cd");
            Object prod_cl = (Object) mapInVar.get("prod_cl");

            for (int i = 0; i < srchInDs.size(); i++) {
                hmParam = srchInDs.get(i);

                hmParam.put("reg_man", oLoginUser.getUserid());
                hmParam.put("accnt_no", accnt_no);
                hmParam.put("pay_day", pay_day);
                hmParam.put("next_cnt1", next_cnt1);
                hmParam.put("tcnt1", tcnt1);
                hmParam.put("next_cnt2", next_cnt2);
                hmParam.put("tcnt2", tcnt2);
                hmParam.put("next_cnt3", next_cnt3);
                hmParam.put("tcnt3", tcnt3);
                hmParam.put("prod_cd", prod_cd);
                hmParam.put("prod_cl", prod_cl);

                ParamUtils.addCenterParam(hmParam);

                CommonUtils.printLog(hmParam);

                if (prod_cl.equals("03")) {

                    DlwEvntService.insertEvntPaymng(hmParam); // 상조부금
                    DlwEvntService.insertEvntPaymngDtl(hmParam); // 결합금
                    DlwEvntService.insertEvntPaymngDtl1(hmParam); // 추가금

                } else {

                    DlwEvntService.insertEvntPaymng(hmParam); // 상조부금

                }

                System.out
                        .println("===================================>>>>> insertEvntPayMngSave");
                CommonUtils.printLog(hmParam);

                mapOutDs.put("ds_output", listMap);
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 행사보고서 현황 - 행사분석(종합분석)
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/total-anal")
    public ModelAndView getEventTotalAnal(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap(); // 장법 분석현황
        DataSetMap listMap2 = new DataSetMap(); // 신규유지 분석현황
        DataSetMap listMap3 = new DataSetMap(); // 지부 분석현황
        DataSetMap listMap4 = new DataSetMap(); // 지역 분석현황
        DataSetMap listMap5 = new DataSetMap(); // 직영CP 분석현황
        DataSetMap listMap6 = new DataSetMap(); // 종교 분석현황

        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            // 페이징 정보
            DataSetMap listInDs = (DataSetMap) mapInDs
                    .get("gds_RequestCompVariable");
            if (listInDs != null && listInDs.size() > 0) {
                Map pMap = listInDs.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }

            ParamUtils.addCenterParam(hmParam);

            System.out.println("===================================>>>>>");
            CommonUtils.printLog(hmParam);

            // 장법 분석현황
            List<Map<String, Object>> mList = DlwEvntService
                    .getEvntAnal1(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            // 신규유지 분석현황
            List<Map<String, Object>> mList2 = DlwEvntService
                    .getEvntAnal2(hmParam);
            listMap2.setRowMaps(mList2);
            mapOutDs.put("ds_output2", listMap2);

            // 지부 분석현황
            List<Map<String, Object>> mList3 = DlwEvntService
                    .getEvntAnal3(hmParam);
            listMap3.setRowMaps(mList3);
            mapOutDs.put("ds_output3", listMap3);

            // 지역 분석현황
            List<Map<String, Object>> mList4 = DlwEvntService
                    .getEvntAnal4(hmParam);
            listMap4.setRowMaps(mList4);
            mapOutDs.put("ds_output4", listMap4);

            // 직영CP 분석현황
            List<Map<String, Object>> mList5 = DlwEvntService
                    .getEvntAnal5(hmParam);
            listMap5.setRowMaps(mList5);
            mapOutDs.put("ds_output5", listMap5);

            // 종교 분석현황
            List<Map<String, Object>> mList6 = DlwEvntService
                    .getEvntAnal6(hmParam);
            listMap6.setRowMaps(mList6);
            mapOutDs.put("ds_output6", listMap6);

            //2018.03.12 접속 로그////////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            //////////////////////////////////////////////////////////////////////////////////////////////////////

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 행사취소현황 분석 메인 리스트 조회
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/cncl-list")
    public ModelAndView getAnnlCnclList(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            // 페이징 정보
            DataSetMap listInDs = (DataSetMap) mapInDs
                    .get("gds_RequestCompVariable");
            if (listInDs != null && listInDs.size() > 0) {
                Map pMap = listInDs.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }

            ParamUtils.addCenterParam(hmParam);

            int nTotal = DlwEvntService.getAnnlCnclCount(hmParam);
            mapOutVar.put("tc_prod", nTotal);

            System.out.println("===================================>>>>>");
            CommonUtils.printLog(hmParam);
            List<Map<String, Object>> mList = DlwEvntService
                    .getAnnlCnclList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            //2018.03.12 접속 로그////////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            //////////////////////////////////////////////////////////////////////////////////////////////////////

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 행사취소현황 및 분석 - 취소분석
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/total-cncl")
    public ModelAndView srchEventCnclAnal(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap(); // 지부별
        DataSetMap listMap2 = new DataSetMap(); // 취소사유별
        DataSetMap listMap3 = new DataSetMap(); // 소요시간별

        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            // 페이징 정보
            DataSetMap listInDs = (DataSetMap) mapInDs
                    .get("gds_RequestCompVariable");
            if (listInDs != null && listInDs.size() > 0) {
                Map pMap = listInDs.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }

            ParamUtils.addCenterParam(hmParam);

            System.out.println("===================================>>>>>");
            CommonUtils.printLog(hmParam);

            // 지부별 분석현황
            List<Map<String, Object>> mList = DlwEvntService
                    .getEvntCncl1(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            // 취소사유별 분석현황
            List<Map<String, Object>> mList2 = DlwEvntService
                    .getEvntCncl2(hmParam);
            listMap2.setRowMaps(mList2);
            mapOutDs.put("ds_output2", listMap2);

            // 취소소요시간별 분석현황
            List<Map<String, Object>> mList3 = DlwEvntService
                    .getEvntCncl3(hmParam);
            listMap3.setRowMaps(mList3);
            mapOutDs.put("ds_output3", listMap3);

            //2018.03.12 접속 로그////////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            //////////////////////////////////////////////////////////////////////////////////////////////////////

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * CP 등록여부 검색한다.
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/evnt-cpchk")
    public ModelAndView getEvntCpChk(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            Object seq = (Object) mapInVar.get("seq");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            // 페이징 정보
            DataSetMap listInDs = (DataSetMap) mapInDs
                    .get("gds_RequestCompVariable");
            if (listInDs != null && listInDs.size() > 0) {
                Map pMap = listInDs.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }

            hmParam.put("seq", seq);

            ParamUtils.addCenterParam(hmParam);

            int nTotal = DlwEvntService.getEvntCpChkCount(hmParam);
            mapOutVar.put("tc_cpcnt", nTotal);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 행사보고서 현황 - 행사분석(상품분류명/지부별)
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/total-branchanal")
    public ModelAndView getEventBranchAnal(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap(); // 헤더
        DataSetMap listMap2 = new DataSetMap(); // 리스트

        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            // 페이징 정보
            DataSetMap listInDs = (DataSetMap) mapInDs
                    .get("gds_RequestCompVariable");
            if (listInDs != null && listInDs.size() > 0) {
                Map pMap = listInDs.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }

            ParamUtils.addCenterParam(hmParam);

            System.out.println("===================================>>>>>");
            CommonUtils.printLog(hmParam);

            // 헤더(가변)
            List<Map<String, Object>> mList = DlwEvntService
                    .getEvntBrchAnal1(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            String branchqury = DlwEvntService.getbranchQury(hmParam);
            hmParam.put("branchqury", branchqury);

            // 분석현황 리스트
            List<Map<String, Object>> mList2 = DlwEvntService
                    .getEvntBrchAnal2(hmParam);
            listMap2.setRowMaps(mList2);
            mapOutDs.put("ds_output2", listMap2);

            //2018.03.12 접속 로그////////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            //////////////////////////////////////////////////////////////////////////////////////////////////////

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 행사보고서 현황 - 행사분석(CP별/상품분류별)
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/total-Emplanal")
    public ModelAndView getEventEmplAnal(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap(); // 헤더
        DataSetMap listMap2 = new DataSetMap(); // 리스트

        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            // 페이징 정보
            DataSetMap listInDs = (DataSetMap) mapInDs
                    .get("gds_RequestCompVariable");
            if (listInDs != null && listInDs.size() > 0) {
                Map pMap = listInDs.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }

            ParamUtils.addCenterParam(hmParam);

            System.out.println("===================================>>>>>");
            CommonUtils.printLog(hmParam);

            // 헤더(가변)
            List<Map<String, Object>> mList = DlwEvntService
                    .getEvntEmplAnal1(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            String emplqury = DlwEvntService.getEmplQury(hmParam);
            hmParam.put("emplqury", emplqury);

            // 분석현황 리스트
            List<Map<String, Object>> mList2 = DlwEvntService
                    .getEvntEmplAnal2(hmParam);
            listMap2.setRowMaps(mList2);
            mapOutDs.put("ds_output2", listMap2);

            //2018.03.12 접속 로그////////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            //////////////////////////////////////////////////////////////////////////////////////////////////////

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 행사보고서 현황 - 행사취소분석(상품분류명/지부별)
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/total-branchcncl")
    public ModelAndView getEventBranchCncl(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap(); // 헤더
        DataSetMap listMap2 = new DataSetMap(); // 리스트

        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            // 페이징 정보
            DataSetMap listInDs = (DataSetMap) mapInDs
                    .get("gds_RequestCompVariable");
            if (listInDs != null && listInDs.size() > 0) {
                Map pMap = listInDs.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }

            ParamUtils.addCenterParam(hmParam);

            System.out.println("===================================>>>>>");
            CommonUtils.printLog(hmParam);

            // 헤더(가변)
            List<Map<String, Object>> mList = DlwEvntService
                    .getEvntBrchCncl1(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            String branchqury = DlwEvntService.getbranchQuryCncl(hmParam);
            hmParam.put("branchqury", branchqury);

            // 분석현황 리스트
            List<Map<String, Object>> mList2 = DlwEvntService
                    .getEvntBrchCncl2(hmParam);
            listMap2.setRowMaps(mList2);
            mapOutDs.put("ds_output2", listMap2);

            //2018.03.12 접속 로그////////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            //////////////////////////////////////////////////////////////////////////////////////////////////////

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 행사보고서 현황 - 행사취소분석(CP별/상품분류별)
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/total-emplcncl")
    public ModelAndView getEventEmplCncl(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap(); // 헤더
        DataSetMap listMap2 = new DataSetMap(); // 리스트

        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            // 페이징 정보
            DataSetMap listInDs = (DataSetMap) mapInDs
                    .get("gds_RequestCompVariable");
            if (listInDs != null && listInDs.size() > 0) {
                Map pMap = listInDs.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }

            ParamUtils.addCenterParam(hmParam);

            System.out.println("===================================>>>>>");
            CommonUtils.printLog(hmParam);

            // 헤더(가변)
            List<Map<String, Object>> mList = DlwEvntService
                    .getEvntEmplCncl1(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            String emplqury = DlwEvntService.getEmplQuryCncl(hmParam);
            hmParam.put("emplqury", emplqury);

            // 분석현황 리스트
            List<Map<String, Object>> mList2 = DlwEvntService
                    .getEvntEmplCncl2(hmParam);
            listMap2.setRowMaps(mList2);
            mapOutDs.put("ds_output2", listMap2);

            //2018.03.12 접속 로그////////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            //////////////////////////////////////////////////////////////////////////////////////////////////////

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 의전행사 사진 분류 - 행사회원 조회
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/getEvntPicSort")
    public ModelAndView getEvntPicSort(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        DataSetMap listMapCpInfo = new DataSetMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            if (listInDs.size() > 0) {
                hmParam = listInDs.get(0);

                String useCpInfo = CommonUtils.nvls((String) hmParam
                        .get("use_cp_info"));

                List<Map<String, Object>> mList = DlwEvntService
                        .getEvntPicSort(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);

                if ("Y".equals(useCpInfo)) {
                    List<Map<String, Object>> lstCpInfo = commonService
                            .selectCpInfo(hmParam);
                    listMapCpInfo.setRowMaps(lstCpInfo);
                    mapOutDs.put("ds_output2", listMapCpInfo);
                }
            }

            //2018.03.13 접속 로그////////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            //////////////////////////////////////////////////////////////////////////////////////////////////////

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 의전행사 사진 출력
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/getEvenPhoto")
    public void getEvenPhoto(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> hmParam = null;
        Map<String, Object> mRow = null;
        String upload_cl = "";
        String file_nm = "";
        String reg_man = "";
        String reg_dm = "";

        try {
            String sFileCode = CommonUtils.nvls(request.getParameter("fileCode"));
            hmParam = new HashMap<String, Object>();
            hmParam.put("file_code", sFileCode);

            String sBasePath = "";

            String osName = System.getProperty("os.name").toLowerCase();
            if (osName.indexOf("windows") >= 0)
            {
            	sBasePath = EgovProperties.getProperty("life.protocolevent.photo.windows.basePath");
            }
            else
            {
            	sBasePath = EgovProperties.getProperty("life.protocolevent.photo.unix.basePath");
            }

            String srcFilePath = sBasePath + "/" + sFileCode.substring(0, 6) + "/" + sFileCode;
            //String srcFilePath = "C:/" + sFileCode.substring(0, 6) + "/" + sFileCode;

            System.out.println("사진 탐색 path :: " + srcFilePath);
            if (osName.indexOf("windows") >= 0)
            {
                srcFilePath = srcFilePath.replaceAll("/", "\\\\");
            }

            System.out.println("디스크에 " + srcFilePath);

            File xDownFile = new File(srcFilePath);
            if (!xDownFile.exists())
            {
            	System.out.println("디스크에 실제 파일이 존재하지 않습니다.");
                //throw new EgovBizException("디스크에 실제 파일이 존재하지 않습니다.");
            }

            String contentType = request.getContentType();

            if (contentType == null)
            {
                if (request.getHeader("user-agent").indexOf("MSIE 5.5") != -1)
                {
                    response.setContentType("doesn/matter;");
                }
                else
                {
                    response.setContentType("application/octet-stream");
                }
            }
            else
            {
                response.setContentType(contentType);
            }

            response.setHeader("Content-Transfer-Encoding:", "binary");
            response.setHeader("Content-Length", "" + xDownFile.length());
            response.setHeader("Pragma", "no-cache;");
            response.setHeader("Expires", "-1;");

            if (xDownFile.exists())
            {
            	byte b[] = new byte[1024 * 4];
            	BufferedInputStream fin = new BufferedInputStream(new FileInputStream(xDownFile));
            	BufferedOutputStream outs = new BufferedOutputStream(response.getOutputStream());

            	try
            	{
            		int read = 0;
            		while ((read = fin.read(b)) != -1)
            		{
            			outs.write(b,0,read);
            		}
            	}
            	catch (Exception e)
            	{
            		e.printStackTrace();
            	}
            	finally
            	{
            		if (outs != null)
            		{
            			outs.close();
            		}
            		if (fin != null)
            		{
            			fin.close();
            		}
            	}

            	outs.flush();
            	outs.close();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 의전행사 분류회원 출력
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/getClassPhoto")
    public void getClassPhoto(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> hmParam = null;
        Map<String, Object> mRow = null;
        String upload_cl = "";
        String file_nm = "";
        String reg_man = "";
        String reg_dm = "";

        try {
            String sFileCode = CommonUtils.nvls(request.getParameter("fileCode"));
            hmParam = new HashMap<String, Object>();
            hmParam.put("file_code", sFileCode);

            String sBasePath = "";

            String osName = System.getProperty("os.name").toLowerCase();
            if (osName.indexOf("windows") >= 0)
            {
            	sBasePath = EgovProperties.getProperty("life.protocolevent.photo.windows.basePath");
            }
            else
            {
            	sBasePath = EgovProperties.getProperty("life.protocolevent.photo.unix.basePath");
            }

            String srcFilePath = sBasePath + "/" + sFileCode.substring(0, 6) + "/" + sFileCode;
            //String srcFilePath = "C:/" + sFileCode.substring(0, 6) + "/" + sFileCode;

            System.out.println("사진 탐색 path :: " + srcFilePath);
            if (osName.indexOf("windows") >= 0)
            {
                srcFilePath = srcFilePath.replaceAll("/", "\\\\");
            }

            System.out.println("디스크에 " + srcFilePath);

            File xDownFile = new File(srcFilePath);
            if (!xDownFile.exists())
            {
            	System.out.println("디스크에 실제 파일이 존재하지 않습니다.");
                //throw new EgovBizException("디스크에 실제 파일이 존재하지 않습니다.");
            }

            String contentType = request.getContentType();

            if (contentType == null)
            {
                if (request.getHeader("user-agent").indexOf("MSIE 5.5") != -1)
                {
                    response.setContentType("doesn/matter;");
                }
                else
                {
                    response.setContentType("application/octet-stream");
                }
            }
            else
            {
                response.setContentType(contentType);
            }

            response.setHeader("Content-Transfer-Encoding:", "binary");
            response.setHeader("Content-Length", "" + xDownFile.length());
            response.setHeader("Pragma", "no-cache;");
            response.setHeader("Expires", "-1;");

            if (xDownFile.exists())
            {
            	byte b[] = new byte[1024 * 4];
            	BufferedInputStream fin = new BufferedInputStream(new FileInputStream(xDownFile));
            	BufferedOutputStream outs = new BufferedOutputStream(response.getOutputStream());

            	try
            	{
            		int read = 0;
            		while ((read = fin.read(b)) != -1)
            		{
            			outs.write(b,0,read);
            		}
            	}
            	catch (Exception e)
            	{
            		e.printStackTrace();
            	}
            	finally
            	{
            		if (outs != null)
            		{
            			outs.close();
            		}
            		if (fin != null)
            		{
            			fin.close();
            		}
            	}

            	outs.flush();
            	outs.close();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 의전행사 사진 다운로드
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    /*
    @RequestMapping(value = "/getEvenPhotoDownLoad")
    public void getEvenPhotoDownLoad(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> hmParam = null;
        Map<String, Object> mRow = null;
        String upload_cl = "";
        String file_nm = "";
        String reg_man = "";
        String reg_dm = "";

        try {
        	ZipOutputStream outputStream = null;
            FileImageInputStream fileInputStream = null;
            byte[] buff = new byte[1024 * 4];

            String sFileCode = CommonUtils.nvls(request.getParameter("fileCode"));
            hmParam = new HashMap<String, Object>();
            hmParam.put("file_code", sFileCode);

            String sBasePath = "";
            String sZipPath = "";

            String[] sArrFileCodes = sFileCode.split(",");

            String osName = System.getProperty("os.name").toLowerCase();
            if (osName.indexOf("windows") >= 0)
            {
            	sBasePath = EgovProperties.getProperty("life.protocolevent.photo.windows.basePath");
            	sZipPath  = EgovProperties.getProperty("life.protocolevent.photodownload.windows.basePath");
            	sBasePath = sBasePath.replaceAll("/", "\\\\");
            	sZipPath  = sZipPath.replaceAll("/", "\\\\");
            }
            else
            {
            	sBasePath = EgovProperties.getProperty("life.protocolevent.photo.unix.basePath");
            	sZipPath  = EgovProperties.getProperty("life.protocolevent.photodownload.unix.basePath");
            }

            //sZipPath = "C:/" + sFileCode.substring(0, 6) + "/";

            File fileZipDownload = new File(sZipPath);
            if(fileZipDownload.exists() == false)
            {
            	fileZipDownload.mkdir();
            }

            try
            {
                outputStream = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(sZipPath + "resultZip.zip"),1024 * 4));

                for(int idx = 0; idx < sArrFileCodes.length; idx++)
                {
                	String srcFilePath = sBasePath + sFileCode.substring(0, 6) + "/" + sArrFileCodes[idx];
                	//String srcFilePath = "C:/" + sFileCode.substring(0, 6) + "/" + sArrFileCodes[idx];

                	if (osName.indexOf("windows") >= 0)
                    {
                		srcFilePath = srcFilePath.replaceAll("/", "\\\\");
                    }

                	System.out.println("파일패스 ::: " + srcFilePath);

                	//File oddImageFile = new File(sBasePath + sFileCode.substring(0, 6) + "/" + sArrFileCodes[idx]);

                	//BufferedImage bi =  ImageIO.read(oddImageFile);
                	//ImageIO.write(bi, "jpg", new File(sZipPath + "/" + sArrFileCodes[idx]));

                	fileInputStream = new FileImageInputStream(new File(srcFilePath));
                    outputStream.putNextEntry(new ZipEntry(sArrFileCodes[idx]));

                    int length;
                    while ((length = fileInputStream.read(buff)) > 0)
                    {
                        outputStream.write(buff, 0, length);
                        System.out.println(length);
                    }

                    outputStream.closeEntry();
                    fileInputStream.close();
                }

                outputStream.close();

            }
            catch (Exception e)
            {
                // Exception Handling
            }
            finally
            {
                try
                {
                    outputStream.closeEntry();
                    outputStream.close();
                    fileInputStream.close();
                }
                catch (IOException e)
                {
                    // Exception Handling
                }
            }

            File xDownFile = new File(sZipPath + "resultZip.zip");
            if (!xDownFile.exists())
            {
            	System.out.println("디스크에 실제 파일이 존재하지 않습니다.");
                //throw new EgovBizException("디스크에 실제 파일이 존재하지 않습니다.");
            }

            String contentType = request.getContentType();

            if (contentType == null)
            {
                if (request.getHeader("user-agent").indexOf("MSIE 5.5") != -1)
                {
                    response.setContentType("doesn/matter;");
                }
                else
                {
                    response.setContentType("application/octet-stream");
                }
            }
            else
            {
                response.setContentType(contentType);
            }

            response.setHeader("Content-Transfer-Encoding:", "binary");
            response.setHeader("Content-Length", "" + xDownFile.length());
            response.setHeader("Pragma", "no-cache;");
            response.setHeader("Expires", "-1;");

            if (xDownFile.exists())
            {
            	byte b[] = new byte[1024 * 4];
            	BufferedInputStream fin = new BufferedInputStream(new FileInputStream(xDownFile));
            	BufferedOutputStream outs = new BufferedOutputStream(response.getOutputStream());

            	try
            	{
            		int read = 0;
            		while ((read = fin.read(b)) != -1)
            		{
            			outs.write(b,0,read);
            		}
            	}
            	catch (Exception e)
            	{
            		e.printStackTrace();
            	}
            	finally
            	{
            		if (outs != null)
            		{
            			outs.close();
            		}
            		if (fin != null)
            		{
            			fin.close();
            		}
            	}

            	outs.flush();
            	outs.close();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    */

    /**
     * 의전행사 사진 분류 - 분류/미분류회원 조회
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/getEvntPicList")
    public ModelAndView getEvntPicList(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            if (listInDs.size() > 0) {
                hmParam = listInDs.get(0);
                List<Map<String, Object>> mList = DlwEvntService
                        .getEvntPicList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            }

            //2018.03.13 접속 로그////////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            //////////////////////////////////////////////////////////////////////////////////////////////////////

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 선택파일 미분류로 이동
     *
     * @param xpDto
     *            XPlatformMapDTO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/disconncectAccnt")
    public ModelAndView disconncectAccnt(XPlatformMapDTO xpDto)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            int rst = DlwEvntService.disconncectAccnt(xpDto);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());

        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 선택파일 회원연결
     *
     * @param xpDto
     *            XPlatformMapDTO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/conncectAccnt")
    public ModelAndView conncectAccnt(XPlatformMapDTO xpDto) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            int rst = DlwEvntService.conncectAccnt(xpDto);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());

        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 행사사진 삭제
     *
     * @param xpDto
     *            XPlatformMapDTO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/deleteEvtPic")
    public ModelAndView deleteEvtPic(XPlatformMapDTO xpDto) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            int rst = DlwEvntService.deleteEvtPic(xpDto);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());

        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 행사사진 다운로드 파일 압축 - 대명 홈페이지에서 실행되어야 함 - 실제 다운로드는 별개의 요청 메소드로 완료됨 - ASIS :
     * evtPicBatchDownload
     *
     * @param xpDto
     *            XPlatformMapDTO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/evtPicBatchDownload")
    public ModelAndView evtPicBatchDownload(XPlatformMapDTO xpDto)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {

            List<Map<String, Object>> lst = DlwEvntService
                    .evtPicBatchDownload(xpDto);
            listMap.setRowMaps(lst);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());

        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 의전행사 사진등록
     *
     * @param xpDto
     *            XPlatformMapDTO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/insertEvtPicInfo")
    public ModelAndView saveMonitorResultReport(XPlatformMapDTO xpDto)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
        	System.out.println(xpDto.toString());
            int rst = DlwEvntService.insertEvtPicInfo(xpDto);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());

        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    @RequestMapping(value = "/saveEvenPhotoUpLoad")
    public void saveEvenPhotoUpLoad(HttpServletRequest request, HttpServletResponse response)throws Exception {
    	// 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        Map<String, Object> mResult = new HashMap<>();
        String fileNm = "";

        PlatformData resData = new PlatformData();
        VariableList resVarList = resData.getVariableList();

        try
        {
            DlwEvntService.saveEvenPhotoUpLoad(request, response);

            resVarList.add("ErrorCode"	, szErrorCode);
            resVarList.add("ErrorMsg"	, szErrorMsg);
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

    /**
     * 외부 일용직 입금대장 조회
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/mngrip-list")
    public ModelAndView getMngrIpList(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            // 페이징 정보
            DataSetMap listInDs = (DataSetMap) mapInDs
                    .get("gds_RequestCompVariable");
            if (listInDs != null && listInDs.size() > 0) {
                Map pMap = listInDs.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }

            String job_duty = (String) hmParam.get("job_duty");

            if (job_duty == null || job_duty.trim().length() == 0) {

            } else {
                String indata = job_duty.substring(0, 1);

                if (indata.equals(",")) {
                    job_duty = job_duty.substring(1);
                } else {
                    job_duty = job_duty;
                }
            }

            hmParam.put("job_duty", job_duty);

            ParamUtils.addCenterParam(hmParam);

            int nTotal = DlwEvntService.getMngrIpCount(hmParam);
            mapOutVar.put("tc_prod", nTotal);

            System.out.println("===================================>>>>>");
            CommonUtils.printLog(hmParam);
            List<Map<String, Object>> mList = DlwEvntService
                    .getMngrIpList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            //2018.03.12 접속 로그////////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            //////////////////////////////////////////////////////////////////////////////////////////////////////

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 장례용품 재고현황
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/stock-close-list")
    public ModelAndView getStockClList(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            // 페이징 정보
            DataSetMap listInDs = (DataSetMap) mapInDs
                    .get("gds_RequestCompVariable");
            if (listInDs != null && listInDs.size() > 0) {
                Map pMap = listInDs.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }

            ParamUtils.addCenterParam(hmParam);

            int nTotal = DlwEvntService.getStockClCount(hmParam);
            mapOutVar.put("tc_prod", nTotal);

            System.out.println("===================================>>>>>");
            CommonUtils.printLog(hmParam);
            List<Map<String, Object>> mList = DlwEvntService
                    .getStockClList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 추가매출 - 직영
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/addsalesin-list")
    public ModelAndView getAddSalesInList(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            ParamUtils.addCenterParam(hmParam);

            System.out
                    .println("===================================>>>>> getAddSalesInList");
            CommonUtils.printLog(hmParam);
            List<Map<String, Object>> mList = DlwEvntService
                    .getAddSalesInList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            //2018.03.12 접속 로그////////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            //////////////////////////////////////////////////////////////////////////////////////////////////////

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 추가매출 - 외주
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/addsalesout-list")
    public ModelAndView getAddSalesOutList(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            ParamUtils.addCenterParam(hmParam);

            System.out
                    .println("===================================>>>>> getAddSalesOutList");
            CommonUtils.printLog(hmParam);
            List<Map<String, Object>> mList = DlwEvntService
                    .getAddSalesOutList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            //2018.03.12 접속 로그////////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            //////////////////////////////////////////////////////////////////////////////////////////////////////

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }
    
    /**
     * 추가매출 - 장지
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/funeral-list")
    public ModelAndView getFuneralList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            List<Map<String, Object>> mList = DlwEvntService.getFuneralList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            //2018.03.12 접속 로그////////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            //////////////////////////////////////////////////////////////////////////////////////////////////////

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
     * 일자별 발주현황
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/orderanal-list")
    public ModelAndView getOrderAnalList(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            // 페이징 정보
            DataSetMap listInDs = (DataSetMap) mapInDs
                    .get("gds_RequestCompVariable");
            if (listInDs != null && listInDs.size() > 0) {
                Map pMap = listInDs.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }

            ParamUtils.addCenterParam(hmParam);

            int nTotal = DlwEvntService.getOrderAnalCount(hmParam);
            mapOutVar.put("tc_prod", nTotal);

            System.out.println("===================================>>>>>");
            CommonUtils.printLog(hmParam);
            List<Map<String, Object>> mList = DlwEvntService
                    .getOrderAnalList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 행사잔금처리현황
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/evntremainamt-list")
    public ModelAndView getEvntRemainAmtList(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            // 페이징 정보
            DataSetMap listInDs = (DataSetMap) mapInDs
                    .get("gds_RequestCompVariable");
            if (listInDs != null && listInDs.size() > 0) {
                Map pMap = listInDs.get(0);
            }

            ParamUtils.addCenterParam(hmParam);

            System.out.println("===================================>>>>>");
            CommonUtils.printLog(hmParam);
            List<Map<String, Object>> mList = DlwEvntService
                    .getEvntRemainAmtList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            //2018.03.12 접속 로그////////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            //////////////////////////////////////////////////////////////////////////////////////////////////////

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 잔금처리현황 비고수정
     *
     * @param pmParam
     *            Map<String, ?>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/remain_bigo_update")
    public ModelAndView RemainBigoUpdate(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            // CommonUtils.printLog("========================================= newprod");

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");

            String emp_gubun = (String) hmParam.get("gubun_code");

            for (int i = 0; i < srchInDs.size(); i++) {
                hmParam = srchInDs.get(i);

                hmParam.put("reg_man", oLoginUser.getUserid());
                hmParam.put("upd_man", oLoginUser.getUserid());
                hmParam.put("emp_gubun", emp_gubun);

                ParamUtils.addCenterParam(hmParam);

                int rowType = ((Integer) hmParam
                        .get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

                System.out
                        .println("==============================================>>"
                                + rowType);
                CommonUtils.printLog(hmParam);

                if (rowType == DataSet.ROW_TYPE_UPDATED) {
                    System.out.println("<<<<<<<<<<<<< 업데이트 >>>>>>>>>>>>>>>>>>");
                    DlwEvntService.RemainBigoUpdate(hmParam); // 수정
                }

                if (rowType == DataSet.ROW_TYPE_INSERTED) {
                    System.out.println("<<<<<<<<<<<<< 신규 >>>>>>>>>>>>>>>>>>");
                }

                if (rowType == DataSet.ROW_TYPE_DELETED) {
                    System.out.println("<<<<<<<<<<<<< 삭제 >>>>>>>>>>>>>>>>>>");
                }

                // mapOutVar.put("tc_mem_prod", mList.size());
                // listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            }

            //2018.03.12 접속 로그////////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            //////////////////////////////////////////////////////////////////////////////////////////////////////

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 일자별 입출고 내역
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/stock-day-list")
    public ModelAndView getStockDayList(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            // 페이징 정보
            DataSetMap listInDs = (DataSetMap) mapInDs
                    .get("gds_RequestCompVariable");
            if (listInDs != null && listInDs.size() > 0) {
                Map pMap = listInDs.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }

            ParamUtils.addCenterParam(hmParam);

            int nTotal = DlwEvntService.getStockDayCount(hmParam);
            mapOutVar.put("tc_prod", nTotal);

            System.out.println("===================================>>>>>");
            CommonUtils.printLog(hmParam);
            List<Map<String, Object>> mList = DlwEvntService
                    .getStockDayList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 물품 및 서비스 제공현황 리스트
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/srvprod-list")
    public ModelAndView getSrvProdList(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap1 = new DataSetMap(); // 상복
        DataSetMap listMap2 = new DataSetMap(); // 제단
        DataSetMap listMap3 = new DataSetMap(); // 장의차량
        DataSetMap listMap4 = new DataSetMap(); // 엠블런스
        DataSetMap listMap5 = new DataSetMap(); // 유골함
        DataSetMap listMap6 = new DataSetMap(); // 장례용품
        DataSetMap listMap6H = new DataSetMap(); // 직영품목(헤더)
        DataSetMap listMap7 = new DataSetMap(); // 현장발주
        DataSetMap listMap8 = new DataSetMap(); // 장례식장
        DataSetMap listMap9 = new DataSetMap(); // 협력업체
        DataSetMap listMap10 = new DataSetMap(); // 기타(용품/인력)
        DataSetMap listMap11 = new DataSetMap(); // 행사품목전체
        DataSetMap listMap12 = new DataSetMap(); // 장례식장(금액)
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            // 페이징 정보
            /*
             * DataSetMap listInDs =
             * (DataSetMap)mapInDs.get("gds_RequestCompVariable"); if (listInDs
             * != null && listInDs.size() > 0) { Map pMap = listInDs.get(0);
             * hmParam.put("startLine", pMap.get("startNum"));
             * hmParam.put("endLine", pMap.get("endNum")); }
             */

            String tab_id = (String) hmParam.get("tab_id");

            ParamUtils.addCenterParam(hmParam);

            System.out.println("===================================>>>>>");
            CommonUtils.printLog(hmParam);

            if (tab_id.equals("0")) {
                // 상복
                List<Map<String, Object>> mList = DlwEvntService
                        .getSrvProdList1(hmParam);
                listMap1.setRowMaps(mList);
                mapOutDs.put("ds_output1", listMap1);
            } else if (tab_id.equals("1")) {
                // 제단
                List<Map<String, Object>> mList2 = DlwEvntService
                        .getSrvProdList2(hmParam);
                listMap2.setRowMaps(mList2);
                mapOutDs.put("ds_output2", listMap2);
            } else if (tab_id.equals("2")) {
                // 장의차량
                List<Map<String, Object>> mList3 = DlwEvntService
                        .getSrvProdList3(hmParam);
                listMap3.setRowMaps(mList3);
                mapOutDs.put("ds_output3", listMap3);
            } else if (tab_id.equals("3")) {
                // 앰블런스
                List<Map<String, Object>> mList4 = DlwEvntService
                        .getSrvProdList4(hmParam);
                listMap4.setRowMaps(mList4);
                mapOutDs.put("ds_output4", listMap4);
            } else if (tab_id.equals("4")) {
                // 유골함
                List<Map<String, Object>> mList5 = DlwEvntService
                        .getSrvProdList5(hmParam);
                listMap5.setRowMaps(mList5);
                mapOutDs.put("ds_output5", listMap5);
            } else if (tab_id.equals("5")) {
                hmParam.put("frg_cl", "0001"); // 장례용품
                // 장례용품 헤더(가변)
                List<Map<String, Object>> mList6H = DlwEvntService
                        .getSrvProdList_Head(hmParam);
                listMap6H.setRowMaps(mList6H);
                mapOutDs.put("ds_output6h", listMap6H);

                DlwEvntService.getSrvProdList_func(hmParam); // 가변데이터 펑션실행
                String gds_qury = DlwEvntService.getSrvProdList_qry(hmParam); // 쿼리가져오기
                hmParam.put("gds_qury", gds_qury); // 쿼리 변수에 담기

                // 장례용품 조회 리스트
                List<Map<String, Object>> mList6 = DlwEvntService
                        .getSrvProdListIn(hmParam);
                listMap6.setRowMaps(mList6);
                mapOutDs.put("ds_output6", listMap6);
            } else if (tab_id.equals("6")) {
                hmParam.put("frg_cl", "0002"); // 현장발주
                // 현장발주 헤더(가변)
                List<Map<String, Object>> mList6H = DlwEvntService
                        .getSrvProdList_Head(hmParam);
                listMap6H.setRowMaps(mList6H);
                mapOutDs.put("ds_output6h", listMap6H);

                DlwEvntService.getSrvProdList_func(hmParam); // 가변데이터 펑션실행
                String gds_qury = DlwEvntService.getSrvProdList_qry(hmParam); // 쿼리가져오기
                hmParam.put("gds_qury", gds_qury); // 쿼리 변수에 담기

                // 장례용품 조회 리스트
                List<Map<String, Object>> mList7 = DlwEvntService
                        .getSrvProdListIn(hmParam);
                listMap7.setRowMaps(mList7);
                mapOutDs.put("ds_output6", listMap7);
            } else if (tab_id.equals("7")) {
                hmParam.put("frg_cl", "0003"); // 장례식장
                // 장례식장 헤더(가변)
                List<Map<String, Object>> mList6H = DlwEvntService
                        .getSrvProdList_Head(hmParam);
                listMap6H.setRowMaps(mList6H);
                mapOutDs.put("ds_output6h", listMap6H);

                DlwEvntService.getSrvProdList_func(hmParam); // 가변데이터 펑션실행
                String gds_qury = DlwEvntService.getSrvProdList_qry(hmParam); // 쿼리가져오기
                hmParam.put("gds_qury", gds_qury); // 쿼리 변수에 담기

                // 장례식장 조회 리스트
                List<Map<String, Object>> mList8 = DlwEvntService
                        .getSrvProdListIn(hmParam);
                listMap8.setRowMaps(mList8);
                mapOutDs.put("ds_output6", listMap8);
            } else if (tab_id.equals("8")) {
                hmParam.put("frg_cl", "0004"); // 협력업체
                // 협력업체 헤더(가변)
                List<Map<String, Object>> mList6H = DlwEvntService
                        .getSrvProdList_Head(hmParam);
                listMap6H.setRowMaps(mList6H);
                mapOutDs.put("ds_output6h", listMap6H);

                DlwEvntService.getSrvProdList_func(hmParam); // 가변데이터 펑션실행
                String gds_qury = DlwEvntService.getSrvProdList_qry(hmParam); // 쿼리가져오기
                hmParam.put("gds_qury", gds_qury); // 쿼리 변수에 담기

                // 협력업체 조회 리스트
                List<Map<String, Object>> mList9 = DlwEvntService
                        .getSrvProdListIn(hmParam);
                listMap9.setRowMaps(mList9);
                mapOutDs.put("ds_output6", listMap9);
            } else if (tab_id.equals("9")) {
                hmParam.put("frg_cl", "0005"); // 기타(용품/인력)
                // 기타(용품/인력) 헤더(가변)
                List<Map<String, Object>> mList6H = DlwEvntService
                        .getSrvProdList_Head(hmParam);
                listMap6H.setRowMaps(mList6H);
                mapOutDs.put("ds_output6h", listMap6H);

                DlwEvntService.getSrvProdList_func(hmParam); // 가변데이터 펑션실행
                String gds_qury = DlwEvntService.getSrvProdList_qry(hmParam); // 쿼리가져오기
                hmParam.put("gds_qury", gds_qury); // 쿼리 변수에 담기

                // 기타(용품/인력) 조회 리스트
                List<Map<String, Object>> mList10 = DlwEvntService
                        .getSrvProdListIn(hmParam);
                listMap10.setRowMaps(mList10);
                mapOutDs.put("ds_output6", listMap10);
            } else if (tab_id.equals("10")) {
                hmParam.put("frg_cl", ""); // 행사품목전체
                // 행사품목전체 헤더(가변)
                List<Map<String, Object>> mList6H = DlwEvntService
                        .getSrvProdList_Head(hmParam);
                listMap6H.setRowMaps(mList6H);
                mapOutDs.put("ds_output6h", listMap6H);

                DlwEvntService.getSrvProdList_func(hmParam); // 가변데이터 펑션실행
                String gds_qury = DlwEvntService.getSrvProdList_qry(hmParam); // 쿼리가져오기
                hmParam.put("gds_qury", gds_qury); // 쿼리 변수에 담기

                // 행사품목전체 조회 리스트
                List<Map<String, Object>> mList11 = DlwEvntService
                        .getSrvProdListIn(hmParam);
                listMap11.setRowMaps(mList11);
                mapOutDs.put("ds_output6", listMap11);
            } else if (tab_id.equals("11")) {
                hmParam.put("frg_cl", "0003"); // 장례식장(금액)
                // 장례식장 헤더(가변)
                List<Map<String, Object>> mList6H = DlwEvntService
                        .getSrvProdList_Head(hmParam);
                listMap6H.setRowMaps(mList6H);
                mapOutDs.put("ds_output6h", listMap6H);

                DlwEvntService.getSrvProdList_func2(hmParam); // 가변데이터 펑션실행
                String gds_qury = DlwEvntService.getSrvProdList_qry(hmParam); // 쿼리가져오기
                hmParam.put("gds_qury", gds_qury); // 쿼리 변수에 담기

                // 장례식장(금액) 조회 리스트
                hmParam.put("frg_cl", ""); // 장례식장(금액) --> 전체조회되도록!
                List<Map<String, Object>> mList12 = DlwEvntService
                        .getSrvProdListIn(hmParam);
                listMap12.setRowMaps(mList12);
                mapOutDs.put("ds_output6", listMap12);
            }

            //2018.03.12 접속 로그////////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            //////////////////////////////////////////////////////////////////////////////////////////////////////

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 물품 제공현황 분석리스트
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/srvprod-anal")
    public ModelAndView getSrvProdAnal(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap1 = new DataSetMap(); // 상복
        DataSetMap listMap2 = new DataSetMap(); // 제단
        DataSetMap listMap3 = new DataSetMap(); // 장의차량
        DataSetMap listMap4 = new DataSetMap(); // 장례식장(CP)
        DataSetMap listMap5 = new DataSetMap(); // 월별분석

        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            /* 물품 제공현황 분석 - 월별분석(탭) 화면에서 연도별로 조회할 수 있도록 요청 => stt_dt 추가 : 2018.02.06 */
            String stt_dt = (String) hmParam.get("stt_dt");
            stt_dt = stt_dt.substring(0, 4);
            hmParam.put("stt_dt", stt_dt);

            String tab_id = (String) hmParam.get("tab_id");

            ParamUtils.addCenterParam(hmParam);

            System.out.println("===================================>>>>>");
            CommonUtils.printLog(hmParam);

            if (tab_id.equals("0")) {
                // 상복
                List<Map<String, Object>> mList = DlwEvntService
                        .getSrvProdAnal1(hmParam);
                listMap1.setRowMaps(mList);
                mapOutDs.put("ds_output1", listMap1);
            } else if (tab_id.equals("1")) {
                // 제단
                List<Map<String, Object>> mList2 = DlwEvntService
                        .getSrvProdAnal2(hmParam);
                listMap2.setRowMaps(mList2);
                mapOutDs.put("ds_output2", listMap2);
            } else if (tab_id.equals("2")) {
                // 장의차량
                List<Map<String, Object>> mList3 = DlwEvntService
                        .getSrvProdAnal3(hmParam);
                listMap3.setRowMaps(mList3);
                mapOutDs.put("ds_output3", listMap3);
            } else if (tab_id.equals("3")) {
                // 장례식장
                List<Map<String, Object>> mList4 = DlwEvntService
                        .getSrvProdAnal4(hmParam);
                listMap4.setRowMaps(mList4);
                mapOutDs.put("ds_output4", listMap4);
            } else if (tab_id.equals("4")) {
                // 월별분석
                List<Map<String, Object>> mList5 = DlwEvntService
                        .getSrvProdAnal5(hmParam);
                listMap5.setRowMaps(mList5);
                mapOutDs.put("ds_output5", listMap5);
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 행사 SMS 전송 시 현금영수증 정보 가져오기
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/smspayment")
    public ModelAndView getsmsPayMent(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            Object accnt_no = (Object) mapInVar.get("accnt_no");
            Object rpt_gubun = (Object) mapInVar.get("rpt_gubun");

            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            hmParam.put("accnt_no", accnt_no);
            hmParam.put("rpt_gubun", rpt_gubun);

            ParamUtils.addCenterParam(hmParam);

            System.out
                    .println("===================================>>>>> getsmsPayMent");
            CommonUtils.printLog(hmParam);

            List<Map<String, Object>> mList = DlwEvntService
                    .getsmsPayMent(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 월별 지부별 장의용품 재고현황
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/evenmonth-brlist")
    public ModelAndView getMonthBrList(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            ParamUtils.addCenterParam(hmParam);

            System.out
                    .println("===================================>>>>> getMonthBrList");
            CommonUtils.printLog(hmParam);
            List<Map<String, Object>> mList = DlwEvntService
                    .getMonthBrList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 월별 창고별 장의용품 재고현황
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/evenmonth-whlist")
    public ModelAndView getMonthWhList(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            ParamUtils.addCenterParam(hmParam);

            System.out
                    .println("===================================>>>>> getMonthWhList");
            CommonUtils.printLog(hmParam);
            List<Map<String, Object>> mList = DlwEvntService
                    .getMonthWhList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 일자별 지부별 물품 입고/사용내역 조회 리스트
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/evengds-list")
    public ModelAndView getGdsIpUseList(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap1 = new DataSetMap(); // 입고 리스트
        DataSetMap listMap1H = new DataSetMap(); // 입고 헤더
        DataSetMap listMap2 = new DataSetMap(); // 사용 리스트
        DataSetMap listMap2H = new DataSetMap(); // 사용 헤더

        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            String tab_id = (String) hmParam.get("tab_id");

            ParamUtils.addCenterParam(hmParam);

            System.out.println("===================================>>>>>");
            CommonUtils.printLog(hmParam);

            if (tab_id.equals("0")) {
                // 입고 내역 헤더(가변)
                List<Map<String, Object>> mList1H = DlwEvntService
                        .getGdsIpList_Head(hmParam);
                listMap1H.setRowMaps(mList1H);
                mapOutDs.put("ds_output_hd1", listMap1H);

                DlwEvntService.getGdsIpList_func(hmParam); // 가변데이터 펑션실행
                String gds_qury = DlwEvntService.getGdsIpList_qry(hmParam); // 쿼리가져오기
                hmParam.put("gds_qury", gds_qury); // 쿼리 변수에 담기

                // 입고 내역 조회 리스트
                List<Map<String, Object>> mList1 = DlwEvntService
                        .getGdsIpList(hmParam);
                listMap1.setRowMaps(mList1);
                mapOutDs.put("ds_output1", listMap1);
            } else if (tab_id.equals("1")) {
                // 사용 내역 헤더(가변)
                List<Map<String, Object>> mList2H = DlwEvntService
                        .getGdsUseList_Head(hmParam);
                listMap2H.setRowMaps(mList2H);
                mapOutDs.put("ds_output_hd2", listMap2H);

                DlwEvntService.getGdsUseList_func(hmParam); // 가변데이터 펑션실행
                String gds_qury = DlwEvntService.getGdsUseList_qry(hmParam); // 쿼리가져오기
                hmParam.put("gds_qury", gds_qury); // 쿼리 변수에 담기

                // 사용 내역 조회 리스트
                List<Map<String, Object>> mList2 = DlwEvntService
                        .getGdsUseList(hmParam);
                listMap2.setRowMaps(mList2);
                mapOutDs.put("ds_output2", listMap2);
            }

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * CP별/일반경비별 현황
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/srchcost-list")
    public ModelAndView getCostList(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            ParamUtils.addCenterParam(hmParam);

            System.out
                    .println("===================================>>>>> getMonthWhList");
            CommonUtils.printLog(hmParam);
            List<Map<String, Object>> mList = DlwEvntService
                    .getCostList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 회원별 일반경비별 현황(CP별 경비현황 상세팝업화면)
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/srchcostmem-list")
    public ModelAndView getCostMemList(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            ParamUtils.addCenterParam(hmParam);

            System.out
                    .println("===================================>>>>> getCostMemList");
            CommonUtils.printLog(hmParam);
            List<Map<String, Object>> mList = DlwEvntService
                    .getCostMemList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 손익분석 집계표
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/getBenefit-list")
    public ModelAndView getBenefitList(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap1 = new DataSetMap(); // 행사
        DataSetMap listMap2 = new DataSetMap(); // 월
        DataSetMap listMap3 = new DataSetMap(); // 지부
        DataSetMap listMap4 = new DataSetMap(); // CP
        DataSetMap listMap5 = new DataSetMap(); // 외주업체
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            String tab_id = (String) hmParam.get("tab_id");

            ParamUtils.addCenterParam(hmParam);

            System.out.println("===================================>>>>>");
            CommonUtils.printLog(hmParam);

            if (tab_id.equals("0")) {
                List<Map<String, Object>> mList = DlwEvntService
                        .getBenefitList1(hmParam);
                listMap1.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap1);
            } else if (tab_id.equals("1")) {
                List<Map<String, Object>> mList2 = DlwEvntService
                        .getBenefitList2(hmParam);
                listMap2.setRowMaps(mList2);
                mapOutDs.put("ds_output", listMap2);
            } else if (tab_id.equals("2")) {
                List<Map<String, Object>> mList3 = DlwEvntService
                        .getBenefitList3(hmParam);
                listMap3.setRowMaps(mList3);
                mapOutDs.put("ds_output", listMap3);
            } else if (tab_id.equals("3")) {
                List<Map<String, Object>> mList4 = DlwEvntService
                        .getBenefitList4(hmParam);
                listMap4.setRowMaps(mList4);
                mapOutDs.put("ds_output", listMap4);
            } else if (tab_id.equals("4")) {
                List<Map<String, Object>> mList5 = DlwEvntService
                        .getBenefitList5(hmParam);
                listMap5.setRowMaps(mList5);
                mapOutDs.put("ds_output", listMap5);
            }

            //2018.03.12 접속 로그////////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            //////////////////////////////////////////////////////////////////////////////////////////////////////

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 재고마감 - 조회리스트
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/getstock-list")
    public ModelAndView getStockMgList(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap(); // 품목별
        DataSetMap listMap2 = new DataSetMap(); // 창고별

        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            ParamUtils.addCenterParam(hmParam);

            System.out
                    .println("===================================>>>>> getStockMgList");
            CommonUtils.printLog(hmParam);

            // 품목별 마감
            List<Map<String, Object>> mList = DlwEvntService
                    .getStockGoodsList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            // 창고별 마감
            List<Map<String, Object>> mList2 = DlwEvntService
                    .getStockWhList(hmParam);
            listMap2.setRowMaps(mList2);
            mapOutDs.put("ds_output2", listMap2);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 재고마감 - 마감취소
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/deletestock")
    public ModelAndView deleteStockMg(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();

        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            ParamUtils.addCenterParam(hmParam);

            System.out
                    .println("===================================>>>>> deleteStockMg");
            CommonUtils.printLog(hmParam);

            // 마감취소
            DlwEvntService.deleteStockMg(hmParam);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 재고마감 - 마감저장(프로시저 호출)
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/insertstock")
    public ModelAndView insertStockMg(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            hmParam.put("reg_man", oLoginUser.getUserid());

            ParamUtils.addCenterParam(hmParam);

            System.out
                    .println("===================================>>>>> insertStockMg");
            CommonUtils.printLog(hmParam);

            // 마감저장
            DlwEvntService.insertStockMg(hmParam);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 대체용품 수정 및 저장
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/repsave")
    public ModelAndView insertRepGoodsSave(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();

        Map<String, Object> hmParam = new HashMap<String, Object>();
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            // CommonUtils.printLog("========================================= newprod");

            DataSetMap srch_rep = (DataSetMap) mapInDs.get("ds_input");

            Object seq = (Object) mapInVar.get("seq");
            Object accnt_no = (Object) mapInVar.get("accnt_no");
            CommonUtils.printLog(accnt_no);

            // 대체용품 수정&저장
            for (int i = 0; i < srch_rep.size(); i++) {
                hmParam = srch_rep.get(i);

                hmParam.put("reg_man", oLoginUser.getUserid());
                hmParam.put("upd_man", oLoginUser.getUserid());
                hmParam.put("seq", seq);
                hmParam.put("accnt_no", accnt_no);

                ParamUtils.addCenterParam(hmParam);

                int rowType = ((Integer) hmParam
                        .get(XPlatformConstant.DATASET_ROW_TYPE)).intValue();

                System.out
                        .println("==============================================>>"
                                + rowType);
                CommonUtils.printLog(hmParam);

                if (rowType == DataSet.ROW_TYPE_UPDATED) {
                    System.out.println("<<<<<<<<<<<<< 업데이트 >>>>>>>>>>>>>>>>>>");
                    DlwEvntService.updateRepGoods(hmParam); // 수정
                }

                if (rowType == DataSet.ROW_TYPE_INSERTED) {

                    System.out.println("<<<<<<<<<<<<< 신규 >>>>>>>>>>>>>>>>>>");
                    DlwEvntService.insertRepGoods(hmParam); // 등록
                }

                if (rowType == DataSet.ROW_TYPE_DELETED) {

                    System.out.println("<<<<<<<<<<<<< 삭제 >>>>>>>>>>>>>>>>>>");
                    DlwEvntService.deleteRepGoods(hmParam); // 삭제
                }

                mapOutDs.put("ds_output", listMap);
            } // end tab1 for

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 대체 용품 조회(개인)
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/rep-list")
    public ModelAndView getEvtRepList(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap(); // 품목별

        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            ParamUtils.addCenterParam(hmParam);

            System.out
                    .println("===================================>>>>> getEvtRepList");
            CommonUtils.printLog(hmParam);

            // 품목별 마감
            List<Map<String, Object>> mList = DlwEvntService
                    .getEvtRepList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 대체용품 전체 현황
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/repgoods-list")
    public ModelAndView getRepGoodsList(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            // 페이징 정보
            DataSetMap listInDs = (DataSetMap) mapInDs
                    .get("gds_RequestCompVariable");
            if (listInDs != null && listInDs.size() > 0) {
                Map pMap = listInDs.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }

            ParamUtils.addCenterParam(hmParam);

            int nTotal = DlwEvntService.getRepGoodsCount(hmParam);
            mapOutVar.put("tc_prod", nTotal);

            System.out.println("===================================>>>>>");
            CommonUtils.printLog(hmParam);
            List<Map<String, Object>> mList = DlwEvntService
                    .getRepGoodsList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            //2018.03.12 접속 로그////////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            //////////////////////////////////////////////////////////////////////////////////////////////////////

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 행사유지현황 분석
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/evt_ujilist")
    public ModelAndView getEvtUjiList(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            ParamUtils.addCenterParam(hmParam);

            System.out.println("===================================>>>>>");
            CommonUtils.printLog(hmParam);
            List<Map<String, Object>> mList = DlwEvntService
                    .getEvtUjiList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            //2018.03.12 접속 로그////////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            //////////////////////////////////////////////////////////////////////////////////////////////////////

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }

    /**
     * 행사문자 보낼 시 회원정보 조회
     *
     * @param pmParam
     *            Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/chatmeminfo")
    public ModelAndView getEvntChatInfo(XPlatformMapDTO xpDto, Model model)
            throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        try {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap srchInDs = (DataSetMap) mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }

            ParamUtils.addCenterParam(hmParam);

            System.out.println("===================================>>>>>");
            CommonUtils.printLog(hmParam);
            List<Map<String, Object>> mList = DlwEvntService
                    .getEvntChatInfo(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME,
                    xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,
                    xpDto.getOutDataSetMap());
        } catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);

        return modelAndView;
    }


    /**
     * CJ문자보낼때.. 회원정보 조회
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/cjchatmeminfo")
    public ModelAndView getcjEvntChatInfo(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            ParamUtils.addCenterParam(hmParam);

            System.out.println("===================================>>>>>");
            CommonUtils.printLog(hmParam);
            List<Map<String, Object>> mList = DlwEvntService.getEvntcjChatInfo(hmParam);
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
     * 알림톡 구동시 가상계좌 검색
     * 최초작성 : 2017-09-06 김준호
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/cjchatvrtlinfo")
    public ModelAndView getEventcjChatVrtlInfo(XPlatformMapDTO xpDto, Model model) throws Exception {
        // 결과 담는 변수
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        // 화면에서 넘어온 값 담는 변수
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode="0";
        String szErrorMsg="OK";

        // 시작
        try {
           // Map <String, Object> mapInVar     = xpDto.getInVariableMap(); // 화면에서 넘겨준 var 변수
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap(); // 화면에서 넘겨준 ds 변수
            //Map <String, Object> mapOutVar    = xpDto.getOutVariableMap(); // 화면으로 넘겨줄 var 변수
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap(); // 화면으로 넘겨줄 ds 변수

            // 화면에서 받은 ds변수를 가져온다.
            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }
            System.out.println("==============================");
            System.out.println(hmParam.get("mem_no"));
            /*****************************************************
             현재 세션 센터 코드
             ParamUtils.addCenterParam(hmParam);
             pmParam.put("cntr_cd", oLoginUser.getCentercd());
             ******************************************************/
            ParamUtils.addCenterParam(hmParam);
            // 담은 변수들을 로그로 남긴다.
            System.out.println("DlwEvntController.getcjVrtlChatInfo :: 알림톡 회원 가상계좌 정보 조회");
            CommonUtils.printLog(hmParam);

            // 검색후 결과를 담는다.
            List<Map<String, Object>> mList = DlwEvntService.getEventcjChatVrtlInfo(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            // 담은 결과를 modelAndView에 입력
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