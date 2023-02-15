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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.biz.service.ChprBasiService;
import powerservice.core.bean.ActionList;
import powerservice.core.bean.ActionResult;
import powerservice.core.bean.ActionResultType;
import powerservice.core.util.ParamUtils;
import powerservice.web.security.TokenManager;

/**
 * CHPR 관리를 한다.
 * 담당자 관리
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 정훈
 * @version 1.0
 * @date 2015/08/01
 * @프로그램ID <PS-UI-DS02>
 */
@Controller
@RequestMapping(value = "/biz/chpr-basi")
public class ChprBasiController {

    @Resource
    private ChprBasiService chprBasiService;

    /**
     * 담당자 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
   @RequestMapping(value = "/list")
    public ModelAndView getChprBasiList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        int nTotal = chprBasiService.getChprBasiCount(pmParam);

        ParamUtils.addPagingParam(pmParam);
        List<Map<String, Object>> mList = chprBasiService.getChprBasiList(pmParam);

        oData.setTotal(nTotal);
        oData.setList(mList);
        oResult.setData(oData);


        modelAndView.addObject(oResult);
        return modelAndView;
    }



    /**
     * 담당자 정보를 등록/수정한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    public ModelAndView saveChprBasi(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();



        String chpr_id = StringUtils.defaultString((String) pmParam.get("chpr_id"));

        ParamUtils.addSaveParam(pmParam);

        if ("".equals(chpr_id)) {
            chpr_id = chprBasiService.insertChprBasi(pmParam);
        } else {
            chprBasiService.updateChprBasi(pmParam);
        }

        oResult.setData(chprBasiService.getChprBasi(chpr_id));

        modelAndView.addObject(oResult);
        return modelAndView;
    }


    /**
     * 담당자 정보(1건)를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/view")
    @ResponseBody
    public ModelAndView getChprBasi(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();

        String sChprId = StringUtils.defaultString((String)pmParam.get("chpr_id"));
        oResult.setData(chprBasiService.getChprBasi(sChprId));

        modelAndView.addObject(oResult);

        return modelAndView;
    }

    /**
     * 담당자 정보(팝업)를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
   @RequestMapping(value = "/popupList")
    public ModelAndView getChprBasiPopupList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        int nTotal = chprBasiService.getChprBasiPopupCount(pmParam);

        ParamUtils.addPagingParam(pmParam);
        List<Map<String, Object>> mList = chprBasiService.getChprBasiPopupList(pmParam);

        oData.setTotal(nTotal);
        oData.setList(mList);
        oResult.setData(oData);


        modelAndView.addObject(oResult);
        return modelAndView;
    }



}