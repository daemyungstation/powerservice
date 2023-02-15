/*
 * (@)# EvstItemDtlController.java
 *
 * @author 최현식
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.bean.UserSession;
import powerservice.business.evl.service.EvstItemDtlService;
import powerservice.core.bean.ActionResult;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

/**
 * 평가항목을 관리를 한다.
 * Copyright (c) 2014 Company Inwoo Tech Inc.
 *
 * @author    최현식
 * @version 1.0
 * @date 2014/04/01
 * @프로그램ID EvstItemDtl
 */
@Controller
@RequestMapping(value = "/evst-item-dtl")
public class EvstItemDtlController {

    @Resource
    private EvstItemDtlService evstItemDtlService;

    /**
     * 평가항목 정보(목록)를 검색한다.
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public ModelAndView getEvstItemDtlList(XPlatformMapDTO xpDto, Model model, String psPathTyp) throws Exception {
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

            List<Map<String, Object>> mList = evstItemDtlService.getEvstItemDtlList(hmParam);
            listMap.setRowMaps(mList);
            mapOutDs.put("ds_output", listMap);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, xpDto.getOutDataSetMap());

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
     * 평가항목 정보를 등록/수정한다.(ajax)
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws Exception
     */
    @RequestMapping(value = "/itm-save")
    public ModelAndView qaSheetItmSave(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();

        ParamUtils.addSaveParam(pmParam);
        evstItemDtlService.insertEvstItemDtl(pmParam);
        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 평가항목 정보를 등록/수정한다.(ajax)
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws Exception
     */
    @RequestMapping(value = "/type-save")
    public ModelAndView qasheetTypeSave(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();

        String sSelectCheck = StringUtils.defaultString((String) pmParam.get("selectcheck1"));
        pmParam.put("selectcheck", sSelectCheck.split(","));
        ParamUtils.addSaveParam(pmParam);

        evstItemDtlService.insertEvltTyp(pmParam);
        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 평가항목 정보를 삭제한다.(평가유형 삭제)
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    public ModelAndView deleteEvstItemDtl(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

            if (listInDs != null && listInDs.size() > 0) {
                hmParam = listInDs.get(0);
            }

            String sSelectCheck = StringUtils.defaultString((String) hmParam.get("selectcheck"));
            hmParam.put("selectcheck", sSelectCheck.split(","));
            ParamUtils.addSaveParam(hmParam);

            evstItemDtlService.deleteEvstItemDtl(hmParam);

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
     * 평가항목 정보를 제외한다.(평가유형/항목 추가/제외 팝업)
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws Exception
     */
    @RequestMapping(value = "/itm-delete")
    public ModelAndView delete(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();

        String sSelectCheck = StringUtils.defaultString((String) pmParam.get("evlt_item_id"));
        pmParam.put("selectcheck", sSelectCheck.split(","));
        ParamUtils.addSaveParam(pmParam);

        evstItemDtlService.deleteEvstItemDtl(pmParam);
        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 평가지 항목정보를 조회한다.
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws Exception
     */
    @RequestMapping(value = "/preview")
    public ModelAndView previewEvstDtl(XPlatformMapDTO xpDto, Model model, String psPathTyp) throws Exception {
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

            ParamUtils.addCenterParam(hmParam);

            List<Map<String, Object>> mList = evstItemDtlService.getEvstItemDtl(hmParam);

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
     * 평가유형&항목 정보를 등록/수정한다.(ajax)
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    public ModelAndView insertEvstTypItem(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");

            if (listInDs != null && listInDs.size() > 0) {
                hmParam = listInDs.get(0);
            }
            String sSelectCheck = StringUtils.defaultString((String) hmParam.get("selectcheck"));
            hmParam.put("selectcheck", sSelectCheck.split(","));
            ParamUtils.addSaveParam(hmParam);

            // 업체구분
            UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
            hmParam.put("bzpt_div", oLoginUser.getBzptdiv());

            evstItemDtlService.insertEvstTypItem(hmParam);

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
     * 할당된평가항목 순서를 변경한다.
     *
     * @param pmParam List<Map<String, Object>>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/item-sort-order")
    public ModelAndView updateItemSortOrder(@RequestBody List<Map<String, Object>> pmModelList) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        if (pmModelList != null && pmModelList.size() > 0) {
            Map<String, Object> mParam = new HashMap<String, Object>();
            ParamUtils.addSaveParam(mParam);

            evstItemDtlService.updateItemSortOrder(pmModelList, mParam);
        }

        modelAndView.addObject(oResult);
        return modelAndView;
    }


}