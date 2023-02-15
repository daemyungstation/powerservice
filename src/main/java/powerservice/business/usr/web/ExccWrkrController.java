/*
 * (@)# ExccWrkrController.java
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

package powerservice.business.usr.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.bean.UserSession;
import powerservice.business.usr.service.ExccWrkrService;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;
import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;

/**
 * 우수사원 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 배윤정
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID ExccWrkr
 */
@Controller
@RequestMapping(value = "/user/excc-wrkr")
public class ExccWrkrController {

    @Resource
    private ExccWrkrService exccWrkrService;

    /**
     * 우수사원 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list/{pageType}")
    public ModelAndView getExccWrkrList(@PathVariable("pageType") String psPathType, XPlatformMapDTO xpDto, Model model, String psPathTyp) throws Exception {
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

            // 상담 화면에서 조회하는경우
            if ("usr".equals(psPathType)) {
                hmParam.put("main_yn", "Y");
            }

            ParamUtils.addPagingParam(hmParam);

            UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
            hmParam.put("bzpt_div", oLoginUser.getBzptdiv());

            int nTotal = exccWrkrService.getExccWrkrCount(hmParam);
            mapOutVar.put("tc_cnt", nTotal);

            List<Map<String, Object>> mList = exccWrkrService.getExccWrkrList(hmParam);
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
    @RequestMapping(value = "/list/{pageType}")
    public ModelAndView getExccWrkrList(@PathVariable("pageType") String psPathType, @RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        // 상담 화면에서 조회하는경우
        if ("usr".equals(psPathType)) {
            pmParam.put("main_yn", "Y");
        }

        ParamUtils.addPagingParam(pmParam);

        int nTotal = exccWrkrService.getExccWrkrCount(pmParam);

        List<Map<String, Object>> moDataList = exccWrkrService.getExccWrkrList(pmParam);

        oData.setTotal(nTotal);
        oData.setList(moDataList);

        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }
    */
    /**
     * 선정순위 중복 건수를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/duplicate-count")
    public ModelAndView getExccWrkrDuplicateCount(XPlatformMapDTO xpDto, Model model, String psPathTyp) throws Exception {
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

            String sOldChosBasYrmn = StringUtils.defaultString((String) hmParam.get("old_chos_bas_yrmn"));
            String sChosBasYrmn = StringUtils.defaultString((String) hmParam.get("chos_bas_yrmn"));

            Map<String, Object> mSearchParam = ParamUtils.getCenterParam();
            mSearchParam.put("chos_bas_yrmn", sChosBasYrmn);
            UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
            mSearchParam.put("bzpt_div", oLoginUser.getBzptdiv());
            hmParam.put("bzpt_div", oLoginUser.getBzptdiv());

            int nTotal = 0;

            if (!sOldChosBasYrmn.equals(sChosBasYrmn)) {
                // 해당월에 선정된 우수사원 상담사인지 확인
                mSearchParam.put("user_id", hmParam.get("user_id"));

                nTotal = exccWrkrService.getExccWrkrCount(mSearchParam);

                if (nTotal > 0) {
                    szErrorCode = "-1";
                    szErrorMsg  = "이미 해당월에 우수사원으로 선정된 상담사입니다.";

                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

                    return modelAndView;
                }

                // 해당월의 우수사원 선정수 조회
                mSearchParam.remove("user_id");
                nTotal = exccWrkrService.getExccWrkrCount(mSearchParam);

                if (nTotal >= 2) {
                    szErrorCode = "-1";
                    szErrorMsg  = "이미 해당월에 2명의 우수사원 선정이 완료되었습니다.";

                    modelAndView.addObject(XPlatformConstant.ERROR_CODE, szErrorCode);
                    modelAndView.addObject(XPlatformConstant.ERROR_MSG,  szErrorMsg);

                    return modelAndView;
                }
            }

            // 순위중복조회
            mSearchParam.put("chos_rnkn", hmParam.get("chos_rnkn"));
            mSearchParam.put("chos_bas_yrmn", hmParam.get("chos_bas_yrmn"));
            mSearchParam.put("excc_wrkr_id", hmParam.get("excc_wrkr_id"));

            nTotal = exccWrkrService.getExccWrkrDuplicateCount(mSearchParam);

            mapOutVar.put("check_cnt", nTotal);

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
    @RequestMapping(value = "/duplicate-count")
    public ModelAndView getExccWrkrDuplicateCount(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        String sOldChosBasYrmn = StringUtils.defaultString((String) pmParam.get("old_chos_bas_yrmn"));
        String sChosBasYrmn = StringUtils.defaultString((String) pmParam.get("chos_bas_yrmn"));

        Map<String, Object> mSearchParam = ParamUtils.getCenterParam();
        mSearchParam.put("chos_bas_yrmn", sChosBasYrmn);

        int nTotal = 0;

        if (!sOldChosBasYrmn.equals(sChosBasYrmn)) {
            // 해당월에 선정된 우수사원 상담사인지 확인
            mSearchParam.put("user_id", pmParam.get("user_id"));

            nTotal = exccWrkrService.getExccWrkrCount(mSearchParam);

            if (nTotal > 0) {
                oResult.setResult(ActionResultType.ERROR);
                oResult.setErrMsg("이미 해당월에 우수사원으로 선정된 상담사입니다.");

                modelAndView.addObject(oResult);
                return modelAndView;
            }

            // 해당월의 우수사원 선정수 조회
            mSearchParam.remove("user_id");
            nTotal = exccWrkrService.getExccWrkrCount(mSearchParam);

            if (nTotal >= 2) {
                oResult.setResult(ActionResultType.ERROR);
                oResult.setErrMsg("이미 해당월에 2명의 우수사원 선정이 완료되었습니다.");

                modelAndView.addObject(oResult);
                return modelAndView;
            }
        }

        // 순위중복조회
        mSearchParam.put("chos_rnkn", pmParam.get("chos_rnkn"));
        mSearchParam.put("chos_bas_yrmn", pmParam.get("chos_bas_yrmn"));
        mSearchParam.put("excc_wrkr_id", pmParam.get("excc_wrkr_id"));

        nTotal = exccWrkrService.getExccWrkrDuplicateCount(mSearchParam);

        oData.setTotal(nTotal);

        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }
    */
    /**
     * 우수사원 정보를 등록/수정한다.(ajax)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    public ModelAndView saveExccWrkr(XPlatformMapDTO xpDto, Model model) throws Exception {
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

            String sExccWrkrId = StringUtils.defaultString((String) hmParam.get("excc_wrkr_id"));

            ParamUtils.addSaveParam(hmParam);

            UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
            hmParam.put("bzpt_div", oLoginUser.getBzptdiv());

            // 우수사원 저장
            if ("".equals(sExccWrkrId)) {	// 신규등록시
                sExccWrkrId = exccWrkrService.insertExccWrkr(hmParam);
            } else {	// 수정
                exccWrkrService.updateExccWrkr(hmParam);
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
    public ModelAndView saveExccWrkr(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        String sExccWrkrId = StringUtils.defaultString((String) pmParam.get("excc_wrkr_id"));

        ParamUtils.addSaveParam(pmParam);

        // 우수사원 저장
        if ("".equals(sExccWrkrId)) {	// 신규등록시
            sExccWrkrId = exccWrkrService.insertExccWrkr(pmParam);
        } else {	// 수정
            exccWrkrService.updateExccWrkr(pmParam);
        }
        oResult.setData(exccWrkrService.getExccWrkr(sExccWrkrId));

        modelAndView.addObject(oResult);
        return modelAndView;
    }
    */
    /**
     * 우수사원 정보를 삭제한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    public ModelAndView deleteExccWrkr(XPlatformMapDTO xpDto, Model model) throws Exception {
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

        exccWrkrService.deleteExccWrkr(hmParam);

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
    public ModelAndView deleteExccWrkr(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        String sSelectCheck = StringUtils.defaultString((String) pmParam.get("selectcheck"));
        pmParam.put("selectcheck", sSelectCheck.split(","));

        exccWrkrService.deleteExccWrkr(pmParam);

        modelAndView.addObject(oResult);
        return modelAndView;
    }
    */
}