/*
 * (@)# TblChkController.java
 *
 * @author 김현경
 * @version 1.0
 * @date 2015/07/22
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

package powerservice.business.mta.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.mta.service.TblChkService;
import powerservice.core.bean.ActionList;
import powerservice.core.bean.ActionResult;
import powerservice.core.util.ParamUtils;

/**
 * 컬럼 기본값 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 김현경
 * @version 1.0
 * @date 2015/07/22
 * @프로그램ID TblChk
 */

@Controller
@RequestMapping(value = "/metasystem/tbl-check")
public class TblChkController {

    @Resource
    private TblChkService tblChkService;

    /**
     * 테이블 체크를 검색한다. (15.07.22)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/tbl-list")
    public ModelAndView getTblChkList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        int nTotal = tblChkService.getTblChkCount(pmParam);

        ParamUtils.addPagingParam(pmParam);
        List<Map<String, Object>> mList = tblChkService.getTblChkList(pmParam);

        oData.setTotal(nTotal);
        oData.setList(mList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 테이블 상세 체크를 검색한다. (15.08.05)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/tbl-detail-list")
    public ModelAndView getTblDetailList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        int nTotal = tblChkService.getTblDtailCount(pmParam);

        ParamUtils.addPagingParam(pmParam);
        List<Map<String, Object>> mList = tblChkService.getTblDetailList(pmParam);

        oData.setTotal(nTotal);
        oData.setList(mList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 컬럼 체크를 검색한다. (15.07.22)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/colm-list")
    public ModelAndView getColmChkList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        int nTotal = tblChkService.getColmChkCount(pmParam);

        ParamUtils.addPagingParam(pmParam);
        List<Map<String, Object>> mList = tblChkService.getColmChkList(pmParam);

        oData.setTotal(nTotal);
        oData.setList(mList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 컬럼 상세 체크를 검색한다. (15.07.22)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/colm-detail-list")
    public ModelAndView getColmDetailList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        int nTotal = tblChkService.getColmDtailCount(pmParam);

        ParamUtils.addPagingParam(pmParam);
        List<Map<String, Object>> mList = tblChkService.getColmDetailList(pmParam);

        oData.setTotal(nTotal);
        oData.setList(mList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 도메인 체크를 검색한다. (15.08.06)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/domain-list")
    public ModelAndView getDonChkList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        int nTotal = tblChkService.getDonChkCount(pmParam);

        ParamUtils.addPagingParam(pmParam);
        List<Map<String, Object>> mList = tblChkService.getDonChkList(pmParam);

        oData.setTotal(nTotal);
        oData.setList(mList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }
}