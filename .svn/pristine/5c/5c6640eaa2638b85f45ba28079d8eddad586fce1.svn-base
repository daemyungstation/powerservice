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

import powerservice.business.wkly.service.WklyRprgEtcService;
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
 * @프로그램ID WklyRprgEtcController
 */
@Controller
@RequestMapping(value = "/wklyetc/wklyetc")
public class WklyRprgEtcController {

    @Resource
    private WklyRprgEtcService wklyRprgEtcService;

    /**
     * 주간보고 등록 상세정보를 불러온다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public ModelAndView getWklyReportDetail(@RequestBody Map<String, Object> param) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();

        oResult.setData(wklyRprgEtcService.getWklyEtcList(param));
        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 주간보고를 등록/수정한다.
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

        wklyRprgEtcService.saveWklyEtc(param);

        oResult.setData(wklyRprgEtcService.getWklyEtcList(param));
        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 주간보고 대상자를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/result")
    public ModelAndView getWklyReportResult(@RequestBody Map<String, Object> param) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        List<Map<String, Object>> mList = wklyRprgEtcService.getEtcResult(param);

        oData.setList(mList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 기타사항을 삭제한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    public ModelAndView deleteWklyEtc(@RequestBody Map<String, Object> param) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();

        wklyRprgEtcService.deleteWklyEtc(param);

        modelAndView.addObject(oResult);
        return modelAndView;
    }
}