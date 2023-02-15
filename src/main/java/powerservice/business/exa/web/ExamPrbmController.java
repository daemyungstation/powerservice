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
@RequestMapping(value = "/exam/exam-prbm")
public class ExamPrbmController {

    @Resource
    private ExamPrbmService examPrbmService;

    @Resource
    private ExamPrbmItemService examPrbmItemService;

    /**
     * 시험문제 정보(목록)를 검색한다.
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public ModelAndView getExamPrbmList(XPlatformMapDTO xpDto, Model model, String psPathTyp) throws Exception {
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
            ParamUtils.addSaveParam(hmParam);
            int nTotal = examPrbmService.getExamPrbmCount(hmParam);
            mapOutVar.put("tc_cust_memo", nTotal);

            List<Map<String, Object>> mList = examPrbmService.getExamPrbmList(hmParam);
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
    public ModelAndView getExamPrbmList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        ParamUtils.addPagingParam(pmParam);
        ParamUtils.addCenterParam(pmParam);

        int nTotal = examPrbmService.getExamPrbmCount(pmParam);
        List<Map<String, Object>> mdataList = examPrbmService.getExamPrbmList(pmParam);

        oData.setTotal(nTotal);
        oData.setList(mdataList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }
    */
    /**
     * 시험문제 정보를 등록/수정한다.(ajax)
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    public ModelAndView saveExamPrbm(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";

        try{
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            examPrbmService.saveExamPrbm(mapInDs);


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
    public ModelAndView saveExamPrbm(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();

        ParamUtils.addSaveParam(pmParam);

        String sExamPrbmId = examPrbmService.saveExamPrbm(pmParam);

        oResult.setData(examPrbmService.getExamPrbm(sExamPrbmId));

        modelAndView.addObject(oResult);
        return modelAndView;
    }
    */
    /**
     * 시험문제 정보를 삭제한다.
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    public ModelAndView deleteExamPrbm(XPlatformMapDTO xpDto, Model model) throws Exception {
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
                examPrbmService.deleteExamPrbm(hmParam);
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
    public ModelAndView deleteExamPrbm(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();

        ParamUtils.addCenterParam(pmParam);

        String sExamPrbmId = StringUtils.defaultString((String) pmParam.get("exam_prbm_id"));
        if (!"".equals(sExamPrbmId)) {
            examPrbmService.deleteExamPrbm(pmParam);
        } else {
            oResult.setResult(ActionResultType.ERROR);
            oResult.setErrMsg("시험문제아이디가 없습니다.");
        }

        modelAndView.addObject(oResult);
        return modelAndView;
    }
    */
    /**
     * 시험문제 채점 정보(목록)를 검색한다.
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws Exception
     */
    @RequestMapping(value = "/marking-list")
    public ModelAndView getExamPrbmMarkingList(XPlatformMapDTO xpDto, Model model, String psPathTyp) throws Exception {
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

            List<Map<String, Object>> mList = examPrbmService.getExamPrbmMarkingList(hmParam);

         // 시험문제 정답 추가
            if (mList != null) {
                hmParam.put("orderBy", "EXPE_SQNC");
                hmParam.put("orderDirection", "ASC");
                Map<String, Object> mExamPrbm = null;
                List<Map<String, Object>> mdataExamPrbmItemLst = null;
                Map<String, Object> mExamPrbmItem = null;
                for (int i = 0; i < mList.size(); i++) {
                    mExamPrbm = mList.get(i);

                    String sPrbmAnsrCntn = StringUtils.defaultString((String) mExamPrbm.get("prbm_ansr_cntn")); // 시험문제 작성 답안
                    String sAnsrAcqrScrVl = StringUtils.defaultString(mExamPrbm.get("ansr_acqr_scr_vl") + "");
                    String sExamPrbmDtscVl = StringUtils.defaultString(mExamPrbm.get("exam_prbm_dtsc_vl") + "");
                    String sExamPrbmTypCd = StringUtils.defaultString((String) mExamPrbm.get("exam_prbm_typ_cd"));

                    hmParam.put("exam_prbm_id", mExamPrbm.get("exam_prbm_id"));
                    mdataExamPrbmItemLst = examPrbmItemService.getExamPrbmItemList(hmParam);

                    if (mdataExamPrbmItemLst != null) {
                        String sCranNo = ""; // 시험문제 정답 번호
                        String sCranNoChk = "";
                        String sCranCntn = ""; // 시험문제 정답 내용
                        for (int j = 0; j < mdataExamPrbmItemLst.size(); j++) {
                            mExamPrbmItem = mdataExamPrbmItemLst.get(j);
                            // 시험문제 작성 답안
                            if (!"30".equals(mExamPrbm.get("exam_prbm_typ_cd"))) { // 객관식인 경우
                                sPrbmAnsrCntn = sPrbmAnsrCntn.replace((String) mExamPrbmItem.get("exam_prbm_item_id"), String.valueOf(j + 1));
                            }
                            // 정답인 경우 시험문제 정답 작성
                            if ("Y".equals(mExamPrbmItem.get("cran_yn"))) {
                                if ("30".equals(mExamPrbm.get("exam_prbm_typ_cd"))) { // 주관식인 경우
                                    sCranCntn = ((String)mExamPrbmItem.get("exam_prbm_item_cntn")).trim();
                                } else { // 객관식인 경우 문제 번호로 변경
                                    sCranNoChk += (!"".equals(sCranNo) ? "," : "") + (j + 1);
                                    sCranNo += (!"".equals(sCranNo) ? "\r\n" : "") + (j + 1);
                                    sCranCntn += (!"".equals(sCranCntn) ? "\r\n" : "") + mExamPrbmItem.get("exam_prbm_item_cntn");
                                }
                            }
                        }

                        if ("null".equals(sAnsrAcqrScrVl) || "".equals(sAnsrAcqrScrVl)) {
                            if ("30".equals(sExamPrbmTypCd) && sCranCntn.equals(sPrbmAnsrCntn.trim())) {
                                sAnsrAcqrScrVl = sExamPrbmDtscVl;
                            } else if (!"30".equals(sExamPrbmTypCd) && sCranNoChk.equals(sPrbmAnsrCntn)) {
                                sAnsrAcqrScrVl = sExamPrbmDtscVl;
                            } else {
                                sAnsrAcqrScrVl = "0";
                            }
                        }

                        mExamPrbm.put("prbm_ansr_cntn", sPrbmAnsrCntn);
                        mExamPrbm.put("cran_no", sCranNo);
                        mExamPrbm.put("cran_cntn", sCranCntn);
                        mExamPrbm.put("ansr_acqr_scr_vl", sAnsrAcqrScrVl);
                    }
                    mList.set(i, mExamPrbm);
                }
            }

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
    @RequestMapping(value = "/marking-list")
    public ModelAndView getExamPrbmMarkingList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        ParamUtils.addPagingParam(pmParam);
        ParamUtils.addCenterParam(pmParam);

        int nTotal = examPrbmService.getExamPrbmMarkingCount(pmParam);
        List<Map<String, Object>> mdataList = examPrbmService.getExamPrbmMarkingList(pmParam);
        // 시험문제 정답 추가
        if (mdataList != null) {
            pmParam.put("orderBy", "EXPE_SQNC");
            pmParam.put("orderDirection", "ASC");
            Map<String, Object> mExamPrbm = null;
            List<Map<String, Object>> mdataExamPrbmItemLst = null;
            Map<String, Object> mExamPrbmItem = null;
            for (int i = 0; i < mdataList.size(); i++) {
                mExamPrbm = mdataList.get(i);

                String sPrbmAnsrCntn = StringUtils.defaultString((String) mExamPrbm.get("prbm_ansr_cntn")); // 시험문제 작성 답안
                String sAnsrAcqrScrVl = StringUtils.defaultString(mExamPrbm.get("ansr_acqr_scr_vl") + "");
                String sExamPrbmDtscVl = StringUtils.defaultString(mExamPrbm.get("exam_prbm_dtsc_vl") + "");
                String sExamPrbmTypCd = StringUtils.defaultString((String) mExamPrbm.get("exam_prbm_typ_cd"));

                pmParam.put("exam_prbm_id", mExamPrbm.get("exam_prbm_id"));
                mdataExamPrbmItemLst = examPrbmItemService.getExamPrbmItemList(pmParam);

                if (mdataExamPrbmItemLst != null) {
                    String sCranNo = ""; // 시험문제 정답 번호
                    String sCranNoChk = "";
                    String sCranCntn = ""; // 시험문제 정답 내용
                    for (int j = 0; j < mdataExamPrbmItemLst.size(); j++) {
                        mExamPrbmItem = mdataExamPrbmItemLst.get(j);
                        // 시험문제 작성 답안
                        if (!"30".equals(mExamPrbm.get("exam_prbm_typ_cd"))) { // 객관식인 경우
                            sPrbmAnsrCntn = sPrbmAnsrCntn.replace((String) mExamPrbmItem.get("exam_prbm_item_id"), String.valueOf(j + 1));
                        }
                        // 정답인 경우 시험문제 정답 작성
                        if ("Y".equals(mExamPrbmItem.get("cran_yn"))) {
                            if ("30".equals(mExamPrbm.get("exam_prbm_typ_cd"))) { // 주관식인 경우
                                sCranCntn = ((String)mExamPrbmItem.get("exam_prbm_item_cntn")).trim();
                            } else { // 객관식인 경우 문제 번호로 변경
                                sCranNoChk += (!"".equals(sCranNo) ? "," : "") + (j + 1);
                                sCranNo += (!"".equals(sCranNo) ? "\r\n" : "") + (j + 1);
                                sCranCntn += (!"".equals(sCranCntn) ? "\r\n" : "") + mExamPrbmItem.get("exam_prbm_item_cntn");
                            }
                        }
                    }

                    if ("null".equals(sAnsrAcqrScrVl) || "".equals(sAnsrAcqrScrVl)) {
                        if ("30".equals(sExamPrbmTypCd) && sCranCntn.equals(sPrbmAnsrCntn.trim())) {
                            sAnsrAcqrScrVl = sExamPrbmDtscVl;
                        } else if (!"30".equals(sExamPrbmTypCd) && sCranNoChk.equals(sPrbmAnsrCntn)) {
                            sAnsrAcqrScrVl = sExamPrbmDtscVl;
                        } else {
                            sAnsrAcqrScrVl = "0";
                        }
                    }

                    mExamPrbm.put("prbm_ansr_cntn", sPrbmAnsrCntn);
                    mExamPrbm.put("cran_no", sCranNo);
                    mExamPrbm.put("cran_cntn", sCranCntn);
                    mExamPrbm.put("ansr_acqr_scr_vl", sAnsrAcqrScrVl);
                }
                mdataList.set(i, mExamPrbm);
            }
        }

        oData.setTotal(nTotal);
        oData.setList(mdataList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }
    */
    /**
     * 시험문제 분야별 결과 정보(목록)를 검색한다.
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws Exception
     */
    @RequestMapping(value = "/field-result-list")
    public ModelAndView getExamPrbmFieldResultList(XPlatformMapDTO xpDto, Model model, String psPathTyp) throws Exception {
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

            UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
            hmParam.put("bzpt_div", oLoginUser.getBzptdiv());//업체구분

            List<Map<String, Object>> mList = examPrbmService.getExamPrbmFieldResultList(hmParam);

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
    @RequestMapping(value = "/field-result-list")
    public ModelAndView getExamPrbmFieldResultList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        ParamUtils.addPagingParam(pmParam);
        ParamUtils.addCenterParam(pmParam);

        int total = examPrbmService.getExamPrbmFieldResultCount(pmParam);
        List<Map<String, Object>> list = examPrbmService.getExamPrbmFieldResultList(pmParam);

        oData.setTotal(total);
        oData.setList(list);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }
     */
    /**
     * 시험문제 결과 정보(목록)를 검색한다.
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws Exception
     */
    @RequestMapping(value = "/result-list")
    public ModelAndView getExamPrbmResultList(XPlatformMapDTO xpDto, Model model, String psPathTyp) throws Exception {
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

            ParamUtils.addPagingParam(hmParam);
            ParamUtils.addCenterParam(hmParam);

            UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
            hmParam.put("bzpt_div", oLoginUser.getBzptdiv());//업체구분

            int nTotal = examPrbmService.getExamPrbmResultCount(hmParam);
            mapOutVar.put("tc_cnt", nTotal);

            List<Map<String, Object>> mList = examPrbmService.getExamPrbmResultList(hmParam);

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
    public ModelAndView getExamPrbmResultList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        ParamUtils.addPagingParam(pmParam);
        ParamUtils.addCenterParam(pmParam);

        int total = examPrbmService.getExamPrbmResultCount(pmParam);
        List<Map<String, Object>> list = examPrbmService.getExamPrbmResultList(pmParam);

        oData.setTotal(total);
        oData.setList(list);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }
*/
    /**
     * 시험문제 순서 정보를 저장한다.(ajax)
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws Exception
     */
    @RequestMapping(value = "/update-expe-sqnc")
    public ModelAndView updateExamPrbmExpeSqnc(@RequestBody List<Map<String, Object>> pmdatModels) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();

        if (pmdatModels != null && pmdatModels.size() > 0) {
            Map<String, Object> mParam = new HashMap<String, Object>();
            ParamUtils.addSaveParam(mParam);

            examPrbmService.updateExamPrbmExpeSqnc(pmdatModels, mParam);
        }
        modelAndView.addObject(oResult);
        return modelAndView;
    }

}