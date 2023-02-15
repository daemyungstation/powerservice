/*
 * (@)# CoBaVlController.java
 *
 * @author 김현경
 * @version 1.0
 * @date 2015/04/15
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

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.mta.service.CoBaVlService;
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
 * @date 2015/04/15
 * @프로그램ID CoBaVl
 */

@Controller
@RequestMapping(value = "/metasystem/colm-basi-vl")
public class CoBaVlController {

    @Resource
    private CoBaVlService coBaVlService;

    /**
     * 컬럼 기본 관리를 검색한다. (15.04.15)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public ModelAndView getCoBaVlList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        int nTotal = coBaVlService.getCoBaVlCount(pmParam);

        ParamUtils.addPagingParam(pmParam);
        List<Map<String, Object>> mList = coBaVlService.getCoBaVlList(pmParam);

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
    public ModelAndView saveCoBaVl(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        String colm_id = StringUtils.defaultString((String) pmParam.get("colm_basi_vl_id"));

        ParamUtils.addSaveParam(pmParam);

        if("".equals(colm_id)){
            // id 값이 없으면 등록
            colm_id = coBaVlService.insertCoBaVl(pmParam);
        } else {
            // id 값이 있으면 수정
            coBaVlService.updateCoBaVl(pmParam);
        }

        oResult.setData(coBaVlService.getCoBaVl(colm_id));

        modelAndView.addObject(oResult);
        return modelAndView;
    }
}