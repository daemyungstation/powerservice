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

import powerservice.business.biz.service.SlopIssHstrService;
import powerservice.core.bean.ActionList;
import powerservice.core.bean.ActionResult;
import powerservice.core.bean.ActionResultType;
import powerservice.core.util.ParamUtils;
import powerservice.web.security.TokenManager;

/**
 * 사업원장관리  -> 영업 이슈정보 이력
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정훈
 * @version 1.0
 * @date 2015/08/08
 * @프로그램ID <PS-UI-DS11>
 */
@Controller
@RequestMapping(value = "/biz/slop-iss-hstr-div")
public class SlopIssHstrController {

    @Resource
    private SlopIssHstrService slopIssHstrService;

    /**
     * 영업 이슈정보 이력을 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public ModelAndView getSlopIssDtlList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        int nTotal = slopIssHstrService.getSlopIssHstrCount(pmParam);
        ParamUtils.addPagingParam(pmParam);
        List<Map<String, Object>> mList = slopIssHstrService.getSlopIssHstrList(pmParam);
        oData.setTotal(nTotal);
        oData.setList(mList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

}