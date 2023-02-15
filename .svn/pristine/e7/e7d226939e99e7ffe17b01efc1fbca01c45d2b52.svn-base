/*
 * (@)# CoBaVlController.java
 *
 * @author 김현경
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

import java.util.ArrayList;
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
import powerservice.business.bean.UserSession;
import powerservice.business.exa.service.EmshService;
import powerservice.business.exa.service.ExamPrbmItemService;
import powerservice.business.exa.service.ExamPrbmService;
import powerservice.core.bean.ActionList;
import powerservice.core.bean.ActionResult;
import powerservice.core.bean.ActionResultType;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;

/**
 * 시험지를 관리한다.
 * Copyright (c) 2014 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2014/04/01
 * @프로그램ID Emsh
 */

@Controller
@RequestMapping(value = "/exam/emsh")
public class EmshController {

    @Resource
    private EmshService emshService;

    @Resource
    private ExamPrbmService examPrbmService;

    @Resource
    private ExamPrbmItemService examPrbmItemService;

    /**
     * 시험지 정보(목록)를 검색한다.
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public ModelAndView getEmshList(XPlatformMapDTO xpDto, Model model) throws Exception {
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
                Map pMap = listInDs.get(0);
                hmParam.put("startLine", pMap.get("startNum"));
                hmParam.put("endLine", pMap.get("endNum"));
            }

            UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
            hmParam.put("bzpt_div", oLoginUser.getBzptdiv());//업체구분

            int nTotal = emshService.getEmshCount(hmParam);
            mapOutVar.put("tc_cust_memo", nTotal);

            List<Map<String, Object>> mList = emshService.getEmshList(hmParam);

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
    public ModelAndView getEmshList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        ParamUtils.addPagingParam(pmParam);
        ParamUtils.addCenterParam(pmParam);

        int nTotal = emshService.getEmshCount(pmParam);
        List<Map<String, Object>> mdataList = emshService.getEmshList(pmParam);

        oData.setTotal(nTotal);
        oData.setList(mdataList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }
    */
    /**
     * 시험지 정보를 등록/수정한다.(ajax)
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    public ModelAndView saveEmsh(XPlatformMapDTO xpDto, Model model) throws Exception {
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
                String sEmshId = StringUtils.defaultString((String) hmParam.get("emsh_id"));
                ParamUtils.addSaveParam(hmParam);

                UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
                hmParam.put("bzpt_div", oLoginUser.getBzptdiv());//업체구분

                if ("".equals(sEmshId)) {
                    sEmshId = emshService.insertEmsh(hmParam);
                } else {
                    emshService.updateEmsh(hmParam);
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
    /*
    @RequestMapping(value = "/save")
    public ModelAndView saveEmsh(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();

        String sEmshId = StringUtils.defaultString((String) pmParam.get("emsh_id"));
        ParamUtils.addSaveParam(pmParam);

        if ("".equals(sEmshId)) {
            sEmshId = emshService.insertEmsh(pmParam);
        } else {
            emshService.updateEmsh(pmParam);
        }
        oResult.setData(emshService.getEmsh(sEmshId));

        modelAndView.addObject(oResult);
        return modelAndView;
    }
    */
    /**
     * 시험지 정보를 삭제한다.
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    public ModelAndView deleteEmsh(XPlatformMapDTO xpDto, Model model) throws Exception {
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
                emshService.deleteEmsh(hmParam);
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
    public ModelAndView deleteEmsh(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();

        ParamUtils.addCenterParam(pmParam);

        emshService.deleteEmsh(pmParam);

        modelAndView.addObject(oResult);
        return modelAndView;
    }
    */
    /**
     * 시험지 정보를 복사한다.
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws Exception
     */
    @RequestMapping(value = "/copy")
    public ModelAndView copyEmsh(XPlatformMapDTO xpDto, Model model) throws Exception {
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
                emshService.copyEmsh(hmParam);
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
    @RequestMapping(value = "/copy")
    public ModelAndView copyEmsh(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();

        ParamUtils.addSaveParam(pmParam);

        emshService.copyEmsh(pmParam);

        modelAndView.addObject(oResult);
        return modelAndView;
    }
    */
    /**
     * 시험지 미리보기 정보를 검색한다.
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws Exception
     */
    @RequestMapping(value = "/preview")
    public ModelAndView getEmshPreview(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");
        Map<String, Object> hmParam = new HashMap<String, Object>();
        DataSetMap listMap = new DataSetMap();
        DataSetMap listMap2 = new DataSetMap();

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

            ParamUtils.addPagingParam(hmParam);
            ParamUtils.addCenterParam(hmParam);

            // 시험지 조회
            String sEmshId = StringUtils.defaultString((String) hmParam.get("emsh_id"));
            if ("".equals(sEmshId)) {
                szErrorCode = "-1";
                szErrorMsg  = "시험지아이디가 없습니다.";
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

                return modelAndView;
            }
            Map<String, Object> mEmsh = emshService.getEmsh(sEmshId);
            if (mEmsh == null) {
                szErrorCode = "-1";
                szErrorMsg  = "시험지 정보가 없습니다.";
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

                return modelAndView;
            }

            // 시험문제 정보 조회
            hmParam.put("emsh_id", sEmshId);
            hmParam.put("orderBy", "EXPE_SQNC");
            hmParam.put("orderDirection", "ASC");
            List<Map<String, Object>> mdataItemList = examPrbmService.getExamPrbmList(hmParam);
            if (mdataItemList == null) {
                szErrorCode = "-1";
                szErrorMsg  = "시험문제 정보가 없습니다.";
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

                return modelAndView;
            }

            List<Map<String, Object>> examPrbmItemList = null;
            List<Map<String, Object>> item = new ArrayList<Map<String,Object>>();
            // 시험문제항목 정보 조회
            for (int i = 0; i < mdataItemList.size(); i++) {
                Map<String, Object> examquestion = mdataItemList.get(i);

                hmParam.put("exam_prbm_id", examquestion.get("exam_prbm_id"));
                hmParam.put("exam_prbm_typ_cd", examquestion.get("exam_prbm_typ_cd"));
                examPrbmItemList = examPrbmItemService.getExamPrbmItemAnsrList(hmParam);
                item.addAll(examPrbmItemList);



            }

            listMap.setRowMaps(mdataItemList);
            listMap2.setRowMaps(item);
            mapOutDs.put("ds_output", listMap);
            mapOutDs.put("ds_output2", listMap2);

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
    /*@RequestMapping(value = "/preview")
    public ModelAndView getEmshPreview(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            ParamUtils.addPagingParam(hmParam);
            ParamUtils.addCenterParam(hmParam);

            String exam_prbm_ids = StringUtils.defaultString((String) hmParam.get("exam_prbm_ids"));
            if (!"".equals(exam_prbm_ids)) {
                hmParam.put("exam_prbm_ids", exam_prbm_ids.split(","));
            }

            // 시험문제항목 정보 조회
            List<Map<String, Object>>  mdataItemList = examPrbmItemService.getExamPrbmItemAnsrList(hmParam);


            listMap.setRowMaps(mdataItemList);
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
    */
    /*
    @RequestMapping(value = "/preview")
    public ModelAndView getEmshPreview(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();
        Map<String, Object> oData = new HashMap<String, Object>();

        ParamUtils.addPagingParam(pmParam);
        ParamUtils.addCenterParam(pmParam);

        // 시험지 조회
        String sEmshId = StringUtils.defaultString((String) pmParam.get("emsh_id"));
        if ("".equals(sEmshId)) {
            oResult.setResult(ActionResultType.ERROR);
            oResult.setErrMsg("시험지아이디가 없습니다.");
            modelAndView.addObject(oResult);
            return modelAndView;
        }
        Map<String, Object> mEmsh = emshService.getEmsh(sEmshId);
        if (mEmsh == null) {
            oResult.setResult(ActionResultType.ERROR);
            oResult.setErrMsg("시험지 정보가 없습니다.");
            modelAndView.addObject(oResult);
            return modelAndView;
        }

        // 시험문제 정보 조회
        pmParam.put("emsh_id", sEmshId);
        pmParam.put("orderBy", "EXPE_SQNC");
        pmParam.put("orderDirection", "ASC");
        List<Map<String, Object>> mdataItemList = examPrbmService.getExamPrbmList(pmParam);
        if (mdataItemList == null) {
            oResult.setResult(ActionResultType.ERROR);
            oResult.setErrMsg("시험문제 정보가 없습니다.");
            modelAndView.addObject(oResult);
            return modelAndView;
        }

        // 시험문제항목 정보 조회
        for (int i = 0; i < mdataItemList.size(); i++) {
            Map<String, Object> examquestion = mdataItemList.get(i);

            pmParam.put("exam_prbm_id", examquestion.get("exam_prbm_id"));
            pmParam.put("exam_prbm_typ_cd", examquestion.get("exam_prbm_typ_cd"));
            examquestion.put("exam_prbm_item_list", examPrbmItemService.getExamPrbmItemAnsrList(pmParam));

            mdataItemList.set(i, examquestion);
        }

        oData.put("emsh", mEmsh);
        oData.put("exam_prbm_list", mdataItemList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }
    */
}