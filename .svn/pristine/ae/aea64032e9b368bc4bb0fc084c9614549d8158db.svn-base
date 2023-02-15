/*
 * (@)# ExamPrbmAnsrController.java
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
import powerservice.business.exa.service.ExamPrbmAnsrService;
import powerservice.core.bean.ActionList;
import powerservice.core.bean.ActionResult;
import powerservice.core.util.ParamUtils;

/**
 * 시험답안을 관리한다.
 * Copyright (c) 2014 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2014/04/01
 * @프로그램ID ExamAnswer
 */

@Controller
@RequestMapping(value = "/exam/exam-prbm-ansr")
public class ExamPrbmAnsrController {

    @Resource
    private ExamPrbmAnsrService examPrbmAnsrService;

    /**
     * 시험답안 정보(목록)를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public ModelAndView getExamPrbmAnsrList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        ParamUtils.addPagingParam(pmParam);
        ParamUtils.addCenterParam(pmParam);

        int nTotal = examPrbmAnsrService.getExamPrbmAnsrCount(pmParam);
        List<Map<String, Object>> mdataList = examPrbmAnsrService.getExamPrbmAnsrList(pmParam);

        oData.setTotal(nTotal);
        oData.setList(mdataList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 시험답안 정보를 등록한다.(ajax)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/insert")
    public ModelAndView insertExamPrbmAnsr(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();

        ParamUtils.addSaveParam(pmParam);

        examPrbmAnsrService.insertExamPrbmAnsr(pmParam);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 시험답안 리스트 정보를 등록한다.(ajax)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/insert-list")
    public ModelAndView insertExamPrbmAnsrList(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            mapOutVar.put("ansr_Id", hmParam.get("exam_prbm_id"));

            ParamUtils.addSaveParam(hmParam);

            examPrbmAnsrService.insertExamPrbmAnsrList(hmParam);

            modelAndView.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
            modelAndView.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	 xpDto.getOutDataSetMap());

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
    @RequestMapping(value = "/insert-list")
    public ModelAndView insertExamPrbmAnsrList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();

        ParamUtils.addSaveParam(pmParam);

        examPrbmAnsrService.insertExamPrbmAnsrList(pmParam);

        modelAndView.addObject(oResult);
        return modelAndView;
    }
    */
    /**
     * 시험답안 정보를 수정한다.(ajax)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/update")
    public ModelAndView updateExamPrbmAnsr(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();

        String sExamAnsrId = StringUtils.defaultString((String) pmParam.get("exam_ansr_id"));
        ParamUtils.addSaveParam(pmParam);

        if ("".equals(sExamAnsrId)) {
            sExamAnsrId = examPrbmAnsrService.insertExamPrbmAnsr(pmParam);
        } else {
            examPrbmAnsrService.updateExamPrbmAnsr(pmParam);
        }
        oResult.setData(examPrbmAnsrService.getExamPrbmAnsr(sExamAnsrId));

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 시험답안 정보를 삭제한다.
     *
     * @pmParam request HttpServletRequest
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    public ModelAndView deleteExamPrbmAnsr(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();

        ParamUtils.addCenterParam(pmParam);

        examPrbmAnsrService.deleteExamPrbmAnsr(pmParam);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 시험답안 채점 정보를 저장한다.(ajax)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/marking-save-list")
    public ModelAndView saveExamPrbmAnsrMarkingList(XPlatformMapDTO xpDto, Model model) throws Exception {
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
                // 시험답안 채점 저장
                for (int i=0; i<listInDs.size(); i++) {
                    hmParam = listInDs.get(i);
                    String sExamAnsrId = StringUtils.defaultString((String) hmParam.get("exam_ansr_id"));

                    ParamUtils.addSaveParam(hmParam);

                    if ("".equals(sExamAnsrId)) {
                        examPrbmAnsrService.insertExamPrbmAnsr(hmParam);
                    } else {
                        hmParam.remove("prbm_ansr_cntn");
                        examPrbmAnsrService.updateExamPrbmAnsr(hmParam);
                    }
                }
            }

            examPrbmAnsrService.saveExamPrbmAnsrMarkingList(hmParam);

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
    @RequestMapping(value = "/marking-save-list")
    public ModelAndView saveExamPrbmAnsrMarkingList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();

        ParamUtils.addSaveParam(pmParam);

        examPrbmAnsrService.saveExamPrbmAnsrMarkingList(pmParam);

        modelAndView.addObject(oResult);
        return modelAndView;
    }
    */
}