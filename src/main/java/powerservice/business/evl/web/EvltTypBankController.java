/*
 * (@)# EvltTypController.java
 *
 * @author 배윤정
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

package powerservice.business.evl.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.bean.UserSession;
import powerservice.business.evl.service.EvltItemBankService;
import powerservice.business.evl.service.EvltTypBankService;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

/**
 * 평가유형을 관리를 한다.
 * Copyright (c) 2014 Company Inwoo Tech Inc.
 *
 * @author    최현식
 * @version 1.0
 * @date 2014/04/01
 * @프로그램ID EvltTyp
 */
@Controller
@RequestMapping(value = "/evlt-typ-bank")
public class EvltTypBankController {

    @Resource
    private EvltTypBankService evltTypBankService;

    @Resource
    private EvltItemBankService evltItemBankService;

    /**
     * 평가유형 트리 정보를 검색한다.
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/tree")
    public ModelAndView getEvltTypTreeView(XPlatformMapDTO xpDto, Model model, String psPathTyp) throws Exception {
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
            // 업체구분
            UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
            hmParam.put("bzpt_div", oLoginUser.getBzptdiv());

            ParamUtils.addCenterParam(hmParam);
            List<Map<String, Object>> mList  = evltTypBankService.getEvltTypTree(hmParam);

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
     * 평가유형 정보(목록)를 검색한다.
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/list")
    public ModelAndView getEvltTypList(XPlatformMapDTO xpDto, Model model, String psPathTyp) throws Exception {
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
            if (listInDs != null && listInDs.size() > 0) {
                Map<String, Object> pMap = listInDs.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }
            ParamUtils.addCenterParam(hmParam);

            // 업체구분
            UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
            hmParam.put("bzpt_div", oLoginUser.getBzptdiv());

            List<Map<String, Object>> mList = evltTypBankService.getEvltTypList(hmParam);

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
     * 평가유형 정보를 조회한다.(1건)
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/view")
    public ModelAndView view(XPlatformMapDTO xpDto, Model model, String psPathTyp) throws Exception {
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

            // 업체구분
            UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
            hmParam.put("bzpt_div", oLoginUser.getBzptdiv());

            ParamUtils.addCenterParam(hmParam);
            listMap.setRowMaps(evltTypBankService.getEvltTypView(hmParam));

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
     * 평가유형 정보를 등록/수정한다.(ajax)
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/save")
    public ModelAndView save(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try{
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

            if (listInDs != null && listInDs.size() > 0) {
                Map<String, Object> hmParam = (Map<String, Object>)listInDs.get(0);

                String sEvltTypId = StringUtils.defaultString((String) hmParam.get("evlt_typ_id"));
                ParamUtils.addSaveParam(hmParam);

                // 업체구분
                UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
                hmParam.put("bzpt_div", oLoginUser.getBzptdiv());

                if ("".equals(sEvltTypId)) {
                    sEvltTypId = evltTypBankService.insertEvltTyp(hmParam);
                } else {
                    evltTypBankService.updateEvltTyp(hmParam);
                }
            }

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
     * 평가유형 정보를 삭제한다.
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/delete")
    public ModelAndView delete(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try{
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

            if (listInDs != null && listInDs.size() > 0) {
                Map<String, Object> hmParam = (Map<String, Object>)listInDs.get(0);

                // 업체구분
                UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
                hmParam.put("bzpt_div", oLoginUser.getBzptdiv());
                ParamUtils.addCenterParam(hmParam);

                evltTypBankService.deleteEvltTyp(hmParam);
            }

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
    * 평가유형 DROPDOWNLIST.
    *
    * @param request HttpServletRequest
    * @param response HttpServletResponse
    * @throws Exception
    */
   @RequestMapping(value = "/drop-down-list")
   public ModelAndView getEvltTypForDropDownList(XPlatformMapDTO xpDto, Model model, String psPathTyp) throws Exception {
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
               hmParam = srchInDs.get(0);
           }

           // 업체구분
           UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
           hmParam.put("bzpt_div", oLoginUser.getBzptdiv());
           ParamUtils.addCenterParam(hmParam);

           List<Map<String, Object>> mList = evltTypBankService.getEvltTypDropdownList(hmParam);
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
}