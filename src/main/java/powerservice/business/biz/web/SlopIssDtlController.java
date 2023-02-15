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

import powerservice.business.biz.service.SlopIssDtlService;
import powerservice.business.biz.service.SlopIssHstrService;
import powerservice.core.bean.ActionList;
import powerservice.core.bean.ActionResult;
import powerservice.core.util.ParamUtils;

/**
 * 사업원장관리  -> 영업이슈정보
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정훈
 * @version 1.0
 * @date 2015/08/07
 * @프로그램ID <PS-UI-DS10>
 */
@Controller
@RequestMapping(value = "/biz/slop-iss-dtl-div")
public class SlopIssDtlController {

    @Resource
    private SlopIssDtlService slopIssDtlService;

    @Resource
    private SlopIssHstrService slopIssHstrService;

    /**
     * 사업원장관리  -> 영업이슈정보 정보를 검색한다.
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

        int nTotal = slopIssDtlService.getSlopIssDtlCount(pmParam);

        ParamUtils.addPagingParam(pmParam);

        List<Map<String, Object>> mList = slopIssDtlService.getSlopIssDtlList(pmParam);

        oData.setTotal(nTotal);

        oData.setList(mList);

        oResult.setData(oData);



        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 사업원장관리  -> 영업이슈정보를 등록/수정한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    public ModelAndView saveSlopIssDtl(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        String slop_iss_id = StringUtils.defaultString((String) pmParam.get("slop_iss_id"));

        ParamUtils.addSaveParam(pmParam);

        if ("".equals(slop_iss_id)) {

            slop_iss_id = slopIssDtlService.insertSlopIssDtl(pmParam);
            //이력내역 저장
            slopIssHstrService.insertSlopIssHstr(pmParam);
        } else {

            slopIssDtlService.updateSlopIssDtl(pmParam);
            //이력내역 저장
            slopIssHstrService.insertSlopIssHstr(pmParam);
        }

        oResult.setData(slopIssDtlService.getSlopIssDtl(slop_iss_id));

        modelAndView.addObject(oResult);

        return modelAndView;
    }

    /**
     * 사업원장관리  -> 영업이슈정보를 삭제한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    public ModelAndView deleteNobd(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        ParamUtils.addSaveParam(pmParam);
        int cnt = slopIssDtlService.deleteSlopIssDtl(pmParam);
        slopIssHstrService.deleteSlopIssHstr(pmParam);
        oResult.setData(cnt);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

}