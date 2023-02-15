/*
 * (@)# WklyRprgBasiController.java
 *
 * @author 박상수
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

package powerservice.business.wkly.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.wkly.service.WklyBswrDtlsCdService;
import powerservice.business.wkly.service.WklyBswrTypService;
import powerservice.core.bean.ActionList;
import powerservice.core.bean.ActionResult;
import powerservice.core.bean.ActionResultType;
import powerservice.core.util.ParamUtils;

/**
 * 주간보고 관리를 한다.
 *
 * Copyright (c) 2013 Company Inwoo Tech Inc.
 *
 * @author 박상수
 * @version 1.0
 * @date 2015/01/05
 * @프로그램ID WklyRprgBasiController
 */
@Controller
@RequestMapping(value = "/system/wklycode-set")
public class WklyBswrTypController {

    @Resource
    private WklyBswrTypService wklyBswrTypService;

    @Resource
    private WklyBswrDtlsCdService wklyBswrDtlsCdService;

    /**
     * 코드셋 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public ModelAndView searchCodeSet(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        int ntotal = wklyBswrTypService.getCodeSetCount(pmParam);

        ParamUtils.addPagingParam(pmParam);
        List<Map<String, Object>> mDataList = wklyBswrTypService.getCodeSetList(pmParam);

        oData.setTotal(ntotal);
        oData.setList(mDataList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 코드셋 정보를 검색한다.
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws Exception
     */
    @RequestMapping(value = "/drop-down-list")
    public ModelAndView searchCodeSetList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        Map<String, Object> mSearchParam = ParamUtils.convertDropDownParam(pmParam);

        int ntotal = wklyBswrTypService.getCodeSetCount(pmParam);
        List<Map<String, Object>> mDataList = wklyBswrTypService.getCodeSetList(mSearchParam);

        oData.setTotal(ntotal);
        oData.setList(mDataList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 코드셋 정보를 등록/수정한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    public ModelAndView save(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();
        String sResult = "";

        Object oPageIndx = pmParam.get("page_indx");

        ParamUtils.addSaveParam(pmParam);

        if (oPageIndx == null) {
             sResult = wklyBswrTypService.insertCodeSet(pmParam);
        } else {
            wklyBswrTypService.updateCodeSet(pmParam);
        }

        if (!sResult.equals("")) {
            oResult.setResult(ActionResultType.ERROR);
            oResult.setErrMsg(sResult);
        } else {
            Map<String, Object> mSearchParam = ParamUtils.getCenterParam();
            mSearchParam.put("wkly_bswr_typ_cd", pmParam.get("wkly_bswr_typ_cd"));
            ParamUtils.addCenterParam(mSearchParam);

            oResult.setData(wklyBswrTypService.getCodeSet(mSearchParam));
        }

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    @RequestMapping(value = "/duplicate-count")
    public ModelAndView getCodeSetDuplicateCount(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        ParamUtils.addCenterParam(pmParam);
        int nTotal = wklyBswrTypService.getCodeSetDuplicateCount(pmParam);

        oData.setTotal(nTotal);

        oResult.setData(oData);
        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 코드셋 순서 최대값을 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @throws Exception
     */
    @RequestMapping(value = "/max-sequence")
    public ModelAndView getCodeSetMaxSequence(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();

        ParamUtils.addCenterParam(pmParam);

        oResult.setData(wklyBswrTypService.getCodeSetMaxSequence(pmParam));
        modelAndView.addObject(oResult);
        return modelAndView;
    }
}