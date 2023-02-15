/*
 * (@)# DonInfController.java
 *
 * @author 김현경
 * @version 1.0
 * @date 2015/04/06
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.mta.service.DonInfService;
import powerservice.core.bean.ActionList;
import powerservice.core.bean.ActionResult;
import powerservice.core.util.ParamUtils;

/**
 * 도메인 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 김현경
 * @version 1.0
 * @date 2015/04/06
 * @프로그램ID DonInf
 */

@Controller
@RequestMapping(value = "/metasystem/domn-info")
public class DonInfController {

    @Resource
    private DonInfService donInfService;

    /**
     * 도메인 관리 트리 정보를 검색한다.(15.04.06)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/tree")
    public ModelAndView searchDonInfTreeView(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        pmParam.put("treefg", "Y");
        ParamUtils.addPagingParam(pmParam);
        ParamUtils.addCenterParam(pmParam);

        List<Map<String, Object>> mList = donInfService.getDonInfTreeList(pmParam);

        oData.setList(mList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 도메인 관리 정보를 검색한다. (15.04.06)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public ModelAndView getDonInfList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        ParamUtils.addPagingParam(pmParam);
        ParamUtils.addCenterParam(pmParam);

        int nTotal = donInfService.getDonInfCount(pmParam);
        List<Map<String, Object>> mList = donInfService.getDonInfList(pmParam);

        oData.setTotal(nTotal);
        oData.setList(mList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 도메인 관리 정보를 등록/수정한다. (15.04.07)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    public ModelAndView saveDonInf(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        ParamUtils.addSaveParam(pmParam);

        Map<String, Object> searchParam = new HashMap<String, Object>();
        searchParam.put("domn_cd", pmParam.get("domn_cd"));
        searchParam.put("hgrn_domn_cd", pmParam.get("hgrn_domn_cd"));
        searchParam.put("centercd", pmParam.get("centercd"));

        donInfService.mergeDonInf(pmParam);

        oResult.setData(donInfService.getDonInf(searchParam));

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 도메인 관리 순서 중복 건수를 검색한다. (15.04.07)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/duplicate-count")
    public ModelAndView getDonInfDuplicateCount(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        ParamUtils.addCenterParam(pmParam);

        int nTotal = donInfService.getDonInfDuplicateCount(pmParam);

        oData.setTotal(nTotal);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 도메인 관리 정보를 삭제한다. (15.04.07)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public ModelAndView deleteDonInf(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        pmParam.put("treefg", "Y");

        int nTotal = donInfService.getDonInfCount(pmParam);	// 해당 도메인의 하위 도메인 수
        int referCount = 1;									// 칼럼 상세정보에서 사용중인 도메인 수

        if(nTotal == 0){
            referCount = donInfService.getRefercolmCount(pmParam);
            if(referCount == 0){
                donInfService.deleteDonInf(pmParam);
            }
        }

        oData.setTotal(referCount);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 도메인 순서 최대값을 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/max-order")
    public ModelAndView getDonInfMaxSequence(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        ParamUtils.addCenterParam(pmParam);

        oResult.setData(donInfService.getDonInfMaxSequence(pmParam));

        modelAndView.addObject(oResult);
        return modelAndView;
    }
}