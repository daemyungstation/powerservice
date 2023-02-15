/*
 * (@)# BkmkInfoController.java
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.bean.UserSession;
import powerservice.business.kms.service.BkmkInfoService;
import powerservice.core.bean.ActionList;
import powerservice.core.bean.ActionResult;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

/**
 * 즐겨찾기 정보 관리를 한다.
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 차건호
 * @version 1.0
 * @date 2015/04/16
 * @프로그램ID BkmkInfo
 */

@Controller
@RequestMapping(value = "/knowledge/bkmk-info")
public class BkmkInfoController {

    @Resource
    private BkmkInfoService bkmkInfoService;

    /**
     * 즐겨찾기 정보를 검색한다.(관리화면)
     *
     * @param pmParam Map<String, String>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public ModelAndView getBkmkInfoList(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map<String, Object> hmParam = new HashMap<String, Object>();
        DataSetMap listMap = new DataSetMap();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try {
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

            int nTotal = bkmkInfoService.getBkmkInfoCount(hmParam);
            mapOutVar.put("total_count", nTotal);

            ParamUtils.addCenterParam(hmParam);

            List<Map<String, Object>> mList = bkmkInfoService.getBkmkInfoList(hmParam);
            listMap.setRowMaps(mList);
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
     * 즐겨찾기 정보를 검색한다.(통합검색)
     *
     * @param pmParam Map<String, String>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list-user/{pageType}")
    @ResponseBody
    public ModelAndView getBkmkInfoListUser(@PathVariable("pageType") String pathType, @RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        String sBkmkDivCd = "";

        if ("useful".equals(pathType)) {			// 유용한 페이지
            sBkmkDivCd = "10";
        } else if ("related".equals(pathType)) {	// 관련 페이지
            sBkmkDivCd = "20";
        } else if ("indi".equals(pathType)) {	// 개인 페이지
            sBkmkDivCd = "30";
        }

        pmParam.put("bkmk_div_cd", sBkmkDivCd);
        pmParam.put("use_yn", "Y");
        pmParam.put("orderBy", "expe_sqnc");
        pmParam.put("orderDirection", "asc");

        ParamUtils.addCenterParam(pmParam);

        UserSession userSession = (UserSession) SessionUtils.getLoginUser();
        String sTeamCd = userSession.getTeamcd();
        String sUserId = userSession.getUserid();

        pmParam.put("team2_cd", sTeamCd);
        pmParam.put("user_id", sUserId);

        System.out.println("BKMKINFO PARAM :"+pmParam);

        List<Map<String, Object>> mList = bkmkInfoService.getBkmkInfoList(pmParam);

        oData.setTotal(mList.size());
        oData.setList(mList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 즐겨찾기 순서 최대값을 검색한다.
     *
     * @param pmParam Map<String, String>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/max-order")
    @ResponseBody
    public ModelAndView getBkmkInfoMaxOrd(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try{
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            ParamUtils.addCenterParam(hmParam);

            if(mapInVar.size() >0 ){
                hmParam.put("bkmk_div_cd",mapInVar.get("bkmk_div_cd"));
            }

            int order = bkmkInfoService.getBkmkInfoMaxOrd(hmParam);
            mapOutVar.put("maxOrder", order);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME,   xpDto.getOutDataSetMap());

        }catch (Exception e){
            e.printStackTrace();
            szErrorCode = "-1";
            szErrorMsg  = e.getMessage();
        }

        modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
        modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

        return modelAndView;
    }

    /**
     * 즐겨찾기 순서 중복 건수를 검색한다.
     *
     * @param pmParam Map<String, String>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/duplicate-count")
    @ResponseBody
    public ModelAndView getBkmkInfoDupCount(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            int nTotal = bkmkInfoService.getBkmkInfoDupCount(hmParam);
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
     * 즐겨찾기 정보를 등록/수정한다.(ajax)
     *
     * @param pmParam Map<String, String>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public ModelAndView saveBkmkInfo(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            String sBkmkId = StringUtils.defaultString((String) hmParam.get("bkmk_id"));

            //대명라이프웨이 수정
            ParamUtils.addSaveParam(hmParam);
            ParamUtils.addCenterParam(hmParam);

            if ("".equals(sBkmkId)) {
                sBkmkId = bkmkInfoService.insertBkmkInfo(hmParam);
            } else {
                bkmkInfoService.updateBkmkInfo(hmParam);
            }

            //대명라이프웨이 수정
            //Map<String, Object> mData = ParamUtils.getCenterParam();
            hmParam.put("bkmk_id", sBkmkId);
            Map<String, Object> mData = bkmkInfoService.getBkmkInfo(hmParam);

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
     * 즐겨찾기 정보를 등록한다.(통합검색 > 즐겨찾기 관리)
     *
     * @param pmParam List<Map<String, Object>>
     * @param response HttpServletResponse
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/save-tot-sch")
    @ResponseBody
    public ModelAndView saveBkmkInfoForTotSch(@RequestBody List<Map<String, Object>> pModels) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        if (pModels != null && pModels.size() > 0) {
            Map<String, Object> mParam = new HashMap<String, Object>();
            ParamUtils.addSaveParam(mParam);

            UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
            mParam.put("user_id", oLoginUser.getUserid());
            mParam.put("team2_cd", oLoginUser.getTeamcd());

            for (Map<String, Object> mModel : pModels) {
                String sBkmkId = StringUtils.defaultString((String) mModel.get("bkmk_id"));

                mParam.put("bkmk_nm", mModel.get("bkmk_nm"));
                mParam.put("bkmk_addr", mModel.get("bkmk_addr"));
                mParam.put("bkmk_div_cd", "30");
                mParam.put("use_yn", "Y");

                if ("".equals(sBkmkId)) {
                    bkmkInfoService.insertBkmkInfo(mParam);
                } else {
                    mParam.put("expe_sqnc", mModel.get("expe_sqnc"));
                    mParam.put("bkmk_id", mModel.get("bkmk_id"));

                    bkmkInfoService.updateBkmkInfo(mParam);
                }
            }
        }
        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 즐겨찾기 정보를 삭제한다.
     *
     * @param pmParam Map<String, String>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public ModelAndView deleteBkmkInfo(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map<String, Object> hmParam = new HashMap<String, Object>();

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";
        try{
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            String sSelectCheck = StringUtils.defaultString((String) mapInVar.get("selectcheck"));

            if(sSelectCheck != null && sSelectCheck != ""){
                // 여러건일때
                hmParam.put("selectcheck", sSelectCheck.split(","));
            } else{
                // 1건일때
                DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
                if (listInDs != null && listInDs.size() > 0) {
                    hmParam = listInDs.get(0);
                }
            }

            bkmkInfoService.deleteBkmkInfo(hmParam);

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