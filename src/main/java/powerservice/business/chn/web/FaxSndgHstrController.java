/*
 * (@)# FaxSndgHstrController.java
 *
 * @author 김은지
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

package powerservice.business.chn.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.chn.service.FaxSndgHstrService;
import powerservice.core.bean.ActionList;
import powerservice.core.bean.ActionResult;
import powerservice.core.util.ParamUtils;

/**
 * FAX 전송 이력 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 김은지
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID FaxSndgHstr
 */
@Controller
@RequestMapping(value = "/chnl/fax-sndg-hstr")
public class FaxSndgHstrController {

    @Resource
    private FaxSndgHstrService faxSndgHstrService;

    /**
     * SMS 전송 이력 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public ModelAndView readSmsSendAllExcel(@RequestBody Map<String, Object> pmParam) throws Exception{
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        ParamUtils.addCenterParam(pmParam);
        ParamUtils.addPagingParam(pmParam);
        List<Map<String, Object>> mdataList = faxSndgHstrService.getFaxSndgHstrList(pmParam);

        oData.setList(mdataList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }
}