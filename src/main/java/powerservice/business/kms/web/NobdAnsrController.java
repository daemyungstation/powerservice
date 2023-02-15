/*
 * (@)# NobdAnsrController.java
 *
 * @author 차건호
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

package powerservice.business.kms.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.kms.service.NobdAnsrService;
import powerservice.core.bean.ActionList;
import powerservice.core.bean.ActionResult;
import powerservice.core.util.ParamUtils;

/**
 * 게시판 댓글 관리를 한다
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 차건호
 * @version 1.0
 * @date 2015/04/20
 * @프로그램ID NobdAnsr
 */

@Controller
@RequestMapping(value = "/knowledge/nobd-ansr")
public class NobdAnsrController {

    @Resource
    private NobdAnsrService nobdAnsrService;

    /**
     * 게시판 댓글 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public ModelAndView getNobdAnsrList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        int nTotal = nobdAnsrService.getNobdAnsrCount(pmParam);

        ParamUtils.addPagingParam(pmParam);
        List<Map<String, Object>> mNobdAnsrList = nobdAnsrService.getNobdAnsrList(pmParam);

        oData.setTotal(nTotal);
        oData.setList(mNobdAnsrList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 게시판 댓글 정보를 등록/수정한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public ModelAndView saveNobdAnsr(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();
        String sNobdAnsrId = StringUtils.defaultString((String) pmParam.get("nobd_ansr_id"));

        ParamUtils.addSaveParam(pmParam);

        if ("".equals(sNobdAnsrId)) {
            sNobdAnsrId = nobdAnsrService.insertNobdAnsr(pmParam);
        } else {
            nobdAnsrService.updateNobdAnsr(pmParam);
        }

        Map<String, Object> mParam = ParamUtils.getCenterParam();
        mParam.put("nobd_ansr_id", sNobdAnsrId);

        oResult.setData(nobdAnsrService.getNobdAnsr(mParam));

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 게시판 댓글 정보를 삭제한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public ModelAndView deleteNobdAnsr(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();

        nobdAnsrService.deleteNobdAnsr(pmParam);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

}