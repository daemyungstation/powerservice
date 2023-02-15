/*
 * (@)# EdctCrsController.java
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

package powerservice.business.edc.web;

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

import powerservice.business.bean.UserSession;
import powerservice.business.edc.service.EdctCrsService;
import powerservice.business.edc.service.EdctTrprService;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;


/**
 * 교육평가 관리를 한다.
 *
 * Copyright (c) 2013 Company Inwoo Tech Inc.
 *
 * @author 박상수
 * @version 1.0
 * @date 2015/01/05
 * @프로그램ID EdctCrsController
 */

@Controller
@RequestMapping(value = "/edct-crs/edct-crs")
public class EdctCrsController {

    @Resource
    private EdctCrsService edctCrsService;

    @Resource
    private EdctTrprService edctTrprService;

    /**
     * 교육평가를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public ModelAndView getEdctCrsList(XPlatformMapDTO xpDto, Model model, String psPathTyp) throws Exception {
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

            // 업체구분
            UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
            hmParam.put("bzpt_div", oLoginUser.getBzptdiv());

            int nTotal = edctCrsService.getEdctCrsCount(hmParam);
            mapOutVar.put("total_count", nTotal);

            List<Map<String, Object>> mList = edctCrsService.getEdctCrsList(hmParam);
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
     * 교육평가를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
/*    @RequestMapping(value = "/monitoring")
    public ModelAndView getMonitoringList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        pmParam.put("edumonitoring", "on");

        int nTotal = edctCrsService.getEdctCrsCount(pmParam);

        ParamUtils.addPagingParam(pmParam);

        List<Map<String, Object>> mList = edctCrsService.getEdctCrsList(pmParam);

        oData.setTotal(nTotal);
        oData.setList(mList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }*/


    /**
     * 교육평가를 등록/수정한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    public ModelAndView saveEdcuation(XPlatformMapDTO xpDto, Model model) throws Exception {
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
                Map<String, Object> hmParam = listInDs.get(0);
                String sEdctCrsId = StringUtils.defaultString((String) hmParam.get("edct_crs_id"));//교육 과정 ID
                String sEdctPrgrStatCd = StringUtils.defaultString((String) hmParam.get("edct_prgr_stat_cd"));//교육 진행 상태 코드

                // 업체구분
                UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
                hmParam.put("bzpt_div", oLoginUser.getBzptdiv());

                if ("".equals(sEdctCrsId)) {//신규
                    ParamUtils.addSaveParam(hmParam);
                    sEdctCrsId = edctCrsService.insertEdctCrs(hmParam);
                } else {
                    if ("20".equals(sEdctPrgrStatCd)) {
                        Map<String, Object> mSrchAssign = new HashMap<String, Object>();
                        mSrchAssign.put("edct_crs_id", sEdctCrsId);
                        mSrchAssign.put("assign_yn", "N");

                        if (edctTrprService.getEdctTrprcount(mSrchAssign) > 0) {
                            szErrorCode ="-1";
                            szErrorMsg = "아직 할당되지 않은 교육대상자가 있습니다.";
                        } else {
                            edctCrsService.updateEdctCrs(hmParam);
                        }
                    } else {
                        edctCrsService.updateEdctCrs(hmParam);
                    }
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
     * 교육평가를 복사한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/copy")
    public ModelAndView copyEdctCrs(XPlatformMapDTO xpDto, Model model) throws Exception {
        ModelAndView modelAndView = new ModelAndView("xplatformMapView");

        // 에러코드및 메시지
        String szErrorCode = "0";
        String szErrorMsg  = "OK";
        String sEdctCrsId = "";

        try{
            Map <String, Object> mapInVar     = xpDto.getInVariableMap();
            Map <String, DataSetMap> mapInDs  = xpDto.getInDataSetMap();
            Map <String, Object> mapOutVar    = xpDto.getOutVariableMap();
            Map <String, DataSetMap> mapOutDs = xpDto.getOutDataSetMap();

            DataSetMap listInDs = (DataSetMap)mapInDs.get("ds_input");
            Map<String, Object> hmParam = listInDs.get(0);

            ParamUtils.addSaveParam(hmParam);
            sEdctCrsId = edctCrsService.copyEdctCrs(hmParam);

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
     * 교육평가를 삭제한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/delete")

    public ModelAndView deleteEdctCrs(XPlatformMapDTO xpDto, Model model) throws Exception {
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
            Map<String, Object> hmParam = listInDs.get(0);

            String educationCheck = StringUtils.defaultString((String) hmParam.get("selectcheck"));
            if (!"".equals(educationCheck)) {
                hmParam.put("selectcheck", educationCheck.split(","));
            }

            edctCrsService.deleteEdctCrs(hmParam);

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
     * 월간 교육훈련 결과(상담사별)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/processEdctTrprresult")
    public ModelAndView getUserProcstateList(XPlatformMapDTO xpDto, Model model, String psPathTyp) throws Exception {
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

            // 업체구분
            UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
            hmParam.put("bzpt_div", oLoginUser.getBzptdiv());

            List<Map<String, Object>> mList = edctCrsService.searchEdctCrsResult(hmParam);
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
     * 교육평가를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */

    @RequestMapping(value = "/mypage-list")
    public ModelAndView getMypageEduList(XPlatformMapDTO xpDto, Model model, String psPathTyp) throws Exception {
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

            // 교육대상자 사용자ID 조회조건 추가
            UserSession user = (UserSession) SessionUtils.getLoginUser();
            if (user == null) {
                szErrorCode = "-1";
                szErrorMsg  = "사용자정보가 없습니다.";
            }

            hmParam.put("user_id", user.getUserid());
            hmParam.put("mypage", "Y");

            int nTotal = edctCrsService.getMyPageEdctCount(hmParam);
            mapOutVar.put("total_count", nTotal);

            List<Map<String, Object>> mList = edctCrsService.getMyPageEdctList(hmParam);

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
     * 교육평가를 조회한다. - MYPAGE
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws Exception
     */
    @RequestMapping(value="/view-edct-crs")
    public ModelAndView getEdctCrs(XPlatformMapDTO xpDto, Model model, String psPathTyp) throws Exception {
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

            List<Map<String, Object>> mList = edctCrsService.getMyPageEdctList(hmParam);
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