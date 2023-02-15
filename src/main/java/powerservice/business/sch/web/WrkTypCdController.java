/*
 * (@)# WrkTypCdController.java
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

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.sch.service.WrkScdlDtlService;
import powerservice.business.sch.service.WrkTypCdService;
import powerservice.core.bean.ActionList;
import powerservice.core.bean.ActionResult;
import powerservice.core.bean.ActionResultType;
import powerservice.core.util.ParamUtils;

/**
 * 근무유형 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID WrkTypCd
 */
@Controller
@RequestMapping(value = "/scdl/wrk-typ-cd")
public class WrkTypCdController {

    @Resource
    private WrkTypCdService wrkTypCdService;

    @Resource
    private WrkScdlDtlService wrkScdlDtlService;

    /**
     * 근무유형 정보의 검색 수를 반환한다.
     *
     * @param pmParam Map<String, String>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public ModelAndView searchWrkTypCdList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        ParamUtils.addCenterParam(pmParam);
        int nTotal = wrkTypCdService.getWrkTypCdCount(pmParam);

        ParamUtils.addPagingParam(pmParam);
        List<Map<String, Object>> mList = wrkTypCdService.getWrkTypCdList(pmParam);

        oData.setTotal(nTotal);
        oData.setList(mList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 근무유형 정보를 등록/수정한다.
     *
     * @param pmParam Map<String, String>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    public ModelAndView save(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        Object oPageIndx = pmParam.get("page_indx");
        String wWrkTypCd = StringUtils.defaultString((String) pmParam.get("wrk_typ_cd"));

        // 근무유형코드 대문자 변경
        wWrkTypCd = wWrkTypCd.toUpperCase();

        ParamUtils.addSaveParam(pmParam);

        Map<String, Object> mSearchParam = ParamUtils.getCenterParam();
        mSearchParam.put("wrk_typ_cd", wWrkTypCd);

        if (oPageIndx == null) { // 등록
            // 중복 확인
            int nCount = wrkTypCdService.getWrkTypCdCount(mSearchParam);
            if (nCount > 0) {
                oResult.setResult(ActionResultType.ERROR);
                oResult.setErrMsg("중복된 유형코드입니다.\n다른 코드를 입력하세요.");

                modelAndView.addObject(oResult);
                return modelAndView;
            }

            pmParam.put("wrk_typ_cd", wWrkTypCd);
            wrkTypCdService.insertWrkTypCd(pmParam);
        } else { // 수정
            wrkTypCdService.updateWrkTypCd(pmParam);
        }

        oResult.setData(wrkTypCdService.getWrkTypCd(mSearchParam));

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 근무유형 정보를 삭제한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    public ModelAndView delete(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        String wWrkTypCd = StringUtils.defaultString((String) pmParam.get("wrk_typ_cd"));

        // 근무유형 스케줄 배정여부 확인

        Map<String, Object> mSearchParam = ParamUtils.getCenterParam();
        mSearchParam.put("wrk_typ_cd", wWrkTypCd);

        int nCount = wrkScdlDtlService.getWrkScdlDtlCount(mSearchParam);
        if (nCount > 0) {
            oResult.setResult(ActionResultType.ERROR);
            oResult.setErrMsg("이미 스케줄이 배정된 근무유형입니다.\n삭제할 수 없습니다.");

            modelAndView.addObject(oResult);
            return modelAndView;
        }

        wrkTypCdService.deleteWrkTypCd(pmParam);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

}