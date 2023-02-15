/*
 * (@)# ScdlDtlController.java
 *
 * @author 배윤정
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.bean.UserSession;
import powerservice.business.sch.service.ScdlDtlService;
import powerservice.core.bean.ActionResult;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;

/**
 * 일정내역 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID ScdlDtl
 */
@Controller
@RequestMapping(value = "/scdl/scdl-dtl")
public class ScdlDtlController {

    @Resource
    private ScdlDtlService scdlDtlService;

    /**
     * 일정내역 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public ModelAndView getScdlDtlList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();

        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
        pmParam.put("cntr_cd", oLoginUser.getCentercd());
        pmParam.put("user_id", oLoginUser.getUserid());

        ParamUtils.addFilterParam(pmParam);

        List<Map<String, Object>> mList = null;

        if (pmParam.get("filters") != null) {
            SimpleDateFormat oDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            oDateFormat.setTimeZone(TimeZone.getTimeZone("KR"));

            String sSttDt = (String) pmParam.get("start");
            String sEndDt = (String) pmParam.get("end");
            Date dSttDt = oDateFormat.parse(sSttDt);
            Date dEndDt = oDateFormat.parse(sEndDt);

            oDateFormat = new SimpleDateFormat("yyyyMMdd");
            pmParam.put("stt_dt", oDateFormat.format(dSttDt));
            pmParam.put("end_dt", oDateFormat.format(dEndDt));

            mList = scdlDtlService.getScdlDtlList(pmParam);
        }

        oResult.setData(mList);
        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 휴무일 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @throws Exception
     */
    @RequestMapping(value = "/offday-list")
    public ModelAndView getScdlDtlOffdayList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();

        ParamUtils.addCenterParam(pmParam);

        // 검색년월로 시작일, 종료일 변수 셋팅
        String sSrchYrMn = StringUtils.defaultString((String) pmParam.get("srch_yrmn"));
        pmParam.put("stt_dt", sSrchYrMn + "01");
        pmParam.put("end_dt", sSrchYrMn + "31");

        // 휴무일만 조회하도록 filter 변수 셋팅
        Map<String, String> mFilter = new HashMap<String, String>();
        mFilter.put("value", "O");
        List<Map<String, String>> mFilterList = new ArrayList<Map<String, String>>();
        mFilterList.add(mFilter);
        pmParam.put("filters", mFilterList);

        oResult.setData(scdlDtlService.getScdlDtlList(pmParam));
        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 일정내역 정보를 등록/수정한다.
     *
     * @param pmModelList List<Map<String, Object>>
     * @return ActionResult
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    public ModelAndView saveScdlDtl(@RequestBody List<Map<String, Object>> pmModelList) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();

        if (pmModelList != null && pmModelList.size() > 0) {
            Map<String, Object> mParam = new HashMap<String, Object>();
            ParamUtils.addSaveParam(mParam);
            scdlDtlService.saveScdlDtl(pmModelList, mParam);
        }

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 일정내역 정보를 삭제한다.
     *
     * @param pmModelList List<Map<String, Object>>
     * @return ActionResult
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    public ModelAndView deleteScdlDtl(@RequestBody List<Map<String, Object>> pmModelList) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();

        if (pmModelList != null && pmModelList.size() > 0) {
            scdlDtlService.deleteScdlDtl(pmModelList);
        }

        modelAndView.addObject(oResult);
        return modelAndView;
    }

}