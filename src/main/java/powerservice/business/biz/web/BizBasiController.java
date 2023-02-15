/*
 * (@)# BizBasiController.java
 *
 * @author 배윤정
 * @version 1.0
 * @date 2015/07/30
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

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import powerservice.business.bean.UserSession;
import powerservice.business.biz.service.BizBasiService;
import powerservice.core.bean.ActionList;
import powerservice.core.bean.ActionResult;
import powerservice.core.util.ParamUtils;
import powerservice.core.util.SessionUtils;

/**
 * 기준값 관리를 한다.
 *
 * Copyright (c) 2015 Company Inwoo Tech Inc.
 *
 * @author 이희철
 * @version 1.0
 * @date 2015/04/01
 * @프로그램ID BizBasi
 */
@Controller
@RequestMapping(value = "/biz")
public class BizBasiController {

	@Resource
	private BizBasiService bizBasiService;

    /**
     * 사업원장 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/biz-basi/list")
    public ModelAndView getBizBasiList(@RequestBody Map<String, Object> pmParam) throws Exception {
    	ModelAndView modelAndView = new ModelAndView("actionResultView");
    	
        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();
        
        ParamUtils.addPagingParam(pmParam);
        ParamUtils.addCenterParam(pmParam);
        
        UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
        
        //나의 관심사업
        Boolean bMineflag = (Boolean) pmParam.get("mine_flag");
        if (bMineflag != null && bMineflag) {
        	pmParam.put("mine_check", "Y");
        	pmParam.put("user_id", oLoginUser.getUserid());
        } else {
        	pmParam.put("mine_check", "");
        }
        
        int nTotal = bizBasiService.getBizBasiCount(pmParam);

        List<Map<String, Object>> mList = bizBasiService.getBizBasiList(pmParam);

        oData.setTotal(nTotal);
        oData.setList(mList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }

    /**
     * 사업원장 정보를 등록/수정한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/biz-basi/save")
    public ModelAndView saveBizBasi(@RequestBody Map<String, Object> pmParam) throws Exception {
    	ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();
        
        String sBizBasiId = StringUtils.defaultString((String) pmParam.get("biz_id"));
        ParamUtils.addSaveParam(pmParam);

        if ("".equals(sBizBasiId)) {
            sBizBasiId = bizBasiService.insertBizBasi(pmParam);
        } else {
            bizBasiService.updateBizBasi(pmParam);
        }

        oResult.setData(bizBasiService.getBizBasi(sBizBasiId));

        modelAndView.addObject(oResult);
        return modelAndView;
    }
    
    /**
     * 사업원장 정보를 삭제한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/biz-basi/delete")
    public ModelAndView deleteBizBasi(@RequestBody Map<String, Object> pmParam) throws Exception {
    	ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        bizBasiService.deleteBizBasi(pmParam);

        modelAndView.addObject(oResult);
        return modelAndView;
    }
    
	/**
     * 사업원장의 담당자수, 영업활동건수, 영업이슈건수를 조회한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/biz-basi/check-info-cnt")
    public ModelAndView checkInfoCnt(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");

        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        int nTotal = bizBasiService.checkInfoCnt(pmParam);

        oData.setTotal(nTotal);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }
    
    /**
     * 관심사업 정보를 등록/수정한다.
     *
     * @param pmParam Map<String, ?>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/cncr-biz-dtl/save")
    public ModelAndView mergeCncrBizDtl(@RequestBody Map<String, Object> pmParam) throws Exception {
    	ModelAndView modelAndView = new ModelAndView("actionResultView");
    	ActionResult oResult = new ActionResult();
    	
    	ParamUtils.addSaveParam(pmParam);
    	UserSession oLoginUser = (UserSession) SessionUtils.getLoginUser();
        pmParam.put("user_id", oLoginUser.getUserid());			
    	
    	String[] sBizIdList = StringUtils.defaultString((String) pmParam.get("biz_selectcheck")).split(",");
    	if (sBizIdList != null) {
    		for(String sBizId : sBizIdList){
    			if (sBizId != null && !"".equals(sBizId) ) {
    				pmParam.put("biz_id", sBizId);
    				bizBasiService.mergeCncrBizDtl(pmParam);
				}
    		}
			
		}
    	modelAndView.addObject(oResult);
        return modelAndView;
    }
    
    /**
     * 관심사업 정보를 검색한다.
     *
     * @param pmParam Map<String, Object>
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/cncr-biz-dtl/list")
    public ModelAndView getCncrBizList(@RequestBody Map<String, Object> pmParam) throws Exception {
    	ModelAndView modelAndView = new ModelAndView("actionResultView");
    	
        ActionResult oResult = new ActionResult();
        ActionList oData = new ActionList();

        int nTotal = bizBasiService.getCncrBizCount(pmParam);

        ParamUtils.addPagingParam(pmParam);
        List<Map<String, Object>> mList = bizBasiService.getCncrBizList(pmParam);

        oData.setTotal(nTotal);
        oData.setList(mList);
        oResult.setData(oData);

        modelAndView.addObject(oResult);
        return modelAndView;
    }
    
    /**
     * 관심사업 정보를 삭제한다.
     *
     * @param pmParam Map<String, ?>
     * @return ActionResult
     * @throws Exception
     */
    @RequestMapping(value = "/cncr-biz-dtl/delete")
    @ResponseBody
    public ModelAndView deleteCncrBiz(@RequestBody Map<String, Object> pmParam) throws Exception {
        ModelAndView modelAndView = new ModelAndView("actionResultView");
        ActionResult oResult = new ActionResult();

        String sSelectcheck = StringUtils.defaultString((String) pmParam.get("cncr_selectcheck"));
        pmParam.put("selectcheck", sSelectcheck.split(","));

        bizBasiService.deleteCncrBiz(pmParam);

        modelAndView.addObject(oResult);
        return modelAndView;
    }
}