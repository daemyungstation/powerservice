/*
 * (@)# WklyBswrDtlsCdController.java
 *
 * @author 박상수
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

package powerservice.business.wkly.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.wkly.service.WklyBswrDtlsCdService;
import powerservice.core.bean.ActionList;
import powerservice.core.bean.ActionResult;
import powerservice.core.bean.ActionResultType;
import powerservice.core.util.ParamUtils;

/**
 * 주간보고 관리를 한다.
 *
 * Copyright (c) 2013 Company Inwoo Tech Inc.
 *
 * @author 박상수
 * @version 1.0
 * @date 2015/01/05
 * @프로그램ID WklyBswrDtlsCdController
 */
@Controller
public class WklyBswrDtlsCdController {

    @Resource
    private WklyBswrDtlsCdService wklyBswrDtlsCdService;

    /**
     * 코드 정보를 검색한다.
     *
     * @param sCodeSetList String[]
     * @throws Exception
     */
    @RequestMapping(value = "/wklycode/list")
    public ModelAndView searchCode(@RequestBody String[] sCodeSetList) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();
        List<Map<String, Object>> mDataList = wklyBswrDtlsCdService.getCodeList(sCodeSetList);
        oResult.setData(mDataList);
        modelAndView.addObject(oResult);
        return modelAndView;
    }

    @RequestMapping(value = {"/system/wklycode/drop-down-list"})
    public ModelAndView getUserListForDropDownList(@RequestBody Map<String, Object> param) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();
        ActionList data = new ActionList();

        Map<String, Object> searchParam = ParamUtils.convertDropDownParam(param);

        int total = wklyBswrDtlsCdService.getCodeCount(searchParam);
        List<Map<String, Object>> list = wklyBswrDtlsCdService.getCodeList(searchParam);

        data.setTotal(total);
        data.setList(list);
        oResult.setData(data);

        modelAndView.addObject(oResult);
        return modelAndView;
    }


    /**
     * 코드 정보 목록을 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @throws Exception
     */
    @RequestMapping(value = "/system/wklycode/list")
    public ModelAndView searchCode(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        int nTotal = wklyBswrDtlsCdService.getCodeCount(pmParam);

        ParamUtils.addPagingParam(pmParam);
        List<Map<String, Object>> mDataList = wklyBswrDtlsCdService.getCodeList(pmParam);

        oData.setTotal(nTotal);
        oData.setList(mDataList);

        oResult.setData(oData);
        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 코드 순서 최대값을 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @throws Exception
     */
    @RequestMapping(value = "/system/wklycode/max-sequence")
    public ModelAndView getCodeMaxSequence(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();

        ParamUtils.addCenterParam(pmParam);

        oResult.setData(wklyBswrDtlsCdService.getCodeMaxSequence(pmParam));
        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 코드 순서 중복 건수를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @throws Exception
     */
    @RequestMapping(value = "/system/wklycode/duplicate-count")
    public ModelAndView getCodeDuplicateCount(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        ParamUtils.addCenterParam(pmParam);
        int nTotal = wklyBswrDtlsCdService.getCodeDuplicateCount(pmParam);

        oData.setTotal(nTotal);

        oResult.setData(oData);
        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 코드 정보를 등록/수정한다.
     *
     * @param pmParam Map<String, Object>
     * @return ActionResult
     * @throws Exception
     */
    @RequestMapping(value = "/system/wklycode/save")
    public ModelAndView save(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();

        Object oPageIndx = pmParam.get("page_indx");
        String sCdSetCd = StringUtils.defaultString((String) pmParam.get("wkly_bswr_typ_cd"));
        String sCd = StringUtils.defaultString((String) pmParam.get("wkly_bswr_dtls_cd"));

        if ("".equals(sCdSetCd)) {
            oResult.setResult(ActionResultType.ERROR);
            oResult.setErrMsg("코드셋을 먼저 등록하세요.");
            modelAndView.addObject(oResult);
            return modelAndView;
        }

        ParamUtils.addSaveParam(pmParam);

        Map<String, Object> mSearchParam = ParamUtils.getCenterParam();
        mSearchParam.put("wkly_bswr_typ_cd", sCdSetCd);
        mSearchParam.put("wkly_bswr_dtls_cd", sCd);

        if (oPageIndx == null) { // 등록
            int nCount = wklyBswrDtlsCdService.getCodeCount(mSearchParam);
            if (nCount > 0) {
                oResult.setResult(ActionResultType.ERROR);
                oResult.setErrMsg("동일한 코드가 존재합니다.");
                modelAndView.addObject(oResult);
                return modelAndView;
            }

            wklyBswrDtlsCdService.insertCode(pmParam);
        } else { // 수정
            wklyBswrDtlsCdService.updateCode(pmParam);
        }

        oResult.setData(wklyBswrDtlsCdService.getCode(mSearchParam));
        modelAndView.addObject(oResult);
        return modelAndView;
    }
}