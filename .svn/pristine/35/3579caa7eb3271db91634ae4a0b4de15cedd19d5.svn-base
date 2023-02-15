/*
 * (@)# DpmsReslController.java
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

package powerservice.business.cam.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.cam.service.DpmsReslService;
import powerservice.core.bean.ActionList;
import powerservice.core.bean.ActionResult;
import powerservice.core.util.ParamUtils;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

/**
 * 캠페인 결과를 관리 한다.
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정용재
 * @version 1.0
 * @date 2015/05/07
 * @프로그램ID DpmsResl
 */

@Controller
@RequestMapping(value="/cmpg/dpms-resl")
public class DpmsReslController {

    @Resource
    private DpmsReslService dpmsReslService;

    /**
     * 캠페인 결과 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/{pageType}")
    @ResponseBody
    public ModelAndView getCustomerCampaignList(@PathVariable("pageType") String pathType,  XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map<String, Object> hmParam = new HashMap<String, Object>();
        DataSetMap listMap = new DataSetMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try{
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            //조회조건
            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }
            if("N".equals((String)mapInVar.get("total"))){
                // 페이징 정보
                DataSetMap listInDs = (DataSetMap)mapInDs.get("gds_RequestCompVariable");
                if (listInDs != null && listInDs.size() > 0) {
                    Map pMap = listInDs.get(0);
                    hmParam.put("startLine", pMap.get("startNum"));
                    hmParam.put("endLine", pMap.get("endNum"));
                }
            }

            int nTotal = 0;
            List<Map<String, Object>> mDataList = null;

            if (pathType.equals("user")) {
                nTotal = dpmsReslService.getDpmsReslByUserCount(hmParam);
                mDataList = dpmsReslService.getDpmsReslByUserList(hmParam);
            } else if(pathType.equals("customer")) {
                nTotal = dpmsReslService.getDpmsReslByCustomerCount(hmParam);
                mDataList = dpmsReslService.getDpmsReslByCustomerList(hmParam);
            }

            if("N".equals((String)mapInVar.get("total"))){
                mapOutVar.put("total_count", nTotal);
            }

            listMap.setRowMaps(mDataList);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,   xpDto.getOutDataSetMap());

        } catch(Exception e){
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

    /**
     * 센터현황(캠페인)>상담실적TOP10을 조회한다.
     *
     * @pmParam pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/top10")
    public ModelAndView getDpmsReslTop10(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();
        ParamUtils.addCenterParam(pmParam);

        int nTotal = 10;
        List<Map<String, Object>> mDataList = dpmsReslService.getDpmsReslTop10(pmParam);

        oData.setTotal(nTotal);
        oData.setList(mDataList);

        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 센터현황(캠페인)>캠페인 처리현황을 조회한다.
     *
     * @param pmParam 검색 조건
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/dsps/{pageType}")
    public ModelAndView getDpmsReslDsps(@PathVariable("pageType") String pageType, @RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();
        ParamUtils.addCenterParam(pmParam);

        Map<String, Object> oData = null;

        pmParam.put("pageType",pageType);
        oData = dpmsReslService.getDpmsReslDsps(pmParam);

        if (pageType.equals("tdy")) {
            oData.put("cnsr_cscnt", dpmsReslService.getTdyDpmsCnsrCount(pmParam));
        }

        oResult.setData(oData);
        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 센터현황(캠페인)>상담실적TOP10을 조회한다.
     *
     * @pmParam pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/dcl-dsps")
    public ModelAndView getDpmsReslDclDsps(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ParamUtils.addCenterParam(pmParam);

        oResult.setData(dpmsReslService.getDpmsReslDclDsps(pmParam));

        modelAndView.addObject(oResult);
        return modelAndView;
    }
}