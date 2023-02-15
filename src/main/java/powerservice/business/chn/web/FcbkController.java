/*
 * (@)# FcbkController.java
 *
 * @author 유오성
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.chn.service.FcbkService;
import powerservice.business.sys.service.FileService;
import powerservice.core.bean.ActionList;
import powerservice.core.bean.ActionResult;
import powerservice.core.util.ParamUtils;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;

/**
 * 페이스북 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 유오성
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID Fcbk
 */
@Controller
@RequestMapping(value = "/chnl/fcbk")
public class FcbkController {

    @Resource
    private FcbkService fcbkService;

    @Resource
    private FileService fileService;

    public static final String APP_SECRET = "413dd9d17ca432e48fadb88972a63002";
    public static final String ACCESS_TOKEN = "CAAMs1rXADyEBADV1fvdPRZCuOCSnvADemZCLCLvZBzxY2T2eTXdE2DT3zWEcZAsDlZBmGZBMcEIsq6JmQBCxNZCEtdP3oFfs479njVWkvkWvPWGJ6d25T3hTjUK5dRsZCMrKOzV9vQ3icuCiudFahW9eC1ApjDnMmGbL8KPg3j18tSvzFPJ8CoQLr8d7wHddUFcZD";
    public static final String USER_ID = "682189195216632";

    /**
     * 페이스북 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public ModelAndView getFcbkList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        int nTotal = fcbkService.getFcbkCount(pmParam);

        ParamUtils.addPagingParam(pmParam);
        List<Map<String, Object>> mDataList = fcbkService.getFcbkList(pmParam);

        oData.setTotal(nTotal);
        oData.setList(mDataList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }


    /**
     * 페이스북정보(1건)를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/view")
    public ModelAndView getFcbk(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        Map<String, Object> mData = new HashMap<String, Object>();

        ParamUtils.addCenterParam(pmParam);

        String fcbk_id = (String) pmParam.get("fcbk_id");

        // 페이스북 상세정보 조회
        Map<String, Object> mList = fcbkService.getFcbk(fcbk_id);
        mData.put("fcbk", mList);

        oResult.setData(mData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 페이스북 댓글 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/cmmn/list")
    public ModelAndView getFcbkCmntList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        int nTotal = fcbkService.getFcbkCmmnCount(pmParam);

        ParamUtils.addPagingParam(pmParam);
        List<Map<String, Object>> mFcbkCmntList = fcbkService.getFcbkCmmnList(pmParam);

        oData.setTotal(nTotal);
        oData.setList(mFcbkCmntList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 페이스북 사진 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/phto/list")
    public ModelAndView getFcbkPhtoList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        int nTotal = fcbkService.getFcbkPhtoCount(pmParam);

        ParamUtils.addPagingParam(pmParam);
        List<Map<String, Object>> mFcbkPhtoList = fcbkService.getFcbkPhtoList(pmParam);

        oData.setTotal(nTotal);
        oData.setList(mFcbkPhtoList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }


    /**
     * 페이스북 댓글 정보를 등록/수정한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/cmmn/save")
    public ModelAndView saveFcbkCmmn(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        String sCmmnId = StringUtils.defaultString((String) pmParam.get("cmmn_id"));

        ParamUtils.addSaveParam(pmParam);

        String sFcbkId = (String) pmParam.get("fcbk_id");
        String sCmmnCntn = (String) pmParam.get("cmmn_cntn");

        FacebookClient oFcbkClient = new DefaultFacebookClient(ACCESS_TOKEN, APP_SECRET);
        FacebookType oPublishMessageResponse = oFcbkClient.publish(sFcbkId + "/comments", FacebookType.class, Parameter.with("message", sCmmnCntn));

        sCmmnId = oPublishMessageResponse.getId();
        pmParam.put("cmmn_id", sCmmnId);
        fcbkService.insertFcbkCmmn(pmParam);

        Map<String, Object> mParam = ParamUtils.getCenterParam();
        mParam.put("cmmn_id", sCmmnId);

        oResult.setData(fcbkService.getFcbkCmmn(mParam));

        modelAndView.addObject(oResult);
        return modelAndView;
    }

}