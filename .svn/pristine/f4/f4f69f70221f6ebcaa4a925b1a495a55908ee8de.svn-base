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

import powerservice.business.biz.service.SlopActvRprgService;
import powerservice.core.bean.ActionList;
import powerservice.core.bean.ActionResult;
import powerservice.core.util.ParamUtils;

/**
 * 사업원장관리  -> 영업활동정보
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정훈
 * @version 1.0
 * @date 2015/08/07
 * @프로그램ID <PS-UI-DS09>
 */
@Controller
@RequestMapping(value = "/biz/slop-actv-rprg-div")
public class SlopActvRprgController {

    @Resource
    private SlopActvRprgService slopActvRprgService;

    /**
     * 사업원장관리  -> 영업활동정보
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public ModelAndView getSlopActvRprgList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        int nTotal = slopActvRprgService.getSlopActvRprgCount(pmParam);

        ParamUtils.addPagingParam(pmParam);

        List<Map<String, Object>> mList = slopActvRprgService.getSlopActvRprgList(pmParam);

        oData.setTotal(nTotal);
        oData.setList(mList);
        oResult.setData(oData);


         modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 사업원장관리  -> 영업활동정보를 등록/수정한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    public ModelAndView saveSlopActvRprg(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        String slop_actv_rprg_id = StringUtils.defaultString((String) pmParam.get("slop_actv_rprg_id"));

        ParamUtils.addSaveParam(pmParam);

        if ("".equals(slop_actv_rprg_id)) {

            slop_actv_rprg_id = slopActvRprgService.insertSlopActvRprg(pmParam);
        } else {

            slopActvRprgService.updateSlopActvRprg(pmParam);
        }

        oResult.setData(slopActvRprgService.getSlopActvRprg(slop_actv_rprg_id));

        modelAndView.addObject(oResult);

        return modelAndView;
    }

    /**
     * 사업원장관리  -> 영업활동정보를 삭제한다.
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
        int cnt = slopActvRprgService.deleteSlopActvRprg(pmParam);
        oResult.setData(cnt);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

}