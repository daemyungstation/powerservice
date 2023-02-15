/*
 * (@)# CompChprDtlController.java
 *
 * @author 배윤정
 * @version 1.0
 * @date 2015/08/05
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

package powerservice.business.biz.web;

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

import powerservice.business.biz.service.ChprBasiService;
import powerservice.business.biz.service.CompChprDtlService;
import powerservice.core.bean.ActionList;
import powerservice.core.bean.ActionResult;
import powerservice.core.util.ParamUtils;

/**
 * 고객사/협력사의 담당자를 관리한다.
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 배윤정
 * @version 1.0
 * @date 2015/08/05
 * @프로그램ID CompChprDtl
 */
@Controller
@RequestMapping(value = "/biz/comp-chpr-dtl")
public class CompChprDtlController {

    @Resource
    private CompChprDtlService compChprDtlService;

    /**
     * 고객사/협력사의 담당자 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
   @RequestMapping(value = "/list")
    public ModelAndView getCompChprDtlList(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        int nTotal = compChprDtlService.getCompChprCount(pmParam);

        ParamUtils.addPagingParam(pmParam);
        List<Map<String, Object>> mList = compChprDtlService.getCompChprList(pmParam);

        oData.setTotal(nTotal);
        oData.setList(mList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 고객사/협력사의 담당자 정보를 등록/수정한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    public ModelAndView saveCompChprDtl(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        Map<String, Object> resultPmParam = new HashMap<String, Object>();

        String biz_id = "";
        String chpr_id = StringUtils.defaultString((String) pmParam.get("chpr_id")); 
        String comp_type = StringUtils.defaultString((String) pmParam.get("comp_type"));
        ParamUtils.addSaveParam(pmParam);

        //DELETE INSERT 실행
        compChprDtlService.deleteCscoChprDtl(pmParam);
        compChprDtlService.deleteCprtCompChprDtl(pmParam);

        //C:고객사, R:협력사
        if("C".equals(comp_type)){
        	biz_id = compChprDtlService.insertCscoChprDtl(pmParam);
    	}else if("R".equals(comp_type)){
    		biz_id = compChprDtlService.insertCprtCompChprDtl(pmParam);
    	}
        
        resultPmParam.put("biz_id", biz_id);
        resultPmParam.put("chpr_id", chpr_id);
        
        oResult.setData(compChprDtlService.getCompChpr(resultPmParam));

        modelAndView.addObject(oResult);
        return modelAndView;
    }
    
    /**
     * 고객사/협력사 정보를 삭제한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
	@RequestMapping(value = "/delete")
    public ModelAndView deleteNobd(@RequestBody Map<String, Object> pmParam) throws Exception {
    	ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();

        String sSelectCheck = StringUtils.defaultString((String) pmParam.get("chpr_selectcheck"));
        
        pmParam.put("biz_id", sSelectCheck.split("-")[0]);
        String[] sBizIdList = sSelectCheck.split(",");
        String[] sChprIdList = new String[sBizIdList.length];
        
        for (int i = 0; i < sBizIdList.length; i++) {
        	sChprIdList[i] = sBizIdList[i].split("-")[1];
		}
        pmParam.put("selectcheck", sChprIdList);
        
        compChprDtlService.deleteCscoChprDtl(pmParam);
        compChprDtlService.deleteCprtCompChprDtl(pmParam);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

	/**
     * 담당자 정보 중복체크를 한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/check-chpr-cnt")
    @ResponseBody
    public ModelAndView checkChprCnt(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        int nTotal = compChprDtlService.getCompChprCount(pmParam);

        oData.setTotal(nTotal);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

}