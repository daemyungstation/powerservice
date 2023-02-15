/*
 * (@)# MoRecpDtlController.java
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

package powerservice.business.cns.web;

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

import powerservice.business.cns.service.MoRecpDtlService;
import powerservice.business.sys.service.FileService;
import powerservice.core.bean.ActionList;
import powerservice.core.bean.ActionResult;
import powerservice.core.bean.ActionResultType;
import powerservice.core.util.ParamUtils;

/*
 * MO수신 정보를 관리한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 차건호
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID MoRecpDtl
 */
@Controller
@RequestMapping(value = "/chnl/mo-recp")
public class MoRecpDtlController {

    @Resource
    private MoRecpDtlService moRecpDtlService;

    @Resource
    private FileService fileService;

    /*
     * MO수신 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public ModelAndView getMoRecpDtlList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        int nTotal = moRecpDtlService.getMoRecpDtlCount(pmParam);

        ParamUtils.addPagingParam(pmParam);
        List<Map<String, Object>> mList = moRecpDtlService.getMoRecpDtlList(pmParam);

        oData.setTotal(nTotal);
        oData.setList(mList);

        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /*
     * MO수신 정보를 삭제한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public ModelAndView deleteMoRecpDtl(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();

        String sSelectCheck = StringUtils.defaultString((String) pmParam.get("selectcheck"));
        String[] aSelectCheck = sSelectCheck.split(",");
        if (aSelectCheck.length < 1) {
            oResult.setResult(ActionResultType.ERROR);
            oResult.setErrMsg("MO수신 건을 선택하세요.");

            modelAndView.addObject(oResult);
            return modelAndView;
        }

        pmParam.put("selectcheck", aSelectCheck);
        moRecpDtlService.deleteMoRecpDtl(pmParam);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /*
     * MO수신 정보 상세를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/view")
    @ResponseBody
    public ModelAndView viewMoRecpDtl(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        Map<String, Object> oData = new HashMap<String, Object>();

        String sMoRecpId = StringUtils.defaultString((String) pmParam.get("mo_recp_id"));
        if ("".equals(sMoRecpId)) {
            oResult.setResult(ActionResultType.ERROR);
            oResult.setErrMsg("MO수신ID가 없습니다.");

            modelAndView.addObject(oResult);
            return modelAndView;
        }

        Map<String, Object> mSearchParam = ParamUtils.getCenterParam();
        mSearchParam.put("mo_recp_id", sMoRecpId);

        pmParam.put("rltn_item_id",pmParam.get("mo_recp_id"));

        Map<String, Object> mMoRecpDtl = moRecpDtlService.getMoRecpDtl(mSearchParam);
        List<Map<String, Object>> mFileList = fileService.getFileList(pmParam);

        oData.put("morecpdtl", mMoRecpDtl);
        oData.put("filelist", mFileList);

        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }



    /*
     * MO 상담 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/mo-cons-list")
    @ResponseBody
    public ModelAndView getMoConsList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        int nTotal = moRecpDtlService.getMoConsCount(pmParam);

        ParamUtils.addPagingParam(pmParam);
        List<Map<String, Object>> mList = moRecpDtlService.getMoConsList(pmParam);

        oData.setTotal(nTotal);
        oData.setList(mList);

        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

}