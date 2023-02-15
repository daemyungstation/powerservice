/*
 * (@)# DlwCondController.java
 *
 * @author 석민
 * @version 1.0
 * @date 2016/06/01
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;



//2018.03.20 로그 추가
import powerservice.business.common.service.CommonService;
import powerservice.business.dlw.service.DataAthrQuryService;
import powerservice.business.dlw.service.DlwCondService;
import powerservice.core.util.ParamUtils;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;
/**
 * 대명라이프웨이 현황 정보를 관리한다
 *
 * Copyright (c) 2016 Company Inwoo Tech Inc.
 *
 * @author 석민
 * @version 1.0
 * @date 2016/06/01
 * @프로그램ID DlwCond
 */
@Controller
@RequestMapping(value = "/dlw/cond")
public class DlwCondController {

    @Resource
    private DlwCondService DlwCondService;

    @Resource
    private DataAthrQuryService dataAthrQuryService;

    @Resource
    private CommonService commonService;

    /**
     * 대명라이프웨이 종합 회원 현황을 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/mem-list")
    public ModelAndView getCondMemList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap listStatMap = new DataSetMap();
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
            if (listInDs.size() > 0) {
                hmParam = listInDs.get(0);

                String excel_fg = (String)mapInVar.get("excel_fg");
                // 페이징 정보
                DataSetMap listInGds = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                if (listInGds != null && listInGds.size() > 0) {
                    Map pMap = listInGds.get(0);
                    if (!"Y".equals(excel_fg)) {
                        hmParam.put("startLine", pMap.get("startNum"));
                        hmParam.put("endLine", pMap.get("endNum"));
                    }
                }

                //세션
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("paramEmpleNo", hmParam.get("rgsr_id"));
                hmParam.put("paramAs", "EMP");
                String dataAthrQury = dataAthrQuryService.getDataAthrQury(hmParam);
                hmParam.put("dataAthrQury", dataAthrQury);

                int nTotal = DlwCondService.getCondMemCount(hmParam);
                mapOutVar.put("tc_condMem", nTotal);

                List<Map<String, Object>> mList = DlwCondService.getCondMemList(hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);
            }

            //2018.03.22 접속 로그//////////////////////////////////////////////////////////////////////////////
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
     * 대명라이프웨이 종합 입금 현황을 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/pay-list")
    public ModelAndView getCondPayList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap listStatMap = new DataSetMap();
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
            if (listInDs.size() > 0) {
                hmParam = listInDs.get(0);

                String excel_fg = (String)mapInVar.get("excel_fg");
                // 페이징 정보
                DataSetMap listInGds = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                if (listInGds != null && listInGds.size() > 0) {
                    Map pMap = listInGds.get(0);
                    if (!"Y".equals(excel_fg)) {
                        hmParam.put("startLine", pMap.get("startNum"));
                        hmParam.put("endLine", pMap.get("endNum"));
                    }
                }

                //세션
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("paramEmpleNo", hmParam.get("rgsr_id"));
                hmParam.put("paramAs", "EMP");
                String dataAthrQury = dataAthrQuryService.getDataAthrQury(hmParam);
                hmParam.put("dataAthrQury", dataAthrQury);

                if (!"Y".equals(excel_fg)) {
                    int nTotal = DlwCondService.getPayCount(hmParam);
                    mapOutVar.put("tc_condPay", nTotal);
                }

                List<Map<String, Object>> mList = DlwCondService.getPayList(hmParam);
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
     * 대명라이프웨이 종합 입금 현황을 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/cbo-certf")
    public ModelAndView getCertfCond(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap listStatMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            hmParam.put("menu_cd", mapInVar.get("menu_cd"));
            hmParam.put("lvl", mapInVar.get("lvl"));
            hmParam.put("high_cl_cd", mapInVar.get("high_cl_cd"));

            List<Map<String, Object>> mList = DlwCondService.getCertfCond(hmParam);
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
     * 대명라이프웨이 연체현황을 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/delay-pay-list")
    public ModelAndView getDelayPayCondList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap delayPayByProdAllMap = new DataSetMap();

        DataSetMap delayPayByProdOneYearMap = new DataSetMap();
        DataSetMap delayPayByProdTwoYearMap = new DataSetMap();
        DataSetMap delayPayByProdThreeYearMap = new DataSetMap();
        DataSetMap delayPayByProd1Map = new DataSetMap();
        DataSetMap delayPayByProd2Map = new DataSetMap();
        DataSetMap delayPayByProd3Map = new DataSetMap();
        DataSetMap delayPayByProd4Map = new DataSetMap();
        DataSetMap delayPayByProd5Map = new DataSetMap();
        DataSetMap delayPayByProd6Map = new DataSetMap();
        DataSetMap delayPayByProd7Map = new DataSetMap();
        DataSetMap delayPayByProd8Map = new DataSetMap();
        DataSetMap delayPayByProd9Map = new DataSetMap();
        DataSetMap delayPayByProd10Map = new DataSetMap();
        DataSetMap delayPayByProd11Map = new DataSetMap();
        DataSetMap delayPayByProd12Map = new DataSetMap();

        DataSetMap delayPayByBondAllMap = new DataSetMap();
        DataSetMap delayPayByBondOneYearMap = new DataSetMap();
        DataSetMap delayPayByBondTwoYearMap = new DataSetMap();
        DataSetMap delayPayByBondThreeYearMap = new DataSetMap();
        DataSetMap delayPayByBond1Map = new DataSetMap();
        DataSetMap delayPayByBond2Map = new DataSetMap();
        DataSetMap delayPayByBond3Map = new DataSetMap();
        DataSetMap delayPayByBond4Map = new DataSetMap();
        DataSetMap delayPayByBond5Map = new DataSetMap();
        DataSetMap delayPayByBond6Map = new DataSetMap();
        DataSetMap delayPayByBond7Map = new DataSetMap();
        DataSetMap delayPayByBond8Map = new DataSetMap();
        DataSetMap delayPayByBond9Map = new DataSetMap();
        DataSetMap delayPayByBond10Map = new DataSetMap();
        DataSetMap delayPayByBond11Map = new DataSetMap();
        DataSetMap delayPayByBond12Map = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            List<Map<String, Object>> RealPayAll = new ArrayList<Map<String,Object>>();

            List<Map<String, Object>> delayPayByProdAll = new ArrayList<Map<String,Object>>();
            List<Map<String, Object>> delayPayByProdOneYear = new ArrayList<Map<String,Object>>();
            List<Map<String, Object>> delayPayByProdTwoYear = new ArrayList<Map<String,Object>>();
            List<Map<String, Object>> delayPayByProdThreeYear = new ArrayList<Map<String,Object>>();
            List<Map<String, Object>> delayPayByProd1 = new ArrayList<Map<String,Object>>();
            List<Map<String, Object>> delayPayByProd2 = new ArrayList<Map<String,Object>>();
            List<Map<String, Object>> delayPayByProd3 = new ArrayList<Map<String,Object>>();
            List<Map<String, Object>> delayPayByProd4 = new ArrayList<Map<String,Object>>();
            List<Map<String, Object>> delayPayByProd5 = new ArrayList<Map<String,Object>>();
            List<Map<String, Object>> delayPayByProd6 = new ArrayList<Map<String,Object>>();
            List<Map<String, Object>> delayPayByProd7 = new ArrayList<Map<String,Object>>();
            List<Map<String, Object>> delayPayByProd8 = new ArrayList<Map<String,Object>>();
            List<Map<String, Object>> delayPayByProd9 = new ArrayList<Map<String,Object>>();
            List<Map<String, Object>> delayPayByProd10 = new ArrayList<Map<String,Object>>();
            List<Map<String, Object>> delayPayByProd11 = new ArrayList<Map<String,Object>>();
            List<Map<String, Object>> delayPayByProd12 = new ArrayList<Map<String,Object>>();

            List<Map<String, Object>> delayPayByBondAll = new ArrayList<Map<String,Object>>();
            List<Map<String, Object>> delayPayByBondOneYear = new ArrayList<Map<String,Object>>();
            List<Map<String, Object>> delayPayByBondTwoYear = new ArrayList<Map<String,Object>>();
            List<Map<String, Object>> delayPayByBondThreeYear = new ArrayList<Map<String,Object>>();
            List<Map<String, Object>> delayPayByBond1 = new ArrayList<Map<String,Object>>();
            List<Map<String, Object>> delayPayByBond2 = new ArrayList<Map<String,Object>>();
            List<Map<String, Object>> delayPayByBond3 = new ArrayList<Map<String,Object>>();
            List<Map<String, Object>> delayPayByBond4 = new ArrayList<Map<String,Object>>();
            List<Map<String, Object>> delayPayByBond5 = new ArrayList<Map<String,Object>>();
            List<Map<String, Object>> delayPayByBond6 = new ArrayList<Map<String,Object>>();
            List<Map<String, Object>> delayPayByBond7 = new ArrayList<Map<String,Object>>();
            List<Map<String, Object>> delayPayByBond8 = new ArrayList<Map<String,Object>>();
            List<Map<String, Object>> delayPayByBond9 = new ArrayList<Map<String,Object>>();
            List<Map<String, Object>> delayPayByBond10 = new ArrayList<Map<String,Object>>();
            List<Map<String, Object>> delayPayByBond11 = new ArrayList<Map<String,Object>>();
            List<Map<String, Object>> delayPayByBond12 = new ArrayList<Map<String,Object>>();

            hmParam.put("bas_dt", mapInVar.get("bas_dt"));

            // 연체 (전체)
            hmParam.put("rntm", "all");
            delayPayByProdAll = DlwCondService.getDelayPayByProd(hmParam);       // 상품기준
            RealPayAll = DlwCondService.getRealPay(hmParam);                     // 출금실적
            delayPayByBondAll = DlwCondService.getDelayPayByBond(hmParam);      // 채권기준

            if (delayPayByProdAll != null && delayPayByProdAll.size() > 0 && RealPayAll != null && RealPayAll.size() > 0) {
                if (delayPayByProdAll.size() == RealPayAll.size()) {
                    for (int i = 0; i < delayPayByProdAll.size(); i++) {
                        Map<String, Object> delayPayMap = delayPayByProdAll.get(i);
                        Map<String, Object> RealPayMap = RealPayAll.get(i);
                        delayPayMap.putAll(RealPayMap);
                    }
                }
            }

            // 연체 (13~24) (25~36) (37~)
            hmParam.put("rntm", "one_year");
            delayPayByProdOneYear = DlwCondService.getDelayPayByProd(hmParam);   // 상품기준
            delayPayByBondOneYear = DlwCondService.getDelayPayByBond(hmParam);   // 채권기준
            hmParam.put("rntm", "two_year");
            delayPayByProdTwoYear = DlwCondService.getDelayPayByProd(hmParam);   // 상품기준
            delayPayByBondTwoYear = DlwCondService.getDelayPayByBond(hmParam);   // 채권기준
            hmParam.put("rntm", "three_year");
            delayPayByProdThreeYear = DlwCondService.getDelayPayByProd(hmParam); // 상품기준
            delayPayByBondThreeYear = DlwCondService.getDelayPayByBond(hmParam); // 채권기준

            // 연체 (회차별)
            hmParam.put("rntm", "1");
            delayPayByProd1 = DlwCondService.getDelayPayByProd(hmParam);  // 상품기준
            delayPayByBond1 = DlwCondService.getDelayPayByBond(hmParam);  // 채권기준
            hmParam.put("rntm", "2");
            delayPayByProd2 = DlwCondService.getDelayPayByProd(hmParam);  // 상품기준
            delayPayByBond2 = DlwCondService.getDelayPayByBond(hmParam);  // 채권기준
            hmParam.put("rntm", "3");
            delayPayByProd3 = DlwCondService.getDelayPayByProd(hmParam);  // 상품기준
            delayPayByBond3 = DlwCondService.getDelayPayByBond(hmParam);  // 채권기준
            hmParam.put("rntm", "4");
            delayPayByProd4 = DlwCondService.getDelayPayByProd(hmParam);  // 상품기준
            delayPayByBond4 = DlwCondService.getDelayPayByBond(hmParam);  // 채권기준
            hmParam.put("rntm", "5");
            delayPayByProd5 = DlwCondService.getDelayPayByProd(hmParam);  // 상품기준
            delayPayByBond5 = DlwCondService.getDelayPayByBond(hmParam);  // 채권기준
            hmParam.put("rntm", "6");
            delayPayByProd6 = DlwCondService.getDelayPayByProd(hmParam);  // 상품기준
            delayPayByBond6 = DlwCondService.getDelayPayByBond(hmParam);  // 채권기준
            hmParam.put("rntm", "7");
            delayPayByProd7 = DlwCondService.getDelayPayByProd(hmParam);  // 상품기준
            delayPayByBond7 = DlwCondService.getDelayPayByBond(hmParam);  // 채권기준
            hmParam.put("rntm", "8");
            delayPayByProd8 = DlwCondService.getDelayPayByProd(hmParam);  // 상품기준
            delayPayByBond8 = DlwCondService.getDelayPayByBond(hmParam);  // 채권기준
            hmParam.put("rntm", "9");
            delayPayByProd9 = DlwCondService.getDelayPayByProd(hmParam);  // 상품기준
            delayPayByBond9 = DlwCondService.getDelayPayByBond(hmParam);  // 채권기준
            hmParam.put("rntm", "10");
            delayPayByProd10 = DlwCondService.getDelayPayByProd(hmParam); // 상품기준
            delayPayByBond10 = DlwCondService.getDelayPayByBond(hmParam); // 채권기준
            hmParam.put("rntm", "11");
            delayPayByProd11 = DlwCondService.getDelayPayByProd(hmParam); // 상품기준
            delayPayByBond11 = DlwCondService.getDelayPayByBond(hmParam); // 채권기준
            hmParam.put("rntm", "12");
            delayPayByProd12 = DlwCondService.getDelayPayByProd(hmParam); // 상품기준
            delayPayByBond12 = DlwCondService.getDelayPayByBond(hmParam); // 채권기준


            delayPayByProdAllMap.setRowMaps(delayPayByProdAll);
            delayPayByBondAllMap.setRowMaps(delayPayByBondAll);
            delayPayByProdOneYearMap.setRowMaps(delayPayByProdOneYear);
            delayPayByBondOneYearMap.setRowMaps(delayPayByBondOneYear);
            delayPayByProdTwoYearMap.setRowMaps(delayPayByProdTwoYear);
            delayPayByBondTwoYearMap.setRowMaps(delayPayByBondTwoYear);
            delayPayByProdThreeYearMap.setRowMaps(delayPayByProdThreeYear);
            delayPayByBondThreeYearMap.setRowMaps(delayPayByBondThreeYear);
            delayPayByProd1Map.setRowMaps(delayPayByProd1);
            delayPayByBond1Map.setRowMaps(delayPayByBond1);
            delayPayByProd2Map.setRowMaps(delayPayByProd2);
            delayPayByBond2Map.setRowMaps(delayPayByBond2);
            delayPayByProd3Map.setRowMaps(delayPayByProd3);
            delayPayByBond3Map.setRowMaps(delayPayByBond3);
            delayPayByProd4Map.setRowMaps(delayPayByProd4);
            delayPayByBond4Map.setRowMaps(delayPayByBond4);
            delayPayByProd5Map.setRowMaps(delayPayByProd5);
            delayPayByBond5Map.setRowMaps(delayPayByBond5);
            delayPayByProd6Map.setRowMaps(delayPayByProd6);
            delayPayByBond6Map.setRowMaps(delayPayByBond6);
            delayPayByProd7Map.setRowMaps(delayPayByProd7);
            delayPayByBond7Map.setRowMaps(delayPayByBond7);
            delayPayByProd8Map.setRowMaps(delayPayByProd8);
            delayPayByBond8Map.setRowMaps(delayPayByBond8);
            delayPayByProd9Map.setRowMaps(delayPayByProd9);
            delayPayByBond9Map.setRowMaps(delayPayByBond9);
            delayPayByProd10Map.setRowMaps(delayPayByProd10);
            delayPayByBond10Map.setRowMaps(delayPayByBond10);
            delayPayByProd11Map.setRowMaps(delayPayByProd11);
            delayPayByBond11Map.setRowMaps(delayPayByBond11);
            delayPayByProd12Map.setRowMaps(delayPayByProd12);
            delayPayByBond12Map.setRowMaps(delayPayByBond12);

            mapOutDs.put("ds_outDpProdAll", delayPayByProdAllMap);
            mapOutDs.put("ds_outDpBondAll", delayPayByBondAllMap);
            mapOutDs.put("ds_outDpProd1Y", delayPayByProdOneYearMap);
            mapOutDs.put("ds_outDpBond1Y", delayPayByBondOneYearMap);
/* 머지전 -2016-12-01
mapOutDs.put("ds_outDpProd2Y", delayPayByProdOneYearMap);
mapOutDs.put("ds_outDpBond2Y", delayPayByBondOneYearMap);
mapOutDs.put("ds_outDpProd3Y", delayPayByProdOneYearMap);
mapOutDs.put("ds_outDpBond3Y", delayPayByBondOneYearMap);
아래 4라인은 머지 후
*/
            mapOutDs.put("ds_outDpProd2Y", delayPayByProdTwoYearMap);
            mapOutDs.put("ds_outDpBond2Y", delayPayByBondTwoYearMap);
            mapOutDs.put("ds_outDpProd3Y", delayPayByProdThreeYearMap);
            mapOutDs.put("ds_outDpBond3Y", delayPayByBondThreeYearMap);
            mapOutDs.put("ds_outDpProd1", delayPayByProd1Map);
            mapOutDs.put("ds_outDpBond1", delayPayByBond1Map);
            mapOutDs.put("ds_outDpProd2", delayPayByProd2Map);
            mapOutDs.put("ds_outDpBond2", delayPayByBond2Map);
            mapOutDs.put("ds_outDpProd3", delayPayByProd3Map);
            mapOutDs.put("ds_outDpBond3", delayPayByBond3Map);
            mapOutDs.put("ds_outDpProd4", delayPayByProd4Map);
            mapOutDs.put("ds_outDpBond4", delayPayByBond4Map);
            mapOutDs.put("ds_outDpProd5", delayPayByProd5Map);
            mapOutDs.put("ds_outDpBond5", delayPayByBond5Map);
            mapOutDs.put("ds_outDpProd6", delayPayByProd6Map);
            mapOutDs.put("ds_outDpBond6", delayPayByBond6Map);
            mapOutDs.put("ds_outDpProd7", delayPayByProd7Map);
            mapOutDs.put("ds_outDpBond7", delayPayByBond7Map);
            mapOutDs.put("ds_outDpProd8", delayPayByProd8Map);
            mapOutDs.put("ds_outDpBond8", delayPayByBond8Map);
            mapOutDs.put("ds_outDpProd9", delayPayByProd9Map);
            mapOutDs.put("ds_outDpBond9", delayPayByBond9Map);
            mapOutDs.put("ds_outDpProd10", delayPayByProd10Map);
            mapOutDs.put("ds_outDpBond10", delayPayByBond10Map);
            mapOutDs.put("ds_outDpProd11", delayPayByProd11Map);
            mapOutDs.put("ds_outDpBond11", delayPayByBond11Map);
            mapOutDs.put("ds_outDpProd12", delayPayByProd12Map);
            mapOutDs.put("ds_outDpBond12", delayPayByBond12Map);

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
     * 대명라이프웨이 실적 현황을 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/acrs-list")
    public ModelAndView getAcrsProdList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap listStatMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            String bas_dt = (String)mapInVar.get("bas_dt");

            int bas_yr = Integer.parseInt(bas_dt.substring(0, 4));  //기준년도
            int bas_mon = Integer.parseInt(bas_dt.substring(5, 7)); //기준월

            String stt_dt = "";
            String end_dt = "";

            GregorianCalendar cal = new GregorianCalendar();
            SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd");

            List<Map<String, Object>> tmpList = new ArrayList<Map<String,Object>>();
            List<Map<String, Object>> mTotalList = new ArrayList<Map<String,Object>>();


            // 월별
            for (int i = 0; i < bas_mon; i++) {
                cal.set(bas_yr, i, 1);
                hmParam.put("stt_dt", date.format(cal.getTime()));
                cal.set(bas_yr, i+1, 1);
                hmParam.put("end_dt", date.format(cal.getTime()));

                hmParam.put("mon_gbn", (i+1)+"월");
                tmpList = DlwCondService.getAcrsProd(hmParam);
                mTotalList.addAll(tmpList);
            }

            // 연간
            cal.set(bas_yr, 0, 1);
            hmParam.put("stt_dt", date.format(cal.getTime()));
            hmParam.put("end_dt", bas_dt);

            hmParam.put("mon_gbn", "year");
            tmpList = DlwCondService.getAcrsProd(hmParam);
            mTotalList.addAll(tmpList);

            //총누적
            cal.set(2011, 0, 1);
            hmParam.put("stt_dt", date.format(cal.getTime()));
            hmParam.put("end_dt", bas_dt);

            hmParam.put("mon_gbn", "total");
            tmpList = DlwCondService.getAcrsProd(hmParam);
            mTotalList.addAll(tmpList);

            listMap.setRowMaps(mTotalList);
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
     * 대명라이프웨이 실적 현황을 검색한다. By batch Data
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/acrs-list-batch")
    public ModelAndView getAcrsProdListByBatch(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap listStatMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            String bas_dt = (String)mapInVar.get("bas_dt");

            int bas_yr = Integer.parseInt(bas_dt.substring(0, 4));  //기준년도
            int bas_mon = Integer.parseInt(bas_dt.substring(5, 7)); //기준월

            String stt_dt = "";
            String end_dt = "";

            GregorianCalendar cal = new GregorianCalendar();
            SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd");

            List<Map<String, Object>> tmpList = new ArrayList<Map<String,Object>>();
            List<Map<String, Object>> mTotalList = new ArrayList<Map<String,Object>>();

            int lastDay = 0;
            String strmon = "";
            String strbasmon = "";
            int intmon = 0;
            int intbasmon = 0;
            // 월별
            for (int i = 0; i < bas_mon; i++) {
                cal.set(bas_yr, i, 1);
                hmParam.put("stt_dt", date.format(cal.getTime()));

                lastDay = cal.getActualMaximum(cal.DATE);
                cal.set(bas_yr, i, lastDay);

                intmon = String.valueOf("0" + (i + 1)).length();
                strmon = String.valueOf("0" + (i + 1)).substring((intmon -2), intmon);

                // 2017.10.27 데이터 불일치로 수정...
                //intbasmon = String.valueOf("0" + (i + 1)).length();
                //strbasmon = String.valueOf("0" + bas_mon).substring((intbasmon - 2), intbasmon);

                intbasmon = String.valueOf("0" + bas_mon).length();
                strbasmon = String.valueOf("0" + bas_mon).substring((intbasmon - 2), intbasmon);

                if (strbasmon.equals(strmon))  {
                    hmParam.put("end_dt", bas_dt);
                }else{
                    hmParam.put("end_dt", date.format(cal.getTime()));
                }
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>chk>>>>" + bas_dt);
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>chk>>>>" + strmon);
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>chk>>>>" + strbasmon);
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>stt>>>>" + hmParam.get("stt_dt"));
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>end>>>>" + hmParam.get("end_dt"));

                hmParam.put("mon_gbn", (i+1)+"월");
                tmpList = DlwCondService.getAcrsProdByBatch(hmParam);
                mTotalList.addAll(tmpList);
            }

            // 연간
            cal.set(bas_yr, 0, 1);
            hmParam.put("stt_dt", date.format(cal.getTime()));
            hmParam.put("end_dt", bas_dt);

            hmParam.put("mon_gbn", "year");
            tmpList = DlwCondService.getAcrsProdByBatch(hmParam);
            mTotalList.addAll(tmpList);

            //총누적
            cal.set(2011, 0, 1);
            hmParam.put("stt_dt", date.format(cal.getTime()));
            hmParam.put("end_dt", bas_dt);

            hmParam.put("mon_gbn", "total");
            tmpList = DlwCondService.getAcrsProdByBatch(hmParam);
            mTotalList.addAll(tmpList);

            listMap.setRowMaps(mTotalList);
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

    private String substring(String string, int i) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 대명라이프웨이 종합 입금 현황을 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/bas-month")
    public ModelAndView getBasMonth(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap listStatMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            List<Map<String, Object>> mList = DlwCondService.getBasMonth(hmParam);
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
     * 대명라이프웨이 입급현황(new)을 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/pay-month")
    public ModelAndView getPayMonth(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        DataSetMap listMap2 = new DataSetMap();
        DataSetMap listStatMap = new DataSetMap();
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
            if (listInDs.size() > 0) {
                hmParam = listInDs.get(0);

                String excel_fg = (String)mapInVar.get("excel_fg");
                // 페이징 정보
                DataSetMap listInGds = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                if (listInGds != null && listInGds.size() > 0) {
                    Map pMap = listInGds.get(0);
                    if (!"Y".equals(excel_fg)) {
                        hmParam.put("startLine", pMap.get("startNum"));
                        hmParam.put("endLine", pMap.get("endNum"));
                    }
                }

                //세션
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("paramEmpleNo", hmParam.get("rgsr_id"));
                hmParam.put("paramAs", "EMP");
                String dataAthrQury = dataAthrQuryService.getDataAthrQury(hmParam);
                hmParam.put("dataAthrQury", dataAthrQury);

                String mem_cl = (String)hmParam.get("mem_cl");

                int nTotal = 0;

                if (!"Y".equals(excel_fg)) {
                    List<Map<String, Object>> mList = new ArrayList<Map<String,Object>>();
                    if ("mem".equalsIgnoreCase(mem_cl)) {
                        nTotal = DlwCondService.getPayMonthByMemCount(hmParam);
                        mList = DlwCondService.getPayMonthByMemList(hmParam);
                    } else {
                        nTotal = DlwCondService.getPayMonthCount(hmParam);
                        mList = DlwCondService.getPayMonthList(hmParam);
                    }
                    listMap.setRowMaps(mList);
                    mapOutDs.put("ds_output", listMap);
                }
                listMap2.setRowMaps(hmParam);
                mapOutDs.put("ds_output_excel", listMap2);
                mapOutVar.put("tc_condPay", nTotal);
            }

            //2018.03.20 접속 로그//////////////////////////////////////////////////////////////////////////////
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
     * 일일청구기준데이터 배치로 돌리기 때문에 이부분은 테스트가 끝난뒤 삭제될 것임
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/batchMbidCsvFileMake")
    public ModelAndView batchMbidCsvFileMake(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            int nResult = DlwCondService.insertMbidCsvFileMake();
            mapOutVar.put("nBatchSuccessCode", nResult);
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
     * 수당 수수료 마감
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/insertSetClose")
    public ModelAndView insertSetClose(XPlatformMapDTO xpDto, Model model) throws Exception {
    	ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        DataSetMap listMap = new DataSetMap();
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg = "OK";

        int nResult = 0;
        String nTabIndex = "";

        try
        {
            Map<String, Object> mapInVar = xpDto.getInVariableMap();
            Map<String, DataSetMap> mapInDs = xpDto.getInDataSetMap();
            Map<String, Object> mapOutVar = xpDto.getOutVariableMap();
            Map<String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap) mapInDs.get("ds_input");
            
            if(listInDs.size() > 0)
            {
            	SimpleDateFormat sdf = new SimpleDateFormat ("yyyyMM");
            	String toYYYYMM = sdf.format(System.currentTimeMillis());
            	
            	Calendar cal = new GregorianCalendar(Locale.KOREA);
            	
            	cal.set(Calendar.YEAR, Integer.parseInt(toYYYYMM.substring(0, 4)));
            	cal.set(Calendar.MONTH, Integer.parseInt(toYYYYMM.substring(4, 6)) - 1);
            	cal.add(Calendar.MONTH, -1);
            	
            	String sPreMonth = sdf.format(cal.getTime());
            			
            	hmParam = listInDs.get(0);

                //세션
                ParamUtils.addSaveParam(hmParam);
                hmParam.put("alow_dt", sPreMonth);
                hmParam.put("p_emple_no", hmParam.get("rgsr_id"));
                hmParam.put("alow_calc_gbn", "C");
                
                System.out.println("이값은 뭐지?" + hmParam);
                
                List<Map<String, Object>> mList = DlwCondService.getAlowCalcMgmtExtList(hmParam);
                
                if(mList.size() <= 0)
                {
                	// 수수료 마감 프로세스
                    DlwCondService.insertCloseDataComm(hmParam);
                    
                    // 수당 마감 프로세스, 이월환수 프로세스, 당월 전체 이월환수 데이터 이월환수 TABLE인서트 프로세스
                    DlwCondService.insertCloseDataAlow(hmParam);
                    
                    // 산출정보 저장 (중복산출을 막기 위함이다.)
                    DlwCondService.insertAlowCalcMgmtExtList(hmParam);
                }
                else
                {
                	szErrorCode = "-1";
                    szErrorMsg = "이미 산출된 수당/수수료(마감)가 존재합니다. 중복산출은 불가능 합니다.";
                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);
                    return modelAndView;
                }
            }

            mapOutVar.put("intCloseCnt", nResult);
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
     * 최초 수수료 / 수당 산출
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/insertAlowBasicInfo")
    public ModelAndView insertAlowBasicInfo(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            
            ParamUtils.addSaveParam(hmParam);
            hmParam.put("alow_calc_gbn", "A");
            
            System.out.println("산출 파라미터 확인 : " + hmParam.toString());
            List<Map<String, Object>> mList = DlwCondService.getAlowCalcMgmtExtList(hmParam);
            
            if(mList.size() <= 0)
            {
            	int nResult = DlwCondService.insertAlowBasicInfo();
            }
            else
            {
            	szErrorCode = "-1";
                szErrorMsg = "이미 산출된 수당/수수료(산출)가 존재합니다. 중복산출은 불가능 합니다.";
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                modelAndView.addObject(XPlatformConstant.ERROR_MSG, szErrorMsg);
                return modelAndView;
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