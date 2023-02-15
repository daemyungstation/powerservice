/*
 * (@)# GdsController.java
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

package powerservice.business.sys.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.sys.service.GdsService;
import powerservice.core.bean.ActionList;
import powerservice.core.bean.ActionResult;
import powerservice.core.bean.ActionResultType;
import powerservice.core.util.ParamUtils;

/**
 * 제품 정보 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID Gds
 */
@Controller
@RequestMapping(value = "/syst/gds")
public class GdsController {

    @Resource
    private GdsService gdsService;

    /**
     * 제품 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public ModelAndView get(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        int nTotal = gdsService.getGdsCount(pmParam);

        ParamUtils.addPagingParam(pmParam);
        List<Map<String, Object>> mList = gdsService.getGdsList(pmParam);

        oData.setTotal(nTotal);
        oData.setList(mList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 제품 정보를 등록/수정한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    public ModelAndView save(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        Object oPageIndx = pmParam.get("page_indx");
        String sGdsCd = StringUtils.defaultString((String) pmParam.get("gds_cd"));

        ParamUtils.addSaveParam(pmParam);

        Map<String, Object> mSearchParam = ParamUtils.getCenterParam();
        mSearchParam.put("gds_cd", sGdsCd);

        if (oPageIndx == null) {
            // 중복 확인
            int nCount = gdsService.getGdsCount(mSearchParam);
            if (nCount > 0 ) {
                oResult.setResult(ActionResultType.ERROR);
                oResult.setErrMsg("동일한 제품코드가 존재합니다.");

                modelAndView.addObject(oResult);
                return modelAndView;
            }

            gdsService.insertGds(pmParam);
        } else {
            gdsService.updateGds(pmParam);
        }

        oResult.setData(gdsService.getGds(mSearchParam));

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 제품 정보를 삭제한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    public ModelAndView delete(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        String sGdsCd = StringUtils.defaultString((String) pmParam.get("gds_cd"));
        if ("".equals(sGdsCd)) {
            oResult.setResult(ActionResultType.ERROR);
            oResult.setErrMsg("제품코드가 없습니다.");

            modelAndView.addObject(oResult);
            return modelAndView;
        }

        gdsService.deleteGds(pmParam);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

}