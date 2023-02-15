/*
 * (@)# examPrbmItemController.java
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/15
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

package powerservice.business.exa.web;

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

import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;
import powerservice.business.exa.service.ExamPrbmItemService;
import powerservice.business.exa.service.ExamPrbmService;
import powerservice.core.bean.ActionList;
import powerservice.core.bean.ActionResult;
import powerservice.core.bean.ActionResultType;
import powerservice.core.util.ParamUtils;

/**
 * 시험지를 관리한다.
 * Copyright (c) 2014 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2014/04/01
 * @프로그램ID examPrbmItem
 */

@Controller
@RequestMapping(value = "/exam/exam-prbm-item")
public class ExamPrbmItemController {

    @Resource
    private ExamPrbmItemService examPrbmItemService;

    /**
     * 시험문제항목 정보(목록)를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public ModelAndView getExamPrbmItemList(XPlatformMapDTO xpDto, Model model, String psPathTyp) throws Exception {
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

            List<Map<String, Object>> mList = examPrbmItemService.getExamPrbmItemList(hmParam);

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
    /*
    @RequestMapping(value = "/list")
    public ModelAndView getExamPrbmItemList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        ParamUtils.addPagingParam(pmParam);
        ParamUtils.addCenterParam(pmParam);

        int nTotal = examPrbmItemService.getExamPrbmItemCount(pmParam);
        List<Map<String, Object>> mdataList = examPrbmItemService.getExamPrbmItemList(pmParam);

        oData.setTotal(nTotal);
        oData.setList(mdataList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }
    */
    /**
     * 시험문제항목 정보를 등록/수정한다.(ajax)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    public ModelAndView saveExamPrbmItem(@RequestBody List<Map<String, Object>> pmdataModels) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();

        if (pmdataModels != null && pmdataModels.size() > 0) {
            Map<String, Object> mParam = new HashMap<String, Object>();
            ParamUtils.addSaveParam(mParam);

            examPrbmItemService.saveExamPrbmItem(pmdataModels, mParam);
        }
        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 시험문제항목 정보를 삭제한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    public ModelAndView deleteExamPrbmItem(XPlatformMapDTO xpDto, Model model) throws Exception {
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
                Map hmParam = listInDs.get(0);
                ParamUtils.addSaveParam(hmParam);
                examPrbmItemService.deleteExamPrbmItem(hmParam);
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
    /*
    @RequestMapping(value = "/delete")
    public ModelAndView deleteExamPrbmItem(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();

        ParamUtils.addCenterParam(pmParam);

        String sExamPrbmItemId = StringUtils.defaultString((String) pmParam.get("exam_prbm_item_id"));
        if (!"".equals(sExamPrbmItemId)) {
            examPrbmItemService.deleteExamPrbmItem(pmParam);
        } else {
            oResult.setResult(ActionResultType.ERROR);
            oResult.setErrMsg("시험문제항목아이디가 없습니다.");
        }

        modelAndView.addObject(oResult);
        return modelAndView;
    }
    */
    /**
     * 시험문제항목 순서를 변경한다.
     *
     * @param pmParam List<Map<String, Object>>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/update-expe-sqnc")
    public ModelAndView updateExamPrbmItemExpeSqnc(@RequestBody List<Map<String, Object>> pmModelList) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();

        if (pmModelList != null && pmModelList.size() > 0) {
            Map<String, Object> mParam = new HashMap<String, Object>();
            ParamUtils.addSaveParam(mParam);

            examPrbmItemService.updateExamPrbmItemExpeSqnc(pmModelList, mParam);
        }

        modelAndView.addObject(oResult);
        return modelAndView;
    }

}