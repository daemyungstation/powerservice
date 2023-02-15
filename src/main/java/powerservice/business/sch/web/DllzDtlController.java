/*
 * (@)# DllzDtlController.java
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

package powerservice.business.sch.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import egovframework.rte.cmmn.ria.xplatform.XPlatformConstant;
import egovframework.rte.cmmn.ria.xplatform.map.DataSetMap;
import egovframework.rte.cmmn.ria.xplatform.map.XPlatformMapDTO;
import powerservice.business.sch.service.DllzDtlService;
import powerservice.business.usr.service.LgnHstrService;
import powerservice.core.bean.ActionList;
import powerservice.core.bean.ActionResult;
import powerservice.core.bean.ActionResultType;
import powerservice.core.bean.UserSessionCore;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;

/**
 * 근태내역 관리를 한다.
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정재동
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID DllzDtl
 */
@Controller
@RequestMapping(value = {"/mypage/dllz-dtl", "/scdl/dllz-dtl", "/user/dllz-dtl"})
public class DllzDtlController {

    @Resource
    private DllzDtlService dllzDtlService;

    @Resource
    private LgnHstrService lgnHstrService;
    /**
     * 근태내역 정보를 검색한다.
     *
     * @pmParam pmParam Map<String, Object>
     * @return ActionResult
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public ModelAndView getDllzDtlList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        ParamUtils.addPagingParam(pmParam);
        ParamUtils.addCenterParam(pmParam);

        // MyPage 근태현황관리 인 경우 사용자ID 추가
        if ("Y".equals(pmParam.get("mypage_flag"))) {
            UserSessionCore oLoginUser = SessionUtils.getLoginUser();
            pmParam.put("user_id", oLoginUser.getUserid());
        }

        int nTotal = dllzDtlService.getDllzDtlCount(pmParam);
        List<Map<String, Object>> mList = dllzDtlService.getDllzDtlList(pmParam);

        oData.setTotal(nTotal);
        oData.setList(mList);

        oResult.setData(oData);
        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 근태내역 정보를 등록/수정한다.
     *
     * @pmParam pmParam Map<String, Object>
     * @return ActionResult
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public ModelAndView save(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();

        Object oPageIndx = pmParam.get("page_indx");
        String sGoofDt = StringUtils.defaultString((String) pmParam.get("goof_dt"));
        String sUserId = StringUtils.defaultString((String) pmParam.get("user_id"));

        Map<String, Object> mSearchParam = ParamUtils.getCenterParam();
        mSearchParam.put("goof_dt", sGoofDt);
        mSearchParam.put("user_id", sUserId);

        ParamUtils.addSaveParam(pmParam);

        if (oPageIndx == null) { // 등록
            // 중복 확인
            int nCount = dllzDtlService.getDllzDtlCount(mSearchParam);
            if (nCount > 0) {
                oResult.setResult(ActionResultType.ERROR);
                oResult.setErrMsg("선택한 일자에 상담사 근태내역이 존재합니다.");

                modelAndView.addObject(oResult);
                return modelAndView;
            }

            dllzDtlService.insertDllzDtl(pmParam);
        } else { // 수정
            dllzDtlService.updateDllzDtl(pmParam);
        }

        oResult.setData(dllzDtlService.getDllzDtl(mSearchParam));
        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 근태내역 정보를 삭제한다.
     *
     * @pmParam pmParam Map<String, Object>
     * @return ActionResult
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public ModelAndView delete(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();

        dllzDtlService.deleteDllzDtl(pmParam);

        modelAndView.addObject(oResult);
        return modelAndView;
    }


    /**
     * 로그인이력 정보를 검색한다.
     *
     * @pmParam pmParam Map<String, Object>
     * @return ActionResult
     * @throws Exception
     */
    @RequestMapping(value = "/lgn-hstr-list")
    @ResponseBody
    public ModelAndView getLgnHstrList(XPlatformMapDTO xpDto, Model model, String psPathTyp) throws Exception {
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

            ParamUtils.addCenterParam(hmParam);
            int nTotal = lgnHstrService.getLgnHstrCount(hmParam);
            mapOutVar.put("tc_cnt", nTotal);

            ParamUtils.addPagingParam(hmParam);
            List<Map<String, Object>> mList = lgnHstrService.getLgnHstrList(hmParam);
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
    @RequestMapping(value = "/lgn-hstr-list")
    @ResponseBody
    public ModelAndView getLgnHstrList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        ParamUtils.addCenterParam(pmParam);
        int nTotal = lgnHstrService.getLgnHstrCount(pmParam);

        ParamUtils.addPagingParam(pmParam);
        List<Map<String, Object>> mList = lgnHstrService.getLgnHstrList(pmParam);

        oData.setTotal(nTotal);
        oData.setList(mList);

        oResult.setData(oData);
        modelAndView.addObject(oResult);
        return modelAndView;
    }
    */

}