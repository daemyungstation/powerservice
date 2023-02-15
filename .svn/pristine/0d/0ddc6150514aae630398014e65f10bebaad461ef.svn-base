/*
 * (@)# UnpyMngeController.java
 *
 * @author 정용재
 * @version 1.0
 * @date 2016/07/14
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
package powerservice.business.cam.web;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.cam.service.UnpyMngeService;
import powerservice.common.util.CommonUtils;
import powerservice.core.util.ParamUtils;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

//2017.12.26 로그 추가
import powerservice.business.common.service.CommonService;
/**
 * 원장테이블
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정훈
 * @version 1.0
 * @date 2016/07/14
 * @프로그램ID UnpyMnge
 */

@Controller
@RequestMapping(value="/cmpg/unpy-mnge")
public class UnpyMngeController {

    @Resource
    private UnpyMngeService unpyMngeService;

    @Resource
    private CommonService commonService;

    /**
     * 미납정보 목록을 검색한다.
     *
     * 개발일시 : 2016.07.14
     * 개 발 자 : 정용재
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value="/list")
    @ResponseBody
    public ModelAndView getUnpyMngeList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map<String, Object> hmParam = new HashMap<String, Object>();
        DataSetMap listMap = new DataSetMap();

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
                hmParam = (Map<String, Object>)srchInDs.get(0);
            }

            // 페이징 정보
            DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
            if (!"Y".equals(mapInVar.get("excel")) && listInDs != null && listInDs.size() > 0) {
                Map<String, Object> pMap = (Map<String, Object>)listInDs.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }
            ParamUtils.addCenterParam(hmParam);

            int nTotal = unpyMngeService.getUnpyMngeCount(hmParam);;
            List<Map<String, Object>> mList = unpyMngeService.getUnpyMngeList(hmParam);

            listMap.setRowMaps(mList);
            mapOutVar.put("total_count", nTotal);
            mapOutDs.put("ds_output", listMap);

            //2017.12.26 접속 로그////////////////////////////////////////////////////////////////////////////////
            DataSetMap listLogIn = (DataSetMap)mapInDs.get("gds_logInput");

            if (listLogIn.size() > 0) {
                hmParam = listLogIn.get(0);
                commonService.insertLog(hmParam);
            }
            //////////////////////////////////////////////////////////////////////////////////////////////////////

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,   xpDto.getOutDataSetMap());

        }catch(Exception e){
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

    /**
     * 미납정보 통계 정보를 검색한다.
     *
     * 개발일시 : 2016.08.10
     * 개 발 자 : 정용재
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value="/report/{pagePath}")
    @ResponseBody
    public ModelAndView getUnpyMngeReportWithProdGbnCd(XPlatformMapDTO xpDto, Model model, @PathVariable("pagePath") String psPagePath) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map<String, Object> hmParam = new HashMap<String, Object>();
        DataSetMap listMap = new DataSetMap();

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
                hmParam = (Map<String, Object>)srchInDs.get(0);
            }

            // 페이징 정보
            DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
            if (!"Y".equals(mapInVar.get("excel")) && listInDs != null && listInDs.size() > 0) {
                Map<String, Object> pMap = (Map<String, Object>)listInDs.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }
            ParamUtils.addCenterParam(hmParam);


            String sFunc = "";
            switch(psPagePath) {
            case "ichae-dt-cnt":
                sFunc = "getUnpyMngeReportWithIchaeDtCnt";
                break;

            case "ichae-dt-price":
                sFunc = "getUnpyMngeReportWithIchaeDtPrice";
                break;

            case "total":
                sFunc = "getUnpyMngeReportWithTotal";
                break;

            case "cnsr":
                sFunc = "getUnpyMngeReportWithCnsr";
                break;

            case "prod-gbn-cd-total":
                sFunc = "getUnpyMngeReportWithProdGbnCdTotal";
                break;

            case "prod-gbn-cd":
                sFunc = "getUnpyMngeReportWithProdGbnCd";
                break;

            case "pay-state":
                sFunc = "getUnpyMngeReportWithPayState";
                break;

            case "pay-state2":
                sFunc = "getUnpyMngeReportWithPayState2";
                break;

            case "section2":
                sFunc = "getUnpyMngeReportWithSection2";
                break;

            case "camp-type":
                sFunc = "getUnpyMngeReportWithCamptype";
                break;

            default :
                break;
            }

            if (!"".equals(sFunc)) {
                Method method = UnpyMngeService.class.getDeclaredMethod(sFunc, Map.class);
                List<Map<String, Object>> mList = (List<Map<String, Object>>) method.invoke(unpyMngeService, hmParam);
                listMap.setRowMaps(mList);
                mapOutDs.put("ds_output", listMap);

                modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
                modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,   xpDto.getOutDataSetMap());
            } else {
                szErrorCode = "-1";
                szErrorMsg = "해당 경로는 존재하지 않습니다.";
            }

        }catch(Exception e){
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

}
