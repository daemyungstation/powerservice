/*
 * (@)# RlvtInttController.java
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

package powerservice.business.kms.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.kms.service.RlvtInttService;
import powerservice.core.util.ParamUtils;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

/**
 * 유관기관 정보를 관리 한다.
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 차건호
 * @version 1.0
 * @date 2015/04/16
 * @프로그램ID RlvtIntt
 */

@Controller
@RequestMapping(value = "/knowledge/rlvt-intt")
public class RlvtInttController {

    @Resource
    private RlvtInttService rlvtInttService;

    /**
     * 유관기관 정보를 검색한다.
     *
     * @param pmParam Map<String, String>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public ModelAndView getRlvtInttList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

          //조회조건
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

            int nTotal = rlvtInttService.getRlvtInttCount(hmParam);
            mapOutVar.put("total_count", nTotal);

            List<Map<String, Object>> mlist = rlvtInttService.getRlvtInttList(hmParam);
            listMap.setRowMaps(mlist);
            mapOutDs.put("ds_output", listMap);

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
     * 유관기관 순서 최대값을 검색한다.
     *
     * @return ActionResult
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/max-order")
    @ResponseBody
    public ModelAndView getRlvtInttMaxOrder(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";
        try {
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();

            Map<String, Object> mParam = ParamUtils.getCenterParam();

            int order = rlvtInttService.getRlvtInttMaxOrder(mParam);
            mapOutVar.put("maxOrder", order);

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
     * 유관기관 순서 중복 건수를 검색한다.
     *
     * @param pmParam Map<String, String>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/duplicate-count")
    @ResponseBody
    public ModelAndView getRlvtInttDupCout(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

          //조회조건
            DataSetMap srchInDs = (DataSetMap)mapInDs.get("ds_input");
            if (srchInDs != null && srchInDs.size() > 0) {
                hmParam = srchInDs.get(0);
            }
            ParamUtils.addCenterParam(hmParam);

            int nTotal = rlvtInttService.getRlvtInttDupCout(hmParam);
            mapOutVar.put("dupCount", nTotal);

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
     * 유관기관 정보를 등록/수정한다.
     *
     * @param pmParam Map<String, String>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public ModelAndView saveRlvtIntt(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            Boolean dupFlag =  Boolean.valueOf((String)mapInVar.get("dupFlag")).booleanValue();
            Map hmParam = listInDs.get(0);
            hmParam.put("dup_fg",dupFlag);

            String sRlvtInttId = StringUtils.defaultString((String) hmParam.get("rlvt_intt_id"));

            ParamUtils.addSaveParam(hmParam);
            ParamUtils.addCenterParam(hmParam);

            if ("".equals(sRlvtInttId)) {
                sRlvtInttId = rlvtInttService.insertRlvtIntt(hmParam);
            } else{
                rlvtInttService.updateRlvtIntt(hmParam);
            }

            Map<String, Object> mData = rlvtInttService.getRlvtIntt(sRlvtInttId);

            DataSetMap dsList = new DataSetMap();

            dsList.setRowMaps(mData);
            mapOutDs.put("ds_output", dsList);
            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,   xpDto.getOutDataSetMap());

        }catch(Exception e){
            e.printStackTrace();
            szErrorCode ="-1";
            szErrorMsg = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);
        return modelAndView;
    }

    /**
     * 유관기관 정보를 삭제한다.
     *
     * @param pmParam Map<String, String>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public ModelAndView deleteRlvtIntt(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

            hmParam = listInDs.get(0);
            rlvtInttService.deleteRlvtIntt(hmParam);
        }catch (Exception e) {
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);
        return modelAndView;
    }

}