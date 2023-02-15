/*
 * (@)# TblDtlController.java
 *
 * @author 김현경
 * @version 1.0
 * @date 2015/04/27
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.mta.service.TblDtlService;
import powerservice.business.mta.service.CoBaVlService;
import powerservice.business.mta.service.TrmDtlService;
import powerservice.core.bean.ActionList;
import powerservice.core.bean.ActionResult;
import powerservice.core.bean.ActionResultType;
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
@RequestMapping(value = "/metasystem/tbl-dtl")
public class TblDtlController {

    @Resource
    private TblDtlService tblDtlService;

    @Resource
    private CoBaVlService coBaVlService;

    @Resource
    private TrmDtlService trmDtlService;

    /**
     * 테이블 관리(테이블 상세) 정보를 검색한다. (15.04.27)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/tablelist")
    public ModelAndView getTblDtl(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        ParamUtils.addPagingParam(pmParam);
        ParamUtils.addCenterParam(pmParam);

        int nTotal = tblDtlService.getTblDtlCount(pmParam);
        List<Map<String, Object>> mList = tblDtlService.getTblDtlList(pmParam);

        oData.setTotal(nTotal);
        oData.setList(mList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 테이블 관리(테이블 상세) 정보를 등록/수정한다. (15.04.28)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/tablesave")
    public ModelAndView saveTblDtl(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        Object PageIndx = pmParam.get("page_indx");

        String enlt_nm = StringUtils.defaultString((String) pmParam.get("tbl_enlt_nm")); // 테이블 영문명

        ParamUtils.addSaveParam(pmParam);

        if (PageIndx == null) {
            int nTotal = tblDtlService.getTblDtlCount(pmParam);

            if (nTotal > 0) {
                oResult.setResult(ActionResultType.ERROR);
                oResult.setErrMsg("동일한 테이블이 존재합니다.");

                modelAndView.addObject(oResult);
                return modelAndView;
            }

            enlt_nm = tblDtlService.insertTblDtl(pmParam);
        } else {
            tblDtlService.updateTblDtl(pmParam);
        }

        oResult.setData(tblDtlService.getTblDtl(enlt_nm));

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 테이블 관리(테이블 상세) 정보를 삭제한다. (15.04.28)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/tabledelete")
    @ResponseBody
    public ModelAndView deleteTrmDtl(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        int nTotal = tblDtlService.getTclDtlCount(pmParam);

        if(nTotal == 0){
            tblDtlService.deleteTblDtl(pmParam);
        }

        oData.setTotal(nTotal);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 테이블 관리(칼럼 상세) 정보를 검색한다. (15.04.27)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/colmlist")
    public ModelAndView getTclDtl(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        ParamUtils.addPagingParam(pmParam);
        ParamUtils.addCenterParam(pmParam);

        int nTotal = tblDtlService.getTclDtlCount(pmParam);
        List<Map<String, Object>> mList = tblDtlService.getTclDtlList(pmParam);

        oData.setTotal(nTotal);
        oData.setList(mList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 테이블 관리(칼럼 상세) 정보를 등록/수정한다. (15.05.06)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/colmsave")
    public ModelAndView saveTclDtl(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        Object PageIndx = pmParam.get("page_indx");

        ParamUtils.addSaveParam(pmParam);
        ParamUtils.addCenterParam(pmParam);

        if (PageIndx == null) {

            int nTotal = tblDtlService.getTclDtlCount(pmParam);

            if (nTotal > 0) {
                oResult.setResult(ActionResultType.ERROR);
                oResult.setErrMsg("동일한 테이블이 존재합니다.");

                modelAndView.addObject(oResult);
                return modelAndView;
            }
            tblDtlService.insertTclDtl(pmParam);
        } else {
            tblDtlService.updateTclDtl(pmParam);
        }

        oResult.setData(tblDtlService.getTclDtl(pmParam));

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 테이블 관리 칼럼 상세정보 순서 최대값을 검색한다. (15.05.07)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/max-order")
    @ResponseBody
    public ModelAndView getMaxOrder(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        ParamUtils.addCenterParam(pmParam);

        oResult.setData(tblDtlService.getTclDtlMaxOrder(pmParam));

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 테이블 관리 칼럼 상세정보 중복체크를 한다. (15.05.07)
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value  ="/check")
    @ResponseBody
    public ModelAndView getDuplicateCount(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        ParamUtils.addCenterParam(pmParam);

        int Colm = tblDtlService.checkTclDtl(pmParam);				// 용어명 중복이면 1, 아니면 0
        int Ord = tblDtlService.getTclDtlDuplicateCount(pmParam);	// 순서   중복이면 1, 아니면 0
        int nTotal = 7;

        if(Colm == 1 && Ord == 1){			// 용어명 중복,     순서 중복
            nTotal = 1;
        }else if(Colm == 1 && Ord == 0){	// 용어명 중복,     순서 중복아님
            nTotal = 2;
        }else if(Colm == 0 && Ord == 1){	// 용어명 중복아님, 순서 중복
            nTotal = 3;
        }else{								// 용어명 중복아님, 순서 중복아님
            nTotal = 4;
        }

        oData.setTotal(nTotal);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 테이블 칼럼 관리 기본값 콤보박스 정보를 조회한다. (15.05.08)
     *
     * @param param Map<String, Object>
     * @return ActionResult
     * @throws Exception
     */
    @RequestMapping(value = "/drop-down-list")
    @ResponseBody
    public ModelAndView searchTclDtlBasiDropDownList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        Map<String, Object> searchParam = ParamUtils.convertDropDownParam(pmParam);

        searchParam.put("use_yn", "Y");
        searchParam.put("orderBy", "amnt_dttm");
        searchParam.put("orderDirection", "asc");

        int nTotal = coBaVlService.getCoBaVlCount(searchParam);
        List<Map<String, Object>> mdataList = coBaVlService.getCoBaVlList(searchParam);

        oData.setTotal(nTotal);
        oData.setList(mdataList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 용어 이름 정보를 검색한다. (15.05.11)
     *
     * @param param Map<String, Object>
     * @return ActionResult
     * @throws Exception
     */
    @RequestMapping(value = "/termnm")
    @ResponseBody
    public ModelAndView getTermdictionList (@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        ParamUtils.addPagingParam(pmParam);

        pmParam.put("dateType", "all");
        if (pmParam.get("filter") != null) {
            @SuppressWarnings("unchecked")
            Map<String, List<Map<String, String>>> mFilter = (Map<String, List<Map<String, String>>>) pmParam.get("filter");

            if (mFilter.get("filters") != null) {
                List<Map<String, String>> mFilterList = (List<Map<String, String>>) mFilter.get("filters");
                if (mFilterList != null && mFilterList.size() > 0) {
                    Map<String, String> mFilterString = (Map<String, String>) (mFilterList).get(0);

                    pmParam.put("term_nm", mFilterString.get("value"));
                }
            }
        }
        List<Map<String, Object>> mList = trmDtlService.getTrmDtlNmList(pmParam);

        oData.setList(mList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 테이블 칼럼 관리 정보를 삭제한다. (15.05.11)
     *
     * @param param Map<String, Object>
     * @return ActionResult
     * @throws Exception
     */
    @RequestMapping(value = "/colmdelete")
    @ResponseBody
    public ModelAndView deleteTermdiction(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        // 1건일때
        String term_id = StringUtils.defaultString((String) pmParam.get("term_id"));
        pmParam.put("term_id", term_id);

        String selectcheck = StringUtils.defaultString((String) pmParam.get("selectcheck"));
        pmParam.put("selectcheck", selectcheck.split(","));

        tblDtlService.deleteTclDtl(pmParam);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 용어 참조 테이블 내역 검색한다. (15.05.13)
     *
     * @param pmParam Map<String, String>
     * @return ActionResult
     * @throws Exception
     */
    @RequestMapping(value = "/refertable")
    @ResponseBody
    public ModelAndView referTblDtl(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        ParamUtils.addPagingParam(pmParam);
        ParamUtils.addCenterParam(pmParam);

        int nTotal = tblDtlService.getReferTblDtlCount(pmParam);
        List<Map<String, Object>> mList = tblDtlService.getReferTblDtl(pmParam);

        oData.setTotal(nTotal);
        oData.setList(mList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }
}