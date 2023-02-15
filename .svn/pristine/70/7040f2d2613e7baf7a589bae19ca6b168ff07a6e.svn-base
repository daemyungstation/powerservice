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

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;
import powerservice.business.bean.UserSession;
import powerservice.business.exa.service.ExamBasiService;
import powerservice.business.exa.service.ExamPrbmItemService;
import powerservice.business.exa.service.ExamPrbmService;
import powerservice.business.exa.service.ExamTrprService;
import powerservice.common.util.DateUtil;
import powerservice.core.bean.ActionList;
import powerservice.core.bean.ActionResult;
import powerservice.core.bean.ActionResultType;
import powerservice.core.bean.UserSessionCore;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;

/**
 * 시험지를 관리한다.
 * Copyright (c) 2014 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2014/04/01
 * @프로그램ID Exam
 */

@Controller
@RequestMapping(value = "/exam/exam-basi")
public class ExamBasiController {

    @Resource
    private ExamBasiService examBasiService;

    @Resource
    private ExamTrprService examTrprService;

    @Resource
    private ExamPrbmService examPrbmService;

    @Resource
    private ExamPrbmItemService examPrbmItemService;

    /**
     * 시험 정보(목록)를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public ModelAndView getExamBasiList(XPlatformMapDTO xpDto, Model model, String psPathTyp) throws Exception {
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

            int nTotal = examBasiService.getExamBasiCount(hmParam);
            mapOutVar.put("tc_cust_memo", nTotal);

            List<Map<String, Object>> mList = examBasiService.getExamBasiList(hmParam);

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
    public ModelAndView getExamBasiList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        ParamUtils.addPagingParam(pmParam);
        ParamUtils.addCenterParam(pmParam);

        int nTotal = examBasiService.getExamBasiCount(pmParam);
        List<Map<String, Object>> mdataList = examBasiService.getExamBasiList(pmParam);

        oData.setTotal(nTotal);
        oData.setList(mdataList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }
    */
    /**
     * 시험 정보를 등록/수정한다.(ajax)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    public ModelAndView saveExamBasi(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                String sExamId = StringUtils.defaultString((String) hmParam.get("exam_id"));
                ParamUtils.addSaveParam(hmParam);

                UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
                System.out.println("업체구분  === :"+oLoginUser.getBzptdiv());
                hmParam.put("bzpt_div", oLoginUser.getBzptdiv());//업체구분

                if ("".equals(sExamId)) {
                    sExamId = examBasiService.insertExamBasi(hmParam);
                } else {
                    // 종료인 경우 전체 채점여부 확인
                    String exam_prgr_stat_cd_change = StringUtils.defaultString(String.valueOf(hmParam.get("exam_prgr_stat_cd_change")));

                    if ("40".equals(exam_prgr_stat_cd_change)) {
                        hmParam.put("no_marking_yn", "Y");
                        int count = examTrprService.getExamTrprCount(hmParam);
                        if (count > 0) {
                            szErrorMsg = "채점 완료 후 종료하시기 바랍니다.";
                            szErrorCode = "-1";
                            return modelAndView;
                        }
                    }
                examBasiService.updateExamBasi(hmParam);
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
    public ModelAndView saveExamBasi(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();

        String sExamId = StringUtils.defaultString((String) pmParam.get("exam_id"));
        ParamUtils.addSaveParam(pmParam);

        if ("".equals(sExamId)) {
            sExamId = examBasiService.insertExamBasi(pmParam);
        } else {
            // 종료인 경우 전체 채점여부 확인
            String sExamPrgrStatCdChange = StringUtils.defaultString(String.valueOf(pmParam.get("exam_prgr_stat_cd_change")));
            if ("40".equals(sExamPrgrStatCdChange)) {
                pmParam.put("no_marking_yn", "Y");
                int count = examTrprService.getExamTrprCount(pmParam);
                if (count > 0) {
                    oResult.setResult(ActionResultType.ERROR);
                    oResult.setErrMsg("채점 완료 후 종료하시기 바랍니다.");
                    modelAndView.addObject(oResult);
                    return modelAndView;
                }
            }
            examBasiService.updateExamBasi(pmParam);
        }
        oResult.setData(examBasiService.getExamBasi(sExamId));

        modelAndView.addObject(oResult);
        return modelAndView;
    }
    */
    /**
     * 시험 정보를 삭제한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    public ModelAndView deleteExamBasi(XPlatformMapDTO xpDto, Model model) throws Exception {
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

                ParamUtils.addCenterParam(hmParam);
                examBasiService.deleteExamBasi(hmParam);
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
    public ModelAndView deleteExamBasi(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();

        ParamUtils.addCenterParam(pmParam);

        examBasiService.deleteExamBasi(pmParam);

        modelAndView.addObject(oResult);
        return modelAndView;
    }
    */
    /**
     * 사용자 시험 정보(목록)를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/my-page-list")
    public ModelAndView getExamBasiMyPageList(XPlatformMapDTO xpDto, Model model, String psPathTyp) throws Exception {
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

            // 시험대상자 사용자ID 조회조건 추가
            UserSessionCore user = SessionUtils.getLoginUser();
            if (user == null) {
                szErrorCode = "-1";
                szErrorMsg  = "사용자 정보가 없습니다.";

                modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

                return modelAndView;
            }
            hmParam.put("exam_trpr_id", user.getUserid());

            int nTotal = examBasiService.getExamBasiMyPageCount(hmParam);
            mapOutVar.put("tc_cnt", nTotal);
            List<Map<String, Object>> mList = examBasiService.getExamBasiMyPageList(hmParam);
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
    @RequestMapping(value = "/my-page-list")
    public ModelAndView getExamBasiMyPageList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        ParamUtils.addPagingParam(pmParam);
        ParamUtils.addCenterParam(pmParam);

        // 시험대상자 사용자ID 조회조건 추가
        UserSessionCore user = SessionUtils.getLoginUser();
        if (user == null) {
            oResult.setResult(ActionResultType.ERROR);
            oResult.setErrMsg("사용자 정보가 없습니다.");
            modelAndView.addObject(oResult);
            return modelAndView;
        }
        pmParam.put("exam_trpr_id", user.getUserid());

        int nTotal = examBasiService.getExamBasiMyPageCount(pmParam);
        List<Map<String, Object>> mdataList = examBasiService.getExamBasiMyPageList(pmParam);

        oData.setTotal(nTotal);
        oData.setList(mdataList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }
    */
    /**
     * 사용자 시험응시 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/exam-info")
    public ModelAndView getExamBasiInfo(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            // 시험 정보 확인

            String sExamId = StringUtils.defaultString((String) hmParam.get("exam_id"));
            String sExamTrprId = StringUtils.defaultString((String) hmParam.get("exam_trpr_id"));
            String sExamAdminId = StringUtils.defaultString((String) hmParam.get("exam_admin_id"));
            String sSearchYn = StringUtils.defaultString((String) hmParam.get("search_yn"));
            System.out.println("### sExamAdminId [" + sExamAdminId + "]");
            if ("".equals(sExamId)) {
                szErrorCode = "-1";
                szErrorMsg  = "시험 정보가 없습니다.";
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

                return modelAndView;
            }
            if ("".equals(sExamTrprId)) {
                szErrorCode = "-1";
                szErrorMsg  = "시험대상자 정보가 없습니다.";
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

                return modelAndView;
            }

            UserSessionCore user = SessionUtils.getLoginUser();
            if (user == null) {
                szErrorCode = "-1";
                szErrorMsg  = "로그인 정보가 없습니다.";
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

                return modelAndView;
            } /*else if (!"".equals(sExamAdminId)) { // 관리자인 경우
                if (!sExamAdminId.equals(user.getUserid())) {
                    szErrorCode = "-1";
                    szErrorMsg  = "시험관리자 정보가 로그인 사용자와 일치하지 않습니다.";
                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

                    return modelAndView;
                }
            } else if (!sExamTrprId.equals(user.getUserid())) { // 상담사인 경우
                szErrorCode = "-1";
                szErrorMsg  = "시험대상자 정보가 로그인 사용자와 일치하지 않습니다.";
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

                return modelAndView;
            }*/

            // 시험 정보 조회

            List<Map<String, Object>> mdataExamlist = examBasiService.getExamBasiMyPageList(hmParam);
            if (mdataExamlist == null || mdataExamlist.size() != 1) {
                szErrorCode = "-1";
                szErrorMsg  = "시험대상자가 아닙니다.";
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

                return modelAndView;
            }
            Map<String, Object> exam = mdataExamlist.get(0);
            /*
            String sAnsrYn = StringUtils.defaultString((String) exam.get("ansr_yn"));
            if (!"Y".equals(sSearchYn) && "".equals(sExamAdminId) && !"Y".equals(sAnsrYn)) {
                szErrorCode = "-1";
                szErrorMsg  = "시험응시할 수 없습니다.";
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

                return modelAndView;
            }
             */
            // 시험지 정보 확인
            String sEmshId = StringUtils.defaultString((String) exam.get("emsh_id"));
            if ("".equals(sEmshId)) {
                szErrorCode = "-1";
                szErrorMsg  = "시험지 정보가 없습니다.";
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

                return modelAndView;
            }

            // 시험문제 정보 조회
            hmParam.put("emsh_id", sEmshId);
            List<Map<String, Object>> mdataExamPrbmList = examPrbmService.getExamPrbmAnsrList(hmParam);
            if (mdataExamPrbmList == null) {
                szErrorCode = "-1";
                szErrorMsg  = "시험문제 정보가 없습니다.";
                modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

                return modelAndView;
            }

            List<Map<String, Object>> examPrbmItemList = null;
            List<Map<String, Object>> item = new ArrayList<Map<String,Object>>();

            // 시험문제항목 정보 조회
            for (int i = 0; i < mdataExamPrbmList.size(); i++) {
                Map<String, Object> mExamPrbm = mdataExamPrbmList.get(i);

                hmParam.put("exam_prbm_id", mExamPrbm.get("exam_prbm_id"));
                hmParam.put("exam_prbm_typ_cd", mExamPrbm.get("exam_prbm_typ_cd"));

                examPrbmItemList = examPrbmItemService.getExamPrbmItemAnsrList(hmParam);
                item.addAll(examPrbmItemList);

            }

            listMap.setRowMaps(mdataExamPrbmList);
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
    /*
    @RequestMapping(value = "/exam-info")
    public ModelAndView getExamBasiInfo(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();
        Map<String, Object> oData = new HashMap<String, Object>();

        ParamUtils.addPagingParam(pmParam);
        ParamUtils.addCenterParam(pmParam);

        // 시험 정보 확인

        String sExamId = StringUtils.defaultString((String) pmParam.get("exam_id"));
        String sExamTrprId = StringUtils.defaultString((String) pmParam.get("exam_trpr_id"));
        String sExamAdminId = StringUtils.defaultString((String) pmParam.get("exam_admin_id"));
        String sSearchYn = StringUtils.defaultString((String) pmParam.get("search_yn"));
        System.out.println("### sExamAdminId [" + sExamAdminId + "]");
        if ("".equals(sExamId)) {
            oResult.setResult(ActionResultType.ERROR);
            oResult.setErrMsg("시험 정보가 없습니다.");
            modelAndView.addObject(oResult);
            return modelAndView;
        }
        if ("".equals(sExamTrprId)) {
            oResult.setResult(ActionResultType.ERROR);
            oResult.setErrMsg("시험대상자 정보가 없습니다.");
            modelAndView.addObject(oResult);
            return modelAndView;
        }

        UserSessionCore user = SessionUtils.getLoginUser();
        if (user == null) {
            oResult.setResult(ActionResultType.ERROR);
            oResult.setErrMsg("로그인 정보가 없습니다.");
            modelAndView.addObject(oResult);
            return modelAndView;
        } else if (!"".equals(sExamAdminId)) { // 관리자인 경우
            if (!sExamAdminId.equals(user.getUserid())) {
                oResult.setResult(ActionResultType.ERROR);
                oResult.setErrMsg("시험관리자 정보가 로그인 사용자와 일치하지 않습니다.");
                modelAndView.addObject(oResult);
                return modelAndView;
            }
        } else if (!sExamTrprId.equals(user.getUserid())) { // 상담사인 경우
            oResult.setResult(ActionResultType.ERROR);
            oResult.setErrMsg("시험대상자 정보가 로그인 사용자와 일치하지 않습니다.");
            modelAndView.addObject(oResult);
            return modelAndView;
        }

        // 시험 정보 조회

        List<Map<String, Object>> mdataExamlist = examBasiService.getExamBasiMyPageList(pmParam);
        if (mdataExamlist == null || mdataExamlist.size() != 1) {
            oResult.setResult(ActionResultType.ERROR);
            oResult.setErrMsg("시험대상자가 아닙니다.");
            modelAndView.addObject(oResult);
            return modelAndView;
        }
        Map<String, Object> exam = mdataExamlist.get(0);

        String sAnsrYn = StringUtils.defaultString((String) exam.get("ansr_yn"));
        if (!"Y".equals(sSearchYn) && "".equals(sExamAdminId) && !"Y".equals(sAnsrYn)) {
            oResult.setResult(ActionResultType.ERROR);
            oResult.setErrMsg("시험응시할 수 없습니다.");
            modelAndView.addObject(oResult);
            return modelAndView;
        }

        // 시험지 정보 확인
        String sEmshId = StringUtils.defaultString((String) exam.get("emsh_id"));
        if ("".equals(sEmshId)) {
            oResult.setResult(ActionResultType.ERROR);
            oResult.setErrMsg("시험지 정보가 없습니다.");
            modelAndView.addObject(oResult);
            return modelAndView;
        }

        // 시험문제 정보 조회
        pmParam.put("emsh_id", sEmshId);
        List<Map<String, Object>> mdataExamPrbmList = examPrbmService.getExamPrbmAnsrList(pmParam);
        if (mdataExamPrbmList == null) {
            oResult.setResult(ActionResultType.ERROR);
            oResult.setErrMsg("시험문제 정보가 없습니다.");
            modelAndView.addObject(oResult);
            return modelAndView;
        }

        // 시험문제항목 정보 조회
        for (int i = 0; i < mdataExamPrbmList.size(); i++) {
            Map<String, Object> mExamPrbm = mdataExamPrbmList.get(i);

            pmParam.put("exam_prbm_id", mExamPrbm.get("exam_prbm_id"));
            pmParam.put("exam_prbm_typ_cd", mExamPrbm.get("exam_prbm_typ_cd"));
            mExamPrbm.put("exam_prbm_item_list", examPrbmItemService.getExamPrbmItemAnsrList(pmParam));

            mdataExamPrbmList.set(i, mExamPrbm);
        }

        oData.put("exam", exam);
        oData.put("exam_prbm_list", mdataExamPrbmList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }
*/
    /**
     * 시험 결과 정보(목록)를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/result-list")
    public ModelAndView getExamResultList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            int nTotal = examBasiService.getExamBasiCount(hmParam);
            mapOutVar.put("tc_cust_memo", nTotal);

            List<Map<String, Object>> mList = examBasiService.getExamResultList(hmParam);

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
    @RequestMapping(value = "/result-list")
    public ModelAndView getExamResultList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        ParamUtils.addPagingParam(pmParam);
        ParamUtils.addCenterParam(pmParam);

        int nTotal = examBasiService.getExamBasiCount(pmParam);
        List<Map<String, Object>> mdataList = examBasiService.getExamResultList(pmParam);

        oData.setTotal(nTotal);
        oData.setList(mdataList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }
*/
    /**
     * 시험 결과 정보(점수별)를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/result-list/score")
    public ModelAndView getExamResultScoreList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            //int nTotal = examBasiService.getExamBasiCount(hmParam);
            List<Map<String, Object>> mList = examBasiService.getExamResultScoreList(hmParam);
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
    public ModelAndView getExamResultScoreList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        ParamUtils.addPagingParam(pmParam);
        ParamUtils.addCenterParam(pmParam);

        int nTotal = examBasiService.getExamBasiCount(pmParam);
        List<Map<String, Object>> mdataList = examBasiService.getExamResultScoreList(pmParam);

        oData.setTotal(nTotal);
        oData.setList(mdataList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }
    */
    /**
     * 평가계획별 결과조회 엑셀다운
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/result-list/excel-down")
    public void getExamResultExcel(@RequestParam Map<String, Object> pmParam, HttpServletResponse response) throws Exception {
        ServletOutputStream fout = null;

        ParamUtils.addCenterParam(pmParam);
        List<Map<String, Object>> mdataList = examBasiService.getExamResultScoreList(pmParam);

        if (mdataList.size() != 0) {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("기간별결과조회(점수)");
            XSSFRow row;
            XSSFCell cell;

            int nTrprCnt = Integer.parseInt(mdataList.get(0).get("trpr_cnt") + "");
            int nExamRow = 1;
            int nListRow = 0;
            int nExamCnt = 0;
            String sExamAvg = "";
            String sExamRank = "";

            XSSFCellStyle style = workbook.createCellStyle();
            XSSFCellStyle styleTitle = workbook.createCellStyle();
            styleTitle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
            styleTitle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
            styleTitle.setBorderTop(XSSFCellStyle.BORDER_THIN);
            styleTitle.setBorderRight(XSSFCellStyle.BORDER_THIN);
            styleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
            style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
            style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
            style.setBorderTop(XSSFCellStyle.BORDER_THIN);
            style.setBorderRight(XSSFCellStyle.BORDER_THIN);

            row = sheet.createRow(0);
            cell = row.createCell(0);
            cell.setCellValue("시험명(평균)/상담유형명(순위)");
            cell.setCellStyle(styleTitle);
            sheet.setColumnWidth(0, 7000);

            for (int i = 0; i < nTrprCnt; i++) { // 사용자명
                cell = row.createCell(i+1);
                cell.setCellValue((String)mdataList.get(i).get("exam_trpr_nm"));
                cell.setCellStyle(styleTitle);
                sheet.setColumnWidth(i+1, 5000);
            }

            for (int i = 0; i < mdataList.size(); i+=nTrprCnt) { // 시험명
                row = sheet.createRow(nExamRow);
                cell = row.createCell(0);
                sExamAvg = String.valueOf(mdataList.get(i).get("exam_avg"));
                if ("null".equals(sExamAvg)) {
                    sExamAvg = "미채점";
                }
                cell.setCellValue(mdataList.get(i).get("exam_nm") + "(" + sExamAvg + ")");
                cell.setCellStyle(style);

                for (int j = 0; j < nTrprCnt; j++) {
                    cell = row.createCell(j+1);
                    nExamCnt = Integer.parseInt(mdataList.get(nListRow).get("exam_cnt") + "");
                    sExamRank = String.valueOf(mdataList.get(nListRow).get("exam_rnkn_sqno"));
                    if (nExamCnt == 0) {
                        cell.setCellValue("해당없음");
                    } else {
                        if ("null".equals(sExamRank)) {
                            cell.setCellValue("미채점");
                        } else {
                            cell.setCellValue(mdataList.get(nListRow).get("last_scr_vl") + "점(" + sExamRank + "위)");
                        }
                    }
                    cell.setCellStyle(style);
                    ++nListRow;
                }
                ++nExamRow;
            }

            try {
                fout = response.getOutputStream();
                StringBuffer contentDisposition = new StringBuffer();
                String excelnm = "기간별_결과조회(점수)";
                contentDisposition.append("attachment;fileName=\"");
                contentDisposition.append(URLEncoder.encode(excelnm, "UTF-8").replaceAll("\\+", "%20"));
                contentDisposition.append("_");
                contentDisposition.append(DateUtil.currentTimeToString(DateUtil.SDF_YYYYMMDDHHMM));
                contentDisposition.append(".xlsx");
                contentDisposition.append("\";");
                response.setHeader("Content-Disposition", contentDisposition.toString());
                response.setContentType("application/x-msexcel");
                workbook.write(fout);
                fout.flush();
                fout.close();
            } catch(Exception exception) {
                exception.printStackTrace();
            } finally {
                if (fout != null) {
                    fout.close();
                }
            }
        }
    }

    /**
     * 2015_02_10_최현식
     * 시험정보 리스트 조회 추가
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/search/list")
    public ModelAndView getSearchExamBasiList(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            // 시험대상자 사용자ID 조회조건 추가
            UserSessionCore user = SessionUtils.getLoginUser();

            hmParam.put("exam_trpr_id", user.getUserid());

            int nTotal = examBasiService.getSearchExamBasiCount(hmParam);
            List<Map<String, Object>> mList = examBasiService.getSearchExamBasiList(hmParam);

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
    @RequestMapping(value = "/search/list")
    public ModelAndView getSearchExamBasiList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        ParamUtils.addPagingParam(pmParam);
        ParamUtils.addCenterParam(pmParam);

        // 시험대상자 사용자ID 조회조건 추가
        UserSessionCore user = SessionUtils.getLoginUser();

        pmParam.put("exam_trpr_id", user.getUserid());

        int nTotal = examBasiService.getSearchExamBasiCount(pmParam);
        List<Map<String, Object>> mdataList = examBasiService.getSearchExamBasiList(pmParam);

        oData.setTotal(nTotal);
        oData.setList(mdataList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }
*/
}