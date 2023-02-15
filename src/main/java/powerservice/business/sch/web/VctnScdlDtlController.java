/*
 * (@)# WrkScdlDtlController.java
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

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import powerservice.business.sch.service.VctnScdlDtlService;
import powerservice.core.bean.ActionList;
import powerservice.core.bean.ActionResult;
import powerservice.core.util.ParamUtils;

/**
 * 휴가일정 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID VctnScdlDtl
 */
@Controller
@RequestMapping(value = "/scdl/vctn-scdl-dtl")
public class VctnScdlDtlController {

    @Resource
    private VctnScdlDtlService vctnScdlDtlService;

    /**
     * 휴가일정 정보를 검색한다.
     *
     * @param pmParam Map<String, String>
     * @return ActionResult
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public ModelAndView getVctnScdlDtlList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        ParamUtils.addPagingParam(pmParam);
        ParamUtils.addCenterParam(pmParam);
        List<Map<String, Object>> mList = vctnScdlDtlService.getVctnScdlDtlList(pmParam);

        oData.setTotal(mList.size());
        oData.setList(mList);

        oResult.setData(oData);
        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 휴가일정 정보를 등록/수정한다.
     *
     * @param mModelList List<Map<String, Object>>
     * @return ActionResult
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    public ModelAndView saveVctnScdlDtl(@RequestBody List<Map<String, Object>> mModelList) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        if (mModelList != null && mModelList.size() > 0) {
            Map<String, Object> mParam = new HashMap<String, Object>();
            ParamUtils.addSaveParam(mParam);
            vctnScdlDtlService.saveVctnScdlDtl(mModelList, mParam);
        }

        modelAndView.addObject(oResult);
        return modelAndView;
    }

}