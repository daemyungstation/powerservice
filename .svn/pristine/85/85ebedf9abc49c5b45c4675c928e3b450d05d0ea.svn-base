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

import powerservice.business.wkly.service.WklyBswrDtlService;
import powerservice.core.bean.ActionList;
import powerservice.core.bean.ActionResult;
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
@RequestMapping(value = "/wklyBusiness/wklyBusiness")
public class WklyBswrDtlController {

    @Resource
    private WklyBswrDtlService wklyBswrDtlService;

    /**
     * 주간보고 업무리스트를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public ModelAndView getEducationList(@RequestBody Map<String, Object> param) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();
        ActionList data = new ActionList();

        int total = wklyBswrDtlService.getWklyBusinessCount(param);

        ParamUtils.addPagingParam(param);
        List<Map<String, Object>> list = wklyBswrDtlService.getWklyBusinessList(param);

        data.setTotal(total);
        data.setList(list);
        oResult.setData(data);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 주간업무를 등록/수정한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    public ModelAndView saveWklyReport(@RequestBody Map<String, Object> param) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();

        ParamUtils.addSaveParam(param);

        wklyBswrDtlService.saveWklyBusiness(param);

        oResult.setData(wklyBswrDtlService.getWklyBusiness(param));
        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 주간업무를 삭제한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    public ModelAndView deleteWklyReport(@RequestBody Map<String, Object> param) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();

        ParamUtils.addCenterParam(param);
        ParamUtils.addSaveParam(param);

        wklyBswrDtlService.deleteWklyBusiness(param);

        oResult.setData(wklyBswrDtlService.getWklyBusinessList(param));
        modelAndView.addObject(oResult);
        return modelAndView;
    }
}