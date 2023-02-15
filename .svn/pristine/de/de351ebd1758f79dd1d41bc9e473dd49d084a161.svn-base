/*
 * (@)# CscoBasiController.java
 *
 * @author 정훈
 * @version 1.0
 * @date 2015/07/27
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

package powerservice.business.biz.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.biz.service.CompBasiService;
import powerservice.core.bean.ActionList;
import powerservice.core.bean.ActionResult;
import powerservice.core.bean.ActionResultType;
import powerservice.core.util.ParamUtils;
import powerservice.web.security.TokenManager;

/**
 * 고객사/협력사관리를 한다.
 * 고객사/협력사 관리
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정훈
 * @version 1.0
 * @date 2015/07/27
 * @프로그램ID <PS-UI-DS01>
 */
@Controller
@RequestMapping(value = "/biz/comp-basi")
public class CompBasiController {

    @Resource
    private CompBasiService compBasiService;

    /**
     * 고객사/협력사 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public ModelAndView getCompBasiList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        int nTotal = compBasiService.getCompBasiCount(pmParam);

        ParamUtils.addPagingParam(pmParam);
        List<Map<String, Object>> mList = compBasiService.getCompBasiList(pmParam);

        oData.setList(mList);

        oData.setTotal(nTotal);
        oResult.setData(oData);
        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 고객사/협력사를 등록/수정한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    public ModelAndView saveCompBasi(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        String comp_id = StringUtils.defaultString((String) pmParam.get("comp_id"));

        ParamUtils.addSaveParam(pmParam);

        if ("".equals(comp_id)) {
            comp_id = compBasiService.insertCompBasi(pmParam);
        } else {
            compBasiService.updateCompBasi(pmParam);
        }

        oResult.setData(compBasiService.getCompBasi(comp_id));

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 고객사/협력사 Popup 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/popupList")
    public ModelAndView getCompBasiPopupList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        int nTotal = compBasiService.getCompBasiPopupCount(pmParam);

        ParamUtils.addPagingParam(pmParam);
        List<Map<String, Object>> mList = compBasiService.getCompBasiPopupList(pmParam);

        oData.setList(mList);

        oData.setTotal(nTotal);
        oResult.setData(oData);
        modelAndView.addObject(oResult);
        return modelAndView;
    }



}